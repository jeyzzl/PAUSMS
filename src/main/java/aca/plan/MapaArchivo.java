// Bean del Catalogo Curso
package  aca.plan;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MapaArchivo{
	
	private String planId;
	private String folio;
	private String nombre;
	private byte[] archivo;	
	
	public MapaArchivo(){
		planId		= "";
		folio		= "0";
		nombre		= "-";
		archivo		= null;		
	}

	public String getPlanId() {
		return planId;
	}

	public void setPlanId(String planId) {
		this.planId = planId;
	}

	public String getFolio() {
		return folio;
	}

	public void setFolio(String folio) {
		this.folio = folio;
	}

	public byte[] getArchivo() {
		return archivo;
	}

	public void setArchivo(byte[] archivo) {
		this.archivo = archivo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public void mapeaReg(ResultSet rs ) throws SQLException{
		planId			= rs.getString("PLAN_ID")==null?"-":rs.getString("PLAN_ID");
		folio			= rs.getString("FOLIO")==null?"0":rs.getString("FOLIO");
		nombre			= rs.getString("NOMBRE")==null?"-":rs.getString("NOMBRE");
		archivo			= rs.getBytes("ARCHIVO")==null?null:rs.getBytes("ARCHIVO");
	}
	
}