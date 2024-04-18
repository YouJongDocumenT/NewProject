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


    // 이번달 총 매입액 조회
    public List<MainStockDataDTO> PurchaseSumByMonth() {
        logger.info("이번달 총 매입액 정보 조회");
        return dataMapper.getAllPurchaseSumByMonth();
    }
    // 이번달 총 매출액 조회
    public List<MainStockDataDTO> SellSumByMonth() {
        logger.info("이번달 총 매출액 정보 조회");
        return dataMapper.getAllSellSumByMonth();
    }
    // 이번달 PNL 조회
    public List<MainStockDataDTO> SellPNLByMonth() {
        logger.info("이번달 PNL 조회");
        return dataMapper.getAllSellPNLByMonth();
    }
    // 이번달 선입고 조회
    public List<MainStockDataDTO> SellPrecByMonth() {
        logger.info("이번달 선입고 조회");
        return dataMapper.getAllSellPrecByMonth();
    }

    //월별 매입 건수
    public List<MainStockCountByMonthDTO> PurCountByMonth() {
        logger.info("월별 매입 건수 조회");
        return dataMapper.getAllPurCountByMonth();
    }
    //월별 매출 건수
    public List<MainStockCountByMonthDTO> SellCountByMonth() {
        logger.info("월별 매출 건수 조회");
        return dataMapper.getAllSellCountByMonth();
    }
    //월별 선입고 건수
    public List<MainStockCountByMonthDTO> PrecivCountByMonth() {
        logger.info("월별 선입고 건수 조회");
        return dataMapper.getAllPrecivCountByMonth();
    }


    // 올해 매입 내역카운트 검색
    public List<SellDataByYearDTO> DataListSellByYear() {
        logger.info("올해 매입 내역카운트 검색");
        return dataMapper.getDataListSellByYear();
    }

    // 올해 매출 내역카운트 검색
    public List<PurchaseDataByYearDTO> DataListByPurByYear() {
        logger.info("올해 매출 내역카운트 검색");
        return dataMapper.getDataListByPurByYear();
    }

    // 올해 PNL 내역카운트 검색
    public List<PNLdataDTO> PNLDataListByYear() {
        logger.info("올해 PNL 내역카운트 검색");
        return dataMapper.getPNLDataListByYear();
    }



    //올해 전월대비 매입액 비율
    public List<PercentDTO> PurchaseDataPercentByMonth() {
        logger.info("올해 전월대비 매입액 비율");
        return dataMapper.getPurchaseDataPercentByMonth();
    }

    //올해 전월대비 매출액 비율
    public List<PercentDTO> SellDataPercentByMonth() {
        logger.info("올해 전월대비 매출액 비율");
        return dataMapper.getSellDataPercentByMonth();
    }

    //올해 전월대비 선입고액 비율
    public List<PercentDTO> PrecivDataPercentByMonth() {
        logger.info("올해 전월대비 선입고액 비율");
        return dataMapper.getPrecivDataPercentByMonth();
    }

    //선입고 금액 랭킹
    public List<PrecivRankDTO> PrecivRankData() {
        logger.info("선입고 금액 랭킹");
        return dataMapper.getPrecivRankData();
    }
}
