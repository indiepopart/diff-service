package com.waes.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "Body")
public class Body {

	@NotNull
	private byte[] body;
	
	@EmbeddedId
	private BodyId id;

	public Body() {
		super();
	}
	
	public Body(BodyId id, @NotNull byte[] body) {
		super();
		this.body = body;
		this.id = id;
	}

	public Body(Long id, DiffSide diffSide, byte[] payload) {
		this(new BodyId(id, diffSide), payload);
	}
	
	public BodyId getId() {
		return id;
	}

	public void setId(BodyId id) {
		this.id = id;
	}

	@Lob
	@NotNull
	@Column(name = "body", columnDefinition="BLOB")
	public byte[] getBody() {
		return body;
	}

	public void setBody(byte[] body) {
		this.body = body;
	}

}
