package aca.internado;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Carlos
 *
 */
public class DormitorioUtil {
	
	public boolean insertReg(Connection conn, Dormitorio dormi ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.INT_DORMITORIO(DORMITORIO_ID, NOMBRE, PRECEPTOR,SEXO) VALUES(TO_NUMBER(?,'99'),?,?,?)"); 
			ps.setString(1, dormi.getDormitorioId());
			ps.setString(2, dormi.getNombre());
			ps.setString(3, dormi.getPreceptor());
			ps.setString(4, dormi.getSexo());
			if ( ps.executeUpdate()== 1){
				ok = true;
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.internado.DormitorioUtil|insertReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	public boolean updateReg(Connection conn, Dormitorio dormi ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.INT_DORMITORIO SET NOMBRE = ?, PRECEPTOR = ?, SEXO = ? WHERE DORMITORIO_ID = TO_NUMBER(?,'99')");			 
			ps.setString(1, dormi.getNombre());
			ps.setString(2, dormi.getPreceptor());
			ps.setString(3, dormi.getSexo());
			ps.setString(4, dormi.getDormitorioId());
			if ( ps.executeUpdate()== 1){
				ok = true;
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.internado.DormitorioUtil|updateReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean deleteReg(Connection conn, String dormitorioId ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.INT_DORMITORIO WHERE DORMITORIO_ID = TO_NUMBER(?,'99')"); 
			ps.setString(1,dormitorioId);			
			if ( ps.executeUpdate()== 1){
				ok = true;
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.internado.DormitorioUtil|deleteReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public Dormitorio mapeaRegId(Connection con, String dormitorioId) throws SQLException{
		Dormitorio dormi = new Dormitorio();
		PreparedStatement ps = null;
		ResultSet rs = null;		
		try{ 
			ps = con.prepareStatement("SELECT * FROM ENOC.INT_DORMITORIO WHERE DORMITORIO_ID = TO_NUMBER(?,'99') "); 
			ps.setString(1,dormitorioId);
			rs = ps.executeQuery();
			
			if(rs.next()){
				dormi.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.internado.DormitorioUtil|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return dormi;
	}
	
	public static String getNombre(Connection con, String dormitorioId) throws SQLException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		String nombre = "-";
		try{ 
			ps = con.prepareStatement("SELECT NOMBRE FROM ENOC.INT_DORMITORIO WHERE DORMITORIO_ID = TO_NUMBER(?,'99') "); 
			ps.setString(1,dormitorioId);
			rs = ps.executeQuery();			
			if(rs.next()){
				nombre = rs.getString("NOMBRE"); 
			}
		}catch(Exception ex){
			System.out.println("Error - aca.internado.DormitorioUtil|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return nombre;
	}
	
	public ArrayList<Dormitorio> getListAll(Connection conn, String orden) throws Exception {
		ArrayList<Dormitorio> listor = new ArrayList<Dormitorio>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
		try{
			comando = "SELECT DORMITORIO_ID, NOMBRE, PRECEPTOR, SEXO FROM ENOC.INT_DORMITORIO "+orden;	 
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				Dormitorio dormi = new Dormitorio();
				dormi.mapeaReg(rs);
				listor.add(dormi);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.internado.DormitorioUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return listor;
	}
	
	public ArrayList<Acceso> getPersonal(Connection conn, String dormitorioId, String orden) throws SQLException{
		ArrayList<Acceso> listor = new ArrayList<Acceso>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
		try{
			comando = "SELECT * FROM ENOC.INT_ACCESO WHERE DORMITORIO_ID = '"+dormitorioId+"' "+orden;			 
			rs = st.executeQuery(comando);
			while (rs.next()){
				Acceso acceso = new Acceso();
				acceso.mapeaReg(rs);
				listor.add(acceso);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.internado.DormitorioUtil|getPersonal|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return listor;
	}
	
	public ArrayList<Cuarto> getCuartos(Connection conn, String dormitorioId, String orden) throws SQLException{
		ArrayList<Cuarto> listor = new ArrayList<Cuarto>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
		
		try{
			comando = "SELECT * FROM ENOC.INT_CUARTO WHERE DORMITORIO_ID = '"+dormitorioId+"' "+orden; 
			rs = st.executeQuery(comando);
			while (rs.next()){
				Cuarto cuarto= new Cuarto();
				cuarto.mapeaReg(rs);
				listor.add(cuarto);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.internado.DormitorioUtil|getCuartos|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return listor;
	}
	
	public ArrayList<Cuarto> getCuartos(Connection conn, String dormitorioId, String pasillo, String orden) throws SQLException{
		ArrayList<Cuarto> listor = new ArrayList<Cuarto>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
		
		try{
			comando = "SELECT * FROM ENOC.INT_CUARTO WHERE DORMITORIO_ID = '"+dormitorioId+"' AND PASILLO = '"+pasillo+"' "+orden; 
			rs = st.executeQuery(comando);
			while (rs.next()){
				Cuarto cuarto= new Cuarto();
				cuarto.mapeaReg(rs);
				listor.add(cuarto);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.internado.DormitorioUtil|getCuartos|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return listor;
	}
	
	public String nextCuarto(Connection conn, String dormitorioId) throws SQLException{
		String cuarto = "";
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
		try{
			comando = "SELECT COALESCE(MAX(TO_CHAR(CUARTO_ID,'000'))+1,1) FROM ENOC.INT_CUARTO WHERE DORMITORIO_ID='"+dormitorioId+"'"; 
			rs = st.executeQuery(comando);
			if (rs.next()){
				cuarto = rs.getString(1);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.internado.DormitorioUtil|nextCuarto|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return cuarto;
	}
	
	public ArrayList<Alumno> getAlumnos(Connection conn, String dormitorioId, String orden) throws SQLException{
		ArrayList<Alumno> listor = new ArrayList<Alumno>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
		try{
			comando = "SELECT * FROM ENOC.INT_ALUMNO WHERE DORMITORIO_ID = '"+dormitorioId+"' "+orden;			 
			rs = st.executeQuery(comando);
			while (rs.next()){
				Alumno alumno = new Alumno();
				alumno.mapeaReg(rs);
				listor.add(alumno);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.internado.DormitorioUtil|getAlumnos|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return listor;
	}
	
	
	
	public String getDormitorioAlumno(Connection conn, String codigoPersonal) throws SQLException{
		String dormi="";
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
		try{
			comando = "SELECT DORMITORIO_ID FROM ENOC.INT_ALUMNO WHERE CODIGO_PERSONAL = '"+codigoPersonal+"'";			 
			rs = st.executeQuery(comando);
			if (rs.next()){
				dormi = rs.getString(1);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.internado.DormitorioUtil|getDormitorioAlumno|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return dormi;
	}
	
	public String getCuartoAlumno(Connection conn, String codigoPersonal) throws SQLException{
		String dormi="";
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
		try{
			comando = "SELECT CUARTO_ID FROM ENOC.INT_ALUMNO WHERE CODIGO_PERSONAL = '"+codigoPersonal+"'";			 
			rs = st.executeQuery(comando);
			if (rs.next()){
				dormi = rs.getString(1);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.internado.DormitorioUtil|getCuartoAlumno|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return dormi;
	}
	
	public String getPasilloAlumno(Connection conn, String codigoPersonal) throws SQLException{
		String dormi="";
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
		try{
			comando = "SELECT PASILLO FROM ENOC.INT_CUARTO WHERE CUARTO_ID = ("+ 
						"SELECT CUARTO_ID FROM ENOC.INT_ALUMNO WHERE CODIGO_PERSONAL='"+codigoPersonal+"')";			 
			rs = st.executeQuery(comando);
			if (rs.next()){
				dormi = rs.getString(1);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.internado.DormitorioUtil|getPasilloAlumno|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		return dormi;
	}
	
	public static HashMap<String,Cuarto> mapCuartos(Connection conn, String dormitorioId) throws SQLException{
		
		HashMap<String,Cuarto> map		= new HashMap<String,Cuarto>();
		Statement st 							= conn.createStatement();
		ResultSet rs		 					= null;
		String comando							= "";
				
		try{
			comando = " SELECT"
					+ "	DORMITORIO_ID, CUARTO_ID, PASILLO, CUPO, ESTADO"			
					+ " FROM ENOC.INT_CUARTO"
					+ " WHERE DORMITORIO_ID = "+dormitorioId;
			rs = st.executeQuery(comando);			
			while (rs.next()){
				Cuarto cuarto = new Cuarto();
				cuarto.mapeaReg(rs);				
				map.put(cuarto.getCuartoId(), cuarto);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.internado.DormitorioUtil|mapCuartos|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return map;
	}
}