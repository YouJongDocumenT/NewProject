package com.ras.bandostockproject.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
@NoArgsConstructor
public class PNLRatioDTO {
    private String year;
    private String month;
    private String AllSumPce;
    private String AllSumSce;
}
