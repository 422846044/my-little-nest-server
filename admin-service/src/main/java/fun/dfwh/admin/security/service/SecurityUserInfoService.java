package fun.dfwh.admin.security.service;

import fun.dfwh.admin.entity.UserInfo;
import fun.dfwh.admin.entity.UserRoleInfo;
import fun.dfwh.admin.security.domain.SecurityUserDetails;
import fun.dfwh.admin.security.domain.SecurityUserRole;
import fun.dfwh.admin.service.UserRoleService;
import fun.dfwh.admin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class SecurityUserInfoService {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRoleService userRoleService;

    public UserDetails getUserDetailsByUserName(String username) {
        SecurityUserDetails userDetails = null;
        UserInfo userInfo = userService.getUserInfoByUserName(username);
        if(Objects.nonNull(userInfo)){
            userDetails = new SecurityUserDetails();
            userDetails.setUserId(Long.toString(userInfo.getId()));
            userDetails.setUserName(userInfo.getUsername());
            userDetails.setPassWord(userInfo.getPassword());
            userDetails.setEnable(!"2".equals(userInfo.getStatus()));
            List<UserRoleInfo> userRoleList = userRoleService.getUserRoleByUserId(userInfo.getId());
            List<SecurityUserRole> securityUserRoleList = new ArrayList<>();
            for (UserRoleInfo userRoleInfo : userRoleList) {
                securityUserRoleList.add(new SecurityUserRole(Long.toString(userRoleInfo.getRoleId())));
            }
            userDetails.setAuthorities(securityUserRoleList);
        }
        return userDetails;
    }
}
