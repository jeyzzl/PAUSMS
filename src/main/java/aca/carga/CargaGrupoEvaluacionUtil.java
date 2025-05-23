// Clase Util para la tabla de Carga
package aca.carga;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CargaGrupoEvaluacionUtil{
	
	public boolean insertReg(Connection conn, CargaGrupoEvaluacion evaluacion ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			if (evaluacion.getValor()==null || evaluacion.getValor().equals("null")) evaluacion.setValor("0");
			if (evaluacion.getEvaluacionE42()==null || evaluacion.getEvaluacionE42().equals("null")) 
				evaluacion.setEvaluacionE42("0");
			ps = conn.prepareStatement("INSERT INTO ENOC.CARGA_GRUPO_EVALUACION"+ 
				"(CURSO_CARGA_ID, EVALUACION_ID, NOMBRE_EVALUACION, FECHA, ESTRATEGIA_ID, VALOR, TIPO, EVALUACION_E42 ) "+
				"VALUES( ?, "+
				"TO_NUMBER(?,'99'), "+
				"?, "+
				"TO_DATE(?,'DD/MM/YYYY'), "+
				"?, "+
				"TO_NUMBER(?,'999.99'), "+
				"?," +
				"TO_NUMBER(?,'9999999') )");
					
			ps.setString(1, evaluacion.getCursoCargaId());
			ps.setString(2, evaluacion.getEvaluacionId());
			ps.setString(3, evaluacion.getNombreEvaluacion());
			ps.setString(4, evaluacion.getFecha());
			ps.setString(5, evaluacion.getEstrategiaId());
			ps.setString(6, evaluacion.getValor());
			ps.setString(7, evaluacion.getTipo());
			ps.setString(8, evaluacion.getEvaluacionE42());
			
			if (ps.executeUpdate()== 1){				
				ok = true;				
			}else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoEvaluacion|insertReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public boolean updateReg(Connection conn, CargaGrupoEvaluacion evaluacion ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.CARGA_GRUPO_EVALUACION "+ 
				"SET NOMBRE_EVALUACION = ?, "+
				"FECHA = TO_DATE(?,'DD/MM/YYYY'), "+
				"ESTRATEGIA_ID  = ?, "+
				"VALOR = TO_NUMBER(?,'999.99'), "+
				"TIPO  = ?," +
				"EVALUACION_E42 = TO_NUMBER(?,'9999999') "+
				"WHERE CURSO_CARGA_ID = ? "+
				"AND EVALUACION_ID = TO_NUMBER(?,'99')");
			
			ps.setString(1, evaluacion.getNombreEvaluacion());
			ps.setString(2, evaluacion.getFecha());
			ps.setString(3, evaluacion.getEstrategiaId());
			ps.setString(4, evaluacion.getValor());
			ps.setString(5, evaluacion.getTipo());
			ps.setString(6, evaluacion.getEvaluacionE42());
			ps.setString(7, evaluacion.getCursoCargaId());
			ps.setString(8, evaluacion.getEvaluacionId());
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoEvaluacion|updateReg|:"+ex);		 
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public boolean updateRegE42(Connection conn, CargaGrupoEvaluacion evaluacion ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.CARGA_GRUPO_EVALUACION "+ 
				"SET NOMBRE_EVALUACION = ?, "+
				"FECHA = TO_DATE(?,'DD/MM/YYYY'), "+
				"ESTRATEGIA_ID  = ?, "+
				"VALOR = TO_NUMBER(?,'999.99'), "+
				"TIPO  = ? " +				
				"WHERE EVALUACION_E42 = TO_NUMBER(?,'9999999')");
			ps.setString(1, evaluacion.getNombreEvaluacion());
			ps.setString(2, evaluacion.getFecha());
			ps.setString(3, evaluacion.getEstrategiaId());			
			ps.setString(4, evaluacion.getValor());
			ps.setString(5, evaluacion.getTipo());
			ps.setString(6, evaluacion.getEvaluacionE42());
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoEvaluacion|updateRegE42|:"+ex);		 
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public boolean deleteReg(Connection conn, String cursoCargaId, String evaluacionId ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.CARGA_GRUPO_EVALUACION WHERE CURSO_CARGA_ID = ? AND EVALUACION_ID = TO_NUMBER(?,'99')");
			ps.setString(1, cursoCargaId);
			ps.setString(2, evaluacionId);			
			if (ps.executeUpdate()== 1)
				ok = true;			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoEvaluacion|delete Reg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean deleteReg(Connection conn, String cursoCargaId) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.CARGA_GRUPO_EVALUACION "+ 
				"WHERE CURSO_CARGA_ID = ? ");
			ps.setString(1, cursoCargaId);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoEvaluacion|deleteReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean deleteRegE42(Connection conn, String evaluacionE42 ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.CARGA_GRUPO_EVALUACION "+ 
				"WHERE EVALUACION_E42 = TO_NUMBER(?,'9999999')");
			ps.setString(1, evaluacionE42);			
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoEvaluacion|deleteRegE42|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean copiarEstrategias(Connection conn, String ccOrigen, String ccDestino) throws Exception{
		boolean ok = true;
		conn.setAutoCommit(false);
		ArrayList<CargaGrupoEvaluacion> listor = getLista(conn, ccOrigen, "ORDER BY SUBSTR(FECHA,7,4)||SUBSTR(FECHA,4,5)||SUBSTR(FECHA,1,2), EVALUACION_ID");
		CargaGrupoEvaluacion origen = new CargaGrupoEvaluacion();
		CargaGrupoEvaluacion destino = new CargaGrupoEvaluacion();
		CargaGrupoEvaluacionUtil EvalU = new CargaGrupoEvaluacionUtil();
		for (int i = 0;i<listor.size();i++){
			origen = (CargaGrupoEvaluacion) listor.get(i);
			destino.setEstrategiaId(origen.getEstrategiaId());
			destino.setCursoCargaId(ccDestino);
			destino.setEvaluacionId(origen.getEvaluacionId());
			destino.setFecha(origen.getFecha());
			destino.setNombreEvaluacion(origen.getNombreEvaluacion());
			destino.setTipo(origen.getTipo());
			destino.setValor(origen.getValor());
			ok &= EvalU.insertReg(conn, destino);
		}
		if(ok){
			conn.commit();
		}else{
			conn.rollback();
		}
		conn.setAutoCommit(true);
		return ok;
	}
	
	public ArrayList<CargaGrupoEvaluacion> getTareas(Connection conn, String codigoPersonal, String cargaId, String fecha) throws SQLException{
		
		ArrayList<CargaGrupoEvaluacion> lisTareas	= new ArrayList<CargaGrupoEvaluacion>();
		ResultSet rs 		= null;
		String comando		= "";
		ArrayList<aca.vista.AlumnoCurso> lisCursos = new aca.vista.AlumnoCursoUtil().getListAlumnoCarga(conn,codigoPersonal, cargaId, "ORDER BY NOMBRE_CURSO");
		try{
			
			comando = "SELECT CURSO_CARGA_ID, EVALUACION_ID, NOMBRE_EVALUACION, "+
				"TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, ESTRATEGIA_ID, VALOR, " +
				"TIPO, EVALUACION_E42 "+
				"FROM ENOC.CARGA_GRUPO_EVALUACION "+ 
				"WHERE CURSO_CARGA_ID = ? AND FECHA = to_date(?,'dd/mm/yyyy')";
			PreparedStatement ps = conn.prepareStatement(comando);
			for (int i=0;i<lisCursos.size();i++){
				ps.setString(1,((aca.vista.AlumnoCurso)lisCursos.get(i)).getCursoCargaId());
				ps.setString(2,fecha);
				rs = ps.executeQuery();
				while (rs.next()){
					CargaGrupoEvaluacion evaluacion = new CargaGrupoEvaluacion();
					evaluacion.mapeaReg(rs);
					lisTareas.add(evaluacion);
				}
			}
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoEvaluacionUtil|getTareas|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
		}
		return lisTareas;
	}
	
	public CargaGrupoEvaluacion mapeaRegId( Connection conn, String cursoCargaId, String evaluacionId ) throws SQLException{
		
		CargaGrupoEvaluacion evaluacion = new CargaGrupoEvaluacion();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement("SELECT "+
				"CURSO_CARGA_ID, EVALUACION_ID, NOMBRE_EVALUACION, "+
				"TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, ESTRATEGIA_ID, VALOR, " +
				"TIPO, EVALUACION_E42 "+
				"FROM ENOC.CARGA_GRUPO_EVALUACION "+ 
				"WHERE CURSO_CARGA_ID = ? "+
				"AND EVALUACION_ID = TO_NUMBER(?,'99')");
			ps.setString(1, cursoCargaId);
			ps.setString(2, evaluacionId);
			
			rs = ps.executeQuery();
			if (rs.next()){
				evaluacion.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoEvaluacion|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return evaluacion;
	}
	
	public boolean existeReg(Connection conn, String cursoCargaId, String evaluacionId) throws SQLException{
		boolean 			ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.CARGA_GRUPO_EVALUACION "+ 
				"WHERE CURSO_CARGA_ID = ? AND EVALUACION_ID = TO_NUMBER(?,'99')");
			ps.setString(1, cursoCargaId);
			ps.setString(2, evaluacionId);
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoEvaluacion|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public boolean existeRegE42(Connection conn, String evaluacionE42) throws SQLException{
		boolean 			ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.CARGA_GRUPO_EVALUACION "+ 
				"WHERE EVALUACION_E42 = TO_NUMBER(?,'9999999')");
			ps.setString(1, evaluacionE42);
						
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoEvaluacion|existeRegE42|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public String maximoReg(Connection conn, String cursoCargaId) throws SQLException{
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		String maximo			= "1";
		
		try{
			ps = conn.prepareStatement("SELECT COALESCE(MAX(EVALUACION_ID)+1,1) AS MAXIMO "+
				"FROM ENOC.CARGA_GRUPO_EVALUACION "+ 
				"WHERE CURSO_CARGA_ID = ?");
			ps.setString(1, cursoCargaId);
			
			rs = ps.executeQuery();
			if (rs.next())
				maximo = rs.getString("MAXIMO");			
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoEvaluacion|maximoReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return maximo;
	}
	
	public boolean notasReg(Connection conn, String cursoCargaId, String evaluacionId) throws SQLException{
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		boolean bOk				= false;
		
		try{
			ps = conn.prepareStatement("SELECT * "+
				"FROM ENOC.KRDX_ALUMNO_EVAL "+ 
				"WHERE CURSO_CARGA_ID = ? "+
				"AND EVALUACION_ID = TO_NUMBER(?,'99') "+
				"AND NOTA!=0");
			ps.setString(1, cursoCargaId);
			ps.setString(2, evaluacionId);
			
			rs = ps.executeQuery();
			if (rs.next()) bOk = true;							
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoEvaluacion|notasReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return bOk;
	}
	
	public boolean deleteNotas(Connection conn, String cursoCargaId, String evaluacionId) throws SQLException{
		boolean bOk			 	= false;		
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.KRDX_ALUMNO_EVAL "+ 
				"WHERE CURSO_CARGA_ID = ? "+
				"AND EVALUACION_ID = TO_NUMBER(?,'99')");	
			ps.setString(1, cursoCargaId);
			ps.setString(2, evaluacionId);
			
			if (ps.executeUpdate() > 0)		
				bOk = true;
			else
				bOk = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoEvaluacion|deleteNotas|:"+ex);
		}finally{		
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return bOk;
	}
	
	public double promedioEstrategia(Connection conn, String cursoCargaId, String evaluacionId) throws SQLException{
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		double promedio=0, alumnos=0;
		
		
		try{
			ps = conn.prepareStatement("SELECT NOTA "+
				"FROM ENOC.KRDX_ALUMNO_EVAL "+ 
				"WHERE CURSO_CARGA_ID = ? "+
				"AND EVALUACION_ID = TO_NUMBER(?,'99')");
			ps.setString(1, cursoCargaId);
			ps.setString(2, evaluacionId);			
			rs = ps.executeQuery();
			while(rs.next()){
				alumnos++;
				promedio += rs.getDouble("NOTA");
			}
			promedio = promedio / alumnos;		
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoEvaluacion|promedioEstrategia|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return promedio;
	}
	
	public int NumEstVirtual(Connection conn, String cursoCargaId) throws SQLException{
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		int cuenta				= 0;
		
		try{
			ps = conn.prepareStatement("SELECT COALESCE(COUNT(EVALUACION_ID),0) AS CUENTA "+
				"FROM ENOC.CARGA_GRUPO_EVALUACION "+ 
				"WHERE CURSO_CARGA_ID = ? AND (EVALUACION_E42=0 OR EVALUACION_E42 IS NULL)");
			ps.setString(1, cursoCargaId);
			
			rs = ps.executeQuery();
			if (rs.next())
				cuenta = rs.getInt("CUENTA");
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoEvaluacion|NumEstVirtual|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return cuenta;
	}
	
	public int NumEstE42(Connection conn, String cursoCargaId) throws SQLException{
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		int cuenta				= 0;
		
		try{
			ps = conn.prepareStatement("SELECT COALESCE(COUNT(EVALUACION_ID),0) AS CUENTA "+
				"FROM ENOC.CARGA_GRUPO_EVALUACION "+ 
				"WHERE CURSO_CARGA_ID = ? AND (EVALUACION_E42 != 0 AND EVALUACION_E42 IS NOT NULL)");
			ps.setString(1, cursoCargaId);
			
			rs = ps.executeQuery();
			if (rs.next())
				cuenta = rs.getInt("CUENTA");
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoEvaluacion|NumEstE42|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return cuenta;
	}
	
	public int NumEstrategias(Connection conn, String cursoCargaId, String Tipo) throws SQLException{
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;		
		String buscarTipo		= "";
		int cuenta				= 0;
		
		try{
			if (Tipo.equals("VIRTUAL")) 
				buscarTipo = "AND EVALUACION_E42=0 ";
			else
				buscarTipo = "AND EVALUACION_E42!=0 ";
			
			ps = conn.prepareStatement("SELECT COALESCE(COUNT(EVALUACION_ID),0) AS CUENTA "+
				"FROM ENOC.CARGA_GRUPO_EVALUACION "+ 
				"WHERE CURSO_CARGA_ID = ? " +buscarTipo);
			ps.setString(1, cursoCargaId);
			
			rs = ps.executeQuery();
			if (rs.next())
				cuenta = rs.getInt("CUENTA");
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoEvaluacion|NumEstrategias|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return cuenta;
	}
	
	public static int getNumEst(Connection conn, String cursoCargaId, String Tipo) throws SQLException{
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;		
		String buscarTipo		= "";
		int cuenta				= 0;
		
		try{
			if (Tipo.equals("VIRTUAL")) 
				buscarTipo = "AND EVALUACION_E42=0 ";
			else if (Tipo.equals("E42"))
				buscarTipo = "AND EVALUACION_E42!=0 ";
			else
				buscarTipo = " ";
			
			ps = conn.prepareStatement("SELECT COALESCE(COUNT(EVALUACION_ID),0) AS CUENTA "+
				"FROM ENOC.CARGA_GRUPO_EVALUACION "+ 
				"WHERE CURSO_CARGA_ID = ? " +buscarTipo);
			ps.setString(1, cursoCargaId);
			
			rs = ps.executeQuery();
			if (rs.next())
				cuenta = rs.getInt("CUENTA");
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoEvaluacion|getNumEst|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return cuenta;
	}
	
	public static String getEvaluacionId(Connection conn, String cursoCargaId, String evaluacionE42 ) throws SQLException{
		ResultSet 			rs	= null;
		PreparedStatement 	ps	= null;
		String evaluacion		= "0";
		
		try{
			ps = conn.prepareStatement("SELECT EVALUACION_ID FROM ENOC.CARGA_GRUPO_EVALUACION "+ 
				"WHERE CURSO_CARGA_ID = ? "+				
				"AND EVALUACION_E42 = TO_NUMBER(?,'9999999')");
			ps.setString(1, cursoCargaId);
			ps.setString(2, evaluacionE42);
			
			rs = ps.executeQuery();
			if (rs.next())
				 evaluacion = rs.getString("EVALUACION_ID");
			else
				evaluacion ="0";
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoEvaluacion|getEvaluacionId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return evaluacion;
	}
	
	public static String getCursoCargaId(Connection conn, String evaluacionE42 ) throws SQLException{
		ResultSet 			rs	= null;
		PreparedStatement 	ps	= null;
		String evaluacion		= "0";
		
		try{
			ps = conn.prepareStatement("SELECT CURSO_CARGA_ID FROM ENOC.CARGA_GRUPO_EVALUACION "+ 
				"WHERE EVALUACION_E42 = TO_NUMBER(?,'9999999')");
			ps.setString(1, evaluacionE42);
			
			rs = ps.executeQuery();
			if (rs.next())
				 evaluacion = rs.getString("CURSO_CARGA_ID");
			else
				evaluacion ="0";
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoEvaluacion|getCursoCargaId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return evaluacion;
	}
	
	public static boolean getTieneActividades(Connection conn, String cursoCargaId, String evaluacionId ) throws SQLException{
		ResultSet 			rs	= null;
		PreparedStatement 	ps	= null;
		boolean ok				= false;
		
		try{
			ps = conn.prepareStatement("SELECT CURSO_CARGA_ID||EVALUACION_ID FROM ENOC.CARGA_GRUPO_ACTIVIDAD "+ 
				"WHERE CURSO_CARGA_ID = ? AND EVALUACION_ID = ?");
			ps.setString(1, cursoCargaId);
			ps.setString(2, evaluacionId);
			
			rs = ps.executeQuery();
			if (rs.next())
				 ok = true;			
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoEvaluacion|getTieneActividades|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public static int deleteGpoEval(Connection conn, String cursoCargaId ) throws SQLException{
		
		PreparedStatement ps	= null;
		int numEval				= 0;
		
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.CARGA_GRUPO_EVALUACION WHERE CURSO_CARGA_ID = ?"); 
			ps.setString(1, cursoCargaId);
			numEval = ps.executeUpdate();
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoEvaluacion|deleteGpoEval|:"+ex);
		}finally{			
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return numEval;
	}
	
	
	public ArrayList<CargaGrupoEvaluacion> getTareas(Connection conn, String codigoPersonal, String cargaId, String fechaI, String fechaF) throws SQLException{
		
		ArrayList<CargaGrupoEvaluacion> lisTareas	= new ArrayList<CargaGrupoEvaluacion>();
		ResultSet rs 		= null;
		String comando		= "";		
		try{
			comando = "SELECT CURSO_CARGA_ID, EVALUACION_ID, NOMBRE_EVALUACION, "+
				"TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, ESTRATEGIA_ID, VALOR, " +
				"TIPO, EVALUACION_E42 "+
				"FROM ENOC.CARGA_GRUPO_EVALUACION "+ 
				"WHERE SUBSTR(CURSO_CARGA_ID,1,6) = ? "+
				"AND FECHA BETWEEN TO_DATE(?,'DD/MM/YYYY') AND TO_DATE(?,'DD/MM/YYYY') "+
				"AND CURSO_CARGA_ID IN "+ 
			 		"(SELECT CURSO_CARGA_ID FROM ENOC.KRDX_CURSO_ACT "+ 
			 		"WHERE CODIGO_PERSONAL = ? "+ 
			 		"AND SUBSTR(CURSO_CARGA_ID,1,6) = ?) "+
			 	"ORDER BY FECHA, ENOC.NOMBRE_MATERIA(CURSO_ORIGEN(CURSO_CARGA_ID))";
			
			PreparedStatement ps = conn.prepareStatement(comando);	
				ps.setString(1,cargaId);
				ps.setString(2,fechaI);
				ps.setString(3,fechaF);
				ps.setString(4,codigoPersonal);
				ps.setString(5,cargaId);				
				rs = ps.executeQuery();
				while (rs.next()){
					CargaGrupoEvaluacion evaluacion = new CargaGrupoEvaluacion();
					evaluacion.mapeaReg(rs);
					lisTareas.add(evaluacion);
				}			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoEvaluacionUtil|getTareas|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
		}
		return lisTareas;
	}
	
	public ArrayList<CargaGrupoEvaluacion> getLista(Connection conn, String cursoCargaId, String orden ) throws SQLException{
		
		ArrayList<CargaGrupoEvaluacion> lisEvaluacion	= new ArrayList<CargaGrupoEvaluacion>();
		Statement st 					= conn.createStatement();
		ResultSet rs 					= null;
		String comando			= "";
		
		try{
			comando = "SELECT CURSO_CARGA_ID, EVALUACION_ID, NOMBRE_EVALUACION, "+
				"TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, ESTRATEGIA_ID, VALOR, " +
				"TIPO, EVALUACION_E42 "+
				"FROM ENOC.CARGA_GRUPO_EVALUACION "+ 
				"WHERE CURSO_CARGA_ID = '"+cursoCargaId+"' "+orden;			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				CargaGrupoEvaluacion evaluacion = new CargaGrupoEvaluacion();
				evaluacion.mapeaReg(rs);
				lisEvaluacion.add(evaluacion);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoEvaluacionUtil|getLista|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisEvaluacion;
	}	
	
	public ArrayList<CargaGrupoEvaluacion> getListAll(Connection conn, String orden ) throws SQLException{
		
		ArrayList<CargaGrupoEvaluacion> lisEvaluacion	= new ArrayList<CargaGrupoEvaluacion>();
		Statement st 					= conn.createStatement();
		ResultSet rs 					= null;
		String comando			= "";
		
		try{
			comando = "SELECT CURSO_CARGA_ID, EVALUACION_ID, NOMBRE_EVALUACION, "+
				"TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, ESTRATEGIA_ID, VALOR, " +
				"TIPO, EVALUACION_E42 "+
				"FROM ENOC.CARGA_GRUPO_EVALUACION "+orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				CargaGrupoEvaluacion evaluacion = new CargaGrupoEvaluacion();
				evaluacion.mapeaReg(rs);
				lisEvaluacion.add(evaluacion);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoEvaluacionUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisEvaluacion;
	}
	public int getNumEstrategias(Connection conn, String cursoCargaId) throws SQLException{
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
		int num = 0;
		
		try{
			comando = "SELECT count(*) from ENOC.CARGA_GRUPO_EVALUACION WHERE CURSO_CARGA_ID = '"+cursoCargaId+"'"; 
			rs = st.executeQuery(comando);
			if (rs.next()){
				num = rs.getInt(1);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoEvaluacionUtil|getNumEstrategias|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return num;
		
	}
	
	public int getNumEstrategiasEvaluadas(Connection conn, String cursoCargaId) throws SQLException{
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
		int num = 0;
		
		try{
			comando = "SELECT ENOC.NUM_ESTRA_EVAL('"+cursoCargaId+"') FROM ENOC.KRDX_ALUMNO_EVAL WHERE CURSO_CARGA_ID ='"+cursoCargaId+"'"; 
			rs = st.executeQuery(comando);
			if (rs.next()){
				num = rs.getInt(1);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoEvaluacionUtil|getNumEstrategiasEvaluadas|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return num;
		
	}
	
	public int getNumAlumnosBaja(Connection conn, String cursoCargaId) throws SQLException{
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
		int num = 0;
		
		try{
			comando = "SELECT COUNT(CODIGO_PERSONAL) " +
					"FROM ENOC.KRDX_CURSO_ACT " + 
					"WHERE CURSO_CARGA_ID ='"+cursoCargaId+"'" +
					"AND TIPOCAL_ID IN('3','4')";
			rs = st.executeQuery(comando);
			if (rs.next()){
				num = rs.getInt(1);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoEvaluacionUtil|getNumAlumnosBaja|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return num;
		
	}
	
	public ArrayList<String> getEstrategiasMes(Connection conn, String cargaId, String orden) throws SQLException{
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		ArrayList<String> lisEst		= new ArrayList<String>();
		
		try{
			comando = "SELECT CURSO_CARGA_ID, VALOR, TO_CHAR(FECHA,'MM') AS FECHA, EVALUACION_ID" +
					" FROM ENOC.CARGA_GRUPO_EVALUACION" + 
					" WHERE SUBSTR(CURSO_CARGA_ID,1,6) = '"+cargaId+"' "+orden;
			rs = st.executeQuery(comando);
			while(rs.next()){
				lisEst.add(rs.getString("CURSO_CARGA_ID"));
				lisEst.add(rs.getString("VALOR"));
				lisEst.add(rs.getString("FECHA"));
				lisEst.add(rs.getString("EVALUACION_ID"));
			}			
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoEvaluacionUtil|getEstrategiasMes|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisEst;
		
	}
	
	public ArrayList<String> getEstMes(Connection conn, String cargaId, String orden) throws SQLException{
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		ArrayList<String> lisEst		= new ArrayList<String>();
		
		try{
			comando = "SELECT "+ 
			  		"ENOC.FACULTAD(A.CARRERA_ID) AS FACULTAD, "+
			  		"CARRERA_ID, "+
			  		"B.CURSO_CARGA_ID, "+ 
			  		"B.VALOR, "+ 
			  		"TO_CHAR(B.FECHA,'MM') AS FECHA "+
					"FROM ENOC.CARGA_GRUPO A, ENOC.CARGA_GRUPO_EVALUACION B "+ 
					"WHERE CARGA_ID= '"+cargaId+"' "+
					"AND B.CURSO_CARGA_ID = A.CURSO_CARGA_ID "+ orden;
			rs = st.executeQuery(comando);
			while(rs.next()){
				lisEst.add(rs.getString("FACULTAD"));
				lisEst.add(rs.getString("CARRERA_ID"));
				lisEst.add(rs.getString("CURSO_CARGA_ID"));
				lisEst.add(rs.getString("VALOR"));
				lisEst.add(rs.getString("FECHA"));
			}			
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoEvaluacionUtil|getEstMes|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisEst;
		
	}
	
	public static HashMap<String, String> mapSumaEsquema(Connection conn, String cargaId) throws SQLException{
		HashMap<String, String> mapa = new HashMap<String, String>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
	
		try{
			comando = "SELECT CURSO_CARGA_ID, COALESCE(SUM(VALOR),0 ) AS PORCENTAJE FROM ENOC.CARGA_GRUPO_EVALUACION WHERE SUBSTR(CURSO_CARGA_ID, 1,6) = '"+cargaId+"'"+
					  " GROUP BY CURSO_CARGA_ID";

			rs = st.executeQuery(comando);
			while (rs.next()){
				mapa.put(rs.getString("CURSO_CARGA_ID"), rs.getString("PORCENTAJE"));
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoEvaluacionUtil|mapSumaEsquema|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return mapa;
	}
	
	public HashMap<String, String> mapaAvanceCargayFacultad(Connection conn, String cargaId, String facultadId) throws SQLException{
		HashMap<String, String> mapa = new HashMap<String, String>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		try{
			String comando = "SELECT CURSO_CARGA_ID AS LLAVE,"
					+ " SUM("
					+ " 	CASE WHEN ENOC.ACTIVIDAD_POR_EVAL(CGE.CURSO_CARGA_ID,CGE.EVALUACION_ID) >= 1 "
					+ "		THEN ROUND(ENOC.ACTIVIDAD_CON_EVAL(CGE.CURSO_CARGA_ID,CGE.EVALUACION_ID) / (ENOC.MATERIA_ALUMNOS(CURSO_CARGA_ID) * ENOC.ACTIVIDAD_POR_EVAL(CGE.CURSO_CARGA_ID,CGE.EVALUACION_ID)) * VALOR,2) "
					+ " 	ELSE ROUND(ENOC.EVALUACION_CON_NOTA(CGE.CURSO_CARGA_ID,CGE.EVALUACION_ID) / ENOC.MATERIA_ALUMNOS(CURSO_CARGA_ID) * VALOR,2) "
					+ "		END) "
					+ " AS VALOR"
					+ " FROM CARGA_GRUPO_EVALUACION CGE"
					+ " WHERE CURSO_CARGA_ID IN (SELECT CURSO_CARGA_ID FROM ENOC.CARGA_GRUPO WHERE CARGA_ID = '"+cargaId+"' AND FACULTAD(CARRERA_ID) = "+facultadId+")"
					+ " AND ENOC.MATERIA_ALUMNOS(CURSO_CARGA_ID) >= 1"
					+ " GROUP BY CURSO_CARGA_ID";
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				mapa.put(rs.getString("LLAVE"), rs.getString("VALOR"));
			}
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoEvaluacionUtil|mapaAvanceCargayFacultad|:"+ex);
		}
		return mapa;
	}
	
	public HashMap<String, String> mapaAvanceMaestro(Connection conn, String codigoPersonal) throws SQLException{
		HashMap<String, String> mapa = new HashMap<String, String>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		try{
			String comando = "SELECT CURSO_CARGA_ID AS LLAVE,"
					+ " SUM("
					+ " 	CASE WHEN ENOC.ACTIVIDAD_POR_EVAL(CGE.CURSO_CARGA_ID,CGE.EVALUACION_ID) >= 1 "
					+ "		THEN ROUND(ENOC.ACTIVIDAD_CON_EVAL(CGE.CURSO_CARGA_ID,CGE.EVALUACION_ID) / (ENOC.MATERIA_ALUMNOS(CURSO_CARGA_ID) * ENOC.ACTIVIDAD_POR_EVAL(CGE.CURSO_CARGA_ID,CGE.EVALUACION_ID)) * VALOR,2) "
					+ " 	ELSE ROUND(ENOC.EVALUACION_CON_NOTA(CGE.CURSO_CARGA_ID,CGE.EVALUACION_ID) / ENOC.MATERIA_ALUMNOS(CURSO_CARGA_ID) * VALOR,2) "
					+ "		END) "
					+ " AS VALOR"
					+ " FROM CARGA_GRUPO_EVALUACION CGE"
					+ " WHERE CURSO_CARGA_ID IN (SELECT CURSO_CARGA_ID FROM ENOC.CARGA_GRUPO WHERE CODIGO_PERSONAL = '"+codigoPersonal+"')"
					+ " AND ENOC.MATERIA_ALUMNOS(CURSO_CARGA_ID) >= 1"
					+ " GROUP BY CURSO_CARGA_ID";

			rs = st.executeQuery(comando);
			while (rs.next()){
				mapa.put(rs.getString("LLAVE"), rs.getString("VALOR"));
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoEvaluacionUtil|mapaAvanceMaestro|:"+ex);
		}
		return mapa;
	}
	
	public HashMap<String, String> mapaEvalPendientes(Connection conn, String codigoPersonal) throws SQLException{
		HashMap<String, String> mapa = new HashMap<String, String>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		try{
			String comando = "SELECT CURSO_CARGA_ID AS LLAVE, COUNT(*) AS VALOR FROM ENOC.CARGA_GRUPO_EVALUACION CGE"
					+ " WHERE CURSO_CARGA_ID IN ( SELECT CURSO_CARGA_ID FROM ENOC.CARGA_GRUPO WHERE CODIGO_PERSONAL = '"+codigoPersonal+"')"
					+ " AND FECHA < TO_DATE(TO_CHAR(SYSDATE,'DD/MM/YYYY'),'DD/MM/YYYY')"
					+ " AND ENOC.EVALUACION_CON_NOTA(CGE.CURSO_CARGA_ID,CGE.EVALUACION_ID) < (ENOC.MATERIA_ALUMNOS(CURSO_CARGA_ID)/2)"
					+ " AND ENOC.MATERIA_ALUMNOS(CURSO_CARGA_ID) >= 1"
					+ " GROUP BY CURSO_CARGA_ID";	
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				mapa.put(rs.getString("LLAVE"), rs.getString("VALOR"));
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoEvaluacionUtil|mapaEvalPendientes|:"+ex);
		}
		return mapa;
	}
	
	public HashMap<String, String> mapaActPendientes(Connection conn, String codigoPersonal) throws SQLException{
		HashMap<String, String> mapa = new HashMap<String, String>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		try{
			String comando = "SELECT CURSO_CARGA_ID AS LLAVE, COUNT(ACTIVIDAD_ID) AS VALOR FROM ENOC.CARGA_GRUPO_ACTIVIDAD CGA"
					+ " WHERE CURSO_CARGA_ID IN ( SELECT CURSO_CARGA_ID FROM ENOC.CARGA_GRUPO WHERE CODIGO_PERSONAL = '"+codigoPersonal+"')"
					+ " AND FECHA < TO_DATE(TO_CHAR(SYSDATE,'DD/MM/YYYY'),'DD/MM/YYYY')"
					+ " AND ENOC.ACTIVIDAD_CON_NOTA(CGA.ACTIVIDAD_ID) < (ENOC.MATERIA_ALUMNOS(CURSO_CARGA_ID)/2)"
					+ " AND ENOC.MATERIA_ALUMNOS(CURSO_CARGA_ID) >= 1"
					+ " GROUP BY CURSO_CARGA_ID";					

			rs = st.executeQuery(comando);
			while (rs.next()){
				mapa.put(rs.getString("LLAVE"), rs.getString("VALOR"));
			}		
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoEvaluacionUtil|mapaActPendientes|:"+ex);
		}
		return mapa;
	}
	
	public HashMap<String, String> mapaEvalPendientesPorCarga(Connection conn, String cargaId, String facultadId) throws SQLException{
		HashMap<String, String> mapa = new HashMap<String, String>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		try{
			String comando = "SELECT CURSO_CARGA_ID AS LLAVE, COUNT(*) AS VALOR FROM ENOC.CARGA_GRUPO_EVALUACION CGE"
					+ " WHERE CURSO_CARGA_ID IN ( SELECT CURSO_CARGA_ID FROM ENOC.CARGA_GRUPO WHERE CARGA_ID = '"+cargaId+"' AND FACULTAD(CARRERA_ID) = '"+facultadId+"')"
					+ " AND FECHA < TO_DATE(TO_CHAR(SYSDATE,'DD/MM/YYYY'),'DD/MM/YYYY')"
					+ " AND ENOC.EVALUACION_CON_NOTA(CGE.CURSO_CARGA_ID,CGE.EVALUACION_ID) < (ENOC.MATERIA_ALUMNOS(CURSO_CARGA_ID)/2)"
					+ " AND ENOC.MATERIA_ALUMNOS(CURSO_CARGA_ID) >= 1"
					+ " GROUP BY CURSO_CARGA_ID";		
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				mapa.put(rs.getString("LLAVE"), rs.getString("VALOR"));
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoEvaluacionUtil|mapaEvalPendientesPorCarga|:"+ex);
		}
		return mapa;
	}
	
	public HashMap<String, String> mapaActPendientesPorCarga(Connection conn, String cargaId, String facultadId) throws SQLException{
		HashMap<String, String> mapa = new HashMap<String, String>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		try{
			String comando = "SELECT CURSO_CARGA_ID AS LLAVE, COUNT(ACTIVIDAD_ID) AS VALOR FROM ENOC.CARGA_GRUPO_ACTIVIDAD CGA"
					+ " WHERE CURSO_CARGA_ID IN ( SELECT CURSO_CARGA_ID FROM ENOC.CARGA_GRUPO WHERE CARGA_ID = '"+cargaId+"' AND FACULTAD(CARRERA_ID) = '"+facultadId+"')"
					+ " AND FECHA < TO_DATE(TO_CHAR(SYSDATE,'DD/MM/YYYY'),'DD/MM/YYYY')"
					+ " AND ENOC.ACTIVIDAD_CON_NOTA(CGA.ACTIVIDAD_ID) < (ENOC.MATERIA_ALUMNOS(CURSO_CARGA_ID)/2)"
					+ " AND ENOC.MATERIA_ALUMNOS(CURSO_CARGA_ID) >= 1"
					+ " GROUP BY CURSO_CARGA_ID";			
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				mapa.put(rs.getString("LLAVE"), rs.getString("VALOR"));
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.carga.spring.CargaGrupoEvaluacionUtil|mapaActPendientesPorCarga|:"+ex);
		}
		return mapa;
	}
}