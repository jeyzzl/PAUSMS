package  aca.financiero.spring;

public class FinConcept{
	private int concId;
	private String name;
	private double unitCost;
	private String type;	
	private String status;
	private String cursoClave;
	private String code;
	
	public FinConcept(){
		concId			= 0;
		name			= "";
		unitCost		= 0;
		type			= "F";
		status			= "A";
		cursoClave		= "0";
		code 			= "0";
	}

	public int getConcId() {
		return concId;
	}
	public void setConcId(int concId) {
		this.concId = concId;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public double getUnitCost() {
		return unitCost;
	}
	public void setUnitCost(double unitCost) {
		this.unitCost = unitCost;
	}

	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

	public String getCursoClave() {
		return cursoClave;
	}
	public void setCursoClave(String cursoClave) {
		this.cursoClave = cursoClave;
	}

	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
}
