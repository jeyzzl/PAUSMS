package aca.vista.spring;

import java.util.ArrayList;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import aca.util.Fecha;
import java.util.HashMap;
import java.util.List;;

@Component
public class InscritosDao {

	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;

	public Inscritos mapeaRegId(String codigoPersonal) {

		Inscritos inscrito = new Inscritos();

		try {
			String comando = "SELECT "
					+ " CARGA_ID,BLOQUE_ID,CODIGO_PERSONAL,NOMBRE,APELLIDO_PATERNO, APELLIDO_MATERNO,NOMBRE_LEGAL,COTEJADO,"
					+ " TO_CHAR(F_NACIMIENTO,'DD/MM/YYYY') F_NACIMIENTO,"
					+ " SEXO, ESTADO_CIVIL, RELIGION_ID,PAIS_ID,ESTADO_ID, CIUDAD_ID,NACIONALIDAD,"
					+ " CURP, MODALIDAD_ID,CLAS_FIN,RESIDENCIA_ID,DORMITORIO,PLAN_ID,CARRERA_ID, SALDO,"
					+ " TO_CHAR(F_INSCRIPCION,'DD/MM/YYYY') F_INSCRIPCION, TIPOALUMNO_ID" + " FROM ENOC.INSCRITOS"
					+ " WHERE CODIGO_PERSONAL = ?";
			Object[] parametros = new Object[] { codigoPersonal };
			inscrito = enocJdbc.queryForObject(comando, new InscritosMapper(), parametros);

		} catch (Exception ex) {
			System.out.println("Error - aca.vista.spring.IncritosDao|mapeaRegId|:" + ex);
			ex.printStackTrace();
		}

		return inscrito;

	}

	public boolean existeReg(String codigoPersonal) {
		boolean ok = false;

		try {
			String comando = "SELECT COUNT (*) FROM ENOC.INSCRITOS WHERE CODIGO_PERSONAL = ? ";
			Object[] parametros = new Object[] { codigoPersonal };
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1) {
				ok = true;
			}

		} catch (Exception ex) {
			System.out.println("Error - aca.vista.spring.IncritosDao|existeReg|:" + ex);
		}

