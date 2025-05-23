package aca.trabajo.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import aca.Mapa;
import aca.alumno.spring.AlumPersonal;

@Component
public class TrabAlumDao {

	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;

	public boolean insertReg(TrabAlum alum) {
		boolean ok = false;

		try {
			String comando = "INSERT INTO ENOC.TRAB_ALUM(DEPT_ID, CAT_ID, MATRICULA, PERIODO_ID, HORAS, ESTADO, PAGO)"
					+ " VALUES( ?, ?, ?, ?, ?, ?, ? )";
			Object[] parametros = new Object[] { alum.getDeptId(), alum.getCatId(), alum.getMatricula(),
					alum.getPeriodoId(), alum.getHoras(), alum.getEstado(), alum.getPago() };
			if (enocJdbc.update(comando, parametros) == 1) {
				ok = true;
			}
		} catch (Exception ex) {
			System.out.println("Error - aca.categoria.spring.TrabAlumDao|insertReg|:" + ex);
		}

		return ok;
	}

	public boolean updateReg(TrabAlum alum) {
		boolean ok = false;
		try {
			String comando = "UPDATE ENOC.TRAB_ALUM SET DEPT_ID = ?, CAT_ID = ?, PERIODO_ID = ?, HORAS = ?, ESTADO = ?, PAGO = ? WHERE MATRICULA = ? AND DEPT_ID = ? AND CAT_ID = ? AND PERIODO_ID = ?";
			Object[] parametros = new Object[] { alum.getDeptId(), alum.getCatId(), alum.getPeriodoId(), alum.getHoras(), alum.getEstado(), alum.getPago(),
												alum.getMatricula(), alum.getDeptId(), alum.getCatId(), alum.getPeriodoId() };
			if (enocJdbc.update(comando, parametros) == 1) {
				ok = true;
			}
		} catch (Exception ex) {
			System.out.println("Error - aca.trabajo.spring.TrabAlumDao|updateReg|:" + ex);
		}

		return ok;
	}

	public boolean deleteReg(String matricula, String periodoId, String deptId, String catId) {
		boolean ok = false;
		try {
			String comando = "DELETE FROM ENOC.TRAB_ALUM WHERE MATRICULA = ? AND PERIODO_ID = ? AND DEPT_ID = TO_NUMBER(?,'999') AND CAT_ID = TO_NUMBER(?,'999')";
			Object[] parametros = new Object[] { matricula, periodoId, deptId, catId };
			if (enocJdbc.update(comando, parametros) == 1) {
				ok = true;
			}
		} catch (Exception ex) {
			System.out.println("Error - aca.trabajo.spring.TrabAlumDao|deleteReg|:" + ex);
		}

		return ok;
	}

	public TrabAlum mapeaRegId(String matricula, String deptId, String catId, String periodoId) {

		TrabAlum alum = new TrabAlum();
		try {
			String comando = "SELECT COUNT(*) FROM ENOC.TRAB_ALUM WHERE MATRICULA = ? AND DEPT_ID = TO_NUMBER(?,'999') AND CAT_ID = TO_NUMBER(?,'999') AND PERIODO_ID = TO_NUMBER(?,'999')";
			Object[] parametros = new Object[] { matricula, deptId, catId, periodoId };
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1) {
				comando = "SELECT DEPT_ID, CAT_ID, MATRICULA, PERIODO_ID, HORAS, ESTADO, PAGO "
						+ "FROM ENOC.TRAB_ALUM "
						+ "WHERE MATRICULA = ? AND "
						+ "DEPT_ID = TO_NUMBER(?,'999') AND "
						+ "CAT_ID = TO_NUMBER(?,'999') AND "
						+ "PERIODO_ID = TO_NUMBER(?,'999')";
				alum = enocJdbc.queryForObject(comando, new TrabAlumMapper(), parametros);
			}
		} catch (Exception ex) {
			System.out.println("Error - aca.trabajo.spring.TrabAlumDao|mapeaRegId|:" + ex);
			ex.printStackTrace();
		}

