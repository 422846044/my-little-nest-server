package fun.dfwh.admin.service;

import fun.dfwh.admin.entity.UserInfo;

public interface UserService {
    UserInfo getUserInfoByUserName(String userName);
}
