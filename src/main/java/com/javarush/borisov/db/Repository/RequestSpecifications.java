package com.javarush.borisov.db.Repository;

import com.javarush.borisov.entity.Request;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class RequestSpecifications {

    public static Specification<Request> addressContainsAll(List<String> keywords) {
        return (root, query, cb) -> {
            if (keywords == null || keywords.isEmpty()) {
                return cb.conjunction(); // без фильтра
            }

            Predicate[] predicates = keywords.stream()
                    .map(kw -> cb.like(cb.lower(root.get("address")), "%" + kw.toLowerCase() + "%"))
                    .toArray(Predicate[]::new);

            return cb.and(predicates);
        };
    }
}