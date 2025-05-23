/**
 * 
 */
package aca.internado;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Elifo
 *
 */
public class ComAutorizacion {
	private String matricula;
	private String numComidas;
	private String tipoComida;
	private String fechaInicial;
	private String fechaFinal;
	private String usuario;
	private String cliente;
	private String paquete;
	private String cargaId;
	private String bloque;
	
	public ComAutorizacion(){
		matricula			= "";
		numComidas			= "";
		tipoComida			= "";
		fechaInicial		= "";
		fechaFinal			= "";
		usuario				= "";
		cliente				= "";
		paquete				= "";
		cargaId				= "";
		bloque				= "";
	}
	
	/**
	 * @return the bloque
	 */
	public String getBloque() {
		return bloque;
	}


	/**
	 * @param bloque the bloque to set
	 */
	public void setBloque(String bloque) {
		this.bloque = bloque;
	}


	/**
	 * @return the cargaId
	 */
	public String getCargaId() {
		return cargaId;
	}


	/**
	 * @param cargaId the cargaId to set
	 */
	public void setCargaId(String cargaId) {
		this.cargaId = cargaId;
	}


	/**
	 * @return the cliente
	 */
	public String getCliente() {
		return cliente;
	}


	/**
	 * @param cliente the cliente to set
	 */
	public void setCliente(String cliente) {
		this.cliente = cliente;
	}


	/**
	 * @return the fechaFinal
	 */
	public String getFechaFinal() {
		return fechaFinal;
	}


	/**
	 * @param fechaFinal the fechaFinal to set
	 */
	public void setFechaFinal(String fechaFinal) {
		this.fechaFinal = fechaFinal;
	}


	/**
	 * @return the fechaInicial
	 */
	public String getFechaInicial() {
		return fechaInicial;
	}


	/**
	 * @param fechaInicial the fechaInicial to set
	 */
	public void setFechaInicial(String fechaInicial) {
		this.fechaInicial = fechaInicial;
	}


	/**
	 * @return the matricula
	 */
	public String getMatricula() {
		return matricula;
	}


	/**
	 * @param matricula the matricula to set
	 */
	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}


	/**
	 * @return the numComidas
	 */
	public String getNumComidas() {
		return numComidas;
	}


	/**
	 * @param numComidas the numComidas to set
	 */
	public void setNumComidas(String numComidas) {
		this.numComidas = numComidas;
	}


	/**
	 * @return the paquete
	 */
	public String getPaquete() {
		return paquete;
	}


	/**
	 * @param paquete the paquete to set
	 */
	public void setPaquete(String paquete) {
		this.paquete = paquete;
	}


	/**
	 * @return the tipoComida
	 */
	public String getTipoComida() {
		return tipoComida;
	}


	/**
	 * @param tipoComida the tipoComida to set
	 */
	public void setTipoComida(String tipoComida) {
		this.tipoComida = tipoComida;
	}


	/**
	 * @return the usuario
	 */
	public String getUsuario() {
		return usuario;
	}


	/**
	 * @param usuario the usuario to set
	 */
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		matricula			= rs.getString("MATRICULA");
		numComidas			= rs.getString("NUM_COMIDAS");
		tipoComida			= rs.getString("TIPO_COMIDA");
		fechaInicial		= rs.getString("FECHA_INICIAL");
		fechaFinal			= rs.getString("FECHA_FINAL");
		usuario				= rs.getString("USUARIO");
		cliente				= rs.getString("CLIENTE");
		paquete				= rs.getString("PAQUETE");
		cargaId				= rs.getString("CARGA_ID");
		bloque				= rs.getString("BLOQUE");
	}

	public void mapeaRegId(Connection con, String matricula, String cargaId, String bloque) throws SQLException{
		PreparedStatement ps = null;
		ResultSet rs = null;		
		try{ 
			ps = con.prepareStatement("SELECT MATRICULA, NUM_COMIDAS," +
					" TIPO_COMIDA, TO_CHAR(FECHA_INICIAL, 'DD/MM/YYYY') AS FECHA_INICIAL," +
					" TO_CHAR(FECHA_FINAL, 'DD/MM/YYYY') AS FECHA_FINAL, USUARIO," +
					" CLIENTE, PAQUETE, CARGA_ID, BLOQUE" +
					" FROM NOE.COM_AUTORIZACION" +
					" WHERE MATRICULA = ?" +
					" AND CARGA_ID = ?" +
					" AND BLOQUE = ?");
			ps.setString(1,matricula);
			ps.setString(2, cargaId);
			ps.setInt(3, Integer.parseInt(bloque));
			rs = ps.executeQuery();
			if(rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.internado.ComAutorizacionUtil|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
	}
}