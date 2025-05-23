/*
 * Created on 06-abr-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package aca.investiga;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * @author Karelly
 *
 */
public class InvProyectoUtil {
	
	public boolean insertReg(Connection conn, InvProyecto proy) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.INV_PROYECTO(PROYECTO_ID, PROYECTO_NOMBRE, CODIGO_PERSONAL, TIPO, LINEA, "
					+ " CARRERA_ID, DEPARTAMENTO, FECHA_INICIO, FECHA_FINAL,"
//					+ " RESUMEN,"
//					+ " ESTADO_ARTE,"
					+ " DOCUMENTO,"
					+ " ESTADO,"
					+ " FOLIO,"
//					+ " ANTECEDENTES,"
//					+ " JUSTIFICACION,"
//					+ " RES_DOCENTE,"
//					+ " RES_ALUMNO,"
					+ " INVESTIGADORES) "
					+ " VALUES(?, ?, ?, TO_NUMBER(?,'99'), ?, ?, ?, TO_DATE(?,'DD/MM/YYYY'), TO_DATE(?,'DD/MM/YYYY'),"
//					+ " ?, ?,"
					+ " ?, ?, ?,"
//					+ " ?, ?, ?, ?,"
					+ " ?)");
			
			ps.setString(1, proy.getProyectoId());
			ps.setString(2, proy.getProyectoNombre());
			ps.setString(3, proy.getCodigoPersonal());
			ps.setString(4, proy.getTipo());
			ps.setString(5, proy.getLinea());
			ps.setString(6, proy.getCarreraId());
			ps.setString(7, proy.getDepartamento());
			ps.setString(8, proy.getFechaInicio());
			ps.setString(9, proy.getFechaFinal());
//			ps.setString(10, proy.getResumen());
//			ps.setString(11, proy.getEstadoArte());
			ps.setString(10, proy.getDocumento());
			ps.setString(11, proy.getEstado());
			ps.setString(12, proy.getFolio());
//			ps.setString(15, proy.getAntecedentes());
//			ps.setString(16, proy.getJustificacion());			
//			ps.setString(17, proy.getResDocente());
//			ps.setString(18, proy.getResAlumno());
			ps.setString(13, proy.getInvestigadores());			
			
			if ( ps.executeUpdate()== 1){
				ok = true;				
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.investiga.InvProyecto|insertReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean updateReg(Connection conn, InvProyecto proy) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.INV_PROYECTO "
					+ " SET PROYECTO_NOMBRE = ?,"
					+ " CODIGO_PERSONAL = ?,"
					+ " TIPO = TO_NUMBER(?,'99'),"
					+ " LINEA = ? ,"
					+ " CARRERA_ID = ?,"
					+ " DEPARTAMENTO = ?,"
					+ " FECHA_INICIO = TO_DATE(?,'DD/MM/YYYY'),"
					+ " FECHA_FINAL = TO_DATE(?, 'DD/MM/YYYY'),"
//					+ " RES_DOCENTE = ?, "
//					+ " RES_ALUMNO = ?,"
					+ " INVESTIGADORES = ?,"
					+ " DOCUMENTO = ?,"
					+ " ESTADO = ?, "
					+ " FOLIO = ?	"
//					+ " RESUMEN = ?,"
//					+ " ESTADO_ARTE = ?,"
//					+ " ANTECEDENTES = ?,"
//					+ " JUSTIFICACION = ?,"
					+ " WHERE PROYECTO_ID = ? ");			
			
			ps.setString(1, proy.getProyectoNombre());
			ps.setString(2, proy.getCodigoPersonal());
			ps.setString(3, proy.getTipo());
			ps.setString(4, proy.getLinea());
			ps.setString(5, proy.getCarreraId());
			ps.setString(6, proy.getDepartamento());
			ps.setString(7, proy.getFechaInicio());
			ps.setString(8, proy.getFechaFinal());
//			ps.setString(9, proy.getResDocente());
//			ps.setString(10, proy.getResAlumno());
			ps.setString(9, proy.getInvestigadores());
			ps.setString(10, proy.getDocumento());
			ps.setString(11, proy.getEstado());
			ps.setString(12, proy.getFolio());
//			ps.setString(15, proy.getResumen());
//			ps.setString(16, proy.getEstadoArte());
//			ps.setString(17, proy.getAntecedentes());
//			ps.setString(18, proy.getJustificacion());	
			ps.setString(13, proy.getProyectoId());
				
