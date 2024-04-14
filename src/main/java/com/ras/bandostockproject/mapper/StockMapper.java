package com.ras.bandostockproject.mapper;

import com.ras.bandostockproject.dto.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface StockMapper {

    void insertStock(StockListDTO stockListDTO);
    List<StockListDTO> getAllStockProduct();

    void updateStockQuantityRefresh(int SellQuantity, int PurchasePDTNUM);
    void deleteZeroQuantityStock();
    void updateStockPriceAllPrice(int purchasePrice);
    void updateAllSellCNT(int sellPrice);
    int getPurchaseEapriceByStock(int purchasePDTNUM);
    void updateRevenue(int REVENUE);
    void updateTax(int tax);
    List<StockListDTO> stockList();
    List<StockPriceDTO> stockPrice();
    void updateAllStockPrice(int ResultPrice);
    void updateReceivingPriceCNT(int preReceivingPrice);
    void updateStateCreditCNT(int creditPrice);
}
