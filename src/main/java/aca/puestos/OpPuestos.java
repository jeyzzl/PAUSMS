package aca.puestos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;

public class OpPuestos {

	Connection con;

	public OpPuestos(Connection con) {
		this.con = con;
	}

	public int maxID(String table, String id_column) {
		int salida = 0;
		try {
			PreparedStatement pst = con.prepareStatement("SELECT MAX("
					+ id_column + ") as max FROM " + table);
			ResultSet rs = pst.executeQuery();
			if (rs.next()) {
				salida = rs.getInt("max");
			}
			rs.close();
			pst.close();

		} catch (SQLException sqle) {
			System.err.println("error insertFunciones " + sqle);
		}
		
		
		return salida + 1;
	}

	public String getVerbo(int id){
		String salida = "";
		try{
			PreparedStatement pst = con.prepareStatement("SELECT * FROM DANIEL.PUE_CATFUNCIONES WHERE ID=?");
			pst.setInt(1, id);
			
			ResultSet rs = pst.executeQuery();
			
			if(rs.next()){
				salida = rs.getString("verbo");
			}
			rs.close();
			pst.close();
			
		}catch(SQLException sqle){
			System.err.println("error getVerbo " + sqle);
		}
		
		return salida;
	}
	
	public void removePueFuncion(int verbo, int id_seccion, int id_funcion){
		try{
			PreparedStatement pst = con.prepareStatement("delete from daniel.pue_funciones where verbo=? and id_seccion=? and id_funcion=?");
			pst.setInt(1, verbo);
			pst.setInt(2, id_seccion);
			pst.setInt(3, id_funcion);
			pst.executeUpdate();
			
		}catch(SQLException sqle){
			System.err.println("error removeFuncion " + sqle);
		}
	}
	
	public int insertFunciones(HttpServletRequest request) {
		//System.out.println("SI ESTA INTENTANDO METER FUNCIONES !!!!!!!!");
		int salida = 0;
		try {

			int verbo = 0;
			//System.out.println("SI ESTA INTENTANDO METER FUNCIONES !!!!!!!!" + request.getParameter("otro"));
			if (request.getParameter("verbo").equals("00")) {
				if (!request.getParameter("otro").equals("")) {
					verbo = insertCatFunciones(request.getParameter("otro"));
					
				}
			}else{
				verbo = Integer.valueOf(request.getParameter("verbo"));
			}

			PreparedStatement pst = con.prepareStatement("INSERT INTO DANIEL.PUE_FUNCIONES "
							+ "(ID_FUNCION, ID_SECCION, VERBO, DESCRIPCION) VALUES(?,?,?,?)");
			pst.setInt(1, maxID("DANIEL.PUE_FUNCIONES", "ID_FUNCION"));
			pst.setInt(2, Integer.valueOf(request.getParameter("id_seccion")));
			pst.setInt(3, verbo);
			pst.setString(4, request.getParameter("descripcion"));

			if (verbo != 0) {
				salida = pst.executeUpdate();
			}

		} catch (SQLException sqle) {
			System.err.println("error insertFunciones " + sqle);
		}
		return salida;
	}

	public int insertCatFunciones(String verbo) {
		int salida = 0;
		try {
			PreparedStatement pst = con.prepareStatement("INSERT INTO DANIEL.PUE_CATFUNCIONES "
							+ "(ID, VERBO) VALUES(?,?)");

			int maxid = maxID("DANIEL.PUE_CATFUNCIONES", "ID");
			
			pst.setInt(1, maxid);
			pst.setString(2, verbo);

			int resu = pst.executeUpdate();
			if (resu != 0)
				salida = maxid;

		} catch (SQLException sqle) {
			System.err.println("error insertCatFunciones " + sqle);
		}
		return salida;
	}

	public int insertPuesto(HttpServletRequest request) {
		int salida = 0;
		try {
			PreparedStatement pst = con.prepareStatement("INSERT INTO DANIEL.PUESTOS_RH "
							+ "(ID_SECCION, ID_CATEGORIA, OBJETIVO_PUESTO, EXPERIENCIA, EDAD_MAXIMA, SEXO, " +
							"ESTUDIOS, AVANCE_ESTUDIOS) VALUES(?,?,?,?,?,?,?,?)");
			pst.setInt(1, Integer.valueOf(request.getParameter("id_seccion")));
			pst.setInt(2, Integer.valueOf(request.getParameter("id_categoria")));
			pst.setString(3, request.getParameter("objetivo_puesto"));
			pst.setString(4, request.getParameter("experiencia"));
			pst.setString(5, request.getParameter("edad"));
			pst.setString(6, request.getParameter("sexo"));
			pst.setString(7, request.getParameter("estudios"));
			pst.setString(8, request.getParameter("avance_estudios"));

			salida = pst.executeUpdate();

		} catch (SQLException sqle) {
			System.err.println("error insertPuesto " + sqle);
		}
		return salida;
	}

