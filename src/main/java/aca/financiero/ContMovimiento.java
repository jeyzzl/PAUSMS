package aca.financiero;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ContMovimiento {
	private String idEjercicio;
	private String idLibro;	
	private String idCcosto;
	private String folio;
	private String numMovto;
	private String fecha;
	private String descripcion;
	private String importe;
	private String naturaleza;
	private String referencia;
	private String referencia2;
	private String idCtaMayorM;
	private String idCcostoM;
	private String idAuxiliarM;
	private String status;
	private String tipoCuenta;
	private String conceptoId;
	
	
	// Constructor
	public ContMovimiento(){
		
		idEjercicio 	= "";
		idLibro 		= "";	
		idCcosto		= "";
		folio			= "0";
		numMovto		= "0";
		fecha			= "";
		descripcion		= "";
		importe			= "0";
		naturaleza		= "";
		referencia		= "";
		referencia2		= "";
		idCtaMayorM		= "";
		idCcostoM		= "";
		idAuxiliarM		= "";
		status			= "";
		tipoCuenta		= "";
		conceptoId		= "";		
		
	}	
	
	/**
	 * @return the idEjercicio
	 */
	public String getIdEjercicio() {
		return idEjercicio;
	}

	/**
	 * @param idEjercicio the idEjercicio to set
	 */
	public void setIdEjercicio(String idEjercicio) {
		this.idEjercicio = idEjercicio;
	}


	/**
	 * @return the idLibro
	 */
	public String getIdLibro() {
		return idLibro;
	}


	/**
	 * @param idLibro the idLibro to set
	 */
	public void setIdLibro(String idLibro) {
		this.idLibro = idLibro;
	}


	/**
	 * @return the idCcosto
	 */
	public String getIdCcosto() {
		return idCcosto;
	}


	/**
	 * @param idCcosto the idCcosto to set
	 */
	public void setIdCcosto(String idCcosto) {
		this.idCcosto = idCcosto;
	}


	/**
	 * @return the folio
	 */
	public String getFolio() {
		return folio;
	}


	/**
	 * @param folio the folio to set
	 */
	public void setFolio(String folio) {
		this.folio = folio;
	}


	/**
	 * @return the numMovto
	 */
	public String getNumMovto() {
		return numMovto;
	}


	/**
	 * @param numMovto the numMovto to set
	 */
	public void setNumMovto(String numMovto) {
		this.numMovto = numMovto;
	}


	/**
	 * @return the fecha
	 */
	public String getFecha() {
		return fecha;
	}


	/**
	 * @param fecha the fecha to set
	 */
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}


	/**
	 * @return the descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}


	/**
	 * @param descripcion the descripcion to set
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}


	/**
	 * @return the importe
	 */
	public String getImporte() {
		return importe;
	}


	/**
	 * @param importe the importe to set
	 */
	public void setImporte(String importe) {
		this.importe = importe;
	}


	/**
	 * @return the naturaleza
	 */
	public String getNaturaleza() {
		return naturaleza;
	}


	/**
	 * @param naturaleza the naturaleza to set
	 */
	public void setNaturaleza(String naturaleza) {
		this.naturaleza = naturaleza;
	}


	/**
	 * @return the referencia
	 */
	public String getReferencia() {
		return referencia;
	}


	/**
	 * @param referencia the referencia to set
	 */
	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}


	/**
	 * @return the referencia2
	 */
	public String getReferencia2() {
		return referencia2;
	}


	/**
	 * @param referencia2 the referencia2 to set
	 */
	public void setReferencia2(String referencia2) {
		this.referencia2 = referencia2;
	}


	/**
	 * @return the idCtaMayorM
	 */
	public String getIdCtaMayorM() {
		return idCtaMayorM;
	}


	/**
	 * @param idCtaMayorM the idCtaMayorM to set
	 */
	public void setIdCtaMayorM(String idCtaMayorM) {
		this.idCtaMayorM = idCtaMayorM;
	}


	/**
	 * @return the idCcostoM
	 */
	public String getIdCcostoM() {
		return idCcostoM;
	}


	/**
	 * @param idCcostoM the idCcostoM to set
	 */
	public void setIdCcostoM(String idCcostoM) {
		this.idCcostoM = idCcostoM;
	}


	/**
	 * @return the idAuxiliarM
	 */
	public String getIdAuxiliarM() {
		return idAuxiliarM;
	}


	/**
	 * @param idAuxiliarM the idAuxiliarM to set
	 */
	public void setIdAuxiliarM(String idAuxiliarM) {
		this.idAuxiliarM = idAuxiliarM;
	}


	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}


	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}


	/**
	 * @return the tipoCuenta
	 */
	public String getTipoCuenta() {
		return tipoCuenta;
	}


	/**
	 * @param tipoCuenta the tipoCuenta to set
	 */
	public void setTipoCuenta(String tipoCuenta) {
		this.tipoCuenta = tipoCuenta;
	}


	/**
	 * @return the conceptoId
	 */
	public String getConceptoId() {
		return conceptoId;
	}

	/**
	 * @param conceptoId the conceptoId to set
	 */
	public void setConceptoId(String conceptoId) {
		this.conceptoId = conceptoId;
	}

	public void mapeaReg(ResultSet rs ) throws SQLException{
		idEjercicio			= rs.getString("ID_EJERCICIO");		
		idLibro				= rs.getString("ID_LIBRO");
		idCcosto			= rs.getString("ID_CCOSTO");
		folio				= rs.getString("FOLIO");
		numMovto			= rs.getString("NUMMOVTO");
		fecha				= rs.getString("FECHA");
		descripcion			= rs.getString("DESCRIPCION");
		importe				= rs.getString("IMPORTE");
		naturaleza			= rs.getString("NATURALEZA");
		referencia			= rs.getString("REFERENCIA");
		referencia2			= rs.getString("REFERENCIA2");
		idCtaMayorM			= rs.getString("ID_CTAMAYORM");
		idCcostoM			= rs.getString("ID_CCOSTOM");
		idAuxiliarM			= rs.getString("ID_AUXILIARM");
		status				= rs.getString("STATUS");
		tipoCuenta			= rs.getString("TIPO_CUENTA");
		conceptoId			= rs.getString("CONCEPTO_ID");
	}
	
	public static double getSaldoAnterior(Connection conn, String matricula, String ejercicioId, String libro, String cuentas ) throws SQLException{
		
		Statement st		= conn.createStatement();
		ResultSet 	rs		= null;
		String comando 		= "";
		double	saldo		= 0;
		
		try{
			comando = "SELECT COALESCE(SUM(IMPORTE* CASE NATURALEZA WHEN 'C' THEN 1 ELSE -1 END),0) AS IMPORTE"
					+ " FROM MATEO.CONT_MOVIMIENTO"
					+ " WHERE ID_AUXILIARM = '"+matricula+"'"
					+ " AND ID_EJERCICIO = '"+ejercicioId+"'"
					+ " AND ID_LIBRO = '"+libro+"'"
					+ " AND ID_CTAMAYORM IN ("+cuentas+")";
			
			rs = st.executeQuery(comando);
			if (rs.next())
				saldo = rs.getDouble("IMPORTE");
			
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.ContMovimiento|getSaldoAnterior|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { st.close(); } catch (Exception ignore) { }
		}
		
		return saldo;
	}
	
}