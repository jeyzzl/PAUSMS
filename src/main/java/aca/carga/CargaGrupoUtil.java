// Clase Util para la tabla de Carga
package aca.carga;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import aca.catalogo.CatCarrera;
import aca.catalogo.CatFacultad;
import aca.catalogo.CatFacultadUtil;

public class CargaGrupoUtil{	
	
	public boolean insertReg(Connection conn, CargaGrupo grupo ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.CARGA_GRUPO"+ 
				"(CURSO_CARGA_ID, CARGA_ID, BLOQUE_ID, CARRERA_ID, CODIGO_PERSONAL, GRUPO,"+
				" MODALIDAD_ID, F_INICIO, F_FINAL, F_ENTREGA, RESTRICCION, ESTADO, HORARIO, F_EVALUACION," +
				" VALEUCAS, NUM_ALUM, SEMANAS, OPTATIVA, COMENTARIO, CODIGO_OTRO, PRECIO) "+
				"VALUES( ?, ?, "+
				"TO_NUMBER(?,'99'), "+
				"?, ?, ?, "+
				"TO_NUMBER(?,'99'), "+
				"TO_DATE(?,'DD/MM/YYYY'), "+
				"TO_DATE(?,'DD/MM/YYYY'), "+
				"TO_DATE(?,'DD/MM/YYYY'), "+
				"?, ?, ?, "+
				"TO_DATE(?,'DD/MM/YYYY')," +
				" ?, TO_NUMBER(?, '999'), " +
				"TO_NUMBER(?, '999'),?, ?, ?, TO_NUMBER(?, '999999.99'))");
			
			ps.setString(1,  grupo.getCursoCargaId());
			ps.setString(2,  grupo.getCargaId());
			ps.setString(3,  grupo.getBloqueId());
			ps.setString(4,  grupo.getCarreraId());
			ps.setString(5,  grupo.getCodigoPersonal());
			ps.setString(6,  grupo.getGrupo());
			ps.setString(7,  grupo.getModalidadId());
			ps.setString(8,  grupo.getFInicio());
			ps.setString(9,  grupo.getFFinal());
			ps.setString(10, grupo.getFEntrega());
			ps.setString(11, grupo.getRestriccion());
			ps.setString(12, grupo.getEstado());
			ps.setString(13, grupo.getHorario());
			ps.setString(14, grupo.getFEvaluacion());
			ps.setString(15, grupo.getValeucas());
			ps.setString(16, grupo.getNumAlum());
			ps.setString(17, grupo.getSemanas());
			ps.setString(18, grupo.getOptativa());
			ps.setString(19, grupo.getComentario());
			ps.setString(20, grupo.getCodigoOtro());
			ps.setString(21, grupo.getPrecio());
			
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupo|insertReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public boolean updateReg(Connection conn, CargaGrupo grupo ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.CARGA_GRUPO"+ 
				" SET CARGA_ID = ?,"+
				" BLOQUE_ID = TO_NUMBER(?,'99'),"+
				" CARRERA_ID = ?,"+
				" CODIGO_PERSONAL = ?,"+
				" GRUPO = ?,"+
				" MODALIDAD_ID = TO_NUMBER(?,'99'),"+
				" F_INICIO = TO_DATE(?,'DD/MM/YYYY'),"+
				" F_FINAL  = TO_DATE(?,'DD/MM/YYYY'),"+
				" F_ENTREGA= TO_DATE(?,'DD/MM/YYYY'),"+
				" RESTRICCION = ?,"+
				" ESTADO = ?,"+
				" HORARIO = ?,"+
				" F_EVALUACION = TO_DATE(?,'DD/MM/YYYY'),"+
				" VALEUCAS = ?,"+
				" NUM_ALUM = TO_NUMBER(?, '999'),"+
				" SEMANAS = TO_NUMBER(?, '999'),"+
				" OPTATIVA = ?," +
				" COMENTARIO = ?,"+
				" CODIGO_OTRO = ?,"+
				" PRECIO = TO_NUMBER(?, '999999.99')"+
				" WHERE CURSO_CARGA_ID = ?");		
			
			ps.setString(1,  grupo.getCargaId());
			ps.setString(2,  grupo.getBloqueId());
			ps.setString(3,  grupo.getCarreraId());
			ps.setString(4,  grupo.getCodigoPersonal());
			ps.setString(5,  grupo.getGrupo());
			ps.setString(6,  grupo.getModalidadId());
			ps.setString(7,  grupo.getFInicio());
			ps.setString(8,  grupo.getFFinal());
			ps.setString(9, grupo.getFEntrega());
			ps.setString(10, grupo.getRestriccion());
			ps.setString(11, grupo.getEstado());
			ps.setString(12, grupo.getHorario());
			ps.setString(13, grupo.getFEvaluacion());
			ps.setString(14, grupo.getValeucas());
			ps.setString(15, grupo.getNumAlum());
			ps.setString(16, grupo.getSemanas());
			ps.setString(17, grupo.getOptativa());
			ps.setString(18, grupo.getComentario());
			ps.setString(19, grupo.getCodigoOtro());
			ps.setString(20, grupo.getPrecio());
			ps.setString(21,  grupo.getCursoCargaId());
			
			if (ps.executeUpdate()== 1){
				ok = true;	
				chkFechas(conn, grupo.getCursoCargaId());
			}else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupo|updateReg|:"+ex);		 
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public boolean updateHorario(Connection conn, String cursoCargaId, String horario ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.CARGA_GRUPO "+ 
				"SET HORARIO = ? "+
				"WHERE CURSO_CARGA_ID = ? ");			
			ps.setString(1, horario);
			ps.setString(2, cursoCargaId);
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupo|updateHorario|:"+ex);		 
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public boolean updateFEvaluacion(Connection conn, String cursoCargaId, String fEvaluacion ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.CARGA_GRUPO "+ 
				"SET F_EVALUACION = TO_DATE(?,'DD/MM/YYYY') "+
				"WHERE CURSO_CARGA_ID = ? ");
			ps.setString(1, fEvaluacion);
			ps.setString(2, cursoCargaId);
			
			if (ps.executeUpdate()== 1){
				ok = true;
				chkFechas(conn, cursoCargaId);
			}else{
				ok = false;
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupo|updateFEvaluacion|:"+ex);		 
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public boolean updateFechas(Connection conn, String cursoCargaId, String fechaIni, String fechaFin, String grupo, String precio, String comentario, String restriccion) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.CARGA_GRUPO"+ 
				" SET F_INICIO = TO_DATE(?,'DD/MM/YYYY')," +
				" F_FINAL =  TO_DATE(?,'DD/MM/YYYY')," +
				" GRUPO = ?, PRECIO = TO_NUMBER(?,'999999.99'), COMENTARIO = ?,"+
				" RESTRICCION = ? "+
				" WHERE CURSO_CARGA_ID = ? ");
			ps.setString(1, fechaIni);
			ps.setString(2, fechaFin);
			ps.setString(3, grupo);
			ps.setString(4, precio);
			ps.setString(5, comentario);
			ps.setString(6, restriccion);
			ps.setString(7, cursoCargaId);
			
