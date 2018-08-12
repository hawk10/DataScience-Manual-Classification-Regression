package com.DS.KNN.ML.kNN;

import com.DS.KNN.Entity.DataSet;
import com.DS.KNN.Utilities;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.util.*;

@Service
public class Import {


    public DataSet importFlowerDS(String path) {

        List<String> trainingData = new ArrayList<>();
        List<String> testData = new ArrayList<>();

        DataSet dataSet = new DataSet(trainingData,testData);

        try {

            File rawDataFile = new File(path);
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

    public DataSet readDataSet(MultipartFile dataSetRaw) {

        List<String> trainingData = new ArrayList<>();
        List<String> testData = new ArrayList<>();

        DataSet dataSet = new DataSet(trainingData,testData);

        try {

            File dataFile = Utilities.convertMultipartFile2File(dataSetRaw);

            FileReader fileReader = new FileReader(dataFile);
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
