// CLASE DE LA TABLA CONV_SOLICITUD
package aca.conva;

import java.sql.*;
import java.util.ArrayList;

public class ConvSolicitudUtil {
	
	
	public boolean insertReg(Connection conn, ConvSolicitud sol ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO CONV_SOLICITUD"+
				"(CODIGO_PERSONAL, CURSO_ID, USUARIO, FECHA, TIPO, PROCESO_ID, NOTA, MATERIA_O, CREDITOS_O, NOTA_O) "+
				"VALUES( ?,?,?, "+				
				"TO_DATE(?,'DD/MM/YYYY'), "+
				"?, ?, ?, ?, TO_NUMBER(?,'99'),? ");
					
			ps.setString(1, sol.getCodigoPersonal());
			ps.setString(2, sol.getCursoId());
			ps.setString(3, sol.getUsuario());
			ps.setString(4, sol.getFecha());
			ps.setString(5, sol.getTipo());			
			ps.setInt(6, Integer.parseInt(sol.getProcesoId()));
			ps.setString(7, sol.getNota());
			ps.setString(8, sol.getMateria_O());
			ps.setString(9, sol.getCreditos_O());
			ps.setString(10, sol.getNota_O());
			
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.conva.ConvSolicitud|insertReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public boolean updateReg(Connection conn, ConvSolicitud sol ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.CONV_SOLICITUD "+
				"SET "+				
				"USUARIO = ?, "+
				"FECHA = TO_DATE(?,'DD/MM/YYYY'), "+
				"TIPO = ?, "+				
				"PROCESO_ID = ?, " +
				"NOTA = ?" +
				"MATERIA_O = ?" +
				"CREDITOS_O = TO_NUMBER(?, '99')" +
				"NOTA_O = ?"+
				"WHERE CODIGO_PERSONAL = ? " +
				"AND CURSO_ID = ? ");		
			
			ps.setString(1, sol.getUsuario());
			ps.setString(2, sol.getFecha());
			ps.setString(3, sol.getTipo());			
			ps.setInt(4, Integer.parseInt(sol.getProcesoId()));
			ps.setString(5, sol.getNota());
			ps.setString(6, sol.getMateria_O());
			ps.setString(7, sol.getCreditos_O());
			ps.setString(8, sol.getNota_O());
			ps.setString(9, sol.getCodigoPersonal());
			ps.setString(10, sol.getCursoId());
			
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.conva.ConvSolicitud|updateReg|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public boolean deleteReg(Connection conn, String codigoPersonal, String cursoId ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.CONV_SOLICITUD "+
				"WHERE CODIGO_PERSONAL = ?  AND CURSO_ID = ?");
			ps.setString(1, codigoPersonal);
			ps.setString(2, cursoId);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.conva.ConvSolicitud|deleteReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public ConvSolicitud mapeaRegId( Connection conn, String codigoPersonal, String cursoId ) throws SQLException{
		
		ConvSolicitud sol = new ConvSolicitud();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement("SELECT "+
				"CODIGO_PERSONAL, CURSO_ID, USUARIO, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, TIPO, PROCESO_ID, CARRERA, NOTA, MATERIA_O, CREDITOS_O, NOTA_O"+
				"FROM ENOC.CONV_SOLICITUD WHERE CODIGO_PERSONAL = ? " +
				"AND CURSO_ID = ? ");
			ps.setString(1, codigoPersonal);
			ps.setString(2, cursoId);
			
			
			rs = ps.executeQuery();
			if (rs.next()){
				sol.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.conva.ConvSolicitud|mapeaRegId|:"+ex);
		}finally{
			if(rs!=null) rs.close();
			if(ps!=null) ps.close();
		}
		
		return sol;
	}
	
	public boolean existeReg(Connection conn, String codigoPersonal, String cursoId ) throws SQLException{
		boolean 		ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT CODIGO_PERSONAL FROM ENOC.CONV_SOLICITUD "+
				"WHERE CODIGO_PERSONAL = ? AND CUROS_ID = ?");
			ps.setString(1, codigoPersonal);
			ps.setString(2, cursoId);
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.conva.ConvSolicitud|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}

	public ArrayList<ConvSolicitud> getListAll(Connection conn, String orden ) throws SQLException{
		
		ArrayList<ConvSolicitud> lisSolicitud		= new ArrayList<ConvSolicitud>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
		
		try{
			comando = "SELECT CODIGO_PERSONAL, CURSO_ID, USUARIO, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, TIPO, PROCESO_ID, NOTA, MATERIA_O, CREDITOS_O, NOTA_O" +					
					"FROM ENOC.CONV_SOLICITUD "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				ConvSolicitud solicitud = new ConvSolicitud();
				solicitud.mapeaReg(rs);
				lisSolicitud.add(solicitud);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.conva.ConvSolicitudUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisSolicitud;
	}
	
	public ArrayList<ConvSolicitud> getList(Connection conn, String codigoPersonal, String orden ) throws SQLException{
		
		ArrayList<ConvSolicitud> lisSolicitud		= new ArrayList<ConvSolicitud>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
		
		try{
			comando = "SELECT CODIGO_PERSONAL, CURSO_ID, USUARIO, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, TIPO, PROCESO_ID, NOTA, MATERIA_O, CREDITOS_O, NOTA_O " +					
					"FROM ENOC.CONV_SOLICITUD WHERE CODIGO_PERSONAL = '"+codigoPersonal+"' "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				ConvSolicitud solicitud = new ConvSolicitud();
				solicitud.mapeaReg(rs);
				lisSolicitud.add(solicitud);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.conva.ConvSolicitudUtil|getList|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisSolicitud;
	}
	
	public ArrayList<ConvSolicitud> getListDistinct(Connection conn, String orden) throws SQLException{
		
		ArrayList<ConvSolicitud> lisSolicitud		= new ArrayList<ConvSolicitud>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
		
		try{
			comando = "SELECT DISTINCT(CODIGO_PERSONAL) CODIGO_PERSONAL, CURSO_ID, USUARIO, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, TIPO, PROCESO_ID, NOTA, MATERIA_O, CREDITOS_O, NOTA_O" +					
					"FROM ENOC.CONV_SOLICITUD "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				ConvSolicitud solicitud = new ConvSolicitud();
				solicitud.mapeaReg(rs);
				lisSolicitud.add(solicitud);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.conva.ConvSolicitudUtil|getListDisctinct|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisSolicitud;
	}
}