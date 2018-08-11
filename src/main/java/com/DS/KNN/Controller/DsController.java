package com.DS.KNN.Controller;

import com.DS.KNN.Service.SpringBatch;
import com.DS.KNN.Service.kNNService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("ds")
public class DsController {

    private static final String KNN ="/knn";

    @Autowired
    kNNService kNNService;

    @RequestMapping(path = KNN, method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public void launchJob() {
            try {
                int index = 4;
                int start = 0;
//                while(start< index) {
//                    kNNService.kNNEucladianDistance();
                    kNNService.PredictPrice();
//                    kNNService.kNNEucladianDistanceResultRange();
                    start++;
//                }


            } catch (Exception e) {
                e.printStackTrace();
            }

    }
}
