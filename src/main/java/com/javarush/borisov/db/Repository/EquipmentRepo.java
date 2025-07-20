package com.javarush.borisov.db.Repository;

import com.javarush.borisov.entity.Equipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface EquipmentRepo extends JpaRepository<Equipment, Long> {

   @Modifying
   @Transactional
   @Query(value = """
    DELETE rem
    FROM requests_equipments_montage rem
    JOIN equipments e ON rem.equipment_id = e.equipment_id
    WHERE e.equipment_id = :eqId
      AND rem.request_id = :reqId
    """, nativeQuery = true)
   int deleteEquipmentMontageFromRequest(@Param("reqId") Long reqId, @Param("eqId")Long eqId);

   @Modifying
   @Transactional
   @Query(value = """
    DELETE reu
    FROM requests_equipments_unmontage reu
   JOIN equipments e ON reu.equipment_id = e.equipment_id
    WHERE e.equipment_id = :eqId
      AND reu.request_id = :reqId
""", nativeQuery = true)
   int deleteEquipmentUnmontageFromRequest(@Param("reqId") Long reqId, @Param("eqId")Long eqId);
}
