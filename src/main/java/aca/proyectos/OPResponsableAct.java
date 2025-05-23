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
public class OPResponsableAct {
	
	Connection c;
	
	public OPResponsableAct(Connection con){
		c=con;
	}

    public List<DptoResponsableAct> ListResponsablesActividad(int id_actividad) {
        List<DptoResponsableAct> salida = new ArrayList<DptoResponsableAct>();

        try {
        	
        	PreparedStatement pst = c.prepareStatement("SELECT CLAVE, ID_ACTIVIDAD, CARGO, FECHA_CREACION, ESTADO "
                    + "FROM DANIEL.DPTO_RESPONSABLEACT WHERE ID_ACTIVIDAD=? AND ESTADO='A'");
        	
            pst.setInt(1, id_actividad);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                DptoResponsableAct dme;
                dme = new DptoResponsableAct();
                dme.setClave(rs.getString("clave"));
                dme.setId_actividad(rs.getInt("id_actividad"));
                dme.setCargo(rs.getString("cargo"));
                dme.setFecha_creacion(rs.getString("fecha_creacion"));
                dme.setEstado(rs.getString("estado"));

                salida.add(dme);
            }
            rs.close();
            pst.close();

        } catch (SQLException sqle) {
            System.err.println("Error en PreparedStatement ListResponsablesActividad " + sqle);
        }
        return salida;
    }

    public List<DptoResponsableAct> ListActividadesResponsable(String clave) {
        List<DptoResponsableAct> salida = new ArrayList<DptoResponsableAct>();

        try {
        	
        	PreparedStatement pst = c.prepareStatement("SELECT CLAVE, ID_ACTIVIDAD, CARGO, FECHA_CREACION, ESTADO "
                    + "FROM DANIEL.DPTO_RESPONSABLEACT WHERE CLAVE=?");
        	
            pst.setString(1, clave);
            
        	ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                DptoResponsableAct dme;
                dme = new DptoResponsableAct();
                dme.setClave(rs.getString("clave"));
                dme.setId_actividad(rs.getInt("id_actividad"));
                dme.setCargo(rs.getString("cargo"));
                dme.setFecha_creacion(rs.getString("fecha_creacion"));
                dme.setEstado(rs.getString("estado"));

                salida.add(dme);
            }
            rs.close();
            pst.close();

        } catch (SQLException sqle) {
            System.err.println("Error en PreparedStatement ListResponsablesActividad " + sqle);
        }
        return salida;
    }

    public void addResponsable(HttpServletRequest request) {
        boolean pasa = true;
        if (request.getParameter("TKL_id_actividad").equals("")) {
            pasa = false;
        }
        if (request.getParameter("clave").equals("")) {
            pasa = false;
        }
        if (pasa) {
            
            try {
            	
            	PreparedStatement pst = c.prepareStatement("INSERT INTO DANIEL.DPTO_RESPONSABLEACT "
                        + "(CLAVE, ID_ACTIVIDAD, CARGO, ESTADO) VALUES(?,?,?,?)"); 
                
            	PreparedStatement pstb = c.prepareStatement("SELECT * FROM DANIEL.DPTO_RESPONSABLEACT WHERE CLAVE=? AND ID_ACTIVIDAD=?");
                        
                pstb.setString(1, request.getParameter("clave"));
                pstb.setInt(2, Integer.valueOf(request.getParameter("TKL_id_actividad")));

                ResultSet rs = pstb.executeQuery();

                if (!rs.next()) {
                pst.setString(1, request.getParameter("clave"));
                pst.setInt(2, Integer.valueOf(request.getParameter("TKL_id_actividad")));
                pst.setString(3, request.getParameter("cargo"));
                pst.setString(4, "A");
                pst.executeUpdate();
                pst.close();
                }else{
                    desactivarEmpleado(Integer.valueOf(request.getParameter("TKL_id_actividad")), request.getParameter("clave"), "A", request.getParameter("cargo"));
                }
            } catch (SQLException sqle) {
                System.err.println("Error en addResponsable " + sqle);
            }
        }
    }

    public DptoPersonal getEmpleado( String clave) {
        DptoPersonal dme = null;

        try {
        	
        	PreparedStatement pst = c.prepareStatement("SELECT ID, CLAVE, NOMBRE, APPATERNO, APMATERNO "
                    + "FROM ARON.EMPLEADO WHERE CLAVE=?");
        	
            pst.setString(1, clave);
            
            ResultSet rs = pst.executeQuery();
            
            while (rs.next()) {
                dme = new DptoPersonal();
                dme.setId(rs.getInt(rs.getInt("id")));
                dme.setClave(rs.getString("clave"));
                dme.setNombre(rs.getString("nombre"));
                dme.setAppaterno(rs.getString("appaterno"));
                dme.setApmaterno(rs.getString("apmaterno"));
            }
            rs.close();
            pst.close();

        } catch (SQLException sqle) {
            System.err.println("Error en PreparedStatement getEmpleado " + sqle);
        }
        return dme;
    }

    public List<DptoPersonal> listaEmpleadosDpto(String ccosto, String ejercicio) {
        List<DptoPersonal> salida = new ArrayList<DptoPersonal>();

        try {
        	
        	PreparedStatement pst = c.prepareStatement("SELECT ID, CLAVE, NOMBRE, APPATERNO, APMATERNO "
                    + "FROM ARON.EMPLEADO WHERE ID IN "
                    + "         (SELECT EMPLEADO_ID FROM ARON.EMPLEADO_PUESTOS WHERE ID_EJERCICIO=? AND ID_CCOSTO=? AND STATUS='A')");
        	
            pst.setString(1, ccosto);
            
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                DptoPersonal dme;
                dme = new DptoPersonal();
                dme.setId(rs.getInt(rs.getInt("id")));
                dme.setClave(rs.getString("clave"));
                dme.setNombre(rs.getString("nombre"));
                dme.setAppaterno(rs.getString("appaterno"));
                dme.setApmaterno(rs.getString("apmaterno"));
                salida.add(dme);
            }
            rs.close();

            pst.close();

        } catch (SQLException sqle) {
            System.err.println("Error en PreparedStatement listaEmpleadosDpto " + sqle);
        }
        return salida;
    }

    public void desactivarEmpleado( int id_actividad, String clave, String estado, String Cargo) {
        try {
        	
        	PreparedStatement pst = c.prepareStatement("UPDATE DANIEL.DPTO_RESPONSABLEACT "
                    + "SET ESTADO=?,CARGO=?, FECHA_CREACION=now() WHERE CLAVE=? AND ID_ACTIVIDAD=?");
        	
            pst.setString(1, estado);
            pst.setString(2, Cargo);
            pst.setString(3, clave);
            pst.setInt(4, id_actividad);
            pst.executeUpdate();
            pst.close();
        } catch (SQLException sqle) {
            System.err.println("Error en desactivarEmpleado " + sqle);
        }
    }
}
