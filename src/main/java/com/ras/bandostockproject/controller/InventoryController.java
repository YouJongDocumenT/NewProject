package com.ras.bandostockproject.controller;

import com.ras.bandostockproject.dto.inventory.GeoJSON;
import com.ras.bandostockproject.dto.inventory.Polygon;
import com.ras.bandostockproject.dto.inventory.SellingGeoJSON;
import com.ras.bandostockproject.service.inventory.GeometryUtils;
import com.ras.bandostockproject.service.inventory.InventoryService;
import com.ras.bandostockproject.service.inventory.RectangleCutter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class InventoryController {

    private final InventoryService inventoryService;

    @Autowired
    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }


    // post로 변경
    @PostMapping("/cutting")
    public ResponseEntity<?> cutting(@RequestBody Map<String, Integer> payload){

        int[] dimensions = GeometryUtils.parseRectangleDimensions(inventoryService.selectGeometry());   // 원본 재고 길이 받아와서 가로 세로 길이 배열에 저장
        System.out.println(inventoryService.selectGeometry());
        System.out.println(dimensions[0]+ " " + dimensions[1]);

        RectangleCutter cutter = new RectangleCutter(dimensions[0], dimensions[1]);     // 가로 세로 길이 만큼 재고 생성


        if(!inventoryService.selectSellingGeometry().isEmpty())
        {
            GeometryUtils utils = new GeometryUtils();
            // 모든 좌표 불러와서 실재 재고 만큼 자르기

            List<Polygon> polygons = new ArrayList<>();
            polygons = utils.parseCoordinates(inventoryService.selectSellingGeometry());    // # db에서 좌표 받아서 list로 저장

            for (Polygon polygon : polygons) {
                cutter.cutRectangle(polygon);
            }
        }
        System.out.println("create successfully");

        SellingGeoJSON sellingGeoJSON = new SellingGeoJSON();
        sellingGeoJSON.setRectangle(cutter.cutOptimalRectangle(payload.get("item1"), payload.get("item2")));
        cutter.printBoard();
        sellingGeoJSON.setOriginId(1);
        System.out.println("cutting : " + sellingGeoJSON.getRectangle());
        inventoryService.insertSellingGeometry(sellingGeoJSON);

        return ResponseEntity.ok().body("Cutting request processed successfully");
    }

    @PostMapping("/createInventory")
    public ResponseEntity<?> createInventory(@RequestBody Map<String, Integer> payload){

        System.out.println(payload.get("item1"));
        System.out.println(payload.get("item2"));
        String inputPoint = "(0 0, " + payload.get("item1") + " 0, 0 " + payload.get("item2") + ", " + payload.get("item1") + " " + payload.get("item2")
            + "," + " 0 0)";
        // 재고 새로추가하는 버튼
        GeoJSON geoJSON = new GeoJSON();
        geoJSON.setRectangle(inputPoint);
        inventoryService.insertGeometry(geoJSON);
        System.out.println("Start_Inventory"+geoJSON.getRectangle());
        return ResponseEntity.ok().body("createInventory request processed successfully");
    }

    @GetMapping("/inventory")
    public String inventory(Model model){

        GeometryUtils utils = new GeometryUtils();
        // 모든 좌표 불러와서 실재 재고 만큼 자르기

        GeometryUtils.parseRectangleDimensions(inventoryService.selectGeometry());      // # 원본이 되는 재고 좌표 가져오는 서비스
        System.out.println(inventoryService.selectGeometry());
        System.out.println("원본 재고 가로 길이:"+GeometryUtils.parseRectangleDimensions(inventoryService.selectGeometry())[0]);
        System.out.println("원본 재고 세로 길이:"+GeometryUtils.parseRectangleDimensions(inventoryService.selectGeometry())[1]);

        List<Polygon> polygons = new ArrayList<>();
        polygons = utils.parseCoordinates(inventoryService.selectSellingGeometry());    // # db에서 좌표 받아서 list로 저장, model로 넘겨줘야 할 list

        System.out.println(polygons);
        // # 모든 좌표를 JSON으로 받아오는 서비스;
        // inventoryService.selectSellingGeometry();   

        return "inventory";
    }

    @GetMapping("/loss")
    public void lossInventory(){

        SellingGeoJSON sellingGeoJSON = new SellingGeoJSON();
        sellingGeoJSON.setRectangle("(0 0, 20 0, 0 20, 20 20, 0 0)");
        sellingGeoJSON.setOriginId(1);
        System.out.println("cuttingLoss : " + sellingGeoJSON.getRectangle());
        inventoryService.insertLossGeometry(sellingGeoJSON);
        // 실적 좌표들을 먼저 계산하고 loss를 계산후 loss난 면적만큼 손실에 += 대입연산 업데이트
        
    }

}
