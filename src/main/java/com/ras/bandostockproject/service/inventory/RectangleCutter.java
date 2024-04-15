package com.ras.bandostockproject.service.inventory;

import com.ras.bandostockproject.dto.inventory.Point;
import com.ras.bandostockproject.dto.inventory.Polygon;

import java.util.List;

public class RectangleCutter {
    private boolean[][] board;
    private int[][] prefixSum;

    public RectangleCutter(int height, int width) {
        this.board = new boolean[height][width]; // false로 초기화 (사용 가능한 상태)
        this.prefixSum = new int[height + 1][width + 1];
        computePrefixSumInitial(); // Initialize prefix sum for the initial state
    }

    private void computePrefixSumInitial() {
        for (int i = 1; i <= board.length; i++) {
            for (int j = 1; j <= board[0].length; j++) {
                prefixSum[i][j] = prefixSum[i-1][j] + prefixSum[i][j-1] - prefixSum[i-1][j-1] + (board[i-1][j-1] ? 0 : 1);
            }
        }
    }

    public void computePrefixSum() {
        for (int i = 1; i <= board.length; i++) {
            for (int j = 1; j <= board[0].length; j++) {
                prefixSum[i][j] = (board[i-1][j-1] ? 0 : 1)
                        + prefixSum[i-1][j] + prefixSum[i][j-1] - prefixSum[i-1][j-1];
            }
        }
    }

    public String cutOptimalRectangle(int rectHeight, int rectWidth) {
        for (int j = 0; j <= board[0].length - rectWidth; j++) {
            for (int i = 0; i <= board.length - rectHeight; i++) {
                if (isSpaceAvailable(i, j, rectHeight, rectWidth)) {
                    fillSpace(i, j, rectHeight, rectWidth);
                    return formatPoints(i, j, rectHeight, rectWidth);
                }
            }
        }
        return null;
    }

    public void cutRectangle(Polygon polygon) {
        List<Point> points = polygon.getPoints();
        Point bottomLeft = points.get(0);
        Point topRight = points.get(2);

        int startX = (int) bottomLeft.getX();
        int startY = (int) bottomLeft.getY();
        int endX = (int) topRight.getX();
        int endY = (int) topRight.getY();

        for (int j = startX; j < endX; j++) {
            for (int i = startY; i < endY; i++) {
                if (i >= 0 && i < board.length && j >= 0 && j < board[0].length) {
                    board[i][j] = true;
                }
            }
        }
        computePrefixSum();
    }

    private boolean isSpaceAvailable(int startY, int startX, int rectHeight, int rectWidth) {
        int sum = prefixSum[startY + rectHeight][startX + rectWidth]
                - prefixSum[startY][startX + rectWidth]
                - prefixSum[startY + rectHeight][startX]
                + prefixSum[startY][startX];
        return sum == rectHeight * rectWidth;
    }

    private void fillSpace(int startY, int startX, int rectHeight, int rectWidth) {
        for (int i = startY; i < startY + rectHeight; i++) {
            for (int j = startX; j < startX + rectWidth; j++) {
                board[i][j] = true;
            }
        }
        computePrefixSum(); // Recompute prefix sums after updating the board
    }

    private String formatPoints(int startY, int startX, int rectHeight, int rectWidth) {
        int x1 = startX;
        int y1 = startY;
        int x2 = startX + rectWidth;
        int y2 = startY;
        int x3 = startX + rectWidth;
        int y3 = startY + rectHeight;
        int x4 = startX;
        int y4 = startY + rectHeight;
        return "(" + x1 + " " + y1 + ", " + x2 + " " + y2 + ", " + x3 + " " + y3 + ", " + x4 + " " + y4 + ", " + x1 + " " + y1 + ")";
    }

    public void printBoard() {
        for (boolean[] row : board) {
            for (boolean cell : row) {
                System.out.print(cell ? "1 " : "0 ");
            }
            System.out.println();
        }
    }
}
