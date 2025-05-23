package aca.cultural;

import java.sql.*;
import java.util.ArrayList;


import aca.cultural.CompEventoImagen;

public class CompEventoImagenUtil{
	
	public boolean insertRegByte(Connection conn, CompEventoImagen compEventoImagen) throws Exception{
		PreparedStatement ps 	= null;
		boolean ok 				= false;
		
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.COMP_EVENTO_IMAGEN"+ 
				"(EVENTO_ID, IMAGEN_ID, DESCRIPCION, IMAGEN) "+
				"VALUES( ?, ?, ?, ?)");
			
			ps.setString(1, compEventoImagen.getEventoId());
			ps.setString(2, compEventoImagen.getImagenId());
			ps.setString(3, compEventoImagen.getDescripcion());
			ps.setBytes(4, compEventoImagen.getImagen());
			
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;		
			
		}catch(Exception ex){
			System.out.println("Error - aca.cultural.CompEventoImagenUtil|insertRegByte|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}

	public boolean updateReg(Connection conn, CompEventoImagen compEventoImagen ) throws Exception{
		PreparedStatement ps 	= null;		
		boolean ok 				= false;
		
		try{
			ps = conn.prepareStatement("UPDATE ENOC.COMP_EVENTO_IMAGEN "+ 
				" SET DESCRIPCION = ?, "+
				" IMAGEN = ? "+
				" WHERE EVENTO_ID = ? AND IMAGEN_ID = ? ");
			
			ps.setString(1, compEventoImagen.getDescripcion());
			ps.setBytes(2, compEventoImagen.getImagen());
			ps.setString(3, compEventoImagen.getEventoId());
			ps.setString(4, compEventoImagen.getImagenId());
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;	
			
		}catch(Exception ex){
			System.out.println("Error - aca.cultural.CompEventoImagenUtil|updateReg|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}

	public boolean deleteReg(Connection conn, String eventoId, String imagenId ) throws Exception{
		PreparedStatement ps 	= null;
		boolean ok 				= false;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.COMP_EVENTO_IMAGEN "+ 
				"WHERE EVENTO_ID = ? AND IMAGEN_ID = ? ");
			
			ps.setString(1, eventoId);
			ps.setString(2, imagenId);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;		
			
		}catch(Exception ex){
			System.out.println("Error - aca.cultural.CompEventoImagenUtil|deleteReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean deleteImagen(Connection conn, String eventoId ) throws Exception{
		PreparedStatement ps 	= null;
		boolean ok 				= false;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.COMP_EVENTO_IMAGEN "+ 
				"WHERE EVENTO_ID = ?");
			
			ps.setString(1, eventoId);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;		
			
		}catch(Exception ex){
			System.out.println("Error - aca.cultural.CompEventoImagenUtil|deleteImagen|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean deleteImagenTodo(Connection conn,String eventos ) throws Exception{
		PreparedStatement ps 	= null;
		boolean ok 				= false;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.COMP_EVENTO_IMAGEN "+ 
				"WHERE EVENTO_ID IN ("+eventos+")");
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;		
			
		}catch(Exception ex){
			System.out.println("Error - aca.cultural.CompEventoImagenUtil|deleteImagenTodo|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public CompEventoImagen mapeaRegId( Connection conn, String eventoId, String imagenId) throws SQLException{
		
		CompEventoImagen imagen = new CompEventoImagen();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
		ps = conn.prepareStatement("SELECT"+
			" EVENTO_ID, IMAGEN_ID, DESCRIPCION, IMAGEN"+
			" FROM ENOC.COMP_EVENTO_IMAGEN"+ 
			" WHERE EVENTO_ID = ? AND IMAGEN_ID = ? ");
		ps.setString(1, eventoId);
		ps.setString(2, imagenId);
		
		rs = ps.executeQuery();
		if (rs.next()){			
			imagen.mapeaReg(rs);
		}
		}catch(Exception ex){
			System.out.println("Error - aca.cultural.CompEventoImagenUtil|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return imagen;
	}

	public boolean existeReg(Connection conn, String eventoId) throws SQLException{
		
		PreparedStatement ps	= null;
		boolean 		ok 		= false;
		ResultSet 		rs		= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.COMP_EVENTO_IMAGEN "+ 
				"WHERE EVENTO_ID = ? AND IMAGEN_ID = ? ");
			
			ps.setString(1, eventoId);		
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.cultural.CompEventoImagenUtil|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean existeReg(Connection conn, String eventoId, String imagenId) throws SQLException{
		
		PreparedStatement ps	= null;
		boolean 		ok 		= false;
		ResultSet 		rs		= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.COMP_EVENTO_IMAGEN "+ 
				"WHERE EVENTO_ID = ? AND IMAGEN_ID = ? ");
			
			ps.setString(1, eventoId);
			ps.setString(2, imagenId);
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.cultural.CompEventoImagenUtil|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public String maximoReg(Connection conn, String eventoId) throws SQLException{
 		String 		maximo 		= "1";
 		ResultSet 		rs		= null;
 		PreparedStatement ps	= null;
 		
 		try{
 			ps = conn.prepareStatement("SELECT MAX(IMAGEN_ID)+1 AS MAXIMO FROM ENOC.COMP_EVENTO_IMAGEN "+ 
 				" WHERE EVENTO_ID = ?");
 			
 			ps.setString(1, eventoId);
 			
 			rs = ps.executeQuery();
 			if (rs.next())
 				maximo = rs.getString("MAXIMO");
 			if(maximo == null)
 				maximo = "1";
 		}catch(Exception ex){
 			System.out.println("Error - aca.cultural.CompEventoImagenUtil|maximoReg|:"+ex);
 		}finally{
	 		try { rs.close(); } catch (Exception ignore) { }
	 		try { ps.close(); } catch (Exception ignore) { }
 		}
 		return maximo;
 	}

	public ArrayList<CompEventoImagen> getListAll(Connection Conn, String orden ) throws SQLException{
		
		ArrayList<CompEventoImagen> list 	= new ArrayList<CompEventoImagen>();
		Statement st 					= Conn.createStatement();
		ResultSet rs 					= null;
		String comando					= "";
		
		try{
			comando = "SELECT * FROM ENOC.COMP_EVENTO_IMAGEN "+orden;			
			rs = st.executeQuery(comando);
			while (rs.next()){
				CompEventoImagen obj = new CompEventoImagen();
				obj.mapeaReg(rs);
				list.add(obj);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.cultural.CompEventoImagenUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}		
		
		return list;
	}
		
	
	public ArrayList<CompEventoImagen> getImagenEvento(Connection Conn, String eventoId, String orden ) throws SQLException{
		
		ArrayList<CompEventoImagen> list 	= new ArrayList<CompEventoImagen>();
		Statement st 					= Conn.createStatement();
		ResultSet rs 					= null;
		String comando					= "";
		
		try{
			comando = "SELECT * FROM ENOC.COMP_EVENTO_IMAGEN WHERE EVENTO_ID = '"+eventoId+"' "+orden;			
			rs = st.executeQuery(comando);
			while (rs.next()){
				CompEventoImagen obj = new CompEventoImagen();
				obj.mapeaReg(rs);
				list.add(obj);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.cultural.CompEventoImagenUtil|getImagenEvento|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}		
		
		return list;
	}

	public ArrayList<CompEventoImagen> imagenesParaManana(Connection Conn, String orden ) throws SQLException{
		
		ArrayList<CompEventoImagen> list 	= new ArrayList<CompEventoImagen>();
		Statement st 					= Conn.createStatement();
		ResultSet rs 					= null;
		String comando					= "";
		
		try{
			comando = " SELECT * FROM ENOC.COMP_EVENTO_IMAGEN"+
					  " WHERE EVENTO_ID IN"+
					  " (SELECT EVENTO_ID FROM ENOC.COMP_EVENTO WHERE FECHA >= TO_DATE(TO_CHAR(now(),'DD/MM/YYYY'),'DD/MM/YYYY') AND ESTADO = '1')"+orden;			
			rs = st.executeQuery(comando);
			while (rs.next()){
				CompEventoImagen obj = new CompEventoImagen();
				obj.mapeaReg(rs);
				list.add(obj);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.cultural.CompEventoImagenUtil|imagenesParaMañana|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}		
		
		return list;
	}
}