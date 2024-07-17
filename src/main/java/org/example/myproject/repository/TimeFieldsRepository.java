package org.example.myproject.repository;

import org.example.myproject.model.TimeFields;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TimeFieldsRepository extends JpaRepository<TimeFields, Long> {
}
