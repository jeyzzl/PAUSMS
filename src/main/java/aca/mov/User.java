// Clase para la tabla de Acceso
package aca.mov;

import java.util.List;

public class User{
	private String username;
	private String realm;
	private String password;
	private List<Claims> claims;
	
	public User(){		
		username 		= "";
		realm			= "";
		password 		= "";
		claims			= null;
	}	

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRealm() {
		return realm;
	}

	public void setRealm(String realm) {
		this.realm = realm;
	}

	public List<Claims> getClaims() {
		return claims;
	}

	public void setClaims(List<Claims> claims) {
		this.claims = claims;
	}
}