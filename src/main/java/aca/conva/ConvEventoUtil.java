// CLASE DE LA TABLA CONV_SOLICITUD
package aca.conva;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class ConvEventoUtil {
	
	
	public boolean insertReg(Connection conn, ConvEvento evento) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.CONV_EVENTO"+ 
				"(CONVALIDACION_ID, FECHA, USUARIO, CODIGO_PERSONAL, PLAN_ID, ESTADO, COMENTARIO, INSTITUCION, PROGRAMA, TIPO, DICTAMEN, TIPO_CONV, PERIODO) "+
				"VALUES( TO_NUMBER(?,'9999999'),TO_DATE(?,'DD/MM/YYYY'),?,?,?,?,?,?,?,?,?,?,?)");
			
			ps.setString(1, evento.getConvalidacionId());
			ps.setString(2, evento.getFecha());
			ps.setString(3, evento.getUsuario());
			ps.setString(4, evento.getCodigoPersonal());
			ps.setString(5, evento.getPlanId());
			ps.setString(6, evento.getEstado());
			ps.setString(7, evento.getComentario());
			ps.setString(8, evento.getInstitucion());
			ps.setString(9, evento.getPrograma());
			ps.setString(10, evento.getTipo());
			ps.setString(11, evento.getDictamen());
			ps.setString(12, evento.getTipoConv());
			ps.setString(13, evento.getPeriodo());
			
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.conva.ConvEvento|insertReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public boolean updateReg(Connection conn, ConvEvento evento ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.CONV_EVENTO "+ 
				"SET "+
				"FECHA = TO_DATE(?,'DD/MM/YYYY'), "+
				"USUARIO = ?, "+
				"CODIGO_PERSONAL = ?, "+
				"PLAN_ID = ?, "+
				"ESTADO = ?, "+				
				"COMENTARIO = ?, "+
				"INSTITUCION = ?, "+
				"PROGRAMA = ?, " +
				"TIPO = ?, " +
				"DICTAMEN = ?, " +
				"TIPO_CONV = ?, " +
				"PERIODO = ? " +
				"WHERE CONVALIDACION_ID = TO_NUMBER(?,'9999999') ");		
			
			ps.setString(1, evento.getFecha());
			ps.setString(2, evento.getUsuario());
			ps.setString(3, evento.getCodigoPersonal());
			ps.setString(4, evento.getPlanId());
			ps.setString(5, evento.getEstado());
			ps.setString(6, evento.getComentario());
			ps.setString(7, evento.getInstitucion());
			ps.setString(8, evento.getPrograma());
			ps.setString(9, evento.getTipo());
			ps.setString(10, evento.getDictamen());
			ps.setString(11, evento.getTipoConv());
			ps.setString(12, evento.getPeriodo());
			ps.setString(13, evento.getConvalidacionId());				
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.conva.ConvEvento|updateReg|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public boolean updateInstitucion(Connection conn, String institucion, String programa, String convalidacionId  ) throws Exception{
		
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.CONV_EVENTO "+ 
				"SET INSTITUCION = ?, "+
				"PROGRAMA = ? "+
				"WHERE CONVALIDACION_ID = TO_NUMBER(?,'9999999') ");
			ps.setString(1, institucion);
			ps.setString(2, programa);
			ps.setString(3, convalidacionId);
			
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.conva.ConvEvento|updateInstitucion|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public boolean updateDicCom(Connection conn, String dictamen, String comentario, String convalidacionId ) throws Exception{		
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.CONV_EVENTO"
					+ " SET DICTAMEN = ?,"
					+ " COMENTARIO = ?"
					+ " WHERE CONVALIDACION_ID = TO_NUMBER(?,'9999999')");
			ps.setString(1, dictamen);
			ps.setString(2, comentario);
			ps.setString(3, convalidacionId);		
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.conva.ConvEvento|updateDicCom|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}		
		return ok;
	}
	
	public boolean deleteReg(Connection conn, String convalidacionId ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.CONV_EVENTO "+ 
				"WHERE CONVALIDACION_ID = TO_NUMBER(?,'9999999')");
			ps.setString(1, convalidacionId);			
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.conva.ConvEvento|deleteReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public ConvEvento mapeaRegId( Connection conn, String convalidacionId ) throws SQLException{
		
		ConvEvento evento = new ConvEvento();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement("SELECT "+
				"CONVALIDACION_ID, TO_CHAR(FECHA,'DD/MM/YYYY') FECHA, USUARIO, CODIGO_PERSONAL, " +
				"PLAN_ID, ESTADO, COMENTARIO, INSTITUCION, PROGRAMA, TIPO, DICTAMEN, TIPO_CONV, PERIODO "+
				"FROM ENOC.CONV_EVENTO WHERE CONVALIDACION_ID = TO_NUMBER(?,'9999999')"); 
			ps.setString(1, convalidacionId);		
			
			rs = ps.executeQuery();
			if (rs.next()){
				evento.mapeaReg(rs);
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.conva.ConvEvento|mapeaRegId|:"+ex);
		}finally{
			if(rs!=null) rs.close();
			if(ps!=null) ps.close();
		}
		
		return evento;		
	}
	
	public boolean existeCovaliadacion(Connection conn, String convalidacionId) throws SQLException{
		boolean 		ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT CODIGO_PERSONAL FROM ENOC.CONV_EVENTO "+ 
				"WHERE CONVALIDACION_ID = TO_NUMBER(?,'9999999')");
			ps.setString(1, convalidacionId);
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.conva.ConvEvento|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public boolean existeMatricula(Connection conn, String matricula) throws SQLException{
		boolean 		ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT CODIGO_PERSONAL FROM ENOC.CONV_EVENTO "+ 
				"WHERE CODIGO_PERSONAL = ? ");
			ps.setString(1, matricula);
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.conva.ConvEvento|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public static boolean existeEvento(Connection conn, String codigoPersonal) throws SQLException{
		boolean 		ok 		= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT CODIGO_PERSONAL FROM ENOC.CONV_EVENTO "+ 
				"WHERE CODIGO_PERSONAL = ? ");
			ps.setString(1, codigoPersonal);
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.conva.ConvEvento|existeEvento|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public static boolean aplicaConvalidacion(Connection conn, String codigoPersonal, String planId) throws SQLException{
				
		PreparedStatement 	ps	= null;
		ResultSet 			rs	= null;
		boolean 			ok 	= false;
		int numCursos			= 0;
		
		try{
			ps = conn.prepareStatement("SELECT "+
				"COALESCE(COUNT(CODIGO_PERSONAL),0) AS NUMCURSOS "+
				"FROM ENOC.ALUMNO_CURSO "+
				"WHERE CODIGO_PERSONAL = ? " +
				"AND PLAN_ID = ? ");			
				// "AND TIPOCAL_ID != 'M'");
			ps.setString(1, codigoPersonal);
			ps.setString(2, planId);
			
			rs = ps.executeQuery();
			if (rs.next()){
				numCursos = rs.getInt("NUMCURSOS");
				if (numCursos<1)
					ok = true;
			}
			//if (ok==true) {System.out.println("");}
				
		}catch(Exception ex){
			System.out.println("Error - aca.conva.ConvEvento|aplicaConvalidacion|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public int getMaxReg(Connection conn) throws SQLException{
		ResultSet 		rs		= null;
 		PreparedStatement ps	= null;
 		int maximo				= 1;
 		
 		try{
 			
 			ps = conn.prepareStatement("SELECT MAX(CONVALIDACION_ID)+1 MAXIMO FROM ENOC.CONV_EVENTO "); 
 			
 			rs = ps.executeQuery();
 			if (rs.next())
 				maximo = rs.getInt("MAXIMO");
 			if(maximo == 0)
 				maximo = 1;
 			
 		}catch(Exception ex){
 			System.out.println("Error - aca.conva.ConvEvento|getMaxReg|:"+ex);
 		}finally{
	 		try { rs.close(); } catch (Exception ignore) { }
	 		try { ps.close(); } catch (Exception ignore) { }
 		}
 		
 		return maximo;
	}
	
	public static int numConvPorEstado(Connection conn, String facultad, String estado) throws SQLException{
		ResultSet 		rs		= null;
 		PreparedStatement ps	= null;
 		int total				= 0;
 		
 		try{
 			
 			ps = conn.prepareStatement("SELECT COUNT(CODIGO_PERSONAL) AS TOTAL FROM CONV_EVENTO"
 					+ " WHERE ENOC.FACULTAD(ENOC.CARRERA(PLAN_ID)) = '"+facultad+"'"
 					+ " AND ESTADO = '"+estado+"'"); 
 			
 			rs = ps.executeQuery();
 			if (rs.next())
 				total = rs.getInt("TOTAL");
 		}catch(Exception ex){
 			System.out.println("Error - aca.conva.ConvEvento|numConvPorEstado|:"+ex);
 		}finally{
	 		try { rs.close(); } catch (Exception ignore) { }
	 		try { ps.close(); } catch (Exception ignore) { }
 		}
 		
 		return total;
	}	
	
	public ArrayList<ConvEvento> getListAll(Connection conn, String orden ) throws SQLException{
		
		ArrayList<ConvEvento> lisEvento		= new ArrayList<ConvEvento>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
		
		try{
			comando = "SELECT CONVALIDACION_ID, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, USUARIO, CODIGO_PERSONAL, " +
					"PLAN_ID, ESTADO, COMENTARIO, INSTITUCION, PROGRAMA, TIPO, DICTAMEN, TIPO_CONV, PERIODO " +					
					"FROM ENOC.CONV_EVENTO "+orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				ConvEvento solicitud = new ConvEvento();
				solicitud.mapeaReg(rs);
				lisEvento.add(solicitud);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.conva.ConvEventoUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisEvento;
	}
	
	public ArrayList<ConvEvento> getListPorFechaTipo(Connection conn, String fechaIni, String fechaFin, String tipo, String orden) throws SQLException{
		ArrayList<ConvEvento> lisEvento		= new ArrayList<ConvEvento>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
		
		try{
			comando = " SELECT CONVALIDACION_ID, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, USUARIO, CODIGO_PERSONAL, "
					+ " PLAN_ID, ESTADO, COMENTARIO, INSTITUCION, PROGRAMA, TIPO, DICTAMEN, TIPO_CONV, PERIODO "
					+ " FROM ENOC.CONV_EVENTO "
					+ " WHERE TIPO IN("+tipo+") AND FECHA BETWEEN TO_DATE('"+fechaIni+"','DD/MM/YYYY') AND TO_DATE('"+fechaFin+"','DD/MM/YYYY')"+orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				ConvEvento solicitud = new ConvEvento();
				solicitud.mapeaReg(rs);
				lisEvento.add(solicitud);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.conva.ConvEventoUtil|getListPorFechaTipo|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return lisEvento;
	}
	
	public ArrayList<ConvEvento> getListAllRecientes(Connection conn, String periodoAnterior, String periodoActual, String orden ) throws SQLException{
		
		ArrayList<ConvEvento> lisEvento		= new ArrayList<ConvEvento>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
		
		try{
			comando = " SELECT CONVALIDACION_ID, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, USUARIO, CODIGO_PERSONAL, "
					+ " PLAN_ID, ESTADO, COMENTARIO, INSTITUCION, PROGRAMA, TIPO, DICTAMEN, TIPO_CONV, PERIODO "					
					+ " FROM ENOC.CONV_EVENTO "
					+ " WHERE CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM ENOC.ALUM_ESTADO WHERE SUBSTR(CARGA_ID,1,4) IN ('"+periodoAnterior+"','"+periodoActual+"')) "+orden;			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				ConvEvento solicitud = new ConvEvento();
				solicitud.mapeaReg(rs);
				lisEvento.add(solicitud);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.conva.ConvEventoUtil|getListAllRecientes|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisEvento;
	}
	
	public ArrayList<ConvEvento> getList(Connection conn, String convalidacionId, String orden ) throws SQLException{
		
		ArrayList<ConvEvento> lisEvento		= new ArrayList<ConvEvento>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
		
		try{
			comando = "SELECT CONVALIDACION_ID, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, USUARIO, CODIGO_PERSONAL, " +
					"PLAN_ID, ESTADO, COMENTARIO, INSTITUCION, PROGRAMA, TIPO, DICTAMEN, TIPO_CONV, PERIODO " +					
					"FROM ENOC.CONV_EVENTO WHERE CONVALIDACION_ID = '"+convalidacionId+"' "+orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				ConvEvento evento = new ConvEvento();
				evento.mapeaReg(rs);
				lisEvento.add(evento);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.conva.ConvEventoUtil|getList|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisEvento;
	}
	
	public static ArrayList<ConvEvento> getConvaVacio(Connection conn ) throws SQLException{
		
		ArrayList<ConvEvento> lisEvento		= new ArrayList<ConvEvento>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
		
		try{
			comando = "SELECT * FROM ENOC.CONV_EVENTO "
					+ " WHERE ESTADO = 'S'"
					+ " AND now() - FECHA > 7 "
					+ " AND CONVALIDACION_ID NOT IN (SELECT DISTINCT(CONVALIDACION_ID) FROM ENOC.CONV_MATERIA)"; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				ConvEvento evento = new ConvEvento();
				evento.mapeaReg(rs);
				lisEvento.add(evento);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.conva.ConvEventoUtil|getConvaVacio|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisEvento;
	}

		
	public ArrayList<ConvEvento> getListPersonal(Connection conn, String matricula, String orden ) throws SQLException{
		
		ArrayList<ConvEvento> lisEvento		= new ArrayList<ConvEvento>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
		
		try{
			comando = "SELECT CONVALIDACION_ID, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, USUARIO, CODIGO_PERSONAL, " +
					"PLAN_ID, ESTADO, COMENTARIO, INSTITUCION, PROGRAMA, TIPO, DICTAMEN, TIPO_CONV, PERIODO " +					
					"FROM ENOC.CONV_EVENTO WHERE CODIGO_PERSONAL = '"+matricula+"' "+orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				ConvEvento evento = new ConvEvento();
				evento.mapeaReg(rs);
				lisEvento.add(evento);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.conva.ConvEventoUtil|getListPersonal|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisEvento;
	}
	
	public ArrayList<ConvEvento> getSolicPorTipo(Connection conn, String matricula, String tipo, String orden ) throws SQLException{
		
		ArrayList<ConvEvento> lisEvento		= new ArrayList<ConvEvento>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
		
		try{
			comando = "	SELECT CONVALIDACION_ID, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, USUARIO, CODIGO_PERSONAL, " +
					  " PLAN_ID, ESTADO, COMENTARIO, INSTITUCION, PROGRAMA, TIPO, DICTAMEN, TIPO_CONV, PERIODO " +					
					  " FROM ENOC.CONV_EVENTO WHERE CODIGO_PERSONAL = '"+matricula+"' AND TIPO IN ("+tipo+") "+orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				ConvEvento evento = new ConvEvento();
				evento.mapeaReg(rs);
				lisEvento.add(evento);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.conva.ConvEventoUtil|getSolicPorTipo|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return lisEvento;
	}
	
	public ArrayList<ConvEvento> getListInscritos(Connection conn, String orden) throws SQLException{
		
		ArrayList<ConvEvento> lisEvento		= new ArrayList<ConvEvento>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
		
		try{
			comando = "SELECT CONVALIDACION_ID, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, USUARIO, CODIGO_PERSONAL, " +
					"PLAN_ID, ESTADO, COMENTARIO, INSTITUCION, PROGRAMA, TIPO, DICTAMEN, TIPO_CONV, PERIODO "+
					" FROM ENOC.CONV_EVENTO "+orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){				
				ConvEvento evento = new ConvEvento();
				evento.mapeaReg(rs);
				lisEvento.add(evento);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.conva.ConvEventoUtil|getListInscritos|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisEvento;
	}
	
	public ArrayList<ConvEvento> getListInscritosEstado(Connection conn,String estado, String orden) throws SQLException{
		
		ArrayList<ConvEvento> lisEvento		= new ArrayList<ConvEvento>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
		
		try{
			comando = "SELECT CONVALIDACION_ID, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, USUARIO, CODIGO_PERSONAL, " +
					"PLAN_ID, ESTADO, COMENTARIO, INSTITUCION, PROGRAMA, TIPO, DICTAMEN, TIPO_CONV, PERIODO "+
					" FROM ENOC.CONV_EVENTO WHERE ESTADO IN('"+estado+"') "+orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){				
				ConvEvento evento = new ConvEvento();
				evento.mapeaReg(rs);
				lisEvento.add(evento);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.conva.ConvEventoUtil|getListInscritosEstado|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisEvento;
	}
	
	public ArrayList<ConvEvento> getListEventosCumplidos(Connection conn, String orden ) throws SQLException{
		
		ArrayList<ConvEvento> lisEvento		= new ArrayList<ConvEvento>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
		
		try{
			comando = "SELECT CONVALIDACION_ID, TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA, USUARIO, CODIGO_PERSONAL," +
					 " PLAN_ID, ESTADO, COMENTARIO, INSTITUCION, PROGRAMA, TIPO, DICTAMEN, TIPO_CONV, PERIODO " +
					 " FROM ENOC.CONV_EVENTO" + 
					 " WHERE ESTADO = 'T'" +
					 " AND CONVALIDACION_ID NOT IN (SELECT CONVALIDACION_ID FROM ENOC.CONV_MATERIA" + 
					 							  " WHERE ESTADO <> 'R'" +
					 							  " AND ESTADO <> 'N')"+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				ConvEvento evento = new ConvEvento();
				evento.mapeaReg(rs);
				lisEvento.add(evento);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.conva.ConvEventoUtil|getListEventosCumplidos|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisEvento;
	}
	
	public ArrayList<ConvEvento> getListEventosRegInc(Connection conn, String orden ) throws SQLException{
		
		ArrayList<ConvEvento> lisEvento		= new ArrayList<ConvEvento>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
		
		try{
			comando = "SELECT CONVALIDACION_ID, TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA, USUARIO, CODIGO_PERSONAL," +
					 " PLAN_ID, ESTADO, COMENTARIO, INSTITUCION, PROGRAMA, TIPO, DICTAMEN, TIPO_CONV, PERIODO " +
					 " FROM ENOC.CONV_EVENTO" + 
					 " WHERE ESTADO = 'T'" +
					 " AND CONVALIDACION_ID IN (SELECT CONVALIDACION_ID FROM ENOC.CONV_MATERIA" + 
					 							  " WHERE ESTADO = 'R')"+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				ConvEvento evento = new ConvEvento();
				evento.mapeaReg(rs);
				lisEvento.add(evento);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.conva.ConvEventoUtil|getListEventosRegInc|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisEvento;
	}
	
	
	public ArrayList<ConvEvento> getListInscritosConv(Connection conn, String orden) throws SQLException{
			
		ArrayList<ConvEvento> lisEvento		= new ArrayList<ConvEvento>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
		
		try{
			comando = " SELECT CONVALIDACION_ID, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, USUARIO, CODIGO_PERSONAL," +
					" PLAN_ID, ESTADO, COMENTARIO, INSTITUCION, PROGRAMA, TIPO, DICTAMEN, TIPO_CONV, PERIODO " +
					" FROM ENOC.CONV_EVENTO WHERE CODIGO_PERSONAL" + 
					" IN (SELECT CODIGO_PERSONAL FROM ENOC.INSCRITOS WHERE INSCRITOS.CODIGO_PERSONAL = ENOC.CONV_EVENTO.CODIGO_PERSONAL)"+orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){				
				ConvEvento evento = new ConvEvento();
				evento.mapeaReg(rs);
				lisEvento.add(evento);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.conva.ConvEventoUtil|getListInscritosConv|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisEvento;
	}	
	
	public HashMap<String,String> mapConvPorFacultad(Connection conn ) throws SQLException{
		
		HashMap<String,String> map = new HashMap<String,String>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando				= "";
		String llave				= "";
		
		try{
			comando = " SELECT ENOC.FACULTAD(ENOC.CARRERA(PLAN_ID)) AS FACULTAD, ESTADO, COALESCE(COUNT(CODIGO_PERSONAL),0) AS TOTAL"
					+ " FROM ENOC.CONV_EVENTO"
					+ " GROUP BY ENOC.FACULTAD(ENOC.CARRERA(PLAN_ID)), ESTADO";
			rs = st.executeQuery(comando);
			while (rs.next()){				
				
				llave = rs.getString("FACULTAD")+rs.getString("ESTADO");
				map.put(llave, rs.getString("TOTAL"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.conva.ConvEventoUtil|mapConvPorFacultad|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return map;
	}
	
	public HashMap<String,String> mapConvPorFacultad(Connection conn, String fechaIni, String fechaFin, String tipo) throws SQLException{
		
		HashMap<String,String> map = new HashMap<String,String>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando				= "";
		String llave				= "";
		
		try{
			comando = " SELECT ENOC.FACULTAD(ENOC.CARRERA(PLAN_ID)) AS FACULTAD, ESTADO, COALESCE(COUNT(CODIGO_PERSONAL),0) AS TOTAL"
					+ " FROM ENOC.CONV_EVENTO"
					+ " WHERE FECHA BETWEEN TO_DATE('"+fechaIni+"','DD/MM/YYYY') AND TO_DATE('"+fechaFin+"','DD/MM/YYYY') AND TIPO IN("+tipo+")"
					+ " GROUP BY ENOC.FACULTAD(ENOC.CARRERA(PLAN_ID)), ESTADO";
			rs = st.executeQuery(comando);
			while (rs.next()){				
				
				llave = rs.getString("FACULTAD")+rs.getString("ESTADO");
				map.put(llave, rs.getString("TOTAL"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.conva.ConvEventoUtil|mapConvPorFacultad|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return map;
	}
	
	public HashMap<String,String> mapConvReciente(Connection conn, String periodoAnterior, String periodoActual) throws SQLException{
		
		HashMap<String,String> map = new HashMap<String,String>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando				= "";
		String llave				= "";
		
		try{
			comando = " SELECT ENOC.FACULTAD(ENOC.CARRERA(PLAN_ID)) AS FACULTAD, ESTADO, COALESCE(COUNT(CODIGO_PERSONAL),0) AS TOTAL"
					+ " 	FROM ENOC.CONV_EVENTO"
					+ " 	WHERE CODIGO_PERSONAL "
					+ " 		IN (SELECT CODIGO_PERSONAL FROM ENOC.ALUM_ESTADO WHERE SUBSTR(CARGA_ID,1,4) IN ('"+periodoAnterior+"','"+periodoActual+"'))"
					+ " 		GROUP BY ENOC.FACULTAD(ENOC.CARRERA(PLAN_ID)), ESTADO"; 
			rs = st.executeQuery(comando);
			while (rs.next()){				
				
				llave = rs.getString("FACULTAD")+rs.getString("ESTADO");
				map.put(llave, rs.getString("TOTAL"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.conva.ConvEventoUtil|mapConvReciente|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return map;
	}
	
	public HashMap<String,String> mapConvPorCarrera(Connection conn ) throws SQLException{
		
		HashMap<String,String> map = new HashMap<String,String>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando				= "";
		String llave				= "";
		
		try{
			comando = " SELECT ENOC.CARRERA(PLAN_ID) AS CARRERA, ESTADO, COALESCE(COUNT(CODIGO_PERSONAL),0) AS TOTAL"
					+ " FROM ENOC.CONV_EVENTO"
					+ " GROUP BY ENOC.CARRERA(PLAN_ID), ESTADO";
			rs = st.executeQuery(comando);
			while (rs.next()){				
				
				llave = rs.getString("CARRERA")+rs.getString("ESTADO");
				map.put(llave, rs.getString("TOTAL"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.conva.ConvEventoUtil|mapConvPorCarrera|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return map;
	}
	
	public HashMap<String,String> mapConvPorCarreraReciente(Connection conn, String periodoAnterior, String periodoActual) throws SQLException{
		
		HashMap<String,String> map = new HashMap<String,String>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando				= "";
		String llave				= "";
		
		try{
			comando = " SELECT ENOC.CARRERA(PLAN_ID) AS CARRERA, ESTADO, COALESCE(COUNT(CODIGO_PERSONAL),0) AS TOTAL "
					+ " FROM ENOC.CONV_EVENTO "
					+ " WHERE CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM ENOC.ALUM_ESTADO WHERE SUBSTR(CARGA_ID,1,4) IN ('"+periodoAnterior+"','"+periodoActual+"')) "
					+ " GROUP BY ENOC.CARRERA(PLAN_ID), ESTADO";
			rs = st.executeQuery(comando);
			while (rs.next()){				
				
				llave = rs.getString("CARRERA")+rs.getString("ESTADO");
				map.put(llave, rs.getString("TOTAL"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.conva.ConvEventoUtil|mapConvPorCarreraReciente|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return map;
	}
	
	public HashMap<String,String> mapConvPorFechaTipo(Connection conn, String fechaIni, String fechaFin, String tipo) throws SQLException{
		
		HashMap<String,String> map = new HashMap<String,String>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando				= "";
		String llave				= "";
		
		try{
			comando = " SELECT ENOC.CARRERA(PLAN_ID) AS CARRERA, ESTADO, COALESCE(COUNT(CODIGO_PERSONAL),0) AS TOTAL"
					+ " FROM ENOC.CONV_EVENTO "
					+ " WHERE FECHA BETWEEN TO_DATE('"+fechaIni+"','DD/MM/YYYY') AND TO_DATE('"+fechaFin+"','DD/MM/YYYY') AND TIPO IN("+tipo+") "
					+ " GROUP BY ENOC.CARRERA(PLAN_ID), ESTADO";
			rs = st.executeQuery(comando);
			while (rs.next()){				
				
				llave = rs.getString("CARRERA")+rs.getString("ESTADO");
				map.put(llave, rs.getString("TOTAL"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.conva.ConvEventoUtil|mapConvPorFechaTipo|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return map;
	}
	
}