//Bean del Catálogo de Documentos

package aca.por;

import java.sql.*;

public class PorSeccion {

	private String porId;
	private String seccionId;
	private String titulo;
	private String seccionNombre;
	private String seccionSuperior;
	private String tipo;
	private String acceso;
	private String orden;
	private String estado;
	private String instrucciones;
	
	public PorSeccion(){
		porId 			= "";
		seccionId		= "";
		seccionNombre	= "";		
		seccionSuperior	= "";
		tipo   			= "";
		orden 			= "0";
		estado 			= "A"; 
		instrucciones	= "-";
	}
	
	/**
	 * @return the porId
	 */
	public String getPorId() {
		return porId;
	}

	/**
	 * @param porId the porId to set
	 */
	public void setPorId(String porId) {
		this.porId = porId;
	}

	/**
	 * @return the seccionId
	 */
	public String getSeccionId() {
		return seccionId;
	}

	/**
	 * @param seccionId the seccionId to set
	 */
	public void setSeccionId(String seccionId) {
		this.seccionId = seccionId;
	}

	/**
	 * @return the titulo
	 */
	public String getTitulo() {
		return titulo;
	}

	/**
	 * @param titulo the titulo to set
	 */
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	/**
	 * @return the seccionNombre
	 */
	public String getSeccionNombre() {
		return seccionNombre;
	}

	/**
	 * @param seccionNombre the seccionNombre to set
	 */
	public void setSeccionNombre(String seccionNombre) {
		this.seccionNombre = seccionNombre;
	}

	/**
	 * @return the seccionSuperior
	 */
	public String getSeccionSuperior() {
		return seccionSuperior;
	}

	/**
	 * @param seccionSuperior the seccionSuperior to set
	 */
	public void setSeccionSuperior(String seccionSuperior) {
		this.seccionSuperior = seccionSuperior;
	}

	/**
	 * @return the tipo
	 */
	public String getTipo() {
		return tipo;
	}

	/**
	 * @param tipo the tipo to set
	 */
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	/**
	 * @return the acceso
	 */
	public String getAcceso() {
		return acceso;
	}

	/**
	 * @param acceso the acceso to set
	 */
	public void setAcceso(String acceso) {
		this.acceso = acceso;
	}

	/**
	 * @return the orden
	 */
	public String getOrden() {
		return orden;
	}

	/**
	 * @param orden the orden to set
	 */
	public void setOrden(String orden) {
		this.orden = orden;
	}

	/**
	 * @return the estado
	 */
	public String getEstado() {
		return estado;
	}

	/**
	 * @param estado the estado to set
	 */
	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	/**
	 * @return the instrucciones
	 */
	public String getInstrucciones() {
		return instrucciones;
	}

	/**
	 * @param estado the instrucciones to set
	 */
	public void setInstrucciones(String instrucciones) {
		this.instrucciones = instrucciones;
	}

	public boolean insertReg(Connection conn ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO "+
				"ENOC.POR_SECCION( POR_ID, SECCION_ID, TITULO, SECCION_NOMBRE, SECCION_SUPERIOR,  TIPO, ACCESO, ORDEN, ESTADO, INSTRUCCIONES) "+
				"VALUES( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
			ps.setString(1, porId);
			ps.setString(2, seccionId);
			ps.setString(3, titulo);
			ps.setString(4, seccionNombre);			
			ps.setString(5, seccionSuperior);
			ps.setString(6, tipo);
			ps.setString(7, acceso);
			ps.setString(8, orden);
			ps.setString(9, estado);
			ps.setString(10, instrucciones);
			
			if (ps.executeUpdate()== 1)
				ok = true;				
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.por.PorSeccion|insertReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public boolean updateReg(Connection conn ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.POR_SECCION"
				+ " SET SECCION_NOMBRE = ?, SECCION_SUPERIOR = ?, TIPO = ?, TITULO = ?, ACCESO = ?, ORDEN = ?, ESTADO = ?, INSTRUCCIONES = ?"
				+ " WHERE POR_ID = ? AND SECCION_ID = ?");
			ps.setString(1, seccionNombre);
			ps.setString(2, seccionSuperior);			
			ps.setString(3, tipo);
			ps.setString(4, titulo);
			ps.setString(5, acceso);
			ps.setString(6, orden);
			ps.setString(7, estado);
			ps.setString(8, instrucciones);
			ps.setString(9, porId);
			ps.setString(10, seccionId);
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.por.PorSeccion|updateReg|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public boolean updateTipo(Connection conn ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.POR_SECCION"
				+ " SET  TIPO = ?"
				+ " WHERE POR_ID = ? AND SECCION_ID = ?");
			ps.setString(1, tipo);
			ps.setString(2, porId);
			ps.setString(3, seccionId);
			
			if (ps.executeUpdate()== 1)
				ok = true;	
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.por.PorSeccion|updateReg|:"+ex);		
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	
	public boolean deleteReg(Connection conn ) throws Exception{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.POR_SECCION "+ 
				"WHERE POR_ID = ? AND SECCION_ID = ?");
			ps.setString(1, porId);
			ps.setString(2, seccionId);
			
			if (ps.executeUpdate()== 1)
				ok = true;
			else
				ok = false;
		}catch(Exception ex){
			System.out.println("Error - aca.por.PorSeccion|deleteReg|:"+ex);			
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		porId 					= rs.getString("POR_ID");
		seccionId 				= rs.getString("SECCION_ID");
		titulo 					= rs.getString("TITULO");
		seccionNombre			= rs.getString("SECCION_NOMBRE");
		seccionSuperior 		= rs.getString("SECCION_SUPERIOR");
		tipo            	   	= rs.getString("TIPO");
		acceso            	   	= rs.getString("ACCESO");
		orden            	   	= rs.getString("ORDEN");
		estado            	   	= rs.getString("ESTADO");
		instrucciones			= rs.getString("INSTRUCCIONES");
	}
	
