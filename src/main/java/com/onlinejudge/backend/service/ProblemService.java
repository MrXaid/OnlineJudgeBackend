package com.onlinejudge.backend.service;

import com.onlinejudge.backend.model.Problem;
import com.onlinejudge.backend.payload.ProblemAdminResponseDTO;
import com.onlinejudge.backend.payload.ProblemResponseDTO;
import com.onlinejudge.backend.repository.ProblemRepository;
import com.onlinejudge.backend.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProblemService {

    private final ProblemRepository problemRepository;

    public Problem createProblem(Problem problem) {
        if (problemRepository.existsByName(problem.getName())) {
            throw new IllegalArgumentException("Problem already exists with the same name");
        }
        return problemRepository.save(problem);
    }

    public List<Problem> getAllProblems() {
        return problemRepository.findAll();
    }

    public Problem getProblemById(Long id) {
        return problemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Problem", "id", id));
    }

    public void deleteProblem(Long id) {
        Problem problem = getProblemById(id);
        problemRepository.delete(problem); // Test cases deleted via @ElementCollection
    }
    public ProblemResponseDTO mapToUserDTO(Problem problem) {
        return ProblemResponseDTO.builder()
                .id(problem.getId())
                .name(problem.getName())
                .author(problem.getAuthor())
                .statement(problem.getStatement())
                .explanation(problem.getExplanation())
                .tags(problem.getTags())
                .countAC(problem.getCountAC())
                .countTotal(problem.getCountTotal())
                .sampleTestcases(problem.getSampleTestcases())
                .time(problem.getTime())
                .memory(problem.getMemory())
                .build();
    }
    public ProblemAdminResponseDTO mapToAdminDTO(Problem problem) {
        return ProblemAdminResponseDTO.builder()
                .id(problem.getId())
                .name(problem.getName())
                .author(problem.getAuthor())
                .statement(problem.getStatement())
                .explanation(problem.getExplanation())
                .tags(problem.getTags())
                .countAC(problem.getCountAC())
                .countTotal(problem.getCountTotal())
                .sampleTestcases(problem.getSampleTestcases())
                .systemTestcases(problem.getSystemTestcases())
                .time(problem.getTime())
                .memory(problem.getMemory())
                .build();
    }

}
