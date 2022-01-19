package com.example.sampleapplication.controller.data;

import com.example.sampleapplication.db.entity.data.Data;
import com.example.sampleapplication.service.data.DataService;
import com.example.sampleapplication.view.data.DataView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class DataController {


    @Autowired
    DataService dataservice;

    @GetMapping(value="/get/{id}")
    private ResponseEntity<DataView> getData(@PathVariable  Integer id){
        return new ResponseEntity<>( dataservice.retrieveData(id), HttpStatus.OK);
    }

    @PostMapping(value="/post")
    private ResponseEntity<String> postData(@RequestBody Data data){
        return new ResponseEntity<>(dataservice.createData(data),HttpStatus.OK);
    }

    @PutMapping(value="/put")
    private ResponseEntity<String> putData(@RequestBody Data data){
        return new ResponseEntity<>(dataservice.updateData(data),HttpStatus.OK);
    }



    @DeleteMapping(value="/delete/{id}")
    private ResponseEntity<String> deleteData(@PathVariable Integer id){

        return new ResponseEntity<>(dataservice.deleteData(id),HttpStatus.OK);
    }

}
