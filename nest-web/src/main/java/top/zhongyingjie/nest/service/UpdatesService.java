package top.zhongyingjie.nest.service;

import top.zhongyingjie.nest.vo.UpdatesVO;

import java.util.List;

public interface UpdatesService {

    List<UpdatesVO> getNewUpdates();
}
