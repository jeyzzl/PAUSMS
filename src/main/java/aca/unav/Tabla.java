package aca.unav;

public class Tabla {

	private String usuario;
	private String tableName;
	
	public Tabla() {
		usuario     = "";
		tableName 	= "";
	}

	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}	
}
