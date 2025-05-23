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
 * 
 */
public class Requisitos {

    public boolean guardaRequisito(Connection conn, RequisitoVO requisito) throws SQLException{
        String comando = "";
    	PreparedStatement ps=null;
    	boolean grabo=true;
    	try{
    	    comando="INSERT INTO ENOC.TIT_REQUISITOS VALUES ((select COALESCE(max(REQUISITO_ID),0)+1 from TIT_requisitos),?,?)"; 
    	    ps = conn.prepareStatement(comando);
    	    ps.setString(1,requisito.getNombre());
    	    ps.setInt(2,requisito.getOrden());
    		ps.executeUpdate();
    	}catch(Exception ex){
    		System.out.println("Error - aca.tit.Requisitos|guardaRequisito|:"+ex);
    		grabo=false;
    	}
    	finally{
    	    try { ps.close(); } catch (Exception ignore) { }
    	}
    	return grabo;
    }
    
    public ArrayList<RequisitoVO> getRequisitos(Connection conn) throws SQLException{
        ArrayList<RequisitoVO> requisitos=new ArrayList<RequisitoVO>();
        String comando = "";
        RequisitoVO requisito=null;
    	PreparedStatement ps=null;
    	ResultSet rs = null;
    	try{
    	    comando="select * from ENOC.TIT_REQUISITOS order by orden"; 
    	    ps = conn.prepareStatement(comando);
    	    rs = ps.executeQuery();
    	    while(rs.next()){
    	        requisito=new RequisitoVO();
    	        requisito.setId(rs.getInt(1));
    	        requisito.setNombre(rs.getString(2));
    	        requisito.setOrden(rs.getInt(3));
    	        requisitos.add(requisito);
    	    }
   		
    	}catch(Exception ex){
    		System.out.println("Error - aca.tit.Requisitos|getRequisitos|:"+ex);
    	}
    	finally{
    	    try { ps.close(); } catch (Exception ignore) { }
    	}
    	return requisitos;
    }

    public RequisitoVO getRequisito(Connection conn,int id) throws SQLException{
        String comando = "";
        RequisitoVO requisito=null;
    	PreparedStatement ps=null;
    	ResultSet rs = null;
    	try{
    	    comando="select * from ENOC.TIT_REQUISITOS "+ 
    	    		"where requisito_id=?";
    	    ps = conn.prepareStatement(comando);
    	    ps.setInt(1,id);
    	    rs = ps.executeQuery();
    	    while(rs.next()){
    	        requisito=new RequisitoVO();
    	        requisito.setId(rs.getInt(1));
    	        requisito.setNombre(rs.getString(2));
    	        requisito.setOrden(rs.getInt(3));
    	    }
   		
    	}catch(Exception ex){
    		System.out.println("Error - aca.tit.Requisitos|RequisitoVO getRequisito|:"+ex);
    	}
    	finally{
    	    try { ps.close(); } catch (Exception ignore) { }
    	}
    	return requisito;
    }

    public void modificaRequisito(Connection conn, RequisitoVO requisito) throws SQLException{

        String comando = "";
    	PreparedStatement ps=null;
    	try{
    	    comando="update ENOC.TIT_REQUISITOS set " + 
    	    		"requisito_nombre=?,orden=? " +
    	    		"where requisito_id=?";
    	    ps = conn.prepareStatement(comando);
    	    ps.setString(1,requisito.getNombre());
    	    ps.setInt(2,requisito.getOrden());
    	    ps.setInt(3,requisito.getId());
    	    ps.executeUpdate();
   		
    	}catch(Exception ex){
    		System.out.println("Error - aca.tit.Requisitos|modificaRequisito|:"+ex);
    	}
    	finally{
    	    try { ps.close(); } catch (Exception ignore) { }
    	}
    }

    public void eliminaRequisito(Connection conn, String id) throws SQLException{

        String comando = "";
    	PreparedStatement ps=null;
    	try{
    	    comando="delete from ENOC.TIT_REQUISITOS " + 
    	    		"where requisito_id=?";
    	    ps = conn.prepareStatement(comando);
    	    ps.setString(1,id);
    	    ps.executeUpdate();
   		
    	}catch(Exception ex){
    		System.out.println("Error - aca.tit.Requisitos|eliminaRequisito|:"+ex);
    	}
    	finally{
    	    try { ps.close(); } catch (Exception ignore) { }
    	}
    }
}