package com.examen.time.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.examen.time.entity.TimeTrackingEntity;

@Repository
public interface TimeTrackingRepository  extends JpaRepository<TimeTrackingEntity, Long> {

}
