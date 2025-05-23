// Clase Util para la tabla de Mapa_Plan
package aca.por.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class PorRegistroDao{
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( PorRegistro registro ) {
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.POR_REGISTRO(CODIGO_PERSONAL, EQUIPO_ID, ESTADO )"
					+ " VALUES( ?, TO_NUMBER(?,'99'),?)";

			Object[] parametros = new Object[] {registro.getCodigoPersonal(),registro.getEquipoId(), registro.getEstado()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.PorRegistroDao|insertReg|:"+ex);
		}
		return ok;
	}	
	
	public boolean updateReg( PorRegistro registro ) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.POR_REGISTRO"
					+ " SET EQUIPO_ID = TO_NUMBER(?,'99'),"
					+ " ESTADO = ?"
					+ " WHERE CODIGO_PERSONAL = ?";
			
			Object[] parametros = new Object[] {registro.getEquipoId(), registro.getEstado(), registro.getCodigoPersonal()};				
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.PorRegistroDao|updateReg|:"+ex);		
		}
		return ok;
	}
	
	public boolean deleteReg( String codigoPersonal ) {
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.POR_REGISTRO WHERE CODIGO_PERSONAL = ?";
			
			Object[] parametros = new Object[] {codigoPersonal};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.PorRegistroDao|deleteReg|:"+ex);
		}
		return ok;
	}
	
	public PorRegistro mapeaRegId( String codigoPersonal) {
		PorRegistro registro = new PorRegistro();
		try{
			String comando = "SELECT CODIGO_PERSONAL, EQUIPO_ID, ESTADO FROM ENOC.POR_REGISTRO WHERE CODIGO_PERSONAL = ?";
			
				Object[] parametros = new Object[] {codigoPersonal};
				registro = enocJdbc.queryForObject(comando,  new PorRegistroMapper(), parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.PorRegistroDao|mapeaRegId|:"+ex);
		}
		
		return registro;		
	}	
	
	public boolean existeReg( String codigoPersonal) {
		boolean 		ok 	= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.POR_REGISTRO WHERE CODIGO_PERSONAL = ?"; 
			
			Object[] parametros = new Object[] {codigoPersonal};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros)>=1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.PorRegistroDao|existeReg|:"+ex);
		}
		return ok;
	}
	
	public String getEquipo( String codigoPersonal) {
		String equipo 	= "0";
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.POR_REGISTRO WHERE CODIGO_PERSONAL = ?";			
			Object[] parametros = new Object[] {codigoPersonal};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros)>=1){
				comando = "SELECT EQUIPO_ID FROM ENOC.POR_REGISTRO WHERE CODIGO_PERSONAL = ?";
				equipo = enocJdbc.queryForObject(comando,String.class, parametros);				
			}
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.PorRegistroDao|existeReg|:"+ex);
		}
		return equipo;
	}
	
	public String getEstado( String codigoPersonal) {
		String estado 	= "A";
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.POR_REGISTRO WHERE CODIGO_PERSONAL = ?"; 
			
			Object[] parametros = new Object[] {codigoPersonal};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros)>=1){
				comando = "SELECT ESTADO FROM ENOC.POR_REGISTRO WHERE CODIGO_PERSONAL = ?";
				estado = enocJdbc.queryForObject(comando,String.class, parametros);				
			}
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.PorRegistroDao|existeReg|:"+ex);
		}
		return estado;
	}
	
	public List<PorRegistro> getListAll( String orden ) {
		List<PorRegistro> lista	= new ArrayList<PorRegistro>();
		
		try{
			String comando = "SELECT CODIGO_PERSONAL, EQUIPO_ID, ESTADO FROM ENOC.POR_REGISTRO "+orden; 
			
			lista = enocJdbc.query(comando, new PorRegistroMapper());
			
		}catch(Exception ex){
			System.out.println("Error - aca.plan.spring.PorRegistroDao|getListAll|:"+ex);
		}		
		return lista;
	}
	
	public HashMap<String,String> mapaEstados( ) {	
		
		HashMap<String,String> mapa = new HashMap<String,String>();
		List<aca.Mapa> lista		= new ArrayList<aca.Mapa>();
		
		try{
			String comando = "SELECT CODIGO_PERSONAL AS LLAVE, ESTADO AS VALOR FROM POR_REGISTRO";
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for (aca.Mapa map : lista ) {
				mapa.put(map.getLlave(), map.getValor());
			}	
			
		}catch(Exception ex){
			System.out.println("Error - aca.por.spring.PorRegistroDao|mapaEstados|:"+ex);
			ex.printStackTrace();
		}
		
		return mapa;		
	}
	
}