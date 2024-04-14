package com.ras.bandostockproject.service;

import com.ras.bandostockproject.dto.PurchaseListDTO;
import com.ras.bandostockproject.dto.SellListDTO;
import com.ras.bandostockproject.dto.StockListDTO;
import com.ras.bandostockproject.dto.StockPriceDTO;
import com.ras.bandostockproject.mapper.SellMapper;
import com.ras.bandostockproject.mapper.StockMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockService {

    private static final Logger logger = LoggerFactory.getLogger(StockService.class);

    private final SellMapper sellMapper;
    private final StockMapper stockMapper;

    public StockService(SellMapper sellMapper, StockMapper stockMapper) {
        this.sellMapper = sellMapper;
        this.stockMapper = stockMapper;
    }

    // 재고 저장
    public void saveStock(StockListDTO stockListDTO) {
        logger.info("재고 저장");
        stockMapper.insertStock(stockListDTO);
    }

    //제품명 조회
    public List<StockListDTO> StockProduct() {
        logger.info("재고 제품 조회");
        return stockMapper.getAllStockProduct();
    }

    // 재고 개수 리프레시
    public void StockQuantityRefresh(int SellQuantity, int PurchasePDTNUM) {
        logger.info("재고 저장");
        stockMapper.updateStockQuantityRefresh(SellQuantity, PurchasePDTNUM);
    }

    // 재고 개수 0인값 삭제
    public void deleteZeroQuantityStock() {
        logger.info("재고 개수 0인값 삭제");
        stockMapper.deleteZeroQuantityStock();
    }

    // 총 재고금액, 총 구매금액 업데이트
    public void updateStockPriceAllPrice(int purchasePrice) {
        logger.info("총 재고금액, 총 구매금액 업데이트");
        stockMapper.updateStockPriceAllPrice(purchasePrice);
    }
    // 총 판매금액 업데이트
    public void updateAllSellCNT(int sellPrice) {
        logger.info("총 판매금액 업데이트");
        stockMapper.updateAllSellCNT(sellPrice);
    }

    // 재고테이블 개당구매가격 조회
    public int getPurchaseEapriceByStock(int purchasePDTNUM) {
        logger.info("재고테이블 개당구매가격 조회");
        return stockMapper.getPurchaseEapriceByStock(purchasePDTNUM);
    }
    // 순수익 업데이트
    public void updateRevenue(int REVENUE) {
        logger.info("순수익 업데이트");
        stockMapper.updateRevenue(REVENUE);
    }
    // 누적세금 업데이트
    public void updateTax(int tax) {
        logger.info("누적세금 업데이트");
        stockMapper.updateTax(tax);
    }
    // 재고정보 조회
    public List<StockListDTO> stockList() {
        logger.info("재고정보 조회");
        return stockMapper.stockList();
    }
    // 재고금액 조회
    public List<StockPriceDTO> stockPrice() {
        logger.info("재고금액 조회");
        return stockMapper.stockPrice();
    }

    // 총 재고금액 업데이트
    public void updateAllStockPrice(int ResultPrice) {
        logger.info("총 재고금액 업데이트");
        stockMapper.updateAllStockPrice(ResultPrice);
    }

    // 선입고금액 업데이트
    public void updateReceivingPriceCNT(int preReceivingPrice) {
        logger.info("선입고금액 업데이트");
        stockMapper.updateReceivingPriceCNT(preReceivingPrice);
    }

    // 외상금액 업데이트
    public void updateStateCreditCNT(int creditPrice) {
        logger.info("외상금액 업데이트");
        stockMapper.updateStateCreditCNT(creditPrice);
    }
}