			if (ps.executeUpdate()== 1){
				ok = true;				
			}else{
				ok = false;
			}	
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupo|updateFechas|:"+ex); 
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public boolean updateEstado(Connection conn, String cursoCargaId, String estado ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.CARGA_GRUPO "+ 
				"SET ESTADO = ? "+
				"WHERE CURSO_CARGA_ID = ? ");
			ps.setString(1, estado);
			ps.setString(2, cursoCargaId);
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupo|updateEstado|:"+ex);		 
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public boolean updateBloque(Connection conn, String cursoCargaId, String bloqueId) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.CARGA_GRUPO "+ 
				" SET BLOQUE_ID = ? "+
				" WHERE CURSO_CARGA_ID = ? ");
			ps.setString(1, bloqueId);
			ps.setString(2, cursoCargaId);
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupo|updateBloque|:"+ex);		 
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}

	
	public boolean deleteReg(Connection conn, String cursoCargaId ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.CARGA_GRUPO "+ 
				"WHERE CURSO_CARGA_ID = ? ");
			ps.setString(1, cursoCargaId);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupo|deleteReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	
	public CargaGrupo mapeaRegId( Connection conn, String cursoCargaId ) throws SQLException{
		CargaGrupo grupo = new CargaGrupo();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement("SELECT"+
				" CURSO_CARGA_ID,"+
				" CARGA_ID,"+
				" TO_CHAR(BLOQUE_ID,'99') AS BLOQUE_ID,"+
				" CARRERA_ID,"+
				" COALESCE(CODIGO_PERSONAL,' ') AS CODIGO_PERSONAL,"+
				" COALESCE(GRUPO,' ') AS GRUPO,"+
				" TO_CHAR(MODALIDAD_ID,'99') AS MODALIDAD_ID,"+
				" TO_CHAR(F_INICIO,'DD/MM/YYYY') AS F_INICIO,"+
				" TO_CHAR(F_FINAL,'DD/MM/YYYY') AS F_FINAL,"+
				" TO_CHAR(F_ENTREGA,'DD/MM/YYYY') AS F_ENTREGA,"+
				" RESTRICCION,"+
				" ESTADO,"+
				" HORARIO,"+
				" TO_CHAR(F_EVALUACION,'DD/MM/YYYY') AS F_EVALUACION," +
				" VALEUCAS, NUM_ALUM, SEMANAS, OPTATIVA, COMENTARIO, CODIGO_OTRO, PRECIO"+
				" FROM ENOC.CARGA_GRUPO WHERE CURSO_CARGA_ID = ? "); 
			ps.setString(1, cursoCargaId);
			
			rs = ps.executeQuery();
			if (rs.next()){
				grupo.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupo|mapeaRegId*|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return grupo;
	}
	
	public boolean existeReg(Connection conn, String cursoCargaId) throws SQLException{
		boolean 			ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.CARGA_GRUPO "+ 
				"WHERE CURSO_CARGA_ID = ?");
			ps.setString(1, cursoCargaId);
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupo|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public boolean existeCargaCarrera(Connection conn, String cargaId, String carreraId) throws SQLException{
		boolean 			ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.CARGA_GRUPO "+ 
				"WHERE CARGA_ID = '"+cargaId+"' AND CARRERA_ID = '"+carreraId+"' ");
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupo|existeCargaCarrera|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public void chkFechas(Connection conn, String cursoCargaId ) throws SQLException{
		PreparedStatement ps	= null;
		PreparedStatement ps2	= null;
		ResultSet 		rs		= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.CARGA_GRUPO "+ 
				"WHERE CURSO_CARGA_ID = ? "+
				"AND F_EVALUACION > F_FINAL");
			ps.setString(1, cursoCargaId);			
			rs = ps.executeQuery();
			if (rs.next()){
				ps2 = conn.prepareStatement("UPDATE ENOC.CARGA_GRUPO SET F_EVALUACION = F_FINAL WHERE CURSO_CARGA_ID = ?");
				ps2.setString(1, cursoCargaId);
				ps2.executeUpdate();				
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupo|chkFechas|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }			
			if (ps2!=null) ps2.close();
		}
	}
	
	public static boolean conAlumnos(Connection conn, String cursoCargaId ) throws SQLException{
		boolean 			ok 	= false;
		ResultSet 			rs	= null;
		PreparedStatement	ps= null;
		
		try{
			ps = conn.prepareStatement("SELECT CURSO_CARGA_ID "+ 
				"FROM ENOC.KRDX_CURSO_ACT "+ 
				"WHERE CURSO_CARGA_ID = ? "+	
				"AND TIPOCAL_ID IN ('I','1','2','3','4','5','6')");
			ps.setString(1, cursoCargaId);
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupo|conAlumnos|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public static String getCodigoPersonal(Connection conn, String cursoCargaId ) throws SQLException{
		PreparedStatement	ps 	= null;
		ResultSet 			rs	= null;
		String codigoPersonal 	= "0000000";
		
		
		try{
			ps = conn.prepareStatement("SELECT CODIGO_PERSONAL "+ 
				"FROM ENOC.CARGA_GRUPO "+ 
				"WHERE CURSO_CARGA_ID = ?");
			ps.setString(1, cursoCargaId);
			
			rs = ps.executeQuery();
			if (rs.next())
				codigoPersonal = rs.getString("CODIGO_PERSONAL");			
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupo|getCodigoPersonal|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return codigoPersonal;
	}
	
	public static String getEstado(Connection conn, String cursoCargaId ) throws SQLException{
		PreparedStatement	ps 	= null;
		ResultSet 			rs	= null;
		String estado 	= "0";		
		
		try{
			ps = conn.prepareStatement("SELECT COALESCE(ESTADO,'X') AS ESTADO "+ 
				"FROM ENOC.CARGA_GRUPO "+ 
				"WHERE CURSO_CARGA_ID = ?");
			ps.setString(1, cursoCargaId);
			
			rs = ps.executeQuery();
			if (rs.next())
				estado = rs.getString("ESTADO");		
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupo|getEstado|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return estado;
	}
	
	public static String getCarreraId(Connection conn, String cursoCargaId ) throws SQLException{
		PreparedStatement	ps 	= null;
		ResultSet 			rs	= null;
		String carrera 	= "0";		
		
		try{
			ps = conn.prepareStatement("SELECT COALESCE(CARRERA_ID,'X') AS CARRERA"+
				" FROM ENOC.CARGA_GRUPO"+ 
				" WHERE CURSO_CARGA_ID = ?");
			ps.setString(1, cursoCargaId);
			
			rs = ps.executeQuery();
			if (rs.next())
				carrera = rs.getString("CARRERA");		
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupo|getCarreraId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return carrera;
	}
	
	public static String getCargaId(Connection conn, String cursoCargaId ) throws SQLException{
		PreparedStatement	ps 	= null;
		ResultSet 			rs	= null;
		String cargaId 		= "0";		
		
		try{
			ps = conn.prepareStatement("SELECT SUBSTR(CURSO_CARGA_ID, 1,6) AS CARGA_ID " +
					" FROM ENOC.CARGA_GRUPO " + 
					" WHERE CURSO_CARGA_ID = ?");
			ps.setString(1, cursoCargaId);
			
			rs = ps.executeQuery();
			if (rs.next())
				cargaId = rs.getString("CARGA_ID");		
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupo|getCarreraId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return cargaId;
	}
	
	public static String getFechaInicio(Connection conn, String cursoCargaId ) throws SQLException{
		PreparedStatement	ps 	= null;
		ResultSet 			rs	= null;
		String fecha 			= "";		
		
		try{
			ps = conn.prepareStatement("SELECT TO_CHAR(F_INICIO,'DD/MM/YYYY') AS F_INICIO "+ 
				"FROM ENOC.CARGA_GRUPO "+ 
				"WHERE CURSO_CARGA_ID = ?");
			ps.setString(1, cursoCargaId);
			
			rs = ps.executeQuery();
			if (rs.next())
				fecha	= rs.getString("F_INICIO");
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupo|getFechaInicio|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return fecha;
	}
	
	public static String getFechaFinal(Connection conn, String cursoCargaId ) throws SQLException{
		PreparedStatement	ps 	= null;
		ResultSet 			rs	= null;
		String fecha 			= "";		
		
		try{
			ps = conn.prepareStatement("SELECT TO_CHAR(F_FINAL,'DD/MM/YYYY') AS F_FINAL "+
				"FROM ENOC.CARGA_GRUPO "+ 
				"WHERE CURSO_CARGA_ID = ?");
			ps.setString(1, cursoCargaId);
			
			rs = ps.executeQuery();
			if (rs.next())
				fecha	= rs.getString("F_FINAL");		
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupo|getFechaFinal|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return fecha;
	}
	
	
	/**
	 * Esta funcion consulta si la materia tiene cambios de nota debido a un "acta de correccion" ..    	
	 **/
	public static boolean getTieneCorreccionNotas(Connection conn, String cursoCargaId ) throws SQLException{
		PreparedStatement	ps 	= null;
		ResultSet 			rs	= null;
		boolean correccion 		= false;		
		
		try{
			ps = conn.prepareStatement("SELECT CURSO_CARGA_ID FROM ENOC.KRDX_CURSO_CAL WHERE CURSO_CARGA_ID = ? AND TIPO = 'C' ");
			ps.setString(1, cursoCargaId);
			
			rs = ps.executeQuery();
			if (rs.next())
				correccion = true;		
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupo|getCarreraId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return correccion;
	}
	
	/**
	 * Esta funcion consulta si la materia tiene cambios de nota debido a un "acta de correccion" ..    	
	 **/
	public static boolean getTieneCargaDocente(Connection conn, String codigoPersonal, String cargaId ) throws SQLException{
		PreparedStatement	ps 	= null;
		ResultSet 			rs	= null;
		boolean docencia 		= false;
		
		try{
			ps = conn.prepareStatement("SELECT VALEUCAS FROM ENOC.CARGA_GRUPO" + 
					" WHERE CODIGO_PERSONAL = ?" +
					" AND CARGA_ID = ?" +
					" AND VALEUCAS='S'");
			ps.setString(1, codigoPersonal );
			ps.setString(2, cargaId );
			
			rs = ps.executeQuery();
			if (rs.next())
				docencia = true;
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupo|getCarreraId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return docencia;
	}
	
	public static String getOptativa(Connection conn, String cursoCargaId ) throws SQLException{
		PreparedStatement	ps 	= null;
		ResultSet 			rs	= null;
		String optativa 		= "";		
		
		try{
			ps = conn.prepareStatement("SELECT OPTATIVA FROM ENOC.CARGA_GRUPO WHERE CURSO_CARGA_ID = ?"); 
			ps.setString(1, cursoCargaId);
			
			rs = ps.executeQuery();
			if (rs.next())
				optativa	= rs.getString("OPTATIVA");
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupo|getOptativa|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return optativa;
	}
	
	public static String getModalidad(Connection conn, String cursoCargaId ) throws SQLException{
		PreparedStatement	ps 	= null;
		ResultSet 			rs	= null;
		String modalidad 		= "0";		
		
		try{
			ps = conn.prepareStatement("SELECT MODALIDAD_ID FROM ENOC.CARGA_GRUPO  WHERE CURSO_CARGA_ID = ?");
			ps.setString(1, cursoCargaId);
			
			rs = ps.executeQuery();
			if (rs.next())
				modalidad = rs.getString("MODALIDAD_ID");		
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupo|getModalidad|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return modalidad;
	}
	
	public static String getFacultadGrupo(Connection conn, String cursoCargaId ) throws SQLException{
		PreparedStatement	ps 	= null;
		ResultSet 			rs	= null;
		String fac 				= "000";		
		
		try{
			ps = conn.prepareStatement("SELECT ENOC.FACULTAD(CARRERA_ID) AS FACID FROM ENOC.CARGA_GRUPO WHERE CURSO_CARGA_ID = ?");
			ps.setString(1, cursoCargaId);
			
			rs = ps.executeQuery();
			if (rs.next())
				fac = rs.getString("FACID");		
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupo|getFacultadGrupo|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return fac;
	}
	
	public static int verificaHorario(Connection conn, String cursoCargaId) throws SQLException{
		PreparedStatement	ps 	= null;
		ResultSet 			rs	= null;
		int TH 					= Integer.parseInt(aca.plan.CursoUtil.getFS(conn, aca.carga.CargaGrupoCursoUtil.cursoIdOrigen(conn, cursoCargaId)));			
		int cont 				= 0;
		
		try{
			ps = conn.prepareStatement("SELECT HORARIO FROM ENOC.CARGA_GRUPO WHERE CURSO_CARGA_ID = '"+cursoCargaId+"'");			
			rs = ps.executeQuery();
			
			if(rs.next()){
				String horario = rs.getString("HORARIO");
				if(horario.contains("1")){
					if(TH==1) cont++;
					for(int i=0; i<horario.length(); i++){
						if(cont==TH) break;
						if(horario.charAt(i)=='1') cont++;
					}
				}
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupo|verificaHorario|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return TH-cont;
	}	
	
	public static boolean tieneHorarioTablaCondiciones(Connection conn, String condiciones) throws SQLException{
		PreparedStatement	ps 	= null;
		ResultSet 			rs	= null;
		boolean tieneHorario = false;
		try{
			ps = conn.prepareStatement("SELECT CURSO_CARGA_ID, HORARIO FROM "+condiciones);
			rs = ps.executeQuery();
			while(rs.next()){
				String cursoCargaId = rs.getString("CURSO_CARGA_ID");
				int TH = Integer.parseInt(aca.plan.CursoUtil.getFS(conn, aca.carga.CargaGrupoCursoUtil.cursoIdOrigen(conn, cursoCargaId)));			
				int cont = 0;
				String horario = rs.getString("HORARIO");
				if(horario.contains("1")){
					if(TH==1) cont++;
					for(int i=0; i<horario.length(); i++){
						if(cont==TH) break;
						if(horario.charAt(i)=='1') cont++;
					}
				}
				if(TH-cont<=0){
					tieneHorario = true;
					break;
				}
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupo|tieneHorarioTablaCondiciones|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return tieneHorario;
	}
	
	public static HashMap<String,String> getMapAll(Connection conn, String orden ) throws SQLException{
		
		HashMap<String,String> mapCarga = new HashMap<String,String>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando				= "";		
		
		try{
			comando = " SELECT CARGA_ID, COUNT(CURSO_CARGA_ID) AS NUMERO " +
					" FROM ENOC.CARGA_GRUPO  " +
					" WHERE CURSO_CARGA_ID NOT IN (SELECT CURSO_CARGA_ID FROM ENOC.KRDX_CURSO_ACT)" +
					" GROUP BY CARGA_ID "+ orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){							
				mapCarga.put(rs.getString("CARGA_ID"), rs.getString("NUMERO"));
			}

		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupo|getMapAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return mapCarga;
	}
	
	public static String getNumCursosMaestro(Connection conn, String codigoPersonal) throws SQLException{
		PreparedStatement	ps 	= null;
		ResultSet 			rs	= null;
		String numCursos 		= "0";
		try{
			ps = conn.prepareStatement("SELECT COALESCE(COUNT(CODIGO_PERSONAL),0) AS NUMCURSOS FROM ENOC.CARGA_GRUPO WHERE CODIGO_PERSONAL = ?");
			ps.setString(1, codigoPersonal);			
			rs = ps.executeQuery();
			if(rs.next()){
				numCursos = rs.getString("NUMCURSOS");
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupo|getNumCursosMaestro|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return numCursos;
	}	
	
	public static String getNumCursosMaestro(Connection conn, String codigoPersonal, String cargaId) throws SQLException{
		PreparedStatement	ps 	= null;
		ResultSet 			rs	= null;
		String numCursos 		= "0";
		try{
			ps = conn.prepareStatement("SELECT COALESCE(COUNT(CODIGO_PERSONAL),0) AS NUMCURSOS FROM ENOC.CARGA_GRUPO WHERE CODIGO_PERSONAL = ? AND CARGA_ID = ?");
			ps.setString(1, codigoPersonal);			
			ps.setString(2, cargaId);
			rs = ps.executeQuery();
			if(rs.next()){
				numCursos = rs.getString("NUMCURSOS");
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupo|getNumCursosMaestro|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return numCursos;
	}
		
	public ArrayList<CargaGrupo> getListAll(Connection conn, String orden ) throws SQLException{
		
		ArrayList<CargaGrupo> lisGrupo	= new ArrayList<CargaGrupo>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
		
		try{
			comando = "SELECT CURSO_CARGA_ID, CARGA_ID, BLOQUE_ID, CARRERA_ID,"+
				" CODIGO_PERSONAL, GRUPO, MODALIDAD_ID, F_INICIO, F_FINAL, F_ENTREGA,"+
				" RESTRICCION, ESTADO, HORARIO, TO_CHAR(F_EVALUACION,'DD/MM/YYYY') AS F_EVALUACION,"+
				" VALEUCAS, NUM_ALUM, SEMANAS, OPTATIVA, COMENTARIO, CODIGO_OTRO, PRECIO"+
				" FROM ENOC.CARGA_GRUPO "+ orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				CargaGrupo grupo = new CargaGrupo();
				grupo.mapeaReg(rs);
				lisGrupo.add(grupo);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisGrupo;
	}
	
	public ArrayList<String> getListBloques(Connection conn, String cargaId, String orden ) throws SQLException{
			
			ArrayList<String> lisBloque	= new ArrayList<String>();
			Statement st 			= conn.createStatement();
			ResultSet rs 			= null;
			String comando	= "";
			
			try{
				comando = "SELECT DISTINCT(BLOQUE_ID) AS BLOQUE_ID FROM ENOC.CARGA_GRUPO WHERE CARGA_ID = '"+cargaId+"' "+ orden; 
				
				rs = st.executeQuery(comando);
				while (rs.next()){
					
					lisBloque.add(rs.getString("BLOQUE_ID"));
				}
				
			}catch(Exception ex){
				System.out.println("Error - aca.carga.CargaGrupoUtil|getListBloques|:"+ex);
			}finally{
				try { rs.close(); } catch (Exception ignore) { }
				try { st.close(); } catch (Exception ignore) { }
			}
			
			return lisBloque;
		}
	
	public ArrayList<CatFacultad> getFacultades(Connection conn, String cargaId) throws SQLException{
		ArrayList<CatFacultad> listor	= new ArrayList<CatFacultad>();		
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		CatFacultadUtil facU = new aca.catalogo.CatFacultadUtil();
		String comando	= "";
		
		try{
			comando = "select ENOC.FACULTAD(carrera_id) from ENOC.carga_grupo where carga_id='"+cargaId+"' group by ENOC.FACULTAD(carrera_id)";			 
			rs = st.executeQuery(comando);
			while (rs.next()){
				CatFacultad fac = new aca.catalogo.CatFacultad();				
				fac = facU.mapeaRegId(conn,rs.getString(1),"1");
				listor.add(fac);
				fac = null;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoUtil|getFacultades|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return listor;
	}
	
	public ArrayList<CatCarrera> getCarreras(Connection conn, String cargaId, String facultadId) throws SQLException{
		ArrayList<CatCarrera> listor	= new ArrayList<CatCarrera>();		
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
		aca.catalogo.CatCarreraUtil carreraU = new aca.catalogo.CatCarreraUtil();
		try{
			comando = "select carrera_id from ENOC.carga_grupo where carga_id='"+cargaId+"' and ENOC.FACULTAD(carrera_id) = '"+facultadId+"' group by carrera_id";			 
			rs = st.executeQuery(comando);
			while (rs.next()){
				aca.catalogo.CatCarrera carrera = new aca.catalogo.CatCarrera();
				carrera = carreraU.mapeaRegId(conn,facultadId,rs.getString(1));
				listor.add(carrera);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoUtil|getCarreras|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return listor;
	}
	
	public long getTotalCarga(Connection conn, String cargaId) throws SQLException{
		long total = 0;
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
		
		try{
			comando = "SELECT count(*) FROM ENOC.CARGA_GRUPO WHERE CARGA_ID = '"+cargaId+"'"; 
			rs = st.executeQuery(comando);
			if (rs.next()){
				total = rs.getLong(1);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoUtil|getTotalCarga|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return total;
	}
	
	public long getTotalCarga(Connection conn, String cargaId,String facultadId) throws SQLException{
		long total = 0;
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
		
		try{
			comando = "SELECT count(*) FROM ENOC.CARGA_GRUPO WHERE CARGA_ID = '"+cargaId+"' and ENOC.FACULTAD(carrera_id) = '"+facultadId+"'";			 
			rs = st.executeQuery(comando);
			if (rs.next()){
				total = rs.getLong(1);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoUtil|getTotalCarga|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return total;
	}
	
	public long getTotalCargaCarrera(Connection conn, String cargaId,String carreraId) throws SQLException{
		long total = 0;
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
		
		try{
			comando = "SELECT count(*) FROM ENOC.CARGA_GRUPO WHERE CARGA_ID = '"+cargaId+"' and carrera_id = '"+carreraId+"'";			 
			rs = st.executeQuery(comando);
			if (rs.next()){
				total = rs.getLong(1);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoUtil|getTotalCargaCarrera|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return total;
	}
	
	public long getTotalCargaCarreraMaestro(Connection conn, String cargaId,String carreraId,String codigoPersonal) throws SQLException{
		long total = 0;
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
		
		try{
			comando = "SELECT count(*) FROM ENOC.CARGA_GRUPO WHERE CARGA_ID = '"+cargaId+"' and carrera_id = '"+carreraId+"' and codigo_personal='"+codigoPersonal+"'";			 
			rs = st.executeQuery(comando);
			if (rs.next()){
				total = rs.getLong(1);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoUtil|getTotalCargaCarreraMaestro|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return total;
	}
	
	public long getCargaEstado(Connection conn, String cargaId,String estado) throws SQLException{
		long total = 0;
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
		
		try{
			comando = "SELECT count(*) FROM ENOC.CARGA_GRUPO WHERE CARGA_ID = '"+cargaId+"' and estado='"+estado+"'";			 
			rs = st.executeQuery(comando);
			if (rs.next()){
				total = rs.getLong(1);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoUtil|getCargaEstado|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return total;
	}
	
	public long getCargaEstado(Connection conn, String cargaId,String estado,String facultadId) throws SQLException{
		long total = 0;
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
		
		try{
			comando = "SELECT count(*) FROM ENOC.CARGA_GRUPO WHERE CARGA_ID = '"+cargaId+"' and estado='"+estado+"' and ENOC.FACULTAD(carrera_id) = '"+facultadId+"'";			 
			rs = st.executeQuery(comando);
			if (rs.next()){
				total = rs.getLong(1);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoUtil|getCargaEstado|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return total;
	}
	
	public long getCargaEstadoCarrera(Connection conn, String cargaId,String estado,String carreraId) throws SQLException{
		long total = 0;
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
		
		try{
			comando = "SELECT count(*) FROM ENOC.CARGA_GRUPO WHERE CARGA_ID = '"+cargaId+"' and estado='"+estado+"' and carrera_id = '"+carreraId+"'";			 
			rs = st.executeQuery(comando);
			if (rs.next()){
				total = rs.getLong(1);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoUtil|getCargaEstadoCarrera|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return total;
	}
	
	public long getCargaEstadoCarrera(Connection conn, String cargaId,String estado,String carreraId,String codigoPersonal) throws SQLException{
		long total = 0;
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
		
		try{
			comando = "SELECT count(*) FROM ENOC.CARGA_GRUPO WHERE CARGA_ID = '"+cargaId+"' and estado='"+estado+"' and carrera_id = '"+carreraId+"' and codigo_personal='"+codigoPersonal+"'";			 
			rs = st.executeQuery(comando);
			if (rs.next()){
				total = rs.getLong(1);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoUtil|getCargaEstadoCarrera|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return total;
	}
	
	public ArrayList<CargaGrupo> getListaCarga(Connection conn, String cargaId, String orden ) throws SQLException{
		
		ArrayList<CargaGrupo> lisGrupo	= new ArrayList<CargaGrupo>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
		
		try{
			comando = "SELECT CURSO_CARGA_ID, CARGA_ID, BLOQUE_ID, CARRERA_ID,"+
				" CODIGO_PERSONAL, GRUPO, MODALIDAD_ID, F_INICIO, F_FINAL, F_ENTREGA,"+
				" RESTRICCION, ESTADO, HORARIO, TO_CHAR(F_EVALUACION,'DD/MM/YYYY') AS F_EVALUACION," +
				" VALEUCAS, NUM_ALUM, SEMANAS, OPTATIVA, COMENTARIO, CODIGO_OTRO, PRECIO"+
				" FROM ENOC.CARGA_GRUPO "+ 
				" WHERE CARGA_ID = '"+cargaId+"' "+ orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				CargaGrupo grupo = new CargaGrupo();
				grupo.mapeaReg(rs);
				lisGrupo.add(grupo);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoUtil|getListaCarga|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisGrupo;
	}
	
	public ArrayList<CargaGrupo> getListaCarrera(Connection conn, String cargaId, String carreraId, String orden ) throws SQLException{
		
		ArrayList<CargaGrupo> lisGrupo	= new ArrayList<CargaGrupo>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
		
		try{
			comando = "SELECT CURSO_CARGA_ID, CARGA_ID, BLOQUE_ID, CARRERA_ID,"+
				" CODIGO_PERSONAL, GRUPO, MODALIDAD_ID, F_INICIO, F_FINAL, F_ENTREGA,"+
				" RESTRICCION, ESTADO, HORARIO, TO_CHAR(F_EVALUACION,'DD/MM/YYYY') AS F_EVALUACION,"+
				" VALEUCAS, NUM_ALUM, SEMANAS, OPTATIVA, COMENTARIO, CODIGO_OTRO, PRECIO"+
				" FROM ENOC.CARGA_GRUPO"+ 
				" WHERE CARGA_ID = '"+cargaId+"'"+
				" AND CARRERA_ID = '"+carreraId+"' "+ orden;			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				CargaGrupo grupo = new CargaGrupo();
				grupo.mapeaReg(rs);
				lisGrupo.add(grupo);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoUtil|getListaCarrera|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisGrupo;
	}
	
	// 
	public ArrayList<String> getListGruposCarga(Connection conn, String cargaId, String orden ) throws SQLException{
		
		ArrayList<String> lisGrupo		= new ArrayList<String>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = "SELECT"+ 
				" ENOC.FACULTAD(CARRERA_ID) AS FACULTAD,"+
				" CARRERA_ID,"+
				" CURSO_CARGA_ID,"+
				" ESTADO,"+
				" MODALIDAD_ID"+
				" FROM ENOC.CARGA_GRUPO"+ 
				" WHERE CARGA_ID = '"+cargaId+"' "+ orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				lisGrupo.add(rs.getString("FACULTAD"));
				lisGrupo.add(rs.getString("CARRERA_ID"));
				lisGrupo.add(rs.getString("CURSO_CARGA_ID"));
				lisGrupo.add(rs.getString("ESTADO"));
				lisGrupo.add(rs.getString("MODALIDAD_ID"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoUtil|getListGruposCarga|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisGrupo;
	}
	
	public String getNumGrupos(Connection conn, String cargaId, String carreraId ) throws SQLException{
				
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando		= "";
		String total			= "0";
		
		try{
			comando = "SELECT COUNT(CURSO_carga_Id) AS TOTAL FROM ENOC.CARGA_GRUPO "+ 
				"WHERE carga_Id = '"+cargaId+"' "+
				"AND CARRERA_ID = '"+carreraId+"'";

			rs = st.executeQuery(comando);
			if (rs.next()){	
				total = rs.getString("TOTAL");
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoUtil|getNumGrupos|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return total;
	}
	
	public String getNumMaestros(Connection Conn, String cargaId, String carreraId ) throws SQLException{
		
		Statement st 			= Conn.createStatement();
		ResultSet rs 			= null;
		String comando		= "";
		String total			= "0";
		
		try{
			comando = "SELECT COUNT(CODIGO_PERSONAL) AS TOTAL FROM ENOC.CARGA_GRUPO "+ 
				"WHERE CARGA_ID = '"+cargaId+"' "+
				"AND CARRERA_ID = '"+carreraId+"' "+
				"AND COALESCE(TRIM(CODIGO_PERSONAL), 'X') != 'X' ";
			
			rs = st.executeQuery(comando);
			if (rs.next()){	
				total = rs.getString("TOTAL");
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoUtil|getNumMaestros|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return total;
	}
	
	public HashMap<String, int[]> getNumGrupoProfesorHorarios(Connection Conn, String cargaId) throws SQLException{
		
		Statement st 				= Conn.createStatement();
		ResultSet rs 				= null;
		ResultSet rs2 				= null;
		ResultSet rs3 				= null;
		HashMap<String, int[]> arr 	= new HashMap<String, int[]>();
		String carreraId 			= "";
		try{
			
			String comando = "SELECT COUNT(CURSO_CARGA_ID) AS GRUPOS, CARRERA_ID" +
							" FROM ENOC.CARGA_GRUPO WHERE CARGA_ID = '"+cargaId+"'" +
							" GROUP BY CARRERA_ID ORDER BY CARRERA_ID";
			rs = st.executeQuery(comando);
			while(rs.next()){
				int[] arreglo = {0,0,0};				
				carreraId = rs.getString("CARRERA_ID");
				arreglo[0] = Integer.parseInt(rs.getString("GRUPOS"));
				arr.put(carreraId, arreglo);
			}
			
			comando = "SELECT COALESCE(COUNT(CODIGO_PERSONAL),0) AS MAESTROS, CARRERA_ID FROM ENOC.CARGA_GRUPO" +
					" WHERE CARGA_ID = '"+cargaId+"'" +
					" AND COALESCE(TRIM(CODIGO_PERSONAL), 'X') != 'X'" +
					" GROUP BY CARRERA_ID ORDER BY CARRERA_ID";
			rs2 = st.executeQuery(comando);
			while(rs2.next()){
				carreraId 	= rs2.getString("CARRERA_ID");
				arr.get(carreraId)[1] = Integer.parseInt(rs2.getString("MAESTROS"));
			}
			
			comando = "SELECT HORARIO, CARRERA_ID, (SELECT COALESCE(SUM(HT+HP),0) FROM ENOC.MAPA_CURSO" +
					" WHERE CURSO_ID = (SELECT CURSO_ID FROM ENOC.CARGA_GRUPO_CURSO" +
					" WHERE CURSO_CARGA_ID = CARGA_GRUPO.CURSO_CARGA_ID AND ORIGEN = 'O'))AS HORASTOTALES" +
					" FROM ENOC.CARGA_GRUPO WHERE CARGA_ID = '"+cargaId+"' ORDER BY CARRERA_ID";
			rs3 = st.executeQuery(comando);
			while(rs3.next()){
				carreraId 			= rs3.getString("CARRERA_ID");
				int horasT 			= Integer.parseInt(rs3.getString("HORASTOTALES"));
				String horario 		= rs3.getString("HORARIO");
				
				int cont = 0;
				if(horario.contains("1")){
					if(horasT==1) cont++;
					for(int i=0; i<horario.length(); i++){
						if(cont==horasT) break;
						if(horario.charAt(i)=='1') cont++;
					}
				}
				if(horasT-cont == 0) arr.get(carreraId)[2]++;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoUtil|getNumGrupoProfesorHorarios|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			if (rs2!=null) rs2.close();
			if (rs3!=null) rs3.close();
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return arr;
	}
	
	public HashMap<String, CargaGrupo> mapCargaGrupo(Connection conn, String cargaId) throws SQLException{
		HashMap<String, CargaGrupo> mapaCargaGrupo = new HashMap<String, CargaGrupo>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
	
		try{
			comando = " SELECT CURSO_CARGA_ID, CARGA_ID, BLOQUE_ID, CARRERA_ID,"
					+ " CODIGO_PERSONAL, GRUPO, MODALIDAD_ID, F_INICIO, F_FINAL, F_ENTREGA,"
					+ " RESTRICCION, ESTADO, HORARIO, TO_CHAR(F_EVALUACION,'DD/MM/YYYY') AS F_EVALUACION,"
					+ " VALEUCAS, NUM_ALUM, SEMANAS, OPTATIVA, COMENTARIO, CODIGO_OTRO, PRECIO"
					+ " FROM ENOC.CARGA_GRUPO "
					+ " WHERE CARGA_ID = '"+cargaId+"' "; 
		
			rs = st.executeQuery(comando);
			while (rs.next()){
				CargaGrupo cargaGrupo = new CargaGrupo();
				cargaGrupo.mapeaReg(rs);
				mapaCargaGrupo.put(cargaGrupo.getCursoCargaId(), cargaGrupo);
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.plan.GrupoUtil|mapCargaGrupo|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return mapaCargaGrupo;
	}
	
	public HashMap<String, CargaGrupo> getAllMapaCargaGrupo(Connection conn, String orden) throws SQLException{
		HashMap<String, CargaGrupo> mapaCargaGrupo = new HashMap<String, CargaGrupo>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
	
		try{
			comando = "SELECT CURSO_CARGA_ID, CARGA_ID, BLOQUE_ID, CARRERA_ID,"+
					" CODIGO_PERSONAL, GRUPO, MODALIDAD_ID, F_INICIO, F_FINAL, F_ENTREGA,"+
					" RESTRICCION, ESTADO, HORARIO, TO_CHAR(F_EVALUACION,'DD/MM/YYYY') AS F_EVALUACION,"+
					" VALEUCAS, NUM_ALUM, SEMANAS, OPTATIVA, COMENTARIO, CODIGO_OTRO, PRECIO"+
					" FROM ENOC.CARGA_GRUPO "+ orden; 
		
			rs = st.executeQuery(comando);
			while (rs.next()){
				CargaGrupo cargaGrupo = new CargaGrupo();
				cargaGrupo.mapeaReg(rs);
				mapaCargaGrupo.put(cargaGrupo.getCursoCargaId(), cargaGrupo);
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.plan.GrupoUtil|getAllMapaCargaGrupo|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return mapaCargaGrupo;
	}
	
	/* Map que cuenta la cantidad de materias de una carga academica */
	public static HashMap<String, String> getTotGpo(Connection conn, String cargas) throws SQLException{
		HashMap<String, String> mapaGrupo = new HashMap<String, String>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando			= "";
		String valor 			= "0";
		
		try{
			comando = "SELECT CARGA_ID, COALESCE(COUNT(CURSO_CARGA_ID),0) AS TOTAL" +
					" FROM ENOC.CARGA_GRUPO" +
					" WHERE CARGA_ID IN ("+cargas+")" +
					" GROUP BY CARGA_ID";		
			rs = st.executeQuery(comando);
			while (rs.next()){
				if (rs.getString("TOTAL")==null||rs.getString("TOTAL").equals("")) 
					valor = "0";
				else
					valor = rs.getString("TOTAL");
				
				mapaGrupo.put(rs.getString("CARGA_ID"), valor);
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.plan.GrupoUtil|getTotGpo|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return mapaGrupo;
	}
	
	/* Map que cuenta la cantidad de materias de una facultad en la carga academica */
	public static HashMap<String, String> mapMatPorFacultadEnCarga(Connection conn, String cargaId) throws SQLException{
		HashMap<String, String> mapa = new HashMap<String, String>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando			= "";		
		
		try{
			comando = " SELECT ENOC.FACULTAD(CARRERA_ID) AS FACULTAD, COALESCE(COUNT(CURSO_CARGA_ID),0) AS TOTAL"
					+ " FROM ENOC.CARGA_GRUPO"
					+ " WHERE CARGA_ID = '"+cargaId+"' GROUP BY ENOC.FACULTAD(CARRERA_ID)";
			rs = st.executeQuery(comando);
			while (rs.next()){
				mapa.put(rs.getString("FACULTAD"), rs.getString("TOTAL"));
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.plan.GrupoUtil|mapMatPorFacultadEnCarga|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return mapa;
	}
	
	/* Map que cuenta la cantidad de materias de un maestro en la carga academica */
	public static HashMap<String, String> mapMatPorMaestroEnCarga(Connection conn, String cargaId) throws SQLException{
		HashMap<String, String> mapa = new HashMap<String, String>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando			= "";		
		
		try{
			comando = " SELECT CODIGO_PERSONAL, COALESCE(COUNT(CURSO_CARGA_ID),0) AS TOTAL"
					+ " FROM ENOC.CARGA_GRUPO"
					+ " WHERE CARGA_ID = '"+cargaId+"' GROUP BY CODIGO_PERSONAL";
			rs = st.executeQuery(comando);
			while (rs.next()){
				mapa.put(rs.getString("CODIGO_PERSONAL"), rs.getString("TOTAL"));
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.plan.GrupoUtil|mapMatPorMaestroEnCarga|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return mapa;
	}
	
	/* Map que cuenta la cantidad de materias reprobadas de una facultad en la carga academica */
	public static HashMap<String, String> mapMatRepPorFacultadEnCarga(Connection conn, String cargaId) throws SQLException{
		HashMap<String, String> mapa = new HashMap<String, String>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando			= "";		
		
		try{
			comando = " SELECT ENOC.FACULTAD(CARRERA_ID) AS FACULTAD, COALESCE(COUNT(CURSO_CARGA_ID),0) AS TOTAL"
					+ " FROM ENOC.CARGA_GRUPO"
					+ " WHERE CARGA_ID = '"+cargaId+"'"
					+ " AND CURSO_CARGA_ID IN (SELECT CURSO_CARGA_ID FROM ENOC.ALUMNO_CURSO WHERE TIPOCAL_ID IN('2','4') AND SUBSTR(CURSO_CARGA_ID,1,6) = '"+cargaId+"')"
					+ " GROUP BY ENOC.FACULTAD(CARRERA_ID)";
			rs = st.executeQuery(comando);
			while (rs.next()){
				mapa.put(rs.getString("FACULTAD"), rs.getString("TOTAL"));
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.plan.GrupoUtil|mapMatRepPorFacultadEnCarga|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return mapa;
	}
	
	/* Map que cuenta la cantidad de materias por estado de un maestro en una carga academica */
	public static HashMap<String, String> getTotEdoMaestro(Connection conn, String carga, String codigoMaestro) throws SQLException{
		HashMap<String, String> mapaGrupo = new HashMap<String, String>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando			= "";
		String valor 			= "0";
		
		try{
			comando = "SELECT CARGA_ID, ESTADO, COALESCE(COUNT(ESTADO),0) AS TOTAL" +
					" FROM ENOC.CARGA_GRUPO" +
					" WHERE CARGA_ID = '"+carga+"'"+
					" AND CODIGO_PERSONAL = '"+codigoMaestro+"' " +
					" GROUP BY CARGA_ID, ESTADO";		
			rs = st.executeQuery(comando);
			while (rs.next()){
				if (rs.getString("TOTAL")==null||rs.getString("TOTAL").equals("")) 
					valor = "0";
				else
					valor = rs.getString("TOTAL");
				
				mapaGrupo.put(rs.getString("CARGA_ID")+rs.getString("ESTADO"), valor);
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.plan.GrupoUtil|getTotEdoMaestro|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return mapaGrupo;
	}
	
	/* Cuenta la cantidad de materias por estado carga academica */
	public static HashMap<String, String> getTotGpoEdo(Connection conn, String cargas) throws SQLException{
		HashMap<String, String> mapaGrupo = new HashMap<String, String>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando			= "";
		String llave			= "";
		String valor 			= "0";
		
		try{
			comando = "SELECT CARGA_ID, ESTADO, COALESCE(COUNT(CURSO_CARGA_ID),0) AS TOTAL" +
					" FROM ENOC.CARGA_GRUPO" +
					" WHERE CARGA_ID IN ("+cargas+")" +
					" GROUP BY CARGA_ID, ESTADO";		
			rs = st.executeQuery(comando);
			while (rs.next()){
				llave 	= rs.getString("CARGA_ID")+rs.getString("ESTADO");
				if (rs.getString("TOTAL")==null||rs.getString("TOTAL").equals("")) 
					valor = "0";
				else
					valor = rs.getString("TOTAL");
				mapaGrupo.put(llave, valor);
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoUtil|getTotGpoEdo|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return mapaGrupo;
	}
	
	/* Map que cuenta la cantidad de materias por carrera de una carga academica */
	public static HashMap<String, String> getTotGpoFacEdo(Connection conn, String cargas) throws SQLException{
		HashMap<String, String> mapaGrupo = new HashMap<String, String>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando			= "";
		String valor 			= "0";
		
		try{
			comando = "SELECT CARGA_ID, ENOC.FACULTAD(CARRERA_ID) AS FACULTAD_ID, ESTADO, COALESCE(COUNT(CURSO_CARGA_ID),0) AS TOTAL" +
					" FROM ENOC.CARGA_GRUPO" +
					" WHERE CARGA_ID IN ("+cargas+")" +
					" GROUP BY CARGA_ID, ENOC.FACULTAD(CARRERA_ID), ESTADO";
			rs = st.executeQuery(comando);
			while (rs.next()){
				if (rs.getString("TOTAL")==null||rs.getString("TOTAL").equals("")) 
					valor = "0";
				else
					valor = rs.getString("TOTAL");
				
				mapaGrupo.put(rs.getString("CARGA_ID")+rs.getString("FACULTAD_ID")+rs.getString("ESTADO"), valor);
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.plan.GrupoUtil|getTotGpoFacEdo|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return mapaGrupo;
	}
	
	/* Map que cuenta la cantidad de materias por carrera y estado de una carga academica */
	public static HashMap<String, String> getTotGpoFac(Connection conn, String cargas) throws SQLException{
		HashMap<String, String> mapaGrupo = new HashMap<String, String>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando			= "";
		String valor 			= "0";
		
		try{
			comando = "SELECT CARGA_ID, ENOC.FACULTAD(CARRERA_ID) AS FACULTAD_ID, COALESCE(COUNT(CURSO_CARGA_ID),0) AS TOTAL" +
					" FROM ENOC.CARGA_GRUPO" +
					" WHERE CARGA_ID IN ("+cargas+")" +
					" GROUP BY CARGA_ID, ENOC.FACULTAD(CARRERA_ID)";
			rs = st.executeQuery(comando);
			while (rs.next()){
				if (rs.getString("TOTAL")==null||rs.getString("TOTAL").equals("")) 
					valor = "0";
				else
					valor = rs.getString("TOTAL");
				
				mapaGrupo.put(rs.getString("CARGA_ID")+rs.getString("FACULTAD_ID"), valor);
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.plan.GrupoUtil|getTotGpoFac|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return mapaGrupo;
	}
	
	/* Map que cuenta la cantidad de materias por carrera de una carga academica */
	public static HashMap<String, String> getTotGpoCarr(Connection conn, String cargas) throws SQLException{
		HashMap<String, String> mapaGrupo = new HashMap<String, String>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando			= "";
		String valor 			= "0";
		
		try{
			comando = "SELECT CARGA_ID, CARRERA_ID, COALESCE(COUNT(CURSO_CARGA_ID),0) AS TOTAL" +
					" FROM ENOC.CARGA_GRUPO" +
					" WHERE CARGA_ID IN ("+cargas+")" +
					" GROUP BY CARGA_ID, CARRERA_ID";
			rs = st.executeQuery(comando);
			while (rs.next()){
				if (rs.getString("TOTAL")==null||rs.getString("TOTAL").equals("")) 
					valor = "0";
				else
					valor = rs.getString("TOTAL");
				
				mapaGrupo.put(rs.getString("CARGA_ID")+rs.getString("CARRERA_ID"), valor);
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.plan.GrupoUtil|getTotGpoCarr|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return mapaGrupo;
	}
	
	public static HashMap<String, String> getTotGpoCarrEdo(Connection conn, String cargas) throws SQLException{
		HashMap<String, String> mapaGrupo = new HashMap<String, String>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando			= "";
		String valor 			= "0";
		
		try{
			comando = "SELECT CARGA_ID, CARRERA_ID AS CARRERA_ID, ESTADO, COALESCE(COUNT(CURSO_CARGA_ID),0) AS TOTAL" +
					" FROM ENOC.CARGA_GRUPO" +
					" WHERE CARGA_ID IN ("+cargas+")" +
					" GROUP BY CARGA_ID, CARRERA_ID, ESTADO";
			rs = st.executeQuery(comando);
			while (rs.next()){
				if (rs.getString("TOTAL")==null||rs.getString("TOTAL").equals("")) 
					valor = "0";
				else
					valor = rs.getString("TOTAL");
				
				mapaGrupo.put(rs.getString("CARGA_ID")+rs.getString("CARRERA_ID")+rs.getString("ESTADO"), valor);
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.plan.GrupoUtil|getTotGpoCarrEdo|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return mapaGrupo;
	}

	public static HashMap<String, String> getMapaFacultadIdPorCursoCargaId(Connection conn, String codigoPersonal, String cargaId) throws SQLException{
		HashMap<String, String> mapaFacultadId = new HashMap<String, String>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
	
		try{
			comando = "SELECT CURSO_CARGA_ID, ENOC.FACULTAD(CARRERA_ID) AS FACULTAD_ID FROM ENOC.CARGA_GRUPO " +
					"WHERE CURSO_CARGA_ID IN (SELECT CURSO_CARGA_ID FROM ENOC.ALUMNO_CURSO WHERE CODIGO_PERSONAL='"+codigoPersonal+"' AND CARGA_ID = '"+cargaId+"') ";

			rs = st.executeQuery(comando);
			while (rs.next()){
				mapaFacultadId.put(rs.getString("CURSO_CARGA_ID"), rs.getString("FACULTAD_ID"));
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.plan.GrupoUtil|getMapaFacultadIdPorCursoCargaId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return mapaFacultadId;
	}
	
	public static HashMap<String, String> getMapaFacultadIdPorCursoCargaIdMaestros(Connection conn, String codigoPersonal, String cargaId) throws SQLException{
		HashMap<String, String> mapaFacultadId = new HashMap<String, String>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
	
		try{
			comando = "SELECT CURSO_CARGA_ID, ENOC.FACULTAD(CARRERA_ID) AS FACULTAD_ID FROM ENOC.CARGA_GRUPO " +
					"WHERE CODIGO_PERSONAL='"+codigoPersonal+"' AND CARGA_ID = '"+cargaId+"'";

			rs = st.executeQuery(comando);
			while (rs.next()){
				mapaFacultadId.put(rs.getString("CURSO_CARGA_ID"), rs.getString("FACULTAD_ID"));
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.plan.GrupoUtil|getMapaFacultadIdPorCursoCargaId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return mapaFacultadId;
	}
	
	public String getCarrera(Connection conn, String cursoCargaId ) throws SQLException{
		
			Statement st 			= conn.createStatement();
			ResultSet rs 			= null;
			String comando			= "";
			String carrera			= "";
		
			try{
				comando = "SELECT ENOC.nombre_carrera(carrera_id) as carrera FROM ENOC.CARGA_GRUPO "+ 
					"WHERE CURSO_CARGA_Id = '"+cursoCargaId+"' ";
			
				rs = st.executeQuery(comando);
				if (rs.next()){	
					carrera = rs.getString("carrera");
				}
			
			}catch(Exception ex){
				System.out.println("Error - aca.carga.CargaGrupoUtil|getCarrera|:"+ex);
			}finally{
				try { rs.close(); } catch (Exception ignore) { }
				try { st.close(); } catch (Exception ignore) { }
			}
		
			return carrera;
	}
	
	public String getCursoId(Connection conn, String cursoCargaId ) throws SQLException{
		
				Statement st 			= conn.createStatement();
				ResultSet rs 			= null;
				String comando			= "";
				String curso_id			= "";
		
				try{
					comando = "SELECT CURSO_ID FROM ENOC.CARGA_GRUPO_CURSO "+ 
						"WHERE CURSO_CARGA_Id = '"+cursoCargaId+"' "+
						"AND ORIGEN = 'O'";
			
					rs = st.executeQuery(comando);
					if (rs.next()){	
						curso_id = rs.getString("curso_id");
					}
			
				}catch(Exception ex){
					System.out.println("Error - aca.carga.CargaGrupoUtil|getCursoId|:"+ex);
				}finally{
					try { rs.close(); } catch (Exception ignore) { }
					try { st.close(); } catch (Exception ignore) { }
				}
		
				return curso_id;
	}
	
	public String getFacultad(Connection conn, String cursoCargaId ) throws SQLException{
		
			Statement st 			= conn.createStatement();
			ResultSet rs 			= null;
			String comando			= "";
			String facultad			= "";
		
			try{
				comando = 
						"SELECT ENOC.FACULTAD_NOMBRE(ENOC.FACULTAD(CARRERA_ID)) AS FACULTAD " +						
						"FROM ENOC.CARGA_GRUPO WHERE CURSO_CARGA_Id = '"+cursoCargaId+"' "; 
			
				rs = st.executeQuery(comando);
				if (rs.next()){	
					facultad = rs.getString("FACULTAD");					
				}
			
			}catch(Exception ex){
				System.out.println("Error - aca.carga.CargaGrupoUtil|getFacultad|:"+ex);
			}finally{
				try { rs.close(); } catch (Exception ignore) { }
				try { st.close(); } catch (Exception ignore) { }
			}
		
			return facultad;
	}	
	
	public String getMaestro(Connection conn, String cursoCargaId ) throws SQLException{
		
				Statement st 			= conn.createStatement();
				ResultSet rs 			= null;
				String comando			= "";
				String nombre			= "";
		
				try{
					comando = "SELECT EMP_NOMBRE(codigo_personal) AS NOMBRE FROM ENOC.CARGA_GRUPO "+ 
						"WHERE CURSO_CARGA_Id = '"+cursoCargaId+"' ";
			
					rs = st.executeQuery(comando);
					if (rs.next()){	
						nombre = rs.getString("NOMBRE");
					}
			
				}catch(Exception ex){
					System.out.println("Error - aca.carga.CargaGrupoUtil|getMaestro|:"+ex);
				}finally{
					try { rs.close(); } catch (Exception ignore) { }
					try { st.close(); } catch (Exception ignore) { }
				}
		
				return nombre;
	}
	
	public String getCoordinador(Connection conn, String cursoCargaId ) throws SQLException{
		
			Statement st 			= conn.createStatement();
			ResultSet rs 			= null;
			String comando			= "";
			String carrera			= "";
		
			try{
				comando = "SELECT coordinador_carrera(carrera_id) as carrera FROM ENOC.CARGA_GRUPO "+ 
					"WHERE CURSO_CARGA_Id = '"+cursoCargaId+"' ";
			
				rs = st.executeQuery(comando);
				if (rs.next()){	
					carrera = rs.getString("carrera");
				}
			
			}catch(Exception ex){
				System.out.println("Error - aca.carga.CargaGrupoUtil|getCordinador|:"+ex);
			}finally{
				try { rs.close(); } catch (Exception ignore) { }
				try { st.close(); } catch (Exception ignore) { }
			}
		
			return carrera;
	}
	
	
	/* Map que cuenta la cantidad de materias por tipo de curso en una carga academica */
	public static HashMap<String, String> mapMateriasPorTipo(Connection conn, String carga) throws SQLException{
		HashMap<String, String> mapaGrupo = new HashMap<String, String>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando			= "";
		String valor 			= "0";
		
		try{
			comando = "SELECT ENOC.TIPOCURSO_ID(CURSO_ID) AS TIPO, COALESCE(COUNT(CURSO_CARGA_ID),0) AS TOTAL" +
					" FROM ENOC.CARGA_GRUPO_CURSO WHERE SUBSTR(CURSO_CARGA_ID,1,6) = '"+carga+"'" +
					" AND ORIGEN = 'O' " +
					" GROUP BY ENOC.TIPOCURSO_ID(CURSO_ID)";
			rs = st.executeQuery(comando);
			while (rs.next()){
				if (rs.getString("TOTAL")==null||rs.getString("TOTAL").equals("")) 
					valor = "0";
				else
					valor = rs.getString("TOTAL");
				
				mapaGrupo.put(rs.getString("TIPO"), valor);
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.plan.GrupoUtil|mapMateriasPorTipo|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		//System.out.println("size: "+ mapaGrupo.size() + mapaGrupo.get(tipo.getTipoCursoId()));
		return mapaGrupo;
	}
	
	/* Map que cuenta la cantidad de alumnos por tipo de curso en una carga academica */
	public static HashMap<String, String> mapAlumnosPorTipo(Connection conn, String carga) throws SQLException{
		HashMap<String, String> mapaGrupo = new HashMap<String, String>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando			= "";
		String valor 			= "0";
		
		try{
			comando = "SELECT ENOC.TIPOCURSO_ID(CURSO_ID) AS TIPO, COALESCE(COUNT(DISTINCT(CODIGO_PERSONAL)),0) AS TOTAL" +
					" FROM ENOC.KRDX_CURSO_ACT WHERE SUBSTR(CURSO_CARGA_ID,1,6) = '"+carga+"'" +
					" GROUP BY ENOC.TIPOCURSO_ID(CURSO_ID)";
			rs = st.executeQuery(comando);
			while (rs.next()){
				if (rs.getString("TOTAL")==null||rs.getString("TOTAL").equals("")) 
					valor = "0";
				else
					valor = rs.getString("TOTAL");
				
				mapaGrupo.put(rs.getString("TIPO"), valor);
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.plan.GrupoUtil|mapAlumnosPorTipo|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return mapaGrupo;
	}
	
	/* Map que cuenta la cantidad de alumnos por tipo de curso en una carga academica */
	public ArrayList<String> getListaCargaTopMaestro(Connection conn, String cargaId ) throws SQLException{
		
		ArrayList<String> lisTopMaterias = new ArrayList<String>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
		
		try{
			comando = "SELECT CODIGO_PERSONAL, COUNT(CURSO_CARGA_ID) AS CURSO_CARGA_ID " +
					" FROM ENOC.CARGA_GRUPO " +
					" WHERE CARGA_ID = '"+cargaId+"'" +
					" GROUP BY CODIGO_PERSONAL" +
					" ORDER BY CURSO_CARGA_ID DESC";
			
			rs = st.executeQuery(comando);
			for(int i=0; i<10; i++){
				while (rs.next()){
					lisTopMaterias.add(rs.getString("CODIGO_PERSONAL")+"-"+rs.getString("CURSO_CARGA_ID"));
				}
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoUtil|getListaCargaTopMaestro|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisTopMaterias;
	}	

	/* Map que cuenta la cantidad de alumnos por tipo de curso en una carga academica */
	public ArrayList<String> getListaCargaTopMaestroAlum(Connection conn, String cargaId ) throws SQLException{
		
		ArrayList<String> lisTopMaterias = new ArrayList<String>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
		
		try{
			comando = "SELECT CODIGO_PERSONAL," +
					" SUM((SELECT COUNT(CURSO_CARGA_ID) AS CARGA_ID FROM ENOC.KRDX_CURSO_ACT WHERE CURSO_CARGA_ID = CARGA_GRUPO.CURSO_CARGA_ID)) AS ALUMNOS" +
					" FROM ENOC.CARGA_GRUPO " +
					" WHERE CARGA_ID = '"+cargaId+"'" +
					" GROUP BY CODIGO_PERSONAL" +
					" ORDER BY alumnos DESC";
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				lisTopMaterias.add(rs.getString("CODIGO_PERSONAL")+"-"+rs.getString("ALUMNOS"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoUtil|getListaCargaTopMaestroAlum|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisTopMaterias;
	}
		
	// SELECT TIPOCURSO_ID(CURSO_ID), COUNT(DISTINCT(CODIGO_PERSONAL)) FROM ENOC.KRDX_CURSO_ACT WHERE SUBSTR(CURSO_CARGA_ID,1,6) = '12131A' GROUP BY TIPOCURSO_ID(CURSO_ID);
	
	public static HashMap<String, String> getGruposCarga(Connection conn, String cargaId) throws SQLException{
		HashMap<String, String> mapaGrupo = new HashMap<String, String>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
	
		try{
			comando = " SELECT CARRERA_ID, COALESCE(COUNT(CURSO_CARGA_ID), 0) AS GRUPOS "+
					  " FROM ENOC.CARGA_GRUPO WHERE CARGA_ID = '"+cargaId+"' "+
					  " GROUP BY CARRERA_ID";

			rs = st.executeQuery(comando);
			while (rs.next()){
				mapaGrupo.put(rs.getString("CARRERA_ID"), rs.getString("GRUPOS"));
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.plan.GrupoUtil|getGruposCarga|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return mapaGrupo;
	}
	
	public static HashMap<String, String> getMaestrosCarga(Connection conn, String cargaId) throws SQLException{
		HashMap<String, String> mapaMaestros = new HashMap<String, String>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
	
		try{
			comando = " SELECT CARRERA_ID, COALESCE(COUNT(CODIGO_PERSONAL),0) AS MAESTROS "+ 
					  " FROM ENOC.CARGA_GRUPO "+
				      " WHERE CARGA_ID = '"+cargaId+"' "+
					  " AND COALESCE(TRIM(CODIGO_PERSONAL), 'X') != 'X' "+
					  " GROUP BY CARRERA_ID";

			rs = st.executeQuery(comando);
			while (rs.next()){
				mapaMaestros.put(rs.getString("CARRERA_ID"), rs.getString("MAESTROS"));
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.plan.GrupoUtil|getMaestrosCarga|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return mapaMaestros;
	}
	
	public static HashMap<String, String> getTienenHorariosCarga(Connection conn, String cargaId) throws SQLException{
		HashMap<String, String> mapaHorarios = new HashMap<String, String>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
	
		try{
			comando = " SELECT CARRERA_ID, COALESCE(COUNT(CURSO_CARGA_ID),0) AS HORARIOS"+
					  " FROM ENOC.CARGA_GRUPO CG "+
					  " WHERE CARGA_ID = '"+cargaId+"' "+
					  " AND (SELECT COALESCE(SUM(HT+HP),0) FROM ENOC.MAPA_CURSO WHERE CURSO_ID = ENOC.CURSO_ORIGEN(CG.CURSO_CARGA_ID)) <= "+ 
					  " (SELECT COUNT(*) FROM ENOC.CARGA_GRUPO_HORA WHERE CURSO_CARGA_ID = CG.CURSO_CARGA_ID) "+
					  " GROUP BY CARRERA_ID";

			rs = st.executeQuery(comando);
			while (rs.next()){
				mapaHorarios.put(rs.getString("CARRERA_ID"), rs.getString("HORARIOS"));
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.plan.GrupoUtil|getTienenHorariosCarga|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return mapaHorarios;
	}
	
	public static HashMap<String, String> getTienenHorariosMaestro(Connection conn, String cargaId, String codigoPersonal) throws SQLException{
		HashMap<String, String> mapaHorarios = new HashMap<String, String>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
	
		try{
			comando = " SELECT CARRERA_ID, COALESCE(COUNT(CURSO_CARGA_ID),0) AS HORARIOS"+
					  " FROM ENOC.CARGA_GRUPO CG "+
					  " WHERE CARGA_ID = '"+cargaId+"' "+
					  " AND (SELECT COALESCE(SUM(HT+HP),0) FROM ENOC.MAPA_CURSO WHERE CURSO_ID = CURSO_ORIGEN(CG.CURSO_CARGA_ID)) <= "+ 
					  " (SELECT COUNT(*) FROM ENOC.CARGA_GRUPO_HORA WHERE CURSO_CARGA_ID = CG.CURSO_CARGA_ID) AND CODIGO_PERSONAL = '"+codigoPersonal+"' "+
					  " GROUP BY CARRERA_ID";

			rs = st.executeQuery(comando);
			while (rs.next()){
				mapaHorarios.put(rs.getString("CARRERA_ID"), rs.getString("HORARIOS"));
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.plan.GrupoUtil|getTienenHorariosMaestro|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return mapaHorarios;
	}
	
	public static ArrayList<String> getListMaestros(Connection conn, String cargaId, String planId) throws SQLException{
		
		ArrayList<String> lisTopMaterias = new ArrayList<String>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
		
		try{
			comando = "SELECT "
					+ " 	CURSO_ID, (SELECT CODIGO_PERSONAL FROM ENOC.CARGA_GRUPO WHERE CURSO_CARGA_ID = ENOC.CARGA_GRUPO_CURSO.CURSO_CARGA_ID) AS MAESTRO, ORIGEN"
					+ " FROM ENOC.CARGA_GRUPO_CURSO "
					+ " WHERE SUBSTR(CURSO_CARGA_ID,1,6) = '"+cargaId+"'"
					+ " AND CURSO_ID LIKE '"+planId+"%' ORDER BY CURSO_ID";
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				lisTopMaterias.add(rs.getString("CURSO_ID")+"@@"+rs.getString("MAESTRO")+"@@"+rs.getString("ORIGEN"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoUtil|getListMaestros|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisTopMaterias;
	}
	
	public static HashMap<String, CargaGrupo> mapaGruposDiferidos(Connection conn ) throws SQLException{
		HashMap<String, CargaGrupo> mapa = new HashMap<String, CargaGrupo>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
	
		try{
			comando = "SELECT CURSO_CARGA_ID, CARGA_ID, BLOQUE_ID, CARRERA_ID,"+
				" CODIGO_PERSONAL, GRUPO, MODALIDAD_ID, F_INICIO, F_FINAL, F_ENTREGA,"+
				" RESTRICCION, ESTADO, HORARIO, TO_CHAR(F_EVALUACION,'DD/MM/YYYY') AS F_EVALUACION,"+
				" VALEUCAS, NUM_ALUM, SEMANAS, OPTATIVA, COMENTARIO, CODIGO_OTRO, PRECIO"+
				" FROM ENOC.CARGA_GRUPO"+
				" WHERE CURSO_CARGA_ID IN (SELECT CURSO_CARGA_ID FROM ENOC.KRDX_CURSO_ACT WHERE TIPOCAL_ID IN ('5','6'))"; 

			rs = st.executeQuery(comando);
			while (rs.next()){
				CargaGrupo grupo = new CargaGrupo();
				grupo.mapeaReg(rs);
				mapa.put(rs.getString("CURSO_CARGA_ID"), grupo);
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.plan.GrupoUtil|mapaGruposDiferidos|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return mapa;
	}
	
	public HashMap<String, String> getRestriccionMateria(Connection conn, String cargaId) throws SQLException{
		HashMap<String, String> mapaGrupo = new HashMap<String, String>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando			= "";
		String llave			= "";
		//String valor 			= "";
		
		try{
			comando = "SELECT CURSO_CARGA_ID, CARGA_ID, BLOQUE_ID, CARRERA_ID, "+
					 "CODIGO_PERSONAL, GRUPO, MODALIDAD_ID, F_INICIO, F_FINAL, F_ENTREGA, "+
					 "RESTRICCION, ESTADO, HORARIO, TO_CHAR(F_EVALUACION,'DD/MM/YYYY') AS F_EVALUACION, "+
					 "VALEUCAS, NUM_ALUM, SEMANAS, OPTATIVA, COMENTARIO, CODIGO_OTRO, PRECIO "+
					 "FROM ENOC.CARGA_GRUPO "+  
					 "WHERE CARGA_ID = '"+cargaId+"'";	
			rs = st.executeQuery(comando);
			while (rs.next()){
				llave 	= rs.getString("CURSO_CARGA_ID");
				mapaGrupo.put(llave, rs.getString("RESTRICCION"));
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.plan.GrupoUtil|getRestriccionMateria|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return mapaGrupo;
	}
	
	public HashMap<String, String> mapMateriasCarga(Connection conn, String periodoId) throws SQLException{
		HashMap<String, String> mapaGrupo = new HashMap<String, String>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando			= "";
		String llave			= "";
		//String valor 			= "";
		
		try{
			comando = "SELECT CARGA_ID, COUNT(*) AS MATERIAS FROM ENOC.CARGA_GRUPO WHERE CARGA_ID IN (SELECT CARGA_ID FROM ENOC.CARGA WHERE PERIODO = '"+periodoId+"') GROUP BY CARGA_ID";	
			rs = st.executeQuery(comando);
			while (rs.next()){
				llave 	= rs.getString("CARGA_ID");
				mapaGrupo.put(llave, rs.getString("MATERIAS"));
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.plan.GrupoUtil|mapMateriasCarga|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return mapaGrupo;
	}
	
	public HashMap<String, String> mapMateriasFacultad(Connection conn, String cargaId) throws SQLException{
		HashMap<String, String> mapaGrupo = new HashMap<String, String>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando			= "";
		String llave			= "";
		//String valor 			= "";
		
		try{
			comando = "SELECT ENOC.FACULTAD(CARRERA_ID) AS FACULTAD, COUNT(*) AS MATERIAS FROM ENOC.CARGA_GRUPO WHERE CARGA_ID = '"+cargaId+"' GROUP BY ENOC.FACULTAD(CARRERA_ID)";	
			rs = st.executeQuery(comando);
			while (rs.next()){
				llave 	= rs.getString("FACULTAD");
				mapaGrupo.put(llave, rs.getString("MATERIAS"));
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.plan.GrupoUtil|mapMateriasFacultad|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return mapaGrupo;
	}
	
	public HashMap<String, String> mapMateriasMaestro(Connection conn, String cargaId) throws SQLException{
		HashMap<String, String> mapaGrupo = new HashMap<String, String>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando			= "";
		String llave			= "";
		//String valor 			= "";
		
		try{
			comando = "SELECT CODIGO_PERSONAL, COUNT(*) AS MATERIAS FROM ENOC.CARGA_GRUPO WHERE CARGA_ID = '"+cargaId+"' GROUP BY CODIGO_PERSONAL";	
			rs = st.executeQuery(comando);
			while (rs.next()){
				llave 	= rs.getString("CODIGO_PERSONAL");
				mapaGrupo.put(llave, rs.getString("MATERIAS"));
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.plan.GrupoUtil|mapMateriasMaestro|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return mapaGrupo;
	}
	
	public HashMap<String, String> mapMaestrosCarga(Connection conn, String periodoId) throws SQLException{
		HashMap<String, String> mapaGrupo = new HashMap<String, String>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando			= "";
		String llave			= "";
		//String valor 			= "";
		
		try{
			comando = "SELECT CARGA_ID, COUNT(DISTINCT(CODIGO_PERSONAL)) AS MAESTROS FROM ENOC.CARGA_GRUPO WHERE CARGA_ID IN (SELECT CARGA_ID FROM ENOC.CARGA WHERE PERIODO = '"+periodoId+"') GROUP BY CARGA_ID";	
			rs = st.executeQuery(comando);
			while (rs.next()){
				llave 	= rs.getString("CARGA_ID");
				mapaGrupo.put(llave, rs.getString("MAESTROS"));
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.plan.GrupoUtil|mapMaestrosCarga|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return mapaGrupo;
	}
	
	public HashMap<String, String> mapMaestrosFacultad(Connection conn, String cargaId) throws SQLException{
		HashMap<String, String> mapaGrupo = new HashMap<String, String>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando			= "";
		String llave			= "";
		//String valor 			= "";
		
		try{
			comando = "SELECT ENOC.FACULTAD(CARRERA_ID) AS FACULTAD, COUNT(DISTINCT(CODIGO_PERSONAL)) AS MAESTROS FROM ENOC.CARGA_GRUPO WHERE CARGA_ID = '"+cargaId+"' GROUP BY ENOC.FACULTAD(CARRERA_ID)";
			rs = st.executeQuery(comando);
			while (rs.next()){
				llave 	= rs.getString("FACULTAD");
				mapaGrupo.put(llave, rs.getString("MAESTROS"));
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.plan.GrupoUtil|mapMaestrosFacultad|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return mapaGrupo;
	}
	
	public HashMap<String, String> mapEvaluacionesCarga(Connection conn, String periodoId) throws SQLException{
		HashMap<String, String> mapaGrupo = new HashMap<String, String>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando			= "";
		String llave			= "";
		//String valor 			= "";
		
		try{
			comando = "SELECT CARGA_ID, SUM(GRUPO_NUM_EVAL(CURSO_CARGA_ID)) AS EVALUACIONES FROM ENOC.CARGA_GRUPO WHERE CARGA_ID IN (SELECT CARGA_ID FROM ENOC.CARGA WHERE PERIODO = '"+periodoId+"') GROUP BY CARGA_ID";	
			rs = st.executeQuery(comando);
			while (rs.next()){
				llave 	= rs.getString("CARGA_ID");
				mapaGrupo.put(llave, rs.getString("EVALUACIONES"));
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.plan.GrupoUtil|mapEvaluacionesCarga|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return mapaGrupo;
	}
	
	public HashMap<String, String> mapEvaluacionesFacultad(Connection conn, String cargaId) throws SQLException{
		HashMap<String, String> mapaGrupo = new HashMap<String, String>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando			= "";
		String llave			= "";
		//String valor 			= "";
		
		try{
			comando = "SELECT ENOC.FACULTAD(CARRERA_ID) AS FACULTAD, SUM(GRUPO_NUM_EVAL(CURSO_CARGA_ID)) AS EVALUACIONES FROM ENOC.CARGA_GRUPO WHERE CARGA_ID = '"+cargaId+"' GROUP BY ENOC.FACULTAD(CARRERA_ID)";	
			rs = st.executeQuery(comando);
			while (rs.next()){
				llave 	= rs.getString("FACULTAD");
				mapaGrupo.put(llave, rs.getString("EVALUACIONES"));
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.plan.GrupoUtil|mapEvaluacionesFacultad|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return mapaGrupo;
	}
	
	/* Mapa de la cantidad de materias agendadas por facultad en una carga academica */
	public HashMap<String, String> mapAgendadasFacultad(Connection conn, String cargaId) throws SQLException{
		HashMap<String, String> mapaGrupo = new HashMap<String, String>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando			= "";
		String llave			= "";
		//String valor 			= "";
		
		try{
			comando = " SELECT ENOC.FACULTAD(CARRERA_ID) AS FACULTAD, COUNT(CURSO_CARGA_ID) AS TOTAL"
					+ " FROM ENOC.CARGA_GRUPO"
					+ " WHERE CARGA_ID = '"+cargaId+"'"
					+ " AND CURSO_CARGA_ID IN"
					+ " 	(SELECT DISTINCT(CURSO_CARGA_ID) FROM ENOC.CARGA_GRUPO_ACTIVIDAD WHERE CURSO_CARGA_ID LIKE '"+cargaId+"%' AND AGENDADA_E42 = 'S')"
					+ " GROUP BY ENOC.FACULTAD(CARRERA_ID)";	
			rs = st.executeQuery(comando);
			while (rs.next()){
				llave 	= rs.getString("FACULTAD");
				mapaGrupo.put(llave, rs.getString("TOTAL"));
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.plan.GrupoUtil|mapAgendadasFacultad|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return mapaGrupo;
	}
	
	/* Mapa de la cantidad de materias agendadas por carga academica*/
	public HashMap<String, String> mapAgendadasCargas(Connection conn, String periodoId) throws SQLException{
		HashMap<String, String> mapaGrupo = new HashMap<String, String>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando			= "";
		String llave			= "";		
		
		try{
			comando = " SELECT CARGA_ID, COUNT(CURSO_CARGA_ID) AS TOTAL"
					+ " FROM ENOC.CARGA_GRUPO"
					+ " WHERE CARGA_ID IN( SELECT CARGA_ID FROM ENOC.CARGA WHERE PERIODO = '"+periodoId+"' )"
					+ " AND CURSO_CARGA_ID IN"
					+ " 	(SELECT DISTINCT(CURSO_CARGA_ID) FROM ENOC.CARGA_GRUPO_ACTIVIDAD WHERE CURSO_CARGA_ID LIKE CARGA_GRUPO.CARGA_ID||'%' AND AGENDADA_E42 = 'S')"
					+ " GROUP BY CARGA_ID";	
			rs = st.executeQuery(comando);
			while (rs.next()){
				llave 	= rs.getString("CARGA_ID");
				mapaGrupo.put(llave, rs.getString("TOTAL"));
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.plan.GrupoUtil|mapAgendadasFacultad|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return mapaGrupo;
	}
	
	public HashMap<String, String> mapEvaluacionesMateria(Connection conn, String cargaId) throws SQLException{
		HashMap<String, String> mapaGrupo = new HashMap<String, String>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando			= "";
		String llave			= "";
		//String valor 			= "";
		
		try{
			comando = "SELECT CURSO_CARGA_ID, GRUPO_NUM_EVAL(CURSO_CARGA_ID) AS EVALUACIONES FROM ENOC.CARGA_GRUPO WHERE CARGA_ID = '"+cargaId+"'";
			rs = st.executeQuery(comando);
			while (rs.next()){
				llave 	= rs.getString("CURSO_CARGA_ID");
				mapaGrupo.put(llave, rs.getString("EVALUACIONES"));
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.plan.GrupoUtil|mapEvaluacionesFacultad|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return mapaGrupo;
	}
	
	public HashMap<String, String> mapEvaluacionesMaestro(Connection conn, String codigoPersonal) throws SQLException{
		HashMap<String, String> mapaGrupo = new HashMap<String, String>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando			= "";
		String llave			= "";
		//String valor 			= "";
		
		try{
			comando = "SELECT CURSO_CARGA_ID, GRUPO_NUM_EVAL(CURSO_CARGA_ID) AS EVALUACIONES FROM ENOC.CARGA_GRUPO WHERE CODIGO_PERSONAL = '"+codigoPersonal+"'";
			rs = st.executeQuery(comando);
			while (rs.next()){
				llave 	= rs.getString("CURSO_CARGA_ID");
				mapaGrupo.put(llave, rs.getString("EVALUACIONES"));
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.plan.GrupoUtil|mapEvaluacionesFacultad|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return mapaGrupo;
	}	
	
	public HashMap<String, String> mapActividadesCarga(Connection conn, String periodoId) throws SQLException{
		HashMap<String, String> mapaGrupo = new HashMap<String, String>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando			= "";
		String llave			= "";
		//String valor 			= "";
		
		try{
			comando = "SELECT CARGA_ID, SUM(GRUPO_NUM_ACTIV(CURSO_CARGA_ID)) AS ACTIVIDADES FROM ENOC.CARGA_GRUPO WHERE CARGA_ID IN (SELECT CARGA_ID FROM ENOC.CARGA WHERE PERIODO = '"+periodoId+"') GROUP BY CARGA_ID";	
			rs = st.executeQuery(comando);
			while (rs.next()){
				llave 	= rs.getString("CARGA_ID");
				mapaGrupo.put(llave, rs.getString("ACTIVIDADES"));
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.plan.GrupoUtil|mapActividadesCarga|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return mapaGrupo;
	}
	
	public HashMap<String, String> mapActividadesFacultad(Connection conn, String cargaId) throws SQLException{
		HashMap<String, String> mapaGrupo = new HashMap<String, String>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando			= "";
		String llave			= "";
		//String valor 			= "";
		
		try{
			comando = "SELECT ENOC.FACULTAD(CARRERA_ID) AS FACULTAD, SUM(GRUPO_NUM_ACTIV(CURSO_CARGA_ID)) AS ACTIVIDADES FROM ENOC.CARGA_GRUPO WHERE CARGA_ID = '"+cargaId+"' GROUP BY ENOC.FACULTAD(CARRERA_ID)";	
			rs = st.executeQuery(comando);
			while (rs.next()){
				llave 	= rs.getString("FACULTAD");
				mapaGrupo.put(llave, rs.getString("ACTIVIDADES"));
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.plan.GrupoUtil|mapActividadesFacultad|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return mapaGrupo;
	}
	
	public HashMap<String, String> mapActividadesMateria(Connection conn, String cargaId) throws SQLException{
		HashMap<String, String> mapaGrupo = new HashMap<String, String>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando			= "";
		String llave			= "";
		//String valor 			= "";
		
		try{
			comando = "SELECT CURSO_CARGA_ID, GRUPO_NUM_ACTIV(CURSO_CARGA_ID) AS ACTIVIDADES FROM ENOC.CARGA_GRUPO WHERE CARGA_ID = '"+cargaId+"'";	
			rs = st.executeQuery(comando);
			while (rs.next()){
				llave 	= rs.getString("CURSO_CARGA_ID");
				mapaGrupo.put(llave, rs.getString("ACTIVIDADES"));
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.plan.GrupoUtil|mapActividadesFacultad|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return mapaGrupo;
	}
	
	public HashMap<String, String> mapActividadesMaestro(Connection conn, String codigoPersonal) throws SQLException{
		HashMap<String, String> mapaGrupo = new HashMap<String, String>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando			= "";
		String llave			= "";
		//String valor 			= "";
		
		try{
			comando = "SELECT CURSO_CARGA_ID, GRUPO_NUM_ACTIV(CURSO_CARGA_ID) AS ACTIVIDADES FROM ENOC.CARGA_GRUPO WHERE CODIGO_PERSONAL = '"+codigoPersonal+"'";
			rs = st.executeQuery(comando);
			while (rs.next()){
				llave 	= rs.getString("CURSO_CARGA_ID");
				mapaGrupo.put(llave, rs.getString("ACTIVIDADES"));
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.plan.GrupoUtil|mapActividadesFacultad|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return mapaGrupo;
	}
	
	public HashMap<String, String> mapMateriasConEval(Connection conn, String cargaId) throws SQLException{
		HashMap<String, String> mapaGrupo = new HashMap<String, String>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando			= "";
		String llave			= "";
		//String valor 			= "";
		
		try{
			comando = " SELECT ENOC.FACULTAD(CARRERA_ID) AS FACULTAD, COUNT(CURSO_CARGA_ID) AS TOTAL "
					+ " FROM ENOC.CARGA_GRUPO"
					+ " WHERE CARGA_ID = '"+cargaId+"'"
					+ " AND CURSO_CARGA_ID IN"
					+ "		(SELECT CURSO_CARGA_ID FROM ENOC.CARGA_GRUPO_EVALUACION WHERE CURSO_CARGA_ID LIKE '"+cargaId+"%')"
					+ " GROUP BY ENOC.FACULTAD(CARRERA_ID)";
			rs = st.executeQuery(comando);			
			while (rs.next()){
				llave 	= rs.getString("FACULTAD");
				mapaGrupo.put(llave, rs.getString("TOTAL"));
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.plan.GrupoUtil|mapMateriasConEval|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return mapaGrupo;
	}
	
	public HashMap<String, String> mapMateriasConActi(Connection conn, String cargaId) throws SQLException{
		HashMap<String, String> mapaGrupo = new HashMap<String, String>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando			= "";
		String llave			= "";
		//String valor 			= "";
		
		try{
			comando = " SELECT ENOC.FACULTAD(CARRERA_ID) AS FACULTAD, COUNT(CURSO_CARGA_ID) AS TOTAL "
					+ " FROM ENOC.CARGA_GRUPO"
					+ " WHERE CARGA_ID = '"+cargaId+"'"
					+ " AND CURSO_CARGA_ID IN"
					+ "		(SELECT CURSO_CARGA_ID FROM ENOC.CARGA_GRUPO_ACTIVIDAD WHERE CURSO_CARGA_ID LIKE '"+cargaId+"%')"
					+ " GROUP BY ENOC.FACULTAD(CARRERA_ID)";
			rs = st.executeQuery(comando);			
			while (rs.next()){
				llave 	= rs.getString("FACULTAD");
				mapaGrupo.put(llave, rs.getString("TOTAL"));
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.plan.GrupoUtil|mapMateriasConActi|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return mapaGrupo;
	}
	
	public HashMap<String, String> mapMateriasConEsquemaCompleto(Connection conn, String cargaId) throws SQLException{
		HashMap<String, String> mapaGrupo = new HashMap<String, String>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando			= "";
		String llave			= "";
		//String valor 			= "";
		
		try{
			comando = " SELECT ENOC.FACULTAD(CARRERA_ID) AS FACULTAD, COUNT(CURSO_CARGA_ID) AS TOTAL "
					+ " FROM ENOC.CARGA_GRUPO"
					+ " WHERE CARGA_ID = '"+cargaId+"'"
					+ " AND CURSO_CARGA_ID IN"
					+ "		(SELECT CURSO_CARGA_ID FROM ENOC.CARGA_GRUPO_EVALUACION WHERE CURSO_CARGA_ID LIKE '"+cargaId+"%')"
					+ " AND (SELECT COALESCE(SUM(VALOR),0) FROM ENOC.CARGA_GRUPO_EVALUACION WHERE CURSO_CARGA_ID = CARGA_GRUPO.CURSO_CARGA_ID) >= 100"
					+ " GROUP BY ENOC.FACULTAD(CARRERA_ID)";
			rs = st.executeQuery(comando);			
			while (rs.next()){
				llave 	= rs.getString("FACULTAD");
				mapaGrupo.put(llave, rs.getString("TOTAL"));
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.plan.GrupoUtil|mapMateriasConEval|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return mapaGrupo;
	}
	
	public HashMap<String, String> mapMateriasEsquemaCompleto(Connection conn, String periodoId) throws SQLException{
		HashMap<String, String> mapaGrupo = new HashMap<String, String>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando			= "";
		String llave			= "";
		//String valor 			= "";
		
		try{
			comando = " SELECT CARGA_ID, COUNT(CURSO_CARGA_ID) AS TOTAL "
					+ " FROM ENOC.CARGA_GRUPO"
					+ " WHERE CARGA_ID IN (SELECT CARGA_ID FROM ENOC.CARGA WHERE PERIODO = '"+periodoId+"')"
					+ " AND (SELECT COALESCE(SUM(VALOR),0) FROM ENOC.CARGA_GRUPO_EVALUACION WHERE CURSO_CARGA_ID = CARGA_GRUPO.CURSO_CARGA_ID) >= 100"
					+ " GROUP BY CARGA_ID";
			rs = st.executeQuery(comando);			
			while (rs.next()){
				llave 	= rs.getString("CARGA_ID");
				mapaGrupo.put(llave, rs.getString("TOTAL"));
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.plan.GrupoUtil|mapMateriasEsquemaCompleto|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return mapaGrupo;
	}
	
	public HashMap<String, String> mapMateriasEvaluacion(Connection conn, String periodoId) throws SQLException{
		HashMap<String, String> mapaGrupo = new HashMap<String, String>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando			= "";
		String llave			= "";
		//String valor 			= "";
		
		try{
			comando = "SELECT CARGA_ID, GRUPO_NUM_EVAL(CURSO_CARGA_ID) AS EVALUACION, COUNT(CURSO_CARGA_ID) AS NUMMATERIAS FROM ENOC.CARGA_GRUPO WHERE CARGA_ID IN (SELECT CARGA_ID FROM ENOC.CARGA WHERE PERIODO = '"+periodoId+"') GROUP BY CARGA_ID, GRUPO_NUM_EVAL(CURSO_CARGA_ID)";	
			rs = st.executeQuery(comando);
			while (rs.next()){
				llave 	= rs.getString("CARGA_ID")+rs.getString("EVALUACION");
				mapaGrupo.put(llave, rs.getString("NUMMATERIAS"));
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.plan.GrupoUtil|mapMateriasEvaluacion|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return mapaGrupo;
	}
	
	public static ArrayList<String> getMaestrosCargaPeriodo(Connection conn, String periodoId) throws SQLException{
		
		ArrayList<String> listMaestrosCarga = new ArrayList<String>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
		
		try{
			comando = "SELECT DISTINCT(CODIGO_PERSONAL) AS CODIGO, USUARIO_APELLIDO(CODIGO_PERSONAL) AS NOMBRE FROM ENOC.CARGA_GRUPO WHERE CARGA_ID IN "
					+ "(SELECT CARGA_ID FROM ENOC.CARGA WHERE PERIODO = '"+periodoId+"') "
					+ "AND TRIM(CODIGO_PERSONAL) != ' ' "
					+ "ORDER BY USUARIO_APELLIDO(CODIGO_PERSONAL)";
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				listMaestrosCarga.add(rs.getString("CODIGO")+"@@"+rs.getString("NOMBRE"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoUtil|getMaestrosCargaPeriodo|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return listMaestrosCarga;
	}
	
	// Lista de maestros que imparten materias en una carga y facultad
	public static ArrayList<String> lisMaestrosCarga(Connection conn, String cargaId, String orden) throws SQLException{
			
		ArrayList<String> listMaestrosCarga = new ArrayList<String>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
		
		try{
			comando = " SELECT DISTINCT(CODIGO_PERSONAL) AS CODIGO, ENOC.EMP_APELLIDO(CODIGO_PERSONAL) FROM ENOC.CARGA_GRUPO"
					+ " WHERE CARGA_ID = '"+cargaId+"' "+ orden;
			rs = st.executeQuery(comando);
			while (rs.next()){
				listMaestrosCarga.add(rs.getString("CODIGO"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoUtil|lisMaestrosCarga|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return listMaestrosCarga;
	}
	
	// Lista de maestros que imparten materias en una carga y facultad
	public static ArrayList<String> lisMaestrosSinFac(Connection conn, String cargaId, String facultad, String orden) throws SQLException{
				
		ArrayList<String> listMaestros = new ArrayList<String>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
		
		try{
			comando = " SELECT DISTINCT(CODIGO_PERSONAL) AS CODIGO"
					+ " FROM ENOC.CARGA_GRUPO"
					+ " WHERE CARGA_ID = '"+cargaId+"'"
					+ " AND ENOC.FACULTAD(CARRERA_ID) = '"+facultad+"'"
					+ " AND CODIGO_PERSONAL NOT IN (SELECT CODIGO_PERSONAL FROM ENOC.HCA_MAESTRO) "+ orden;
			rs = st.executeQuery(comando);
			while (rs.next()){
				listMaestros.add(rs.getString("CODIGO"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoUtil|lisMaestrosSinFac|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return listMaestros;
	}
	
	// Lista de maestros que imparten materias en una carga y facultad
	public static ArrayList<String> lisMaestrosCargayFac(Connection conn, String cargaId, String facultadId, String orden) throws SQLException{
		
		ArrayList<String> listMaestrosCarga = new ArrayList<String>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
		
		try{
			comando = " SELECT DISTINCT(CODIGO_PERSONAL) AS CODIGO FROM ENOC.CARGA_GRUPO"
					+ " WHERE CARGA_ID = '"+cargaId+"'"
					+ " AND ENOC.FACULTAD(CARRERA_ID) = '"+facultadId+"' "+ orden;			
			rs = st.executeQuery(comando);
			while (rs.next()){
				listMaestrosCarga.add(rs.getString("CODIGO"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoUtil|lisMaestrosCargayFac|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return listMaestrosCarga;
	}
	
	public HashMap<String, String> profesorCarga(Connection conn, String cargaId) throws SQLException{
		HashMap<String, String> mapaGrupo = new HashMap<String, String>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando			= "";
		String llave			= "";
		//String valor 			= "";
		
		try{
			comando = "SELECT DISTINCT(CODIGO_PERSONAL) AS CODIGO, EMP_NOMBRE(CODIGO_PERSONAL) AS NOMBRE FROM ENOC.CARGA_GRUPO"
					+ " WHERE CARGA_ID = '"+cargaId+"'";	
			rs = st.executeQuery(comando);
			while (rs.next()){
				llave 	= rs.getString("CODIGO");
				mapaGrupo.put(llave, rs.getString("NOMBRE"));
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoUtil|profesorCargaCarreraFacultads|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return mapaGrupo;
	}
	
	public HashMap<String, String> mapMateriasProfesor(Connection conn, String periodoId) throws SQLException{
		HashMap<String, String> mapaGrupo = new HashMap<String, String>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando			= "";
		String llave			= "";
		//String valor 			= "";
		
		try{
			comando = "SELECT CODIGO_PERSONAL, COUNT(CURSO_CARGA_ID) AS MATERIAS FROM ENOC.CARGA_GRUPO WHERE CARGA_ID IN (SELECT CARGA_ID FROM ENOC.CARGA WHERE PERIODO = '"+periodoId+"') GROUP BY CODIGO_PERSONAL";	
			rs = st.executeQuery(comando);
			while (rs.next()){
				llave 	= rs.getString("CODIGO_PERSONAL");
				mapaGrupo.put(llave, rs.getString("MATERIAS"));
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoUtil|mapMateriasProfesor|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return mapaGrupo;
	}
	
	public HashMap<String, String> mapEvaluacionProfesor(Connection conn, String periodoId) throws SQLException{
		HashMap<String, String> mapaGrupo = new HashMap<String, String>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando			= "";
		String llave			= "";
		//String valor 			= "";
		
		try{
			comando = "SELECT CODIGO_PERSONAL, SUM(GRUPO_NUM_EVAL(CURSO_CARGA_ID)) AS EVALUACION FROM ENOC.CARGA_GRUPO WHERE CARGA_ID IN (SELECT CARGA_ID FROM ENOC.CARGA WHERE PERIODO = '"+periodoId+"') GROUP BY CODIGO_PERSONAL";	
			rs = st.executeQuery(comando);
			while (rs.next()){
				llave 	= rs.getString("CODIGO_PERSONAL");
				mapaGrupo.put(llave, rs.getString("EVALUACION"));
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoUtil|mapEvaluacionProfesor|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return mapaGrupo;
	}
	
	public HashMap<String, String> mapActividadProfesor(Connection conn, String periodoId) throws SQLException{
		HashMap<String, String> mapaGrupo = new HashMap<String, String>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando			= "";
		String llave			= "";
		//String valor 			= "";
		
		try{
			comando = "SELECT CODIGO_PERSONAL, SUM(GRUPO_NUM_ACTIV(CURSO_CARGA_ID)) AS ACTIVIDADES FROM ENOC.CARGA_GRUPO WHERE CARGA_ID IN (SELECT CARGA_ID FROM ENOC.CARGA WHERE PERIODO = '"+periodoId+"') GROUP BY CODIGO_PERSONAL";	
			rs = st.executeQuery(comando);
			while (rs.next()){
				llave 	= rs.getString("CODIGO_PERSONAL");
				mapaGrupo.put(llave, rs.getString("ACTIVIDADES"));
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.plan.GrupoUtil|mapActividadProfesor|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return mapaGrupo;
	}
	
	// Map que cuenta el numeros de materias por facultad en una carga
	public HashMap<String, String> mapFacMaterias(Connection conn, String cargaId) throws SQLException{
		HashMap<String, String> mapaGrupo = new HashMap<String, String>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando			= "";
		String llave			= "";
		//String valor 			= "";
		
		try{
			comando = "SELECT ENOC.FACULTAD(CARRERA_ID) AS FACULTAD, COUNT(DISTINCT(CODIGO_PERSONAL)) AS TOTAL FROM ENOC.CARGA_GRUPO WHERE CARGA_ID = '"+cargaId+"' GROUP BY ENOC.FACULTAD(CARRERA_ID)";	
			rs = st.executeQuery(comando);
			while (rs.next()){
				llave 	= rs.getString("FACULTAD");
				mapaGrupo.put(llave, rs.getString("TOTAL"));
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoUtil|mapFacMaterias|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return mapaGrupo;
	}
	
	// Map de materias evaluadas en e42 o Sistema Academico
	public HashMap<String, String> mapGrupoRegistro(Connection conn, String cargaId, String opcion) throws SQLException{
		HashMap<String, String> mapaGrupo = new HashMap<String, String>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando			= "";
		
		try{
			comando = " SELECT CURSO_CARGA_ID FROM ENOC.CARGA_GRUPO "
					+ " WHERE CARGA_ID = '"+cargaId+"'"
					+ " AND CURSO_CARGA_ID IN (SELECT CURSO_CARGA_ID FROM ENOC.CARGA_GRUPO_EVALUACION "+opcion+")";
			rs = st.executeQuery(comando);
			while (rs.next()){				
				mapaGrupo.put(rs.getString("CURSO_CARGA_ID"), rs.getString("CURSO_CARGA_ID"));
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoUtil|mapGrupoRegistro|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return mapaGrupo;
	}
	/*
	 *SELECT CURSO_CARGA_ID FROM ENOC.CARGA_GRUPO 
WHERE CARGA_ID = '14151A'
AND CURSO_CARGA_ID IN (SELECT CURSO_CARGA_ID FROM ENOC.CARGA_GRUPO_EVALUACION WHERE EVALUACION_E42 = 0); 
	 * */
	
	
	
	public static ArrayList<String> lisAllMaestros(Connection conn, String cargaId) throws SQLException{
		
		ArrayList<String> lisAllMaestros = new ArrayList<String>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
		
		try{
			comando = "SELECT ENOC.FACULTAD(CARRERA_ID) AS FACULTAD, CARRERA_ID, CODIGO_PERSONAL "
					+ "FROM ENOC.CARGA_GRUPO "
					+ "WHERE CARGA_ID = '"+cargaId+"' "
					+ "GROUP BY ENOC.FACULTAD(CARRERA_ID), CARRERA_ID, CODIGO_PERSONAL "
					+ "ORDER BY FACULTAD, CARRERA_ID, CODIGO_PERSONAL";
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				lisAllMaestros.add(rs.getString("FACULTAD")+"@@"+rs.getString("CARRERA_ID")+"@@"+rs.getString("CODIGO_PERSONAL"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoUtil|lisAllMaestros|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisAllMaestros;
	}
	
	public HashMap<String, String> mapEvalCargaAcademico(Connection conn, String cargaId) throws SQLException{
		HashMap<String, String> mapaGrupo = new HashMap<String, String>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando			= "";
		
		try{
			comando ="SELECT CODIGO_PERSONAL, COUNT(CURSO_CARGA_ID) AS TOTAL FROM ENOC.CARGA_GRUPO "
					+ "WHERE CARGA_ID = '"+cargaId+"' "
					+ "AND CURSO_CARGA_ID IN (SELECT CURSO_CARGA_ID FROM ENOC.KRDX_ALUMNO_EVAL WHERE CURSO_CARGA_ID LIKE '"+cargaId+"%' AND EVALUACION_E42 = 0)"
					+ "GROUP BY CODIGO_PERSONAL";
			rs = st.executeQuery(comando);
			while (rs.next()){				
				mapaGrupo.put(rs.getString("CODIGO_PERSONAL"), rs.getString("TOTAL"));
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoUtil|mapEvalCargaAcademico|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return mapaGrupo;
	}
	
	public HashMap<String, String> mapEvalCargaECuarentaYDos(Connection conn, String cargaId) throws SQLException{
		HashMap<String, String> mapaGrupo = new HashMap<String, String>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando			= "";
		
		try{
			comando ="SELECT CODIGO_PERSONAL, COUNT(CURSO_CARGA_ID) AS TOTAL FROM ENOC.CARGA_GRUPO "
					+ "WHERE CARGA_ID = '"+cargaId+"' "
					+ "AND CURSO_CARGA_ID IN (SELECT CURSO_CARGA_ID FROM ENOC.KRDX_ALUMNO_EVAL WHERE CURSO_CARGA_ID LIKE '"+cargaId+"%' AND EVALUACION_E42 != 0)"
					+ "GROUP BY CODIGO_PERSONAL";
			rs = st.executeQuery(comando);
			while (rs.next()){				
				mapaGrupo.put(rs.getString("CODIGO_PERSONAL"), rs.getString("TOTAL"));
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoUtil|mapEvalCargaECuarentaYDos|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return mapaGrupo;
	}
	
	public HashMap<String, String> mapMateriasMaestros(Connection conn, String cargaId) throws SQLException{
		HashMap<String, String> mapaMaestros = new HashMap<String, String>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando			= "";
		
		try{
			comando ="SELECT "
				+ "CODIGO_PERSONAL, "
				+ "COUNT(CURSO_CARGA_ID) AS TOTAL "
				+ "FROM ENOC.CARGA_GRUPO "
				+ "WHERE CARGA_ID = '"+cargaId+"' "
				+ "GROUP BY CODIGO_PERSONAL";
			rs = st.executeQuery(comando);
			while (rs.next()){				
				mapaMaestros.put(rs.getString("CODIGO_PERSONAL"), rs.getString("TOTAL"));
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoUtil|mapMateriasMaestros|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return mapaMaestros;
	}
	
	public HashMap<String, String> mapCarreraOrigen(Connection conn, String cargaId) throws SQLException{
		HashMap<String, String> mapa = new HashMap<String, String>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando			= "";
		
		try{
			comando ="SELECT CURSO_CARGA_ID, CARRERA_ID FROM ENOC.CARGA_GRUPO WHERE CARGA_ID = '"+cargaId+"'";
			rs = st.executeQuery(comando);
			while (rs.next()){				
				mapa.put(rs.getString("CURSO_CARGA_ID"), rs.getString("CARRERA_ID"));
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.carga.CargaGrupoUtil|mapCarreraOrigen|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return mapa;
	}
	
}