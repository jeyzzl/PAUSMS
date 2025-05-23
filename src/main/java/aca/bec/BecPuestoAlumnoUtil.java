package aca.bec;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class BecPuestoAlumnoUtil {
	
	public boolean insertReg(Connection conn, BecPuestoAlumno becPuestoAlumno) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.BEC_PUESTO_ALUMNO"+ 
				"(PUESTO_ID, ID_EJERCICIO," +
				" ID_CCOSTO, CATEGORIA_ID, CODIGO_PERSONAL, FECHA_INI, FECHA_FIN, TIPO, ESTADO, USUARIO, PERIODO_ID, PLAN_ID, DESCRIPCION)"+
				" VALUES( ?, ?, ?, TO_NUMBER(?,'999'), ?, TO_DATE(?, 'DD/MM/YYYY'), TO_DATE(?, 'DD/MM/YYYY'), ?, ?, ?, ?, ?, ? )");
			
			ps.setString(1,  becPuestoAlumno.getPuestoId());
			ps.setString(2,  becPuestoAlumno.getIdEjercicio());
			ps.setString(3,  becPuestoAlumno.getIdCcosto());
			ps.setString(4,  becPuestoAlumno.getCategoriaId());
			ps.setString(5,  becPuestoAlumno.getCodigoPersonal());
			ps.setString(6,  becPuestoAlumno.getFechaIni());
			ps.setString(7,  becPuestoAlumno.getFechaFin());
			ps.setString(8,  becPuestoAlumno.getTipo());
			ps.setString(9,  becPuestoAlumno.getEstado());
			ps.setString(10, becPuestoAlumno.getUsuario());
			ps.setString(11, becPuestoAlumno.getPeriodoId());
			ps.setString(12, becPuestoAlumno.getPlanId());
			ps.setString(13, becPuestoAlumno.getDescripcion());
			
			
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecPuestoAlumnoUtil|insertReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public boolean updateReg(Connection conn, BecPuestoAlumno becPuestoAlumno) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.BEC_PUESTO_ALUMNO"+ 
				" SET ID_EJERCICIO = ?," +
				" ID_CCOSTO = ?, CATEGORIA_ID = TO_NUMBER(?, '999'), CODIGO_PERSONAL = ?, FECHA_INI = TO_DATE(?, 'DD/MM/YYYY')," +
				" FECHA_FIN = TO_DATE(?, 'DD/MM/YYYY'), TIPO = ?, ESTADO = ?, USUARIO = ?, PERIODO_ID = ?, PLAN_ID = ?, DESCRIPCION = ? "+				
				" WHERE PUESTO_ID = ? ");
			
			ps.setString(1,  becPuestoAlumno.getIdEjercicio());
			ps.setString(2,  becPuestoAlumno.getIdCcosto());
			ps.setString(3,  becPuestoAlumno.getCategoriaId());
			ps.setString(4,  becPuestoAlumno.getCodigoPersonal());
			ps.setString(5,  becPuestoAlumno.getFechaIni());
			ps.setString(6,  becPuestoAlumno.getFechaFin());
			ps.setString(7,  becPuestoAlumno.getTipo());
			ps.setString(8,  becPuestoAlumno.getEstado());
			ps.setString(9,  becPuestoAlumno.getUsuario());
			ps.setString(10, becPuestoAlumno.getPeriodoId());
			ps.setString(11, becPuestoAlumno.getPlanId());
			ps.setString(12, becPuestoAlumno.getDescripcion());
			ps.setString(13, becPuestoAlumno.getPuestoId());
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecPuestoAlumnoUtil|updateReg|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public boolean update(Connection conn, String idCcosto, String categoriaId, String puestoId) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.BEC_PUESTO_ALUMNO"+ 
				" SET ID_CCOSTO = ?, CATEGORIA_ID = TO_NUMBER(?, '999')"+			
				" WHERE PUESTO_ID = ? ");
			
			ps.setString(1,  idCcosto);
			ps.setString(2,  categoriaId);
			ps.setString(3,  puestoId);
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecPuestoAlumnoUtil|update|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	 
	
	public boolean contratar(Connection conn, String estado, String puestoId) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.BEC_PUESTO_ALUMNO"+ 
				" SET ESTADO = ? "+				
				" WHERE PUESTO_ID = ? ");
			
			ps.setString(1,  estado);
			ps.setString(2,  puestoId);
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecPuestoAlumnoUtil|contratar|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public boolean deleteReg(Connection conn, String puestoId) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.BEC_PUESTO_ALUMNO"+ 
				" WHERE PUESTO_ID = ? ");
			
			ps.setString(1,  puestoId);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecPuestoAlumnoUtil|deleteReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public BecPuestoAlumno mapeaRegId(Connection conn, String puestoId) throws SQLException{
		BecPuestoAlumno becPuestoAlumno = new BecPuestoAlumno();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement("SELECT PUESTO_ID, ID_EJERCICIO, ID_CCOSTO, CATEGORIA_ID, CODIGO_PERSONAL," +
					" TO_CHAR(FECHA_INI, 'DD/MM/YYYY') AS FECHA_INI, TO_CHAR(FECHA_FIN, 'DD/MM/YYYY') AS FECHA_FIN, TIPO, ESTADO, USUARIO, PERIODO_ID, NIVEL_ID, PLAN_ID, DESCRIPCION" +
					" FROM ENOC.BEC_PUESTO_ALUMNO WHERE PUESTO_ID = ?  "); 
			
			ps.setString(1,  puestoId);
			
			rs = ps.executeQuery();
			if (rs.next()){
				becPuestoAlumno.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecPuestoAlumnoUtil|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return becPuestoAlumno;
	}
	
	public BecPuestoAlumno mapeaRegIdPuesto(Connection conn, String puestoId) throws SQLException{
		BecPuestoAlumno becPuestoAlumno = new BecPuestoAlumno();
		PreparedStatement ps 	= null;
		ResultSet rs 			= null;		
		try{
			ps = conn.prepareStatement("SELECT PUESTO_ID, ID_EJERCICIO, ID_CCOSTO, CATEGORIA_ID, CODIGO_PERSONAL," +
					" TO_CHAR(FECHA_INI, 'DD/MM/YYYY') AS FECHA_INI, TO_CHAR(FECHA_FIN, 'DD/MM/YYYY') AS FECHA_FIN, TIPO, ESTADO, USUARIO, PERIODO_ID, NIVEL_ID, PLAN_ID, DESCRIPCION" +
					" FROM ENOC.BEC_PUESTO_ALUMNO WHERE PUESTO_ID = '"+puestoId+"'  "); 
			rs = ps.executeQuery();
			if (rs.next()){
				becPuestoAlumno.mapeaReg(rs);				
			}
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecPuestoAlumnoUtil|mapeaRegIdPuesto|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return becPuestoAlumno;
	}
	
	public BecPuestoAlumno mapeaPuestoActual(Connection conn, String codigoPersonal) throws SQLException{
		BecPuestoAlumno becPuestoAlumno = new BecPuestoAlumno();
		PreparedStatement ps 	= null;
		ResultSet rs 			= null;		
		try{
			ps = conn.prepareStatement("SELECT PUESTO_ID, ID_EJERCICIO, ID_CCOSTO, CATEGORIA_ID, CODIGO_PERSONAL,"+
					" TO_CHAR(FECHA_INI, 'DD/MM/YYYY') AS FECHA_INI, TO_CHAR(FECHA_FIN, 'DD/MM/YYYY') AS FECHA_FIN, TIPO, ESTADO, USUARIO, PERIODO_ID, NIVEL_ID, PLAN_ID, DESCRIPCION"+
					" FROM ENOC.BEC_PUESTO_ALUMNO WHERE CODIGO_PERSONAL = ? AND now() BETWEEN FECHA_INI AND FECHA_FIN");			
			ps.setString(1,  codigoPersonal);			
			rs = ps.executeQuery();
			if (rs.next()){				
				becPuestoAlumno.mapeaReg(rs);				
			}
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecPuestoAlumnoUtil|mapeaPuestoActual|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return becPuestoAlumno;
	}
	
	public boolean existeReg(Connection conn, String puestoId) throws SQLException{
		boolean ok 			= false;
		ResultSet rs 			= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.BEC_PUESTO_ALUMNO WHERE PUESTO_ID = ? "); 
			ps.setString(1,  puestoId);
			
			rs= ps.executeQuery();		
			if(rs.next()){
				ok = true;
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecPuestoAlumnoUtil|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public String maximoReg(Connection conn, String ejercicioId) throws SQLException{
 		String 		maximo 		= "1";
 		ResultSet 		rs		= null;
 		PreparedStatement ps	= null;
 		
 		try{
 			ps = conn.prepareStatement("SELECT COALESCE(TO_NUMBER(MAX(SUBSTR(PUESTO_ID,9,4)), '9999')+1,1) AS MAXIMO FROM ENOC.BEC_PUESTO_ALUMNO " +
 					"WHERE PUESTO_ID LIKE ?||'%'");
 			
 			ps.setString(1, ejercicioId);
 			
 			rs = ps.executeQuery();
 			if (rs.next())
 				maximo = rs.getString("MAXIMO");
 			
 			if(maximo.length() == 0) maximo = "0001";
 			if(maximo.length() == 1) maximo = "000"+maximo;
 			if(maximo.length() == 2) maximo = "00"+maximo;
 			if(maximo.length() == 3) maximo = "0"+maximo;
 			
 		}catch(Exception ex){
 			System.out.println("Error - aca.bec.BecPuestoAlumnoUtil|maximoReg|:"+ex);
 		}finally{
	 		try { rs.close(); } catch (Exception ignore) { }
	 		try { ps.close(); } catch (Exception ignore) { }
 		}
 		
 		return maximo;
 	}
	
	public String minimoReg(Connection conn, String ejercicioId) throws SQLException{
		
 		PreparedStatement ps	= null;
 		ResultSet 		rs		= null;
 		String 		minimo 		= "0001";
 		
 		try{
 			ps = conn.prepareStatement("SELECT COALESCE(TO_NUMBER(SUBSTR(MIN(PUESTO_ID),9,4),'9999')-1,1) AS MINIMO FROM ENOC.BEC_PUESTO_ALUMNO " +
 					"WHERE PUESTO_ID LIKE ?||'%'");
 			
 			ps.setString(1, ejercicioId);
 			
 			rs = ps.executeQuery();
 			if (rs.next())
 				minimo = rs.getString("MINIMO");
 			
 			if(minimo.length() == 0) minimo = "0001";
 			if(minimo.length() == 1) minimo = "000"+minimo;
 			if(minimo.length() == 2) minimo = "00"+minimo;
 			if(minimo.length() == 3) minimo = "0"+minimo;
 				
 		}catch(Exception ex){
 			System.out.println("Error - aca.bec.BecPuestoAlumnoUtil|minimoReg|:"+ex);
 		}finally{
	 		try { rs.close(); } catch (Exception ignore) { }
	 		try { ps.close(); } catch (Exception ignore) { }
 		}
 		
 		return minimo;
 	}
	
	public String maximoPuesto(Connection conn, String codigoPersonal) throws SQLException{
		PreparedStatement ps	= null; 		
 		ResultSet 		rs		= null;
 		String 		maximo 		= "1";
 		
 		try{
 			ps = conn.prepareStatement(" SELECT MAX(PUESTO_ID) AS MAXIMO FROM ENOC.BEC_PUESTO_ALUMNO WHERE CODIGO_PERSONAL = ?");
 			ps.setString(1, codigoPersonal);
 			
 			rs = ps.executeQuery();
 			if (rs.next())
 				maximo = rs.getString("MAXIMO");
 			
 		}catch(Exception ex){
 			System.out.println("Error - aca.bec.BecPuestoAlumnoUtil|maximoReg|:"+ex);
 		}finally{
	 		try { rs.close(); } catch (Exception ignore) { }
	 		try { ps.close(); } catch (Exception ignore) { }
 		}
 		
 		return maximo;
 	}
	
	public boolean existeFechaBetween(Connection conn, String codigoPersonal, String fecha, String puestoId) throws SQLException{
		boolean ok 				= false;
		ResultSet rs 			= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement(	"SELECT * FROM ENOC.BEC_PUESTO_ALUMNO " +
										" WHERE CODIGO_PERSONAL = '"+codigoPersonal+"' " +
										" AND TO_DATE('"+fecha+"', 'DD/MM/YYYY') >= FECHA_INI" +
										" AND TO_DATE('"+fecha+"', 'DD/MM/YYYY') <= FECHA_FIN" +
										" AND PUESTO_ID NOT IN ('"+puestoId+"')" ); 
			
			rs= ps.executeQuery();		
			if(rs.next()){
				ok = true;
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecPuestoAlumnoUtil|existeFechaBetween|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public boolean existeFechaINIBetween(Connection conn, String codigoPersonal, String fechaIni, String fechaFin, String puestoId) throws SQLException{
		boolean ok 				= false;
		ResultSet rs 			= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement(	"SELECT * FROM ENOC.BEC_PUESTO_ALUMNO " +
										" WHERE CODIGO_PERSONAL = '"+codigoPersonal+"' " +
										" AND FECHA_INI >= TO_DATE('"+fechaIni+"', 'DD/MM/YYYY')" +
										" AND FECHA_INI <= TO_DATE('"+fechaFin+"', 'DD/MM/YYYY')" +
										" AND PUESTO_ID NOT IN ('"+puestoId+"')" ); 
			
			rs= ps.executeQuery();		
			if(rs.next()){
				ok = true;
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecPuestoAlumnoUtil|existeFechaBetween|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public boolean existeFechaFINBetween(Connection conn, String codigoPersonal, String fechaIni, String fechaFin, String puestoId) throws SQLException{
		boolean ok 				= false;
		ResultSet rs 			= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement(	"SELECT * FROM ENOC.BEC_PUESTO_ALUMNO " +
										" WHERE CODIGO_PERSONAL = '"+codigoPersonal+"' " +
										" AND FECHA_FIN >= TO_DATE('"+fechaIni+"', 'DD/MM/YYYY')" +
										" AND FECHA_FIN <= TO_DATE('"+fechaFin+"', 'DD/MM/YYYY')" +
										" AND PUESTO_ID NOT IN ('"+puestoId+"')" ); 
			
			rs= ps.executeQuery();		
			if(rs.next()){
				ok = true;
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecPuestoAlumnoUtil|existeFechaBetween|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
 	
	public HashMap<String,String> getColportaje(Connection conn) throws SQLException{
		
		HashMap<String, String> mapa = new HashMap<String, String>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando			= "";
		try{
			comando = "SELECT MATRICULA, SALDO FROM noe.clp_docs_confirmados";
			rs = st.executeQuery(comando);
			while (rs.next()){					
				mapa.put(rs.getString("MATRICULA"), rs.getString("SALDO"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.BecPuestoAlumno |getColportaje|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return mapa;
	}
	
	public static String getNumPuestosAlumno(Connection conn, String idCcosto, String tipo, String idEjercicio) throws SQLException{
 		String 		cantidad 	= "0";
 		ResultSet 		rs		= null;
 		PreparedStatement ps	= null;
 		
 		try{
 			ps = conn.prepareStatement("SELECT COUNT(*) AS CANTIDAD FROM ENOC.BEC_PUESTO_ALUMNO WHERE ID_CCOSTO = ? AND TIPO = ? AND ID_EJERCICIO = ? ");
 			
 			ps.setString(1, idCcosto);
 			ps.setString(2, tipo);
 			ps.setString(3, idEjercicio);
 			
 			rs = ps.executeQuery();
 			if (rs.next())
 				cantidad = rs.getString("CANTIDAD");
 			
 		}catch(Exception ex){
 			System.out.println("Error - aca.bec.BecPuestoAlumnoUtil|getNumPuestosAlumno|:"+ex);
 		}finally{
	 		try { rs.close(); } catch (Exception ignore) { }
	 		try { ps.close(); } catch (Exception ignore) { }
 		}
 		
 		return cantidad;
 	}
	
	public static String getNumPuestosAlumnosActivos(Connection conn, String idCcosto, String tipo, String idEjercicio) throws SQLException{
 		String 		cantidad 	= "0";
 		ResultSet 		rs		= null;
 		PreparedStatement ps	= null;
 		
 		try{
 			ps = conn.prepareStatement("SELECT COUNT(*) AS CANTIDAD FROM ENOC.BEC_PUESTO_ALUMNO" +
 					" WHERE ID_CCOSTO = ?" +
 					" AND TIPO = ?" +
 					" AND ID_EJERCICIO = ?" +
 					" AND TO_DATE(TO_CHAR(now(),'DD/MM/YYYY'),'DD/MM/YYYY') BETWEEN FECHA_INI AND FECHA_FIN");
 			
 			ps.setString(1, idCcosto);
 			ps.setString(2, tipo);
 			ps.setString(3, idEjercicio);
 			
 			rs = ps.executeQuery();
 			if (rs.next())
 				cantidad = rs.getString("CANTIDAD");
 			
 		}catch(Exception ex){
 			System.out.println("Error - aca.bec.BecPuestoAlumnoUtil|getNumPuestosAlumno|:"+ex);
 		}finally{
	 		try { rs.close(); } catch (Exception ignore) { }
	 		try { ps.close(); } catch (Exception ignore) { }
 		}
 		
 		return cantidad;
 	}
	
	public static String getPuestoAlumno(Connection conn, String codigoPersonal, String cargaId) throws SQLException{
		
 		PreparedStatement ps	= null;
 		ResultSet rs			= null;
 		String puesto 			= "-";
 		
 		try{
 			ps = conn.prepareStatement("SELECT COALESCE(ALPUESTO_PUESTO_ID,'-') AS PUESTO FROM MATEO.FES_CC_AFE_ACUERDOS"
 					+ " WHERE MATRICULA = ?"
 					+ " AND CARGA_ID = ?");
 			
 			ps.setString(1, codigoPersonal);
 			ps.setString(2, cargaId); 			
 			
 			rs = ps.executeQuery();
 			if (rs.next())
 				puesto = rs.getString("PUESTO");
 			
 		}catch(Exception ex){
 			System.out.println("Error - aca.bec.BecPuestoAlumnoUtil|getPuestoAlumno|:"+ex);
 		}finally{
	 		try { rs.close(); } catch (Exception ignore) { }
	 		try { ps.close(); } catch (Exception ignore) { }
 		}
 		
 		return puesto;
 	}
	
	// Map de puestos vigentes en una fecha determinada
		public HashMap<String,BecPuestoAlumno> getPuestosAlumno(Connection conn, String idEjercicio, String fecha) throws SQLException{
			
			HashMap<String, BecPuestoAlumno> mapa = new HashMap<String, BecPuestoAlumno>();
			Statement st 			= conn.createStatement();
			ResultSet rs 			= null;
			String comando			= "";
			try{
				comando = "SELECT PUESTO_ID, ID_EJERCICIO, ID_CCOSTO, CATEGORIA_ID, CODIGO_PERSONAL," +
						" TO_CHAR(FECHA_INI, 'DD/MM/YYYY') AS FECHA_INI, TO_CHAR(FECHA_FIN, 'DD/MM/YYYY') AS FECHA_FIN," +
						" TIPO, ESTADO, USUARIO, PERIODO_ID, NIVEL_ID, PLAN_ID, DESCRIPCION" +
						" FROM ENOC.BEC_PUESTO_ALUMNO" +
						" WHERE ID_EJERCICIO = '"+idEjercicio+"'" +
						" AND TO_DATE('"+fecha+"','DD/MM/YYYY') BETWEEN FECHA_INI AND FECHA_FIN";
				rs = st.executeQuery(comando);
				while (rs.next()){
					BecPuestoAlumno obj = new BecPuestoAlumno();
					obj.mapeaReg(rs);				
					mapa.put(rs.getString("CODIGO_PERSONAL"), obj);
				}
				
			}catch(Exception ex){
				System.out.println("Error - aca.vista.BecPuestoAlumnoUtil |getPuestoAlumno|:"+ex);
			}finally{
				try { rs.close(); } catch (Exception ignore) { }
				try { st.close(); } catch (Exception ignore) { }
			}
			
			return mapa;
		}
	
	public ArrayList<BecPuestoAlumno> getListAll(Connection conn, String orden) throws SQLException{
			
		ArrayList<BecPuestoAlumno> lis 			= new ArrayList<BecPuestoAlumno>();
		Statement st 							= conn.createStatement();
		ResultSet rs 							= null;
		String comando							= "";
		
		try{
			comando = "SELECT * FROM ENOC.BEC_PUESTO_ALUMNO "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				BecPuestoAlumno obj = new BecPuestoAlumno();
				obj.mapeaReg(rs);
				lis.add(obj);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecPuestoAlumnoUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lis;
	}
	
	public ArrayList<BecPuestoAlumno> getListAllEjercicio(Connection conn, String idEjercicio, String orden) throws SQLException{
		
		ArrayList<BecPuestoAlumno> lis 			= new ArrayList<BecPuestoAlumno>();
		Statement st 							= conn.createStatement();
		ResultSet rs 							= null;
		String comando							= "";
		
		try{
			comando = "SELECT COALESCE(PUESTO_ID,'-') AS PUESTO_ID, ID_EJERCICIO, ID_CCOSTO, CATEGORIA_ID, CODIGO_PERSONAL," +
					" TO_CHAR(FECHA_INI, 'DD/MM/YYYY') AS FECHA_INI, TO_CHAR(FECHA_FIN, 'DD/MM/YYYY') AS FECHA_FIN, "+
					" TIPO, ESTADO, USUARIO, PERIODO_ID, NIVEL_ID, PLAN_ID, DESCRIPCION"+
					" FROM ENOC.BEC_PUESTO_ALUMNO WHERE ID_EJERCICIO = '"+idEjercicio+"' "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				BecPuestoAlumno obj= new BecPuestoAlumno();
				obj.mapeaReg(rs);
				lis.add(obj);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecPuestoAlumnoUtil|getListAllEjercicio|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lis;
	}
	
	public ArrayList<BecPuestoAlumno> getListDepartamento(Connection conn, String idEjercicio, String idCcosto, String orden) throws SQLException{
		
		ArrayList<BecPuestoAlumno> lis 			= new ArrayList<BecPuestoAlumno>();
		Statement st 							= conn.createStatement();
		ResultSet rs 							= null;
		String comando							= "";
		
		try{
			comando = "SELECT PUESTO_ID, ID_EJERCICIO, ID_CCOSTO, CATEGORIA_ID, CODIGO_PERSONAL," +
					" TO_CHAR(FECHA_INI, 'DD/MM/YYYY') AS FECHA_INI, TO_CHAR(FECHA_FIN, 'DD/MM/YYYY') AS FECHA_FIN, TIPO, ESTADO, USUARIO, PERIODO_ID, NIVEL_ID, PLAN_ID, DESCRIPCION" +
					" FROM ENOC.BEC_PUESTO_ALUMNO" +
					" WHERE ID_EJERCICIO = '"+idEjercicio+"' " +
					" AND ID_CCOSTO = '"+idCcosto+"' "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				BecPuestoAlumno obj= new BecPuestoAlumno();
				obj.mapeaReg(rs);
				lis.add(obj);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecPuestoAlumnoUtil|getListDepartamento|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lis;
	}
		
	public ArrayList<BecPuestoAlumno> getListPuestosActivos(Connection conn, String idEjercicio, String idCcosto, String categoriaId, String orden) throws SQLException{
		
		ArrayList<BecPuestoAlumno> lis 			= new ArrayList<BecPuestoAlumno>();
		Statement st 							= conn.createStatement();
		ResultSet rs 							= null;
		String comando							= "";
		
		try{
			comando = "SELECT PUESTO_ID, ID_EJERCICIO, ID_CCOSTO, CATEGORIA_ID, CODIGO_PERSONAL," +
					" TO_CHAR(FECHA_INI, 'DD/MM/YYYY') AS FECHA_INI, TO_CHAR(FECHA_FIN, 'DD/MM/YYYY') AS FECHA_FIN, TIPO, ESTADO, USUARIO, PERIODO_ID, NIVEL_ID, PLAN_ID, DESCRIPCION" +
					" FROM ENOC.BEC_PUESTO_ALUMNO " +
					" WHERE ID_EJERCICIO = '"+idEjercicio+"' " +
					" AND now() BETWEEN FECHA_INI AND FECHA_FIN" +
					" AND ID_CCOSTO = '"+idCcosto+"' AND CATEGORIA_ID = '"+categoriaId+"' "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				BecPuestoAlumno obj= new BecPuestoAlumno();
				obj.mapeaReg(rs);
				lis.add(obj);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecPuestoAlumnoUtil|getListPuestos|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lis;
	}
	
	public ArrayList<BecPuestoAlumno> getListPuestosFecha(Connection conn, String fechaPuesto, String idEjercicio, String idCcosto, String categoriaId, String orden) throws SQLException{
		
		ArrayList<BecPuestoAlumno> lis 			= new ArrayList<BecPuestoAlumno>();
		Statement st 							= conn.createStatement();
		ResultSet rs 							= null;
		String comando							= "";
		
		try{
			comando = "SELECT PUESTO_ID, ID_EJERCICIO, ID_CCOSTO, CATEGORIA_ID, CODIGO_PERSONAL," +
					" TO_CHAR(FECHA_INI, 'DD/MM/YYYY') AS FECHA_INI, TO_CHAR(FECHA_FIN, 'DD/MM/YYYY') AS FECHA_FIN, TIPO, ESTADO, USUARIO, PERIODO_ID, NIVEL_ID, PLAN_ID, DESCRIPCION" +
					" FROM ENOC.BEC_PUESTO_ALUMNO " +
					" WHERE ID_EJERCICIO = '"+idEjercicio+"' " +
					" AND TO_DATE('"+fechaPuesto+"', 'DD/MM/YYYY') BETWEEN FECHA_INI AND FECHA_FIN" +
					" AND ID_CCOSTO = '"+idCcosto+"' AND CATEGORIA_ID = '"+categoriaId+"' "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				BecPuestoAlumno obj= new BecPuestoAlumno();
				obj.mapeaReg(rs);
				lis.add(obj);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecPuestoAlumnoUtil|getListPuestosFecha|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lis;
	}
	
	public ArrayList<BecPuestoAlumno> getListPuestos(Connection conn, String idEjercicio, String idCcosto, String categoriaId, String orden) throws SQLException{
		
		ArrayList<BecPuestoAlumno> lis 			= new ArrayList<BecPuestoAlumno>();
		Statement st 							= conn.createStatement();
		ResultSet rs 							= null;
		String comando							= "";
		
		try{
			comando = "SELECT PUESTO_ID, ID_EJERCICIO, ID_CCOSTO, CATEGORIA_ID, CODIGO_PERSONAL," +
					" TO_CHAR(FECHA_INI, 'DD/MM/YYYY') AS FECHA_INI, TO_CHAR(FECHA_FIN, 'DD/MM/YYYY') AS FECHA_FIN, TIPO, ESTADO, USUARIO, PERIODO_ID, NIVEL_ID, PLAN_ID, DESCRIPCION" +
					" FROM ENOC.BEC_PUESTO_ALUMNO WHERE ID_EJERCICIO = '"+idEjercicio+"' " +
					" AND ID_CCOSTO = '"+idCcosto+"' AND CATEGORIA_ID = '"+categoriaId+"' "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				BecPuestoAlumno obj= new BecPuestoAlumno();
				obj.mapeaReg(rs);
				lis.add(obj);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecPuestoAlumnoUtil|getListPuestos|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lis;
	}
	
	public ArrayList<BecPuestoAlumno> getListPuestosNivel(Connection conn, String idEjercicio, String idCcosto, String categoriaId, String nivelId, String orden) throws SQLException{
		
		ArrayList<BecPuestoAlumno> lis 			= new ArrayList<BecPuestoAlumno>();
		Statement st 							= conn.createStatement();
		ResultSet rs 							= null;
		String comando							= "";
		
		try{
			comando = "SELECT PUESTO_ID, ID_EJERCICIO, ID_CCOSTO, CATEGORIA_ID, CODIGO_PERSONAL," +
					" TO_CHAR(FECHA_INI, 'DD/MM/YYYY') AS FECHA_INI, TO_CHAR(FECHA_FIN, 'DD/MM/YYYY') AS FECHA_FIN, TIPO, ESTADO, USUARIO, PERIODO_ID, NIVEL_ID, PLAN_ID, DESCRIPCION" +
					" FROM ENOC.BEC_PUESTO_ALUMNO WHERE ID_EJERCICIO = '"+idEjercicio+"' " +
					" AND ID_CCOSTO = '"+idCcosto+"' AND CATEGORIA_ID = '"+categoriaId+"' AND NIVEL_ID IN ( "+nivelId+" )"+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				BecPuestoAlumno obj= new BecPuestoAlumno();
				obj.mapeaReg(rs);
				lis.add(obj);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecPuestoAlumnoUtil|getListPuestosNivel|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lis;
	}
	
	public ArrayList<BecPuestoAlumno> getListPuestosSC(Connection conn, String idEjercicio, String idCcosto, String orden) throws SQLException{
		
		ArrayList<BecPuestoAlumno> lis 			= new ArrayList<BecPuestoAlumno>();
		Statement st 							= conn.createStatement();
		ResultSet rs 							= null;
		String comando							= "";
		
		try{
			comando = "SELECT PUESTO_ID, ID_EJERCICIO, ID_CCOSTO, CATEGORIA_ID, CODIGO_PERSONAL," +
					" TO_CHAR(FECHA_INI, 'DD/MM/YYYY') AS FECHA_INI, TO_CHAR(FECHA_FIN, 'DD/MM/YYYY') AS FECHA_FIN, TIPO, ESTADO, USUARIO, PERIODO_ID, NIVEL_ID, PLAN_ID, DESCRIPCION" +
					" FROM ENOC.BEC_PUESTO_ALUMNO" +
					" WHERE ID_EJERCICIO = '"+idEjercicio+"' " +
					" AND ID_CCOSTO = '"+idCcosto+"'" +
					" AND now() BETWEEN FECHA_INI AND FECHA_FIN "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				BecPuestoAlumno obj= new BecPuestoAlumno();
				obj.mapeaReg(rs);
				lis.add(obj);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecPuestoAlumnoUtil|getListPuestosSC|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lis;
	}
	
	public ArrayList<BecPuestoAlumno> getListAlumnosCategoria(Connection conn, String idEjercicio, String categoriaId,String periodoId, String orden) throws SQLException{
		
		ArrayList<BecPuestoAlumno> lis 			= new ArrayList<BecPuestoAlumno>();
		Statement st 							= conn.createStatement();
		ResultSet rs 							= null;
		String comando							= "";
		
		try{
			comando = "SELECT * FROM ENOC.BEC_PUESTO_ALUMNO WHERE ID_EJERCICIO = '"+idEjercicio+"' AND CATEGORIA_ID = '"+categoriaId+"' "
					+ "AND PERIODO_ID = '"+periodoId+"' AND now() BETWEEN FECHA_INI AND FECHA_FIN "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				BecPuestoAlumno obj= new BecPuestoAlumno();
				obj.mapeaReg(rs);
				lis.add(obj);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecPuestoAlumnoUtil|getListAlumnosCategoria|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lis;
	}
	
	public ArrayList<BecPuestoAlumno> listAlumnosEnCategoria(Connection conn, String idEjercicio, String categoriaId, String fecha, String orden) throws SQLException{
		
		ArrayList<BecPuestoAlumno> lis 			= new ArrayList<BecPuestoAlumno>();
		Statement st 							= conn.createStatement();
		ResultSet rs 							= null;
		String comando							= "";
		
		try{
			comando = "SELECT * FROM ENOC.BEC_PUESTO_ALUMNO WHERE ID_EJERCICIO = '"+idEjercicio+"' AND CATEGORIA_ID = '"+categoriaId+"' "
					+ "AND TO_DATE('"+fecha+"') BETWEEN FECHA_INI AND FECHA_FIN "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				BecPuestoAlumno obj= new BecPuestoAlumno();
				obj.mapeaReg(rs);
				lis.add(obj);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecPuestoAlumnoUtil|listAlumnosEnCategoria|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lis;
	}
	
	public HashMap<String,BecPuestoAlumno> getMapPuestos(Connection conn, String idEjercicio) throws SQLException{
		
		HashMap<String, BecPuestoAlumno> mapa = new HashMap<String, BecPuestoAlumno>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando			= "";
		try{
			comando = "SELECT PUESTO_ID, ID_EJERCICIO, ID_CCOSTO, CATEGORIA_ID, CODIGO_PERSONAL," +
					" TO_CHAR(FECHA_INI, 'DD/MM/YYYY') AS FECHA_INI, TO_CHAR(FECHA_FIN, 'DD/MM/YYYY') AS FECHA_FIN," +
					" TIPO, ESTADO, USUARIO, PERIODO_ID, NIVEL_ID, PLAN_ID, DESCRIPCION" +
					" FROM ENOC.BEC_PUESTO_ALUMNO" +
					" WHERE ID_EJERCICIO = '"+idEjercicio+"'";
			rs = st.executeQuery(comando);
			while (rs.next()){
				BecPuestoAlumno obj = new BecPuestoAlumno();
				obj.mapeaReg(rs);				
				mapa.put(rs.getString("PUESTO_ID"), obj);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.vista.BecPuestoAlumnoUtil |getMapPuestos|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return mapa;
	}
	
	public ArrayList<BecPuestoAlumno> getListAllEjerciciosAlum(Connection conn, String codigoPersonal, String orden) throws SQLException{
			
			ArrayList<BecPuestoAlumno> lis 			= new ArrayList<BecPuestoAlumno>();
			Statement st 							= conn.createStatement();
			ResultSet rs 							= null;
			String comando							= "";
			
			try{
				comando = "SELECT PUESTO_ID, ID_EJERCICIO, ID_CCOSTO, CATEGORIA_ID, CODIGO_PERSONAL," +
						" TO_CHAR(FECHA_INI, 'DD/MM/YYYY') AS FECHA_INI, TO_CHAR(FECHA_FIN, 'DD/MM/YYYY') AS FECHA_FIN, TIPO, ESTADO, USUARIO, PERIODO_ID, NIVEL_ID, PLAN_ID, DESCRIPCION"+
						" FROM ENOC.BEC_PUESTO_ALUMNO WHERE CODIGO_PERSONAL = '"+codigoPersonal+"' "+orden;
				
				rs = st.executeQuery(comando);
				while (rs.next()){
					
					BecPuestoAlumno obj= new BecPuestoAlumno();
					obj.mapeaReg(rs);
					lis.add(obj);
				}
				
			}catch(Exception ex){
				System.out.println("Error - aca.bec.BecPuestoAlumnoUtil|getListAllEjercicio|:"+ex);
			}finally{
				try { rs.close(); } catch (Exception ignore) { }
				try { st.close(); } catch (Exception ignore) { }
			}
			
			return lis;
	}
	
	public HashMap<String,String> mapAlumCarrera(Connection conn, String idEjercicio, String fecha) throws SQLException{
		
		HashMap<String, String> mapa = new HashMap<String, String>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando			= "";
		try{
			comando = "SELECT CODIGO_PERSONAL, CARRERA_ID FROM ENOC.ALUM_ESTADO"
					+ " WHERE CODIGO_PERSONAL||CARGA_ID||BLOQUE_ID IN"
					+ "		(SELECT MATRICULA||CARGA_ID||BLOQUE FROM MATEO.FES_CC_AFE_ACUERDOS"
					+ "		WHERE ACUERDO_EJERCICIO_ID = '"+idEjercicio+"'"
					+ "		AND TIPO_ID IN (1,3)"
					+ "		AND MATRICULA||ALPUESTO_PUESTO_ID IN"
					+ "			(SELECT CODIGO_PERSONAL||PUESTO_ID FROM ENOC.BEC_PUESTO_ALUMNO"
					+ "			WHERE ID_EJERCICIO = '"+idEjercicio+"'"
					+ " 		AND FECHA_INI<= TO_DATE('"+fecha+"', 'DD/MM/YYYY') AND FECHA_FIN>=TO_DATE('"+fecha+"', 'DD/MM/YYYY')))";
			
			rs = st.executeQuery(comando);
			while (rs.next()){				
				mapa.put(rs.getString("CODIGO_PERSONAL"), rs.getString("CARRERA_ID"));				
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.becPuestoAlumno|mapAlumCarrera|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return mapa;
	}
	
	public HashMap<String,String> mapAlumCarrera(Connection conn, String idEjercicio, String fecha, String tipos) throws SQLException{
		
		HashMap<String, String> mapa = new HashMap<String, String>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando			= "";
		try{
			comando = "SELECT CODIGO_PERSONAL, CARRERA_ID FROM ENOC.ALUM_ESTADO"
					+ " WHERE SUBSTR(CARGA_ID,5,1) IN ("+tipos+")"
					+ " AND CODIGO_PERSONAL||CARGA_ID||BLOQUE_ID IN"
					+ "		(SELECT MATRICULA||CARGA_ID||BLOQUE FROM MATEO.FES_CC_AFE_ACUERDOS"
					+ "		WHERE ACUERDO_EJERCICIO_ID = '"+idEjercicio+"'"
					+ "		AND TIPO_ID IN (1,3)"
					+ "		AND MATRICULA||ALPUESTO_PUESTO_ID IN"
					+ "			(SELECT CODIGO_PERSONAL||PUESTO_ID FROM ENOC.BEC_PUESTO_ALUMNO"
					+ "			WHERE ID_EJERCICIO = '"+idEjercicio+"'"
					+ " 		AND FECHA_INI<= TO_DATE('"+fecha+"', 'DD/MM/YYYY') AND FECHA_FIN>=TO_DATE('"+fecha+"', 'DD/MM/YYYY')))";
			
			rs = st.executeQuery(comando);
			while (rs.next()){				
				mapa.put(rs.getString("CODIGO_PERSONAL"), rs.getString("CARRERA_ID"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.becPuestoAlumno|mapAlumCarrera|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return mapa;
	}
	
	/** CUENTA EL NUMERO DE PUESTOS OCUPADOS EN UNA CATEGORIA DURANTE UN EJERCICO Y PERIODO */
	public static HashMap<String, String> mapTotPuestosEnCategoria(Connection conn, String ejercicioId, String periodoId)throws SQLException{
		HashMap<String, String> map = new HashMap<String, String>();
		
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;		 
		String comando	= "";
		
		try{			
			comando = " SELECT CATEGORIA_ID, COALESCE(COUNT(CATEGORIA_ID),0) AS TOTAL FROM ENOC.BEC_PUESTO_ALUMNO"
					+ " WHERE ID_EJERCICIO = '"+ejercicioId+"' AND PERIODO_ID = '"+periodoId+"' GROUP BY CATEGORIA_ID";
			//System.out.println(comando);
			rs = st.executeQuery(comando);
			while(rs.next()){
				map.put(rs.getString("CATEGORIA_ID"), rs.getString("TOTAL"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.becPuestoAlumno|mapTotPuestosEnCategoria|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}		
		return map;
	}
	
	/** CUENTA EL NUMERO DE PUESTOS OCUPADOS EN UNA CATEGORIA DURANTE UN EJERCICO Y FECHA */
	public static HashMap<String, String> mapTotPuestosPorCategoria(Connection conn, String ejercicioId, String fecha)throws SQLException{
		HashMap<String, String> map = new HashMap<String, String>();
		
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;		 
		String comando	= "";
		
		try{			
			comando = " SELECT CATEGORIA_ID, COALESCE(COUNT(CATEGORIA_ID),0) AS TOTAL FROM ENOC.BEC_PUESTO_ALUMNO"
					+ " WHERE ID_EJERCICIO = '"+ejercicioId+"' AND TO_DATE('"+fecha+"', 'DD/MM/YYYY') BETWEEN FECHA_INI AND FECHA_FIN"
					+ " GROUP BY CATEGORIA_ID";
			rs = st.executeQuery(comando);
			while(rs.next()){
				map.put(rs.getString("CATEGORIA_ID"), rs.getString("TOTAL"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.bec.becPuestoAlumno|mapTotPuestosPorCategoria|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}		
		return map;
	}
	
}