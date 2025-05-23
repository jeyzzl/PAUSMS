package  aca.financiero.spring;

public class FinGroupConcept{
	private int groupId;
	private int concId;
	private int noUnits;
	
	public FinGroupConcept(){
		groupId			= 0;
		concId			= 0;
		noUnits			= 0;		
	}

	public int getGroupId() {
		return groupId;
	}
	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	public int getConcId() {
		return concId;
	}
	public void setConcId(int concId) {
		this.concId = concId;
	}

	public int getNoUnits() {
		return noUnits;
	}
	public void setNoUnits(int noUnits) {
		this.noUnits = noUnits;
	}
	
}
