package com.DS.KNN.Entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class UnivariateLinearRegression {
    @Id
    private String id;
    private Double slope;
    private Double yIntercept;
    private Double value;
    private Double maxY;
    private Double minY;
    private Double maxX;
    private Double minX;

    public UnivariateLinearRegression() {
    }

    public UnivariateLinearRegression(Double slope, Double yIntercept) {
        this.slope = slope;
        this.yIntercept = yIntercept;
    }

    public UnivariateLinearRegression(Double slope, Double yIntercept, Double maxY, Double minY,Double maxX, Double minX) {
        this.slope = slope;
        this.yIntercept = yIntercept;
        this.maxY = maxY;
        this.minY = minY;
        this.maxX = maxX;
        this.minX = minX;
    }

    public Double getMaxX() {
        return maxX;
    }

    public void setMaxX(Double maxX) {
        this.maxX = maxX;
    }

    public Double getMinX() {
        return minX;
    }

    public void setMinX(Double minX) {
        this.minX = minX;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Double getMaxY() {
        return maxY;
    }

    public void setMaxY(Double maxY) {
        this.maxY = maxY;
    }

    public Double getMinY() {
        return minY;
    }

    public void setMinY(Double minY) {
        this.minY = minY;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Double getSlope() {
        return slope;
    }

    public void setSlope(Double slope) {
        this.slope = slope;
    }

    public Double getyIntercept() {
        return yIntercept;
    }

    public void setyIntercept(Double yIntercept) {
        this.yIntercept = yIntercept;
    }
}
