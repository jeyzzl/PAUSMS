package aca.financiero.spring;

public class SaldosAlumnos {
	
	private String matricula;
	private String saldoGlobal;
	private String saldo;
	private String importeContratos;
	private String SVencido;
	private String diferencia;
	
	public SaldosAlumnos(){
		matricula			= "";
		saldoGlobal			= "";
		saldo				= "";		
		importeContratos	= "";
		SVencido			= "";
		diferencia			= "";	
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
	
	public String getSaldoGlobal() {
		return saldoGlobal;
	}

	public void setSaldoGlobal(String saldoGlobal) {
		this.saldoGlobal = saldoGlobal;
	}

	public String getImporteContratos() {
		return importeContratos;
	}

	public void setImporteContratos(String importeContrato) {
		this.importeContratos = importeContrato;
	}

	public String getSVencido() {
		return SVencido;
	}

	public void setSVencido(String sVencido) {
		this.SVencido = sVencido;
	}

	public String getDiferencia() {
		return diferencia;
	}

	public void setDiferencia(String diferencia) {
		this.diferencia = diferencia;
	}
}