/*
 * Created on 11/05/2005
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
import java.util.HashMap;

/**
 * @author Pedro
 *
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Titulo {
    
    public boolean guardaTitulo(Connection conn, TituloVO titulo) throws SQLException{
        String comando = "";
    	PreparedStatement ps=null;
    	boolean grabo=true;
    	ResultSet rs=null;
    	boolean existe=false;
    	try{
    	    comando="select titulo_id from ENOC.tit_titulo where codigo_personal=?"; 
    	    ps = conn.prepareStatement(comando);
    	    ps.setString(1,titulo.getCodigoPersonal());
    		rs=ps.executeQuery();
    		if(rs.next())existe=true;

    		if(existe) comando="INSERT INTO ENOC.TIT_Titulo VALUES (concat(?,(select concat('_',substr(titulo_id,9)+1) from ENOC.tit_titulo where codigo_personal=?)),?,?,?)"; 
    		else comando="INSERT INTO ENOC.TIT_Titulo VALUES (concat(?,(select concat('_',count(*)+1) from ENOC.tit_titulo where codigo_personal=?)),?,?,?)"; 
    	    ps = conn.prepareStatement(comando);
    	    ps.setString(1,titulo.getCodigoPersonal());
    	    ps.setString(2,titulo.getCodigoPersonal());
    	    ps.setString(3,titulo.getCodigoPersonal());
    	    ps.setString(4,titulo.getDescripcion());
    	    ps.setString(5,titulo.getPlanId());
    		ps.executeUpdate();    		
    	}catch(Exception ex){
    		System.out.println("Error - aca.tit.Titulo|guardaTitulo|:"+ex);
    		grabo=false;
    	}
    	finally{
    	    try { ps.close(); } catch (Exception ignore) { }
    	}
    	return grabo;
    }
 
    public ArrayList<TituloVO> getTitulos(Connection conn,String codigoPersonal) throws SQLException{
        ArrayList<TituloVO> titulos=new ArrayList<TituloVO>();
        String comando = "";
        TituloVO titulo=null;
    	PreparedStatement ps=null;
    	ResultSet rs = null;
    	try{
    	    comando="select * from ENOC.TIT_Titulo where codigo_personal=?";
    	    ps = conn.prepareStatement(comando);
    	    ps.setString(1,codigoPersonal);
    	    rs = ps.executeQuery();
    	    while(rs.next()){
    	        titulo=new TituloVO();
    	        titulo.setTituloId(rs.getString(1));
    	        titulo.setCodigoPersonal(rs.getString(2));
    	        titulo.setDescripcion(rs.getString(3));
    	        titulo.setPlanId(rs.getString(4));
    	        titulos.add(titulo);
    	    }
   		
    	}catch(Exception ex){
    		System.out.println("Error - aca.tit.Titulo|getTitulos|:"+ex);
    	}
    	finally{
    	    try { ps.close(); } catch (Exception ignore) { }
    	    try { rs.close(); } catch (Exception ignore) { }
    	}
    	return titulos;
    }

    public TituloVO getTitulo(Connection conn,String id) throws SQLException{
        String comando = "";
        TituloVO titulo=null;
    	PreparedStatement ps=null;
    	ResultSet rs = null;
    	try{
    	    comando="select * from ENOC.TIT_Titulo "+
    	    		"where Titulo_id=?";
    	    ps = conn.prepareStatement(comando);
    	    ps.setString(1,id);
    	    rs = ps.executeQuery();
    	    while(rs.next()){
    	        titulo=new TituloVO();
    	        titulo.setTituloId(rs.getString(1));
    	        titulo.setCodigoPersonal(rs.getString(2));
    	        titulo.setDescripcion(rs.getString(3));
    	        titulo.setPlanId(rs.getString(4));
    	    }
   		
    	}catch(Exception ex){
    		System.out.println("Error - aca.tit.Titulo|TituloVO getTitulo|:"+ex);
    	}
    	finally{
    	    try { ps.close(); } catch (Exception ignore) { }
    	}
    	return titulo;
    }

    public void modificaTitulo(Connection conn, TituloVO titulo) throws SQLException{

        String comando = "";
    	PreparedStatement ps=null;
    	try{
    	    comando="update ENOC.TIT_Titulo set " +
    	    		"codigo_personal=?,descripcion=?,plan_id=? " +
    	    		"where Titulo_id=?";
    	    ps = conn.prepareStatement(comando);
    	    ps.setString(1,titulo.getCodigoPersonal());
    	    ps.setString(2,titulo.getDescripcion());
    	    ps.setString(3,titulo.getPlanId());
    	    ps.setString(4,titulo.getTituloId());
    	    ps.executeUpdate();
   		
    	}catch(Exception ex){
    		System.out.println("Error - aca.tit.Titulo|modificaTitulo|:"+ex);
    	}
    	finally{
    	    try { ps.close(); } catch (Exception ignore) { }
    	}
    }

    public String eliminaTitulo(Connection conn, String id) throws SQLException{

        String comando = "";
        String matricula="";
    	PreparedStatement ps	= null;
    	PreparedStatement ps2	= null;
    	ResultSet rs=null;
    	try{
    	    comando="select titulo_id from ENOC.TIT_DOCALUM where titulo_id=?"; 
    	    ps2 = conn.prepareStatement(comando);
    	    ps2.setString(1,id);
    	    rs = ps2.executeQuery();
    	    if(rs.next()){
    	        matricula=rs.getString(1);
    	    }
    	    if(matricula.equals("")){
    	        comando="delete from ENOC.TIT_Titulo " +
    	        		"where titulo_id=?";
    	    	ps = conn.prepareStatement(comando);
    	    	ps.setString(1,id);
    	    	ps.executeUpdate();    	    	
    	    }
   		
    	}catch(Exception ex){
    		System.out.println("Error - aca.tit.Titulo|eliminaTitulo|:"+ex);
    	}
    	finally{
    	    try { ps.close(); } catch (Exception ignore) { }
    	    if (ps2!=null) ps2.close();
    	}
    	return matricula;
    }
    
	public ArrayList<String> getTitulosPosibles(Connection conn, String Codigo_personal) throws SQLException{
		ResultSet rs = null;
		ArrayList<String> planes=new ArrayList<String>();
		PreparedStatement ps = null; 
		try{
			ps = conn.prepareStatement("SELECT plan_id FROM ENOC.ALUM_PLAN "+ 
			        			"WHERE CODIGO_PERSONAL=? "+ 
			        			"and plan_id not in "+ 
									"(select plan_id from ENOC.tit_titulo where codigo_personal=?)"); 
			ps.setString(1, Codigo_personal);
			ps.setString(2, Codigo_personal);
			
			rs = ps.executeQuery();
			while (rs.next()){
				planes.add(rs.getString(1));
			}
		}catch(Exception ex){
			System.out.println("Error - aca.tit.Titulo|eliminaTitulo|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return planes;
	}
	
	public static String getTituloId(Connection conn, String codigoPersonal, String planId) throws SQLException, Exception {
		
		PreparedStatement ps	= null;
		ResultSet rs 			= null;
		String eventoId			= "";
		
		try{
			ps = conn.prepareStatement("SELECT TITULO_ID FROM ENOC.TIT_TITULO WHERE CODIGO_PERSONAL = '"+codigoPersonal+"' " + 
					" AND PLAN_ID = '"+planId+"' ");
			
			rs = ps.executeQuery();
			if (rs.next()){
				eventoId = rs.getString("TITULO_ID");
			}
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.Titulo|getTituloId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return eventoId;
	}
	
	public HashMap<String,TituloVO> mapTitulados(Connection conn) throws SQLException{
        HashMap<String,TituloVO> titulos = new HashMap<String,TituloVO>();
        String comando 			= "";
        TituloVO titulo			= null;
    	PreparedStatement ps	= null;
    	ResultSet rs 			= null;
    	String llave			= "";
    	try{
    		// Valida que los alumnos tengan registrado el titulo en profesiones
    	    comando="SELECT * FROM ENOC.TIT_TITULO WHERE TITULO_ID IN (SELECT TITULO_ID FROM ENOC.TIT_DOCALUM WHERE ENOC.TIT_DOCALUM.DOCUMENTO_ID IN (32,42,48))";
    	    ps = conn.prepareStatement(comando);    	    
    	    rs = ps.executeQuery();
    	    while(rs.next()){
    	        titulo=new TituloVO();
    	        titulo.setTituloId(rs.getString(1));
    	        titulo.setCodigoPersonal(rs.getString(2));
    	        titulo.setDescripcion(rs.getString(3));
    	        titulo.setPlanId(rs.getString(4));
    	        llave = titulo.getCodigoPersonal()+titulo.getPlanId();
    	        titulos.put(llave, titulo);
    	    }
   		
    	}catch(Exception ex){
    		System.out.println("Error - aca.tit.Titulo|mapTitulados|:"+ex);
    	}
    	finally{
    	    try { ps.close(); } catch (Exception ignore) { }
    	    try { rs.close(); } catch (Exception ignore) { }
    	}
    	return titulos;
    }

	
}