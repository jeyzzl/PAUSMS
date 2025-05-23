package aca.bec;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class BecPuestoUtil {
	
	public boolean insertReg(Connection conn, BecPuesto becPuesto) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.BEC_PUESTO"+ 
				"(ID_EJERCICIO, ID_CCOSTO," +
				" CATEGORIA_ID, PERIODO_ID, JUSTIFICACION, FUNCION, COMPETENCIAS, CERTIFICACIONES, CANTIDAD, ESTADO, OTRAS_COMP)"+
				" VALUES( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )");
					
			ps.setString(1,  becPuesto.getIdEjercicio());
			ps.setString(2,  becPuesto.getIdCcosto());
			ps.setString(3,  becPuesto.getCategoriaId());
			ps.setString(4,  becPuesto.getPeriodoId());
			ps.setString(5,  becPuesto.getJustificacion());
			ps.setString(6,  becPuesto.getFuncion());
			ps.setString(7,  becPuesto.getCompetencias());
			ps.setString(8,  becPuesto.getCertificaciones());
			ps.setString(9,  becPuesto.getCantidad());
			ps.setString(10, becPuesto.getEstado());
			ps.setString(11, becPuesto.getOtrasComp());
			
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecPuestoUtil|insertReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public boolean updateReg(Connection conn, BecPuesto becPuesto) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.BEC_PUESTO"+ 
				" SET JUSTIFICACION = ?," +
				" FUNCION = ?, COMPETENCIAS = ?, CERTIFICACIONES = ?, CANTIDAD = ?, ESTADO = ?, OTRAS_COMP = ? "+				
				" WHERE ID_EJERCICIO = ? AND ID_CCOSTO = ? AND CATEGORIA_ID = ? AND PERIODO_ID = ? ");
			
			ps.setString(1,  becPuesto.getJustificacion());
			ps.setString(2,  becPuesto.getFuncion());
			ps.setString(3,  becPuesto.getCompetencias());
			ps.setString(4,  becPuesto.getCertificaciones());
			ps.setString(5,  becPuesto.getCantidad());
			ps.setString(6,  becPuesto.getEstado());
			ps.setString(7,  becPuesto.getOtrasComp());
			ps.setString(8,  becPuesto.getIdEjercicio());
			ps.setString(9,  becPuesto.getIdCcosto());
			ps.setString(10, becPuesto.getCategoriaId());
			ps.setString(11, becPuesto.getPeriodoId());
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecPuestoUtil|updateReg|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	
	public boolean deleteReg(Connection conn, String idEjercicio, String idCcosto, String categoriaId, String periodoId) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.BEC_PUESTO"+ 
				" WHERE ID_EJERCICIO = ? AND ID_CCOSTO = ? AND CATEGORIA_ID = ? AND PERIODO_ID = ? ");
			
			ps.setString(1,  idEjercicio);
			ps.setString(2,  idCcosto);
			ps.setString(3,  categoriaId);
			ps.setString(4,  periodoId);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecPuestoUtil|deleteReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public BecPuesto mapeaRegId(Connection conn, String idEjercicio, String idCcosto, String categoriaId, String periodoId) throws SQLException{
		BecPuesto becPuesto = new BecPuesto();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement("SELECT  * " +
					" FROM ENOC.BEC_PUESTO WHERE ID_EJERCICIO = ? AND ID_CCOSTO = ? AND CATEGORIA_ID = ? AND PERIODO_ID = ? "); 
			
			ps.setString(1,  idEjercicio);
			ps.setString(2,  idCcosto);
			ps.setString(3,  categoriaId);
			ps.setString(4,  periodoId);
			
			rs = ps.executeQuery();
			if (rs.next()){
				becPuesto.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecPuestoUtil|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return becPuesto;
	}
	
	public boolean existeReg(Connection conn, String idEjercicio, String idCcosto, String categoriaId, String periodoId) throws SQLException{
		boolean ok 			= false;
		ResultSet rs 			= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.BEC_PUESTO WHERE ID_EJERCICIO = ? AND ID_CCOSTO = ? AND CATEGORIA_ID = ? AND PERIODO_ID = ? "); 
			ps.setString(1,  idEjercicio);
			ps.setString(2,  idCcosto);
			ps.setString(3,  categoriaId);
			ps.setString(4,  periodoId);
			
			rs= ps.executeQuery();		
			if(rs.next()){
				ok = true;
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecPuestoUtil|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public String getCantidadBecasUsadas(Connection conn, String idEjercicio, String ccosto, String tipo, String fechaPuesto, String periodoId) throws SQLException{
		String cantidad 		= "0";
		ResultSet rs 			= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT COUNT(PUESTO_ID) AS CANTIDAD FROM ENOC.BEC_PUESTO_ALUMNO"
					+ " WHERE ID_EJERCICIO = '"+idEjercicio+"'"
					+ " AND ID_CCOSTO = '"+ccosto+"'"					
					+ " AND TIPO = '"+tipo+"'"
					+ " AND TO_DATE('"+fechaPuesto+"', 'DD/MM/YYYY') BETWEEN FECHA_INI AND FECHA_FIN"
					+ " AND PERIODO_ID = '"+periodoId+"'");
			
			rs= ps.executeQuery();		
			if(rs.next()){
				cantidad = rs.getString("CANTIDAD");
			}
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecPuestoUtil|getCantidadActualTemporal|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return cantidad;
	}
	
	public String getCantidadActualTemporal(Connection conn, String fechaPuesto, String idEjercicio, String ccosto) throws SQLException{
		String cantidad 		= "0";
		ResultSet rs 			= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT COUNT(PUESTO_ID) AS CANTIDAD FROM ENOC.BEC_PUESTO_ALUMNO" +
					" WHERE ID_EJERCICIO = '"+idEjercicio+"'" +
					" AND ID_CCOSTO = '"+ccosto+"'" +
					" AND TIPO = 'T'" +
					" AND TO_DATE('"+fechaPuesto+"', 'DD/MM/YYYY') BETWEEN FECHA_INI AND FECHA_FIN");
			
			rs= ps.executeQuery();		
			if(rs.next()){
				cantidad = rs.getString("CANTIDAD");
			}
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecPuestoUtil|getCantidadActualTemporal|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return cantidad;
	}
	
	public String getCantidadActualBasica(Connection conn, String fechaPuesto, String idEjercicio, String ccosto) throws SQLException{
		String cantidad 		= "0";
		ResultSet rs 			= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT COUNT(PUESTO_ID) AS CANTIDAD FROM ENOC.BEC_PUESTO_ALUMNO " +
					" WHERE ID_EJERCICIO = '"+idEjercicio+"' " +
					" AND ID_CCOSTO = '"+ccosto+"'" +
					" AND TIPO = 'B'" +
					" AND TO_DATE('"+fechaPuesto+"', 'DD/MM/YYYY') BETWEEN FECHA_INI AND FECHA_FIN"); 
			
			rs= ps.executeQuery();		
			if(rs.next()){
				cantidad = rs.getString("CANTIDAD");
			}
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecPuestoUtil|getCantidadActualBasica|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return cantidad;
	}
	
	public String getCantidadActualIndustrial(Connection conn, String fechaPuesto, String idEjercicio, String ccosto) throws SQLException{
		String cantidad 		= "0";
		ResultSet rs 			= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT COUNT(PUESTO_ID) AS CANTIDAD FROM ENOC.BEC_PUESTO_ALUMNO " +
					" WHERE ID_EJERCICIO = '"+idEjercicio+"' " +
					" AND ID_CCOSTO = '"+ccosto+"'" +
					" AND TIPO = 'I'" +
					" AND TO_DATE('"+fechaPuesto+"', 'DD/MM/YYYY') BETWEEN FECHA_INI AND FECHA_FIN"); 
			
			rs= ps.executeQuery();		
			if(rs.next()){
				cantidad = rs.getString("CANTIDAD");
			}
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecPuestoUtil|getCantidadActualIndustrial|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return cantidad;
	}
	
	public String getCantidadActualPreIndustrial(Connection conn, String fechaPuesto, String idEjercicio, String ccosto) throws SQLException{
		String cantidad 		= "0";
		ResultSet rs 			= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT COUNT(PUESTO_ID) AS CANTIDAD FROM ENOC.BEC_PUESTO_ALUMNO " +
					" WHERE ID_EJERCICIO = '"+idEjercicio+"' " +
					" AND ID_CCOSTO = '"+ccosto+"'" +
					" AND TIPO = 'P'" +
					" AND TO_DATE('"+fechaPuesto+"', 'DD/MM/YYYY') BETWEEN FECHA_INI AND FECHA_FIN"); 
			
			rs= ps.executeQuery();		
			if(rs.next()){
				cantidad = rs.getString("CANTIDAD");
			}
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecPuestoUtil|getCantidadActualIndustrial|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return cantidad;
	}
	
	public String getCantidadActualPostgrado(Connection conn, String fechaPuesto, String idEjercicio, String ccosto) throws SQLException{
		String cantidad 		= "0";
		ResultSet rs 			= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT COUNT(PUESTO_ID) AS CANTIDAD FROM ENOC.BEC_PUESTO_ALUMNO " +
					" WHERE ID_EJERCICIO = '"+idEjercicio+"' " +
					" AND ID_CCOSTO = '"+ccosto+"'" +
					" AND TIPO = 'M'" +
					" AND TO_DATE('"+fechaPuesto+"', 'DD/MM/YYYY') BETWEEN FECHA_INI AND FECHA_FIN");
			
			rs= ps.executeQuery();		
			if(rs.next()){
				cantidad = rs.getString("CANTIDAD");
			}
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecPuestoUtil|getCantidadActualIndustrial|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return cantidad;
	}
	
	public ArrayList<BecPuesto> getListAll(Connection conn, String orden) throws SQLException{
			
		ArrayList<BecPuesto> lis 		= new ArrayList<BecPuesto>();
		Statement st 					= conn.createStatement();
		ResultSet rs 					= null;
		String comando					= "";
		
		try{
			comando = "SELECT * FROM ENOC.BEC_PUESTO "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				BecPuesto obj= new BecPuesto();
				obj.mapeaReg(rs);
				lis.add(obj);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecPuestoUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lis;
	}
	
	public ArrayList<BecPuesto> getListAllEjercicio(Connection conn, String idEjercicio, String orden) throws SQLException{
		
		ArrayList<BecPuesto> lis 		= new ArrayList<BecPuesto>();
		Statement st 					= conn.createStatement();
		ResultSet rs 					= null;
		String comando					= "";
		
		try{
			comando = "SELECT * FROM ENOC.BEC_PUESTO WHERE ID_EJERCICIO = '"+idEjercicio+"' "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				BecPuesto obj= new BecPuesto();
				obj.mapeaReg(rs);
				lis.add(obj);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecPuestoUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lis;
	}
	
	public ArrayList<BecPuesto> getListAllCcosto(Connection conn, String idCcosto, String idEjercicio, String periodoId, String orden) throws SQLException{
		
		ArrayList<BecPuesto> lis 		= new ArrayList<BecPuesto>();
		Statement st 					= conn.createStatement();
		ResultSet rs 					= null;
		String comando					= "";
		
		try{
			comando = "SELECT * FROM ENOC.BEC_PUESTO WHERE ID_CCOSTO = '"+idCcosto+"' AND ID_EJERCICIO = '"+idEjercicio+"' AND PERIODO_ID = '"+periodoId+"' "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				BecPuesto obj= new BecPuesto();
				obj.mapeaReg(rs);
				lis.add(obj);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecPuestoUtil|getListAllCcosto|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lis;
	}
	
	public ArrayList<BecPuesto> getListAllCcosto(Connection conn, String idCcosto, String idEjercicio, String orden) throws SQLException{
		
		ArrayList<BecPuesto> lis 		= new ArrayList<BecPuesto>();
		Statement st 					= conn.createStatement();
		ResultSet rs 					= null;
		String comando					= "";
		
		try{
			comando = "SELECT * FROM ENOC.BEC_PUESTO WHERE ID_CCOSTO = '"+idCcosto+"' AND ID_EJERCICIO = '"+idEjercicio+"' "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				BecPuesto obj= new BecPuesto();
				obj.mapeaReg(rs);
				lis.add(obj);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecPuestoUtil|getListAllCcosto|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lis;
	}
	
	public ArrayList<BecPuesto> listPuestosDepto(Connection conn, String idCcosto, String idEjercicio, String fecha, String orden) throws SQLException{
		
		ArrayList<BecPuesto> lis 		= new ArrayList<BecPuesto>();
		Statement st 					= conn.createStatement();
		ResultSet rs 					= null;
		String comando					= "";
		
		try{
			comando = "SELECT * FROM ENOC.BEC_PUESTO"
					+ " WHERE ID_CCOSTO = '"+idCcosto+"'"
					+ " AND ID_EJERCICIO = '"+idEjercicio+"'"
					+ " AND PERIODO_ID IN (SELECT PERIODO_ID FROM ENOC.BEC_PERIODO WHERE TO_DATE('"+fecha+"','DD/MM/YYYY') BETWEEN FECHA_INI AND FECHA_FIN) "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				BecPuesto obj= new BecPuesto();
				obj.mapeaReg(rs);
				lis.add(obj);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecPuestoUtil|getListAllCcosto|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lis;
	}
	
	public ArrayList<BecPuesto> getListCategorias(Connection conn, String periodoId, String idCcosto, String orden) throws SQLException{
			
			ArrayList<BecPuesto> lisCat 		= new ArrayList<BecPuesto>();
			Statement st 					= conn.createStatement();
			ResultSet rs 					= null;
			String comando					= "";
			
			try{
				comando = "SELECT * FROM ENOC.BEC_PUESTO WHERE PERIODO_ID = '"+periodoId+"' AND ID_CCOSTO = '"+idCcosto+"' "+ orden;
				
				rs = st.executeQuery(comando);
				while (rs.next()){
					
					BecPuesto obj= new BecPuesto();
					obj.mapeaReg(rs);
					lisCat.add(obj);
				}
				
			}catch(Exception ex){
				System.out.println("Error - aca.bec.BecPuestoUtil|getListCategorias|:"+ex);
			}finally{
				try { rs.close(); } catch (Exception ignore) { }
				try { st.close(); } catch (Exception ignore) { }
			}
			
			return lisCat;
	}
	
	public ArrayList<BecPuesto> getListCategorias(Connection conn, String idCcosto, String orden) throws SQLException{
		
		ArrayList<BecPuesto> lisCat 		= new ArrayList<BecPuesto>();
		Statement st 					= conn.createStatement();
		ResultSet rs 					= null;
		String comando					= "";
		
		try{
			comando = "SELECT * FROM ENOC.BEC_PUESTO WHERE ID_CCOSTO = '"+idCcosto+"' "+ orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				BecPuesto obj= new BecPuesto();
				obj.mapeaReg(rs);
				lisCat.add(obj);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecPuestoUtil|getListCategorias|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisCat;
	}
	
	public boolean tieneAlumnos(Connection conn, String idEjercicio, String idCcosto, String categoria, String periodoId) throws SQLException{
		Statement st 					= conn.createStatement();
		ResultSet rs 					= null;
		String comando					= "";
		boolean tiene					= false;
		
		try{
			comando = "SELECT COUNT(*) AS CONTEO FROM ENOC.BEC_PUESTO_ALUMNO WHERE ID_EJERCICIO = '"+idEjercicio+"' AND ID_CCOSTO = '"+idCcosto+"' AND CATEGORIA_ID = '"+categoria+"' AND PERIODO_ID = '"+periodoId+"' ";
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				if(Integer.parseInt(rs.getString("CONTEO"))!=0){
					tiene = true;
				}
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecPuestoUtil|tieneAlumnos|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return tiene;
	}
	
	public HashMap<String,BecPuesto> mapPuestos(Connection conn, String idEjercicio) throws SQLException{
		
		HashMap<String, BecPuesto> mapa = new HashMap<String, BecPuesto>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando			= "";
		try{
			comando = " SELECT * FROM ENOC.BEC_PUESTO"
					+ " WHERE ID_EJERCICIO = '"+idEjercicio+"'";
			rs = st.executeQuery(comando);
			while (rs.next()){
				BecPuesto obj = new BecPuesto();
				obj.mapeaReg(rs);				
				mapa.put(rs.getString("ID_EJERCICIO")+rs.getString("ID_CCOSTO")+rs.getString("CATEGORIA_ID")+rs.getString("PERIODO_ID"), obj);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecPuestoUtil|getMapPuestos|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return mapa;
	}

}
