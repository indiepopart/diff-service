package com.waes;

import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.waes.model.Body;
import com.waes.model.DiffSide;
import com.waes.service.DiffResult;
import com.waes.service.DiffService;

@Controller
public class DiffController {
	
	@Autowired
	protected DiffService diffService;
	
	@RequestMapping(value="/v1/diff/{id}/left", method=RequestMethod.POST)
	@ResponseBody
	public void left(@PathVariable Long id, @RequestBody Payload payload) {
		byte[] decodedBytes = Base64.getDecoder().decode(payload.getBase64());
		Body body = new Body(id, DiffSide.LEFT, decodedBytes);
		diffService.saveBody(body);
	}

	@RequestMapping(value="/v1/diff/{id}/right", method=RequestMethod.POST)
	@ResponseBody
	public void right(@PathVariable Long id, @RequestBody Payload payload) {
		byte[] decodedBytes = Base64.getDecoder().decode(payload.getBase64());
		Body body = new Body(id, DiffSide.RIGHT, decodedBytes);
		diffService.saveBody(body);		
	}
	
	@RequestMapping(value="/v1/diff/{id}", method=RequestMethod.GET)
	@ResponseBody
	public DiffResult diff(@PathVariable Long id) { 
		return diffService.getDiff(id, id);
	}
}
