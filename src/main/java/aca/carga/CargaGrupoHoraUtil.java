// Clase Util para la tabla de Carga
package aca.carga;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;


public class CargaGrupoHoraUtil{
	
	public boolean insertReg(Connection conn, CargaGrupoHora hora ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.CARGA_GRUPO_HORA"+ 
				"(CURSO_CARGA_ID, SALON_ID, HORARIO_ID, DIA, PERIODO, BLOQUE_ID ) "+
				"VALUES( ?, ?, TO_NUMBER(?,'9999999'), TO_NUMBER(?,'9'), TO_NUMBER(?,'99'), TO_NUMBER(?,'99'))");
			
			ps.setString(1, hora.getCursoCargaId());
			ps.setString(2, hora.getSalonId());
			ps.setString(3, hora.getHorarioId());
			ps.setString(4, hora.getDia());
			ps.setString(5, hora.getPeriodo());
			ps.setString(6, hora.getBloqueId());
			
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoHora|insertReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public boolean updateReg(Connection conn, String cursoCargaId, String bloqueId ) throws Exception{
		
		PreparedStatement ps = null;
		boolean ok = false;		
		try{
			ps = conn.prepareStatement("UPDATE ENOC.CARGA_GRUPO_HORA SET BLOQUE_ID = TO_NUMBER(?,'99') WHERE CURSO_CARGA_ID = ?");		
			
			ps.setString(1, bloqueId);
			ps.setString(2, cursoCargaId);
			
			if (ps.executeUpdate() >= 1)
				ok = true;				
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoHora|updateReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public boolean deleteReg(Connection conn, String cursoCargaId, String salonId, String horarioId, String periodoId, String bloqueId, String dia ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.CARGA_GRUPO_HORA"
					+ " WHERE CURSO_CARGA_ID = ?"
					+ " AND SALON_ID = ?"
					+ " AND HORARIO_ID = TO_NUMBER(?,'9999999')"
					+ " AND PERIODO = TO_NUMBER(?,'99')"
					+ " AND BLOQUE_ID = TO_NUMBER(?,'99')"
					+ " AND DIA = TO_NUMBER(?,'9')");		
			ps.setString(1, cursoCargaId);
			ps.setString(2, salonId);
			ps.setString(3, horarioId);			
			ps.setString(4, periodoId);			
			ps.setString(5, bloqueId);
			ps.setString(6, dia);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoHora|deleteReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean deleteReg(Connection conn, String cursoCargaId ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.CARGA_GRUPO_HORA "+ 
				"WHERE CURSO_CARGA_ID = ? ");
			ps.setString(1, cursoCargaId);
			
			if (ps.executeUpdate() >= 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoHora|deleteReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public CargaGrupoHora mapeaRegId( Connection conn, String cursoCargaId, String salonId, String horarioId, String dia, String periodo ) throws SQLException{
		
		CargaGrupoHora hora = new CargaGrupoHora();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement("SELECT "+
				"CURSO_CARGA_ID, SALON_ID, HORARIO_ID, DIA, PERIODO, BLOQUE_ID "+
				"FROM ENOC.CARGA_GRUPO_HORA "+ 
				"WHERE CURSO_CARGA_ID = ? "+
				"AND SALON_ID = ?" +
				"AND HORARIO_ID = TO_NUMBER(?,'9999999')" +
				"AND PERIODO = TO_NUMBER(?,'99')" +
				"AND BLOQUE_ID = TO_NUMBER(?,'99')" +
				"AND DIA = TO_NUMBER(?,'9')");
			ps.setString(1, cursoCargaId);
			ps.setString(2, salonId);
			
			rs = ps.executeQuery();
			if (rs.next()){
				hora.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoHora|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return hora;
	}
	
	public CargaGrupoHora mapeaRegCurso( Connection conn, String cursoCargaId ) throws SQLException{
		
		CargaGrupoHora hora = new CargaGrupoHora();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement("SELECT "+
				"CURSO_CARGA_ID, SALON_ID, HORARIO_ID, DIA, PERIODO, BLOQUE_ID "+
				"FROM ENOC.CARGA_GRUPO_HORA "+ 
				"WHERE CURSO_CARGA_ID = ?");
			ps.setString(1, cursoCargaId);
			
			rs = ps.executeQuery();
			if (rs.next()){
				hora.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoHora|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return hora;
	}
	
	public boolean existeReg(Connection conn, String cursoCargaId, String salonId, String horarioId, String periodo, String bloqueId, String dia) throws SQLException{
		boolean 			ok 	= false;
		ResultSet 			rs		= null;
		PreparedStatement 	ps		= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.CARGA_GRUPO_HORA "+ 
				"WHERE CURSO_CARGA_ID = ? "+
				"AND SALON_ID = ?" +
				"AND HORARIO_ID = TO_NUMBER(?,'9999999')" +
				"AND PERIODO = TO_NUMBER(?,'99')" +
				"AND BLOQUE_ID = TO_NUMBER(?,'99')" +
				"AND DIA = TO_NUMBER(?,'9')");
			ps.setString(1, cursoCargaId);
			ps.setString(2, salonId);
			ps.setString(3, horarioId);
			ps.setString(4, periodo);			
			ps.setString(5, bloqueId);
			ps.setString(6, dia);
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoHora|existeReg|:"+ex);
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
			ps = conn.prepareStatement("SELECT * FROM ENOC.CARGA_GRUPO_HORA "+ 
				"WHERE SALON_ID = ? ");
			ps.setString(1, salonId);
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoHora|existeSalon|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public static boolean existeCargaGrupo(Connection conn, String cursoCargaId) throws SQLException{
		boolean 			ok 		= false;
		ResultSet 			rs		= null;
		PreparedStatement 	ps		= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.CARGA_GRUPO_HORA "+ 
				"WHERE CURSO_CARGA_ID = ? ");
			ps.setString(1, cursoCargaId);
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoHora|existeCargaGrupo|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public static String numPeriodosMateria(Connection conn, String cursoCargaId) throws SQLException{
		PreparedStatement 	ps		= null;
		ResultSet 			rs		= null;
		String horas				= "0";
		
		try{
			ps = conn.prepareStatement("SELECT COALESCE(COUNT(CURSO_CARGA_ID),0) AS NUMHORAS FROM ENOC.CARGA_GRUPO_HORA WHERE CURSO_CARGA_ID = ?");
			ps.setString(1, cursoCargaId);
			
			rs = ps.executeQuery();
			if (rs.next())
				horas = rs.getString("NUMHORAS");			
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoHora|numPeriodosMateria|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return horas;
	}
		
	public ArrayList<CargaGrupoHora> getListAll(Connection conn, String orden ) throws SQLException{
		
		ArrayList<CargaGrupoHora> lisHorario	= new ArrayList<CargaGrupoHora>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando		= "";
		
		try{
			comando = "SELECT CURSO_CARGA_ID, SALON_ID, HORARIO_ID, PERIODO, DIA, BLOQUE_ID "+
					"FROM ENOC.CARGA_GRUPO_HORARIO "+ orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				CargaGrupoHora horario = new CargaGrupoHora();
				horario.mapeaReg(rs);
				lisHorario.add(horario);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoHoraUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisHorario;
	}
	
	public ArrayList<CargaGrupoHora> horarioSalonFacultad(Connection conn, String cargaId, String horarioId, String salonId, String orden ) throws SQLException{
		
		ArrayList<CargaGrupoHora> lisHorario	= new ArrayList<CargaGrupoHora>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando		= "";
		
		try{
			comando = " SELECT * FROM ENOC.CARGA_GRUPO_HORA CGH" +
					" WHERE (SELECT CARGA_ID FROM ENOC.CARGA_GRUPO WHERE CURSO_CARGA_ID = CGH.CURSO_CARGA_ID)= '"+cargaId+"' AND SALON_ID = '"+salonId+"' " +
					  " AND HORARIO_ID = '"+horarioId+"'"+ orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				CargaGrupoHora horario = new CargaGrupoHora();
				horario.mapeaReg(rs);
				lisHorario.add(horario);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoHoraUtil|horarioSalonFacultad|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisHorario;
	}
	
	public HashMap<String, ArrayList<CargaGrupoHora>> getPeriodoHashMap(Connection conn, String cargaId, String bloqueId, String horarioId, String salonId ) throws SQLException{
		
		HashMap<String, ArrayList<CargaGrupoHora>> lisHorario		= new HashMap<String, ArrayList<CargaGrupoHora>>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando			= "";
		String llave			= "";
		
		ArrayList<CargaGrupoHora> arrTmp = new ArrayList<CargaGrupoHora>(); 
		
		try{
			comando = " SELECT * FROM ENOC.CARGA_GRUPO_HORA CGH" +
					" WHERE (SELECT CARGA_ID FROM ENOC.CARGA_GRUPO WHERE CURSO_CARGA_ID = CGH.CURSO_CARGA_ID)= '"+cargaId+"' AND SALON_ID = '"+salonId+"' " +
					  " AND BLOQUE_ID = '"+bloqueId+"' AND HORARIO_ID = '"+horarioId+"' ORDER BY DIA , PERIODO"; 
			
			rs = st.executeQuery(comando);
			
			String llaveTmp = "";
			while (rs.next()){				
				CargaGrupoHora obj = new CargaGrupoHora();
				obj.mapeaReg(rs);
				llave = obj.getDia()+","+obj.getPeriodo();
			
				if(llaveTmp.equals("")){
					llaveTmp = llave;
				}
				
				if(!llaveTmp.equals(llave)){
					lisHorario.put(llaveTmp, arrTmp);
					arrTmp = new ArrayList<CargaGrupoHora>(); 
					llaveTmp = llave;
				}
				
				arrTmp.add(obj);				
			}
			
			lisHorario.put(llave, arrTmp);
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CargaGrupoHoraUtil|getPeriodoHashMap|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisHorario;
	}

	public HashMap<String, CargaGrupoHora> getPeriodiHashMapAlumno(Connection conn, String cargaId, String horarioId, String salonId ) throws SQLException{
		
		HashMap<String, CargaGrupoHora> lisHorario		= new HashMap<String, CargaGrupoHora>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando			= "";
		String llave			= "";
		
		try{
			comando = " SELECT * FROM ENOC.CARGA_GRUPO_HORA CGH" +
					" WHERE (SELECT CARGA_ID FROM ENOC.CARGA_GRUPO WHERE CURSO_CARGA_ID = CGH.CURSO_CARGA_ID)= '"+cargaId+"' AND HORARIO_ID = "+horarioId; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){				
				CargaGrupoHora obj = new CargaGrupoHora();
				obj.mapeaReg(rs);
				llave = obj.getDia()+","+obj.getPeriodo();
				lisHorario.put(llave, obj);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.catalogo.CargaGrupoHoraUtil|getPeriodiHashMapAlumno|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisHorario;
	}
	
	public ArrayList<String> horariosSalonFacultades(Connection conn, String cargaId, String horarioId, String salonId, String orden ) throws SQLException{
		
		ArrayList<String> lisHorario	= new ArrayList<String>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando		= "";
		
		try{
			comando = " SELECT DISTINCT(HORARIO_ID) AS HORARIO_ID FROM ENOC.CARGA_GRUPO_HORA CGH" +
					  " WHERE (SELECT CARGA_ID FROM ENOC.CARGA_GRUPO WHERE CURSO_CARGA_ID = CGH.CURSO_CARGA_ID)= '"+cargaId+"' AND SALON_ID = '"+salonId+"' "+ orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				lisHorario.add(rs.getString("HORARIO_ID"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoHoraUtil|horariosSalonFacultades|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisHorario;
	}
	
	public static ArrayList<String> horariosChocan(Connection conn, String cargaId, String salonId ) throws SQLException{
		
		ArrayList<String> lisHorario	= new ArrayList<String>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando		= "";
		
		try{
			comando = " SELECT " +
					  " CURSO_CARGA_ID, DIA||CHF.HORA_INICIO||CHF.MINUTO_INICIO AS INICIO, DIA||CHF.HORA_FINAL||CHF.MINUTO_FINAL AS FINAL " +
					  " FROM ENOC.CARGA_GRUPO_HORA CGH, ENOC.CAT_HORARIOFACULTAD CHF " +
					  " WHERE (SELECT CARGA_ID FROM ENOC.CARGA_GRUPO WHERE CURSO_CARGA_ID = CGH.CURSO_CARGA_ID) = '"+cargaId+"' " +
					  " AND SALON_ID = '"+salonId+"' " +
					  " AND CHF.HORARIO_ID = CGH.HORARIO_ID " +
					  " AND CHF.PERIODO = CGH.PERIODO ORDER BY INICIO, FINAL "; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				lisHorario.add(rs.getString("CURSO_CARGA_ID")+","+rs.getString("INICIO")+","+rs.getString("FINAL"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoHoraUtil|horariosChocan|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisHorario;
	}
	
	public static ArrayList<String> horariosSalon(Connection conn, String cargaId, String salonId, String bloqueId ) throws SQLException{
		
		ArrayList<String> lisHorario	= new ArrayList<String>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando		= "";
		
		try{
			comando = " SELECT " +
					  " CURSO_CARGA_ID, DIA||CHF.HORA_INICIO||CHF.MINUTO_INICIO AS INICIO, DIA||CHF.HORA_FINAL||CHF.MINUTO_FINAL AS FINAL " +
					  " FROM ENOC.CARGA_GRUPO_HORA CGH, ENOC.CAT_HORARIOFACULTAD CHF " +
					  " WHERE (SELECT CARGA_ID FROM ENOC.CARGA_GRUPO WHERE CURSO_CARGA_ID = CGH.CURSO_CARGA_ID) = '"+cargaId+"' " +
					  " AND SALON_ID = '"+salonId+"' " +
					  "	AND BLOQUE_ID = '"+bloqueId+" '"+
					  " AND CHF.HORARIO_ID = CGH.HORARIO_ID " +
					  " AND CHF.PERIODO = CGH.PERIODO ORDER BY INICIO, FINAL "; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				lisHorario.add(rs.getString("CURSO_CARGA_ID")+","+rs.getString("INICIO")+","+rs.getString("FINAL"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoHoraUtil|horariosSalon|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisHorario;
	}
	
	public ArrayList<String> MateriasChocanInscripcion(Connection conn, String cargaId, String cursoCargaId ) throws SQLException{
			
			ArrayList<String> lisHorario	= new ArrayList<String>();
			Statement st 				= conn.createStatement();
			ResultSet rs 				= null;
			String comando		= "";
			
			try{
				comando = " SELECT " +
						  " CURSO_CARGA_ID, DIA||CHF.HORA_INICIO||CHF.MINUTO_INICIO AS INICIO, DIA||CHF.HORA_FINAL||CHF.MINUTO_FINAL AS FINAL " +
						  " FROM ENOC.CARGA_GRUPO_HORA CGH, ENOC.CAT_HORARIOFACULTAD CHF " +
						  " WHERE (SELECT CARGA_ID FROM ENOC.CARGA_GRUPO WHERE CURSO_CARGA_ID = CGH.CURSO_CARGA_ID) = '"+cargaId+"' " +
						  " AND CGH.CURSO_CARGA_ID = '"+cursoCargaId+"' " +
						  " AND CHF.HORARIO_ID = CGH.HORARIO_ID " +
						  " AND CHF.PERIODO = CGH.PERIODO ORDER BY INICIO, FINAL "; 
				
				rs = st.executeQuery(comando);
				while (rs.next()){
					
					lisHorario.add(rs.getString("CURSO_CARGA_ID")+","+rs.getString("INICIO")+","+rs.getString("FINAL"));
				}
				
			}catch(Exception ex){
				System.out.println("Error - aca.carga.CargaGrupoHoraUtil|horariosChocan|:"+ex);
			}finally{
				try { rs.close(); } catch (Exception ignore) { }
				try { st.close(); } catch (Exception ignore) { }
			}
			
			return lisHorario;
		}
	
	public ArrayList<String> salonesDelGrupo(Connection conn, String cursoCargaId, String orden ) throws SQLException{
		
		ArrayList<String> lisSalon	= new ArrayList<String>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando		= "";
		
		try{
			comando = " SELECT DISTINCT(SALON_ID) AS SALON FROM ENOC.CARGA_GRUPO_HORA WHERE CURSO_CARGA_ID = '"+cursoCargaId+"' "+ orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){				
				lisSalon.add(rs.getString("SALON"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoHoraUtil|salonesDelGrupo|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisSalon;
	}
	
	public static String getInicioFinal(Connection conn, String horarioId, String periodo ) throws SQLException{
		
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		String nombre			= "1";
		
		try{
			ps = conn.prepareStatement(" SELECT HORA_INICIO||MINUTO_INICIO AS INICIO, HORA_FINAL||MINUTO_FINAL AS FINAL " +
									   " FROM ENOC.CAT_HORARIOFACULTAD WHERE PERIODO = '"+periodo+"' AND HORARIO_ID = '"+horarioId+"' "); 
			rs = ps.executeQuery();
			if (rs.next())
				nombre = rs.getString("INICIO")+","+rs.getString("FINAL");	
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoHoraUtil|getInicioFinal|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return nombre;
	}
	
	public static String getHorarioPrincipal(Connection conn, String codigoPersonal, String cargaId ) throws SQLException{
		
		PreparedStatement ps	= null;	
		ResultSet 		rs		= null;		
		String horario			= "0";
		int maximo				= 0;
		
		try{
			ps = conn.prepareStatement(" SELECT HORARIO_ID, COALESCE(COUNT(HORARIO_ID),0) AS TOTAL FROM ENOC.CARGA_GRUPO_HORA"+ 
					" WHERE CURSO_CARGA_ID IN"+
					"	(SELECT CURSO_CARGA_ID FROM ENOC.KRDX_CURSO_ACT " +
					"	WHERE CODIGO_PERSONAL = '"+codigoPersonal+"' " +
					"	AND CARGA_DEL_GRUPO(CURSO_CARGA_ID)='"+cargaId+"'" +
					"	AND TIPOCAL_ID IN ('M','I'))" +
					" GROUP BY HORARIO_ID"); 
			rs = ps.executeQuery();
			while(rs.next()){
				if (rs.getInt("TOTAL") > maximo){
					horario 	= rs.getString("HORARIO_ID");
					maximo 		= rs.getInt("TOTAL");
				}
			}	
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoHoraUtil|getHorarioPrincipal|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return horario;
	}
	
	public ArrayList<String> horariosAlumno(Connection conn, String codigoPersonal, String cargaId ) throws SQLException{
		
		ArrayList<String> lisSalon	= new ArrayList<String>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando		= "";
		
		try{
			comando = " SELECT HORARIO_ID, COALESCE(COUNT(HORARIO_ID),0) AS TOTAL FROM ENOC.CARGA_GRUPO_HORA"+ 
					" WHERE CURSO_CARGA_ID IN"+
					"	(SELECT CURSO_CARGA_ID FROM ENOC.KRDX_CURSO_ACT " +
					"	WHERE CODIGO_PERSONAL = '"+codigoPersonal+"' " +
					"	AND CARGA_DEL_GRUPO(CURSO_CARGA_ID)='"+cargaId+"'" +
					"	AND TIPOCAL_ID IN ('M','I'))" +
					" GROUP BY HORARIO_ID";
			
			rs = st.executeQuery(comando);
			while (rs.next()){				
				lisSalon.add(rs.getString("HORARIO_ID"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoHoraUtil|horariosAlumno|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisSalon;
	}
	
	public static String getHorarioPrincipalMaestro(Connection conn, String codigoPersonal, String cargaId ) throws SQLException{
		
		PreparedStatement ps	= null;	
		ResultSet 		rs		= null;		
		String horario			= "0";
		int maximo				= 0;
		
		try{
			ps = conn.prepareStatement(" SELECT HORARIO_ID, COALESCE(COUNT(HORARIO_ID),0) AS TOTAL FROM ENOC.CARGA_GRUPO_HORA"+ 
					" WHERE CURSO_CARGA_ID IN"+
					" (SELECT CURSO_CARGA_ID FROM ENOC.CARGA_GRUPO " +
					" WHERE CODIGO_PERSONAL = '"+codigoPersonal+"' " +
					" AND CARGA_ID='"+cargaId+"')" +
					" GROUP BY HORARIO_ID"); 
			rs = ps.executeQuery();
			while(rs.next()){
				if (rs.getInt("TOTAL") > maximo){
					horario 	= rs.getString("HORARIO_ID");
					maximo 		= rs.getInt("TOTAL");
				}
			}	
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoHoraUtil|getHorarioPrincipalMaestro|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return horario;
	}
}