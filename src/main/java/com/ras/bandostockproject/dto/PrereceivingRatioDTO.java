package com.ras.bandostockproject.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
@NoArgsConstructor
public class PrereceivingRatioDTO {
    private String year;
    private String month;
    private int pRequallZeroCount;
    private int sellSumPceZero;
    private int pRequallOneCount;
    private int sellSumPceOne;
}
