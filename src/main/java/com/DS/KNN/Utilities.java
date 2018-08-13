package com.DS.KNN;

import com.DS.KNN.Entity.DataSetCustom;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.Reader;
import java.util.*;

@Service
public class Utilities {

    public static HashMap<DataSetCustom, Double> sortByComparatorDataSet(Map<DataSetCustom, Double> unsortMap, final boolean order)
    {

        List<Map.Entry<DataSetCustom, Double>> list = new LinkedList<>(unsortMap.entrySet());

        // Sorting the list based on values
        Collections.sort(list, new Comparator<Map.Entry<DataSetCustom, Double>>()
        {
            public int compare(Map.Entry<DataSetCustom, Double> o1,
                               Map.Entry<DataSetCustom, Double> o2)
            {
                if (order)
                {
                    return o1.getValue().compareTo(o2.getValue());
                }
                else
                {
                    return o2.getValue().compareTo(o1.getValue());

                }
            }
        });

        // Maintaining insertion order with the help of LinkedList
        HashMap<DataSetCustom, Double> sortedMap = new LinkedHashMap<DataSetCustom, Double>();
        for (Map.Entry<DataSetCustom, Double> entry : list)
        {
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        return sortedMap;
    }

    public Map<String, Integer> sortByComparatorInteger(Map<String, Integer> unsortMap, final boolean order)
    {

        List<Map.Entry<String, Integer>> list = new LinkedList<Map.Entry<String, Integer>>(unsortMap.entrySet());

        // Sorting the list based on values
        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>()
        {
            public int compare(Map.Entry<String, Integer> o1,
                               Map.Entry<String, Integer> o2)
            {
                if (order)
                {
                    return o1.getValue().compareTo(o2.getValue());
                }
                else
                {
                    return o2.getValue().compareTo(o1.getValue());

                }
            }
        });

        // Maintaining insertion order with the help of LinkedList
        Map<String, Integer> sortedMap = new LinkedHashMap<String, Integer>();
        for (Map.Entry<String, Integer> entry : list)
        {
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        return sortedMap;
    }

    public static Map<DataSetCustom,Double> combineMaps(Map<DataSetCustom,Double> map1 , Map<DataSetCustom,Double> map2) {
        Map<DataSetCustom, Double> combinedAddedReplacedNew = new HashMap<>();
        try {

            for (Map.Entry<DataSetCustom, Double> added : map1.entrySet()) {

                Double addedValue = added.getValue();
                DataSetCustom addedKey = added.getKey();

                //inner loops beings
                for (Map.Entry<DataSetCustom, Double> replaced : map2.entrySet()) {

                    DataSetCustom replacedKey = replaced.getKey();
                    Double replacedValue = replaced.getValue();

                    if (replacedKey.equals(addedKey)) {
                        addedValue = addedValue + replacedValue;
                        combinedAddedReplacedNew.put(addedKey, addedValue);
                    }

                    if (!replacedKey.equals(addedKey)) {
                        if (!map1.containsKey(replacedKey)) {
                            combinedAddedReplacedNew.put(replacedKey, replacedValue);
                        } else {
                            if (!replacedValue.equals(addedValue)) {
                                if (!map1.get(replacedKey).equals(addedValue + "," + replacedValue) && replacedKey.equals(addedKey))
                                    combinedAddedReplacedNew.put(replacedKey, addedValue + replacedValue);
                            }
                        }
                    }
                }
                //inner loop ends

                if (!map1.containsKey(addedKey)) {
                    combinedAddedReplacedNew.put(addedKey, addedValue);
                }
                if (map1.containsKey(addedKey)) {
                    combinedAddedReplacedNew.put(addedKey, addedValue);
                }


            }

            if (combinedAddedReplacedNew.isEmpty()) {
                combinedAddedReplacedNew = map1;
            }

            if (combinedAddedReplacedNew.isEmpty()) {
                combinedAddedReplacedNew = map2;
            }

        }
        catch(Exception e) {
            e.printStackTrace();

        }
        return combinedAddedReplacedNew;
    }

    public static File convertMultipartFile2File(MultipartFile multipartFile) throws Exception {



        File dataFile = new File(multipartFile.getOriginalFilename());
        dataFile.createTempFile("data",".csv");
        FileOutputStream dataFileStream = new FileOutputStream(dataFile);
        dataFileStream.write(multipartFile.getBytes());
        dataFileStream.close();
        return dataFile;

    }

    public static List<Map<String,String>> convertMultipartFile2CSV2Map(File file) {


        List<Map<String,String>> mapList = new ArrayList<>();
        try {
//        CSVParser csvParsed = CSVParser.parse(Utilities.convertMultipartFile2File(file), Charset.defaultCharset(), CSVFormat.RFC4180);
            Reader fileReader = new FileReader(file);
            Iterable<CSVRecord> csvParsed = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(fileReader);

            for (CSVRecord record : csvParsed) {

                mapList.add(record.toMap());

            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return mapList;
    }

    public static double calculateSD(List<Double> valueList) {
        double sum = 0.0, standardDeviation = 0.0;

        for(double num : valueList) {
            sum += num;
        }

        double mean = sum/10;

        for(double num: valueList) {
            standardDeviation += Math.pow(num - mean, 2);
        }

        return Math.sqrt(standardDeviation/10);
    }
}
