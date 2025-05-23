package aca.catalogo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import aca.ucas.ValuesO;

public class UCAAEspeciales{

    public void guardaEspecial(Connection conn, ValuesO especial) throws SQLException{
    	String comando = "";
    	PreparedStatement ps=null;
    	try{
    	    comando="INSERT INTO ENOC.CAT_AESPECIALES VALUES ((select COALESCE(max(AESPECIALES_id),0)+1 from ENOC.cat_aespeciales),?,?,?)"; 
    	    ps = conn.prepareStatement(comando);
    	    ps.setString(1,especial.nombre);
    	    ps.setString(2,especial.tipo);
    	    ps.setDouble(3,especial.valor);
    		ps.executeUpdate();
   		
    	}catch(Exception ex){
    		System.out.println("Error - aca.catalogo.UCAAEspeciales|guardaEspecial|:"+ex);
    	}
    	finally{
    	    try { ps.close(); } catch (Exception ignore) { }
    	}
    }

    public ArrayList<ValuesO> getEspecials(Connection conn) throws SQLException{
    	String comando = "";
    	PreparedStatement ps=null;
    	ResultSet rs=null;
    	ValuesO especial =null;
    	ArrayList<ValuesO> especials = new ArrayList<ValuesO>();
    	try{
    	    comando="select * from ENOC.CAT_AESPECIALES order by AESPECIALES_id"; 
    	    ps = conn.prepareStatement(comando);
    		rs=ps.executeQuery();
    		while(rs.next()){
    		    especial=new ValuesO();
    		    especial.id=rs.getInt(1);
    		    especial.nombre=rs.getString(2);
    		    especial.tipo=rs.getString(3);
    		    especial.valor=rs.getDouble(4);
    		    especials.add(especial);
    		}
    	}catch(Exception ex){
    		System.out.println("Error - aca.catalogo.UCAAEspeciales|getEspecials|:"+ex);
    	}
    	finally{
    	    try { ps.close(); } catch (Exception ignore) { }
    	    try { rs.close(); } catch (Exception ignore) { }
    	}
    	return especials;
    }

    public ValuesO getEspecial(Connection conn,String idEspecial) throws SQLException{
    	String comando = "";
    	PreparedStatement ps=null;
    	ResultSet rs=null;
    	ValuesO especial =null;
    	try{
    	    comando="select * from ENOC.CAT_AESPECIALES where AESPECIALES_id=?"; 
    	    ps = conn.prepareStatement(comando);
    	    ps.setString(1,idEspecial);
    		rs=ps.executeQuery();
    		if(rs.next()){
    		    especial=new ValuesO();
    		    especial.id=rs.getInt(1);
    		    especial.nombre=rs.getString(2);
    		    especial.tipo=rs.getString(3);
    		    especial.valor=rs.getDouble(4);
    		}
    	}catch(Exception ex){
    		System.out.println("Error - aca.catalogo.UCAAEspeciales|getEspecial|:"+ex);
    	}
    	finally{
    	    try { ps.close(); } catch (Exception ignore) { }
    	    try { rs.close(); } catch (Exception ignore) { }
    	}
    	return especial;
    }
    
    public void eliminaEspecial(Connection conn, int idEspecial) throws SQLException{
    	String comando = "";
    	PreparedStatement ps=null;
    	try{
    	    comando="delete from ENOC.CAT_AESPECIALES where AESPECIALES_id = ?"; 
    	    ps = conn.prepareStatement(comando);
    	    ps.setInt(1,idEspecial);
    		ps.executeUpdate();
   		
    	}catch(Exception ex){
    		System.out.println("Error - aca.catalogo.UCAAEspeciales|eliminaEspecial|:"+ex);
    	}
    	finally{
    	    try { ps.close(); } catch (Exception ignore) { }
    	}
    }
    
    public void modificaEspecial(Connection conn, ValuesO especial) throws SQLException{
    	String comando = "";
    	PreparedStatement ps=null;
    	try{
    	    comando="UPDATE ENOC.CAT_AESPECIALES SET NOMBRE=?,TIPO=?,VALOR=? WHERE AESPECIALES_ID=?"; 
    	    ps = conn.prepareStatement(comando);
    	    ps.setString(1,especial.nombre);
    	    ps.setString(2,especial.tipo);
    	    ps.setDouble(3,especial.valor);
    	    ps.setInt(4,especial.id);
    		ps.executeUpdate();
   		
    	}catch(Exception ex){
    		System.out.println("Error - aca.catalogo.UCAAEspeciales|modificaEspecial|:"+ex);
    	}
    	finally{
    	    try { ps.close(); } catch (Exception ignore) { }
    	}
    }
    
}