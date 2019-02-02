package com.waes.service;

public class DetectedDiff {
	
	protected Long offset;
	protected Long length;
	
	public DetectedDiff(Long offset, Long length) {
		super();
		this.offset = offset;
		this.length = length;
	}

	public Long getOffset() {
		return offset;
	}

	public Long getLength() {
		return length;
	}
}
