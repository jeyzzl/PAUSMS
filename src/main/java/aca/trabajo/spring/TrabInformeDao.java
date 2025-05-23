package aca.trabajo.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import aca.Mapa;

@Component
public class TrabInformeDao {

	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;

	public boolean insertReg(TrabInforme informe) {
		boolean ok = false;

		try {
			String comando = "INSERT INTO ENOC.TRAB_INFORME(INFORME_ID, NOMBRE, ESTADO, PERIODO_ID)"
					+ " VALUES( ?, ?, ?, ? )";
			Object[] parametros = new Object[] { informe.getInformeId(), informe.getNombreInforme(),
					informe.getEstado(), informe.getPeriodoId() };
			if (enocJdbc.update(comando, parametros) == 1) {
				ok = true;
			}
		} catch (Exception ex) {
			System.out.println("Error - aca.categoria.spring.TrabInformeDao|insertReg|:" + ex);
		}

		return ok;
	}

	public boolean updateReg(TrabInforme informe) {
		boolean ok = false;
		try {
			String comando = "UPDATE ENOC.TRAB_INFORME SET NOMBRE = ?, ESTADO = ?, PERIODO_ID = ? WHERE INFORME_ID = TO_NUMBER(?,'99999')";
			Object[] parametros = new Object[] { informe.getNombreInforme(), informe.getEstado(),
					informe.getPeriodoId(), informe.getInformeId() };
			if (enocJdbc.update(comando, parametros) == 1) {
				ok = true;
			}
		} catch (Exception ex) {
			System.out.println("Error - aca.trabajo.spring.TrabInformeDao|updateReg|:" + ex);
		}

		return ok;
	}

	public boolean deleteReg(String informeId) {
		boolean ok = false;
		try {
			String comando = "DELETE FROM ENOC.TRAB_INFORME WHERE INFORME_ID = TO_NUMBER(?,'99999')";
			Object[] parametros = new Object[] { informeId };
			if (enocJdbc.update(comando, parametros) == 1) {
				ok = true;
			}
		} catch (Exception ex) {
			System.out.println("Error - aca.trabajo.spring.TrabInformeDao|deleteReg|:" + ex);
		}

		return ok;
	}

	public TrabInforme mapeaRegId(String informeId) {

		TrabInforme informe = new TrabInforme();
		try {
			String comando = "SELECT COUNT(*) FROM ENOC.TRAB_INFORME WHERE INFORME_ID = TO_NUMBER(?,'99999')";
			Object[] parametros = new Object[] { informeId };
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1) {
				comando = "SELECT * FROM ENOC.TRAB_INFORME WHERE INFORME_ID = TO_NUMBER(?,'99999')";
				informe = enocJdbc.queryForObject(comando, new TrabInformeMapper(), parametros);
			}
		} catch (Exception ex) {
			System.out.println("Error - aca.trabajo.spring.TrabInformeDao|mapeaRegId|:" + ex);
			ex.printStackTrace();
		}

		return informe;
	}

	public boolean existeReg(String informeId) {
		boolean ok = false;
		try {
			String comando = "SELECT COUNT(INFORME_ID) FROM ENOC.TRAB_INFORME WHERE INFORME_ID = TO_NUMBER(?, '99999')";
			Object[] parametros = new Object[] { informeId };
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1) {
				ok = true;
			}
		} catch (Exception ex) {
			System.out.println("Error - aca.trabajo.spring.TrabInformeDao|existeReg|:" + ex);
		}

		return ok;
	}

	public String maximoReg() {
		String maximo = "01";
		try {
			String comando = "SELECT COALESCE(MAX(INFORME_ID)+1,1) AS MAXIMO FROM ENOC.TRAB_INFORME";
			if (enocJdbc.queryForObject(comando, Integer.class) >= 1) {
				maximo = enocJdbc.queryForObject(comando, String.class);
				if (maximo.length() == 1)
					maximo = "0" + maximo;
			}
		} catch (Exception ex) {
			System.out.println("Error - aca.trabajo.spring.TrabInformeDao|maximoReg|:" + ex);
		}

		return maximo;
	}

	// Llena el listor con todos los elementos de la tabla
	public List<TrabInforme> lisTodos(String orden) {

		List<TrabInforme> lista = new ArrayList<TrabInforme>();
		try {
			String comando = "SELECT INFORME_ID, NOMBRE, ESTADO, PERIODO_ID FROM ENOC.TRAB_INFORME" + orden;
			lista = enocJdbc.query(comando, new TrabInformeMapper());
		} catch (Exception ex) {
			System.out.println("Error - aca.trabajo.spring.TrabInformeDao|lisTodos|:" + ex);
		}
		return lista;
	}

	public List<TrabInforme> lisPorPeriodo(String periodoId, String orden) {

		List<TrabInforme> lista = new ArrayList<TrabInforme>();
		try {
			String comando = "SELECT INFORME_ID, NOMBRE, ESTADO, PERIODO_ID FROM ENOC.TRAB_INFORME WHERE PERIODO_ID = ?"
					+ orden;
			Object[] parametros = new Object[] { periodoId };
			lista = enocJdbc.query(comando, new TrabInformeMapper(), parametros);
		} catch (Exception ex) {
			System.out.println("Error - aca.trabajo.spring.TrabInformeDao|lisPorPeriodo|:" + ex);
		}
		return lista;
	}
	
	public HashMap<String, String> mapaPorPeriodo(String orden) {

		HashMap<String, String> map = new HashMap<String, String>();
		List<aca.Mapa> list = new ArrayList<aca.Mapa>();
		try {
			String comando = "SELECT DISTINCT PERIODO_ID AS LLAVE, NOMBRE AS VALOR FROM ENOC.TRAB_INFORME";
			list = enocJdbc.query(comando, new aca.MapaMapper());
			for (Mapa alum : list) {
				map.put(alum.getLlave(), alum.getValor());
				
			}
		} catch (Exception ex) {
			System.out.println("Error - aca.trabajo.spring.TrabAlumDao|mapaPorPeriodo|:" + ex);
		}
		return map;
	}
	
	public HashMap<String,String> horasRegistradasPorReporte(){
		HashMap<String,String> map = new HashMap<String,String>();
		List<aca.Mapa> list = new ArrayList<aca.Mapa>();
		try {
			String comando = "SELECT I.INFORME_ID AS LLAVE, COALESCE(SUM(U.HORAS), 0) AS VALOR FROM TRAB_INFORME I LEFT JOIN TRAB_INFORME_ALUM U ON I.INFORME_ID = U.INFORME_ID GROUP BY I.INFORME_ID";
			list = enocJdbc.query(comando, new aca.MapaMapper());
			for(Mapa horas : list) {
				map.put(horas.getLlave(), horas.getValor());
			}
		}catch (Exception ex) {
			System.out.println("Error - aca.trabajo.spring.TrabInformeDao|horasRegistradasPorReporte|:" + ex);		
		}
		return map;
	}
}
