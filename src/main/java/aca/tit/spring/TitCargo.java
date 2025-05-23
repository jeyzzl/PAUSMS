/*
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package aca.tit.spring;

public class TitCargo {
	
    String cargoId;
    String cargoNombre;

    public TitCargo() {
    	
    	cargoId 	 	= "";
    	cargoNombre		= "";
    }

	public String getCargoId() {
		return cargoId;
	}

	public void setCargoId(String cargoId) {
		this.cargoId = cargoId;
	}

	public String getCargoNombre() {
		return cargoNombre;
	}

	public void setCargoNombre(String cargoNombre) {
		this.cargoNombre = cargoNombre;
	}
   	
}