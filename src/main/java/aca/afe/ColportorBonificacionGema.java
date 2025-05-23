package aca.afe;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ColportorBonificacionGema {
	private String matricula;
	private String depCaja;
	private String depBanco;
	private String depDiezmos;
	private String depCompras;
	private String depComprasBonificables;
	private String totalDepositos;
	private String bonificacionGema;
	private String bonificacionUM;
	
	// Constructor
	public ColportorBonificacionGema(){		
		matricula 						= "";		
		depCaja	        				= "";
		matricula						= "";
		depBanco						= "";
		depDiezmos						= "";
		depCompras						= "";
		depComprasBonificables			= "";
		totalDepositos					= "";
		bonificacionGema				= "";
		bonificacionUM					= "";
	}
	
	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getDepCaja() {
		return depCaja;
	}

	public void setDepCaja(String depCaja) {
		this.depCaja = depCaja;
	}

	public String getDepBanco() {
		return depBanco;
	}

	public void setDepBanco(String depBanco) {
		this.depBanco = depBanco;
	}

	public String getDepDiezmos() {
		return depDiezmos;
	}

	public void setDepDiezmos(String depDiezmos) {
		this.depDiezmos = depDiezmos;
	}

	public String getDepCompras() {
		return depCompras;
	}

	public void setDepCompras(String depCompras) {
		this.depCompras = depCompras;
	}

	public String getDepComprasBonificables() {
		return depComprasBonificables;
	}

	public void setDepComprasBonificables(String depComprasBonificables) {
		this.depComprasBonificables = depComprasBonificables;
	}

	public String getTotalDepositos() {
		return totalDepositos;
	}

	public void setTotalDepositos(String totalDepositos) {
		this.totalDepositos = totalDepositos;
	}

	public String getBonificacionGema() {
		return bonificacionGema;
	}

	public void setBonificacionGema(String bonificacionGema) {
		this.bonificacionGema = bonificacionGema;
	}

	public String getBonificacionUM() {
		return bonificacionUM;
	}

	public void setBonificacionUM(String bonificacionUM) {
		this.bonificacionUM = bonificacionUM;
	}

	public void mapeaReg(ResultSet rs ) throws SQLException{
		matricula 					= rs.getString("MATRICULA");		
		depCaja						= rs.getString("DEP_CAJA");
		depBanco					= rs.getString("DEP_BANCO");
		depDiezmos					= rs.getString("DEP_DIEZMOS");
		depCompras					= rs.getString("DEP_COMPRAS");
		depComprasBonificables		= rs.getString("DEP_COMPRAS_BONIFICABLES");
		totalDepositos				= rs.getString("TOTAL_DEPOSITOS");
		bonificacionGema			= rs.getString("BONIFICACION_GEMA");
		bonificacionUM				= rs.getString("BONIFICACION_UM");
	}
	
	public void mapeaRegId( Connection conn, String matricula ) throws SQLException, IOException{
 		PreparedStatement ps =  null;
 		ResultSet rs = null;
 		try{
 		ps = conn.prepareStatement("SELECT "+
 			" MATRICULA, DEP_CAJA, DEP_BANCO, DEP_DIEZMOS, DEP_COMPRAS, "+
 			" DEP_COMPRAS_BONIFICABLES,TOTAL_DEPOSITOS, BONIFICACION_GEMA, BONIFICACION_UM "+
 			" FROM NOE.COLPORTOR_BONIFICACION_GEMA WHERE MATRICULA = ? ");
 		ps.setString(1, matricula);	
 		rs = ps.executeQuery();
 		if (rs.next()){
 			mapeaReg(rs);
 		}
 		}catch(Exception ex){
			System.out.println("Error - aca.afe.BonifiacionGemaUtil|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
 	}
	
}