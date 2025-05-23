package aca.conva;
//Bean de seguimiento de convalidacion segun la materia
import java.sql.*;

public class ConvMateria{
	
	private String convalidacionId;
	private String cursoId;
	private String fecha;
	private String tipo;
	private String estado;
	private String materia_O;
	private String creditos_O;
	private String nota_O;
	private String fNota;
	private String folio;
	
	public ConvMateria(){
		convalidacionId 	= "";
		cursoId				= "";
		fecha				= "";
		tipo				= "";
		estado				= "";
		materia_O			= "";
		creditos_O			= "";
		nota_O				= "";
		fNota				= "";
		folio				= "";
	}
	
	public String getfNota() {
		return fNota;
	}

	public void setfNota(String fNota) {
		this.fNota = fNota;
	}
	
	public String getConvalidacionId() {
		return convalidacionId;
	}
	
	public void setConvalidacionId(String convalidacionId) {
		this.convalidacionId = convalidacionId;
	}
	
	public String getCursoId() {
		return cursoId;
	}
	
	public void setCursoId(String cursoId) {
		this.cursoId = cursoId;
	}
	
	public String getEstado() {
		return estado;
	}
	
	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	public String getFecha() {
		return fecha;
	}
	
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	
	public String getTipo() {
		return tipo;
	}
	
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	public String getCreditos_O() {
		return creditos_O;
	}
	
	public void setCreditos_O(String creditos_O) {
		this.creditos_O = creditos_O;
	}

	public String getMateria_O() {
		return materia_O;
	}

	public void setMateria_O(String materia_O) {
		this.materia_O = materia_O;
	}

	public String getNota_O() {
		return nota_O;
	}

	public void setNota_O(String nota_O) {
		this.nota_O = nota_O;
	}
	
	public String getFolio() {
		return folio;
	}

	public void setFolio(String folio) {
		this.folio = folio;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		convalidacionId 	= rs.getString("CONVALIDACION_ID");
		cursoId 			= rs.getString("CURSO_ID");
		fecha	 			= rs.getString("FECHA");
		tipo				= rs.getString("TIPO");
		estado				= rs.getString("ESTADO");
		materia_O			= rs.getString("MATERIA_O");
		creditos_O			= rs.getString("CREDITOS_O");
		nota_O				= rs.getString("NOTA_O");
		fNota				= rs.getString("FECHA_NOTA");
		folio				= rs.getString("FOLIO");
	}
	
	public void mapeaRegId( Connection conn, String convalidacionId, String cursoId ) throws SQLException{	
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = conn.prepareStatement("SELECT "+
				"CONVALIDACION_ID, CURSO_ID, TO_CHAR(FECHA, 'DD/MM/YYYY') AS FECHA, TIPO, ESTADO, MATERIA_O, CREDITOS_O, NOTA_O, TO_CHAR(FECHA_NOTA,'DD/MM/YYYY') AS FECHA_NOTA, FOLIO "+
				"FROM ENOC.CONV_MATERIA WHERE CONVALIDACION_ID = TO_NUMBER(?,'9999999') " + 
				"AND CURSO_ID = ?");
			ps.setString(1, convalidacionId);
			ps.setString(2, cursoId);
			
			rs = ps.executeQuery();
			if (rs.next()){
				mapeaReg(rs);
			}
		}catch(Exception ex){
			System.out.println("Error - aca.conva.ConvMateria|mapeaRegId|:"+ex);
		}finally{
			if(rs!=null) rs.close();
			if(ps!=null) ps.close();
		}	
				
	}
	
}