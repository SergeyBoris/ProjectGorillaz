package com.javarush.borisov.db.Dao;

import com.javarush.borisov.config.MySessionCreator;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;
@Transactional
public abstract class AbstractDao<T> {


    private final Class<T> clazz;

    public AbstractDao(Class<T> clazz) {
        this.clazz = clazz;
    }

    public  T getById(Long id) {
        try (Session session = MySessionCreator.getSessionCreator().openSession()) {
            return session.get(clazz, id);
        }
    }

    public  List<T> getAll(){

        try (Session session = MySessionCreator.getSessionCreator().openSession()) {
            return session.createQuery("from " + clazz.getSimpleName(), clazz).list();
        }
    }


    public  void save(T entity){
        try (Session session = MySessionCreator.getSessionCreator().openSession()) {
                    session.save(entity);
        }
    }
    public boolean update(T entity){
        Transaction transaction = null;
        try (Session session = MySessionCreator.getSessionCreator().openSession()) {
            transaction = session.beginTransaction();
            session.update(entity);
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
            return false;
        }
    }

    public  void delete(T entity){
        try (Session session = MySessionCreator.getSessionCreator().openSession()) {
                    session.delete(entity);

        }
    }
    public void deleteById(Long id){
        try (Session session = MySessionCreator.getSessionCreator().openSession()) {
                    session.delete(getById(id));
        }
    }
    public void deleteAll(){
        try (Session session = MySessionCreator.getSessionCreator().openSession()) {
                    session.delete(getAll());
        }
    }
}
