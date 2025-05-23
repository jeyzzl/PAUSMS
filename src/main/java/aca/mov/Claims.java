package aca.mov;

public class Claims{
	String uri;
	String value;
	
	// Constructor
	public Claims(){		
		uri 		= "www";
		value 		= "-";		
	}
	
	public Claims(String uri, String value){		
		this.uri 		= uri;
		this.value 		= value;		
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}	
	
}