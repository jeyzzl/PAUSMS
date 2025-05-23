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

/**
 * @author elifo
 *
 */
public class HcaMaestroUtil {
	
	public boolean insertReg(Connection conn, HcaMaestro hcaMaestro) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.HCA_MAESTRO(CODIGO_PERSONAL, FACULTAD_ID, CARRERA_ID, ESTADO)" +
				" VALUES(?, ?, ?, ?)");
			
			ps.setString(1, hcaMaestro.getCodigoPersonal());
			ps.setString(2, hcaMaestro.getFacultadId());			
			ps.setString(3, hcaMaestro.getCarreraId());
			ps.setString(4, hcaMaestro.getEstado());
			
			if(ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.hca.HcaMaestroUtil|insertReg|:"+ex);			
		}finally{
			if(ps!=null) ps.close();
		}		
		return ok;
	}	
	
	public boolean updateReg(Connection conn, HcaMaestro hcaMaestro) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.HCA_MAESTRO" + 
				" SET FACULTAD_ID = ?," +
				" CARRERA_ID = ?," +
				" ESTADO = ?" +
				" WHERE CODIGO_PERSONAL = ?");
			
			ps.setString(1, hcaMaestro.getFacultadId());
			ps.setString(2, hcaMaestro.getCarreraId());
			ps.setString(3, hcaMaestro.getEstado());
			ps.setString(4, hcaMaestro.getCodigoPersonal());
						
			if(ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.hca.HcaMaestroUtil|updateReg|:"+ex);		
		}finally{
			if(ps!=null) ps.close();
		}		
		return ok;
	}	
	
	public boolean deleteReg(Connection conn, String codigoPersonal) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.HCA_MAESTRO"+ 
				" WHERE CODIGO_PERSONAL = ?");
			
			ps.setString(1, codigoPersonal);
			
			if(ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.hca.HcaMaestroUtil|deleteReg|:"+ex);			
		}finally{
			if(ps!=null) ps.close();
		}
		return ok;
	}
	
	public HcaMaestro mapeaRegId(Connection con, String codigoPersonal) throws SQLException{
		HcaMaestro hcaMaestro = new HcaMaestro();
		PreparedStatement ps = null;
		ResultSet rs = null;		
		try{ 
		ps = con.prepareStatement("SELECT CODIGO_PERSONAL," +
				" FACULTAD_ID, CARRERA_ID, ESTADO FROM ENOC.HCA_MAESTRO" + 
				" WHERE CODIGO_PERSONAL = ?");
		
		ps.setString(1, codigoPersonal);
		
		rs = ps.executeQuery();
		
		if(rs.next()){
			
			hcaMaestro.mapeaReg(rs);
		}
		}catch(Exception ex){
			System.out.println("Error - aca.hca.HcaMaestroUtil|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}	
		return hcaMaestro;
	}
	
	public boolean existeReg(Connection conn, String codigoPersonal) throws SQLException{
		boolean 		ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.HCA_MAESTRO" + 
					" WHERE CODIGO_PERSONAL = ?");
			
			ps.setString(1, codigoPersonal);
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.hca.HcaMaestroUtil|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}			
		return ok;
	}
	
	public static String getEmpFacBase(Connection conn, String codigoPersonal) throws SQLException{
		
		PreparedStatement ps	= null;
		ResultSet 		rs		= null;
		String fac				= "xxx";
		
		try{
			ps = conn.prepareStatement("SELECT FACULTAD_ID FROM ENOC.HCA_MAESTRO" + 
					" WHERE CODIGO_PERSONAL = ? AND ESTADO = 'B'");	
			
			ps.setString(1, codigoPersonal);
			
			rs = ps.executeQuery();
			if (rs.next())
				fac = rs.getString("FACULTAD_ID");			
			
		}catch(Exception ex){
			System.out.println("Error - aca.hca.HcaMaestroUtil||getEmpFacBase:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}		
		return fac;
	}
	
	public static String getEmpCarreraBase(Connection conn, String codigoPersonal) throws SQLException{
		
		PreparedStatement ps	= null;
		ResultSet 		rs		= null;
		String carrera			= "xxxxx";
		
		
		try{
			ps = conn.prepareStatement("SELECT CARRERA_ID FROM ENOC.HCA_MAESTRO" + 
					" WHERE CODIGO_PERSONAL = ? AND ESTADO = 'B'");	
			
			ps.setString(1, codigoPersonal);
			
			rs = ps.executeQuery();
			if (rs.next())
				carrera = rs.getString("CARRERA_ID");			
			
		}catch(Exception ex){
			System.out.println("Error - aca.hca.HcaMaestroUtil||getEmpCarreraBase:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}	
		return carrera;
	}
	
	public static String numMaeReg(Connection conn, String cargaId, String facultadId) throws SQLException{
		
		PreparedStatement ps	= null;
		ResultSet 		rs		= null;
		String total			= "0";

		try{
			ps = conn.prepareStatement("SELECT COUNT(CODIGO_PERSONAL) AS TOTAL FROM ENOC.HCA_MAESTRO" 
					+ " WHERE CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM ENOC.CARGA_GRUPO WHERE CARGA_ID = ? AND ENOC.FACULTAD(CARRERA_ID) = ?)");
			
			ps.setString(1, cargaId);
			ps.setString(2, facultadId);
			
			rs = ps.executeQuery();
			if (rs.next())
				total = rs.getString("TOTAL");			
			
		}catch(Exception ex){
			System.out.println("Error - aca.hca.HcaMaestroUtil||numMaeReg:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}	
		return total;
	}
	
	public static String numMaeRegFac(Connection conn, String cargaId, String facultadId) throws SQLException{
		
		PreparedStatement ps	= null;
		ResultSet 		rs		= null;
		String total			= "0";
		
		try{
			ps = conn.prepareStatement("SELECT COUNT(CODIGO_PERSONAL) AS TOTAL FROM ENOC.HCA_MAESTRO" 
					+ " WHERE ENOC.FACULTAD(CARRERA_ID) = ?"
					+ " AND CODIGO_PERSONAL IN "
					+ "(SELECT CODIGO_PERSONAL FROM ENOC.CARGA_GRUPO WHERE CARGA_ID = ? AND ENOC.FACULTAD(CARRERA_ID) = ?)");
			
			ps.setString(1, facultadId);
			ps.setString(2, cargaId);
			ps.setString(3, facultadId);
			
			rs = ps.executeQuery();
			if (rs.next())
				total = rs.getString("TOTAL");			
			
		}catch(Exception ex){
			System.out.println("Error - aca.hca.HcaMaestroUtil||numMaeRegFac:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}	
		return total;
	}
	
	public ArrayList<HcaMaestro> getListAll(Connection conn, String orden ) throws SQLException{
		
		ArrayList<HcaMaestro> lisMaestro	= new ArrayList<HcaMaestro>();
		Statement st 						= conn.createStatement();
		ResultSet rs 						= null;
		String comando						= "";
		
		try{
			comando = "SELECT CODIGO_PERSONAL, FACULTAD_ID, CARRERA_ID, ESTADO " +
					"FROM ENOC.HCA_MAESTRO " +orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				HcaMaestro maestro = new HcaMaestro();
				maestro.mapeaReg(rs);
				lisMaestro.add(maestro);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.hca.HcaMaestroUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisMaestro;
	}
	
	public ArrayList<HcaMaestro> getListFacultad(Connection conn, String facultadId, String orden ) throws SQLException{
		
		ArrayList<HcaMaestro> lisMaestro	= new ArrayList<HcaMaestro>();
		Statement st 					= conn.createStatement();
		ResultSet rs 					= null;
		String comando					= "";
		
		try{
			comando = "SELECT CODIGO_PERSONAL, FACULTAD_ID, CARRERA_ID, ESTADO " +
					"FROM ENOC.HCA_MAESTRO WHERE FACULTAD_ID = '"+facultadId+"' " +orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				HcaMaestro maestro = new HcaMaestro();
				maestro.mapeaReg(rs);
				lisMaestro.add(maestro);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.hca.HcaMaestroUtil|getListFacultad|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisMaestro;
	}

	public ArrayList<HcaMaestro> getListAcceso(Connection conn, String codigoPersonal, String orden) throws SQLException{
		
		ResultSet rs 					= null;
		Statement st 					= conn.createStatement();
		ArrayList<HcaMaestro> lisProfesor 	= new ArrayList<HcaMaestro>();
		String Comando					= "";
		
		try{
			Comando="SELECT CODIGO_PERSONAL, FACULTAD_ID, CARRERA_ID, ESTADO" +
					" FROM ENOC.HCA_MAESTRO" + 
					" WHERE (SELECT ACCESOS" +
							" FROM ENOC.ACCESO" + 
							" WHERE CODIGO_PERSONAL = '"+codigoPersonal+"') LIKE '%'||CARRERA_ID||'%' "+orden;
			rs= st.executeQuery(Comando);		
			
			while(rs.next()){
				HcaMaestro maestro = new HcaMaestro();
				maestro.mapeaReg(rs);
				lisProfesor.add(maestro);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.hca.HcaMaestroUtil|getListAcceso|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisProfesor;
	}
	
}