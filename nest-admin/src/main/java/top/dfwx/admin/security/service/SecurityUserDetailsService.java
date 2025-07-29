package top.dfwx.admin.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * 获取用户信息
 */
@Service
public class SecurityUserDetailsService implements UserDetailsService {
    @Autowired
    private SecurityUserInfoService securityUserInfoService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails userDetails = securityUserInfoService.getUserDetailsByUserName(username);
        if(Objects.isNull(userDetails)) throw new UsernameNotFoundException("用户不存在");
        return userDetails;
    }
}
