package com.iticbcn.mariapalomeque.Controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

import org.hibernate.SessionFactory;

import com.iticbcn.mariapalomeque.DAO.CompaniaDAO;
import com.iticbcn.mariapalomeque.Model.Compania;

public class CompaniaController {
    
    public static void agregarCompania(BufferedReader bf, SessionFactory sf) {
        try {
            System.out.print("Ingrese el nombre de la compañía: ");
            String nombre = bf.readLine();
            
            System.out.print("Ingrese la localidad de la compañía: ");
            String localidad = bf.readLine();
            
            Compania compania = new Compania(nombre, localidad);
            CompaniaDAO companiaDAO = new CompaniaDAO(sf);
            companiaDAO.save(compania);
            
            System.out.println("Compañía registrada exitosamente.");
        } catch (IOException e) {
            System.err.println("Error en la entrada de datos: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error al guardar la compañía: " + e.getMessage());
        }
    }

    public static List<Compania> listarCompanias(SessionFactory sf) {
        try {
            CompaniaDAO companiaDAO = new CompaniaDAO(sf);
            return companiaDAO.getAll();
        } catch (Exception e) {
            System.err.println("Error al listar compañías: " + e.getMessage());
            return null;
        }
    }

    public static void mostrarCompanias(SessionFactory sf) {
        try {
            List<Compania> companias = listarCompanias(sf);
            if (companias == null || companias.isEmpty()) {
                System.out.println("No hay compañías registradas.");
                return;
            }

            System.out.println("\n===== LISTA DE COMPAÑÍAS DISPONIBLES =====");
            for (Compania c : companias) {
                System.out.println("ID: " + c.getIdCompania() + " - Nombre: " + c.getNombre());
            }
        } catch (Exception e) {
            System.err.println("Error al mostrar compañías: " + e.getMessage());
        }
    }

    public static void eliminarCompania(BufferedReader bf, SessionFactory sf) {
        try {
            List<Compania> companias = listarCompanias(sf);
            if (companias == null || companias.isEmpty()) {
                System.out.println("No hay compañías registradas para eliminar.");
                return;
            }

            mostrarCompanias(sf);

            Compania companiaSeleccionada = null;
            CompaniaDAO companiaDAO = new CompaniaDAO(sf);
            
            while (companiaSeleccionada == null) {
                System.out.print("\nIngrese el ID de la compañía que desea eliminar: ");
                try {
                    int idCompania = Integer.parseInt(bf.readLine());
                    companiaSeleccionada = companiaDAO.get(idCompania);
                    if (companiaSeleccionada == null) {
                        System.out.println("ID inválido. Intente nuevamente.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Entrada no válida. Introduzca un número.");
                } catch (IOException e) {
                    System.err.println("Error de entrada: " + e.getMessage());
                    return;
                }
            }

            companiaDAO.delete(companiaSeleccionada);
            System.out.println("Compañía eliminada correctamente.");
            
        } catch (Exception e) {
            System.err.println("Error al eliminar la compañía: " + e.getMessage());
        }
    }
}