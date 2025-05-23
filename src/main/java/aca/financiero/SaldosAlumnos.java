package aca.financiero;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		matricula				= rs.getString("MATRICULA");
		saldoGlobal				= rs.getString("SALDOGLOBAL");
		saldo					= rs.getString("SALDO");
		importeContratos		= rs.getString("IMPORTE_CONTRATOS");
		SVencido				= rs.getString("SVENCIDO");
		diferencia				= rs.getString("DIFERENCIA");
	}
	
	public void mapeaRegId(Connection con, String matricula) throws SQLException{
		PreparedStatement ps = null;
		ResultSet rs = null;	
		try{
			ps = con.prepareStatement("SELECT * FROM NOE.SALDOS_ALUMNOS"
					+ " WHERE MATRICULA = ?");
			
			ps.setString(1, matricula);
			rs = ps.executeQuery();
			
			if(rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.financiero.SaldosAlumnos|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
	}
}