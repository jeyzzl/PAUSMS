/*
 * Created on 13/02/2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package aca.ssoc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
/**
 * @author Pedro
 *
 */
public class DocAlumno {
    java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
    
    public boolean guardaDocumento(Connection conn, DocAlumVO documento) throws SQLException{
		String comando = "";
		PreparedStatement ps=null;
    	boolean grabo=true;
    	try{
    	    comando="INSERT INTO ENOC.SSOC_DOCALUM(CODIGO_PERSONAL,FOLIO,PLAN_ID,DOCUMENTO_ID,ASIGNACION_ID,FECHA,NUM_HORAS,COMENTARIO,ENTREGADO) " + 
    	    		"VALUES(?,(SELECT COALESCE(MAX(FOLIO),0)+1 FROM ENOC.SSOC_DOCALUM WHERE CODIGO_PERSONAL=?),?,?,?,TO_DATE(?,'dd/mm/yyyy'),?,?,?)"; 
    	    ps = conn.prepareStatement(comando);
    	    ps.setString(1,documento.getCodigoPersonal());
    	    ps.setString(2,documento.getCodigoPersonal());
			ps.setString(3,documento.getPlanId());
    	    ps.setInt(4,documento.getDocumentoId());
    	    ps.setInt(5,documento.getAsignacionId());
    	    ps.setString(6,documento.getFecha());
    	    ps.setInt(7,documento.getNumHoras());
    	    ps.setString(8,documento.getComentario());
    	    ps.setString(9,documento.getEntregado());
    		ps.executeUpdate();
    	}catch(Exception ex){
    		System.out.println("Error - aca.ssoc.DocAlumno|guardaDocumento|:"+ex);
    		grabo=false;
    	}
    	finally{
    	    try { ps.close(); } catch (Exception ignore) { }
    	}
    	return grabo;
    }
    
    public ArrayList<DocAlumVO> getDocumentos(Connection conn,String codigoPersonal) throws SQLException{
        ArrayList<DocAlumVO> documentos=new ArrayList<DocAlumVO>();
        String comando = "";
        DocAlumVO documento=null;
    	PreparedStatement ps=null;
    	ResultSet rs = null;
    	try{
    	    comando="SELECT CODIGO_PERSONAL, FOLIO, PLAN_ID, DOCUMENTO_ID, ASIGNACION_ID, FECHA, NUM_HORAS, COMENTARIO, ENTREGADO, MES FROM ENOC.SSOC_DOCALUM WHERE CODIGO_PERSONAL= ? ORDER BY FECHA, DOCUMENTO_ID ASC "; 
    	    ps = conn.prepareStatement(comando);
    	    ps.setString(1,codigoPersonal);
    	    rs = ps.executeQuery();
    	    while(rs.next()){
    	        documento=new DocAlumVO();
    	        documento.setCodigoPersonal(rs.getString(1));
    	        documento.setFolio(rs.getInt(2));
				documento.setPlanId(rs.getString(3));
    	        documento.setDocumentoId(rs.getInt(4));
    	        documento.setAsignacionId(rs.getInt(5));
    	        documento.setFecha(sdf.format(rs.getDate(6)));
    	        documento.setNumHoras(rs.getInt(7));
    	        documento.setComentario(rs.getString(8));
    	        documento.setEntregado(rs.getString(9));
    	        documentos.add(documento);
    	    }
   		
    	}catch(Exception ex){
    		System.out.println("Error - aca.ssoc.DocAlumno|getDocumentos|:"+ex);
    	}
    	finally{
    	    try { ps.close(); } catch (Exception ignore) { }
    	    try { rs.close(); } catch (Exception ignore) { }
    	}
    	return documentos;
    }

