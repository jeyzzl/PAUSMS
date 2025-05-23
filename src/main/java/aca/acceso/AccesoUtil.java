// Clase para la tabla de Acceso
package aca.acceso;

import java.security.MessageDigest;
import java.sql.*;
import java.util.ArrayList;

public class AccesoUtil{
	
	
	public boolean insertReg(Connection conn, Acceso acceso ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.ACCESO(CODIGO_PERSONAL, ADMINISTRADOR, SUPERVISOR, COTEJADOR, ACCESOS, MODALIDAD, USUARIO, CLAVE, INGRESO, CONVALIDA, BECAS, PORTAL_ALUMNO, PORTAL_MAESTRO, IDIOMA, MENU, ALUMNO_MOVIL, CLAVE_INICIAL) " + 
					"VALUES(?,?,?,?,?,TO_NUMBER(?,'99'),?,?,?,?,?,?,?,?,?,?,?)");
			ps.setString(1, acceso.getCodigoPersonal());
			ps.setString(2, acceso.getAdministrador());
			ps.setString(3, acceso.getSupervisor());
			ps.setString(4, acceso.getCotejador());
			ps.setString(5, acceso.getAccesos());
			ps.setString(6, acceso.getModalidad());
			ps.setString(7, acceso.getUsuario());
			ps.setString(8, acceso.getClave());
			ps.setString(9, acceso.getIngreso());
			ps.setString(10,acceso.getConvalida());
			ps.setString(11,acceso.getBecas());
			ps.setString(12,acceso.getPortalAlumno());
			ps.setString(13,acceso.getPortalMaestro());
			ps.setString(14,acceso.getIdioma());
			ps.setString(15,acceso.getMenu());
			ps.setString(16,acceso.getAlumnoMovil());
			ps.setString(17,acceso.getClaveInicial());
			if ( ps.executeUpdate()== 1){
				ok = true;
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.acceso.AccesoUtil|insertReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean updateReg(Connection conn, Acceso acceso ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.ACCESO SET ADMINISTRADOR = ?, SUPERVISOR= ?, COTEJADOR= ?, ACCESOS= ?, MODALIDAD= TO_NUMBER(?,'99'), USUARIO= ?, CLAVE= ?, INGRESO = ?, CONVALIDA = ?, BECAS = ?, PORTAL_ALUMNO = ?, "
					+ " PORTAL_MAESTRO = ?, IDIOMA = ?, MENU = ?, ALUMNO_MOVIL = ?, CLAVE_INICIAL = ?"
					+ "  WHERE CODIGO_PERSONAL = ?");			 
			ps.setString(1, acceso.getAdministrador());
			ps.setString(2, acceso.getSupervisor());
			ps.setString(3, acceso.getCotejador());
			ps.setString(4, acceso.getAccesos());
			ps.setString(5, acceso.getModalidad());
			ps.setString(6, acceso.getUsuario());
			ps.setString(7, acceso.getClave());
			ps.setString(8, acceso.getIngreso());
			ps.setString(9,acceso.getConvalida());
			ps.setString(10,acceso.getBecas());
			ps.setString(11,acceso.getPortalAlumno());
			ps.setString(12,acceso.getPortalMaestro());
			ps.setString(13,acceso.getIdioma());
			ps.setString(14,acceso.getMenu());
			ps.setString(15, acceso.getAlumnoMovil());
			ps.setString(16, acceso.getClaveInicial());
			ps.setString(17, acceso.getCodigoPersonal());
			
			
			if ( ps.executeUpdate()== 1){
				ok = true;			
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.acceso.AccesoUtil|updateReg|:"+ex); 
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean deleteReg(Connection conn, String codigoPersonal ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.ACCESO WHERE CODIGO_PERSONAL = ?"); 
			ps.setString(1,codigoPersonal);			
			if ( ps.executeUpdate()== 1){
				ok = true;				
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.acceso.AccesoUtil|deleteReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}	
	
	public Acceso mapeaRegId(Connection con, String codigoPersonal) throws SQLException{		
		Acceso acceso 			= new Acceso();
		PreparedStatement ps 	= null;
		ResultSet rs 			= null;
		try{			
			ps = con.prepareStatement("SELECT CODIGO_PERSONAL, ADMINISTRADOR, SUPERVISOR, COTEJADOR,"
					+ " COALESCE(ACCESOS,'X') AS ACCESOS, MODALIDAD, USUARIO, CLAVE, INGRESO, CONVALIDA,"
					+ " BECAS, PORTAL_ALUMNO, PORTAL_MAESTRO, IDIOMA, MENU, ALUMNO_MOVIL, CLAVE_INICIAL"
					+ " FROM ENOC.ACCESO WHERE CODIGO_PERSONAL = ? "); 
			ps.setString(1,codigoPersonal);
			rs = ps.executeQuery();
			
			if(rs.next()){
				acceso.mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.acceso.AccesoUtil|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return acceso;
	}
	
	public boolean existeReg(Connection conn, String codigoPersonal) throws SQLException{
		boolean ok 				= false;
		ResultSet rs 			= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.ACCESO WHERE CODIGO_PERSONAL = ? "); 
			ps.setString(1, codigoPersonal);
			rs= ps.executeQuery();		
			if(rs.next()){
				ok = true;
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.acceso.AccesoUtil|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public static boolean esAdministrador(Connection conn, String codigoPersonal) throws Exception{
		boolean admin=false;
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
		
		try{
			comando = "SELECT ADMINISTRADOR FROM ENOC.ACCESO WHERE CODIGO_PERSONAL = '"+codigoPersonal+"'";			 
			rs = st.executeQuery(comando);
			if (rs.next()){								
				if (rs.getString(1).equals("S")) admin = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.acceso.AccesoUtil|esAdministrador|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return admin;
	}
	
	public static boolean esSupervisor(Connection conn, String codigoPersonal) throws Exception{
		boolean supervisor=false;
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
		
		try{
			comando = "SELECT SUPERVISOR FROM ENOC.ACCESO WHERE CODIGO_PERSONAL = '"+codigoPersonal+"'";			 
			rs = st.executeQuery(comando);
			if (rs.next()){								
				if (rs.getString(1).equals("S")) supervisor = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.acceso.AccesoUtil|esSupervisor|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return supervisor;
	}
	
	public static boolean esCotejador(Connection conn, String codigoPersonal) throws Exception{
		boolean admin=false;
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
		
		try{
			comando = "SELECT COTEJADOR FROM ENOC.ACCESO WHERE CODIGO_PERSONAL = '"+codigoPersonal+"'";			 
			rs = st.executeQuery(comando);
			if (rs.next()){								
				if (rs.getString(1).equals("S")) admin = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.acceso.AccesoUtil|esCotejador|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return admin;
	}
	
	public static boolean esConvalidador(Connection conn, String codigoPersonal) throws Exception{
		boolean admin=false;
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	= "";
		
		try{
			comando = "SELECT CONVALIDA FROM ENOC.ACCESO WHERE CODIGO_PERSONAL = '"+codigoPersonal+"'";			 
			rs = st.executeQuery(comando);
			if (rs.next()){								
				if (rs.getString(1).equals("S")) admin = true;
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.acceso.AccesoUtil|esConvalidador|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return admin;
	}
	
	public static String getAccesos(Connection conn, String codigoPersonal) throws Exception{		
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		String accesos 		= " ";
		
		try{
			comando = "SELECT ACCESOS FROM ENOC.ACCESO WHERE CODIGO_PERSONAL = '"+codigoPersonal+"'";
			rs = st.executeQuery(comando);
			if (rs.next()){
				accesos = rs.getString(1);
				if(accesos == null)
					accesos = " ";
			}else
				accesos = " ";
		}catch(Exception ex){
			System.out.println("Error - aca.acceso.AccesoUtil|getAccesos|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return accesos;
	}
	
	public static boolean getValidaUsuario(Connection conn, String usuario) throws SQLException{
 		
 		ResultSet 		rs		= null;
 		PreparedStatement ps	= null; 		
 		boolean valida	 		= false;
 		
 		try{
 			ps = conn.prepareStatement("SELECT USUARIO FROM ENOC.ACCESO "+ 
 				"WHERE USUARIO = ? "); 					
 			ps.setString(1,usuario);
 			rs = ps.executeQuery(); 
 			if (rs.next()){ 				 
 				valida = true;
 			}

 		}catch(Exception ex){
 			System.out.println("Error - aca.acceso.AccesoUtil|getValidaUsuario|:"+ex);
 		}finally{
	 		try { rs.close(); } catch (Exception ignore) { }
	 		try { ps.close(); } catch (Exception ignore) { }
 		}
 		
 		return valida;
 	}
 	
 	public static boolean getValidaClave(Connection conn, String usuario, String clave) throws SQLException{
 		
 		PreparedStatement ps	= null;
 		ResultSet 		rs		= null;
 		boolean valida	 		= false;
 		
 		try{
 			ps = conn.prepareStatement("SELECT CLAVE FROM ENOC.ACCESO WHERE USUARIO = ? AND CLAVE = ?");				
 			ps.setString(1,usuario);
 			ps.setString(2,clave);
 			rs = ps.executeQuery();
 			if (rs.next()){
 				if (rs.getString("CLAVE").equals(clave))
 					valida = true;
 			}

 		}catch(Exception ex){
 			System.out.println("Error - aca.acceso.AccesoUtil|getValidaClave|:"+ex);
 		}finally{
	 		try { rs.close(); } catch (Exception ignore) { }
	 		try { ps.close(); } catch (Exception ignore) { }
 		}
 		
 		return valida;
 	}
 	
 	public static String getEsUsuario(Connection Conn, String  usuario, String clave ) throws SQLException{
		
 		PreparedStatement ps	= null;
 		ResultSet 		rs		= null;		
		String codigoPersonal 	= "x";
		usuario = usuario.replaceAll("'", "");
		try{
			ps = Conn.prepareStatement("SELECT CODIGO_PERSONAL FROM ENOC.ACCESO " +
				" WHERE LENGTH(USUARIO) = LENGTH(?)" +
				" AND LENGTH(CLAVE) = LENGTH(?) " +
				" AND USUARIO = ? AND CLAVE = ?");
 			ps.setString(1,usuario);
 			ps.setString(2,clave);
 			ps.setString(3,usuario);
 			ps.setString(4,clave);
 			rs = ps.executeQuery();			
			if (rs.next()){
				codigoPersonal = rs.getString("CODIGO_PERSONAL");
			}else{
				codigoPersonal = "x";
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.acceso.AccesoUtil|getEsUsuario|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return codigoPersonal;
	}
 	
 	public boolean updateClave(Connection conn, String usuario, String clave, String codigoPersonal) throws Exception{
 		boolean ok = false;
 		PreparedStatement ps = null;
 		try{ 			
 			ps = conn.prepareStatement("UPDATE ENOC.ACCESO SET USUARIO = ?, CLAVE = ? WHERE CODIGO_PERSONAL = ? ");
 				
 			ps.setString(1,usuario);
 			ps.setString(2,clave);
 			ps.setString(3,codigoPersonal);
 			
 			if (ps.executeUpdate()== 1)
 				ok = true;	
 			else
 				ok = false;
 		}catch(Exception ex){
 			System.out.println("Error - aca.acceso.AccesoUtil|updateClave|:"+ex);
 		}finally{
 			if (ps != null) ps.close(); 			
 		}
 		
 		return ok;
 	}
 	
 	public static String getModalidad(Connection Conn, String  usuario) throws SQLException{
		
		Statement st 			= Conn.createStatement();
		ResultSet rs 			= null;
		String comando			= "";
		String modalidad 		= "0";
		try{
			comando = "SELECT MODALIDAD FROM ENOC.ACCESO "+ 
				"WHERE CODIGO_PERSONAL = '"+usuario+"' ";
			rs = st.executeQuery(comando);
			if (rs.next()){
				modalidad = rs.getString("MODALIDAD");
			}else{
				modalidad = "x";
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.acceso.AccesoUtil|getModalidad|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return modalidad;
	}
 	
 	// Encriptar la clave en MD5
 	public static String getMD5(String cadena) throws Exception {
 		
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] b = md.digest(cadena.getBytes());
 
        int size = b.length;
        StringBuilder h = new StringBuilder(size);
        for (int i = 0; i < size; i++) {
 
            int u = b[i] & 255;
 
            if (u < 16){
                h.append("0").append(Integer.toHexString(u));
            }else{
                h.append(Integer.toHexString(u));
            }
        }
        return h.toString();
    }
 	
 	public static boolean getBecas(Connection Conn, String  usuario) throws SQLException{
		
		Statement st 			= Conn.createStatement();
		ResultSet rs 			= null;
		String comando			= "";
		boolean becas 			= false;
		try{
			comando = "SELECT BECAS FROM ENOC.ACCESO "+ 
				"WHERE CODIGO_PERSONAL = '"+usuario+"' ";
			rs = st.executeQuery(comando);
			if (rs.next()){
				if (rs.getString("BECAS").equals("S")) becas = true;
				if (rs.getString("BECAS").equals("N")) becas = false;
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.acceso.AccesoUtil|getBecas|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return becas;
	}
 	
 	public static String getPortalMaestro(Connection Conn, String  usuario) throws SQLException{
		
		Statement st 			= Conn.createStatement();
		ResultSet rs 			= null;
		String comando			= "";
		String portal	 		= "N";
		try{
			comando = "SELECT PORTAL_MAESTRO FROM ENOC.ACCESO "+ 
				"WHERE CODIGO_PERSONAL = '"+usuario+"' ";
			rs = st.executeQuery(comando);
			if (rs.next()){
				portal = rs.getString("PORTAL_MAESTRO");
			}else{
				portal = "x";
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.acceso.AccesoUtil|getPortalMaestro|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return portal;
	}

	public static String getPortalAlumno(Connection Conn, String  usuario) throws SQLException{
	
		Statement st 			= Conn.createStatement();
		ResultSet rs 			= null;
		String comando			= "";
		String portal 			= "0";
		try{
			comando = "SELECT PORTAL_ALUMNO FROM ENOC.ACCESO "+ 
				"WHERE CODIGO_PERSONAL = '"+usuario+"' ";
			rs = st.executeQuery(comando);
			if (rs.next()){
				portal = rs.getString("PORTAL_ALUMNO");
			}else{
				portal = "x";
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.acceso.AccesoUtil|getPortalAlumno|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return portal;
	}
	
	public boolean updateIdioma(Connection conn, String idioma, String codigoPersonal ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ACCESO SET IDIOMA = ? WHERE CODIGO_PERSONAL = ?");
			
			ps.setString(1, idioma);
			ps.setString(2, codigoPersonal);
			
			if ( ps.executeUpdate()== 1){
				ok = true;
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.acceso.AccessoUtil|updateIdioma|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}

	public static String getIdioma(Connection Conn, String  usuario) throws SQLException{
		
		Statement st 			= Conn.createStatement();
		ResultSet rs 			= null;
		String comando			= "";
		String idioma 			= "es";
		try{
			comando = "SELECT IDIOMA FROM ENOC.ACCESO "+
				"WHERE CODIGO_PERSONAL = '"+usuario+"' ";
			rs = st.executeQuery(comando);
			if (rs.next()){				
				idioma = rs.getString("IDIOMA");
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.acceso.AccesoUtil|getIdioma|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return idioma;
	}
	
	public static String getMenu(Connection Conn, String  usuario) throws SQLException{
		
		Statement st 			= Conn.createStatement();
		ResultSet rs 			= null;
		String comando			= "";
		String idioma 			= "es";
		try{
			comando = "SELECT MENU FROM ENOC.ACCESO "+
				"WHERE CODIGO_PERSONAL = '"+usuario+"' ";
			rs = st.executeQuery(comando);
			if (rs.next()){				
				idioma = rs.getString("MENU");
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.acceso.AccesoUtil|getMenu|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return idioma;
	}
	
		
	public ArrayList<Acceso> getListAll(Connection conn, String orden ) throws SQLException{
		
		ArrayList<Acceso> lisAcceso 	= new ArrayList<Acceso>();
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	    = "";
		
		try{
			comando = "SELECT CODIGO_PERSONAL, ADMINISTRADOR, SUPERVISOR, COTEJADOR,"
					+ " ACCESOS, MODALIDAD, USUARIO, CLAVE,"
					+ " SUPERVISOR, EXPIRA, INGRESO, CONVALIDA,"
					+ " BECAS, PORTAL_MAESTRO, PORTAL_ALUMNO, IDIOMA, MENU, ALUMNO_MOVIL, CLAVE_INICIAL"
					+ " FROM ENOC.ACCESO "+orden;
			
			rs = st.executeQuery(comando);
			while (rs.next()){
				
				Acceso acceso = new Acceso();
				acceso.mapeaReg(rs);
				lisAcceso.add(acceso);
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.acceso.AccesoUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return lisAcceso;
	}
	
	public static boolean getUsuarioPermiso(Connection conn, String codigoPersonal, String facultadId) throws SQLException{		
		
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando	    = "";
		String accesos		= "";
		String facultad		= "";
		boolean ok			= false; 
		
		try{
			comando = "SELECT TRIM(ACCESOS) AS ACCESOS FROM ENOC.ACCESO WHERE CODIGO_PERSONAL= '"+codigoPersonal+"'";			
			rs = st.executeQuery(comando);
			if(rs.next()){
				accesos = rs.getString("ACCESOS");
				String[] arreglo = accesos.split(" ");
				for(int i=0;i<arreglo.length;i++){
					facultad = aca.catalogo.CatCarreraUtil.getFacultadId(conn, arreglo[i]);
					if (facultad.equals(facultadId)){
						ok = true;
						break;	
					}
				}
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.acceso.AccesoUtil|getListAll|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public static boolean existeUsuario(Connection conn, String codigoPersonal, String usuario) throws Exception{	
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		boolean existe 		= false;
		
		try{
			comando = "SELECT COALESCE(CODIGO_PERSONAL,'VACIO') AS CODIGO_PERSONAL FROM ENOC.ACCESO " +
					" WHERE CODIGO_PERSONAL!= '"+codigoPersonal+"'" +
					" AND USUARIO = '"+usuario+"'";			
			rs = st.executeQuery(comando);
			if (rs.next()){				
				if (!rs.getString("CODIGO_PERSONAL").equals("VACIO") && rs.getString("CODIGO_PERSONAL")!=null){					
					existe = true;
				}					
			}			
		}catch(Exception ex){
			System.out.println("Error - aca.acceso.AccesoUtil|existeUsuario|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}		
		return existe;
	}
}