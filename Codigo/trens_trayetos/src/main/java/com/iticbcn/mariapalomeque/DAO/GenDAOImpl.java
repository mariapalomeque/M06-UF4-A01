package com.iticbcn.mariapalomeque.DAO;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.JDBCException;
import org.hibernate.Session;
import org.hibernate.SessionException;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

public abstract class GenDAOImpl<T> implements GenDAO<T> {

    private SessionFactory sessionFactory;
    private Class<T> classe;

    public GenDAOImpl(SessionFactory sessionFactory, Class<T> classe) {
        this.sessionFactory = sessionFactory;
        this.classe = classe;
    }

    protected SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    @Override
    public void save(T entity) throws Exception {
        try (Session ses = sessionFactory.openSession()) {
            ses.beginTransaction();
            try {
                ses.persist(entity);
                ses.getTransaction().commit();
            } catch (JDBCException jdbcex) {
                handleException(ses, jdbcex, "Error de JDBC");                
            } catch (HibernateException hbex) {
                handleException(ses, hbex, "Error de Hibernate en la transacción");     
            } catch (Exception ex) {
                handleException(ses, ex, "Otras excepciones");    
            }
        } catch (SessionException sesexcp) {
            System.err.println("Error de Sesión: " + sesexcp.getMessage());
            throw sesexcp;
        } catch (HibernateException hbex) {
            System.err.println("Error de Hibernate: " + hbex.getMessage());
            throw hbex;
        }        
    }

    @Override
    public T get(int id) throws Exception {
        T entity = null;
    
        try (Session ses = sessionFactory.openSession()) {
            try {
                entity = ses.find(classe, id);
            } catch (JDBCException jdbcex) {
                handleException(ses, jdbcex, "Error de JDBC");                       
            } catch (HibernateException hbex) {
                handleException(ses, hbex, "Error de Hibernate en la consulta");     
            } catch (Exception ex) {
                handleException(ses, ex, "Otras excepciones");    
            }
        } catch (SessionException sesexcp) {
            System.err.println("Error de Sesión: " + sesexcp.getMessage());
            throw sesexcp;
        } catch (HibernateException hbex) {
            System.err.println("Error de Hibernate: " + hbex.getMessage());
            throw hbex;
        }
    
        return entity;
    }
    
    @Override
    public void update(T entity) throws Exception {
        try (Session ses = sessionFactory.openSession()) {
            ses.beginTransaction();
            try {
                ses.merge(entity);
                ses.getTransaction().commit();
            } catch (JDBCException jdbcex) {
                handleException(ses, jdbcex, "Error de JDBC");                
            } catch (HibernateException hbex) {
                handleException(ses, hbex, "Error de Hibernate en la transacción");     
            } catch (Exception ex) {
                handleException(ses, ex, "Otras excepciones");    
            }
        } catch (SessionException sesexcp) {
            System.err.println("Error de Sesión: " + sesexcp.getMessage());
            throw sesexcp;
        } catch (HibernateException hbex) {
            System.err.println("Error de Hibernate: " + hbex.getMessage());
            throw hbex;
        }
    }

    @Override
    public void delete(T entity) throws Exception {
        try (Session ses = sessionFactory.openSession()) {
            ses.beginTransaction();
            try {
                ses.remove(entity);
                ses.getTransaction().commit();
            } catch (JDBCException jdbcex) {
                handleException(ses, jdbcex, "Error de JDBC");                
            } catch (HibernateException hbex) {
                handleException(ses, hbex, "Error de Hibernate en la transacción");     
            } catch (Exception ex) {
                handleException(ses, ex, "Otras excepciones");    
            }
        } catch (SessionException sesexcp) {
            System.err.println("Error de Sesión: " + sesexcp.getMessage());
            throw sesexcp;
        } catch (HibernateException hbex) {
            System.err.println("Error de Hibernate: " + hbex.getMessage());
            throw hbex;
        }
    }

    @Override
    public List<T> getAll() throws Exception {
        List<T> entities = new ArrayList<>();

        try (Session ses = sessionFactory.openSession()) {
            try {
                Query<T> q = ses.createQuery("from " + classe.getName(), classe); 
                entities = q.list();
            } catch (Exception e) {
                throw new Exception("Error al obtener las entidades.", e);
            }
        } catch (Exception e) {
            throw new Exception("Error al gestionar la sesión Hibernate.", e);
        }

        return entities;
    }

    private void handleException(Session ses, Exception ex, String errorMsg) throws Exception{
        if (ses.getTransaction() != null && ses.getTransaction().isActive()) {
            ses.getTransaction().rollback();
        }
        System.err.println(errorMsg + ": " + ex.getMessage());
        throw ex;
    }
}