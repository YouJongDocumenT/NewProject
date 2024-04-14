package com.ras.bandostockproject.service.inventory;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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
}