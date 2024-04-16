package com.ras.bandostockproject.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
@NoArgsConstructor
public class MainStockDataDTO {
    private String SumPurchaseByMonth;
    private String SumSellByMonth;
    private String TotalResult;
    private String TotalPreReciving;

}
