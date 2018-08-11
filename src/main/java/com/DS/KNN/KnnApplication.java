package com.DS.KNN;

import com.DS.KNN.Entity.DataSet;
import com.DS.KNN.Service.kNNService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.lang.reflect.Executable;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootApplication
public class KnnApplication {

	public static void main(String[] args) {

		SpringApplication.run(KnnApplication.class, args);
	}
}
