package com.DS.KNN.ML.kNN;

import com.DS.KNN.Entity.DataSet;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
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
        Double testDataED = getCustomEucladianDistance(testDataList, range);
        Double trainingDataED = getCustomEucladianDistance(trainingDataList, range);
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

    public void univariateLinearRegressionTest(Double sumX, Double sumY, Double sumXY, Double sumX2, Double sumY2,Double numberOfRecords) {
        //independent value is the value that we already have =x
        //dependent value is to predict?? = y
        HashMap<Double,Double> mapXY = new HashMap<>();

        mapXY.put(new Double(21), new Double(65));
        mapXY.put(new Double(25), new Double(79));
        mapXY.put(new Double(42), new Double(75));
//        mapXY.put(new Double(43), new Double(99));
        mapXY.put(new Double(57), new Double(87));
//        mapXY.put(new Double(59), new Double(81));

        numberOfRecords = Double.valueOf(mapXY.size());
        for(Map.Entry<Double,Double> entry : mapXY.entrySet()) {

            sumX += entry.getKey();
            sumY += entry.getValue();

            sumX2 += Math.pow(entry.getKey(),new Double(2));
            sumY2 += Math.pow(entry.getValue(),new Double(2));

            sumXY += entry.getKey() * entry.getValue();

        }

        //a = y intercept
        double SUMY_M_SUMX2 = sumY * sumX2;
        double SUMX_M_SUMXY = sumX * sumXY;
        double SUMX2_M_N = sumX2 * numberOfRecords;
        double SUMX_POWER2 = Math.pow(sumX,new Double(2));

        double A_TOP = SUMY_M_SUMX2 - SUMX_M_SUMXY;
        double A_BOT = SUMX2_M_N - SUMX_POWER2;

        double Y_INTERCEPT = A_TOP / A_BOT;

        //b =  slope
        double SUMXY_M_N = sumXY * numberOfRecords;
        double SUMX_M_SUMY = sumX * sumY;

        double B_TOP = SUMXY_M_N - SUMX_M_SUMY;
        double B_BOT = SUMX2_M_N -SUMX_POWER2 ;

        double SLOPE = B_TOP / B_BOT;

        //y' = a + bx;
        //answer  SLOPE + Y_INTERCEPT(INDEPENDENT VAR)

        double answer = Y_INTERCEPT + SLOPE * new Double(59);
        System.out.println(answer);
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


    public Double checkAccuracyInRange(Map<String,Integer> votedResponse, String correctCategory) {
        HashMap votedCat = new HashMap();
        Double correctVote = new Double(0);
        Double totalVotes = new Double(0);
        List<Double> correctvotes = new ArrayList<>();
        for(Map.Entry entry : votedResponse.entrySet()) {

            String category = entry.getKey().toString();
            Integer votes = Integer.valueOf(entry.getValue().toString());
            Double categoryRange = Double.parseDouble(correctCategory);
            Double maxRange = categoryRange + categoryRange *0.110;
            Double minRange = categoryRange *0.90;

            if (Double.parseDouble(entry.getKey().toString()) > minRange
                    && Double.parseDouble(entry.getKey().toString()) < maxRange) {

                correctVote = Double.valueOf(votes);
                correctvotes.add(Double.parseDouble(entry.getKey().toString()));
            }
            totalVotes += votes;
        }
        if(correctVote != 0) {
            return correctvotes.size()/totalVotes;
        }
        return new Double(0);
    }


    }


