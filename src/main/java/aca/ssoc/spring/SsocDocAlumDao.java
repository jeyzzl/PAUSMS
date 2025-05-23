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
public class SsocDocAlumDao {
	
	@Autowired
	@Qualifier("jdbcEnoc")
	private JdbcTemplate enocJdbc;
	
    java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
    
    public boolean guardaDocumento( SsocDocAlum documento) {
    	boolean ok = false;
    	try{
    		String comando="INSERT INTO ENOC.SSOC_DOCALUM(CODIGO_PERSONAL,FOLIO,PLAN_ID,DOCUMENTO_ID,ASIGNACION_ID,FECHA,NUM_HORAS,COMENTARIO,ENTREGADO,USUARIO) " + 
    	    		"VALUES(?,?,?,?,?,TO_DATE(?,'DD/MM/YYYY'),?,?,?,?)";
    	    Object[] parametros = new Object[]{documento.getCodigoPersonal(), documento.getFolio(),
    	    		documento.getPlanId(), documento.getDocumentoId(), documento.getAsignacionId(), documento.getFecha(),
    	    		documento.getNumHoras(), documento.getComentario(), documento.getEntregado(), documento.getUsuario()
    	    };
    	    if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
    	}catch(Exception ex){
    		System.out.println("Error - aca.ssoc.spring.SsocDocAlumDao|guardaDocumento|:"+ex);    		
    	}
    	return ok;
    }
    
    public boolean modificaDocumento( SsocDocAlum documento) {
        boolean ok = false;        
    	try{
    	    String comando = "UPDATE ENOC.SSOC_DOCALUM SET"
    	    		+ " PLAN_ID = ?, DOCUMENTO_ID = ?,ASIGNACION_ID = ?,"
    	    		+ " FECHA = TO_DATE(?,'DD/MM/YYYY'),NUM_HORAS = ?,"
    	    		+ " COMENTARIO = ?, ENTREGADO = ?, USUARIO = ?"
    	    		+ " WHERE CODIGO_PERSONAL = ?"
    	    		+ " AND FOLIO = ?";
    	    Object[] parametros = new Object[]{documento.getPlanId(), documento.getDocumentoId(),
    	    documento.getAsignacionId(), documento.getFecha(), documento.getNumHoras(), documento.getComentario(),
    	    documento.getEntregado(), documento.getUsuario(), documento.getCodigoPersonal(), documento.getFolio()};
    	    if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
    	}catch(Exception ex){
    		System.out.println("Error - aca.ssoc.spring.SsocDocAlumDao|modificaDocumento|:"+ex);
    	}
    	return ok;
    }

    public boolean eliminaDocumento( String codigoPersonal,int folio) {
        boolean ok = false;        
    	try{
    		String comando = "DELETE FROM ENOC.SSOC_DOCALUM WHERE CODIGO_PERSONAL = ? AND FOLIO = ?";
    		Object[] parametros = new Object[]{codigoPersonal, folio};
    		if (enocJdbc.update(comando,parametros) == 1){
    			ok = true;
    		}
    	}catch(Exception ex){
    		System.out.println("Error - aca.ssoc.spring.SsocDocAlumDao|eliminaDocumento|:"+ex);
    	}
    	return ok;
    }
    
    public boolean existeReg( String codigoPersonal, String folio){
    	boolean ok	   = false;    	
    	try{
    	    String comando=" SELECT COUNT(CODIGO_PERSONAL) FROM ENOC.SSOC_DOCALUM WHERE CODIGO_PERSONAL = ? AND FOLIO = TO_NUMBER(?,'99')";
    	    Object[] parametros = new Object[] {codigoPersonal, folio};
			if (enocJdbc.queryForObject(comando,Integer.class, parametros)>=1){
				ok = true;
			}
    	}catch(Exception ex){
    		System.out.println("Error - aca.ssoc.spring.SsocDocAlumDao|existeReg|:"+ex);
    	}
    	return ok;
    }    
    
    public String maximoReg( String codigoPersonal ){
    	int maximo = 0;    	
    	try{
    	    String comando = "SELECT COALESCE(MAX(FOLIO)+1,1) FROM ENOC.SSOC_DOCALUM WHERE CODIGO_PERSONAL = ?";
    	    Object[] parametros = new Object[] {codigoPersonal};
			maximo = enocJdbc.queryForObject(comando,Integer.class, parametros);
    	}catch(Exception ex){
    		System.out.println("Error - aca.ssoc.spring.SsocDocAlumDao|existeReg|:"+ex);
    	}
    	return String.valueOf(maximo);
    }
	
