package com.placeflow.dto;

public class AuthDTO {

    // ── Register Request ──────────────────────────────
    public static class RegisterRequest {
        private String username;
        private String email;
        private String password;

        public RegisterRequest() {}

        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }

        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }

        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
    }

    // ── Login Request ─────────────────────────────────
    public static class LoginRequest {
        private String email;
        private String password;

        public LoginRequest() {}

        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }

        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
    }

    // ── Auth Response ─────────────────────────────────
    public static class AuthResponse {
        private String token;
        private Long userId;
        private String username;
        private String email;
        private String role;

        public AuthResponse() {}

        public AuthResponse(String token, Long userId, String username, String email, String role) {
            this.token = token;
            this.userId = userId;
            this.username = username;
            this.email = email;
            this.role = role;
        }

        public String getToken() { return token; }
        public void setToken(String token) { this.token = token; }

        public Long getUserId() { return userId; }
        public void setUserId(Long userId) { this.userId = userId; }

        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }

        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }

        public String getRole() { return role; }
        public void setRole(String role) { this.role = role; }
    }
}
