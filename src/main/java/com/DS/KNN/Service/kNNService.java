package com.DS.KNN.Service;

import com.DS.KNN.Entity.DataSetCustom;
import com.DS.KNN.Entity.UnivariateLinearRegression;
import com.DS.KNN.ML.kNN.Algorithms;
import com.DS.KNN.ML.kNN.Import;
import com.DS.KNN.ML.kNN.Neighbours;
import com.DS.KNN.ML.kNN.Prediction;
import com.DS.KNN.Repository.DataSetRepository;
import com.DS.KNN.Utilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Async
@Service
public class kNNService {

    @Autowired
    DataSetRepository dataSetRepository;

    public HashMap<DataSetCustom, Double> kNNEucladianDistanceClassification() {
        Import importData = new Import();
        Neighbours neighbours = new Neighbours();
        Algorithms algorithms = new Algorithms();
        Prediction prediction = new Prediction();
        int run = 0;

        HashMap<DataSetCustom, Double> dataSetMasterModel = new HashMap();

        while (run < 1000) {
//            DataSetCustom dataSetCustom = importData.importFlowerDS("src/main/resources/iris.data.txt");
            DataSetCustom dataSetCustom = importData.importFlowerDS("src/main/resources/insurance2.csv");
            HashMap<String, Double> stringIntegerHashMap = algorithms.calculateEucladianDistance(dataSetCustom, 5);
            int index = 1;
            Double totalAccuracy = new Double(0);
            for (String testData : dataSetCustom.getTestData()) {
                List<Object> nearestNeighbours = neighbours.getNeighbours(dataSetCustom.getTrainingData(), testData, 7);
                Map<String, Integer> votedResponse = prediction.kNNEucaladianDistancePrediction(nearestNeighbours);
                String[] split = testData.split(",\\s*");
                String testInstanceCat = split[split.length - 1];
                Double accuracy = algorithms.checkAccuracy(votedResponse, testInstanceCat);
                totalAccuracy += accuracy;
                index++;
            }
            double resultAccuracy = totalAccuracy / dataSetCustom.getTestData().size();
            if(resultAccuracy >0.50) {
                dataSetMasterModel.put(dataSetCustom,resultAccuracy);
            }
            if(run%10 == 0) {
                System.out.println(run);
            }
            run++;
        }
        System.out.println(dataSetMasterModel);
        return  dataSetMasterModel;
    }

    public HashMap<DataSetCustom, Double> kNNEucladianDistancepseudoRegression(MultipartFile dataSetFile, int range) throws  Exception{

        Import importData = new Import();
        Neighbours neighbours = new Neighbours();
        Algorithms algorithms = new Algorithms();
        Prediction prediction = new Prediction();
        int run = 0;

        HashMap<DataSetCustom, Double> dataSetMasterModel = new HashMap();
        HashMap<DataSetCustom, Double> rejectedModels = new HashMap();

        while (run < 200) {


            DataSetCustom dataSetCustom = importData.readDataSet(dataSetFile);
            HashMap<String, Double> stringIntegerHashMap = algorithms.calculateEucladianDistance(dataSetCustom, range);
            int index = 1;
            Double totalAccuracy = new Double(0);
            for (String testData : dataSetCustom.getTestData()) {
                List<Object> nearestNeighbours = neighbours.getNeighboursRange(dataSetCustom.getTrainingData(), testData, 5);
                Map<String, Integer> votedResponse = prediction.kNNEucaladianDistancePredictionRange(nearestNeighbours);
                String[] split = testData.split(",\\s*");
                String testInstanceCat = split[split.length - 1];
                Double accuracy = algorithms.checkAccuracyInRange(votedResponse, testInstanceCat);
                totalAccuracy += accuracy;
                index++;
            }
            double resultAccuracy = totalAccuracy / dataSetCustom.getTestData().size();
            if(resultAccuracy >0.15) {
                dataSetMasterModel.put(dataSetCustom,resultAccuracy);
            }
            else{
                rejectedModels.put(dataSetCustom,resultAccuracy);
            }
            if(run%100 == 0) {
                System.out.println(run);
            }
            run++;
        }
        System.out.println(dataSetMasterModel);
        return  dataSetMasterModel;
    }

