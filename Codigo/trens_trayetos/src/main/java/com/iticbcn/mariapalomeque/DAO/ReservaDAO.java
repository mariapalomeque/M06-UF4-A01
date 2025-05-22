package com.iticbcn.mariapalomeque.DAO;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import com.iticbcn.mariapalomeque.Model.Reserva;

public class ReservaDAO extends GenDAOImpl<Reserva> {

    public ReservaDAO(SessionFactory sessionFactory) {
        super(sessionFactory, Reserva.class);
    }

    public List<Reserva> getReservasByCliente(int idCliente) throws Exception {
        List<Reserva> reservas = null;
        try (Session ses = super.getSessionFactory().openSession()) {
            Query<Reserva> query = ses.createQuery("FROM Reserva WHERE idCliente = :idCliente", Reserva.class);
            query.setParameter("idCliente", idCliente);
            reservas = query.list();
        } catch (Exception e) {
            System.err.println("Error al obtener reservas por cliente: " + e.getMessage());
            throw e;
        }
        return reservas;
    }

    public Double getTotalReservasByCompania(int idCompania) throws Exception {
        Double total = null;
        try (Session ses = super.getSessionFactory().openSession()) {
            Query<Double> query = ses.createQuery(
                "SELECT SUM(r.costeTotal) FROM Reserva r WHERE r.compania.idCompania = :idCompania", 
                Double.class);
            query.setParameter("idCompania", idCompania);
            total = query.uniqueResult();
        } catch (Exception e) {
            System.err.println("Error al calcular total de reservas por compañía: " + e.getMessage());
            throw e;
        }
        return total != null ? total : 0.0;
    }
}