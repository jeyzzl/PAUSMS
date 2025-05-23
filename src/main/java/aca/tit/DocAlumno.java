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
 */
public class DocAlumno {
    java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
    public boolean guardaDocumento(Connection conn, DocAlumVO documento) throws SQLException{
        String comando = "";
    	PreparedStatement ps=null;
    	boolean grabo=true;
    	try{
    	    comando="insert into ENOC.tit_docalum(titulo_id,documento_id,fecha,entregado,comentario) " + 
    	    		"values(?,?,to_date(?,'dd/mm/yyyy'),?,?)";
    	    ps = conn.prepareStatement(comando);
    	    ps.setString(1,documento.getTituloId());
    	    ps.setInt(2,documento.getDocumentoId());
    	    ps.setString(3,documento.getFecha());
    	    ps.setString(4,documento.getEntregado());
    	    ps.setString(5,documento.getComentario());
    		ps.executeUpdate();
    	}catch(Exception ex){
    		System.out.println("Error - aca.tit.DOcAlumno|guardaDocumento|:"+ex);
    		grabo=false;
    	}
    	finally{
    	    try { ps.close(); } catch (Exception ignore) { }
    	}
    	return grabo;
    }
    
    public ArrayList<DocAlumVO> getDocumentos(Connection conn,String tituloId) throws SQLException{
        ArrayList<DocAlumVO> documentos=new ArrayList<DocAlumVO>();
        String comando = "";
        DocAlumVO documento=null;
    	PreparedStatement ps=null;
    	ResultSet rs = null;
    	try{
    	    comando="select * from ENOC.tit_DOCALUM where titulo_id=? order by fecha,documento_id asc";
    	    ps = conn.prepareStatement(comando);
    	    ps.setString(1,tituloId);
    	    rs = ps.executeQuery();
    	    while(rs.next()){
    	        documento=new DocAlumVO();
    	        documento.setTituloId(rs.getString(1));
    	        documento.setDocumentoId(rs.getInt(2));
    	        documento.setFecha(sdf.format(rs.getDate(3)));
    	        documento.setEntregado(rs.getString(4));
    	        documento.setComentario(rs.getString(5));
    	        documentos.add(documento);
    	    }
   		
    	}catch(Exception ex){
    		System.out.println("Error - aca.tit.DOcAlumno|getDocumentos|:"+ex);
    	}
    	finally{
    	    try { ps.close(); } catch (Exception ignore) { }
    	}
    	return documentos;
    }
    
    public ArrayList<DocAlumVO> getDocumentos(Connection conn,String tituloId, String tipo) throws SQLException{
        ArrayList<DocAlumVO> documentos=new ArrayList<DocAlumVO>();
        String comando = "";
        DocAlumVO documento=null;
    	PreparedStatement ps=null;
    	ResultSet rs = null;
    	try{
    	    comando="SELECT * FROM ENOC.TIT_DOCALUM" + 
    	    		" WHERE TITULO_ID=?" +
    	    		" AND DOCUMENTO_ID IN (SELECT DOCUMENTO_ID FROM ENOC.TIT_DOCUMENTOS" + 
    	    							" WHERE TIPO = ?)" +
    	    		" ORDER BY TIT_ORDEN(DOCUMENTO_ID)";
    	    ps = conn.prepareStatement(comando);
    	    ps.setString(1, tituloId);
    	    ps.setString(2, tipo);
    	    rs = ps.executeQuery();
    	    while(rs.next()){
    	        documento=new DocAlumVO();
    	        documento.setTituloId(rs.getString(1));
    	        documento.setDocumentoId(rs.getInt(2));
    	        documento.setFecha(sdf.format(rs.getDate(3)));
    	        documento.setEntregado(rs.getString(4));
    	        documento.setComentario(rs.getString(5));
    	        documentos.add(documento);
    	    }
   		
    	}catch(Exception ex){
    		System.out.println("Error - aca.tit.DOcAlumno|getDocumentos|:"+ex);
    	}
    	finally{
    	    try { ps.close(); } catch (Exception ignore) { }
    	}
    	return documentos;
    }
    
