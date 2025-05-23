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
public class Docencia {
	public Date fechai;
	public Date fechaf;

    public boolean guardaDocencia(Connection conn, ValuesUca docencia) throws SQLException{
        String comando	= "";    	
    	Statement stmt	= conn.createStatement();
    	boolean grabo	= true;
    	try{    		
    	    comando="INSERT INTO ENOC.UCA_DOCENCIA(CODIGO_PERSONAL,CARGA_ID,BLOQUE_ID," + 
    	    		"DOCENCIA_ID,HSEMANAL,HPERIODO,DESCRIPCION,UCAS)" +
    	    		" VALUES ('"+docencia.codigoPersonal+"',"+
					"'"+docencia.cargaId+"',"+
					"TO_NUMBER('"+docencia.bloqueId+"'),"+
					"TO_NUMBER('"+docencia.id+"'),"+
					docencia.hSemanal+","+
					docencia.hPeriodo+","+
					"'"+docencia.descripcion+"',"+
					"(select valor from ENOC.cat_docencia where docencia_id="+docencia.id+")*"+docencia.hSemanal+")"; 
    		if (stmt.executeUpdate(comando)==0) grabo = false;    		
    	}catch(Exception ex){
    		System.out.println("Error - aca.ucas.Docencia|guardaDocencia|:"+ex);
    		grabo=false;
    	}
    	finally{
    	    try { stmt.close(); } catch (Exception ignore) { }
    	}
    	return grabo;
    }
    
    public boolean guardaPvezVale(Connection conn,String materias, String pvez, String vale) throws SQLException{
        String comando = "";
        String[] vez = pvez.split(",");
        String[] val = vale.split(",");
        String[] mats = materias.split(",");
    	PreparedStatement ps=null;
    	boolean grabo=true;
    	try{
    	    comando="UPDATE ENOC.CARGA_GRUPO SET "+ 
			"PRIMERAVEZ = ?, "+
			"VALEUCAS = ? "+
			"WHERE CURSO_CARGA_ID = ?";
    	    ps = conn.prepareStatement(comando);
    	    for(int i=0;i<vez.length;i++){
    	        ps.setString(1,vez[i]);
    	        ps.setString(2,val[i]);
    	        ps.setString(3,mats[i]);
    	        ps.executeUpdate();
    	        //System.out.println(mats[i]+","+vez[i]+","+val[i]);
    	    }
    	}catch(Exception ex){
    		System.out.println("Error - aca.ucas.Docencia|guardaPvezVale|:"+ex);
    		grabo=false;
    	}
    	finally{
    	    try { ps.close(); } catch (Exception ignore) { }
    	}
    	return grabo;
    }

    public ArrayList<ValuesUca> getDocencias(Connection conn,String codigoPersonal,String cargaId) throws SQLException{
        ArrayList<ValuesUca> docencias=new ArrayList<ValuesUca>();
        String comando = "";
        ValuesUca docencia=null;
    	PreparedStatement ps=null;
    	ResultSet rs = null;
    	try{
    	    comando="select s.codigo_personal,s.carga_id,s.bloque_id,"+
    	    		"s.docencia_id,s.hsemanal,s.hperiodo,s.descripcion,"+
    	    		"s.ucas, c.nombre, c.valor "+
    	    		"from ENOC.UCA_DOCENCIA s, ENOC.cat_docencia c "+ 
    	    		"where c.docencia_id=s.docencia_id "+
    				"and s.codigo_personal=? " +
    	    		"and s.carga_id=?";
    	    ps = conn.prepareStatement(comando);
    	    ps.setString(1,codigoPersonal);
    	    ps.setString(2,cargaId);
    	    rs = ps.executeQuery();
    	    while(rs.next()){
    	        docencia=new ValuesUca();
    	        docencia.codigoPersonal=rs.getString(1);
    	        docencia.cargaId=rs.getString(2);
    	        docencia.bloqueId=rs.getInt(3);
    	        docencia.id=rs.getInt(4);
    	        docencia.hSemanal=rs.getDouble(5);
    	        docencia.hPeriodo=rs.getDouble(6);
    	        docencia.descripcion=rs.getString(7);
    	        docencia.ucas=rs.getDouble(8);
    	        docencia.nombre=rs.getString(9);
    	        docencia.setValor(rs.getDouble(10));
    	        docencias.add(docencia);
    	    }
   		
    	}catch(Exception ex){
    		System.out.println("Error - aca.ucas.Docencia|getDocencias|:"+ex);
    	}
    	finally{
    	    try { ps.close(); } catch (Exception ignore) { }
    	}
    	return docencias;
    }

