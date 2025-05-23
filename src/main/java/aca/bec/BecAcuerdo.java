package aca.bec;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BecAcuerdo {
	private String folio;
	private String codigoPersonal;
	private String idEjercicio;
	private String tipo;
	private String fecha;
	private String matricula;
	private String ensenanza;
	private String internado;
	private String valor;
	private String vigencia;
	private String estado;
	private String promesa;
	private String idCcosto;
	private String puestoId;
	private String horas;
	private String tipoadicional;
	private String usuario;
	
	public BecAcuerdo(){
		folio 			= "0";
		codigoPersonal	= "0";
		idEjercicio 	= "0";
		tipo 			= "0";
		fecha 			= aca.util.Fecha.getHoy();
		matricula		= "0";
		ensenanza		= "0";
		internado 		= "0";
		valor 			= "0";
		vigencia 		= aca.util.Fecha.getHoy();
		estado 			= "A";
		promesa			= "0";
		idCcosto		= "0";
		puestoId		= "0";
		horas 			= "0";
		tipoadicional	= "0";
		usuario 		= "0";
	}

	public String getFolio() {
		return folio;
	}

	public void setFolio(String folio) {
		this.folio = folio;
	}

	public String getCodigoPersonal() {
		return codigoPersonal;
	}

	public void setCodigoPersonal(String codigoPersonal) {
		this.codigoPersonal = codigoPersonal;
	}

	public String getIdEjercicio() {
		return idEjercicio;
	}

	public void setIdEjercicio(String idEjercicio) {
		this.idEjercicio = idEjercicio;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getEnsenanza() {
		return ensenanza;
	}

	public void setEnsenanza(String ensenanza) {
		this.ensenanza = ensenanza;
	}

	public String getInternado() {
		return internado;
	}

	public void setInternado(String internado) {
		this.internado = internado;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public String getVigencia() {
		return vigencia;
	}

	public void setVigencia(String vigencia) {
		this.vigencia = vigencia;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getPromesa() {
		return promesa;
	}

	public void setPromesa(String promesa) {
		this.promesa = promesa;
	}

	public String getIdCcosto() {
		return idCcosto;
	}

	public void setIdCcosto(String idCcosto) {
		this.idCcosto = idCcosto;
	}

	public String getPuestoId() {
		return puestoId;
	}

	public void setPuestoId(String puestoId) {
		this.puestoId = puestoId;
	}

	public String getHoras() {
		return horas;
	}

	public void setHoras(String horas) {
		this.horas = horas;
	}
	
	public String getTipoadicional() {
		return tipoadicional;
	}

	public void setTipoadicional(String tipoadicional) {
		this.tipoadicional = tipoadicional;
	}
	
	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	
	public void mapeaReg(ResultSet rs) throws SQLException{
		folio 			= rs.getString("FOLIO");
		codigoPersonal	= rs.getString("CODIGO_PERSONAL");
		idEjercicio 	= rs.getString("ID_EJERCICIO");
		tipo 			= rs.getString("TIPO");
		fecha 			= rs.getString("FECHA");
		matricula		= rs.getString("MATRICULA");
		ensenanza		= rs.getString("ENSENANZA");
		internado 		= rs.getString("INTERNADO");
		valor 			= rs.getString("VALOR");
		vigencia		= rs.getString("VIGENCIA");
		estado			= rs.getString("ESTADO");
		promesa			= rs.getString("PROMESA");
		idCcosto		= rs.getString("ID_CCOSTO");
		puestoId		= rs.getString("PUESTO_ID");
		horas		 	= rs.getString("HORAS");
		tipoadicional	= rs.getString("TIPOADICIONAL");
		usuario 		= rs.getString("USUARIO");
	}
	
	public void mapeaRegId(Connection conn) throws SQLException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement("SELECT FOLIO, CODIGO_PERSONAL, ID_EJERCICIO, TIPO, TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA, MATRICULA, ENSENANZA, INTERNADO, VALOR, " +
					" TO_CHAR(VIGENCIA, 'DD/MM/YYYY') AS VIGENCIA, ESTADO, PROMESA, ID_CCOSTO, PUESTO_ID, HORAS, TIPOADICIONAL, USUARIO " +
					" FROM ENOC.BEC_ACUERDO WHERE FOLIO = TO_NUMBER(?, '999') AND CODIGO_PERSONAL = ? "); 
			
			ps.setString(1,  folio);
			ps.setString(2,  codigoPersonal);
			
			rs = ps.executeQuery();
			if (rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecAcuerdo|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
	}
	
}