package com.iticbcn.mariapalomeque.Controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

import org.hibernate.SessionFactory;

import com.iticbcn.mariapalomeque.DAO.CompaniaDAO;
import com.iticbcn.mariapalomeque.DAO.ReservaDAO;
import com.iticbcn.mariapalomeque.Model.Compania;
import com.iticbcn.mariapalomeque.Model.Reserva;

public class ReservaController {

    public static void agregarReserva(BufferedReader bf, SessionFactory sf) {
        try {
            System.out.print("Ingrese el ID del cliente: ");
            int idCliente = Integer.parseInt(bf.readLine());

            System.out.print("Ingrese el coste total: ");
            double costeTotal = Double.parseDouble(bf.readLine());

            System.out.print("Ingrese la fecha (YYYY-MM-DD): ");
            String fecha = bf.readLine();

            CompaniaDAO companiaDAO = new CompaniaDAO(sf);
            List<Compania> companias = companiaDAO.getAll();
            
            if (companias == null || companias.isEmpty()) {
                System.out.println("No hay compañías registradas. Primero añada una compañía.");
                return;
            }

            System.out.println("\n===== LISTA DE COMPAÑÍAS DISPONIBLES =====");
            for (Compania c : companias) {
                System.out.println("ID: " + c.getIdCompania() + " - Nombre: " + c.getNombre());
            }

            Compania companiaSeleccionada = null;
            while (companiaSeleccionada == null) {
                System.out.print("\nIngrese el ID de la compañía para la reserva: ");
                try {
                    int idCompania = Integer.parseInt(bf.readLine());
                    companiaSeleccionada = companiaDAO.get(idCompania);
                    if (companiaSeleccionada == null) {
                        System.out.println("ID inválido. Intente nuevamente.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Entrada no válida. Introduzca un número.");
                }
            }

            Reserva nuevaReserva = new Reserva(idCliente, costeTotal, fecha, companiaSeleccionada);
            ReservaDAO reservaDAO = new ReservaDAO(sf);
            reservaDAO.save(nuevaReserva);
            
            System.out.println("Reserva añadida correctamente.");

        } catch (IOException | NumberFormatException e) {
            System.err.println("Error en la entrada de datos: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error al agregar la reserva: " + e.getMessage());
        }
    }

    public static void mostrarReservasPorCliente(BufferedReader bf, SessionFactory sf) {
        try {
            System.out.print("Ingrese el ID del cliente: ");
            int idCliente = Integer.parseInt(bf.readLine());

            ReservaDAO reservaDAO = new ReservaDAO(sf);
            List<Reserva> reservas = reservaDAO.getReservasByCliente(idCliente);

            if (reservas == null || reservas.isEmpty()) {
                System.out.println("No hay reservas para este cliente.");
                return;
            }

            System.out.println("\n===== RESERVAS DEL CLIENTE " + idCliente + " =====");
            for (Reserva r : reservas) {
                System.out.println(r);
            }

        } catch (IOException | NumberFormatException e) {
            System.err.println("Error en la entrada de datos: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error al obtener reservas: " + e.getMessage());
        }
    }
}