	public ArrayList<DocAlumVO> getDocPlan(Connection conn,String codigoPersonal, String Plan) throws SQLException{
        ArrayList<DocAlumVO> documentos=new ArrayList<DocAlumVO>();
        String comando = "";
        DocAlumVO documento=null;
    	PreparedStatement ps=null;
    	ResultSet rs = null;
    	try{
    	    comando="SELECT CODIGO_PERSONAL, FOLIO, PLAN_ID, " +
    	    		"DOCUMENTO_ID, ASIGNACION_ID, FECHA, NUM_HORAS, " +
    	    		"COMENTARIO, ENTREGADO, MES " +
    	    		"FROM ENOC.SSOC_DOCALUM WHERE CODIGO_PERSONAL= ? " + 
    	    		"AND PLAN_ID = ? ORDER BY FECHA, DOCUMENTO_ID ASC ";
    	    ps = conn.prepareStatement(comando);
    	    ps.setString(1,codigoPersonal);
			ps.setString(2,Plan);
    	    rs = ps.executeQuery();
    	    while(rs.next()){
    	        documento = new DocAlumVO();
    	        documento.setCodigoPersonal(rs.getString(1));
    	        documento.setFolio(rs.getInt(2));
				documento.setPlanId(rs.getString(3));
    	        documento.setDocumentoId(rs.getInt(4));
    	        documento.setAsignacionId(rs.getInt(5));
    	        documento.setFecha(sdf.format(rs.getDate(6)));
    	        documento.setNumHoras(rs.getInt(7));
    	        documento.setComentario(rs.getString(8));
    	        documento.setEntregado(rs.getString(9));
    	        documentos.add(documento);
    	    }
   		
    	}catch(Exception ex){
    		System.out.println("Error - aca.ssoc.DocAlumno|getDocPlan|:"+ex);
    	}
    	finally{
    	    try { ps.close(); } catch (Exception ignore) { }
    	    try { rs.close(); } catch (Exception ignore) { }
    	}
    	return documentos;
    }
	
    public DocAlumVO getDocumento(Connection conn,String codigoPersonal, int folio) throws SQLException{
        String comando = "";
        DocAlumVO documento=null;
    	PreparedStatement ps=null;
    	ResultSet rs = null;
    	try{
    	    comando="SELECT CODIGO_PERSONAL, FOLIO, PLAN_ID, DOCUMENTO_ID, ASIGNACION_ID, FECHA, NUM_HORAS, COMENTARIO, ENTREGADO, MES FROM ENOC.SSOC_DOCALUM "+ 
    	    		"WHERE CODIGO_PERSONAL=? "+
    	    		"AND FOLIO=?";
    	    ps = conn.prepareStatement(comando);
    	    ps.setString(1,codigoPersonal);
    	    ps.setInt(2,folio);
    	    rs = ps.executeQuery();
    	    if(rs.next()){
    	        documento=new DocAlumVO();
    	        documento.setCodigoPersonal(rs.getString(1));
    	        documento.setFolio(rs.getInt(2));
				documento.setPlanId(rs.getString(3));
    	        documento.setDocumentoId(rs.getInt(4));
    	        documento.setAsignacionId(rs.getInt(5));
    	        documento.setFecha(sdf.format(rs.getDate(6)));
    	        documento.setNumHoras(rs.getInt(7));
    	        documento.setComentario(rs.getString(8));
    	        documento.setEntregado(rs.getString(9));    	        
    	    }
   		
    	}catch(Exception ex){
    		System.out.println("Error - aca.ssoc.DocAlumno|getDocumento|:"+ex);
    	}
    	finally{
    	    try { ps.close(); } catch (Exception ignore) { }
    	    try { rs.close(); } catch (Exception ignore) { }
    	}
    	return documento;
    }

    public void modificaDocumento(Connection conn, DocAlumVO documento) throws SQLException{

        String comando = "";
    	PreparedStatement ps=null;
    	try{
    	    comando="UPDATE ENOC.SSOC_DOCALUM SET " + 
    			"PLAN_ID=?, DOCUMENTO_ID=?,ASIGNACION_ID=?, " +
    			"FECHA=TO_DATE(?,'DD/MM/YYYY'),NUM_HORAS=?, " +
    			"COMENTARIO=?,ENTREGADO=? " +
    	    	"WHERE CODIGO_PERSONAL=? "+
	    		"AND FOLIO=?";
	    	ps = conn.prepareStatement(comando);
			ps.setString(1,documento.getPlanId());
			ps.setInt(2,documento.getDocumentoId());
    	    ps.setInt(3,documento.getAsignacionId());
    	    ps.setString(4,documento.getFecha());
    	    ps.setInt(5,documento.getNumHoras());
    	    ps.setString(6,documento.getComentario());
    	    ps.setString(7,documento.getEntregado());
    	    ps.setString(8,documento.getCodigoPersonal());
    	    ps.setInt(9,documento.getFolio());
    	    ps.executeUpdate();  	    	
   		
    	}catch(Exception ex){
    		System.out.println("Error - aca.ssoc.DocAlumno|modificaDocumento|:"+ex);
    	}
    	finally{
    	    try { ps.close(); } catch (Exception ignore) { }
    	}
    }

    public void eliminaDocumento(Connection conn, String codigoPersonal,int folio) throws SQLException{

        String comando = "";
    	PreparedStatement ps=null;
    	try{
    	    comando="DELETE FROM ENOC.SSOC_DOCALUM " + 
    	    		"WHERE CODIGO_PERSONAL=? AND FOLIO=?";
    	    ps = conn.prepareStatement(comando);
    	    ps.setString(1,codigoPersonal);
    	    ps.setInt(2,folio);
    	    ps.executeUpdate();
   		
    	}catch(Exception ex){
    		System.out.println("Error - aca.ssoc.DocAlumno|eliminaDocumento|:"+ex);
    	}
    	finally{
    	    try { ps.close(); } catch (Exception ignore) { }
    	}
    }
    
