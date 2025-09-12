package top.zhongyingjie.nest.mapper;

import top.zhongyingjie.nest.domain.FightLogInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author Kong
*/
public interface FightLogInfoMapper {

    int deleteByPrimaryKey(Long id);

    int insert(FightLogInfo record);

    int insertSelective(FightLogInfo record);

    FightLogInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(FightLogInfo record);

    int updateByPrimaryKey(FightLogInfo record);

    /**
     * 通过QQ号和时间查询日志信息列表
     *
     * @param qqNum QQ号
     * @param sDate 开始时间
     * @param eDate 结束时间
     * @return 日志列表
     */
    List<FightLogInfo> selectByQQNumAndCreateTime(@Param("qqNum") Integer qqNum,
                                                  @Param("sDate") String sDate,
                                                  @Param("eDate") String eDate);
}
