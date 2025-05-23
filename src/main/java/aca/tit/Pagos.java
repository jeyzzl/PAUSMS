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
import java.util.ArrayList;

/**
 * @author Pedro
 *
 *
 */
public class Pagos {

    public boolean guardaPago(Connection conn, RequisitoVO pago) throws SQLException{
        String comando = "";
    	PreparedStatement ps=null;
    	boolean grabo=true;
    	try{
    	    comando="INSERT INTO ENOC.TIT_PAGO VALUES ((SELECT COALESCE(MAX(PAGO_ID),0)+1 FROM ENOC.TIT_PAGO),?)"; 
    	    ps = conn.prepareStatement(comando);
    	    ps.setString(1,pago.getNombre());
    		ps.executeUpdate();
    	}catch(Exception ex){
    		System.out.println("Error - aca.tit.Pagos|guardaPagos|:"+ex);
    		grabo=false;
    	}
    	finally{
    	    try { ps.close(); } catch (Exception ignore) { }
    	}
    	return grabo;
    }
    
    public RequisitoVO getPago(Connection conn,int id) throws SQLException{
        String comando = "";
        RequisitoVO documento=null;
    	PreparedStatement ps=null;
    	ResultSet rs = null;
    	try{
    	    comando="SELECT * FROM ENOC.TIT_PAGO WHERE PAGO_ID = ?"; 
    	    ps = conn.prepareStatement(comando);
    	    ps.setInt(1,id);
    	    rs = ps.executeQuery();
    	    while(rs.next()){
    	        documento=new RequisitoVO();
    	        documento.setId(rs.getInt(1));
    	        documento.setNombre(rs.getString(2));
    	    }
   		
    	}catch(Exception ex){
    		System.out.println("Error - aca.tit.Pagos|RequisitoVO getPago|:"+ex);
    	}
    	finally{
    	    try { ps.close(); } catch (Exception ignore) { }
			try { rs.close(); } catch (Exception ignore) { }
    	}
    	return documento;
    }
    public ArrayList<RequisitoVO> getPagos(Connection conn) throws SQLException{
        String comando = "";
        ArrayList<RequisitoVO> pagos=new ArrayList<RequisitoVO>();
        RequisitoVO pago=null;
    	PreparedStatement ps=null;
    	ResultSet rs = null;
    	try{
    	    comando="SELECT * FROM ENOC.TIT_PAGO ORDER BY PAGO_ID"; 
    	    ps = conn.prepareStatement(comando);
    	    rs = ps.executeQuery();
    	    while(rs.next()){
    	        pago=new RequisitoVO();
    	        pago.setId(rs.getInt(1));
    	        pago.setNombre(rs.getString(2));
    	        pagos.add(pago);
    	    }
   		
    	}catch(Exception ex){
    		System.out.println("Error - aca.tit.Pagos|ArrayList getPagos|:"+ex);
    	}
    	finally{
    	    try { ps.close(); } catch (Exception ignore) { }
			try { rs.close(); } catch (Exception ignore) { }
    	}
    	return pagos;
    }

    public void modificaPago(Connection conn, RequisitoVO pago) throws SQLException{

        String comando = "";
    	PreparedStatement ps=null;
    	try{
    	    comando="UPDATE ENOC.TIT_PAGO SET NOMBRE_PAGO = ? WHERE PAGO_ID = ?"; 
    	    ps = conn.prepareStatement(comando);
    	    ps.setString(1,pago.getNombre());
    	    ps.setInt(2,pago.getId());
    	    ps.executeUpdate();
   		
    	}catch(Exception ex){
    		System.out.println("Error - aca.tit.Pagos|modificaPago|:"+ex);
    	}
    	finally{
    	    try { ps.close(); } catch (Exception ignore) { }
    	}
    }

    public String eliminaPago(Connection conn, String id) throws SQLException{

		PreparedStatement ps	= null;
    	ResultSet rs			= null;
		String comando 			= "";
		String resultado 		= ""; 
    	
    	try{
    	    comando="SELECT PAGO_ID FROM ENOC.TIT_ALUMPAGO WHERE PAGO_ID = ?"; 
    	    ps = conn.prepareStatement(comando);
    	    ps.setString(1,id);
    	    rs = ps.executeQuery();
    	    if(!rs.next()){				
				comando="DELETE FROM ENOC.TIT_PAGO WHERE PAGO_ID = ?"; 
				ps = conn.prepareStatement(comando);
				ps.setString(1,id);
				if (ps.executeUpdate()==1){
					resultado = "Registro Borrado!!";				
				}else{
					resultado = "Error al borrar!!";
				}				
    	    }else{
				resultado = "No se puede borrar porque tiene alumnos asignados!!";
			}
   		
    	}catch(Exception ex){
    		System.out.println("Error - aca.tit.Pagos|eliminaPago|:"+ex);
    	}
    	finally{
    	    try { ps.close(); } catch (Exception ignore) { }
			try { rs.close(); } catch (Exception ignore) { }
    	}
    	return resultado;
    }
}