package com.ras.bandostockproject.mapper;

import com.ras.bandostockproject.dto.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DataMapper {

    List<SellDataByYearDTO> getYearDataListSell(String yearData);
    List<PurchaseDataByYearDTO> getYearDataListByPur(String yearData);
    List<PNLRatioDTO> getPnlData(String yearData, String monthData);
    List<PrereceivingRatioDTO> getPreRecivingData(String yearData, String monthData);


    List<MainStockDataDTO> getAllPurchaseSumByMonth();
    List<MainStockDataDTO> getAllSellSumByMonth();
    List<MainStockDataDTO> getAllSellPNLByMonth();
    List<MainStockDataDTO> getAllSellPrecByMonth();
    List<MainStockCountByMonthDTO> getAllPurCountByMonth();
    List<MainStockCountByMonthDTO> getAllSellCountByMonth();
    List<MainStockCountByMonthDTO> getAllPrecivCountByMonth();
}
