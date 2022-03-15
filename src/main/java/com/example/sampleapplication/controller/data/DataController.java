package com.example.sampleapplication.controller.data;

import com.example.sampleapplication.ratelimiter.annotation.RateLimit;
import com.example.sampleapplication.timetracker.annotation.TimeTracker;
import com.example.sampleapplication.db.entity.data.Data;
import com.example.sampleapplication.service.data.DataService;
import com.example.sampleapplication.view.data.DataView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.RequestScope;


@RestController
public class DataController {


    @Autowired
    DataService dataservice;




    @TimeTracker
    @RateLimit(keyGenerator="ipBasedKeyGenerator")
    @GetMapping(value="/data/{id}")
    public ResponseEntity<DataView> getData(@PathVariable  Integer id){
        return new ResponseEntity<>( dataservice.retrieveData(id), HttpStatus.OK);
    }

    @TimeTracker
    @RateLimit(keyGenerator="ipBasedKeyGenerator")
    @PostMapping(value="/data")
    public ResponseEntity<String> postData(@RequestBody Data data){
        dataservice.createData(data);
        return new ResponseEntity<>("Data entered!!!",HttpStatus.CREATED);
    }

    @TimeTracker
    @RateLimit(keyGenerator="ipBasedKeyGenerator")
    @PutMapping(value="/data")
    public ResponseEntity<String> putData(@RequestBody Data data){
        dataservice.updateData(data);
        return new ResponseEntity<>("Data successfully updated!!",HttpStatus.OK);
    }


    @TimeTracker
    @RateLimit(keyGenerator="ipBasedKeyGenerator")
    @DeleteMapping(value="/data/{id}")
    public ResponseEntity<String> deleteData(@PathVariable Integer id){
        dataservice.deleteData(id);
        return new ResponseEntity<>("Data successfully Deleted!!",HttpStatus.OK);
    }

}
