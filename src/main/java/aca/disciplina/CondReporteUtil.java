//CLASE DE LA TABLA COND_REPORTE
package aca.disciplina;

import java.sql.*;
import java.util.ArrayList;

public class CondReporteUtil {
	
	public boolean insertReg(Connection conn, CondReporte reporte ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO "+
				"ENOC.COND_REPORTE(IDREPORTE, NOMBRE, TIPO ) "+
				"VALUES( TO_NUMBER(?,'999'), ?, ? ) ");
			ps.setString(1, reporte.getIdReporte());
			ps.setString(2, reporte.getNombre());			
			ps.setString(3, reporte.getTipo());
			
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.disciplina.CondReporte|insertReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public boolean updateReg(Connection conn, CondReporte reporte ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.COND_REPORTE "+ 
				"SET NOMBRE = ?, TIPO = ? "+
				"WHERE IDREPORTE = TO_NUMBER(?,'999')");
			ps.setString(1, reporte.getNombre());
			ps.setString(2, reporte.getTipo());
			ps.setString(3, reporte.getIdReporte());
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.disciplina.CondReporte|updateReg|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public boolean deleteReg(Connection conn, String idReporte ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.COND_REPORTE "+ 
				"WHERE IDREPORTE = TO_NUMBER(?,'999')");
			ps.setString(1, idReporte);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.disciplina.CondReporte|deleteReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public CondReporte mapeaRegId(Connection con, String idReporte) throws SQLException{
		CondReporte reporte = new CondReporte();
		PreparedStatement ps = null;
		ResultSet rs = null;		
		try{
			ps = con.prepareStatement("SELECT IDREPORTE, NOMBRE, TIPO FROM ENOC.COND_REPORTE " + 
					"WHERE IDREPORTE = TO_NUMBER(?,'999') ");				
			ps.setString(1,idReporte);
			rs = ps.executeQuery();
			
			if(rs.next()){
				reporte.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.disciplina.CondReporte|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return reporte;
	}
	
	public boolean existeReg(Connection conn, String idReporte) throws SQLException{
		boolean 		ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.COND_REPORTE WHERE IDREPORTE = TO_NUMBER(?,'999') "); 
			ps.setString(1, idReporte);
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.disciplina.CondReporte|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public String maximoReg(Connection conn) throws SQLException{
		String maximo 			= "1";
		ResultSet rs			= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT MAX(IDREPORTE)+1 MAXIMO FROM ENOC.COND_REPORTE"); 
			rs = ps.executeQuery();
			if (rs.next())
				maximo = rs.getString("MAXIMO");
			
		}catch(Exception ex){
			System.out.println("Error - aca.disciplina.CondReporte|maximoReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return maximo;
	}
	
	public static String nombreReporte(Connection conn, String idReporte) throws SQLException{
		PreparedStatement ps	= null;
		ResultSet rs			= null;		
		String nombre 			= "X";
		
		try{
			ps = conn.prepareStatement("SELECT NOMBRE FROM ENOC.COND_REPORTE WHERE IDREPORTE = TO_NUMBER(?,'999')"); 
			ps.setString(1,idReporte);
			rs = ps.executeQuery();
			if (rs.next())
				nombre = rs.getString("NOMBRE");
			
		}catch(Exception ex){
			System.out.println("Error - aca.disciplina.CondReporte|nombreReporte|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return nombre;
	}
	
	public static String tipoReporte(Connection conn, String idReporte) throws SQLException{
		PreparedStatement ps	= null;
		ResultSet rs			= null;		
		String tipo 			= "X";
		
		try{
			ps = conn.prepareStatement("SELECT TIPO FROM ENOC.COND_REPORTE WHERE IDREPORTE = TO_NUMBER(?,'999')");
			ps.setString(1,idReporte);
			rs = ps.executeQuery();
			if (rs.next())
				tipo = rs.getString("TIPO");
			
		}catch(Exception ex){
			System.out.println("Error - aca.disciplina.CondReporte|tipoReporte|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return tipo;
	}
	
	public static int getElogios(Connection conn, String codigoPersonal) throws SQLException, Exception{
		PreparedStatement ps	= null;
		ResultSet rs			= null;		
		int numExtras			= 0;
		
		try{
			ps 	= conn.prepareStatement("SELECT COUNT(IDREPORTE) AS IDREPORTE FROM ENOC.COND_REPORTE WHERE IDREPORTE IN"
					+ " (SELECT IDREPORTE FROM ENOC.COND_ALUMNO WHERE MATRICULA = ?) AND TIPO = 'C'");
			ps.setString(1, codigoPersonal);
			
			rs = ps.executeQuery();
			if (rs.next()){
				numExtras = rs.getInt("IDREPORTE");
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.disciplina.Condreporte|getElogios|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return numExtras;
	}
	
	public static int getUnidades(Connection conn, String codigoPersonal) throws SQLException, Exception{
		PreparedStatement ps	= null;
		ResultSet rs			= null;		
		int numExtras			= 0;
		
		try{
			ps 	= conn.prepareStatement("SELECT COUNT(IDREPORTE) AS IDREPORTE FROM ENOC.COND_REPORTE WHERE IDREPORTE IN"
					+ " (SELECT IDREPORTE FROM ENOC.COND_ALUMNO WHERE MATRICULA = ?) AND TIPO = 'D'");
			ps.setString(1, codigoPersonal);
			
			rs = ps.executeQuery();
			if (rs.next()){
				numExtras = rs.getInt("IDREPORTE");
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.disciplina.Condreporte|getUnidades|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return numExtras;
	}
	
	public ArrayList<CondReporte> getListAll(Connection conn, String orden ) throws SQLException{
		
		ArrayList<CondReporte> lisReporte		= new ArrayList<CondReporte>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
		
		try{
			comando = "SELECT IDREPORTE, NOMBRE, TIPO FROM ENOC.COND_REPORTE "+ orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				CondReporte reporte = new CondReporte();
				reporte.mapeaReg(rs);
				lisReporte.add(reporte);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.disciplina.CondReporteUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisReporte;
	}
	
	public ArrayList<CondReporte> getLista(Connection conn, String idReporte, String orden ) throws SQLException{
		
		ArrayList<CondReporte> lisReporte 			= new ArrayList<CondReporte>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
		
		try{
			comando = "SELECT IDREPORTE, NOMBRE, TIPO FROM ENOC.COND_REPORTE WHERE IDREPORTE= '"+idReporte+"' "+ orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				CondReporte reporte = new CondReporte();
				reporte.mapeaReg(rs);
				lisReporte.add(reporte);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.disciplina.CondReporteUtil|getLista|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisReporte;
	}
	
	public ArrayList<CondReporte> getListTipo(Connection conn, String orden ) throws SQLException{
		
		ArrayList<CondReporte> lisReporte 			= new ArrayList<CondReporte>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
		
		try{
			comando = "SELECT DISTINCT(R.IDREPORTE), R.NOMBRE, R.TIPO FROM ENOC.COND_REPORTE R, " + 
					"ENOC.COND_ALUMNO A WHERE R.IDREPORTE = A.IDREPORTE AND R.TIPO IN('D','C') "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				CondReporte reporte = new CondReporte();
				reporte.mapeaReg(rs);
				lisReporte.add(reporte);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.disciplina.CondReporteUtil|getListTipo|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisReporte;
	}
}