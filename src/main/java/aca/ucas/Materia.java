/*
 * Created on 25/04/2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package aca.ucas;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author Pedro
 *
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Materia {

    public void modificaMateria(Connection conn, float valor,int tipo) throws SQLException{

        String comando = "";
    	PreparedStatement ps=null;
    	try{
    	    comando="update ENOC.UCA_MATERIA set " + 
    	    		"valor=? where tipo=?";
    	    ps = conn.prepareStatement(comando);
    	    ps.setFloat(1,valor);
    	    ps.setInt(2,tipo);
    	    ps.executeUpdate();
   		
    	}catch(Exception ex){
    		System.out.println("Error - aca.ucas.Materia|guardaMateria|:"+ex);
    	}
    	finally{
    	    try { ps.close(); } catch (Exception ignore) { }
    	}
    }
    
    public float getValor(Connection conn,int tipo) throws SQLException{
        String comando = "";
        float valor=0;
    	PreparedStatement ps=null;
    	ResultSet rs = null;
    	try{
    	    comando="select valor from ENOC.UCA_MATERIA where tipo=? "+ 
    	    		"order by tipo asc";
    	    ps = conn.prepareStatement(comando);
    	    ps.setInt(1,tipo);
    	    rs = ps.executeQuery();
    	    while(rs.next()){
    	       valor=rs.getFloat(1);
    	    }
   		
    	}catch(Exception ex){
    		System.out.println("Error - aca.ucas.Materia|getValor|:"+ex);
    	}
    	finally{
    	    try { ps.close(); } catch (Exception ignore) { }
    	}
    	return valor;
    }

    public ArrayList<MateriaVO> getValores(Connection conn) throws SQLException{
        String comando = "";
        MateriaVO materia;
        ArrayList<MateriaVO> valores=new ArrayList<MateriaVO>();
    	PreparedStatement ps=null;
    	ResultSet rs = null;
    	try{
    	    comando="select * from ENOC.UCA_MATERIA "+ 
    	    		"order by tipo asc";
    	    ps = conn.prepareStatement(comando);
    	    rs = ps.executeQuery();
    	    while(rs.next()){
        	    materia=new MateriaVO();
        	    materia.setTipo(rs.getInt(1));
        	    materia.setDescripcion(rs.getString(2));
        	    materia.setValor(rs.getFloat(3));        	    
    	        valores.add(materia);
    	    }
   		
    	}catch(Exception ex){
    		System.out.println("Error - aca.ucas.Materia|getValores|:"+ex);
    	}
    	finally{
    	    try { ps.close(); } catch (Exception ignore) { }
    	}
    	return valores;
    }    
}