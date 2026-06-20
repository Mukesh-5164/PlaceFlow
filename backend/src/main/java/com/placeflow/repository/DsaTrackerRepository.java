package com.placeflow.repository;

import com.placeflow.entity.DsaTracker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DsaTrackerRepository extends JpaRepository<DsaTracker, Long> {

    @Query("SELECT AVG(d.progressPercentage) FROM DsaTracker d")
    Double findAverageProgress();
}
