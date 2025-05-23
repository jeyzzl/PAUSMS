package aca.leg;

import java.sql.*;

public class LegExtranjero {
	private String codigo;
	private String rne;
	private String libro;
	private String hoja;
	private String carrera;	
	private String comentario;
	private String telefono;
	
	public LegExtranjero(){
		codigo		= "";
		libro		= "";
		hoja		= "";
		rne			= "";	
		carrera		= "";	
		comentario	= "";	
		telefono 	= "";
	}
	
	/**
	 * @return the codigo
	 */
	public String getCodigo() {
		return codigo;
	}

	/**
	 * @param codigo the codigo to set
	 */
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	/**
	 * @return the rne
	 */
	public String getRne() {
		return rne;
	}

	/**
	 * @param rne the rne to set
	 */
	public void setRne(String rne) {
		this.rne = rne;
	}

	/**
	 * @return the libro
	 */
	public String getLibro() {
		return libro;
	}

	/**
	 * @param libro the libro to set
	 */
	public void setLibro(String libro) {
		this.libro = libro;
	}

	/**
	 * @return the hoja
	 */
	public String getHoja() {
		return hoja;
	}

	/**
	 * @param hoja the hoja to set
	 */
	public void setHoja(String hoja) {
		this.hoja = hoja;
	}

	/**
	 * @return the carrera
	 */
	public String getCarrera() {
		return carrera;
	}

	/**
	 * @param carrera the carrera to set
	 */
	public void setCarrera(String carrera) {
		this.carrera = carrera;
	}

	/**
	 * @return the comentario
	 */
	public String getComentario() {
		return comentario;
	}

	/**
	 * @param comentario the comentario to set
	 */
	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	/**
	 * @return the telefono
	 */
	public String getTelefono() {
		return telefono;
	}

	/**
	 * @param telefono the telefono to set
	 */
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}	
	
	public void mapeaReg(ResultSet rs) throws SQLException{
		codigo			= rs.getString("CODIGO");
		rne				= rs.getString("RNE");
		libro			= rs.getString("LIBRO");
		hoja			= rs.getString("HOJA");
		carrera			= rs.getString("CARRERA");		
		comentario		= rs.getString("COMENTARIO");		
		telefono		= rs.getString("TELEFONO");		
	}
	
	public void mapeaRegId(Connection conn, String codigo)	throws SQLException{
		
		ResultSet rs			= null;
		PreparedStatement ps	= null;
		try{
			ps=conn.prepareStatement("SELECT CODIGO, COALESCE(RNE,'VACIO') AS RNE, " +
					"COALESCE(LIBRO,'VACIO')AS LIBRO, " +
					"COALESCE(HOJA,'VACIO') AS HOJA, " +
					"COALESCE(CARRERA,'VACIO') AS CARRERA, " +
					"COALESCE(COMENTARIO,'VACIO')AS COMENTARIO, " +				
					"COALESCE(TELEFONO,'VACIO') AS TELEFONO " +
					"FROM ENOC.LEG_EXTRANJERO " + 
					"WHERE CODIGO = ? ");
			ps.setString(1, codigo);
			rs=ps.executeQuery();
			if(rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.leg.LegExtranjero|mapeaRegId|:"+ex);
			ex.printStackTrace();
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
	
	}
	
}