package com.onlinejudge.backend.model;

import jakarta.persistence.*;
import lombok.*;

import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Table(name = "submissions")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Submission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String problemName;
    @Lob
    private String code;

    private String language;

    private String verdict;

    private String date;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "problem_id")
    private Problem problem;

    public Submission(String problemName, String code, String language,
                      String verdict, User user, Problem problem) {
        this.problemName = problemName;
        this.code = code;
        this.language = language;
        this.verdict = verdict;
        this.user = user;
        this.problem = problem;
        this.date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }
}

