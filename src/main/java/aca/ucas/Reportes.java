/*
 * Created on 1/09/2005
 *
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
 */
public class Reportes {
    
    public ArrayList<ProfesorVO> getReportexFacultad(Connection conn,String cargaId) throws SQLException{
        ArrayList<ProfesorVO> ucas=new ArrayList<ProfesorVO>();
        ProfesorVO uca;
        String comando = "";
    	PreparedStatement ps=null;
    	ResultSet rs = null;
    	try{
    	    comando="select "+ 
    	 "distinct ENOC.facultad_nombre(up.facultad_id) as facultad,ENOC.nombre_carrera(up.carrera_id) as carrera, up.codigo_personal, "+
   		 "emp_nombre2(up.codigo_personal) as nombre, "+
   		 "(select COALESCE(sum(ucas),0) from ENOC.uca_servicio where codigo_personal=up.codigo_personal and carga_id=?) as servicio, "+ 
   		 "(select COALESCE(sum(ucas),0) from ENOC.uca_docencia where codigo_personal=up.codigo_personal and carga_id=?) as docencia, "+ 
   		 "(select COALESCE(sum(ucas),0) from ENOC.uca_aporte where codigo_personal=up.codigo_personal and carga_id=?) as aporte, "+ 
   		 "(select COALESCE(sum(ucas),0) from ENOC.uca_aregulares where codigo_personal=up.codigo_personal and carga_id=?) as aregulares, "+ 
   		 "(select COALESCE(sum(ucas),0) from ENOC.uca_aespeciales where codigo_personal=up.codigo_personal and carga_id=?) as aespeciales, "+ 
   		 "( "+
   		 "	 (select COALESCE(sum(ucas),0) from ENOC.uca_servicio where codigo_personal=up.codigo_personal and carga_id=?) + "+ 
   		 " 	 (select COALESCE(sum(ucas),0) from ENOC.uca_docencia where codigo_personal=up.codigo_personal and carga_id=?) +  "+ 
   		 " 	 (select COALESCE(sum(ucas),0) from ENOC.uca_aporte where codigo_personal=up.codigo_personal and carga_id=?) + "+ 
   		 "	 (select COALESCE(sum(ucas),0) from ENOC.uca_aregulares where codigo_personal=up.codigo_personal and carga_id=?) + "+ 
   		 "	 (select COALESCE(sum(ucas),0) from ENOC.uca_aespeciales where codigo_personal=up.codigo_personal and carga_id=?) "+ 
   		 ") as total "+
   		 "from  "+
   		 "ENOC.uca_profesor up "+
   		 "where status='P' and codigo_personal in (select codigo_personal from ENOC.carga_grupo where carga_id=?) "+ 
   		 "order by facultad,carrera,codigo_personal";
    	
    	    ps = conn.prepareStatement(comando);
    	    ps.setString(1,cargaId);
    	    ps.setString(2,cargaId);
    	    ps.setString(3,cargaId);
    	    ps.setString(4,cargaId);
    	    ps.setString(5,cargaId);
    	    ps.setString(6,cargaId);
    	    ps.setString(7,cargaId);
    	    ps.setString(8,cargaId);
    	    ps.setString(9,cargaId);
    	    ps.setString(10,cargaId);
    	    ps.setString(11,cargaId);
    	    rs = ps.executeQuery();
    	    while(rs.next()){
    	        uca=new ProfesorVO();
    	        uca.setFacultad(rs.getString(1));
    	        uca.setCarrera(rs.getString(2));
    	        uca.setCodigoPersonal(rs.getString(3));
    	        uca.setNombre(rs.getString(4));
    	        uca.setServicio(rs.getDouble(5));
    	        uca.setDocencia(rs.getDouble(6));
    	        uca.setAporte(rs.getDouble(7));
    	        uca.setAregulares(rs.getDouble(8));
    	        uca.setAespeciales(rs.getDouble(9));
    	        uca.setTotal(rs.getDouble(10));
    	        ucas.add(uca);
    	    }
   		
    	}catch(Exception ex){
    		System.out.println("Error - aca.ucas.Reportes|getReportexFacultad|:"+ex);
    	}
    	finally{
    	    try { ps.close(); } catch (Exception ignore) { }
    	}
    	return ucas;
    }
    
