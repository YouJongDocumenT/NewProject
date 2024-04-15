package com.ras.bandostockproject.controller;

import com.ras.bandostockproject.service.inventory.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class InventoryRestController {

    private final InventoryService inventoryService;

    @Autowired
    public InventoryRestController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    // # 재고 출력 리스트
    @RequestMapping("/inventoryList")
    public List<Integer> getInventoryList(){
        System.out.println(inventoryService.selectInventoryIdList());

        return inventoryService.selectInventoryIdList();
    }
}
