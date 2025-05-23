package aca.bsc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BscUm {
	private String periodoId;
	private String cargaId;
	private String ciclo;
	private String inscritos;
	private String orden;

	public String getPeriodoId() {
		return periodoId;
	}

	public void setPeriodoId(String periodoId) {
		this.periodoId = periodoId;
	}

	public String getCargaId() {
		return cargaId;
	}

	public void setCargaId(String cargaId) {
		this.cargaId = cargaId;
	}

	public String getCiclo() {
		return ciclo;
	}

	public void setCiclo(String ciclo) {
		this.ciclo = ciclo;
	}

	public String getInscritos() {
		return inscritos;
	}

	public void setInscritos(String inscritos) {
		this.inscritos = inscritos;
	}

	public String getOrden() {
		return orden;
	}

	public void setOrden(String orden) {
		this.orden = orden;
	}

	
	public void mapeaReg(ResultSet rs) throws SQLException {
		periodoId = rs.getString("PERIODO_ID");
		cargaId = rs.getString("CARGA_ID");
		ciclo = rs.getString("CICLO");
		inscritos = rs.getString("INSCRITOS");
		orden = rs.getString("ORDEN");
	}
	
	public void mapeaRegId(Connection con, String periodoId, String cargaId, String ciclo) throws SQLException {		
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = con.prepareStatement("SELECT * FROM ENOC.BSC_UM WHERE PERIODO_ID = ? AND CARGA_ID = ? AND CICLO = ? ");
			ps.setString(1, periodoId);
			ps.setString(2, cargaId);
			ps.setString(3, ciclo);
			rs = ps.executeQuery();

			if (rs.next()) {
				mapeaReg(rs);
			}
		} catch (Exception ex) {
			System.out.println("Error - aca.bsc.BscUmUtil|mapeaRegId|:" + ex);
		} finally {
			if (rs != null)
				rs.close();
			if (ps != null)
				ps.close();
		}
		
	}

	
}