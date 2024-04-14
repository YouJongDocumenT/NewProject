package com.ras.bandostockproject.controller;

import com.ras.bandostockproject.dto.PurchaseListDTO;
import com.ras.bandostockproject.dto.StockListDTO;
import com.ras.bandostockproject.dto.StockPriceDTO;
import com.ras.bandostockproject.service.StockService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class StockController {

    private static final Logger logger = LoggerFactory.getLogger(StockController.class);
    private final StockService stockService;

    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    // 재고 화면단
    @GetMapping("stock")
    public String Stock(Model model){

        // 재고정보 조회
        List<StockListDTO> stockList = stockService.stockList();
        model.addAttribute("stockList", stockList);

        // 재고금액 조회
        List<StockPriceDTO> stockPrice = stockService.stockPrice();
        model.addAttribute("stockPrice", stockPrice);

        return "BandoUI/Stock";
    }
}
