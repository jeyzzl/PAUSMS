package aca.catalogo.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class CatHorarioAccesoDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( CatHorarioAcceso horario) {
		boolean ok = false;		
		try{
			String comando = "INSERT INTO ENOC.CAT_HORARIO_ACCESO (HORARIO_ID, CODIGO_PERSONAL)"
					+ " VALUES( TO_NUMBER(?,'9999999'), ?)";
			Object[] parametros = new Object[] {horario.getHorarioId(), horario.getCodigoPersonal()};	
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatHorarioAccesoDao|insertReg|:"+ex);			
		}
		
		return ok;
	}
	
	public boolean deleteReg( String horarioId, String codigoPersonal ) {
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.CAT_HORARIO_ACCESO WHERE HORARIO_ID = TO_NUMBER(?,'9999999') AND CODIGO_PERSONAL = ?";
			Object[] parametros = new Object[] {horarioId, codigoPersonal};	
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatHorarioAccesoDao|deleteReg|:"+ex);
		}
		
		return ok;
	}
	
	public boolean existeReg( String horarioId) {
		boolean 		ok 	= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.CAT_HORARIO_ACCESO WHERE HORARIO_ID = TO_NUMBER(?,'9999999')";			
			Object[] parametros = new Object[] {horarioId};
			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1){
				ok = true;
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatHorarioAccesoDao|existeReg|:"+ex);
		}
		
		return ok;
	}
	
	public String maximoReg() {
		int maximo = 1;
		
		try{
			String comando = "SELECT COALESCE(MAX(HORARIO_ID)+1,1) FROM ENOC.CAT_HORARIO_ACCESO";
			maximo = enocJdbc.queryForObject(comando, Integer.class);					
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatHorarioAccesoDao|existeReg|:"+ex);
		}
		
		return String.valueOf(maximo);
	} 
	
	public String getCountUsers(String horarioId) {
		String number = "0";
		
		try {
			String comando = "SELECT COUNT (*) FROM ENOC.CAT_HORARIO_ACCESO WHERE HORARIO_ID = TO_NUMBER(?,'9999999')";
			Object[] parametros = new Object[] {horarioId};
			number = enocJdbc.queryForObject(comando, String.class, parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatHorarioAccesoDao|getCountUsers|:"+ex);
		}
		return number;
	}
	
	public CatHorarioAcceso mapeaRegId(String horarioId) {
		
		CatHorarioAcceso horario 	= new CatHorarioAcceso();
		try{
			String comando = "SELECT * FROM ENOC.CAT_HORARIO_ACCESO WHERE HORARIO_ID = TO_NUMBER(?,'9999999')";
			Object[] parametros = new Object[] {horarioId};
			horario = enocJdbc.queryForObject(comando, new CatHorarioAccesoMapper(), parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatHorarioAccesoDao|mapeaRegId|:"+ex);
		}
		
		return horario;
	}
	
	public List<CatHorarioAcceso> getListAll( String orden ) {
		
		List<CatHorarioAcceso> lista		= new ArrayList<CatHorarioAcceso>();		
		try{
			String comando = "SELECT * FROM ENOC.CAT_HORARIO_ACCESO "+orden;
			lista = enocJdbc.query(comando, new CatHorarioAccesoMapper());
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatHorarioAccesoDao|getListAll|:"+ex);
		}		
		return lista;
	}
	
	public List<CatHorarioAcceso> getListByHorarioId( String horarioId) {
		
		List<CatHorarioAcceso> lista		= new ArrayList<CatHorarioAcceso>();		
		try{
			String comando = "SELECT * FROM ENOC.CAT_HORARIO_ACCESO WHERE HORARIO_ID = TO_NUMBER(?,'9999999')";
			Object[] parametros = new Object[] {horarioId};
			lista = enocJdbc.query(comando, new CatHorarioAccesoMapper(), parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatHorarioAccesoDao|getListByHorarioId|:"+ex);
		}		
		return lista;
	}
	
	public HashMap<String, String> mapUsersByHorarioId(){		
		HashMap<String, String> mapa = new HashMap<String, String>();
		List<aca.Mapa> lista			= new ArrayList<aca.Mapa>();		
		try{			
			String comando = "SELECT HORARIO_ID AS LLAVE, COUNT (*) AS VALOR FROM ENOC.CAT_HORARIO_ACCESO GROUP BY HORARIO_ID";
			lista = enocJdbc.query(comando, new aca.MapaMapper());		
			for(aca.Mapa map :lista){
				mapa.put(map.getLlave(), (String)map.getValor());
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.spring.CatCarreraDao|mapUsersByHorarioId|:"+ex);
		}
		
		return mapa;
	}
}
