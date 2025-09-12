package top.zhongyingjie.nest.service;

import top.zhongyingjie.nest.vo.UpdatesVO;

import java.util.List;

/**
 * 动态信息服务接口
 *
 * @author Kong
 */
public interface UpdatesService {

    /**
     * 获取最新动态
     *
     * @return 动态试图对象列表
     */
    List<UpdatesVO> getNewUpdates();
}
