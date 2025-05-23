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
public class OPInforme {
	Connection c;
	public OPInforme(Connection con){
		c= con;
	}
	

    public void addInforme(HttpServletRequest request) {
        boolean pasa = true;
        if (request.getParameter("TKL_idactividad").equals("")) {
            pasa = false;
        }
        if (request.getParameter("TKL_clave").equals("")) {
            pasa = false;
        }
        if (request.getParameter("detalle").equals("")) {
            pasa = false;
        }

        if (pasa) {
            try {
            	
            	PreparedStatement pst = c.prepareStatement("INSERT INTO DANIEL.DPTO_INFORMES "
                        + "(ID_ACTIVIDAD,TIPO_INFORME, DETALLE, PORCENTAJE_AVANCE, ESTADO, CLAVE) VALUES(?,?,?,?,?,?)");
            	
                pst.setInt(1, Integer.valueOf(request.getParameter("TKL_idactividad")));
                pst.setString(2, request.getParameter("tipo_informe"));
                pst.setString(3, request.getParameter("detalle"));
                pst.setInt(4, Integer.valueOf(request.getParameter("porcentaje_avance")));
                pst.setString(5, "SIN REVISAR");
                pst.setString(6, request.getParameter("TKL_clave"));

                pst.executeUpdate();
                pst.close();
            } catch (SQLException sqle) {
                System.err.println("Error en addInforme " + sqle);
            }
        }

    }

    public void revisaInforme(HttpServletRequest request) {
        boolean pasa = true;
        if (request.getParameter("TKL_idactividad").equals("")) {
            pasa = false;
        }
        if (request.getParameter("TKL_clave").equals("")) {
            pasa = false;
        }
        if (request.getParameter("descripcion").equals("")) {
            pasa = false;
        }

        if (pasa) {
            try {
            	
            	PreparedStatement pst = c.prepareStatement("UPDATE DANIEL.DPTO_INFORMES "
                        + "SET FECHA_REVISADO=now(), CLAVE_REVISO=?, ESTADO=? WHERE ID=?");

                pst.setString(1, request.getParameter("TKL_clavereviso"));
                pst.setString(2, request.getParameter("estado"));
                pst.setInt(3, Integer.valueOf(request.getParameter("id_informe")));

                pst.executeUpdate();
                pst.close();
            } catch (SQLException sqle) {
                System.err.println("Error en revisaInforme " + sqle);
            }
        }

    }
    
