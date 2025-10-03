package top.zhongyingjie.nest.service.impl;

import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.context.ApplicationEventPublisher;
import top.zhongyingjie.common.constant.ArticleStatus;
import top.zhongyingjie.common.domain.Result;
import top.zhongyingjie.common.entity.ArticleTagsInfo;
import top.zhongyingjie.common.utils.IpUtils;
import top.zhongyingjie.nest.event.ArticleViewEvent;
import top.zhongyingjie.nest.event.DayViewEvent;
import top.zhongyingjie.nest.mapper.ArticleTagsInfoMapper;
import top.zhongyingjie.nest.pojo.dto.ArticlePageQueryDTO;
import top.zhongyingjie.nest.vo.ArticleListVO;
import top.zhongyingjie.common.entity.ArticleInfo;
import top.zhongyingjie.nest.mapper.ArticleInfoMapper;
import top.zhongyingjie.nest.service.ArticleService;
import top.zhongyingjie.nest.vo.ArticleInfoVO;
import top.zhongyingjie.nest.vo.HistoryListVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 文章信息服务实现
 *
 * @author Kong
 */
@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleInfoMapper articleInfoMapper;

    @Autowired
    private ArticleTagsInfoMapper articleTagsInfoMapper;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Override
    public Result<Result.PageData<ArticleListVO>> getArticleByPage(ArticlePageQueryDTO articlePageQuery) {
        StrUtil.trim(articlePageQuery.getKeyword());
        articlePageQuery.setOrder("ai." + articlePageQuery.getOrder());
        List<ArticleListVO> voList = new ArrayList<>();
        PageHelper.startPage(articlePageQuery.getPageNum(), articlePageQuery.getPageSize());
        List<ArticleInfo> articleInfoList = articleInfoMapper.selectByPage(articlePageQuery.getKeyword(),
                articlePageQuery.getCategory(),
                articlePageQuery.getTag(),
                articlePageQuery.getYear(),
                articlePageQuery.getMonth(),
                articlePageQuery.getOrder(),
                articlePageQuery.getSort(),
                ArticleStatus.VALID);
        PageInfo<ArticleInfo> pageInfo = new PageInfo<>(articleInfoList);
        List<Long> articleIdList = articleInfoList.stream().map(ArticleInfo::getId).collect(Collectors.toList());
        if (!articleIdList.isEmpty()) {
            List<ArticleTagsInfo> articleTagsInfoList = articleTagsInfoMapper.selectByArticleIds(articleIdList);
            Map<Long, List<ArticleTagsInfo>> articleIdKeyTagsInfoMap = articleTagsInfoList
                    .stream().collect(Collectors.groupingBy(ArticleTagsInfo::getArticleId));
            for (ArticleInfo articleInfo : articleInfoList) {
                ArticleListVO vo = new ArticleListVO();
                vo.setId(Long.toString(articleInfo.getId()));
                vo.setTitle(articleInfo.getTitle());
                vo.setCategory(articleInfo.getCategory());
                vo.setSummary(articleInfo.getSummary());
                vo.setCover(articleInfo.getCover());
                // 获取标签信息
                List<ArticleTagsInfo> articleTagsInfoListVO = articleIdKeyTagsInfoMap.get(articleInfo.getId());
                vo.setTags(Objects.isNull(articleTagsInfoListVO) ? Collections.emptyList() : articleTagsInfoListVO
                        .stream().map(ArticleTagsInfo::getTagsCode).collect(Collectors.toList()));
                vo.setCreateTime(articleInfo.getCreateTime());
                voList.add(vo);
            }
        }
        applicationEventPublisher.publishEvent(new DayViewEvent(IpUtils.getIpAddr()));
        return Result.page(voList, pageInfo.getTotal());
    }

    @Override
    public ArticleInfoVO getArticleInfoById(Long id) {
        ArticleInfoVO vo = new ArticleInfoVO();
        ArticleInfo articleInfo = articleInfoMapper.selectByPrimaryKey(id);
        Long articleInfoId = articleInfo.getId();
        vo.setTitle(articleInfo.getTitle());
        vo.setId(articleInfoId);
        vo.setContent(articleInfo.getContent());
        vo.setCategory(articleInfo.getCategory());
        List<Integer> tagsCodeList =
                articleTagsInfoMapper.selectTagsCodeByArticleId(articleInfoId);
        vo.setTags(tagsCodeList);
        vo.setAuthor(articleInfo.getCreateBy());
        vo.setCreateTime(articleInfo.getCreateTime());
        applicationEventPublisher.publishEvent(new ArticleViewEvent(IpUtils.getIpAddr(), vo.getTitle()));
        return vo;
    }

    @Override
    public List<HistoryListVO> getHistory() {
        List<HistoryListVO> dataList = articleInfoMapper.selectHistoryCount();
        return dataList;
    }

    @Override
    public Long getCount() {
        return articleInfoMapper.selectCountByStatus(ArticleStatus.VALID);
    }
}
