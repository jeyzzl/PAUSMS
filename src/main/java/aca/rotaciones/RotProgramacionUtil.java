package aca.rotaciones;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class RotProgramacionUtil {
	public ArrayList<RotProgramacion> getListAll(Connection conn, String orden ) throws SQLException{
			
		ArrayList<RotProgramacion> list	= new ArrayList<RotProgramacion>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando				= "";
		
		try{
			comando = "SELECT PROGRAMACION_ID, HOSPITAL_ID, ESPECIALIDAD_ID, CODIGO_PERSONAL," +
					"	TO_CHAR(F_INICIO,'DD/MM/YYYY') AS F_INICIO, TO_CHAR(F_FINAL,'DD/MM/YYYY') AS F_FINAL, INSCRIPCION," +
					"	PAGO, ANUAL, INTEGRADA FROM ENOC.ROT_PROGRAMACION "+orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				RotProgramacion obj = new RotProgramacion();
				obj.mapeaReg(rs);
				list.add(obj);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.rotaciones.RotProgramacionUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return list;
	}
	
	public ArrayList<RotProgramacion> getListHospEsp(Connection conn, String hospitalId, String especialidadId, String orden ) throws SQLException{
		
		ArrayList<RotProgramacion> list	= new ArrayList<RotProgramacion>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando				= "";
		
		try{
			comando = "SELECT PROGRAMACION_ID, HOSPITAL_ID, ESPECIALIDAD_ID," +
					" CODIGO_PERSONAL, TO_CHAR(F_INICIO,'DD/MM/YYYY') AS F_INICIO, TO_CHAR(F_FINAL,'DD/MM/YYYY') AS F_FINAL," +
					" INSCRIPCION, PAGO, ANUAL, INTEGRADA" +
					" FROM ENOC.ROT_PROGRAMACION WHERE HOSPITAL_ID = '"+hospitalId+"' AND ESPECIALIDAD_ID = '"+especialidadId+"' "+orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				RotProgramacion obj = new RotProgramacion();
				obj.mapeaReg(rs);
				list.add(obj);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.rotaciones.RotProgramacionUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return list;
	}
	
	public ArrayList<RotProgramacion> getListHosp(Connection conn, String hospitalId, String orden ) throws SQLException{
		
		ArrayList<RotProgramacion> list	= new ArrayList<RotProgramacion>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando				= "";
		
		try{
			comando = "SELECT PROGRAMACION_ID, HOSPITAL_ID, ESPECIALIDAD_ID," +
					" CODIGO_PERSONAL, TO_CHAR(F_INICIO,'DD/MM/YYYY') AS F_INICIO, TO_CHAR(F_FINAL,'DD/MM/YYYY') AS F_FINAL," +
					" INSCRIPCION, PAGO, ANUAL, INTEGRADA" +
					" FROM ENOC.ROT_PROGRAMACION WHERE HOSPITAL_ID = '"+hospitalId+"' "+orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				RotProgramacion obj = new RotProgramacion();
				obj.mapeaReg(rs);
				list.add(obj);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.rotaciones.RotProgramacionUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return list;
	}
	
	public ArrayList<RotProgramacion> getListHospSinPago(Connection conn, String orden ) throws SQLException{
		
		ArrayList<RotProgramacion> list	= new ArrayList<RotProgramacion>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando				= "";
		
		try{
			comando = "SELECT PROGRAMACION_ID, HOSPITAL_ID, ESPECIALIDAD_ID," +
					" CODIGO_PERSONAL, TO_CHAR(F_INICIO,'DD/MM/YYYY') AS F_INICIO, TO_CHAR(F_FINAL,'DD/MM/YYYY') AS F_FINAL," +
					" INSCRIPCION, PAGO, ANUAL, INTEGRADA" +
					" FROM ENOC.ROT_PROGRAMACION WHERE PAGO = 0 "+orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				RotProgramacion obj = new RotProgramacion();
				obj.mapeaReg(rs);
				list.add(obj);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.rotaciones.RotProgramacionUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return list;
	}
	
	
	public ArrayList<RotProgramacion> getListHospEspConAlumnos(Connection conn, String hospitalId, String especialidadId, String orden ) throws SQLException{
		
		ArrayList<RotProgramacion> list	= new ArrayList<RotProgramacion>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando				= "";
		
		try{
			comando = "SELECT PROGRAMACION_ID, HOSPITAL_ID, ESPECIALIDAD_ID," +
					" CODIGO_PERSONAL, TO_CHAR(F_INICIO,'DD/MM/YYYY') AS F_INICIO, TO_CHAR(F_FINAL,'DD/MM/YYYY') AS F_FINAL," +
					" INSCRIPCION, PAGO, ANUAL, INTEGRADA" +
					" FROM ENOC.ROT_PROGRAMACION WHERE HOSPITAL_ID = '"+hospitalId+"' AND ESPECIALIDAD_ID = '"+especialidadId+"' " +
					" AND CODIGO_PERSONAL IS NOT NULL "+orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				RotProgramacion obj = new RotProgramacion();
				obj.mapeaReg(rs);
				list.add(obj);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.rotaciones.RotProgramacionUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return list;
	}
	
	public ArrayList<RotProgramacion> getListHospEspConAlumnosFecha(Connection conn, String hospitalId, String especialidadId, String fecha, String orden ) throws SQLException{
		
		ArrayList<RotProgramacion> list	= new ArrayList<RotProgramacion>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando				= "";
		
		try{
			comando = "SELECT PROGRAMACION_ID, HOSPITAL_ID, ESPECIALIDAD_ID," +
					" CODIGO_PERSONAL, TO_CHAR(F_INICIO,'DD/MM/YYYY') AS F_INICIO, TO_CHAR(F_FINAL,'DD/MM/YYYY') AS F_FINAL," +
					" INSCRIPCION, PAGO, ANUAL, INTEGRADA" +
					" FROM ENOC.ROT_PROGRAMACION WHERE HOSPITAL_ID = '"+hospitalId+"' AND ESPECIALIDAD_ID = '"+especialidadId+"' " +
					" AND CODIGO_PERSONAL IS NOT NULL AND TO_CHAR(F_INICIO,'DD/MM/YYYY') = '"+fecha+"' "+orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				RotProgramacion obj = new RotProgramacion();
				obj.mapeaReg(rs);
				list.add(obj);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.rotaciones.RotProgramacionUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return list;
	}
	
	public ArrayList<RotProgramacion> getListALumnos(Connection conn, String orden ) throws SQLException{
		
		ArrayList<RotProgramacion> list	= new ArrayList<RotProgramacion>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando				= "";
		
		try{
			comando = "SELECT * FROM ENOC.ROT_PROGRAMACION WHERE CODIGO_PERSONAL IS NOT NULL "+orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				RotProgramacion obj = new RotProgramacion();
				obj.mapeaReg(rs);
				list.add(obj);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.rotaciones.RotProgramacionUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return list;
	}
	
	public ArrayList<String> getMatriculas(Connection conn, String orden ) throws SQLException{
		
		ArrayList<String> list	= new ArrayList<String>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando				= "";
		
		try{
			comando = "SELECT DISTINCT(CODIGO_PERSONAL) FROM ENOC.ROT_PROGRAMACION WHERE CODIGO_PERSONAL IS NOT NULL "+orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){

				list.add(rs.getString("CODIGO_PERSONAL"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.rotaciones.RotProgramacionUtil|getMatriculas|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return list;
	}
	
	public ArrayList<RotProgramacion> getListInstitucion(Connection conn, String institucionId, String orden ) throws SQLException{
		
		ArrayList<RotProgramacion> list	= new ArrayList<RotProgramacion>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando				= "";
		
		try{
			comando = "SELECT PROGRAMACION_ID, HOSPITAL_ID, ESPECIALIDAD_ID," +
					" CODIGO_PERSONAL, TO_CHAR(F_INICIO,'DD/MM/YYYY') AS F_INICIO, TO_CHAR(F_FINAL,'DD/MM/YYYY') AS F_FINAL," +
					" INSCRIPCION, PAGO, ANUAL, INTEGRADA" +
					" FROM ENOC.ROT_PROGRAMACION WHERE HOSPITAL_ID IN(SELECT HOSPITAL_ID FROM ENOC.ROT_HOSPITAL WHERE INSTITUCION_ID = "+institucionId+" )" +
					" AND CODIGO_PERSONAL IS NOT NULL "+orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				RotProgramacion obj = new RotProgramacion();
				obj.mapeaReg(rs);
				list.add(obj);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.rotaciones.RotProgramacionUtil|getListInstitucion|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return list;
	}
	
	public ArrayList<RotProgramacion> getListInstitucionFechas(Connection conn, String institucionId, String fechaInicio, String fechaFinal, String orden ) throws SQLException{
		
		ArrayList<RotProgramacion> list	= new ArrayList<RotProgramacion>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando				= "";
		
		try{
			comando = "SELECT PROGRAMACION_ID, HOSPITAL_ID, ESPECIALIDAD_ID," +
					" CODIGO_PERSONAL, TO_CHAR(F_INICIO,'DD/MM/YYYY') AS F_INICIO, TO_CHAR(F_FINAL,'DD/MM/YYYY') AS F_FINAL," +
					" INSCRIPCION, PAGO, ANUAL, INTEGRADA" +
					" FROM ENOC.ROT_PROGRAMACION WHERE HOSPITAL_ID IN(SELECT HOSPITAL_ID FROM ENOC.ROT_HOSPITAL WHERE INSTITUCION_ID = "+institucionId+" )" +
					" AND CODIGO_PERSONAL IS NOT NULL " +
					" AND F_INICIO >= TO_DATE('"+fechaInicio+"', 'DD/MM/YYYY')" +
					" AND F_FINAL <= TO_DATE('"+fechaFinal+"', 'DD/MM/YYYY')"+orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				RotProgramacion obj = new RotProgramacion();
				obj.mapeaReg(rs);
				list.add(obj);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.rotaciones.RotProgramacionUtil|getListInstitucion|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return list;
	}
}
