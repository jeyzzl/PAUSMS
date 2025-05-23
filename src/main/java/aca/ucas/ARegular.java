/*
 * Created on 13/02/2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package aca.ucas;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

/**
 * @author Pedro
 *
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ARegular {
	public Date fechai;
	public Date fechaf;

    public boolean guardaARegular(Connection conn, ValuesUca aregular) throws SQLException{
        String comando	= "";
    	Statement stmt	= conn.createStatement();
    	boolean grabo	= true;
    	try{
    	    comando="INSERT INTO ENOC.UCA_AREGULARES(CODIGO_PERSONAL, CARGA_ID, BLOQUE_ID, " + 
    	    		"AREGULARES_ID, HSEMANAL, HPERIODO, DESCRIPCION, UCAS) "+
    	    	"VALUES('"+aregular.codigoPersonal+"'," +
		    	"'"+aregular.cargaId+"',"+
		    	"TO_NUMBER('"+aregular.bloqueId+"'),"+
		    	"TO_NUMBER('"+aregular.id+"'),"+
		    	aregular.hSemanal+","+
		    	aregular.hPeriodo+","+
				"'"+aregular.descripcion+"',"+
				"(select valor from ENOC.cat_aregulares where aregulares_id="+aregular.id+")*"+aregular.hSemanal+")"; 
    	    if (stmt.executeUpdate(comando)==0) grabo=false;
    	}catch(Exception ex){
    		System.out.println("Error - aca.ucas.ARegular|guardaARegular|:"+ex);
    		grabo=false;
    	}
    	finally{
    	    try { stmt.close(); } catch (Exception ignore) { }
    	}
    	return grabo;
    }
    
    public ArrayList<ValuesUca> getARegulars(Connection conn,String codigoPersonal, String cargaId) throws SQLException{
        ArrayList<ValuesUca> aregulars=new ArrayList<ValuesUca>();
        String comando = "";
        ValuesUca aregular=null;
    	PreparedStatement ps=null;
    	ResultSet rs = null;
    	try{
    	    comando="select s.codigo_personal,s.carga_id,s.bloque_id,"+
    	    		"s.aregulares_id,s.hsemanal,s.hperiodo,s.descripcion,"+
    	    		"s.ucas, c.nombre, c.valor "+
    	    		"from ENOC.UCA_ARegulares s, ENOC.cat_aregulares c "+ 
    	    		"where c.aregulares_id=s.aregulares_id "+
    				"and s.codigo_personal=? "+
    	    		"and s.carga_id=?";
    	    ps = conn.prepareStatement(comando);
    	    ps.setString(1,codigoPersonal);
    	    ps.setString(2,cargaId);
    	    rs = ps.executeQuery();
    	    while(rs.next()){
    	        aregular=new ValuesUca();
    	        aregular.codigoPersonal=rs.getString(1);
    	        aregular.cargaId=rs.getString(2);
    	        aregular.bloqueId=rs.getInt(3);
    	        aregular.id=rs.getInt(4);
    	        aregular.hSemanal=rs.getDouble(5);
    	        aregular.hPeriodo=rs.getDouble(6);
    	        aregular.descripcion=rs.getString(7);
    	        aregular.ucas=rs.getDouble(8);
    	        aregular.nombre=rs.getString(9);
    	        aregular.setValor(rs.getDouble(10));
    	        aregulars.add(aregular);
    	    }
   		
    	}catch(Exception ex){
    		System.out.println("Error - aca.ucas.ARegular|getARegulars|:"+ex);
    	}
    	finally{
    	    try { ps.close(); } catch (Exception ignore) { }
    	}
    	return aregulars;
    }

    public ValuesUca getARegular(Connection conn,ValuesUca aregular) throws SQLException{
        String comando = "";
    	PreparedStatement ps=null;
    	ResultSet rs = null;
    	try{
    	    comando="select * from ENOC.UCA_ARegulares "+
    	    		"where codigo_personal=? and "+
    	    		"carga_id=? and bloque_id=? "+
    	    		"and aregulares_id =?";
    	    ps = conn.prepareStatement(comando);
    	    ps.setString(1,aregular.codigoPersonal);
    	    ps.setString(2,aregular.cargaId);
    	    ps.setInt(3,aregular.bloqueId);
    	    ps.setInt(4,aregular.id);
    	    rs = ps.executeQuery();
    	    while(rs.next()){
    	        aregular=new ValuesUca();
    	        aregular.codigoPersonal=rs.getString(1);
    	        aregular.cargaId=rs.getString(2);
    	        aregular.bloqueId=rs.getInt(3);
    	        aregular.id=rs.getInt(4);
    	        aregular.hSemanal=rs.getDouble(5);
    	        aregular.hPeriodo=rs.getDouble(6);
    	        aregular.descripcion=rs.getString(7);
    	        aregular.ucas=rs.getDouble(8);
    	    }
   		
    	}catch(Exception ex){
    		System.out.println("Error - aca.ucas.ARegular|getARegular|:"+ex);
    	}
    	finally{
    	    try { ps.close(); } catch (Exception ignore) { }
    	}
    	return aregular;
    }

    public void modificaARegular(Connection conn, ValuesUca aregular) throws SQLException{

        String comando = "";
    	PreparedStatement ps=null;
    	try{
    	    comando="update ENOC.UCA_ARegulares set " +
    	    		"hsemanal=?,hperiodo=?,descripcion=?,ucas=TO_NUMBER((select valor from ENOC.cat_aregulares where aregulares_id="+aregular.id+")*"+aregular.hSemanal+")"+ 
    	    		" where codigo_personal=? and carga_id=? " +
    	    		"and bloque_id=? and aregulares_id=?";
    	    ps = conn.prepareStatement(comando);
    	    ps.setDouble(1,aregular.hSemanal);
    	    ps.setDouble(2,aregular.hPeriodo);
    	    ps.setString(3,aregular.descripcion);
    	    ps.setString(4,aregular.codigoPersonal);
    	    ps.setString(5,aregular.cargaId);
    	    ps.setInt(6,aregular.bloqueId);
    	    ps.setInt(7,aregular.id);
    	    ps.executeUpdate();
   		
    	}catch(Exception ex){
    		System.out.println("Error - aca.ucas.ARegular|modificaARegular|:"+ex);
    	}
    	finally{
    	    try { ps.close(); } catch (Exception ignore) { }
    	}
    }

    public void eliminaARegular(Connection conn, ValuesUca aregular) throws SQLException{

        String comando = "";
    	PreparedStatement ps=null;
    	try{
    	    comando="delete from ENOC.UCA_ARegulares " +
    	    		"where codigo_personal=? and carga_id=? " +
    	    		"and bloque_id=? and aregulares_id=?";
    	    ps = conn.prepareStatement(comando);
    	    ps.setString(1,aregular.codigoPersonal);
    	    ps.setString(2,aregular.cargaId);
    	    ps.setInt(3,aregular.bloqueId);
    	    ps.setInt(4,aregular.id);
    	    ps.executeUpdate();
   		
    	}catch(Exception ex){
    		System.out.println("Error - aca.ucas.ARegular|eliminaARegular|:"+ex);
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
    		System.out.println("Error - aca.ucas.ARegular|getFechasCarga|:"+ex);
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
    		System.out.println("Error - aca.ucas.ARegular|getBloque|:"+ex);
    	}
    	finally{
    	    try { ps.close(); } catch (Exception ignore) { }
    	}
    	return bloque;
    }
}