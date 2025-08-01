package top.dfwx.admin.security.service;

import top.dfwx.admin.entity.UserInfo;
import top.dfwx.admin.entity.UserRoleInfo;
import top.dfwx.admin.security.domain.SecurityUserDetails;
import top.dfwx.admin.security.domain.SecurityUserRole;
import top.dfwx.admin.service.UserRoleService;
import top.dfwx.admin.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class SecurityUserInfoService {

    @Autowired
    private IUserService iUserService;

    @Autowired
    private UserRoleService userRoleService;

    public UserDetails getUserDetailsByUserName(String username) {
        SecurityUserDetails userDetails = null;
        UserInfo userInfo = iUserService.getUserInfoByUserName(username);
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
