package com.DS.KNN;

import com.DS.KNN.Entity.DataSet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.*;

@SpringBootApplication
public class KnnApplication {

	public static void main(String[] args) {

		Import importData = new Import();
		Neighbours neighbours = new Neighbours();
		Algorithms algorithms = new Algorithms();
		Prediction prediction = new Prediction();
		String testInstance = "5.1,3.5,1.4,0.2,Iris-setosa";
		int run = 0;
		HashMap<DataSet, Double> dataSetMasterModel = new HashMap();
		while (run < 10000) {
			DataSet dataSet = importData.importFlowerDS("src/main/resources/iris.data.txt");
			HashMap<String, Double> stringIntegerHashMap = algorithms.calculateEucladianDistance(dataSet, 3);
			int index = 1;
			Double totalAccuracy = new Double(0);
			for (String testData : dataSet.getTestData()) {
				List<Object> nearestNeighbours = neighbours.getNeighbours(dataSet.getTrainingData(), testData, 3);
				Map<String, Integer> votedResponse = prediction.kNNEucaladianDistancePrediction(nearestNeighbours);
				String[] split = testData.split(",\\s*");
				String testInstanceCat = split[split.length - 1];
				Double accuracy = algorithms.checkAccuracy(votedResponse, testInstanceCat);
				totalAccuracy += accuracy;
//				System.out.println(index + ": " + accuracy);
				index++;
			}
			double resultAccuracy = totalAccuracy / dataSet.getTestData().size();
//			System.out.println("Accuracy: " + resultAccuracy);
			if(resultAccuracy >0.98) {
				dataSetMasterModel.put(dataSet,resultAccuracy);
			}
			System.out.println(run);
			run++;
		}
		System.out.println(dataSetMasterModel);
		for(Map.Entry entry : dataSetMasterModel.entrySet()) {
			System.out.println(entry.getKey().toString());

		}


//		SpringApplication.run(KnnApplication.class, args);
	}
}
