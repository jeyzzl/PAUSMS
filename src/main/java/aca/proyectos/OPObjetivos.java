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
public class OPObjetivos {
	
	Connection c;
	
	public OPObjetivos(Connection con){
		c=con;
	}
       
    public DptoObjetivos getObjetivo(int id) {
        DptoObjetivos dob = null;
        try {
        	
        	PreparedStatement pst = c.prepareStatement("SELECT ID, ID_CCOSTO,ID_METAINST,DESCRIPCION, FECHA_CREADO "
                    + "FROM DANIEL.DPTO_OBJETIVOS WHERE ID=?");
        	
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
                if (rs.next()) {
                    dob = new DptoObjetivos();
                    dob.setId(rs.getInt("id"));
                    dob.setId_ccosto(rs.getString("id_ccosto"));
                    dob.setId_metainst(rs.getInt("id_metainst"));
                    dob.setDescripcion(rs.getString("descripcion"));
                    dob.setFecha_creado(rs.getString("fecha_creado"));
                    
                }
                rs.close();
            
            pst.close();
        } catch (SQLException sqle) {
            System.err.println("Error en PreparedStatement getObjetivo " + sqle);
        }
        return dob;
    }
    
    public List<DptoObjetivos> listObjetivos( String ccosto) {
        List<DptoObjetivos> salida = new ArrayList<DptoObjetivos>();
        
        try {
        	
        	PreparedStatement pst = c.prepareStatement("SELECT ID, ID_CCOSTO,ID_METAINST,DESCRIPCION, FECHA_CREADO "
                    + "FROM DANIEL.DPTO_OBJETIVOS WHERE ID_CCOSTO=? order by polo,id");
        	
            pst.setString(1, ccosto);
            ResultSet rs = pst.executeQuery();
                while (rs.next()) {
                    DptoObjetivos dob = new DptoObjetivos();
                    dob.setId(rs.getInt("id"));
                    dob.setId_ccosto(rs.getString("id_ccosto"));
                    dob.setId_metainst(rs.getInt("id_metainst"));
                    dob.setDescripcion(rs.getString("descripcion"));
                    dob.setFecha_creado(rs.getString("fecha_creado"));
                    salida.add(dob);
                }
                rs.close();
            
            pst.close();
        } catch (SQLException sqle) {
            System.err.println("Error en PreparedStatement listObjetivos " + sqle);
        }
        return salida;
    }
    
    public void addDptoObjetivo( HttpServletRequest request) {
        boolean pasa = true;
        if (request.getParameter("id_ccosto").equals("")) {
            pasa = false;
        }
        if (request.getParameter("descripcion").equals("")) {
            pasa = false;
        }
        if (pasa) {
            try {
            	
            	PreparedStatement pst = c.prepareStatement("INSERT INTO DANIEL.DPTO_OBJETIVOS "
                        + "(ID_CCOSTO, DESCRIPCION) VALUES(?,?)");
            	
                pst.setString(1, request.getParameter("id_ccosto"));
                pst.setString(2, request.getParameter("descripcion"));
                pst.executeUpdate();
                pst.close();
            } catch (SQLException sqle) {
                System.err.println("Error en addDptoObjetivo " + sqle);
            }
        }
    }
    
    public void cambiaObjetivo(HttpServletRequest request) {
        boolean pasa = true;
        if (request.getParameter("id_ccosto").equals("")) {
            pasa = false;
        }
        if (request.getParameter("descripcion").equals("")) {
            pasa = false;
        }
        if (pasa) {
            try {
            	
            	PreparedStatement pst = c.prepareStatement("UPDATE DANIEL.DPTO_OBJETIVOS SET "
                        + "DESCRIPCION=?, FECHA_CREADO=now() WHERE ID=?");
            	
                pst.setString(1, request.getParameter("descripcion"));
                pst.setInt(2, Integer.valueOf(request.getParameter("id")));
                pst.executeUpdate();
                pst.close();
            } catch (SQLException sqle) {
                System.err.println("Error en cambiaObjetivo " + sqle);
            }
        }
    }
}
