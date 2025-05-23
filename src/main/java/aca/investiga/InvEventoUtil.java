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
public class InvEventoUtil {
	
	public boolean insertReg(Connection conn, InvEvento evento ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.INV_EVENTO(CODIGO_PERSONAL, FOLIO, PROYECTO_ID, FECHA_SOLICITUD,"
					+ " FECHA_INICIO, LUGAR, TIPO_EVENTO, DIAS, NOMBRE_EVENTO, PARTICIPA, TIPO_BECA, ALUMNOS, HOSPEDAJE, TRANSPORTE,"
					+ " VIATICOS, GASTOS, DESCRIPCION, ESTADO)"
					+ " VALUES(?, TO_NUMBER(?,'999'), ?, TO_DATE(?,'DD/MM/YYYY'), TO_DATE(?,'DD/MM/YYYY'), ?, TO_NUMBER(?,'99'), TO_NUMBER(?,'99') , ?,"
					+ " TO_NUMBER(?,'99'), TO_NUMBER(?, '99'), ?, TO_NUMBER(?,'999999.99'),TO_NUMBER(?, '999999.99'), TO_NUMBER(?, '999999.99'), TO_NUMBER(?, '999999.99'), ?, ?)"); 
			
			ps.setString(1, evento.getCodigoPersonal());
			ps.setString(2, evento.getFolio());
			ps.setString(3, evento.getProyectoId());
			ps.setString(4, evento.getFechaSolicitud());
			ps.setString(5, evento.getFechaInicio());
			ps.setString(6, evento.getLugar());
			ps.setString(7, evento.getTipoEvento());
			ps.setString(8, evento.getDias());
			ps.setString(9, evento.getNombreEvento());
			ps.setString(10, evento.getParticipa());
			ps.setString(11, evento.getTipoBeca());
			ps.setString(12, evento.getAlumnos());
			ps.setString(13, evento.getHospedaje());
			ps.setString(14, evento.getTransporte());
			ps.setString(15, evento.getViaticos());
			ps.setString(16, evento.getGastos());
			ps.setString(17, evento.getDescripcion());
			ps.setString(18, evento.getEstado());

			if ( ps.executeUpdate()== 1){
				ok = true;				
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.investiga.InvEvento|insertReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean updateReg(Connection conn, InvEvento evento ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.INV_EVENTO SET PROYECTO_ID = ?,"
					+ " FECHA_SOLICITUD = TO_DATE(?, 'DD/MM/YYYY'), "
					+ " FECHA_INICIO = TO_DATE(?, 'DD/MM/YYYY'),"
					+ " LUGAR = ?,"
					+ " TIPO_EVENTO = TO_NUMBER(?,'99'),"
					+ " DIAS = TO_NUMBER(?,'99'),"
					+ " NOMBRE_EVENTO = ?,"
					+ " PARTICIPA = TO_NUMBER(?,'99'),"
					+ " TIPO_BECA = TO_NUMBER(?,'99'),"
					+ " ALUMNOS = ?,"
					+ " HOSPEDAJE = TO_NUMBER(?,'999999.99'),"
					+ " TRANSPORTE = TO_NUMBER(?,'999999.99'),"
					+ " VIATICOS = TO_NUMBER(?,'999999.99'),"
					+ " GASTOS = TO_NUMBER(?,'999999.99'),"
					+ " DESCRIPCION = ?,"
					+ " ESTADO = ?"
					+ " WHERE CODIGO_PERSONAL = ? AND FOLIO = TO_NUMBER(?, '999')");
			
			ps.setString(1, evento.getProyectoId());
			ps.setString(2, evento.getFechaSolicitud());
			ps.setString(3, evento.getFechaInicio());
			ps.setString(4, evento.getLugar());
			ps.setString(5, evento.getTipoEvento());
			ps.setString(6, evento.getDias());
			ps.setString(7, evento.getNombreEvento());
			ps.setString(8, evento.getParticipa());
			ps.setString(9, evento.getTipoBeca());
			ps.setString(10, evento.getAlumnos());
			ps.setString(11, evento.getHospedaje());
			ps.setString(12, evento.getTransporte());
			ps.setString(13, evento.getViaticos());
			ps.setString(14, evento.getGastos());
			ps.setString(15, evento.getDescripcion());
			ps.setString(16, evento.getEstado());			
			ps.setString(17, evento.getCodigoPersonal());
			ps.setString(18, evento.getFolio());		
			
			if ( ps.executeUpdate()== 1){
				ok = true;				
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.investiga.InvEvento|updateReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean deleteReg(Connection conn, String codigoPersonal, String folio ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.INV_EVENTO WHERE CODIGO_PERSONAL = ? AND FOLIO = TO_NUMBER(?,'999')"); 
			ps.setString(1, codigoPersonal);
			ps.setString(2, folio);
		
			if ( ps.executeUpdate()== 1){
				ok = true;				
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.investiga.InvEvento|deleteReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}	
	
	public InvEvento mapeaRegId(Connection con, String codigoPersonal, String folio) throws SQLException{
		
		InvEvento evento = new InvEvento();
		PreparedStatement ps = null;
		ResultSet rs = null;	
		try{ 
			ps = con.prepareStatement("SELECT CODIGO_PERSONAL, FOLIO, PROYECTO_ID, TO_CHAR(FECHA_SOLICITUD,'DD/MM/YYYY') AS FECHA_SOLICITUD,"
					+ " TO_CHAR(FECHA_INICIO,'DD/MM/YYYY') AS FECHA_INICIO, LUGAR, TIPO_EVENTO, DIAS, NOMBRE_EVENTO, PARTICIPA, TIPO_BECA, "
					+ " ALUMNOS, HOSPEDAJE, TRANSPORTE, VIATICOS, GASTOS, DESCRIPCION, ESTADO "
					+ " FROM ENOC.INV_EVENTO WHERE CODIGO_PERSONAL = ? AND FOLIO = TO_NUMBER(?,'999')"); 
			ps.setString(1, codigoPersonal);
			ps.setString(2, folio);
			
			rs = ps.executeQuery();
			
			if(rs.next()){
				evento.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.investiga.InvEvento|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return evento;
	}
	
	public boolean existeReg(Connection conn, String codigoPersonal, String folio) throws SQLException{
		boolean 		ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.INV_EVENTO WHERE CODIGO_PERSONAL = ? AND FOLIO = TO_NUMBER(?,'999') "); 
			ps.setString(1,codigoPersonal);
			ps.setString(2,folio);
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error -  aca.investiga.InvEvento|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public static boolean updateEstado(Connection conn, String folio, String codigoPersonal, String estado ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.INV_EVENTO "
					+ " SET ESTADO = ?"					
					+ " WHERE FOLIO = TO_NUMBER(?,'999') "
					+ " AND CODIGO_PERSONAL = ? ");
			
			ps.setString(1,estado);
			ps.setString(2,folio);
			ps.setString(3,codigoPersonal);
			
			if ( ps.executeUpdate()== 1){
				ok = true;				
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.investiga.InvEvento|updateEstado|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public String getAlumnosSol(Connection conn, String codigoPersonal, String folio) throws SQLException{
		String opcion			= " ";
		ResultSet rs			= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT ALUMNOS FROM ENOC.INV_EVENTO WHERE CODIGO_PERSONAL = ? AND FOLIO = TO_NUMBER(?,'999')"); 
			ps.setString(1,codigoPersonal);
			ps.setString(2,folio);
			rs = ps.executeQuery();
			if (rs.next())
				opcion = rs.getString("ALUMNOS");
			
		}catch(Exception ex){
			System.out.println("Error - aca.investiga.InvEvento|getAlumnosSol|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return opcion;
	}
	
	public boolean updateAlumnos(Connection conn, String alumnos, String folio, String codigoPersonal ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.INV_EVENTO "+ 
				" SET ALUMNOS = ? "+
				" WHERE  FOLIO = ?"+ 
				" AND CODIGO_PERSONAL = ? ");
			
			ps.setString(1,alumnos);
			ps.setString(2,folio);
			ps.setString(3,codigoPersonal);
			
			if ( ps.executeUpdate()== 1){
				ok = true;				
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.investiga.InvEvento|updateAlumnos|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public String maxReg(Connection conn, String codigoPersonal) throws SQLException{
		String maximo 			= "1";
		ResultSet rs			= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT COALESCE(MAX(FOLIO)+1,1) AS MAXIMO FROM ENOC.INV_EVENTO WHERE CODIGO_PERSONAL = ?"); 
			ps.setString(1,codigoPersonal);
			rs = ps.executeQuery();
			if (rs.next())
				maximo = rs.getString("MAXIMO");
			
		}catch(Exception ex){
			System.out.println("Error - aca.investiga.InvEvento|maxReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return maximo;
	}
	
	public static String getEstado(Connection conn, String folio, String codigoPersonal ) throws SQLException{
		PreparedStatement ps	= null;
		ResultSet 		rs		= null;		
		String estado 			= "-";
		
		try{
			ps = conn.prepareStatement("SELECT ESTADO FROM ENOC.INV_EVENTO WHERE FOLIO = TO_NUMBER(?,'999') AND CODIGO_PERSONAL = ? ");
			ps.setString(1,folio);
			ps.setString(2,codigoPersonal);
			rs = ps.executeQuery();
			if (rs.next())
				estado = rs.getString("ESTADO");
			
		}catch(Exception ex){
			System.out.println("Error - aca.investiga.InvEvento|getEstado|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return estado;
	}
	

	public ArrayList<InvEvento> getListAll(Connection conn, String orden ) throws SQLException{
		ArrayList<InvEvento> listProyectos	= new ArrayList<InvEvento>();
		Statement st 	= conn.createStatement();
		ResultSet rs 	= null;
		String comando	= "";
		try{
			comando = " SELECT CODIGO_PERSONAL, FOLIO, PROYECTO_ID, TO_CHAR(FECHA_SOLICITUD, 'DD/MM/YYYY') AS FECHA_SOLICITUD,"
					+ " TO_CHAR(FECHA_INICIO,'DD/MM/YYYY') AS FECHA_INICIO, LUGAR, TIPO_EVENTO, DIAS, NOMBRE_EVENTO, "
					+ " PARTICIPA, TIPO_BECA, ALUMNOS, HOSPEDAJE, TRANSPORTE,"
					+ " VIATICOS, GASTOS, DESCRIPCION, ESTADO FROM ENOC.INV_EVENTO "+orden; 
			
			rs = st.executeQuery(comando);			
			while (rs.next()){
				
				InvEvento proyecto = new InvEvento();				
				proyecto.mapeaReg(rs);
				listProyectos.add(proyecto);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.investiga.InvEventoUtil|getListAll|:"+ex);
		}finally{		
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }	
		}
		
		return listProyectos;
	}
	
	public ArrayList<InvEvento> getListEventosEmpleado(Connection conn, String codigoPersonal, String orden ) throws SQLException{
		ArrayList<InvEvento> listEventos	= new ArrayList<InvEvento>();
		Statement st 	= conn.createStatement();
		ResultSet rs 	= null;
		String comando	= "";
		
		try{
			
			comando = " SELECT CODIGO_PERSONAL, FOLIO, PROYECTO_ID, TO_CHAR(FECHA_SOLICITUD, 'DD/MM/YYYY') AS FECHA_SOLICITUD,"
					+ " TO_CHAR(FECHA_INICIO, 'DD/MM/YYYY') AS FECHA_INICIO, LUGAR, TIPO_EVENTO, DIAS, NOMBRE_EVENTO, PARTICIPA, TIPO_BECA, "
					+ " ALUMNOS, HOSPEDAJE, TRANSPORTE, VIATICOS, GASTOS, DESCRIPCION, ESTADO "
					+ " FROM ENOC.INV_EVENTO WHERE CODIGO_PERSONAL = '"+codigoPersonal+"' "+orden; 
			
			rs = st.executeQuery(comando);			
			while (rs.next()){
				
				InvEvento proyecto = new InvEvento();				
				proyecto.mapeaReg(rs);
				listEventos.add(proyecto);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.investiga.InvEventoUtil|getListEventosEmpleado|:"+ex);
		}finally{		
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }	
		}
		
		return listEventos;
	}
	
	public ArrayList<InvEvento> getListProyectosDirectorFac(Connection conn, String facultad, String orden ) throws SQLException{
		ArrayList<InvEvento> listEventos	= new ArrayList<InvEvento>();
		Statement st 	= conn.createStatement();
		ResultSet rs 	= null;
		String comando	= "";
		
		try{
			comando = " SELECT CODIGO_PERSONAL, FOLIO, PROYECTO_ID, TO_CHAR(FECHA_SOLICITUD, 'DD/MM/YYYY') AS FECHA_SOLICITUD,"
					+ " TO_CHAR(FECHA_INICIO, 'DD/MM/YYYY') AS FECHA_INICIO, LUGAR, TIPO_EVENTO, DIAS, NOMBRE_EVENTO, PARTICIPA, TIPO_BECA,"
					+ " ALUMNOS, HOSPEDAJE, TRANSPORTE, VIATICOS, GASTOS, DESCRIPCION, ESTADO"
					+ " FROM ENOC.INV_EVENTO WHERE PROYECTO_ID IN (SELECT PROYECTO_ID FROM ENOC.INV_PROYECTO WHERE ENOC.FACULTAD(CARRERA_ID) = '"+facultad+"') "+orden;
			
			rs = st.executeQuery(comando);			
			while (rs.next()){
				
				InvEvento evento = new InvEvento();				
				evento.mapeaReg(rs);
				listEventos.add(evento);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.investiga.InvProyectoUtil|getListProyectosDirectorFac|:"+ex);
		}finally{		
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }	
		}
		
		return listEventos;
	}
}