package com.DS.KNN.Repository;

import com.DS.KNN.Entity.DataSetCustom;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DataSetRepository extends MongoRepository<DataSetCustom, String> {



}