    public String getNombreDocumento(Connection conn, int id) throws SQLException{

        String comando = "";
    	PreparedStatement ps=null;
    	ResultSet rs =null;
    	String nombre="";
    	try{
    	    comando="SELECT DOCUMENTO_NOMBRE FROM ENOC.SSOC_DOCUMENTOS " + 
    	    		"WHERE DOCUMENTO_ID=?";
    	    ps = conn.prepareStatement(comando);
    	    ps.setInt(1,id);
    	    rs=ps.executeQuery();
    	    if(rs.next())nombre=rs.getString(1);
   		
    	}catch(Exception ex){
    		System.out.println("Error - aca.ssoc.DocAlumno|getNOmbreDocumento|:"+ex);
    	}
    	finally{
    	    try { ps.close(); } catch (Exception ignore) { }
    	    try { rs.close(); } catch (Exception ignore) { }
    	}
    	return nombre;
    }

    public ArrayList<String> getDocumentosFaltantes(Connection conn, String codigoPersonal, String planId) throws SQLException{
        ArrayList<String> documentos=new ArrayList<String>();
        String comando = "";
        PreparedStatement ps=null;
    	ResultSet rs = null;
    	try{
    	    comando=" SELECT DOCUMENTO_ID, COALESCE(COMENTARIO,'-') AS COMENTARIO FROM ENOC.SSOC_DOCALUM"+ 
    	    		" WHERE CODIGO_PERSONAL=?" +
    	    		" AND PLAN_ID = ?" +
    	    		" AND ENTREGADO='N' " +
    	    		" ORDER BY ENOC.SSOC_DOCORDEN(DOCUMENTO_ID)";
    	    ps = conn.prepareStatement(comando);
    	    ps.setString(1, codigoPersonal);
    	    ps.setString(2, planId);
    	    rs = ps.executeQuery();
    	    while(rs.next()){
    	        documentos.add(rs.getString("DOCUMENTO_ID")+"&&"+rs.getString("COMENTARIO"));
    	    }
   		
    	}catch(Exception ex){
    		System.out.println("Error - aca.ssoc.DocAlumno|getDocumentosFaltantes|:"+ex);
    	}
    	finally{
    	    try { ps.close(); } catch (Exception ignore) { }
    	    try { rs.close(); } catch (Exception ignore) { }
    	}
    	return documentos;
    }

	public boolean modificaPlan(Connection conn, String codigo_personal, String planOld, String planNew ) throws SQLException{

        PreparedStatement ps	= null;
        String comando 			= "";
        boolean ok				= false;
		
    	try{ comando="UPDATE ENOC.SSOC_DOCALUM SET PLAN_ID = ?  WHERE CODIGO_PERSONAL = ? AND PLAN_ID = ?"; 
				ps = conn.prepareStatement(comando);
				ps.setString(1,planNew);				
				ps.setString(2,codigo_personal);
				ps.setString(3,planOld);
				if (ps.executeUpdate()>=1){
					ok = true;
				}
    	}catch(Exception ex){
    		System.out.println("Error - aca.ssoc.DocAlumno|modificaPlan|:"+ex);
    	}
    	finally{
    	    try { ps.close(); } catch (Exception ignore) { }
    	}
    	return ok;
    }
	
	public int numDocAlum(Connection conn, String codigoPersonal, String planId) throws SQLException{
		
		PreparedStatement ps	= null;
		ResultSet rs			= null;
		String comando 			= "";
    	int elementos			= 0;
    	
    	try{
    	    comando=" SELECT COALESCE(COUNT(CODIGO_PERSONAL),0) AS NUMDOC FROM ENOC.SSOC_DOCALUM WHERE CODIGO_PERSONAL = ? AND PLAN_ID = ?"; 
    	    ps = conn.prepareStatement(comando);
    	    ps.setString(1, codigoPersonal);
    	    ps.setString(2, planId);			
    		rs = ps.executeQuery();
    		if (rs.next()){
    			elementos = rs.getInt("NUMDOC");
    		}    			
    		
    	}catch(Exception ex){
    		System.out.println("Error - aca.ssoc.DocAlumno|numDocAlum|:"+ex);    		
    	}
    	finally{
    	    try { ps.close(); } catch (Exception ignore) { }
    	    try { rs.close(); } catch (Exception ignore) { }
    	}
    	return elementos;
    }
	
