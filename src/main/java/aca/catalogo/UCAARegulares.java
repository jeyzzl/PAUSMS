package aca.catalogo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import aca.ucas.ValuesO;

public class UCAARegulares{

    public void guardaRegular(Connection conn, ValuesO regular) throws SQLException{
    	String comando = "";
    	PreparedStatement ps=null;
    	try{
    	    comando="INSERT INTO ENOC.CAT_AREGULARES VALUES ((select COALESCE(max(AREGULARES_id),0)+1 from ENOC.cat_aregulares),?,?)"; 
    	    ps = conn.prepareStatement(comando);
    	    ps.setString(1,regular.nombre);
    	    ps.setDouble(2,regular.valor);
    		ps.executeUpdate();
   		
    	}catch(Exception ex){
    		System.out.println("Error - aca.catalogo.UCAARegulares|guardaRegular|:"+ex);
    	}
    	finally{
    	    try { ps.close(); } catch (Exception ignore) { }
    	}
    }

    public ArrayList<ValuesO> getRegulars(Connection conn) throws SQLException{
    	String comando = "";
    	PreparedStatement ps=null;
    	ResultSet rs=null;
    	ValuesO regular =null;
    	ArrayList<ValuesO> regulars = new ArrayList<ValuesO>();
    	try{
    	    comando="select * from ENOC.CAT_AREGULARES order by AREGULARES_id"; 
    	    ps = conn.prepareStatement(comando);
    		rs=ps.executeQuery();
    		while(rs.next()){
    		    regular=new ValuesO();
    		    regular.id=rs.getInt(1);
    		    regular.nombre=rs.getString(2);
    		    regular.valor=rs.getDouble(3);
    		    regulars.add(regular);
    		}
    	}catch(Exception ex){
    		System.out.println("Error - aca.catalogo.UCAARegulares|getRegulars|:"+ex);
    	}
    	finally{
    	    try { ps.close(); } catch (Exception ignore) { }
    	    try { rs.close(); } catch (Exception ignore) { }
    	}
    	return regulars;
    }

    public ValuesO getRegular(Connection conn,String idRegular) throws SQLException{
    	String comando = "";
    	PreparedStatement ps=null;
    	ResultSet rs=null;
    	ValuesO regular =null;
    	try{
    	    comando="select * from ENOC.CAT_AREGULARES where AREGULARES_id=?"; 
    	    ps = conn.prepareStatement(comando);
    	    ps.setString(1,idRegular);
    		rs=ps.executeQuery();
    		if(rs.next()){
    		    regular=new ValuesO();
    		    regular.id=rs.getInt(1);
    		    regular.nombre=rs.getString(2);
    		    regular.valor=rs.getDouble(3);
    		}
    	}catch(Exception ex){
    		System.out.println("Error - aca.catalogo.UCAARegulares|getRegular|:"+ex);
    	}
    	finally{
    	    try { ps.close(); } catch (Exception ignore) { }
    	    try { rs.close(); } catch (Exception ignore) { }
    	}
    	return regular;
    }
    
    public void eliminaRegular(Connection conn, int idRegular) throws SQLException{
    	String comando = "";
    	PreparedStatement ps=null;
    	try{
    	    comando="delete from ENOC.CAT_AREGULARES where AREGULARES_id = ?"; 
    	    ps = conn.prepareStatement(comando);
    	    ps.setInt(1,idRegular);
    		ps.executeUpdate();
   		
    	}catch(Exception ex){
    		System.out.println("Error - aca.catalogo.UCAARegulares|eliminaRegular|:"+ex);
    	}
    	finally{
    	    try { ps.close(); } catch (Exception ignore) { }
    	}
    }
    
    public void modificaRegular(Connection conn, ValuesO regular) throws SQLException{
    	String comando = "";
    	PreparedStatement ps=null;
    	try{
    	    comando="UPDATE ENOC.CAT_AREGULARES SET NOMBRE=?,VALOR=? WHERE AREGULARES_ID=?"; 
    	    ps = conn.prepareStatement(comando);
    	    ps.setString(1,regular.nombre);
    	    ps.setDouble(2,regular.valor);
    	    ps.setInt(3,regular.id);
    		ps.executeUpdate();
   		
    	}catch(Exception ex){
    		System.out.println("Error - aca.catalogo.UCAARegulares|modificaRegular|:"+ex);
    	}
    	finally{
    	    try { ps.close(); } catch (Exception ignore) { }
    	}
    }
    
}