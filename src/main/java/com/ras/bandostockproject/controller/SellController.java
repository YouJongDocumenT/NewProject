package com.ras.bandostockproject.controller;

import com.ras.bandostockproject.dto.PcompDTO;
import com.ras.bandostockproject.dto.SellListDTO;
import com.ras.bandostockproject.dto.StockListDTO;
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
public class SellController {

    private static final Logger logger = LoggerFactory.getLogger(SellController.class);

    private final PurchaseService purchaseService;
    private final SellService sellService;
    private final StockService stockService;

    @Autowired
    public SellController(PurchaseService purchaseService, SellService sellService, StockService stockService) {
        this.purchaseService = purchaseService;
        this.sellService = sellService;
        this.stockService = stockService;
    }

    // 총 판매 화면단
    @GetMapping("sell")
    public String Sell(Model model){

        // 판매리스트 조회
        List<SellListDTO> sellList = sellService.sellList();
        model.addAttribute("SellList", sellList);

        // 재고금액 조회
        List<StockPriceDTO> stockPrice = stockService.stockPrice();
        model.addAttribute("stockPrice", stockPrice);

        return "BandoUI/Sell";
    }

    // 고객사별 매입 화면단
    @GetMapping("sellcomplist")
    public String SellCompList(Model model){

        // 고객사정보 조회
        List<SellListDTO> sellCompList = sellService.sellCompList();
        model.addAttribute("sellCompList", sellCompList);

        // 재고금액 조회
        List<StockPriceDTO> stockPrice = stockService.stockPrice();
        model.addAttribute("stockPrice", stockPrice);

        return "BandoUI/SellCompList";
    }

    // sellId에 대한 매출 정보 조회
    @ResponseBody
    @PostMapping("sellcomplist/sellid")
    public Map handleSellId(@RequestBody SellListDTO sellListDTO) {
        // 받아온 sellId값에 해당하는 구매정보를 가져옴
        int SID = sellListDTO.getSellId();
        Map<String, Object> data = new HashMap<>();
        data.put("SellListById", sellService.sellListById(SID));
        return  data;
    }


    @GetMapping("/selladd")
    public String SellAdd(Model model){
        // 재고 조회
        List<StockListDTO> StockProduct = stockService.StockProduct();
        model.addAttribute("StockProduct", StockProduct);

        // 고객사정보 조회
        List<SellListDTO> sellComps = sellService.SellComps();
        model.addAttribute("sellComps", sellComps);
        logger.info(sellComps.toString());

        // 기계정보 조회
        List<SellListDTO> machines = sellService.machines();
        model.addAttribute("machines", machines);

        // 판매리스트 조회
        List<SellListDTO> sellList = sellService.sellList();
        model.addAttribute("SellList", sellList);
        System.out.println(sellList);

        // 재고금액 조회
        List<StockPriceDTO> stockPrice = stockService.stockPrice();
        model.addAttribute("stockPrice", stockPrice);


        return "BandoUI/SellAdd";
    }

    // 회사정보 저장
    @ResponseBody
    @PostMapping("/selladd/saveSellComp")
    public String saveSellComp(SellListDTO sellListDTO) {
        logger.info("고객사 정보 추가");
        sellService.saveSellComp(sellListDTO);
        return "구매 정보가 성공적으로 저장되었습니다.";
    }

    // 기계정보 저장
    @ResponseBody
    @PostMapping("/selladd/saveMachine")
    public String saveMachine(SellListDTO sellListDTO) {
        logger.info("기계 정보 추가");
        sellService.saveMachine(sellListDTO);
        return "구매 정보가 성공적으로 저장되었습니다.";
    }

    // 담당자정보 저장
    @ResponseBody
    @PostMapping("/selladd/savePerson")
    public String savePerson(SellListDTO sellListDTO) {
        logger.info("담당자 정보 추가");
        sellService.savePerson(sellListDTO);
        return "담당자 정보가 성공적으로 저장되었습니다.";
    }

    // 판매정보 저장
    @ResponseBody
    @PostMapping("/selladd/saveSell")
    public String saveSell(SellListDTO sellListDTO) {
        logger.info("판매 정보 추가");
        sellService.saveSell(sellListDTO);
        System.out.println(sellListDTO.getPreReceiving());

        int sellPrice = sellListDTO.getSellPrice();
        // 총 판매금액 업데이트
        stockService.updateAllSellCNT(sellPrice);
        if(sellListDTO.getPreReceiving() == 1){
            logger.info("선입고 금액 추가");
            stockService.updateReceivingPriceCNT(sellPrice);
        }

        // 순수익 계산
        int sellEaprice = sellListDTO.getSellEaprice();
        int sellQuantity = sellListDTO.getSellQuantity();
        int purchasePDTNUM = sellListDTO.getPurchasePDTNUM();
        int purchaseEaprice = stockService.getPurchaseEapriceByStock(purchasePDTNUM);
        logger.info(String.valueOf(purchaseEaprice));
        /* (개당판매가격 * 판매개수) - (개당구매가격 * 판매개수) */
        int REVENUE = (sellEaprice * sellQuantity) - (purchaseEaprice * sellQuantity);
        stockService.updateRevenue(REVENUE);

        // 총 재고금액 계산
        /* (개당구매가격 * 판매개수) */
        int ResultPrice = (purchaseEaprice * sellQuantity);
        stockService.updateAllStockPrice(ResultPrice);

        // 누적세금 업데이트
        int tax = sellListDTO.getTax();
        stockService.updateTax(tax);

        // 재고 카운트 0인것 삭제
        int SellQuantity = sellListDTO.getSellQuantity();
        int PurchasePDTNUM = sellListDTO.getPurchasePDTNUM();
        stockService.StockQuantityRefresh(SellQuantity, PurchasePDTNUM);
        stockService.deleteZeroQuantityStock();
        return "구매 정보가 성공적으로 저장되었습니다.";
    }

    @ResponseBody
    @PostMapping("selladd/addperson")
    public Map handleAddPerson(@RequestBody SellListDTO sellListDTO) {
        // 받아온 sellId값에 해당하는 구매정보를 가져옴
        int SID = sellListDTO.getSellId();
        System.out.println(SID);
        Map<String, Object> data = new HashMap<>();
        data.put("MachineBysellId", sellService.MachineBysellId(SID));
        return  data;
    }

    @ResponseBody
    @PostMapping("selladd/getperson")
    public Map handleGetPerson(@RequestBody SellListDTO sellListDTO) {
        // 받아온 sellId값에 해당하는 구매정보를 가져옴
        int SID = sellListDTO.getMachineId();
        System.out.println(SID);
        Map<String, Object> data = new HashMap<>();
        data.put("PersonByMachineId", sellService.PersonByMachineId(SID));
        return  data;
    }

    @ResponseBody
    @PostMapping("selladd/getpdtnum")
    public Map handleGetPDTNUM(@RequestBody StockListDTO stockListDTO) {
        // 받아온 pdtnum값에 해당하는 개당 가격정보를 가져옴
        int SID = stockListDTO.getPurchasePDTNUM();
        System.out.println(SID);
        Map<String, Object> data = new HashMap<>();;
        data.put("EapriceByPDT", sellService.EapriceByPDT(SID));
        System.out.println(data);
        return  data;
    }

}
