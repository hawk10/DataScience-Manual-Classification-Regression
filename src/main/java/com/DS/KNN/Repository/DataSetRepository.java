package com.DS.KNN.Repository;

import com.DS.KNN.Entity.DataSet;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DataSetRepository extends MongoRepository<DataSet, Long> {



}
