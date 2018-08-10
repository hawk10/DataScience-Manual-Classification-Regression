package com.DS.KNN;

import com.DS.KNN.Entity.DataSet;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;

public class Import {


    public DataSet importFlowerDS(String path) {

        List<String> trainingData = new ArrayList<>();
        List<String> testData = new ArrayList<>();

        DataSet dataSet = new DataSet(trainingData,testData);

        try {

            File rawDataFile = new File(path);
            String absolutePath = rawDataFile.getAbsolutePath();
            FileReader fileReader = new FileReader(rawDataFile);

            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String line = null;


            while(bufferedReader.readLine() != null) {

                line = bufferedReader.readLine();
                int random = (int) (Math.random() * 100);
                if(line != null) {
                    if (random < 68) {

                        trainingData.add(line);

                    } else {

                        testData.add(line);
                    }
                }
            }

        }catch (Exception e) {
                e.printStackTrace();
        }

        return dataSet;

    }

}
