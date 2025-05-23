//asdasdsd
package aca.bec;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BecTipo {
	private String idEjercicio;
	private String tipo;
	private String nombre;
	private String departamentos;
	private String cuenta;
	private String porcentaje;
	private String meses;
	private String horas;
	private String horasPrepa;
	private String acuerdo;
	private String importe;
	private String tipoAlumno;
	private String diezmo;
	private String estado;
	private String acumula;
	private String colporta;
	private String maximo;
	private String limite;
	private String cuentaSunplus;
	private String flag;
	
	public BecTipo(){
		idEjercicio		= "";
		tipo			= "";
		nombre			= "";
		departamentos   = "";
		cuenta 			= "";
		porcentaje 		= "";
		meses 	 		= "";
		horas			= "";
		horasPrepa		= "";
		acuerdo			= "";
		importe			= "";
		tipoAlumno		= "";
		diezmo			= "N";
		estado			= "A";
		acumula			= "N";
		colporta 		= "N";
		maximo 			= "0";
		limite 			= "0";
		cuentaSunplus 	= "-";
		flag 			= "";
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

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDepartamentos() {
		return departamentos;
	}

	public void setDepartamentos(String departamentos) {
		this.departamentos = departamentos;
	}

	public String getCuenta() {
		return cuenta;
	}

	public void setCuenta(String cuenta) {
		this.cuenta = cuenta;
	}

	public String getPorcentaje() {
		return porcentaje;
	}

	public void setPorcentaje(String porcentaje) {
		this.porcentaje = porcentaje;
	}

	public String getMeses() {
		return meses;
	}

	public void setMeses(String meses) {
		this.meses = meses;
	}
	
	public String getHoras() {
		return horas;
	}

	public void setHoras(String horas) {
		this.horas = horas;
	}

	public String getHorasPrepa() {
		return horasPrepa;
	}

	public void setHorasPrepa(String horasPrepa) {
		this.horasPrepa = horasPrepa;
	}

	public String getAcuerdo() {
		return acuerdo;
	}

	public void setAcuerdo(String acuerdo) {
		this.acuerdo = acuerdo;
	}

	public String getImporte() {
		return importe;
	}

	public void setImporte(String importe) {
		this.importe = importe;
	}	
	
	public String getTipoAlumno() {
		return tipoAlumno;
	}

	public void setTipoAlumno(String tipoAlumno) {
		this.tipoAlumno = tipoAlumno;
	}
	
	public String getDiezmo() {
		return diezmo;
	}

	public void setDiezmo(String diezmo) {
		this.diezmo = diezmo;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getAcumula() {
		return acumula;
	}

	public void setAcumula(String acumula) {
		this.acumula = acumula;
	}

	public String getColporta() {
		return colporta;
	}

	public void setColporta(String colporta) {
		this.colporta = colporta;
	}
	
	public String getMaximo() {
		return maximo;
	}


	public void setMaximo(String maximo) {
		this.maximo = maximo;
	}
	
	public String getLimite() {
		return limite;
	}

	public void setLimite(String limite) {
		this.limite = limite;
	}
	
	public String getCuentaSunplus() {
		return cuentaSunplus;
	}
	
	public void setCuentaSunplus(String cuentaSunplus) {
		this.cuentaSunplus = cuentaSunplus;
	}
	
	public void mapeaReg(ResultSet rs) throws SQLException{
		idEjercicio			= rs.getString("ID_EJERCICIO");
		tipo 				= rs.getString("TIPO");
		nombre 				= rs.getString("NOMBRE");
		departamentos		= rs.getString("DEPARTAMENTOS");
		cuenta 				= rs.getString("CUENTA");
		porcentaje 			= rs.getString("PORCENTAJE");
		meses 	 			= rs.getString("MESES");
		horas				= rs.getString("HORAS");
		horasPrepa			= rs.getString("HORAS_PREPA");
		acuerdo				= rs.getString("ACUERDO");
		importe				= rs.getString("IMPORTE");
		tipoAlumno			= rs.getString("TIPO_ALUMNO");
		diezmo				= rs.getString("DIEZMO");
		estado				= rs.getString("ESTADO");
		acumula				= rs.getString("ACUMULA");
		colporta			= rs.getString("COLPORTA");
		maximo				= rs.getString("MAXIMO");
		limite				= rs.getString("LIMITE");
		cuentaSunplus		= rs.getString("CUENTA_SUNPLUS");
	}
	
	public void mapeaRegId(Connection conn) throws SQLException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement("SELECT  * " +
					" FROM ENOC.BEC_TIPO WHERE ID_EJERCICIO = ? AND TIPO = TO_NUMBER(?,'99')"); 
			
			ps.setString(1,  idEjercicio);
			ps.setString(2,  tipo);
			
			rs = ps.executeQuery();
			if (rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.bec.BecTipo|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}
	}
	
}