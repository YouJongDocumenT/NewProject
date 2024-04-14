package com.ras.bandostockproject.service;

import com.ras.bandostockproject.dto.PurchaseListDTO;
import com.ras.bandostockproject.dto.SellListDTO;
import com.ras.bandostockproject.dto.StockListDTO;
import com.ras.bandostockproject.mapper.SellMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SellService {

    private static final Logger logger = LoggerFactory.getLogger(SellService.class);

    private final SellMapper sellMapper;

    public SellService(SellMapper sellMapper) {
        this.sellMapper = sellMapper;
    }

    // 고객사 회사정보 저장
    public void saveSellComp(SellListDTO sellListDTO) {
        logger.info("고객사 회사정보 저장");
        sellMapper.insertSellComp(sellListDTO);
    }

    // 고객사 회사정보 조회
    public List<SellListDTO> SellComps() {
        logger.info("고객사 회사정보 조회");
        return sellMapper.getAllSellComps();
    }
    // 고객사 기계정보 조회
    public List<SellListDTO> machines() {
        logger.info("기계정보 조회");
        return sellMapper.getAllMachines();
    }

    // 기계정보 저장
    public void saveMachine(SellListDTO sellListDTO) {
        logger.info("기계정보 저장");
        sellMapper.insertAllMachine(sellListDTO);
    }

    // 담당자정보 저장
    public void savePerson(SellListDTO sellListDTO) {
        logger.info("담당자 정보 저장");
        sellMapper.insertPerson(sellListDTO);
    }


    // 판매정보 저장
    public void saveSell(SellListDTO sellListDTO) {
        logger.info("판매정보 조회");
        sellMapper.insertAllSell(sellListDTO);
    }

    // 모든 판매정보 조회
    public List<SellListDTO> sellList() {
        logger.info("모든 판매정보 조회");
        return sellMapper.getAllSellList();
    }

    // 고객사 정보 조회
    public List<SellListDTO> sellCompList() {
        logger.info("고객사 정보 조회");
        return sellMapper.getAllsellCompList();
    }

    // sellId에 따른 판매정보 조회
    public List<SellListDTO> sellListById(int SID) {
        logger.info("sellId에 따른 판매정보 조회");
        return sellMapper.getAllsellListById(SID);
    }

    // 선입고 판매정보 조회
    public List<SellListDTO> sellReceivingList() {
        logger.info("선입고 판매정보 조회");
        return sellMapper.getAllsellReceivingList();
    }

    // sellId에 따른 회사정보 조회
    public List<SellListDTO> sellCompById(int SID) {
        logger.info("sellId에 따른 회사정보 조회");
        return sellMapper.getAllsellCompById(SID);
    }

    // sellId에 따른 담당자 조회
    public List<SellListDTO> sellPersonById(int SID) {
        logger.info("sellId에 따른 담당자 조회");
        return sellMapper.getAllsellPersonById(SID);
    }

    // sellId에 따른 담당자모든 정보 조회
    public List<SellListDTO> PersonByIdList(int SID) {
        logger.info("sellId에 따른 담당자 조회");
        return sellMapper.getAllPersonByIdList(SID);
    }

    // sellId에 따른 회사정보 조회
    public List<SellListDTO> MachineBysellId(int SID) {
        logger.info("sellId에 따른 회사정보 조회");
        return sellMapper.getAllMachineBysellId(SID);
    }

    // sellId에 따른 담당자 정보 조회
    public List<SellListDTO> PersonByMachineId(int SID) {
        logger.info("sellId에 따른 담당자 조회");
        return sellMapper.getPersonByMachineId(SID);
    }

    // pdtnum에 따른 담당자 정보 조회
    public List<StockListDTO> EapriceByPDT(int SID) {
        logger.info("pdtnum에따른 개당가격 조회");
        return sellMapper.getEapriceByPDT(SID);
    }
}
