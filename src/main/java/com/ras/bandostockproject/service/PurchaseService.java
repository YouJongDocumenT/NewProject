package com.ras.bandostockproject.service;

import com.ras.bandostockproject.dto.PcompDTO;
import com.ras.bandostockproject.dto.PurchaseDTO;
import com.ras.bandostockproject.dto.PurchaseListDTO;
import com.ras.bandostockproject.dto.StockListDTO;
import com.ras.bandostockproject.mapper.PurchaseMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PurchaseService {

    private static final Logger logger = LoggerFactory.getLogger(PurchaseService.class);

    private final PurchaseMapper purchaseMapper;

    public PurchaseService(PurchaseMapper purchaseMapper) {
        this.purchaseMapper = purchaseMapper;
    }

    // 매입처 회사정보 저장
    public void saveComp(PcompDTO pcompDTO) {
        logger.info("회사정보 저장");
        purchaseMapper.insertPurchaseComp(pcompDTO);
    }
    // 매입처 회사정보 조회
    public List<PcompDTO> PurchaseComps() {
        logger.info("회사정보 조회");
        return purchaseMapper.getAllPurchaseComps();
    }

    // 매입정보 저장
    public void savePurchase(PurchaseDTO purchaseDTO) {
        logger.info("매입정보 저장");
        purchaseMapper.insertPurchase(purchaseDTO);
    }

    // 매입 정보 조회
    public List<PurchaseListDTO> purchaseList() {
        logger.info("매입정보 조회");
        return purchaseMapper.getAllPurchaseList();
    }

    // 최근에 저장한 매입정보 조회
    public List<StockListDTO> purchaseRecentData() {
        logger.info("최근 매입 정보 조회");
        return purchaseMapper.getAllPurchaseRecentData();
    }

    // 매입 정보 조회
    public List<PcompDTO> purchaseCompList() {
        logger.info("매입처정보 조회");
        return purchaseMapper.getAllpurchaseCompList();
    }
    // purchaseID에 따른 매입 정보 조회
    public List<PurchaseListDTO> purchaseListById(int PID) {
        logger.info("purchaseID에 따른 매입 정보 조회");
        return purchaseMapper.getAllpurchaseListById(PID);
    }

    // 외상 매입 정보 조회
    public List<PurchaseListDTO> purchaseCreditList() {
        logger.info("외상 매입정보 조회");
        return purchaseMapper.getAllpurchaseCreditList();
    }

    // id값에 따른 매입 정보 조회
    public List<PcompDTO> purchaseCompById(int PID) {
        logger.info("매입처ID 정보 조회");
        return purchaseMapper.getAllpurchaseCompById(PID);
    }
}
