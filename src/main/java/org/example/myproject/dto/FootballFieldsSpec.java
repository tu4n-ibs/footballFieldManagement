package org.example.myproject.dto;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.example.myproject.model.FootballFields;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class FootballFieldsSpec implements Specification<FootballFields> {

    private FootballFieldsDTO footballFieldsDTO;

    public FootballFieldsSpec(FootballFieldsDTO footballFieldsDTO) {
        this.footballFieldsDTO = footballFieldsDTO;
    }

    @Override
    public Predicate toPredicate(Root<FootballFields> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        if (footballFieldsDTO.getName() != null) {
            predicates.add(criteriaBuilder.like(root.get("name"), "%" + footballFieldsDTO.getName() + "%"));
        }
        if (footballFieldsDTO.getLocation() != null) {
            predicates.add(criteriaBuilder.like(root.get("location"), "%" + footballFieldsDTO.getLocation() + "%"));
        }
        if (footballFieldsDTO.getTypeFields() != null) {
            predicates.add(criteriaBuilder.like(root.get("typeFields"), "%" + footballFieldsDTO.getTypeFields() + "%"));
        }
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
