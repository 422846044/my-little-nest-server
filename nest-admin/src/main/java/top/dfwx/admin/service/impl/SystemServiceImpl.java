package top.dfwx.admin.service.impl;

import top.dfwx.admin.mapper.SysConfInfoMapper;
import top.dfwx.admin.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author atulan_zyj
 * @date 2025/2/17
 */
@Service
public class SystemServiceImpl implements SystemService {

    @Autowired
    private SysConfInfoMapper sysConfInfoMapper;

    @Override
    public String getValue(String key) {
        return sysConfInfoMapper.selectValueByKey(key);
    }

}
