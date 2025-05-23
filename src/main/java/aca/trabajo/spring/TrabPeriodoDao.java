package aca.trabajo.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class TrabPeriodoDao {

	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;

	public boolean insertReg(TrabPeriodo periodo) {
		boolean ok = false;

		try {
			String comando = "INSERT INTO ENOC.TRAB_PERIODO(PERIODO_ID, NOMBRE, ESTADO, FECHA_INI, FECHA_FIN)"
					+ " VALUES( ?, ?, ?, TO_DATE(?, 'DD/MM/YYYY'), TO_DATE(?, 'DD/MM/YYYY'))";
			Object[] parametros = new Object[] { periodo.getPeriodoId(), periodo.getNombrePeriodo(),
					periodo.getEstado(), periodo.getFechaIni(), periodo.getFechaFin() };
			if (enocJdbc.update(comando, parametros) == 1) {
				ok = true;
			}
		} catch (Exception ex) {
			System.out.println("Error - aca.categoria.spring.TrabPeriodoDao|insertReg|:" + ex);
		}

		return ok;
	}

	public boolean updateReg(TrabPeriodo periodo) {
		boolean ok = false;
		try {
			String comando = "UPDATE ENOC.TRAB_PERIODO SET NOMBRE = ?, ESTADO = ?,"
					+ " FECHA_INI = TO_DATE(?, 'DD/MM/YYYY'),"
					+ " FECHA_FIN = TO_DATE(?, 'DD/MM/YYYY')"
					+ " WHERE PERIODO_ID = TO_NUMBER(?,'999')";
			Object[] parametros = new Object[] { periodo.getNombrePeriodo(), periodo.getEstado(), periodo.getFechaIni(),
					periodo.getFechaFin(), periodo.getPeriodoId() };
			if (enocJdbc.update(comando, parametros) == 1) {
				ok = true;
			}
		} catch (Exception ex) {
			System.out.println("Error - aca.trabajo.spring.TrabPeriodoDao|updateReg|:" + ex);
		}

		return ok;
	}

	public boolean deleteReg(String periodoId) {
		boolean ok = false;
		try {
			String comando = "DELETE FROM ENOC.TRAB_PERIODO WHERE PERIODO_ID = TO_NUMBER(?,'999')";
			Object[] parametros = new Object[] { periodoId };
			if (enocJdbc.update(comando, parametros) == 1) {
				ok = true;
			}
		} catch (Exception ex) {
			System.out.println("Error - aca.trabajo.spring.TrabPeriodoDao|deleteReg|:" + ex);
		}

		return ok;
	}

	public TrabPeriodo mapeaRegId(String periodoId) {

		TrabPeriodo periodo = new TrabPeriodo();
		try {
			String comando = "SELECT COUNT(*) FROM ENOC.TRAB_PERIODO WHERE PERIODO_ID = TO_NUMBER(?,'999')";
			Object[] parametros = new Object[] { periodoId };
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1) {
				comando = "SELECT PERIODO_ID, NOMBRE, TO_CHAR(FECHA_INI, 'DD/MM/YYYY') AS FECHA_INI, TO_CHAR(FECHA_FIN, 'DD/MM/YYYY') AS FECHA_FIN, ESTADO "
						+ "FROM ENOC.TRAB_PERIODO WHERE PERIODO_ID = TO_NUMBER(?,'999')";
				periodo = enocJdbc.queryForObject(comando, new TrabPeriodoMapper(), parametros);
			}
		} catch (Exception ex) {
			System.out.println("Error - aca.trabajo.spring.TrabPeriodoDao|mapeaRegId|:" + ex);
			ex.printStackTrace();
		}

		return periodo;
	}

	public boolean existeReg(String periodoId) {
		boolean ok = false;
		try {
			String comando = "SELECT COUNT(PERIODO_ID) FROM ENOC.TRAB_PERIODO WHERE PERIODO_ID = TO_NUMBER(?, '999')";
			Object[] parametros = new Object[] { periodoId };
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1) {
				ok = true;
			}
		} catch (Exception ex) {
			System.out.println("Error - aca.trabajo.spring.TrabPeriodoDao|existeReg|:" + ex);
		}

		return ok;
	}

	public String maximoReg() {
		String maximo = "01";
		try {
			String comando = "SELECT COALESCE(MAX(PERIODO_ID)+1,1) AS MAXIMO FROM ENOC.TRAB_PERIODO";
			if (enocJdbc.queryForObject(comando, Integer.class) >= 1) {
				maximo = enocJdbc.queryForObject(comando, String.class);
				if (maximo.length() == 1)
					maximo = "0" + maximo;
			}
		} catch (Exception ex) {
			System.out.println("Error - aca.trabajo.spring.TrabPeriodoDao|maximoReg|:" + ex);
		}

		return maximo;
	}

	// Llena el listor con todos los elementos de la tabla
	public List<TrabPeriodo> lisTodos(String orden) {

		List<TrabPeriodo> lista = new ArrayList<TrabPeriodo>();
		try {
			String comando = "SELECT PERIODO_ID, NOMBRE, ESTADO, TO_CHAR(FECHA_INI, 'DD/MM/YYYY') AS FECHA_INI, TO_CHAR(FECHA_FIN, 'DD/MM/YYYY') AS FECHA_FIN FROM ENOC.TRAB_PERIODO " + orden;
			lista = enocJdbc.query(comando, new TrabPeriodoMapper());
		} catch (Exception ex) {
			System.out.println("Error - aca.trabajo.spring.TrabPeriodoDao|lisTodos|:" + ex);
		}
		return lista;
	}

	public List<TrabPeriodo> lisActivos(String orden) {

		List<TrabPeriodo> lista = new ArrayList<TrabPeriodo>();
		try {
			String comando = "SELECT PERIODO_ID, NOMBRE, ESTADO, TO_CHAR(FECHA_INI, 'DD/MM/YYYY') AS FECHA_INI, TO_CHAR(FECHA_FIN, 'DD/MM/YYYY') AS FECHA_FIN FROM ENOC.TRAB_PERIODO WHERE ESTADO = 'A'" + orden;
			lista = enocJdbc.query(comando, new TrabPeriodoMapper());
		} catch (Exception ex) {
			System.out.println("Error - aca.trabajo.spring.TrabPeriodoDao|lisTodos|:" + ex);
		}
		return lista;
	}

	public List<TrabPeriodo> lisTodosPorAlumno(String codigoAlumno, String orden) {

		List<TrabPeriodo> lista = new ArrayList<TrabPeriodo>();
		try {
			String comando = "SELECT PERIODO_ID, NOMBRE, ESTADO, TO_CHAR(FECHA_INI, 'DD/MM/YYYY') AS FECHA_INI, TO_CHAR(FECHA_FIN, 'DD/MM/YYYY') AS FECHA_FIN FROM ENOC.TRAB_PERIODO "
							+"WHERE PERIODO_ID IN (SELECT PERIODO_ID FROM TRAB_ALUM WHERE MATRICULA = ?)" + orden;
			Object[] parametros = new Object[] { codigoAlumno };
			lista = enocJdbc.query(comando, new TrabPeriodoMapper(), parametros);
		} catch (Exception ex) {
			System.out.println("Error - aca.trabajo.spring.TrabPeriodoDao|lisTodos|:" + ex);
		}
		return lista;
	}
	
	public HashMap<String,String> mapaPeriodoNombre(){		
		HashMap<String,String> mapa 	= new HashMap<String,String>();
		List<aca.Mapa> lista			= new ArrayList<aca.Mapa>();		
		try{
			String comando = "SELECT PERIODO_ID AS LLAVE, NOMBRE AS VALOR FROM ENOC.TRAB_PERIODO";
			lista = enocJdbc.query(comando, new aca.MapaMapper());	
			for ( aca.Mapa objeto : lista){
				 mapa.put(objeto.getLlave(), objeto.getValor());
			}				
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.TrabPeriodoDao|mapaPeriodoNombre|:"+ex);
		}		
		return mapa;
	}

	public HashMap<String,String> mapaPeriodoNombre(String periodoId){		
		HashMap<String,String> mapa 	= new HashMap<String,String>();
		List<aca.Mapa> lista			= new ArrayList<aca.Mapa>();		
		try{
			String comando = "SELECT MATRICULA AS LLAVE, PERIODO_ID AS VALOR FROM TRAB_ALUM WHERE PERIODO_ID = ?";
			lista = enocJdbc.query(comando, new aca.MapaMapper(), periodoId);	
			for ( aca.Mapa objeto : lista){
				 mapa.put(objeto.getLlave(), objeto.getValor());
			}				
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.TrabPeriodoDao|mapaPeriodoNombre|:"+ex);
		}		
		return mapa;
	}

}
