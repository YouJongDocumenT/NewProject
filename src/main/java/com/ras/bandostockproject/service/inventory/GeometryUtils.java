package com.ras.bandostockproject.service.inventory;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ras.bandostockproject.dto.inventory.GeoJSON;
import com.ras.bandostockproject.dto.inventory.Polygon;
import com.ras.bandostockproject.dto.inventory.SellingGeoJSON;

import java.util.ArrayList;
import java.util.List;

public class GeometryUtils {

    public static List<Polygon> parseCoordinates(List<SellingGeoJSON> geometries) {
        List<Polygon> polygons = new ArrayList<>(); // This will store the polygons with their points

        ObjectMapper mapper = new ObjectMapper();

        for (SellingGeoJSON geo : geometries) {
            Polygon polygon = new Polygon();
            try {
                JsonNode root = mapper.readTree(geo.getRectangle());
                JsonNode firstLinearRing = root.get("coordinates").get(0);

                for (JsonNode coord : firstLinearRing) {
                    double x = coord.get(0).asDouble();
                    double y = coord.get(1).asDouble();
                    polygon.addPoint(x, y);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            polygons.add(polygon); // Add the complete polygon to the list
        }
        return polygons;
    }

    public static int[] parseRectangleDimensions(GeoJSON geometry) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode root = mapper.readTree(geometry.getRectangle());
            JsonNode coordinates = root.get("coordinates").get(0);

            double minX = Double.MAX_VALUE;
            double maxX = Double.MIN_VALUE;
            double minY = Double.MAX_VALUE;
            double maxY = Double.MIN_VALUE;

            for (JsonNode coord : coordinates) {
                double x = coord.get(0).asDouble();
                double y = coord.get(1).asDouble();
                if (x < minX) minX = x;
                if (x > maxX) maxX = x;
                if (y < minY) minY = y;
                if (y > maxY) maxY = y;
            }

            int width = (int) (maxX - minX);
            int height = (int) (maxY - minY);

            return new int[]{width, height};
        } catch (Exception e) {
            e.printStackTrace();
            return new int[]{0, 0};  // Return default value in case of an error
        }
    }
}