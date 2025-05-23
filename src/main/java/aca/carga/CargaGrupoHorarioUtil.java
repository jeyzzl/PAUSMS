// Clase Util para la tabla de Carga
package aca.carga;

import java.sql.*;
import java.util.ArrayList;

public class CargaGrupoHorarioUtil{
	
	public boolean insertReg(Connection conn, CargaGrupoHorario horario ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.CARGA_GRUPO_HORARIO"+ 
				"(CURSO_CARGA_ID, SALON_ID, HORARIO, TIPO, VALIDA_CRUCE ) "+
				"VALUES( ?, ?, ?, ?, ?)");
			
			ps.setString(1, horario.getCursoCargaId());
			ps.setString(2, horario.getSalonId());
			ps.setString(3, horario.getHorario());
			ps.setString(4, horario.getTipo());
			ps.setString(5, horario.getValidaCruce());
			
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoHorario|insertReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public boolean updateReg(Connection conn, CargaGrupoHorario horario ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.CARGA_GRUPO_HORARIO "+				 
				"SET HORARIO = ?, "+
				"TIPO = ?, "+
				"VALIDA_CRUCE = ? "+				
				"WHERE CURSO_CARGA_ID = ? "+
				"AND SALON_ID = ?");			
			
			ps.setString(1, horario.getHorario());
			ps.setString(2, horario.getTipo());
			ps.setString(3, horario.getValidaCruce());
			ps.setString(4, horario.getCursoCargaId());
			ps.setString(5, horario.getSalonId());
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoHorario|updateReg|:"+ex);		 
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public boolean deleteReg(Connection conn, String cursoCargaId, String salonId) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.CARGA_GRUPO_HORARIO "+ 
				"WHERE CURSO_CARGA_ID = ? "+
				"AND SALON_ID = ?");
			ps.setString(1, cursoCargaId);
			ps.setString(2, salonId);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoHorario|deleteReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean deleteReg(Connection conn, String cursoCargaId ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.CARGA_GRUPO_HORARIO "+ 
				"WHERE CURSO_CARGA_ID = ? ");
			ps.setString(1, cursoCargaId);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoHorario|deleteReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public CargaGrupoHorario mapeaRegId( Connection conn, String cursoCargaId, String salonId ) throws SQLException{
		
		CargaGrupoHorario hora = new CargaGrupoHorario();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement("SELECT "+
				"CURSO_CARGA_ID, SALON_ID, HORARIO, TIPO, VALIDA_CRUCE "+
				"FROM ENOC.CARGA_GRUPO_HORARIO "+ 
				"WHERE CURSO_CARGA_ID = ? "+
				"AND SALON_ID = ?");
			ps.setString(1, cursoCargaId);
			ps.setString(2, salonId);
			
			rs = ps.executeQuery();
			if (rs.next()){
				hora.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoHorario|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return hora;
	}
	
	public CargaGrupoHorario mapeaRegCurso( Connection conn, String cursoCargaId ) throws SQLException{
		
		CargaGrupoHorario hora = new CargaGrupoHorario();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement("SELECT "+
				"CURSO_CARGA_ID, SALON_ID, HORARIO, TIPO, VALIDA_CRUCE "+
				"FROM ENOC.CARGA_GRUPO_HORARIO "+ 
				"WHERE CURSO_CARGA_ID = ? ");
			ps.setString(1, cursoCargaId);
			
			rs = ps.executeQuery();
			if (rs.next()){
				hora.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoHorario|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return hora;
	}
	
	public boolean existeReg(Connection conn, String cursoCargaId, String salonId) throws SQLException{
		boolean 			ok 	= false;
		ResultSet 			rs		= null;
		PreparedStatement 	ps		= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.CARGA_GRUPO_HORARIO "+ 
				"WHERE CURSO_CARGA_ID = ? "+
				"AND SALON_ID = ?");
			ps.setString(1, cursoCargaId);
			ps.setString(2, salonId);
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoHorario|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public static boolean existeSalon(Connection conn, String salonId) throws SQLException{
		boolean 			ok 	= false;
		ResultSet 			rs		= null;
		PreparedStatement 	ps		= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.CARGA_GRUPO_HORARIO "+ 
				"WHERE SALON_ID = ? ");
			ps.setString(1, salonId);
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoHorario|existeSalon|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	// Regresa las posiciones del horario que están ocupadas.
	public static String getPosiciones(String horario ) throws SQLException{
		
		String strPos	= "";
		int numPos		= 0;
		
		try{			
			while (horario.indexOf("1", numPos) > -1){
				if (numPos>0)
					strPos += ","+String.valueOf(horario.indexOf("1", numPos)+1);
				else
					strPos += String.valueOf(horario.indexOf("1", numPos)+1);
				numPos = horario.indexOf("1", numPos)+1;
			}
				
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CargaGrupoHorario|getPosiciones|:"+ex);
		}finally{			
		}
			
		return strPos;
	}
		
	public ArrayList<CargaGrupoHorario> getListAll(Connection conn, String orden ) throws SQLException{
		
		ArrayList<CargaGrupoHorario> lisHorario	= new ArrayList<CargaGrupoHorario>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando		= "";
		
		try{
			comando = "SELECT CURSO_CARGA_ID, SALON_ID, HORARIO, TIPO, VALIDA_CRUCE "+
					"FROM ENOC.CARGA_GRUPO_HORARIO "+ orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				CargaGrupoHorario horario = new CargaGrupoHorario();
				horario.mapeaReg(rs);
				lisHorario.add(horario);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.HorarioUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisHorario;
	}
	
	public ArrayList<CargaGrupoHorario> getLista(Connection conn, String cursoCargaId, String orden ) throws SQLException{
		
		ArrayList<CargaGrupoHorario> lisHorario	= new ArrayList<CargaGrupoHorario>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando		= "";
		
		try{
			comando = "SELECT "+
			"CURSO_CARGA_ID, SALON_ID, HORARIO, TIPO, VALIDA_CRUCE "+
			"FROM ENOC.CARGA_GRUPO_HORARIO "+ 
			"WHERE CURSO_CARGA_ID = '"+cursoCargaId+"' "+ orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){				
				CargaGrupoHorario horario = new CargaGrupoHorario();
				horario.mapeaReg(rs);
				lisHorario.add(horario);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.HorarioUtil|getLista|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisHorario;
	}
	
	public ArrayList<CargaGrupoHorario> getListaSalonesCargaId(Connection conn, String cursoCargaId, String salon, String orden ) throws SQLException{
		
		ArrayList<CargaGrupoHorario> lisHorario	= new ArrayList<CargaGrupoHorario>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando		= "";
		
		try{
			comando = "SELECT * FROM ENOC.CARGA_GRUPO_HORARIO "+ 
			"WHERE SUBSTR(CURSO_CARGA_ID, 0, 6) = '"+cursoCargaId.substring(0, 6)+"' AND SALON_ID = '"+salon+"' "+ orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){				
				CargaGrupoHorario horario = new CargaGrupoHorario();
				horario.mapeaReg(rs);
				lisHorario.add(horario);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.HorarioUtil|getListaSalonesCargaId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisHorario;
	}
	public ArrayList<CargaGrupoHorario> getListaSalonesCargaIdBloque(Connection conn, String cursoCargaId, String bloqueId, String salon, String orden ) throws SQLException{
		
		ArrayList<CargaGrupoHorario> lisHorario	= new ArrayList<CargaGrupoHorario>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando		= "";
		
		try{
			comando = " SELECT * FROM ENOC.CARGA_GRUPO_HORARIO "+
					  " WHERE SUBSTR(CURSO_CARGA_ID, 0, 6) = '"+cursoCargaId.substring(0, 6)+"' AND SALON_ID = '"+salon+"' "+
					  " AND GRUPO_BLOQUE(CURSO_CARGA_ID) = "+bloqueId+" "+ orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){				
				CargaGrupoHorario horario = new CargaGrupoHorario();
				horario.mapeaReg(rs);
				lisHorario.add(horario);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.HorarioUtil|getListaSalonesCargaId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisHorario;
	}
}