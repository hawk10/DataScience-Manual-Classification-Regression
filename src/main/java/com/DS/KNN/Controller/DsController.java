package com.DS.KNN.Controller;

import com.DS.KNN.Entity.DataSet;
import com.DS.KNN.Service.kNNService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.concurrent.CompletableFuture;

@Controller
@RequestMapping("ds/knn")
public class DsController {

    private static final String KNN_CAT ="/classification";
    private static final String KNN_VALUE ="/pseudoRegression";
    private static final String KNN_PREDICT ="/predict";

    @Autowired
    kNNService kNNService;

    @RequestMapping(path = KNN_CAT, method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public CompletableFuture<ResponseEntity> launchClassificationRest(@RequestBody MultipartFile dataSet, int range ) {
        return (CompletableFuture.supplyAsync(() -> launchClassification(dataSet, range)));
    }
    public ResponseEntity launchClassification(MultipartFile dataSet, int range ) {

        String modelID = "";
        try {

                HashMap<DataSet, Double> dataSetDoubleHashMap = new HashMap<>();

                int index = 4;
                int start = 0;
                while(start< index) {
                    HashMap<DataSet, Double> datasetED = kNNService.kNNEucladianDistanceClassification();
                    dataSetDoubleHashMap.putAll(datasetED);
                    start++;
                }

                 modelID = kNNService.saveModelHighestAccuracy(dataSetDoubleHashMap);

            } catch (Exception e) {
                e.printStackTrace();
            return new ResponseEntity(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity(modelID,HttpStatus.OK);

    }
    @RequestMapping(path = KNN_VALUE, method = RequestMethod.POST,consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public CompletableFuture<ResponseEntity> launchPseudoRegressionRest(@RequestBody MultipartFile dataSet, int range ) {
        return (CompletableFuture.supplyAsync(() -> launchpseudoRegression(dataSet,range )));
    }
    @Async
    public ResponseEntity launchpseudoRegression(MultipartFile dataSet, int range ) {

        String modelID = "";
        try {

            HashMap<DataSet, Double> dataSetDoubleHashMap = new HashMap<>();

            int index = 1;
            int start = 0;
            while(start< index) {
            HashMap<DataSet, Double> datasetED = kNNService.kNNEucladianDistancepseudoRegression(dataSet,range);
                    dataSetDoubleHashMap.putAll(datasetED);
                    start++;
            }

            modelID = kNNService.saveModelHighestAccuracy(dataSetDoubleHashMap);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);

        }
        return new ResponseEntity(modelID,HttpStatus.OK);

    }

    @RequestMapping(path = KNN_PREDICT, method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public CompletableFuture<ResponseEntity> launchJobPredictRest(@RequestParam String id, String criteria) {
        return (CompletableFuture.supplyAsync(() -> launchJobPredict(id, criteria)));
    }
    public ResponseEntity launchJobPredict(String id, String criteria) {
        String predictPrice = "";
        try {

             predictPrice = kNNService.PredictPrice(id,criteria);


        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity(predictPrice,HttpStatus.OK);
    }

}
