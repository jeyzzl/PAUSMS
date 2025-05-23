package aca.financiero;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CCP_Presupuesto {
	private String idEjercicio;
	private String idCtamayor;
	private String idCcosto;
	private String idAuxiliar;
	private String mes;
	private String importe;
	
	public CCP_Presupuesto(){
		idEjercicio ="";
		idCtamayor = "";
		idCcosto = "";
		idAuxiliar = "";
		mes = "";
		importe ="";
	}
	
	public String getIdEjercicio() {
		return idEjercicio;
	}
	public void setIdEjercicio(String idEjercicio) {
		this.idEjercicio = idEjercicio;
	}
	public String getIdCtamayor() {
		return idCtamayor;
	}
	public void setIdCtamayor(String idCtamayor) {
		this.idCtamayor = idCtamayor;
	}
	public String getIdCcosto() {
		return idCcosto;
	}
	public void setIdCcosto(String idCcosto) {
		this.idCcosto = idCcosto;
	}
	public String getIdAuxiliar() {
		return idAuxiliar;
	}
	public void setIdAuxiliar(String idAuxiliar) {
		this.idAuxiliar = idAuxiliar;
	}
	public String getMes() {
		return mes;
	}
	public void setMes(String mes) {
		this.mes = mes;
	}
	public String getImporte() {
		return importe;
	}
	public void setImporte(String importe) {
		this.importe = importe;
	}
	
	public void mapeaReg(ResultSet rs) throws SQLException{
		idEjercicio 	= rs.getString("ID_EJERCICIO");
		idCtamayor 		= rs.getString("ID_CTAMAYOR");
		idCcosto 		= rs.getString("ID_CCOSTO");
		idAuxiliar 		= rs.getString("ID_AUXILIAR");
		mes 			= rs.getString("MES");
		importe	 		= rs.getString("IMPORTE");
	}
	
}