    public List<DptoInformes> listInformes(int id_actividad) {
        List<DptoInformes> salida = new ArrayList<DptoInformes>();

        try {
        	
        	PreparedStatement pst = c.prepareStatement("SELECT ID, ID_ACTIVIDAD, to_char(FECHA_CREADO,'dd/mm/yyyy') as FECHA_CREADO, TIPO_INFORME, DETALLE, PORCENTAJE_AVANCE, CLAVE_REVISO, FECHA_REVISADO, ESTADO, CLAVE FROM DANIEL.DPTO_INFORMES WHERE ID_ACTIVIDAD=?");
        	
            pst.setInt(1, id_actividad);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                DptoInformes dme;
                dme = new DptoInformes();
                dme.setId(rs.getInt("id"));
                dme.setId_actividad(rs.getInt("id_actividad"));
                dme.setFecha_creado(rs.getString("fecha_creado"));
                dme.setTipo_informe(rs.getString("tipo_informe"));
                dme.setDetalle(rs.getString("detalle"));
                dme.setPorcentaje_avance(rs.getInt("porcentaje_avance"));
                dme.setEstado(rs.getString("estado"));
                dme.setClave(rs.getString("clave"));

                salida.add(dme);
            }
            rs.close();
            pst.close();

        } catch (SQLException sqle) {
            System.err.println("Error en PreparedStatement infNoRevisadosXActividad " + sqle);
        }
        return salida;
    }

    public List<DptoInformes> infNoRevisadosXActividad( int id_actividad) {
        List<DptoInformes> salida = new ArrayList<DptoInformes>();

        try {
        	
        	PreparedStatement pst = c.prepareStatement("SELECT * FROM DANIEL.DPTO_INFORMES WHERE ID_ACTIVIDAD=? AND FECHA_REVISADO IS NULL");
        	
            pst.setInt(1, id_actividad);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                DptoInformes dme;
                dme = new DptoInformes();
                dme.setId(rs.getInt("id"));
                dme.setId_actividad(rs.getInt("id_actividad"));
                dme.setFecha_creado(rs.getString("fecha_creado"));
                dme.setTipo_informe(rs.getString("tipo_informe"));
                dme.setDetalle(rs.getString("detalle"));
                dme.setPorcentaje_avance(rs.getInt("porcentaje_avance"));
                dme.setEstado(rs.getString("estado"));
                dme.setClave(rs.getString("clave"));

                salida.add(dme);
            }
            rs.close();
            pst.close();

        } catch (SQLException sqle) {
            System.err.println("Error en PreparedStatement infNoRevisadosXActividad " + sqle);
        }
        return salida;
    }

    public List<DptoInformes> infNoRevisadosActividadEmpleado( int id_actividad, String claveempleado) {
        List<DptoInformes> salida = new ArrayList<DptoInformes>();

        try {
        	
        	PreparedStatement pst = c.prepareStatement("SELECT * FROM DANIEL.DPTO_INFORMES WHERE ID_ACTIVIDAD=? AND CLAVE=? AND FECHA_REVISADO IS NULL");
        	
            pst.setInt(1, id_actividad);
            pst.setString(2, claveempleado);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                DptoInformes dme;
                dme = new DptoInformes();
                dme.setId(rs.getInt("id"));
                dme.setId_actividad(rs.getInt("id_actividad"));
                dme.setFecha_creado(rs.getString("fecha_creado"));
                dme.setTipo_informe(rs.getString("tipo_informe"));
                dme.setDetalle(rs.getString("detalle"));
                dme.setPorcentaje_avance(rs.getInt("porcentaje_avance"));
                dme.setEstado(rs.getString("estado"));
                dme.setClave(rs.getString("clave"));

                salida.add(dme);
            }
            rs.close();
            pst.close();

        } catch (SQLException sqle) {
            System.err.println("Error en PreparedStatement infNoRevisadosActividadEmpleado " + sqle);
        }
        return salida;
    }

    public List<DptoInformes> infRevisadosXActividad( int id_actividad) {
        List<DptoInformes> salida = new ArrayList<DptoInformes>();

        try {
        	
        	PreparedStatement pst = c.prepareStatement("SELECT * FROM DANIEL.DPTO_INFORMES WHERE ID_ACTIVIDAD=? AND FECHA_REVISADO IS NOT NULL");
        	
            pst.setInt(1, id_actividad);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                DptoInformes dme;
                dme = new DptoInformes();
                dme.setId(rs.getInt("id"));
                dme.setId_actividad(rs.getInt("id_actividad"));
                dme.setFecha_creado(rs.getString("fecha_creado"));
                dme.setTipo_informe(rs.getString("tipo_informe"));
                dme.setDetalle(rs.getString("detalle"));
                dme.setPorcentaje_avance(rs.getInt("porcentaje_avance"));
                dme.setClave_reviso(rs.getString("clave_reviso"));
                dme.setFecha_revisado(rs.getString("fecha_revisado"));
                dme.setEstado(rs.getString("estado"));
                dme.setClave(rs.getString("clave"));

                salida.add(dme);
            }
            rs.close();
            pst.close();

        } catch (SQLException sqle) {
            System.err.println("Error en PreparedStatement infRevisadosXActividad " + sqle);
        }
        return salida;
    }

    public List<DptoInformes> infRevisadosActividadEmpleado(int id_actividad, String claveempleado) {
        List<DptoInformes> salida = new ArrayList<DptoInformes>();

        try {
        	
        	PreparedStatement pst = c.prepareStatement("SELECT * FROM DANIEL.DPTO_INFORMES WHERE ID_ACTIVIDAD=? AND CLAVE=? AND FECHA_REVISADO IS NOT NULL");
        	
            pst.setInt(1, id_actividad);
            pst.setString(2, claveempleado);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                DptoInformes dme;
                dme = new DptoInformes();
                dme.setId(rs.getInt("id"));
                dme.setId_actividad(rs.getInt("id_actividad"));
                dme.setFecha_creado(rs.getString("fecha_creado"));
                dme.setTipo_informe(rs.getString("tipo_informe"));
                dme.setDetalle(rs.getString("detalle"));
                dme.setPorcentaje_avance(rs.getInt("porcentaje_avance"));
                dme.setClave_reviso(rs.getString("clave_reviso"));
                dme.setFecha_revisado(rs.getString("fecha_revisado"));
                dme.setEstado(rs.getString("estado"));
                dme.setClave(rs.getString("clave"));

                salida.add(dme);
            }
            rs.close();
            pst.close();

        } catch (SQLException sqle) {
            System.err.println("Error en PreparedStatement infRevisadosActividadEmpleado " + sqle);
        }
        return salida;
    }

    public DptoInformes getInforme( int id_informe) {
        DptoInformes salida = null;
        try {
        	
        	PreparedStatement pst = c.prepareStatement("SELECT * "
                    + "FROM DANIEL.DPTO_INFORMES WHERE ID=?");
        	
            pst.setInt(1, id_informe);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                salida = new DptoInformes();
                salida.setId(rs.getInt("id"));
                salida.setId_actividad(rs.getInt("id_actividad"));
                salida.setFecha_creado(rs.getString("fecha_creado"));
                salida.setTipo_informe(rs.getString("tipo_informe"));
                salida.setDetalle(rs.getString("detalle"));
                salida.setPorcentaje_avance(rs.getInt("porcentaje_avance"));
                salida.setClave_reviso(rs.getString("clave_reviso"));
                salida.setFecha_revisado(rs.getString("fecha_revisado"));
                salida.setEstado(rs.getString("estado"));
                salida.setClave(rs.getString("clave"));
            }
            rs.close();

            pst.close();
        } catch (SQLException sqle) {
            System.err.println("Error en PreparedStatement getInforme " + sqle);
        }
        return salida;
    }

}
