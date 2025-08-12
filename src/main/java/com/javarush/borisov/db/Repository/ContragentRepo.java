package com.javarush.borisov.db.Repository;

import com.javarush.borisov.entity.Contragent;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContragentRepo extends JpaRepository<Contragent, Integer> {

    @Override
    @NonNull
    List<Contragent> findAll();
}
