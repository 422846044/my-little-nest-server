package top.dfwx.nest.service;

import top.dfwx.nest.vo.UpdatesVO;

import java.util.List;

public interface UpdatesService {

    List<UpdatesVO> getNewUpdates();
}
