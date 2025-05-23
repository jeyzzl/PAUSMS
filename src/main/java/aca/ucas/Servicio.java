/*
 * Created on 13/02/2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package aca.ucas;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

/**
 * @author Pedro
 *
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Servicio {
	public Date fechai;
	public Date fechaf;

    public boolean guardaServicio(Connection conn, ValuesUca servicio) throws SQLException{
        String comando = "";    	
    	Statement stmt = conn.createStatement();    	
    	boolean grabo=true;
    	
    	try{
    	    comando="INSERT INTO ENOC.UCA_SERVICIO(CODIGO_PERSONAL, CARGA_ID, BLOQUE_ID, " + 
    	    		"SERVICIO_ID, HSEMANAL, HPERIODO, DESCRIPCION, UCAS) " +
    	    	"VALUES('"+servicio.codigoPersonal+"'," +
    	    	"'"+servicio.cargaId+"',"+
    	    	"TO_NUMBER('"+servicio.bloqueId+"'),"+
    	    	"TO_NUMBER('"+servicio.id+"'),"+
    	    	servicio.hSemanal+","+
    	    	servicio.hPeriodo+","+
				"'"+servicio.descripcion+"',"+
				"(select valor from ENOC.cat_servicio where servicio_id="+servicio.id+")*"+servicio.hSemanal+")"; 
    	    if (stmt.executeUpdate(comando)==0) grabo = false; 
    	}catch(Exception ex){
    		System.out.println("Error - aca.ucas.Servicio|guardaServicio|:"+ex);
    		grabo=false;
    	}
    	finally{
    	    try { stmt.close(); } catch (Exception ignore) { }
    	}
    	return grabo;
    }
    
    public ArrayList<ValuesUca> getServicios(Connection conn,String codigoPersonal, String cargaId) throws SQLException{
        ArrayList<ValuesUca> servicios=new ArrayList<ValuesUca>();
        String comando = "";
        ValuesUca servicio=null;
    	PreparedStatement ps=null;
    	ResultSet rs = null;
    	try{
    	    comando="select s.codigo_personal,s.carga_id,s.bloque_id,"+
    	    		"s.servicio_id,s.hsemanal,s.hperiodo,s.descripcion,"+
    	    		"s.ucas, c.nombre, c.valor "+
    	    		"from ENOC.UCA_SERVICIO s, cat_servicio c "+ 
    	    		"where c.servicio_id=s.servicio_id "+
    	    		"and s.codigo_personal=? " +
    				"and s.carga_id=?";
    	    ps = conn.prepareStatement(comando);
    	    ps.setString(1,codigoPersonal);
    	    ps.setString(2,cargaId);
    	    rs = ps.executeQuery();
    	    while(rs.next()){
    	        servicio=new ValuesUca();
    	        servicio.codigoPersonal=rs.getString(1);
    	        servicio.cargaId=rs.getString(2);
    	        servicio.bloqueId=rs.getInt(3);
    	        servicio.id=rs.getInt(4);
    	        servicio.hSemanal=rs.getDouble(5);
    	        servicio.hPeriodo=rs.getDouble(6);
    	        servicio.descripcion=rs.getString(7);
    	        servicio.ucas=rs.getDouble(8);
    	        servicio.nombre=rs.getString(9);
    	        servicio.setValor(rs.getDouble(10));
    	        servicios.add(servicio);
    	    }
   		
    	}catch(Exception ex){
    		System.out.println("Error - aca.ucas.Servicio|getServicios|:"+ex);
    	}
    	finally{
    	    try { ps.close(); } catch (Exception ignore) { }
    	}
    	return servicios;
    }

    public ValuesUca getServicio(Connection conn,ValuesUca servicio) throws SQLException{
        String comando = "";
    	PreparedStatement ps=null;
    	ResultSet rs = null;
    	try{
    	    comando="select * from ENOC.UCA_SERVICIO "+ 
    	    		"where codigo_personal=? and "+
    	    		"carga_id=? and bloque_id=? "+
    	    		"and servicio_id =?";
    	    ps = conn.prepareStatement(comando);
    	    ps.setString(1,servicio.codigoPersonal);
    	    ps.setString(2,servicio.cargaId);
    	    ps.setInt(3,servicio.bloqueId);
    	    ps.setInt(4,servicio.id);
    	    rs = ps.executeQuery();
    	    while(rs.next()){
    	        servicio=new ValuesUca();
    	        servicio.codigoPersonal=rs.getString(1);
    	        servicio.cargaId=rs.getString(2);
    	        servicio.bloqueId=rs.getInt(3);
    	        servicio.id=rs.getInt(4);
    	        servicio.hSemanal=rs.getDouble(5);
    	        servicio.hPeriodo=rs.getDouble(6);
    	        servicio.descripcion=rs.getString(7);
    	        servicio.ucas=rs.getDouble(8);
    	    }
   		
    	}catch(Exception ex){
    		System.out.println("Error - aca.ucas.Servicio|getServicio|:"+ex);
    	}
    	finally{
    	    try { ps.close(); } catch (Exception ignore) { }
    	}
    	return servicio;
    }

    public void modificaServicio(Connection conn, ValuesUca servicio) throws SQLException{

        String comando = "";
    	PreparedStatement ps=null;
    	try{
    	    comando="update ENOC.UCA_SERVICIO set " + 
    	    		"hsemanal=?,hperiodo=?,descripcion=?,ucas=TO_NUMBER((select valor from ENOC.cat_servicio where servicio_id="+servicio.id+")*"+servicio.hSemanal+")"+ 
    	    		" where codigo_personal=? and carga_id=? " +
    	    		"and bloque_id=? and servicio_id=?";
    	    ps = conn.prepareStatement(comando);
    	    ps.setDouble(1,servicio.hSemanal);
    	    ps.setDouble(2,servicio.hPeriodo);
    	    ps.setString(3,servicio.descripcion);
    	    ps.setString(4,servicio.codigoPersonal);
    	    ps.setString(5,servicio.cargaId);
    	    ps.setInt(6,servicio.bloqueId);
    	    ps.setInt(7,servicio.id);
    	    ps.executeUpdate();
   		
    	}catch(Exception ex){
    		System.out.println("Error - aca.ucas.Servicio|modificaServicio|:"+ex);
    	}
    	finally{
    	    try { ps.close(); } catch (Exception ignore) { }
    	}
    }

    public void eliminaServicio(Connection conn, ValuesUca servicio) throws SQLException{

        String comando = "";
    	PreparedStatement ps=null;
    	try{
    	    comando="delete from ENOC.UCA_SERVICIO " + 
    	    		"where codigo_personal=? and carga_id=? " +
    	    		"and bloque_id=? and servicio_id=?";
    	    ps = conn.prepareStatement(comando);
    	    ps.setString(1,servicio.codigoPersonal);
    	    ps.setString(2,servicio.cargaId);
    	    ps.setInt(3,servicio.bloqueId);
    	    ps.setInt(4,servicio.id);
    	    ps.executeUpdate();
   		
    	}catch(Exception ex){
    		System.out.println("Error - aca.ucas.Servicio|eliminaServicio|:"+ex);
    	}
    	finally{
    	    try { ps.close(); } catch (Exception ignore) { }
    	}
    }

    public void getFechasCarga(Connection conn,String cargaId) throws SQLException{

        String comando = "";
    	PreparedStatement ps=null;
    	ResultSet rs;
    	try{
    	    comando="select f_inicio,f_final from ENOC.carga where carga_id=?"; 
    	    ps = conn.prepareStatement(comando);
    	    ps.setString(1,cargaId);
    	    rs=ps.executeQuery();
    	    if(rs.next()){
    	    	fechai=rs.getDate(1);
    	    	fechaf=rs.getDate(2);
    	    }
   		
    	}catch(Exception ex){
    		System.out.println("Error - aca.ucas.Servicio|getFechasCarga|:"+ex);
    	}
    	finally{
    	    try { ps.close(); } catch (Exception ignore) { }
    	}
    }
    
    public int getBloque(Connection conn,String cargaId) throws SQLException{

        String comando = "";
    	PreparedStatement ps=null;
    	ResultSet rs;
    	int bloque=0;
    	try{
    	    comando="select bloque_id from ENOC.carga_bloque where carga_id=?  "; 
    	    ps = conn.prepareStatement(comando);
    	    ps.setString(1,cargaId);
    	    rs=ps.executeQuery();
    	    if(rs.next()){
    	    	bloque=rs.getInt(1);
    	    }
   		
    	}catch(Exception ex){
    		System.out.println("Error - aca.ucas.Servicio|getBloque|:"+ex);
    	}
    	finally{
    	    try { ps.close(); } catch (Exception ignore) { }
    	}
    	return bloque;
    }
}