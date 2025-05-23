package aca.tit.spring;

public class TitPago {
	
    String pagoId;
    String nombrePago;

    public TitPago() {
    	pagoId 	 		= "";
    	nombrePago		= "";
    }

	public String getPagoId() {
		return pagoId;
	}

	public void setPagoId(String pagoId) {
		this.pagoId = pagoId;
	}

	public String getNombrePago() {
		return nombrePago;
	}

	public void setNombrePago(String nombrePago) {
		this.nombrePago = nombrePago;
	}

}