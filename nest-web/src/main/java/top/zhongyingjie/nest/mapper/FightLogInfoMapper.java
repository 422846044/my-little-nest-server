package top.zhongyingjie.nest.mapper;

import top.zhongyingjie.nest.domain.FightLogInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author 11th
* @description 针对表【log_info】的数据库操作Mapper
* @createDate 2024-01-30 21:09:48
* @Entity com.dfwh.djctask.domain.LogInfo
*/
public interface FightLogInfoMapper {

    int deleteByPrimaryKey(Long id);

    int insert(FightLogInfo record);

    int insertSelective(FightLogInfo record);

    FightLogInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(FightLogInfo record);

    int updateByPrimaryKey(FightLogInfo record);

    List<FightLogInfo> selectByQQNumAndCreateTime(@Param("qqNum") Integer qqNum,
                                                  @Param("sDate") String sDate,
                                                  @Param("eDate") String eDate);
}
