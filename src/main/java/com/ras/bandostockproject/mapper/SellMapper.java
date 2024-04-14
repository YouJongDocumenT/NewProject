package com.ras.bandostockproject.mapper;

import com.ras.bandostockproject.dto.PurchaseDTO;
import com.ras.bandostockproject.dto.PurchaseListDTO;
import com.ras.bandostockproject.dto.SellListDTO;
import com.ras.bandostockproject.dto.StockListDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SellMapper {

    void insertSellComp(SellListDTO sellListDTO);
    List<SellListDTO> getAllSellComps();
    List<SellListDTO> getAllMachines();
    void insertAllMachine(SellListDTO sellListDTO);
    void insertPerson(SellListDTO sellListDTO);
    void insertAllSell(SellListDTO sellListDTO);
    List<SellListDTO> getAllSellList();
    List<SellListDTO> getAllsellCompList();
    List<SellListDTO> getAllsellListById(int SID);
    List<SellListDTO> getAllsellReceivingList();
    List<SellListDTO> getAllsellCompById(int SID);
    List<SellListDTO> getAllsellPersonById(int SID);
    List<SellListDTO> getAllPersonByIdList(int SID);
    List<SellListDTO> getAllMachineBysellId(int SID);
    List<SellListDTO> getPersonByMachineId(int SID);
    List<StockListDTO> getEapriceByPDT(int SID);
}
