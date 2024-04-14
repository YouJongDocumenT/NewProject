package com.ras.bandostockproject.controller;

import com.ras.bandostockproject.dto.PcompDTO;
import com.ras.bandostockproject.dto.PurchaseListDTO;
import com.ras.bandostockproject.dto.SellListDTO;
import com.ras.bandostockproject.dto.StockPriceDTO;
import com.ras.bandostockproject.service.PurchaseService;
import com.ras.bandostockproject.service.SellService;
import com.ras.bandostockproject.service.StockService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class DataListController {
    private static final Logger logger = LoggerFactory.getLogger(PurchaseController.class);

    private final PurchaseService purchaseService;
    private final StockService stockService;
    private final SellService sellService;

    @Autowired
    public DataListController(PurchaseService purchaseService, StockService stockService, SellService sellService) {
        this.purchaseService = purchaseService;
        this.stockService = stockService;
        this.sellService = sellService;
    }

    // 총 매입 화면단
    @GetMapping("statepricelist")
    public String StatePriceList(Model model){

        // 외상 금액 리스트
        List<PurchaseListDTO> CreditList = purchaseService.purchaseCreditList();
        model.addAttribute("CreditList", CreditList);
        // 선입고 금액 리스트
        List<SellListDTO> ReceivingPriceList = sellService.sellReceivingList();
        model.addAttribute("ReceivingPriceList", ReceivingPriceList);
        // 재고금액 조회
        List<StockPriceDTO> stockPrice = stockService.stockPrice();
        model.addAttribute("stockPrice", stockPrice);

        return "BandoUI/StatePriceList";
    }

    @GetMapping("companylist")
    public String CompanyList(Model model){

        // 매입처정보 조회
        List<PcompDTO> purchaseCompList = purchaseService.purchaseCompList();
        model.addAttribute("purchaseCompList", purchaseCompList);

        // 고객사정보 조회
        List<SellListDTO> sellCompList = sellService.sellCompList();
        model.addAttribute("sellCompList", sellCompList);

        return "BandoUI/CompanyList";
    }

    @ResponseBody
    @PostMapping("companylist/purchaseCompById")
    public Map handlePurchaseId(@RequestBody PcompDTO pcompDTO) {
        // 받아온 purchaseId값에 해당하는 구매정보를 가져옴
        logger.info(String.valueOf(pcompDTO.getPurchaseId()));
        int PID = pcompDTO.getPurchaseId();
        Map<String, Object> data = new HashMap<>();
        data.put("PurchaseCompById", purchaseService.purchaseCompById(PID));
        return  data;
    }

    @ResponseBody
    @PostMapping("companylist/sellCompById")
    public Map handleSellId(@RequestBody SellListDTO sellListDTO) {
        // 받아온 sellId값에 해당하는 구매정보를 가져옴
        int SID = sellListDTO.getSellId();
        Map<String, Object> data = new HashMap<>();
        data.put("sellPersonById", sellService.sellPersonById(SID));
        return  data;
    }

    @ResponseBody
    @PostMapping("companylist/person")
    public Map handlePerson(@RequestBody SellListDTO sellListDTO) {
        // 받아온 sellId값에 해당하는 구매정보를 가져옴
        int SID = sellListDTO.getPersonId();
        Map<String, Object> data = new HashMap<>();
        data.put("PersonByIdList", sellService.PersonByIdList(SID));
        return  data;
    }
}
