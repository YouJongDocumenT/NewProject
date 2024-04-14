package com.ras.bandostockproject.dto;

import lombok.*;


@Getter
@Setter
@ToString
@NoArgsConstructor
public class PcompDTO {
    private int purchaseId;
    private String purchaseCompany;
    private String address;
    private String compNumber;
    private String email;
    private String comMemo;
    private int credit_count;

}
