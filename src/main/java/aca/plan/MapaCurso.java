// Bean del Catálogo Cursos
package  aca.plan;
import java.sql.*;

public class MapaCurso{
	
	private String planId;
	private String cursoId;
	private String cursoClave;
	private String nombreCurso;
	private String ciclo;
	private String creditos;
	private String ht;
	private String hp;
	private String hi;
	private String fCreada;
	private String notaAprobatoria;
	private String estado;
	private String tipoCursoId;
	private String unid;
	private String onLine;
	private String obligatorio;
	private String completo;
	private String hh;
	private String hfd;
	private String hss;
	private String has;
	private String horario;
	private String areaId;
	
	public MapaCurso(){
		planId			= "";
		cursoId			= "";
		cursoClave		= "";
		nombreCurso		= "";
		ciclo			= "";
		creditos		= "0";
		ht				= "0";
		hp				= "0";
		hi				= "0";
		fCreada			= "";
		notaAprobatoria	= "";
		estado			= "";
		tipoCursoId		= "";
		unid			= "";
		onLine			= "";
		obligatorio		= "";
		completo		= "";
		hh				= "0";
		hfd				= "1";
		hss				= "0";
		has				= "0";
		horario 		= "0";
		areaId	 		= "0";
	}
	
	public String getHfd() {
		return hfd;
	}

	public void setHfd(String hfd) {
		this.hfd = hfd;
	}

	public String getHss() {
		return hss;
	}

	public void setHss(String hss) {
		this.hss = hss;
	}

	public String getHas() {
		return has;
	}

	public void setHas(String has) {
		this.has = has;
	}

	public String getHh() {
		return hh;
	}

	public void setHh(String hh) {
		this.hh = hh;
	}

	public String getPlanId(){
		return planId;
	}
	
	public void setPlanId( String planId){
		this.planId = planId;
	}	
	
	public String getCursoId(){
		return cursoId;
	}
	
	public void setCursoId( String cursoId){
		this.cursoId = cursoId;
	}	
	
	public String getNombreCurso(){
		return nombreCurso;
	}
	
	public void setNombreCurso( String nombreCurso){
		this.nombreCurso = nombreCurso;
	}
	
	public String getCiclo(){
		return ciclo;
	}
	
	public void setCiclo( String ciclo){
		this.ciclo = ciclo;
	}
	
	public String getCreditos(){
		return creditos;
	}
	
	public void setCreditos( String creditos){
		this.creditos = creditos;
	}
	
	public String getHt(){
		return ht;
	}
	
	public void setHt( String ht){
		this.ht = ht;
	}
	
	public String getHp(){
		return hp;
	}
	
	public void setHp( String hp){
		this.hp = hp;
	}
	
	public String getFCreada(){
		return fCreada;
	}
	
	public void setFCreada( String fCreada){
		this.fCreada = fCreada;
	}
	
	public String getNotaAprobatoria(){
		return notaAprobatoria;
	}
	
	public void setNotaAprobatoria( String notaAprobatoria){
		this.notaAprobatoria = notaAprobatoria;
	}
	
	public String getEstado(){
		return estado;
	}
	
	public void setEstado( String Estado){
		this.estado = Estado;
	}
	
	public String getTipoCursoId() {
		return tipoCursoId;
	}

	public void setTipoCursoId(String tipoCursoId) {
		this.tipoCursoId = tipoCursoId;
	}
	
	public String getOnLine() {
		return onLine;
	}

	public void setOnLine(String onLine) {
		this.onLine = onLine;
	}

	public String getUnid() {
		return unid;
	}

	public void setUnid(String unid) {
		this.unid = unid;
	}
	
	public String getCursoClave() {
		return cursoClave;
	}

	public void setCursoClave(String cursoClave) {
		this.cursoClave = cursoClave;
	}

	public String getfCreada() {
		return fCreada;
	}

	public void setfCreada(String fCreada) {
		this.fCreada = fCreada;
	}

	public String getHi() {
		return hi;
	}

	public void setHi(String hi) {
		this.hi = hi;
	}
	
	public String getObligatorio() {
		return obligatorio;
	}

	public void setObligatorio(String obligatorio) {
		this.obligatorio = obligatorio;
	}
	
	public String getCompleto() {
		return completo;
	}

	public void setCompleto(String completo) {
		this.completo = completo;
	}	
	
	public String getHorario() {
		return horario;
	}

	public void setHorario(String horario) {
		this.horario = horario;
	}

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	public void mapeaReg(ResultSet rs ) throws SQLException{
		planId 			= rs.getString("PLAN_ID");
		cursoId 		= rs.getString("CURSO_ID");
		nombreCurso 	= rs.getString("NOMBRE_CURSO");
		ciclo	 		= rs.getString("CICLO");
		creditos 		= rs.getString("CREDITOS");
		ht				= rs.getString("HT");
		hp 				= rs.getString("HP");
		hi 				= rs.getString("HI");
		fCreada 		= rs.getString("F_CREADA");
		notaAprobatoria	= rs.getString("NOTA_APROBATORIA");
		estado 			= rs.getString("ESTADO");
		tipoCursoId		= rs.getString("TIPOCURSO_ID");	
		unid			= rs.getString("UNID");
		onLine			= rs.getString("ON_LINE");
		cursoClave		= rs.getString("CURSO_CLAVE");
		obligatorio		= rs.getString("OBLIGATORIO");
		completo		= rs.getString("COMPLETO");
		hh				= rs.getString("HH");
		hfd				= rs.getString("HFD");
		hss				= rs.getString("HSS");
		has				= rs.getString("HAS");
		horario			= rs.getString("HORARIO");
		areaId			= rs.getString("AREA_ID");
	}
	
	public void mapeaRegId( Connection conn, String cursoId) throws SQLException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try{
			ps = conn.prepareStatement("SELECT PLAN_ID, CURSO_ID, NOMBRE_CURSO, " +
				"CICLO, CREDITOS, COALESCE(HT,0) AS HT, COALESCE(HP,0) AS HP, COALESCE(HI,0) AS HI, "+
				"TO_CHAR(F_CREADA,'DD/MM/YYYY') F_CREADA, " +
				"NOTA_APROBATORIA, ESTADO, TIPOCURSO_ID, " +
				"UNID, ON_LINE, CURSO_CLAVE, OBLIGATORIO, COMPLETO, HH, HFD, HSS, HAS, HORARIO, AREA_ID " +
				"FROM ENOC.MAPA_CURSO WHERE CURSO_ID = ? ");
			ps.setString(1, cursoId);
			rs = ps.executeQuery();
			if (rs.next()){
				mapeaReg(rs);
			}
		
		}catch(Exception ex){
			System.out.println("Error - aca.plan.MapaCurso|mapeaRegId|:"+ex);
		}finally{
			try { rs.close(); } catch (Exception ignore) { }
			try { ps.close(); } catch (Exception ignore) { }
		}		
		
	}
}