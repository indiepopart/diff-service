package com.waes;

import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ResponseStatusException;

import com.waes.model.Body;
import com.waes.model.DiffSide;
import com.waes.service.DiffResult;
import com.waes.service.DiffService;
import com.waes.service.DiffServiceException;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Controller
@RequestMapping(value="/v1/diff")
public class DiffController {
	
	@Autowired
	protected DiffService diffService;
	
	@ApiOperation(value = "Save a binary data body for a left side of comparison")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = ""),
			@ApiResponse(code = 422, message = "Invalid/Empty base64 body"),
			@ApiResponse(code = 400, message = "Bad Request")})
	@RequestMapping(value="/{id}/left", method=RequestMethod.POST)
	@ResponseBody
	public void left(@PathVariable Long id, @RequestBody DataPayload payload) {
		saveBody(id, payload, DiffSide.LEFT);
	}

	@ApiOperation(value = "Save a binary data body for a right side of comparison")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = ""),
			@ApiResponse(code = 422, message = "Invalid/Empty base64 body"),
			@ApiResponse(code = 400, message = "Bad Request")})
	@RequestMapping(value="/{id}/right", method=RequestMethod.POST)
	@ResponseBody
	public void right(@PathVariable Long id, @RequestBody DataPayload payload) {
		saveBody(id, payload, DiffSide.RIGHT);
	}
	
	protected void saveBody(Long id, DataPayload payload, DiffSide diffSide) {
		if (StringUtils.isEmpty(payload.getBase64())) {
			throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Empty base64 body");
		}

		try {
			byte[] decodedBytes = Base64.getDecoder().decode(payload.getBase64());
			Body body = new Body(id, diffSide, decodedBytes);
			diffService.saveBody(body);				
		} catch (IllegalArgumentException e) {
			throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Invalid base64 body", e);
		}
	}
	
	@ApiOperation(value = "Compare two binary data bodies previously saved")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = ""),
			@ApiResponse(code = 400, message = "Left/Right not found")})	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	@ResponseBody
	public DiffResult diff(@PathVariable Long id) {
		try {
			return diffService.getDiff(id, id);
		} catch (DiffServiceException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
		}
	}
}
