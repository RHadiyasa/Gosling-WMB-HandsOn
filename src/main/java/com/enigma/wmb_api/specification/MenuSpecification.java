package com.enigma.wmb_api.specification;

import com.enigma.wmb_api.constant.MenuCategory;
import com.enigma.wmb_api.dto.request.SearchMenuRequest;
import com.enigma.wmb_api.entity.Menu;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class MenuSpecification {
    public static Specification<Menu> getSpecification(SearchMenuRequest searchMenuRequest) {
        return new Specification<Menu>() {
          public Predicate toPredicate(Root<Menu> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
              List<Predicate> predicates = new ArrayList<>();
                if (searchMenuRequest.getName() != null) {
//                    predicates.add(cb.equal(root.get("name"), searchMenuRequest.getName()));
                    predicates.add(
                            cb.like(cb.lower(root.get("name")),
                                    "%" + searchMenuRequest.getName().toLowerCase() + "%")); // ignoreCase
                }

                  if (searchMenuRequest.getIsReady()) {
                      predicates.add(
                              cb.equal(root.get("isAvailable"),
                                      true));
                      predicates.add(
                              cb.greaterThan(root.get("stock"), 0));
                  }

                  if (searchMenuRequest.getCategory() != null) {
    //                    predicates.add(cb.equal(root.get("name"), searchMenuRequest.getName()));
                      predicates.add(
                              cb.equal(root.get("category"),
                                      MenuCategory.fromValue(searchMenuRequest.getCategory()))); // ignoreCase
                  }
                  Long minPrice = searchMenuRequest.getMinPrice();
                  Long maxPrice = searchMenuRequest.getMaxPrice();
                  if (minPrice != null && maxPrice != null) {
                      Predicate minMaxPredicate = cb.between(root.get("price"), minPrice, maxPrice);
                      predicates.add(minMaxPredicate);
                  } else if (minPrice != null) {
                      Predicate minPredicate = cb.greaterThanOrEqualTo(root.get("price"), minPrice);
                      predicates.add(minPredicate);
                  } else if (maxPrice != null) {
                      Predicate maxPredicate = cb.lessThanOrEqualTo(root.get("price"), maxPrice);
                      predicates.add(maxPredicate);
                  }


              if (predicates.isEmpty()) return cb.conjunction();

                  // root criteria builder
                  return cb.and(predicates.toArray(new Predicate[predicates.size()]));
          }
        };
    }
}
