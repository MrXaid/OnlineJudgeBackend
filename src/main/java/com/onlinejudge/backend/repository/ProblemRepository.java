package com.onlinejudge.backend.repository;

import com.onlinejudge.backend.model.Problem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProblemRepository extends JpaRepository<Problem, Long> {
    boolean existsByName(String name);
}
