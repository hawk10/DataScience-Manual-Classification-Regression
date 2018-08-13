package com.DS.KNN.Entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigInteger;
import java.util.List;

@Document
public class DataSetCustom {

    @Id
    private String id;
    private List<String> trainingData;
    private List<String> TestData;
    private Double accuracy;

    public DataSetCustom() {
    }

    public DataSetCustom(List<String> trainingData, List<String> testData) {
        this.trainingData = trainingData;
        TestData = testData;
    }

    public DataSetCustom(List<String> trainingData, List<String> testData, Double accuracy) {
        this.trainingData = trainingData;
        TestData = testData;
        this.accuracy = accuracy;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Double getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(Double accuracy) {
        this.accuracy = accuracy;
    }

    public List<String> getTrainingData() {
        return trainingData;
    }

    public void setTrainingData(List<String> trainingData) {
        this.trainingData = trainingData;
    }

    public List<String> getTestData() {
        return TestData;
    }

    public void setTestData(List<String> testData) {
        TestData = testData;
    }


}
