package org.example.myproject.repository;

import org.example.myproject.model.FootballFields;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FootballFieldsRepository extends JpaRepository<FootballFields, Long> {
}
