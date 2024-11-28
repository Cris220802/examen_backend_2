package com.examen.time.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.examen.time.repository.TimeTrackingRepository;
import com.examen.time.entity.TimeTrackingEntity;

@Service
public class TimeTrackingService {
     @Autowired
    private TimeTrackingRepository timeTrackingRepository;

    //GET de todos
    public List<TimeTrackingEntity> getAllTimes(){
        return timeTrackingRepository.findAll();
    }

    //GET de uno
    public TimeTrackingEntity getTimeeById(Long id){
        return timeTrackingRepository.findById(id).orElse(null);
    }

    //POST
    public TimeTrackingEntity createTime(TimeTrackingEntity timeTrackingEntity){
        return timeTrackingRepository.save(timeTrackingEntity);
    }

    //PUT
    public TimeTrackingEntity updateTime(TimeTrackingEntity timeTrackingEntity){
        if(timeTrackingRepository.existsById(timeTrackingEntity.getId())){
            return timeTrackingRepository.save(timeTrackingEntity);
        }
        return null;
    }

    //DELETE
    public void deleteTimeById(Long id){
        if(timeTrackingRepository.existsById(id)){
            timeTrackingRepository.deleteById(id);
        }
    }
}
