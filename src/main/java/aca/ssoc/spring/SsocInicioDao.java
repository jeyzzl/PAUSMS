//Clase  para la tabla SSOC_INICIO
package aca.ssoc.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;


@Component
public class SsocInicioDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( SsocInicio inicio ){
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.SSOC_INICIO" + 
					" (CODIGO_PERSONAL, PLAN_ID, FECHA, PORCENTAJE, SEMESTRE)" +
					" VALUES(?,?,TO_DATE(?,'DD/MM/YYYY'), TO_NUMBER(?,'999'), TO_NUMBER(?,'99'))";
			Object[] parametros = new Object[] {inicio.getCodigoPersonal(), inicio.getPlanId(), inicio.getFecha(), inicio.getPorcentaje(),
					 inicio.getSemestre()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
			
		}catch(Exception ex){
			System.out.println("Error - aca.ssoc.spring.SsocInicioDao|insertReg|:"+ex);
		}
		
		return ok;
	}
	
	public boolean updateReg( SsocInicio inicio ){
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.SSOC_INICIO" + 
					" SET FECHA = ?," +
					" PORCENTAJE = TO_NUMBER(?,'999')," +
					" SEMESTRE = TO_NUMBER(?,'99')" +
					" WHERE CODIGO_PERSONAL = ?" +
					" AND PLAN_ID = ?";
			Object[] parametros = new Object[] {inicio.getFecha(), inicio.getPorcentaje(), inicio.getSemestre(), inicio.getCodigoPersonal(),
					 inicio.getPlanId()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
			
		}catch(Exception ex){
			System.out.println("Error - aca.ssoc.spring.SsocInicioDao|updateReg|:"+ex);
		}
		
		return ok;
	}
	
	public boolean deleteReg( String codigoPersonal, String planId ) {
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.SSOC_INICIO" + 
					" WHERE CODIGO_PERSONAL = ?" +
					" AND PLAN_ID = ?";
			Object[] parametros = new Object[] {codigoPersonal,planId};
			if (enocJdbc.update(comando, parametros)==1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.ssoc.spring.SsocInicioDao|deleteReg|:"+ex);
		}
		
		return ok;
	}
	
	public SsocInicio mapeaRegId( String codigoPersonal, String planId){
		
		SsocInicio inicio = new SsocInicio();			
		try{
			String comando = "SELECT CODIGO_PERSONAL," +
					" PLAN_ID, TO_CHAR(FECHA,'DD/MM/YYY') AS FECHA, PORCENTAJE, SEMESTRE" +				
					" FROM ENOC.SSOC_INICIO" + 
					" WHERE CODIGO_PERSONAL = ?" +
					" AND PLAN_ID = ?";
			Object[] parametros = new Object[] {inicio};
			inicio = enocJdbc.queryForObject(comando, new SsocInicioMapper(), parametros);		
		}catch(Exception ex){
			System.out.println("Error - aca.ssoc.spring.SsocInicioDao|mapeaRegId|:"+ex);
		}
		return inicio;
	}
	
	public boolean existeReg( String codigoPersonal, String planId ){
		boolean ok 			= false;		
		try{
			String comando = "SELECT COUNT (*) FROM ENOC.SSOC_INICIO WHERE CODIGO_PERSONAL = ? AND PLAN_ID = ?";
			Object[] parametros = new Object[] {codigoPersonal, planId};
			if (enocJdbc.update(comando, parametros)==1){
				ok = true;
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.ssoc.spring.SsocInicioDao|existeReg|:"+ex);
		}
		
		return ok;
	}
	
	public String getFecha( String codigoPersonal, String planId){
		
		String fecha			= "01/01/1950";
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.SSOC_INICIO WHERE CODIGO_PERSONAL = ? AND PLAN_ID = ?";
			Object[] parametros = new Object[] {codigoPersonal, planId};
 			if (enocJdbc.queryForObject(comando,Integer.class, parametros)>=1){
 				comando = "SELECT TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA FROM ENOC.SSOC_INICIO" + 
 						" WHERE CODIGO_PERSONAL = ?" +
 						" AND PLAN_ID = ?";
 				fecha = enocJdbc.queryForObject(comando,String.class, parametros);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.ssoc.spring.SsocInicioDao|getFecha|:"+ex);
		}
		
		return fecha;
	}
	
	public String getMinFecha( String codigoPersonal, String planId){

		String fecha			= "01/01/1950"; 
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.SSOC_DOCALUM WHERE CODIGO_PERSONAL = ? AND PLAN_ID = ?";
			Object[] parametros = new Object[] {codigoPersonal, planId};
 			if (enocJdbc.queryForObject(comando,Integer.class, parametros) >= 1){
 				comando = "SELECT TO_CHAR(MIN(FECHA),'DD/MM/YYYY') AS FECHA FROM ENOC.SSOC_DOCALUM" + 
 						" WHERE CODIGO_PERSONAL = ?" +
 						" AND PLAN_ID = ?";
 				fecha = enocJdbc.queryForObject(comando,String.class, parametros);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.ssoc.spring.SsocInicioDao|getMinFecha|:"+ex);
		}
		
		return fecha;
	}
	
	public List<SsocInicio> getListAll( String orden ){
		List<SsocInicio> lista 			= new ArrayList<SsocInicio>();
		
		try{
			String comando = "SELECT CODIGO_PERSONAL, PLAN_ID, FECHA , PORCENTAJE, SEMESTRE FROM ENOC.SSOC_INICIO " +orden; 
			lista = enocJdbc.query(comando, new SsocInicioMapper());
			
		}catch(Exception ex){
			System.out.println("Error - aca.ssoc.spring.SsocInicioDao|getListAll|:"+ex);
		}
		
		return lista;
	}
	
	public List<SsocInicio> getListAlumConDoc( String documentoId, String fechaIni, String fechaFin, String orden ){
		List<SsocInicio> lista 			= new ArrayList<SsocInicio>();
		
		try{
			String comando = "SELECT CODIGO_PERSONAL, PLAN_ID, TO_CHAR(FECHA ,'DD/MM/YYYY') AS FECHA, PORCENTAJE, SEMESTRE"
					+ " FROM ENOC.SSOC_INICIO"
					+ " WHERE CODIGO_PERSONAL||PLAN_ID IN ("
					+ " SELECT CODIGO_PERSONAL||PLAN_ID FROM ENOC.SSOC_DOCALUM "
					+ "	WHERE DOCUMENTO_ID = TO_NUMBER(?,'99')"
					+ "	AND FECHA BETWEEN TO_DATE(?,'DD/MM/YYYY') AND TO_DATE(?,'DD/MM/YYYY')" 
					+ "	AND ENTREGADO = 'S' "
				 	+ " ) " +orden;
			lista = enocJdbc.query(comando, new SsocInicioMapper(),documentoId,fechaIni,fechaFin);
			
		}catch(Exception ex){
			System.out.println("Error - aca.ssoc.spring.SsocInicioDao|getListAlumConDoc|:"+ex);
		}
		
		return lista;
	}
	
	public List<SsocInicio> getListInscrito( String orden ){
		List<SsocInicio> lista 			= new ArrayList<SsocInicio>();
		
		try{
			String comando = " SELECT DISTINCT(CODIGO_PERSONAL), PLAN_ID , TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA , PORCENTAJE, SEMESTRE"
					+ " FROM ENOC.SSOC_INICIO WHERE CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM ENOC.INSCRITOS) " +orden;
			lista = enocJdbc.query(comando, new SsocInicioMapper());
			
		}catch(Exception ex){
			System.out.println("Error - aca.ssoc.spring.SsocInicioDao|getListInscrito|:"+ex);
		}
		
		return lista;
	}
	
	public List<SsocInicio> getListActivo( String orden ){
		List<SsocInicio> lista = new ArrayList<SsocInicio>();
		
		try{
			String comando = "SELECT CODIGO_PERSONAL, PLAN_ID, TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA, PORCENTAJE, SEMESTRE " +
					  " FROM ENOC.SSOC_INICIO WHERE CODIGO_PERSONAL||PLAN_ID IN " + 
						" (SELECT CODIGO_PERSONAL||PLAN_ID FROM ENOC.SSOC_DOCALUM)" + 
						" AND CODIGO_PERSONAL||PLAN_ID NOT IN" +
						"   (SELECT CODIGO_PERSONAL||PLAN_ID FROM ENOC.SSOC_DOCALUM " + 
						"    WHERE DOCUMENTO_ID IN (21,22,23)) " +orden;
			lista = enocJdbc.query(comando, new SsocInicioMapper());
			
		}catch(Exception ex){
			System.out.println("Error - aca.ssoc.spring.SsocInicioDao|getListActivo|:"+ex);
		}
		
		return lista;
	}

	public List<SsocInicio> getListActivoPorFechaInicio(String fechaIni, String fechaFin){
		List<SsocInicio> lista = new ArrayList<SsocInicio>();
		
		try{
			String comando = "SELECT * FROM SSOC_INICIO"
					+ " WHERE CODIGO_PERSONAL||PLAN_ID IN (SELECT CODIGO_PERSONAL||PLAN_ID FROM ENOC.SSOC_DOCALUM WHERE DOCUMENTO_ID = 3 AND FECHA BETWEEN TO_DATE(?,'DD/MM/YYYY') AND TO_DATE(?,'DD/MM/YYYY'))"
					+ " AND CODIGO_PERSONAL||PLAN_ID NOT IN (SELECT CODIGO_PERSONAL||PLAN_ID FROM ENOC.SSOC_DOCALUM WHERE DOCUMENTO_ID = 22)";
			Object[] parametros = new Object[] {fechaIni, fechaFin};			
			lista = enocJdbc.query(comando,  new SsocInicioMapper(), parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.ssoc.spring.SsocInicioDao|getListActivoPorFechaInicio|:"+ex);
		}
		
		return lista;
	}
	
	public HashMap<String,String> mapaAlumnoSector() {	
		HashMap<String,String> mapa = new HashMap<String,String>();
		List<aca.Mapa> lista		= new ArrayList<aca.Mapa>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL AS LLAVE, SECTOR AS VALOR FROM ENOC.SSOC_ASIGNACION WHERE SECTOR != 0 ORDER BY CODIGO_PERSONAL,F_INICIO";
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for (aca.Mapa map : lista ) {
				mapa.put(map.getLlave(), map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.ssoc.spring.SsocInicioDao|mapaAlumnoSector|:"+ex);
			ex.printStackTrace();
		}
		
		return mapa;		
	}
	
	public HashMap<String,String> mapaAvanceActual() {
		HashMap<String,String> mapa = new HashMap<String,String>();
		List<aca.Mapa> lista		= new ArrayList<aca.Mapa>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL||PLAN_ID AS LLAVE, ENOC.ALUM_PROMEDIO_CREDITOS(CODIGO_PERSONAL, PLAN_ID) AS VALOR"
					+ " FROM ENOC.SSOC_INICIO"
					+ " WHERE CODIGO_PERSONAL IN (SELECT DISTINCT(CODIGO_PERSONAL) FROM ENOC.INSCRITOS)";
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for (aca.Mapa map : lista ) {
				mapa.put(map.getLlave(), map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.ssoc.spring.SsocInicioDao|mapaAvanceActual|:"+ex);
			ex.printStackTrace();
		}
		
		return mapa;		
	}
	
}