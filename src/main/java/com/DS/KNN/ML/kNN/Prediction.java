package com.DS.KNN.ML.kNN;

import com.DS.KNN.Utilities;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class Prediction {

    public Map<String,Integer> kNNEucaladianDistancePrediction(List<Object> neighbours) {

        HashMap<String,Integer> categoryVote = new HashMap();

        for(Object object: neighbours) {
            Map.Entry mapEntry = (Map.Entry) object;
            List<String> keySplit = Arrays.asList(mapEntry.getKey().toString().split(",\\s*"));
            String category = keySplit.get(keySplit.size()-1);
            if(categoryVote.containsKey(category)) {

                categoryVote.replace(category,categoryVote.get(category)+1);

            }else {
                categoryVote.put(category,1);
            }

        }
        Utilities utilities = new Utilities();
        Map<String, Integer> sortedMap = utilities.sortByComparatorInteger(categoryVote, false);
        return sortedMap;
    }
}
