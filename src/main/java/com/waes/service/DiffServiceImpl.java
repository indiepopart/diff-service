package com.waes.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.waes.model.Body;
import com.waes.model.BodyId;
import com.waes.model.BodyRepository;
import com.waes.model.DiffResultCode;
import com.waes.model.DiffSide;

@Service
public class DiffServiceImpl implements DiffService {
	
	protected Logger logger = LoggerFactory.getLogger(DiffServiceImpl.class);

	@Autowired
	protected BodyRepository bodyRepository;
	
	@Override
	public DiffResult getDiff(Long leftId, Long rightId){
		Optional<Body> left = bodyRepository.findById(new BodyId(leftId, DiffSide.LEFT));
		if (!left.isPresent()) {
			throw new DiffServiceException("Left not found");
		}
		Optional<Body> right = bodyRepository.findById(new BodyId (rightId, DiffSide.RIGHT));
		if (!right.isPresent()) {
			throw new DiffServiceException("Right not found");
		}
		
		byte[] leftBody = left.get().getBody();
		byte[] rightBody = right.get().getBody();
		if (leftBody.length !=  rightBody.length) {
			return new DiffResult(DiffResultCode.NOT_EQUAL_SIZE);
		}
		
		// Compare
		List<DetectedDiff> detectedDiffs = compare(leftBody, rightBody);
		
		if (detectedDiffs.isEmpty()) {
			return new DiffResult(DiffResultCode.EQUAL);
		} else {
			return new DiffResult(DiffResultCode.SAME_SIZE, detectedDiffs);
		}
	}
	
	protected List<DetectedDiff> compare(byte[] leftBody, byte[] rightBody){
		List<DetectedDiff> detectedDiffs = new ArrayList<>();
		int i = 0;
		while (i < leftBody.length) {
			logger.trace("left " + leftBody[i] + ", right " + rightBody[i]);
			if (leftBody[i] != rightBody[i]) {
				logger.trace("Diff offset at " + i);
				int o = i++;
				while ( i < leftBody.length && leftBody[i] != rightBody[i]) {
					logger.trace("Still different at " + i++);
				}
				int length = i - o;
				DetectedDiff detectedDiff = new DetectedDiff(Long.valueOf(o), Long.valueOf(length));
				logger.debug("Add detected diff " + o + " with length " + length);
				detectedDiffs.add(detectedDiff);
			} else {
				i++;
			}
		}
		return detectedDiffs;
	}

	@Override
	public void saveBody(Body body) {
		bodyRepository.save(body);
	}
}
