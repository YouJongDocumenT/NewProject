package com.ras.bandostockproject.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
@NoArgsConstructor
public class StockPriceDTO {
    private int stockPrice;
    private int allPruchaseCNT;
    private int allSellCNT;
    private int revenue;
    private int tax;

}
