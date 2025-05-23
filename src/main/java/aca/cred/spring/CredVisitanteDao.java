package aca.cred.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class CredVisitanteDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
	public boolean insertReg( CredVisitante visitante ){

		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.CRED_VISITANTE(CODIGO_PERSONAL, NOMBRE, PATERNO, MATERNO, RFID, RFID_TAG, ESTADO, FECHA, COMENTARIO) " +
				"VALUES(?, ?, ?, ?, ?, ?, ?, TO_DATE(?,'DD/MM/YYYY'),?) ";
			Object[] parametros = new Object[] {
				visitante.getCodigoPersonal(),visitante.getNombre(),visitante.getPaterno(),visitante.getMaterno(),visitante.getRfid(),visitante.getRfidTag(),
				visitante.getEstado(),visitante.getFecha(),visitante.getComentario()
					
			};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
			
		}catch(Exception ex){
			System.out.println("Error - aca.cred.spring.credVisitante|insertReg|:"+ex);			
		}
		
		return ok;
	}	
	
	public boolean updateReg( CredVisitante visitante ){
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.CRED_VISITANTE " + 
				"SET NOMBRE = ?, PATERNO = ?, MATERNO = ?, RFID = ?, RFID_TAG = ?, ESTADO = ?, FECHA = ?, COMENTARIO = ? " +
				"WHERE CODIGO_PERSONAL = ? ";
			Object[] parametros = new Object[] {
					visitante.getNombre(),visitante.getPaterno(),visitante.getMaterno(),visitante.getRfid(),visitante.getRfidTag(),
					visitante.getEstado(),visitante.getFecha(),visitante.getComentario(),visitante.getCodigoPersonal()
			};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}	
			
		}catch(Exception ex){
			System.out.println("Error - aca.cred.spring.credVisitante|updateReg|:"+ex);		
		}
		
		return ok;
	}	
	
	public boolean deleteReg( String codigoPersonal ){
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.CRED_VISITANTE "+ 
				"WHERE CODIGO_PERSONAL = ? ";
			Object[] parametros = new Object[] {codigoPersonal};
			if (enocJdbc.update(comando, parametros)==1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.cred.spring.credVisitante|deleteReg|:"+ex);			
		}
		
		return ok;
	}
	
	public CredVisitante mapeaRegId( String codigoPersonal){		
		
		CredVisitante evento = new CredVisitante();		
		
		try{
			String comando = "SELECT CODIGO_PERSONAL, NOMBRE, PATERNO, MATERNO, RFID, RFID_TAG, ESTADO, FECHA, COMENTARIO FROM ENOC.CRED_VISITANTE " +					
					"WHERE CODIGO_PERSONAL = ? ";		
			Object[] parametros = new Object[] {codigoPersonal};
			evento = enocJdbc.queryForObject(comando, new CredVisitanteMapper(), parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.cred.spring.credVisitante|mapeaRegId|:"+ex);
		}
		
		return evento;
	}
	
	public boolean existeReg( String codigoPersonal){
		boolean 		ok 	= false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.CRED_VISITANTE WHERE CODIGO_PERSONAL = ? "; 
			Object[] parametros = new Object[] {codigoPersonal};
			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
				ok = true;
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.cred.spring.credVisitante|existeReg|:"+ex);
		}
		
		return ok;
	}
	
	public String maximoReg( String codigoPersonal){

		String maximo			= "1";
		
		try{
			String comando = "SELECT TO_CHAR((MAX(TO_NUMBER(CODIGO_PERSONAL,'9999999')+1)) AS MAXIMO FROM ENOC.CRED_VISITANTE WHERE CODIGO_PERSONAL = ? ";
			Object[] parametros = new Object[] {codigoPersonal};
 			if (enocJdbc.queryForObject(comando,Integer.class,parametros)>=1){
 				maximo = enocJdbc.queryForObject(comando,String.class,parametros);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.cred.spring.credVisitante|maximoReg|:"+ex);
		}
		
		return maximo;
	}	
	
	public String generaCodigoVisitante(String year){
		String codigo	= "";
		int numero		= 0;
		
		year = year.substring(8);
		
		try{
			String comando = "SELECT COALESCE(MAX (CODIGO_PERSONAL), '0') AS CODIGO FROM CRED_VISITANTE WHERE CODIGO_PERSONAL LIKE '%V"+year+"%'";
			
			codigo = enocJdbc.queryForObject(comando,String.class);
			
			if(codigo.equals("0")) {
				codigo = "0001";
			}else {
				codigo 		= codigo.substring(3);
				numero 		= Integer.valueOf(codigo) + 1;
				codigo 		= String.valueOf(numero);
				
				while(codigo.length() < 4){
					codigo = "0"+codigo;
				}
			}
			
			codigo = "V"+year+codigo;
			
		}catch(Exception ex){
			System.out.println("Error - aca.cred.spring.credVisitante|generaCodigoVisitante|:"+ex);
		}
		
		return codigo;
	}
	
	public List<CredVisitante> getListAll( String orden){
		
		List<CredVisitante> lista		= new ArrayList<CredVisitante>();

		try{
			String comando = "SELECT CODIGO_PERSONAL, NOMBRE, PATERNO, MATERNO, RFID, RFID_TAG, ESTADO, FECHA, COMENTARIO" +
					" FROM ENOC.CRED_VISITANTE "+orden;	 
			lista = enocJdbc.query(comando, new CredVisitanteMapper());
			
		}catch(Exception ex){
			System.out.println("Error - aca.cred.spring.credVisitante|getListAll|:"+ex);
		}
		
		return lista;
	}
	
	public List<CredVisitante> getListVisitante( String codigoPersonal){
		
		List<CredVisitante> lista		= new ArrayList<CredVisitante>();
		
		try{
			String comando = "SELECT CODIGO_PERSONAL, NOMBRE, PATERNO, MATERNO, RFID, RFID_TAG, ESTADO, FECHA, COMENTARIO" +
					" FROM ENOC.CRED_VISITANTE WHERE CODIGO_PERSONAL = "+codigoPersonal;	
			lista = enocJdbc.query(comando, new CredVisitanteMapper());
			
		}catch(Exception ex){
			System.out.println("Error - aca.cred.spring.credVisitante|getListAll|:"+ex);
		}
		
		return lista;
	}
	
	public HashMap<String,CredVisitante> getMapAll( String orden ){
		
		HashMap<String,CredVisitante> mapa = new HashMap<String,CredVisitante>();
		List<CredVisitante> lista		= new ArrayList<CredVisitante>();
		
		try{
			String comando = "SELECT CODIGO_PERSONAL, NOMBRE, PATERNO, MATERNO, RFID, RFID_TAG, ESTADO, FECHA, COMENTARIO"
					+ " FROM ENOC.CRED_VISITANTE "+ orden;
			lista = enocJdbc.query(comando, new CredVisitanteMapper());
			for(CredVisitante visitante  : lista){				
				mapa.put(visitante.getCodigoPersonal(), visitante);					
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.cred.spring.credVisitante|getMapAll|:"+ex);
		}
		
		return mapa;
	}

}
