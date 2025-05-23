// Clase para la tabla de CandTipo
package aca.candado;

import java.sql.*;
import java.util.ArrayList;

public class CandAlumnoUtil{
	
	public boolean insertReg(Connection conn, CandAlumno candado ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.CAND_ALUMNO"+ 
					"( CODIGO_PERSONAL, "+
					"FOLIO, "+
					"CANDADO_ID, "+
					"F_CREADO, "+
					//"F_BORRADO, "+
					"US_ALTA, "+
					//"US_BAJA, "+
					"COMENTARIO, "+
					"ESTADO) "+
					"VALUES(?,TO_NUMBER(?,'999'),?, "+
					"TO_DATE(?,'DD/MM/YYYY'),"+
					//"TO_DATE(?,'DD/MM/YYYY'),"+
					"?,?,?)");			
			ps.setString(1, candado.getCodigoPersonal());
			ps.setString(2, candado.getFolio());
			ps.setString(3, candado.getCandadoId());
			ps.setString(4, candado.getFCreado());
			//ps.setString(5,fBorrado);
			ps.setString(5, candado.getUsAlta());
			//ps.setString(6,usBaja);
			ps.setString(6, candado.getComentario());
			ps.setString(7, candado.getEstado());			
			
			if ( ps.executeUpdate()== 1){
				ok = true;
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.candado.CandAlumnoUtil|insertReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean updateReg(Connection conn, CandAlumno candado ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.CAND_ALUMNO "+ 
				"SET CANDADO_ID = ?, "+
				"F_CREADO = TO_DATE(?,'DD/MM/YYYY'), "+
				"F_BORRADO = TO_DATE(?,'DD/MM/YYYY'), "+
				"US_ALTA = ?, "+
				"US_BAJA= ?, "+
				"COMENTARIO = ?, "+
				"ESTADO = ? "+
				"WHERE CODIGO_PERSONAL = ? "+
				"AND FOLIO = TO_NUMBER(?,'999')");
			
			ps.setString(1, candado.getCandadoId());
			ps.setString(2, candado.getFCreado());
			ps.setString(3, candado.getFBorrado());
			ps.setString(4, candado.getUsAlta());
			ps.setString(5, candado.getUsBaja());
			ps.setString(6, candado.getComentario());
			ps.setString(7, candado.getEstado());
			ps.setString(8, candado.getCodigoPersonal());			
			ps.setString(9, candado.getFolio());
			
			if ( ps.executeUpdate()== 1){
				ok = true;
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.candado.CandAlumnoUtil|updateReg|:"+ex); 
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean deleteReg(Connection conn, String codigoPersonal, String candadoId ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.CAND_ALUMNO "+ 
				"WHERE CODIGO_PERSONAL = ? AND FOLIO = ?");
			ps.setString(1,codigoPersonal);
			ps.setString(2,candadoId);
			if ( ps.executeUpdate()== 1){
				ok = true;
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.candado.CandAlumnoUtil|deleteReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean deleteRegExtranjero(Connection conn, String codigoPersonal, String usAlta, String candadoId, String planId ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.CAND_ALUMNO"
					+ " WHERE CODIGO_PERSONAL = ?"
					+ " AND US_ALTA = ?"
					+ " AND CANDADO_ID = ?"
					+ " AND COMENTARIO = ?");
			ps.setString(1,codigoPersonal);
			ps.setString(2,usAlta);
			ps.setString(3,candadoId);
			ps.setString(4,planId);
			if ( ps.executeUpdate()== 1){
				ok = true;
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.candado.CandAlumnoUtil|deleteRegExtranjero|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public CandAlumno mapeaRegId(Connection con, String codigoPersonal, String folio) throws SQLException{
		
		CandAlumno candado = new CandAlumno();		
		PreparedStatement ps = null;
		ResultSet rs = null;		
		try{
			ps = con.prepareStatement("SELECT "+
				"CODIGO_PERSONAL, FOLIO, CANDADO_ID,  "+
				"TO_CHAR(F_CREADO,'DD/MM/YYYY') AS F_CREADO, "+
				"TO_CHAR(F_BORRADO,'DD/MM/YYYY') AS F_BORRADO, "+
				"US_ALTA, "+
				"US_BAJA, "+
				"COMENTARIO, "+
				"ESTADO "+
				"FROM ENOC.CAND_ALUMNO "+ 
				"WHERE CODIGO_PERSONAL = ? "+
				"AND FOLIO = TO_NUMBER(?,'999') ");
			ps.setString(1,codigoPersonal);
			ps.setString(2,folio);
			rs = ps.executeQuery();
			
			if(rs.next()){
				candado.mapeaReg(rs);
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.candado.CandAlumnoUtil|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return candado;
	}
	
	public boolean existeReg(Connection conn, String codigoPersonal, String folio) throws SQLException{
		boolean ok 				= false;
		ResultSet rs 			= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.CAND_ALUMNO WHERE CODIGO_PERSONAL = ? AND FOLIO = ? "); 
			ps.setString(1,codigoPersonal);
			ps.setString(2,folio);
			rs= ps.executeQuery();		
			if(rs.next()){
				ok = true;
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.candado.CandAlumnoUtil|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public static String maximoReg(Connection conn, String codigoPersonal) throws SQLException{		
		ResultSet rs 			= null;
		PreparedStatement ps	= null;
		String maximo 			= "1";
		
		try{
			ps = conn.prepareStatement("SELECT COALESCE(MAX(FOLIO),0)+1 AS MAXIMO "+
				"FROM ENOC.CAND_ALUMNO WHERE CODIGO_PERSONAL = ? "); 
			ps.setString(1,codigoPersonal);			
			rs= ps.executeQuery();		
			if(rs.next()){
				maximo = rs.getString("MAXIMO");
			}
		}catch(Exception ex){
			System.out.println("Error - aca.candado.CandAlumnoUtil|maximoReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return maximo;
	}
	
	public static boolean conCandados(Connection conn, String codigoPersonal) throws SQLException{		
		ResultSet rs 			= null;
		PreparedStatement ps	= null;
		boolean bOk 			= false;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.CAND_ALUMNO "+ 
				"WHERE CODIGO_PERSONAL = ? AND ESTADO = 'A'");
			ps.setString(1,codigoPersonal);			
			rs= ps.executeQuery();		
			if(rs.next()){
				bOk = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.candado.CandAlumnoUtil|conCandados|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return bOk;
	}	
	
	public static boolean tieneCandado(Connection conn, String codigoPersonal, String candadoId) throws SQLException{		
		ResultSet rs 			= null;
		PreparedStatement ps	= null;
		boolean bOk 			= false;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.CAND_ALUMNO "+ 
				"WHERE CODIGO_PERSONAL = ?" +
				" AND CANDADO_ID LIKE ?||'%'" +
				" AND ESTADO = 'A'");
			ps.setString(1,codigoPersonal);
			ps.setString(2, candadoId);
			rs= ps.executeQuery();		
			if(rs.next()){
				bOk = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.candado.CandAlumnoUtil|conCandados|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return bOk;
	}
	
	public boolean removerCand(Connection conn, CandAlumno candado ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.CAND_ALUMNO "
				+ " SET F_BORRADO = TO_DATE(?,'DD/MM/YYYY'), "
				+ " US_BAJA= ?, "
				+ " ESTADO = ? "
				+ " WHERE CODIGO_PERSONAL = ? "
				+ " AND CANDADO_ID=?"
				+ " AND ESTADO = 'A'");			
			
			ps.setString(1, candado.getFBorrado());
			ps.setString(2, candado.getUsBaja());
			ps.setString(3, candado.getEstado());
			ps.setString(4, candado.getCodigoPersonal());			
			ps.setString(5, candado.getCandadoId());
			
			if ( ps.executeUpdate()== 1){
				ok = true;				
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.candado.CandAlumnoUtil|updateReg|:"+ex); 
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
		
	public ArrayList<CandAlumno> getListAll(Connection conn, String orden ) throws SQLException{
		
		ArrayList<CandAlumno> lisCandado 	= new ArrayList<CandAlumno>();
		Statement st 	= conn.createStatement();
		ResultSet rs 	= null;
		String comando	= "";
		
		try{
			comando = "SELECT "+
			"CODIGO_PERSONAL, FOLIO, CANDADO_ID,  "+
			"TO_DATE(F_CREADO,'DD/MM/YYYY') AS F_CREADO, "+
			"TO_DATE(F_BORRADO,'DD/MM/YYYY') AS F_BORRADO, "+
			"US_ALTA, "+
			"US_BAJA, "+
			"COMENTARIO, "+
			"ESTADO "+
			"FROM ENOC.CAND_ALUMNO "+orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				CandAlumno candado = new CandAlumno();
				candado.mapeaReg(rs);
				lisCandado.add(candado);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.candado.CandAlumnoUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisCandado;
	}
	
	public ArrayList<CandAlumno> getLista(Connection conn, String codigoPersonal, String orden ) throws SQLException{
		
		ArrayList<CandAlumno> lisCandado 	= new ArrayList<CandAlumno>();
		Statement st 	= conn.createStatement();
		ResultSet rs 	= null;
		String comando	= "";
		
		try{
			comando = "SELECT "+
			"CODIGO_PERSONAL, FOLIO, CANDADO_ID,  "+
			"TO_CHAR(F_CREADO,'DD/MM/YYYY') AS F_CREADO, "+
			"TO_CHAR(F_BORRADO,'DD/MM/YYYY') AS F_BORRADO, "+
			"US_ALTA, "+
			"US_BAJA, "+
			"COMENTARIO, "+
			"ESTADO "+
			"FROM ENOC.CAND_ALUMNO "+ 
			"WHERE CODIGO_PERSONAL = '"+codigoPersonal+"' "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				CandAlumno candado = new CandAlumno();
				candado.mapeaReg(rs);
				lisCandado.add(candado);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.candado.CandAlumnoUtil|getLista|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}		
		
		return lisCandado;
	}
	
	public ArrayList<CandAlumno> getLista(Connection conn, String codigoPersonal, String estado, String orden ) throws SQLException{
		
		ArrayList<CandAlumno> lisCandado 	= new ArrayList<CandAlumno>();
		Statement st 	= conn.createStatement();
		ResultSet rs 	= null;
		String comando	= "";
		
		try{
			comando = "SELECT "+
			"CODIGO_PERSONAL, FOLIO, CANDADO_ID,  "+
			"TO_CHAR(F_CREADO,'DD/MM/YYYY') AS F_CREADO, "+
			"TO_CHAR(F_BORRADO,'DD/MM/YYYY') AS F_BORRADO, "+
			"US_ALTA, "+
			"US_BAJA, "+
			"COMENTARIO, "+
			"ESTADO "+
			"FROM ENOC.CAND_ALUMNO "+ 
			"WHERE CODIGO_PERSONAL = '"+codigoPersonal+"' "+
			"AND ESTADO = '"+estado+"' "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				CandAlumno candado = new CandAlumno();
				candado.mapeaReg(rs);
				lisCandado.add(candado);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.candado.CandAlumnoUtil|getLista|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}				
		
		return lisCandado;
	}
	
	public ArrayList<CandAlumno> getListCandadoFecha(Connection conn, String fecha, String estado, String orden ) throws SQLException{
		
		ArrayList<CandAlumno> lisCandado 	= new ArrayList<CandAlumno>();
		Statement st 	= conn.createStatement();
		ResultSet rs 	= null;
		String comando	= "";
		
		try{
			comando = " SELECT CODIGO_PERSONAL, FOLIO, CANDADO_ID, TO_CHAR(F_CREADO,'DD/MM/YYYY') AS F_CREADO,"
					+ " TO_CHAR(F_BORRADO,'DD/MM/YYYY') AS F_BORRADO, US_ALTA, US_BAJA, COMENTARIO, ESTADO"
					+ " FROM ENOC.CAND_ALUMNO"					
					+ " WHERE ESTADO IN("+estado+")"
					+ " AND F_CREADO > TO_DATE('"+fecha+"','DD/MM/YYYY') "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				CandAlumno candado = new CandAlumno();
				candado.mapeaReg(rs);
				lisCandado.add(candado);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.candado.CandAlumnoUtil|getListCandado|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisCandado;
	}	
	
	public ArrayList<CandAlumno> getListCandado(Connection conn, String periodoId, String tipoId, String orden ) throws SQLException{
		
		ArrayList<CandAlumno> lisCandado 	= new ArrayList<CandAlumno>();
		Statement st 	= conn.createStatement();
		ResultSet rs 	= null;
		String comando	= "";
		
		try{
			comando = "SELECT "+
			"CODIGO_PERSONAL, FOLIO, CANDADO_ID,  " +
			"TO_CHAR(F_CREADO,'DD/MM/YYYY') AS F_CREADO, " +
			"TO_CHAR(F_BORRADO,'DD/MM/YYYY') AS F_BORRADO, " +
			"US_ALTA, US_BAJA, COMENTARIO, ESTADO " +
			"FROM ENOC.CAND_ALUMNO " + 
			"WHERE SUBSTR(CANDADO_ID,1,2) = '"+tipoId+"' " +
			" AND ESTADO = 'A' " +
			" AND '"+periodoId+"' IN(SELECT PERIODO_ID FROM ENOC.CAT_PERIODO " + 
			" WHERE CAND_ALUMNO.F_CREADO BETWEEN F_INICIO AND F_FINAL )" +orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				CandAlumno candado = new CandAlumno();
				candado.mapeaReg(rs);
				lisCandado.add(candado);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.candado.CandAlumnoUtil|getListCand|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}	
		
		return lisCandado;
	}
	
	public ArrayList<CandAlumno> getListCand(Connection conn, String codigoPersonal, String tipoId, String orden ) throws SQLException{
		
		ArrayList<CandAlumno> lisCandado 	= new ArrayList<CandAlumno>();
		Statement st 	= conn.createStatement();
		ResultSet rs 	= null;
		String comando	= "";
		
		try{
			comando = "SELECT "+
			"CODIGO_PERSONAL, FOLIO, CANDADO_ID,  " +
			"TO_CHAR(F_CREADO,'DD/MM/YYYY') AS F_CREADO, " +
			"TO_CHAR(F_BORRADO,'DD/MM/YYYY') AS F_BORRADO, " +
			"US_ALTA, US_BAJA, COMENTARIO, ESTADO " +
			"FROM ENOC.CAND_ALUMNO WHERE CODIGO_PERSONAL = '"+codigoPersonal+"' " + 
			"AND CANDADO_ID LIKE '"+tipoId+"%' "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				CandAlumno candado = new CandAlumno();
				candado.mapeaReg(rs);
				lisCandado.add(candado);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.candado.CandAlumnoUtil|getListCand|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}	
		
		return lisCandado;
	}
	
	public ArrayList<CandAlumno> getListCandAlumno(Connection conn, String codigoPersonal, String tipoId, String estado, String orden ) throws SQLException{
		
		ArrayList<CandAlumno> lisCandado 	= new ArrayList<CandAlumno>();
		Statement st 	= conn.createStatement();
		ResultSet rs 	= null;
		String comando	= "";
		
		try{
			comando = "SELECT "+
			" CODIGO_PERSONAL, FOLIO, CANDADO_ID," +
			" TO_CHAR(F_CREADO,'DD/MM/YYYY') AS F_CREADO," +
			" TO_CHAR(F_BORRADO,'DD/MM/YYYY') AS F_BORRADO," +
			" US_ALTA, US_BAJA, COMENTARIO, ESTADO " +
			" FROM ENOC.CAND_ALUMNO "+
			" WHERE CODIGO_PERSONAL = '"+codigoPersonal+"'" + 
			" AND CANDADO_ID LIKE '"+tipoId+"%' "+
			" AND ESTADO = '"+estado+"' "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				CandAlumno candado = new CandAlumno();
				candado.mapeaReg(rs);
				lisCandado.add(candado);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.candado.CandAlumnoUtil|getListCand|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}	
		
		return lisCandado;
	}
	
	public static boolean validaCandadoDeudor(Connection conn, String codigoPersonal) throws SQLException{
		ResultSet rs 			= null;
		PreparedStatement ps	= null;
		PreparedStatement ps2	= null;
		boolean bOk 			= false;		
		int folio=0;
		
		try{
			
			double saldo = aca.financiero.FesContratoFinancieroUtil.getAlumSaldoVencido(conn, codigoPersonal);
			//System.out.println("Saldo: "+codigoPersonal+":"+saldo);
			
			// Quita candado en caso de que lo tenga			
			if (saldo >= -3000){
				ps = conn.prepareStatement("SELECT FOLIO FROM ENOC.CAND_ALUMNO " + 
						"WHERE CODIGO_PERSONAL = ? AND ESTADO = 'A' AND CANDADO_ID = '0401'");
				ps.setString(1,codigoPersonal);
				rs= ps.executeQuery();
				if(rs.next()){
					folio = rs.getInt("FOLIO");
					ps2 = conn.prepareStatement("DELETE CAND_ALUMNO WHERE CODIGO_PERSONAL = ? AND FOLIO = ?");
					ps2.setString(1,codigoPersonal);
					ps2.setInt(2,folio);
					if (ps2.executeUpdate()==1){
						bOk = true;
					}
				}
			}else{
				
				if(!aca.alumno.AlumUtil.esInscrito(conn, codigoPersonal)){
					ps = conn.prepareStatement("SELECT FOLIO FROM ENOC.CAND_ALUMNO " + 
					"WHERE CODIGO_PERSONAL = ? AND CANDADO_ID = '0401'" +
					"AND (ESTADO = 'A' OR (ESTADO = 'I' AND MONTHS_BETWEEN(TO_DATE(TO_CHAR(now(),'DD/MM/YYYY'),'DD/MM/YYYY'), F_BORRADO)<2))");
					ps.setString(1,codigoPersonal);
					rs= ps.executeQuery();
					if(!rs.next()){				
						folio = Integer.parseInt(CandAlumno.maximoReg(conn,codigoPersonal));
						ps2 = conn.prepareStatement("INSERT INTO ENOC.CAND_ALUMNO" + 
								"(CODIGO_PERSONAL, FOLIO, CANDADO_ID, F_CREADO, US_ALTA, COMENTARIO, ESTADO) " +
								"VALUES(?,?,'0401',TO_DATE(TO_CHAR(now(),'DD/MM/YYYY'),'DD/MM/YYYY')," +
								"'9800260','DEUDA MAYOR DE $3,000','A')");
						ps2.setString(1,codigoPersonal);
						ps2.setInt(2,folio);
						if (ps2.executeUpdate()==1){
							bOk = true;
						}
					}
				}				
			}
		}catch(Exception ex){
			System.out.println("Error - aca.candado.CandAlumnoUtil|validaCandadoDeudor|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
			if (ps2!=null) ps2.close();
		}		
		return bOk;
	}
	
}