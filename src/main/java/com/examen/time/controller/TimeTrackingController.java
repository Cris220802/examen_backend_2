package com.examen.time.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.examen.time.entity.TimeTrackingEntity;
import com.examen.time.service.TimeTrackingService;

@RestController
@RequestMapping("/api/v1/time")
public class TimeTrackingController {
    @Autowired
    private TimeTrackingService timeTrackingService;

    //GET de todos
    @GetMapping
    public ResponseEntity<List<TimeTrackingEntity>> getAll(){
        return ResponseEntity.ok(timeTrackingService.getAllTimes());
    }

    //GET
    @GetMapping("/{id}")
    public ResponseEntity<TimeTrackingEntity> getById(@PathVariable Long id){
        return ResponseEntity.ok(timeTrackingService.getTimeeById(id));
    }

    //POST
    @PostMapping
    public ResponseEntity<TimeTrackingEntity> create(@RequestBody TimeTrackingEntity timeTrackingEntity){
        return ResponseEntity.ok(timeTrackingService.createTime(timeTrackingEntity));
    }

    //PUT
    @PutMapping("/{id}")
    public ResponseEntity<TimeTrackingEntity> update(@RequestBody TimeTrackingEntity timeTrackingEntity, @PathVariable Long id){
        timeTrackingEntity.setId(id);
        return ResponseEntity.ok(timeTrackingService.updateTime(timeTrackingEntity));
    }

    //DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<TimeTrackingEntity> delete(@PathVariable Long id){
        timeTrackingService.deleteTimeById(id);
        return ResponseEntity.noContent().build();
    }
}
