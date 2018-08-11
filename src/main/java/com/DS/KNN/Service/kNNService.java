package com.DS.KNN.Service;

import com.DS.KNN.ML.kNN.Algorithms;
import com.DS.KNN.Entity.DataSet;
import com.DS.KNN.ML.kNN.Import;
import com.DS.KNN.ML.kNN.Neighbours;
import com.DS.KNN.ML.kNN.Prediction;
import com.DS.KNN.Repository.DataSetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.*;

@Async
@Service
public class kNNService {

    @Autowired
    DataSetRepository dataSetRepository;

    public HashMap<DataSet, Double> kNNEucladianDistance() {
        Import importData = new Import();
        Neighbours neighbours = new Neighbours();
        Algorithms algorithms = new Algorithms();
        Prediction prediction = new Prediction();
        int run = 0;

        HashMap<DataSet, Double> dataSetMasterModel = new HashMap();

        while (run < 1000) {
//            DataSet dataSet = importData.importFlowerDS("src/main/resources/iris.data.txt");
            DataSet dataSet = importData.importFlowerDS("src/main/resources/insurance2.csv");
            HashMap<String, Double> stringIntegerHashMap = algorithms.calculateEucladianDistance(dataSet, 5);
            int index = 1;
            Double totalAccuracy = new Double(0);
            for (String testData : dataSet.getTestData()) {
                List<Object> nearestNeighbours = neighbours.getNeighbours(dataSet.getTrainingData(), testData, 7);
                Map<String, Integer> votedResponse = prediction.kNNEucaladianDistancePrediction(nearestNeighbours);
                String[] split = testData.split(",\\s*");
                String testInstanceCat = split[split.length - 1];
                Double accuracy = algorithms.checkAccuracy(votedResponse, testInstanceCat);
                totalAccuracy += accuracy;
                index++;
            }
            double resultAccuracy = totalAccuracy / dataSet.getTestData().size();
            if(resultAccuracy >0.50) {
                dataSetMasterModel.put(dataSet,resultAccuracy);
            }
            if(run%10 == 0) {
                System.out.println(run);
            }
            run++;
        }
        System.out.println(dataSetMasterModel);
        return  dataSetMasterModel;
    }

    public HashMap<DataSet, Double> kNNEucladianDistanceResultRange() {
        Import importData = new Import();
        Neighbours neighbours = new Neighbours();
        Algorithms algorithms = new Algorithms();
        Prediction prediction = new Prediction();
        int run = 0;

        HashMap<DataSet, Double> dataSetMasterModel = new HashMap();
        HashMap<DataSet, Double> rejectedModels = new HashMap();

        while (run < 1000) {
//            DataSet dataSet = importData.importFlowerDS("src/main/resources/iris.data.txt");
            DataSet dataSet = importData.importFlowerDS("src/main/resources/insurance2.csv");
            HashMap<String, Double> stringIntegerHashMap = algorithms.calculateEucladianDistance(dataSet, 5);
            int index = 1;
            Double totalAccuracy = new Double(0);
            for (String testData : dataSet.getTestData()) {
                List<Object> nearestNeighbours = neighbours.getNeighboursRange(dataSet.getTrainingData(), testData, 5);
                Map<String, Integer> votedResponse = prediction.kNNEucaladianDistancePredictionRange(nearestNeighbours);
                String[] split = testData.split(",\\s*");
                String testInstanceCat = split[split.length - 1];
                Double accuracy = algorithms.checkAccuracyInRange(votedResponse, testInstanceCat);
                totalAccuracy += accuracy;
                index++;
            }
            double resultAccuracy = totalAccuracy / dataSet.getTestData().size();
            if(resultAccuracy >0.15) {
                dataSetMasterModel.put(dataSet,resultAccuracy);
            }
            else{
                rejectedModels.put(dataSet,resultAccuracy);
            }
            if(run%100 == 0) {
                System.out.println(run);
            }
            run++;
        }
        System.out.println(dataSetMasterModel);
        return  dataSetMasterModel;
    }

