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
public class Aporte {
	public Date fechai;
	public Date fechaf;

    public boolean guardaAporte(Connection conn, ValuesUca aporte) throws SQLException{
        String comando	= "";
    	Statement stmt	= conn.createStatement();
    	boolean grabo	= true;
    	try{
    	    comando="INSERT INTO ENOC.UCA_APORTE(CODIGO_PERSONAL, CARGA_ID, BLOQUE_ID, " + 
    	    		"APORTE_ID, HSEMANAL, HPERIODO, DESCRIPCION, UCAS) " +
    	    	"VALUES('"+aporte.codigoPersonal+"'," +
	    	    "'"+aporte.cargaId+"',"+
	    	    "TO_NUMBER('"+aporte.bloqueId+"'),"+
	    	    "TO_NUMBER('"+aporte.id+"'),"+
	    	    aporte.hSemanal+","+
	    	    aporte.hPeriodo+","+
				"'"+aporte.descripcion+"',"+
				"(select valor from ENOC.cat_aporte where aporte_id="+aporte.id+")*"+aporte.hSemanal+")"; 
    	    if (stmt.executeUpdate(comando)==0) grabo = false;
    	    
    	}catch(Exception ex){
    		System.out.println("Error - aca.ucas.Aporte|guardaAporte|:"+ex);
    		grabo=false;
    	}
    	finally{
    	    try { stmt.close(); } catch (Exception ignore) { }
    	}
    	return grabo;
    }
    
    public ArrayList<ValuesUca> getAportes(Connection conn,String codigoPersonal, String cargaId) throws SQLException{
        ArrayList<ValuesUca> aportes=new ArrayList<ValuesUca>();
        String comando = "";
        ValuesUca aporte=null;
    	PreparedStatement ps=null;
    	ResultSet rs = null;
    	try{
    	    comando="select s.codigo_personal,s.carga_id,s.bloque_id,"+
    	    		"s.aporte_id,s.hsemanal,s.hperiodo,s.descripcion,"+
    	    		"s.ucas, c.nombre, c.valor "+
    	    		"from ENOC.UCA_Aporte s, ENOC.cat_aporte c "+ 
    	    		"where c.aporte_id=s.aporte_id "+
    				"and s.codigo_personal=? "+
    				"and s.carga_id=?";
    	    ps = conn.prepareStatement(comando);
    	    ps.setString(1,codigoPersonal);
    	    ps.setString(2,cargaId);
    	    rs = ps.executeQuery();
    	    while(rs.next()){
    	        aporte=new ValuesUca();
    	        aporte.codigoPersonal=rs.getString(1);
    	        aporte.cargaId=rs.getString(2);
    	        aporte.bloqueId=rs.getInt(3);
    	        aporte.id=rs.getInt(4);
    	        aporte.hSemanal=rs.getDouble(5);
    	        aporte.hPeriodo=rs.getDouble(6);
    	        aporte.descripcion=rs.getString(7);
    	        aporte.ucas=rs.getDouble(8);
    	        aporte.nombre=rs.getString(9);
    	        aporte.setValor(rs.getDouble(10));
    	        aportes.add(aporte);
    	    }
   		
    	}catch(Exception ex){
    		System.out.println("Error - aca.ucas.AEspecial|getAportes|:"+ex);
    	}
    	finally{
    	    try { ps.close(); } catch (Exception ignore) { }
    	}
    	return aportes;
    }

    public ValuesUca getAporte(Connection conn,ValuesUca aporte) throws SQLException{
        String comando = "";
    	PreparedStatement ps=null;
    	ResultSet rs = null;
    	try{
    	    comando="select * from ENOC.UCA_Aporte "+
    	    		"where codigo_personal=? and "+
    	    		"carga_id=? and bloque_id=? "+
    	    		"and aporte_id =?";
    	    ps = conn.prepareStatement(comando);
    	    ps.setString(1,aporte.codigoPersonal);
    	    ps.setString(2,aporte.cargaId);
    	    ps.setInt(3,aporte.bloqueId);
    	    ps.setInt(4,aporte.id);
    	    rs = ps.executeQuery();
    	    while(rs.next()){
    	        aporte=new ValuesUca();
    	        aporte.codigoPersonal=rs.getString(1);
    	        aporte.cargaId=rs.getString(2);
    	        aporte.bloqueId=rs.getInt(3);
    	        aporte.id=rs.getInt(4);
    	        aporte.hSemanal=rs.getDouble(5);
    	        aporte.hPeriodo=rs.getDouble(6);
    	        aporte.descripcion=rs.getString(7);
    	        aporte.ucas=rs.getDouble(8);
    	    }
   		
    	}catch(Exception ex){
    		System.out.println("Error - aca.ucas.AEspecial|getAporte|:"+ex);
    	}
    	finally{
    	    try { ps.close(); } catch (Exception ignore) { }
    	}
    	return aporte;
    }

    public void modificaAporte(Connection conn, ValuesUca aporte) throws SQLException{

        String comando = "";
    	PreparedStatement ps=null;
    	try{
    	    comando="update ENOC.UCA_Aporte set " +
    	    		"hsemanal=?,hperiodo=?,descripcion=?,ucas=TO_NUMBER((select valor from ENOC.cat_aporte where aporte_id="+aporte.id+")*"+aporte.hSemanal+")"+ 
    	    		" where codigo_personal=? and carga_id=? " +
    	    		"and bloque_id=? and aporte_id=?";
    	    ps = conn.prepareStatement(comando);
    	    ps.setDouble(1,aporte.hSemanal);
    	    ps.setDouble(2,aporte.hPeriodo);
    	    ps.setString(3,aporte.descripcion);
    	    ps.setString(4,aporte.codigoPersonal);
    	    ps.setString(5,aporte.cargaId);
    	    ps.setInt(6,aporte.bloqueId);
    	    ps.setInt(7,aporte.id);
    	    ps.executeUpdate();
   		
    	}catch(Exception ex){
    		System.out.println("Error - aca.ucas.AEspecial|modificaAporte|:"+ex);
    	}
    	finally{
    	    try { ps.close(); } catch (Exception ignore) { }
    	}
    }

    public void eliminaAporte(Connection conn, ValuesUca aporte) throws SQLException{

        String comando = "";
    	PreparedStatement ps=null;
    	try{
    	    comando="delete from ENOC.UCA_Aporte " +
    	    		"where codigo_personal=? and carga_id=? " +
    	    		"and bloque_id=? and aporte_id=?";
    	    ps = conn.prepareStatement(comando);
    	    ps.setString(1,aporte.codigoPersonal);
    	    ps.setString(2,aporte.cargaId);
    	    ps.setInt(3,aporte.bloqueId);
    	    ps.setInt(4,aporte.id);
    	    ps.executeUpdate();
   		
    	}catch(Exception ex){
    		System.out.println("Error - aca.ucas.AEspecial|eliminaAporte|:"+ex);
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