    public String getHTProfesor(Connection conn,String cargaId,String codigo) throws SQLException{
        
        String comando = "",ht="";
    	PreparedStatement ps=null;
    	ResultSet rs = null;
    	try{
    	    comando="select COALESCE(sum(ht),0) as ht from ENOC.mapa_curso where curso_id in "+ 
    	    		"(select curso_id from ENOC.carga_grupo_curso where origen='O' and curso_carga_id in "+ 
    	    		"		(select curso_carga_id from ENOC.carga_grupo where codigo_personal=? "+ 
			   		"			and carga_id=? and valeucas='S'))";
    	
    	    ps = conn.prepareStatement(comando);
    	    ps.setString(1,codigo);
    	    ps.setString(2,cargaId);
    	    rs = ps.executeQuery();
    	    if(rs.next()){
    	        ht=rs.getString(1);
    	    }
   		
    	}catch(Exception ex){
    		System.out.println("Error - aca.ucas.Reportes|getHTProfesor|:"+ex);
    	}
    	finally{
    	    try { ps.close(); } catch (Exception ignore) { }
    	}
    	return ht;
    }

    public String getHPProfesor(Connection conn,String cargaId,String codigo) throws SQLException{
        
        String comando = "",hp="";
    	PreparedStatement ps=null;
    	ResultSet rs = null;
    	try{
    	    comando="select COALESCE(sum(hp),0) as hp from ENOC.mapa_curso where curso_id in "+ 
    	    		"(select curso_id from ENOC.carga_grupo_curso where origen='O' and curso_carga_id in "+ 
    	    		"		(select curso_carga_id from ENOC.carga_grupo where codigo_personal=? "+ 
			   		"			and carga_id=? and valeucas='S'))";
    	
    	    ps = conn.prepareStatement(comando);
    	    ps.setString(1,codigo);
    	    ps.setString(2,cargaId);
    	    rs = ps.executeQuery();
    	    if(rs.next()){
    	        hp=rs.getString(1);
    	    }
   		
    	}catch(Exception ex){
    		System.out.println("Error - aca.ucas.Reportes|getHPProfesor|:"+ex);
    	}
    	finally{
    	    try { ps.close(); } catch (Exception ignore) { }
    	}
    	return hp;
    }
	
	public String getAlumnosProfesor(Connection conn,String cargaId,String codigoProfesor) throws SQLException{
        
        String comando = "",numAlum="";
    	PreparedStatement ps=null;
    	ResultSet rs = null;
    	try{
    	    
			
			comando= "SELECT COUNT(B.CODIGO_PERSONAL) FROM ENOC.CARGA_GRUPO A, ENOC.KRDX_CURSO_ACT B "+				 
					"WHERE A.CARGA_ID =  ? "+
					"AND A.CODIGO_PERSONAL = ? "+
					"AND B.CURSO_CARGA_ID = A.CURSO_CARGA_ID "+
					"AND B.TIPOCAL_ID NOT IN ('M','3')";
    	
    	    ps = conn.prepareStatement(comando);
    	    ps.setString(1,cargaId);
    	    ps.setString(2,codigoProfesor);
    	    rs = ps.executeQuery();
    	    if(rs.next()){
				numAlum=rs.getString(1);
    	    }
   		
    	}catch(Exception ex){
    		System.out.println("Error: aca.ucas.Reportes|getAlumnosProfesor:"+ex);
    	}
    	finally{
    	    try { ps.close(); } catch (Exception ignore) { }
    	}
    	return numAlum;
    }

}