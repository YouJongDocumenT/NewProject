package com.ras.bandostockproject.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;


@Getter
@Setter
@ToString
@NoArgsConstructor
public class PurchaseListDTO {
    private int credit;
    private String purchaseCompany;
    private String address;
    private String compNumber;
    private String email;
    private int purchaseId;
    private String product;
    private String standard;
    private int purchaseEaprice;
    private int purchaseQuantity;
    private int purchasePrice;
    private int purchasePDTNUM;
    private Date purchaseDate;
}
