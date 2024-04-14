package com.ras.bandostockproject.mapper;


import com.ras.bandostockproject.dto.inventory.GeoJSON;
import com.ras.bandostockproject.dto.inventory.SellingGeoJSON;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface InventoryMapper {
    void insertGeometry(GeoJSON geoJSON);

    void insertSellingGeometry(SellingGeoJSON sellingGeoJSON);

    void insertLossGeometry(SellingGeoJSON sellingGeoJSON);

    List<SellingGeoJSON> selectSellingGeometry();

}
