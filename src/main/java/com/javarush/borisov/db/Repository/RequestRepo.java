package com.javarush.borisov.db.Repository;


import com.javarush.borisov.db.constants.RequestStatus;
import com.javarush.borisov.entity.Request;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

public interface RequestRepo extends JpaRepository<Request,Long> {


    List<Request> findByEquipmentsMontage_SerialNumberOrEquipmentsUnmontage_SerialNumber(String s1, String s2);

    @Query("SELECT YEAR(r.closeDate) AS year, MONTH(r.closeDate) AS month " +
            "FROM Request r WHERE r.closeDate IS NOT NULL " +
            "GROUP BY YEAR(r.closeDate), MONTH(r.closeDate)")
    List<YearMonthProjection> findAvailableRequests_ClosedDatesGroupedByYearMonth();

    @Query("""
    SELECT r FROM Request r
    LEFT JOIN FETCH r.equipmentsMontage
    LEFT JOIN FETCH r.equipmentsUnmontage
    WHERE r.status IN :statuses
    ORDER BY r.createDate ASC
""")
    List<Request> findByStatusIn(@Param("statuses") Collection<RequestStatus> statuses);
    List<Request> findRequestByTid(String tid);

}

