package aca.bec.spring;


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
	private String aplicaAdicional;
	private String maximo;
	private String limite;
	private String cuentaSunplus;
	private String flag;
	private String mostrar;
	private String descripcion;
	
	
	public BecTipo(){
		idEjercicio			= "0";
		tipo				= "0";
		nombre				= "-";
		departamentos		= "";
		cuenta				= "0";
		porcentaje			= "0";
		meses				= "";
		horas				= "";
		horasPrepa			= "";
		acuerdo				= "";
		importe				= "0";
		tipoAlumno			= "";
		diezmo				= "0";
		estado				= "";
		acumula				= "0";
		colporta			= "";
		aplicaAdicional		= "";
		maximo				= "0";
		limite				= "0";
		cuentaSunplus		= "-";
		flag				= "";
		mostrar				= "S";
		descripcion			= "-";
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

	public String getAplicaAdicional() {
		return aplicaAdicional;
	}
	public void setAplicaAdicional(String aplicaAdicional) {
		this.aplicaAdicional = aplicaAdicional;
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
	
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getMostrar() {
		return mostrar;
	}
	public void setMostrar(String mostrar){
		this.mostrar = mostrar;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}	
}