package com.ras.bandostockproject.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
@NoArgsConstructor
public class PrecivRankDTO {
    private int Ranking;
    private String sellCompany;
    private int PreCountResult;
    private int SumPriceResult;

}