    public SsocDocAlum getDocumento(String codigoPersonal, int folio){        
        SsocDocAlum objeto = new SsocDocAlum();
    	try{
    		String comando="SELECT CODIGO_PERSONAL, FOLIO, PLAN_ID, DOCUMENTO_ID, ASIGNACION_ID, FECHA, NUM_HORAS, COMENTARIO, ENTREGADO, MES, USUARIO FROM ENOC.SSOC_DOCALUM "+ 
    	    		"WHERE CODIGO_PERSONAL=? "+
    	    		"AND FOLIO=?";
    	    Object[] parametros = new Object[]{codigoPersonal, folio};
    	    objeto = enocJdbc.queryForObject(comando,  new SsocDocAlumMapper(), parametros);    	    
    	}catch(Exception ex){
    		System.out.println("Error - aca.ssoc.spring.SsocDocAlumDao|getDocumento|:"+ex);
    	}
    	return objeto;
    }
    
    public SsocDocAlum mapeaReg(String codigoPersonal, String folio) {        
        SsocDocAlum objeto = new SsocDocAlum();        
    	try{
    	    String comando="SELECT CODIGO_PERSONAL, FOLIO, PLAN_ID, DOCUMENTO_ID, ASIGNACION_ID, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, NUM_HORAS, COMENTARIO, ENTREGADO, MES, USUARIO FROM ENOC.SSOC_DOCALUM"
    	    		+ " WHERE CODIGO_PERSONAL=? AND FOLIO = TO_NUMBER(?,'99')";
    	    Object[] parametros = new Object[]{codigoPersonal, folio};
    	    objeto = enocJdbc.queryForObject(comando, new SsocDocAlumMapper(), parametros);    	    
    	}catch(Exception ex){
    		System.out.println("Error - aca.ssoc.spring.SsocDocAlumDao|mapeaReg|:"+ex);
    	}
    	return objeto;
    }

    
    
    public String getNombreDocumento( int id) {
    	
        String comando = "";
    	String nombre="";
    	try{
    	    comando="SELECT DOCUMENTO_NOMBRE FROM ENOC.SSOC_DOCUMENTOS " + 
    	    		"WHERE DOCUMENTO_ID=?";
    	    Object[] parametros = new Object[]{id};
    	    nombre = enocJdbc.queryForObject(comando, String.class, parametros);
    	    
    	}catch(Exception ex){
    		System.out.println("Error - aca.ssoc.spring.SsocDocAlumDao|getNOmbreDocumento|:"+ex);
    	}
    	return nombre;
    }
    
    public List<SsocDocAlum> getDocumentos(String codigoPersonal) {
        List<SsocDocAlum> lista = new ArrayList<SsocDocAlum>();
    	try{
    	    String comando="SELECT CODIGO_PERSONAL, FOLIO, PLAN_ID, DOCUMENTO_ID, ASIGNACION_ID, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, NUM_HORAS, COMENTARIO, ENTREGADO, MES, USUARIO FROM ENOC.SSOC_DOCALUM WHERE CODIGO_PERSONAL= ? ORDER BY FECHA, DOCUMENTO_ID ASC";
    	    lista = enocJdbc.query(comando, new SsocDocAlumMapper());    	    
    	}catch(Exception ex){
    		System.out.println("Error - aca.ssoc.spring.SsocDocAlumDao|getDocumentos|:"+ex);
    	}
    	return lista;
    }
    
    public List<SsocDocAlum> getDocPlan(String codigoPersonal, String Plan) {
        List<SsocDocAlum> lista = new ArrayList<SsocDocAlum>();
    	try{
    	    String comando = " SELECT CODIGO_PERSONAL, FOLIO, PLAN_ID, DOCUMENTO_ID, ASIGNACION_ID, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, NUM_HORAS, COMENTARIO, ENTREGADO, MES, USUARIO"
    	    		+ " FROM ENOC.SSOC_DOCALUM "
    	    		+ " WHERE CODIGO_PERSONAL= ?"
    	    		+ " AND PLAN_ID = ? ORDER BY ENOC.SSOC_DOCORDEN(DOCUMENTO_ID)";
    	    Object[] parametros = new Object[]{codigoPersonal, Plan};
    	    lista = enocJdbc.query(comando, new SsocDocAlumMapper(), parametros);    	    
    	}catch(Exception ex){
    		System.out.println("Error - aca.ssoc.spring.SsocDocAlumDao|getDocPlan|:"+ex);
    	}
    	return lista;
    }
    
