package aca.alumno;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class AlumEstudioUtil {
	
	public boolean insertReg(Connection conn, AlumEstudio alumEstudio ) throws Exception{
		boolean ok 				= false;
		PreparedStatement ps 	= null;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.ALUM_ESTUDIO"+ 
				"(CODIGO_PERSONAL, ID, TITULO, INSTITUCION, COMPLETO, INICIO, FIN, DEPENDENCIA, CONVALIDA)"+
				" VALUES(?, TO_NUMBER(?, '99'), ?, ?, ?, ?, ?, ?, ?)");
					
			ps.setString(1,	alumEstudio.getCodigoPersonal());
			ps.setString(2, alumEstudio.getId());
			ps.setString(3, alumEstudio.getTitulo());
			ps.setString(4,	alumEstudio.getInstitucion());
			ps.setString(5, alumEstudio.getCompleto());
			ps.setString(6, alumEstudio.getInicio());
			ps.setString(7,	alumEstudio.getFin());
			ps.setString(8, alumEstudio.getDependencia());
			ps.setString(9, alumEstudio.getConvalida());

			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumEstudioUtil|insertReg|:"+ex);	
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}	
	
	public boolean updateReg(Connection conn, AlumEstudio alumEstudio ) throws Exception{
		boolean ok 				= false;
		PreparedStatement ps 	= null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.ALUM_ESTUDIO"
					+ " SET TITULO = ?,"
					+ " INSTITUCION = ?,"
					+ " COMPLETO = ?," 		
					+ " INICIO = ?,"
					+ " FIN = ?,"
					+ " DEPENDENCIA = ?," 		
					+ " CONVALIDA = ?"
					+ " WHERE CODIGO_PERSONAL = ? AND ID = TO_NUMBER(?, '99')");
			
			ps.setString(1, alumEstudio.getTitulo());
			ps.setString(2,	alumEstudio.getInstitucion());
			ps.setString(3, alumEstudio.getCompleto());
			ps.setString(4, alumEstudio.getInicio());
			ps.setString(5,	alumEstudio.getFin());
			ps.setString(6, alumEstudio.getDependencia());
			ps.setString(7, alumEstudio.getConvalida());
			ps.setString(8,	alumEstudio.getCodigoPersonal());
			ps.setString(9, alumEstudio.getId());
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumEstudioUtil|updateReg|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean deleteReg(Connection conn, String codigoPersonal, String id) throws Exception{
		boolean ok 				= false;
		PreparedStatement ps 	= null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.ALUM_ESTUDIO"+ 
				" WHERE CODIGO_PERSONAL = ? AND ID = TO_NUMBER(?, '99')");
			
			ps.setString(1,	codigoPersonal);
			ps.setString(2, id);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumEstudioUtil|deleteReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public AlumEstudio mapeaRegId( Connection conn, String codigoPersonal, String id) throws SQLException{
		ResultSet rs 			= null;
		PreparedStatement ps 	= null; 
		AlumEstudio alumEstudio 			= new AlumEstudio();
		try{
			ps = conn.prepareStatement(" SELECT CODIGO_PERSONAL, ID, TITULO, INSTITUCION, COMPLETO, INICIO, FIN, DEPENDENCIA, CONVALIDA "
									 + " FROM ENOC.ALUM_ESTUDIO WHERE CODIGO_PERSONAL = "+codigoPersonal+" AND ID = "+id); 
			rs = ps.executeQuery();
			if (rs.next()){
				alumEstudio.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.alumno.AlumEstudioUtil|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return alumEstudio;
	}
	
	public String maximoReg(Connection conn) throws SQLException{
		
		String maximo			= "1";
		ResultSet rs			= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement ("SELECT COALESCE(MAX(ID)+1,1) AS MAXIMO FROM ENOC.ALUM_ESTUDIO"); 
			rs = ps.executeQuery();
			if (rs.next())
				maximo = rs.getString("MAXIMO");
				
		}catch(Exception ex){
			System.out.println("Error -  aca.alumno.AlumEstudioUtil|maximoReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return maximo;
	}
	
	public boolean existeReg(Connection conn, String codigoPersonal, String id) throws SQLException{
		
		PreparedStatement ps	= null;
		ResultSet rs			= null;
		boolean ok 				= false;
		
		try{
			ps = conn.prepareStatement ("SELECT * FROM ENOC.ALUM_ESTUDIO WHERE CODIGO_PERSONAL = ? AND ID = TO_NUMBER(?,'99')");
			ps.setString(1,	codigoPersonal);
			ps.setString(2, id);
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			
		}catch(Exception ex){
			System.out.println("Error -  aca.alumno.AlumEstudioUtil|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public ArrayList<AlumEstudio> lisAlumEstudio(Connection conn, String codigoPersonal) throws SQLException{
		ArrayList<AlumEstudio> lisEstudios 	= new ArrayList<AlumEstudio>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando				= "";
		
		try{
			comando = " SELECT CODIGO_PERSONAL, ID, TITULO, INSTITUCION, COMPLETO, INICIO, FIN, DEPENDENCIA, CONVALIDA"
					 + " FROM ENOC.ALUM_ESTUDIO WHERE CODIGO_PERSONAL = "+codigoPersonal;
			rs = st.executeQuery(comando);
			while (rs.next()){
				AlumEstudio estudio = new AlumEstudio();
				estudio.mapeaReg(rs);
				lisEstudios.add(estudio);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.bitacora.BitTramiteAlumnoUtil|lisAlumEstudio|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return lisEstudios;
	}

}
