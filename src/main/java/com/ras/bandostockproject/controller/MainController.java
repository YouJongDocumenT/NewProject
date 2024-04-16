package com.ras.bandostockproject.controller;

import com.ras.bandostockproject.dto.MainStockCountByMonthDTO;
import com.ras.bandostockproject.dto.MainStockDataDTO;
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
        System.out.println(PurchaseSumByMonth);
        // 이번달 총 매출액
        List<MainStockDataDTO> SellSumByMonth = dataService.SellSumByMonth();
        model.addAttribute("SellSumByMonth", SellSumByMonth);
        System.out.println(SellSumByMonth);
        // 이번달 PNL
        List<MainStockDataDTO> SellPNLByMonth = dataService.SellPNLByMonth();
        model.addAttribute("SellPNLByMonth", SellPNLByMonth);
        System.out.println(SellPNLByMonth);
        //이번달 선입고 건수
        List<MainStockDataDTO> SellPrecByMonth = dataService.SellPrecByMonth();
        model.addAttribute("SellPrecByMonth", SellPrecByMonth);
        System.out.println(SellPrecByMonth);


        //월별 매입 건수
        List<MainStockCountByMonthDTO> PurCountByMonth = dataService.PurCountByMonth();
        model.addAttribute("PurCountByMonth", PurCountByMonth);
        System.out.println(PurCountByMonth);
        //월별 매출 건수
        List<MainStockCountByMonthDTO> SellCountByMonth = dataService.SellCountByMonth();
        model.addAttribute("SellCountByMonth", SellCountByMonth);
        System.out.println(SellCountByMonth);
        //월별 선입고 건수
        List<MainStockCountByMonthDTO> PrecivCountByMonth = dataService.PrecivCountByMonth();
        model.addAttribute("PrecCountByMonth", PrecivCountByMonth);
        System.out.println(PrecivCountByMonth);

        return "BandoUI/mainStock";
    }















    @GetMapping("datalist")
    public String DataList(){

        return "BandoUI/DataList";
    }
    @GetMapping("purchaselist")
    public String Purchaselist(){

        return "BandoUI/purchaselist";
    }
    @GetMapping("selllist")
    public String Selllist(){

        return "BandoUI/selllist";
    }
    @GetMapping("tradelist")
    public String Tradelist(){

        return "BandoUI/tradelist";
    }


    @GetMapping("test")
    public String TemplateTest(){

        return "BandoUI/TemplateUI/PurchaseCompList";
    }

}