	public List<Seccion> getAronPuestos() {
		List<Seccion> salida = new ArrayList<Seccion>();
		try {
			PreparedStatement pst = con.prepareStatement("SELECT SECCION_ID, DESCRIPCION_NUEVA, CATEGORIA_ID " +
					"FROM ARON.SECCION order by seccion_id");
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				Seccion sec = new Seccion();
				sec.setCategoria_id(rs.getInt("CATEGORIA_ID"));
				sec.setDescripcion_nueva(rs.getString("DESCRIPCION_NUEVA"));
				sec.setSeccion_id(rs.getInt("SECCION_ID"));
				salida.add(sec);
			}
			rs.close();
			pst.close();
		} catch (SQLException sqle) {
			System.err.println("getAronPuestos " + sqle);
		}

		return salida;
	}

	public Seccion aronPuesto(int id) {
		Seccion salida = null;
		try {
			PreparedStatement pst = con.prepareStatement("SELECT SECCION_ID, DESCRIPCION_NUEVA, CATEGORIA_ID " +
					"FROM ARON.SECCION where seccion_id=? order by seccion_id");
			pst.setInt(1, id);
			ResultSet rs = pst.executeQuery();
			if (rs.next()) {
				salida = new Seccion();
				salida.setCategoria_id(rs.getInt("CATEGORIA_ID"));
				salida.setDescripcion_nueva(rs.getString("DESCRIPCION_NUEVA"));
				salida.setSeccion_id(rs.getInt("SECCION_ID"));

			}
			rs.close();
			pst.close();
		} catch (SQLException sqle) {
			System.err.println("aronPuesto " + sqle);
		}

		return salida;
	}

	public Seccion aronPuestob(int id) {
		Seccion salida = null;
		try {
			PreparedStatement pst = con.prepareStatement("SELECT SECCION_ID, DESCRIPCION_NUEVA, CATEGORIA_ID " +
					"FROM ARON.SECCION where id=? order by seccion_id");
			pst.setInt(1, id);
			ResultSet rs = pst.executeQuery();
			if (rs.next()) {
				salida = new Seccion();
				salida.setCategoria_id(rs.getInt("CATEGORIA_ID"));
				salida.setDescripcion_nueva(rs.getString("DESCRIPCION_NUEVA"));
				salida.setSeccion_id(rs.getInt("SECCION_ID"));

			}
			rs.close();
			pst.close();
		} catch (SQLException sqle) {
			System.err.println("aronPuesto " + sqle);
		}

		return salida;
	}

	public List<CatCategoria> lsCategorias() {
		List<CatCategoria> salida = new ArrayList<CatCategoria>();
		try {
			PreparedStatement pst = con.prepareStatement("SELECT ID, NOMBRE FROM ARON.CATEGORIA order by id");
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				CatCategoria cat = new CatCategoria();
				cat.setId(rs.getInt("id"));
				cat.setNombre(rs.getString("nombre"));
				salida.add(cat);
			}
			rs.close();
			pst.close();
		} catch (SQLException sqle) {
			System.err.println("lsCategorias " + sqle);
		}
		return salida;
	}

	public CatCategoria getCategoria(int id) {
		CatCategoria salida = null;
		try {
			PreparedStatement pst = con.prepareStatement("SELECT ID, NOMBRE FROM ARON.CATEGORIA WHERE ID=?");
			pst.setInt(1, id);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				salida = new CatCategoria();
				salida.setId(rs.getInt("id"));
				salida.setNombre(rs.getString("nombre"));
			}
			rs.close();
			pst.close();
		} catch (SQLException sqle) {
			System.err.println("getCategoria " + sqle);
		}
		return salida;
	}

	public List<PuestoRH> puestosRh() {
		List<PuestoRH> salida = new ArrayList<PuestoRH>();
		try {
			PreparedStatement pst = con.prepareStatement("SELECT "
							+ "ID_SECCION,ID_CATEGORIA,PUESTO_SUPERIOR,OBJETIVO_PUESTO,EXPERIENCIA,EDAD_MAXIMA,SEXO,NIVEL_INGLES "
							+ "FROM DANIEL.PUESTOS_RH ");
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				PuestoRH prh = new PuestoRH();
				prh.setId_seccion(rs.getInt("id_seccion"));
				prh.setId_seccion(rs.getInt("id_categoria"));
				prh.setPuesto_superior(rs.getInt("puesto_superior"));
				prh.setObjetivo_puesto(rs.getString("objetivo_puesto"));
				prh.setExperiencia(rs.getString("experiencia"));
				prh.setEdad_maxima(rs.getString("edad_maxima"));
				prh.setSexo(rs.getString("sexo"));
				prh.setNivel_ingles(rs.getString("nivel_ingles"));

				salida.add(prh);

			}
			rs.close();
			pst.close();
		} catch (SQLException sqle) {
			System.err.println("puestosRh " + sqle);
		}

		return salida;
	}

	public PuestoRH getPuestoRh(int id) {
		PuestoRH salida = null;
		try {
			PreparedStatement pst = con.prepareStatement("SELECT "
							+ "ID_SECCION,ID_CATEGORIA,PUESTO_SUPERIOR,OBJETIVO_PUESTO," +
							"EXPERIENCIA,EDAD_MAXIMA,SEXO,NIVEL_INGLES, ESTUDIOS, AVANCE_ESTUDIOS "
							+ "FROM DANIEL.PUESTOS_RH WHERE ID_SECCION=?");
			pst.setInt(1, id);
			ResultSet rs = pst.executeQuery();
			if (rs.next()) {
				salida = new PuestoRH();
				salida.setId_seccion(rs.getInt("id_seccion"));
				salida.setId_categoria(rs.getInt("id_categoria"));
				salida.setPuesto_superior(rs.getInt("puesto_superior"));
				salida.setObjetivo_puesto(rs.getString("objetivo_puesto"));
				salida.setExperiencia(rs.getString("experiencia"));
				salida.setEdad_maxima(rs.getString("edad_maxima"));
				salida.setSexo(rs.getString("sexo"));
				salida.setNivel_ingles(rs.getString("nivel_ingles"));
				salida.setEstudios(rs.getString("estudios"));
				salida.setAvance_estudios(rs.getString("avance_estudios"));

			}
			rs.close();
			pst.close();
		} catch (SQLException sqle) {
			System.err.println("getPuestoRh " + sqle);
		}
		return salida;
	}

	public List<CatFunciones> lsCatFunciones() {
		List<CatFunciones> salida = new ArrayList<CatFunciones>();
		try {
			PreparedStatement pst = con.prepareStatement("SELECT ID, VERBO FROM DANIEL.PUE_CATFUNCIONES ORDER BY VERBO");
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				CatFunciones cf = new CatFunciones();
				cf.setId(rs.getInt("id"));
				cf.setVerbo(rs.getString("verbo"));
				salida.add(cf);
			}
			rs.close();
			pst.close();
		} catch (SQLException sqle) {
			System.err.println("error lsCatFunciones " + sqle);
		}
		return salida;
	}

	public List<PuestoFuncionRH> lsFuncionesSeccion(int id_seccion) {
		List<PuestoFuncionRH> salida = new ArrayList<PuestoFuncionRH>();
		try {
			PreparedStatement pst = con.prepareStatement("SELECT ID_FUNCION, ID_SECCION, VERBO, DESCRIPCION " +
					"FROM DANIEL.PUE_FUNCIONES WHERE ID_SECCION=?");
			pst.setInt(1, id_seccion);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				PuestoFuncionRH cf = new PuestoFuncionRH();
				cf.setId_funcion(rs.getInt("id_funcion"));
				cf.setId_seccion(rs.getInt("id_seccion"));
				cf.setVerbo(rs.getInt("verbo"));
				cf.setDescripcion(rs.getString("descripcion"));
				salida.add(cf);
			}
			rs.close();
			pst.close();
		} catch (SQLException sqle) {
			System.err.println("error lsCatFunciones " + sqle);
		}
		return salida;
	}

	public List<String> puestosDepto(String ccosto, String id_ejercicio) {
		List<String> salida = new ArrayList<String>();
		try {
			PreparedStatement pst = con.prepareStatement("SELECT ID, DESCRIPCION, DESCRIPCION_NUEVA FROM ARON.SECCION WHERE ID IN ("
							+ "select distinct(EP.PUESTO_ID) from ARON.EMPLEADO_PUESTOS EP "
							+ "WHERE EP.ID_EJERCICIO='001-2014' and EP.ID_CCOSTO=?)");
			pst.setString(1, ccosto);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				salida.add(rs.getString("id"));
			}
			rs.close();
			pst.close();
		} catch (SQLException sqle) {
			System.err.println("puestosDepto" + sqle);
		}

		return salida;
	}

	public int actualizaPuesto(HttpServletRequest request) {
		int salida=0;
		try{
			PreparedStatement pst = con.prepareStatement("UPDATE DANIEL.PUESTOS_RH SET " +
					"ID_CATEGORIA=?, OBJETIVO_PUESTO=?,EXPERIENCIA=?," +
					"EDAD_MAXIMA=?, SEXO=?, ESTUDIOS=?, AVANCE_ESTUDIOS=? " +
					"WHERE ID_SECCION=? ");
			
			pst.setInt(1, Integer.valueOf(request.getParameter("id_categoria")));
			pst.setString(2, request.getParameter("objetivo_puesto"));
			pst.setString(3, request.getParameter("experiencia"));
			pst.setString(4, request.getParameter("edad"));
			pst.setString(5, request.getParameter("sexo"));
			pst.setString(6, request.getParameter("estudios"));
			pst.setString(7, request.getParameter("avance_estudios"));
			pst.setInt(8, Integer.valueOf(request.getParameter("id_seccion")));
			
			salida = pst.executeUpdate();
			
		}catch(SQLException sqle){
			System.err.println("actualizaPuesto" + sqle);
		}
		return salida;
	}

}
