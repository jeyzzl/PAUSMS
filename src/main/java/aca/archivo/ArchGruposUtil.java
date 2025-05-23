//Clase  para la tabla ARCH_DOCALUM
package aca.archivo;

import java.sql.*;
import java.util.ArrayList;

public class ArchGruposUtil {
	
	public boolean insertReg(Connection conn, ArchGrupos grupo) throws SQLException {
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement ("INSERT INTO ENOC.ARCH_GRUPOS(GRUPO, " + 
					"IDDOCUMENTO,IDSTATUS) VALUES(TO_NUMBER(?,'999'),TO_NUMBER(?,'999'),TO_NUMBER(?,'99'))");
			
			ps.setString(1, grupo.getGrupo());
			ps.setString(2, grupo.getIdDocumento());
			ps.setString(3, grupo.getIdStatus());
						
			if ( ps.executeUpdate()== 1){
				ok = true;				
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.ArchGruposUtil|insertReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}

	public boolean updateReg(Connection conn, ArchGrupos grupo ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.ARCH_GRUPOS" + 
					" SET IDSTATUS = TO_NUMBER(?,'999')" +
					" WHERE GRUPO = TO_NUMBER(?,'999')" +
					" AND IDDOCUMENTO = TO_NUMBER(?,'99')");			
			ps.setString(1, grupo.getIdStatus());
			ps.setString(2, grupo.getGrupo());
			ps.setString(3, grupo.getIdDocumento());
						
			if ( ps.executeUpdate()== 1){
				ok = true;				
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.ArchGruposUtil|updateReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}

	public boolean deleteReg(Connection conn, String grupo, String idDocumento ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.ARCH_GRUPOS" + 
					" WHERE GRUPO = TO_NUMBER(?,'999')" +
					" AND IDDOCUMENTO = TO_NUMBER(?,'999')");
			ps.setString(1, grupo);
			ps.setString(2, idDocumento);
			if ( ps.executeUpdate()== 1){
				ok = true;				
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.ArchGruposUtil|deleteReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public ArchGrupos mapeaRegId(Connection con, String grupo, String idDocumento, String idStatus) throws SQLException{
		
		ArchGrupos archGrup = new ArchGrupos();
		PreparedStatement ps = null;
		ResultSet rs = null;		
		try{
			ps = con.prepareStatement("SELECT GRUPO, IDDOCUMENTO " +
					"IDSTATUS, FROM ENOC.ARCH_GRUPOS"); 
			ps.setString(1,grupo);
			ps.setString(2,idDocumento);
			ps.setString(3,idStatus);
			rs = ps.executeQuery();
			
			if(rs.next()){
				archGrup.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.ArchGruposUtil|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return archGrup;
	}

	public boolean existeReg(Connection conn, String grupo, String idDocumento, String idStatus) throws SQLException{
		boolean ok 			= false;
		ResultSet rs 			= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.ARCH_GRUPOS WHERE GRUPO = TO_NUMBER(?,'999') AND IDDOCUMENTO = TO_NUMBER(?,'999') AND IDSTATUS = TO_NUMBER(?,'99')"); 
			ps.setString(1,grupo);
			ps.setString(2,idDocumento);
			ps.setString(3,idStatus);
			rs= ps.executeQuery();		
			if(rs.next()){
				ok = true;
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.ArchGruposUtil|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}

	public boolean existeRegSinStatus(Connection conn, String grupo, String idDocumento) throws SQLException{
		boolean ok 			= false;
		ResultSet rs 			= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.ARCH_GRUPOS" + 
					" WHERE GRUPO = TO_NUMBER(?,'999')" +
					" AND IDDOCUMENTO = TO_NUMBER(?,'999')");
			ps.setString(1,grupo);
			ps.setString(2,idDocumento);
			rs= ps.executeQuery();		
			if(rs.next()){
				ok = true;
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.ArchGruposUtil|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}

	public static String maxReg(Connection conn) throws SQLException{
		ResultSet rs 			= null;
		PreparedStatement ps	= null;
		String maximo			= "X";
		
		try{
			ps = conn.prepareStatement("SELECT COALESCE(MAX(GRUPO)+1, 1) AS MAXIMO FROM ENOC.ARCH_GRUPOS"); 
			
			rs = ps.executeQuery();		
			if(rs.next()){
				maximo = rs.getString("MAXIMO");
			}
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.ArchGruposUtil|maxReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return maximo;
	}

	public ArrayList<ArchGrupos> getListAll(Connection conn, String orden ) throws SQLException{
		
		ArrayList<ArchGrupos> lisArchGrupos 	= new ArrayList<ArchGrupos>();
		Statement st 	= conn.createStatement();
		ResultSet rs 	= null;
		String comando	= "";
		
		try{
			comando = "SELECT GRUPO, IDDOCUMENTO, IDSTATUS" +
					" FROM ENOC.ARCH_GRUPOS "+orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				ArchGrupos documento = new ArchGrupos();
				documento.mapeaReg(rs);
				lisArchGrupos.add(documento);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.ArchGruposUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisArchGrupos;
	}
	
	public ArrayList<ArchGrupos> getListGrupo(Connection conn, String grupoId, String orden ) throws SQLException{
		
		ArrayList<ArchGrupos> lisArchGrupos 	= new ArrayList<ArchGrupos>();
		Statement st 	= conn.createStatement();
		ResultSet rs 	= null;
		String comando	= "";
		
		try{
			comando = "SELECT GRUPO, IDDOCUMENTO, IDSTATUS" +
					" FROM ENOC.ARCH_GRUPOS" + 
					" WHERE GRUPO = "+grupoId+" "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				ArchGrupos documento = new ArchGrupos();
				documento.mapeaReg(rs);
				lisArchGrupos.add(documento);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.ArchGruposUtil|getListGrupo|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisArchGrupos;
	}
	
	public ArrayList<ArchGrupos> getListGrupoCarrera(Connection conn, String carreraId, String orden ) throws SQLException{
		
		ArrayList<ArchGrupos> lisArchGrupos 	= new ArrayList<ArchGrupos>();
		Statement st 	= conn.createStatement();
		ResultSet rs 	= null;
		String comando	= "";
		
		try{
			comando = " SELECT GRUPO, IDDOCUMENTO, IDSTATUS"
					+ " FROM ENOC.ARCH_GRUPOS"
					+ " WHERE (SELECT REPLACE(GRUPOS,' ','-')||'-' FROM ENOC.ARCH_GRUPOS_CARRERA WHERE CARRERA =  '"+carreraId+"') LIKE '%-'||GRUPO||'-%' " +orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				ArchGrupos documento = new ArchGrupos();
				documento.mapeaReg(rs);
				lisArchGrupos.add(documento);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.archivo.ArchGruposUtil|getListGrupoCarrera|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisArchGrupos;
	}
}