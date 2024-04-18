package com.ras.bandostockproject.controller;

import com.ras.bandostockproject.dto.*;
import com.ras.bandostockproject.service.DataService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class MainController {

    private final DataService dataService;

    public MainController(DataService dataService) {
        this.dataService = dataService;
    }

    @GetMapping("mainstock")
    public String mainStock(Model model){
        // 이번달 총 매입액
        List<MainStockDataDTO> PurchaseSumByMonth = dataService.PurchaseSumByMonth();
        model.addAttribute("PurchaseSumByMonth", PurchaseSumByMonth);

        // 이번달 총 매출액
        List<MainStockDataDTO> SellSumByMonth = dataService.SellSumByMonth();
        model.addAttribute("SellSumByMonth", SellSumByMonth);

        // 이번달 PNL
        List<MainStockDataDTO> SellPNLByMonth = dataService.SellPNLByMonth();
        model.addAttribute("SellPNLByMonth", SellPNLByMonth);

        //이번달 선입고 건수
        List<MainStockDataDTO> SellPrecByMonth = dataService.SellPrecByMonth();
        model.addAttribute("SellPrecByMonth", SellPrecByMonth);



        //월별 매입 건수
        List<MainStockCountByMonthDTO> PurCountByMonth = dataService.PurCountByMonth();
        model.addAttribute("PurCountByMonth", PurCountByMonth);

        //월별 매출 건수
        List<MainStockCountByMonthDTO> SellCountByMonth = dataService.SellCountByMonth();
        model.addAttribute("SellCountByMonth", SellCountByMonth);

        //월별 선입고 건수
        List<MainStockCountByMonthDTO> PrecivCountByMonth = dataService.PrecivCountByMonth();
        model.addAttribute("PrecCountByMonth", PrecivCountByMonth);



        //올해 매입 내역카운트 검색
        List<SellDataByYearDTO> DataListSellByYear = dataService.DataListSellByYear();
        model.addAttribute("DataListSellByYear", DataListSellByYear);


        //올해 매출 내역카운트 검색
        List<PurchaseDataByYearDTO> DataListByPurByYear = dataService.DataListByPurByYear();
        model.addAttribute("DataListByPurByYear", DataListByPurByYear);


        //올해 PNL 내역카운트 검색
        List<PNLdataDTO> PNLDataListByYear = dataService.PNLDataListByYear();
        model.addAttribute("PNLDataListByYear", PNLDataListByYear);




        //올해 전월대비 매입액 비율
        List<PercentDTO> PurchaseDataPercentByMonth = dataService.PurchaseDataPercentByMonth();
        model.addAttribute("PurchaseDataPercentByMonth", PurchaseDataPercentByMonth);


        //올해 전월대비 매출액 비율
        List<PercentDTO> SellDataPercentByMonth = dataService.SellDataPercentByMonth();
        model.addAttribute("SellDataPercentByMonth", SellDataPercentByMonth);


        //올해 전월대비 선입고액 비율
        List<PercentDTO> PrecivDataPercentByMonth = dataService.PrecivDataPercentByMonth();
        model.addAttribute("PrecivDataPercentByMonth", PrecivDataPercentByMonth);




        //선입고 금액 랭킹
        List<PrecivRankDTO> PrecivRankData = dataService.PrecivRankData();
        model.addAttribute("PrecivRankData", PrecivRankData);
        System.out.println(PrecivRankData);


        return "BandoUI/mainStock";
    }
}
