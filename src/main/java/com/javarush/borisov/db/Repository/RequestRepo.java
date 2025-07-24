package com.javarush.borisov.db.Repository;


import com.javarush.borisov.entity.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RequestRepo extends JpaRepository<Request,Long> {
    List<Request> findByEquipmentsMontage_SerialNumberOrEquipmentsUnmontage_SerialNumber(String s1, String s2);

    @Query("SELECT YEAR(r.closeDate) AS year, MONTH(r.closeDate) AS month " +
            "FROM Request r WHERE r.closeDate IS NOT NULL " +
            "GROUP BY YEAR(r.closeDate), MONTH(r.closeDate)")
    List<YearMonthProjection> findAvailableRequests_ClosedDatesGroupedByYearMonth();

}
