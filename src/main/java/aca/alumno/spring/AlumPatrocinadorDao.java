package aca.alumno.spring;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import aca.Mapa;
import aca.catalogo.spring.CatPatrocinador;
import aca.catalogo.spring.CatPatrocinadorMapper;
import aca.trabajo.spring.TrabAlum;
import aca.trabajo.spring.TrabAlumMapper;

@Component
public class AlumPatrocinadorDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(AlumPatrocinador alumPat) {
		boolean ok = false;

		try {
			String comando = "INSERT INTO ENOC.ALUM_PATROCINADOR(CODIGO_PERSONAL, PERIODO_ID, PATROCINADOR_ID, PORCENTAJE, CANTIDAD)"
					+ " VALUES( ?, ?, ?, ?, ? )";
			Object[] parametros = new Object[] { alumPat.getCodigoPersonal(), alumPat.getPeriodoId(), alumPat.getPatrocinadorId(),
					alumPat.getPorcentaje(), alumPat.getCantidad()};
			if (enocJdbc.update(comando, parametros) == 1) {
				ok = true;
			}
		} catch (Exception ex) {
			System.out.println("Error - aca.categoria.spring.AlumPatrocinadorDao|insertReg|:" + ex);
		}

		return ok;
	}

	public boolean updateReg(AlumPatrocinador alumPat) {
		boolean ok = false;
		try {
			String comando = "UPDATE ENOC.ALUM_PATROCINADOR SET CODIGO_PERSONAL = ?, PERIODO_ID = ?, PATROCINADOR_ID = ?, PORCENTAJE = ?, CANTIDAD = ? WHERE CODIGO_PERSONAL = ?";
			Object[] parametros = new Object[] { alumPat.getCodigoPersonal(), alumPat.getPeriodoId(), alumPat.getPatrocinadorId(), alumPat.getPorcentaje(), alumPat.getCantidad(),
					alumPat.getCodigoPersonal()
					};
			if (enocJdbc.update(comando, parametros) == 1) {
				ok = true;
			}
		} catch (Exception ex) {
			System.out.println("Error - aca.trabajo.spring.AlumPatrocinadorDao|updateReg|:" + ex);
		}

		return ok;
	}
	
	public boolean existeReg( String codigoPersonal, String periodoId, String patrocinadorId) {
		boolean ok 	= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.ALUM_PATROCINADOR WHERE CODIGO_PERSONAL = ? AND PERIODO_ID = ? AND PATROCINADOR_ID = ?";
			Object[] parametros = new Object[] {codigoPersonal, periodoId, patrocinadorId};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros) >= 1){
				ok = true;
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.AlumPatrocinadorDao|existeReg|:"+ex);
		}
		
		return ok;
	}

	public boolean deleteReg(String codigoPersonal, String periodoId, String patrocinadorId) {
		boolean ok = false;
		try {
			String comando = "DELETE FROM ENOC.ALUM_PATROCINADOR WHERE CODIGO_PERSONAL = ? AND PERIODO_ID = ? AND PATROCINADOR_ID = ?";
			Object[] parametros = new Object[] { codigoPersonal, periodoId, patrocinadorId};
			if (enocJdbc.update(comando, parametros) == 1) {
				ok = true;
			}
		} catch (Exception ex) {
			System.out.println("Error - aca.trabajo.spring.AlumPatrocinadorDao|deleteReg|:" + ex);
		}

		return ok;
	}

	public AlumPatrocinador mapeaRegId(String codigoPersonal, String periodoId, String patrocinadorId) {

		AlumPatrocinador alum = new AlumPatrocinador();
		try {
			String comando = "SELECT COUNT(*) FROM ENOC.ALUM_PATROCINADOR WHERE CODIGO_PERSONAL = ? AND PERIODO_ID = ? AND PATROCINADOR_ ID = TO_NUMBER(?,'999')";
			Object[] parametros = new Object[] { codigoPersonal, periodoId, patrocinadorId };
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1) {
				comando = "SELECT CODIGO_PERSONAL, PERIODO_ID, PATROCINADOR_ID, PORCENTAJE, CANTIDAD FROM ENOC.ALUM_PATROCINADOR WHERE CODIGO_PERSONAL = ? AND PERIODO_ID = ? AND PATROCINADOR_ ID = TO_NUMBER(?,'999') ";
				alum = enocJdbc.queryForObject(comando, new AlumPatrocinadorMapper(), parametros);
			}
		} catch (Exception ex) {
			System.out.println("Error - aca.trabajo.spring.AlumPatrocinadorDao|mapeaRegId|:" + ex);
			ex.printStackTrace();
		}

		return alum;
	}
	
	public List<AlumPatrocinador> lisTodos( String orden ) {
				
			List<AlumPatrocinador> lista = new ArrayList<AlumPatrocinador>();		
			try{
				String comando = "SELECT CODIGO_PERSONAL, PERIODO_ID, PATROCINADOR_ID, PORCENTAJE, CANTIDAD FROM ENOC.ALUM_PATROCINADOR "+ orden;				
				lista = enocJdbc.query(comando, new AlumPatrocinadorMapper());
			}catch(Exception ex){
				System.out.println("Error - aca.catalogo.spring.AlumPatrocinadorDao|lisTodos|:"+ex);
			}		
			return lista;
		}
	
	public List<AlumPatrocinador> lisTodosPorAlum(String codigoPersonal,  String orden ) {
			
		List<AlumPatrocinador> lista = new ArrayList<AlumPatrocinador>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL, PERIODO_ID, PATROCINADOR_ID, PORCENTAJE, CANTIDAD FROM ENOC.ALUM_PATROCINADOR WHERE CODIGO_PERSONAL = ? "+ orden;
			Object[] parametros = new Object[] {codigoPersonal};
			lista = enocJdbc.query(comando, new AlumPatrocinadorMapper(), parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.AlumPatrocinadorDao|lisTodosPorAlum|:"+ex);
		}		
		return lista;
	}
	
	public List<AlumPatrocinador> lisPorPeriodo(String codigoPersonal,  String periodoId, String orden ) {
		
		List<AlumPatrocinador> lista = new ArrayList<AlumPatrocinador>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL, PERIODO_ID, PATROCINADOR_ID, PORCENTAJE, CANTIDAD FROM ENOC.ALUM_PATROCINADOR WHERE CODIGO_PERSONAL = ? AND PERIODO_ID = ?"+ orden;
			Object[] parametros = new Object[] {codigoPersonal, periodoId};
			lista = enocJdbc.query(comando, new AlumPatrocinadorMapper(), parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.AlumPatrocinadorDao|lisPorPeriodo|:"+ex);
		}		
		return lista;
	}

	public List<AlumPatrocinador> lisPatrocinadosPorPeriodo( String periodoId, String orden ) {
		
		List<AlumPatrocinador> lista = new ArrayList<AlumPatrocinador>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL, PERIODO_ID, PATROCINADOR_ID, PORCENTAJE, CANTIDAD FROM ENOC.ALUM_PATROCINADOR WHERE PERIODO_ID = ?"+ orden;
			Object[] parametros = new Object[] {periodoId};
			lista = enocJdbc.query(comando, new AlumPatrocinadorMapper(), parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.AlumPatrocinadorDao|lisPatrocinadosPorPeriodo|:"+ex);
		}		
		return lista;
	}

	public List<AlumPatrocinador> lisPatrocinadosPorPeriodo( String periodoId, String patrocinadorId, String orden ) {
		
		List<AlumPatrocinador> lista = new ArrayList<AlumPatrocinador>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL, PERIODO_ID, PATROCINADOR_ID, PORCENTAJE, CANTIDAD FROM ENOC.ALUM_PATROCINADOR WHERE PERIODO_ID = ? AND PATROCINADOR_ID = TO_NUMBER(?)"+ orden;
			Object[] parametros = new Object[] {periodoId, patrocinadorId};
			lista = enocJdbc.query(comando, new AlumPatrocinadorMapper(), parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.AlumPatrocinadorDao|lisPatrocinadosPorPeriodo|:"+ex);
		}		
		return lista;
	}

	public HashMap<String, AlumPatrocinador> mapaAlumPatrocinador(String periodoId) {

		HashMap<String, AlumPatrocinador> map = new HashMap<String, AlumPatrocinador>();
		List<AlumPatrocinador> list = new ArrayList<AlumPatrocinador>();
		try {
			String comando = "SELECT CODIGO_PERSONAL, PERIODO_ID, PATROCINADOR_ID, PORCENTAJE, CANTIDAD FROM ENOC.ALUM_PATROCINADOR WHERE PERIODO_ID = ?";
			Object[] parametros = new Object[] {periodoId};
			list = enocJdbc.query(comando, new AlumPatrocinadorMapper(), parametros);
			for (AlumPatrocinador alum : list) {
				map.put(alum.getCodigoPersonal(), alum);
				
			}
		} catch (Exception ex) {
			System.out.println("Error - aca.trabajo.spring.AlumPatrocinadorDao|mapaAlumPatrocinador|:" + ex);
		}
		return map;
	}
	
	public HashMap<String, AlumPatrocinador> mapaPatrocinadorPorPeriodo(String codigoPersonal, String periodoId) {

		HashMap<String, AlumPatrocinador> map = new HashMap<String, AlumPatrocinador>();
		List<AlumPatrocinador> list = new ArrayList<AlumPatrocinador>();
		try {
			String comando = "SELECT CODIGO_PERSONAL, PERIODO_ID, PATROCINADOR_ID, PORCENTAJE, CANTIDAD FROM ENOC.ALUM_PATROCINADOR WHERE CODIGO_PERSONAL = ? AND PERIODO_ID = ?";
			Object[] parametros = new Object[] {codigoPersonal, periodoId};
			list = enocJdbc.query(comando, new AlumPatrocinadorMapper(), parametros);
			for (AlumPatrocinador alum : list) {
				map.put(alum.getCodigoPersonal(), alum);
				
			}
		} catch (Exception ex) {
			System.out.println("Error - aca.trabajo.spring.AlumPatrocinadorDao|mapaPatrocinadorPorPeriodo|:" + ex);
		}
		return map;
	}

}
