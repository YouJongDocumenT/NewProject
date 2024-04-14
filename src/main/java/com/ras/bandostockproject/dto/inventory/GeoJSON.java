package com.ras.bandostockproject.dto.inventory;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class GeoJSON {

    private int idGeometry;
    private String Rectangle;
    private int profit;
    private int loss;

}
