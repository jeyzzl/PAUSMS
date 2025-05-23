package aca.kardex.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class KrdxMaximoDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;

	public boolean insertReg(KrdxMaximo maximo){
		boolean ok = false;
		try{
			String comando = "INSERT INTO ENOC.KRDX_MAXIMO " 
				+ " (CODIGO_PERSONAL, CURSO_CARGA_ID, EVALUADAS, PUNTOS, MAXIMO, CARRERA_ID) " 
				+ " VALUES( ?, ?, TO_NUMBER(?,'999.99'), TO_NUMBER(?,'999.99'), TO_NUMBER(?,'999.99'), ?";
			
			Object[] parametros = new Object[] {maximo.getCodigoPersonal(),maximo.getCursoCargaId(),maximo.getEvaluadas(), maximo.getPuntos(),
					maximo.getMaximo(), maximo.getCarreraId()
			};
 			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
					
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.spring.KrdxMaximoDao|insertReg|:"+ex);
		}
		
		return ok;
	}
	
	public boolean updateReg(KrdxMaximo maximo){
		boolean ok = false;
		try{
			//System.out.println("Datos:"+nota+":"+notaConva+":"+folio);
			String comando = "UPDATE ENOC.KRDX_MAXIMO" + 
				" SET" +
				" CARRERA_ID = ?," +
				" MAXIMO = TO_NUMBER(?,'999.99')," +
				" PUNTOS = TO_NUMBER(?,'999.99')," +
				" EVALUADAS = TO_NUMBER(?,'999.99') " +
				" WHERE CURSO_CARGA_ID = ? AND CODIGO_PERSONAL = ?";
			
			Object[] parametros = new Object[] {maximo.getCarreraId(), maximo.getMaximo(), maximo.getPuntos(),maximo.getEvaluadas(),
					maximo.getCursoCargaId(),maximo.getCodigoPersonal()
			};
 			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
			
		}catch(Exception eCurso_id2){
			System.out.println("Error - aca.kardex.spring.KrdxMaximoDao|updateReg|:"+eCurso_id2);
		}
		
		return ok;
	}	
	
	public boolean deleteReg(String codigoPersonal, String cursoCargaId){
		boolean ok = false;
		try{
			String comando = "DELETE FROM ENOC.KRDX_MAXIMO "+ 
				"WHERE CODIGO_PERSONAL = ? AND CURSO_CARGA_ID = ?";
			Object[] parametros = new Object[] {codigoPersonal,cursoCargaId};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
			
		}catch(Exception eCurso_id2){
			System.out.println("Error - aca.kardex.spring.KrdxMaximoDao|deletReg|:"+eCurso_id2);
		}
		
		return ok;
	}
	
	public KrdxMaximo mapeaRegId(String codigoPersonal, String cursoCargaId ){
		
		KrdxMaximo krdxCurso = new KrdxMaximo();	
		try{ 
			String comando = " SELECT CODIGO_PERSONAL, CURSO_CARGA_ID, EVALUADAS, PUNTOS, MAXIMO, CARRERA_ID) " +
				" WHERE CODIGO_PERSONAL = ?" +
				" AND CURSO_CARGA_ID = ?";			
			Object[] parametros = new Object[] {codigoPersonal, cursoCargaId};
			krdxCurso = enocJdbc.queryForObject(comando, new KrdxMaximoMapper(), parametros);			
		}catch(Exception eCurso_id2){
			System.out.println("Error - aca.kardex.spring.KrdxMaximoDao|mapeaRegId|:"+eCurso_id2);
		}
		
		return krdxCurso;
	}
	
	public boolean existeReg(String codigoPersonal, String cursoCargaId){
		boolean 		ok 	= false;		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.KRDX_CURSO_IMP WHERE CODIGO_PERSONAL = ? AND CURSO_CARGA_ID = ?";
			Object[] parametros = new Object[] {codigoPersonal,cursoCargaId};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros)>=1){
				ok = true;
			}			
		}catch(Exception eCurso_id2){
			System.out.println("Error - aca.kardex.spring.KrdxMaximoDao|existeReg|:"+eCurso_id2);
		}		
		return ok;
	}
	
	public HashMap<String,String> mapaPorCarga(int minimo, int maximo) {
		
		HashMap<String,String> mapa 	= new HashMap<String,String>();
		List<aca.Mapa> lista = new ArrayList<aca.Mapa>();
		
		try{
			String comando = "SELECT SUBSTR(CURSO_CARGA_ID,1,6) AS LLAVE, COUNT(*) AS VALOR FROM ENOC.KRDX_MAXIMO"
					+ " WHERE SUBSTR(CURSO_CARGA_ID,1,6) IN (SELECT CARGA_ID FROM ENOC.CARGA WHERE TO_DATE( TO_CHAR(SYSDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN F_INICIO AND F_FINAL)"
					+ " AND MAXIMO BETWEEN "+minimo+" AND "+maximo+""
					+ " GROUP BY SUBSTR(CURSO_CARGA_ID,1,6)";
			lista = enocJdbc.query(comando, new aca.MapaMapper());	
			for(aca.Mapa map : lista){				
				mapa.put(map.getLlave(), (String)map.getValor());
			}			
			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.spring.KrdxMaximoDao|mapaPorCarga|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String,String> mapaPorFacultades(int minimo, int maximo) {
		
		HashMap<String,String> mapa 	= new HashMap<String,String>();
		List<aca.Mapa> lista = new ArrayList<aca.Mapa>();
		
		try{
			String comando = "SELECT FACULTAD(CARRERA_ID) AS LLAVE, COUNT(*) AS VALOR FROM ENOC.KRDX_MAXIMO"
					+ " WHERE SUBSTR(CURSO_CARGA_ID,1,6) IN (SELECT CARGA_ID FROM ENOC.CARGA WHERE TO_DATE( TO_CHAR(SYSDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN F_INICIO AND F_FINAL)"
					+ " AND MAXIMO BETWEEN "+minimo+" AND "+maximo+""
					+ " GROUP BY FACULTAD(CARRERA_ID)";
			lista = enocJdbc.query(comando, new aca.MapaMapper());	
			for(aca.Mapa map : lista){				
				mapa.put(map.getLlave(), (String)map.getValor());
			}			
			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.spring.KrdxMaximoDao|mapaPorFacultades|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String,String> mapaPorCarreras(int minimo, int maximo) {
		
		HashMap<String,String> mapa 	= new HashMap<String,String>();
		List<aca.Mapa> lista = new ArrayList<aca.Mapa>();
		
		try{
			String comando = "SELECT CARRERA_ID AS LLAVE, COUNT(*) AS VALOR FROM ENOC.KRDX_MAXIMO"
					+ " WHERE SUBSTR(CURSO_CARGA_ID,1,6) IN (SELECT CARGA_ID FROM ENOC.CARGA WHERE TO_DATE( TO_CHAR(SYSDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN F_INICIO AND F_FINAL)"
					+ " AND MAXIMO BETWEEN "+minimo+" AND "+maximo+""
					+ " GROUP BY CARRERA_ID";
			lista = enocJdbc.query(comando, new aca.MapaMapper());	
			for(aca.Mapa map : lista){				
				mapa.put(map.getLlave(), (String)map.getValor());
			}			
			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.spring.KrdxMaximoDao|mapaPorCarreras|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String,String> mapaPorAlumno(int minimo, int maximo) {
		
		HashMap<String,String> mapa 	= new HashMap<String,String>();
		List<aca.Mapa> lista = new ArrayList<aca.Mapa>();
		
		try{
			String comando = "SELECT CARRERA_ID||CODIGO_PERSONAL AS LLAVE, COUNT(*) AS VALOR FROM ENOC.KRDX_MAXIMO"
					+ " WHERE SUBSTR(CURSO_CARGA_ID,1,6) IN (SELECT CARGA_ID FROM ENOC.CARGA WHERE TO_DATE( TO_CHAR(SYSDATE,'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN F_INICIO AND F_FINAL)"
					+ " AND MAXIMO BETWEEN "+minimo+" AND "+maximo+""
					+ " GROUP BY CARRERA_ID||CODIGO_PERSONAL";
			lista = enocJdbc.query(comando, new aca.MapaMapper());	
			for(aca.Mapa map : lista){				
				mapa.put(map.getLlave(), (String)map.getValor());
			}			
			
		}catch(Exception ex){
			System.out.println("Error - aca.kardex.spring.KrdxMaximoDao|mapaPorAlumno|:"+ex);
		}
		
		return mapa;
	}
	
}