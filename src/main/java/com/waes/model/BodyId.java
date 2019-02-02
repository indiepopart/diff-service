package com.waes.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Embeddable;

@Embeddable
public class BodyId implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private DiffSide side;
	
	public BodyId() {
		
	}
	
	public BodyId(Long id, DiffSide side) {
		super();
		this.id = id;
		this.side = side;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public DiffSide getSide() {
		return side;
	}
	public void setSide(DiffSide side) {
		this.side = side;
	}
	@Override
	public int hashCode() {
		return Objects.hash(id, side);
	}
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
        if (!(o instanceof BodyId)) return false;
        BodyId that = (BodyId) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(side, that.side);
	}

}
