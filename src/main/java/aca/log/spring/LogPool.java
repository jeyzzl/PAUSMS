package aca.log.spring;

public class LogPool {
	
	public String id;
	public String dato;
	public String url;

	public LogPool() {
		id			= "0";
		dato		= "-";
		url			= "-";
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDato() {
		return dato;
	}

	public void setDato(String dato) {
		this.dato = dato;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
}
