package com.ras.bandostockproject.controller;

import com.ras.bandostockproject.dto.StockListDTO;
import com.ras.bandostockproject.dto.StockPriceDTO;
import com.ras.bandostockproject.dto.inventory.GeoJSON;
import com.ras.bandostockproject.service.StockService;
import com.ras.bandostockproject.service.inventory.InventoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class DistanceStockController {

    private static final Logger logger = LoggerFactory.getLogger(DistanceStockController.class);
    private final StockService stockService;
    private final InventoryService inventoryService;

    public DistanceStockController(StockService stockService, InventoryService inventoryService) {
        this.stockService = stockService;
        this.inventoryService = inventoryService;
    }

    @GetMapping("/distancestock")
    public String DistanceStockView(Model model){

        // 재고정보 조회
        List<StockListDTO> stockList = stockService.stockList();
        model.addAttribute("stockList", stockList);

        // 재고금액 조회
        List<StockPriceDTO> stockPrice = stockService.stockPrice();
        model.addAttribute("stockPrice", stockPrice);

        return "BandoUI/DistanceStock/DistanceStockView";
    }

    @ResponseBody
    @PostMapping("/distancestock/getStock")
    public Map<String, Object> getStock(){

        Map<String, Object> data = new HashMap<>();

        // 재고 좌표 가져옴 (id값에 따라 재고를 가져오도록 수정 필요)
        data.put("Rectangle", inventoryService.selectGeometry());
        logger.info("리턴");
        return data;
    }

}
