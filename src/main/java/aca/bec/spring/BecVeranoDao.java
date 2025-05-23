package aca.bec.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class BecVeranoDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;

	public boolean insertReg( BecVerano verano) {
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.BEC_VERANO"+ 
					"(CODIGO_PERSONAL, DEPARTAMENTO, PUESTO, TELEFONO, CORREO, IMPORTE)" +
					" VALUES( ?, ?, ?, ?, ?, TO_NUMBER(?,'999999.99') )";
					
			Object[] parametros = new Object[] {verano.getCodigoPersonal(), verano.getDepartamento(),
					verano.getPuesto(), verano.getTelefono(), verano.getCorreo(), verano.getImporte()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecVeranoDao|insertReg|:"+ex);
		}
		return ok;
	}
	
	public boolean updateReg( BecVerano verano) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.BEC_VERANO "
					+ " SET DEPARTAMENTO = ?, "
					+ " PUESTO = ?, "
					+ " TELEFONO = ?, "
					+ " CORREO = ?, "
					+ " IMOPRTE = TO_NUMBER(?,'999999.99')"
					+ " WHERE CODIGO_PERSONAL = ? ";
			
			Object[] parametros = new Object[] {verano.getDepartamento(), verano.getPuesto(), verano.getTelefono(),
					verano.getCorreo(), verano.getImporte(), verano.getCodigoPersonal()};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecVeranoDao|updateReg|:"+ex);
		}
		return ok;
	}
	
	public boolean deleteReg( String codigoPersonal) {
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.BEC_VERANO"+ 
				" WHERE CODIGO_PERSONAL = ?";
			
			Object[] parametros = new Object[] {codigoPersonal};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecVeranoDao|deleteReg|:"+ex);			
		}
		return ok;
	}
	
	public boolean existeReg( String codigoPersonal) {
		boolean ok 			= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.BEC_VERANO WHERE CODIGO_PERSONAL = ? "; 
				
			Object[] parametros = new Object[] {codigoPersonal};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecVeranoDao|existeReg|:"+ex);
		}
		return ok;
	}
	
	public BecVerano mapeaRegId( String codigoPersonal) {
		BecVerano verano = new BecVerano();
		
		try{
			String comando = "SELECT * "
					+ " FROM ENOC.BEC_VERANO WHERE CODIGO_PERSONAL = ? "; 

			Object[] parametros = new Object[] {codigoPersonal};
			verano = enocJdbc.queryForObject(comando, new BecVeranoMapper(),parametros);
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecVeranoDao|mapeaRegId|:"+ex);
		}
		return verano;
	}
	
	public List<BecVerano> getListAll( String orden) {
		List<BecVerano> lista 	= new ArrayList<BecVerano>();
		String comando			= "";
		
		try{
			comando = "SELECT CODIGO_PERSONAL, DEPARTAMENTO, PUESTO, TELEFONO, CORREO, IMPORTE FROM ENOC.BEC_VERANO "+orden;
			
			lista = enocJdbc.query(comando, new BecVeranoMapper());
			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecVeranoDao|getListAll|:"+ex);
		}
		return lista;
	}
	
	public HashMap <String,BecVerano> getMapAll( String orden ) {
		List<BecVerano> lista	= new ArrayList<BecVerano>();
		HashMap<String,BecVerano> map = new HashMap<String,BecVerano>();
		
		try{
			String comando = "SELECT * FROM ENOC.BEC_VERANO "+ orden;

			lista = enocJdbc.query(comando,new BecVeranoMapper());
			for(BecVerano obj : lista){				
				map.put(obj.getCodigoPersonal(), obj);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.bec.spring.BecaVeranoDao|getMapAll|:"+ex);
		}
		return map;
	}
	
}
