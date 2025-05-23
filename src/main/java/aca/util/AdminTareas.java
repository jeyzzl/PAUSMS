package aca.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminTareas {
	
	private String folio;
	private String fCrea;
	private String fInicio;
	private String fTerminado;
	private String nombre;
	private String descripcion;
	private String desarrollador;
	private String cliente;
	private String modulo;
	private String estado;
	
	public AdminTareas(){
		folio 			= "";
		fCrea 			= "";
		fInicio			= "";
		fTerminado		= "";
		nombre			= "";
		descripcion		= "";
		desarrollador	= "";
		cliente			= "";
		modulo			= "";
		estado 			= "";
	}

	

	public String getFolio() {
		return folio;
	}



	public void setFolio(String folio) {
		this.folio = folio;
	}



	public String getfCrea() {
		return fCrea;
	}



	public void setfCrea(String fCrea) {
		this.fCrea = fCrea;
	}



	public String getfInicio() {
		return fInicio;
	}



	public void setfInicio(String fInicio) {
		this.fInicio = fInicio;
	}



	public String getfTerminado() {
		return fTerminado;
	}



	public void setfTerminado(String fTerminado) {
		this.fTerminado = fTerminado;
	}



	public String getNombre() {
		return nombre;
	}



	public void setNombre(String nombre) {
		this.nombre = nombre;
	}



	public String getDescripcion() {
		return descripcion;
	}



	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}



	public String getDesarrollador() {
		return desarrollador;
	}



	public void setDesarrollador(String desarrollador) {
		this.desarrollador = desarrollador;
	}



	public String getCliente() {
		return cliente;
	}



	public void setCliente(String cliente) {
		this.cliente = cliente;
	}



	public String getModulo() {
		return modulo;
	}



	public void setModulo(String modulo) {
		this.modulo = modulo;
	}



	public String getEstado() {
		return estado;
	}



	public void setEstado(String estado) {
		this.estado = estado;
	}



	public boolean insertReg(Connection conn ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("INSERT INTO ENOC.ADMIN_TAREAS(FOLIO, " + 
					"F_CREA, F_INICIO, F_TERMINADO, NOMBRE, DESCRIPCION, DESARROLLADOR, CLIENTE, MODULO, ESTADO) " +
					"VALUES(TO_NUMBER(?,'9999999'), TO_DATE(?,'DD/MM/YYYY'), TO_DATE(?,'DD/MM/YYYY'), " +
					"TO_DATE(?,'DD/MM/YYYY'), ?, ?, ?, ?, ?, ?)");
			ps.setString(1,folio);
			ps.setString(2,fCrea);
			ps.setString(3,fInicio);
			ps.setString(4,fTerminado);
			ps.setString(5,nombre);
			ps.setString(6,descripcion);
			ps.setString(7,desarrollador);
			ps.setString(8,cliente);
			ps.setString(9,modulo);
			ps.setString(10,estado);
			if ( ps.executeUpdate()== 1){
				ok = true;				
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.util.AdminTareas|insertReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean updateReg(Connection conn ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("UPDATE ENOC.ADMIN_TAREAS SET " + 
					" F_CREA = TO_DATE(?,'DD/MM/YYYY'), F_INICIO = TO_DATE(?,'DD/MM/YYYY'), F_TERMINADO = TO_DATE(?,'DD/MM/YYYY')," +
					" NOMBRE = ?, DESCRIPCION = ?, DESARROLLADOR = ?, CLIENTE = ?, MODULO = ?, ESTADO = ? WHERE FOLIO = TO_NUMBER(?,'9999999') ");			
			ps.setString(1,fCrea);
			ps.setString(2,fInicio);
			ps.setString(3,fTerminado);
			ps.setString(4,nombre);	
			ps.setString(5,descripcion);
			ps.setString(6,desarrollador);
			ps.setString(7,cliente);
			ps.setString(8,modulo);
			ps.setString(9,estado);
			ps.setString(10,folio);
			
			if ( ps.executeUpdate()== 1){
				ok = true;				
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.util.AdminTareas|updateReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public boolean deleteReg(Connection conn ) throws SQLException{
		boolean ok = false;
		PreparedStatement ps = null;
		try{
			ps = conn.prepareStatement("DELETE FROM ENOC.ADMIN_TAREAS WHERE FOLIO = ? ");
			ps.setString(1,folio);	
			if ( ps.executeUpdate()== 1){
				ok = true;				
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.util.AdminTareas|deleteReg|:"+ex);
		}finally{
			try { ps.close(); } catch (Exception ignore) { }
		}
		return ok;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		folio				= rs.getString("FOLIO");
		fCrea  				= rs.getString("F_CREA");
		fInicio				= rs.getString("F_INICIO");
		fTerminado			= rs.getString("F_TERMINADO");
		nombre				= rs.getString("NOMBRE");
		descripcion			= rs.getString("DESCRIPCION");
		desarrollador		= rs.getString("DESARROLLADOR");
		cliente				= rs.getString("CLIENTE");
		modulo				= rs.getString("MODULO");
		estado				= rs.getString("ESTADO");
	}
	
	public void mapeaRegId(Connection con, String folio) throws SQLException{
		PreparedStatement ps = null;
		ResultSet rs = null;	
		try{
			ps = con.prepareStatement("SELECT FOLIO, TO_CHAR(F_CREA,'DD/MM/YYYY') AS F_CREA, TO_CHAR(F_INICIO,'DD/MM/YYYY') AS F_INICIO," +
					" TO_CHAR(F_TERMINADO,'DD/MM/YYYY') AS F_TERMINADO, NOMBRE, DESCRIPCION, " +
					" DESARROLLADOR, CLIENTE, MODULO, ESTADO " +
					" FROM ENOC.ADMIN_TAREAS WHERE FOLIO = "+folio+" ");		 
			rs = ps.executeQuery();
		
			if(rs.next()){		
				mapeaReg(rs);		
			}
		}catch(Exception ex){
			System.out.println("Error - aca.util.AdminTareas|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
	}
	
	public boolean existeReg(Connection conn) throws SQLException{
		boolean ok 				= false;
		ResultSet rs 			= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT * FROM ENOC.ADMIN_TAREAS " + 
					"WHERE FOLIO = ? ");
			ps.setString(1,folio);
			rs= ps.executeQuery();		
			if(rs.next()){
				ok = true;
			}else{
				ok = false;
			}
		}catch(Exception ex){
			System.out.println("Error - aca.util.AdminTareas|existeReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return ok;
	}
	
	public String maximoReg(Connection conn) throws SQLException{
		String maximo 			= "1";
		ResultSet rs			= null;
		PreparedStatement ps	= null;
		
		try{
			ps = conn.prepareStatement("SELECT MAX(FOLIO)+1 AS MAXIMO FROM ENOC.ADMIN_TAREAS"); 
			rs = ps.executeQuery();
			if (rs.next())
				maximo = rs.getString("MAXIMO");
			
		}catch(Exception ex){
			System.out.println("Error - aca.util.AdminTareas|maximoReg|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
		
		return maximo==null?"1":maximo;
	}
	
}	
