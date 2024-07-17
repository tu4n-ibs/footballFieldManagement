package org.example.myproject.repository;

import org.example.myproject.model.FootballFields;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FootballFieldsRepository extends JpaRepository<FootballFields, Long> , JpaSpecificationExecutor<FootballFields> {
    List<FootballFields> findAllByUserId(Long userId);
}
