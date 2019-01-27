package com.waes.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BodyRepository extends JpaRepository<Body, Long> {
	
	@Query("SELECT b FROM Body b WHERE b.id = :id and b.side = :side")
	Body findByIdAndSide(@Param("id") Long id, @Param("side") DiffSide side);
}
