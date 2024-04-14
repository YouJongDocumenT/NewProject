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
public class SellListDTO {

    private int preReceiving;
    private int sellId;
    private String sellCompany;
    private String address;
    private String compNumber;
    private String email;
    private int preReceivingCount;

    private int machineId;
    private String machineName;
    private String memo;

    private int personId;
    private String person;
    private String personNumber;
    private String comMemo;

    private int sellPDTNUM;
    private String product;
    private int purchasePDTNUM;
    private String standard;
    private int sellQuantity;
    private int sellEaprice;
    private int sellPrice;
    private int tax;
    private Date sellDate;

}
