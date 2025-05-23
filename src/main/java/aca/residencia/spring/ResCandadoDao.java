package aca.residencia.spring;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class ResCandadoDao {

	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( ResCandado res ){
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.RES_CANDADO(CODIGO_PERSONAL, COMENTARIO, USUARIO, " + 
					" FECHA, ESTADO) " +
					" VALUES(?,?,?,TO_DATE(?,'DD/MM/YYYY'),?)";
			Object[] parametros = new Object[] {res.getCodigoPersonal(),res.getComentario(),res.getUsuario(),res.getFecha(),res.getEstado()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
			
		}catch(Exception ex){
			System.out.println("Error - aca.residencia.ResCandado|insertReg|:"+ex);
		}
		
		return ok;
	}
	
	public boolean updateReg( ResCandado res ){
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.RES_CANDADO SET COMENTARIO = ? , " +
					" USUARIO =  ?, " +
					" FECHA = TO_DATE(?,'DD/MM/YYYY'), " +
					" ESTADO = ? " + 
					" WHERE CODIGO_PERSONAL = ?";	
			Object[] parametros = new Object[] {res.getComentario(),res.getUsuario(),res.getFecha(),res.getEstado(),res.getCodigoPersonal()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.residencia.ResCandado|updateReg|:"+ex);
		}
		
		return ok;
	}
	
	public boolean deleteReg( String codigoPersonal ){
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.RES_CANDADO WHERE CODIGO_PERSONAL= ?"; 
			Object[] parametros = new Object[] {codigoPersonal};
			if (enocJdbc.update(comando, parametros)==1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.residencia.ResCandado|deletetReg|:"+ex);
		}
		
		return ok;
	}
	
	public boolean existeReg( String codigoPersonal ){
		boolean ok 			= false;
		
		try{
			String comando = "SELECT COUNT (*) FROM ENOC.RES_CANDADO WHERE CODIGO_PERSONAL = ? "; 
			Object[] parametros = new Object[] {codigoPersonal};
			if (enocJdbc.update(comando, parametros)==1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.residencia.ResCandado|existeReg|:"+ex);
		}
		
		return ok;
	}
	
	public List<ResCandado> getListAll(String orden){
		
	List<ResCandado> lista = new ArrayList<ResCandado>();
	
	try{
		String comando = "SELECT CODIGO_PERSONAL, COMENTARIO, USUARIO, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, ESTADO FROM ENOC.RES_CANDADO "+ orden; 
		lista = enocJdbc.query(comando, new ResCandadoMapper());
		
	}catch(Exception ex){
		System.out.println("Error - aca.residencia.CandadoUtil|getListAll|:"+ex);
	}
	
	return lista;
	}
}