			if ( ps.executeUpdate()== 1){
				ok = true;				
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.investiga.InvProyectoUtil|updateReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public static boolean updateDocumento(Connection conn, String proyectoId, String documento ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.INV_PROYECTO "
					+ " SET DOCUMENTO = ?"					
					+ " WHERE PROYECTO_ID = ? ");
			
			ps.setString(1,documento);
			ps.setString(2,proyectoId);
			
			if ( ps.executeUpdate()== 1){
				ok = true;				
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.investiga.InvProyecto|updateDocumento|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public static boolean updateEstado(Connection conn, String proyectoId, String estado ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.INV_PROYECTO "
					+ " SET ESTADO = ?"					
					+ " WHERE PROYECTO_ID = ? ");
			
			ps.setString(1,estado);
			ps.setString(2,proyectoId);
			
			if ( ps.executeUpdate()== 1){
				ok = true;				
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.investiga.InvProyecto|updateEstado|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean deleteReg(Connection conn, String proyectoId ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.INV_PROYECTO WHERE PROYECTO_ID = ? "); 
			ps.setString(1,proyectoId);			
		
			if ( ps.executeUpdate()== 1){
				ok = true;				
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.investiga.InvProyecto|deleteReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public InvProyecto mapeaRegId(Connection con, String proyectoId) throws SQLException{
		
		InvProyecto inv = new InvProyecto();
		PreparedStatement ps = null;
		ResultSet rs = null;		
		try{ 
			ps = con.prepareStatement("SELECT PROYECTO_ID, PROYECTO_NOMBRE, CODIGO_PERSONAL, TIPO, LINEA,"
					+ " CARRERA_ID, DEPARTAMENTO, TO_CHAR(FECHA_INICIO, 'DD/MM/YYYY') AS FECHA_INICIO, "
					+ " TO_CHAR(FECHA_FINAL, 'DD/MM/YYYY') AS FECHA_FINAL, RESUMEN, ESTADO_ARTE, DOCUMENTO, ESTADO, FOLIO, "
					+ " ANTECEDENTES, JUSTIFICACION, RES_DOCENTE, RES_ALUMNO, INVESTIGADORES"
					+ " FROM ENOC.INV_PROYECTO"
					+ " WHERE PROYECTO_ID = ? "); 
			ps.setString(1,proyectoId);
			
			rs = ps.executeQuery();
			
			if(rs.next()){
				inv.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.investiga.InvProyecto|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return inv;
	}
	
	public String maxReg(Connection con, String year) throws SQLException{
		PreparedStatement ps = null;
		ResultSet rs 	= null;
		String maximo 	= year+"-001"; 
		try{ 
			ps = con.prepareStatement(" SELECT COALESCE(TRIM(MAX(SUBSTR(PROYECTO_ID,6,3)+1)), '1') AS MAXIMO FROM ENOC.INV_PROYECTO WHERE SUBSTR(PROYECTO_ID,1,4) = ?");
			ps.setString(1,year);
			rs = ps.executeQuery();
			
			if(rs.next()){
				maximo = rs.getString("MAXIMO");
				if (maximo.length()==1){ 
					maximo = year+"-00"+maximo;
				}else if (maximo.length()==2){
					maximo = year+"-0"+maximo;
				}else if(maximo.length()==3){
					maximo = year+"-"+maximo;
				}else{
					maximo = year+maximo;
				}				
			}
		}catch(Exception ex){
			System.out.println("Error - aca.investiga.InvProyecto|maxReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }			
		}
		return maximo;
	}
	
	public boolean existeReg(Connection conn, String proyectoId) throws SQLException{
		boolean 		ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.INV_PROYECTO WHERE PROYECTO_ID = ? "); 
			ps.setString(1,proyectoId);
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.investiga.InvProyecto|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public static String getEstado(Connection conn, String proyectoId) throws SQLException{
		PreparedStatement ps	= null;
		ResultSet 		rs		= null;		
		String estado 			= "-";
		
		try{
			ps = conn.prepareStatement("SELECT ESTADO FROM ENOC.INV_PROYECTO WHERE PROYECTO_ID = ? ");
			ps.setString(1,proyectoId);
			rs = ps.executeQuery();
			if (rs.next())
				estado = rs.getString("ESTADO");
			
		}catch(Exception ex){
			System.out.println("Error - aca.investiga.InvProyecto|getEstado|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return estado;
	}
	
	public static String getNombreProyecto(Connection conn, String proyectoId) throws SQLException{
		PreparedStatement ps	= null;
		ResultSet 		rs		= null;		
		String estado 			= "-";
		
		try{
			ps = conn.prepareStatement("SELECT PROYECTO_NOMBRE FROM ENOC.INV_PROYECTO WHERE PROYECTO_ID = ? ");
			ps.setString(1,proyectoId);
			rs = ps.executeQuery();
			if (rs.next())
				estado = rs.getString("PROYECTO_NOMBRE");
			
		}catch(Exception ex){
			System.out.println("Error - aca.investiga.InvProyecto|getNombreProyecto|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return estado;
	}

	public ArrayList<InvProyecto> getListAll(Connection conn, String orden ) throws SQLException{
		ArrayList<InvProyecto> listProyectos	= new ArrayList<InvProyecto>();
		Statement st 	= conn.createStatement();
		ResultSet rs 	= null;
		String comando	= "";
		
		try{
			comando = " SELECT PROYECTO_ID, PROYECTO_NOMBRE, CODIGO_PERSONAL, TIPO, LINEA, CARRERA_ID, DEPARTAMENTO,"
					+ " TO_CHAR(FECHA_INICIO,'DD/MM/YYYY') AS FECHA_INICIO, TO_CHAR(FECHA_FINAL, 'DD/MM/YYYY') AS FECHA_FINAL,"
					+ " RESUMEN, ESTADO_ARTE, DOCUMENTO, ESTADO, FOLIO, ANTECEDENTES, JUSTIFICACION, RES_DOCENTE, RES_ALUMNO, INVESTIGADORES"
					+ " FROM ENOC.INV_PROYECTO "+orden; 
			
			rs = st.executeQuery(comando);			
			while (rs.next()){
				
				InvProyecto proyecto = new InvProyecto();				
				proyecto.mapeaReg(rs);
				listProyectos.add(proyecto);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.investiga.InvProyectoUtil|getListAll|:"+ex);
		}finally{		
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }	
		}
		
		return listProyectos;
	}
	
	public ArrayList<InvProyecto> getListProyectosEmpleado(Connection conn, String codigoPersonal, String orden ) throws SQLException{
		ArrayList<InvProyecto> listProyectos	= new ArrayList<InvProyecto>();
		Statement st 	= conn.createStatement();
		ResultSet rs 	= null;
		String comando	= "";
		
		try{
			comando = " SELECT PROYECTO_ID, PROYECTO_NOMBRE, CODIGO_PERSONAL, TIPO, LINEA, CARRERA_ID, DEPARTAMENTO,"
					+ " TO_CHAR(FECHA_INICIO,'DD/MM/YYYY') AS FECHA_INICIO, TO_CHAR(FECHA_FINAL,'DD/MM/YYYY') AS FECHA_FINAL,"
					+ " RESUMEN, ESTADO_ARTE, DOCUMENTO, ESTADO, FOLIO, ANTECEDENTES, JUSTIFICACION, RES_DOCENTE, RES_ALUMNO, INVESTIGADORES"
					+ " FROM ENOC.INV_PROYECTO WHERE CODIGO_PERSONAL = '"+codigoPersonal+"' "+orden; 
			
			rs = st.executeQuery(comando);			
			while (rs.next()){
				
				InvProyecto proyecto = new InvProyecto();				
				proyecto.mapeaReg(rs);
				listProyectos.add(proyecto);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.investiga.InvProyectoUtil|getListProyectosEmpleado|:"+ex);
		}finally{		
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }	
		}
		
		return listProyectos;
	}
	
	// Filtra los proyectos que pertenezcan a las facultades del referente
	public ArrayList<InvProyecto> getListProyectosReferente(Connection conn, String facultades, String orden ) throws SQLException{
		ArrayList<InvProyecto> listProyectos	= new ArrayList<InvProyecto>();
		Statement st 	= conn.createStatement();
		ResultSet rs 	= null;
		String comando	= "";
		
		try{
			comando = " SELECT PROYECTO_ID, PROYECTO_NOMBRE, CODIGO_PERSONAL, TIPO, LINEA, CARRERA_ID, DEPARTAMENTO,"
					+ " TO_CHAR(FECHA_INICIO,'DD/MM/YYYY') AS FECHA_INICIO, TO_CHAR(FECHA_FINAL,'DD/MM/YYYY') AS FECHA_FINAL,"
					+ " RESUMEN, ESTADO_ARTE, DOCUMENTO, ESTADO, FOLIO, ANTECEDENTES, JUSTIFICACION, RES_DOCENTE, RES_ALUMNO, INVESTIGADORES"
					+ " FROM ENOC.INV_PROYECTO WHERE ENOC.FACULTAD(CARRERA_ID) IN("+facultades+") "+orden;
			
			rs = st.executeQuery(comando);			
			while (rs.next()){
				
				InvProyecto proyecto = new InvProyecto();				
				proyecto.mapeaReg(rs);
				listProyectos.add(proyecto);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.investiga.InvProyectoUtil|getListProyectosReferente|:"+ex);
		}finally{		
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }	
		}
		
		return listProyectos;
	}
	
	// Filtra los proyectos que pertenezcan a las carreras del refrente
	public ArrayList<InvProyecto> lisProyectosReferente(Connection conn, String referente, String orden ) throws SQLException{
		ArrayList<InvProyecto> listProyectos	= new ArrayList<InvProyecto>();
		Statement st 	= conn.createStatement();
		ResultSet rs 	= null;
		String comando	= "";
		
		try{
			comando = " SELECT PROYECTO_ID, PROYECTO_NOMBRE, CODIGO_PERSONAL, TIPO, LINEA, CARRERA_ID, DEPARTAMENTO,"
					+ " TO_CHAR(FECHA_INICIO,'DD/MM/YYYY') AS FECHA_INICIO, TO_CHAR(FECHA_FINAL,'DD/MM/YYYY') AS FECHA_FINAL,"
					+ " RESUMEN, ESTADO_ARTE, DOCUMENTO, ESTADO, FOLIO, ANTECEDENTES, JUSTIFICACION, RES_DOCENTE, RES_ALUMNO, INVESTIGADORES"
					+ " FROM ENOC.INV_PROYECTO WHERE CARRERA_ID IN(SELECT CARRERA_ID FROM INV_REFERENTE WHERE CODIGO_ID =  "+referente+") "+orden;
			
			rs = st.executeQuery(comando);			
			while (rs.next()){
				
				InvProyecto proyecto = new InvProyecto();				
				proyecto.mapeaReg(rs);
				listProyectos.add(proyecto);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.investiga.InvProyectoUtil|getListProyectosReferente|:"+ex);
		}finally{		
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }	
		}
		
		return listProyectos;
	}
	
	public ArrayList<InvProyecto> getListProyectosCarrera(Connection conn, String carreraId, String codigoId, String orden ) throws SQLException{
		ArrayList<InvProyecto> listProyectos	= new ArrayList<InvProyecto>();
		Statement st 	= conn.createStatement();
		ResultSet rs 	= null;
		String comando	= "";
		
		try{
			comando = " SELECT PROYECTO_ID, PROYECTO_NOMBRE, CODIGO_PERSONAL, TIPO, LINEA, CARRERA_ID, DEPARTAMENTO,"
					+ " TO_CHAR(FECHA_INICIO,'DD/MM/YYYY') AS FECHA_INICIO, TO_CHAR(FECHA_FINAL,'DD/MM/YYYY') AS FECHA_FINAL,"
					+ " RESUMEN, ESTADO_ARTE, DOCUMENTO, ESTADO, FOLIO, ANTECEDENTES, JUSTIFICACION, RES_DOCENTE, RES_ALUMNO, INVESTIGADORES"
					+ " FROM ENOC.INV_PROYECTO WHERE CARRERA_ID IN (SELECT CARRERA_ID FROM ENOC.INV_REFERENTE WHERE CARRERA_ID ='"+carreraId+"'"
							+ " AND CODIGO_ID ='"+codigoId+"' ) "+orden; 
			
			rs = st.executeQuery(comando);			
			while (rs.next()){
				
				InvProyecto proyecto = new InvProyecto();				
				proyecto.mapeaReg(rs);
				listProyectos.add(proyecto);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.investiga.InvProyectoUtil|getListProyectosCarrera|:"+ex);
		}finally{		
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }	
		}
		
		return listProyectos;
	}
}