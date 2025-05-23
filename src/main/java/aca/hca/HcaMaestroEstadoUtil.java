/**
 * 
 */
package aca.hca;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class HcaMaestroEstadoUtil {
	
	public boolean insertReg(Connection conn, HcaMaestroEstado hcaMaestroEstado) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.HCA_MAESTRO_ESTADO" +
				"(CODIGO_PERSONAL, CARGA_ID, T_SEMANAL, T_SEMESTRAL, ESTADO)" +
				" VALUES(?, ?, TO_NUMBER(?, '9999.99'), TO_NUMBER(?, '9999.99'), TO_NUMBER(?, '9'))");
			
			ps.setString(1, hcaMaestroEstado.getCodigoPersonal());
			ps.setString(2, hcaMaestroEstado.getCargaId());			
			ps.setString(3, hcaMaestroEstado.getTSemanal());
			ps.setString(4, hcaMaestroEstado.getTSemestral());
			ps.setString(5, hcaMaestroEstado.getEstado());
			
			if(ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.hca.HcaMaestroEstadoUtil|insertReg|:"+ex);			
		}finally{
			if(ps!=null) ps.close();
		}		
		return ok;
	}	
	
	public boolean updateReg(Connection conn, HcaMaestroEstado hcaMaestroEstado) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.HCA_MAESTRO_ESTADO" + 
				" SET T_SEMANAL = TO_NUMBER(?, '9999.99')," +
				" T_SEMESTRAL = TO_NUMBER(?, '9999.99')," +
				" ESTADO = TO_NUMBER(?, '9')" +
				" WHERE CODIGO_PERSONAL = ?" +
				" AND CARGA_ID = ?");
			
			ps.setString(1, hcaMaestroEstado.getTSemanal());
			ps.setString(2, hcaMaestroEstado.getTSemestral());
			ps.setString(3, hcaMaestroEstado.getEstado());
			ps.setString(4, hcaMaestroEstado.getCodigoPersonal());
			ps.setString(5, hcaMaestroEstado.getCargaId());
						
			if(ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.hca.HcaMaestroEstadoUtil|updateReg|:"+ex);		
		}finally{
			if(ps!=null) ps.close();
		}		
		return ok;
	}	
	
	public boolean deleteReg(Connection conn, String codigoPersonal, String cargaId) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.HCA_MAESTRO_ESTADO"+ 
				" WHERE CODIGO_PERSONAL = ?" +
				" AND CARGA_ID = ?");
			
			ps.setString(1, codigoPersonal);
			ps.setString(2, cargaId);
			
			if(ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.hca.HcaMaestroEstadoUtil|deleteReg|:"+ex);			
		}finally{
			if(ps!=null) ps.close();
		}
		return ok;
	}
	
	public HcaMaestroEstado mapeaRegId(Connection con, String codigoPersonal, String cargaId) throws SQLException{
		HcaMaestroEstado hcaMaestroEstado = new HcaMaestroEstado();
		PreparedStatement ps = null;
		ResultSet rs = null;		
		try{
			ps = con.prepareStatement("SELECT CODIGO_PERSONAL," +
					" CARGA_ID, T_SEMANAL, T_SEMESTRAL, ESTADO FROM ENOC.HCA_MAESTRO_ESTADO" + 
					" WHERE CODIGO_PERSONAL = ?" +
					" AND CARGA_ID = ?");
			
			ps.setString(1, codigoPersonal);
			ps.setString(2, cargaId);
			
			rs = ps.executeQuery();
			
			if(rs.next()){
				
				hcaMaestroEstado.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.hca.HcaMaestroEstadoUtil|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}	
		return hcaMaestroEstado;
	}
	
	public boolean existeReg(Connection conn, String codigoPersonal, String cargaId) throws SQLException{
		boolean 		ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.HCA_MAESTRO_ESTADO" + 
					" WHERE CODIGO_PERSONAL = ?" +
					" AND CARGA_ID = ?");
			
			ps.setString(1, codigoPersonal);
			ps.setString(2, cargaId);
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.hca.HcaMaestroEstadoUtil|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}			
		return ok;
	}
	
	public static int getEstadoCargaDocente(Connection conn, String codigoPersonal, String cargaId) throws SQLException{
		
		PreparedStatement ps	= null;
		ResultSet 		rs		= null;
		int estado 				= 1; 
		
		try{
			ps = conn.prepareStatement("SELECT COALESCE(ESTADO,1) AS EDO FROM ENOC.HCA_MAESTRO_ESTADO" + 
					" WHERE CODIGO_PERSONAL = ?" +
					" AND CARGA_ID = ?");
			
			ps.setString(1, codigoPersonal);
			ps.setString(2, cargaId);
			
			rs = ps.executeQuery();
			if (rs.next())
				estado = rs.getInt("EDO");		
			
		}catch(Exception ex){
			System.out.println("Error - aca.hca.HcaMaestroEstadoUtil|getEstadoCargaDocente|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return estado;
	}
	
	public ArrayList<HcaMaestroEstado> getListAll(Connection conn, String orden ) throws SQLException{
		
		ArrayList<HcaMaestroEstado> lisMaestro	= new ArrayList<HcaMaestroEstado>();
		Statement st 						= conn.createStatement();
		ResultSet rs 						= null;
		String comando						= "";
		
		try{
			comando = "SELECT CODIGO_PERSONAL, CARGA_ID, T_SEMANAL, T_SEMESTRAL, ESTADO " +
					"FROM ENOC.HCA_MAESTRO_ESTADO " +orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				HcaMaestroEstado maestro = new HcaMaestroEstado();
				maestro.mapeaReg(rs);
				lisMaestro.add(maestro);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.hca.HcaMaestroEstadoUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return lisMaestro;
	}
	
	public HashMap<String, HcaMaestroEstado> getMapAll(Connection conn, String orden ) throws SQLException{
		
		HashMap<String, HcaMaestroEstado> mapaMaestro	= new HashMap<String, HcaMaestroEstado>();
		Statement st 						= conn.createStatement();
		ResultSet rs 						= null;
		String comando						= "";
		
		try{
			comando = "SELECT CODIGO_PERSONAL, CARGA_ID, T_SEMANAL, T_SEMESTRAL, ESTADO " +
					"FROM ENOC.HCA_MAESTRO_ESTADO " +orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				HcaMaestroEstado maestro = new HcaMaestroEstado();
				maestro.mapeaReg(rs);
				mapaMaestro.put(maestro.getCodigoPersonal(), maestro);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.hca.HcaMaestroEstadoUtil|getMapAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return mapaMaestro;
	}
}