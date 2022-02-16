package com.example.sampleapplication.controller.data;

import com.example.sampleapplication.ratelimiter.annotation.RateLimit;
import com.example.sampleapplication.ratelimiter.clientip.ClientIpHandler;
import com.example.sampleapplication.timetracker.annotation.TimeTracker;
import com.example.sampleapplication.db.entity.data.Data;
import com.example.sampleapplication.service.data.DataService;
import com.example.sampleapplication.view.data.DataView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;



@RestController
public class DataController {


    @Autowired
    DataService dataservice;



    @TimeTracker
    @RateLimit(expression="{'get-data-'+@clientIpHandler.getClientIp(#request)}")
    @GetMapping(value="/data/{id}")
    public ResponseEntity<DataView> getData(HttpServletRequest request, @PathVariable  Integer id){
        return new ResponseEntity<>( dataservice.retrieveData(id), HttpStatus.OK);
    }

    @TimeTracker
    @RateLimit(expression= "{'post-data-'+@clientIpHandler.getClientIp(#request)}")
    @PostMapping(value="/data")
    public ResponseEntity<String> postData(HttpServletRequest request,@RequestBody Data data){
        dataservice.createData(data);
        return new ResponseEntity<>("Data entered!!!",HttpStatus.CREATED);
    }

    @TimeTracker
    @RateLimit(expression= "{'put-data-'+@clientIpHandler.getClientIp(#request)}")
    @PutMapping(value="/data")
    public ResponseEntity<String> putData(HttpServletRequest request,@RequestBody Data data){
        dataservice.updateData(data);
        return new ResponseEntity<>("Data successfully updated!!",HttpStatus.OK);
    }


    @TimeTracker
    @RateLimit(expression= "{'delete-data-'+@clientIpHandler.getClientIp(#request)}")
    @DeleteMapping(value="/data/{id}")
    public ResponseEntity<String> deleteData(HttpServletRequest request,@PathVariable Integer id){
        dataservice.deleteData(id);
        return new ResponseEntity<>("Data successfully Deleted!!",HttpStatus.OK);
    }

}
