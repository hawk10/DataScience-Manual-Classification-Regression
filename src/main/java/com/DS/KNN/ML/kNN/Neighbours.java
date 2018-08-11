package com.DS.KNN.ML.kNN;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class Neighbours {


    public List<Object> getNeighbours(List<String> trainingSetList, String testInstance, int results2Return) {

        Algorithms algorithms = new Algorithms();
        HashMap distance = new HashMap();
        HashMap neighbours =  new LinkedHashMap();
        for(String trainingSet : trainingSetList) {

            Double eucladianDistance = algorithms.getEucladianDistance(trainingSet, testInstance, 3);
            distance.put(trainingSet,eucladianDistance);

        }

        List<Object> mapEntryList = new ArrayList<>();
        Map<String, Integer> sortedMapAsc = sortByComparator(distance, true);
        Map.Entry<String, Integer> nextItem = sortedMapAsc.entrySet().iterator().next();
        for(int x = 0; x < results2Return; x ++) {


            Object mapData = sortedMapAsc.entrySet().toArray()[x];
            mapEntryList.add(mapData);

        }

        return mapEntryList;
    }

    private static Map<String, Double> sortByComparator(Map<String, Double> unsortMap, final boolean order)
    {

        List<Map.Entry<String, Double>> list = new LinkedList<Map.Entry<String, Double>>(unsortMap.entrySet());

        // Sorting the list based on values
        Collections.sort(list, new Comparator<Map.Entry<String, Double>>()
        {
            public int compare(Map.Entry<String, Double> o1,
                               Map.Entry<String, Double> o2)
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
        Map<String, Double> sortedMap = new LinkedHashMap<String, Double>();
        for (Map.Entry<String, Double> entry : list)
        {
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        return sortedMap;
    }
}
