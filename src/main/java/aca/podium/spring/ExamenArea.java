package aca.podium.spring;

public class ExamenArea {
	
	private int id;
	private boolean termino;
	private boolean activo;
	private int areaId;
	private int examenId;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public boolean isTermino() {
		return termino;
	}
	public void setTermino(boolean termino) {
		this.termino = termino;
	}
	public boolean isActivo() {
		return activo;
	}
	public void setActivo(boolean activo) {
		this.activo = activo;
	}
	public int getAreaId() {
		return areaId;
	}
	public void setAreaId(int areaId) {
		this.areaId = areaId;
	}
	public int getExamenId() {
		return examenId;
	}
	public void setExamenId(int examenId) {
		this.examenId = examenId;
	}
}
