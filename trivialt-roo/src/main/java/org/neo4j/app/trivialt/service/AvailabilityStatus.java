package org.neo4j.app.trivialt.service;

import flexjson.JSONSerializer;

public class AvailabilityStatus {

	private String key = "";
	private boolean available = false;

	public AvailabilityStatus(boolean b) {
		setAvailable(b);
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

	public static AvailabilityStatus available(String key) {
		AvailabilityStatus availableTrue = new AvailabilityStatus(true);
		availableTrue.setKey(key);
		return availableTrue;
	}

	public static AvailabilityStatus notAvailable(String key) {
		AvailabilityStatus availableFalse = new AvailabilityStatus(false);
		availableFalse.setKey(key);
		return availableFalse;
	}

	public String toJson() {
        return new JSONSerializer().exclude("*.class").serialize(this);
	}
	
}
