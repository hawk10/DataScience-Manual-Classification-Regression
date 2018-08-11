package com.DS.KNN.Service;

import com.DS.KNN.ML.kNN.Algorithms;
import com.DS.KNN.Entity.DataSet;
import com.DS.KNN.ML.kNN.Import;
import com.DS.KNN.ML.kNN.Neighbours;
import com.DS.KNN.ML.kNN.Prediction;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Async
@Service
public class kNNService {

    public HashMap<DataSet, Double> kNNEucladianDistance() {
        Import importData = new Import();
        Neighbours neighbours = new Neighbours();
        Algorithms algorithms = new Algorithms();
        Prediction prediction = new Prediction();
        int run = 0;

        HashMap<DataSet, Double> dataSetMasterModel = new HashMap();

        while (run < 10000) {
            DataSet dataSet = importData.importFlowerDS("src/main/resources/iris.data.txt");
            HashMap<String, Double> stringIntegerHashMap = algorithms.calculateEucladianDistance(dataSet, 3);
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
            if(resultAccuracy >0.99) {
                dataSetMasterModel.put(dataSet,resultAccuracy);
            }
            if(run%1000 == 0) {
                System.out.println(run);
            }
            run++;
        }
        System.out.println(dataSetMasterModel);
        return  dataSetMasterModel;
    }

}
