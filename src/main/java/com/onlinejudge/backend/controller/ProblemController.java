package com.onlinejudge.backend.controller;

import com.onlinejudge.backend.model.Problem;
import com.onlinejudge.backend.model.Role;
import com.onlinejudge.backend.model.User;
import com.onlinejudge.backend.payload.APIResponse;
import com.onlinejudge.backend.payload.ProblemAdminResponseDTO;
import com.onlinejudge.backend.payload.ProblemResponseDTO;
import com.onlinejudge.backend.service.ProblemService;
import com.onlinejudge.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/problems")
@RequiredArgsConstructor
public class ProblemController {

    private final ProblemService problemService;
    private final UserService userService;

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Problem> createProblem(@RequestBody Problem problem) {
        Problem created = problemService.createProblem(problem);
        return ResponseEntity.ok(created);
    }
    @GetMapping("/create")
    public ResponseEntity<APIResponse> handleInvalidGetCreate() {
        return new ResponseEntity<>(
                new APIResponse("Request method 'GET' is not supported for /api/problems/create", false),
                HttpStatus.METHOD_NOT_ALLOWED
        );
    }

    @GetMapping
    public ResponseEntity<List<ProblemResponseDTO>> getAllProblems() {
        List<Problem> problems = problemService.getAllProblems();
        List<ProblemResponseDTO> dtoList = problems.stream()
                .map(problemService::mapToUserDTO)
                .toList();
        return ResponseEntity.ok(dtoList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProblemById(@PathVariable Long id, Principal principal) {
        Problem problem = problemService.getProblemById(id);
        User user = userService.getUserByUsername(principal.getName());

        if (user.getRole() == Role.ADMIN) {
            ProblemAdminResponseDTO adminDTO = problemService.mapToAdminDTO(problem);
            return ResponseEntity.ok(adminDTO);
        }

        ProblemResponseDTO userDTO = problemService.mapToUserDTO(problem);
        return ResponseEntity.ok(userDTO);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteProblem(@PathVariable Long id) {
        problemService.deleteProblem(id);
        return ResponseEntity.ok("Problem deleted successfully");
    }
}
