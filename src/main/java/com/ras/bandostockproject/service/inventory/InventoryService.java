package com.ras.bandostockproject.service.inventory;

import com.ras.bandostockproject.dto.inventory.GeoJSON;
import com.ras.bandostockproject.dto.inventory.SellingGeoJSON;
import com.ras.bandostockproject.mapper.InventoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventoryService {
    private final InventoryMapper inventoryMapper;

    @Autowired
    public InventoryService(InventoryMapper inventoryMapper) {
        this.inventoryMapper = inventoryMapper;
    }

    public void insertGeometry(GeoJSON geoJSON){
        inventoryMapper.insertGeometry(geoJSON);
    }

    public void insertSellingGeometry(SellingGeoJSON sellingGeoJSON){
        inventoryMapper.insertSellingGeometry(sellingGeoJSON);
    }

    public void insertLossGeometry(SellingGeoJSON sellingGeoJSON){
        inventoryMapper.insertLossGeometry(sellingGeoJSON);
    }

    public List<SellingGeoJSON> selectSellingGeometry(){
        return inventoryMapper.selectSellingGeometry();
    }

    public GeoJSON selectGeometry(){
        return inventoryMapper.selectGeometry();
    }
}
