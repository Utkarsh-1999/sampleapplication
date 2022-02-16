package com.example.sampleapplication.service.data;

import com.example.sampleapplication.db.entity.data.Data;
import com.example.sampleapplication.db.repository.data.DataRepository;
import com.example.sampleapplication.exception.data.DataAlreadyExistsException;
import com.example.sampleapplication.exception.data.DataNotFoundException;
import com.example.sampleapplication.view.data.DataView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DataService {

    @Autowired
    DataRepository repo;


    @Cacheable(value="data",key="#id")
    public DataView retrieveData(Integer id) throws IllegalArgumentException,DataNotFoundException{

        Optional<Data> data=repo.findById(id);
        DataView dataView =new DataView();
        if(data.isEmpty())
        {
             throw new DataNotFoundException();
        }

        dataView.setValue(data.get().getValue());
        return dataView;

    }


    public  void createData(Data data) throws DataAlreadyExistsException{

        if(repo.existsById(data.id))
        {
            throw new DataAlreadyExistsException();
        }
        repo.save(data);



    }


    @CachePut(value = "data",key="#data.id")
    public DataView updateData(Data data){
        repo.save(data);
        DataView dataView=new DataView();
        dataView.setValue(data.getValue());
        return dataView;
    }


    @CacheEvict(value="data",key="#id")
    public void deleteData(Integer id) throws DataNotFoundException{

        if(!repo.existsById(id))
        {
            throw new DataNotFoundException();
        }

        repo.deleteById(id);


    }
}
