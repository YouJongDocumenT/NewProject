package com.ras.bandostockproject.service;

import com.ras.bandostockproject.dto.*;
import com.ras.bandostockproject.mapper.DataMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DataService {

    private static final Logger logger = LoggerFactory.getLogger(DataService.class);

    private final DataMapper dataMapper;

    public DataService(DataMapper dataMapper) {
        this.dataMapper = dataMapper;
    }


    // 년도에따른 매입매출 내역카운트 검색
    public List<SellDataByYearDTO> YearDataListSell(String yearData) {
        logger.info("년도에따른 매입매출 내역카운트 검색");
        return dataMapper.getYearDataListSell(yearData);
    }

    // 년도에따른 매입매출 내역카운트 검색
    public List<PurchaseDataByYearDTO> YearDataListByPur(String yearData) {
        logger.info("년도에따른 매입매출 내역카운트 검색");
        return dataMapper.getYearDataListByPur(yearData);
    }

    // PNL 검색
    public List<PNLRatioDTO> PnlData(String yearData, String monthData) {
        logger.info("PNL 검색");
        return dataMapper.getPnlData(yearData, monthData);
    }

    // PreRecivingData 검색
    public List<PrereceivingRatioDTO> PreRecivingData(String yearData, String monthData) {
        logger.info("PNL 검색");
        return dataMapper.getPreRecivingData(yearData, monthData);
    }
}
