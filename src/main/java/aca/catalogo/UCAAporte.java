package aca.catalogo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import aca.ucas.ValuesO;

public class UCAAporte{

    public void guardaAporte(Connection conn, ValuesO aporte) throws SQLException{
    	String comando = "";
    	PreparedStatement ps=null;
    	try{
    	    comando="INSERT INTO ENOC.CAT_APORTE VALUES ((select COALESCE(max(aporte_id),0)+1 from ENOC.cat_aporte),?,?)"; 
    	    ps = conn.prepareStatement(comando);
    	    ps.setString(1,aporte.nombre);
    	    ps.setDouble(2,aporte.valor);
    		ps.executeUpdate();
   		
    	}catch(Exception ex){
    		System.out.println("Error - aca.catalogo.UCAAporte|guardaAporte|:"+ex);
    	}
    	finally{
    	    try { ps.close(); } catch (Exception ignore) { }
    	}
    }

    public ArrayList<ValuesO> getAportes(Connection conn) throws SQLException{
    	String comando = "";
    	PreparedStatement ps=null;
    	ResultSet rs=null;
    	ValuesO aporte =null;
    	ArrayList<ValuesO> aportes = new ArrayList<ValuesO>();
    	try{
    	    comando="select * from ENOC.CAT_APORTE order by aporte_id"; 
    	    ps = conn.prepareStatement(comando);
    		rs=ps.executeQuery();
    		while(rs.next()){
    		    aporte=new ValuesO();
    		    aporte.id=rs.getInt(1);
    		    aporte.nombre=rs.getString(2);
    		    aporte.valor=rs.getDouble(3);
    		    aportes.add(aporte);
    		}
    	}catch(Exception ex){
    		System.out.println("Error - aca.catalogo.UCAAporte|getAportes|:"+ex);
    	}
    	finally{
    	    try { ps.close(); } catch (Exception ignore) { }
    	    try { rs.close(); } catch (Exception ignore) { }
    	}
    	return aportes;
    }

    public ValuesO getAporte(Connection conn,String idAporte) throws SQLException{
    	String comando = "";
    	PreparedStatement ps=null;
    	ResultSet rs=null;
    	ValuesO aporte =null;
    	try{
    	    comando="select * from ENOC.CAT_APORTE where aporte_id=?"; 
    	    ps = conn.prepareStatement(comando);
    	    ps.setString(1,idAporte);
    		rs=ps.executeQuery();
    		if(rs.next()){
    		    aporte=new ValuesO();
    		    aporte.id=rs.getInt(1);
    		    aporte.nombre=rs.getString(2);
    		    aporte.valor=rs.getDouble(3);
    		}
    	}catch(Exception ex){
    		System.out.println("Error - aca.catalogo.UCAAporte|getAporte|:"+ex);
    	}
    	finally{
    	    try { ps.close(); } catch (Exception ignore) { }
    	    try { rs.close(); } catch (Exception ignore) { }
    	}
    	return aporte;
    }
    
    public void eliminaAporte(Connection conn, int idAporte) throws SQLException{
    	String comando = "";
    	PreparedStatement ps=null;
    	try{
    	    comando="delete from ENOC.CAT_APORTE where aporte_id = ?"; 
    	    ps = conn.prepareStatement(comando);
    	    ps.setInt(1,idAporte);
    		ps.executeUpdate();
   		
    	}catch(Exception ex){
    		System.out.println("Error - aca.catalogo.UCAAporte|eliminaAporte|:"+ex);
    	}
    	finally{
    	    try { ps.close(); } catch (Exception ignore) { }
    	}
    }
    
    public void modificaAporte(Connection conn, ValuesO aporte) throws SQLException{
    	String comando = "";
    	PreparedStatement ps=null;
    	try{
    	    comando="UPDATE ENOC.CAT_APORTE SET NOMBRE=?,VALOR=? WHERE APORTE_ID=?"; 
    	    ps = conn.prepareStatement(comando);
    	    ps.setString(1,aporte.nombre);
    	    ps.setDouble(2,aporte.valor);
    	    ps.setInt(3,aporte.id);
    		ps.executeUpdate();
   		
    	}catch(Exception ex){
    		System.out.println("Error - aca.catalogo.UCAAporte|modificaAporte|:"+ex);
    	}
    	finally{
    	    try { ps.close(); } catch (Exception ignore) { }
    	}
    }
    
}