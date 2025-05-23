// Clase Util para la tabla de Carga
package aca.carga.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class CargaPermisoDao{
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( CargaPermiso permiso ) {
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.CARGA_PERMISO"+ 
				"(CARGA_ID, CARRERA_ID, RECUPERACION, USUARIO ) VALUES( ?, ?, ?, ? )";
			
			
			Object[] parametros = new Object[] {permiso.getCargaId(),permiso.getCarreraId(),permiso.getRecuperacion(), permiso.getUsuario()};
 			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaPermisoDao|insertReg|:"+ex);			
		}		
		return ok;
	}
	
	public boolean updateReg( CargaPermiso permiso ) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.CARGA_PERMISO SET RECUPERACION = ?, USUARIO = ?"
				+ " WHERE CARGA_ID = ?"
				+ " AND CARRERA_ID = ?";						
			Object[] parametros = new Object[] {permiso.getRecuperacion(),permiso.getCargaId(),permiso.getUsuario(),permiso.getCarreraId()}; 			 			
 			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaPermisoDao|updateReg|:"+ex); 
		}
		return ok;
	}
	
	public boolean deleteReg( String cargaId, String carreraId ) {
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.CARGA_PERMISO WHERE CARGA_ID = ? AND CARRERA_ID = ?";
			
			Object[] parametros = new Object[] {cargaId,carreraId};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaPermisoDao|deleteReg|:"+ex);			
		}
		return ok;
	}
	
	
	public CargaPermiso mapeaRegId(  String cargaId, String carreraId ) {
		CargaPermiso permiso = new CargaPermiso();
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.CARGA_PERMISO"
					+ " WHERE CARGA_ID = ?"
					+ " AND CARRERA_ID = ?";
			
			Object[] parametros = new Object[] {cargaId,carreraId};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				comando = "SELECT CARGA_ID, CARRERA_ID, RECUPERACION, USUARIO"
					+ " FROM ENOC.CARGA_PERMISO"
					+ " WHERE CARGA_ID = ?"
					+ " AND CARRERA_ID = ?";
			
				permiso = enocJdbc.queryForObject(comando, new CargaPermisoMapper(), parametros);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaPermisoDao|mapeaRegId|:"+ex);
		}
		return permiso;
	}
	
	public boolean existeReg( String cargaId, String carreraId) {
		boolean 			ok 	= false;		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.CARGA_PERMISO"
					+ " WHERE CARGA_ID = ?"
					+ " AND CARRERA_ID = ?";
			
			Object[] parametros = new Object[] {cargaId,carreraId};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaPermisoDao|existeReg|:"+ex);
		}
		return ok;
	}
	
	public boolean esCarreraRecuperacion( String cargaId, String carreraId) {
		boolean 			ok 		= false;
		
		try{
			String comando = "SELECT RECUPERACION FROM ENOC.CARGA_PERMISO "+ 
				"WHERE CARGA_ID = ? "+
				"AND CARRERA_ID = ?";
			
			Object[] parametros = new Object[] {cargaId,carreraId};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaPermisoDao|esCarreraRecuperacion|:"+ex);
		}		
		return ok;
	}
	
	public boolean carreraPermitida( String cargaId, String carreraId) {
		boolean 			ok 		= false;
		
		try{
			String comando = "SELECT CARRERA_ID FROM ENOC.CARGA_PERMISO "+ 
				"WHERE CARGA_ID = ? "+
				"AND CARRERA_ID = ?";
			
			Object[] parametros = new Object[] {cargaId,carreraId};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaPermisoDao|carreraPermitida|:"+ex);
		}
		return ok;
	}
	
	public List<CargaPermiso> getListAll( String orden ) {
		List<CargaPermiso> lista		= new ArrayList<CargaPermiso>();
		String comando		= "";
		
		try{
			comando = "SELECT CARGA_ID, CARRERA_ID, RECUPERACION, USUARIO FROM ENOC.CARGA_PERMISO "+ orden; 
			
			lista = enocJdbc.query(comando, new CargaPermisoMapper());
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaPermisoDao|getListAll|:"+ex);
		}
		return lista;
	}
	
	public List<CargaPermiso> getLista( String cargaId, String orden ) {
		List<CargaPermiso> lista		= new ArrayList<CargaPermiso>();	
		try{
			String comando = "SELECT CARGA_ID, CARRERA_ID, RECUPERACION, USUARIO FROM ENOC.CARGA_PERMISO WHERE CARGA_ID = ? "+ orden;
			Object[] parametros = new Object[] {cargaId};
			lista = enocJdbc.query(comando, new CargaPermisoMapper(), parametros);
		}catch(Exception ex){
			System.out.println("Error- aca.carga.spring.CargaPermisoDao|getLista|:"+ex);
		}
		
		return lista;
	}
	
	public List<CargaPermiso> getListaSinPermiso( String cargaId, String orden ) {
		List<CargaPermiso> lista		= new ArrayList<CargaPermiso>();	
		try{
			String comando = "SELECT '"+cargaId+"' AS carga_Id, CARRERA_ID, 'N' AS RECUPERACION, '0' AS USUARIO"
				+ " FROM ENOC.CAT_CARRERA " 
				+ " WHERE CARRERA_ID NOT IN "
				+ "	(SELECT CARRERA_ID FROM ENOC.CARGA_PERMISO WHERE carga_Id = ?) "+orden;			
			lista = enocJdbc.query(comando, new CargaPermisoMapper(), cargaId);			
		}catch(Exception ex){
			System.out.println("Error- aca.carga.spring.CargaPermisoDao|getListaSinPermiso|:"+ex);
		}
		return lista;
	}
	
	public HashMap<String, String> mapaPermisos(String cargaId){
		HashMap<String, String> mapa 	= new HashMap<String, String>();
		List<aca.Mapa> lista			= new ArrayList<aca.Mapa>();		
		try{			
			String comando = "SELECT CARRERA_ID AS LLAVE, USUARIO AS VALOR "
					+ " FROM ENOC.CARGA_PERMISO"
					+ " WHERE CARGA_ID = ?";
			Object[] parametros = new Object[] {cargaId};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for(aca.Mapa map :lista){
				mapa.put(map.getLlave(), (String)map.getValor());
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaPermisoDao|mapaPermisos|:"+ex);
		}
		
		return mapa;
	}
}