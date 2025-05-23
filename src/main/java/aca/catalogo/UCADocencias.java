package aca.catalogo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import aca.ucas.ValuesO;

public class UCADocencias{

    public void guardaDocencia(Connection conn, ValuesO docencia) throws SQLException{
    	String comando = "";
    	PreparedStatement ps=null;
    	try{
    	    comando="INSERT INTO ENOC.CAT_DOCENCIA VALUES ((select COALESCE(max(docencia_id),0)+1 from ENOC.cat_docencia),?,?)"; 
    	    ps = conn.prepareStatement(comando);
    	    ps.setString(1,docencia.nombre);
    	    ps.setDouble(2,docencia.valor);
    		ps.executeUpdate();
   		
    	}catch(Exception ex){
    		System.out.println("Error - aca.catalogo.UCADocencias|guardaDocencia|:"+ex);
    	}finally{
    	    try { ps.close(); } catch (Exception ignore) { }
    	}
    }

    public ArrayList<ValuesO> getDocencias(Connection conn) throws SQLException{
    	String comando = "";
    	PreparedStatement ps=null;
    	ResultSet rs=null;
    	ValuesO docencia =null;
    	ArrayList<ValuesO> docencias = new ArrayList<ValuesO>();
    	try{
    	    comando="select * from ENOC.CAT_DOCENCIA order by docencia_id"; 
    	    ps = conn.prepareStatement(comando);
    		rs=ps.executeQuery();
    		while(rs.next()){
    		    docencia=new ValuesO();
    		    docencia.id=rs.getInt(1);
    		    docencia.nombre=rs.getString(2);
    		    docencia.valor=rs.getDouble(3);
    		    docencias.add(docencia);
    		}
    	}catch(Exception ex){
    		System.out.println("Error - aca.catalogo.UCADocencias|getDocencias|:"+ex);
    	}finally{
    	    try { ps.close(); } catch (Exception ignore) { }
    	    try { rs.close(); } catch (Exception ignore) { }
    	}
    	return docencias;
    }

    public ValuesO getDocencia(Connection conn,String idDocencia) throws SQLException{
    	String comando = "";
    	PreparedStatement ps=null;
    	ResultSet rs=null;
    	ValuesO docencia =null;
    	try{
    	    comando="select * from ENOC.CAT_DOCENCIA where docencia_id=?"; 
    	    ps = conn.prepareStatement(comando);
    	    ps.setString(1,idDocencia);
    		rs=ps.executeQuery();
    		if(rs.next()){
    		    docencia=new ValuesO();
    		    docencia.id=rs.getInt(1);
    		    docencia.nombre=rs.getString(2);
    		    docencia.valor=rs.getDouble(3);
    		}
    	}catch(Exception ex){
    		System.out.println("Error - aca.catalogo.UCADocencias|getDocencia|:"+ex);
    	}finally{
    	    try { ps.close(); } catch (Exception ignore) { }
    	    try { rs.close(); } catch (Exception ignore) { }
    	}
    	return docencia;
    }
    
    public void eliminaDocencia(Connection conn, int idDocencia) throws SQLException{
    	String comando = "";
    	PreparedStatement ps=null;
    	try{
    	    comando="delete from ENOC.CAT_DOCENCIA where docencia_id = ?"; 
    	    ps = conn.prepareStatement(comando);
    	    ps.setInt(1,idDocencia);
    		ps.executeUpdate();
   		
    	}catch(Exception ex){
    		System.out.println("Error - aca.catalogo.UCADocencias|eliminaDocencia|:"+ex);
    	}finally{
    	    try { ps.close(); } catch (Exception ignore) { }
    	}
    }
    
    public void modificaDocencia(Connection conn, ValuesO docencia) throws SQLException{
    	String comando = "";
    	PreparedStatement ps=null;
    	try{
    	    comando="UPDATE ENOC.CAT_DOCENCIA SET NOMBRE=?,VALOR=? WHERE DOCENCIA_ID=?"; 
    	    ps = conn.prepareStatement(comando);
    	    ps.setString(1,docencia.nombre);
    	    ps.setDouble(2,docencia.valor);
    	    ps.setInt(3,docencia.id);
    		ps.executeUpdate();
   		
    	}catch(Exception ex){
    		System.out.println("Error - aca.catalogo.UCADocencias|modificaDocencia|:"+ex);
    	}finally{
    	    try { ps.close(); } catch (Exception ignore) { }
    	}
    }
}