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
public class OpRecFinancieros {
	
    Connection c;
    
    public OpRecFinancieros(Connection con){
    	c=con;
    }
    
    public void rmRecfin(int id){
    	try{
    		PreparedStatement pst = c.prepareStatement("DELETE FROM DANIEL.DPTO_RECFINANCIEROS WHERE ID=?");
    		pst.setInt(1, id);
    		
    		pst.executeUpdate();
    		pst.close();
    	}catch(SQLException sqle ){
    		System.err.println("Error en PreparedStatement rmRecfin " + sqle);
    	}
    }

    public List<DptoRecFinancieros> getLista(int id_actividad){
        List<DptoRecFinancieros> salida = new ArrayList<DptoRecFinancieros>();

        try {
        	
        	PreparedStatement pst = c.prepareStatement("SELECT ID, ID_ACTIVIDAD, "
        	+" TIPO_RECURSOS, IMPORTE, to_char(FECHA_CREACION,'dd-mm-yyyy') as FECHA_CREACION, ESTADO  "
                    + " FROM DANIEL.DPTO_RECFINANCIEROS WHERE ID_ACTIVIDAD=?");
        	
            pst.setInt(1, id_actividad);
            
        	ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                DptoRecFinancieros dme;
                dme = new DptoRecFinancieros();
                dme.setId(rs.getInt("id"));
                dme.setId_actividad(rs.getInt("id_actividad"));
                dme.setTipo_recursos(rs.getString("tipo_recursos"));
                dme.setImporte(rs.getInt("importe"));
                dme.setFecha_creacion(rs.getString("fecha_creacion"));
                dme.setEstado(rs.getString("estado"));

                salida.add(dme);
            }
 
            rs.close();
            pst.close();

        } catch (SQLException sqle) {
            System.err.println("Error en PreparedStatement getLista " + sqle);
        }
        return salida;
    }
    
    public void addDptoRecfinancieros(HttpServletRequest request) {
        boolean pasa = true;
        if (request.getParameter("TKL_idactividad").equals("")) {
            pasa = false;
        }
        if (request.getParameter("importe").equals("")) {
            pasa = false;
        }
        if (pasa) {
            try {
            	
            	PreparedStatement pst = c.prepareStatement("INSERT INTO DANIEL.DPTO_RECFINANCIEROS "
                        + "(ID_ACTIVIDAD, TIPO_RECURSOS, IMPORTE,ESTADO) VALUES(?,?,?,?)");
            	
                pst.setString(1, request.getParameter("TKL_idactividad"));
                pst.setString(2, request.getParameter("tipo_recursos"));
                pst.setString(3, request.getParameter("importe"));
                pst.setString(4, "A");
                pst.executeUpdate();
                pst.close();
            } catch (SQLException sqle) {
                System.err.println("Error en addDptoRecfinancieros " + sqle);
            }
        }
    }
}
