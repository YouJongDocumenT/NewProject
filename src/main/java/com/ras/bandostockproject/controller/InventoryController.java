package com.ras.bandostockproject.controller;

import com.ras.bandostockproject.dto.inventory.GeoJSON;
import com.ras.bandostockproject.dto.inventory.SellingGeoJSON;
import com.ras.bandostockproject.service.inventory.InventoryService;
import com.ras.bandostockproject.service.inventory.RectangleCutter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

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

        RectangleCutter cutter = new RectangleCutter(20, 100);

        SellingGeoJSON sellingGeoJSON = new SellingGeoJSON();
        sellingGeoJSON.setRectangle(cutter.cutRectangle(payload.get("item1"), payload.get("item2")));
        sellingGeoJSON.setOriginId(1);
        System.out.println("cutting : " + sellingGeoJSON.getRectangle());
        inventoryService.insertSellingGeometry(sellingGeoJSON);

        return ResponseEntity.ok().body("Cutting request processed successfully");
    }

    @GetMapping("/createInventory")
    public void createInventory(){

        GeoJSON geoJSON = new GeoJSON();
        geoJSON.setRectangle("(0 0, 100 0, 0 100, 100 100, 0 0)");
        inventoryService.insertGeometry(geoJSON);
        System.out.println("Start_Inventory"+geoJSON.getRectangle());
    }

    @GetMapping("/inventory")
    public String inventory(Model model){

        RectangleCutter cutter = new RectangleCutter(20, 100);

        cutter.cutRectangle(3, 4);
        cutter.cutRectangle(5, 5);
        cutter.cutRectangle(10, 10);
        cutter.cutRectangle(3, 4);
        cutter.cutRectangle(3, 4);
        cutter.cutRectangle(3, 4);
        cutter.cutRectangle(2, 4);
        cutter.cutRectangle(4, 2);

        cutter.printBoard();

//        List<SellingGeoJSON> list = inventoryService.selectSellingGeometry();
//        System.out.println(list);
//        GeometryUtils utils = new GeometryUtils();
//        List<Polygon> allPolygons = GeometryUtils.parseCoordinates(list);
//        for (Polygon polygon : allPolygons) {
//            System.out.println("Polygon Points: " + polygon);
//        }

        //        InventoryManager inventoryManager = new InventoryManager(100, 200);
//        inventoryManager.markSoldArea(1, 1, 50, 50);
//        inventoryManager.markSoldArea(51, 1, 62 , 62);
//        inventoryManager.markSoldArea(0, 0, 10, 10);  // Overlapping test
//
//        System.out.println("Total unavailable area before scrap: " + inventoryManager.calculateTotalUnavailableArea() + " cm^2");
//        inventoryManager.printInventory();
//        // Marking a scrap area and calculating loss
//        int lossArea = inventoryManager.calculateLossArea(50, 50, 70, 70);
//        System.out.println("Loss area due to scrap: " + lossArea + " cm^2");
//
//        inventoryManager.printInventory();
//        System.out.println("Total unavailable area after scrap: " + inventoryManager.calculateTotalUnavailableArea() + " cm^2");

        // cutting 하면서 실적 면적은 컬럼에 += 대입하며 업데이트

        // inventory에 redirect
        // 모든 좌표들을 불러와서 면적 계산 후 화면에 표기

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
