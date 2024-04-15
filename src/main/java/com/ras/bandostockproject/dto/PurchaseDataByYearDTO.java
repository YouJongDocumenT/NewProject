package com.ras.bandostockproject.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
@NoArgsConstructor
public class PurchaseDataByYearDTO {
    private String year;
    private String PurchaseSumByJan;
    private int PurchaseCountByJan;
    private String PurchaseSumByFeb;
    private int PurchaseCountByFeb;
    private String PurchaseSumByMar;
    private int PurchaseCountByMar;
    private String PurchaseSumByApr;
    private int PurchaseCountByApr;
    private String PurchaseSumByMay;
    private int PurchaseCountByMay;
    private String PurchaseSumByJun;
    private int PurchaseCountByJun;
    private String PurchaseSumByJul;
    private int PurchaseCountByJul;
    private String PurchaseSumByAug;
    private int PurchaseCountByAug;
    private String PurchaseSumBySep;
    private int PurchaseCountBySep;
    private String PurchaseSumByOct;
    private int PurchaseCountByOct;
    private String PurchaseSumByNov;
    private int PurchaseCountByNov;
    private String PurchaseSumByDec;
    private int PurchaseCountByDec;


}
