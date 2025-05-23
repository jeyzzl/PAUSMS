package aca.rotaciones;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

public class RotEspecialidad {

	private String especialidadId;
	private String especialidadNombre;
	private String cursoId;
	private String semanas;
	private String planId;
	
	public RotEspecialidad(){		
		especialidadId		= "";
		especialidadNombre	= "";
		cursoId			 	= "";
		semanas				= "";
		planId				= "";
	}

	public String getEspecialidadId() {
		return especialidadId;
	}

	public void setEspecialidadId(String especialidadId) {
		this.especialidadId = especialidadId;
	}

	public String getEspecialidadNombre() {
		return especialidadNombre;
	}

	public void setEspecialidadNombre(String especialidadNombre) {
		this.especialidadNombre = especialidadNombre;
	}

	public String getCursoId() {
		return cursoId;
	}

	public void setCursoId(String cursoId) {
		this.cursoId = cursoId;
	}

	public String getSemanas() {
		return semanas;
	}

	public void setSemanas(String semanas) {
		this.semanas = semanas;
	}
	
	public String getPlanId() {
		return planId;
	}

	public void setPlanId(String planId) {
		this.planId = planId;
	}

	public boolean insertReg(Connection conn ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.ROT_ESPECIALIDAD(ESPECIALIDAD_ID, ESPECIALIDAD_NOMBRE, " + 
					" CURSO_ID, SEMANAS, PLAN_ID) " +
					" VALUES(TO_NUMBER(?,'999'),?,?,TO_NUMBER(?,'999'),?)");
			ps.setString(1,especialidadId);
			ps.setString(2,especialidadNombre);		
			ps.setString(3,cursoId);
			ps.setString(4,semanas);
			ps.setString(5,planId);
			
			if ( ps.executeUpdate()== 1){
				ok = true;				
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.rotaciones.RotEspecilidad|insertReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean updateReg(Connection conn ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.ROT_ESPECIALIDAD SET ESPECIALIDAD_NOMBRE = ? , " +
					" CURSO_ID =  ?, SEMANAS = ?, PLAN_ID = ? " +
					" WHERE ESPECIALIDAD_ID = ?  ");			
			
			ps.setString(1,especialidadNombre);
			ps.setString(2,cursoId);		
			ps.setString(3,semanas);
			ps.setString(4,planId);
			ps.setString(5,especialidadId);
			
			if ( ps.executeUpdate()== 1){
				ok = true;				
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.rotaciones.RotEspecialidad|updateReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean deleteReg(Connection conn ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.ROT_ESPECIALIDAD WHERE ESPECIALIDAD_ID = ? "); 
			ps.setString(1,especialidadId);		
			if ( ps.executeUpdate()== 1){
				ok = true;				
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.rotaciones.RotEspecialidad|deletetReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		especialidadId  	= rs.getString("ESPECIALIDAD_ID");
		especialidadNombre	= rs.getString("ESPECIALIDAD_NOMBRE");				
		cursoId				= rs.getString("CURSO_ID");
		semanas				= rs.getString("SEMANAS");
		planId				= rs.getString("PLAN_ID");
	}
	
	public void mapeaRegId(Connection con, String especialidadId) throws SQLException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = con.prepareStatement("SELECT ESPECIALIDAD_ID, ESPECIALIDAD_NOMBRE, CURSO_ID, SEMANAS, PLAN_ID " +
					" FROM ENOC.ROT_ESPECIALIDAD WHERE ESPECIALIDAD_ID = ? "); 
			ps.setString(1,especialidadId);
			rs = ps.executeQuery();
			
			if(rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.rotaciones.RotEspecialidad|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
	}
	
	public boolean existeReg(Connection conn) throws SQLException{
		boolean ok 			= false;
		ResultSet rs 			= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.ROT_ESPECIALIDAD WHERE ESPECIALIDAD_ID = ? "); 
			ps.setString(1, especialidadId);
			rs= ps.executeQuery();		
			if(rs.next()){
				ok = true;
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.rotaciones.RotEspecialidad|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public String maximoReg(Connection conn) throws SQLException{
		
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		String maximo			= "1";
		
		try{
			ps = conn.prepareStatement("SELECT (MAX(ESPECIALIDAD_ID)+1) AS MAXIMO FROM ENOC.ROT_ESPECIALIDAD");

			rs = ps.executeQuery();
			if (rs.next())
				maximo = rs.getString("MAXIMO");	
			
		}catch(Exception ex){
			System.out.println("Error - aca.rotaciones.RotEspecialidad|maximoReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return maximo;
	}	
	
	public static String getNombre(Connection conn, String especialidadId) throws SQLException{
		
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		String nombre			= "";
		
		try{
			ps = conn.prepareStatement("SELECT ESPECIALIDAD_NOMBRE FROM ENOC.ROT_ESPECIALIDAD WHERE ESPECIALIDAD_ID = "+especialidadId+" "); 
			rs = ps.executeQuery();
			if (rs.next())
				nombre = rs.getString("ESPECIALIDAD_NOMBRE");	
			
		}catch(Exception ex){
			System.out.println("Error - aca.rotaciones.RotEspecialidad|getNombre|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return nombre;
	}
	
	public static HashMap<String,String> getNombres(Connection conn) throws SQLException{
		
		HashMap<String,String> map	= new HashMap<String, String>();
		Statement st 					    = conn.createStatement();
		ResultSet rs 					    = null;
		String comando	             		= "";
		String llave						= "";
		
		try{
			comando = "SELECT ESPECIALIDAD_ID, ESPECIALIDAD_NOMBRE FROM ENOC.ROT_ESPECIALIDAD ";
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				llave = rs.getString("ESPECIALIDAD_ID");
				map.put(llave, rs.getString("ESPECIALIDAD_NOMBRE"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.rotaciones.RotEspecialidad|getNombres|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return map;
	}
	
	public static String getSemanas(Connection conn, String especialidadId) throws SQLException{
		
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		String nombre			= "";
		
		try{
			ps = conn.prepareStatement("SELECT SEMANAS FROM ENOC.ROT_ESPECIALIDAD WHERE ESPECIALIDAD_ID = "+especialidadId+" "); 
			rs = ps.executeQuery();
			if (rs.next())
				nombre = rs.getString("SEMANAS");	
			
		}catch(Exception ex){
			System.out.println("Error - aca.rotaciones.RotEspecialidad|getSemanas|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return nombre;
	}


}