		return ok;
	}

	public boolean inscrito(String codigoPersonal) {
		boolean ok = false;

		try {
			String comando = "SELECT COUNT(CODIGO_PERSONAL) FROM ENOC.INSCRITOS " + " WHERE CODIGO_PERSONAL = ? ";
			Object[] parametros = new Object[] { codigoPersonal };
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1) {
				ok = true;
			}

		} catch (Exception ex) {
			System.out.println("Error - aca.vista.spring.IncritosDao|inscrito|:" + ex);
		}

		return ok;
	}

	public String getCarreraId(String codigoPersonal) {

		String carrera = "x";

		try {
			String comando = "SELECT CARRERA_ID FROM ENOC.INSCRITOS WHERE CODIGO_PERSONAL = '" + codigoPersonal + "' ";
			Object[] parametros = new Object[] { codigoPersonal };
			carrera = enocJdbc.queryForObject(comando, String.class, parametros);

		} catch (Exception ex) {
			System.out.println("Error - aca.vista.spring.IncritosDao|getCarreraId|:" + ex);
		}

		return carrera;
	}

	public String getEdad(String codigoPersonal) {

		String edad = "x";

		try {
			String comando = "SELECT ENOC.ALUM_EDAD(CODIGO_PERSONAL) AS EDAD "
					+ " FROM ENOC.INSCRITOS WHERE CODIGO_PERSONAL ='" + codigoPersonal + "'";
			Object[] parametros = new Object[] { codigoPersonal };
			edad = enocJdbc.queryForObject(comando, String.class, parametros);

		} catch (Exception ex) {
			System.out.println("Error - aca.vista.spring.IncritosDao|getEdad|:" + ex);

		}

		return edad;
	}

	public String getMexicanos() {

		String nacion = "x";

		try {
			String comando = "SELECT COUNT(NACIONALIDAD) NACIONALIDAD FROM ENOC.INSCRITOS "
					+ " WHERE RESIDENCIA_ID = 'I' AND DORMITORIO IN('1','2','3','4') AND NACIONALIDAD= '91'";
			nacion = enocJdbc.queryForObject(comando, String.class);

		} catch (Exception ex) {
			System.out.println("Error - aca.vista.Inscritos|getMexicanos|:" + ex);
		}

		return nacion;
	}

	public String getMexicanosUM() {

		String nacion = "x";

		try {
			String comando = "SELECT COUNT(NACIONALIDAD) NACIONALIDAD FROM ENOC.INSCRITOS "
					+ " WHERE RESIDENCIA_ID = 'I' AND DORMITORIO IN('1','2','3','4') AND NACIONALIDAD= '91'"
					+ " AND MODALIDAD_ID IN (1,2,3,4)";
			
			nacion = enocJdbc.queryForObject(comando, String.class);

		} catch (Exception ex) {
			System.out.println("Error - aca.vista.spring.IncritosDao|getMexicanosUM|:" + ex);
		}

		return nacion;
	}

	public String getTipoAlum(String tipo) {

		String tipoAlum = "x";

		try {
			String comando = "SELECT COUNT(CODIGO_PERSONAL) AS TOTAL FROM ENOC.INSCRITOS"
					+ " WHERE RESIDENCIA_ID = 'I' AND DORMITORIO IN('1','2','3','4')" + " AND TIPOALUMNO_ID = ? "; ///
			tipoAlum = enocJdbc.queryForObject(comando, String.class, tipo);

		} catch (Exception ex) {
			System.out.println("Error - aca.vista.spring.IncritosDao|getTipoAlum|:" + ex);
		}

		return tipoAlum;
	}

	public String getTipoAlumUM(String tipo) {

		String tipoAlum = "X";

		try {
			String comando = "SELECT  COUNT(CODIGO_PERSONAL) AS TOTAL FROM ENOC.INSCRITOS"
					+ " WHERE RESIDENCIA_ID = 'I' AND DORMITORIO IN('1','2','3','4')" + " AND TIPOALUMNO_ID = ?" 
					+ " AND MODALIDAD_ID IN (1,2,3,4)";
			tipoAlum = enocJdbc.queryForObject(comando, String.class,tipo);

		} catch (Exception ex) {
			System.out.println("Error - aca.vista.spring.IncritosDao|getTipoAlumUM|:" + ex);
		}

		return tipoAlum;
	}

	public String getInscritosFacultad(String facultadId) {

		String cantAlum = "x";

		try {
			String comando = "SELECT COUNT(CODIGO_PERSONAL) AS CODIGO FROM ENOC.INSCRITOS" + " WHERE CARRERA_ID IN"
					+ " (SELECT CARRERA_ID FROM ENOC.CAT_CARRERA WHERE FACULTAD_ID = '?')";
			Object[] parametros = new Object[] { facultadId };
			cantAlum = enocJdbc.queryForObject(comando, String.class, parametros);

		} catch (Exception ex) {
			System.out.println("Error - aca.vista.spring.IncritosDao|getInscritosFacultad|:" + ex);
		}

		return cantAlum;
	}

	public String getInscritosCarrera(String carreraId) {

		String cantAlum = "x";

		try {
			String comando = "SELECT COUNT(CODIGO_PERSONAL) AS CODIGO FROM ENOC.INSCRITOS"
					+ " WHERE CARRERA_ID = ?";
			Object[] parametros = new Object[] { carreraId };
			cantAlum = enocJdbc.queryForObject(comando, String.class, parametros);

		} catch (Exception ex) {
			System.out.println("Error - aca.vista.spring.IncritosDao|getInscritosCarrera|:" + ex);
		}

		return cantAlum;
	}

	public String fechaNac(String codigoPersonal) {

		String fechaN = "X";

		try {
			String comando = "SELECT TO_CHAR(F_NACIMIENTO,'DD/MM/YYYY') AS F_NACIMIENTO FROM ENOC.INSCRITOS "
					+ " WHERE CODIGO_PERSONAL = ?";
			Object[] parametros = new Object[] { codigoPersonal };
			fechaN = enocJdbc.queryForObject(comando, String.class, parametros);

		} catch (Exception ex) {
			System.out.println("Error: aca.vista.Spring.InscritosDao||fechaNac" + ex);
		}

		return fechaN;
	}

	public String getAlumFormaPago(String codigoPersonal) {

		String formaPago = "C";

		try {
			String comando = "SELECT FORMAPAGO FROM MATEO.FES_CCOBRO WHERE MATRICULA = ?"
					+ " AND CARGA_ID IN (SELECT CARGA_ID FROM ENOC.INSCRITOS WHERE CODIGO_PERSONAL= ?)";
			Object[] parametros = new Object[] { codigoPersonal };
			formaPago = enocJdbc.queryForObject(comando, String.class, parametros);

		} catch (Exception ex) {
			System.out.println("Error: aca.vista.Spring.InscritosDao|getAlumFormaPago|" + ex);
		}

		return formaPago;
	}

	public List<Inscritos> getListAll(String orden) {

		List<Inscritos> lista = new ArrayList<Inscritos>();

		try {
			String comando = "SELECT DISTINCT(CODIGO_PERSONAL) AS CODIGO_PERSONAL, ENOC.FACULTAD(CARRERA_ID), "
					+ " CARGA_ID,BLOQUE_ID,NOMBRE,APELLIDO_PATERNO, APELLIDO_MATERNO,NOMBRE_LEGAL,COTEJADO,"
					+ " TO_CHAR(F_NACIMIENTO,'DD/MM/YYYY') AS F_NACIMIENTO, "
					+ " SEXO, ESTADO_CIVIL, RELIGION_ID,PAIS_ID,ESTADO_ID, CIUDAD_ID,NACIONALIDAD,"
					+ " CURP, MODALIDAD_ID,CLAS_FIN,RESIDENCIA_ID, DORMITORIO,PLAN_ID,CARRERA_ID, SALDO, "
					+ " TO_CHAR(F_INSCRIPCION,'DD/MM/YYYY') F_INSCRIPCION, TIPOALUMNO_ID " + " FROM ENOC.INSCRITOS "
					+ orden;
			lista = enocJdbc.query(comando, new InscritosMapper());

		} catch (Exception ex) {
			System.out.println("Error - aca.vista.spring.IncritosDao|getListAll|:" + ex);
		}

		return lista;
	}
	
	public List<Inscritos> getListAllPorCarga(String orden, String cargaId) {

		List<Inscritos> lista = new ArrayList<Inscritos>();

		try {
			String comando = "SELECT DISTINCT(CODIGO_PERSONAL) AS CODIGO_PERSONAL, ENOC.FACULTAD(CARRERA_ID), "
					+ " CARGA_ID,BLOQUE_ID,NOMBRE,APELLIDO_PATERNO, APELLIDO_MATERNO,NOMBRE_LEGAL,COTEJADO,"
					+ " TO_CHAR(F_NACIMIENTO,'DD/MM/YYYY') AS F_NACIMIENTO, "
					+ " SEXO, ESTADO_CIVIL, RELIGION_ID,PAIS_ID,ESTADO_ID, CIUDAD_ID,NACIONALIDAD,"
					+ " CURP, MODALIDAD_ID,CLAS_FIN,RESIDENCIA_ID, DORMITORIO,PLAN_ID,CARRERA_ID, SALDO, "
					+ " TO_CHAR(F_INSCRIPCION,'DD/MM/YYYY') F_INSCRIPCION, TIPOALUMNO_ID " + " FROM ENOC.INSCRITOS WHERE CARGA_ID = ? "
					+ orden;
			Object[] parametros = new Object[] { cargaId };
			lista = enocJdbc.query(comando, new InscritosMapper(), parametros);

		} catch (Exception ex) {
			System.out.println("Error - aca.vista.spring.IncritosDao|getListAllPorCarga|:" + ex);
		}

		return lista;
	}

	public List<Inscritos> getListAllPorFecha(String fechaIni, String fechaFin, String orden) {

		List<Inscritos> lista = new ArrayList<Inscritos>();

		try {
			String comando = "SELECT DISTINCT(CODIGO_PERSONAL) AS CODIGO_PERSONAL, "
					+ " CARGA_ID,BLOQUE_ID,NOMBRE,APELLIDO_PATERNO, APELLIDO_MATERNO,NOMBRE_LEGAL,COTEJADO,"
					+ " TO_CHAR(F_NACIMIENTO,'DD/MM/YYYY') AS F_NACIMIENTO, "
					+ " SEXO, ESTADO_CIVIL, RELIGION_ID,PAIS_ID,ESTADO_ID, CIUDAD_ID,NACIONALIDAD,"
					+ " CURP, MODALIDAD_ID,CLAS_FIN,RESIDENCIA_ID, DORMITORIO,PLAN_ID,CARRERA_ID, SALDO, "
					+ " TO_CHAR(F_INSCRIPCION,'DD/MM/YYYY') F_INSCRIPCION, TIPOALUMNO_ID "
					+ " FROM ENOC.INSCRITOS "
					+ " WHERE F_INSCRIPCION BETWEEN TO_DATE(?,'DD/MM/YYYY') AND TO_DATE(?,'DD/MM/YYYY') " + orden; ///
			lista = enocJdbc.query(comando, new InscritosMapper(),fechaIni,fechaFin);
		} catch (Exception ex) {
			System.out.println("Error - aca.vista.spring.IncritosDao|getListAllPorFecha|:" + ex);
		}

		return lista;
	}

	public List<Inscritos> getListAllPorFechas(String modalidades, String fechaIni, String fechaFin, String orden) {

		List<Inscritos> lista = new ArrayList<Inscritos>();
		try {
			String comando = "SELECT DISTINCT(CODIGO_PERSONAL) AS CODIGO_PERSONAL,"
					+ " CARGA_ID,BLOQUE_ID,NOMBRE,APELLIDO_PATERNO, APELLIDO_MATERNO,NOMBRE_LEGAL,COTEJADO,"
					+ " TO_CHAR(F_NACIMIENTO,'DD/MM/YYYY') AS F_NACIMIENTO,"
					+ " SEXO, ESTADO_CIVIL, RELIGION_ID,PAIS_ID,ESTADO_ID, CIUDAD_ID,NACIONALIDAD,"
					+ " CURP, MODALIDAD_ID,CLAS_FIN,RESIDENCIA_ID, DORMITORIO,PLAN_ID,CARRERA_ID, SALDO,"
					+ " TO_CHAR(F_INSCRIPCION,'DD/MM/YYYY') AS F_INSCRIPCION, TIPOALUMNO_ID"
					+ " FROM ENOC.INSCRITOS"
					+ " WHERE MODALIDAD_ID IN ("+modalidades+")"
					+ " AND F_INSCRIPCION BETWEEN TO_DATE(?,'DD/MM/YYYY') AND TO_DATE(?,'DD/MM/YYYY') " + orden;	///		
			lista = enocJdbc.query(comando, new InscritosMapper(),fechaIni,fechaFin);

		} catch (Exception ex) {
			System.out.println("Error - aca.vista.spring.IncritosDao|getListAllPorFechas|:" + ex);
		}

		return lista;
	}

	public List<Inscritos> getListAllPorCargaGrado(String orden, String cargaId, int grado) {

		List<Inscritos> lista = new ArrayList<Inscritos>();

		try {
			String comando = "SELECT DISTINCT(CODIGO_PERSONAL) AS CODIGO_PERSONAL, ENOC.FACULTAD(CARRERA_ID),"
					+ " CARGA_ID,BLOQUE_ID,NOMBRE,APELLIDO_PATERNO, APELLIDO_MATERNO,NOMBRE_LEGAL,COTEJADO,"
					+ " TO_CHAR(F_NACIMIENTO,'DD/MM/YYYY') AS F_NACIMIENTO,"
					+ " SEXO, ESTADO_CIVIL, RELIGION_ID,PAIS_ID,ESTADO_ID, CIUDAD_ID,NACIONALIDAD,"
					+ " CURP, MODALIDAD_ID,CLAS_FIN,RESIDENCIA_ID, DORMITORIO,PLAN_ID,CARRERA_ID, SALDO,"
					+ " TO_CHAR(F_INSCRIPCION,'DD/MM/YYYY') F_INSCRIPCION, TIPOALUMNO_ID" 
					+ " FROM ENOC.INSCRITOS"
					+ " WHERE CARGA_ID = ?"
					+ " AND CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM ENOC.ALUM_PLAN WHERE GRADO = ? AND ESTADO = 1)"
					+ orden;
			Object[] parametros = new Object[] { cargaId, grado };
			lista = enocJdbc.query(comando, new InscritosMapper(), parametros);

		} catch (Exception ex) {
			System.out.println("Error - aca.vista.spring.IncritosDao|getListAllPorCargaGrado|:" + ex);
		}

		return lista;
	}
	
	public List<String> lisPlanesInscritos(String codigoAlumno) {
		List<String> lista = new ArrayList<String>();
		try {
			String comando = "SELECT DISTINCT(PLAN_ID) FROM ENOC.INSCRITOS WHERE CODIGO_PERSONAL = ?  ORDER BY PLAN_ID";		
			lista = enocJdbc.queryForList(comando, String.class, codigoAlumno);
		} catch (Exception ex) {
			System.out.println("Error - aca.vista.spring.IncritosDao|lisPlanesInscritos|:" + ex);
		}
		return lista;
	}

	public List<Inscritos> getListNotServAlumno(String orden) {

		List<Inscritos> lista = new ArrayList<Inscritos>();

		try {
			String comando = "SELECT DISTINCT(CODIGO_PERSONAL) AS CODIGO_PERSONAL, "
					+ " CARGA_ID,BLOQUE_ID,NOMBRE,APELLIDO_PATERNO, APELLIDO_MATERNO,NOMBRE_LEGAL,COTEJADO,"
					+ " TO_CHAR(F_NACIMIENTO,'DD/MM/YYYY') AS F_NACIMIENTO, "
					+ " SEXO, ESTADO_CIVIL, RELIGION_ID,PAIS_ID,ESTADO_ID, CIUDAD_ID,NACIONALIDAD,"
					+ " CURP, MODALIDAD_ID,CLAS_FIN,RESIDENCIA_ID, DORMITORIO,PLAN_ID,CARRERA_ID, TO_CHAR(F_INSCRIPCION,'DD/MM/YYYY') F_INSCRIPCION, TIPOALUMNO_ID AS SALDO "
					+ " FROM ENOC.INSCRITOS WHERE CODIGO_PERSONAL NOT IN "
					+ " (SELECT CODIGO_PERSONAL FROM ENOC.SERV_ALUMNO WHERE EVENTO_ID IN (SELECT EVENTO_ID FROM ENOC.SERV_EVENTO WHERE now() BETWEEN FECHA_INICIO AND FECHA_FINAL)) "
					+ orden;
			lista = enocJdbc.query(comando, new InscritosMapper());

		} catch (Exception ex) {
			System.out.println("Error - aca.vista.spring.IncritosDao|getListAll|:" + ex);
		}

		return lista;
	}
	
	public List<String> getCargasInscritas(String codigoAlumno) {

		List<String> lista = new ArrayList<String>();

		try {
			String comando = "SELECT COUNT(*) FROM ENOC.INSCRITOS WHERE CODIGO_PERSONAL = ?";
			Object[] parametros = new Object[] { codigoAlumno };
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1) {
				comando = "SELECT CARGA_ID FROM ENOC.INSCRITOS WHERE CODIGO_PERSONAL = ?";
				lista = enocJdbc.queryForList(comando, String.class, parametros);
			}
		} catch (Exception ex) {
			System.out.println("Error - aca.vista.spring.IncritosDao|getCargasInscritas|:" + ex);
		}

		return lista;
	}

	public TreeMap<String, Inscritos> getMapAll(String orden) {

		TreeMap<String, Inscritos> Inscritos = new TreeMap<String, Inscritos>();
		List<Inscritos> lista = new ArrayList<Inscritos>();
		try {
			String comando = "SELECT DISTINCT(CODIGO_PERSONAL) AS CODIGO_PERSONAL, "
					+ " CARGA_ID,BLOQUE_ID,NOMBRE,APELLIDO_PATERNO, APELLIDO_MATERNO,NOMBRE_LEGAL,COTEJADO,"
					+ " TO_CHAR(F_NACIMIENTO,'DD/MM/YYYY') AS F_NACIMIENTO, "
					+ " SEXO, ESTADO_CIVIL, RELIGION_ID,PAIS_ID,ESTADO_ID, CIUDAD_ID,NACIONALIDAD,"
					+ " CURP, MODALIDAD_ID,CLAS_FIN,RESIDENCIA_ID, DORMITORIO,PLAN_ID,CARRERA_ID, SALDO,"
					+ " TO_CHAR(F_INSCRIPCION,'DD/MM/YYYY') F_INSCRIPCION, TIPOALUMNO_ID " + " FROM ENOC.INSCRITOS "
					+ orden;
			lista = enocJdbc.query(comando, new InscritosMapper());
			for (Inscritos ins : lista) {
				Inscritos.put(ins.getCodigoPersonal(), ins);
			}

		} catch (Exception ex) {
			System.out.println("Error - aca.vista.spring.IncritosDao|getMapAll|:" + ex);
		}

		return Inscritos;
	}

	public List<Inscritos> getListDesercion(String orden) {

		List<Inscritos> lista = new ArrayList<Inscritos>();

		// En este query traemos la clave de la facultad y la enviamos al Bean
		// através del campo CURP.
		// También el grado se usa en usuario y el semestre se usa en la clave
		try {
			String comando = "SELECT" + " DISTINCT(CODIGO_PERSONAL) AS CODIGO_PERSONAL,"
					+ " CARGA_ID,BLOQUE_ID, NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO, NOMBRE_LEGAL, COTEJADO,"
					+ " TO_CHAR(F_NACIMIENTO,'DD/MM/YYYY') F_NACIMIENTO,"
					+ " SEXO, ESTADO_CIVIL, RELIGION_ID,PAIS_ID,ESTADO_ID, CIUDAD_ID,NACIONALIDAD,"
					+ " ENOC.FACULTAD(CARRERA_ID) AS CURP, MODALIDAD_ID,CLAS_FIN,RESIDENCIA_ID,DORMITORIO,PLAN_ID,CARRERA_ID, SALDO, "
					+ " TO_CHAR(F_INSCRIPCION,'DD/MM/YYYY') AS F_INSCRIPCION, TIPOALUMNO_ID" + " FROM ENOC.INSCRITOS"
					+ " UNION"
					+ " SELECT A.CODIGO_PERSONAL AS CODIGO_PERSONAL, 'XXXXXX', 1, B.NOMBRE, B.APELLIDO_PATERNO, B.APELLIDO_MATERNO, B.NOMBRE_LEGAL,'N' AS COTEJADO,"
					+ " '01/01/2000' AS F_NACIMIENTO, B.SEXO,'-' AS ESTADO_CIVIL,B.RELIGION_ID,"
					+ " B.PAIS_ID,0 AS ESTADO_ID, B.CIUDAD_ID , B.NACIONALIDAD, ENOC.FACULTAD(CARRERA_ID) AS CURP,"
					+ " 1 AS MODALIDAD_ID, 1 AS CLAS_FIN, C.RESIDENCIA_ID,'-' AS DORMITORIO, A.PLAN_ID,A.CARRERA_ID,0 AS SALDO, "
					+ " '01/01/2000' AS F_INSCRIPCION, C.TIPO_ALUMNO AS TIPO_ALUMNO"
					+ " FROM ENOC.ALUM_EGRESO A, ENOC.ALUM_PERSONAL B, ENOC.ALUM_ACADEMICO C"
					+ " WHERE A.CODIGO_PERSONAL NOT IN (SELECT CODIGO_PERSONAL FROM ENOC.INSCRITOS)"
					+ " AND B.CODIGO_PERSONAL = A.CODIGO_PERSONAL" + " AND C.CODIGO_PERSONAL=B.CODIGO_PERSONAL"
					+ " AND A.EVENTO_ID IN(14,15) " + orden;
			lista = enocJdbc.query(comando, new InscritosMapper());

		} catch (Exception ex) {
			System.out.println("Error - aca.vista.spring.IncritosDao|getListDesercion|:" + ex);
		}

		return lista;
	}

	public List<Inscritos> getListAllUM(String orden) {

		List<Inscritos> lista = new ArrayList<Inscritos>();

		try {
			String comando = "SELECT DISTINCT(CODIGO_PERSONAL) AS CODIGO_PERSONAL, ENOC.FACULTAD(CARRERA_ID),"
					+ " CARGA_ID,BLOQUE_ID,NOMBRE,APELLIDO_PATERNO, APELLIDO_MATERNO,NOMBRE_LEGAL,COTEJADO,"
					+ " TO_CHAR(F_NACIMIENTO,'DD/MM/YYYY') F_NACIMIENTO, "
					+ " SEXO, ESTADO_CIVIL, RELIGION_ID,PAIS_ID,ESTADO_ID, CIUDAD_ID,NACIONALIDAD,"
					+ " CURP, MODALIDAD_ID,CLAS_FIN,RESIDENCIA_ID,DORMITORIO,PLAN_ID,CARRERA_ID, SALDO, "
					+ " TO_CHAR(F_INSCRIPCION,'DD/MM/YYYY') F_INSCRIPCION, TIPOALUMNO_ID " + " FROM ENOC.INSCRITOS "
					+ orden;
			lista = enocJdbc.query(comando, new InscritosMapper());

		} catch (Exception ex) {
			System.out.println("Error - aca.vista.spring.IncritosDao|getListAllUM|:" + ex);
		}

		return lista;
	}

	public List<Inscritos> getListAllModalidades(String modalidades, String orden) {

		List<Inscritos> lista = new ArrayList<Inscritos>();

		try {
			String comando = "SELECT DISTINCT(CODIGO_PERSONAL) AS CODIGO_PERSONAL, "
					+ " CARGA_ID,BLOQUE_ID,NOMBRE,APELLIDO_PATERNO, APELLIDO_MATERNO,NOMBRE_LEGAL,COTEJADO,"
					+ " TO_CHAR(F_NACIMIENTO,'DD/MM/YYYY') F_NACIMIENTO, "
					+ " SEXO, ESTADO_CIVIL, RELIGION_ID,PAIS_ID,ESTADO_ID, CIUDAD_ID,NACIONALIDAD,"
					+ " CURP, MODALIDAD_ID,CLAS_FIN,RESIDENCIA_ID,DORMITORIO,PLAN_ID,CARRERA_ID, SALDO, "
					+ " TO_CHAR(F_INSCRIPCION,'DD/MM/YYYY') F_INSCRIPCION, TIPOALUMNO_ID "
					+ " FROM ENOC.INSCRITOS WHERE MODALIDAD_ID IN (" + modalidades + ") " + orden;
			lista = enocJdbc.query(comando, new InscritosMapper());

		} catch (Exception ex) {
			System.out.println("Error - aca.vista.spring.IncritosDao|getListAllModalidades|:" + ex);
		}

		return lista;
	}

	public List<Inscritos> getListAllModalidadesPorFechas(String modalidades, String fechaIni, String fechaFin,
			String orden) {

		List<Inscritos> lista = new ArrayList<Inscritos>();

		try {
			String comando = "SELECT DISTINCT(CODIGO_PERSONAL) AS CODIGO_PERSONAL, "
					+ " CARGA_ID,BLOQUE_ID,NOMBRE,APELLIDO_PATERNO, APELLIDO_MATERNO,NOMBRE_LEGAL,COTEJADO,"
					+ " TO_CHAR(F_NACIMIENTO,'DD/MM/YYYY') F_NACIMIENTO, "
					+ " SEXO, ESTADO_CIVIL, RELIGION_ID,PAIS_ID,ESTADO_ID, CIUDAD_ID,NACIONALIDAD,"
					+ " CURP, MODALIDAD_ID,CLAS_FIN,RESIDENCIA_ID,DORMITORIO,PLAN_ID,CARRERA_ID, SALDO, "
					+ " TO_CHAR(F_INSCRIPCION,'DD/MM/YYYY') F_INSCRIPCION, TIPOALUMNO_ID "
					+ " FROM ENOC.INSCRITOS WHERE MODALIDAD_ID IN (" + modalidades + ") "
					+ " AND F_INSCRIPCION BETWEEN TO_DATE('" + fechaIni + "','DD/MM/YYYY') AND TO_DATE('" + fechaFin
					+ "','DD/MM/YYYY') " + orden;
			lista = enocJdbc.query(comando, new InscritosMapper());

		} catch (Exception ex) {
			System.out.println("Error - aca.vista.spring.IncritosDao|getListAllModalidadesPorFechas|:" + ex);

		}

		return lista;
	}
	
	public List<Inscritos> lisModalidadesPorFechasyResidencia(String modalidades, String fechaIni, String fechaFin, String residencia, String orden) {

		List<Inscritos> lista = new ArrayList<Inscritos>();

		try {
			String comando = "SELECT DISTINCT(CODIGO_PERSONAL) AS CODIGO_PERSONAL, "
					+ " CARGA_ID,BLOQUE_ID,NOMBRE,APELLIDO_PATERNO, APELLIDO_MATERNO,NOMBRE_LEGAL,COTEJADO,"
					+ " TO_CHAR(F_NACIMIENTO,'DD/MM/YYYY') F_NACIMIENTO, "
					+ " SEXO, ESTADO_CIVIL, RELIGION_ID,PAIS_ID,ESTADO_ID, CIUDAD_ID,NACIONALIDAD,"
					+ " CURP, MODALIDAD_ID,CLAS_FIN,RESIDENCIA_ID,DORMITORIO,PLAN_ID,CARRERA_ID, SALDO, "
					+ " TO_CHAR(F_INSCRIPCION,'DD/MM/YYYY') F_INSCRIPCION, TIPOALUMNO_ID "
					+ " FROM ENOC.INSCRITOS WHERE MODALIDAD_ID IN ("+modalidades+")"
					+ " AND F_INSCRIPCION BETWEEN TO_DATE(?,'DD/MM/YYYY') AND TO_DATE(?,'DD/MM/YYYY')"
					+ " AND RESIDENCIA_ID = ? " + orden;
			Object[] parametros = new Object[] {fechaIni, fechaFin, residencia };
			lista = enocJdbc.query(comando, new InscritosMapper(), parametros);
		} catch (Exception ex) {
			System.out.println("Error - aca.vista.spring.IncritosDao|getListAllModalidadesPorFechas|:" + ex);

		}

		return lista;
	}

	public List<Inscritos> getLista(String nombre, String paterno, String materno, String orden) {

		List<Inscritos> lista = new ArrayList<Inscritos>();

		try {
			String comando = "SELECT DISTINCT(CODIGO_PERSONAL) AS CODIGO_PERSONAL, "
					+ " CARGA_ID,BLOQUE_ID,NOMBRE,APELLIDO_PATERNO, APELLIDO_MATERNO,NOMBRE_LEGAL,COTEJADO,"
					+ " TO_CHAR(F_NACIMIENTO,'DD/MM/YYYY') F_NACIMIENTO, "
					+ " SEXO, ESTADO_CIVIL, RELIGION_ID,PAIS_ID,ESTADO_ID, CIUDAD_ID,NACIONALIDAD,"
					+ " CURP, MODALIDAD_ID,CLAS_FIN,RESIDENCIA_ID, DORMITORIO, PLAN_ID,CARRERA_ID, SALDO, "
					+ " TO_CHAR(F_INSCRIPCION,'DD/MM/YYYY') F_INSCRIPCION, TIPOALUMNO_ID " + " FROM ENOC.INSCRITOS "
					+ " WHERE NOMBRE LIKE UPPER('" + nombre + "%') " + " AND APELLIDO_PATERNO LIKE UPPER('" + paterno
					+ "%') " + " AND APELLIDO_MATERNO LIKE UPPER('" + materno + "%') " + orden;
			lista = enocJdbc.query(comando, new InscritosMapper());

		} catch (Exception ex) {
			System.out.println("Error - aca.vista.spring.IncritosDao|getLista|:" + ex);

		}

		return lista;
	}

	public List<Inscritos> getListCumple(String mes, String dia, String orden) {

		List<Inscritos> lista = new ArrayList<Inscritos>();

		try {

			String comando = "SELECT "
					+ " DISTINCT(CODIGO_PERSONAL) AS CODIGO_PERSONAL,CARGA_ID,BLOQUE_ID,NOMBRE,APELLIDO_PATERNO, APELLIDO_MATERNO,NOMBRE_LEGAL,COTEJADO,"
					+ " TO_CHAR(F_NACIMIENTO,'DD/MM/YYYY') F_NACIMIENTO, "
					+ " SEXO, ESTADO_CIVIL, RELIGION_ID,PAIS_ID,ESTADO_ID, CIUDAD_ID,NACIONALIDAD,"
					+ " CURP, MODALIDAD_ID,CLAS_FIN,RESIDENCIA_ID, DORMITORIO, PLAN_ID,CARRERA_ID, SALDO, "
					+ " TO_CHAR(F_INSCRIPCION,'DD/MM/YYYY') F_INSCRIPCION, TIPOALUMNO_ID " + " FROM ENOC.INSCRITOS "
					+ " WHERE SUBSTR(TO_CHAR(F_NACIMIENTO,'DD/MM/YYYY'),4,2)= '" + mes + "' ";
			if (!dia.equals("0")) {
				comando = comando + "AND SUBSTR(TO_CHAR(F_NACIMIENTO,'DD/MM/YYYY'),1,2)= '" + dia + "' ";
			}
			comando = comando + " " + orden;
			lista = enocJdbc.query(comando, new InscritosMapper());

		} catch (Exception ex) {
			System.out.println("Error - aca.vista.spring.IncritosDao|getListCumple|:" + ex);

		}

		return lista;
	}

	public List<Inscritos> getListCumpleSem(String Orden) {

		List<Inscritos> lista = new ArrayList<Inscritos>();
		List<Inscritos> temp 	= new ArrayList<Inscritos>();
		Fecha fecha = new aca.util.Fecha();
		List<String> fechas = fecha.getSemanaActual();
		String fechaTemp = "";

		try {
			for (int j = 0; j < 7; j++) {
				fechaTemp = (String) fechas.get(j);

				String comando = " SELECT " + " DISTINCT(CODIGO_PERSONAL) AS CODIGO_PERSONAL,CARGA_ID,BLOQUE_ID, "
						+ " NOMBRE,APELLIDO_PATERNO, APELLIDO_MATERNO,NOMBRE_LEGAL,COTEJADO, "
						+ " TO_CHAR(F_NACIMIENTO,'DD/MM/YYYY') F_NACIMIENTO, "
						+ " SEXO, ESTADO_CIVIL, RELIGION_ID,PAIS_ID,ESTADO_ID, CIUDAD_ID,NACIONALIDAD,"
						+ " CURP, MODALIDAD_ID,CLAS_FIN,RESIDENCIA_ID, DORMITORIO, PLAN_ID,CARRERA_ID, SALDO, "
						+ " TO_CHAR(F_INSCRIPCION,'DD/MM/YYYY') F_INSCRIPCION, TIPOALUMNO_ID " + " FROM ENOC.INSCRITOS "
						+ " WHERE SUBSTR(TO_CHAR(F_NACIMIENTO,'DD/MM/YYYY'),1,5)= '" + fechaTemp.substring(0, 5) + "' ";

				comando = comando + " " + Orden;
				temp = enocJdbc.query(comando, new InscritosMapper());
				for (Inscritos insc : temp) {
					lista.add(insc);
				}
			}

		} catch (Exception ex) {
			System.out.println("Error - aca.vista.spring.IncritosDao|getListCumpleSem|:" + ex);

		}

		return lista;
	}

	public List<String> getListCumpleSemana(String orden) {

		List<String> lista 		= new ArrayList<String>();
		List<String> temp 		= new ArrayList<String>();
		Fecha fecha = new aca.util.Fecha();
		List<String> fechas = fecha.getSemanaActual();
		String fechaTemp = "";

		try {
			for (int j = 0; j < 7; j++) {
				fechaTemp = (String) fechas.get(j);

				String comando = "SELECT DISTINCT(CODIGO_PERSONAL) AS CODIGO_PERSONAL FROM ENOC.INSCRITOS"
						+ " WHERE SUBSTR(TO_CHAR(F_NACIMIENTO,'DD/MM/YYYY'),1,5)= '" + fechaTemp.substring(0, 5) + "' "
						+ orden;
				temp = enocJdbc.queryForList(comando, String.class);
				for (String insc : temp) {
					lista.add(insc);
				}
			}

		} catch (Exception ex) {
			System.out.println("Error - aca.vista.spring.IncritosDao|getListCumpleSemana|:" + ex);

		}

		return lista;
	}

	public List<Inscritos> getListDisciplina(String periodo, String orden) {

		List<Inscritos> lista = new ArrayList<Inscritos>();

		try {
			String comando = "SELECT DISTINCT(CODIGO_PERSONAL) AS CODIGO_PERSONAL, CARGA_ID, "
					+ " BLOQUE_ID , NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO, NOMBRE_LEGAL, COTEJADO, "
					+ " TO_CHAR(F_NACIMIENTO,'DD/MM/YYYY') F_NACIMIENTO, SEXO, ESTADO_CIVIL, "
					+ " RELIGION_ID,PAIS_ID,ESTADO_ID, CIUDAD_ID,NACIONALIDAD, CURP, "
					+ " MODALIDAD_ID,CLAS_FIN,RESIDENCIA_ID,DORMITORIO,PLAN_ID,CARRERA_ID, SALDO, F_INSCRIPCION, TIPOALUMNO_ID "
					+ " FROM ENOC.COND_ALUMNO , ENOC.INSCRITOS  WHERE MATRICULA = CODIGO_PERSONAL "
					+ " AND PERIODO_ID = '" + periodo + "' " + orden;
			lista = enocJdbc.query(comando, new InscritosMapper());

		} catch (Exception ex) {
			System.out.println("Error - aca.vista.spring.IncritosDao|getListDisciplina|:" + ex);

		}

		return lista;
	}

	public String getNombre(String codigoPersonal, String orden) {

		String comando = "";
		String nombre = "x";

		try {
			if (orden.equals("NOMBRE")) {
				comando = "SELECT NOMBRE||' '||APELLIDO_PATERNO||' '||APELLIDO_MATERNO AS NOMBRE "
						+ " FROM ENOC.INSCRITOS WHERE CODIGO_PERSONAL = ? "+orden;
			} else {
				comando = "SELECT APELLIDO_PATERNO||' '||APELLIDO_MATERNO||' '||NOMBRE AS NOMBRE "
						+ " FROM ENOC.INSCRITOS WHERE CODIGO_PERSONAL = ? "+orden;
			}
			Object[] parametros = new Object[] { codigoPersonal };
			nombre = enocJdbc.queryForObject(comando, String.class, parametros);

		} catch (Exception ex) {
			System.out.println("Error - aca.vista.spring.IncritosDao|getNombre|:" + ex);

		}

		return nombre;
	}

	public String getCuentaAlum(String idMentor, String periodoId, String facultadId) {

		String alumno = "x";

		try {
			String comando = "SELECT COUNT(I.CODIGO_PERSONAL) ALUMNOS" + " FROM ENOC.INSCRITOS I, ENOC.MENT_ALUMNO MA"
					+ " WHERE ENOC.FACULTAD(I.CARRERA_ID)= ? "
					+ " AND MA.CODIGO_PERSONAL = I.CODIGO_PERSONAL" + " AND MA.PERIODO_ID = ?"
					+ " AND MA.MENTOR_ID = ? AND MA.STATUS = 'A'";
			Object[] parametros = new Object[] {facultadId,periodoId,idMentor};
			alumno = enocJdbc.queryForObject(comando, String.class, parametros);

		} catch (Exception ex) {
			System.out.println("Error - aca.vista.spring.IncritosDao|getMentorAlum|:" + ex);
		}

		return alumno;
	}

	public List<Inscritos> getListAlumSinMentor(String periodoId, String orden) {

		List<Inscritos> lista = new ArrayList<Inscritos>();

		try {
			String comando = "SELECT CODIGO_PERSONAL, NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO,"
					+ " CARGA_ID, MODALIDAD_ID,BLOQUE_ID,NOMBRE_LEGAL,COTEJADO,F_NACIMIENTO,"
					+ " SEXO,ESTADO_CIVIL,RELIGION_ID,PAIS_ID,ESTADO_ID,CIUDAD_ID,NACIONALIDAD,CURP,"
					+ " CLAS_FIN,PLAN_ID,RESIDENCIA_ID, DORMITORIO, CARRERA_ID, SALDO, "
					+ " TO_CHAR(F_INSCRIPCION,'DD/MM/YYYY') F_INSCRIPCION, TIPOALUMNO_ID " + " FROM ENOC.INSCRITOS "
					+ " WHERE CODIGO_PERSONAL NOT IN "
					+ "	(SELECT CODIGO_PERSONAL FROM ENOC.MENT_ALUMNO WHERE PERIODO_ID ='" + periodoId
					+ "' AND STATUS = 'A') " + orden;
			lista = enocJdbc.query(comando, new InscritosMapper());

		} catch (Exception ex) {
			System.out.println("Error - aca.vista.spring.IncritosDao|getListAlumSinMentor|:" + ex);

		}

		return lista;
	}

	// Lista de alumnos sin mentor en una facultad
	public List<Inscritos> getListAlumSinMentor(String periodoId, String facultadId, String orden) {

		List<Inscritos> lista = new ArrayList<Inscritos>();

		try {
			String comando = " SELECT CODIGO_PERSONAL, NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO,"
					+ " CARGA_ID, MODALIDAD_ID,BLOQUE_ID,NOMBRE_LEGAL,COTEJADO,F_NACIMIENTO,"
					+ " SEXO,ESTADO_CIVIL,RELIGION_ID,PAIS_ID,ESTADO_ID,CIUDAD_ID,NACIONALIDAD,CURP,"
					+ " CLAS_FIN,PLAN_ID,RESIDENCIA_ID, DORMITORIO, CARRERA_ID, SALDO, "
					+ " TO_CHAR(F_INSCRIPCION,'DD/MM/YYYY') F_INSCRIPCION, TIPOALUMNO_ID " + " FROM ENOC.INSCRITOS"
					+ " WHERE ENOC.FACULTAD(CARRERA_ID) = ?" + ""
					+ " AND CODIGO_PERSONAL NOT IN(SELECT CODIGO_PERSONAL FROM ENOC.MENT_ALUMNO WHERE PERIODO_ID = ? AND STATUS = 'A') " + orden;
			lista = enocJdbc.query(comando, new InscritosMapper(),facultadId,periodoId);

		} catch (Exception ex) {
			System.out.println("Error - aca.vista.spring.IncritosDao|getListAlumSinMentor|:" + ex);

		}

		return lista;
	}

	public List<Inscritos> getListFAC(String orden, String carreraId) {

		List<Inscritos> lista = new ArrayList<Inscritos>();

		try {
			String comando = "SELECT DISTINCT(CODIGO_PERSONAL) AS CODIGO_PERSONAL, "
					+ " CARGA_ID,BLOQUE_ID,NOMBRE,APELLIDO_PATERNO, APELLIDO_MATERNO,NOMBRE_LEGAL,COTEJADO,"
					+ " TO_CHAR(F_NACIMIENTO,'DD/MM/YYYY') F_NACIMIENTO, "
					+ " SEXO, ESTADO_CIVIL, RELIGION_ID,PAIS_ID,ESTADO_ID, CIUDAD_ID,NACIONALIDAD,"
					+ " CURP, MODALIDAD_ID,CLAS_FIN,RESIDENCIA_ID,DORMITORIO,PLAN_ID,CARRERA_ID, SALDO, "
					+ " TO_CHAR(F_INSCRIPCION,'DD/MM/YYYY') F_INSCRIPCION, TIPOALUMNO_ID "
					+ " FROM ENOC.INSCRITOS WHERE MODALIDAD_ID IN (1,4,5) AND CARRERA_ID = ?"
					+ orden;
			lista = enocJdbc.query(comando, new InscritosMapper(),carreraId);///

		} catch (Exception ex) {
			System.out.println("Error - aca.vista.spring.IncritosDao|getListFAC|:" + ex);

		}

		return lista;
	}

	public List<Inscritos> getListDormitorio(String dormitorio, String cargas, String modalidades, String orden) {

		List<Inscritos> lista = new ArrayList<Inscritos>();

		try {
			String comando = " SELECT CODIGO_PERSONAL, CARGA_ID, BLOQUE_ID, NOMBRE, APELLIDO_PATERNO,"
					+ " APELLIDO_MATERNO, NOMBRE_LEGAL, COTEJADO, TO_CHAR(F_NACIMIENTO,'DD/MM/YYYY') AS F_NACIMIENTO,"
					+ " SEXO, ESTADO_CIVIL, RELIGION_ID, PAIS_ID, ESTADO_ID, CIUDAD_ID, NACIONALIDAD,"
					+ " CURP, MODALIDAD_ID, CLAS_FIN, RESIDENCIA_ID, DORMITORIO, PLAN_ID, CARRERA_ID, SALDO, "
					+ " TO_CHAR(F_INSCRIPCION,'DD/MM/YYYY') F_INSCRIPCION, TIPOALUMNO_ID" + " FROM ENOC.INSCRITOS"
					+ " WHERE CARGA_ID IN (" + cargas + ")" + " AND MODALIDAD_ID IN (" + modalidades + ")"
					+ " AND DORMITORIO = ?" + " AND RESIDENCIA_ID = 'I' " + orden;
			lista = enocJdbc.query(comando, new InscritosMapper(),dormitorio);///

		} catch (Exception ex) {
			System.out.println("Error - aca.vista.spring.IncritosDao|getListDormitorio|:" + ex);
		}

		return lista;
	}

	public List<Inscritos> getListDormitorioPorFecha(String dormitorioId, String fechaIni, String fechaFin) {
		List<Inscritos> lista = new ArrayList<Inscritos>();

		try {
			String comando = " SELECT CODIGO_PERSONAL, CARGA_ID, BLOQUE_ID, NOMBRE, APELLIDO_PATERNO,"
					+ " APELLIDO_MATERNO, NOMBRE_LEGAL, COTEJADO, TO_CHAR(F_NACIMIENTO,'DD/MM/YYYY') AS F_NACIMIENTO,"
					+ " SEXO, ESTADO_CIVIL, RELIGION_ID, PAIS_ID, ESTADO_ID, CIUDAD_ID, NACIONALIDAD,"
					+ " CURP, MODALIDAD_ID, CLAS_FIN, RESIDENCIA_ID, DORMITORIO, PLAN_ID, CARRERA_ID, SALDO, "
					+ " TO_CHAR(F_INSCRIPCION,'DD/MM/YYYY') F_INSCRIPCION, TIPOALUMNO_ID" + " FROM ENOC.INSCRITOS"
					+ " WHERE RESIDENCIA_ID = 'I' AND DORMITORIO = ? "
					+ " AND F_INSCRIPCION BETWEEN TO_DATE(?,'DD/MM/YYYY') AND TO_DATE(?,'DD/MM/YYYY') ";
			lista = enocJdbc.query(comando, new InscritosMapper(),dormitorioId,fechaIni,fechaFin);///

		} catch (Exception ex) {
			System.out.println("Error - aca.vista.spring.IncritosDao|getListDormitorioPorFecha|:" + ex);
		}

		return lista;
	}

	public List<Inscritos> getListDormitorioPorFechaInscritos(String dormitorioId, String fechaIni, String fechaFin) {
		List<Inscritos> lista = new ArrayList<Inscritos>();

		try {
			String comando = " SELECT CODIGO_PERSONAL, CARGA_ID, BLOQUE_ID, NOMBRE, APELLIDO_PATERNO,"
					+ " APELLIDO_MATERNO, NOMBRE_LEGAL, COTEJADO, TO_CHAR(F_NACIMIENTO,'DD/MM/YYYY') AS F_NACIMIENTO,"
					+ " SEXO, ESTADO_CIVIL, RELIGION_ID, PAIS_ID, ESTADO_ID, CIUDAD_ID, NACIONALIDAD,"
					+ " CURP, MODALIDAD_ID, CLAS_FIN, RESIDENCIA_ID, DORMITORIO, PLAN_ID, CARRERA_ID, SALDO, "
					+ " TO_CHAR(F_INSCRIPCION,'DD/MM/YYYY') F_INSCRIPCION, TIPOALUMNO_ID" + " FROM ENOC.INSCRITOS"
					+ " WHERE RESIDENCIA_ID = 'I' AND DORMITORIO = ? "
					+ " AND F_INSCRIPCION BETWEEN TO_DATE(?,'DD/MM/YYYY') AND TO_DATE(?,'DD/MM/YYYY') "
					+ " AND CODIGO_PERSONAL IN(SELECT CODIGO_PERSONAL FROM ENOC.INT_ALUMNO WHERE DORMITORIO_ID = ?)";
			lista = enocJdbc.query(comando, new InscritosMapper(),dormitorioId,fechaIni,fechaFin,dormitorioId);///

		} catch (Exception ex) {
			System.out.println("Error - aca.vista.spring.IncritosDao|getListDormitorioPorFechaInscritos|:" + ex);

		}
		return lista;
	}

	public List<Inscritos> getListDormitorio(String dormitorio, String cargas, String modalidades, String fechaIni,
			String fechaFin, String orden) {

		List<Inscritos> lista = new ArrayList<Inscritos>();

		try {
			String comando = " SELECT CODIGO_PERSONAL, CARGA_ID, BLOQUE_ID, NOMBRE, APELLIDO_PATERNO,"
					+ " APELLIDO_MATERNO, NOMBRE_LEGAL, COTEJADO, TO_CHAR(F_NACIMIENTO,'DD/MM/YYYY') AS F_NACIMIENTO,"
					+ " SEXO, ESTADO_CIVIL, RELIGION_ID, PAIS_ID, ESTADO_ID, CIUDAD_ID, NACIONALIDAD,"
					+ " CURP, MODALIDAD_ID, CLAS_FIN, RESIDENCIA_ID, DORMITORIO, PLAN_ID, CARRERA_ID, SALDO, "
					+ " TO_CHAR(F_INSCRIPCION,'DD/MM/YYYY') F_INSCRIPCION, TIPOALUMNO_ID" + " FROM ENOC.INSCRITOS"
					+ " WHERE CARGA_ID IN (" + cargas + ")" + " AND MODALIDAD_ID IN (" + modalidades + ")"
					+ " AND DORMITORIO = ?" + " AND F_INSCRIPCION BETWEEN TO_DATE(?,'DD/MM/YYYY') AND TO_DATE(?,'DD/MM/YYYY')" 
					+ " AND RESIDENCIA_ID = 'I' "
					+ orden;
			lista = enocJdbc.query(comando, new InscritosMapper(),dormitorio,fechaIni,fechaFin);///

		} catch (Exception ex) {
			System.out.println("Error - aca.vista.spring.IncritosDao|getListDormitorio|:" + ex);

		}

		return lista;
	}
	
	public List<Inscritos> lisUnidadesPorDormitorio( String periodoId, String fechaIni, String fechaFin, String orden) {

		List<Inscritos> lista = new ArrayList<Inscritos>();

		try {
			String comando = " SELECT CODIGO_PERSONAL, CARGA_ID, BLOQUE_ID, NOMBRE, APELLIDO_PATERNO,"
					+ " APELLIDO_MATERNO, NOMBRE_LEGAL, COTEJADO, TO_CHAR(F_NACIMIENTO,'DD/MM/YYYY') AS F_NACIMIENTO,"
					+ " SEXO, ESTADO_CIVIL, RELIGION_ID, PAIS_ID, ESTADO_ID, CIUDAD_ID, NACIONALIDAD,"
					+ " CURP, MODALIDAD_ID, CLAS_FIN, RESIDENCIA_ID, DORMITORIO, PLAN_ID, CARRERA_ID, SALDO, "
					+ " TO_CHAR(F_INSCRIPCION,'DD/MM/YYYY') F_INSCRIPCION, TIPOALUMNO_ID"
					+ " FROM ENOC.INSCRITOS"
					+ " WHERE RESIDENCIA_ID = 'I'"
					+ " AND CODIGO_PERSONAL IN (SELECT MATRICULA FROM ENOC.COND_ALUMNO WHERE PERIODO_ID = ?)"
					+ " AND F_INSCRIPCION BETWEEN TO_DATE(?,'DD/MM/YYYY') AND TO_DATE(?,'DD/MM/YYYY') "	+ orden;
			Object[] parametros = new Object[] { periodoId, fechaIni, fechaFin };
			lista = enocJdbc.query(comando, new InscritosMapper(), parametros);
			
		} catch (Exception ex) {
			System.out.println("Error - aca.vista.spring.IncritosDao|getListDormitorio|:" + ex);

		}

		return lista;
	}

	public List<Inscritos> lisInternadoCalificado(String cargas, String modalidades, String fechaIni, String fechaFin,
			String fechaInternado, String orden) {

		List<Inscritos> lista = new ArrayList<Inscritos>();

		try {
			String comando = " SELECT CODIGO_PERSONAL, CARGA_ID, BLOQUE_ID, NOMBRE, APELLIDO_PATERNO,"
					+ " APELLIDO_MATERNO, NOMBRE_LEGAL, COTEJADO, TO_CHAR(F_NACIMIENTO,'DD/MM/YYYY') AS F_NACIMIENTO,"
					+ " SEXO, ESTADO_CIVIL, RELIGION_ID, PAIS_ID, ESTADO_ID, CIUDAD_ID, NACIONALIDAD,"
					+ " CURP, MODALIDAD_ID, CLAS_FIN, RESIDENCIA_ID, DORMITORIO, PLAN_ID, CARRERA_ID, SALDO, "
					+ " TO_CHAR(F_INSCRIPCION,'DD/MM/YYYY') F_INSCRIPCION, TIPOALUMNO_ID" + " FROM ENOC.INSCRITOS"
					+ " WHERE CARGA_ID IN (" + cargas + ")" + " AND MODALIDAD_ID IN (" + modalidades + ")"
					+ " AND F_INSCRIPCION BETWEEN TO_DATE(?,'DD/MM/YYYY') AND TO_DATE(?,'DD/MM/YYYY')"
					+ " AND CODIGO_PERSONAL IN "
					+ "		(SELECT MATRICULA FROM NOE.COM_AUTORIZACION WHERE CARGA_ID IN (" + cargas
					+ ") AND NUM_COMIDAS > 0 " + "		AND TO_DATE(?,'DD/MM/YYYY') BETWEEN FECHA_INICIAL AND FECHA_FINAL"
					+ " 	AND BLOQUE = ENOC.INSCRITOS.BLOQUE_ID) " + orden;
			lista = enocJdbc.query(comando, new InscritosMapper(),fechaIni,fechaFin,fechaInternado); ///

		} catch (Exception ex) {
			System.out.println("Error - aca.vista.spring.IncritosDao|getListDormitorio|:" + ex);

		}

		return lista;
	}

	public List<Inscritos> getListDormitorioModalidad(String modalidades, String dormitorio, String fechaIni,
			String fechaFin, String orden) {

		List<Inscritos> lista = new ArrayList<Inscritos>();

		try {
			String comando = "SELECT CODIGO_PERSONAL, CARGA_ID, BLOQUE_ID, NOMBRE, APELLIDO_PATERNO,"
					+ " APELLIDO_MATERNO, NOMBRE_LEGAL, COTEJADO, TO_CHAR(F_NACIMIENTO,'DD/MM/YYYY') F_NACIMIENTO,"
					+ " SEXO, ESTADO_CIVIL, RELIGION_ID, PAIS_ID, ESTADO_ID, CIUDAD_ID, NACIONALIDAD,"
					+ " CURP, MODALIDAD_ID, CLAS_FIN, RESIDENCIA_ID, DORMITORIO, PLAN_ID, CARRERA_ID, SALDO, "
					+ " TO_CHAR(F_INSCRIPCION,'DD/MM/YYYY') F_INSCRIPCION, TIPOALUMNO_ID" + " FROM ENOC.INSCRITOS "
					+ " WHERE MODALIDAD_ID IN (" + modalidades + ") " + " AND DORMITORIO = ?"
					+ " AND F_INSCRIPCION BETWEEN TO_DATE(?,'DD/MM/YYYY') AND TO_DATE(?,'DD/MM/YYYY')" 
					+ " AND RESIDENCIA_ID = 'I' " + orden;
			lista = enocJdbc.query(comando, new InscritosMapper(),dormitorio,fechaIni,fechaFin);

		} catch (Exception ex) {
			System.out.println("Error - aca.vista.spring.IncritosDao|getListDormitorio|:" + ex);

		}

		return lista;
	}

	public List<Inscritos> getListSinDormitorio(String cargas, String modalidades, String orden) {

		List<Inscritos> lista = new ArrayList<Inscritos>();

		try {
			String comando = " SELECT CODIGO_PERSONAL, CARGA_ID, BLOQUE_ID, NOMBRE, APELLIDO_PATERNO,"
					+ " APELLIDO_MATERNO, NOMBRE_LEGAL, COTEJADO, TO_CHAR(F_NACIMIENTO,'DD/MM/YYYY') F_NACIMIENTO,"
					+ " SEXO, ESTADO_CIVIL, RELIGION_ID, PAIS_ID, ESTADO_ID, CIUDAD_ID, NACIONALIDAD,"
					+ " CURP, MODALIDAD_ID, CLAS_FIN, RESIDENCIA_ID, DORMITORIO, PLAN_ID, CARRERA_ID, SALDO,"
					+ " TO_CHAR(F_INSCRIPCION,'DD/MM/YYYY') F_INSCRIPCION, TIPOALUMNO_ID" + " FROM ENOC.INSCRITOS"
					+ " WHERE CARGA_ID IN (" + cargas + ")" + " AND MODALIDAD_ID IN (" + modalidades + ")"
					+ " AND DORMITORIO NOT IN ('1','2','3','4')" + " AND RESIDENCIA_ID = 'I' " + orden;
			lista = enocJdbc.query(comando, new InscritosMapper());

		} catch (Exception ex) {
			System.out.println("Error - aca.vista.spring.IncritosDao|getListSinDormitorio|:" + ex);

		}

		return lista;
	}

	public List<Inscritos> getInternosPorGenero(String orden) {

		List<Inscritos> lista = new ArrayList<Inscritos>();
		try {
			String comando = "SELECT CARGA_ID,BLOQUE_ID,CODIGO_PERSONAL,NOMBRE,APELLIDO_PATERNO,APELLIDO_MATERNO,NOMBRE_LEGAL,COTEJADO, "
					+ " TO_CHAR(F_NACIMIENTO,'DD/MM/YYYY') AS F_NACIMIENTO, "
					+ " SEXO, ESTADO_CIVIL, RELIGION_ID,PAIS_ID,ESTADO_ID, CIUDAD_ID,NACIONALIDAD,"
					+ " CURP, MODALIDAD_ID,CLAS_FIN,RESIDENCIA_ID,DORMITORIO,PLAN_ID,CARRERA_ID, SALDO, "
					+ " TO_CHAR(F_INSCRIPCION,'DD/MM/YYYY') F_INSCRIPCION, TIPOALUMNO_ID " + " FROM ENOC.INSCRITOS "
					+ " WHERE CARGA_ID IN (SELECT CARGA_ID FROM ENOC.CARGA WHERE now() BETWEEN F_INICIO AND F_FINAL) "
					+ " AND MODALIDAD_ID IN (1,4,5) AND RESIDENCIA_ID = 'I' " + orden;
			lista = enocJdbc.query(comando, new InscritosMapper());
		} catch (Exception ex) {
			System.out.println("Error - aca.vista.spring.IncritosDao|getInternosPorGenero|:" + ex);
		}
		return lista;
	}

	public List<Inscritos> getInternosPorGeneroModalidad(String modalidades, String fechaIni, String fechaFin, String orden) {

		List<Inscritos> lista = new ArrayList<Inscritos>();

		try {
			String comando = "SELECT CARGA_ID,BLOQUE_ID,CODIGO_PERSONAL,NOMBRE,APELLIDO_PATERNO,APELLIDO_MATERNO,NOMBRE_LEGAL,COTEJADO, "
					+ " TO_CHAR(F_NACIMIENTO,'DD/MM/YYYY') AS F_NACIMIENTO, "
					+ " SEXO, ESTADO_CIVIL, RELIGION_ID,PAIS_ID,ESTADO_ID, CIUDAD_ID,NACIONALIDAD,"
					+ " CURP, MODALIDAD_ID,CLAS_FIN,RESIDENCIA_ID,DORMITORIO,PLAN_ID,CARRERA_ID, SALDO, "
					+ " TO_CHAR(F_INSCRIPCION,'DD/MM/YYYY') F_INSCRIPCION, TIPOALUMNO_ID " + " FROM ENOC.INSCRITOS "
					+ " WHERE CARGA_ID IN (SELECT CARGA_ID FROM ENOC.CARGA WHERE now() BETWEEN F_INICIO AND F_FINAL) "
					+ " AND MODALIDAD_ID IN (" + modalidades + ") AND RESIDENCIA_ID = 'I' "
					+ " AND F_INSCRIPCION BETWEEN TO_DATE(?,'DD/MM/YYYY') AND TO_DATE(?,'DD/MM/YYYY') " + orden;
			lista = enocJdbc.query(comando, new InscritosMapper(),fechaIni,fechaFin); ///

		} catch (Exception ex) {
			System.out.println("Error - aca.vista.spring.IncritosDao|getInternosPorGenero|:" + ex);

		}

		return lista;
	}

	public List<Inscritos> getListExternos(String orden) {

		List<Inscritos> lista = new ArrayList<Inscritos>();
		try {
			String comando = "SELECT DISTINCT(CODIGO_PERSONAL) AS CODIGO_PERSONAL,"
					+ " CARGA_ID, BLOQUE_ID, NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO, NOMBRE_LEGAL, COTEJADO,"
					+ " TO_CHAR(F_NACIMIENTO,'DD/MM/YYYY') AS F_NACIMIENTO,"
					+ " SEXO, ESTADO_CIVIL, RELIGION_ID, PAIS_ID, ESTADO_ID, CIUDAD_ID, NACIONALIDAD,"
					+ " CURP, MODALIDAD_ID, CLAS_FIN, RESIDENCIA_ID, DORMITORIO, PLAN_ID, CARRERA_ID, SALDO,"
					+ " TO_CHAR(F_INSCRIPCION,'DD/MM/YYYY') AS F_INSCRIPCION, TIPOALUMNO_ID"
					+ " FROM ENOC.INSCRITOS"
					+ " WHERE RESIDENCIA_ID = 'E' " + orden;
			lista = enocJdbc.query(comando, new InscritosMapper());
		} catch (Exception ex) {
			System.out.println("Error - aca.vista.spring.IncritosDao|getListExternos|:" + ex);
		}
		return lista;
	}

	public List<Inscritos> getExternosPorFechas(String fechaIni, String fechaFin, String modalidades, String orden) {

		List<Inscritos> lista = new ArrayList<Inscritos>();
		try {
			String comando = " SELECT DISTINCT(CODIGO_PERSONAL) AS CODIGO_PERSONAL,"
					+ " CARGA_ID, BLOQUE_ID, NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO, NOMBRE_LEGAL, COTEJADO,"
					+ " TO_CHAR(F_NACIMIENTO,'DD/MM/YYYY') AS F_NACIMIENTO,"
					+ " SEXO, ESTADO_CIVIL, RELIGION_ID, PAIS_ID, ESTADO_ID, CIUDAD_ID, NACIONALIDAD,"
					+ " CURP, MODALIDAD_ID, CLAS_FIN, RESIDENCIA_ID, DORMITORIO, PLAN_ID, CARRERA_ID, SALDO,"
					+ " TO_CHAR(F_INSCRIPCION,'DD/MM/YYYY') AS F_INSCRIPCION, TIPOALUMNO_ID"
					+ " FROM ENOC.INSCRITOS"
					+ " WHERE RESIDENCIA_ID = 'E'"
					+ " AND F_INSCRIPCION BETWEEN TO_DATE(?,'DD/MM/YYYY') AND TO_DATE (?,'DD/MM/YYYY') "
					+ " AND MODALIDAD_ID IN("+modalidades+") " + orden;
			Object[] parametros = new Object[] { fechaIni, fechaFin };
			lista = enocJdbc.query(comando, new InscritosMapper(), parametros);
		} catch (Exception ex) {
			System.out.println("Error - aca.vista.spring.IncritosDao|getExternosPorFechas|:" + ex);
		}
		return lista;
	}

	public List<Inscritos> getListInscritosExternos(String orden) {

		List<Inscritos> lista = new ArrayList<Inscritos>();
		try {
			String comando = "SELECT DISTINCT(CODIGO_PERSONAL) AS CODIGO_PERSONAL,"
					+ " CARGA_ID, BLOQUE_ID, NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO, NOMBRE_LEGAL, COTEJADO," 
					+ " TO_CHAR(F_NACIMIENTO,'DD/MM/YYYY') AS F_NACIMIENTO,"
					+ " SEXO, ESTADO_CIVIL, RELIGION_ID, PAIS_ID, ESTADO_ID, CIUDAD_ID, NACIONALIDAD," 
					+ " CURP, MODALIDAD_ID, CLAS_FIN, RESIDENCIA_ID, DORMITORIO, PLAN_ID, CARRERA_ID, SALDO," 
					+ " TO_CHAR(F_INSCRIPCION,'DD/MM/YYYY') F_INSCRIPCION, TIPOALUMNO_ID "
					+ " FROM ENOC.INSCRITOS A "
					+ " WHERE RESIDENCIA_ID = 'E' "+ orden;
			lista = enocJdbc.query(comando, new InscritosMapper());
		} catch (Exception ex) {
			System.out.println("Error - aca.vista.spring.IncritosDao|getListInscritosExternos|:" + ex);
		}
		return lista;
	}
	
	public List<Inscritos> lisCondicionados(String periodo, String reporte, String orden) {

		List<Inscritos> lista = new ArrayList<Inscritos>();
		try {
			String comando = "SELECT DISTINCT(CODIGO_PERSONAL) AS CODIGO_PERSONAL,"
					+ " CARGA_ID, BLOQUE_ID, NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO, NOMBRE_LEGAL, COTEJADO," 
					+ " TO_CHAR(F_NACIMIENTO,'DD/MM/YYYY') AS F_NACIMIENTO,"
					+ " SEXO, ESTADO_CIVIL, RELIGION_ID, PAIS_ID, ESTADO_ID, CIUDAD_ID, NACIONALIDAD," 
					+ " CURP, MODALIDAD_ID, CLAS_FIN, RESIDENCIA_ID, DORMITORIO, PLAN_ID, CARRERA_ID, SALDO," 
					+ " TO_CHAR(F_INSCRIPCION,'DD/MM/YYYY') F_INSCRIPCION, TIPOALUMNO_ID "
					+ " FROM ENOC.INSCRITOS A "
					+ " WHERE CODIGO_PERSONAL IN (SELECT MATRICULA FROM ENOC.COND_ALUMNO WHERE PERIODO_ID = ? AND IDREPORTE = TO_NUMBER(?,'999')) "+ orden;
			Object[] parametros = new Object[] { periodo, reporte };
			lista = enocJdbc.query(comando, new InscritosMapper(), parametros);
		} catch (Exception ex) {
			System.out.println("Error - aca.vista.spring.IncritosDao|getListInscritosExternos|:" + ex);
		}
		return lista;
	}

	public List<Inscritos> getListExtSinRegModalidad(String modalidades, String fechaIni, String fechaFin, String orden) {
		List<Inscritos> lista = new ArrayList<Inscritos>();
		try {
			String comando = " SELECT DISTINCT(CODIGO_PERSONAL) AS CODIGO_PERSONAL,"
					+ " CARGA_ID, BLOQUE_ID, NOMBRE, APELLIDO_PATERNO, APELLIDO_MATERNO, NOMBRE_LEGAL, COTEJADO,"
					+ " TO_CHAR(F_NACIMIENTO,'DD/MM/YYYY') AS F_NACIMIENTO,"
					+ " SEXO, ESTADO_CIVIL, RELIGION_ID, PAIS_ID, ESTADO_ID, CIUDAD_ID, NACIONALIDAD,"
					+ " CURP, MODALIDAD_ID, CLAS_FIN, RESIDENCIA_ID, DORMITORIO, PLAN_ID, CARRERA_ID, SALDO,"
					+ " TO_CHAR(F_INSCRIPCION,'DD/MM/YYYY') F_INSCRIPCION, TIPOALUMNO_ID"
					+ " FROM ENOC.INSCRITOS"
					+ " WHERE RESIDENCIA_ID = 'E'"
					+ " AND MODALIDAD_ID IN ("+modalidades+")"
					+ " AND CODIGO_PERSONAL NOT IN (SELECT MATRICULA FROM ENOC.RES_DATOS)"
					+ " AND F_INSCRIPCION BETWEEN TO_DATE(?,'DD/MM/YYYY') AND TO_DATE(?,'DD/MM/YYYY') " + orden;
			Object[] parametros = new Object[] { fechaIni, fechaFin };
			lista = enocJdbc.query(comando, new InscritosMapper(), parametros);
		}catch (Exception ex) {
			System.out.println("Error - aca.residencia.ExternosUtil|getListExtSinRegModalidad|:" + ex);
		}
		return lista;
	}

	public List<Inscritos> getAlumnoCarrera(String carreraId, String orden) {

		List<Inscritos> lista = new ArrayList<Inscritos>();
		try {
			String comando = "SELECT DISTINCT(CODIGO_PERSONAL) AS CODIGO_PERSONAL, "
					+ " CARGA_ID,BLOQUE_ID,NOMBRE,APELLIDO_PATERNO, APELLIDO_MATERNO,NOMBRE_LEGAL,COTEJADO,"
					+ " TO_CHAR(F_NACIMIENTO,'DD/MM/YYYY') AS F_NACIMIENTO, "
					+ " SEXO, ESTADO_CIVIL, RELIGION_ID,PAIS_ID,ESTADO_ID, CIUDAD_ID,NACIONALIDAD,"
					+ " CURP, MODALIDAD_ID,CLAS_FIN,RESIDENCIA_ID, DORMITORIO,PLAN_ID,CARRERA_ID, SALDO,"
					+ " TO_CHAR(F_INSCRIPCION,'DD/MM/YYYY') F_INSCRIPCION, TIPOALUMNO_ID "
					+ " FROM ENOC.INSCRITOS WHERE CARRERA_ID = ? " + orden;
			Object[] parametros = new Object[] { carreraId };
			lista = enocJdbc.query(comando, new InscritosMapper(), parametros);
		} catch (Exception ex) {
			System.out.println("Error - aca.vista.spring.IncritosDao|getAlumnoCarrera|:" + ex);
		}
		return lista;
	}

	public List<Inscritos> getAlumnos(String orden) {

		List<Inscritos> lista = new ArrayList<Inscritos>();

		try {
			String comando = "SELECT DISTINCT(CODIGO_PERSONAL) AS CODIGO_PERSONAL, "
					+ " CARGA_ID,BLOQUE_ID,NOMBRE,APELLIDO_PATERNO, APELLIDO_MATERNO,NOMBRE_LEGAL,COTEJADO,"
					+ " TO_CHAR(F_NACIMIENTO,'DD/MM/YYYY') AS F_NACIMIENTO, "
					+ " SEXO, ESTADO_CIVIL, RELIGION_ID,PAIS_ID,ESTADO_ID, CIUDAD_ID,NACIONALIDAD,"
					+ " CURP, MODALIDAD_ID,CLAS_FIN,RESIDENCIA_ID, DORMITORIO,PLAN_ID,CARRERA_ID, SALDO,"
					+ " TO_CHAR(F_INSCRIPCION,'DD/MM/YYYY') F_INSCRIPCION, TIPOALUMNO_ID, ENOC.FACULTAD(CARRERA_ID) "
					+ " FROM ENOC.INSCRITOS " + orden;
			lista = enocJdbc.query(comando, new InscritosMapper());

		} catch (Exception ex) {
			System.out.println("Error - aca.vista.spring.IncritosDao|getAlumnos|:" + ex);

		}

		return lista;
	}

	public List<Inscritos> getAlumnoPais(String paisId, String orden) {

		List<Inscritos> lista = new ArrayList<Inscritos>();

		try {
			String comando = "SELECT DISTINCT(CODIGO_PERSONAL) AS CODIGO_PERSONAL, "
					+ " CARGA_ID,BLOQUE_ID,NOMBRE,APELLIDO_PATERNO, APELLIDO_MATERNO,NOMBRE_LEGAL,COTEJADO,"
					+ " TO_CHAR(F_NACIMIENTO,'DD/MM/YYYY') AS F_NACIMIENTO, "
					+ " SEXO, ESTADO_CIVIL, RELIGION_ID,PAIS_ID,ESTADO_ID, CIUDAD_ID,NACIONALIDAD,"
					+ " CURP, MODALIDAD_ID,CLAS_FIN,RESIDENCIA_ID, DORMITORIO,PLAN_ID,CARRERA_ID, SALDO,"
					+ " TO_CHAR(F_INSCRIPCION,'DD/MM/YYYY') F_INSCRIPCION, TIPOALUMNO_ID "
					+ " FROM ENOC.INSCRITOS WHERE PAIS_ID = ? " + orden;
			lista = enocJdbc.query(comando, new InscritosMapper(),paisId);///

		} catch (Exception ex) {
			System.out.println("Error - aca.vista.spring.IncritosDao|getAlumnoPais|:" + ex);

		}

		return lista;
	}

	public List<Inscritos> getListUbicacion(String orden) {

		List<Inscritos> lista = new ArrayList<Inscritos>();

		try {
			String comando = "SELECT DISTINCT(CODIGO_PERSONAL) AS CODIGO_PERSONAL, "
					+ " CARGA_ID,BLOQUE_ID,NOMBRE,APELLIDO_PATERNO, APELLIDO_MATERNO,NOMBRE_LEGAL,COTEJADO,"
					+ " TO_CHAR(F_NACIMIENTO,'DD/MM/YYYY') AS F_NACIMIENTO, "
					+ " SEXO, ESTADO_CIVIL, RELIGION_ID,PAIS_ID,ESTADO_ID, CIUDAD_ID,NACIONALIDAD,"
					+ " CURP, MODALIDAD_ID,CLAS_FIN,RESIDENCIA_ID, DORMITORIO,PLAN_ID,CARRERA_ID, SALDO,"
					+ " TO_CHAR(F_INSCRIPCION,'DD/MM/YYYY') F_INSCRIPCION, TIPOALUMNO_ID, ENOC.FACULTAD(CARRERA_ID) "
					+ " FROM ENOC.INSCRITOS WHERE MODALIDAD_ID = '1' AND CODIGO_PERSONAL IN "
					+ " (SELECT CODIGO_PERSONAL FROM ENOC.ALUM_UBICACION ) " + orden;
			lista = enocJdbc.query(comando, new InscritosMapper());

		} catch (Exception ex) {
			System.out.println("Error - aca.vista.spring.IncritosDao|getListAll|:" + ex);

		}

		return lista;
	}
	
	public List<Inscritos> getListaReporte2( String orden){
		 List<Inscritos> lista = new ArrayList<Inscritos>();
		 
		 try{
			 String comando = " SELECT DISTINCT(CODIGO_PERSONAL) AS CODIGO_PERSONAL,"
			 		+ " CARGA_ID,BLOQUE_ID,NOMBRE,APELLIDO_PATERNO, APELLIDO_MATERNO,NOMBRE_LEGAL,COTEJADO,"
			 		+ " TO_CHAR(F_NACIMIENTO,'DD/MM/YYYY') AS F_NACIMIENTO,"
			 		+ " SEXO, ESTADO_CIVIL, RELIGION_ID,PAIS_ID,ESTADO_ID, CIUDAD_ID,NACIONALIDAD,"
			 		+ " CURP, MODALIDAD_ID,CLAS_FIN,RESIDENCIA_ID, DORMITORIO,PLAN_ID,CARRERA_ID, SALDO,"
			 		+ " TO_CHAR(F_INSCRIPCION,'DD/MM/YYYY') F_INSCRIPCION, TIPOALUMNO_ID "
			 		+ " FROM ENOC.INSCRITOS "+orden;
			lista = enocJdbc.query(comando, new InscritosMapper());
		 
		 }catch(Exception ex){
			 System.out.println("Error - aca.vista.spring.IncritosDao|getListaReporte2|:"+ex);
		 }
		 return lista;
	 }

