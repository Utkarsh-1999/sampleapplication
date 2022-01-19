package com.example.sampleapplication.db.repository.data;

import com.example.sampleapplication.db.entity.data.Data;
import org.springframework.data.repository.CrudRepository;

public interface DataRepository extends CrudRepository<Data,Integer> {
}
