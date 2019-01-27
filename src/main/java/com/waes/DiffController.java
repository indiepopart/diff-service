package com.waes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.waes.model.Body;
import com.waes.model.DiffSide;
import com.waes.service.DiffService;

@Controller
public class DiffController {
	
	@Autowired
	protected DiffService diffService;
	
	@RequestMapping(value="/v1/diff/{id}/left", method=RequestMethod.POST)
	public void left(@PathVariable Long id, @RequestBody String payload) {
		Body body = new Body(id, payload, DiffSide.LEFT);
		diffService.saveBody(body);
	}

	@RequestMapping(value="/v1/diff/{id}/right", method=RequestMethod.POST)
	public void right(@PathVariable Long id, @RequestBody String left) {
		
	}
	
	@RequestMapping(value="/v1/diff/{id}", method=RequestMethod.GET)
	public void diff(@PathVariable Long id) {
		
	}
}
