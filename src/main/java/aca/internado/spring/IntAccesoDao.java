package aca.internado.spring;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class IntAccesoDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;	
	
	public boolean insertReg( IntAcceso acceso ) {
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.INT_ACCESO(CODIGO_PERSONAL,DORMITORIO_ID, ROL, PASILLO) VALUES(?,?,?,?)";
			Object[] parametros = new Object[] { acceso.getCodigoPersonal(),	acceso.getDormitorioId(),acceso.getRol(),acceso.getPasillo() };
		 	if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.internado.spring.IntAccesoDao|insertReg|:"+ex);
		}		
		return ok;
	}
	
	public boolean updateReg( IntAcceso acceso ) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.INT_ACCESO SET ROL = ?, PASILLO = ? WHERE DORMITORIO_ID = ? AND CODIGO_PERSONAL = ?";	
			Object[] parametros = new Object[] { acceso.getRol(),acceso.getPasillo(), acceso.getDormitorioId(), acceso.getCodigoPersonal() };
		 	if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.internado.spring.IntAccesoDao|updateReg|:"+ex);
		}
		
		return ok;
	}
	
	public boolean deleteReg( String dormitorioId, String codigoPersonal, String rol ) {
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.INT_ACCESO WHERE DORMITORIO_ID = ? AND CODIGO_PERSONAL = ? AND ROL = ?";
			Object[] parametros = new Object[] { dormitorioId, codigoPersonal, rol };
		 	if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.internado.spring.IntAccesoDao|deleteReg|:"+ex);
		}
		
		return ok;
	}
	
	public IntAcceso mapeaRegId(String dormitorioId, String codigoPersonal, String rol) {
		IntAcceso acceso = new IntAcceso();
		
		try{ 
			String comando = "SELECT * FROM ENOC.INT_ACCESO WHERE DORMITORIO_ID = ? AND CODIGO_PERSONAL = ? AND ROL = ?";
			Object[] parametros = new Object[] { dormitorioId, codigoPersonal, rol };
			acceso = enocJdbc.queryForObject(comando, new IntAccesoMapper(),parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.internado.spring.IntAccesoDao|mapeaRegId|:"+ex);
		}
		
		return acceso;
	}
	
	public IntAcceso mapeaRegId(String codigoPersonal, String rol) {
		IntAcceso acceso = new IntAcceso();
		
		try{ 
			String comando = "SELECT * FROM ENOC.INT_ACCESO WHERE CODIGO_PERSONAL = ? AND ROL = ?";
			Object[] parametros = new Object[] { codigoPersonal, rol };
			acceso = enocJdbc.queryForObject(comando, new IntAccesoMapper(),parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.internado.spring.IntAccesoDao|mapeaRegId|:"+ex);
		}
		
		return acceso;
	}
	
	public IntAcceso mapeaRegId(String codigoPersonal) {
		IntAcceso acceso = new IntAcceso();
		
		try{ 
			String comando = "SELECT * FROM ENOC.INT_ACCESO WHERE CODIGO_PERSONAL = ?";
			Object[] parametros = new Object[] { codigoPersonal};
			acceso = enocJdbc.queryForObject(comando, new IntAccesoMapper(),parametros);			
		}catch(Exception ex){
			System.out.println("Error - aca.internado.spring.IntAccesoDao|mapeaRegId|:"+ex);
		}
		
		return acceso;
	}
	
	public boolean existeRegId(String codigoPersonal, String rol) {
		boolean ok = false;		
		try{ 
			String comando = "SELECT COUNT(*) FROM ENOC.INT_ACCESO WHERE CODIGO_PERSONAL = ? AND ROL = ?";
			Object[] parametros = new Object[] { codigoPersonal, rol };
			if(enocJdbc.queryForObject(comando, Integer.class ,parametros) >= 1) {
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.internado.spring.IntAccesoDao|existeRegId|:"+ex);
		}
		
		return ok;
	}

	public boolean existeRegId(String codigoPersonal, String rol, String dormitorioId) {
		boolean ok = false;		
		try{ 
			String comando = "SELECT COUNT(*) FROM ENOC.INT_ACCESO WHERE CODIGO_PERSONAL = ? AND ROL = ? AND DORMITORIO_ID = ?";
			Object[] parametros = new Object[] { codigoPersonal, rol, dormitorioId };
			if(enocJdbc.queryForObject(comando, Integer.class ,parametros) >= 1) {
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.internado.spring.IntAccesoDao|existeRegId|:"+ex);
		}
		
		return ok;
	}
	
	public int tieneAcceso( String codigoPersonal)  {		
		String comando	= "";
		int numero 		= 0;		
		try{
			comando = "SELECT COUNT(*) FROM ENOC.INT_ACCESO WHERE CODIGO_PERSONAL = ?";
			if (enocJdbc.queryForObject(comando,Integer.class, codigoPersonal) >= 1){			
				comando = "SELECT DORMITORIO_ID FROM ENOC.INT_ACCESO WHERE CODIGO_PERSONAL = ?";
				numero = enocJdbc.queryForObject(comando,Integer.class, codigoPersonal);
			}else{				
				comando = "SELECT COUNT(*) FROM ENOC.INT_DORMITORIO WHERE PRECEPTOR = ?";
				if (enocJdbc.queryForObject(comando,Integer.class, codigoPersonal) >= 1){			
					comando = "SELECT DORMITORIO_ID FROM ENOC.INT_DORMITORIO WHERE PRECEPTOR = ?";
					numero = enocJdbc.queryForObject(comando, Integer.class, codigoPersonal);			
				}
			}
		}catch(Exception ex){
			System.out.println("Error - aca.internado.spring.IntAccesoDao|tieneAcceso|:"+ex);
		}
		
		return numero;		
	}
	
	public String getDormitorioId( String codigoPersonal)  {
		
		String dormitorio 		= "0";		
		try{
			String comando	= "SELECT COUNT(*) FROM ENOC.INT_ACCESO WHERE CODIGO_PERSONAL = ?";
			if (enocJdbc.queryForObject(comando,Integer.class, codigoPersonal)>=1){			
				comando	= "SELECT DORMITORIO_ID FROM ENOC.INT_ACCESO WHERE CODIGO_PERSONAL = ?";
				dormitorio = enocJdbc.queryForObject(comando,String.class, codigoPersonal);
			}						
		}catch(Exception ex){
			System.out.println("Error - aca.internado.spring.IntAccesoDao|getDormitorioId|:"+ex);
		}
		
		return dormitorio;		
	}
	
	public String getRolUsuairo( String codigoPersonal)  {
		
		String rol 		= "0";		
		try{
			String comando	= "SELECT COUNT(*) FROM ENOC.INT_ACCESO WHERE CODIGO_PERSONAL = ?";
			if (enocJdbc.queryForObject(comando,Integer.class, codigoPersonal)>=1){			
				comando	= "SELECT ROL FROM ENOC.INT_ACCESO WHERE CODIGO_PERSONAL = ?";
				rol = enocJdbc.queryForObject(comando, String.class, codigoPersonal);
			}						
		}catch(Exception ex){
			System.out.println("Error - aca.internado.spring.IntAccesoDao|getRolUsuario|:"+ex);
		}
		
		return rol;		
	}
	
	public boolean esPreceptor( String codigoPersonal)  {
		
		String comando		= "";
		boolean ok = false;
		
		try{
			comando = "SELECT COUNT(*) FROM ENOC.INT_ACCESO WHERE CODIGO_PERSONAL = ? AND ROL = 'P'";
			if (enocJdbc.queryForObject(comando,Integer.class, codigoPersonal)>=1){
				ok=true;
			}else{
				comando = "SELECT COUNT(*) FROM ENOC.INT_DORMITORIO WHERE PRECEPTOR = ?"; 
				if (enocJdbc.queryForObject(comando,Integer.class, codigoPersonal)>=1){
					ok=true;
				}				
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.internado.spring.IntAccesoDao|esPreceptor|:"+ex);
		}
		
		return ok;		
	}
	
	public boolean esMonitor( String codigoPersonal){
		
		String comando		= "";
		boolean ok = false;
		
		try{
			comando = "SELECT COUNT(*) FROM ENOC.INT_ACCESO WHERE CODIGO_PERSONAL = ? AND ROL = 'M'"; 
			if (enocJdbc.queryForObject(comando,Integer.class, codigoPersonal)>=1){
				ok=true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.internado.spring.IntAccesoDao|esMonitor|:"+ex);
		}
		
		return ok;		
	}

	public boolean esAsistente( String codigoPersonal){
		
		String comando		= "";
		boolean ok = false;
		
		try{
			comando = "SELECT COUNT(*) FROM ENOC.INT_ACCESO WHERE CODIGO_PERSONAL = ? AND ROL = 'A'"; 
			if (enocJdbc.queryForObject(comando,Integer.class, codigoPersonal)>=1){
				ok=true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.internado.spring.IntAccesoDao|esAsistente|:"+ex);
		}
		
		return ok;		
	}
	
	public List<IntAcceso> getPersonal(String dormitorioId, String orden) {
		List<IntAcceso> lista = new ArrayList<IntAcceso>();
		try{
			String comando = "SELECT * FROM ENOC.INT_ACCESO WHERE DORMITORIO_ID = ? "+orden;
			lista = enocJdbc.query(comando, new IntAccesoMapper(), dormitorioId);
		}catch(Exception ex){
			System.out.println("Error - aca.internado.spring.IntAccesoDao|getPersonal|:"+ex);
		}
		return lista;
	}
	
	public String getCodigos( ) {
		String codigos = "'X'";
		List<String> lista = new ArrayList<String>();
		try{
			String comando = "SELECT DISTINCT(CODIGO_PERSONAL) FROM ENOC.INT_ACCESO";
			lista = enocJdbc.queryForList(comando, String.class);
			int row=0;
			for(String codigo : lista) {
				row++;
				if (row==1) codigos = "'"+codigo+"'"; else codigos += ",'"+codigo+"'"; 
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.internado.spring.IntAccesoDao|getCodigos|:"+ex);
		}
		return codigos;
	}
	

}