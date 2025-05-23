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
public class OPActividad {
	
	private String error;

	Connection c;

	public OPActividad(Connection con) {
		c = con;
	}

	public DptoActividades getActividad(int id) {
		DptoActividades dme = null;
		try {

			PreparedStatement pst = c
					.prepareStatement("SELECT ID, ID_PROYECTO, to_char(FECHA_INICIO,'dd-mm-yyyy') as FECHA_INICIO, to_char(FECHA_FINAL,'dd-mm-yyyy') as FECHA_FINAL, DESCRIPCION, ESTADO, FECHA_CREACION "
							+ "FROM DANIEL.DPTO_ACTIVIDADES WHERE ID=?");

			pst.setInt(1, id);
			ResultSet rs = pst.executeQuery();
			if (rs.next()) {
				dme = new DptoActividades();
				dme.setId(rs.getInt("id"));
				dme.setId_proyecto(rs.getInt("id_proyecto"));
				dme.setFecha_inicio(rs.getString("fecha_inicio"));
				dme.setFecha_final(rs.getString("fecha_final"));
				dme.setDescripcion(rs.getString("descripcion"));
				dme.setEstado(rs.getString("estado"));
				dme.setFecha_creacion(rs.getString("fecha_creacion"));
			}
			rs.close();
			pst.close();
		} catch (SQLException sqle) {
			System.err.println("Error en PreparedStatement getActividad "
					+ sqle);
		}
		return dme;
	}

	public List<DptoActividades> listActividades(int id_proyecto) {
		List<DptoActividades> salida = new ArrayList<DptoActividades>();

		try {

			PreparedStatement pst = c
					.prepareStatement("SELECT ID, ID_PROYECTO, to_char(FECHA_INICIO,'dd-mm-yyyy') as FECHA_INICIO, to_char(FECHA_FINAL,'dd-mm-yyyy') as FECHA_FINAL, DESCRIPCION, ESTADO, FECHA_CREACION "
							+ "FROM DANIEL.DPTO_ACTIVIDADES WHERE ID_PROYECTO=? AND ESTADO='A'");

			pst.setInt(1, id_proyecto);
			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				DptoActividades dme;
				dme = new DptoActividades();
				dme.setId(rs.getInt("id"));
				dme.setId_proyecto(rs.getInt("id_proyecto"));
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
			System.err.println("Error en PreparedStatement listActividades "
					+ sqle);
		}
		return salida;
	}
	
	public List<DptoActividades> listempActividades(String claveempleado) {
		List<DptoActividades> salida = new ArrayList<DptoActividades>();

		try {

			PreparedStatement pst = c
					.prepareStatement("SELECT ID, ID_PROYECTO, to_char(FECHA_INICIO,'dd-mm-yyyy') as FECHA_INICIO, to_char(FECHA_FINAL,'dd-mm-yyyy') as FECHA_FINAL, DESCRIPCION, ESTADO, FECHA_CREACION "
							+ "FROM DANIEL.DPTO_ACTIVIDADES WHERE ID IN (SELECT ID_ACTIVIDAD FROM DANIEL.DPTO_RESPONSABLEACT WHERE CLAVE=? AND ESTADO='A') AND ESTADO='A' AND ID_PROYECTO IN (SELECT ID FROM DANIEL.EMP_LOGROS WHERE ESTADO='A')");

			pst.setString(1, claveempleado);
			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				DptoActividades dme;
				dme = new DptoActividades();
				dme.setId(rs.getInt("id"));
				dme.setId_proyecto(rs.getInt("id_proyecto"));
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
			System.err.println("Error en PreparedStatement listempActividades "
					+ sqle);
		}
		return salida;
	}

	public List<DptoActividades> listActividadesxResponsable(String responsable) {
		List<DptoActividades> salida = new ArrayList<DptoActividades>();
		try {

			PreparedStatement pst = c
					.prepareStatement("SELECT ID, ID_PROYECTO, TO_CHAR(FECHA_INICIO, 'DD/MM/YYYY') AS FECHA_INICIO , TO_CHAR(FECHA_FINAL, 'DD/MM/YYYY') AS FECHA_FINAL, DESCRIPCION, ESTADO, FECHA_CREACION "
							+ "FROM DANIEL.DPTO_ACTIVIDADES WHERE ID IN (SELECT ID_ACTIVIDAD FROM DANIEL.DPTO_RESPONSABLEACT WHERE CLAVE=?)");

			pst.setString(1, responsable);
			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				DptoActividades dme;
				dme = new DptoActividades();
				dme.setId(rs.getInt("id"));
				dme.setId_proyecto(rs.getInt("id_proyecto"));
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
			System.err.println("Error en PreparedStatement listProyecto "
					+ sqle);
		}
		return salida;
	}

