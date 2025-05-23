// Clase Util para la tabla de Carga
package aca.carga;

import java.sql.*;
import java.util.ArrayList;

public class CargaUtil{
	
	public boolean insertReg(Connection conn, Carga carga ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.CARGA"+ 
				"(CARGA_ID, NOMBRE_CARGA, F_CREADA, PERIODO, CICLO, F_INICIO, "+
				"F_FINAL, F_EXTRA, NUM_CURSOS, ESTADO, TIPOCARGA, SEMANAS, EVALUA) "+
				"VALUES( ?, ?, "+
				"TO_DATE(?,'DD/MM/YYYY'), "+
				"?, "+
				"TO_NUMBER(?,'99'), "+		
				"TO_DATE(?,'DD/MM/YYYY'), "+
				"TO_DATE(?,'DD/MM/YYYY'), "+
				"TO_DATE(?,'DD/MM/YYYY'), "+
				"TO_NUMBER(?,'9999'), "+
				"?,?,TO_NUMBER(?,'99'),?)");
					
			ps.setString(1,  carga.getCargaId());
			ps.setString(2,  carga.getNombreCarga());
			ps.setString(3, carga.getFCreada());
			ps.setString(4, carga.getPeriodo());
			ps.setString(5, carga.getCiclo());
			ps.setString(6, carga.getFInicio());
			ps.setString(7, carga.getFFinal());
			ps.setString(8, carga.getFExtra());
			ps.setString(9, carga.getNumCursos());
			ps.setString(10, carga.getEstado());
			ps.setString(11, carga.getTipoCarga());
			ps.setString(12, carga.getSemanas());
			ps.setString(13, carga.getEvalua());
			
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaUtil|insertReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public boolean updateReg(Connection conn, Carga carga ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.CARGA"
				+ " SET NOMBRE_CARGA = ?,"
				+ " F_CREADA = TO_DATE(?,'DD/MM/YYYY'),"
				+ " PERIODO = ?,"
				+ " CICLO = TO_NUMBER(?,'99'),"
				+ " F_INICIO = TO_DATE(?,'DD/MM/YYYY'),"
				+ " F_FINAL = TO_DATE(?,'DD/MM/YYYY'),"
				+ " F_EXTRA = TO_DATE(?,'DD/MM/YYYY'),"
				+ " NUM_CURSOS = TO_NUMBER(?,'9999'),"
				+ " ESTADO = ?,"
				+ " TIPOCARGA = ?,"
				+ " SEMANAS = TO_NUMBER(?,'99'),"
				+ " EVALUA	= ?"
				+ " WHERE CARGA_ID = ? ");
			
			
			ps.setString(1,  carga.getNombreCarga());
			ps.setString(2, carga.getFCreada());
			ps.setString(3, carga.getPeriodo());
			ps.setString(4, carga.getCiclo());
			ps.setString(5, carga.getFInicio());
			ps.setString(6, carga.getFFinal());
			ps.setString(7, carga.getFExtra());
			ps.setString(8, carga.getNumCursos());
			ps.setString(9, carga.getEstado());
			ps.setString(10, carga.getTipoCarga());
			ps.setString(11, carga.getSemanas());
			ps.setString(12, carga.getEvalua());			
			ps.setString(13,carga.getCargaId());
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaUtil|updateReg|:"+ex);		 
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public static boolean updateFinServicios(Connection conn, String cargaId, String finServicios ) throws Exception{
		PreparedStatement ps = null;
		boolean ok = false;		
		try{
			ps = conn.prepareStatement("UPDATE ENOC.CARGA "+ 
				"SET FIN_SERVICIOS =  TO_DATE(?,'DD/MM/YYYY') "+				
				"WHERE CARGA_ID = ? ");
			ps.setString(1, finServicios);						
			ps.setString(2,cargaId);
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaUtil|updateFinServicios|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	
	public boolean deleteReg(Connection conn, String cargaId ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.CARGA "+ 
				"WHERE CARGA_ID = ? ");
			ps.setString(1, cargaId);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaUtil|deleteReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	
	public Carga mapeaRegId( Connection conn, String cargaId ) throws SQLException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Carga carga = new Carga();
		try{
			ps = conn.prepareStatement("SELECT CARGA_ID, NOMBRE_CARGA, "+
				"TO_CHAR(F_CREADA,'DD/MM/YYYY') F_CREADA, PERIODO, CICLO, "+
				"TO_CHAR(F_INICIO,'DD/MM/YYYY') F_INICIO, "+
				"TO_CHAR(F_FINAL,'DD/MM/YYYY') F_FINAL, "+
				"TO_CHAR(F_EXTRA,'DD/MM/YYYY') F_EXTRA, NUM_CURSOS, ESTADO, TIPOCARGA, SEMANAS, EVALUA "+
				"FROM ENOC.CARGA WHERE CARGA_ID = ? "); 
			ps.setString(1, cargaId);
			
			rs = ps.executeQuery();
			if (rs.next()){
				carga.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaUtil|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return carga;
	}
	
	public boolean existeReg(Connection conn, String cargaId) throws SQLException{
		boolean 			ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.CARGA "+ 
				"WHERE CARGA_ID = ?");
			ps.setString(1, cargaId);
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaUtil|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public String maximoCurso(Connection conn, String cargaId) throws SQLException{		
		ResultSet rs			= null;
		PreparedStatement ps	= null;
		String  s_maximo 		= "";
		
		try{
			ps = conn.prepareStatement("SELECT TO_CHAR(NUM_CURSOS+1,'0000') AS MAXIMO FROM ENOC.CARGA "+ 
				"WHERE CARGA_ID = ?");
			ps.setString(1, cargaId);
			
			rs = ps.executeQuery();
			if (rs.next()){		
				s_maximo = cargaId+"-"+rs.getString("MAXIMO").substring(1,5);
			}else{				
				s_maximo = cargaId + "-0001";
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaUtil|maximoCurso|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return s_maximo;
	}
	
	public boolean updateCurso(Connection conn, String cargaId ) throws Exception{ 
		boolean ok 				= false;			
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.CARGA "+ 
				"SET NUM_CURSOS = NUM_CURSOS + 1 "+
				"WHERE CARGA_ID = ? ");			
			ps.setString(1, cargaId);			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaUtil|updateCurso|:"+ex);		 
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public static String getNombreCarga(Connection conn, String cargaId ) throws Exception{
		ResultSet rs			= null;		
		String  nombre	 		= "VACIO";
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("SELECT NOMBRE_CARGA "+
				"FROM ENOC.CARGA WHERE CARGA_ID = ? ");			 
			ps.setString(1, cargaId);
			
			rs= ps.executeQuery();
			if (rs.next()){
				nombre = rs.getString("NOMBRE_CARGA");
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaUtil|getNombreCarga|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }			
			try { rs.close(); } catch (Exception ignore) { }
		}
		
		return nombre;
	}
	
	public static String getCargasPeriodo(Connection conn, String periodo ) throws Exception{
		ResultSet rs		= null;		
		String  cargas 		= "";
		int row 			= 1;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("SELECT CARGA_ID FROM ENOC.CARGA WHERE PERIODO = ? ORDER BY CARGA_ID");
			ps.setString(1, periodo);
			
			rs= ps.executeQuery();
			while (rs.next()){
				if (row>1) cargas += ",";
				cargas += "'"+rs.getString("CARGA_ID")+"'";
				row++;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaUtil|getCargasPeriodo|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }			
			try { rs.close(); } catch (Exception ignore) { }
		}
		
		return cargas;
	}
	
	public static String getCargasActivas(Connection conn, String fecha ) throws Exception{
		ResultSet rs		= null;		
		String  cargas 		= "";
		int row 			= 1;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("SELECT CARGA_ID FROM ENOC.CARGA" + 
				" WHERE TO_DATE(?,'DD/MM/YY') BETWEEN F_INICIO AND F_FINAL" +
				" ORDER BY CARGA_ID");	
			ps.setString(1, fecha);
			
			rs= ps.executeQuery();
			while (rs.next()){
				if (row>1) cargas += ",";
				cargas += "'"+rs.getString("CARGA_ID")+"'";
				row++;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaUtil|getCargasActivas|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }			
			try { rs.close(); } catch (Exception ignore) { }
		}
		
		return cargas;
	}
	
	public static String getFinServicios(Connection conn, String cargaId ) throws Exception{
		
		PreparedStatement ps 	= null;
		ResultSet rs			= null;		
		String  fecha	 		= "X";
		
		try{
			ps = conn.prepareStatement("SELECT COALESCE(TO_CHAR(FIN_SERVICIOS,'DD/MM/YYYY'),'X') AS FIN_SERVICIOS "+
				" FROM ENOC.CARGA WHERE CARGA_ID = ? ");			 
			ps.setString(1, cargaId);
			
			rs= ps.executeQuery();
			if (rs.next()){
				fecha = rs.getString("FIN_SERVICIOS");
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaUtil|getFinServicios|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }			
			try { rs.close(); } catch (Exception ignore) { }
		}
		
		return fecha;
	}
	
	public static String getCargasSemestre(Connection conn,String periodo1, String periodo2) throws Exception{
		ResultSet rs		= null;		
		String  cargas 		= "";
		int row 			= 1;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("SELECT CARGA_ID FROM ENOC.CARGA " + 
					" WHERE PERIODO IN(?,?)"+
					" AND SUBSTR(CARGA_ID,5,2) IN ('1A','1C','2A','2B')" +
					" AND F_INICIO >= TO_DATE (TO_CHAR(now(),'DD/MM/YYYY'),'DD/MM/YYYY')" +
					" ORDER BY SUBSTR(CARGA_ID,5,1),CARGA_ID");	
			ps.setString(1, periodo1);
			ps.setString(2, periodo2);
			
			rs= ps.executeQuery();
			while (rs.next()){
				if (row>1) cargas += ",";
				cargas += "'"+rs.getString("CARGA_ID")+"'";
				row++;				
			}
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaUtil|getCargasSemestre|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }			
			try { rs.close(); } catch (Exception ignore) { }
		}
		
		return cargas;
	}
	
