package aca.portafolio;

import java.sql.*;

public class PorJefes {

	private String codigoPersonal;
	private String ejercicioId;	
	private String departamentos;
	private String estado;
	
	public PorJefes(){
		codigoPersonal 		= "";
		ejercicioId			= "";		
		departamentos		= "";
		estado				= "";
	}
	
	public String getCodigoPersonal() {
		return codigoPersonal;
	}

	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
	}

	public String getEjercicioId() {
		return ejercicioId;
	}

	public void setEjercicioId(String ejercicioId) {
		this.ejercicioId = ejercicioId;
	}

	public String getDepartamentos() {
		return departamentos;
	}

	public void setDepartamentos(String departamentos) {
		this.departamentos = departamentos;
	}
	
	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public boolean insertReg(Connection conn ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO "+
				"DANIEL.POR_JEFES( CODIGO_PERSONAL, EJERCICIO_ID, DEPARTAMENTOS,  ESTADO) "+
				"VALUES( ?, ?, ?, ?)");
			ps.setString(1, codigoPersonal);
			ps.setString(2, ejercicioId);
			ps.setString(3, departamentos);			
			ps.setString(4, estado);
			
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.portafolio.PorJefes|insertReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public boolean updateReg(Connection conn ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE DANIEL.POR_JEFES "+ 
				"SET EJERCICIO_ID = ?, DEPARTAMENTOS = ?, ESTADO = ? WHERE CODIGO_PERSONAL = ? ");
			ps.setString(1, ejercicioId);
			ps.setString(2, departamentos);			
			ps.setString(3, estado);
			ps.setString(4, codigoPersonal);
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.portafolio.PorJefes|updateReg|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	
	public boolean deleteReg(Connection conn ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM DANIEL.POR_JEFES "+ 
				"WHERE CODIGO_PERSONAL = ?");
			ps.setString(1, codigoPersonal);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.portafolio.PorJefes|deleteReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		codigoPersonal 		= rs.getString("CODIGO_PERSONAL");
		ejercicioId 		= rs.getString("EJERCICIO_ID");		
		departamentos 		= rs.getString("DEPARTAMENTOS");
		estado				= rs.getString("ESTADO");
	}
	
	public void mapeaRegId( Connection conn, String codigoPersonal) throws SQLException{
		ResultSet rs = null;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("SELECT CODIGO_PERSONAL, EJERCICIO_ID, DEPARTAMENTOS, ESTADO FROM DANIEL.POR_JEFES WHERE CODIGO_PERSONAL = ?"); 
			ps.setString(1,codigoPersonal);
			rs = ps.executeQuery();
			if (rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.portafolio.PorJefes|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
	}
	
	public boolean existeReg(Connection conn) throws SQLException{
		boolean 		ok 		= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM DANIEL.POR_JEFES WHERE CODIGO_PERSONAL = ?"); 
			ps.setString(1,codigoPersonal);
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.portafolio.PorJefes|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
}