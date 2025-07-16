package com.javarush.borisov.db.Service;

import com.javarush.borisov.db.Dao.ContragentDao;
import com.javarush.borisov.entity.dto.old.ContragentDtoOld;

import java.util.List;

public class ContragentService {
         ContragentDao contragentDao = new ContragentDao();

    public List<ContragentDtoOld> getAllContragents() {
        return contragentDao.getAll().stream().map(ContragentDtoOld::new).toList();
    }
}
