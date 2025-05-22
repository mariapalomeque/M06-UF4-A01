package com.iticbcn.mariapalomeque.DAO;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import com.iticbcn.mariapalomeque.Model.Trayecto;

public class TrayectoDAO extends GenDAOImpl<Trayecto> {

    public TrayectoDAO(SessionFactory sessionFactory) {
        super(sessionFactory, Trayecto.class);
    }

    public List<Trayecto> getTrayectosBySalidaDestino(String salida, String llegada) throws Exception {
        List<Trayecto> trayectos = null;
        try (Session ses = super.getSessionFactory().openSession()) {
            Query<Trayecto> query = ses.createQuery(
                "FROM Trayecto WHERE salida = :salida AND llegada = :llegada", 
                Trayecto.class);
            query.setParameter("salida", salida);
            query.setParameter("llegada", llegada);
            trayectos = query.list();
        } catch (Exception e) {
            System.err.println("Error al obtener trayectos por salida y destino: " + e.getMessage());
            throw e;
        }
        return trayectos;
    }

    public Double getPrecioMedio() throws Exception {
        Double precioMedio = null;
        try (Session ses = super.getSessionFactory().openSession()) {
            Query<Double> query = ses.createQuery("SELECT AVG(t.precio) FROM Trayecto t", Double.class);
            precioMedio = query.uniqueResult();
        } catch (Exception e) {
            System.err.println("Error al calcular precio medio: " + e.getMessage());
            throw e;
        }
        return precioMedio != null ? precioMedio : 0.0;
    }
}