// Bean del Catalogo Cargas
package  aca.emp.spring;

public class EmpHorasPres{
	private String departamentoId;	
	private String cargaId;
	private String year;
	private String importe;	
	private String saldoAnt;
	
	public EmpHorasPres(){
		departamentoId		= "0";		
		cargaId				= "000000";
		year				= "0000";
		importe				= "0";
		saldoAnt			= "0"; 
	}

	public String getDepartamentoId() {
		return departamentoId;
	}

	public void setDepartamentoId(String departamentoId) {
		this.departamentoId = departamentoId;
	}

	public String getCargaId() {
		return cargaId;
	}

	public void setCargaId(String cargaId) {
		this.cargaId = cargaId;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getImporte() {
		return importe;
	}

	public void setImporte(String importe) {
		this.importe = importe;
	}

	public String getSaldoAnt() {
		return saldoAnt;
	}

	public void setSaldoAnt(String saldoAnt) {
		this.saldoAnt = saldoAnt;
	}
	
}