	public static String getFechaDoc(Connection conn, String codigoPersonal, String planId, String documentoId) throws SQLException{
		
		PreparedStatement ps	= null;
		ResultSet rs			= null;
		String comando 			= "";
    	String fecha			= "01/01/1950";
    	
    	try{
    	    comando=" SELECT TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA FROM ENOC.SSOC_DOCALUM" + 
    	    		" WHERE CODIGO_PERSONAL = ? " +
    	    		" AND PLAN_ID = ?" +
    	    		" AND DOCUMENTO_ID = TO_NUMBER(?,'99')";
    	    ps = conn.prepareStatement(comando);
    	    ps.setString(1, codigoPersonal);
    	    ps.setString(2, planId);
    	    ps.setString(3, documentoId);
    	    
    		rs = ps.executeQuery();
    		if (rs.next()){
    			fecha = rs.getString("FECHA");
    		}    			
    		
    	}catch(Exception ex){
    		System.out.println("Error - aca.ssoc.DocAlumno|numDocAlum|:"+ex);    		
    	}
    	finally{
    	    try { ps.close(); } catch (Exception ignore) { }
    	    try { rs.close(); } catch (Exception ignore) { }
    	}
    	return fecha;
    }
	
	public static boolean getTieneDoctos(Connection conn, String codigoPersonal, String planId) throws SQLException{
		
		PreparedStatement ps	= null;
		ResultSet rs			= null;
		String comando 			= "";
    	boolean ok				= false;
    	
    	try{
    	    comando=" SELECT COALESCE(COUNT(CODIGO_PERSONAL),0) AS ELEMENTOS FROM ENOC.SSOC_DOCALUM" + 
    	    		" WHERE CODIGO_PERSONAL = ? " +
    	    		" AND PLAN_ID = ?";
    	    ps = conn.prepareStatement(comando);
    	    ps.setString(1, codigoPersonal);
    	    ps.setString(2, planId);    	    
    	    
    		rs = ps.executeQuery();
    		if (rs.next()){
    			if ( rs.getInt("ELEMENTOS")>0 ) ok=true;    				
    		}    			
    		
    	}catch(Exception ex){
    		System.out.println("Error - aca.ssoc.DocAlumno|getTieneDoctos|:"+ex);
    	}
    	finally{
    	    try { ps.close(); } catch (Exception ignore) { }
    	    try { rs.close(); } catch (Exception ignore) { }
    	}
    	return ok;
    }
	
	public static boolean existeReg(Connection conn, String codigoPersonal, String folio) throws SQLException{
		
		PreparedStatement ps	= null;
		ResultSet rs			= null;
		String comando 			= "";
    	boolean ok				= false;
    	
    	try{
    	    comando=" SELECT CODIGO_PERSONAL FROM ENOC.SSOC_DOCALUM" + 
    	    		" WHERE CODIGO_PERSONAL = ? " +
    	    		" AND FOLIO = TO_NUMBER(?)";
    	    ps = conn.prepareStatement(comando);
    	    ps.setString(1, codigoPersonal);
    	    ps.setString(2, folio);    	    
    	    
    		rs = ps.executeQuery();
    		if (rs.next()){
    			ok = true;    				
    		}    			
    		
    	}catch(Exception ex){
    		System.out.println("Error - aca.ssoc.DocAlumno|existeReg|:"+ex);
    	}
    	finally{
    	    try { ps.close(); } catch (Exception ignore) { }
    	    try { rs.close(); } catch (Exception ignore) { }
    	}
    	return ok;
    }
	
	public static boolean existeAsignacion(Connection conn, String codigoPersonal, String asignacionId) throws SQLException{		
		PreparedStatement ps	= null;
		ResultSet rs			= null;
		String comando 			= "";
    	boolean ok				= false;
    	
    	try{
    	    comando=" SELECT CODIGO_PERSONAL FROM ENOC.SSOC_DOCALUM WHERE CODIGO_PERSONAL = ? AND ASIGNACION_ID = TO_NUMBER(?,'9')";
    	    ps = conn.prepareStatement(comando);
    	    ps.setString(1, codigoPersonal);
    	    ps.setString(2, asignacionId);    	    
    	    
    		rs = ps.executeQuery();
    		if (rs.next()){
    			ok = true;    				
    		}    			
    		
    	}catch(Exception ex){
    		System.out.println("Error - aca.ssoc.DocAlumno|existeAsignacion|:"+ex);
    	}
    	finally{
    	    try { ps.close(); } catch (Exception ignore) { }
    	    try { rs.close(); } catch (Exception ignore) { }
    	}
    	return ok;
    }
	
}