package aca.bitacora.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class BitAreaUsuarioDao{
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(BitAreaUsuario areaUsuario) {
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.BIT_AREA_USUARIO(AREA_ID, CODIGO_PERSONAL)"
				+ " VALUES(TO_NUMBER(?, '999'), ?)";
			Object[] parametros = new Object[] {areaUsuario.getAreaId(), areaUsuario.getCodigoPersonal()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.bitacora.spring.BitAreaUsuarioDao|insertReg|:"+ex);	
		}
		return ok;
	}
	
	public boolean deleteReg(String areaId, String codigoPersonal) {
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.BIT_AREA_USUARIO"
				+ " WHERE AREA_ID = TO_NUMBER(?, '999')"
				+ " AND CODIGO_PERSONAL = ?";
			Object[] parametros = new Object[] {areaId, codigoPersonal};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.bitacora.spring.BitAreaUsuarioDao|deleteReg|:"+ex);			
		}
		return ok;
	}
	
	public boolean existeReg(String areaId, String codigoPersonal) {
		boolean ok = false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.BIT_AREA_USUARIO"
				+ " WHERE AREA_ID = TO_NUMBER(?, '999') AND CODIGO_PERSONAL = ?";
			Object[] parametros = new Object[] {areaId, codigoPersonal};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.bitacora.spring.BitAreaUsuarioDao|existeReg|:"+ex);
			ex.printStackTrace();
		}
		return ok;
	}
	
	public BitAreaUsuario mapeaRegId( String areaId, String codigoPersonal) {
		
		BitAreaUsuario objeto = new BitAreaUsuario();
		
		try{
			String comando = " SELECT AREA_ID, CODIGO_PERSONAL FROM ENOC.BIT_AREA_USUARIO"
				+ " WHERE AREA_ID = TO_NUMBER(?, '999') AND CODIGO_PERSONAL = ?";
			Object[] parametros = new Object[] {areaId, codigoPersonal};
			objeto = enocJdbc.queryForObject(comando, new BitAreaUsuarioMapper(),parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.bitacora.spring.BitAreaUsuarioDao|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}
		return objeto;
	}
	
	public List<BitAreaUsuario> lisAreasUsuario(String areaId, String orden) {
		
		List<BitAreaUsuario> lista = new ArrayList<BitAreaUsuario>();
		
		try{
			String comando = " SELECT AREA_ID, CODIGO_PERSONAL"
					+ " FROM ENOC.BIT_AREA_USUARIO"
					+ " WHERE AREA_ID = TO_NUMBER(?, '999') "+orden;
			Object[] parametros = new Object[] {areaId};
			lista = enocJdbc.query(comando, new BitAreaUsuarioMapper(),parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.bitacora.spring.BitAreaUsuarioDao|lisAreasUsuario|:"+ex);
		}
		return lista;
	}
	
	public List<String> lisAreasUsuarios(String areaId) {
		
		List<String> lista = new ArrayList<String>();
		
		try{
			String comando = "SELECT CODIGO_PERSONAL FROM ENOC.BIT_AREA_USUARIO WHERE AREA_ID = TO_NUMBER(?, '999')";
			Object[] parametros = new Object[] {areaId};
			lista = enocJdbc.queryForList(comando, String.class,parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.bitacora.spring.BitAreaUsuarioDao|lisAreasUsuarios|:"+ex);
		}
		return lista;
	}
	
	public HashMap<String, String> mapaUsuariosPorArea() {
		
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista 		 = new ArrayList<aca.Mapa>();
		
		try{
			String comando = "SELECT AREA_ID AS LLAVE, COUNT(CODIGO_PERSONAL) AS VALOR FROM ENOC.BIT_AREA_USUARIO GROUP BY AREA_ID";
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for(aca.Mapa m : lista){
				mapa.put(m.getLlave(), (String)m.getValor());
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.bitacora.spring.BitAreaUsuarioDao|mapaAreas|:"+ex);
		}
		return mapa;
	}
	
}
