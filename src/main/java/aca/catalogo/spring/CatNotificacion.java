// Bean del Catalogo de Religiones
package aca.catalogo.spring;


public class CatNotificacion{
	private String notificacionId;	
	private String notificacionNombre;
	
	public CatNotificacion(){
		notificacionId 		= "";		
		notificacionNombre	= "";
	}
	
	/**
	 * @return the notificacionId
	 */
	public String getNotificacionId() {
		return notificacionId;
	}

	/**
	 * @param notificacionId the notificacionId to set
	 */
	public void setNotificacionId(String notificacionId) {
		this.notificacionId = notificacionId;
	}

	/**
	 * @return the notificacionNombre
	 */
	public String getNotificacionNombre() {
		return notificacionNombre;
	}

	/**
	 * @param notificacionNombre the notificacionNombre to set
	 */
	public void setNotificacionNombre(String notificacionNombre) {
		this.notificacionNombre = notificacionNombre;
	}
	
}
