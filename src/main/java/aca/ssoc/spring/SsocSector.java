package aca.ssoc.spring;

public class SsocSector {

	private String sectorId;
	private String sectorNombre;
	
	public SsocSector() {
		sectorId     = "";
		sectorNombre = "";
	}

	public String getSectorId() {
		return sectorId;
	}

	public void setSectorId(String sectorId) {
		this.sectorId = sectorId;
	}

	public String getSectorNombre() {
		return sectorNombre;
	}

	public void setSectorNombre(String sectorNombre) {
		this.sectorNombre = sectorNombre;
	}
	
}