	public void mapeaRegId( Connection conn, String porId, String seccionId) throws SQLException{
		ResultSet rs = null;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("SELECT POR_ID, SECCION_ID, TITULO, SECCION_NOMBRE, SECCION_SUPERIOR, TIPO, ACCESO, ORDEN, ESTADO, INSTRUCCIONES"
					+ " FROM ENOC.POR_SECCION WHERE POR_ID = ? AND SECCION_ID = ?"); 
			ps.setString(1, porId);
			ps.setString(2, seccionId);
			rs = ps.executeQuery();
			if (rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.por.PorSeccion|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
	}
	
	public boolean existeReg(Connection conn) throws SQLException{
		boolean 		ok 		= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.POR_SECCION WHERE POR_ID = ? AND SECCION_ID = ?"); 
			ps.setString(1,porId);
			ps.setString(2,seccionId);
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.por.PorSeccion|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public boolean tieneHijo(Connection conn, String seccionId) throws SQLException{
		boolean 		ok 		= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.POR_SECCION WHERE SECCION_SUPERIOR = '"+seccionId+"' "); 
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.por.PorSeccion|tieneHijo|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
	
	public static String getNombre(Connection conn, String porId, String seccionId) throws SQLException{
		
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		String nombre		= "x";
		
		try{
			comando = "SELECT SECCION_NOMBRE FROM ENOC.POR_SECCION WHERE POR_ID = '"+porId+"' AND SECCION_ID = '"+seccionId+"' ";
			rs = st.executeQuery(comando);
			if (rs.next()){
				nombre = rs.getString("SECCION_NOMBRE");
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.por.PorSeccion|getNombre|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return nombre;
	}
	
	public static String getTitulo(Connection conn, String porId, String seccionId) throws SQLException{
		
		Statement st 		= conn.createStatement();
		ResultSet rs 		= null;
		String comando		= "";
		String titulo		= "x";
		
		try{
			comando = "SELECT TITULO FROM ENOC.POR_SECCION WHERE POR_ID = '"+porId+"' AND SECCION_ID = '"+seccionId+"' ";
			rs = st.executeQuery(comando);
			if (rs.next()){
				titulo = rs.getString("TITULO");
			}
			
		}catch(Exception ex){
			System.out.println("Error - aca.por.PorSeccion|getTitulo|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return titulo;
	}
	
	public String maximoSeccion(Connection conn, String seccionId) throws SQLException{
		Statement st		= conn.createStatement();
		ResultSet rs		= null;		
		int size 			= seccionId.length();
		String maximo 		= "1";
		String comando 		= "";
		
		try{
			if (size < 2){
				comando = "SELECT MAX(SECCION_ID)+1 AS MAXIMO FROM ENOC.POR_SECCION WHERE LENGTH(SECCION_ID)=2";
			}else if (size == 2){
				comando = "SELECT MAX(SUBSTR(SECCION_ID,3,2))+1 AS MAXIMO FROM ENOC.POR_SECCION WHERE SECCION_ID LIKE '"+seccionId+"%' AND LENGTH(SECCION_ID)=4";
			}else if (size == 4){
				comando = "SELECT MAX(SUBSTR(SECCION_ID,5,2))+1 AS MAXIMO FROM ENOC.POR_SECCION WHERE SECCION_ID LIKE '"+seccionId+"%' AND LENGTH(SECCION_ID)=6";
			}else if (size == 6){
				comando = "SELECT MAX(SUBSTR(SECCION_ID,7,2))+1 AS MAXIMO FROM ENOC.POR_SECCION WHERE SECCION_ID LIKE '"+seccionId+"%' AND LENGTH(SECCION_ID)=8";
			}
			rs = st.executeQuery(comando);
			if (rs.next())
				maximo = rs.getString("MAXIMO");
				if(maximo == null){
					maximo = "1";
				}
		}catch(Exception ex){
			System.out.println("Error - aca.por.PorSeccion|maximoSeccion|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return maximo;		
	}
	
	public boolean seUtilizaEmp(Connection conn,String porId, String seccionId) throws SQLException{
		boolean 		ok 		= false;
		ResultSet 		rs		= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.POR_SECCION_EMP WHERE SECCION_ID = '"+seccionId+"' AND POR_ID = '"+porId+"' "); 
			rs = ps.executeQuery();
			if (rs.next())
				ok = true;
			else
				ok = false;
			
		}catch(Exception ex){
			System.out.println("Error - aca.por.PorSeccion|seUtilizaEmp|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}	
}