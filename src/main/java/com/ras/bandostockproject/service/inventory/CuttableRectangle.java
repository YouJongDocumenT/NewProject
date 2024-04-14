package com.ras.bandostockproject.service.inventory;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

public class CuttableRectangle {
    private boolean[][] cutStatus;
    private int width;
    private int height;
    private TreeSet<int[]> corners;

    public CuttableRectangle(int width, int height) {
        this.width = width;
        this.height = height;
        this.cutStatus = new boolean[height][width];
        this.corners = new TreeSet<>((a, b) -> {
            if (a[0] != b[0]) {
                return a[0] - b[0];
            } else {
                return a[1] - b[1];
            }
        });
    }

    public void cut(int startX, int startY, int endX, int endY) {
        for (int y = startY; y <= endY && y < height; y++) {
            for (int x = startX; x <= endX && x < width; x++) {
                cutStatus[y][x] = true;
            }
        }

        // 새로운 꼭지점 정보 추가
        addCorner(startX, startY);
        addCorner(endX, startY);
        addCorner(startX, endY);
        addCorner(endX, endY);

        checkAndRemoveCorners();

    }

    private void addCorner(int x, int y) {
        int[] newCorner = {x, y};
        corners.add(newCorner);
    }

    private void checkAndRemoveCorners() {
        TreeSet<int[]> cornersToRemove = new TreeSet<>((a, b) -> {
            if (a[0] != b[0]) {
                return a[0] - b[0];
            } else {
                return a[1] - b[1];
            }
        });
        for (int[] corner : corners) {
            int x = corner[0];
            int y = corner[1];
            if (x > 0 && y > 0 && x < width - 1 && y < height - 1) {
                if (cutStatus[y - 1][x] && cutStatus[y + 1][x] && cutStatus[y][x - 1] && cutStatus[y][x + 1]) {
                    cornersToRemove.add(corner);
                }
            }
        }
        corners.removeAll(cornersToRemove);
    }

    public void printCorners() {
        System.out.println("Corners:");
        for (int[] corner : corners) {
            System.out.println("(" + corner[0] + ", " + corner[1] + ")");
        }
    }

    public void printCutStatus() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                System.out.print(cutStatus[y][x] ? "X" : "-");
            }
            System.out.println();
        }
    }

    // 가능한 모든 사각형을 찾아내는 메서드
    public List<int[][]> findAllRectangles() {
        List<int[][]> rectangles = new ArrayList<>();
        for (int[] corner1 : corners) {
            for (int[] corner2 : corners) {
                // corner1과 corner2가 대각선상에 위치하는지 확인
                if (corner1[0] != corner2[0] && corner1[1] != corner2[1]) {
                    int[] topLeft = {Math.min(corner1[0], corner2[0]), Math.min(corner1[1], corner2[1])};
                    int[] bottomRight = {Math.max(corner1[0], corner2[0]), Math.max(corner1[1], corner2[1])};
                    int[] topRight = {bottomRight[0], topLeft[1]};
                    int[] bottomLeft = {topLeft[0], bottomRight[1]};

                    // 나머지 두 꼭지점이 corners 세트에 존재하는지 확인
                    if (corners.contains(new int[]{topRight[0], topRight[1]}) && corners.contains(new int[]{bottomLeft[0], bottomLeft[1]})) {
                        rectangles.add(new int[][]{topLeft, topRight, bottomLeft, bottomRight});
                    }
                }
            }
        }
        return rectangles;
    }

    // 각 사각형에 대해 내부 영역을 true로 설정하는 메서드
    public void fillIdentifiedRectangles() {
        List<int[][]> rectangles = findAllRectangles(); // 가능한 모든 사각형을 식별
        for (int[][] rectangle : rectangles) {
            // 각 사각형에 대해 내부 영역을 true로 설정
            for (int y = rectangle[0][1]; y <= rectangle[2][1]; y++) {
                for (int x = rectangle[0][0]; x <= rectangle[1][0]; x++) {
                    cutStatus[y][x] = true;
                }
            }
        }
    }

    public static void main(String[] args) {
        CuttableRectangle rectangle = new CuttableRectangle(100, 40);

        rectangle.cut(2, 1, 20, 20); // 임의로 영역 자르기
        rectangle.cut(3, 24, 40, 30); // 추가 영역 자르기

        rectangle.printCorners(); // 꼭지점 출력

        System.out.println("Original Cut Status:");
        rectangle.printCutStatus();

        rectangle.cut(0, 0, 10, 10); // 임의로 영역 자르기

        rectangle.printCorners(); // 꼭지점 출력

        System.out.println("Original Cut Status:");
        rectangle.printCutStatus();

        rectangle.fillIdentifiedRectangles();   // 4개의 꼭지점을 이어 최소사용 조건 체크

        System.out.println("Original Cut Status:");
        rectangle.printCutStatus();
        rectangle.printCorners(); // 꼭지점 출력
    }
}