package com.waes.service;

import java.util.ArrayList;
import java.util.List;

import com.waes.model.DiffResultCode;

public class DiffResult {

	protected DiffResultCode resultCode;
	protected List<DetectedDiff> detectedDiffs;
	
	public DiffResult(DiffResultCode resultCode) {
		this.resultCode = resultCode;
		detectedDiffs = new ArrayList<DetectedDiff>();
	}	
	
	public DiffResult(DiffResultCode resultCode, List<DetectedDiff> detectedDiffs) {
		this.resultCode = resultCode;
		this.detectedDiffs = detectedDiffs;
	}
	
	public DiffResultCode getResultCode() {
		return resultCode;
	}
	public List<DetectedDiff> getDetectedDiffs() {
		return detectedDiffs;
	}
	
	
}
