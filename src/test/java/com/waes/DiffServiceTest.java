package com.waes;

import static org.assertj.core.api.Assertions.assertThat;
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
import com.waes.service.DetectedDiff;
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
		assertThat(result.getResultCode()).isEqualTo(DiffResultCode.NOT_EQUAL_SIZE);
	}
	

	@Test
	public void givenSameBody_whenGetDiff_returnEqual() throws DiffServiceException {
		// Given same body
		Body leftBody = new Body(1L, DiffSide.LEFT, "abcd".getBytes());
		Body rightBody = new Body(2L, DiffSide.RIGHT, "abcd".getBytes());
		
		when(bodyRepository.findById(new BodyId(1L, DiffSide.LEFT))).thenReturn(Optional.of(leftBody));
		when(bodyRepository.findById(new BodyId(2L, DiffSide.RIGHT))).thenReturn(Optional.of(rightBody));
		
		// When getDiff
		DiffResult result = diffService.getDiff(1L, 2L);
		
		// Return same
		assertThat(result.getResultCode()).isEqualTo(DiffResultCode.EQUAL);
		
	}
	
	@Test
	public void givenSameSizeAndDifferentBody_whenGetDiff_returnDiffOffsets() throws DiffServiceException {
		// Given same size and different body
		byte [] lBytes = new byte[] { 1, 1, 1, 1, 4, 4, 1, 1, 1, 1, 1};
		byte [] rBytes = new byte[] { 1, 2, 2, 1, 1, 1, 1, 1, 1, 1, 1}; 
		Body leftBody = new Body(1L, DiffSide.LEFT, lBytes);
		Body rightBody = new Body(2L, DiffSide.RIGHT, rBytes);
		
		when(bodyRepository.findById(new BodyId(1L, DiffSide.LEFT))).thenReturn(Optional.of(leftBody));
		when(bodyRepository.findById(new BodyId(2L, DiffSide.RIGHT))).thenReturn(Optional.of(rightBody));		
		
		// When getDiff
		DiffResult result = diffService.getDiff(1L, 2L);
		
		// Return Diff Offsets
		assertThat(result.getResultCode()).isEqualTo(DiffResultCode.SAME_SIZE);
		assertThat(result.getDetectedDiffs()).hasSize(2);

	}

	@Test
	public void givenSameSizeAndAllDifferentBody_whenGetDiff_returnDiffOffsets() throws DiffServiceException {
		// Given same size and different body
		Body leftBody = new Body(1L, DiffSide.LEFT, "SmVsb".getBytes());
		Body rightBody = new Body(2L, DiffSide.RIGHT, "Nzc3N".getBytes());
		
		when(bodyRepository.findById(new BodyId(1L, DiffSide.LEFT))).thenReturn(Optional.of(leftBody));
		when(bodyRepository.findById(new BodyId(2L, DiffSide.RIGHT))).thenReturn(Optional.of(rightBody));		
		
		// When getDiff
		DiffResult result = diffService.getDiff(1L, 2L);
		
		// Return Diff Offsets
		assertThat(result.getResultCode()).isEqualTo(DiffResultCode.SAME_SIZE);
		assertThat(result.getDetectedDiffs()).hasSize(1);
		
		DetectedDiff detectedDiff = result.getDetectedDiffs().get(0);		
		assertThat(detectedDiff.getLength()).isEqualTo(leftBody.getBody().length);
	}
	
	@Test
	public void givenSameSizeAndDifferentHeadBody_whenGetDiff_returnDiffOffsets() throws DiffServiceException {
		// Given same size and different body
		byte [] lBytes = new byte[] { 2, 2, 2, 1, 1, 1, 1, 1, 1, 1, 1}; 
		byte [] rBytes = new byte[] { 3, 3, 3, 1, 1, 1, 1, 1, 1, 1, 1}; 
		Body leftBody = new Body(1L, DiffSide.LEFT, lBytes);
		Body rightBody = new Body(2L, DiffSide.RIGHT, rBytes);
		
		when(bodyRepository.findById(new BodyId(1L, DiffSide.LEFT))).thenReturn(Optional.of(leftBody));
		when(bodyRepository.findById(new BodyId(2L, DiffSide.RIGHT))).thenReturn(Optional.of(rightBody));		
		
		// When getDiff
		DiffResult result = diffService.getDiff(1L, 2L);
		
		// Return Diff Offsets
		assertThat(result.getResultCode()).isEqualTo(DiffResultCode.SAME_SIZE);
		assertThat(result.getDetectedDiffs()).hasSize(1);
		
		DetectedDiff detectedDiff = result.getDetectedDiffs().get(0);		
		assertThat(detectedDiff.getOffset()).isEqualTo(0);
		assertThat(detectedDiff.getLength()).isEqualTo(3);
	}	
	
	@Test
	public void givenSameSizeAndDifferentTailBody_whenGetDiff_returnDiffOffsets() throws DiffServiceException {
		// Given same size and different body
		byte [] lBytes = new byte[] { 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2}; 
		byte [] rBytes = new byte[] { 1, 1, 1, 1, 1, 1, 1, 1, 3, 3, 3}; 
				
		Body leftBody = new Body(1L, DiffSide.LEFT, lBytes);
		Body rightBody = new Body(2L, DiffSide.RIGHT, rBytes);
		
		when(bodyRepository.findById(new BodyId(1L, DiffSide.LEFT))).thenReturn(Optional.of(leftBody));
		when(bodyRepository.findById(new BodyId(2L, DiffSide.RIGHT))).thenReturn(Optional.of(rightBody));		
		
		// When getDiff
		DiffResult result = diffService.getDiff(1L, 2L);
		
		// Return Diff Offsets
		assertThat(result.getResultCode()).isEqualTo(DiffResultCode.SAME_SIZE);
		assertThat(result.getDetectedDiffs()).hasSize(1);
		
		DetectedDiff detectedDiff = result.getDetectedDiffs().get(0);		
		assertThat(detectedDiff.getOffset()).isEqualTo(leftBody.getBody().length - 3);
		assertThat(detectedDiff.getLength()).isEqualTo(3);
	}	
	
	@Test
	public void givenSameSizeAndDifferentHeadByte_whenGetDiff_returnDiffOffsets() throws DiffServiceException {
		// Given same size and different body
		Body leftBody = new Body(1L, DiffSide.LEFT, new byte[] {1, 4, 4, 4});
		Body rightBody = new Body(2L, DiffSide.RIGHT, new byte[] {2, 4, 4, 4});
		
		when(bodyRepository.findById(new BodyId(1L, DiffSide.LEFT))).thenReturn(Optional.of(leftBody));
		when(bodyRepository.findById(new BodyId(2L, DiffSide.RIGHT))).thenReturn(Optional.of(rightBody));		
		
		// When getDiff
		DiffResult result = diffService.getDiff(1L, 2L);
		
		// Return Diff Offsets
		assertThat(result.getResultCode()).isEqualTo(DiffResultCode.SAME_SIZE);
		assertThat(result.getDetectedDiffs()).hasSize(1);
		
		DetectedDiff detectedDiff = result.getDetectedDiffs().get(0);		
		assertThat(detectedDiff.getOffset()).isEqualTo(0);
		assertThat(detectedDiff.getLength()).isEqualTo(1);
	}
		
	@Test
	public void givenSameSizeAndDifferentTailByte_whenGetDiff_returnDiffOffsets() throws DiffServiceException {
		// Given same size and different body
		Body leftBody = new Body(1L, DiffSide.LEFT, new byte[] {1, 4, 4, 4});
		Body rightBody = new Body(2L, DiffSide.RIGHT, new byte[] {1, 4, 4, 3});
		
		when(bodyRepository.findById(new BodyId(1L, DiffSide.LEFT))).thenReturn(Optional.of(leftBody));
		when(bodyRepository.findById(new BodyId(2L, DiffSide.RIGHT))).thenReturn(Optional.of(rightBody));		
		
		// When getDiff
		DiffResult result = diffService.getDiff(1L, 2L);
		
		// Return Diff Offsets
		assertThat(result.getResultCode()).isEqualTo(DiffResultCode.SAME_SIZE);
		assertThat(result.getDetectedDiffs()).hasSize(1);
		
		DetectedDiff detectedDiff = result.getDetectedDiffs().get(0);		
		assertThat(detectedDiff.getOffset()).isEqualTo(leftBody.getBody().length - 1);
		assertThat(detectedDiff.getLength()).isEqualTo(1);
	}
	
}
