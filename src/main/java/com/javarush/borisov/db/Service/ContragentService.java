package com.javarush.borisov.db.Service;

import com.javarush.borisov.db.Dao.ContragentDao;
import com.javarush.borisov.db.Dto.ContragentDto;
import com.javarush.borisov.entity.Contragent;

import java.util.List;

public class ContragentService {
         ContragentDao contragentDao = new ContragentDao();

    public List<ContragentDto> getAllContragents() {
        return contragentDao.getAll().stream().map(ContragentDto::new).toList();
    }
}