/*	
	 public List<Inscritos> getListaReporte2( String orden){
		 List<Inscritos> lista = new ArrayList<Inscritos>();
		 
		 try{
			 String comando =
				 "	SELECT DISTINCT(I.CODIGO_PERSONAL) AS CODIGO_PERSONAL," +
				 "	I.CARGA_ID, I.NOMBRE, I.APELLIDO_PATERNO, I.APELLIDO_MATERNO," +
				 "	I.SEXO, I.RESIDENCIA_ID," +
				 "	(SELECT NOMBRE_CORTO FROM ENOC.CAT_FACULTAD WHERE FACULTAD_ID IN" +
				 "	(SELECT FACULTAD_ID FROM ENOC.CAT_CARRERA WHERE CARRERA_ID = I.CARRERA_ID)) AS FACULTAD,"
				 +
				 "	(SELECT NOMBRE_CARRERA FROM ENOC.CAT_CARRERA WHERE CARRERA_ID = I.CARRERA_ID) AS CARRERA,"
				 +
				 "	(SELECT NOMBRE_RELIGION FROM ENOC.CAT_RELIGION WHERE RELIGION_ID = I.RELIGION_ID) AS RELIGION,"
				 + "	(SELECT NOMBRE_TIPO FROM ENOC.CAT_TIPOALUMNO WHERE TIPO_ID IN" +
				 "		(SELECT TIPO_ALUMNO FROM ENOC.ALUM_ACADEMICO WHERE CODIGO_PERSONAL = I.CODIGO_PERSONAL)) AS TIPO,"
				 +
				 "	(SELECT NOMBRE_MODALIDAD FROM ENOC.CAT_MODALIDAD WHERE MODALIDAD_ID = I.MODALIDAD_ID) AS MODALIDAD,"
				 +
				 "	(SELECT NOMBRE_PAIS FROM ENOC.CAT_PAIS WHERE PAIS_ID = I.PAIS_ID) AS PAIS,"
				 +
				 "	(SELECT NOMBRE_ESTADO FROM ENOC.CAT_ESTADO WHERE ESTADO_ID = I.ESTADO_ID AND PAIS_ID = I.PAIS_ID) AS ESTADO"
				 + "	FROM ENOC.INSCRITOS I "+orden;
			lista = enocJdbc.query(comando, new InscritosMapper());
		 
		 }catch(Exception ex){
			 System.out.println("Error - aca.vista.spring.IncritosDao|getListaReporte2|:"+ex);
		 }
		 return lista;
	 }
*/	 
	 
	 
	public List<Inscritos> getListAlumnos(String cursoClave, String orden) {

		List<Inscritos> lista = new ArrayList<Inscritos>();

		try {
			String comando = " SELECT * FROM ENOC.INSCRITOS WHERE CODIGO_PERSONAL IN "
					+ " (SELECT CODIGO_PERSONAL FROM ENOC.KRDX_CURSO_ACT "
					+ " WHERE  (SELECT CURSO_CLAVE FROM ENOC.MAPA_CURSO WHERE CURSO_ID = KRDX_CURSO_ACT.CURSO_ID) = ?"
					+ " " + " AND SUBSTR(CURSO_CARGA_ID,1,6)=INSCRITOS.CARGA_ID) " + orden;
			lista = enocJdbc.query(comando, new InscritosMapper(),cursoClave);

		} catch (Exception ex) {
			System.out.println("Error - aca.vista.spring.IncritosDao|getListAlumnos|:" + ex);
		}

		return lista;
	}

	// Numero de alumnos inscritos en cada paxxs
	public List<String> getPaisesRepresentados(String orden) {

		List<String> lista = new ArrayList<String>();

		try {
			String comando = " SELECT PAIS_ID||'%'||COUNT(DISTINCT(CODIGO_PERSONAL)) AS DATOS FROM ENOC.INSCRITOS GROUP BY PAIS_ID "
					+ orden;
			lista = enocJdbc.queryForList(comando, String.class);

		} catch (Exception ex) {
			System.out.println("Error - aca.vista.spring.IncritosDao|getPaisesRepresentados|:" + ex);

		}

		return lista;
	}

	public List<String> getListaCarreraPorCiclo(String cargas, String modalidades, String fechaIni, String fechaFin, String orden) {
		
		List<String> lista = new ArrayList<String>();

		try {
			String comando = "SELECT DISTINCT(A.CARRERA_ID) AS CARRERA FROM ENOC.INSCRITOS A, ENOC.ALUM_PLAN B"
					+ " WHERE B.CODIGO_PERSONAL = A.CODIGO_PERSONAL AND A.CARGA_ID IN (" + cargas + ")"
					+ " 	AND A.MODALIDAD_ID IN (" + modalidades + ") "
					+ " 	AND B.PLAN_ID = A.PLAN_ID AND B.ESTADO = '1'" + "   AND A.F_INSCRIPCION BETWEEN TO_DATE(? ,'DD/MM/YYYY') AND TO_DATE(? ,'DD/MM/YYYY')"
					+ " GROUP BY A.CARRERA_ID " + orden;
			lista = enocJdbc.queryForList(comando, String.class,fechaIni,fechaFin);///
		} catch (Exception ex) {
			System.out.println("Error - aca.vista.spring.IncritosDao|getListaCarreraPorCiclo|:" + ex);

		}
		return lista;
	}

	public HashMap<String, String> getMapaCantidadPorCiclo(String cargas, String modalidades, String estado) {
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista = new ArrayList<aca.Mapa>();

		try {
			String comando = " SELECT CARRERA_ID||'->'||CICLO AS LLAVE, COUNT(*) AS VALOR FROM ENOC.ALUM_ESTADO"
					+ " WHERE CARGA_ID IN("+ cargas + ")"
					+ " AND MODALIDAD_ID IN (" + modalidades + ")"
					+ " AND ESTADO = ?"
					+ " GROUP BY CARRERA_ID, CICLO";
			lista = enocJdbc.query(comando, new aca.MapaMapper(),estado); ///
			for(aca.Mapa map : lista) {
				mapa.put(map.getLlave(), (String)map.getValor());
			}

		} catch (Exception ex) {
			System.out.println("Error - aca.vista.spring.IncritosDao|getMapaCantidadPorCiclo|:" + ex);

		}
		return mapa;
	}

	public HashMap<String, String> getMapTipo(String orden) {

		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista 	= new ArrayList<aca.Mapa>();
		try {
			String comando = "SELECT CODIGO_PERSONAL AS LLAVE, TIPO_ALUMNO AS VALOR FROM ENOC.ALUM_ACADEMICO"
					+ " WHERE CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM ENOC.INSCRITOS) " + orden;
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for(aca.Mapa map : lista){
				mapa.put(map.getLlave(), (String)map.getValor());
			}
		} catch (Exception ex) {
			System.out.println("Error - aca.vista.spring.IncritosDao|getMapTipo|:" + ex);
		}

		return mapa;
	}
	
	public HashMap<String, String> mapaAlumPorPaises() {

		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista 	= new ArrayList<aca.Mapa>();
		try {
			String comando = "SELECT PAIS_ID AS LLAVE, COUNT(CODIGO_PERSONAL) AS VALOR FROM ENOC.INSCRITOS GROUP BY PAIS_ID";
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for(aca.Mapa map : lista){
				mapa.put(map.getLlave(), (String)map.getValor());
			}
		} catch (Exception ex) {
			System.out.println("Error - aca.vista.spring.IncritosDao|mapaAlumPorPaises|:" + ex);
		}

		return mapa;
	}

	public HashMap<String, String> mapAlumnoCarrera() {

		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista 	= new ArrayList<aca.Mapa>();
		
		try {
			String comando = "SELECT CODIGO_PERSONAL AS LLAVE, CARRERA_ID AS VALOR FROM ENOC.INSCRITOS ";
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			
			for(aca.Mapa map : lista){
				mapa.put(map.getLlave(), (String)map.getValor());
			}
		} catch (Exception ex) {
			System.out.println("Error - aca.vista.spring.IncritosDao|mapAlumnoCarrera|:" + ex);
		}

		return mapa;
	}

	public HashMap<String, String> getMapaInscritos(String modalidades) {
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista 	= new ArrayList<aca.Mapa>();
		
		try {
			String comando = "SELECT DISTINCT(CODIGO_PERSONAL) AS LLAVE, TO_CHAR(F_INSCRIPCION,'DD/MM/YYYY') AS VALOR FROM ENOC.INSCRITOS WHERE MODALIDAD_ID IN ("
					+ modalidades + ")";
			lista = enocJdbc.query(comando, new aca.MapaMapper());			
			for(aca.Mapa map : lista){
				mapa.put(map.getLlave(), (String)map.getValor());
			}
			
		} catch (Exception ex) {
			System.out.println("Error - aca.vista.spring.IncritosDao|getMapaInscritos|:" + ex);

		}
		return mapa;
	}

	public HashMap<String, String> getMapaTodosInscritos() {
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista 	= new ArrayList<aca.Mapa>();
		
		try {
			String comando = "SELECT DISTINCT(CODIGO_PERSONAL) AS LLAVE, TO_CHAR(F_INSCRIPCION,'DD/MM/YYYY') AS VALOR FROM ENOC.INSCRITOS";
			lista = enocJdbc.query(comando, new aca.MapaMapper());			
			for(aca.Mapa map : lista){
				mapa.put(map.getLlave(), (String)map.getValor());
			}
			
		} catch (Exception ex) {
			System.out.println("Error - aca.vista.spring.IncritosDao|getMapaTodosInscritos|:" + ex);
			
		}
		return mapa;
	}

	public HashMap<String, String> mapInscritoUltimaFecha() {
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista 	= new ArrayList<aca.Mapa>();
		
		try {
			String comando = "SELECT CODIGO_PERSONAL AS LLAVE, TO_CHAR(MAX(F_INSCRIPCION),'DD/MM/YYYY') AS VALOR FROM ENOC.INSCRITOS GROUP BY CODIGO_PERSONAL";
			lista = enocJdbc.query(comando, new aca.MapaMapper());			
			for(aca.Mapa map : lista){
				mapa.put(map.getLlave(), (String)map.getValor());
			}	

		} catch (Exception ex) {
			System.out.println("Error - aca.vista.spring.IncritosDao|mapInscritoUltimaFecha|:" + ex);

		}
		return mapa;
	}

	public HashMap<String, String> getMapaInscritos() {
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista 	= new ArrayList<aca.Mapa>();		
		try {
			String comando = "SELECT DISTINCT(CODIGO_PERSONAL) AS LLAVE, TO_CHAR(F_INSCRIPCION,'DD/MM/YYYY') AS VALOR FROM ENOC.INSCRITOS";
			lista = enocJdbc.query(comando, new aca.MapaMapper());			
			for(aca.Mapa map : lista){
				mapa.put(map.getLlave(), (String)map.getValor());
			}
		} catch (Exception ex) {
			System.out.println("Error - aca.vista.spring.IncritosDao|getMapaInscritos|:" + ex);
		}
		return mapa;
	}
	
	public HashMap<String, String> mapaEdades() {
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista 	= new ArrayList<aca.Mapa>();		
		try {
			String comando = "SELECT DISTINCT(CODIGO_PERSONAL) AS LLAVE, ENOC.ALUM_EDAD(CODIGO_PERSONAL) AS VALOR FROM ENOC.INSCRITOS";
			lista = enocJdbc.query(comando, new aca.MapaMapper());			
			for(aca.Mapa map : lista){
				mapa.put(map.getLlave(), (String)map.getValor());
			}		

		} catch (Exception ex) {
			System.out.println("Error - aca.vista.spring.IncritosDao|getMapaInscritos|:" + ex);
		}
		return mapa;
	}

	public HashMap<String, String> mapaInscDormiEntreFechas(String residencia, String fechaIni, String fechaFin, String modalidades) {
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista 	= new ArrayList<aca.Mapa>();

		try {
			String comando = " SELECT DORMITORIO AS LLAVE, COUNT(DISTINCT(CODIGO_PERSONAL)) AS VALOR FROM ENOC.INSCRITOS"
					+ " WHERE RESIDENCIA_ID = ?"
					+ " AND F_INSCRIPCION BETWEEN TO_DATE(?,'DD/MM/YYYY')"
					+ " AND TO_DATE(?,'DD/MM/YYYY')"
					+ " AND MODALIDAD_ID IN ("+modalidades+")"
					+ " GROUP BY DORMITORIO";
			lista = enocJdbc.query(comando, new aca.MapaMapper(),residencia,fechaIni,fechaFin);	///		
			for(aca.Mapa map : lista){
				mapa.put(map.getLlave(), (String)map.getValor());
			}
		} catch (Exception ex) {
			System.out.println("Error - aca.vista.spring.IncritosDao|mapaInscDormiEntreFechas|:" + ex);
		}
		return mapa;
	}

	public HashMap<String, String> getMapaInscritosPrepa(String modalidades) {
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista 	= new ArrayList<aca.Mapa>();
		
		try {
			String comando = "SELECT CODIGO_PERSONAL AS LLAVE, CODIGO_PERSONAL AS VALOR FROM ENOC.INSCRITOS "
					+ " WHERE (SELECT NIVEL_ID FROM ENOC.CAT_CARRERA WHERE CARRERA_ID = INSCRITOS.CARRERA_ID) = 1 AND MODALIDAD_ID IN ("+modalidades+")";
			lista = enocJdbc.query(comando, new aca.MapaMapper());			
			for(aca.Mapa map : lista){
				mapa.put(map.getLlave(), (String)map.getValor());
			}
		} catch (Exception ex) {
			System.out.println("Error - aca.vista.spring.IncritosDao|getMapaInscritosPrepa|:" + ex);
		}
		return mapa;
	}
	
	public HashMap<String, String> getMapaInscritosPrepa() {
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista 	= new ArrayList<aca.Mapa>();
		
		try {
			String comando = "SELECT CODIGO_PERSONAL AS LLAVE, CODIGO_PERSONAL AS VALOR FROM ENOC.INSCRITOS "
					+ " WHERE (SELECT NIVEL_ID FROM ENOC.CAT_CARRERA WHERE CARRERA_ID = INSCRITOS.CARRERA_ID) = 1";
			lista = enocJdbc.query(comando, new aca.MapaMapper());			
			for(aca.Mapa map : lista){
				mapa.put(map.getLlave(), (String)map.getValor());
			}
		} catch (Exception ex) {
			System.out.println("Error - aca.vista.spring.IncritosDao|getMapaInscritosPrepa|:" + ex);
		}
		return mapa;
	}

	public HashMap<String, String> mapInscritosEnPais(){
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista 	= new ArrayList<aca.Mapa>();
		
		try {
			String comando = "SELECT PAIS_ID AS LLAVE, COUNT(CODIGO_PERSONAL) AS VALOR FROM ENOC.INSCRITOS GROUP BY PAIS_ID";
			lista = enocJdbc.query(comando, new aca.MapaMapper());			
			for(aca.Mapa map : lista){
				mapa.put(map.getLlave(), (String)map.getValor());
			}		

		} catch (Exception ex) {
			System.out.println("Error - aca.vista.spring.IncritosDao|mapInscritosEnPais|:" + ex);
		}
		return mapa;
	}

	public List<Inscritos> getInscritosSeguro(String orden) {

		List<Inscritos> lista = new ArrayList<Inscritos>();

		try {
			String comando = " SELECT"
					+ " CARGA_ID,BLOQUE_ID,CODIGO_PERSONAL,NOMBRE,APELLIDO_PATERNO, APELLIDO_MATERNO,NOMBRE_LEGAL,COTEJADO,"
					+ " TO_CHAR(F_NACIMIENTO,'DD/MM/YYYY') F_NACIMIENTO,"
					+ " SEXO, ESTADO_CIVIL, RELIGION_ID,PAIS_ID,ESTADO_ID, CIUDAD_ID,NACIONALIDAD,"
					+ " CURP, MODALIDAD_ID,CLAS_FIN,RESIDENCIA_ID,DORMITORIO,PLAN_ID,CARRERA_ID, SALDO,"
					+ " TO_CHAR(F_INSCRIPCION,'DD/MM/YYYY') F_INSCRIPCION, TIPOALUMNO_ID" + " FROM ENOC.INSCRITOS "
					+ orden;
			lista = enocJdbc.query(comando, new InscritosMapper());

		} catch (Exception ex) {
			System.out.println("Error - aca.vista.spring.InscritosDao|getInscritosSeguro|:" + ex);
		}

		return lista;
	}
	
	public HashMap<String,Inscritos> getMapCarreraInscritos() {
		
		HashMap<String, Inscritos> map		= new HashMap<String,Inscritos>();
		List<Inscritos> list = new ArrayList<Inscritos>();

		try{
			String comando = " SELECT"
					+ " CARGA_ID,BLOQUE_ID,CODIGO_PERSONAL,NOMBRE,APELLIDO_PATERNO, APELLIDO_MATERNO,NOMBRE_LEGAL,COTEJADO,"
					+ " TO_CHAR(F_NACIMIENTO,'DD/MM/YYYY') F_NACIMIENTO,"
					+ " SEXO, ESTADO_CIVIL, RELIGION_ID,PAIS_ID,ESTADO_ID, CIUDAD_ID,NACIONALIDAD,"
					+ " CURP, MODALIDAD_ID,CLAS_FIN,RESIDENCIA_ID,DORMITORIO,PLAN_ID,CARRERA_ID, SALDO,"
					+ " TO_CHAR(F_INSCRIPCION,'DD/MM/YYYY') F_INSCRIPCION, TIPOALUMNO_ID FROM ENOC.INSCRITOS ";
					
			list = enocJdbc.query(comando, new InscritosMapper());
			for (Inscritos inscrito : list ) {
				map.put(inscrito.getCodigoPersonal(), inscrito );
			}
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.InscritosDao|getMapCarreraInscritos|:"+ex);
		}
		return map;
	}
	
	public HashMap<String,String> mapaFormal() {
		
		HashMap<String, String> map		= new HashMap<String,String>();
		List<aca.Mapa> lista 			= new ArrayList<aca.Mapa>();
		
		try{
			String comando = "SELECT CODIGO_PERSONAL AS LLAVE, COUNT(*) AS VALOR FROM ENOC.INSCRITOS"
					+ " WHERE SUBSTR(CARGA_ID,5,1) = '1' OR SUBSTR(CARGA_ID,5,1) = '3'"
					+ " GROUP BY CODIGO_PERSONAL";					
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for (aca.Mapa mapa: lista ) {
				map.put(mapa.getLlave(), mapa.getValor());
			}
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.InscritosDao|mapInscritos1A|:"+ex);
		}
		return map;
	}

	public HashMap<String,String> mapaCimum() {
		
		HashMap<String, String> map		= new HashMap<String,String>();
		List<aca.Mapa> lista 			= new ArrayList<aca.Mapa>();
		
		try{
			String comando = "SELECT CODIGO_PERSONAL AS LLAVE, COUNT(*) AS VALOR FROM ENOC.INSCRITOS"
					+ " WHERE SUBSTR(CARGA_ID,5,1) = '2' GROUP BY CODIGO_PERSONAL";	
			
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for (aca.Mapa mapa: lista ) {
				map.put(mapa.getLlave(), mapa.getValor());
			}
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.InscritosDao|mapaCimum|:"+ex);
		}
		return map;
	}
	
	public HashMap<String,String> mapaIdiomas() {
		
		HashMap<String, String> map		= new HashMap<String,String>();
		List<aca.Mapa> lista 			= new ArrayList<aca.Mapa>();
		
		try{
			String comando = "SELECT CODIGO_PERSONAL AS LLAVE, COUNT(*) AS VALOR FROM ENOC.INSCRITOS"
					+ " WHERE SUBSTR(CARGA_ID,5,1) = '4' GROUP BY CODIGO_PERSONAL";	
			
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for (aca.Mapa mapa: lista ) {
				map.put(mapa.getLlave(), mapa.getValor());
			}
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.InscritosDao|mapaIdiomas|:"+ex);
		}
		return map;
	}
	
	public HashMap<String,String> mapaNacionalidad() {
		
		HashMap<String, String> map		= new HashMap<String,String>();
		List<aca.Mapa> lista 			= new ArrayList<aca.Mapa>();
		
		try{
			String comando = "SELECT CODIGO_PERSONAL AS LLAVE, NACIONALIDAD AS VALOR FROM ENOC.INSCRITOS";			
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for (aca.Mapa mapa: lista ) {
				map.put(mapa.getLlave(), mapa.getValor());
			}
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.InscritosDao|mapaNacionalidad|:"+ex);
		}
		return map;
	}
	
	public HashMap<String,Integer> mapaAsignados(String fechaIni, String fechaFin) {
		
		HashMap<String, Integer> map	= new HashMap<String,Integer>();
		List<String> lista 				= new ArrayList<String>();
		
		try{
			String comando = " SELECT DISTINCT(DORMITORIO_ID) FROM ENOC.INT_CUARTO";
			lista =	 enocJdbc.queryForList(comando, String.class);
			for (String dormi : lista){	
				int asignados = 0;
				comando = "SELECT COUNT(*) FROM ENOC.INSCRITOS WHERE RESIDENCIA_ID = 'I' AND DORMITORIO = ?" 
						+ "AND F_INSCRIPCION BETWEEN TO_DATE(?,'DD/MM/YYYY') AND TO_DATE(?,'DD/MM/YYYY')"
						+ "AND CODIGO_PERSONAL IN(SELECT CODIGO_PERSONAL FROM ENOC.INT_ALUMNO WHERE DORMITORIO_ID = ?)";			
				
				asignados = enocJdbc.queryForObject(comando,Integer.class,dormi,fechaIni,fechaFin,dormi);	///
				
				map.put(dormi, asignados);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.vista.spring.InscritosDao|mapaAsignados|:"+ex);
		}
		return map;
	}

}