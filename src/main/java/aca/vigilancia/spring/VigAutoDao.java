package aca.vigilancia.spring;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class VigAutoDao{
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg(VigAuto objeto){
		boolean ok 				= false;		
		try{
			String comando = "INSERT INTO ENOC.VIG_AUTO (AUTO_ID, PLACAS, ENGOMADO, USUARIO, COMENTARIO, COLOR, MODELO, MARCA, POLIZA, FECHA, ESTADO)"
					+ " VALUES( TO_NUMBER(?, '9999'), ?, ?, ?, ?, ?, ?, ?, ?, TO_DATE(?,'YYYY/MM/DD'), ?)";
			Object[] parametros = new Object[] {
				objeto.getAutoId(),objeto.getPlacas(),objeto.getEngomado(),objeto.getUsuario(),objeto.getComentario(),
				objeto.getColor(),objeto.getModelo(),objeto.getMarca(),objeto.getPoliza(), objeto.getFecha(), objeto.getEstado()			
			};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}				
		}catch(Exception ex){
			System.out.println("Error - aca.vigilancia.spring.VigAutoDao|insertReg|:"+ex);
		}		
		return ok;
	}	
	
	public boolean updateReg(VigAuto objeto){ 		
		boolean ok = false;		
		try{
			String comando = "UPDATE ENOC.VIG_AUTO"
					+ " SET PLACAS = ?, ENGOMADO = ?, USUARIO = ?, COMENTARIO = ?, COLOR = ?, MODELO = ?, MARCA = ?, POLIZA = ?, FECHA = TO_DATE(?,'YYYY/MM/DD'), ESTADO = ?"
					+ " WHERE AUTO_ID = TO_NUMBER(?, '9999')";			
			Object[] parametros = new Object[] {
				objeto.getPlacas(),objeto.getEngomado(),objeto.getUsuario(),objeto.getComentario(),objeto.getColor(),objeto.getModelo(),objeto.getMarca(),objeto.getPoliza(), 
				objeto.getFecha(), objeto.getEstado(), objeto.getAutoId()			
			};				
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.vigilancia.spring.VigAutoDao|updateReg|:"+ex);
		}
		
		return ok;
	}
	 	
	public boolean deleteReg(String autoId){
		boolean ok = false;
		try{
			String comando = "DELETE FROM ENOC.VIG_AUTO WHERE AUTO_ID = TO_NUMBER(?, '9999')";
			if (enocJdbc.update(comando,autoId)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.vigilancia.spring.VigAutoDao|deleteReg|:"+ex);
		}
		return ok;
	}
	
	public VigAuto mapeaRegId(String autoId) {
		VigAuto objeto = new VigAuto();
		try{
	 		String comando = "SELECT AUTO_ID, PLACAS, ENGOMADO, USUARIO, COMENTARIO, COLOR, MODELO, MARCA, POLIZA, TO_CHAR(FECHA,'YYYY/MM/DD') AS FECHA, ESTADO"
	 				+ " FROM ENOC.VIG_AUTO "
	 				+ " WHERE AUTO_ID = TO_NUMBER(?,'9999')";
	 		objeto = enocJdbc.queryForObject(comando, new VigAutoMapper(), autoId);	 		
		}catch(Exception ex){
			System.out.println("Error - aca.vigilancia.spring.VigAutoDao|mapeaRegId|:"+ex);
		}		
		return objeto;
	}
	
	public boolean existeReg(String autoId){
		boolean ok = false;		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.VIG_AUTO WHERE AUTO_ID = ?";
			if (enocJdbc.queryForObject(comando,Integer.class, autoId)>=1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.vigilancia.spring.VigAutoDao|existeReg|:"+ex);
		}		
		return ok;
	}
	
	public int numAutos(String usuario, String estados){
		int total = 0;		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.VIG_AUTO WHERE USUARIO = ? AND ESTADO IN ("+estados+")";
			total = enocJdbc.queryForObject(comando,Integer.class, usuario);					
		}catch(Exception ex){
			System.out.println("Error - aca.vigilancia.spring.VigAutoDao|numAutos|:"+ex);
		}		
		return total;
	}
	
	public String maximoReg(){
		String maximo = "1";		
		try{
			String comando = "SELECT COALESCE(MAX(AUTO_ID)+1,1) FROM ENOC.VIG_AUTO";
			maximo = enocJdbc.queryForObject(comando,String.class);			
		}catch(Exception ex){
			System.out.println("Error - aca.vigilancia.spring.VigAutoDao|maximoReg|:"+ex);
		}		
		return maximo;
	}
	
	public List<VigAuto> lisTodos(String orden ){
		
		List<VigAuto> lis = new ArrayList<VigAuto>();		
		try{
			String comando = "SELECT AUTO_ID, PLACAS, ENGOMADO, USUARIO, COMENTARIO, COLOR, MODELO, MARCA, POLIZA, TO_CHAR(FECHA,'YYYY/MM/DD') AS FECHA, ESTADO FROM ENOC.VIG_AUTO "+orden;
			lis = enocJdbc.query(comando, new VigAutoMapper());
		}catch(Exception ex){
			System.out.println("Error - aca.vigilancia.spring.VigAutoDao|lisTodos|:"+ex);
		}			
		
		return lis;
	}
	
	public List<VigAuto> lisPorUsuario(String usuario, String orden ){		
		List<VigAuto> lis = new ArrayList<VigAuto>();		
		try{
			String comando = "SELECT AUTO_ID, PLACAS, ENGOMADO, USUARIO, COMENTARIO, COLOR, MODELO, MARCA, POLIZA, TO_CHAR(FECHA,'YYYY/MM/DD') AS FECHA, ESTADO FROM ENOC.VIG_AUTO WHERE USUARIO = ? "+orden;
			lis = enocJdbc.query(comando, new VigAutoMapper(), usuario);
		}catch(Exception ex){
			System.out.println("Error - aca.vigilancia.spring.VigAutoDao|lisPorUsuario|:"+ex);
		}			
		
		return lis;
	}
	
	public List<VigAuto> getLista(String placas, String orden ){
		
		List<VigAuto> lisUsuarios	= new ArrayList<VigAuto>();		
		try{
			String comando = "SELECT AUTO_ID, PLACAS, ENGOMADO, USUARIO, COMENTARIO, COLOR, MODELO, MARCA, POLIZA, TO_CHAR(FECHA,'YYYY/MM/DD') AS FECHA, ESTADO" +
				" FROM ENOC.VIG_AUTO WHERE PLACAS LIKE UPPER('%"+placas+"%') "+ 
				" OR ENGOMADO LIKE UPPER('%"+placas+"%') "+
				" OR MARCA LIKE UPPER('%"+placas+"%')" +
				" OR COLOR LIKE UPPER('%"+placas+"%')" +orden;			
			lisUsuarios = enocJdbc.query(comando, new VigAutoMapper());
			
		}catch(Exception ex){
			System.out.println("Error - aca.vigilancia.spring.VigAutoDao|getLista|:"+ex);
		}
		
		return lisUsuarios;
	}

}