		return alum;
	}

	public TrabAlum mapeaRegId(String matricula, String estado) {

		TrabAlum alum = new TrabAlum();
		try {
			String comando = "SELECT COUNT(*) FROM ENOC.TRAB_ALUM WHERE MATRICULA = ? AND ESTADO = ?";
			Object[] parametros = new Object[] { matricula, estado };
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1) {
				comando = "SELECT DEPT_ID, CAT_ID, MATRICULA, PERIODO_ID, HORAS, ESTADO, PAGO "
						+ "FROM ENOC.TRAB_ALUM "
						+ "WHERE MATRICULA = ? AND ESTADO = ?";
				alum = enocJdbc.queryForObject(comando, new TrabAlumMapper(), parametros);
			}
		} catch (Exception ex) {
			System.out.println("Error - aca.trabajo.spring.TrabAlumDao|mapeaRegId|:" + ex);
			ex.printStackTrace();
		}

		return alum;
	}

	public boolean existeReg(String matricula, String periodoId, String deptId, String catId) {
		boolean ok = false;
		try {
			String comando = "SELECT COUNT(MATRICULA) FROM ENOC.TRAB_ALUM WHERE MATRICULA = ? AND PERIODO_ID = ? AND DEPT_ID = TO_NUMBER(?,'999') AND CAT_ID = TO_NUMBER(?,'999')";
			Object[] parametros = new Object[] { matricula, periodoId, deptId, catId };
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1) {
				ok = true;
			}
		} catch (Exception ex) {
			System.out.println("Error - aca.trabajo.spring.TrabAlumDao|existeReg|:" + ex);
		}

		return ok;
	}

	public boolean existeEnPeriodo(String matricula, String periodoId) {
		boolean ok = false;
		try {
			String comando = "SELECT COUNT(MATRICULA) FROM ENOC.TRAB_ALUM WHERE MATRICULA = ? AND PERIODO_ID = ?";
			Object[] parametros = new Object[] { matricula, periodoId};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1) {
				ok = true;
			}
		} catch (Exception ex) {
			System.out.println("Error - aca.trabajo.spring.TrabAlumDao|existeEnPeriodo|:" + ex);
		}

		return ok;
	}

	public boolean existeActivo(String matricula) {
		boolean ok = false;
		try {
			String comando = "SELECT COUNT(MATRICULA) FROM ENOC.TRAB_ALUM WHERE MATRICULA = ? AND ESTADO = 'A'";
			Object[] parametros = new Object[] { matricula};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1) {
				ok = true;
			}
		} catch (Exception ex) {
			System.out.println("Error - aca.trabajo.spring.TrabAlumDao|existeActivo|:" + ex);
		}

		return ok;
	}
	
	public boolean existeRegDept(String matricula, String deptId, String catId) {
		boolean ok = false;
		try {
			String comando = "SELECT COUNT(MATRICULA) FROM ENOC.TRAB_ALUM WHERE MATRICULA = ? AND DEPT_ID = TO_NUMBER(?,'999') AND CAT_ID = TO_NUMBER(?,'999')";
			Object[] parametros = new Object[] { matricula, deptId, catId};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1) {
				ok = true;
			}
		} catch (Exception ex) {
			System.out.println("Error - aca.trabajo.spring.TrabAlumDao|existeRegDept|:" + ex);
		}

		return ok;
	}

	public String maximoReg() {
		String maximo = "01";
		try {
			String comando = "SELECT COALESCE(MAX(MATRICULA)+1,1) AS MAXIMO FROM ENOC.TRAB_ALUM";
			if (enocJdbc.queryForObject(comando, Integer.class) >= 1) {
				maximo = enocJdbc.queryForObject(comando, String.class);
				if (maximo.length() == 1)
					maximo = "0" + maximo;
			}
		} catch (Exception ex) {
			System.out.println("Error - aca.trabajo.spring.TrabAlumDao|maximoReg|:" + ex);
		}

		return maximo;
	}

	// Llena el listor con todos los elementos de la tabla
	public List<TrabAlum> lisTodos(String orden) {

		List<TrabAlum> lista = new ArrayList<TrabAlum>();
		try {
			String comando = "SELECT DEPT_ID, CAT_ID, MATRICULA, PERIODO_ID, HORAS, ESTADO, PAGO FROM ENOC.TRAB_ALUM " + orden;
			lista = enocJdbc.query(comando, new TrabAlumMapper());
		} catch (Exception ex) {
			System.out.println("Error - aca.trabajo.spring.TrabAlumDao|lisTodos|:" + ex);
		}
		return lista;
	}
	
	public List<TrabAlum> lisActivos(String orden) {

		List<TrabAlum> lista = new ArrayList<TrabAlum>();
		try {
			String comando = "SELECT DEPT_ID, CAT_ID, MATRICULA, PERIODO_ID, HORAS, ESTADO, PAGO FROM ENOC.TRAB_ALUM WHERE ESTADO = 'A'" + orden;
			lista = enocJdbc.query(comando, new TrabAlumMapper());
		} catch (Exception ex) {
			System.out.println("Error - aca.trabajo.spring.TrabAlumDao|lisActivos|:" + ex);
		}
		return lista;
	}

	public List<TrabAlum> lisPorAlumno(String codigoAlumno, String orden) {

		List<TrabAlum> lista = new ArrayList<TrabAlum>();
		try {
			String comando = "SELECT DEPT_ID, CAT_ID, MATRICULA, PERIODO_ID, HORAS, ESTADO, PAGO FROM ENOC.TRAB_ALUM WHERE MATRICULA = ?"
					+ orden;
			Object[] parametros = new Object[] { codigoAlumno };
			lista = enocJdbc.query(comando, new TrabAlumMapper(), parametros);
		} catch (Exception ex) {
			System.out.println("Error - aca.trabajo.spring.TrabAlumDao|lisPorAlumno|:" + ex);
		}
		return lista;
	}

	public List<TrabAlum> lisPorAlumno(String codigoAlumno, String periodoId, String orden) {

		List<TrabAlum> lista = new ArrayList<TrabAlum>();
		try {
			String comando = "SELECT DEPT_ID, CAT_ID, MATRICULA, PERIODO_ID, HORAS, ESTADO, PAGO FROM ENOC.TRAB_ALUM WHERE MATRICULA = ? AND PERIODO_ID = ?"
					+ orden;
			Object[] parametros = new Object[] { codigoAlumno, periodoId };
			lista = enocJdbc.query(comando, new TrabAlumMapper(), parametros);
		} catch (Exception ex) {
			System.out.println("Error - aca.trabajo.spring.TrabAlumDao|lisPorAlumno|:" + ex);
		}
		return lista;
	}

	// Llena el listor con todos los elementos de la tabla
	public List<TrabAlum> lisPorDept(String deptId, String orden) {

		List<TrabAlum> lista = new ArrayList<TrabAlum>();
		try {
			Object[] parametros = new Object[] { deptId };
			String comando = "SELECT CAT_ID, MATRICULA, PERIODO_ID, HORAS, ESTADO, PAGO FROM ENOC.TRAB_ALUM WHERE DEPT_ID = ?" + orden;
			lista = enocJdbc.query(comando, new TrabAlumMapper(), parametros);
		} catch (Exception ex) {
			System.out.println("Error - aca.trabajo.spring.TrabAlumDao|lisTodos|:" + ex);
		}
		return lista;
	}

	// Llena el listor con todos los elementos de la tabla
	public List<TrabAlum> lisPorDeptyPeriodo(String periodoId, String deptId, String orden) {

		List<TrabAlum> lista = new ArrayList<TrabAlum>();
		try {
			Object[] parametros = new Object[] { periodoId, deptId };
			String comando = "SELECT t.DEPT_ID, t.CAT_ID, t.MATRICULA, t.PERIODO_ID, t.HORAS, t.ESTADO, t.PAGO"
							+ " FROM ENOC.TRAB_ALUM t"
							+ " JOIN ENOC.ALUM_PERSONAL a ON t.MATRICULA = a.CODIGO_PERSONAL"
							+ " WHERE t.PERIODO_ID = ?  AND t.DEPT_ID = ? AND t.ESTADO = 'A'"
							+orden;
			lista = enocJdbc.query(comando, new TrabAlumMapper(), parametros);
		} catch (Exception ex) {
			System.out.println("Error - aca.trabajo.spring.TrabAlumDao|lisTodos|:" + ex);
		}
		return lista;
	}

	public List<TrabAlum> lisPorDeptyPeriodoCategoria(String periodoId, String deptId, String catId, String orden) {

		List<TrabAlum> lista = new ArrayList<TrabAlum>();
		try {
			Object[] parametros = new Object[] { periodoId, deptId, catId };
			String comando = "SELECT t.DEPT_ID, t.CAT_ID, t.MATRICULA, t.PERIODO_ID, t.HORAS, t.ESTADO, t.PAGO"
							+ " FROM ENOC.TRAB_ALUM t"
							+ " JOIN ENOC.ALUM_PERSONAL a ON t.MATRICULA = a.CODIGO_PERSONAL"
							+ " WHERE t.PERIODO_ID = ?  AND t.DEPT_ID = ? AND t.CAT_ID = ? AND t.ESTADO = 'A'"
							+orden;
			lista = enocJdbc.query(comando, new TrabAlumMapper(), parametros);
		} catch (Exception ex) {
			System.out.println("Error - aca.trabajo.spring.TrabAlumDao|lisTodos|:" + ex);
		}
		return lista;
	}

	// Llena el listor con todos los elementos de la tabla
	public List<TrabAlum> lisPorPeriodo(String periodoId, String orden) {

		List<TrabAlum> lista = new ArrayList<TrabAlum>();
		try {
			Object[] parametros = new Object[] { periodoId };
			String comando = "SELECT t.DEPT_ID, t.CAT_ID, t.MATRICULA, t.PERIODO_ID, t.HORAS, t.ESTADO, t.PAGO"
							+ " FROM ENOC.TRAB_ALUM t"
							+ " JOIN ENOC.ALUM_PERSONAL a ON t.MATRICULA = a.CODIGO_PERSONAL"
							+ " WHERE t.PERIODO_ID = ? AND t.ESTADO = 'A'"
							+orden;
			lista = enocJdbc.query(comando, new TrabAlumMapper(), parametros);
		} catch (Exception ex) {
			System.out.println("Error - aca.trabajo.spring.TrabAlumDao|lisPorPeriodo|:" + ex);
		}
		return lista;
	}

	public List<TrabAlum> lisPorSupervisor(String usuario, String periodoId, String orden) {

		List<TrabAlum> lista = new ArrayList<TrabAlum>();
		try {
			Object[] parametros = new Object[] { usuario, periodoId };
			String comando = "SELECT DEPT_ID, CAT_ID, MATRICULA, PERIODO_ID, HORAS, ESTADO, PAGO FROM ENOC.TRAB_ALUM WHERE MATRICULA IN(SELECT MATRICULA FROM TRAB_INFORME_ALUM WHERE USUARIO = ? AND PERIODO_ID = TO_NUMBER(?,'999')) AND ESTADO = 'A'"
					+ orden;
			lista = enocJdbc.query(comando, new TrabAlumMapper(), parametros);
		} catch (Exception ex) {
			System.out.println("Error - aca.trabajo.spring.TrabAlumDao|lisPorSupervisor|:" + ex);
		}
		return lista;
	}

	public List<TrabAlum> lisPorSupervisor(String usuario, String periodoId, String deptId, String orden) {

		List<TrabAlum> lista = new ArrayList<TrabAlum>();
		try {
			Object[] parametros = new Object[] { usuario, periodoId, deptId };
			String comando = "SELECT DEPT_ID, CAT_ID, MATRICULA, PERIODO_ID, HORAS, ESTADO, PAGO FROM ENOC.TRAB_ALUM WHERE MATRICULA IN(SELECT MATRICULA FROM TRAB_INFORME_ALUM WHERE USUARIO = ? AND PERIODO_ID = TO_NUMBER(?,'999') AND DEPT_ID = TO_NUMBER(?, '999')) AND ESTADO = 'A'"
					+ orden;
			lista = enocJdbc.query(comando, new TrabAlumMapper(), parametros);
		} catch (Exception ex) {
			System.out.println("Error - aca.trabajo.spring.TrabAlumDao|lisPorSupervisor|:" + ex);
		}
		return lista;
	}

	public List<TrabAlum> lisPorSupervisor(String usuario, String periodoId, String deptId, String catId, String orden) {

		List<TrabAlum> lista = new ArrayList<TrabAlum>();
		try {
			Object[] parametros = new Object[] { usuario, periodoId, deptId, catId };
			String comando = "SELECT DEPT_ID, CAT_ID, MATRICULA, PERIODO_ID, HORAS, ESTADO, PAGO FROM ENOC.TRAB_ALUM WHERE MATRICULA IN(SELECT MATRICULA FROM TRAB_INFORME_ALUM WHERE USUARIO = ? AND PERIODO_ID = TO_NUMBER(?,'999') AND DEPT_ID = TO_NUMBER(?, '999') AND CAT_ID = TO_NUMBER(?, '999')) AND ESTADO = 'A'"
					+ orden;
			lista = enocJdbc.query(comando, new TrabAlumMapper(), parametros);
		} catch (Exception ex) {
			System.out.println("Error - aca.trabajo.spring.TrabAlumDao|lisPorSupervisor|:" + ex);
		}
		return lista;
	}

	public HashMap<String, String> mapaAlumNombre(String orden) {

		HashMap<String, String> map = new HashMap<String, String>();
		List<aca.Mapa> list = new ArrayList<aca.Mapa>();
		try {
			String comando = "SELECT TRAB_ALUM.MATRICULA AS LLAVE, ALUM_PERSONAL.NOMBRE || ' ' || ALUM_PERSONAL.APELLIDO_MATERNO || ' ' || ALUM_PERSONAL.APELLIDO_PATERNO AS VALOR FROM TRAB_ALUM INNER JOIN ALUM_PERSONAL ON TRAB_ALUM.MATRICULA = ALUM_PERSONAL.CODIGO_PERSONAL";
			list = enocJdbc.query(comando, new aca.MapaMapper());
			for (Mapa alum : list) {
				map.put(alum.getLlave(), alum.getValor());
			}
		} catch (Exception ex) {
			System.out.println("Error - aca.trabajo.spring.TrabAlumDao|mapaAlumNombre|:" + ex);
		}
		return map;
	}
	
	public HashMap<String, String> mapaPorCat(String orden) {

		HashMap<String, String> map = new HashMap<String, String>();
		List<aca.Mapa> list = new ArrayList<aca.Mapa>();
		try {
			String comando = "SELECT DISTINCT CAT_ID AS LLAVE, MATRICULA AS VALOR FROM ENOC.TRAB_ALUM";
			list = enocJdbc.query(comando, new aca.MapaMapper());
			for (Mapa alum : list) {
				map.put(alum.getLlave(), alum.getValor());
				
			}
		} catch (Exception ex) {
			System.out.println("Error - aca.trabajo.spring.TrabAlumDao|mapaPorPeriodo|:" + ex);
		}
		return map;
	}

	public HashMap<String, TrabAlum> mapaTrab(String query) {

		HashMap<String, TrabAlum> map = new HashMap<String, TrabAlum>();
		List<TrabAlum> list = new ArrayList<TrabAlum>();
		try {
			String comando = "SELECT * FROM ENOC.TRAB_ALUM WHERE ESTADO = 'A'"+query;
			list = enocJdbc.query(comando, new TrabAlumMapper());
			for (TrabAlum alum : list) {
				map.put(alum.getMatricula(), alum);
			}
		} catch (Exception ex) {
			System.out.println("Error - aca.alumno.spring.TrabAlumDao|mapaTrab|:" + ex);
		}
		return map;
	}

	public TrabAlum trabActivo(String matricula) {

		TrabAlum alum = new TrabAlum();
		try {
			String comando = "SELECT COUNT(*) FROM ENOC.TRAB_ALUM WHERE MATRICULA = ? AND ESTADO = 'A'";
			Object[] parametros = new Object[] { matricula };
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1) {
				comando = "SELECT DEPT_ID, CAT_ID, MATRICULA, PERIODO_ID, HORAS, ESTADO, PAGO "
						+ "FROM ENOC.TRAB_ALUM "
						+ "WHERE MATRICULA = ? AND ESTADO = 'A'";
				alum = enocJdbc.queryForObject(comando, new TrabAlumMapper(), parametros);
			}
		} catch (Exception ex) {
			System.out.println("Error - aca.trabajo.spring.TrabAlumDao|trabActivo|:" + ex);
			ex.printStackTrace();
		}

		return alum;
	}

	public boolean desactivarTrab(String matricula) {
		boolean ok = false;
		try {
			String comando = "UPDATE ENOC.TRAB_ALUM SET ESTADO = 'I' WHERE MATRICULA = ? AND ESTADO = 'A'";
			Object[] parametros = new Object[] { matricula};
			if (enocJdbc.update(comando, parametros) == 1) {
				ok = true;
			}
		} catch (Exception ex) {
			System.out.println("Error - aca.trabajo.spring.TrabAlumDao|desactivarTrab|:" + ex);
		}

		return ok;
	}
}
