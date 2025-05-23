/*
 * Created on 13/02/2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package aca.ssoc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author Pedro 
 *
 */
public class Asignacion {
    java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
    
    public boolean guardaAsignacion(Connection conn, AsignacionVO asignacion) throws SQLException{
        String comando = "";
    	PreparedStatement ps=null;
    	boolean grabo=true;
    	try{
    	    comando="insert into ENOC.ssoc_asignacion values(?," + 
    	    		"(select COALESCE(max(asignacion_id),0)+1 from ENOC.ssoc_asignacion where codigo_personal = '"+asignacion.getCodigoPersonal()+"')" + 
    	    		",?,?,?,?,to_date(?,'dd/mm/yyyy'),?)";
    	    ps = conn.prepareStatement(comando);
    	    ps.setString(1,asignacion.getCodigoPersonal());
    	    ps.setString(2,asignacion.getDependencia());
    	    ps.setString(3,asignacion.getDireccion());
    	    ps.setString(4,asignacion.getTelefono());
    	    ps.setString(5,asignacion.getResponsable());
    	    ps.setString(6,asignacion.getFechaInicio());
    	    ps.setString(7,"A");
    		ps.executeUpdate();
    	}catch(Exception ex){
    		System.out.println("Error - aca.ssoc.Asignacion|guardaAsignacion|:"+ex);
    		grabo=false;
    	}
    	finally{
    	    try { ps.close(); } catch (Exception ignore) { }
    	}
    	return grabo;
    }
    
    public ArrayList<AsignacionVO> getAsignaciones(Connection conn, String codigoPersonal) throws SQLException{
        ArrayList<AsignacionVO> asignaciones=new ArrayList<AsignacionVO>();
        String comando = "";
        AsignacionVO asignacion=null;
    	PreparedStatement ps=null;
    	ResultSet rs = null;
    	try{
    	    comando="select * from ENOC.ssoc_asignacion where codigo_personal=?"; 
    	    ps = conn.prepareStatement(comando);
    	    ps.setString(1,codigoPersonal);
    	    rs = ps.executeQuery();
    	    while(rs.next()){
    	        asignacion=new AsignacionVO();
    	        asignacion.setCodigoPersonal(rs.getString(1));
    	        asignacion.setAsignacionId(rs.getString(2));
    	        asignacion.setDependencia(rs.getString(3));
    	        asignacion.setDireccion(rs.getString(4));
    	        asignacion.setTelefono(rs.getString(5));
    	        asignacion.setResponsable(rs.getString(6));
    	        asignacion.setFechaInicio(sdf.format(rs.getDate(7)));
    	        asignacion.setEstado(rs.getString(8));
    	        asignacion.setSector(rs.getString(9));
    	        asignaciones.add(asignacion);
    	    }
   		
    	}catch(Exception ex){
    		System.out.println("Error - aca.ssoc.Asignacion|getAsignaciones|:"+ex);
    	}finally{
    	    try { ps.close(); } catch (Exception ignore) { }
    	    try { rs.close(); } catch (Exception ignore) { }
    	}
    	return asignaciones;
    }

    public AsignacionVO getAsignacion(Connection conn,String codigoPersonal, int id) throws SQLException{
        String comando = "";
        AsignacionVO asignacion=null;
    	PreparedStatement ps=null;
    	ResultSet rs = null;
    	try{
    	    comando="select * from ENOC.ssoc_asignacion "+ 
    	    		"where codigo_personal=? "+
    	    		"and asignacion_id=?";
    	    ps = conn.prepareStatement(comando);
    	    ps.setString(1,codigoPersonal);
    	    ps.setInt(2,id);
    	    rs = ps.executeQuery();
    	    if(rs.next()){
    	        asignacion=new AsignacionVO();
    	        asignacion.setCodigoPersonal(rs.getString(1));
    	        asignacion.setAsignacionId(rs.getString(2));
    	        asignacion.setDependencia(rs.getString(3));
    	        asignacion.setDireccion(rs.getString(4));
    	        asignacion.setTelefono(rs.getString(5));
    	        asignacion.setResponsable(rs.getString(6));
    	        asignacion.setFechaInicio(sdf.format(rs.getDate(7)));
    	        asignacion.setEstado(rs.getString(8));
    	    }
   		
    	}catch(Exception ex){
    		System.out.println("Error - aca.ssoc.Asignacion|getAsignacion|:"+ex);
    	}
    	finally{
    	    try { ps.close(); } catch (Exception ignore) { }
    	    try { rs.close(); } catch (Exception ignore) { }
    	}
    	return asignacion;
    }

