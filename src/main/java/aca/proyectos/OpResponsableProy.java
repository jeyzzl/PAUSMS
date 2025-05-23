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
public class OpResponsableProy {
	
	Connection c;
	
	public OpResponsableProy(Connection con){
		c=con;
	}

   public List<DptoResponsableProy> ListResponsablesProyecto(int id_proyecto) {
        List<DptoResponsableProy> salida = new ArrayList<DptoResponsableProy>();

        try {
        	
        	PreparedStatement pst = c.prepareStatement("SELECT CLAVE, ID_PROYECTO, CARGO, FECHA_CREACION, ESTADO "
                    + "FROM DANIEL.DPTO_RESPONSABLEPROY WHERE ID_PROYECTO=? AND ESTADO='A'");
        	
            pst.setInt(1, id_proyecto);
            
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                DptoResponsableProy dme;
                dme = new DptoResponsableProy();
                dme.setClave(rs.getString("clave"));
                dme.setId_proyecto(rs.getInt("id_proyecto"));
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

    public List<DptoResponsableProy> ListProyectosResponsable( String clave) {
        List<DptoResponsableProy> salida = new ArrayList<DptoResponsableProy>();

        try {
        	
        	PreparedStatement pst = c.prepareStatement("SELECT CLAVE, ID_PROYECTO, CARGO, FECHA_CREACION, ESTADO "
                    + "FROM DANIEL.DPTO_RESPONSABLEPROY WHERE CLAVE=?");
        	
            pst.setString(1, clave);
            
            ResultSet rs = pst.executeQuery();

	        while (rs.next()) {
	            DptoResponsableProy dme;
	            dme = new DptoResponsableProy();
	            dme.setClave(rs.getString("clave"));
	            dme.setId_proyecto(rs.getInt("id_proyecto"));
	            dme.setCargo(rs.getString("cargo"));
	            dme.setFecha_creacion(rs.getString("fecha_creacion"));
	            dme.setEstado(rs.getString("estado"));
	
	            salida.add(dme);
	        }
	        rs.close();
            pst.close();

        } catch (SQLException sqle) {
            System.err.println("Error en PreparedStatement ListProyectoResponsables " + sqle);
        }
        return salida;
    }

    public void addResponsable(HttpServletRequest request) {
        boolean pasa = true;
        if (request.getParameter("TKL_id_proyecto").equals("")) {
            pasa = false;
        }
        if (request.getParameter("clave").equals("")) {
            pasa = false;
        }
        if (pasa) {
            try {
            	
            	PreparedStatement pst = c.prepareStatement("INSERT INTO DANIEL.DPTO_RESPONSABLEPROY "
                        + "(CLAVE, ID_PROYECTO, CARGO, ESTADO) VALUES(?,?,?,?)");
                PreparedStatement pstb = c.prepareStatement("SELECT * FROM DANIEL.DPTO_RESPONSABLEPROY WHERE CLAVE=? AND ID_PROYECTO=?");
                        
                pstb.setString(1, request.getParameter("clave"));
                pstb.setInt(2, Integer.valueOf(request.getParameter("TKL_id_proyecto")));

                ResultSet rs = pstb.executeQuery();

                if (!rs.next()) {

                    pst.setString(1, request.getParameter("clave"));
                    pst.setInt(2, Integer.valueOf(request.getParameter("TKL_id_proyecto")));
                    pst.setString(3, request.getParameter("cargo"));
                    pst.setString(4, "A");
                    pst.executeUpdate();
                } else {
                    desactivarEmpleado(Integer.valueOf(request.getParameter("TKL_id_proyecto")), request.getParameter("clave"), "A");
                }

                rs.close();
                pstb.close();
                pst.close();

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
                dme.setId(rs.getInt("id"));
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
                    + "         (SELECT EMPLEADO_ID FROM ARON.EMPLEADO_PUESTOS WHERE ID_EJERCICIO=? AND ID_CCOSTO=? AND STATUS='A') AND STATUS='A' order by appaterno, apmaterno");
        	
            pst.setString(1, ejercicio);
            pst.setString(2, ccosto);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                DptoPersonal dme;
                dme = new DptoPersonal();
                dme.setId(rs.getInt("id"));
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

    public List<DptoPersonal> listaEmpleadosGeneral(String ccosto, String ejercicio) {
        List<DptoPersonal> salida = new ArrayList<DptoPersonal>();

        try {
        	
        	PreparedStatement pst = c.prepareStatement("SELECT ID, CLAVE, NOMBRE, "
                    + "APPATERNO, APMATERNO "
                    + "FROM ARON.EMPLEADO "
                    + "WHERE ID NOT IN (SELECT EMPLEADO_ID "
                    + "FROM ARON.EMPLEADO_PUESTOS "
                    + "WHERE ID_EJERCICIO=? "
                    + "AND ID_CCOSTO=? "
                    + "AND STATUS='A') AND STATUS='A' order by APPATERNO, apmaterno");
        	
            pst.setString(1, ejercicio);
            pst.setString(2, ccosto);

            ResultSet rs = pst.executeQuery();
            
            while (rs.next()) {
            
                DptoPersonal dme;
                dme = new DptoPersonal();
                dme.setId(rs.getInt("id"));
                dme.setClave(rs.getString("clave"));
                dme.setNombre(rs.getString("nombre"));
                dme.setAppaterno(rs.getString("appaterno"));
                dme.setApmaterno(rs.getString("apmaterno"));                    
                salida.add(dme);
            }
            rs.close();
            pst.close();

        } catch (SQLException sqle) {
            System.err.println("Error en PreparedStatement listaEmpleadosGeneral " + sqle);
        }
        return salida;
    }

    public void desactivarEmpleado(int id_proyecto, String clave, String estado) {
        try {
        	
        	PreparedStatement pst = c.prepareStatement("UPDATE DANIEL.DPTO_RESPONSABLEPROY "
                    + "SET ESTADO=?, FECHA_CREACION=now() WHERE CLAVE=? AND ID_PROYECTO=?");
        	
            pst.setString(1, estado);
            pst.setString(2, clave);
            pst.setInt(3, id_proyecto);
            pst.executeUpdate();
            pst.close();
        } catch (SQLException sqle) {
            System.err.println("Error en desactivarEmpleado " + sqle);
        }
    }
}
