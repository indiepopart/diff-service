package com.waes.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Body")
public class Body {

	@Id	
	private Long id;
	
	private String body;
	
	private DiffSide side;

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public DiffSide getSide() {
		return side;
	}

	public void setSide(DiffSide side) {
		this.side = side;
	}

	
	
	public Body() {
		super();
	}

	public Body(Long id, String body, DiffSide side) {
		super();
		this.id = id;
		this.body = body;
		this.side = side;
	}

}
