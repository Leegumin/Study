package com.jpamember.springboot.controller;


import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.jpamember.springboot.entity.Member;

public class MemberSpecification {
	
	@SuppressWarnings("serial")
	public static Specification<Member> withName(String name) {
        return new Specification<Member>() {
        	
			@Override
            public Predicate toPredicate(
            								Root<Member> root, 
            								CriteriaQuery<?> query, 
            								CriteriaBuilder criteriaBuilder		
            							) {
                // [1] : equal
                return criteriaBuilder.equal(root.get("name"), name);
            }
        };
    }

	@SuppressWarnings("serial")
	public static Specification<Member> withId(String id) {
        return new Specification<Member>() {
        	
			@Override
            public Predicate toPredicate(
            								Root<Member> root, 
            								CriteriaQuery<?> query, 
            								CriteriaBuilder criteriaBuilder		
            							) {
                // [1] : equal
                return criteriaBuilder.equal(root.get("id"), id);
            }
        };
    }

}
