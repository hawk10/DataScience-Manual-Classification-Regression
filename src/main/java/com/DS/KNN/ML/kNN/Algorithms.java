package com.DS.KNN.ML.kNN;

import com.DS.KNN.Entity.DataSetCustom;
import com.DS.KNN.Entity.UnivariateLinearRegression;
import com.DS.KNN.Utilities;
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

    public HashMap<String,Double> calculateEucladianDistance(DataSetCustom dataSetCustom, int range) {

        List<String> testDataList = dataSetCustom.getTestData();
        List<String> trainingDataList = dataSetCustom.getTrainingData();
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

    public UnivariateLinearRegression univariateLinearRegressionGenerateModel(DataSetCustom dataSetCustom) {


        List<HashMap<Double,Double>> uniDataSet = new ArrayList<>();
        List<Double> avgPriceList =  new ArrayList<>();
        List<Double> totalVolumeList =  new ArrayList<>();
        HashMap<Double,Double> mapXY = new HashMap<>();
        Double sumX =  new Double(0);
        Double sumY =  new Double(0);
        Double sumXY =  new Double(0);
        Double sumX2 =  new Double(0);
        Double sumY2 =  new Double(0);
        Double numberOfRecords = Double.valueOf(dataSetCustom.getTrainingData().size());

        Double minX = new Double(0);
        Double minY = new Double(0);
        Double maxX = new Double(0);
        Double maxY = new Double(0);

        for( String trainingData : dataSetCustom.getTrainingData()) {

            List<String> dataLine = Arrays.asList(trainingData.split(",\\s*"));
            Double averagePrice = Double.parseDouble(dataLine.get(2));
            Double totalVolume = Double.parseDouble(dataLine.get(3));
            avgPriceList.add(averagePrice);
            totalVolumeList.add(totalVolume);
            sumX += totalVolume;
            sumY += averagePrice;
            if(maxX == 0) {
                maxX = totalVolume;
            }
            else if (maxX < totalVolume ) {

                maxX = totalVolume;

            }

            if(maxY == 0) {
                maxY = averagePrice;
            }
            else if (maxY < averagePrice ) {

                maxY = averagePrice;

            }

            if(minX == 0) {
                minX = totalVolume;
            }
            else if (minX > totalVolume ) {

                minX = totalVolume;

            }

            if(minY == 0) {
                minY = averagePrice;
            }
            else if (minY > averagePrice ) {

                minY = averagePrice;

            }
//
//            sumX2 += Math.pow(totalVolume,new Double(2));
//            sumY2 += Math.pow(averagePrice,new Double(2));
//
//            sumXY += totalVolume * averagePrice;

        }

        Double meanX = sumX/numberOfRecords;
        Double meanY = sumY/numberOfRecords;
        double avgPriceSD = Utilities.calculateSD(avgPriceList);
        double totalVolumeSD = Utilities.calculateSD(totalVolumeList);
        // data - mean/std

        sumX = new Double(0);
        sumY = new Double(0);



        for( String trainingData : dataSetCustom.getTrainingData()) {

            List<String> dataLine = Arrays.asList(trainingData.split(",\\s*"));
            Double averagePrice = Double.parseDouble(dataLine.get(2));
            Double totalVolume = Double.parseDouble(dataLine.get(3));

//            double avgPriceNormalized = (averagePrice - meanY) / avgPriceSD;
//            double totalVolumeNormalized = (totalVolume - meanX) / totalVolumeSD;

            double totalVolumeNormalized  =(totalVolume  - minX)/(maxX-minX);
            double avgPriceNormalized = (averagePrice- minY)/(maxY-minY);

            sumX += totalVolumeNormalized;
            sumY += avgPriceNormalized;

            sumX2 += Math.pow(totalVolumeNormalized,new Double(2));
            sumY2 += Math.pow(avgPriceNormalized,new Double(2));

            sumXY += totalVolumeNormalized * avgPriceNormalized;

        }


        //independent value is the value that we already have =x
        //dependent value is to predict?? = y

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


        UnivariateLinearRegression univariateLinearRegression = new UnivariateLinearRegression(SLOPE,Y_INTERCEPT,maxY,minY,
                maxX,minX);

        return univariateLinearRegression;
    }

    public Double univariateLinearRegressionPredict(UnivariateLinearRegression model) {

        double avgPriceNormalized = (model.getValue()- model.getMinX())/(model.getMaxX()-model.getMinX());

        double normalziedAnswer = model.getyIntercept() + model.getSlope() * avgPriceNormalized;
        double denormalziedAnswer = normalziedAnswer * (model.getMaxY() - model.getMinY()) + model.getMinY();
//        System.out.println(model.getValue());
        return denormalziedAnswer;
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


