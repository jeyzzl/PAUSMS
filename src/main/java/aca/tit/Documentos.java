/*
 * Created on 13/02/2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package aca.tit;

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
    	    comando="INSERT INTO ENOC.TIT_DOCUMENTOS VALUES ((select COALESCE(max(DOCUMENTO_ID),0)+1 from TIT_documentos),?,?,?)"; 
    	    ps = conn.prepareStatement(comando);
    	    ps.setString(1,documento.getNombre());
    	    ps.setInt(2,documento.getOrden());
    	    ps.setString(3,documento.getTipo());
    		ps.executeUpdate();
    	}catch(Exception ex){
    		System.out.println("Error - aca.tit.Documentos|guardaDocumento|:"+ex);
    		grabo=false;
    	}
    	finally{
    	    try { ps.close(); } catch (Exception ignore) { }
    	}
    	return grabo;
    }
    
    public ArrayList<RequisitoVO> getDocumentos(Connection conn, String orden) throws SQLException{
        ArrayList<RequisitoVO> documentos=new ArrayList<RequisitoVO>();
        String comando = "";
        RequisitoVO documento=null;
    	PreparedStatement ps=null;
    	ResultSet rs = null;
    	try{
    	    comando="SELECT * FROM ENOC.TIT_DOCUMENTOS " + orden; 
    	    ps = conn.prepareStatement(comando);
    	    rs = ps.executeQuery();
    	    while(rs.next()){
    	        documento=new RequisitoVO();
    	        documento.setId(rs.getInt(1));
    	        documento.setNombre(rs.getString(2));
    	        documento.setOrden(rs.getInt(3));
    	        documento.setTipo(rs.getString(4));
    	        documentos.add(documento);
    	    }
   		
    	}catch(Exception ex){
    		System.out.println("Error - aca.tit.Documentos|getDocumentos|:"+ex);
    	}
    	finally{
    	    try { ps.close(); } catch (Exception ignore) { }
    	}
    	return documentos;
    }

    public RequisitoVO getDocumento(Connection conn,int id) throws SQLException{
        String comando = "";
        RequisitoVO documento=null;
    	PreparedStatement ps=null;
    	ResultSet rs = null;
    	try{
    	    comando="SELECT * FROM ENOC.TIT_DOCUMENTOS WHERE DOCUMENTO_ID=?"; 
    	    ps = conn.prepareStatement(comando);
    	    ps.setInt(1,id);
    	    rs = ps.executeQuery();
    	    while(rs.next()){
    	        documento=new RequisitoVO();
    	        documento.setId(rs.getInt(1));
    	        documento.setNombre(rs.getString(2));
    	        documento.setOrden(rs.getInt(3));
    	        documento.setTipo(rs.getString(4));
    	    }
   		
    	}catch(Exception ex){
    		System.out.println("Error - aca.tit.Documentos|getDocumento|:"+ex);
    	}
    	finally{
    	    try { ps.close(); } catch (Exception ignore) { }
    	}
    	return documento;
    }

    public void modificaDocumento(Connection conn, RequisitoVO documento) throws SQLException{

        String comando = "";
    	PreparedStatement ps=null;
    	try{
    	    comando="UPDATE ENOC.TIT_DOCUMENTOS SET DOCUMENTO_NOMBRE=?,ORDEN=?,TIPO=? WHERE DOCUMENTO_ID=?"; 
    	    ps = conn.prepareStatement(comando);
    	    ps.setString(1,documento.getNombre());
    	    ps.setInt(2,documento.getOrden());
    	    ps.setString(3,documento.getTipo());
    	    ps.setInt(4,documento.getId());
    	    ps.executeUpdate();
   		
    	}catch(Exception ex){
    		System.out.println("Error - aca.tit.Documentos|modificaDocumento|:"+ex);
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
    	    comando="SELECT DOCUMENTO_ID FROM ENOC.TIT_DOCALUM WHERE DOCUMENTO_ID=?"; 
    	    ps = conn.prepareStatement(comando);
    	    ps.setString(1,id);
    	    rs = ps.executeQuery();
    	    if(rs.next()){
    	        matricula=rs.getString(2);
    	    }
    	    if(matricula.equals("")){
    	        comando="DELETE FROM ENOC.TIT_DOCUMENTOS WHERE DOCUMENTO_ID = ?"; 
    	    	ps = conn.prepareStatement(comando);
    	    	ps.setString(1,id);
    	    	ps.executeUpdate();
    	    }
   		
    	}catch(Exception ex){
    		System.out.println("Error - aca.tit.Documentos|eliminaDocumento|:"+ex);
    	}
    	finally{
    	    try { ps.close(); } catch (Exception ignore) { }
    	}
    	return matricula;
    }
}