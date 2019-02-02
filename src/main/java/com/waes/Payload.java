package com.waes;

public class Payload {

	protected String base64;

	public Payload(String base64) {
		this.base64 = base64;
	}
	
	public Payload() {
	}

	public String getBase64() {
		return base64;
	}

	public void setBase64(String base64) {
		this.base64 = base64;
	}
	
	
}
