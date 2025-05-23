package aca.bec;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class BecTipoUtil {
	
	public boolean insertReg(Connection conn, BecTipo becTipo) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.BEC_TIPO"
				+ " (ID_EJERCICIO, TIPO, NOMBRE, DEPARTAMENTOS, CUENTA, PORCENTAJE, MESES,"
				+ " HORAS, HORAS_PREPA, ACUERDO, IMPORTE, TIPO_ALUMNO, DIEZMO, ESTADO, ACUMULA, COLPORTA, MAXIMO,LIMITE, CUENTA_SUNPLUS)"
				+ " VALUES( ?, TO_NUMBER(?,'99'), ?, ?, ?, ?, ?, TO_NUMBER(?,'9999'), TO_NUMBER(?,'9999'), ?,"
				+ " TO_NUMBER(?,'999999.99'), ?, ?, ?, ?, ?, TO_NUMBER(?,'999.99'), TO_NUMBER(?,'99999999.99'),"
				+ " ?)");
					
			ps.setString(1,  becTipo.getIdEjercicio());
			ps.setString(2,  becTipo.getTipo());
			ps.setString(3,  becTipo.getNombre());
			ps.setString(4,  becTipo.getDepartamentos());
			ps.setString(5,  becTipo.getCuenta());
			ps.setString(6,  becTipo.getPorcentaje());
			ps.setString(7,  becTipo.getMeses());
			ps.setString(8,  becTipo.getHoras());
			ps.setString(9,  becTipo.getHorasPrepa());
			ps.setString(10, becTipo.getAcuerdo());
			ps.setString(11, becTipo.getImporte());
			ps.setString(12, becTipo.getTipoAlumno());
			ps.setString(13, becTipo.getDiezmo());
			ps.setString(14, becTipo.getEstado());
			ps.setString(15, becTipo.getAcumula());
			ps.setString(16, becTipo.getColporta());
			ps.setString(17, becTipo.getMaximo());
			ps.setString(18, becTipo.getLimite());
			ps.setString(19, becTipo.getCuentaSunplus());
			
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecTipoUtil|insertReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public boolean updateReg(Connection conn, BecTipo becTipo) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.BEC_TIPO"
				+ " SET NOMBRE = ?,"
				+ " DEPARTAMENTOS = ?, CUENTA = ?, PORCENTAJE = ?, MESES = ?, HORAS = TO_NUMBER(?,'9999'), HORAS_PREPA = TO_NUMBER(?,'9999'),"
				+ " ACUERDO = ?, IMPORTE = TO_NUMBER(?,'999999.99'), TIPO_ALUMNO = ?,"
				+ " DIEZMO = ?, ESTADO = ?, ACUMULA = ?, COLPORTA = ?, MAXIMO = TO_NUMBER(?,'999.99'), LIMITE = TO_NUMBER(?,'99999999.99'),"
				+ " CUENTA_SUNPLUS = ?"
				+ " WHERE ID_EJERCICIO = ?"
				+ " AND TIPO = TO_NUMBER(?,'99') ");
			
			ps.setString(1,  becTipo.getNombre());
			ps.setString(2,  becTipo.getDepartamentos());
			ps.setString(3,  becTipo.getCuenta());
			ps.setString(4,  becTipo.getPorcentaje());
			ps.setString(5,  becTipo.getMeses());
			ps.setString(6,  becTipo.getHoras());
			ps.setString(7,  becTipo.getHorasPrepa());
			ps.setString(8, becTipo.getAcuerdo());
			ps.setString(9, becTipo.getImporte());
			ps.setString(10, becTipo.getTipoAlumno());
			ps.setString(11, becTipo.getDiezmo());
			ps.setString(12, becTipo.getEstado());
			ps.setString(13, becTipo.getAcumula());
			ps.setString(14, becTipo.getColporta());
			ps.setString(15, becTipo.getMaximo());
			ps.setString(16,  becTipo.getLimite());
			ps.setString(17,  becTipo.getCuentaSunplus());
			ps.setString(18,  becTipo.getIdEjercicio());
			ps.setString(19,  becTipo.getTipo());
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecTipoUtil|updateReg|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public boolean updateDto(Connection conn, String departamentos, String tipo, String idEjercicio) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.BEC_TIPO SET DEPARTAMENTOS = ?"
					+ " WHERE TIPO = TO_NUMBER('"+tipo+"','99') AND ID_EJERCICIO = ?");
			
			ps.setString(1,  departamentos);
			ps.setString(2,  idEjercicio);
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecTipoUtil|updateDto|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public boolean updateTipoAlumno(Connection conn, String tipoAlumno, String tipo, String idEjercicio) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.BEC_TIPO SET TIPO_ALUMNO = ?"
					+ " WHERE TIPO = TO_NUMBER('"+tipo+"','99') AND ID_EJERCICIO = ?");
			
			ps.setString(1,  tipoAlumno);
			ps.setString(2,  idEjercicio);
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecTipoUtil|updateTipoAlumno|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	
	public boolean updateMes(Connection conn, String meses, String tipo, String idEjercicio) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.BEC_TIPO SET MESES = ?"
					+ " WHERE TIPO = TO_NUMBER('"+tipo+"','99') AND ID_EJERCICIO = ?");
			
			ps.setString(1,  meses);
			ps.setString(2,  idEjercicio);
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecTipoUtil|updateMes|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public boolean deleteReg(Connection conn, String idEjercicio, String tipo) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.BEC_TIPO"+ 
				" WHERE ID_EJERCICIO = ? AND TIPO = TO_NUMBER(?,'99') ");
			
			ps.setString(1,  idEjercicio);
			ps.setString(2,  tipo);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecTipoUtil|deleteReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public BecTipo mapeaRegId(Connection conn, String idEjercicio, String tipo) throws SQLException{
		BecTipo becTipo = new BecTipo();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement("SELECT  * " +
					" FROM ENOC.BEC_TIPO WHERE ID_EJERCICIO = ? AND TIPO = TO_NUMBER(?,'99')"); 
			
			ps.setString(1,  idEjercicio);
			ps.setString(2,  tipo);
			
			rs = ps.executeQuery();
			if (rs.next()){
				becTipo.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecTipoUtil|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return becTipo;
	}
	
	public boolean existeReg(Connection conn, String idEjercicio, String tipo) throws SQLException{
		boolean ok 				= false;
		ResultSet rs 			= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.BEC_TIPO WHERE ID_EJERCICIO = ? AND TIPO = TO_NUMBER(?,'99')"); 
			ps.setString(1,  idEjercicio);
			ps.setString(2,  tipo);
			
			rs= ps.executeQuery();		
			if(rs.next()){
				ok = true;
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecTipoUtil|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public String maximoReg(Connection conn, String idEjercicio) throws SQLException{
		String maximo 			= "1";
		ResultSet rs			= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT MAX(TIPO)+1 MAXIMO FROM ENOC.BEC_TIPO WHERE ID_EJERCICIO = ?"); 
			ps.setString(1,  idEjercicio);
			rs = ps.executeQuery();
			if (rs.next())
				maximo = rs.getString("MAXIMO");
				if(maximo == null){
					maximo = "1";
				}
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecTipoUtil|maximoReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return maximo;		
	}
	
	public String getImporte(Connection conn, String idEjercicio, String acuerdo) throws SQLException{
		String importe 			= "0";
		ResultSet rs			= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT IMPORTE FROM ENOC.BEC_TIPO WHERE ID_EJERCICIO = ? AND ACUERDO = ? "); 
			ps.setString(1,  idEjercicio);
			ps.setString(2,  acuerdo);
			
			rs = ps.executeQuery();
			if (rs.next())
				importe = rs.getString("IMPORTE");
				
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecTipoUtil|getImporte|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return importe;		
	}
	
	public String getHoras(Connection conn, String idEjercicio, String acuerdo) throws SQLException{
		String horas 			= "0";
		ResultSet rs			= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT HORAS FROM ENOC.BEC_TIPO WHERE ID_EJERCICIO = ? AND ACUERDO = ? "); 
			ps.setString(1,  idEjercicio);
			ps.setString(2,  acuerdo);
			
			rs = ps.executeQuery();
			if (rs.next())
				horas = rs.getString("HORAS");
				
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecTipoUtil|getHoras|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return horas;		
	}
	
	public String getHorasPrepa(Connection conn, String idEjercicio, String acuerdo) throws SQLException{
		String horas 			= "0";
		ResultSet rs			= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT HORAS_PREPA FROM ENOC.BEC_TIPO WHERE ID_EJERCICIO = ? AND ACUERDO = ? "); 
			ps.setString(1,  idEjercicio);
			ps.setString(2,  acuerdo);
			
			rs = ps.executeQuery();
			if (rs.next())
				horas = rs.getString("HORAS_PREPA");
				
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecTipoUtil|getHorasPrepa|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return horas;		
	}

	public String getTipo(Connection conn, String idEjercicio, String acuerdo) throws SQLException{
		String tipo 			= "0";
		ResultSet rs			= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT TIPO FROM ENOC.BEC_TIPO WHERE ID_EJERCICIO = ? AND ACUERDO = ? "); 
			ps.setString(1,  idEjercicio);
			ps.setString(2,  acuerdo);
			
			rs = ps.executeQuery();
			if (rs.next())
				tipo = rs.getString("TIPO");
				
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecTipoUtil|getTipo|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return tipo;		
	}
	
	public String getMaximo(Connection conn, String idEjercicio, String tipo) throws SQLException{
		String maximo 			= "0";
		ResultSet rs			= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT COALESCE(MAXIMO,0) AS MAXIMO FROM ENOC.BEC_TIPO WHERE ID_EJERCICIO = ? AND TIPO = TO_NUMBER(?,'99') "); 
			ps.setString(1,  idEjercicio);
			ps.setString(2,  tipo);
			
			rs = ps.executeQuery();
			if (rs.next())
				maximo = rs.getString("MAXIMO");
				
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecTipoUtil|getMaximo|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return maximo;		
	}
	
	public static String getTipoAlumno(Connection conn, String idEjercicio, String tipo) throws SQLException{
		PreparedStatement ps	= null;
		ResultSet rs			= null;		
		String tipoAlumno 		= "none";
		
		try{
			ps = conn.prepareStatement("SELECT TIPO_ALUMNO FROM ENOC.BEC_TIPO WHERE ID_EJERCICIO = ? AND TIPO = TO_NUMBER(?,'99') "); 
			ps.setString(1,  idEjercicio);
			ps.setString(2,  tipo);
			
			rs = ps.executeQuery();
			if (rs.next())
				tipoAlumno = rs.getString("TIPO_ALUMNO");
				
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecTipoUtil|getTipoAlumno|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return tipoAlumno;
	}
	
	public static String getTipoNombre(Connection conn, String tipo) throws SQLException{
		String nombre 			= "";
		ResultSet rs			= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT NOMBRE FROM ENOC.BEC_TIPO WHERE TIPO = TO_NUMBER(?,'99') "); 
			ps.setString(1,  tipo);
			
			rs = ps.executeQuery();
			if (rs.next())
				nombre = rs.getString("NOMBRE");
				
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecTipoUtil|getTipoNombre|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return nombre;		
	}
	
	public static String getTipoNombre(Connection conn, String tipo, String ejercicioId) throws SQLException{
		String nombre 			= "";
		ResultSet rs			= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT NOMBRE FROM ENOC.BEC_TIPO WHERE TIPO = TO_NUMBER(?,'99') AND ID_EJERCICIO = '"+ejercicioId+"'"); 
			ps.setString(1,  tipo);
			
			rs = ps.executeQuery();
			if (rs.next())
				nombre = rs.getString("NOMBRE");
				
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecTipoUtil|getTipoNombre|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return nombre;		
	}
	
	public static String getAcuerdo(Connection conn, String idEjercicio, String tipo) throws SQLException{
		String acuerdo 			= "";
		ResultSet rs			= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT ACUERDO FROM ENOC.BEC_TIPO WHERE ID_EJERCICIO ='"+idEjercicio+"' AND TIPO = TO_NUMBER('"+tipo+"','99')");
			rs = ps.executeQuery();
			if (rs.next())
				acuerdo = rs.getString("ACUERDO");
				
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecTipoUtil|getAcuerdo|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return acuerdo;		
	}	
	
	public ArrayList<BecTipo> getListAll(Connection conn, String orden) throws SQLException{
			
		ArrayList<BecTipo> lis 			= new ArrayList<BecTipo>();
		Statement st 					= conn.createStatement();
		ResultSet rs 					= null;
		String comando					= "";
		
		try{
			comando = "SELECT * FROM ENOC.BEC_TIPO "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				BecTipo obj = new BecTipo();
				obj.mapeaReg(rs);
				lis.add(obj);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecTipoUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lis;
	}
	
	public ArrayList<BecTipo> getListAllEjercicio(Connection conn, String idEjercicio, String orden) throws SQLException{
		
		ArrayList<BecTipo> lis 			= new ArrayList<BecTipo>();
		Statement st 					= conn.createStatement();
		ResultSet rs 					= null;
		String comando					= "";
		
		try{
			comando = "SELECT * FROM ENOC.BEC_TIPO WHERE ID_EJERCICIO = '"+idEjercicio+"' "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				BecTipo obj= new BecTipo();
				obj.mapeaReg(rs);
				lis.add(obj);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecTipoUtil|getListAllEjercicio|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lis;
	}
	
	public ArrayList<BecTipo> getListUsuario(Connection conn, String idEjercicio, String orden) throws SQLException{
		
		ArrayList<BecTipo> lis 			= new ArrayList<BecTipo>();
		Statement st 					= conn.createStatement();
		ResultSet rs 					= null;
		String comando					= "";
		
		try{
			comando = "SELECT * FROM ENOC.BEC_TIPO WHERE ID_EJERCICIO = '"+idEjercicio+"' "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				BecTipo obj= new BecTipo();
				obj.mapeaReg(rs);
				lis.add(obj);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecTipoUtil|getListUsuario|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lis;
	}
	
	public ArrayList<BecTipo> getListAcuerdo(Connection conn, String idEjercicio, String tipos, String orden) throws SQLException{
		
		ArrayList<BecTipo> lis 			= new ArrayList<BecTipo>();
		Statement st 					= conn.createStatement();
		ResultSet rs 					= null;
		String comando					= "";
		
		try{
			comando = "SELECT * FROM ENOC.BEC_TIPO WHERE ID_EJERCICIO = '"+idEjercicio+"' AND ACUERDO IN ("+tipos+") "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				BecTipo obj= new BecTipo();
				obj.mapeaReg(rs);
				lis.add(obj);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecTipoUtil|getListAcuerdo|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lis;
	}
	
	public BecTipo getNombreTipo (Connection conn, String idEjercicio, String tipo) throws SQLException{
		BecTipo obj = new BecTipo();
		Statement st 					= conn.createStatement();
		ResultSet rs 					= null;
		String comando					= "";
		try{
			comando = "SELECT * FROM ENOC.BEC_TIPO WHERE ID_EJERCICIO = '"+idEjercicio+"' AND TIPO = '"+tipo+"' ";
			rs = st.executeQuery(comando);
			while (rs.next()){
				obj.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecTipoUtil|getNombreTipo|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }			
		}
		
		return obj;
	}
	
	public static HashMap<String, BecTipo> mapBecTipo(Connection conn, String ejercicioId) throws SQLException{
		
		HashMap<String,BecTipo> map		= new HashMap<String,BecTipo>();
		Statement st 							= conn.createStatement();
		ResultSet rs		 					= null;
		String comando							= "";
				
		try{
			comando = " SELECT * FROM ENOC.BEC_TIPO WHERE ID_EJERCICIO = '"+ejercicioId+"'";
			rs = st.executeQuery(comando);			
			while (rs.next()){
				BecTipo obj= new BecTipo();
				obj.mapeaReg(rs);				
				map.put(rs.getString("ID_EJERCICIO")+rs.getString("TIPO"), obj);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecTipoUtil|mapBecTipo|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return map;
	}
	
	public static HashMap<String, String> mapaPrecios(Connection conn, String ejercicioId) throws SQLException{
		
		HashMap<String,String> map		= new HashMap<String,String>();
		Statement st 							= conn.createStatement();
		ResultSet rs		 					= null;
		String comando							= "";
				
		try{
			comando = "SELECT DISTINCT(ACUERDO), IMPORTE FROM ENOC.BEC_TIPO WHERE ID_EJERCICIO = '"+ejercicioId+"'";
			rs = st.executeQuery(comando);			
			while (rs.next()){								
				map.put(rs.getString("ACUERDO"),rs.getString("IMPORTE"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecTipoUtil|mapaPrecios|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return map;
	}
	
}