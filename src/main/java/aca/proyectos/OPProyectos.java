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
public class OPProyectos {
	Connection c;
	public OPProyectos(Connection con){
		c=con;
	}
                    
    public DptoProyectos getProyecto(int id) {
        DptoProyectos dme = null;
        try {
        	
        	PreparedStatement pst = c.prepareStatement("SELECT ID, ID_META, FECHA_INICIO, FECHA_FINAL, DESCRIPCION, ESTADO, FECHA_CREACION "
                    + "FROM DANIEL.DPTO_PROYECTOS WHERE ID=?");
        	
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
                if (rs.next()) {
                    
                    dme = new DptoProyectos();
                    dme.setId(rs.getInt("id"));
                    dme.setId_meta(rs.getInt("id_meta"));
                    dme.setFecha_inicio(rs.getString("fecha_inicio"));
                    dme.setFecha_final(rs.getString("fecha_final"));
                    dme.setDescripcion(rs.getString("descripcion"));
                    dme.setEstado(rs.getString("estado"));
                    dme.setFecha_creacion(rs.getString("fecha_creacion"));
                    
                    
                }
                rs.close();
            pst.close();
        } catch (SQLException sqle) {
            System.err.println("Error en PreparedStatement getProyecto " + sqle);
        }
        return dme;
    }
    
    public List<DptoProyectos> listProyecto(int id_meta) {
        List<DptoProyectos> salida = new ArrayList<DptoProyectos>();
        
        try {
        	
        	PreparedStatement pst = c.prepareStatement("SELECT ID, ID_META,to_char(FECHA_INICIO,'dd-mm-yyyy') "
                    + "as FECHA_INICIO, to_char(FECHA_FINAL,'dd-mm-yyyy') as FECHA_FINAL, DESCRIPCION, ESTADO, FECHA_CREACION "
                    + "FROM DANIEL.DPTO_PROYECTOS WHERE ID_META=?");
        	
            pst.setInt(1, id_meta);
            ResultSet rs = pst.executeQuery();
                
                while (rs.next()) {
                    DptoProyectos dme ;
                    dme = new DptoProyectos();
                    dme.setId(rs.getInt("id"));
                    dme.setId_meta(rs.getInt("id_meta"));
                    dme.setFecha_inicio(rs.getString("fecha_inicio"));
                    dme.setFecha_final(rs.getString("fecha_final"));
                    dme.setDescripcion(rs.getString("descripcion"));
                    dme.setEstado(rs.getString("estado"));
                    dme.setFecha_creacion(rs.getString("fecha_creacion"));
                    
                    salida.add(dme);
                }
                rs.close();
            pst.close();
            
            
        } catch (SQLException sqle) {
            System.err.println("Error en PreparedStatement listProyecto " + sqle);
        }
        return salida;
    }
    
    public void addDptoProyectos(HttpServletRequest request) {
        boolean pasa = true;
        if (request.getParameter("TKL_id_meta").equals("")) {
            pasa = false;
        }
        if (request.getParameter("descripcion").equals("")) {
            pasa = false;
        }
        if (pasa) {
            try {
            	
            	PreparedStatement pst = c.prepareStatement("INSERT INTO DANIEL.DPTO_PROYECTOS "
                        + "(ID_META, FECHA_INICIO, FECHA_FINAL, DESCRIPCION, ESTADO) VALUES(?,to_date(?,'dd-mm-yyyy'),to_date(?,'dd-mm-yyyy'),?,?)");
            	
                pst.setInt(1, Integer.valueOf(request.getParameter("TKL_id_meta")));
                pst.setString(2, request.getParameter("fecha_inicio"));
                pst.setString(3, request.getParameter("fecha_final"));
                pst.setString(4, request.getParameter("descripcion"));
                pst.setString(5, "A");
                pst.executeUpdate();
                pst.close();
            } catch (SQLException sqle) {
                System.err.println("Error en addDptoProyectos " + sqle);
            }
        }
    }
    
    public void cambiaProyectos(HttpServletRequest request) {
        boolean pasa = true;
        if (request.getParameter("TKL_id_meta").equals("")) {
            pasa = false;
        }
        if (request.getParameter("descripcion").equals("")) {
            pasa = false;
        }
        if (pasa) {
            try {
            	
            	PreparedStatement pst = c.prepareStatement("UPDATE DANIEL.DPTO_PROYECTOS SET "
                        + "FECHA_INICIO=?, FECHA_FINAL=?, DESCRIPCION=?, ESTADO=? FECHA_CREACION=now() WHERE ID=?");
            	
                pst.setString(1, request.getParameter("fecha_inicio"));
                pst.setString(2, request.getParameter("fecha_final"));
                pst.setString(3, request.getParameter("descripcion"));
                pst.setString(4, "A");
                pst.setInt(5, Integer.valueOf((String)request.getParameter("id")));
                pst.executeUpdate();
                pst.close();
            } catch (SQLException sqle) {
                System.err.println("Error en cambiaProyectos " + sqle);
            }
        }
    }
    
    public String getMeta(int id_meta){
        String salida ="";
        OPMetas op = new OPMetas(c);
        DptoMetas dpm = op.getMeta(id_meta);
        
        if(dpm!=null)
            salida=dpm.getDescripcion();
        
        
        return salida;
    }
}
