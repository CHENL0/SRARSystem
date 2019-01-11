package com.example.srarsystem.commons;

import com.example.srarsystem.entity.ProfessorInfo;
import com.example.srarsystem.entity.ProjectInfo;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.Entity;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Chen
 * @createTime 20181117 12:39
 * @description the tool of page
 */
@Component
public class PageToolUtils {
    /**
     * @Description //TODO the tool of page what can classify by pjStatus and pjType
     * @Author Chen
     * @DateTime 2018/11/17
     * @Param
     * @Return
     */
    public Specification<ProjectInfo> pjSpecification(String productType) {
        Specification<ProjectInfo> specification = new Specification<ProjectInfo>() {
            @Override
            public Predicate toPredicate(Root<ProjectInfo> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<Predicate>();
                Path<Long> pjStatus = root.get("pjStatus");
                Predicate predicate = criteriaBuilder.equal(pjStatus, 1);
                predicates.add(predicate);
                Path<Long> pjType = root.get("pjType");
                Predicate predicate1 = criteriaBuilder.equal(pjType, pjType);
                predicates.add(predicate1);
                return criteriaBuilder.and(predicates
                        .toArray(new Predicate[]{}));
            }
        };
        return specification;
    }
    /**
     * @Description //TODO the tool of page what can classify by pjStatus and pjType
     * @Author Chen
     * @DateTime 2018/11/17
     * @Param
     * @Return
     */
    public Specification<ProfessorInfo> pfSpecification(String pfType) {
        Specification<ProfessorInfo> specification = new Specification<ProfessorInfo>() {
            @Override
            public Predicate toPredicate(Root<ProfessorInfo> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<Predicate>();
                Path<Long> pfTypePath = root.get("pfType");
                Predicate predicate = criteriaBuilder.equal(pfTypePath, pfType);
                predicates.add(predicate);
                return criteriaBuilder.and(predicates
                        .toArray(new Predicate[]{}));
            }
        };
        return specification;
    }
}
