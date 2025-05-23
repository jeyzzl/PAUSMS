/*
 * Created on 13/02/2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package aca.tit;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Pedro
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class PagoAlumno {
    java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
    public boolean guardaPago(Connection conn, DocAlumVO pago) throws SQLException{
        String comando = "";
    	PreparedStatement ps=null;
    	boolean grabo=true;
    	try{
    	    comando="insert into ENOC.tit_alumpago values(?,?,?,to_date(?,'dd/mm/yyyy'),?)"; 
    	    ps = conn.prepareStatement(comando);
    	    ps.setString(1,pago.getTituloId());
    	    ps.setInt(2,pago.getDocumentoId());
    	    ps.setDouble(3,pago.getCantidad());
    	    ps.setString(4,pago.getFecha());
    	    ps.setString(5,pago.getComentario());
    		ps.executeUpdate();
    	}catch(Exception ex){
    		System.out.println("Error - aca.tit.PagoAlumno|guardaPago|:"+ex);
    		grabo=false;
    	}
    	finally{
    	    try { ps.close(); } catch (Exception ignore) { }
    	}
    	return grabo;
    }
    
    public DocAlumVO getPago(Connection conn,String tituloId) throws SQLException{
        String comando = "";
        DocAlumVO pago=null;
    	PreparedStatement ps=null;
    	ResultSet rs = null;
    	try{
    	    comando="select * from ENOC.tit_alumpago "+ 
    	    		"where titulo_id=?";
    	    ps = conn.prepareStatement(comando);
    	    ps.setString(1,tituloId);
    	    rs = ps.executeQuery();
    	    if(rs.next()){
    	        pago=new DocAlumVO();
    	        pago.setTituloId(rs.getString(1));
    	        pago.setDocumentoId(rs.getInt(2));
    	        pago.setCantidad(rs.getDouble(3));
    	        pago.setFecha(sdf.format(rs.getDate(4)));
    	        pago.setComentario(rs.getString(5));
    	    }
   		
    	}catch(Exception ex){
    		System.out.println("Error - aca.tit.PagoAlumno|docAlumVO getPago|:"+ex);
    	}
    	finally{
    	    try { ps.close(); } catch (Exception ignore) { }
    	}
    	return pago;
    }

    public void modificaPago(Connection conn, DocAlumVO pago) throws SQLException{

        String comando = "";
    	PreparedStatement ps=null;
    	try{
    	    comando="update ENOC.tit_alumpago set " + 
			"pago_id=?, "+
			"cantidad=?, " +
			"fecha=to_date(?,'dd/mm/yyyy'), " +
			"comentario=? " +
    	    	"where titulo_id=? ";
	    	ps = conn.prepareStatement(comando);
    	    ps.setInt(1,pago.getDocumentoId());
    	    ps.setDouble(2,pago.getCantidad());
    	    ps.setString(3,pago.getFecha());
    	    ps.setString(4,pago.getComentario());
    	    ps.setString(5,pago.getTituloId());
    	    ps.executeUpdate();
   		
    	}catch(Exception ex){
    		System.out.println("Error - aca.tit.PagoAlumno|modificaPago|:"+ex);
    	}
    	finally{
    	    try { ps.close(); } catch (Exception ignore) { }
    	}
    }

    public void eliminaPago(Connection conn, String tituloId) throws SQLException{

        String comando = "";
    	PreparedStatement ps=null;
    	try{
    	    comando="delete from ENOC.tit_alumpago " + 
    	    		"where titulo_id=?";
    	    ps = conn.prepareStatement(comando);
    	    ps.setString(1,tituloId);
        	System.out.println(comando);
    	    ps.executeUpdate();
    	}catch(Exception ex){
    		System.out.println("Error - aca.tit.PagoAlumno|EliminaPago|:"+ex);
    	}
    	finally{
    	    try { ps.close(); } catch (Exception ignore) { }
    	}
    }
    
    public String getNombrePago(Connection conn, int id) throws SQLException{

        String comando = "";
    	PreparedStatement ps=null;
    	ResultSet rs =null;
    	String nombre="";
    	try{
    	    comando="select nombre_pago from ENOC.tit_pago " + 
    	    		"where pago_id=?";
    	    ps = conn.prepareStatement(comando);
    	    ps.setInt(1,id);
    	    rs=ps.executeQuery();
    	    if(rs.next())nombre=rs.getString(1);
   		
    	}catch(Exception ex){
    		System.out.println("Error - aca.tit.PagoAlumno|getNombrePago|:"+ex);
    	}
    	finally{
    	    try { ps.close(); } catch (Exception ignore) { }
    	}
    	return nombre;
    }
    
}