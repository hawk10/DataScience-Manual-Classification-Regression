package com.DS.KNN.Entity;

import java.util.List;

public class DataSet {

    private List<String> trainingData;
    private List<String> TestData;

    public DataSet() {
    }

    public DataSet(List<String> trainingData, List<String> testData) {
        this.trainingData = trainingData;
        TestData = testData;
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
