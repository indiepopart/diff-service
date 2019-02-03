package com.waes.service;

import io.swagger.annotations.ApiModelProperty;

public class DetectedDiff {
	
	@ApiModelProperty(notes = "The BYTE offset of the difference")
	protected Long offset;
	
	@ApiModelProperty(notes = "The length in BYTES of the difference")
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
