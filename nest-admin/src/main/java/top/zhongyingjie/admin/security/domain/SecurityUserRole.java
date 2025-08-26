package top.zhongyingjie.admin.security.domain;

import org.springframework.security.core.GrantedAuthority;

public class SecurityUserRole implements GrantedAuthority {
    private String role;

    public SecurityUserRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String getAuthority() {
        return this.role;
    }
}
