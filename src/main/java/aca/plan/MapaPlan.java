// Bean del Catalogo Planes
package  aca.plan;

import java.sql.*;

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
		precio			= "N";
		rvoeInicial		= "-";
		
	}	

	public String getEnLinea() {
		return enLinea;
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

	public void mapeaReg(ResultSet rs ) throws SQLException{
		planId 				= rs.getString("PLAN_ID");
		carreraId 			= rs.getString("CARRERA_ID");
		nombrePlan 			= rs.getString("NOMBRE_PLAN");
		fInicio	 			= rs.getString("F_INICIO");
		fFinal 				= rs.getString("F_FINAL");
		fActualiza			= rs.getString("F_ACTUALIZA");
		numCursos 			= rs.getString("NUM_CURSOS");
		minimo 				= rs.getString("MINIMO");
		maximo				= rs.getString("MAXIMO");
		carreraSe 			= rs.getString("CARRERA_SE");
		rvoe				= rs.getString("RVOE");
		oficial				= rs.getString("OFICIAL");
		estado				= rs.getString("ESTADO");
		enLinea				= rs.getString("ENLINEA");
		ciclos				= rs.getString("CICLOS");
		notaExtra			= rs.getString("NOTA_EXTRA");
		general				= rs.getString("GENERAL");
		planSE				= rs.getString("PLAN_SE");
		nombrePlanMujer	 	= rs.getString("NOMBRE_PLAN_MUJER");
		claveProfesiones 	= rs.getString("CLAVE_PROFESIONES");
		precio				= rs.getString("PRECIO");
		rvoeInicial			= rs.getString("RVOE_INICIAL");
	}
	
	public void mapeaRegId( Connection conn, String planId ) throws SQLException{
		PreparedStatement ps 	= null;
		ResultSet rs 			= null;
		
		try{
			ps = conn.prepareStatement("SELECT PLAN_ID, CARRERA_ID, NOMBRE_PLAN,"
				+ " TO_CHAR(F_INICIO,'dd/mm/yyyy') F_INICIO, TO_CHAR(F_FINAL,'dd/mm/yyyy') F_FINAL,"
				+ " TO_CHAR(F_ACTUALIZA,'dd/mm/yyyy') F_ACTUALIZA,"
				+ " NUM_CURSOS, MINIMO, MAXIMO, CARRERA_SE, RVOE, OFICIAL, ESTADO, ENLINEA, CICLOS,"
				+ " NOTA_EXTRA, GENERAL, PLAN_SE, NOMBRE_PLAN_MUJER, CLAVE_PROFESIONES, PRECIO, RVOE_INICIAL"
				+ " FROM ENOC.MAPA_PLAN"
				+ " WHERE PLAN_ID = ? "); 
			ps.setString(1, planId);
			
			rs = ps.executeQuery();
			if (rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.plan.PlanUtil|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}		
	}	
}