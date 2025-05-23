package aca.cultural;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

import aca.cultural.CompRegistro;

public class CompRegistroUtil{
	
	public boolean insertRegByte(Connection conn, CompRegistro compRegistro ) throws Exception{
		PreparedStatement ps 	= null;
		boolean ok 				= false;
		
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.COMP_REGISTRO"+ 
				"(EVENTO_ID, CODIGO_PERSONAL, FECHA, ESTADO, USUARIO, FACULTAD_ID, CARRERA_ID, FECHA_REGISTRO) "+
				"VALUES( TO_NUMBER(?,'99'),?,TO_DATE(?,'DD/MM/YYYY'),?,?,?,?,TO_DATE(?,'DD/MM/YYYY'))");

			ps.setString(1, compRegistro.getEventoId());
			ps.setString(2, compRegistro.getCodigoPersonal());
			ps.setString(3, compRegistro.getFecha());
			ps.setString(4, compRegistro.getEstado());
			ps.setString(5, compRegistro.getUsuario());
			ps.setString(6, compRegistro.getFacultadId());
			ps.setString(7, compRegistro.getCarreraId());
			ps.setString(8, compRegistro.getFechaRegistro());
			
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;		
			
		}catch(Exception ex){
			System.out.println("Error - aca.cultural.CompRegistroUtil|insertRegByte|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
		
	public boolean updateEstado(Connection conn, CompRegistro compRegistro ) throws Exception{
		PreparedStatement ps 	= null;		
		boolean ok 				= false;
		
		try{
			ps = conn.prepareStatement("UPDATE ENOC.COMP_REGISTRO "+ 
				"SET ESTADO = ?,"+
				"USUARIO = ?,"+
				"FECHA = TO_DATE(?,'DD/MM/YYYY')"+
				"WHERE EVENTO_ID = TO_NUMBER(?,'99') AND CODIGO_PERSONAL = ?");
			
			ps.setString(1, compRegistro.getEstado());
			ps.setString(2, compRegistro.getUsuario());
			ps.setString(3, compRegistro.getFecha());
			ps.setString(4, compRegistro.getEventoId());
			ps.setString(5, compRegistro.getCodigoPersonal());
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;	
			
		}catch(Exception ex){
			System.out.println("Error - aca.cultural.CompRegistroUtil|updateEstado|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean deleteReg(Connection conn, String eventoId, String codigoPersonal ) throws Exception{
		PreparedStatement ps 	= null;
		boolean ok 				= false;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.COMP_REGISTRO "+ 
				"WHERE EVENTO_ID = TO_NUMBER(?,'99') AND CODIGO_PERSONAL = ? ");
			
			ps.setString(1, eventoId);
			ps.setString(2, codigoPersonal);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;		
			
		}catch(Exception ex){
			System.out.println("Error - aca.cultural.CompRegistroUtil|deleteReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean deleteRegistro(Connection conn, String eventoId ) throws Exception{
		PreparedStatement ps 	= null;
		boolean ok 				= false;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.COMP_REGISTRO "+ 
				"WHERE EVENTO_ID = TO_NUMBER(?,'99')");
			ps.setString(1, eventoId);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;		
			
		}catch(Exception ex){
			System.out.println("Error - aca.cultural.CompRegistroUtil|deleteRegistro|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean deleteRegistroFacultad(Connection conn,String facultad ) throws Exception{
		PreparedStatement ps 	= null;
		boolean ok 				= false;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.COMP_REGISTRO "+ 
				"WHERE FACULTAD_ID = '"+facultad+"' ");
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;		
			
		}catch(Exception ex){
			System.out.println("Error - aca.cultural.CompRegistroUtil|deleteRegistroFacultad|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean deleteRegistrofacsa(Connection conn, String facultad, String carrera ) throws Exception{
		PreparedStatement ps 	= null;
		boolean ok 				= false;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.COMP_REGISTRO "+ 
				"WHERE FACULTAD_ID = '"+facultad+"' AND CARRERA_ID = '"+carrera+"' ");
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;		
			
		}catch(Exception ex){
			System.out.println("Error - aca.cultural.CompRegistroUtil|deleteRegistrofacsa|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public CompRegistro mapeaRegId( Connection conn, String eventoId, String codigoPersonal) throws SQLException{
		CompRegistro compRegistro = new CompRegistro();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
		ps = conn.prepareStatement("SELECT "+
			" EVENTO_ID, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA, CODIGO_PERSONAL, ESTADO, USUARIO, FACULTAD_ID, CARRERA_ID, TO_CHAR(FECHA,'DD/MM/YYYY') AS FECHA_REGISTRO"+
			" FROM ENOC.COMP_REGISTRO"+ 
			" WHERE EVENTO_ID = '"+eventoId+"' AND CODIGO_PERSONAL = '"+codigoPersonal+"'");
		
		rs = ps.executeQuery();
		if (rs.next()){			
			compRegistro.mapeaReg(rs);
		}
		}catch(Exception ex){
			System.out.println("Error - aca.cultural.CompRegistroUtil|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return compRegistro;
	}

	public boolean existeReg(Connection conn, String eventoId) throws SQLException{
		
		PreparedStatement ps	= null;
		boolean 		ok 		= false;
		ResultSet 		rs		= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.COMP_REGISTRO"
					+ " WHERE EVENTO_ID = TO_NUMBER(?,'99')");
			
			ps.setString(1, eventoId);		
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.cultural.CompRegistroUtil|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean existeReg(Connection conn, String eventoId, String codigoPersonal) throws SQLException{
		
		PreparedStatement ps	= null;
		boolean 		ok 		= false;
		ResultSet 		rs		= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.COMP_REGISTRO "+ 
				"WHERE EVENTO_ID = TO_NUMBER(?,'99') AND CODIGO_PERSONAL = ?");
			
			ps.setString(1, eventoId);
			ps.setString(2, codigoPersonal);
			
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.cultural.CompRegistroUtil|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public ArrayList<CompRegistro> getListAll(Connection conn, String orden) throws SQLException{
		
		ArrayList<CompRegistro> lis		= new ArrayList<CompRegistro>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		
		try{
			comando = "SELECT EVENTO_ID, CODIGO_PERSONAL, ESTADO, TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA, USUARIO, FACULTAD_ID, CARRERA_ID, TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA_REGISTRO"
					+ " FROM ENOC.COMP_REGISTRO "+orden;	 
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				CompRegistro obj = new CompRegistro();
				obj.mapeaReg(rs);
				lis.add(obj);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.cultural.CompRegistroUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lis;
	}
	
	public ArrayList<CompRegistro> acreditados(Connection conn,String matricula, String orden) throws SQLException{
			
			ArrayList<CompRegistro> lis		= new ArrayList<CompRegistro>();
			Statement st 		= conn.createStatement();
			ResultSet rs 		= null;
			String comando		= "";
			
			try{
				comando = "SELECT * FROM ENOC.COMP_REGISTRO WHERE CODIGO_PERSONAL ='"+matricula+"' AND ESTADO='A' "+orden;	 
				rs = st.executeQuery(comando);
				while (rs.next()){
					
					CompRegistro obj = new CompRegistro();
					obj.mapeaReg(rs);
					lis.add(obj);
				}
				
			}catch(Exception ex){
				System.out.println("Error - aca.cultural.CompRegistroUtil|acreditados|:"+ex);
			}finally{
				try { rs.close(); } catch (Exception ignore) { }
				try { st.close(); } catch (Exception ignore) { }
			}
			return lis;
		}
	
	public ArrayList<CompRegistro> reservados(Connection conn,String matricula, String orden) throws SQLException{
			
			ArrayList<CompRegistro> lis		= new ArrayList<CompRegistro>();
			Statement st 					= conn.createStatement();
			ResultSet rs 					= null;
			String comando					= "";
			
			try{
				comando = "SELECT * FROM ENOC.COMP_REGISTRO WHERE CODIGO_PERSONAL ='"+matricula+"' AND ESTADO='R' "+orden;	 
				rs = st.executeQuery(comando);
				while (rs.next()){
					
					CompRegistro obj = new CompRegistro();
					obj.mapeaReg(rs);
					lis.add(obj);
				}
				
			}catch(Exception ex){
				System.out.println("Error - aca.cultural.CompRegistroUtil|reservados|:"+ex);
			}finally{
				try { rs.close(); } catch (Exception ignore) { }
				try { st.close(); } catch (Exception ignore) { }
			}
			
			return lis;
		}
	
	public ArrayList<String> amigos(Connection conn,String eventoId, String orden) throws SQLException{
		
		ArrayList<String> lis		= new ArrayList<String>();
		Statement st 					= conn.createStatement();
		ResultSet rs 					= null;
		String comando					= "";
		
		try{
			comando = " SELECT "+
					  " AP.APELLIDO_PATERNO ||' '|| AP.APELLIDO_MATERNO||' '|| AP.NOMBRE AS NOMBRE, CR.CODIGO_PERSONAL AS CODIGO_PERSONAL"+ 
					  " FROM ENOC.COMP_REGISTRO CR, ENOC.ALUM_PERSONAL AP "+
					  " WHERE AP.CODIGO_PERSONAL = CR.CODIGO_PERSONAL"+
					  " AND CR.EVENTO_ID = '"+eventoId+"' "+orden;	 
			rs = st.executeQuery(comando);
			while (rs.next()){
				lis.add(rs.getString("NOMBRE")+"@@"+rs.getString("CODIGO_PERSONAL"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.cultural.CompRegistroUtil|amigos|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lis;
	}
	
	public ArrayList<String> tieneLegadooAsamblea(Connection conn,String facultadId, String orden) throws SQLException{
		
		ArrayList<String> lis		= new ArrayList<String>();
		Statement st 					= conn.createStatement();
		ResultSet rs 					= null;
		String comando					= "";
		
		try{
			comando = " SELECT CODIGO_PERSONAL FROM ENOC.COMP_REGISTRO"
					+ " WHERE ESTADO = 'A' AND FACULTAD_ID = '"+facultadId+"' "+
					  " UNION "+
                      " SELECT CODIGO_PERSONAL FROM ENOC.COMP_ASAMBLEA_REGISTRO WHERE FACULTAD_ID = '"+facultadId+ "' "+orden;	 
			rs = st.executeQuery(comando);
			while (rs.next()){
				lis.add(rs.getString("CODIGO_PERSONAL"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.cultural.CompRegistroUtil|tieneLegadooAsamblea|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lis;
	}
	
	public ArrayList<String> tieneLegadooAsambleaFacsa(Connection conn,String facultadId, String carreraId, String orden) throws SQLException{
		
		ArrayList<String> lis		= new ArrayList<String>();
		Statement st 					= conn.createStatement();
		ResultSet rs 					= null;
		String comando					= "";
		
		try{
			comando = " SELECT CODIGO_PERSONAL FROM " +
					  " ENOC.COMP_REGISTRO WHERE ESTADO = 'A' AND FACULTAD_ID = '"+facultadId+"' AND CARRERA_ID = '"+carreraId+"' "+
					  " UNION "+
                      " SELECT CODIGO_PERSONAL FROM" +
                      " ENOC.COMP_ASAMBLEA_REGISTRO WHERE FACULTAD_ID = '"+facultadId+ "' AND CARRERA_ID = '"+carreraId+"' "+orden;	 
			rs = st.executeQuery(comando);
			while (rs.next()){
				lis.add(rs.getString("CODIGO_PERSONAL"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.cultural.CompRegistroUtil|tieneLegadooAsambleaFacsa|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lis;
	}
	
	public static String getCantidadReservados(Connection conn, String eventoId) throws SQLException{
			
			String cantidad 				= "0";
			Statement st 					= conn.createStatement();
			ResultSet rs 					= null;
			String comando					= "";
			
			try{
				comando = "SELECT COUNT(EVENTO_ID) AS CONTADOR FROM ENOC.COMP_REGISTRO WHERE EVENTO_ID='"+eventoId+"'";
				
				rs = st.executeQuery(comando);
				while (rs.next()){
					cantidad = rs.getString("CONTADOR");
				}
				
			}catch(Exception ex){
				System.out.println("Error - aca.cultural.CompRegistroUtil|getCantidadReservados|:"+ex);
			}finally{
				try { rs.close(); } catch (Exception ignore) { }
				try { st.close(); } catch (Exception ignore) { }
			}
			
			return cantidad;
	}
	
	public ArrayList<String> accesoFacultades(Connection conn, String codigoPersonal, String orden ) throws SQLException{
		
		ArrayList<String> lisAsambleas	= new ArrayList<String>();
		Statement st 			= conn.createStatement();
		ResultSet rs 			= null;
		String comando	= "";
		
		try{
			comando = " SELECT FACULTAD_ID FROM ENOC.CAT_CARRERA WHERE INSTR((SELECT ACCESOS FROM ENOC.ACCESO WHERE CODIGO_PERSONAL = '"+codigoPersonal+"'),CARRERA_ID)>0"+ orden; 
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				lisAsambleas.add(rs.getString("FACULTAD_ID"));
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.cultural.CompRegistroUtil|accesoFacultades|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}		
		
		return lisAsambleas;
	}
	
	public static String getCantidadLegados(Connection conn, String codigoPersonal, String facultadId) throws SQLException{
		
		String cantidad 				= "0";
		Statement st 					= conn.createStatement();
		ResultSet rs 					= null;
		String comando					= "";
		
		try{
			comando = " SELECT COUNT(CODIGO_PERSONAL) AS NUMLEGADOS FROM ENOC.COMP_REGISTRO " +
					  " WHERE CODIGO_PERSONAL='"+codigoPersonal+"' AND FACULTAD_ID = '"+facultadId+"' " +
					  " AND ESTADO = 'A'";
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				cantidad = rs.getString("NUMLEGADOS");
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.cultural.CompRegistroUtil|getCantidadLegados|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return cantidad;
	}
	
	public HashMap<String,CompRegistro> mapLeg(Connection conn) throws SQLException{
		
		HashMap<String,CompRegistro> map = new HashMap<String,CompRegistro>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando				= "";
		String llave				= "";
		
		try{
			comando = " SELECT EVENTO_ID, CODIGO_PERSONAL, ESTADO, FECHA, USUARIO, FACULTAD_ID, CARRERA_ID FROM ENOC.COMP_REGISTRO";	 
			
			rs = st.executeQuery(comando);
			while (rs.next()){				
				CompRegistro obj = new CompRegistro();
				obj.mapeaReg(rs);
				llave = obj.getFacultadId()+","+obj.getCodigoPersonal();
				map.put(llave, obj);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.est.CompRegistroUtil|mapLeg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return map;
	}
	
	public HashMap<String,CompRegistro> mapLegFacsa(Connection conn) throws SQLException{
		
		HashMap<String,CompRegistro> map = new HashMap<String,CompRegistro>();
		Statement st 				= conn.createStatement();
		ResultSet rs 				= null;
		String comando				= "";
		String llave				= "";
		
		try{
			comando = " SELECT CODIGO_PERSONAL, FACULTAD_ID, EVENTO_ID, FECHA, USUARIO, ESTADO, CARRERA_ID, FECHA_REGISTRO FROM ENOC.COMP_REGISTRO";	 
			
			rs = st.executeQuery(comando);
			while (rs.next()){				
				CompRegistro obj = new CompRegistro();
				obj.mapeaReg(rs);
				llave = obj.getFacultadId()+","+obj.getCodigoPersonal()+","+obj.getCarreraId();
				map.put(llave, obj);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.est.CompRegistroUtil|mapLegFacsa|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return map;
	}
	
	public String facultadAlumno(Connection conn, String codigoPersonal) throws SQLException{
		
		String  facultad		= "0";
		ResultSet rs 			= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement ( " SELECT ENOC.FACULTAD(CARRERA_ID) AS FACULTAD FROM ENOC.INSCRITOS WHERE CODIGO_PERSONAL = '"+codigoPersonal+"'");	 
			rs= ps.executeQuery();		
			if(rs.next()){
				facultad = rs.getString("FACULTAD");
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.cultural.CompRegistroUtil|facultadAlumno|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return facultad;
	}
	
	public String facultadMaestro(Connection conn, String codigoPersonal) throws SQLException{
		
		String  facultad		= "0";
		ResultSet rs 			= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement ( " SELECT DISTINCT FACULTAD_ID FROM ENOC.CAT_CARRERA " +
										 " WHERE INSTR((SELECT ACCESOS FROM ENOC.ACCESO WHERE " +
										 " CODIGO_PERSONAL = '"+codigoPersonal+"'),CARRERA_ID)>0");	 
			rs= ps.executeQuery();		
			if(rs.next()){
				facultad = rs.getString("FACULTAD_ID");
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.cultural.CompRegistroUtil|facultadMaestro|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return facultad;
	}
	
	public String carreraAlumno(Connection conn, String codigoPersonal) throws SQLException{
		
		String  carrera		= "0";
		ResultSet rs 			= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement ( " SELECT CARRERA_ID FROM ENOC.INSCRITOS WHERE CODIGO_PERSONAL = '"+codigoPersonal+"' ");	 
			rs= ps.executeQuery();		
			if(rs.next()){
				carrera = rs.getString("CARRERA_ID");
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.cultural.CompRegistroUtil|carreraAlumno|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return carrera;
	}
	
	public String carreraMaestro(Connection conn, String codigoPersonal) throws SQLException{
		
		String  carrera		= "0";
		ResultSet rs 			= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement ( " SELECT DISTINCT CARRERA_ID FROM ENOC.CAT_CARRERA " +
										 " WHERE INSTR((SELECT ACCESOS FROM ENOC.ACCESO WHERE " +
										 " CODIGO_PERSONAL = '"+codigoPersonal+"'),CARRERA_ID)>0");	 
			rs= ps.executeQuery();		
			if(rs.next()){
				carrera = rs.getString("CARRERA_ID");
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.cultural.CompRegistroUtil|carreraMaestro|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return carrera;
	}
}