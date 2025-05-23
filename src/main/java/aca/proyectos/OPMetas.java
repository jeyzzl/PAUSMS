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
public class OPMetas {
	
	Connection c;

	public OPMetas(Connection con){
		c=con;
	}
	
	
	public List<InstMetas> getInsMetas(int ciclo){
		ArrayList<InstMetas> salida = new ArrayList<InstMetas>();
		try{
			PreparedStatement pst = c.prepareStatement("SELECT ID, ID_CICLO, DESCRIPCION, COMENTARIO FROM DANIEL.INST_META " +
					"WHERE ID_CICLO=? AND VISIBLE='S'");
			pst.setInt(1, ciclo);
			ResultSet rs = pst.executeQuery();
			while(rs.next()){
				InstMetas im = new InstMetas();
				im.setId(rs.getInt("id"));
				im.setId_ciclo(rs.getInt("id_ciclo"));
				im.setDescripcion(rs.getString("descripcion"));
				im.setComentario(rs.getString("comentario"));
				salida.add(im);
			}
			rs.close();
			pst.close();
			
		}catch(SQLException sqle ){
			System.err.println("Error en OPMetas.getInsMetas " + sqle);
		}
		
		return salida;
	}
	
	public InstMetas getInsMeta(int id){
		
		InstMetas salida = new InstMetas();
		try{
			PreparedStatement pst = c.prepareStatement("SELECT ID, ID_CICLO, DESCRIPCION, COMENTARIO FROM DANIEL.INST_META WHERE ID=?");
			pst.setInt(1, id);
			ResultSet rs = pst.executeQuery();
			
			if(rs.next()){
				
				salida = new InstMetas();
				salida.setId(rs.getInt("id"));
				salida.setId_ciclo(rs.getInt("id_ciclo"));
				salida.setDescripcion(rs.getString("descripcion"));
				salida.setComentario(rs.getString("comentario"));
				
			}
			rs.close();
			pst.close();
			
			
			
		}catch(SQLException sqle ){
			System.err.println("Error en  OPMetas.getInsMeta " + sqle);
		}
		
		return salida;
	}
	
    public DptoMetas getMeta(int id) {
        DptoMetas dme = null;
        try {
        	
        	PreparedStatement pst = c.prepareStatement("SELECT ID,ID_OBJETIVO,DESCRIPCION,COMENTARIO, ESTADO,FECHA_CREACION "
                    + "FROM DANIEL.DPTO_METAS WHERE ID=?");
        	
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                OPObjetivos op = new OPObjetivos(c);
                dme = new DptoMetas();
                dme.setId(rs.getInt("id"));
                dme.setId_objetivo(rs.getInt("id_objetivo"));
                dme.setDescripcion(rs.getString("descripcion"));
                dme.setEstado(rs.getString("estado"));
                dme.setFecha_creacion(rs.getString("fecha_creacion"));
                DptoObjetivos dpo = op.getObjetivo(dme.getId_objetivo());
                dme.setDescripcion_objetivo(dpo.getDescripcion());
            }
            rs.close();

            pst.close();
        } catch (SQLException sqle) {
            System.err.println("Error en PreparedStatement getMeta " + sqle);
        }
        return dme;
    }

    public List<DptoMetas> listMetas(int id_objetivo) {
        List<DptoMetas> salida = new ArrayList<DptoMetas>();

        try {
        	
        	PreparedStatement pst = c.prepareStatement("SELECT ID,ID_OBJETIVO,DESCRIPCION,COMENTARIO,ESTADO,FECHA_CREACION "
                    + "FROM DANIEL.DPTO_METAS WHERE ID_OBJETIVO=? ORDER BY ID ASC");
        	
            pst.setInt(1, id_objetivo);
            ResultSet rs = pst.executeQuery();
            OPObjetivos op = new OPObjetivos(c);
            while (rs.next()) {
                DptoMetas dme;
                dme = new DptoMetas();
                dme.setId(rs.getInt("id"));
                dme.setId_objetivo(rs.getInt("id_objetivo"));
                dme.setDescripcion(rs.getString("descripcion"));
                dme.setEstado(rs.getString("estado"));
                dme.setFecha_creacion(rs.getString("fecha_creacion"));
                DptoObjetivos dpo = op.getObjetivo(id_objetivo);
                dme.setDescripcion_objetivo(dpo.getDescripcion());
                salida.add(dme);
            }
            rs.close();
            pst.close();

        } catch (SQLException sqle) {
            System.err.println("Error en PreparedStatement listMetas " + sqle);
        }
        return salida;
    }

    public void addDptoMetas(HttpServletRequest request) {
        boolean pasa = true;
        if (request.getParameter("id_objetivo").equals("")) {
            pasa = false;
        }
        if (request.getParameter("descripcion").equals("")) {
            pasa = false;
        }
        if (pasa) {
            try {
            	
            	PreparedStatement pst = c.prepareStatement("INSERT INTO DANIEL.DPTO_METAS "
                        + "(ID_OBJETIVO,DESCRIPCION,ESTADO) VALUES(?,?,?)");
            	
                pst.setInt(1, Integer.valueOf(request.getParameter("id_objetivo")));
                pst.setString(2, request.getParameter("descripcion"));
                pst.setString(3, "A");
                pst.executeUpdate();
                pst.close();
            } catch (SQLException sqle) {
                System.err.println("Error en addDptoMetas " + sqle);
            }
        }
    }

    public void cambiaMetas( HttpServletRequest request) {
        boolean pasa = true;
        if (request.getParameter("id_objetivo").equals("")) {
            pasa = false;
        }
        if (request.getParameter("descripcion").equals("")) {
            pasa = false;
        }
        if (pasa) {
            try {
            	
            	PreparedStatement pst = c.prepareStatement("UPDATE DANIEL.DPTO_METAS SET "
                        + "DESCRIPCION=?, FECHA_CREACION=now() WHERE ID=?");
            	
                pst.setString(1, request.getParameter("descripcion"));
                pst.setInt(2, Integer.valueOf(request.getParameter("id")));
                pst.executeUpdate();
                pst.close();
            } catch (SQLException sqle) {
                System.err.println("Error en cambiaObjetivo " + sqle);
            }
        }
    }

    public String getObjetivo(int id_objetivo) {
        String salida = "";
        OPObjetivos op = new OPObjetivos(c);
        DptoObjetivos dpo = op.getObjetivo(id_objetivo);

        if (dpo != null) {
            salida = dpo.getDescripcion();
        }

        return salida;
    }

}
