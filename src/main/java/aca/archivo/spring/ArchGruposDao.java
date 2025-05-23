//Clase  para la tabla ARCH_GRUPOS	
package aca.archivo.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class ArchGruposDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( ArchGrupos grupo)  {
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.ARCH_GRUPOS(GRUPO, " + 
					"IDDOCUMENTO,IDSTATUS) VALUES(TO_NUMBER(?,'999'),TO_NUMBER(?,'999'),TO_NUMBER(?,'99'))";
			
			Object[] parametros = new Object[] {grupo.getGrupo(),grupo.getIdDocumento(),grupo.getIdStatus()};
 			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.spring.ArchGruposDao|insertReg|:"+ex);
		}
		return ok;
	}

	public boolean updateReg( ArchGrupos grupo ) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.ARCH_GRUPOS" + 
					" SET IDSTATUS = TO_NUMBER(?,'999')" +
					" WHERE GRUPO = TO_NUMBER(?,'999')" +
					" AND IDDOCUMENTO = TO_NUMBER(?,'99')";			
			Object[] parametros = new Object[] {grupo.getIdStatus(),grupo.getGrupo(),grupo.getIdDocumento()};
 			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.spring.ArchGruposDao|updateReg|:"+ex);
		}
		return ok;
	}

	public boolean deleteReg( String grupo, String idDocumento ) {
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.ARCH_GRUPOS" + 
					" WHERE GRUPO = TO_NUMBER(?,'999')" +
					" AND IDDOCUMENTO = TO_NUMBER(?,'999')";

			Object[] parametros = new Object[] {grupo,idDocumento};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.spring.ArchGruposDao|deleteReg|:"+ex);
		}
		return ok;
	}
	
	public ArchGrupos mapeaRegId( String grupo, String idDocumento) {
		ArchGrupos archGrup = new ArchGrupos();
				
		try{
			String comando = "SELECT GRUPO, IDDOCUMENTO " +
					"IDSTATUS, FROM ENOC.ARCH_GRUPOS WHERE GRUPO = TO_NUMBER(?,'999') AND IDDOCUMENTO = TO_NUMBER(?,'999')"; 

			Object[] parametros = new Object[] {grupo,idDocumento};
			archGrup = enocJdbc.queryForObject(comando, new ArchGruposMapper(), parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.spring.ArchGruposDao|mapeaRegId|:"+ex);
		}
		return archGrup;
	}

	public boolean existeReg( String grupo, String idDocumento) {
		boolean ok 			= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.ARCH_GRUPOS WHERE GRUPO = TO_NUMBER(?,'999') AND IDDOCUMENTO = TO_NUMBER(?,'999')"; 

			Object[] parametros = new Object[] {grupo,idDocumento};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros)>=1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.spring.ArchGruposDao|existeReg|:"+ex);
		}
		return ok;
	}

	public boolean existeRegSinStatus( String grupo, String idDocumento) {
		boolean ok 			= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.ARCH_GRUPOS" + 
					" WHERE GRUPO = TO_NUMBER(?,'999')" +
					" AND IDDOCUMENTO = TO_NUMBER(?,'999')";

			Object[] parametros = new Object[] {grupo,idDocumento};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros)>=1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.spring.ArchGruposDao|existeReg|:"+ex);
		}
		return ok;
	}
	
	public String maxReg() {
		String maximo			= "0";
		
		try{
			String comando = "SELECT COALESCE(MAX(GRUPO)+1, 1) AS MAXIMO FROM ENOC.ARCH_GRUPOS"; 
			
			if (enocJdbc.queryForObject(comando,Integer.class)>=1){
				maximo = enocJdbc.queryForObject(comando,String.class);
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.spring.ArchGruposDao|maxReg|:"+ex);
		}		
		return String.valueOf(maximo);
	}

	public List<ArchGrupos> getListAll( String orden ) {
		List<ArchGrupos> lista 	= new ArrayList<ArchGrupos>();
		
		String comando	= "";
		
		try{
			comando = "SELECT GRUPO, IDDOCUMENTO, IDSTATUS" +
					" FROM ENOC.ARCH_GRUPOS "+orden; 
			
			lista = enocJdbc.query(comando, new ArchGruposMapper());	
			
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.spring.ArchGruposDao|getListAll|:"+ex);
		}
		return lista;
	}
	
	public List<ArchGrupos> getListGrupo( String grupoId, String orden ) {
		List<ArchGrupos> lista 	= new ArrayList<ArchGrupos>();
				
		String comando	= "";
		
		try{
			comando = "SELECT GRUPO, IDDOCUMENTO, IDSTATUS" +
					" FROM ENOC.ARCH_GRUPOS" + 
					" WHERE GRUPO = "+grupoId+" "+orden;
			
			lista = enocJdbc.query(comando, new ArchGruposMapper());	
			
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.spring.ArchGruposDao|getListGrupo|:"+ex);
		}		
		return lista;
	}
	
	public List<ArchGrupos> getListGrupoCarrera( String carreraId, String orden ) {
		List<ArchGrupos> lista 	= new ArrayList<ArchGrupos>();
		try{
			String comando = " SELECT GRUPO, IDDOCUMENTO, IDSTATUS"
					+ " FROM ENOC.ARCH_GRUPOS"
					+ " WHERE (SELECT REPLACE(GRUPOS,' ','-')||'-' FROM ENOC.ARCH_GRUPOS_CARRERA WHERE CARRERA = ?) LIKE '%-'||GRUPO||'-%' " +orden;			
			lista = enocJdbc.query(comando, new ArchGruposMapper(), carreraId);	
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.spring.ArchGruposDao|getListGrupoCarrera|:"+ex);
		}
		return lista;
	}
	
	public HashMap<String,String> mapaTotPorGrupo( ){
		
		HashMap<String,String> mapa	= new HashMap<String, String>();
		List<aca.Mapa> lista = new ArrayList<aca.Mapa>();
		String comando	             		= "";	
		
		try{			
			comando = "SELECT GRUPO AS LLAVE, COUNT(*) AS VALOR FROM ENOC.ARCH_GRUPOS GROUP BY GRUPO";		
			lista = enocJdbc.query(comando, new aca.MapaMapper());			
			for (aca.Mapa map: lista){				
				mapa.put(map.getLlave(), (String)map.getValor());
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.spring.ArchGruposDao|mapaTotPorGrupo|:"+ex);
		}
		
		return mapa;
	}
	
	public HashMap<String,String> mapaGruposAlumno( String codigoPersonal ){
		
		HashMap<String,String> mapa	= new HashMap<String, String>();
		List<aca.Mapa> lista = new ArrayList<aca.Mapa>();		
		try{			
			String comando = " SELECT GRUPO AS LLAVE, COUNT(*) AS VALOR FROM ARCH_GRUPOS"
					+ " WHERE IDDOCUMENTO||IDSTATUS IN (SELECT IDDOCUMENTO||IDSTATUS FROM ARCH_DOCALUM WHERE MATRICULA = ?)"
					+ " GROUP BY GRUPO";
			Object[] parametros = new Object[] {codigoPersonal};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);	
			for (aca.Mapa map: lista){				
				mapa.put(map.getLlave(), (String)map.getValor());
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.spring.ArchGruposDao|mapaGruposAlumno|:"+ex);
		}
		
		return mapa;
	}	
}