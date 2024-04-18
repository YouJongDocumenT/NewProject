package com.ras.bandostockproject.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
@NoArgsConstructor
public class PercentDTO {
    private float PurchasePercent;
    private float SellPercent;
    private float PrecivPercent;
}
