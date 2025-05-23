// Bean del Catalogo Planes
package  aca.plan.spring;

public class MapaPlan{
	
	private String planId;
	private String carreraId;
	private String nombrePlan;
	private String fInicio;
	private String fFinal;
	private String fActualiza;
	private String numCursos;
	private String minimo;
	private String maximo;
	private String carreraSe;
	private String rvoe;
	private String oficial;
	// Letras --> A=Admisible, V=Vigente e I=Inactivo
	private String estado;
	private String enLinea;
	// Total de semestres o tetramestres a cursar 
	private String ciclos;
	private String notaExtra;
	// Letras --> S=Si, N=No
	private String general;
	private String planSE;
	private String nombrePlanMujer;
	private String claveProfesiones;
	private String precio;
	private String rvoeInicial;	
	private String versionId;
	private String semsys;
	private String expediente;
	private String creditos;
	private String planOrigen;
	private String descuento;
	
	public MapaPlan(){
		planId			= "";
		carreraId		= "";
		nombrePlan		= "-";
		fInicio			= "";
		fFinal			= "";
		fActualiza		= "";
		numCursos		= "0";
		minimo			= "0";
		maximo			= "0";
		carreraSe		= "";
		rvoe			= "";
		oficial			= "";
		estado			= "";
		enLinea			= "";
		ciclos 			= "0";
		notaExtra		= "";
		general			= "";
		planSE			= "X";
		precio			= "";
		rvoeInicial		= "-";
		versionId		= "0";
		semsys			= "-";
		expediente		= "-";
		creditos		= "0";
		planOrigen		= "-";
		descuento 		= "0";
	}
	

	public String getPlanOrigen() {
		return planOrigen;
	}


	public void setPlanOrigen(String planOrigen) {
		this.planOrigen = planOrigen;
	}


	public String getEnLinea() {
		return enLinea;
	}


	public String getExpediente() {
		return expediente;
	}


	public void setExpediente(String expediente) {
		this.expediente = expediente;
	}


	public String getCreditos() {
		return creditos;
	}


	public void setCreditos(String creditos) {
		this.creditos = creditos;
	}


	public void setEnLinea(String enLinea) {
		this.enLinea = enLinea;
	}


	public String getEstado() {
		return estado;
	}
	

	public void setEstado(String estado) {
		this.estado = estado;
	}
	

	public String getOficial() {
		return oficial;
	}
	

	public void setOficial(String oficial) {
		this.oficial = oficial;
	}
	

	public String getPlanId(){
		return planId;
	}
	
	public void setPlanId( String planId){
		this.planId = planId;
	}	
	
	public String getCarreraId(){
		return carreraId;
	}
	
	public void setCarreraId( String carreraId){
		this.carreraId = carreraId;
	}	
	
	public String getNombrePlan(){
		return nombrePlan;
	}
	
	public void setNombrePlan( String nombrePlan){
		this.nombrePlan = nombrePlan;
	}
	
	public String getFInicio(){
		return fInicio;
	}
	
	public void setFInicio( String fInicio){
		this.fInicio = fInicio;
	}
	
	public String getFFinal(){
		return fFinal;
	}
	
	public void setFFinal( String fFinal){
		this.fFinal = fFinal;
	}
	
	public String getFActualiza(){
		return fActualiza;
	}
	
	public void setFActualiza( String fActualiza){
		this.fActualiza = fActualiza;
	}
	
	public String getNumCursos(){
		return numCursos;
	}
	
	public void setNumCursos( String numCursos){
		this.numCursos = numCursos;
	}
	
	public String getMinimo(){
		return minimo;
	}
	
	public void setMinimo( String minimo){
		this.minimo = minimo;
	}
	
	public String getMaximo(){
		return maximo;
	}
	
	public void setMaximo( String maximo){
		this.maximo = maximo;
	}
	
	public String getCarreraSe(){
		return carreraSe;
	}
	
	public void setCarreraSe( String carreraSe){
		this.carreraSe = carreraSe;
	}
	
	public String getRvoe(){
		return rvoe;
	}
	
	public void setRvoe( String rvoe){
		this.rvoe = rvoe;
	}	
	
	public String getCiclos() {
		return ciclos;
	}


	public void setCiclos(String ciclos) {
		this.ciclos = ciclos;
	}

	
	public String getNotaExtra() {
		return notaExtra;
	}
	
	public void setNotaExtra(String notaExtra) {
		this.notaExtra = notaExtra;
	}


	public String getGeneral() {
		return general;
	}


	public void setGeneral(String general) {
		this.general = general;
	}
	
	public String getPlanSE() {
		return planSE;
	}

	public void setPlanSE(String planSE) {
		this.planSE = planSE;
	}
	
	public String getNombrePlanMujer() {
		return nombrePlanMujer;
	}

	public void setNombrePlanMujer(String nombrePlanMujer) {
		this.nombrePlanMujer = nombrePlanMujer;
	}	

	public String getClaveProfesiones() {
		return claveProfesiones;
	}

	public void setClaveProfesiones(String claveProfesiones) {
		this.claveProfesiones = claveProfesiones;
	}
	
	public String getPrecio() {
		return precio;
	}

	public void setPrecio(String precio) {
		this.precio = precio;
	}
	
	public String getRvoeInicial() {
		return rvoeInicial;
	}
	
	public void setRvoeInicial(String rvoeInicial) {
		this.rvoeInicial = rvoeInicial;
	}
	
	public String getVersionId() {
		return versionId;
	}
	
	public void setVersionId(String versionId) {
		this.versionId = versionId;
	}


	public String getSemsys() {
		return semsys;
	}


	public void setSemsys(String semsys) {
		this.semsys = semsys;
	}

	public String getDescuento() {
		return descuento;
	}
	public void setDescuento(String descuento) {
		this.descuento = descuento;
	}	

	
}