/*
 * Created on 26/08/2005
 *
*/
package aca.archivo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author Pedro
 *
*/
public class Reportes {
    
    public int getNumeroDocumentosxAlumno(Connection conn,String matricula) throws SQLException{
        PreparedStatement ps=null;
    	ResultSet rs = null;
    	String comando="";
    	
    	int num=0;
    	try{
    	    comando="select distinct (select count(matricula) from arch_docalum "+ 
    	            "where matricula=ad.matricula) as numdoc from arch_docalum ad where matricula = ?"; 
            ps = conn.prepareStatement(comando);
            ps.setString(1,matricula);
    	    rs = ps.executeQuery();
    	    if(rs.next()) num = rs.getInt(1);
    	    
    	}catch(Exception ex){
    		System.out.println("Error - aca.archivo.Reportes|getNumeroDocumentosxAlumno|:"+ex);
    	}
    	finally{
    	    try { ps.close(); } catch (Exception ignore) { }
    	    try { rs.close(); } catch (Exception ignore) { }
    	}
    	return num;
    }

    public ArrayList<Object> getNumeroDocumentosDigitalizadosxAlumnos(Connection conn) throws SQLException{
        PreparedStatement ps=null;
    	ResultSet rs = null;
    	String comando="";
    	ArrayList<Object> lis=new ArrayList<Object>();

    	try{
    	    comando="select distinct ad.matricula,(select count(matricula) from arch_docalum " + 
    	    		"where matricula=ad.matricula) as numdoc from arch_docalum as ad";
    	    ps = conn.prepareStatement(comando);
    	    rs = ps.executeQuery();
    	    while(rs.next()){
    	        lis.add(rs.getString(1));
    	        lis.add(new Integer(rs.getInt(2)));
    	    }
    	}catch(Exception ex){
    		System.out.println("Error - aca.archivo.Reportes|getNumeroDocumentosDigitalizadosxAlumnos|:"+ex);
    	}
    	finally{
    	    try { ps.close(); } catch (Exception ignore) { }
    	    try { rs.close(); } catch (Exception ignore) { }
    	}
    	return lis;
    }


}