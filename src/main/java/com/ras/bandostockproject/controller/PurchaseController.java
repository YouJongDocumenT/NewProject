package com.ras.bandostockproject.controller;

import com.ras.bandostockproject.dto.*;
import com.ras.bandostockproject.service.PurchaseService;
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

import java.util.*;

@Controller
public class PurchaseController {
    private static final Logger logger = LoggerFactory.getLogger(PurchaseController.class);

    private final PurchaseService purchaseService;
    private final StockService stockService;

    @Autowired
    public PurchaseController(PurchaseService purchaseService, StockService stockService) {
        this.purchaseService = purchaseService;
        this.stockService = stockService;
    }

    // 총 매입 화면단
    @GetMapping("purchase")
    public String Purchase(Model model){

        List<PurchaseListDTO> purchaseList = purchaseService.purchaseList();
        model.addAttribute("purchaseList", purchaseList);
        // 재고금액 조회
        List<StockPriceDTO> stockPrice = stockService.stockPrice();
        model.addAttribute("stockPrice", stockPrice);

        return "BandoUI/Purchase";
    }


    // 거래처별 매입 화면단
    @GetMapping("purchasecomplist")
    public String PurchaseCompList(Model model){

        // 매입처정보 조회
        List<PcompDTO> purchaseCompList = purchaseService.purchaseCompList();
        model.addAttribute("purchaseCompList", purchaseCompList);

        // 재고금액 조회
        List<StockPriceDTO> stockPrice = stockService.stockPrice();
        model.addAttribute("stockPrice", stockPrice);

        return "BandoUI/PurchaseCompList";
    }

    @ResponseBody
    @PostMapping("purchasecomplist/purchaseid")
    public Map handlePurchaseId(@RequestBody PcompDTO pcompDTO) {
        // 받아온 purchaseId값에 해당하는 구매정보를 가져옴
        int PID = pcompDTO.getPurchaseId();
        Map<String, Object> data = new HashMap<>();
        data.put("PurchaseListById", purchaseService.purchaseListById(PID));
        return  data;
    }

    // 매입등록 화면단
    @GetMapping("/purchaseadd")
    public String PurchaseAdd(Model model){
        List<PcompDTO> purchaseComps = purchaseService.PurchaseComps();
        model.addAttribute("purchaseComps", purchaseComps);

        List<PurchaseListDTO> purchaseList = purchaseService.purchaseList();
        model.addAttribute("purchaseList", purchaseList);

        // 재고금액 조회
        List<StockPriceDTO> stockPrice = stockService.stockPrice();
        model.addAttribute("stockPrice", stockPrice);

        return "BandoUI/PurchaseAdd";
    }

    // 회사정보 저장
    @ResponseBody
    @PostMapping("/purchaseadd/saveComp")
    public String savePurchaseComp(PcompDTO pcompDTO) {
        logger.info("구매처 정보 추가");
        purchaseService.saveComp(pcompDTO);
        return "구매 정보가 성공적으로 저장되었습니다.";
    }

    // 구매정보 저장
    @ResponseBody
    @PostMapping("/purchaseadd/savePurchase")
    public String savePurchase(PurchaseDTO purchaseDTO) {
        logger.info("구매처 정보 추가");
        purchaseService.savePurchase(purchaseDTO);

        int purchasePrice = purchaseDTO.getPurchasePrice();
        stockService.updateStockPriceAllPrice(purchasePrice);
        if(purchaseDTO.getCredit() == 1){
            logger.info("외상 금액 추가");
            stockService.updateStateCreditCNT(purchasePrice);
        }

        // purchaseService.purchaseRecentData()가 ArrayList를 반환한다고 가정
        ArrayList<StockListDTO> recentDataList = (ArrayList<StockListDTO>) purchaseService.purchaseRecentData();

        // 최근 데이터가 있는지 확인 후 재고테이불에 저장 작업 수행
        if (!recentDataList.isEmpty()) {
            StockListDTO stockListDTO = recentDataList.get(0); // 최근 데이터만 사용
            stockService.saveStock(stockListDTO);
        }

        return "구매 정보가 성공적으로 저장되었습니다.";
    }

}
