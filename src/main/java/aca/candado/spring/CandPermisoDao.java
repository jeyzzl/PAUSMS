// Clase para la tabla de CandTipo
package aca.candado.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class CandPermisoDao{	
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( CandPermiso cand ) {
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.CAND_PERMISO(TIPO_ID, CODIGO_PERSONAL, US_ALTA, FECHA_ALTA) VALUES(?, ?, ?, TO_DATE(?, 'DD/MM/YYYY'))"; 
			Object[] parametros = new Object[] {cand.getTipoId(), cand.getCodioPersonal(), cand.getUsAlta(), cand.getFechaAlta()};		
			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.candado.spring.CandadoPermisoDao|insertReg|:"+ex);
		}
		return ok;
	}
	
	public boolean updateReg( CandPermiso cand ) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.CAND_PERMISO SET TIPO_ID = ?, CODIGO_PERSONAL = ?, US_ALTA = ? WHERE TIPO_ID = ? AND CODIGO_PERSONAL = ?";	
			Object[] parametros = new Object[] {cand.getTipoId(), cand.getCodioPersonal(), cand.getUsAlta(), cand.getTipoId(), cand.getCodioPersonal()};
			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.candado.spring.CandadoPermisoDao|updateReg|:"+ex); 
		}
		return ok;
	}
	
	public boolean deleteReg( String tipoId, String codigoPersonal ) {
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.CAND_PERMISO WHERE TIPO_ID = ? AND CODIGO_PERSONAL = ?"; 
			Object[] parametros = new Object[] {tipoId, codigoPersonal};
						
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.candado.spring.CandadoPermisoDao|deleteReg|:"+ex);
		}
		return ok;
	}
	
	
	public CandPermiso mapeaRegId( String tipoId, String codigoPersonal) {
		CandPermiso permiso = new CandPermiso();		
		try{
			String comando = "SELECT TIPO_ID, CODIGO_PERSONAL, US_ALTA, FECHA_ALTA FROM ENOC.CAND_PERMISO WHERE TIPO_ID = ? AND CODIGO_PERSONAL = ? ";
			Object[] parametros = new Object[] {tipoId, codigoPersonal};
			permiso = enocJdbc.queryForObject(comando, new CandPermisoMapper(), parametros);		
		}catch(Exception ex){
			System.out.println("Error - aca.candado.spring.CandadoPermisoDao|mapeaRegId|:"+ex);
		}
		return permiso;
	}
	
	public boolean existeReg( String tipoId, String codigoPersonal) {
		boolean ok 			= false;
		
		try{
			String comando ="SELECT COUNT(*) FROM ENOC.CAND_PERMISO WHERE TIPO_ID = ? AND CODIGO_PERSONAL = ? "; 
			Object[] parametros = new Object[] {tipoId, codigoPersonal};	
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
				ok = true;
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.candado.spring.CandadoPermisoDao|existeReg|:"+ex);
		}		
		return ok;
	}
		
	public List<CandPermiso> getListAll( String orden ) {
		
		List<CandPermiso> lista 	= new ArrayList<CandPermiso>();
		String comando	= "";
		
		try{
			comando = "SELECT TIPO_ID, CODIGO_PERSONAL, US_ALTA, FECHA_ALTA FROM ENOC.CAND_PERMISO "+orden; 
			
			Object[] parametros = new Object[] {orden};	
			lista = enocJdbc.query(comando, new CandPermisoMapper(),parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.candado.spring.CandadoPermisoDao|getListAll|:"+ex);
		}
		return lista;
	}
	
	public List<CandPermiso> getListaPorTipo( String tipoId, String orden ) {
		
		List<CandPermiso> lista 	= new ArrayList<CandPermiso>();
		
		try{
			String comando = "SELECT TIPO_ID, CODIGO_PERSONAL, US_ALTA, TO_CHAR(FECHA_ALTA, 'DD/MM/YYYY') AS FECHA_ALTA FROM ENOC.CAND_PERMISO WHERE TIPO_ID = ? "+orden;
			lista = enocJdbc.query(comando, new CandPermisoMapper(), tipoId);	
		}catch(Exception ex){
			System.out.println("Error - aca.candado.spring.CandadoPermisoDao|getListaPorTipo|:"+ex);
		}
		return lista;
	}
	
	public List<CandPermiso> getListaPorUsuario( String codigoPersonal, String orden ) {
		
		List<CandPermiso> lista 	= new ArrayList<CandPermiso>();
		try{
			String comando = "SELECT TIPO_ID, CODIGO_PERSONAL, US_ALTA, FECHA_ALTA FROM ENOC.CAND_PERMISO WHERE CODIGO_PERSONAL = ? "+orden;
			lista = enocJdbc.query(comando, new CandPermisoMapper(), codigoPersonal);			
		}catch(Exception ex){
			System.out.println("Error - aca.candado.spring.CandadoPermisoDao|getListaPorUsuario|:"+ex);
		}
		return lista;
	}
	
	public HashMap<String,CandPermiso> getMapaCandadoPermisos() {
		
		HashMap<String,CandPermiso> map = new HashMap<String,CandPermiso>();
		List<CandPermiso> lista = new ArrayList<CandPermiso>(); 
		
		try{
			String comando = " SELECT TIPO_ID, CODIGO_PERSONAL, US_ALTA, FECHA_ALTA FROM ENOC.CAND_PERMISO";
			
			lista = enocJdbc.query(comando, new CandPermisoMapper());
			for (CandPermiso cand : lista ) {
				map.put(cand.getTipoId()+cand.getCodioPersonal(), cand);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.candado.spring.CandadoPermisoDao|getMapaCandadoPermisos|:"+ex);
		}		
		return map;
	}
	
	public HashMap<String,String> mapaPermisosPorTipo() {		
		List<aca.Mapa> lista 				= new ArrayList<aca.Mapa>();		
		HashMap<String, String> mapa		= new HashMap<String,String>();		
		try{
			String comando = "SELECT TIPO_ID AS LLAVE, COUNT(*) AS VALOR FROM CAND_PERMISO GROUP BY TIPO_ID";
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for ( aca.Mapa map : lista) {
				mapa.put(map.getLlave(), map.getValor() );
			}
		}catch(Exception ex){
			System.out.println("Error - aca.candado.spring.CandadoPermisoDao|mapaPermisosPorTipo|:"+ex);
		}		
		return mapa;
	}
	
	public boolean esResponsable( String codigoPersonal) {
		boolean ok 			= false;		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.CAND_PERMISO WHERE CODIGO_PERSONAL = ?";	
			Object[] parametros = new Object[] {codigoPersonal};			
			if (enocJdbc.queryForObject(comando,Integer.class,parametros) >= 1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.candado.spring.CandadoPermisoDao|esResponsable|:"+ex);
		}
		return ok;
	}
	
	
}