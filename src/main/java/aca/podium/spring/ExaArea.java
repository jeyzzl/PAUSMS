package aca.podium.spring;

public class ExaArea {
	
	private int id;
	private String nombre;
	private int orden;
	private int tiempoPre;
	private int tiempoPos;
	private int minimoPre;
	private int minimoPos;
	private float puntosPre;
	private float puntosPos;
	private int facilPre;
	private int facilPos;
	private int medioPre;
	private int medioPos;
	private int dificilPre;
	private int dificilPos;
	
	public ExaArea() {
		nombre 			= "";
		orden			= 0;
		tiempoPre 		= 0;
		tiempoPos 		= 0;
		minimoPre 		= 0;
		minimoPos 		= 0;
		puntosPre 		= 0;
		puntosPos 		= 0;
		facilPre		= 0;
		facilPos		= 0;
		medioPre		= 0;
		medioPos		= 0;
		dificilPre		= 0;
		dificilPos		= 0;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getOrden() {
		return orden;
	}

	public void setOrden(int orden) {
		this.orden = orden;
	}

	public int getTiempoPre() {
		return tiempoPre;
	}

	public void setTiempoPre(int tiempoPre) {
		this.tiempoPre = tiempoPre;
	}

	public int getTiempoPos() {
		return tiempoPos;
	}

	public void setTiempoPos(int tiempoPos) {
		this.tiempoPos = tiempoPos;
	}

	public int getMinimoPre() {
		return minimoPre;
	}

	public void setMinimoPre(int minimoPre) {
		this.minimoPre = minimoPre;
	}

	public int getMinimoPos() {
		return minimoPos;
	}

	public void setMinimoPos(int minimoPos) {
		this.minimoPos = minimoPos;
	}

	public float getPuntosPre() {
		return puntosPre;
	}

	public void setPuntosPre(float puntosPre) {
		this.puntosPre = puntosPre;
	}

	public float getPuntosPos() {
		return puntosPos;
	}

	public void setPuntosPos(float puntosPos) {
		this.puntosPos = puntosPos;
	}

	public int getFacilPre() {
		return facilPre;
	}

	public void setFacilPre(int facilPre) {
		this.facilPre = facilPre;
	}

	public int getFacilPos() {
		return facilPos;
	}

	public void setFacilPos(int facilPos) {
		this.facilPos = facilPos;
	}

	public int getMedioPre() {
		return medioPre;
	}

	public void setMedioPre(int medioPre) {
		this.medioPre = medioPre;
	}

	public int getMedioPos() {
		return medioPos;
	}

	public void setMedioPos(int medioPos) {
		this.medioPos = medioPos;
	}

	public int getDificilPre() {
		return dificilPre;
	}

	public void setDificilPre(int dificilPre) {
		this.dificilPre = dificilPre;
	}

	public int getDificilPos() {
		return dificilPos;
	}

	public void setDificilPos(int dificilPos) {
		this.dificilPos = dificilPos;
	}
}