	public int addDptoActividad(HttpServletRequest request) {
		int salida = 0;
		boolean pasa = true;
		if (request.getParameter("logroid").equals("")) {
			pasa = false;
		}
		if (request.getParameter("descripcion").equals("")) {
			pasa = false;
		}
		if (request.getParameter("fecha_inicio").equals("")) {
			pasa = false;
		}
		if (request.getParameter("fecha_final").equals("")) {
			pasa = false;
		}
		if (pasa) {
			try {

				PreparedStatement pst = c
						.prepareStatement("INSERT INTO DANIEL.DPTO_ACTIVIDADES "
								+ "(ID_PROYECTO, FECHA_INICIO, FECHA_FINAL, DESCRIPCION, ESTADO) VALUES(?, TO_DATE(?, 'DD/MM/YYYY'), TO_DATE(?, 'DD/MM/YYYY'), ?, ?)");

				pst.setInt(1,
						Integer.valueOf(request.getParameter("logroid")));
				pst.setString(2, request.getParameter("fecha_inicio"));
				pst.setString(3, request.getParameter("fecha_final"));
				pst.setString(4, request.getParameter("descripcion"));
				pst.setString(5, "A");
				salida = pst.executeUpdate();
				pst.close();
			} catch (SQLException sqle) {
				System.err.println("Error en addDptoActividad " + sqle);
				setError(sqle.toString());
			}
		}else{
			setError("Falta completar los campos");
		}
		return salida;
	}
	
	public void eliminaActividad(int idactividad) {
		boolean pasa = true;
	
		if (pasa) {
			try {

				PreparedStatement pst = c
						.prepareStatement("UPDATE DANIEL.DPTO_ACTIVIDADES SET "
								+ "FECHA_CREACION=now(),ESTADO='I' WHERE ID=?");

				PreparedStatement pstb=c.prepareCall("UPDATE DANIEL.DPTO_RESPONSABLEACT SET FECHA_CREACION=now(), ESTADO='I' WHERE ID_ACTIVIDAD=?");
				
				pstb.setInt(1, idactividad);
				pstb.executeUpdate();
				
				pst.setInt(1, idactividad);
				pst.executeUpdate();
				
				
				pst.close();
				pstb.close();
			} catch (SQLException sqle) {
				System.err.println("Error en eliminaActividad " + sqle);
			}
		}
	}

	public void cambiaActividad(HttpServletRequest request) {
		boolean pasa = true;
		if (request.getParameter("id_meta").equals("")) {
			pasa = false;
		}
		if (request.getParameter("descripcion").equals("")) {
			pasa = false;
		}
		if (pasa) {
			try {

				PreparedStatement pst = c
						.prepareStatement("UPDATE DANIEL.DPTO_ACTIVIDADES SET "
								+ "FECHA_INICIO=?, FECHA_FINAL=?, DESCRIPCION=?, ESTADO=? FECHA_CREACION=now() WHERE ID=?");

				pst.setString(1, request.getParameter("fecha_inicio"));
				pst.setString(2, request.getParameter("fecha_final"));
				pst.setString(3, request.getParameter("descripcion"));
				pst.setString(4, "A");
				pst.setInt(5, Integer.valueOf((String) request.getParameter("id")));
				pst.executeUpdate();
				pst.close();
			} catch (SQLException sqle) {
				System.err.println("Error en cambiaProyectos " + sqle);
			}
		}
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getpProyecto(int id_Proyecto) {
		String salida = "";
		OPProyectos op = new OPProyectos(c);
		DptoProyectos dpm = op.getProyecto(id_Proyecto);

		if (dpm != null) {
			salida = dpm.getDescripcion();
		}

		return salida;
	}
}
