package aca.rol.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class RolOpcionDao{
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(RolOpcion rol ) {
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO "+
				"ENOC.ROL_OPCION(ROL_ID, OPCION_ID ) "+
				"VALUES( TO_NUMBER(?,'999'), ? ) ";
			
			Object[] parametros = new Object[] {rol.getRolId(),rol.getOpcionId()};
 			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.rol.spring.RolOpcion|insertReg|:"+ex);			
		}
		return ok;
	}	
	
	public boolean updateReg(RolOpcion rol ) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.ROL_OPCION "+ 
				"SET OPCION_ID = ? "+
				"WHERE ROL_ID = TO_NUMBER(?,'999')";
			
			Object[] parametros = new Object[] {rol.getOpcionId(),rol.getRolId()}; 			 			
 			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.rol.spring.RolOpcion|updateReg|:"+ex);		
		}
		return ok;
	}	
	
	
	public boolean deleteTodos(String rolId ) {
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.ROL_OPCION WHERE ROL_ID = TO_NUMBER(?,'999')";				
			Object[] parametros = new Object[] {rolId};
			if (enocJdbc.update(comando,parametros) >= 1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.rol.spring.RolOpcion|deleteReg|:"+ex);			
		}
		return ok;
	}
	
	public RolOpcion mapeaRegId( String rolId) {
		RolOpcion rol = new RolOpcion();
		
		try{
			String comando = "SELECT ROL_ID, OPCION_ID FROM ENOC.ROL_OPCION WHERE ROL_ID = TO_NUMBER(?,'999')";
			
			Object[] parametros = new Object[] {rolId};
			rol = enocJdbc.queryForObject(comando, new RolOpcionMapper(), parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.rol.spring.RolOpcion|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}
		return rol;
	}
	
	public boolean existeReg(String rolId, String opcionId) {
		boolean 		ok 	= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.ROL_OPCION WHERE ROL_ID = TO_NUMBER(?,'999') AND OPCION_ID = ?";			
			Object[] parametros = new Object[] {rolId,opcionId};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros)>=1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.rol.spring.RolOpcion|existeReg|:"+ex);
		}
		return ok;
	}
	
	// Regresa un String que contiene las opciones de un rol
	public String getOpcRol( String rolId ) {
		String opcion		= "";
		List<String> lista  = new ArrayList<String>();
		try{
			String comando = "SELECT OPCION_ID FROM ENOC.ROL_OPCION WHERE ROL_ID = ? ";
			Object[] parametros = new Object[] {rolId};
			lista = enocJdbc.queryForList(comando,String.class, parametros);
			int row=0;
			for (String op : lista) {
				row++;
				if (row==1) opcion = op; else opcion = opcion + "@@"+op; 
			}
		}catch(Exception ex){
			System.out.println("Error - aca.menu.OpcionUtil|getOpcUser|:"+ex);
		}
		return opcion;
	}
	
	// Cuenta el numero de opciones que contiene un rol
	public String getNumOpciones (String rolId ) {
		String total		= "0";		
		try{
			String comando = "SELECT COUNT(OPCION_ID) AS TOTAL FROM ENOC.ROL_OPCION WHERE ROL_ID = "+rolId+"";			
			Object[] parametros = new Object[] {rolId};			
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
				total = enocJdbc.queryForObject(comando, String.class, parametros);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.menu.OpcionUtil|getNumOpciones|:"+ex);
		}
		return total;
	}
	
	
	public List<RolOpcion> getListAll( String orden) {
		List<RolOpcion> lis		= new ArrayList<RolOpcion>();
		String comando		= "";
		
		try{
			comando = "SELECT ROL_ID, OPCION_ID FROM ENOC.ROL_OPCION "+orden;	 
			
			lis = enocJdbc.query(comando, new RolOpcionMapper());
			
		}catch(Exception ex){
			System.out.println("Error - aca.rol.spring.RolOpcionDao|getListAll|:"+ex);
		}
		return lis;
	}
	
	public List<String> lisPorRol( String rol, String orden){
		List<String> lis		= new ArrayList<String>();			
		try{
			String comando = "SELECT OPCION_ID FROM ENOC.ROL_OPCION WHERE ROL_ID = ? "+orden;
			lis = enocJdbc.queryForList(comando, String.class, rol);			
		}catch(Exception ex){
			System.out.println("Error - aca.rol.spring.RolOpcionDao|lisPorRol|:"+ex);
		}
		return lis;
	}
	
	public HashMap<String, String> mapaOpciones( String rolId ) {
		HashMap<String, String> mapa 	= new HashMap<String, String>();
		List<aca.Mapa> lista 			= new ArrayList<aca.Mapa>();		
		try{
			String comando = " SELECT OPCION_ID AS LLAVE, OPCION_ID AS VALOR FROM ENOC.ROL_OPCION WHERE ROL_ID = TO_NUMBER(?,'999')";
			Object[] parametros = new Object[] {rolId};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for(aca.Mapa map : lista){				
				mapa.put(map.getLlave(), (String)map.getValor());
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.rol.spring.RolOpcionDao|mapaOpciones|:"+ex);
		}
		return mapa;
	}
	
	public HashMap<String, String> mapaTotalOpciones( String rolId ) {		
		HashMap<String, String> mapa 	= new HashMap<String, String>();		
		List<aca.Mapa> lista 			= new ArrayList<aca.Mapa>();
		
		try{
			String comando = " SELECT ROL_ID AS LLAVE, COUNT(*) AS VALOR FROM ENOC.ROL_OPCION GROUP BY ROL_ID";			
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for(aca.Mapa map : lista){				
				mapa.put(map.getLlave(), (String)map.getValor());
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.rol.spring.RolOpcionDao|mapaTotalOpciones|:"+ex);
		}
		return mapa;
	}
	
	public HashMap<String, String> mapaOpcionesPorModulo( String rolId ) {		
		HashMap<String, String> mapa 	= new HashMap<String, String>();		
		List<aca.Mapa> lista 						= new ArrayList<aca.Mapa>();		
		try{
			String comando = "SELECT MO.MODULO_ID AS LLAVE, COUNT(RO.OPCION_ID) AS VALOR FROM ENOC.ROL_OPCION RO, ENOC.MODULO_OPCION MO"
					+ " WHERE MO.OPCION_ID = RO.OPCION_ID AND ROL_ID = TO_NUMBER(?,'999')"
					+ " GROUP BY MO.MODULO_ID";			
			lista = enocJdbc.query(comando, new aca.MapaMapper(), rolId);
			for(aca.Mapa map : lista){				
				mapa.put(map.getLlave(), (String)map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.rol.spring.RolOpcionDao|mapaOpcionesPorModulo|:"+ex);
		}
		return mapa;
	}

}	