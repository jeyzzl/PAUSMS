package aca.investiga.spring;

public class InvPresupuesto {
	private String proyectoId;
	private String presupuestoId;
	private String presupuestoNombre;
	private String monto;
	private String tipo;
	
	public InvPresupuesto(){
		proyectoId		  = "";
		presupuestoId 	  = "-";
		presupuestoNombre = "-";
		monto			  = "-";
		tipo			  = "-";
	}
	
	public String getProyectoId() {
		return proyectoId;
	}
	public void setProyectoId(String proyectoId) {
		this.proyectoId = proyectoId;
	}
	public String getPresupuestoId() {
		return presupuestoId;
	}
	public void setPresupuestoId(String presupuestoId) {
		this.presupuestoId = presupuestoId;
	}
	public String getPresupuestoNombre() {
		return presupuestoNombre;
	}
	public void setPresupuestoNombre(String presupuestoNombre) {
		this.presupuestoNombre = presupuestoNombre;
	}
	public String getMonto() {
		return monto;
	}
	public void setMonto(String monto) {
		this.monto = monto;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}	
	
}