	public static String getCargasTetra(Connection conn,String periodo1, String periodo2) throws Exception{
		ResultSet rs		= null;		
		String  cargas 		= "";
		int row 			= 1;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("SELECT CARGA_ID FROM ENOC.CARGA " + 
					" WHERE PERIODO IN (?,?)" +
					" AND SUBSTR(CARGA_ID,5,2) IN ('3A','3B','3C')" +
					" AND F_INICIO >= TO_DATE (TO_CHAR(now(),'DD/MM/YYYY'),'DD/MM/YYYY')" +
					" ORDER BY SUBSTR(CARGA_ID,5,1),CARGA_ID");
			ps.setString(1, periodo1);
			ps.setString(2, periodo2);
			
			rs= ps.executeQuery();
			while (rs.next()){
				if (row>1) cargas += ",";
				cargas += "'"+rs.getString("CARGA_ID")+"'";
				row++;				
			}
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaUtil|getCargasTetra|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }			
			try { rs.close(); } catch (Exception ignore) { }
		}	
		return cargas;
	}
		
	public static String getMejorCarga(Connection conn, String codigoPersonal ) throws Exception{
		PreparedStatement ps 	= null;
		ResultSet rs			= null;
		PreparedStatement ps2 	= null;
		ResultSet rs2			= null;	
		String carga 			= "";
		String comando 			= "";		
				
		try{		
			if (codigoPersonal.substring(0,1).equals("9")){				
				comando = " SELECT COALESCE(MAX(CARGA_ID),'XXXXX') AS CARGA_ID FROM ENOC.CARGA_GRUPO " + 
						"WHERE CODIGO_PERSONAL = ? " +
						"AND CARGA_ID NOT LIKE '2%'";
				ps = conn.prepareStatement( comando );
				ps.setString(1, codigoPersonal);			
				rs= ps.executeQuery();
				if ( rs.next()&& !rs.getString("CARGA_ID").equals("XXXXX")){
					carga  = rs.getString("CARGA_ID");				
				}else{
					comando = "SELECT CARGA_ID FROM ENOC.CARGA "+ 
							"WHERE TO_DATE(TO_CHAR(now(),'DD-MM-YY'),'DD-MM-YY') BETWEEN F_INICIO AND F_FINAL "+
							"AND SUBSTR(CARGA_ID,6,1) IN ('A','B','C','D') ORDER BY CARGA_ID";
					ps2 = conn.prepareStatement( comando );
					rs2 = ps2.executeQuery();
					if (rs2.next()){
						carga = rs2.getString("CARGA_ID");
					}					
				}	
			}else{
				comando = "SELECT COALESCE(MIN(DISTINCT(SUBSTR(CURSO_CARGA_ID,1,6))),'XXXXX') AS CARGA_ID " +
					" FROM ENOC.KRDX_CURSO_ACT" + 
					" WHERE CODIGO_PERSONAL = ?" +
					" AND SUBSTR(CURSO_CARGA_ID,1,6) IN (SELECT CARGA_ID FROM ENOC.CARGA WHERE now() BETWEEN F_INICIO AND F_FINAL)"; 
				ps = conn.prepareStatement( comando );
				ps.setString(1, codigoPersonal);
				rs= ps.executeQuery();
				if ( rs.next() && !rs.getString("CARGA_ID").equals("XXXXX")){
					carga = rs.getString("CARGA_ID");
				}else{
					comando = "SELECT COALESCE(MAX(SUBSTR(CURSO_CARGA_ID,1,6)),'XXXXX') AS CARGA_ID FROM ENOC.KRDX_CURSO_ACT " + 
						"WHERE CODIGO_PERSONAL = ? " +
						"AND CURSO_CARGA_ID NOT LIKE '2%'";
					ps2 = conn.prepareStatement( comando );
					ps2.setString(1, codigoPersonal);
					rs2 = ps2.executeQuery();
					if ( rs2.next()){
						carga = rs2.getString("CARGA_ID");
					}else{
						carga = "xxxxxx";
					}	
				}				
			}			
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaUtil|getMejorCarga|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }			
			try { rs.close(); } catch (Exception ignore) { }
			if (ps2!=null) ps2.close();			
			if (rs2!=null) rs2.close();
		}
		return carga;
	}
	
	public static ArrayList<String> getRangoCarga(Connection conn, String cargaId ) throws Exception{
		ResultSet rs		= null;		
		ArrayList<String> lisFechas	= new ArrayList<String>();
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("SELECT TO_CHAR(F_INICIO,'MM') AS F_INICIO," +
					" TO_CHAR(F_FINAL, 'MM') AS F_FINAL" +
					" FROM ENOC.CARGA WHERE CARGA_ID = ? ");			 
			ps.setString(1, cargaId);
			
			rs= ps.executeQuery();
			if(rs.next()){
				lisFechas.add(rs.getString("F_INICIO"));
				lisFechas.add(rs.getString("F_FINAL"));
			}
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaUtil|getRangoCarga|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }			
			try { rs.close(); } catch (Exception ignore) { }
		}
		
		return lisFechas;
	}
	
	public static String getFInicio(Connection conn, String cargaId ) throws Exception{
		ResultSet rs			= null;		
		String fecha			= "";
		PreparedStatement ps 	= null;
		try{
			ps = conn.prepareStatement("SELECT TO_CHAR(F_INICIO,'DD/MM/YYYY') AS F_INICIO" +
					" FROM ENOC.CARGA WHERE CARGA_ID = ? ");			 
			ps.setString(1, cargaId);
			
			rs= ps.executeQuery();
			if(rs.next()){
				fecha = rs.getString("F_INICIO");
			}
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaUtil|getFInicio|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }			
			try { rs.close(); } catch (Exception ignore) { }
		}
		
		return fecha;
	}
	
	public static boolean esCargaActiva(Connection conn, String carga ) throws Exception{
		PreparedStatement ps 	= null;
		ResultSet rs			= null;	
		boolean esActiva 		= false;
		String hoy				= aca.util.Fecha.getHoy(); 
		try{			
			ps = conn.prepareStatement("SELECT CARGA_ID FROM ENOC.CARGA" + 
				" WHERE CARGA_ID = ? AND TO_DATE(?,'DD/MM/YY') BETWEEN F_INICIO AND F_FINAL");
			ps.setString(1, carga);
			ps.setString(2, hoy);
			
			rs= ps.executeQuery();
			if (rs.next()){
				esActiva = true;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaUtil|getCargasActivas|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }			
			try { rs.close(); } catch (Exception ignore) { }
		}
		
		return esActiva;
	}
	
	public static String getPeriodo(Connection conn, String cargaId ) throws SQLException{
		PreparedStatement	ps 	= null;
		ResultSet 			rs	= null;
		String periodo 		= "0";		
		
		try{
			ps = conn.prepareStatement("SELECT PERIODO FROM ENOC.CARGA WHERE CARGA_ID= ? "); 
			ps.setString(1, cargaId);
			
			rs = ps.executeQuery();
			if (rs.next())
				periodo = rs.getString("PERIODO");		
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaUtil|getPeriodo|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return periodo;
	}
	
	public static String getSemanas(Connection conn, String cargaId ) throws SQLException{
		PreparedStatement	ps 	= null;
		ResultSet 			rs	= null;
		String semanas 			= "0";		
		
		try{
			ps = conn.prepareStatement("SELECT SEMANAS FROM ENOC.CARGA WHERE CARGA_ID= ? "); 
			ps.setString(1, cargaId);
			
			rs = ps.executeQuery();
			if (rs.next())
				semanas = rs.getString("SEMANAS");
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaUtil|getSemanas|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return semanas;
	}
	
	public static String getCiclo(Connection conn, String cargaId ) throws SQLException{
		PreparedStatement	ps 	= null;
		ResultSet 			rs	= null;
		String periodo 		= "0";		
		
		try{
			ps = conn.prepareStatement("SELECT CICLO FROM ENOC.CARGA WHERE CARGA_ID = ? "); 
			ps.setString(1, cargaId);
			
			rs = ps.executeQuery();
			if (rs.next())
				periodo = rs.getString("CICLO");		
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaUtil|getCiclo|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return periodo;
	}
	
	public static boolean esCargaPasada(Connection conn, String cargaId ) throws SQLException{
		PreparedStatement	ps 	= null;
		ResultSet 			rs	= null;
		boolean ok 				= false;		
		
		try{
			ps = conn.prepareStatement("SELECT CARGA_ID FROM ENOC.CARGA WHERE CARGA_ID = ? AND now() >= F_FINAL");
			ps.setString(1, cargaId);
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;		
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaUtil|esCargaPasada|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public static String evaluaCarga(Connection conn, String cargaId ) throws SQLException{
		PreparedStatement	ps 	= null;
		ResultSet 			rs	= null;
		String	evalua			= "";
		
		try{
			ps = conn.prepareStatement("SELECT EVALUA FROM ENOC.CARGA WHERE CARGA_ID = ?");
			ps.setString(1, cargaId);
			
			rs = ps.executeQuery();
			if (rs.next()){
				evalua = rs.getString("EVALUA");		
			}
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaUtil|evaluaCarga|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return evalua;
	}
	
		
	public ArrayList<Carga> getListAll(Connection conn, String orden ) throws SQLException{
		
		ArrayList<Carga> lisCarga	= new ArrayList<Carga>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
		
		try{
			comando = "SELECT CARGA_ID, NOMBRE_CARGA, TO_CHAR(F_CREADA,'DD/MM/YYYY') AS F_CREADA, PERIODO, CICLO, "+
					" TO_CHAR(F_INICIO,'DD/MM/YYYY') AS F_INICIO,"+
					" TO_CHAR(F_FINAL,'DD/MM/YYYY') AS F_FINAL,"+
					" TO_CHAR(F_EXTRA,'DD/MM/YYYY') AS F_EXTRA,"+
					" NUM_CURSOS, ESTADO, TIPOCARGA, SEMANAS, EVALUA"+
					" FROM ENOC.CARGA "+orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				Carga carga = new Carga();
				carga.mapeaReg(rs);
				lisCarga.add(carga);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisCarga;
	}
	
	public ArrayList<Carga> getListAllActivas(Connection conn, String orden ) throws SQLException{
		
		ArrayList<Carga> lisCarga	= new ArrayList<Carga>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
		
		try{
			comando = "SELECT CARGA_ID, NOMBRE_CARGA, TO_CHAR(F_CREADA,'DD/MM/YYYY') AS F_CREADA, PERIODO, CICLO,"+ 
					" TO_CHAR(F_INICIO,'DD/MM/YYYY') AS F_INICIO,"+
					" TO_CHAR(F_FINAL,'DD/MM/YYYY') AS F_FINAL,"+
					" TO_CHAR(F_EXTRA,'DD/MM/YYYY') AS F_EXTRA,"+
					" NUM_CURSOS, ESTADO, TIPOCARGA, SEMANAS, EVALUA"+
					" FROM ENOC.CARGA" +
					" WHERE ESTADO = '1' "+orden;	
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				Carga carga = new Carga();
				carga.mapeaReg(rs);
				lisCarga.add(carga);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaUtil|getListAllActivas|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisCarga;
	}
	
	public ArrayList<Carga> getListPeriodoActual(Connection conn, String orden ) throws SQLException{
		
		ArrayList<Carga> lisCarga	= new ArrayList<Carga>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando			= "";
		String periodo			= "";
		String periodoAnt		= "";
		String periodoSig		= "";
		
		int year1=0, year2=0;
		
		try{
			periodo 	= aca.catalogo.CatPeriodoUtil.getPeriodo(conn);
			
			year1		= Integer.parseInt(periodo.substring(0,2))-1;
			year2		= Integer.parseInt(periodo.substring(2,4))-1;
			if ( String.valueOf(year1).length()==1) periodoAnt = "0"+String.valueOf(year1); else periodoAnt = String.valueOf(year1);
			if ( String.valueOf(year2).length()==1) periodoAnt += "0"+String.valueOf(year2); else periodoAnt +=String.valueOf(year2);
			
			year1		= Integer.parseInt(periodo.substring(0,2))+1;
			year2		= Integer.parseInt(periodo.substring(2,4))+1;
			if ( String.valueOf(year1).length()==1) periodoSig = "0"+String.valueOf(year1); else periodoSig = String.valueOf(year1);
			if ( String.valueOf(year2).length()==1) periodoSig += "0"+String.valueOf(year2); else periodoSig +=String.valueOf(year2);
			
			//periodo 	= "1112";
			//periodoSig 	= "1213";
			
			comando = "SELECT CARGA_ID, NOMBRE_CARGA, TO_CHAR(F_CREADA,'DD/MM/YYYY') AS F_CREADA, PERIODO, CICLO, "+
					"TO_CHAR(F_INICIO,'DD/MM/YYYY') AS F_INICIO, "+
					"TO_CHAR(F_FINAL,'DD/MM/YYYY') AS F_FINAL, "+
					"TO_CHAR(F_EXTRA,'DD/MM/YYYY') AS F_EXTRA, "+
					"NUM_CURSOS, ESTADO, TIPOCARGA, SEMANAS, EVALUA "+
					"FROM ENOC.CARGA " + 
					"WHERE PERIODO IN ('"+periodoAnt+"','"+periodo+"','"+periodoSig+"') " +
					"AND ESTADO = '1' "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				Carga carga = new Carga();
				carga.mapeaReg(rs);
				lisCarga.add(carga);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaUtil|getListPeriodoActual|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisCarga;
	}
	
	public ArrayList<Carga> getListPeriodo(Connection conn, String periodo, String orden ) throws SQLException{
		
		ArrayList<Carga> lisCarga	= new ArrayList<Carga>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
		
		try{
			comando = "SELECT CARGA_ID, NOMBRE_CARGA, TO_CHAR(F_CREADA,'DD/MM/YYYY') F_CREADA, PERIODO, CICLO, "+
					" TO_CHAR(F_INICIO,'DD/MM/YYYY') AS F_INICIO, "+
					" TO_CHAR(F_FINAL,'DD/MM/YYYY') AS F_FINAL, "+
					" TO_CHAR(F_EXTRA,'DD/MM/YYYY') AS F_EXTRA, "+
					" NUM_CURSOS, ESTADO, TIPOCARGA, SEMANAS, EVALUA "+
					" FROM ENOC.CARGA " + 
					" WHERE PERIODO = '"+periodo+"' "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				Carga carga = new Carga();
				carga.mapeaReg(rs);
				lisCarga.add(carga);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaUtil|getListPeriodo|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisCarga;
	}
	
	/* Lista de cargas en aptitud fisica*/ 
	public ArrayList<Carga> getListAptitud(Connection conn, String orden ) throws SQLException{
		
		ArrayList<Carga> lisCarga	= new ArrayList<Carga>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
		
		try{
			comando = "SELECT CARGA_ID, NOMBRE_CARGA, TO_CHAR(F_CREADA,'DD/MM/YYYY') F_CREADA, PERIODO, CICLO, "+
					" TO_CHAR(F_INICIO,'DD/MM/YYYY') AS F_INICIO, "+
					" TO_CHAR(F_FINAL,'DD/MM/YYYY') AS F_FINAL, "+
					" TO_CHAR(F_EXTRA,'DD/MM/YYYY') AS F_EXTRA, "+
					" NUM_CURSOS, ESTADO, TIPOCARGA, SEMANAS, EVALUA "+
					" FROM ENOC.CARGA " + 
					" WHERE CARGA_ID IN (SELECT DISTINCT(CARGAS) FROM ENOC.APFISICA_GRUPO) "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				Carga carga = new Carga();
				carga.mapeaReg(rs);
				lisCarga.add(carga);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaUtil|getListPeriodo|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisCarga;
	}
	
	public ArrayList<Carga> getListCargasActivas(Connection conn, String orden ) throws SQLException{
		
		ArrayList<Carga> lisCarga	= new ArrayList<Carga>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
		
		try{
			comando = "SELECT CARGA_ID, NOMBRE_CARGA, TO_CHAR(F_CREADA,'DD/MM/YYYY') F_CREADA, PERIODO, CICLO, "+
					"TO_CHAR(F_INICIO,'DD/MM/YYYY') F_INICIO, "+
					"TO_CHAR(F_FINAL,'DD/MM/YYYY') F_FINAL, "+
					"TO_CHAR(F_EXTRA,'DD/MM/YYYY') F_EXTRA, "+
					"NUM_CURSOS, ESTADO, TIPOCARGA, SEMANAS, EVALUA "+
					"FROM ENOC.CARGA " + 
					"WHERE now() BETWEEN F_INICIO AND F_FINAL "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				Carga carga = new Carga();
				carga.mapeaReg(rs);
				lisCarga.add(carga);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaUtil|getListCargasActivas|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisCarga;
	}
	
	public ArrayList<Carga> getListCargasPasadas(Connection conn, String orden ) throws SQLException{
		
		ArrayList<Carga> lisCarga	= new ArrayList<Carga>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
		
		try{
			comando = "SELECT CARGA_ID, NOMBRE_CARGA, TO_CHAR(F_CREADA,'DD/MM/YYYY') F_CREADA, PERIODO, CICLO, "+
					"TO_CHAR(F_INICIO,'DD/MM/YYYY') F_INICIO, "+
					"TO_CHAR(F_FINAL,'DD/MM/YYYY') F_FINAL, "+
					"TO_CHAR(F_EXTRA,'DD/MM/YYYY') F_EXTRA, "+
					"NUM_CURSOS, ESTADO, TIPOCARGA, SEMANAS, EVALUA "+
					"FROM ENOC.CARGA " + 
					"WHERE TO_DATE(TO_CHAR(now(),'DD/MM/YYYY'),'DD/MM/YYYY') >= F_FINAL "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				Carga carga = new Carga();
				carga.mapeaReg(rs);
				lisCarga.add(carga);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaUtil|getListCargasActivas|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisCarga;
	}
	
	public ArrayList<Carga> getListCargas(Connection conn, String cargas ) throws SQLException{
		
		ArrayList<Carga> lisCarga	= new ArrayList<Carga>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
		
		try{
			comando = " SELECT CARGA_ID, NOMBRE_CARGA, TO_CHAR(F_CREADA,'DD/MM/YYYY') F_CREADA, PERIODO, CICLO,"
					+ " TO_CHAR(F_INICIO,'DD/MM/YYYY') F_INICIO,"
					+ " TO_CHAR(F_FINAL,'DD/MM/YYYY') F_FINAL,"
					+ " TO_CHAR(F_EXTRA,'DD/MM/YYYY') F_EXTRA,"
					+ " NUM_CURSOS, ESTADO, TIPOCARGA, SEMANAS, EVALUA"
					+ " FROM ENOC.CARGA"
					+ " WHERE CARGA_ID IN ("+cargas+")";
			
			rs = st.executeQuery(comando);
			while (rs.next()){				
				Carga carga = new Carga();
				carga.mapeaReg(rs);
				lisCarga.add(carga);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaUtil|getListCargas|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisCarga;
	}
	
	public ArrayList<Carga> getListMaestro(Connection conn, String codigoPersonal ) throws SQLException{
		
		ArrayList<Carga> lisCarga	= new ArrayList<Carga>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
		
		try{
			comando = "SELECT CARGA_ID, NOMBRE_CARGA, TO_CHAR(F_CREADA,'DD/MM/YYYY') F_CREADA, PERIODO, CICLO, "+
				"TO_CHAR(F_INICIO,'DD/MM/YYYY') F_INICIO, "+
				"TO_CHAR(F_FINAL,'DD/MM/YYYY') F_FINAL, "+
				"TO_CHAR(F_EXTRA,'DD/MM/YYYY') F_EXTRA, "+
				"NUM_CURSOS, ESTADO, TIPOCARGA, SEMANAS, EVALUA "+
				"FROM ENOC.CARGA "+ 
				"WHERE CARGA_ID IN "+
					"(SELECT DISTINCT(CARGA_ID) FROM ENOC.CARGA_GRUPO WHERE CODIGO_PERSONAL = '"+codigoPersonal+"') "+ 
				"ORDER BY TO_CHAR(ENOC.CARGA.F_INICIO, 'YYYY-MM-DD')";
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				Carga carga = new Carga();
				carga.mapeaReg(rs);
				lisCarga.add(carga);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaUtil|getListMaestro|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisCarga;		
	}	
	
	public ArrayList<Carga> getListNoMaestro(Connection conn, String codigoPersonal ) throws SQLException{
		
		ArrayList<Carga> lisCarga	= new ArrayList<Carga>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
		
		try{
			comando = "SELECT CARGA_ID, NOMBRE_CARGA, TO_CHAR(F_CREADA,'DD/MM/YYYY') F_CREADA, PERIODO, CICLO, "+
				"TO_CHAR(F_INICIO,'DD/MM/YYYY') F_INICIO, "+
				"TO_CHAR(F_FINAL,'DD/MM/YYYY') F_FINAL, "+
				"TO_CHAR(F_EXTRA,'DD/MM/YYYY') F_EXTRA, "+
				"NUM_CURSOS, ESTADO, TIPOCARGA, SEMANAS, EVALUA "+
				"FROM ENOC.CARGA "+ 
				"WHERE CARGA_ID NOT IN "+
					"(SELECT DISTINCT(CARGA_ID) FROM ENOC.CARGA_GRUPO WHERE CODIGO_PERSONAL = '"+codigoPersonal+"') "+ 
				"ORDER BY 2";
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				Carga carga = new Carga();
				carga.mapeaReg(rs);
				lisCarga.add(carga);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaUtil|getListNoMaestro|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisCarga;
	}

	public ArrayList<Carga> getListCargasAlumno(Connection conn, String codigoPersonal, String orden) throws SQLException{
		
		ArrayList<Carga> lisCarga	= new ArrayList<Carga>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
		
		try{
			comando = 
				" SELECT DISTINCT(CARGA_ID) AS CARGA FROM ENOC.ALUM_ESTADO WHERE CODIGO_PERSONAL='"+codigoPersonal+"' AND ESTADO = 'I'" +
				" UNION" +
				" SELECT DISTINCT(SUBSTR(CURSO_CARGA_ID,1,6)) AS CARGA FROM ENOC.KRDX_CURSO_ACT WHERE CODIGO_PERSONAL= '"+codigoPersonal+"' "+orden;
			rs = st.executeQuery(comando);
			while (rs.next()){				
				CargaUtil cargaU = new CargaUtil();
				Carga carga = new Carga();
				carga = cargaU.mapeaRegId(conn, rs.getString(1));
				lisCarga.add(carga);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaUtil|getListAlumno|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}		
		return lisCarga;
	}
	
	public ArrayList<Carga> getListAlumno(Connection conn, String codigoPersonal ) throws SQLException{
		
		ArrayList<Carga> lisCarga	= new ArrayList<Carga>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
	
		try{
			comando = "SELECT DISTINCT(CARGA_ID) AS CARGA FROM ENOC.ALUMNO_CURSO" + 
					" WHERE CODIGO_PERSONAL='"+codigoPersonal+"'" +
					" AND NOT CARGA_ID = '000000' ORDER BY 1";
		
			rs = st.executeQuery(comando);
			while (rs.next()){
			
				Carga carga = new Carga();
				CargaUtil cargaU = new CargaUtil();
				
				carga = cargaU.mapeaRegId(conn, rs.getString(1));
				lisCarga.add(carga);
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaUtil|getListAlumno|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
	
		return lisCarga;
	}
	
	public ArrayList<Carga> getListAlumno(Connection conn, String codigoPersonal, String orden) throws SQLException{
		
		ArrayList<Carga> lisCarga	= new ArrayList<Carga>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
	
		try{
			comando = "SELECT DISTINCT(CARGA_ID) AS CARGA FROM ENOC.ALUMNO_CURSO" + 
					" WHERE CODIGO_PERSONAL='"+codigoPersonal+"'" +
					" AND NOT CARGA_ID = '000000' "+orden;
		
			rs = st.executeQuery(comando);
			while (rs.next()){	
				
				Carga carga = new Carga();
				CargaUtil cargaU = new CargaUtil();
				carga = cargaU.mapeaRegId(conn, rs.getString(1));
				lisCarga.add(carga);
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaUtil|getListAlumno|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
	
		return lisCarga;
	}
	
	public ArrayList<String> lisCargas(Connection conn, String tipo, String letras, String orden) throws SQLException{
		
		ArrayList<String> lisCarga	= new ArrayList<String>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
	
		try{
			comando = "SELECT CARGA_ID FROM ENOC.CARGA WHERE SUBSTR(CARGA_ID,5,1) IN ("+tipo+") AND SUBSTR(CARGA_ID,6,1) IN ("+letras+") "+orden;
			rs = st.executeQuery(comando);
			while (rs.next()){			
				lisCarga.add(rs.getString("CARGA_ID"));
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaUtil|lisCargas|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
	
		return lisCarga;
	}
	
	public String getNombre(Connection conn, String cargaId ) throws SQLException{
		
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String s_comando	= "";
		String s_nombre		= "";
		
		try{
			s_comando = "SELECT NOMBRE_CARGA FROM ENOC.CARGA WHERE CARGA_ID = '"+cargaId+"'";			 
			rs = st.executeQuery(s_comando);
			if (rs.next())				
				s_nombre = rs.getString("NOMBRE_CARGA");
			else
				s_nombre = "null";
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaUtil|getNombre|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return s_nombre;
	}
	
	public String getActual(Connection conn, String fecha ) throws SQLException{
		
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
		String cargaId	= "";
		
		try{
			comando = " SELECT CARGA_ID FROM ENOC.CARGA"
					+ " WHERE TO_DATE('"+fecha+"','DD-MM-YY') BETWEEN F_INICIO AND F_FINAL"
					+ " AND SUBSTR(CARGA_ID,6,1) IN ('A','B','C','D')"
					+ " AND SUBSTR(CARGA_ID,5,1) IN ('1','2','3','4','5')"
					+ " ORDER BY CARGA_ID";
			rs = st.executeQuery(comando);
			if (rs.next())				
				cargaId = rs.getString("CARGA_ID");
			else
				cargaId = "xxxxxx";
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaUtil|getAcual|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return cargaId;
	}	
	
	public ArrayList<Carga> getListMaestroPeriodo(Connection conn, String codigoPersonal, String periodo) throws SQLException{
		
		ArrayList<Carga> lisCarga	= new ArrayList<Carga>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
		
		try{
			comando = " SELECT CARGA_ID, NOMBRE_CARGA, TO_CHAR(F_CREADA,'DD/MM/YYYY') F_CREADA, PERIODO, CICLO, "
					+ " TO_CHAR(F_INICIO,'DD/MM/YYYY') F_INICIO,"
					+ " TO_CHAR(F_FINAL,'DD/MM/YYYY') F_FINAL,"
					+ " TO_CHAR(F_EXTRA,'DD/MM/YYYY') F_EXTRA,"
					+ " NUM_CURSOS, ESTADO, TIPOCARGA, SEMANAS, EVALUA"
					+ " FROM ENOC.CARGA"
					+ " WHERE PERIODO = '"+periodo+"'"
					+ " AND ( CARGA_ID IN (SELECT DISTINCT(CARGA_ID) FROM ENOC.CARGA_GRUPO WHERE CODIGO_PERSONAL = '"+codigoPersonal+"') OR"
					+ " 	CARGA_ID IN (SELECT DISTINCT(CARGA_ID) FROM ENOC.HCA_MAESTRO_ACTIVIDAD WHERE CODIGO_PERSONAL = '"+codigoPersonal+"') )"
					+ " ORDER BY CARGA_ID";
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				Carga carga = new Carga();
				carga.mapeaReg(rs);
				lisCarga.add(carga);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaUtil|getListMaestroPeriodo|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisCarga;
	}
	
	// Lista de cargas donde el maestro imparte clases 
	public ArrayList<String> getListMaestroPeriodoCargas(Connection conn, String codigoPersonal, String periodo) throws SQLException{
		
		ArrayList<String> lisCarga	= new ArrayList<String>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
		
		try{
			comando = "SELECT CARGA_ID FROM ENOC.CARGA "
					+ " WHERE PERIODO = '"+periodo+"'"
					+ " AND CARGA_ID IN "
					+ "		(SELECT DISTINCT(CARGA_ID) FROM ENOC.CARGA_GRUPO WHERE CODIGO_PERSONAL = '"+codigoPersonal+"')"
					+ " ORDER BY CARGA_ID";			
			rs = st.executeQuery(comando);
			while (rs.next()){
				lisCarga.add(rs.getString("CARGA_ID"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaUtil|getListMaestroPeriodo|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisCarga;
	}
	
	public ArrayList<Carga> getListMaestroPeriodoCargas(Connection conn, String codigoPersonal, String periodo, String cargas) throws SQLException{
		
		ArrayList<Carga> lisCarga	= new ArrayList<Carga>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
		
		try{
			comando = "SELECT CARGA_ID, NOMBRE_CARGA, TO_CHAR(F_CREADA,'DD/MM/YYYY') F_CREADA, PERIODO, CICLO, "+
				"TO_CHAR(F_INICIO,'DD/MM/YYYY') F_INICIO, "+
				"TO_CHAR(F_FINAL,'DD/MM/YYYY') F_FINAL, "+
				"TO_CHAR(F_EXTRA,'DD/MM/YYYY') F_EXTRA, "+
				"NUM_CURSOS, ESTADO, TIPOCARGA, SEMANAS, EVALUA "+
				"FROM ENOC.CARGA "+ 
				"WHERE CARGA_ID IN "+
					"(SELECT DISTINCT(CARGA_ID) FROM ENOC.CARGA_GRUPO WHERE CODIGO_PERSONAL = '"+codigoPersonal+"')" + 
				" AND PERIODO = '"+periodo+"' " +
				" AND CARGA_ID IN ('"+cargas+"') " +
				" ORDER BY CARGA_ID";
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				Carga carga = new Carga();
				carga.mapeaReg(rs);
				lisCarga.add(carga);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaUtil|getListMaestroPeriodoCargas|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisCarga;
	}
	
	public ArrayList<Carga> getListMaestroPorCargas(Connection conn, String cargaId) throws SQLException{
		
		ArrayList<Carga> lisCarga	= new ArrayList<Carga>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
		
		try{
			comando = "SELECT CARGA_ID, NOMBRE_CARGA, TO_CHAR(F_CREADA,'DD/MM/YYYY') F_CREADA, PERIODO, CICLO, "+
				"TO_CHAR(F_INICIO,'DD/MM/YYYY') F_INICIO, "+
				"TO_CHAR(F_FINAL,'DD/MM/YYYY') F_FINAL, "+
				"TO_CHAR(F_EXTRA,'DD/MM/YYYY') F_EXTRA, "+
				"NUM_CURSOS, ESTADO, TIPOCARGA, SEMANAS, EVALUA "+
				"FROM ENOC.CARGA "+ 
				"WHERE CARGA_ID IN "+
					"("+cargaId+")" +
				" ORDER BY CARGA_ID";
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				Carga carga = new Carga();
				carga.mapeaReg(rs);
				lisCarga.add(carga);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaUtil|getListMaestroPeriodo|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisCarga;
	}
	
	public ArrayList<Carga> getListPorFecha(Connection conn, String fecha, String orden) throws SQLException{
		
		ArrayList<Carga> lisCarga	= new ArrayList<Carga>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
		
		try{
			comando = "SELECT CARGA_ID, NOMBRE_CARGA, TO_CHAR(F_CREADA,'DD/MM/YYYY') F_CREADA, PERIODO, CICLO,"+
				" TO_CHAR(F_INICIO,'DD/MM/YYYY') F_INICIO,"+
				" TO_CHAR(F_FINAL,'DD/MM/YYYY') F_FINAL,"+
				" TO_CHAR(F_EXTRA,'DD/MM/YYYY') F_EXTRA,"+
				" NUM_CURSOS, ESTADO, TIPOCARGA, SEMANAS, EVALUA "+
				" FROM ENOC.CARGA"+ 
				" WHERE TO_DATE('"+fecha+"','DD/MM/YYYY') BETWEEN ENOC.CARGA.F_INICIO AND ENOC.CARGA.F_FINAL "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				Carga carga = new Carga();
				carga.mapeaReg(rs);
				lisCarga.add(carga);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaUtil|getListPorFecha|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisCarga;
	}
	
	public ArrayList<Carga> getCargaAlumProceso(Connection conn, String orden) throws SQLException{
		
		ArrayList<Carga> lisCarga	= new ArrayList<Carga>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
		
		try{
			comando = "SELECT CARGA_ID, NOMBRE_CARGA, TO_CHAR(F_CREADA,'DD/MM/YYYY') AS F_CREADA, PERIODO, CICLO,"+
				" TO_CHAR(F_INICIO,'DD/MM/YYYY') AS F_INICIO,"+
				" TO_CHAR(F_FINAL,'DD/MM/YYYY') AS F_FINAL,"+
				" TO_CHAR(F_EXTRA,'DD/MM/YYYY') AS F_EXTRA,"+
				" NUM_CURSOS, ESTADO, TIPOCARGA, SEMANAS, EVALUA "+
				" FROM ENOC.CARGA"+ 
				" WHERE CARGA_ID IN" +
				"	(SELECT SUBSTR(CURSO_CARGA_ID,1,6) FROM ENOC.KRDX_CURSO_ACT WHERE TIPOCAL_ID IN ('M','C')) "+orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				Carga carga = new Carga();
				carga.mapeaReg(rs);
				lisCarga.add(carga);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaUtil|getCargaAlumProceso|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisCarga;
	}
	
	public ArrayList<Carga> getListCargaActual(Connection conn, String orden ) throws SQLException{
		
		ArrayList<Carga> lisCarga	= new ArrayList<Carga>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
		
		try{
			comando = "SELECT CARGA_ID, NOMBRE_CARGA, TO_CHAR(F_CREADA,'DD/MM/YYYY') F_CREADA, PERIODO, CICLO, "+
					"TO_CHAR(F_INICIO,'DD/MM/YYYY') F_INICIO, "+
					"TO_CHAR(F_FINAL,'DD/MM/YYYY') F_FINAL, "+
					"TO_CHAR(F_EXTRA,'DD/MM/YYYY') F_EXTRA, "+
					"NUM_CURSOS, ESTADO, TIPOCARGA, SEMANAS "+
					"FROM ENOC.CARGA " + 
					"WHERE PERIODO = TO_CHAR(now(),'YYYY') " +orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				Carga carga = new Carga();
				carga.mapeaReg(rs);
				lisCarga.add(carga);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaUtil|getListCargaActual|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisCarga;
	}
	
	public ArrayList<Carga> getListCargaPeriodo(Connection conn, String periodo, String orden ) throws SQLException{
		
		ArrayList<Carga> lisCarga	= new ArrayList<Carga>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
		
		try{
			comando = "SELECT CARGA_ID, NOMBRE_CARGA, TO_CHAR(F_CREADA,'DD/MM/YYYY') AS F_CREADA, PERIODO, CICLO, "+
					"TO_CHAR(F_INICIO,'DD/MM/YYYY') AS F_INICIO, "+
					"TO_CHAR(F_FINAL,'DD/MM/YYYY') AS F_FINAL, "+
					"TO_CHAR(F_EXTRA,'DD/MM/YYYY') AS F_EXTRA, "+
					"NUM_CURSOS, ESTADO, TIPOCARGA, SEMANAS, EVALUA "+
					"FROM ENOC.CARGA " + 
					"WHERE PERIODO = '"+periodo+"' "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				Carga carga = new Carga();
				carga.mapeaReg(rs);
				lisCarga.add(carga);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaUtil|getListCargaSemPas|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisCarga;
	}
	
	public ArrayList<Carga> getListSemestre(Connection conn, String orden ) throws SQLException{
		
		ArrayList<Carga> lisCargaS	= new ArrayList<Carga>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
		
		try{
			comando = "SELECT CARGA_ID, NOMBRE_CARGA, TO_CHAR(F_CREADA,'DD/MM/YYYY') AS F_CREADA, PERIODO, CICLO, "+
					"TO_CHAR(F_INICIO,'DD/MM/YYYY') F_INICIO, "+
					"TO_CHAR(F_FINAL,'DD/MM/YYYY') F_FINAL, "+
					"TO_CHAR(F_EXTRA,'DD/MM/YYYY') F_EXTRA, "+
					"NUM_CURSOS, ESTADO, TIPOCARGA, SEMANAS, EVALUA "+
					"FROM ENOC.CARGA WHERE SUBSTR(CARGA_ID,5,2) IN('1A','1B','1C','2A', '2B', '2C') "+orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				Carga carga = new Carga();
				carga.mapeaReg(rs);
				lisCargaS.add(carga);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaUtil|getListSem|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisCargaS;

	}
	
	
	public ArrayList<Carga> getListTetra(Connection conn, String orden ) throws SQLException{
		
		ArrayList<Carga> lisCargaS	= new ArrayList<Carga>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
		
		try{
			comando = "SELECT CARGA_ID, NOMBRE_CARGA, TO_CHAR(F_CREADA,'DD/MM/YYYY') AS F_CREADA, PERIODO, CICLO, "+
					"TO_CHAR(F_INICIO,'DD/MM/YYYY') F_INICIO, "+
					"TO_CHAR(F_FINAL,'DD/MM/YYYY') F_FINAL, "+
					"TO_CHAR(F_EXTRA,'DD/MM/YYYY') F_EXTRA, "+
					"NUM_CURSOS, ESTADO, TIPOCARGA, SEMANAS, EVALUA "+
					"FROM ENOC.CARGA WHERE SUBSTR(CARGA_ID,5,1)= '3'"+orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				Carga carga = new Carga();
				carga.mapeaReg(rs);
				lisCargaS.add(carga);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaUtil|getListSem|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisCargaS;

	}
	
	public ArrayList<Carga> getListPlanCurso(Connection conn, String orden ) throws SQLException{
		
		ArrayList<Carga> lisCargaS	= new ArrayList<Carga>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
		
		try{
			comando = "SELECT CARGA_ID, NOMBRE_CARGA, TO_CHAR(F_CREADA,'DD/MM/YYYY') AS F_CREADA, PERIODO, CICLO, "+
					"TO_CHAR(F_INICIO,'DD/MM/YYYY') F_INICIO, "+
					"TO_CHAR(F_FINAL,'DD/MM/YYYY') F_FINAL, "+
					"TO_CHAR(F_EXTRA,'DD/MM/YYYY') F_EXTRA, "+
					"NUM_CURSOS, ESTADO, TIPOCARGA, SEMANAS, EVALUA "+
					"FROM ENOC.CARGA WHERE CARGA_ID IN "+ 
					"(SELECT DISTINCT(SUBSTR(CURSO_CARGA_ID,1,6)) FROM ENOC.CARGA_GRUPO_PLAN)" +orden;			 
			rs = st.executeQuery(comando);
			while (rs.next()){				
				Carga carga = new Carga();
				carga.mapeaReg(rs);
				lisCargaS.add(carga);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaUtil|getListPlanCurso|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisCargaS;

	}
	
	public ArrayList<Carga> AllInCargaGrupo(Connection conn, String orden ) throws SQLException{
		
		ArrayList<Carga> lisCargaS	= new ArrayList<Carga>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
		
		try{
			comando = "SELECT CARGA_ID, NOMBRE_CARGA, TO_CHAR(F_CREADA,'DD/MM/YYYY') AS F_CREADA, PERIODO, CICLO, "+
					"TO_CHAR(F_INICIO,'DD/MM/YYYY') F_INICIO, "+
					"TO_CHAR(F_FINAL,'DD/MM/YYYY') F_FINAL, "+
					"TO_CHAR(F_EXTRA,'DD/MM/YYYY') F_EXTRA, "+
					"NUM_CURSOS, ESTADO, TIPOCARGA, SEMANAS, EVALUA "+
					"FROM ENOC.CARGA WHERE CARGA_ID IN "+ 
					"(SELECT CARGA_ID FROM ENOC.CARGA_GRUPO)" +orden;			 
			rs = st.executeQuery(comando);
			while (rs.next()){				
				Carga carga = new Carga();
				carga.mapeaReg(rs);
				lisCargaS.add(carga);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaUtil|AllInCargaGrupo|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisCargaS;

	}
		
	public ArrayList<Carga> cargas(Connection conn, String orden ) throws SQLException{
		
		ArrayList<Carga> lisCargaS	= new ArrayList<Carga>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando				= "";
		
		try{
			comando = "SELECT * " +
					"FROM ENOC.CARGA " +
					"WHERE SUBSTR(CARGA_ID,5,2) = '1A'" +
					"AND TO_DATE(TO_CHAR(F_INICIO,'YYYY'),'YYYY') <= TO_DATE(TO_CHAR(now(),'YYYY'),'YYYY')-5 ORDER BY CARGA_ID" +orden;			 
			rs = st.executeQuery(comando);
			while (rs.next()){				
				Carga carga = new Carga();
				carga.mapeaReg(rs);
				lisCargaS.add(carga);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaUtil|cargas|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisCargaS;

	}
	
	public static ArrayList<String> getPeriodos(Connection conn, String orden ) throws SQLException{
		
		ArrayList<String> list		= new ArrayList<String>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando				= "";
		
		try{
			comando = "SELECT DISTINCT(PERIODO) AS PERIODO FROM ENOC.CARGA WHERE  ESTADO = '1' AND TO_NUMBER(PERIODO,'9999') > 0102 "+orden;	 
			rs = st.executeQuery(comando);
			while (rs.next()){				
				list.add(rs.getString("PERIODO"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaUtil|getPeriodos|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return list;

	}
	
	public int numMaestrosPorCarga(Connection conn, String cargaId ) throws SQLException{
		
		int count 					= 0;
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando				= "";
		
		try{
			comando = "SELECT COUNT(DISTINCT(CODIGO_PERSONAL)) AS CODIGO_PERSONAL FROM ENOC.CARGA_GRUPO WHERE CARGA_ID = '"+cargaId+"'  AND TRIM (CODIGO_PERSONAL) IS NOT NULL";			 
			rs = st.executeQuery(comando);
			while (rs.next()){	
				count = rs.getInt("CODIGO_PERSONAL");
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaUtil|cargas|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return count;

	}
	
	public static int [] countMyF(Connection conn, String cargaId ) throws SQLException{
		
		int[] mascFem				= {0, 0};
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando				= "";
		
		try{
			comando = "SELECT GENERO, COUNT(CODIGO_PERSONAL) AS TOTAL FROM ENOC.MAESTROS "+
					"WHERE CODIGO_PERSONAL IN (SELECT DISTINCT(CODIGO_PERSONAL) "+
					"FROM ENOC.CARGA_GRUPO WHERE CARGA_ID = '"+cargaId+"') GROUP BY GENERO";
			rs = st.executeQuery(comando);
			byte i = 0;
			while (rs.next()){
				mascFem [i] = Integer.parseInt(rs.getString("TOTAL"));
				i++;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaUtil|countMyF|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return mascFem;

	}
	
	public static int promEdad(Connection conn, String cargaId ) throws SQLException{

		ArrayList<Integer> edad		= new ArrayList<Integer>();
		ArrayList<Integer> cantidad	= new ArrayList<Integer>();
		int total					= 0;
		int media 					= 0;
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando				= "";
		
		try{
			comando = "SELECT TO_NUMBER(ENOC.EMP_EDAD(CODIGO_PERSONAL),'9999') AS EDAD, COUNT(CODIGO_PERSONAL) AS CANTIDAD"+
					" FROM ENOC.MAESTROS"+
					" WHERE CODIGO_PERSONAL IN (SELECT DISTINCT(CODIGO_PERSONAL)"+
					" FROM ENOC.CARGA_GRUPO WHERE CARGA_ID = '"+cargaId+"')"+
					" AND (ENOC.EMP_EDAD(CODIGO_PERSONAL)) IS NOT NULL"+
					" GROUP BY ENOC.EMP_EDAD(CODIGO_PERSONAL)"+
					" ORDER BY 1";			 
			rs = st.executeQuery(comando);
			while (rs.next()){
				edad.add(Integer.parseInt(rs.getString("EDAD")));
				cantidad.add(Integer.parseInt(rs.getString("CANTIDAD")));
			}
			for (int p = 0; p<edad.size(); p++){
				media += cantidad.get(p) * edad.get(p);
				total += cantidad.get(p);
			}
			media /= total;
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaUtil|promEdad|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return media;

	}
	
	public static ArrayList<Carga> getListPlan(Connection conn, String planId, String orden ) throws SQLException{
		
		ArrayList<Carga> lisCarga	= new ArrayList<Carga>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
		
		try{
			comando = "SELECT CARGA_ID, NOMBRE_CARGA, TO_CHAR(F_CREADA,'DD/MM/YYYY') AS F_CREADA, PERIODO, CICLO, "+
					" TO_CHAR(F_INICIO,'DD/MM/YYYY') AS F_INICIO,"+
					" TO_CHAR(F_FINAL,'DD/MM/YYYY') AS F_FINAL,"+
					" TO_CHAR(F_EXTRA,'DD/MM/YYYY') AS F_EXTRA,"+
					" NUM_CURSOS, ESTADO, TIPOCARGA, SEMANAS, EVALUA"+
					" FROM ENOC.CARGA"+
					"  WHERE CARGA_ID IN (SELECT CARGA_ID FROM ENOC.CARGA_GRUPO WHERE CURSO_CARGA_ID IN (SELECT CURSO_CARGA_ID FROM ENOC.CARGA_GRUPO_CURSO WHERE ENOC.CURSO_PLAN(CURSO_ID)= '"+planId+"')) "+orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				Carga carga = new Carga();
				carga.mapeaReg(rs);
				lisCarga.add(carga);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargasUtil|getListPlan|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisCarga;
	}
	
public static ArrayList<String> cargasMaestro(Connection conn, String periodoId, String codigoPersonal, String orden ) throws SQLException{
		
		ArrayList<String> lisCarga	= new ArrayList<String>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
		
		try{
			comando = "SELECT CARGA_ID, NOMBRE_CARGA FROM ENOC.CARGA WHERE PERIODO ='"+periodoId+"' AND CARGA_ID IN (SELECT CARGA_ID FROM ENOC.CARGA_GRUPO WHERE CODIGO_PERSONAL ='"+codigoPersonal+"') "+orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				lisCarga.add(rs.getString("CARGA_ID")+"@@"+rs.getString("NOMBRE_CARGA"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargasUtil|cargasMaestro|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisCarga;
	}
}