    public HashMap<DataSet, Double> PredictPriceTest(DataSet primeDataSet) {
        Neighbours neighbours = new Neighbours();
        Algorithms algorithms = new Algorithms();
        Prediction prediction = new Prediction();

        HashMap<DataSet, Double> dataSetMasterModel = new HashMap();
        HashMap<DataSet, Double> rejectedModels = new HashMap();

            Double totalAccuracy = new Double(0);
                List<Object> nearestNeighbours = neighbours.getNeighboursRange(primeDataSet.getTrainingData(), primeDataSet.getTestData().get(4), 1);
                Map<String, Integer> votedResponse = prediction.kNNEucaladianDistancePredictionRange(nearestNeighbours);
                String[] split = primeDataSet.getTestData().get(4).split(",\\s*");
                String testInstanceCat = split[split.length - 1];
                Double accuracy = algorithms.checkAccuracyInRange(votedResponse, testInstanceCat);
                Double cost = Double.parseDouble(split[split.length - 1]);
                HashMap.Entry neighbourMap = (HashMap.Entry) nearestNeighbours.get(0);

                String primeKey = neighbourMap.getKey().toString();
                String[] primeKeys = primeKey.split(",\\s*");
                String primeKeyCost = primeKeys[primeKeys.length - 1];
                double costDiff = Math.abs(Double.parseDouble(primeKeyCost.toString()) - cost);
                System.out.println("Difference between Real Cost and Predicted cost is : " +costDiff );
                System.out.println("Off by " +costDiff/cost );
                System.out.println("Predicted Cost: " + primeKeyCost);
                System.out.println("Actual Cost: "+cost);

        return  dataSetMasterModel;
    }

    public HashMap<DataSet, Double> PredictPrice() {
        Neighbours neighbours = new Neighbours();
        HashMap<DataSet, Double> dataSetMasterModel = new HashMap();
        Double totalOffby = new Double(0);
        List<Double>  accuracyList = new ArrayList<>();
        Optional<DataSet> primeDataSet = dataSetRepository.findById("5b6f34472b4c50db84b99dbc");
        for (String testData : primeDataSet.get().getTestData()) {
            List<Object> nearestNeighbours = neighbours.getNeighboursRange(primeDataSet.get().getTrainingData(), testData, 1);
            String[] split = testData.split(",\\s*");
            Double cost = Double.parseDouble(split[split.length - 1]);
            HashMap.Entry neighbourMap = (HashMap.Entry) nearestNeighbours.get(0);

            String primeKey = neighbourMap.getKey().toString();
            String[] primeKeys = primeKey.split(",\\s*");
            String primeKeyCost = primeKeys[primeKeys.length - 1];
            double costDiff = Math.abs(Double.parseDouble(primeKeyCost.toString()) - cost);
            totalOffby = totalOffby + costDiff / cost;
            accuracyList.add(costDiff / cost);
            System.out.println("Difference between Real Cost and Predicted cost is : " + costDiff);
            System.out.println("Off by " + costDiff / cost);
            System.out.println("Predicted Cost: " + primeKeyCost);
            System.out.println("Actual Cost: " + cost);
        }
        Double tempMax = new Double(0);
      for (Double value : accuracyList) {
            if(tempMax ==0) {
                tempMax =value;
            }
            else if(tempMax < value) {
                tempMax = value;
            }

      }
        Double min = Collections.min(accuracyList);

        System.out.println("Total Off By: " + totalOffby.toString());
        System.out.println("Average Accuracy: " + totalOffby/primeDataSet.get().getTestData().size());
        System.out.println("Highest Discrepancy: " + tempMax.toString());
        System.out.println("Lowest Discrepancy: " + min);


        return  dataSetMasterModel;
    }
}
