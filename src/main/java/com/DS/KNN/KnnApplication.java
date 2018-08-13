package com.DS.KNN;

import com.DS.KNN.Service.kNNService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class KnnApplication {



	public static void main(String[] args) {

//		Import importData = new Import();
//		kNNService kNNService = new kNNService();
//		kNNService.UnivariateRegressionTrain();
//		File file = new File("src/main/resources/insurance2.csv");
//		MultivariateLinearRegression mLR = new MultivariateLinearRegression();
//		List<String> headers = new ArrayList<>();
//		headers.add("smoker");
//		headers.add("charges");
//		headers.add("children");
//		headers.add("sex");
//		headers.add("region");
//		headers.add("age");
//		headers.add("bmi");
//		mLR.normalize(file,headers);
//		kNNService kNNService = new kNNService();
//		kNNService.UnivariateRegressionTrain();
		SpringApplication.run(KnnApplication.class, args);

	}
}
