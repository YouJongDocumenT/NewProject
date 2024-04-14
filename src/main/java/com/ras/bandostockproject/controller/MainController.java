package com.ras.bandostockproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("mainstock")
    public String mainStock(){

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
