// CLASE DE LA TABLA CONV_SOLICITUD
package aca.conva;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class ConvMateriaUtil {
	
	public boolean insertReg(Connection conn, ConvMateria materia ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.CONV_MATERIA"+ 
				"(CONVALIDACION_ID, CURSO_ID, FECHA, TIPO, ESTADO, MATERIA_O, CREDITOS_O, NOTA_O, FECHA_NOTA, FOLIO) "+
				"VALUES( TO_NUMBER(?,'9999999'),?,TO_DATE(?,'DD/MM/YYYY'),?,?,?,TO_NUMBER(?,'99'), ?, TO_DATE(?,'DD/MM/YYYY'), TO_NUMBER(?,'999'))");
			
			ps.setString(1, materia.getConvalidacionId());
			ps.setString(2, materia.getCursoId());
			ps.setString(3, materia.getFecha());
			ps.setString(4, materia.getTipo());
			ps.setString(5, materia.getEstado());
			ps.setString(6, materia.getMateria_O());
			ps.setString(7, materia.getCreditos_O());
			ps.setString(8, materia.getNota_O());
			ps.setString(9, materia.getfNota());
			ps.setString(10, materia.getFolio());
			
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.conva.ConvMateriaUtil|insertReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public boolean updateReg(Connection conn, ConvMateria materia ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.CONV_MATERIA "+ 
				"SET FECHA = TO_DATE(?,'DD/MM/YYYY'), "+
				"TIPO = ?, "+
				"ESTADO = ?, "+
				"MATERIA_O = ?, " +
				"CREDITOS_O = TO_NUMBER(?,'99'), " +
				"NOTA_O = ?, " +
				"FECHA_NOTA = TO_DATE(?,'DD/MM/YYYY'), FOLIO = TO_NUMBER(?,'999') "+
				"WHERE CONVALIDACION_ID = TO_NUMBER(?,'9999999') " +
				"AND CURSO_ID = ?");
			
			ps.setString(1, materia.getFecha());
			ps.setString(2, materia.getTipo());
			ps.setString(3, materia.getEstado());
			ps.setString(4, materia.getMateria_O());
			ps.setString(5, materia.getCreditos_O());
			ps.setString(6, materia.getNota_O());
			ps.setString(7, materia.getfNota());
			ps.setString(8, materia.getFolio());
			ps.setString(9, materia.getConvalidacionId());
			ps.setString(10, materia.getCursoId());	
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.conva.ConvMateriaUtil|updateReg|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public boolean deleteReg(Connection conn, String convalidacionId, String cursoId ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.CONV_MATERIA WHERE CONVALIDACION_ID = TO_NUMBER(?,'9999999') AND CURSO_ID = ?");
			ps.setString(1, convalidacionId);
			ps.setString(2, cursoId);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.conva.ConvMateriaUtil|deleteReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean deleteMateriasDeConvalidacion(Connection conn, String convalidacionId) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.CONV_MATERIA WHERE CONVALIDACION_ID = TO_NUMBER(?,'9999999')");
			ps.setString(1, convalidacionId);			
			if (ps.executeUpdate() >= 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.conva.ConvMateriaUtil|deleteMateriasDeConvalidacion|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	
	public ConvMateria mapeaRegId( Connection conn, String convalidacionId, String cursoId ) throws SQLException{
		
		ConvMateria materia = new ConvMateria();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement("SELECT "+
				"CONVALIDACION_ID, CURSO_ID, TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA, TIPO, ESTADO, MATERIA_O, CREDITOS_O, NOTA_O, TO_CHAR(FECHA_NOTA,'DD/MM/YYYY') AS FECHA_NOTA, FOLIO "+
				"FROM ENOC.CONV_MATERIA WHERE CONVALIDACION_ID = TO_NUMBER(?,'9999999') " + 
				"AND CURSO_ID = ?");
			ps.setString(1, convalidacionId);
			ps.setString(2, cursoId);
			
			rs = ps.executeQuery();
			if (rs.next()){
				materia.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.conva.ConvMateriaUtil|mapeaRegId|:"+ex);
		}finally{
			if(rs!=null) rs.close();
			if(ps!=null) ps.close();
		}
		
		return materia;		
	}
	
	public boolean existeReg(Connection conn, String convalidacionId, String cursoId) throws SQLException{
		boolean 		ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT ESTADO FROM ENOC.CONV_MATERIA "+ 
				"WHERE CONVALIDACION_ID = TO_NUMBER(?,'9999999') " +
				"AND CURSO_ID = ?");
			ps.setString(1, convalidacionId);
			ps.setString(2, cursoId);
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.conva.ConvMateriaUtil|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public boolean existeConv(Connection conn, String convalidacionId) throws SQLException{
		boolean 		ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT ESTADO FROM ENOC.CONV_MATERIA "+ 
				"WHERE CONVALIDACION_ID = TO_NUMBER(?,'9999999') ");
			ps.setString(1, convalidacionId);
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.conva.ConvMateriaUtil|existeConv|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public int getAceptadas(Connection conn, String convalidacionId) throws SQLException{
		
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
		int cantidad = 0;
		
		try{
			comando = "SELECT COUNT(CONVALIDACION_ID) AS MATERIAS" +					
					" FROM ENOC.CONV_MATERIA WHERE CONVALIDACION_ID = "+convalidacionId+ 
					" AND ESTADO = 'S'";
			
			rs = st.executeQuery(comando);
			if(rs.next()){
				cantidad = rs.getInt("MATERIAS");
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.conva.ConvMateriaUtil|getAceptadas|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return cantidad;
	}

	public static int getMatAceptadas(Connection conn, String convalidacionId) throws SQLException{
	
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
		int cantidad = 0;
	
		try{
			comando = "SELECT COUNT(CONVALIDACION_ID) AS MATERIAS" +					
				" FROM ENOC.CONV_MATERIA WHERE CONVALIDACION_ID = "+convalidacionId+ 
				" AND ESTADO = 'S'";
		
			rs = st.executeQuery(comando);
			if(rs.next()){
				cantidad = rs.getInt("MATERIAS");
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.conva.ConvMateriaUtil|getMatAceptadas|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
	
		return cantidad;
	}
	
	public int getRechazadas(Connection conn, String convalidacionId) throws SQLException{
		
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
		int cantidad = 0;
		
		try{
			comando = "SELECT COUNT(CONVALIDACION_ID) AS MATERIAS" +					
					" FROM ENOC.CONV_MATERIA WHERE CONVALIDACION_ID = "+convalidacionId+ 
					" AND ESTADO = 'N'";
			
			rs = st.executeQuery(comando);
			if(rs.next()){
				cantidad = rs.getInt("MATERIAS");
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.conva.ConvMateriaUtil|getRechazadas|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return cantidad;
	}
	
	public static int getMatRechazadas(Connection conn, String convalidacionId) throws SQLException{
		
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
		int cantidad = 0;
		
		try{
			comando = "SELECT COUNT(CONVALIDACION_ID) AS MATERIAS" +					
					" FROM ENOC.CONV_MATERIA WHERE CONVALIDACION_ID = "+convalidacionId+ 
					" AND ESTADO = 'N'";
			
			rs = st.executeQuery(comando);
			if(rs.next()){
				cantidad = rs.getInt("MATERIAS");
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.conva.ConvMateriaUtil|getMatRechazadas|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return cantidad;
	}	

	public ArrayList<ConvMateria> getListAll(Connection conn, String orden ) throws SQLException{		
		ArrayList<ConvMateria> lisEvento	= new ArrayList<ConvMateria>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		try{
			String comando = "SELECT CONVALIDACION_ID, CURSO_ID, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, TIPO, ESTADO, MATERIA_O, CREDITOS_O, NOTA_O, TO_CHAR(FECHA_NOTA,'DD/MM/YYYY') AS FECHA_NOTA, FOLIO " +					
					"FROM ENOC.CONV_MATERIA "+orden; 			
			rs = st.executeQuery(comando);
			while (rs.next()){				
				ConvMateria solicitud = new ConvMateria();
				solicitud.mapeaReg(rs);
				lisEvento.add(solicitud);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.conva.ConvMateriaUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisEvento;
	}
	
	public ArrayList<ConvMateria> getList(Connection conn, String convalidacionId, String orden ) throws SQLException{		
		ArrayList<ConvMateria> lisEvento		= new ArrayList<ConvMateria>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		try{
			String comando = "SELECT CONVALIDACION_ID, CURSO_ID, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, TIPO, ESTADO, MATERIA_O, CREDITOS_O, NOTA_O, TO_CHAR(FECHA_NOTA,'DD/MM/YYYY') AS FECHA_NOTA, FOLIO"
					+ " FROM ENOC.CONV_MATERIA WHERE CONVALIDACION_ID = "+convalidacionId+" "+orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){				
				ConvMateria evento = new ConvMateria();
				evento.mapeaReg(rs);
				lisEvento.add(evento);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.conva.ConvMateriaUtil|getList|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisEvento;
	}
	
	public ArrayList<ConvMateria> getListPorEstado(Connection conn, String convalidacionId, String estados, String orden ) throws SQLException{		
		ArrayList<ConvMateria> lisEvento		= new ArrayList<ConvMateria>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		try{
			String comando = "SELECT CONVALIDACION_ID, CURSO_ID, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, TIPO, ESTADO, MATERIA_O, CREDITOS_O, NOTA_O, TO_CHAR(FECHA_NOTA,'DD/MM/YYYY') AS FECHA_NOTA, FOLIO"
				+ " FROM ENOC.CONV_MATERIA WHERE CONVALIDACION_ID = "+convalidacionId
				+ " AND ESTADO IN ("+estados+") "+orden;			
			rs = st.executeQuery(comando);
			while (rs.next()){				
				ConvMateria evento = new ConvMateria();
				evento.mapeaReg(rs);
				lisEvento.add(evento);
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.conva.ConvMateriaUtil|getList|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisEvento;
	}
	
	public ArrayList<ConvMateria> getListCicloNombre(Connection conn, String convalidacionId, String orden ) throws SQLException{
		
		ArrayList<ConvMateria> lisEvento		= new ArrayList<ConvMateria>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
		
		try{
			comando = " SELECT (ENOC.CICLO_CURSO(CURSO_ID)||'  '||CURSO_ID) AS CONVALIDACION_ID,"
					+ " CURSO_ID, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA,"
					+ " TIPO, ESTADO, MATERIA_O, COALESCE(CREDITOS_O,0) AS CREDITOS_O,"
					+ " COALESCE(NOTA_O,'0') AS NOTA_O, TO_CHAR(FECHA_NOTA,'DD/MM/YYYY') AS FECHA_NOTA, FOLIO"
					+ " FROM ENOC.CONV_MATERIA"
					+ " WHERE CONVALIDACION_ID = "+convalidacionId+" "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				ConvMateria evento = new ConvMateria();
				evento.mapeaReg(rs);
				lisEvento.add(evento);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.conva.ConvMateriaUtil|getListCicloNombre|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisEvento;
	}

	public int getNumMaterias(Connection conn, String convalidacionId) throws SQLException{
	
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
		int cantidad = 0;
		
		try{
			comando = "SELECT COUNT(CONVALIDACION_ID) AS MATERIAS " +					
					"FROM ENOC.CONV_MATERIA WHERE CONVALIDACION_ID = "+convalidacionId; 
			
			rs = st.executeQuery(comando);
			if(rs.next()){
				cantidad = rs.getInt("MATERIAS");
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.conva.ConvMateriaUtil|getNumMaterias|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return cantidad;
	}
	
	public ArrayList<ConvMateria> getListResgistradas(Connection conn, String orden ) throws SQLException{
		
		ArrayList<ConvMateria> lisMaterias		= new ArrayList<ConvMateria>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
		
		try{
			comando = "SELECT A.CONVALIDACION_ID, B.CURSO_ID, TO_CHAR(B.FECHA,'DD/MM/YYYY') AS FECHA, B.TIPO, B.ESTADO," +
					 " B.MATERIA_O, B.CREDITOS_O, B.NOTA_O, TO_CHAR(B.FECHA_NOTA,'DD/MM/YYYY') AS FECHA_NOTA, B.FOLIO" +
					 " FROM ENOC.CONV_EVENTO A, ENOC.CONV_MATERIA B, ENOC.KRDX_CURSO_IMP C" + 
					 " WHERE A.ESTADO = 'T'" +
					 " AND A.CONVALIDACION_ID = B.CONVALIDACION_ID" +
					 " AND B.CURSO_ID = C.CURSO_ID" +
					 " AND A.CODIGO_PERSONAL = C.CODIGO_PERSONAL" +
					 " AND C.TIPOCAL_ID = '1'" +
					 " AND B.ESTADO <> '-'" +
					 " AND B.ESTADO <> 'N' "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				ConvMateria solicitud = new ConvMateria();
				solicitud.mapeaReg(rs);
				lisMaterias.add(solicitud);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.conva.ConvMateriaUtil|getListRegistradas|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisMaterias;
	}
	
	public ArrayList<ConvMateria> getListAlumno(Connection conn, String codigoPersonal, String planId, String orden ) throws SQLException{
		
		ArrayList<ConvMateria> lisMaterias		= new ArrayList<ConvMateria>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
		
		try{
			comando = "SELECT CONVALIDACION_ID, CURSO_ID, TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA," +
					" TIPO, ESTADO, MATERIA_O, CREDITOS_O, NOTA_O, TO_CHAR(FECHA_NOTA,'DD/MM/YYYY') AS FECHA_NOTA, FOLIO FROM ENOC.CONV_MATERIA" + 
					" WHERE CONVALIDACION_ID IN (SELECT CONVALIDACION_ID FROM ENOC.CONV_EVENTO" + 
												" WHERE CODIGO_PERSONAL = '"+codigoPersonal+"'" +
												" AND PLAN_ID = '"+planId+"')"+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				ConvMateria solicitud = new ConvMateria();
				solicitud.mapeaReg(rs);
				lisMaterias.add(solicitud);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.conva.ConvMateriaUtil|getListAlumno|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisMaterias;
	}
	
	public HashMap<String, ConvMateria> mapPorConvIdSolicitud(Connection conn, String convalidacionId) throws SQLException{
		HashMap<String, ConvMateria> map = new HashMap<String, ConvMateria>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando				= "";
		
		try{
			comando = " SELECT CONVALIDACION_ID, CURSO_ID, TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA," +
					  " TIPO, ESTADO, MATERIA_O, CREDITOS_O, NOTA_O, TO_CHAR(FECHA_NOTA,'DD/MM/YYYY') AS FECHA_NOTA, FOLIO " + 					
					  " FROM ENOC.CONV_MATERIA WHERE CONVALIDACION_ID = "+convalidacionId+" "; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				ConvMateria materias = new ConvMateria();
				materias.mapeaReg(rs);
				map.put(rs.getString("CURSO_ID"), materias);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.conva.ConvMateriaUtil|mapPorConvIdSolicitud|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return map;
	}
	
	public HashMap<String, ConvMateria> mapPorConvIdMatOrigen(Connection conn, String convalidacionId) throws SQLException{
		HashMap<String, ConvMateria> map = new HashMap<String, ConvMateria>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando				= "";
		
		try{
			comando = " SELECT CONVALIDACION_ID, CURSO_ID, TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA," +
					  " TIPO, ESTADO, MATERIA_O, CREDITOS_O, NOTA_O, TO_CHAR(FECHA_NOTA,'DD/MM/YYYY') AS FECHA_NOTA, FOLIO " + 					
					  " FROM ENOC.CONV_MATERIA WHERE CONVALIDACION_ID = "+convalidacionId+" "; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				ConvMateria materias = new ConvMateria();
				materias.mapeaReg(rs);
				map.put(rs.getString("MATERIA_O"), materias);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.conva.ConvMateriaUtil|mapPorConvIdMatOrigen|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return map;
	}
	
	public HashMap<String, String> mapaMatConv(Connection conn, String codigoPersonal, String estado) throws SQLException{
		HashMap<String, String> mapa = new HashMap<String, String>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando				= "";
		
		try{
			comando = " SELECT A.CURSO_ID AS CURSO, B.TIPO AS TIPO FROM CONV_MATERIA A, CONV_EVENTO B"
					+ " WHERE B.CONVALIDACION_ID = A.CONVALIDACION_ID"
					+ " AND B.CODIGO_PERSONAL = '"+codigoPersonal+"'"
					+ " AND B.ESTADO IN ("+estado+")"; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){				
				mapa.put(rs.getString("CURSO"), rs.getString("TIPO"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.conva.ConvMateriaUtil|mapaMatConv|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return mapa;
	}
}