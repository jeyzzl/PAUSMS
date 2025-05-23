package aca.reportes.spring;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;


@Component
public class AltasBajasDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public List<AltasBajas> getListAltasBajas(String cargaId){
		
		List<AltasBajas> lista 				= new ArrayList<AltasBajas>();

		try{
			
			String comando = "SELECT DISTINCT(A.CODIGO_PERSONAL) AS MATRICULA,  " +
					"A.APELLIDO_PATERNO||' '||A.APELLIDO_MATERNO||' '||NOMBRE AS NOMBRE, " + 
					"AP.PLAN_ID, " +
					"M.CARRERA_ID, " +
					"FACULTAD(M.CARRERA_ID) AS FACULTAD_ID " +
					"FROM ENOC.ALUM_PERSONAL A, ENOC.KRDX_CURSO_ACT K, ENOC.ALUM_PLAN AP, ENOC.MAPA_PLAN M " + 
					"WHERE SUBSTR(K.CURSO_CARGA_ID,1,6) = ? " +
					"AND A.CODIGO_PERSONAL = AP.CODIGO_PERSONAL " +
					"AND AP.CODIGO_PERSONAL = K.CODIGO_PERSONAL " +
					"AND A.CODIGO_PERSONAL = K.CODIGO_PERSONAL " +
					"AND M.PLAN_ID = AP.PLAN_ID " +
					"AND AP.ESTADO = '1' " +
					"AND TIPOCAL_ID != 'M' " + 
					"ORDER BY 4,3,2";
				Object[] parametros = new Object[] {cargaId};
				lista = enocJdbc.query(comando, new AltasBajasMapper(), parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.reportes.spring.AltasBajasDao|getListAltasBajas|:"+ex);
		}
		
		return lista;
	}
	
	public List<AltasBajas> getListBajas(String cargaId, String bloqueId){
		
		List<AltasBajas> lista 		= new ArrayList<AltasBajas>();

		try{		
			String comando = "SELECT DISTINCT(CODIGO_PERSONAL) AS MATRICULA,"
					+ " ENOC.ALUM_APELLIDO(CODIGO_PERSONAL) AS NOMBRE,"
					+ " PLAN_ID AS PLAN_ID,"
					+ " CARRERA_ID AS CARRERA_ID,"
					+ " FACULTAD_ID AS FACULTAD_ID"
					+ " FROM ENOC.ALUM_ESTADO WHERE CARGA_ID = ? AND ESTADO = '3' AND BLOQUE_ID = ?"
					+ " ORDER BY 5,4,2";			
			Object[] parametros = new Object[] {cargaId,bloqueId};
			lista = enocJdbc.query(comando, new AltasBajasMapper(), parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.reportes.spring.AltasBajasDao|getListBajas|:"+ex);
		}
		
		return lista;
	}
	
	public int getNumCursos(String cargaId, String matricula, String cadena){
		
		int numero				= 0;
		try{			
			String comando = "SELECT SUM(CREDITOS) AS CUENTA " +
					  "FROM ENOC.KRDX_CURSO_ACT K, ENOC.MAPA_CURSO M  " + 
					  "WHERE SUBSTR(CURSO_CARGA_ID,1,6) = ? " + 
					  "AND K.CURSO_ID = M.CURSO_ID " +
					  "AND CODIGO_PERSONAL = ?  " + cadena;
			Object[] parametros = new Object[] {cargaId,matricula};			
			numero = enocJdbc.queryForObject(comando, Integer.class, parametros);	
			
		}catch(Exception ex){
			System.out.println("Error - aca.reportes.spring.AltasBajasDao|getNumCursos|:"+ex);
		}
		
		return numero;
	}
	
	public int getCreditosCalculo(String cargaId, String matricula, String cadena){
		
		int numero				= 0;

		try{
			
			String comando = "SELECT SUM(CREDITOS) AS CREDITOS " +
					  "FROM MATEO.FES_CC_MATERIA " +
					  "WHERE SUBSTR(CURSO_CARGA_ID,1,6) = ? " +					  
					  "AND MATRICULA = ?  " + cadena ;
			Object[] parametros = new Object[] {cargaId,matricula};			
			numero = enocJdbc.queryForObject(comando, Integer.class, parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.reportes.spring.AltasBajasDao|getCreditosCalculo|:"+ex);
		}
		
		return numero;
	}
	
	public String getAlumEstado(String cargaId, String matricula){
		
		String estado			= "";

		try{
			
			String comando = "SELECT COALESCE(BLOQUE_ID||'-'|| ESTADO,'0-X') AS ESTADO " +
					  "FROM ENOC.ALUM_ESTADO " +	 
					  "WHERE CARGA_ID = ? " +
					  "AND CODIGO_PERSONAL = ? " +
					  "AND ESTADO  != 'M'" ;
			
			Object[] parametros = new Object[] {
				cargaId,matricula	
			};
			
			estado = enocJdbc.queryForObject(comando, String.class,parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.reportes.spring.AltasBajasDao|getAlumEstado|:"+ex);
		}
		
		return estado;
	}
	
	public HashMap<String, String> mapAlumnos(){
		
		HashMap<String,String> mapa		= new HashMap<String,String>();
		List<AltasBajas> lista 				= new ArrayList<AltasBajas>();
				
		try{
			String comando = "SELECT CODIGO_PERSONAL, APELLIDO_PATERNO||' '||APELLIDO_MATERNO||' '||NOMBRE AS ALUMNO FROM ENOC.ALUM_PERSONAL"
					+ " WHERE CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM ENOC.ALUM_ESTADO WHERE ESTADO = '3')";
			lista = enocJdbc.query(comando, new AltasBajasMapper());
			
			for(AltasBajas alta : lista){
				mapa.put(alta.getMatricula(), alta.getNombre());
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.reportes.spring.AltasBajasDao|mapAlumnos|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String, String> mapaNumCursos(String cargaId, String cadena){
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista = new ArrayList<aca.Mapa>();
		try{			
			String comando = "SELECT CODIGO_PERSONAL AS LLAVE, SUM(CREDITOS) AS VALOR FROM ENOC.KRDX_CURSO_ACT K, ENOC.MAPA_CURSO M WHERE SUBSTR(CURSO_CARGA_ID,1,6) = ?"
					+ " AND K.CURSO_ID = M.CURSO_ID "+cadena+" GROUP BY CODIGO_PERSONAL";
			
			Object[] parametros = new Object[] {cargaId};			
			lista = enocJdbc.query(comando, new aca.MapaMapper() , parametros);	
			
			for(aca.Mapa objeto : lista) {
				mapa.put(objeto.getLlave(), objeto.getValor());
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.reportes.spring.AltasBajasDao|mapaNumCursos|:"+ex);
		}
		
		return mapa;
	}
	
}