package com.waes;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.waes.model.Body;
import com.waes.model.BodyId;
import com.waes.model.BodyRepository;
import com.waes.model.DiffResultCode;
import com.waes.model.DiffSide;
import com.waes.service.DiffResult;
import com.waes.service.DiffService;
import com.waes.service.DiffServiceException;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { DiffServiceConfiguration.class })
public class DiffServiceTest {
	
	@Autowired
	protected DiffService diffService;
	
	@Autowired
	protected BodyRepository bodyRepository;
	
	@Test
	public void givenDifferentSizeBody_whenGetDiff_returnNotEqualSize() throws DiffServiceException {
		
		// Given different size body
		Body leftBody = new Body(1L, DiffSide.LEFT, "abc".getBytes());
		Body rightBody = new Body(2L, DiffSide.RIGHT, "abcd".getBytes());

		when(bodyRepository.findById(new BodyId(1L, DiffSide.LEFT))).thenReturn(Optional.of(leftBody));
		when(bodyRepository.findById(new BodyId(2L, DiffSide.RIGHT))).thenReturn(Optional.of(rightBody));			
		
		// When getDiff
		DiffResult result = diffService.getDiff(1L, 2L);
		
		// Return not equal size
		assertEquals(DiffResultCode.NOT_EQUAL_SIZE, result.getResultCode());
	}
	

	@Test
	public void givenSameBody_whenGetDiff_returnEqual() {
		// Given same body
		Body leftBody = new Body(1L, DiffSide.LEFT, "abcd".getBytes());
		Body rightBody = new Body(2L, DiffSide.RIGHT, "abcd".getBytes());
		
		when(bodyRepository.findById(new BodyId(1L, DiffSide.LEFT))).thenReturn(Optional.of(leftBody));
		when(bodyRepository.findById(new BodyId(2L, DiffSide.RIGHT))).thenReturn(Optional.of(rightBody));
		
		// When getDiff
		DiffResult result = diffService.getDiff(1L, 2L);
		
		// Return same
		assertEquals(DiffResultCode.EQUAL, result.getResultCode());
		
	}
	
	@Test
	public void givenSameSizeAndDifferentBody_whenGetDiff_returnDiffOffsets() {
		// Given same size and different body
		Body leftBody = new Body(1L, DiffSide.LEFT, "SGVsbG8gbXkgbmFtZSBpcyBqaWWWWWE=".getBytes());
		Body rightBody = new Body(2L, DiffSide.RIGHT, "SGVsbG8gbXXXXXFtZSBpcyBqaW1lbmE=".getBytes());
		
		when(bodyRepository.findById(new BodyId(1L, DiffSide.LEFT))).thenReturn(Optional.of(leftBody));
		when(bodyRepository.findById(new BodyId(2L, DiffSide.RIGHT))).thenReturn(Optional.of(rightBody));		
		
		// When getDiff
		DiffResult result = diffService.getDiff(1L, 2L);
		
		// Return Diff Offsets
		assertEquals(DiffResultCode.SAME_SIZE, result.getResultCode());
		assertFalse(result.getDetectedDiffs().isEmpty());
		assertEquals(2, result.getDetectedDiffs().size());
	}

}
