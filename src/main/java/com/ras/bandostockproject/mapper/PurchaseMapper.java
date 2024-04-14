package com.ras.bandostockproject.mapper;

import com.ras.bandostockproject.dto.PcompDTO;
import com.ras.bandostockproject.dto.PurchaseDTO;
import com.ras.bandostockproject.dto.PurchaseListDTO;
import com.ras.bandostockproject.dto.StockListDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PurchaseMapper {

    void insertPurchaseComp(PcompDTO pcompDTO);
    List<PcompDTO> getAllPurchaseComps();
    void insertPurchase(PurchaseDTO purchaseDTO);
    List<PurchaseListDTO> getAllPurchaseList();
    List<StockListDTO> getAllPurchaseRecentData();
    //#################################################################
    List<PcompDTO> getAllpurchaseCompList();
    List<PurchaseListDTO> getAllpurchaseListById(int PID);

    List<PurchaseListDTO> getAllpurchaseCreditList();
    List<PcompDTO> getAllpurchaseCompById(int PID);
}
