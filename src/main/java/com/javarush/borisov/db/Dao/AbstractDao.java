package com.javarush.borisov.db.Dao;

import com.javarush.borisov.config.MySessionCreator;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;
@Transactional
public abstract class AbstractDao {


    public <T> T getById(Class<T> clazz, Long id) {
        try (Session session = MySessionCreator.getSessionCreator().openSession()) {
            return session.get(clazz, id);
        }
    }

    public <T> List<T> getAll(Class<T> clazz){
        try (Session session = MySessionCreator.getSessionCreator().openSession()) {
            return session.createQuery("from clazz.getSimpleName()", clazz).list();
        }
    }
    public <T> T getSingleByField(Class<T> clazz, String fieldName, String value){
        try (Session session = MySessionCreator.getSessionCreator().openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<T> cq = cb.createQuery(clazz);
            Root<T> root = cq.from(clazz);
            cq.select(root).where(cb.like(root.get(fieldName).as(String.class),"%" + value + "%"));
            return session.createQuery(cq).getSingleResult();
       }
    }
    public <T> List<T> getListByField(Class<T> clazz, String fieldName, String value){
        try (Session session = MySessionCreator.getSessionCreator().openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<T> cq = cb.createQuery(clazz);
            Root<T> root = cq.from(clazz);
            cq.select(root).where(cb.equal(root.get(fieldName).as(String.class),value));
            return session.createQuery(cq).getResultList();
        }
    }

    public <T> void save(T entity){
        try (Session session = MySessionCreator.getSessionCreator().openSession()) {
                    session.save(entity);
        }
    }
    public <T> void update(T entity){
        try (Session session = MySessionCreator.getSessionCreator().openSession()) {
                    session.update(entity);
        }
    }
    public <T> void delete(T entity){
        try (Session session = MySessionCreator.getSessionCreator().openSession()) {
                    session.delete(entity);

        }
    }
    public <T> void deleteById(Class<T> clazz, Long id){
        try (Session session = MySessionCreator.getSessionCreator().openSession()) {
                    session.delete(getById(clazz,id));
        }
    }
    public <T> void deleteAll(Class<T> clazz){
        try (Session session = MySessionCreator.getSessionCreator().openSession()) {
                    session.delete(getAll(clazz));
        }
    }
}