    public HashMap<DataSetCustom, Double> kNNEucladianDistanceResultRangeJob() {
        Import importData = new Import();
        Neighbours neighbours = new Neighbours();
        Algorithms algorithms = new Algorithms();
        Prediction prediction = new Prediction();
        int run = 0;

        HashMap<DataSetCustom, Double> dataSetMasterModel = new HashMap();
        HashMap<DataSetCustom, Double> rejectedModels = new HashMap();

        while (run < 1000) {
//            DataSetCustom dataSetCustom = importData.importFlowerDS("src/main/resources/iris.data.txt");
            DataSetCustom dataSetCustom = importData.importFlowerDS("src/main/resources/insurance2.csv");
            HashMap<String, Double> stringIntegerHashMap = algorithms.calculateEucladianDistance(dataSetCustom, 5);
            int index = 1;
            Double totalAccuracy = new Double(0);
            for (String testData : dataSetCustom.getTestData()) {
                List<Object> nearestNeighbours = neighbours.getNeighboursRange(dataSetCustom.getTrainingData(), testData, 5);
                Map<String, Integer> votedResponse = prediction.kNNEucaladianDistancePredictionRange(nearestNeighbours);
                String[] split = testData.split(",\\s*");
                String testInstanceCat = split[split.length - 1];
                Double accuracy = algorithms.checkAccuracyInRange(votedResponse, testInstanceCat);
                totalAccuracy += accuracy;
                index++;
            }
            double resultAccuracy = totalAccuracy / dataSetCustom.getTestData().size();
            if(resultAccuracy >0.15) {
                dataSetMasterModel.put(dataSetCustom,resultAccuracy);
            }
            else{
                rejectedModels.put(dataSetCustom,resultAccuracy);
            }
            if(run%100 == 0) {
                System.out.println(run);
            }
            run++;
        }
        System.out.println(dataSetMasterModel);
        return  dataSetMasterModel;
    }

    public HashMap<DataSetCustom, Double> PredictPriceTest(DataSetCustom primeDataSetCustom) {
        Neighbours neighbours = new Neighbours();
        Algorithms algorithms = new Algorithms();
        Prediction prediction = new Prediction();

        HashMap<DataSetCustom, Double> dataSetMasterModel = new HashMap();
        HashMap<DataSetCustom, Double> rejectedModels = new HashMap();

            Double totalAccuracy = new Double(0);
                List<Object> nearestNeighbours = neighbours.getNeighboursRange(primeDataSetCustom.getTrainingData(), primeDataSetCustom.getTestData().get(4), 1);
                Map<String, Integer> votedResponse = prediction.kNNEucaladianDistancePredictionRange(nearestNeighbours);
                String[] split = primeDataSetCustom.getTestData().get(4).split(",\\s*");
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

    public String PredictPrice(String id, String criteria) {

        Neighbours neighbours = new Neighbours();
        HashMap<DataSetCustom, Double> dataSetMasterModel = new HashMap();
        Double totalOffby = new Double(0);
        List<Double>  accuracyList = new ArrayList<>();
        id = "5b6f34472b4c50db84b99dbc";
        Optional<DataSetCustom> primeDataSet = dataSetRepository.findById(id);

            List<Object> nearestNeighbours = neighbours.getNeighboursRange(primeDataSet.get().getTrainingData(), criteria, 1);
            HashMap.Entry neighbourMap = (HashMap.Entry) nearestNeighbours.get(0);

            String primeKey = neighbourMap.getKey().toString();
            String[] primeKeys = primeKey.split(",\\s*");
            String primeKeyCost = primeKeys[primeKeys.length - 1];
            System.out.println("Predicted Cost: " + primeKeyCost);

        Double tempMax = new Double(0);
        for (Double value : accuracyList) {
            if(tempMax ==0) {
                tempMax =value;
            }
            else if(tempMax < value) {
                tempMax = value;
            }
            System.out.println(value);

        }
        Double min = Collections.min(accuracyList);

        System.out.println("Total Off By: " + totalOffby.toString());
        System.out.println("Average Accuracy: " + totalOffby/primeDataSet.get().getTestData().size());
        System.out.println("Highest Discrepancy: " + tempMax.toString());
        System.out.println("Lowest Discrepancy: " + min);


        return  primeKeyCost;

    }

        public HashMap<DataSetCustom, Double> PredictPriceTrainingTest() {
        Neighbours neighbours = new Neighbours();
        HashMap<DataSetCustom, Double> dataSetMasterModel = new HashMap();
        Double totalOffby = new Double(0);
        List<Double>  accuracyList = new ArrayList<>();
        Optional<DataSetCustom> primeDataSet = dataSetRepository.findById("5b6f34472b4c50db84b99dbc");
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
            System.out.println(value);

      }
        Double min = Collections.min(accuracyList);

        System.out.println("Total Off By: " + totalOffby.toString());
        System.out.println("Average Accuracy: " + totalOffby/primeDataSet.get().getTestData().size());
        System.out.println("Highest Discrepancy: " + tempMax.toString());
        System.out.println("Lowest Discrepancy: " + min);


        return  dataSetMasterModel;
    }

