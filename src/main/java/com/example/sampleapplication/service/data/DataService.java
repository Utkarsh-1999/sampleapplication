package com.example.sampleapplication.service.data;

import com.example.sampleapplication.db.entity.data.Data;
import com.example.sampleapplication.db.repository.data.DataRepository;
import com.example.sampleapplication.exception.data.DataAlreadyExistsException;
import com.example.sampleapplication.exception.data.DataNotFoundException;
import com.example.sampleapplication.view.data.DataView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DataService {

    @Autowired
    DataRepository repo;



    public DataView retrieveData(Integer id) throws IllegalArgumentException,DataNotFoundException{

        Optional<Data> data=repo.findById(id);
        DataView datavview=new DataView();
        if(!data.isPresent())
        {
             throw new DataNotFoundException();
        }

        datavview.setValue(data.get().getValue());
        return datavview;

    }

    public  String createData(Data data) throws DataAlreadyExistsException{

        if(repo.existsById(data.id))
        {
            throw new DataAlreadyExistsException();
        }
        repo.save(data);
        return "Data entered!!!";


    }

    public String updateData(Data data){
        repo.save(data);
        return "Data updated";
    }

    public String deleteData(Integer id) throws DataNotFoundException{

        if(!repo.existsById(id))
        {
            throw new DataNotFoundException();
        }

        repo.deleteById(id);
        return "Data successfully deleted!!!";

    }
}
