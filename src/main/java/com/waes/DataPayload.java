package com.waes;

public class DataPayload {

	protected String base64;

	public DataPayload(String base64) {
		this.base64 = base64;
	}
	
	public DataPayload() {
	}

	public String getBase64() {
		return base64;
	}

	public void setBase64(String base64) {
		this.base64 = base64;
	}
	
	
}