    public String saveModelHighestAccuracy(HashMap<DataSetCustom, Double> dataSetDoubleHashMap) {
        dataSetDoubleHashMap = Utilities.sortByComparatorDataSet(dataSetDoubleHashMap, false);
        Map.Entry<DataSetCustom, Double> primeDataSetDoubleEntry = (HashMap.Entry<DataSetCustom, Double>) dataSetDoubleHashMap.entrySet().toArray()[0];
        DataSetCustom primeDataSetCustom = primeDataSetDoubleEntry.getKey();
        primeDataSetCustom.setAccuracy(primeDataSetDoubleEntry.getValue());
        Double value = primeDataSetDoubleEntry.getValue();
        System.out.println(value);

        DataSetCustom save = dataSetRepository.save(primeDataSetCustom);
        return primeDataSetCustom.getId();
    }

    public void UnivariateRegressionTrain(MultipartFile file) {

        HashMap<DataSetCustom, Double> primeDataSet = new HashMap<>();

        int index = 0;
        while (index < 400) {
            Double totalOffby = new Double(0);
            List<Double>  accuracyList = new ArrayList<>();

            Import importData = new Import();
//            DataSetCustom dataSetCustom = importData.importFlowerDS("C:/Github/kNN-1/KNN/src/main/resources/avocado.csv");
            DataSetCustom dataSetCustom = importData.readDataSet(file);
            Algorithms algorithms = new Algorithms();
            UnivariateLinearRegression univariateLinearRegressionModel = algorithms.univariateLinearRegressionGenerateModel(dataSetCustom);

            for (String testData : dataSetCustom.getTestData()) {
                List<String> dataLine = Arrays.asList(testData.split(",\\s*"));
                Double averagePrice = Double.parseDouble(dataLine.get(2));
                Double totalVolume = Double.parseDouble(dataLine.get(3));
                univariateLinearRegressionModel.setValue(totalVolume);
                Double predictedValue = algorithms.univariateLinearRegressionPredict(univariateLinearRegressionModel);
                Double priceDiff = Math.abs(predictedValue-averagePrice);
//                System.out.println("Actual Cost: " + averagePrice);
//                System.out.println("Predicted Cost: " + predictedValue);
//                System.out.println("Off By : " + priceDiff);
//                System.out.println("Off By % : " + priceDiff/averagePrice);
                totalOffby = totalOffby + priceDiff/averagePrice;
                accuracyList.add(1-priceDiff/averagePrice);

            }

            Double tempMax = new Double(0);

            for (Double value : accuracyList) {
                if(tempMax ==0) {
                    tempMax =value;
                }
                else if(tempMax < value) {
                    tempMax = value;
                }
//                System.out.println(value);

            }
            Double min = Collections.min(accuracyList);

//            System.out.println("Total Off By: " + totalOffby);
            double avgAccuracy = 1 - totalOffby / dataSetCustom.getTestData().size();
            System.out.println("Average Accuracy: " + avgAccuracy);
//            System.out.println("Highest Discrepancy: " + tempMax.toString());
//            System.out.println("Lowest Discrepancy: " + min);

            if( avgAccuracy > 0.75 ) {
                primeDataSet.put(dataSetCustom, totalOffby/ dataSetCustom.getTestData().size());
            }

            if(index%200 == 0) {
                System.out.println(index);
            }

        index++;
        }

        System.out.println(primeDataSet);
        System.out.println(primeDataSet.size());


    }

}
