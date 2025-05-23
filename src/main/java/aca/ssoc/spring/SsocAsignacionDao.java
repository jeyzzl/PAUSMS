/*
 * Created on 13/02/2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package aca.ssoc.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class SsocAsignacionDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
    public boolean insertReg(SsocAsignacion asig) {
		boolean ok = false;
		
		try{
			String comando = "INSERT INTO ENOC.SSOC_ASIGNACION"+ 
				"(CODIGO_PERSONAL, "+
				"DEPENDENCIA, DIRECCION, "+
				"TELEFONO, RESPONSABLE, F_INICIO, ESTADO, ASIGNACION_ID,SECTOR) "+
				"VALUES(?, ?, ?, ?, ?, TO_DATE(?, 'DD/MM/YYYY'), ?, ?, ?)";
			Object[] parametros = new Object[] {asig.getCodigoPersonal(), asig.getDependencia(),
					asig.getDireccion(), asig.getTelefono(), asig.getResponsable(),
					asig.getFechaInicio(), "A", asig.getAsignacionId(), asig.getSector()};
			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.ssoc.spring.SsocAsignacionDao|insertReg|:"+ex);			
		}
		return ok;
	} 
    
    public boolean updateReg( SsocAsignacion asig) {
		boolean ok = false;
		
		try{
			String comando = "UPDATE ENOC.SSOC_ASIGNACION "+ 
				"SET "+				
				"DEPENDENCIA = ?, "+
				"DIRECCION = ?, "+
				"TELEFONO = ?, "+
				"RESPONSABLE = ?, "+
				"F_INICIO = TO_DATE(?, 'DD/MM/YYYY'), "+
				"SECTOR = ? "+
				"WHERE CODIGO_PERSONAL = ? AND ASIGNACION_ID = TO_NUMBER(?, '9')";
			Object[] parametros = new Object[] { 
				asig.getDependencia(), asig.getDireccion(), asig.getTelefono(), asig.getResponsable(), asig.getFechaInicio(), asig.getSector(), 
				asig.getCodigoPersonal(), asig.getAsignacionId()
			};			
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.ssoc.spring.SsocAsignacionDao|updateReg|:"+ex);		
		}
		return ok;
	}
    
    public boolean deleteReg(String codigoPersonal, String asId ) {
		boolean ok = false;
		
		try{
			String comando = "DELETE FROM ENOC.SSOC_ASIGNACION WHERE CODIGO_PERSONAL = ? AND ASIGNACION_ID = TO_NUMBER(?, '9')";
			Object[] parametros = new Object[] {codigoPersonal, asId};
			if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.ssoc.spring.SsocAsignacionDao|deleteReg|:"+ex);			
		}
		return ok;
	}
    
    public String MaximoReg( String codigoPersonal) {
    	
		String maximo = "1";
		
		try{
			String comando = "SELECT COALESCE(MAX(ASIGNACION_ID)+1,1) AS MAXIMO FROM ENOC.SSOC_ASIGNACION WHERE CODIGO_PERSONAL = ?";
			Object[] parametros = new Object[] {codigoPersonal};
 			if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1) {
 				maximo = enocJdbc.queryForObject(comando, String.class, parametros);
			}
 			
		}catch(Exception ex){
			System.out.println("Error - aca.ssoc.spring.SsocAsignacionDao|maximoReg|:"+ex);
		}
		return maximo;
		
	}
    
    public SsocAsignacion mapeaRegId(  String codigoPersonal, String asignacionId) {
    	SsocAsignacion objeto = new SsocAsignacion();
    	
		try{
			String comando = "SELECT CODIGO_PERSONAL, ASIGNACION_ID, DEPENDENCIA, DIRECCION, "+
				"TELEFONO, RESPONSABLE, TO_CHAR(F_INICIO, 'DD/MM/YYYY') AS F_INICIO, ESTADO, SECTOR FROM ENOC.SSOC_ASIGNACION WHERE CODIGO_PERSONAL = ? AND ASIGNACION_ID = ?";
			Object[] parametros = new Object[] {codigoPersonal, asignacionId};
			objeto = enocJdbc.queryForObject(comando, new SsocAsignacionMapper(), parametros);
			
		}catch(Exception ex){
			System.out.println("Error - aca.ssoc.spring.SsocAsignacionDao|mapeaRegId|:"+ex);
		}
		return objeto;
	}
    
    public boolean existeReg(String codigoPersonal, String asId) {
    	
		boolean ok = false;
		
		try{
			String comando = "SELECT COUNT(*) FROM ENOC.SSOC_ASIGNACION  "+ 
				"WHERE CODIGO_PERSONAL = ? AND ASIGNACION_ID = TO_NUMBER(?, '9') ";
			Object[] parametros = new Object[] {codigoPersonal, asId};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros)>=1){
				ok = true;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.ssoc.spring.SsocAsignacionDao|existeReg|:"+ex);
		}
		
		return ok;
	}
	
    //java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
    
    public boolean guardaAsignacion( SsocAsignacion asig) {
    	boolean grabo=true;
    	try{
    	    String comando="insert into ENOC.ssoc_asignacion values(?," + 
    	    		"(select COALESCE(max(asignacion_id),0)+1 from ENOC.ssoc_asignacion where codigo_personal = ?)" + 
    	    		",?,?,?,?,to_date(?,'dd/mm/yyyy'),?)";
    	    Object[] parametros = new Object[] {asig.getCodigoPersonal(),asig.getCodigoPersonal(), asig.getDependencia(),
					asig.getDireccion(), asig.getTelefono(), asig.getResponsable(),
					asig.getFechaInicio(), "A"};
    	    if (enocJdbc.queryForObject(comando,Integer.class, parametros) >= 1){
    	    	grabo = true;
			}
    	}catch(Exception ex){
    		System.out.println("Error - aca.ssoc.spring.SsocAsignacionDao|guardaAsignacion|:"+ex);
    		grabo=false;
    	}
    	
    	return grabo;
    }
    
    public List<SsocAsignacion> getAsignaciones( String codigoPersonal) {
        List<SsocAsignacion> lista=new ArrayList<SsocAsignacion>();
    	try{
    	    String comando	= " SELECT CODIGO_PERSONAL, DEPENDENCIA, DIRECCION, TELEFONO, RESPONSABLE, TO_CHAR(F_INICIO, 'DD/MM/YYYY') AS F_INICIO, ESTADO, ASIGNACION_ID, SECTOR"
    	    		+ " FROM ENOC.SSOC_ASIGNACION"
    	    		+ " WHERE CODIGO_PERSONAL = ?";
    	    Object[] parametros = new Object[] {codigoPersonal};
    	    lista = enocJdbc.query(comando, new SsocAsignacionMapper(), parametros);    	    
    	}catch(Exception ex){
    		System.out.println("Error - aca.ssoc.spring.SsocAsignacionDao|getAsignaciones|:"+ex);
    	}
    	return lista;
    }

    public SsocAsignacion getAsignacion(String codigoPersonal, int id) {
        SsocAsignacion objeto = new SsocAsignacion();
        String comando = "";
        
    	try{
    	    comando="select CODIGO_PERSONAL, ASIGNACION_ID, DEPENDENCIA, DIRECCION, "+
				"TELEFONO, RESPONSABLE, TO_CHAR(F_INICIO, 'DD/MM/YYYY') AS F_INICIO, ESTADO, SECTOR from ENOC.ssoc_asignacion "+ 
    	    		"where codigo_personal=? "+
    	    		"and asignacion_id=?";
    	    Object[] parametros = new Object[] {codigoPersonal, id};
			objeto = enocJdbc.queryForObject(comando, new SsocAsignacionMapper(), parametros);
			
    	}catch(Exception ex){
    		System.out.println("Error - aca.ssoc.spring.SsocAsignacionDao|getAsignacion|:"+ex);
    	}
    	return objeto;
    }

    public boolean modificaAsignacion( SsocAsignacion ssocAsignacion) {
        String comando = "";
        boolean ok = false;
        
    	try{
    	    comando="update ENOC.ssoc_asignacion set " + 
    			"dependencia=?,direccion=?, " +
    			"telefono=?,responsable=?, " +
    			"f_inicio=to_date(?,'dd/mm/yyyy') " +
    	    	"where codigo_personal=? "+
	    		"and asignacion_id=?";
    	    Object[] parametros = new Object[] {ssocAsignacion.getDependencia(), ssocAsignacion.getDireccion(),
    	    ssocAsignacion.getTelefono(), ssocAsignacion.getResponsable(), ssocAsignacion.getFechaInicio(),
    	    ssocAsignacion.getCodigoPersonal(), ssocAsignacion.getAsignacionId()};
    	    if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
    	    
    	}catch(Exception ex){
    		System.out.println("Error - aca.ssoc.spring.SsocAsignacionDao|modificaAsignacion|:"+ex);
    	}
    	return ok;
    }

    public String eliminaAsignacion( String codigoPersonal, String id) {
		
    	String comando 		= "";    	
    	String matricula	= "";    	
    	try{
    	    comando=" SELECT COUNT(*) FROM ENOC.SSOC_ASIGNACION WHERE CODIGO_PERSONAL = ? AND ASIGNACION_ID = ?";
    	    Object[] parametros = new Object[] {codigoPersonal, id};
    	    if (enocJdbc.queryForObject(comando,Integer.class, parametros)>=1){   
				
				// ELIMINA LA LIGA ENTRE LA ASIGNACION Y EL DOCUMENTO DEL ALUMNO ( SSOC_DOCALUM )
    	        comando = "UPDATE ENOC.SSOC_DOCALUM SET ASIGNACION_ID = 0 " + 
    	        	"WHERE CODIGO_PERSONAL = ?" +
    	        	"AND ASIGNACION_ID = ?";
    	        enocJdbc.update(comando,parametros);
    	        
				// ELIMINA UNA ASIGNACION DEL ALUMNO ( SSOC_ASIGNACION )
				comando="DELETE FROM ENOC.SSOC_ASIGNACION WHERE CODIGO_PERSONAL = ? AND ASIGNACION_ID = ?";
				enocJdbc.update(comando,parametros);
				matricula = "";
    	    }else{
				matricula = "x";
    	    }
   		
    	}catch(Exception ex){
    		System.out.println("Error - aca.ssoc.spring.SsocAsignacionDao|EliminaAsgnacion|:"+ex);
    	}
    	
    	return matricula;
    }
    
    public String getDependenciaUltimaAsignacion( String codigoPersonal) {
		String comando		= "";
    	String dependencia	= "X";
    	
    	try{
    	    comando="SELECT DEPENDENCIA FROM ENOC.SSOC_ASIGNACION" + 
    	    		" WHERE CODIGO_PERSONAL = ?" +
    	    		" AND F_INICIO = (SELECT MAX(F_INICIO) AS F_INICIO" +
    	    						" FROM ENOC.SSOC_ASIGNACION" + 
    	    						" WHERE CODIGO_PERSONAL = ?)";
    	    Object[] parametros = new Object[] {codigoPersonal,codigoPersonal};
			dependencia = enocJdbc.queryForObject(comando,String.class, parametros);
			
    	}catch(Exception ex){
    		System.out.println("Error - aca.ssoc.spring.SsocAsignacionDao|EliminaAsgnacion|:"+ex);
    	}
    	
    	return dependencia;
    }
    
	public HashMap<String,String> mapaSector() {	
		HashMap<String,String> mapa = new HashMap<String,String>();
		List<aca.Mapa> lista		= new ArrayList<aca.Mapa>();
		
		try{
			String comando = "SELECT CODIGO_PERSONAL AS LLAVE, SECTOR AS VALOR FROM ENOC.SSOC_ASIGNACION ORDER BY F_INICIO";
			lista = enocJdbc.query(comando, new aca.MapaMapper());
			for (aca.Mapa map : lista ) {
				mapa.put(map.getLlave(), "0"+map.getValor());
			}	
			
		}catch(Exception ex){
			System.out.println("Error - aca.ssoc.spring.SsocAsignacionDao|mapaSector|:"+ex);
			ex.printStackTrace();
		}
		
		return mapa;		
	}
	
	public HashMap<String,SsocAsignacion> mapaAsignaciones(String codigoPersonal) {	
		HashMap<String,SsocAsignacion> mapa = new HashMap<String,SsocAsignacion>();
		List<SsocAsignacion> lista		= new ArrayList<SsocAsignacion>();
		
		try{
			String comando = "SELECT CODIGO_PERSONAL, ASIGNACION_ID, DEPENDENCIA, DIRECCION,"
					+ " TELEFONO, RESPONSABLE, TO_CHAR(F_INICIO, 'DD/MM/YYYY') AS F_INICIO, ESTADO, SECTOR "
					+ " FROM ENOC.SSOC_ASIGNACION"
					+ " WHERE CODIGO_PERSONAL = ?";
			Object[] parametros = new Object[] {codigoPersonal};
			lista = enocJdbc.query(comando, new SsocAsignacionMapper(), parametros);
			for (SsocAsignacion asignacion: lista ) {
				mapa.put(asignacion.getAsignacionId(), asignacion);
			}	
			
		}catch(Exception ex){
			System.out.println("Error - aca.ssoc.spring.SsocAsignacionDao|mapaAsignaciones|:"+ex);
			ex.printStackTrace();
		}
		
		return mapa;		
	}    

	public HashMap<String,String> mapaSectorAsignado(String fechaIni, String fechaFin) {	
		HashMap<String,String> mapa = new HashMap<String,String>();
		List<SsocAsignacion> lista		= new ArrayList<SsocAsignacion>();
		
		try{
			String comando = "SELECT CODIGO_PERSONAL, ASIGNACION_ID, DEPENDENCIA, DIRECCION,TELEFONO, RESPONSABLE, TO_CHAR(F_INICIO, 'DD/MM/YYYY') AS F_INICIO, ESTADO, SECTOR"
					+ " FROM SSOC_ASIGNACION WHERE CODIGO_PERSONAL IN(SELECT CODIGO_PERSONAL FROM SSOC_INICIO WHERE"
					+ " CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM ENOC.SSOC_DOCALUM WHERE DOCUMENTO_ID = 3 AND FECHA BETWEEN TO_DATE(?,'DD/MM/YYYY') AND TO_DATE(?,'DD/MM/YYYY')"
					+ " AND CODIGO_PERSONAL NOT IN (SELECT CODIGO_PERSONAL FROM ENOC.SSOC_DOCALUM WHERE DOCUMENTO_ID = 22)))";
			
			Object[] parametros = new Object[] {fechaIni,fechaFin};
			
			lista = enocJdbc.query(comando, new SsocAsignacionMapper(), parametros);
			for (SsocAsignacion asignacion: lista ) {
				mapa.put(asignacion.codigoPersonal, asignacion.sector);
			}	
			
		}catch(Exception ex){
			System.out.println("Error - aca.ssoc.spring.SsocAsignacionDao|mapaSectorAsignado|:"+ex);
			ex.printStackTrace();
		}
		
		return mapa;		
	}    
}