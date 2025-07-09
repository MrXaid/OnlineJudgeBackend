package com.onlinejudge.backend.service;

import com.onlinejudge.backend.model.Problem;
import com.onlinejudge.backend.model.Submission;
import com.onlinejudge.backend.model.User;
import com.onlinejudge.backend.payload.request.SubmissionRequest;
import com.onlinejudge.backend.repository.ProblemRepository;
import com.onlinejudge.backend.repository.SubmissionRepository;
import com.onlinejudge.backend.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class SubmissionService {

    private final SubmissionRepository submissionRepository;
    private final ProblemRepository problemRepository;

    public Submission saveInitialSubmission(User user, SubmissionRequest req) {
        Problem problem = problemRepository.findById(req.getProblemId())
                .orElseThrow(() -> new ResourceNotFoundException("Problem", "id", req.getProblemId()));

        Submission submission = Submission.builder()
                .problemName(problem.getName())
                .code(req.getCode())
                .language(req.getLanguage())
                .verdict("Pending")
                .user(user)
                .problem(problem)
                .date(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()))
                .build();

        return submissionRepository.save(submission);
    }

    public void updateVerdict(Submission submission, String verdict) {
        submission.setVerdict(verdict);
        submissionRepository.save(submission);
    }
}
