//Clase Util para la tabla de Actividad
package aca.carga;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class CargaGrupoActividadUtil{
	
	public boolean insertReg(Connection conn, CargaGrupoActividad actividad ) throws Exception{
		PreparedStatement ps 	= null;		
		boolean ok 				= false;
		
		try{		
			
			actividad.setActividadId( maximoReg(conn));
			ps = conn.prepareStatement("INSERT INTO ENOC.CARGA_GRUPO_ACTIVIDAD(" + 
				" ACTIVIDAD_ID, CURSO_CARGA_ID, EVALUACION_ID," +
				" NOMBRE, VALOR, FECHA, ACTIVIDAD_E42, AGENDADA_E42,ENVIAR)"+
				" VALUES(TO_NUMBER(?,'9999999')," +
				" ?," +
				" TO_NUMBER(?,'99')," +
				" ?," +
				" TO_NUMBER(?,'999.99')," +
				" TO_DATE(?,'DD/MM/YYYY HH24:MI:SS')," +
				" TO_NUMBER(?,'9999999'),?,?)");
			ps.setString(1, actividad.getActividadId());
			ps.setString(2, actividad.getCursoCargaId());
			ps.setString(3, actividad.getEvaluacionId());
			ps.setString(4, actividad.getNombre());
			ps.setString(5, actividad.getValor());
			ps.setString(6, actividad.getFecha());
			ps.setString(7, actividad.getActividadE42());
			ps.setString(8, actividad.getAgendadaE42());
			ps.setString(9, actividad.getEnviar());
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoActividad|insertReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }			
		}
		
		return ok;
	}	
	
	public boolean updateReg(Connection conn, CargaGrupoActividad actividad ) throws Exception{
		PreparedStatement ps 	= null;	
		boolean ok 				= false;
		try{			
			ps = conn.prepareStatement( "UPDATE ENOC.CARGA_GRUPO_ACTIVIDAD" 
				+ " SET CURSO_CARGA_ID = ?,"
				+ " EVALUACION_ID = TO_NUMBER(?,'99'),"
				+ " NOMBRE = ?,"
				+ " VALOR = TO_NUMBER(?,'999.99'),"
				+ " FECHA = TO_DATE(?,'DD/MM/YYYY HH24:MI:SS'),"
				+ " ACTIVIDAD_E42 = TO_NUMBER(?,'9999999'),"
				+ " AGENDADA_E42 = ?,"
				+ " ENVIAR = ?"
				+ " WHERE ACTIVIDAD_ID = TO_NUMBER(?,'9999999')");			
			ps.setString(1, actividad.getCursoCargaId());
			ps.setString(2, actividad.getEvaluacionId());
			ps.setString(3, actividad.getNombre());
			ps.setString(4, actividad.getValor());
			ps.setString(5, actividad.getFecha());
			ps.setString(6, actividad.getActividadE42());
			ps.setString(7, actividad.getAgendadaE42());
			ps.setString(8, actividad.getEnviar());
			ps.setString(9, actividad.getActividadId());		
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoActividad|updateReg|:"+ex);		 
		}finally{
			try { ps.close(); } catch (Exception ignore) { }			
		}
		
		return ok;
	}
	
	public boolean updateRegE42(Connection conn, CargaGrupoActividad actividad ) throws Exception{
		boolean ok = false;
		Statement st 			= null;
		ResultSet rs 			= null;
		try{
			st 			= conn.createStatement();
			rs 			= null;
			
			String comando	= " UPDATE ENOC.CARGA_GRUPO_ACTIVIDAD"				 
							+ " SET NOMBRE = '"+actividad.getNombre()+"',"
							+ " VALOR  = "+actividad.getValor()+","
							+ " FECHA = TO_DATE('"+actividad.getFecha()+"','DD/MM/YYYY HH24:MI:SS'),"
							+ " WHERE ACTIVIDAD_E42 = "+ actividad.getActividadE42();
			rs = st.executeQuery(comando);
			
			if (rs.next())
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoActividad|updateRegE42|:"+ex);		 
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public boolean deleteReg(Connection conn, String actividadId ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.CARGA_GRUPO_ACTIVIDAD"+ 
				" WHERE ACTIVIDAD_ID = ? ");
			ps.setLong(1, Long.parseLong(actividadId));
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;		
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoActividad|deleteReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean deleteRegE42(Connection conn, String actividadE42 ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.CARGA_GRUPO_ACTIVIDAD"+ 
				" WHERE ACTIVIDAD_E42 = ? ");
			ps.setLong(1, Long.parseLong(actividadE42));
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoActividad|deleteRegE42|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public CargaGrupoActividad mapeaRegId( Connection conn, String actividadId) throws SQLException{
		
		CargaGrupoActividad actividad = new CargaGrupoActividad();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement("SELECT"+
				" ACTIVIDAD_ID, CURSO_CARGA_ID, EVALUACION_ID,"+
				" NOMBRE, VALOR, TO_CHAR(FECHA,'DD/MM/YYYY HH24:MI:SS') AS FECHA, ACTIVIDAD_E42, AGENDADA_E42, ENVIAR"+
				" FROM ENOC.CARGA_GRUPO_ACTIVIDAD"+	 
				" WHERE ACTIVIDAD_ID = ?");
			ps.setLong(1, Long.parseLong(actividadId));
			
			rs = ps.executeQuery();
			if (rs.next()){
				actividad.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoActividad|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return actividad;
	}
	
	public CargaGrupoActividad mapeaRegIdE42( Connection conn, String actividadE42) throws SQLException{
		
		CargaGrupoActividad actividad = new CargaGrupoActividad();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement("SELECT"+
				" ACTIVIDAD_ID, CURSO_CARGA_ID, EVALUACION_ID,"+
				" NOMBRE, VALOR, TO_CHAR(FECHA,'DD/MM/YYYY HH24:MI:SS') AS FECHA, ACTIVIDAD_E42, AGENDADA_E42, ENVIAR"+
				" FROM ENOC.CARGA_GRUPO_ACTIVIDAD"+	 
				" WHERE ACTIVIDAD_E42 = ?");
			ps.setLong(1, Long.parseLong(actividadE42));
			
			rs = ps.executeQuery();
			if (rs.next()){
				actividad.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoActividad|mapeaRegIdE42|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return actividad;
	}
	
	public boolean existeReg(Connection conn, String actividadId, String cursoCargaId) throws SQLException{
		boolean 			ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps= null;
		
		try{
			ps = conn.prepareStatement("SELECT ACTIVIDAD_ID FROM ENOC.CARGA_GRUPO_ACTIVIDAD"+ 
				" WHERE ACTIVIDAD_ID = ? AND CURSO_CARGA_ID = ?");
			ps.setLong(1, Long.parseLong(actividadId));
			ps.setString(2, cursoCargaId);
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoActividad|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public boolean existeRegId(Connection conn, String actividadId) throws SQLException{
		boolean 			ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps= null;
		
		try{
			ps = conn.prepareStatement("SELECT ACTIVIDAD_ID FROM ENOC.CARGA_GRUPO_ACTIVIDAD"+ 
				" WHERE ACTIVIDAD_ID = ?");
			ps.setLong(1, Long.parseLong(actividadId));
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoActividad|existeRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public boolean existeRegIdE42(Connection conn, String actividadE42) throws SQLException{
		boolean 			ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps= null;
		
		try{
			ps = conn.prepareStatement("SELECT ACTIVIDAD_E42 FROM ENOC.CARGA_GRUPO_ACTIVIDAD"+ 
				" WHERE ACTIVIDAD_E42 = ?");
			ps.setLong(1, Long.parseLong(actividadE42));
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoActividad|existeRegIdE42|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public String maximoReg(Connection conn) throws SQLException{
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		String maximo			= "1";
		
		try{
			ps = conn.prepareStatement("SELECT COALESCE(MAX(ACTIVIDAD_ID)+1,1) AS MAXIMO"+
				" FROM ENOC.CARGA_GRUPO_ACTIVIDAD "); 
			
			rs = ps.executeQuery();
			if (rs.next())
				maximo = String.valueOf(rs.getLong("MAXIMO"));			
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoActividad|maximotReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return maximo;
	}
	
	public static int getNumAct(Connection conn, String cursoCargaId) throws SQLException{
		
		PreparedStatement ps	= null;
		ResultSet rs			= null;
		int numAct				= 0;
		
		try{
			ps = conn.prepareStatement("SELECT COALESCE(COUNT(ACTIVIDAD_ID),0) AS NUMACT"+
				" FROM ENOC.CARGA_GRUPO_ACTIVIDAD WHERE CURSO_CARGA_ID = ?"); 
			ps.setString(1, cursoCargaId);
			
			rs = ps.executeQuery();
			if (rs.next())
				numAct = rs.getInt("NUMACT");			
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoActividad|getNumAct|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return numAct;
	}
	
	public static int deleteGpoAct(Connection conn, String cursoCargaId ) throws SQLException{
		
		PreparedStatement ps	= null;
		int numAct				= 0;
		
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.CARGA_GRUPO_ACTIVIDAD WHERE CURSO_CARGA_ID = ?"); 
			ps.setString(1, cursoCargaId);
			numAct = ps.executeUpdate();
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoActividad|deleteGpoAct|:"+ex);
		}finally{			
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return numAct;
	}
		
	public ArrayList<CargaGrupoActividad> getListAll(Connection conn, String orden ) throws SQLException{
		
		ArrayList<CargaGrupoActividad> lisActividad	= new ArrayList<CargaGrupoActividad>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
		
		try{
			comando = "SELECT ACTIVIDAD_ID, CURSO_CARGA_ID, EVALUACION_ID,"+
					" NOMBRE, VALOR, TO_CHAR(FECHA,'DD/MM/YYYY HH24:MI:SS') AS FECHA, ACTIVIDAD_E42, AGENDADA_E42, ENVIAR"+
					" FROM ENOC.CARGA_GRUPO_ACTIVIDAD "+orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				CargaGrupoActividad actividad = new CargaGrupoActividad();
				actividad.mapeaReg(rs);
				lisActividad.add(actividad);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoActividadUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisActividad;
	}
	
	public ArrayList<CargaGrupoActividad> getListCurso(Connection conn, String cursoCargaId, String orden) throws SQLException{
		
		ArrayList<CargaGrupoActividad> lisActividad	= new ArrayList<CargaGrupoActividad>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
		
		try{
			comando = "SELECT ACTIVIDAD_ID, CURSO_CARGA_ID, EVALUACION_ID,"+
					" NOMBRE, VALOR, TO_CHAR(FECHA,'DD/MM/YYYY HH24:MI:SS') AS FECHA, ACTIVIDAD_E42, AGENDADA_E42, ENVIAR"+
					" FROM ENOC.CARGA_GRUPO_ACTIVIDAD" + 
					" WHERE CURSO_CARGA_ID = '"+cursoCargaId+"' "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				CargaGrupoActividad actividad = new CargaGrupoActividad();
				actividad.mapeaReg(rs);
				lisActividad.add(actividad);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoActividadUtil|getListCurso|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisActividad;
	}
	
	public ArrayList<CargaGrupoActividad> getListCargaEvaluacion(Connection conn, String cursoCarga, String evaluacion ) throws SQLException{
		
		ArrayList<CargaGrupoActividad> lisActividad	= new ArrayList<CargaGrupoActividad>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
		
		try{
			comando = "SELECT ACTIVIDAD_ID, CURSO_CARGA_ID, EVALUACION_ID,"+
				" NOMBRE, VALOR, TO_CHAR(FECHA,'DD/MM/YYYY HH24:MI:SS') AS FECHA, ACTIVIDAD_E42, AGENDADA_E42, ENVIAR"+
				" FROM ENOC.CARGA_GRUPO_ACTIVIDAD"+ 
				" WHERE CURSO_CARGA_ID = '"+cursoCarga+"'" +
				" AND EVALUACION_ID = "+evaluacion+
				" ORDER BY FECHA, ACTIVIDAD_ID";

			rs = st.executeQuery(comando);

			while (rs.next()){
				
				CargaGrupoActividad actividad = new CargaGrupoActividad();
				actividad.mapeaReg(rs);
				lisActividad.add(actividad);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoActividadUtil|getListCargaEvaluacion|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisActividad;
	}
	
	public boolean evalTieneActiv(Connection conn, String cursoCargaId, String evaluacionId ) throws SQLException{
		
		boolean ok				= false;
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando			= "";
		
		try{
			comando = " SELECT ACTIVIDAD_ID FROM ENOC.CARGA_GRUPO_ACTIVIDAD"
					+ " WHERE CURSO_CARGA_ID = '"+cursoCargaId+"'"
					+ " AND EVALUACION_ID = "+evaluacionId;
			rs = st.executeQuery(comando);

			if(rs.next())
				ok = true;
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoActividadUtil|evalTieneActiv|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public String getNombre(Connection conn, String actividadId ) throws SQLException{
		
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando		= "";
		String nombre			= "x";
		
		try{
			comando = "SELECT NOMBRE FROM ENOC.CARGA_GRUPO_ACTIVIDAD WHERE ACTIVIDAD_ID = "+actividadId;
			
			rs = st.executeQuery(comando);
			if (rs.next()){				
				nombre = rs.getString("NOMBRE");
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoActividadUtil|getNombre|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return nombre;
	}
	
	public HashMap<String, ArrayList<String>> getActividadesMes(Connection conn, String cargaId, String orden) throws SQLException{
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		HashMap<String, ArrayList<String>> lis = new HashMap<String, ArrayList<String>>();
		try{
			comando = "SELECT CURSO_CARGA_ID, EVALUACION_ID, COUNT(ACTIVIDAD_ID) AS ACTIVIDADES" +
					" FROM ENOC.CARGA_GRUPO_ACTIVIDAD WHERE SUBSTR(CURSO_CARGA_ID,1,6) = '"+cargaId+"'" +
					" GROUP BY EVALUACION_ID, CURSO_CARGA_ID "+orden;
			
			rs = st.executeQuery(comando);
			while(rs.next()){
				ArrayList<String> lisAct = new ArrayList<String>();
				lisAct.add(rs.getString("ACTIVIDADES"));
				
				lis.put(rs.getString("CURSO_CARGA_ID")+"/"+rs.getString("EVALUACION_ID"), lisAct);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoActividadUtil|getActividadesMes|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lis;
		
	}
	
	// Cuenta las actividades agendades por materia
	public HashMap<String, String> mapActividadesAgendadas(Connection conn, String cargaId, String agendada, String fecha ) throws SQLException{
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		HashMap<String, String> mapa = new HashMap<String, String>();
		try{
			comando = " SELECT CURSO_CARGA_ID, COUNT(ACTIVIDAD_ID) AS TOTAL"
					+ " FROM ENOC.CARGA_GRUPO_ACTIVIDAD"
					+ " WHERE SUBSTR(CURSO_CARGA_ID,1,6) = '"+cargaId+"'"
					+ " AND AGENDADA_E42 = '"+agendada+"'"
					+ " AND FECHA < TO_DATE('"+fecha+"','DD/MM/YYYY HH24:MI:SS')"
					+ " GROUP BY CURSO_CARGA_ID";
			
			rs = st.executeQuery(comando);
			while(rs.next()){				
				mapa.put(rs.getString("CURSO_CARGA_ID"), rs.getString("TOTAL"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoActividadUtil|mapActividadesAgendadas|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return mapa;
		
	}
	
	public HashMap<String, String> mapActividadesAgendadas(Connection conn, String cargaId, String agendada ) throws SQLException{
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		HashMap<String, String> mapa = new HashMap<String, String>();
		try{
			String comando = " SELECT CURSO_CARGA_ID, COUNT(ACTIVIDAD_ID) AS TOTAL"
					+ " FROM ENOC.CARGA_GRUPO_ACTIVIDAD"
					+ " WHERE SUBSTR(CURSO_CARGA_ID,1,6) = '"+cargaId+"'"
					+ " AND AGENDADA_E42 = '"+agendada+"'"					
					+ " GROUP BY CURSO_CARGA_ID";			
			rs = st.executeQuery(comando);
			while(rs.next()){				
				mapa.put(rs.getString("CURSO_CARGA_ID"), rs.getString("TOTAL"));
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoActividadUtil|mapActividadesAgendadas|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return mapa;
		
	}
	
	public HashMap<String, String> mapAgendadasPorMeses(Connection conn, String cargaId, String agendada ) throws SQLException{
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		HashMap<String, String> mapa = new HashMap<String, String>();
		try{
			String comando = " SELECT CURSO_CARGA_ID, TO_CHAR(FECHA,'MM') AS MES, COUNT(ACTIVIDAD_ID) AS TOTAL"
					+ " FROM ENOC.CARGA_GRUPO_ACTIVIDAD"
					+ " WHERE SUBSTR(CURSO_CARGA_ID,1,6) = '"+cargaId+"'"
					+ " AND AGENDADA_E42 = '"+agendada+"'"					
					+ " GROUP BY CURSO_CARGA_ID, TO_CHAR(FECHA,'MM')";			
			rs = st.executeQuery(comando);
			while(rs.next()){				
				mapa.put(rs.getString("CURSO_CARGA_ID")+rs.getString("MES"), rs.getString("TOTAL"));
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoActividadUtil|mapAgendadasPorMeses|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return mapa;
		
	}
	
	public HashMap<String, String> mapaMesesPorCarga(Connection conn, String cargaId) throws SQLException{
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		HashMap<String, String> mapa = new HashMap<String, String>();
		try{
			String comando = " SELECT ENOC.FACULTAD(CARRERA_ID), TO_CHAR(CGA.FECHA,'MM') AS MES, COUNT(CGA.CURSO_CARGA_ID) AS TOTAL" 
					+ " FROM ENOC.CARGA_GRUPO CG, CARGA_GRUPO_ACTIVIDAD CGA" 
					+ " WHERE CG.CARGA_ID = '"+cargaId+"' " 
					+ " AND CGA.CURSO_CARGA_ID = CG.CURSO_CARGA_ID" 
					+ " GROUP BY ENOC.FACULTAD(CARRERA_ID),TO_CHAR(CGA.FECHA,'MM')";			
			rs = st.executeQuery(comando);
			while(rs.next()){				
				mapa.put(rs.getString("ENOC.FACULTAD(CARRERA_ID)")+rs.getString("MES"), rs.getString("TOTAL"));
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoActividadUtil|mapaMesesPorCarga|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}

		return mapa;
	}
		
		public HashMap<String, String> mapaMesesPorCargas(Connection conn, String cargaId) throws SQLException{
			Statement st 		= conn.createStatement();
			ResultSet rs 		= null;
			HashMap<String, String> mapa = new HashMap<String, String>();
			try{
				String comando = " SELECT CG.CARGA_ID, TO_CHAR(CGA.FECHA,'MM') AS MES, COUNT(CGA.CURSO_CARGA_ID) AS TOTAL" 
						+ " FROM ENOC.CARGA_GRUPO CG, CARGA_GRUPO_ACTIVIDAD CGA" 
						+ " WHERE CGA.CURSO_CARGA_ID = CG.CURSO_CARGA_ID" 
						+ " GROUP BY CG.CARGA_ID,TO_CHAR(CGA.FECHA,'MM')";			
				rs = st.executeQuery(comando);
				while(rs.next()){				
					mapa.put(rs.getString("CARGA_ID")+rs.getString("MES"), rs.getString("TOTAL"));
				}			
			}catch(Exception ex){
				System.out.println("Error - aca.carga.CargaGrupoActividadUtil|mapaMesesPorCargas|:"+ex);
			}finally{
				try { rs.close(); } catch (Exception ignore) { }
				try { st.close(); } catch (Exception ignore) { }
			}
			
			return mapa;
		
	}
}