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
public class AEspecial {
	public Date fechai;
	public Date fechaf;

    public boolean guardaAEspecial(Connection conn, ValuesUca aespecial) throws SQLException{
        String comando = "";
    	Statement stmt = conn.createStatement();    	
    	boolean grabo=true;
    	try{
    	    comando="INSERT INTO ENOC.UCA_AESPECIALES(CODIGO_PERSONAL, CARGA_ID, BLOQUE_ID, " + 
    	    		"AESPECIALES_ID, HSEMANAL, HPERIODO, DESCRIPCION, UCAS) " +
    	    	"VALUES('"+aespecial.codigoPersonal+"'," +
			    "'"+aespecial.cargaId+"',"+
			    "TO_NUMBER('"+aespecial.bloqueId+"'),"+
			    "TO_NUMBER('"+aespecial.id+"'),"+
			    aespecial.hSemanal+","+
			    aespecial.hPeriodo+","+
				"'"+aespecial.descripcion+"',"+
				"(select valor from ENOC.cat_aespeciales where aespeciales_id="+aespecial.id+")*"+aespecial.hSemanal+")"; 
    	    if (stmt.executeUpdate(comando)==0) grabo = false;
    	    
    	}catch(Exception ex){
    		System.out.println("Error - aca.ucas.AEspecial|guardaEspecial|:"+ex);
    		grabo=false;
    	}
    	finally{
    	    try { stmt.close(); } catch (Exception ignore) { }
    	}
    	return grabo;
    }
    
    public ArrayList<ValuesUca> getAEspecials(Connection conn,String codigoPersonal,String cargaId) throws SQLException{
        ArrayList<ValuesUca> aespecials=new ArrayList<ValuesUca>();
        String comando = "";
        ValuesUca aespecial=null;
    	PreparedStatement ps=null;
    	ResultSet rs = null;
    	try{
    	    comando="select s.codigo_personal,s.carga_id,s.bloque_id,"+
    	    		"s.aespeciales_id,s.hsemanal,s.hperiodo,s.descripcion,"+
    	    		"s.ucas, c.nombre, c.valor "+
    	    		"from ENOC.UCA_AEspeciales s, ENOC.cat_aespeciales c "+ 
    	    		"where c.aespeciales_id=s.aespeciales_id "+
    				"and s.codigo_personal=? " +
    	    		"and s.carga_id=?";
    	    ps = conn.prepareStatement(comando);
    	    ps.setString(1,codigoPersonal);
    	    ps.setString(2,cargaId);
    	    rs = ps.executeQuery();
    	    while(rs.next()){
    	        aespecial=new ValuesUca();
    	        aespecial.codigoPersonal=rs.getString(1);
    	        aespecial.cargaId=rs.getString(2);
    	        aespecial.bloqueId=rs.getInt(3);
    	        aespecial.id=rs.getInt(4);
    	        aespecial.hSemanal=rs.getDouble(5);
    	        aespecial.hPeriodo=rs.getDouble(6);
    	        aespecial.descripcion=rs.getString(7);
    	        aespecial.ucas=rs.getDouble(8);
    	        aespecial.nombre=rs.getString(9);
    	        aespecial.setValor(rs.getDouble(10));
    	        aespecials.add(aespecial);
    	    }
   		
    	}catch(Exception ex){
    		System.out.println("Error - aca.ucas.AEspecial|getAEspecials|:"+ex);
    	}
    	finally{
    	    try { ps.close(); } catch (Exception ignore) { }
    	}
    	return aespecials;
    }

