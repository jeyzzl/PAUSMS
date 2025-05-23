// Clase para la tabla de Acceso
package aca.mov;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserPas{
	private String username;
	private String realm;	
	@JsonProperty("tenant-domain")
	private String tenantdomain;
	
	// Constructor
	public UserPas(){		
		username 		= "0";
		realm			= "PRIMARY";		
		tenantdomain	= "um.movil";		
	}	
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getRealm() {
		return realm;
	}
	public void setRealm(String realm) {
		this.realm = realm;
	}

	public String getTenantdomain() {
		return tenantdomain;
	}
	public void setTenantdomain(String tenantdomain) {
		this.tenantdomain = tenantdomain;
	}	
}