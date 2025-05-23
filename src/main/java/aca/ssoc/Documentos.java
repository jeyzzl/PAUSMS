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
public class Documentos {

    public boolean guardaDocumento(Connection conn, RequisitoVO documento) throws SQLException{
        String comando = "";
    	PreparedStatement ps=null;
    	boolean grabo=true;
    	try{
    	    comando="INSERT INTO ENOC.SSOC_DOCUMENTOS VALUES ((select COALESCE(max(DOCUMENTO_ID),0)+1 from ENOC.ssoc_documentos),?,?,?)"; 
    	    ps = conn.prepareStatement(comando);
    	    ps.setString(1,documento.getNombre());
    	    ps.setInt(2,documento.getOrden());
    	    ps.setString(3,documento.getObligatorio());
    		ps.executeUpdate();
    	}catch(Exception ex){
    		System.out.println("Error - aca.ssoc.Documentos|guardaDocumento|:"+ex);
    		grabo=false;
    	}
    	finally{
    	    try { ps.close(); } catch (Exception ignore) { }
    	}
    	return grabo;
    }
    
    public ArrayList<RequisitoVO> getDocumentos(Connection conn) throws SQLException{
        ArrayList<RequisitoVO> documentos=new ArrayList<RequisitoVO>();
        String comando = "";
        RequisitoVO documento=null;
    	PreparedStatement ps=null;
    	ResultSet rs = null;
    	try{
    	    comando="select * from ENOC.SSOC_DOCUMENTOS order by orden"; 
    	    ps = conn.prepareStatement(comando);
    	    rs = ps.executeQuery();
    	    while(rs.next()){
    	        documento=new RequisitoVO();
    	        documento.setId(rs.getInt(1));
    	        documento.setNombre(rs.getString(2));
    	        documento.setOrden(rs.getInt(3));
    	        documento.setObligatorio(rs.getString(4));
    	        documentos.add(documento);
    	    }
   		
    	}catch(Exception ex){
    		System.out.println("Error - aca.ssoc.Documentos|getDocumentos|:"+ex);
    	}
    	finally{
    	    try { ps.close(); } catch (Exception ignore) { }
    	    try { rs.close(); } catch (Exception ignore) { }
    	}
    	return documentos;
    }

    public RequisitoVO getDocumento(Connection conn,int id) throws SQLException{
        String comando = "";
        RequisitoVO documento=null;
    	PreparedStatement ps=null;
    	ResultSet rs = null;
    	try{
    	    comando="select * from ENOC.SSOC_DOCUMENTOS "+ 
    	    		"where documento_id=?";
    	    ps = conn.prepareStatement(comando);
    	    ps.setInt(1,id);
    	    rs = ps.executeQuery();
    	    while(rs.next()){
    	        documento=new RequisitoVO();
    	        documento.setId(rs.getInt(1));
    	        documento.setNombre(rs.getString(2));
    	        documento.setOrden(rs.getInt(3));
    	        documento.setObligatorio(rs.getString(4));
    	    }
   		
    	}catch(Exception ex){
    		System.out.println("Error - aca.ssoc.Documentos|getDocumento|:"+ex);
    	}
    	finally{
    	    try { ps.close(); } catch (Exception ignore) { }
    	    try { rs.close(); } catch (Exception ignore) { }
    	}
    	return documento;
    }

    public void modificaDocumento(Connection conn, RequisitoVO documento) throws SQLException{

        String comando = "";
    	PreparedStatement ps=null;
    	try{
    	    comando="update ENOC.SSOC_DOCUMENTOS set " + 
    	    		"documento_nombre=?,orden=?,obligatorio=? " +
    	    		"where documento_id=?";
    	    ps = conn.prepareStatement(comando);
    	    ps.setString(1,documento.getNombre());
    	    ps.setInt(2,documento.getOrden());
    	    ps.setString(3,documento.getObligatorio());
    	    ps.setInt(4,documento.getId());
    	    ps.executeUpdate();
   		
    	}catch(Exception ex){
    		System.out.println("Error - aca.ssoc.Documentos|modificaDocumento|:"+ex);
    	}
    	finally{
    	    try { ps.close(); } catch (Exception ignore) { }
    	}
    }

    public String eliminaDocumento(Connection conn, String id) throws SQLException{

        String comando = "";
        String matricula="";
    	PreparedStatement ps=null;
    	ResultSet rs=null;
    	try{
    	    comando="select documento_id,codigo_personal from ENOC.SSOC_DOCALUM where documento_id=?"; 
    	    ps = conn.prepareStatement(comando);
    	    ps.setString(1,id);
    	    rs = ps.executeQuery();
    	    if(rs.next()){
    	        matricula=rs.getString(2);
    	    }
    	    if(matricula.equals("")){
    	        comando="delete from ENOC.SSOC_DOCUMENTOS " + 
    	        		"where documento_id=?";
    	    	ps = conn.prepareStatement(comando);
    	    	ps.setString(1,id);
    	    	ps.executeUpdate();
    	    }
   		
    	}catch(Exception ex){
    		System.out.println("Error - aca.ssoc.Documentos|eliminaDocumento|:"+ex);
    	}
    	finally{
    	    try { ps.close(); } catch (Exception ignore) { }
    	    try { rs.close(); } catch (Exception ignore) { }
    	}
    	return matricula;
    }
}