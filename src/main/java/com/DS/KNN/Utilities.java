package com.DS.KNN;

import com.DS.KNN.Entity.DataSet;

import java.util.*;

public class Utilities {

    public static HashMap<DataSet, Double> sortByComparatorDataSet(Map<DataSet, Double> unsortMap, final boolean order)
    {

        List<Map.Entry<DataSet, Double>> list = new LinkedList<>(unsortMap.entrySet());

        // Sorting the list based on values
        Collections.sort(list, new Comparator<Map.Entry<DataSet, Double>>()
        {
            public int compare(Map.Entry<DataSet, Double> o1,
                               Map.Entry<DataSet, Double> o2)
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
        HashMap<DataSet, Double> sortedMap = new LinkedHashMap<DataSet, Double>();
        for (Map.Entry<DataSet, Double> entry : list)
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

    public static Map<DataSet,Double> combineMaps(Map<DataSet,Double> map1 , Map<DataSet,Double> map2) {
        Map<DataSet, Double> combinedAddedReplacedNew = new HashMap<>();
        try {

            for (Map.Entry<DataSet, Double> added : map1.entrySet()) {

                Double addedValue = added.getValue();
                DataSet addedKey = added.getKey();

                //inner loops beings
                for (Map.Entry<DataSet, Double> replaced : map2.entrySet()) {

                    DataSet replacedKey = replaced.getKey();
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
}
