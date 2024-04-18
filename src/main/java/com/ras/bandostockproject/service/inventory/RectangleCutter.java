package com.ras.bandostockproject.service.inventory;

import com.ras.bandostockproject.dto.inventory.Point;
import com.ras.bandostockproject.dto.inventory.Polygon;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class RectangleCutter {
    private boolean[][] board;
    private int[][] prefixSum;
    private int discardedArea = 0;  // 폐기된 총 영역을 저장하는 변수

    private List<BoundaryPoint> vertices;

    static class BoundaryPoint {
        int x, y;
        BoundaryPoint(int x, int y) {
            this.x = x;
            this.y = y;
        }
        public String toString() {
            return x + " " + y;
        }
    }


    public RectangleCutter(int height, int width) {
        this.board = new boolean[height][width]; // false로 초기화 (사용 가능한 상태)
        this.prefixSum = new int[height + 1][width + 1];
        this.vertices = new ArrayList<>();
        computePrefixSumInitial(); // Initialize prefix sum for the initial state
    }

    public String cutLossArea(int startX1, int startY1, int startX3, int startY3) {
        int startX2 = startX3;
        int startY2 = startY1;
        int startX4 = startX1;
        int startY4 = startY3;

        int countDiscarded = 0; // 이번 호출에서 새로 폐기되는 영역 카운트

        // 사각형 영역 설정 및 사용 불가능한 영역 카운트
        for (int i = Math.min(startY1, startY3); i <= Math.max(startY1, startY3); i++) {
            for (int j = Math.min(startX1, startX3); j <= Math.max(startX1, startX3); j++) {
                if (!board[i][j]) {  // 셀이 처음 사용될 경우
                    board[i][j] = true;  // 셀을 사용 불가능하게 설정
                    countDiscarded++;    // 폐기되는 영역 카운트 증가
                }
            }
        }

        // 전체 폐기 영역 업데이트
        discardedArea += countDiscarded;

        // 꼭지점들을 문자열로 포맷하여 반환
        return String.format("(%d %d, %d %d, %d %d, %d %d, %d %d)",
                startX1, startY1, startX2, startY2, startX3, startY3, startX4, startY4, startX1, startY1);
    }


    public int getDiscardedArea() {
        return discardedArea;  // 폐기된 영역의 총 넓이 반환
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
        for (int j = board[0].length - rectWidth; j >= 0; j--) {
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


    // 사용가능 재고 한붓그리기 메서드 시작

    public String findBoundaryZeros() {
        int m = board.length;    // Height of the grid (y+1)
        int n = board[0].length; // Width of the grid (x+1)
        if (m == 0 || n == 0) return "";

        // Start at the bottom-left corner of the grid
        int x = m - 1, y = 0;
        int dir = 0; // 0 = right, 1 = up, 2 = left, 3 = down
        int[][] directions = {{0, 1}, {-1, 0}, {0, -1}, {1, 0}};

        // Ensure starting at a zero, if possible
        while (y < n && board[x][y]) {
            y++;
        }
        if (y == n) { // If no zero is found on the bottom row
            y = 0;
            while (x >= 0 && board[x][y]) {
                x--;
            }
            if (x < 0) return ""; // No zeros found at all
        }

        // Add the start point
        vertices.add(new BoundaryPoint(y, x));

        int startX = x, startY = y;
        do {
            int nextX = x + directions[dir][0];
            int nextY = y + directions[dir][1];

            if (nextX >= 0 && nextX < m && nextY >= 0 && nextY < n && !board[nextX][nextY]) {
                // Move in the current direction
                x = nextX;
                y = nextY;
            } else {
                // Change direction clockwise
                dir = (dir + 1) % 4;
                vertices.add(new BoundaryPoint(y, x)); // Add vertex when direction changes
                continue;
            }

            // Check if we have returned to the start
            if (x == startX && y == startY) {
                break;
            }
        } while (true);

        // Build the result string
        StringBuilder result = new StringBuilder();
        for (BoundaryPoint vertex : vertices) {
            result.append(vertex).append(",");
        }
        result.append(vertices.get(0)); // Append the first point at the end to complete the cycle
        return result.toString();
    }
}
