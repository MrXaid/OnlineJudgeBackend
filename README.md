# ⚖️ Online Judge System

A full-fledged, production-grade **Online Code Judge** built with **Spring Boot**, **PostgreSQL**, **Docker**, and **React.js** — inspired by platforms like Codeforces, LeetCode, and AtCoder.

> 🚀 Judge, Evaluate, and Manage Competitive Programming Submissions in Real Time!

---

## 🌟 Features

- 👤 **User Authentication** using JWT + Role-based access (Admin / User)
- 🧠 **Problem Management**: Add problems with sample and system test cases
- 🖥️ **Code Submission Engine**:
  - Supports `C++`, `Java`, and `Python3`
  - Executes securely inside Docker containers
  - Evaluates against both sample & system test cases
- 📊 **Verdict System**: Handles verdicts like `Accepted`, `Wrong Answer`, `TLE`, etc.
- 🔐 **Secure Code Execution** using Linux and Docker (WSL2 for Windows)
- 📁 Submission persistence using PostgreSQL

---

## 🧱 Tech Stack

| Layer            | Technology                  |
|------------------|-----------------------------|
| Backend          | Spring Boot, Spring Security |
| Frontend         | React.js + Axios            |
| Authentication   | JWT (Cookies for session)   |
| Code Execution   | Docker + Bash (`judge.sh`)  |
| Database         | PostgreSQL                  |
| File System      | WSL2 Linux (for safe exec)  |

---

## 🧩 Architecture Overview

```mermaid
graph TD
    User[👤 User] -->|Login/Submit| ReactFrontend
    ReactFrontend -->|REST API| SpringBootApp
    SpringBootApp -->|Executes| judge.sh
    judge.sh -->|Compiles & Runs| DockerContainer
    DockerContainer -->|Result| SpringBootApp
    SpringBootApp -->|Stores| PostgreSQL
    SpringBootApp -->|Verdict| ReactFrontend
