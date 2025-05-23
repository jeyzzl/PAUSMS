/**
 * 
 */
package aca.metrics;

import java.util.List;

/**
 * @author Elifo
 *
 */
public class AvailableTags{
	private String tag;
	private List<String> values;
	
	public AvailableTags(){
		tag			= "0";
		values		= null;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public List<String> getValues() {
		return values;
	}

	public void setValues(List<String> values) {
		this.values = values;
	}
	
}