package com.iticbcn.mariapalomeque.DAO;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import com.iticbcn.mariapalomeque.Model.Compania;

public class CompaniaDAO extends GenDAOImpl<Compania> {

    public CompaniaDAO(SessionFactory sessionFactory) {
        super(sessionFactory, Compania.class);
    }

    public void guardarCompania(Compania compania) {
        try {
            super.save(compania); 
        } catch (Exception e) {
            System.err.println("Error al guardar compañía: " + e.getMessage());
        }
    }

    public static List<Compania> listarCompanias(SessionFactory sf) {
        try {
            CompaniaDAO dao = new CompaniaDAO(sf);
            return dao.getAll(); 
        } catch (Exception e) {
            System.err.println("Error al listar compañías: " + e.getMessage());
            return null;
        }
    }


    public static List<Compania> obtenerCompanias(SessionFactory sf) {
        return listarCompanias(sf); 
    }

    public static Compania obtenerCompaniaPorId(SessionFactory sf, int idCompania) {
        try {
            CompaniaDAO dao = new CompaniaDAO(sf);
            return dao.get(idCompania); 
        } catch (Exception e) {
            System.err.println("Error al buscar la compañía: " + e.getMessage());
            return null;
        }
    }

    public static void eliminarCompania(SessionFactory sf, Compania compania) {
        try {
            CompaniaDAO dao = new CompaniaDAO(sf);
            dao.delete(compania); 
            System.out.println("Compañía eliminada correctamente.");
        } catch (Exception e) {
            System.err.println("Error al eliminar la compañía: " + e.getMessage());
        }
    }

  
    public Compania obtenerCompaniaPorNombre(String nombre) throws Exception {
        Compania compania = null;
        try (Session ses = super.getSessionFactory().openSession()) {
            Query<Compania> query = ses.createQuery("FROM Compania WHERE nombre = :nombre", Compania.class);
            query.setParameter("nombre", nombre);
            compania = query.uniqueResult();
        } catch (Exception e) {
            System.err.println("Error al buscar compañía por nombre: " + e.getMessage());
            throw e;
        }
        return compania;
    }

 
    public List<Compania> obtenerCompaniasPorLocalidad(String localidad) throws Exception {
        List<Compania> companias = null;
        try (Session ses = super.getSessionFactory().openSession()) {
            Query<Compania> query = ses.createQuery("FROM Compania WHERE localidad = :localidad", Compania.class);
            query.setParameter("localidad", localidad);
            companias = query.list();
        } catch (Exception e) {
            System.err.println("Error al buscar compañías por localidad: " + e.getMessage());
            throw e;
        }
        return companias;
    }

    public Long contarCompanias() throws Exception {
        Long total = null;
        try (Session ses = super.getSessionFactory().openSession()) {
            Query<Long> query = ses.createQuery("SELECT COUNT(c) FROM Compania c", Long.class);
            total = query.uniqueResult();
        } catch (Exception e) {
            System.err.println("Error al contar compañías: " + e.getMessage());
            throw e;
        }
        return total != null ? total : 0L;
    }
}