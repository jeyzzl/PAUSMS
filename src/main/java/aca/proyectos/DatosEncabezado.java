/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aca.proyectos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import jakarta.servlet.http.HttpServletRequest;

/**
 *
 * @author Daniel
 */
public class DatosEncabezado {
	
	Connection c;
	
	public DatosEncabezado(Connection con){
		c=con;
	}

    public List<String> jefes(String id_ejercicio) {
        ArrayList<String> salida = new ArrayList<String>();

        try {
        	PreparedStatement pst = c.prepareStatement("SELECT E.CLAVE FROM ARON.CAT_JEFES J, ARON.EMPLEADO E WHERE E.ID=J.JEFE_ID AND EJERCICIO_ID=? ");
        	
            pst.setString(1, id_ejercicio);
            ResultSet rs = pst.executeQuery();
                while (rs.next()) {
                    salida.add(rs.getString("clave"));
                }
                rs.close();
            pst.close();
        } catch (SQLException sqle) {
            System.err.println("Error en jefes " + sqle);
        }

        return salida;
    }

    public List<String> deptosByJefes(String clave, String id_ejercicio) {
    	ArrayList<String> salida = new ArrayList<String>();

        try {
        	
        	PreparedStatement pst = c.prepareStatement(""
                    + "SELECT "
                    + "     J.CCOSTO_ID "
                    + "FROM "
                    + "     ARON.CAT_JEFES J, "
                    + "     ARON.EMPLEADO E "
                    + "WHERE "
                    + "     E.CLAVE=? "
                    + "     AND J.JEFE_ID=E.ID "
                    + "     AND J.EJERCICIO_ID=?");

            pst.setString(1, clave);
            pst.setString(2, id_ejercicio);

            ResultSet rs = pst.executeQuery();
                while (rs.next()) {
                    salida.add(rs.getString("ccosto_id"));
                }
                rs.close();
           
            pst.close();
        } catch (SQLException sqle) {
            System.err.println("Error en deptosByJefes " + sqle);
        }

        return salida;
    }

    public String getCcostoNombre( String ccosto, String id_ejercicio) {
        String salida = "";

        try {
        	
        	PreparedStatement pst = c.prepareStatement("SELECT nombre FROM MATEO.CONT_CCOSTO WHERE  id_ejercicio=?  AND ID_CCOSTO=?");
        	
            pst.setString(1, id_ejercicio);
            pst.setString(2, ccosto);
            ResultSet rs = pst.executeQuery();
                while (rs.next()) {
                    salida = rs.getString("nombre");
                }
                rs.close();
            
            pst.close();
        } catch (SQLException sqle) {
            System.err.println("Error en getCcostoNombre " + sqle);

        }

        return salida;
    }

    public DptoMisionVision getMV( String ccosto) {
        DptoMisionVision dmv = null;

        try {
        	
        	PreparedStatement pst = c.prepareStatement("SELECT * FROM DANIEL.DPTO_MISIONVISION WHERE CCOSTO=?");        	
            pst.setString(1, ccosto);

            ResultSet rs = pst.executeQuery();
                if (rs.next()) {

                    dmv = new DptoMisionVision();
                    dmv.setCcosto(rs.getString("ccosto"));
                    dmv.setCreador(rs.getString("creador"));
                    dmv.setFecha_creado(rs.getString("fecha_creado"));
                    dmv.setFecha_modificado(rs.getString("fecha_modificado"));
                    dmv.setVision(rs.getString("vision"));
                    dmv.setMision(rs.getString("mision"));

                }
                rs.close();
            
            pst.close();
        } catch (SQLException sqle) {
            System.err.println("Error en PreparedStatement getMV " + sqle);
        }
        return dmv;
    }

    public void agregaMisionVision(HttpServletRequest request) {
        boolean pasa = true;
        if (request.getParameter("ccosto").equals("")) {
            pasa = false;
        }
        if (request.getParameter("creador").equals("")) {
            pasa = false;
        }
        if (pasa) {
            try {
            	
            	PreparedStatement pst = c.prepareStatement("INSERT INTO DANIEL.DPTO_MISIONVISION "
                        + "(CCOSTO, CREADOR, FECHA_MODIFICADO, VISION, MISION) VALUES(?,?,now(),?,?)");
            	
                pst.setString(1, request.getParameter("ccosto"));
                pst.setString(2, request.getParameter("creador"));
                pst.setString(3, request.getParameter("vision"));
                pst.setString(4, request.getParameter("mision"));
                pst.executeUpdate();
                pst.close();
            } catch (SQLException sqle) {
                System.err.println("Error en agregaMisionVision " + sqle);
            }
        }
    }

    public void cambiaMisionVision( HttpServletRequest request) {
        boolean pasa = true;
        if (request.getParameter("ccosto").equals("")) {
            pasa = false;
        }
        if (request.getParameter("creador").equals("")) {
            pasa = false;
        }
        if (pasa) {
            try {
            	
            	PreparedStatement pst = c.prepareStatement("UPDATE DANIEL.DPTO_MISIONVISION SET "
                        + "CREADOR=?, FECHA_MODIFICADO=now(), VISION=?, MISION=? WHERE CCOSTO=?");
            	
                pst.setString(1, request.getParameter("creador"));
                pst.setString(2, request.getParameter("vision"));
                pst.setString(3, request.getParameter("mision"));
                pst.setString(4, request.getParameter("ccosto"));
                pst.executeUpdate();
                pst.close();
            } catch (SQLException sqle) {
                System.err.println("Error en agregaMisionVision " + sqle);
            }
        }
    }
}
