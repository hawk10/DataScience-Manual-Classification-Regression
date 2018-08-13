package com.DS.KNN.ML;

import com.DS.KNN.Utilities;

import java.io.File;
import java.util.List;
import java.util.Map;

public class MultivariateLinearRegression {

    public void normalize(File file,List<String> headers) {

        List<Map<String, String>> maps = Utilities.convertMultipartFile2CSV2Map(file);

        double[] arraySumFromMap = getArraySumFromMap(maps, headers.size());
//

    }

    public double[] getArraySumFromMap(List<Map<String, String>> maps, int size ) {

        double[] dataArray = new double[size];


        for (Map<String,String> map : maps) {

            int index = 0;

            for(Map.Entry entry : map.entrySet()) {

                String key = entry.getKey().toString();
                Double value = Double.valueOf(entry.getValue().toString());
                if(dataArray[index] == 0) {
                    dataArray[index] = value;
                }else {
                    dataArray[index] = dataArray[index] + value;
                }
                index++;
            }

        }
        return dataArray;
    }


}
