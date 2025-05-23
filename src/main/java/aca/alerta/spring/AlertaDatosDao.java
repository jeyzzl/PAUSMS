package aca.alerta.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class AlertaDatosDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( AlertaDatos alertaDatos){
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.ALERTA_DATOS"+ 
				"(PERIODO_ID,  CODIGO_PERSONAL, FECHA, DIRECCION, "+
				"PROCEDENCIA, CORREO, CELULAR, SINTOMAS, "+
				"USUARIO, LUGAR1, LUGAR2, ESTADO, OTRO, REFERENTE) "+
				"VALUES(?, ?, TO_DATE(?, 'DD/MM/YYYY'), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )";
			Object[] parametros = new Object[] {
					alertaDatos.getPeriodoId(),alertaDatos.getCodigoPersonal(),alertaDatos.getFecha(),alertaDatos.getDireccion(),alertaDatos.getProcedencia(),alertaDatos.getCorreo(),
					alertaDatos.getCelular(),alertaDatos.getSintomas(),alertaDatos.getUsuario(),alertaDatos.getLugar1(),alertaDatos.getLugar2(),alertaDatos.getEstado(),alertaDatos.getOtro(),
					alertaDatos.getReferente()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
				
		}catch(Exception ex){
			System.out.println("Error - aca.alerta.spring.AlertaDatosDao|insertReg|:"+ex);			
		}
		
		return ok;
	} 
	public boolean updateReg( AlertaDatos alertaDatos ){
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.ALERTA_DATOS "+ 
				"SET "+
				"FECHA = TO_DATE(?, 'DD/MM/YYYY'), "+
				"DIRECCION = ?, "+
				"PROCEDENCIA = ?, "+
				"CORREO = ?, "+
				"CELULAR = ?, "+
				"SINTOMAS = ?, "+
				"USUARIO = ?, "+
				"LUGAR1 = ?, "+
				"LUGAR2 = ?, "+
				"ESTADO = ?, "+
				"OTRO = ?, "+
				"REFERENTE = ? "+
				"WHERE PERIODO_ID = ? AND CODIGO_PERSONAL = ? ";
			Object[] parametros = new Object[] {
					alertaDatos.getFecha(),alertaDatos.getDireccion(),alertaDatos.getProcedencia(),alertaDatos.getCorreo(),alertaDatos.getCelular(),alertaDatos.getSintomas(),
					alertaDatos.getUsuario(),alertaDatos.getLugar1(),alertaDatos.getLugar2(),alertaDatos.getEstado(),alertaDatos.getOtro(),alertaDatos.getReferente(),alertaDatos.getPeriodoId(),
					alertaDatos.getCodigoPersonal()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	

		}catch(Exception ex){
			System.out.println("Error - aca.alerta.spring.AlertaDatosDao|updateReg|:"+ex);		
		}
		
		return ok;
	}
	
	public boolean deleteReg( String periodoId, String codigoPersonal ){
		boolean ok = false;		
		try{
			String comando = "DELETE FROM ENOC.ALERTA_DATOS WHERE PERIODO_ID = ? AND CODIGO_PERSONAL = ? ";
			Object[] parametros = new Object[] {periodoId, codigoPersonal};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.alerta.spring.AlertaDatosDao|deleteReg|:"+ex);			
		}		
		return ok;
	}
	
	public AlertaDatos mapeaRegId( String periodoId, String codigoPersonal ){
		
		AlertaDatos alertaDatos = new AlertaDatos();		
		try{
			String comando = "SELECT "
				+ " PERIODO_ID, FOLIO, CODIGO_PERSONAL, TO_DATE(FECHA, 'DD/MM/YYYY') AS FECHA, DIRECCION,"
				+ " PROCEDENCIA, CORREO, CELULAR, SINTOMAS,"
				+ " USUARIO, LUGAR1, LUGAR2, ESTADO, OTRO, REFERENTE"
				+ " FROM ENOC.ALERTA_DATOS WHERE PERIODO_ID = ? AND CODIGO_PERSONAL = ?";
			Object[] parametros = new Object[] {periodoId, codigoPersonal};
			alertaDatos = enocJdbc.queryForObject(comando, new AlertaDatosMapper(), parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.alerta.spring.AlertaDatosDao|mapeaRegId|:"+ex);
		}		
		return alertaDatos;
	}
	
	public boolean existeReg( String periodoId, String codigoPersonal){
		boolean 		ok 	= false;		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.ALERTA_DATOS WHERE PERIODO_ID = ? AND CODIGO_PERSONAL = ?";
			Object[] parametros = new Object[] {periodoId, codigoPersonal};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros) >= 1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.alerta.spring.AlertaDatosDao|existeReg|:"+ex);
		}		
		return ok;
	}

	public boolean existeAlumno( String periodoId, String codigoPersonal){
		boolean 		ok 	= false;		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.ALERTA_DATOS WHERE PERIODO_ID = ? AND CODIGO_PERSONAL = ?";
			Object[] parametros = new Object[] {periodoId, codigoPersonal};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros) >= 1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.alerta.spring.AlertaDatosDao|existeAlumno|:"+ex);
		}		
		return ok;
	}
	
	public boolean alertaActivaEnModalidadActual( String modalidadId){
		boolean 		ok 	= false;		
		try{
			String comando = " SELECT * FROM ENOC.ALERTA_PERIODO WHERE MODALIDADES LIKE '%-"+modalidadId+"-%' AND ESTADO = 'A' AND now() BETWEEN FECHA_INI AND FECHA_FIN " ;
			Object[] parametros = new Object[] {modalidadId};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros) >= 1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.alerta.spring.AlertaDatosDao|tieneDatos|:"+ex);
		}		
		return ok;
	}
	
	public boolean autorizado( String codigoPersonal, String periodoId){
		boolean 		ok 	= false;		
		try{
			String comando = " SELECT CODIGO_PERSONAL FROM ENOC.ALERTA_DATOS WHERE CODIGO_PERSONAL = ? AND PERIODO_ID = ?"					+ " AND ESTADO = 'A'  ";
			Object[] parametros = new Object[] {codigoPersonal, periodoId};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros) >= 1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.alerta.spring.AlertaDatosDao|tieneDatos|:"+ex);
		}		
		return ok;
	}
	
	public List<AlertaDatos> getAll( String orden){
		
		List<AlertaDatos> lista	= new ArrayList<AlertaDatos>();		
		try{
			String comando = "SELECT PERIODO_ID, FOLIO, CODIGO_PERSONAL, FECHA, DIRECCION, PROCEDENCIA, CORREO, CELULAR, SINTOMAS, "+
					  " USUARIO, LUGAR1, LUGAR2, ESTADO, OTRO, REFERENTE FROM ENOC.ALERTA_DATOS "+ orden;
			lista = enocJdbc.query(comando, new AlertaDatosMapper());			
		}catch(Exception ex){
			System.out.println("Error - aca.alerta.spring.AlertaDatosDao|getAll|:"+ex);
		}		
		return lista;
	}
	
	public HashMap<String, String> getMapEdadAlerta( String periodo){		
		HashMap<String, String> mapa		= new HashMap<String,String>();
		List<aca.Mapa> lista	= new ArrayList<aca.Mapa>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL AS LLAVE, FLOOR(MONTHS_BETWEEN( TO_DATE(TO_CHAR(now(),'DD/MM/YYYY'),'DD/MM/YYYY') ,F_NACIMIENTO)/12) AS EDAD AS VALOR "
					+ " FROM ENOC.ALUM_PERSONAL"
					+ " WHERE CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM ENOC.ALERTA_DATOS WHERE PERIODO_ID = ?)";
			lista = enocJdbc.query(comando, new aca.MapaMapper(), periodo);
			for (aca.Mapa map : lista ) {
				mapa.put(map.getLlave(), map.getValor());
			}			
	
		}catch(Exception ex){
			System.out.println("Error - aca.alerta.spring.AlertaDatosDao|getMapEdadAlerta|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String, String> getMapResidenciaAlerta( String periodo){
		
		HashMap<String, String> mapa		= new HashMap<String,String>();
		List<aca.Mapa> lista	= new ArrayList<aca.Mapa>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL AS LLAVE, RESIDENCIA_ID AS VALOR " +
					" FROM ENOC.ALUM_ACADEMICO" +
					" WHERE CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM ENOC.ALERTA_DATOS WHERE PERIODO_ID = ? )";
			lista = enocJdbc.query(comando, new aca.MapaMapper(), periodo);
			for (aca.Mapa map : lista ) {
				mapa.put(map.getLlave(), map.getValor());
			}	
			
		}catch(Exception ex){
			System.out.println("Error - aca.alerta.spring.AlertaDatosDao|getMapResidenciaAlerta|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String, String> mapDormitorioAlerta( String periodo){
		
		HashMap<String, String> mapa		= new HashMap<String,String>();
		List<aca.Mapa> lista	= new ArrayList<aca.Mapa>();
		
		try{
			String comando = " SELECT CODIGO_PERSONAL AS LLAVE, DORMITORIO AS VALOR "
					+ " FROM ENOC.ALUM_ACADEMICO"
					+ " WHERE CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM ENOC.ALERTA_DATOS WHERE PERIODO_ID = ?)";
			lista = enocJdbc.query(comando, new aca.MapaMapper(), periodo);
			for (aca.Mapa map : lista ) {
				mapa.put(map.getLlave(), map.getValor());
			}	
			
		}catch(Exception ex){
			System.out.println("Error - aca.alerta.spring.AlertaDatosDao|mapDormitorioAlerta|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String, String> mapCicloAlerta( String periodo){
		
		HashMap<String, String> mapa		= new HashMap<String,String>();
		List<aca.Mapa> lista	= new ArrayList<aca.Mapa>();
		
		try{
			String comando = " SELECT CODIGO_PERSONAL AS LLAVE, CICLO AS VALOR "
					+ " FROM ENOC.ALUM_PLAN"
					+ " WHERE ESTADO = '1'"
					+ " AND CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM ENOC.ALERTA_DATOS WHERE PERIODO_ID = ?)";
			lista = enocJdbc.query(comando, new aca.MapaMapper(), periodo);
			for (aca.Mapa map : lista ) {
				mapa.put(map.getLlave(), map.getValor());
			}	
			
		}catch(Exception ex){
			System.out.println("Error - aca.alerta.spring.AlertaDatosDao|mapCicloAlerta|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String, String> mapCarreraActual( String periodo){
		
		HashMap<String, String> mapa		= new HashMap<String,String>();
		List<aca.Mapa> lista	= new ArrayList<aca.Mapa>();
		
		try{
			String comando = " SELECT CODIGO_PERSONAL AS LLAVE, ENOC.CARRERA(PLAN_ID) AS CARRERA AS VALOR"
					+ " FROM ENOC.ALUM_PLAN"
					+ " WHERE ESTADO = '1'"
					+ " AND CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM ENOC.ALERTA_DATOS WHERE PERIODO_ID = ?)";
			lista = enocJdbc.query(comando, new aca.MapaMapper(), periodo);
			for (aca.Mapa map : lista ) {
				mapa.put(map.getLlave(), map.getValor());
			}	
			
		}catch(Exception ex){
			System.out.println("Error - aca.alerta.spring.AlertaDatosDao|mapCicloAlerta|:"+ex);
		}
		
		return mapa;
	}

}