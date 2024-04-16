package com.ras.bandostockproject.dto.inventory;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class SellingGeoJSON {

    private int id;
    private String Rectangle;
    private int originId;
    private boolean isLoss;
    private int moneyAdvantage;
    private int moneyLoss;

}
