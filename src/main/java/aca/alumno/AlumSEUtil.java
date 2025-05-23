// Clase para la tabla de Alum_Academico
package aca.alumno;

import java.sql.*;
import java.util.ArrayList;

public class AlumSEUtil{
	
	public boolean insertReg(Connection conn, AlumSE alumSE ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			
			ps = conn.prepareStatement("INSERT INTO ENOC.ALUM_SE"+ 
				"(CODIGO_PERSONAL, CODIGO_SE,CARGA_ID ,CARRERA_ID ,PLAN_ID, "+
				"GRADO, CICLO, "+
				"GENERO, ESTADO ) "+
				"VALUES( ?, "+
				"?, "+
				"?, "+				
				"?, "+
				"?, "+
				"TO_NUMBER(?,'99'), "+
				"TO_NUMBER(?,'99'), "+				
				"?, "+
				"?)");
					
			ps.setString(1, alumSE.getCodigoPersonal());
			ps.setString(2, alumSE.getCodigoSE());
			ps.setString(3, alumSE.getCargaId());
			ps.setString(4, alumSE.getCarreraId());
			ps.setString(5, alumSE.getPlanId());
			ps.setString(6, alumSE.getGrado());
			ps.setString(7, alumSE.getCiclo());
			ps.setString(8, alumSE.getGenero());
			ps.setString(9, alumSE.getEstado());
						
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumSEUtil|insertReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}	
	
	public boolean updateReg(Connection conn, AlumSE alumSE ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.ALUM_SE "+ 
				"SET "+
				"CODIGO_SE = ?, "+
				"CARRERA_ID = ?, "+
				"PLAN_ID = ?, "+
				"GRADO = TO_NUMBER(?,'99'), "+
				"CICLO = TO_NUMBER(?,'99'), "+
				"GENERO = ?, "+
				"ESTADO = ?, "+
				"WHERE CODIGO_PERSONAL = ? AND CARGA_ID = ? ");
			
			ps.setString(1, alumSE.getCodigoSE());
			ps.setString(2, alumSE.getCarreraId());
			ps.setString(3, alumSE.getPlanId());
			ps.setString(4, alumSE.getGrado());
			ps.setString(5, alumSE.getCiclo());
			ps.setString(6, alumSE.getGenero());
			ps.setString(7, alumSE.getEstado());
			ps.setString(8, alumSE.getCodigoPersonal());
			ps.setString(9, alumSE.getCargaId());
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumSEUtil|updateReg|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean existeReg(Connection conn, String codigoPersonal, String cargaId) throws SQLException{
		boolean 		ok 	= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.ALUM_SE "+ 
				"WHERE CODIGO_PERSONAL = ? AND CARGA_ID = ?");
			ps.setString(1, codigoPersonal);
			ps.setString(2, cargaId);
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumSEUtil|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean deleteReg(Connection conn, String codigoPersonal, String cargaId ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.ALUM_SE "+ 
				"WHERE CODIGO_PERSONAL = ? AND CARGA_ID = ?");
			ps.setString(1, codigoPersonal);
			ps.setString(2, cargaId);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumSEUtil|deleteReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}

	public ArrayList<AlumSE> getListAll(Connection conn, String orden ) throws SQLException{
		
		ArrayList<AlumSE> lisSE= new ArrayList<AlumSE>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando		= "";
		
		try{
			comando = "SELECT "+
				"CODIGO_PERSONAL, CODIGO_SE, CARGA_ID, CARRERA_ID, PLAN_ID, "+
				"GRADO, CICLO, GENERO, ESTADO, "+
				"FROM ENOC.ALUM_SE"+orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				AlumSE se = new AlumSE();
				se.mapeaReg(rs);
				lisSE.add(se);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumSEUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisSE	;
	}
	
	public ResultSet getInscritos(Connection conn, String script ) throws SQLException{
		
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		
		try{			
			rs = st.executeQuery(script);
			
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AcademicoUtil|getInscritos|:"+ex);
		}finally{
			//Aqui no se debe cerrar ni el statement ni el resultset
		}
		return rs;
	}
	
}