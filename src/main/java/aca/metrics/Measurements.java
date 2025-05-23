/**
 * 
 */
package aca.metrics;
/**
 * @author Elifo
 *
 */
public class Measurements{
	String statistic;
	String value;
	
	public Measurements(){
		statistic		= "0";
		value			= "0";
	}

	public String getStatistic() {
		return statistic;
	}

	public void setStatistic(String statistic) {
		this.statistic = statistic;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
}