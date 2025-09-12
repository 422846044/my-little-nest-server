package top.zhongyingjie.admin.security.domain;

import org.springframework.security.core.GrantedAuthority;

/**
 * 安全用户角色类
 *
 * @author Kong
 */
public class SecurityUserRole implements GrantedAuthority {

    private static final long serialVersionUID = -5978008379961711399L;

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
