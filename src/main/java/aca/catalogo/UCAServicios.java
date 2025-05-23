package aca.catalogo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import aca.ucas.ValuesO;

public class UCAServicios{

    public void guardaServicio(Connection conn, ValuesO servicio) throws SQLException{
    	String comando = "";
    	PreparedStatement ps=null;
    	try{
    	    comando="INSERT INTO ENOC.CAT_SERVICIO VALUES ((select COALESCE(max(servicio_id),0)+1 from ENOC.cat_servicio),?,?)"; 
    	    ps = conn.prepareStatement(comando);
    	    ps.setString(1,servicio.nombre);
    	    ps.setDouble(2,servicio.valor);
    		ps.executeUpdate();
   		
    	}catch(Exception ex){
    		System.out.println("Error - aca.catalogo.UCAServicios|guardaServicio|:"+ex);
    	}finally{
    	    try { ps.close(); } catch (Exception ignore) { }
    	}
    }

    public ArrayList<ValuesO> getServicios(Connection conn) throws SQLException{
    	String comando = "";
    	PreparedStatement ps=null;
    	ResultSet rs=null;
    	ValuesO servicio =null;
    	ArrayList<ValuesO> servicios = new ArrayList<ValuesO>();
    	try{
    	    comando="select * from ENOC.CAT_SERVICIO order by servicio_id"; 
    	    ps = conn.prepareStatement(comando);
    		rs=ps.executeQuery();
    		while(rs.next()){
    		    servicio=new ValuesO();
    		    servicio.id=rs.getInt(1);
    		    servicio.nombre=rs.getString(2);
    		    servicio.valor=rs.getDouble(3);
    		    servicios.add(servicio);
    		}
    	}catch(Exception ex){
    		System.out.println("Error - aca.catalogo.UCAServicios|getServicios|:"+ex);
    	}finally{
    	    try { ps.close(); } catch (Exception ignore) { }
    	    try { rs.close(); } catch (Exception ignore) { }
    	}
    	return servicios;
    }

    public ValuesO getServicio(Connection conn,String idServicio) throws SQLException{
    	String comando = "";
    	PreparedStatement ps=null;
    	ResultSet rs=null;
    	ValuesO servicio =null;
    	try{
    	    comando="select * from ENOC.CAT_SERVICIO where servicio_id=?"; 
    	    ps = conn.prepareStatement(comando);
    	    ps.setString(1,idServicio);
    		rs=ps.executeQuery();
    		if(rs.next()){
    		    servicio=new ValuesO();
    		    servicio.id=rs.getInt(1);
    		    servicio.nombre=rs.getString(2);
    		    servicio.valor=rs.getDouble(3);
    		}
    	}catch(Exception ex){
    		System.out.println("Error - aca.catalogo.UCAServicios|getServicio|:"+ex);
    	}finally{
    	    try { ps.close(); } catch (Exception ignore) { }
    	    try { rs.close(); } catch (Exception ignore) { }
    	}
    	return servicio;
    }
    
    public void eliminaServicio(Connection conn, int idServicio) throws SQLException{
    	String comando = "";
    	PreparedStatement ps=null;
    	try{
    	    comando="delete from ENOC.CAT_SERVICIO where servicio_id = ?"; 
    	    ps = conn.prepareStatement(comando);
    	    ps.setInt(1,idServicio);
    		ps.executeUpdate();
   		
    	}catch(Exception ex){
    		System.out.println("Error - aca.catalogo.UCAServicios|eliminaServicio|:"+ex);
    	}finally{
    	    try { ps.close(); } catch (Exception ignore) { }
    	}
    }
    
    public void modificaServicio(Connection conn, ValuesO servicio) throws SQLException{
    	String comando = "";
    	PreparedStatement ps=null;
    	try{
    	    comando="UPDATE ENOC.CAT_SERVICIO SET NOMBRE=?,VALOR=? WHERE SERVICIO_ID=?"; 
    	    ps = conn.prepareStatement(comando);
    	    ps.setString(1,servicio.nombre);
    	    ps.setDouble(2,servicio.valor);
    	    ps.setInt(3,servicio.id);
    		ps.executeUpdate();
   		
    	}catch(Exception ex){
    		System.out.println("Error - aca.catalogo.UCAServicios|modificaServicio|:"+ex);
    	}finally{
    	    try { ps.close(); } catch (Exception ignore) { }
    	}
    }
}