    public List<String> getDocumentosFaltantes( String codigoPersonal, String planId) {
        List<String> lista = new ArrayList<String>();
        String comando = "";
        
    	try{
    	    comando=" SELECT DOCUMENTO_ID FROM ENOC.SSOC_DOCALUM"+ 
    	    		" WHERE CODIGO_PERSONAL=?" +
    	    		" AND PLAN_ID = ?" +
    	    		" AND ENTREGADO='N' " +
    	    		" ORDER BY ENOC.SSOC_DOCORDEN(DOCUMENTO_ID)";
    	    Object[] parametros = new Object[]{codigoPersonal, planId};
    	    lista = enocJdbc.queryForList(comando, String.class, parametros);
    	    
    	}catch(Exception ex){
    		System.out.println("Error - aca.ssoc.spring.SsocDocAlumDao|getDocumentosFaltantes|:"+ex);
    	}
    	return lista;
    }
    
    public List<SsocDocAlum> lisDocumentosPorAlumno( String codigoPersonal, String documentoId, String orden) {
        List<SsocDocAlum> lista = new ArrayList<SsocDocAlum>();
    	try{
    	    String comando=" SELECT CODIGO_PERSONAL, FOLIO, PLAN_ID, DOCUMENTO_ID, ASIGNACION_ID, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, NUM_HORAS, COMENTARIO, ENTREGADO, MES, USUARIO FROM ENOC.SSOC_DOCALUM"+ 
    	    	" WHERE CODIGO_PERSONAL = ?" +
    	    	" AND DOCUMENTO_ID = ? " + orden;
    	    Object[] parametros = new Object[]{codigoPersonal, documentoId};
    	    lista = enocJdbc.query(comando, new SsocDocAlumMapper(), parametros);
    	    
    	}catch(Exception ex){
    		System.out.println("Error - aca.ssoc.spring.SsocDocAlumDao|lisDocumentosPorAlumno|:"+ex);
    	}
    	return lista;
    }

    public List<SsocDocAlum> lisDocumentosFaltantes( String codigoPersonal, String planId) {
        List<SsocDocAlum> lista = new ArrayList<SsocDocAlum>();
    	try{
    	    String comando=" SELECT CODIGO_PERSONAL, FOLIO, PLAN_ID, DOCUMENTO_ID, ASIGNACION_ID, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, NUM_HORAS, COMENTARIO, ENTREGADO, MES, USUARIO FROM ENOC.SSOC_DOCALUM"+ 
    	    		" WHERE CODIGO_PERSONAL=?" +
    	    		" AND PLAN_ID = ?" +
    	    		" AND ENTREGADO='N' " +
    	    		" ORDER BY ENOC.SSOC_DOCORDEN(DOCUMENTO_ID)";
    	    Object[] parametros = new Object[]{codigoPersonal, planId};
    	    lista = enocJdbc.query(comando, new SsocDocAlumMapper(), parametros);
    	    
    	}catch(Exception ex){
    		System.out.println("Error - aca.ssoc.spring.SsocDocAlumDao|lisDocumentosFaltantes|:"+ex);
    	}
    	return lista;
    }

	public boolean modificaPlan( String codigo_personal, String planOld, String planNew ) {
        String comando 			= "";
        boolean ok				= false;
		
    	try{ comando="UPDATE ENOC.SSOC_DOCALUM SET PLAN_ID = ?  WHERE CODIGO_PERSONAL = ? AND PLAN_ID = ?";
    		Object[] parametros = new Object[]{codigo_personal, planOld, planNew};
    		if (enocJdbc.update(comando,parametros)==1){
    			ok = true;
    		}
	    }catch(Exception ex){
    		System.out.println("Error - aca.ssoc.spring.SsocDocAlumDao|modificaPlan|:"+ex);
    	}
    	return ok;
    }
	
	public int numDocAlum( String codigoPersonal, String planId) {
		String comando = "";
    	int elementos  = 0;
    	
    	try{
    	    comando=" SELECT COALESCE(COUNT(CODIGO_PERSONAL),0) AS NUMDOC FROM ENOC.SSOC_DOCALUM WHERE CODIGO_PERSONAL = ? AND PLAN_ID = ?";
    	    Object[] parametros = new Object[]{codigoPersonal, planId};
    	    if (enocJdbc.queryForObject(comando, Integer.class, parametros) >= 1) {
				elementos = enocJdbc.queryForObject(comando, Integer.class, parametros);
			}
    	}catch(Exception ex){
    		System.out.println("Error - aca.ssoc.spring.SsocDocAlumDao|numDocAlum|:"+ex);    		
    	}
    	return elementos;
    }
	
	public String getFechaDoc( String codigoPersonal, String planId, String documentoId) {
		String comando = "";
    	String fecha   = "01/01/1950";
    	
    	try{
    	    comando=" SELECT TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA FROM ENOC.SSOC_DOCALUM" + 
    	    		" WHERE CODIGO_PERSONAL = ? " +
    	    		" AND PLAN_ID = ?" +
    	    		" AND DOCUMENTO_ID = TO_NUMBER(?,'99')";
    	    Object[] parametros = new Object[]{codigoPersonal, planId, documentoId};
    	    fecha = enocJdbc.queryForObject(comando, String.class, parametros);
    	    
    	}catch(Exception ex){
    		System.out.println("Error - aca.ssoc.spring.SsocDocAlumDao|numDocAlum|:"+ex);    		
    	}
    	return fecha;
    }
	
