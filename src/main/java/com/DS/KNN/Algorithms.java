package com.DS.KNN;

import com.DS.KNN.Entity.DataSet;

import java.util.*;
import java.util.stream.Collectors;

public class Algorithms {

    public Double getEucladianDistance(String trainingSet, String testInstance, int range) {

        List<Double> intTrainingDataList = new ArrayList<>();
        List<Double> intTestDataList = new ArrayList<>();

        Double eucResult = new Double(0);
        List<Double> lineDataC2List = new ArrayList<Double>();
        int index = 0 ;
            List<String> trainingSplit = Arrays.asList(trainingSet.split(",\\s*"));
            trainingSplit = trainingSplit.subList(0, range);
            intTrainingDataList = trainingSplit.stream().map(Double::parseDouble).collect(Collectors.toList());

            List<String> TestSplit = Arrays.asList(testInstance.split(",\\s*"));
            TestSplit = TestSplit.subList(0, range);
            intTestDataList = TestSplit.stream().map(Double::parseDouble).collect(Collectors.toList());


            while(index < range) {

                double diff = intTestDataList.get(index) - intTrainingDataList.get(index);

                    double diffSq = Math.pow(diff, 2);
                    eucResult += diffSq;
                index++;

            }
           return eucResult;
            }

    public HashMap<String,Double> calculateEucladianDistance(DataSet dataSet, int range) {

        List<String> testDataList = dataSet.getTestData();
        List<String> trainingDataList = dataSet.getTrainingData();
        Double testDataED = getCustomEucladianDistance(testDataList, 3);
        Double trainingDataED = getCustomEucladianDistance(trainingDataList, 3);
        HashMap<String,Double> mapEd = new HashMap<>();
        mapEd.put("test",testDataED);
        mapEd.put("training",trainingDataED);
        return mapEd;
    }

    private Double getCustomEucladianDistance(List<String> dataList, int range) {


        List<Double> intDataList = new ArrayList<>();
        int index = 0;

        Double eucResult = new Double(0);
        List<Double> lineDataC2List = new ArrayList<Double>();
        try {
            for (String trainingData : dataList) {

                List<String> dataLine = Arrays.asList(trainingData.split(",\\s*"));
                dataLine = dataLine.subList(0, range);
                intDataList = dataLine.stream().map(Double::parseDouble).collect(Collectors.toList());

                if (index != 0) {

                    for (Double data : intDataList) {
                        double diff = lineDataC2List.get(intDataList.indexOf(data)) - data;
                        double diffSq = Math.pow(diff, 2);
                        eucResult += diffSq;
                    }
                } else {
                    lineDataC2List = intDataList;
                }

                index++;
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        double sqrt = Math.sqrt(eucResult);

        return sqrt;
    }

    public Double checkAccuracy(Map<String,Integer> votedResponse, String correctCategory) {
        HashMap votedCat = new HashMap();
        Double correctVote = new Double(0);
        Double totalVotes = new Double(0);
        for(Map.Entry entry : votedResponse.entrySet()) {

            String category = entry.getKey().toString();
            Integer votes = Integer.valueOf(entry.getValue().toString());
            if(category.equals(correctCategory)) {
                correctVote = Double.valueOf(votes);
            }
            totalVotes += votes;
        }
        if(correctVote != 0) {
            return correctVote/totalVotes;
        }
        return new Double(0);
    }

    }