    public ValuesUca getAEspecial(Connection conn,ValuesUca aespecial) throws SQLException{
        String comando = "";
    	PreparedStatement ps=null;
    	ResultSet rs = null;
    	try{
    	    comando="select * from ENOC.UCA_AEspeciales "+
    	    		"where codigo_personal=? and "+
    	    		"carga_id=? and bloque_id=? "+
    	    		"and aespeciales_id =?";
    	    ps = conn.prepareStatement(comando);
    	    ps.setString(1,aespecial.codigoPersonal);
    	    ps.setString(2,aespecial.cargaId);
    	    ps.setInt(3,aespecial.bloqueId);
    	    ps.setInt(4,aespecial.id);
    	    rs = ps.executeQuery();
    	    while(rs.next()){
    	        aespecial=new ValuesUca();
    	        aespecial.codigoPersonal=rs.getString(1);
    	        aespecial.cargaId=rs.getString(2);
    	        aespecial.bloqueId=rs.getInt(3);
    	        aespecial.id=rs.getInt(4);
    	        aespecial.hSemanal=rs.getDouble(5);
    	        aespecial.hPeriodo=rs.getDouble(6);
    	        aespecial.descripcion=rs.getString(7);
    	        aespecial.ucas=rs.getDouble(8);
    	    }
   		
    	}catch(Exception ex){
    		System.out.println("Error - aca.ucas.AEspecial|getAEspecial|:"+ex);
    	}
    	finally{
    	    try { ps.close(); } catch (Exception ignore) { }
    	}
    	return aespecial;
    }

    public void modificaAEspecial(Connection conn, ValuesUca aespecial) throws SQLException{

        String comando = "";
    	PreparedStatement ps=null;
    	try{
    	    comando="update ENOC.UCA_AEspeciales set " +
    	    		"hsemanal=?,hperiodo=?,descripcion=?,ucas=TO_NUMBER((select valor from ENOC.cat_aespeciales where aespeciales_id="+aespecial.id+")*"+aespecial.hSemanal+")"+ 
    	    		" where codigo_personal=? and carga_id=? " +
    	    		"and bloque_id=? and aespeciales_id=?";
    	    ps = conn.prepareStatement(comando);
    	    ps.setDouble(1,aespecial.hSemanal);
    	    ps.setDouble(2,aespecial.hPeriodo);
    	    ps.setString(3,aespecial.descripcion);
    	    ps.setString(4,aespecial.codigoPersonal);
    	    ps.setString(5,aespecial.cargaId);
    	    ps.setInt(6,aespecial.bloqueId);
    	    ps.setInt(7,aespecial.id);
    	    ps.executeUpdate();
   		
    	}catch(Exception ex){
    		System.out.println("Error - aca.ucas.AEspecial|modificaAEspecial|:"+ex);
    	}
    	finally{
    	    try { ps.close(); } catch (Exception ignore) { }
    	}
    }

    public void eliminaAEspecial(Connection conn, ValuesUca aespecial) throws SQLException{

        String comando = "";
    	PreparedStatement ps=null;
    	try{
    	    comando="delete from ENOC.UCA_AEspeciales " +
    	    		"where codigo_personal=? and carga_id=? " +
    	    		"and bloque_id=? and aespeciales_id=?";
    	    ps = conn.prepareStatement(comando);
    	    ps.setString(1,aespecial.codigoPersonal);
    	    ps.setString(2,aespecial.cargaId);
    	    ps.setInt(3,aespecial.bloqueId);
    	    ps.setInt(4,aespecial.id);
    	    ps.executeUpdate();
   		
    	}catch(Exception ex){
    		System.out.println("Error - aca.ucas.AEspecial|eliminaAEspecial|:"+ex);
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
    	    comando="select f_inicio,f_final from ENOC.carga where carga_id=? "; 
    	    ps = conn.prepareStatement(comando);
    	    ps.setString(1,cargaId);
    	    rs=ps.executeQuery();
    	    if(rs.next()){
    	    	fechai=rs.getDate(1);
    	    	fechaf=rs.getDate(2);
    	    }
   		
    	}catch(Exception ex){
    		System.out.println("Error - aca.ucas.AEspecial|getFechasCarga|:"+ex);
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
    	    comando="select bloque_id from ENOC.carga_bloque where carga_id=? "; 
    	    ps = conn.prepareStatement(comando);
    	    ps.setString(1,cargaId);
    	    rs=ps.executeQuery();
    	    if(rs.next()){
    	    	bloque=rs.getInt(1);
    	    }
   		
    	}catch(Exception ex){
    		System.out.println("Error - aca.ucas.AEspecial|getBloque|:"+ex);
    	}
    	finally{
    	    try { ps.close(); } catch (Exception ignore) { }
    	}
    	return bloque;
    }
}