package com.javarush.borisov.db.Dao;

import com.javarush.borisov.config.MySessionCreator;
import com.javarush.borisov.db.constants.RequestStatus;
import com.javarush.borisov.entity.Request;
import org.hibernate.Session;

import java.util.List;

public class RequestDao extends AbstractDao<Request> {

    public RequestDao() {
        super(Request.class);
    }

    public List<Request> getRequestsWhereUsedEquipment(String equipment){

        try (Session session = MySessionCreator.getSessionCreator().openSession()) {
            return session.createQuery(
                            "select distinct r from Request r " +
                            "join fetch r.equipmentsMontage em " +
                            "join fetch r.equipmentsUnmontage eum " +
                            "where em.serialNumber = :serialNumber1 " +
                            "or eum.serialNumber = :serialNumber2 " +
                            "order by r.closeDate",
                            Request.class)
                    .setParameter("serialNumber1", equipment)
                    .setParameter("serialNumber2", equipment)
                    .list();
        }

    }
    public List<Request> getAssignedRequests(){
        try (Session session = MySessionCreator.getSessionCreator().openSession()) {
           return session.createQuery("select distinct r from Request r " +
                                      "left join fetch r.equipmentsMontage " +
                                      "left join fetch r.equipmentsUnmontage " +
                                      "where r.status in (:statuses) " +
                                      "order by r.closeDate", Request.class)
                   .setParameterList("statuses", List.of(RequestStatus.ASSIGNED, RequestStatus.IN_PROGRESS))
                   .list();
        }
    }
    public List<Request> getAssignedUsersRequests(Long id){
        try (Session session = MySessionCreator.getSessionCreator().openSession()) {
            return session.createQuery("select r from Request r " +
                                       "left join fetch r.equipmentsMontage " +
                                       "left join fetch r.equipmentsUnmontage " +
                                       "where r.user.id  = :userId " +
                                       "and r.status in (:statuses) ", Request.class)
                    .setParameter("userId",id)
                    .setParameter("statuses", List.of(RequestStatus.ASSIGNED,RequestStatus.IN_PROGRESS))
                    .list();
        }
    }
}
