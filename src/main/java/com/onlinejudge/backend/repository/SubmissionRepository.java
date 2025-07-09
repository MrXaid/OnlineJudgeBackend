package com.onlinejudge.backend.repository;

import com.onlinejudge.backend.model.Submission;
import com.onlinejudge.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubmissionRepository extends JpaRepository<Submission, Long> {
    List<Submission> findByUser(User user);
}
