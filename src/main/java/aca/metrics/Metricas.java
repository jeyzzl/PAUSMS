/**
 * 
 */
package aca.metrics;

import java.util.List;

/**
 * @author Elifo
 *
 */
public class Metricas{
	private String name;
	private String description;
	private String baseUnit;
	private List<Measurements> measurements;
	private List<AvailableTags> availableTags;
	
	public Metricas(){
		name			= "-";
		description		= "-";
		baseUnit		= "0";
		measurements	= null;
		availableTags	= null;
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

	public String getBaseUnit() {
		return baseUnit;
	}

	public void setBaseUnit(String baseUnit) {
		this.baseUnit = baseUnit;
	}

	public List<Measurements> getMeasurements() {
		return measurements;
	}

	public void setMeasurements(List<Measurements> measurements) {
		this.measurements = measurements;
	}

	public List<AvailableTags> getAvailableTags() {
		return availableTags;
	}

	public void setAvailableTags(List<AvailableTags> availableTags) {
		this.availableTags = availableTags;
	}
	
}