package aca.apFisica.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class ApFisicaGrupoDao{
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(ApFisicaGrupo apFGrupo ){
		boolean ok = false;		
		try{
			String comando = "INSERT INTO"
					+ " ENOC.APFISICA_GRUPO(GRUPO_ID, NOMBRE_GRUPO, LUGAR, INSTRUCTOR, CUPO, F_INICIO, F_FINAL, DIA1, CARGAS, CLAVE, DESCRIPCION, HORA, F_CIERRE, ACCESO, LIGA, SEXO)"
					+ " VALUES(TO_NUMBER(?,'9999'), ?, ?, ?, TO_NUMBER(?, '999'), TO_DATE(?,'DD/MM/YYYY'), TO_DATE(?,'DD/MM/YYYY'), ?, ?, ?, ?, ?, TO_DATE(?, 'DD/MM/YYYY'), ?, ?, ?)";
			Object[] parametros = new Object[] {
					apFGrupo.getGrupoId(),apFGrupo.getNombreGrupo(),apFGrupo.getLugar(),apFGrupo.getInstructor(),
					apFGrupo.getCupo(),apFGrupo.getfInicio(),apFGrupo.getfFinal(),apFGrupo.getDia1(),apFGrupo.getCargas(),
					apFGrupo.getClave(),apFGrupo.getDescripcion(),apFGrupo.getHora(),apFGrupo.getfCierre(),apFGrupo.getAcceso(),
					apFGrupo.getLiga(), apFGrupo.getSexo()
 		 	};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.apFisica.spring.ApFisicaGrupoDao|insertReg|:"+ex);
		}
		
		return ok;
	}
	
	public boolean updateReg(ApFisicaGrupo apFGrupo ){
		boolean ok = false;
		try{
			String comando = "UPDATE ENOC.APFISICA_GRUPO"
					+ " SET NOMBRE_GRUPO = ?, LUGAR = ?, INSTRUCTOR = ?, CUPO = TO_NUMBER(?, '999'), F_INICIO = TO_DATE(?,'DD/MM/YYYY'), F_FINAL = TO_DATE(?,'DD/MM/YYYY'),"
					+ " DIA1 = ?, CARGAS = ?, CLAVE = ?, DESCRIPCION = ?, HORA = ?, F_CIERRE = TO_DATE(?,'DD/MM/YYYY'), ACCESO = ?, LIGA = ?, SEXO = ?"
					+ " WHERE GRUPO_ID = TO_NUMBER(?,'9999') ";
			Object[] parametros = new Object[] {
					apFGrupo.getNombreGrupo(), apFGrupo.getLugar(), apFGrupo.getInstructor(), apFGrupo.getCupo(), apFGrupo.getfInicio(), 
					apFGrupo.getfFinal(), apFGrupo.getDia1(), apFGrupo.getCargas(), apFGrupo.getClave(), apFGrupo.getDescripcion(), apFGrupo.getHora(),
					apFGrupo.getfCierre(), apFGrupo.getAcceso(), apFGrupo.getLiga(), apFGrupo.getSexo(), apFGrupo.getGrupoId()
				};
				if (enocJdbc.update(comando,parametros)==1){
					ok = true;
				}
			
		}catch(Exception ex){
			System.out.println("Error - aca.apFisica.ApFisicaGrupoUtil|updateReg|: "+ex);
		}
		return ok;
	}
	
	public boolean deleteReg(String grupoId ){
		boolean ok = false;		
		try{
			String comando = "DELETE FROM ENOC.APFISICA_GRUPO WHERE GRUPO_ID = TO_NUMBER(?, '9999')";
			Object[] parametros = new Object[] {grupoId};	
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.apFisica.ApFisicaGrupoUtil|deleteReg|:"+ex);
		}		
		return ok;
	}
	
	public boolean existeReg(String grupoId){
		boolean ok 			= false;		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.APFISICA_GRUPO WHERE GRUPO_ID = TO_NUMBER(?, '9999')";
			Object[] parametros = new Object[] {grupoId};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.apFisica.spring.ApFisicaGrupoDao|existeReg|:"+ex);
		}		
		return ok;
	}
	
	public ApFisicaGrupo mapeaRegId(String grupoId){
		ApFisicaGrupo apFGrupo = new ApFisicaGrupo();
		
		try{
			String comando = "SELECT GRUPO_ID, NOMBRE_GRUPO, LUGAR, INSTRUCTOR, CUPO, TO_CHAR(F_INICIO, 'DD/MM/YYYY') AS F_INICIO, TO_CHAR(F_FINAL, 'DD/MM/YYYY') AS F_FINAL,"
					+ " DIA1, CARGAS, CLAVE, DESCRIPCION, HORA, TO_CHAR(F_CIERRE, 'DD/MM/YYYY') AS F_CIERRE, ACCESO, LIGA, SEXO"
					+ " FROM ENOC.APFISICA_GRUPO WHERE GRUPO_ID = TO_NUMBER(?, '9999')"; 
			Object[] parametros = new Object[] {grupoId};
			apFGrupo = enocJdbc.queryForObject(comando, new ApFisicaGrupoMapper(), parametros);
			
		}catch(Exception ex){
 			System.out.println("Error - aca.apFisica.spring.ApFisicaGrupoDao|mapeaRegId|:"+ex);
 		}
		
		return apFGrupo;
	}
	
	public String maximoReg(){
		String maximo			= "1";		
		try{
			String comando = "SELECT MAX(GRUPO_ID)+1 AS MAXIMO FROM ENOC.APFISICA_GRUPO"; 
 				maximo = enocJdbc.queryForObject(comando, String.class);			
		}catch(Exception ex){
			System.out.println("Error - aca.apFisica.ApFisicaGrupoDao|maximo|:"+ex);
		}		
		return maximo;
	}
	
	public String nombreGrupo(String grupoId){
		String nombre			= "-";		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.APFISICA_GRUPO WHERE GRUPO_ID = TO_NUMBER(?, '9999')";
			Object[] parametros = new Object[] {grupoId};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
				comando = "SELECT NOMBRE_GRUPO FROM ENOC.APFISICA_GRUPO WHERE GRUPO_ID = TO_NUMBER(?, '9999')";			
				nombre = enocJdbc.queryForObject(comando, String.class, parametros);
			}			 			
		}catch(Exception ex){
			System.out.println("Error - aca.apFisica.spring.ApFisicaGrupoDao|nombreGrupo|:"+ex);
		}		
		return nombre;
	}
	
	public String nombreInstructor(String grupoId){
		String instructor			= "1";		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.APFISICA_GRUPO WHERE GRUPO_ID = TO_NUMBER(?, '9999')";
			Object[] parametros = new Object[] {grupoId};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
				comando = "SELECT INSTRUCTOR FROM ENOC.APFISICA_GRUPO WHERE GRUPO_ID= TO_NUMBER(?, '9999')";
				instructor = enocJdbc.queryForObject(comando, String.class, parametros);
			}					
		}catch(Exception ex){
			System.out.println("Error - aca.apFisica.spring.ApFisicaGrupoDao|nombreInstructor|:"+ex);
		}		
		return instructor;
	}
	
	public String getLiga(String grupoId){
		String liga			= "1";		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.APFISICA_GRUPO WHERE GRUPO_ID = TO_NUMBER(?, '9999')";
			Object[] parametros = new Object[] {grupoId};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
				comando = "SELECT LIGA FROM ENOC.APFISICA_GRUPO WHERE GRUPO_ID = TO_NUMBER(?, '9999')";
				liga = enocJdbc.queryForObject(comando, String.class, parametros);
			}						
		}catch(Exception ex){
			System.out.println("Error - aca.apFisica.spring.ApFisicaGrupoDao|getLiga|:"+ex);
		}		
		return liga;
	}
	
	public String getSexo(String grupoId){
		String sexo			= "T";		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.APFISICA_GRUPO WHERE GRUPO_ID = TO_NUMBER(?, '9999')";
			Object[] parametros = new Object[] {grupoId};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
				comando = "SELECT SEXO FROM ENOC.APFISICA_GRUPO WHERE GRUPO_ID = TO_NUMBER(?, '9999')";
				sexo = enocJdbc.queryForObject(comando, String.class, parametros);
			}						
		}catch(Exception ex){
			System.out.println("Error - aca.apFisica.spring.ApFisicaGrupoDao|getSexo|:"+ex);
		}		
		return sexo;
	}
	
	public List<ApFisicaGrupo> getGruposAlumnos(String cargaId, String clave, String acceso, String sexo, String orden){
		List<ApFisicaGrupo> lista 	= new ArrayList<ApFisicaGrupo>();		
		try{
			String comando = "SELECT  GRUPO_ID, NOMBRE_GRUPO, LUGAR, INSTRUCTOR, CUPO,"
					+ " TO_CHAR(F_INICIO,'DD/MM/YYYY') AS F_INICIO, TO_CHAR(F_FINAL,'DD/MM/YYYY') AS F_FINAL,"
					+ " DIA1, CARGAS, CLAVE, DESCRIPCION, HORA, TO_CHAR(F_CIERRE,'DD/MM/YYYY') AS F_CIERRE, ACCESO, LIGA, SEXO "
					+ " FROM ENOC.APFISICA_GRUPO"
					+ " WHERE CARGAS LIKE '%"+cargaId+"%'"
					+ " AND SEXO IN ("+sexo+")"
					+ " AND CLAVE = ?"					
					+ " AND ACCESO IN ("+acceso+") "+orden;	
			
			lista = enocJdbc.query(comando, new ApFisicaGrupoMapper(), clave);
		}catch(Exception ex){
			System.out.println("Error - aca.apFisica.string.ApFisicaGrupoDao|getGruposAlumnos|:"+ex);
		}
		return lista;
	}
	
	public List<aca.apFisica.spring.ApFisicaGrupo> lisTieneGrupo(String fecha, String orden){
		
		List<aca.apFisica.spring.ApFisicaGrupo> lista 	= new ArrayList<aca.apFisica.spring.ApFisicaGrupo>();
		
		try{
			String comando = " SELECT GRUPO_ID, NOMBRE_GRUPO, LUGAR, INSTRUCTOR, CUPO, TO_CHAR(F_INICIO, 'DD/MM/YYYY') AS F_INICIO,"
					+ " TO_CHAR(F_FINAL, 'DD/MM/YYYY') AS F_FINAL, DIA1, CARGAS, CLAVE, DESCRIPCION, HORA, TO_CHAR(F_CIERRE, 'DD/MM/YYYY') AS F_CIERRE,"
					+ " ACCESO, LIGA, SEXO"
					+ " FROM ENOC.APFISICA_GRUPO"
					+ " WHERE TO_DATE(?,'DD/MM/YYYY') BETWEEN F_INICIO AND F_FINAL "+orden;
			Object[] parametros = new Object[] {fecha};
			lista = enocJdbc.query(comando, new ApFisicaGrupoMapper(), parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.apFisica.ApFisicaGrupoDao|lisTieneGrupo|:"+ex);
		}
		
		return lista;
	}
	
	public HashMap<String,String> mapaActivos(String cargaId){
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista 		 = new ArrayList<aca.Mapa>();		
		try{
			String comando = " SELECT GRUPO_ID AS LLAVE, GRUPO_ID AS VALOR FROM ENOC.APFISICA_GRUPO"
					+ " WHERE CARGAS LIKE '%"+cargaId+"%'"					
					+ " AND TO_DATE(TO_CHAR(now(), 'DD/MM/YYYY'), 'DD/MM/YYYY') BETWEEN F_INICIO AND F_CIERRE";	
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for(aca.Mapa m : lista){
				mapa.put(m.getLlave(), (String)m.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.apFisica.spring.ApFisicaGrupoDao|mapaActivos|:"+ex);
		}
		
		return mapa;
	}

	public List<ApFisicaGrupo> getGrupo(String grupoId){		
		List<ApFisicaGrupo> lista 	= new ArrayList<ApFisicaGrupo>();				
		try{
			String comando = "SELECT * FROM ENOC.APFISICA_GRUPO WHERE GRUPO_ID= ?";			
			Object[] parametros = new Object[] {grupoId};
			lista = enocJdbc.query(comando, new ApFisicaGrupoMapper(), parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.apFisica.spring.ApFisicaGrupoDao|getGrupo|:"+ex);
		}
		return lista;
	}
	
	public List<String> lisMateriasEnFecha(String fecha){		
		List<String> lista 	= new ArrayList<String>();				
		try{
			String comando = "SELECT DISTINCT(CLAVE) FROM ENOC.APFISICA_GRUPO WHERE TO_DATE(?,'DD/MM/YYYY') BETWEEN F_INICIO AND F_FINAL";			
			lista = enocJdbc.queryForList(comando, String.class, fecha);			
		}catch(Exception ex){
			System.out.println("Error - aca.apFisica.spring.ApFisicaGrupoDao|lisMateriasEnFecha|:"+ex);
		}
		return lista;
	}
	
	public List<String> lisCargasEnFecha(String fecha){		
		List<String> lista 	= new ArrayList<String>();				
		try{
			String comando = "SELECT DISTINCT(CARGAS) FROM ENOC.APFISICA_GRUPO WHERE TO_DATE(?,'DD/MM/YYYY') BETWEEN F_INICIO AND F_FINAL";			
			lista = enocJdbc.queryForList(comando, String.class, fecha);			
		}catch(Exception ex){
			System.out.println("Error - aca.apFisica.spring.ApFisicaGrupoDao|lisCargasEnFecha|:"+ex);
		}
		return lista;
	}
	
	public HashMap<String,String> mapaCupoMujeres(String fecha) {
		HashMap<String,String> mapa = new HashMap<String,String>();
		List<aca.Mapa> lista 		 = new ArrayList<aca.Mapa>();
		try{
			String comando = "SELECT CLAVE AS LLAVE, SUM(CUPO) AS VALOR FROM APFISICA_GRUPO WHERE TO_DATE(?,'DD/MM/YYYY') BETWEEN F_INICIO AND F_FINAL AND SEXO IN ('T','F') GROUP BY CLAVE";
			lista = enocJdbc.query(comando, new aca.MapaMapper(), fecha);
			for(aca.Mapa objeto : lista){
				mapa.put(objeto.getLlave(), objeto.getValor());
			}
		}catch(Exception ex){
			System.out.println("Error - aca.apFisica.spring.ApFisicaGrupoDao|mapaCupoMujeres|:"+ex);
		}
		return mapa;
	}
	
	public HashMap<String,String> mapaCupoHombres(String fecha) {
		HashMap<String,String> mapa = new HashMap<String,String>();
		List<aca.Mapa> lista 		 = new ArrayList<aca.Mapa>();
		try{
			String comando = "SELECT CLAVE AS LLAVE, SUM(CUPO) AS VALOR FROM APFISICA_GRUPO WHERE TO_DATE(?,'DD/MM/YYYY') BETWEEN F_INICIO AND F_FINAL AND SEXO IN ('T','M') GROUP BY CLAVE";
			lista = enocJdbc.query(comando, new aca.MapaMapper(), fecha);
			for(aca.Mapa objeto : lista){
				mapa.put(objeto.getLlave(), objeto.getValor());
			}
		}catch(Exception ex){
			System.out.println("Error - aca.apFisica.spring.ApFisicaGrupoDao|mapaCupoMujeres|:"+ex);
		}
		return mapa;
	}
	
	public HashMap<String, ApFisicaGrupo> mapaGrupos() {
		HashMap<String, ApFisicaGrupo> mapa = new HashMap<String, ApFisicaGrupo>();
		List<ApFisicaGrupo> lista 		 = new ArrayList<ApFisicaGrupo>();
		try{
			String comando = "SELECT GRUPO_ID, NOMBRE_GRUPO, LUGAR, INSTRUCTOR, CUPO, TO_CHAR(F_INICIO, 'DD/MM/YYYY') AS F_INICIO,"
					+ "	TO_CHAR(F_FINAL, 'DD/MM/YYYY') AS F_FINAL, DIA1, CARGAS, CLAVE, DESCRIPCION, HORA, TO_CHAR(F_CIERRE, 'DD/MM/YYYY') AS F_CIERRE, ACCESO, LIGA, SEXO"
					+ " FROM ENOC.APFISICA_GRUPO";
			lista = enocJdbc.query(comando, new ApFisicaGrupoMapper());
			for(ApFisicaGrupo grupo : lista){
				mapa.put(grupo.getGrupoId(), grupo);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.apFisica.spring.ApFisicaGrupoDao|mapaGrupos|:"+ex);
		}
		return mapa;
	}
	
	public HashMap<String, String> mapTieneGrupo() {
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista 		 = new ArrayList<aca.Mapa>();
		try{
			String comando = "SELECT CARGAS||CLAVE AS LLAVE, GRUPO_ID AS VALOR FROM ENOC.APFISICA_GRUPO";					
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for(aca.Mapa m : lista){
				mapa.put(m.getLlave(), (String)m.getValor());
			}
		}catch(Exception ex){
			System.out.println("Error - aca.apFisica.spring.ApFisicaGrupoDao|mapTieneGrupo|:"+ex);
		}
		return mapa;
	}
	
	public HashMap<String, String> mapGrupoActivo(String acceso) {
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista 		 = new ArrayList<aca.Mapa>();
		try{
			String comando = "SELECT CARGAS||CLAVE AS LLAVE, GRUPO_ID AS VALOR FROM ENOC.APFISICA_GRUPO"
					+ " WHERE ACCESO = ? AND TO_DATE(TO_CHAR(now(),'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN F_INICIO AND F_CIERRE";
			Object[] parametros = new Object[] {acceso};	
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for(aca.Mapa m : lista){
				mapa.put(m.getLlave(), (String)m.getValor());
			}
		}catch(Exception ex){
			System.out.println("Error - aca.apFisica.spring.ApFisicaGrupoDao|mapGrupoActivo|:"+ex);
		}
		return mapa;
	}
	
	public HashMap<String, String> mapaNombreGrupo() {
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista 		 = new ArrayList<aca.Mapa>();
		try{
			String comando = "SELECT GRUPO_ID AS LLAVE, NOMBRE_GRUPO AS VALOR FROM ENOC.APFISICA_GRUPO";
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for(aca.Mapa m : lista){
				mapa.put(m.getLlave(), (String)m.getValor());
			}
		}catch(Exception ex){
			System.out.println("Error - aca.apFisica.spring.ApFisicaGrupoDao|mapaNombreGrupo|:"+ex);
		}
		return mapa;
	}
	
}