	public int totalRequisitos( String codigoPersonal, String planId) {		
    	int total = 0;    	
    	try{
    	   String comando = " SELECT COUNT(CODIGO_PERSONAL) FROM ENOC.SSOC_DOCALUM"
    	    		+ " WHERE CODIGO_PERSONAL = ?"
    	    		+ " AND PLAN_ID = ?"
    	    		+ " AND DOCUMENTO_ID IN (SELECT DOCUMENTO_ID FROM ENOC.SSOC_DOCUMENTOS WHERE OBLIGATORIO = 'P')";
    	    Object[] parametros = new Object[]{codigoPersonal, planId};
    	    total = enocJdbc.queryForObject(comando,Integer.class, parametros);
    	}catch(Exception ex){
    		System.out.println("Error - aca.ssoc.spring.SsocDocAlumDao|totalRequisitos|:"+ex);
    	}
    	return total;
    }		
	
	public boolean getTieneDoctos( String codigoPersonal, String planId) {
		String comando = "";
    	boolean ok	   = false;
    	
    	try{
    	    comando=" SELECT COALESCE(COUNT(CODIGO_PERSONAL),0) AS ELEMENTOS FROM ENOC.SSOC_DOCALUM" + 
    	    		" WHERE CODIGO_PERSONAL = ? " +
    	    		" AND PLAN_ID = ?";
    	    Object[] parametros = new Object[]{codigoPersonal, planId};
    	    if (enocJdbc.update(comando,parametros)==1){
				ok = true;
			}
    	}catch(Exception ex){
    		System.out.println("Error - aca.ssoc.spring.SsocDocAlumDao|getTieneDoctos|:"+ex);
    	}
    	return ok;
    }
	
	public HashMap<String,String> mapaFechaDocumento(String documentoId){
		HashMap<String,String> mapa = new HashMap<String,String>();
		List<aca.Mapa> lista		= new ArrayList<aca.Mapa>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL||PLAN_ID AS LLAVE, TO_CHAR(FECHA,'DD/MM/YYYY') AS VALOR FROM ENOC.SSOC_DOCALUM WHERE DOCUMENTO_ID = TO_NUMBER(?,'99')";
			Object[] parametros = new Object[] {documentoId};	
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for (aca.Mapa map : lista ) {
				mapa.put(map.getLlave(), map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.ssoc.spring.SsocDocAlumDao|mapaFechaDocumento|:"+ex);
			ex.printStackTrace();
		}		
		return mapa;		
	} 
	
	public HashMap<String,String> mapaAsignacionesReg(String codigoPersonal) {
		HashMap<String,String> mapa = new HashMap<String,String>();
		List<aca.Mapa> lista		= new ArrayList<aca.Mapa>();		
		try{
			String comando = "SELECT ASIGNACION_ID AS LLAVE, COUNT(ASIGNACION_ID) AS VALOR FROM ENOC.SSOC_DOCALUM WHERE CODIGO_PERSONAL = ? GROUP BY ASIGNACION_ID";
			Object[] parametros = new Object[] {codigoPersonal};
			lista = enocJdbc.query(comando, new aca.MapaMapper(), parametros);
			for (aca.Mapa map : lista ) {
				mapa.put(map.getLlave(), map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.ssoc.spring.SsocDocAlumDao|mapaAsignacionesReg|:"+ex);
			ex.printStackTrace();
		}
		
		return mapa;		
	}
	
	public HashMap<String,String> mapaNombreEmpleado() {
		HashMap<String,String> mapa = new HashMap<String,String>();
		List<aca.Mapa> lista		= new ArrayList<aca.Mapa>();		
		try{
			String comando = "SELECT CODIGO_PERSONAL AS LLAVE, NOMBRE||' '||APELLIDO_PATERNO||' '||APELLIDO_MATERNO AS VALOR FROM MAESTROS"
					+ "	WHERE CODIGO_PERSONAL IN (SELECT DISTINCT(USUARIO) FROM ENOC.SSOC_DOCALUM)";
			lista = enocJdbc.query(comando,  new aca.MapaMapper());
			for (aca.Mapa map : lista ) {
				mapa.put(map.getLlave(), map.getValor());
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.ssoc.spring.SsocDocAlumDao|mapaNombreEmpleado|:"+ex);
			ex.printStackTrace();
		}
		
		return mapa;		
	}
}