    public ArrayList<DocAlumVO> getEntregaDocumentos(Connection conn,String tituloId) throws SQLException{
        ArrayList<DocAlumVO> documentos=new ArrayList<DocAlumVO>();
        String comando = "";
        DocAlumVO documento=null;
    	PreparedStatement ps=null;
    	ResultSet rs = null;
    	try{
    	    comando="SELECT * FROM ENOC.TIT_DOCALUM" + 
    	    		" WHERE TITULO_ID=?" +
    	    		" AND DOCUMENTO_ID IN (SELECT DOCUMENTO_ID FROM ENOC.TIT_DOCUMENTOS" + 
    	    							" WHERE TIPO IN ('G','M','L','P'))" +
    	    		" ORDER BY TIT_ORDEN(DOCUMENTO_ID)";
    	    ps = conn.prepareStatement(comando);
    	    ps.setString(1, tituloId);
    	    rs = ps.executeQuery();
    	    while(rs.next()){
    	        documento=new DocAlumVO();
    	        documento.setTituloId(rs.getString(1));
    	        documento.setDocumentoId(rs.getInt(2));
    	        documento.setFecha(sdf.format(rs.getDate(3)));
    	        documento.setEntregado(rs.getString(4));
    	        documento.setComentario(rs.getString(5));
    	        documentos.add(documento);
    	    }
   		
    	}catch(Exception ex){
    		System.out.println("Error - aca.tit.DOcAlumno|getDocumentos|:"+ex);
    	}
    	finally{
    	    try { ps.close(); } catch (Exception ignore) { }
    	}
    	return documentos;
    }

    public DocAlumVO getDocumento(Connection conn,String tituloId, int id) throws SQLException{
        String comando = "";
        DocAlumVO documento=null;
    	PreparedStatement ps=null;
    	ResultSet rs = null;
    	try{
    	    comando="select * from ENOC.tit_DOCALUM "+
    	    		"where titulo_id=? and documento_id=?";
    	    ps = conn.prepareStatement(comando);
    	    ps.setString(1,tituloId);
    	    ps.setInt(2,id);
    	    rs = ps.executeQuery();
    	    if(rs.next()){
    	        documento=new DocAlumVO();
    	        documento.setTituloId(rs.getString(1));
    	        documento.setDocumentoId(rs.getInt(2));
    	        documento.setFecha(sdf.format(rs.getDate(3)));
    	        documento.setEntregado(rs.getString(4));
    	        documento.setComentario(rs.getString(5));
    	    }
   		
    	}catch(Exception ex){
    		System.out.println("Error - aca.tit.DOcAlumno|getDocumento|:"+ex);
    	}
    	finally{
    	    try { ps.close(); } catch (Exception ignore) { }
    	}
    	return documento;
    }

    public void modificaDocumento(Connection conn, DocAlumVO documento) throws SQLException{

        String comando = "";
    	PreparedStatement ps=null;
    	try{
    	    comando="update ENOC.tit_DOCALUM set " +
    			"fecha=to_date(?,'dd/mm/yyyy'),entregado=?,comentario=? " +
    	    	"where titulo_id=? "+
				"and documento_id=?";
	    	ps = conn.prepareStatement(comando);
    	    ps.setString(1,documento.getFecha());
    	    ps.setString(2,documento.getEntregado());
    	    ps.setString(3,documento.getComentario());
    	    ps.setString(4,documento.getTituloId());
    	    ps.setInt(5,documento.getDocumentoId());
    	    ps.executeUpdate();
   		
    	}catch(Exception ex){
    		System.out.println("Error - aca.tit.DOcAlumno|modificaDocumento|:"+ex);
    	}
    	finally{
    	    try { ps.close(); } catch (Exception ignore) { }
    	}
    }

