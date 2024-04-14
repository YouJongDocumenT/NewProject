package com.ras.bandostockproject.service.inventory;

public class RectangleCutter {
    private boolean[][] board;
    private int[][] prefixSum;

    public RectangleCutter(int height, int width) {
        this.board = new boolean[height][width]; // false로 초기화 (사용 가능한 상태)
        this.prefixSum = new int[height + 1][width + 1];
    }

    private void computePrefixSum() {
        for (int i = 1; i <= board.length; i++) {
            for (int j = 1; j <= board[0].length; j++) {
                prefixSum[i][j] = (board[i - 1][j - 1] ? 0 : 1) // if board[i-1][j-1] is true, then 0, else 1
                        + prefixSum[i - 1][j] + prefixSum[i][j - 1] - prefixSum[i - 1][j - 1];
            }
        }
    }

    public String cutRectangle(int rectHeight, int rectWidth) {
        computePrefixSum();
        for (int i = 0; i <= board.length - rectHeight; i++) {
            for (int j = 0; j <= board[0].length - rectWidth; j++) {
                if (isSpaceAvailable(i, j, rectHeight, rectWidth)) {
                    fillSpace(i, j, rectHeight, rectWidth);
                    String points = formatPoints(i, j, rectHeight, rectWidth);
                    System.out.println("Rectangle (" + rectHeight + "x" + rectWidth + ") cut successfully at " + points);
                    return points;
                }
            }
        }
        System.out.println("Failed to cut rectangle (" + rectHeight + "x" + rectWidth + "): Not enough space.");
        return null;
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
                board[i][j] = true; // 해당 공간을 사용 중으로 표시
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
