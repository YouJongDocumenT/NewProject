package com.ras.bandostockproject.controller;

import com.ras.bandostockproject.dto.inventory.GeoJSON;
import com.ras.bandostockproject.dto.inventory.Polygon;
import com.ras.bandostockproject.dto.inventory.SellingGeoJSON;
import com.ras.bandostockproject.service.inventory.GeometryUtils;
import com.ras.bandostockproject.service.inventory.InventoryService;
import com.ras.bandostockproject.service.inventory.RectangleCutter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.ras.bandostockproject.service.inventory.GeometryUtils.calcSellingSpace;

@Controller
public class InventoryController {

    private static final Logger logger= LoggerFactory.getLogger(InventoryController.class);

    private final InventoryService inventoryService;

    @Autowired
    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }


    // post로 변경
    @Transactional
    @PostMapping("/cutting")
    public ResponseEntity<?> cutting(@RequestBody Map<String, Integer> payload){    // # payload : 프론트에서 받아온 가로 세로 길이

        // # 원본 재고 id도 받아와야함
        int[] dimensions = GeometryUtils.parseRectangleDimensions(inventoryService.selectGeometry());   // 원본 재고 길이 받아와서 가로 세로 길이 배열에 저장
        System.out.println(inventoryService.selectGeometry());
        System.out.println(dimensions[0]+ " " + dimensions[1]);

        RectangleCutter cutter = new RectangleCutter(dimensions[0], dimensions[1]);     // 가로 세로 길이 만큼 재고 생성
        
        // 첫 자르기 작업 시 작업 처리( 별도 처리 하지않으면 cutOptimalRectangle 함수에서 에러남)
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
        int advantage = calcSellingSpace(sellingGeoJSON.getRectangle());
        sellingGeoJSON.setMoneyAdvantage(advantage);
        inventoryService.insertSellingGeometry(sellingGeoJSON);
        inventoryService.updateMoneyAdvantage(sellingGeoJSON);
        cutter.findBoundaryZeros();
        System.out.println("사용가능공간:"+cutter.findBoundaryZeros());
        return ResponseEntity.ok().body("Cutting request processed successfully");
    }

    // 원본 재고 생성
    @PostMapping("/createInventory")
    public ResponseEntity<?> createInventory(@RequestBody Map<String, Integer> payload){

        System.out.println(payload.get("item1"));
        System.out.println(payload.get("item2"));
        String inputPoint = "(0 0, " + payload.get("item1") + " 0, "+ payload.get("item1")
                + " " + payload.get("item2") +", 0 " + payload.get("item2") + "," + " 0 0)";   // mybatis에 넘겨줄 좌표 String으로 변환
        // 재고 새로추가하는 버튼
        GeoJSON geoJSON = new GeoJSON();
        geoJSON.setRectangle(inputPoint);
        inventoryService.insertGeometry(geoJSON);
        System.out.println("Start_Inventory"+geoJSON.getRectangle());
        return ResponseEntity.ok().body("createInventory request processed successfully");
    }

    @GetMapping("/inventory")
    public String inventory(Model model){

        // # 재고 아이디

        GeometryUtils utils = new GeometryUtils();
        // 모든 좌표 불러와서 실재 재고 만큼 자르기


        if(inventoryService.selectGeometry() == null){
            return "inventory";
        }
        GeometryUtils.parseRectangleDimensions(inventoryService.selectGeometry());      // # 원본이 되는 재고 좌표 가져오는 서비스
        System.out.println(inventoryService.selectGeometry());
//        System.out.println("원본 재고 가로 길이:"+GeometryUtils.parseRectangleDimensions(inventoryService.selectGeometry())[0]);
//        System.out.println("원본 재고 세로 길이:"+GeometryUtils.parseRectangleDimensions(inventoryService.selectGeometry())[1]);

        List<Polygon> polygons = new ArrayList<>();
        polygons = utils.parseCoordinates(inventoryService.selectSellingGeometry());    // # db에서 좌표 받아서 list로 저장, model로 넘겨줘야 할 list

        System.out.println(polygons);
        // # 모든 좌표를 JSON으로 받아오는 서비스;
        // inventoryService.selectSellingGeometry();   

        return "inventory";
    }

    @Transactional
    @PostMapping("/loss")
    public ResponseEntity<?> lossInventory(@RequestBody(required = false) Map<String, Integer> payload){ // # @RequestBody Map<String, Integer> payload 수정 필요 -> html에서 손봐야함

        // # 원본 재고 id도 받아와야함
        int[] dimensions = GeometryUtils.parseRectangleDimensions(inventoryService.selectGeometry());   // 원본 재고 길이 받아와서 가로 세로 길이 배열에 저장

        RectangleCutter cutter = new RectangleCutter(dimensions[0], dimensions[1]);     // 가로 세로 길이 만큼 재고 생성

        GeometryUtils utils = new GeometryUtils();
        // 모든 좌표 불러와서 실재 재고 만큼 자르기

        List<Polygon> polygons = new ArrayList<>();
        polygons = utils.parseCoordinates(inventoryService.selectSellingGeometry());    // # db에서 좌표 받아서 list로 저장

        for (Polygon polygon : polygons) {
            cutter.cutRectangle(polygon);
        }

        SellingGeoJSON sellingGeoJSON = new SellingGeoJSON();
        // # model을 받아와서 순서대로 x1, y1, x3, y3을 파라미터로 대입
        sellingGeoJSON.setRectangle(cutter.cutLossArea(0,0,50,50));
        sellingGeoJSON.setMoneyLoss(cutter.getDiscardedArea());
        sellingGeoJSON.setOriginId(1);
        System.out.println("cuttingLoss : " + sellingGeoJSON.getRectangle());
        inventoryService.insertLossGeometry(sellingGeoJSON);
        inventoryService.updateMoneyLoss(sellingGeoJSON);
        // 실적 좌표들을 먼저 계산하고 loss를 계산후 loss난 면적만큼 손실에 += 대입연산 업데이트
        System.out.println(cutter.getDiscardedArea());

        return ResponseEntity.ok().body("createInventory request processed successfully");
    }
}
