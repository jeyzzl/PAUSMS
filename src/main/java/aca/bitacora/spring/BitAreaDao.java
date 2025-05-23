package aca.bitacora.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class BitAreaDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( BitArea area) {
		boolean ok = false;		
		try{
			String comando = "INSERT INTO ENOC.BIT_AREA"+ 
				"(AREA_ID, AREA_NOMBRE, RESPONSABLE)"+
				" VALUES(TO_NUMBER(?, '999'), ?, ?)";
			Object[] parametros = new Object[] {area.getAreaId(),
			area.getAreaNombre(), area.getResponsable()};			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.bitacora.spring.BitAreaDao|insertReg|:"+ex);	
		}
		return ok;
	}	
	
	public boolean updateReg( BitArea area) {
		boolean ok = false;		
		try{
			String comando = "UPDATE ENOC.BIT_AREA"
					+ " SET AREA_NOMBRE = ?,"
					+ " RESPONSABLE = ?,"
					+ " ACCESO = ?" +			
				" WHERE AREA_ID = TO_NUMBER(?, '999')";
			Object[] parametros = new Object[] {area.getAreaNombre(),
			area.getResponsable(), area.getAcceso(), area.getAreaId()};
			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.bitacora.spring.BitAreaDao|updateReg|:"+ex);		
		}
		return ok;
	}
	
	public boolean deleteReg( String areaId) {
		boolean ok = false;		
		try{
			String comando = "DELETE FROM ENOC.BIT_AREA"+ 
				" WHERE AREA_ID = TO_NUMBER(?, '999')";
			Object[] parametros = new Object[] {areaId};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.bitacora.spring.BitAreaDao|deleteReg|:"+ex);			
		}
		return ok;
	}
	
	public boolean existeReg(String areaId) {
		boolean ok = false;		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.BIT_AREA WHERE AREA_ID = TO_NUMBER(?, '999')";
			Object[] parametros = new Object[] {areaId};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.bitacora.spring.BitAreaDao|existeReg|:"+ex);
			ex.printStackTrace();
		}
		return ok;
	}
	
	public BitArea mapeaRegId( String areaId) {
		
		BitArea objeto = new BitArea();		
		try{
			String comando = " SELECT AREA_ID, AREA_NOMBRE, RESPONSABLE, ACCESO "
						+ " FROM ENOC.BIT_AREA WHERE AREA_ID = "+areaId;
			objeto = enocJdbc.queryForObject(comando, new BitAreaMapper());			
		}catch(Exception ex){
			System.out.println("Error - aca.bitacora.spring.BitAreaDao|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}
		return objeto;
	}
	
	public String getAreaNombre(  String areaId) {
		
		String nombre = "-";		
		try{
			String comando = " SELECT AREA_NOMBRE FROM ENOC.BIT_AREA WHERE AREA_ID = TO_NUMBER(?,'99')";			
			Object[] parametros = new Object[] {areaId};
			nombre = enocJdbc.queryForObject(comando,String.class,parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.bitacora.spring.BitAreaDao|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}
		return nombre;
	}
	
	public List<BitArea> lisAreas( String orden) {		
		List<BitArea> lista = new ArrayList<BitArea>();		
		try{
			String comando = " SELECT AREA_ID, AREA_NOMBRE, RESPONSABLE, ACCESO "
					+ " FROM BIT_AREA "+orden;
			lista = enocJdbc.query(comando, new BitAreaMapper());			
		}catch(Exception ex){
			System.out.println("Error - aca.bitacora.spring.BitAreaDao|getTramiteList|:"+ex);
		}
		return lista;
	}	
	
	public List<BitArea> lisAreasUsuario( String codigoPersonal, String orden) {
		
		List<BitArea> lista = new ArrayList<BitArea>();		
		try{
			String comando = " SELECT AREA_ID, AREA_NOMBRE, RESPONSABLE, ACCESO FROM ENOC.BIT_AREA"
					+ " WHERE AREA_ID IN (SELECT AREA_ID FROM ENOC.BIT_AREA_USUARIO WHERE CODIGO_PERSONAL = ?) "+orden;
			Object[] parametros = new Object[] {codigoPersonal};
			lista = enocJdbc.query(comando,  new BitAreaMapper(),parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.bitacora.spring.BitAreaDao|getTramiteList|:"+ex);
		}
		return lista;
	}
	
	public HashMap<String, String> mapaAreas( ) {
		
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista 		 = new ArrayList<aca.Mapa>();
		
		try{
			String comando = " SELECT AREA_ID AS LLAVE, AREA_NOMBRE AS VALOR FROM BIT_AREA ";
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for(aca.Mapa m : lista){
				mapa.put(m.getLlave(), (String)m.getValor());
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.bitacora.spring.BitAreaDao|mapaAreas|:"+ex);
		}
		return mapa;
	}
	
	public String maximoReg() {
		String maximo = "1";
		
		try{
			String comando =  "SELECT COALESCE(MAX(AREA_ID)+1,1) AS MAXIMO FROM ENOC.BIT_AREA";
 			if (enocJdbc.queryForObject(comando, Integer.class) >= 1) {
 				maximo = enocJdbc.queryForObject(comando, String.class);
			}
 			
		}catch(Exception ex){
			System.out.println("Error -  aca.bitacora.spring.BitAreaDao|maximoReg|:"+ex);
		}
		return maximo;
	}
	
}