    public void modificaAsignacion(Connection conn, AsignacionVO asignacion) throws SQLException{

        String comando = "";
    	PreparedStatement ps=null;
    	try{
    	    comando="update ENOC.ssoc_asignacion set " + 
    			"dependencia=?,direccion=?, " +
    			"telefono=?,responsable=?, " +
    			"f_inicio=to_date(?,'dd/mm/yyyy') " +
    	    	"where codigo_personal=? "+
	    		"and asignacion_id=?";
	    	ps = conn.prepareStatement(comando);
    	    ps.setString(1,asignacion.getDependencia());
    	    ps.setString(2,asignacion.getDireccion());
    	    ps.setString(3,asignacion.getTelefono());
    	    ps.setString(4,asignacion.getResponsable());
    	    ps.setString(5,asignacion.getFechaInicio());
    	    ps.setString(6,asignacion.getCodigoPersonal());
    	    ps.setString(7,asignacion.getAsignacionId());
    	    ps.executeUpdate();
   		
    	}catch(Exception ex){
    		System.out.println("Error - aca.ssoc.Asignacion|modificaAsignacion|:"+ex);
    	}
    	finally{
    	    try { ps.close(); } catch (Exception ignore) { }    	    
    	}
    }

    public String eliminaAsignacion(Connection conn, String codigoPersonal,String id) throws SQLException{
		String comando = "";
    	PreparedStatement ps=null;
    	String matricula="";
    	ResultSet rs=null;
    	try{
    	    comando=" SELECT ASIGNACION_ID, CODIGO_PERSONAL " +
    	    		" FROM ENOC.SSOC_ASIGNACION " + 
    	    		" WHERE ASIGNACION_ID = ? " + 
    	    		" AND CODIGO_PERSONAL = ?";
    	    ps = conn.prepareStatement(comando);
    	    ps.setString(1,id);
			ps.setString(2,codigoPersonal);
    	    rs = ps.executeQuery();
    	    if(rs.next()){
				
				// ELIMINA LA LIGA ENTRE LA ASIGNACION Y EL DOCUMENTO DEL ALUMNO ( SSOC_DOCALUM )   
    	        comando = "UPDATE ENOC.SSOC_DOCALUM SET ASIGNACION_ID = 0 " + 
    	        	"WHERE CODIGO_PERSONAL = ?" +
    	        	"AND ASIGNACION_ID = ?";
				ps = conn.prepareStatement(comando);
				ps.setString(1,codigoPersonal);
    	    	ps.setString(2,id);
    	    	ps.executeUpdate();
				
				// ELIMINA UNA ASIGNACION DEL ALUMNO ( SSOC_ASIGNACION )
				comando="DELETE FROM ENOC.SSOC_ASIGNACION " + 
    	    			"WHERE CODIGO_PERSONAL = ? AND ASIGNACION_ID = ?";
    	    	ps = conn.prepareStatement(comando);
    	    	ps.setString(1,codigoPersonal);
    	    	ps.setString(2,id);
    	    	ps.executeUpdate();
				matricula = "";				
    	    }else{
				matricula = "x";
    	    }
   		
    	}catch(Exception ex){
    		System.out.println("Error - aca.ssoc.Asignacion|EliminaAsgnacion|:"+ex);
    	}
    	finally{
    	    try { ps.close(); } catch (Exception ignore) { }
    	    try { rs.close(); } catch (Exception ignore) { }
    	}
    	return matricula;
    }
    
    public String getDependenciaUltimaAsignacion(Connection conn, String codigoPersonal) throws SQLException{
		String comando 			= "";
    	PreparedStatement ps	= null;
    	String dependencia		= "X";
    	ResultSet rs			= null;
    	try{
    	    comando="SELECT DEPENDENCIA FROM ENOC.SSOC_ASIGNACION" + 
    	    		" WHERE CODIGO_PERSONAL = '"+codigoPersonal+"'" +
    	    		" AND F_INICIO = (SELECT MAX(F_INICIO) AS F_INICIO" +
    	    						" FROM ENOC.SSOC_ASIGNACION" + 
    	    						" WHERE CODIGO_PERSONAL = '"+codigoPersonal+"')";
    	    ps = conn.prepareStatement(comando);
    	    rs = ps.executeQuery();
    	    if(rs.next()){
				dependencia = rs.getString("DEPENDENCIA");		
    	    }
   		
    	}catch(Exception ex){
    		System.out.println("Error - aca.ssoc.Asignacion|EliminaAsgnacion|:"+ex);
    	}
    	finally{
    	    try { ps.close(); } catch (Exception ignore) { }
    	    try { rs.close(); } catch (Exception ignore) { }
    	}
    	return dependencia;
    }
    
}