package aca.unav;

public class Columna {

	private String owner;
	private String tableName;
	private String columnName;
	private String dataType;
	private String dataLength;
	private String dataPrecision;
	private String dataDefault;
	private String dataScale;
	private String nullable;	
	
	public Columna() {
		owner 	   		= "";
		tableName 		= "";
		columnName		= "";
		dataType		= "";
		dataLength		= "";		
		dataPrecision	= "";
		dataDefault		= "-";
		dataScale		= "-";
		nullable		= "";
	}

	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getColumnName() {
		return columnName;
	}
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getDataLength() {
		return dataLength;
	}
	public void setDataLength(String dataLength) {
		this.dataLength = dataLength;
	}

	public String getNullable() {
		return nullable;
	}
	public void setNullable(String nullable) {
		this.nullable = nullable;
	}

	public String getDataPrecision() {
		return dataPrecision;
	}
	public void setDataPrecision(String dataPrecision) {
		this.dataPrecision = dataPrecision;
	}

	public String getDataDefault() {
		return dataDefault;
	}
	public void setDataDefault(String dataDefault) {
		this.dataDefault = dataDefault;
	}

	public String getDataScale() {
		return dataScale;
	}
	public void setDataScale(String dataScale) {
		this.dataScale = dataScale;
	}	
}