    public ValuesUca getDocencia(Connection conn,ValuesUca docencia) throws SQLException{
        String comando = "";
    	PreparedStatement ps=null;
    	ResultSet rs = null;
    	try{
    	    comando="select * from ENOC.UCA_DOCENCIA "+ 
    	    		"where codigo_personal=? and "+
    	    		"carga_id=? and bloque_id=? "+
    	    		"and docencia_id =?";
    	    ps = conn.prepareStatement(comando);
    	    ps.setString(1,docencia.codigoPersonal);
    	    ps.setString(2,docencia.cargaId);
    	    ps.setInt(3,docencia.bloqueId);
    	    ps.setInt(4,docencia.id);
    	    rs = ps.executeQuery();
    	    while(rs.next()){
    	        docencia=new ValuesUca();
    	        docencia.codigoPersonal=rs.getString(1);
    	        docencia.cargaId=rs.getString(2);
    	        docencia.bloqueId=rs.getInt(3);
    	        docencia.id=rs.getInt(4);
    	        docencia.hSemanal=rs.getDouble(5);
    	        docencia.hPeriodo=rs.getDouble(6);
    	        docencia.descripcion=rs.getString(7);
    	        docencia.ucas=rs.getDouble(8);
    	    }
   		
    	}catch(Exception ex){
    		System.out.println("Error - aca.ucas.Docencia|getDocencia|:"+ex);
    	}
    	finally{
    	    try { ps.close(); } catch (Exception ignore) { }
    	}
    	return docencia;
    }
    
    public String esPrimeraVez(Connection conn,String cursoCId) throws SQLException{
        String comando = "";
    	PreparedStatement ps=null;
    	ResultSet rs = null;
    	String vez="N";
    	try{
    	    comando="select primeravez from ENOC.carga_grupo "+ 
    	    		"where curso_carga_id=? ";
    	    ps = conn.prepareStatement(comando);
    	    ps.setString(1,cursoCId);
    	    rs = ps.executeQuery();
    	    if(rs.next()){
    	        vez=rs.getString(1);
    	        if(vez==null)vez="N";
    	    }
   		
    	}catch(Exception ex){
    		System.out.println("Error - aca.ucas.Docencia|esPriemeraVez|:"+ex);
    	}
    	finally{
    	    try { ps.close(); } catch (Exception ignore) { }
    	}
    	return vez;
    }

    public boolean valeParaUCA(Connection conn,String cursoCId) throws SQLException{
        String comando = "";
    	PreparedStatement ps=null;
    	ResultSet rs = null;
    	boolean ok=false;
    	try{
    	    comando="select curso_carga_id from ENOC.carga_grupo "+ 
    	    		"where curso_carga_id=? and valeucas=?";
    	    ps = conn.prepareStatement(comando);
    	    ps.setString(1,cursoCId);
    	    ps.setString(2,"S");
    	    rs = ps.executeQuery();
    	    if(rs.next()){
    	        ok=true;
    	    }
   		
    	}catch(Exception ex){
    		System.out.println("Error - aca.ucas.Docencia|valeParaUCA|:"+ex);
    	}
    	finally{
    	    try { ps.close(); } catch (Exception ignore) { }
    	}
    	return ok;
    }

    public void modificaDocencia(Connection conn, ValuesUca docencia) throws SQLException{

        String comando = "";
    	PreparedStatement ps=null;
    	try{
    	    comando="update ENOC.UCA_DOCENCIA set " + 
    	    		"hsemanal=?,hperiodo=?,descripcion=?,ucas=TO_NUMBER((select valor from ENOC.cat_docencia where docencia_id="+docencia.id+")*"+docencia.hSemanal+")"+ 
    	    		" where codigo_personal=? and carga_id=? " +
    	    		"and bloque_id=? and docencia_id=?";
    	    ps = conn.prepareStatement(comando);
    	    ps.setDouble(1,docencia.hSemanal);
    	    ps.setDouble(2,docencia.hPeriodo);
    	    ps.setString(3,docencia.descripcion);
    	    ps.setString(4,docencia.codigoPersonal);
    	    ps.setString(5,docencia.cargaId);
    	    ps.setInt(6,docencia.bloqueId);
    	    ps.setInt(7,docencia.id);
    	    ps.executeUpdate();
   		
    	}catch(Exception ex){
    		System.out.println("Error - aca.ucas.Docencia|modificaDocencia|:"+ex);
    	}
    	finally{
    	    try { ps.close(); } catch (Exception ignore) { }
    	}
    }

    public void eliminaDocencia(Connection conn, ValuesUca docencia) throws SQLException{

        String comando = "";
    	PreparedStatement ps=null;
    	try{
    	    comando="delete from ENOC.UCA_DOCENCIA " + 
    	    		"where codigo_personal=? and carga_id=? " +
    	    		"and bloque_id=? and docencia_id=?";
    	    ps = conn.prepareStatement(comando);
    	    ps.setString(1,docencia.codigoPersonal);
    	    ps.setString(2,docencia.cargaId);
    	    ps.setInt(3,docencia.bloqueId);
    	    ps.setInt(4,docencia.id);
    	    ps.executeUpdate();
   		
    	}catch(Exception ex){
    		System.out.println("Error - aca.ucas.Docencia|eliminaDocencia|:"+ex);
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
    		System.out.println("Error - aca.ucas.Docencia|getFechasCarga|:"+ex);
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
    		System.out.println("Error - aca.ucas.Docencia|getBloque|:"+ex);
    	}
    	finally{
    	    try { ps.close(); } catch (Exception ignore) { }
    	}
    	return bloque;
    }
}