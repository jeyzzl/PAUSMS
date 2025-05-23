package aca.vista.spring;

public class AlumSaldos {
	
	private String matricula;
	private String saldo;
	private String importeContratos;
	private String sVencido;
	private String diferencia;
	
	public AlumSaldos() {
		 matricula 			= "";
		 saldo 				= "";
		 importeContratos 	= "";
		 sVencido 			= "";
		 diferencia 		= "";
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getSaldo() {
		return saldo;
	}

	public void setSaldo(String saldo) {
		this.saldo = saldo;
	}

	public String getImporteContratos() {
		return importeContratos;
	}

	public void setImporteContratos(String importeContratos) {
		this.importeContratos = importeContratos;
	}

	public String getsVencido() {
		return sVencido;
	}

	public void setsVencido(String sVencido) {
		this.sVencido = sVencido;
	}

	public String getDiferencia() {
		return diferencia;
	}

	public void setDiferencia(String diferencia) {
		this.diferencia = diferencia;
	}
	
}
