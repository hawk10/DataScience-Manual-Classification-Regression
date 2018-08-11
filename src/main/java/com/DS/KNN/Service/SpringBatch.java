package com.DS.KNN.Service;

import com.DS.KNN.Entity.DataSet;
import com.DS.KNN.Repository.DataSetRepository;
import com.DS.KNN.Utilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.step.tasklet.TaskletStep;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@EnableBatchProcessing
@Configuration
@Component
public class SpringBatch {

    Logger logger = LoggerFactory.getLogger(SpringBatch.class);

    @Autowired
    JobBuilderFactory jobBuilderFactory;
    @Autowired
    StepBuilderFactory stepBuilderFactory;
    @Autowired
    DataSetRepository dataSetRepository;

    static HashMap<DataSet, Double> dataSetDoubleHashMap = new HashMap<>();

    private TaskletStep taskletMasterStep(String step) {
        return stepBuilderFactory.get(step).tasklet((contribution, chunkContext) -> {
            return RepeatStatus.FINISHED;

        }).build();

    }

    private TaskletStep taskletStep(String step) {
        return stepBuilderFactory.get(step).tasklet((contribution, chunkContext) -> {

            kNNService kNN = new kNNService();
            logger.info("Step:" + step);
            HashMap<DataSet, Double> dataSetDoubleHashMapLocal = kNN.kNNEucladianDistance();
            if(this.dataSetDoubleHashMap.isEmpty()) {
                this.dataSetDoubleHashMap = dataSetDoubleHashMapLocal;
            }
            else {
                this.dataSetDoubleHashMap.putAll(dataSetDoubleHashMapLocal);
            }
            System.out.println(step + " Finished with Size of: " + this.dataSetDoubleHashMap.size());
            System.out.println(step + " Finished with Size of the Local: " + dataSetDoubleHashMapLocal.size());
            return RepeatStatus.FINISHED;


        }).build();

    }

    private TaskletStep taskletEnd(String step) {

        return stepBuilderFactory.get(step).tasklet((contribution, chunkContext) -> {
            try {
                this.dataSetDoubleHashMap = Utilities.sortByComparatorDataSet(this.dataSetDoubleHashMap, false);
                System.out.println(step + " Inside the END JOB : " + this.dataSetDoubleHashMap.size());
                Map.Entry<DataSet, Double> primeDataSetDoubleEntry = (HashMap.Entry<DataSet, Double>) this.dataSetDoubleHashMap.entrySet().toArray()[0];
                DataSet primeDataSet = primeDataSetDoubleEntry.getKey();
                primeDataSet.setAccuracy(primeDataSetDoubleEntry.getValue());
                dataSetRepository.save(primeDataSet);
            }catch (Exception e) {
                e.printStackTrace();
            }
            return RepeatStatus.FINISHED;


        }).build();

    }



    @Bean
    public Job parallelStepsJob() {


        Flow masterFlow = (Flow)new FlowBuilder("master").start(taskletMasterStep("masterJob")).build();

        Flow flowJob1 = (Flow)new FlowBuilder("flow1").start(taskletStep("SlaveJ1")).build();
        Flow flowJob2 = (Flow)new FlowBuilder("flow2").start(taskletStep("SlaveJ2")).build();
        Flow flowJob3 = (Flow)new FlowBuilder("flow3").start(taskletStep("SlaveJ3")).build();
        Flow flowJobFinish = (Flow)new FlowBuilder("flow3").start(taskletEnd("FinishJob")).build();

        Flow slaveFlow = (Flow)new FlowBuilder("slaveFlow").split(new SimpleAsyncTaskExecutor()).add(flowJob1,flowJob2,flowJob3)
                .build();
        return (jobBuilderFactory.get("parallelFlowJob")
                .incrementer(new RunIdIncrementer())
                .start(masterFlow)
                .next(slaveFlow)
                .next(flowJobFinish)
                .build()).build();



    }


}
