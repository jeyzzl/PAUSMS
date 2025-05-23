package  aca.financiero.spring;

public class FinGroup{
	private int groupId;
	private String name;
	private String description;
	
	public FinGroup(){
		groupId			= 0;
		name			= "";
		description		= "";	
	}

	public int getGroupId() {
		return groupId;
	}
	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
}