    public void eliminaDocumento(Connection conn, String tituloId,int id) throws SQLException{

        String comando = "";
    	PreparedStatement ps=null;
    	try{
    	    comando="delete from ENOC.tit_DOCALUM " +
    	    		"where titulo_id=? and documento_id=?";
    	    ps = conn.prepareStatement(comando);
    	    ps.setString(1,tituloId);
    	    ps.setInt(2,id);
    	    ps.executeUpdate();
   		
    	}catch(Exception ex){
    		System.out.println("Error - aca.tit.DOcAlumno|eliminaDocumento|:"+ex);
    	}
    	finally{
    	    try { ps.close(); } catch (Exception ignore) { }
    	}
    }
    
    public String getNombreDocumento(Connection conn, int id) throws SQLException{

        String comando = "";
    	PreparedStatement ps=null;
    	ResultSet rs =null;
    	String nombre="";
    	try{
    	    comando="select documento_nombre from ENOC.tit_documentos " + 
    	    		"where documento_id=?";
    	    ps = conn.prepareStatement(comando);
    	    ps.setInt(1,id);
    	    rs=ps.executeQuery();
    	    if(rs.next())nombre=rs.getString(1);
   		
    	}catch(Exception ex){
    		System.out.println("Error - aca.tit.DOcAlumno|getNombreDocumento|:"+ex);
    	}
    	finally{
    	    try { ps.close(); } catch (Exception ignore) { }
    	}
    	return nombre;
    }

    public ArrayList<String> getDocumentosFaltantes(Connection conn,String tituloId) throws SQLException{
        ArrayList<String> documentos=new ArrayList<String>();
        String comando = "";
    	PreparedStatement ps=null;
    	ResultSet rs = null;
    	try{
    	    comando="select documento_nombre from ENOC.tit_DOCUMENTOS "+
    		"where documento_id in " +
    			"(select documento_id from ENOC.tit_docalum "+ 
    		"where titulo_id=? and entregado='N') and tipo!='S' order by orden";
    	    ps = conn.prepareStatement(comando);
    	    ps.setString(1,tituloId);
    	    rs = ps.executeQuery();
    	    while(rs.next()){
    	        documentos.add(rs.getString(1));
    	    }
   		
    	}catch(Exception ex){
    		System.out.println("Error - aca.tit.DOcAlumno|getDocumentosFaltantes|:"+ex);
    	}
    	finally{
    	    try { ps.close(); } catch (Exception ignore) { }
    	}
    	return documentos;
    }

    public int getNivelPlan(Connection conn,String plan) throws SQLException{
        String comando = "";
    	PreparedStatement ps=null;
    	ResultSet rs = null;
    	int nivel=0;
    	try{
    	    comando="select nivel_id from ENOC.cat_carrera c, ENOC.mapa_plan p where p.carrera_id=c.carrera_id and p.PLAN_ID = ? "; 
    	    ps = conn.prepareStatement(comando);
    	    ps.setString(1,plan);
    	    rs = ps.executeQuery();
    	    if(rs.next())nivel=rs.getInt(1);
   		
    	}catch(Exception ex){
    		System.out.println("Error - aca.tit.DOcAlumno|getNivelPlan|:"+ex);
    	}
    	finally{
    	    try { ps.close(); } catch (Exception ignore) { }
    	}
    	return nivel;
    }
    
    public boolean existeAlumno(Connection conn, String codigoPersonal) throws SQLException{
 		PreparedStatement ps	= null;
 		boolean 		ok 		= false;
 		ResultSet 		rs		= null;		
 		
 		try{
 			ps = conn.prepareStatement("SELECT TITULO_ID FROM ENOC.TIT_DOCALUM "+ 
 				" WHERE SUBSTR(TITULO_ID,0,7)  = ? ");
 			ps.setString(1, codigoPersonal);
 			
 			rs = ps.executeQuery();
 			if (rs.next())
 				ok = true;
 			else
 				ok = false;
 			
 		}catch(Exception ex){
 			System.out.println("Error - aca.tit.DocAluno|existeAlumno|:"+ex);
 		}finally{
 			try { rs.close(); } catch (Exception ignore) { }
 	 		try { ps.close(); } catch (Exception ignore) { }
 		}
 		
 		return ok;
 	}
    
    
}