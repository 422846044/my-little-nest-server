package fun.dfwh.nest.service.impl;

import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import fun.dfwh.common.constant.ArticleStatus;
import fun.dfwh.common.entity.ArticleTagsInfo;
import fun.dfwh.nest.mapper.ArticleTagsInfoMapper;
import fun.dfwh.nest.pojo.dto.ArticlePageQueryDTO;
import fun.dfwh.nest.vo.ArticleListVO;
import fun.dfwh.common.entity.ArticleInfo;
import fun.dfwh.nest.mapper.ArticleInfoMapper;
import fun.dfwh.nest.service.ArticleService;
import fun.dfwh.nest.vo.ArticleInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleInfoMapper articleInfoMapper;

    @Autowired
    private ArticleTagsInfoMapper articleTagsInfoMapper;
    @Override
    public PageInfo getArticleByPage(ArticlePageQueryDTO articlePageQuery) {
        StrUtil.trim(articlePageQuery.getKeyword());
        articlePageQuery.setOrder("ai."+articlePageQuery.getOrder());
        List<ArticleListVO> voList = new ArrayList<>();
        PageHelper.startPage(articlePageQuery.getPageNum(),articlePageQuery.getPageSize());
        List<ArticleInfo> articleInfoList = articleInfoMapper.selectByPage(articlePageQuery.getKeyword(),
                articlePageQuery.getCategory(),
                articlePageQuery.getTag(),
                articlePageQuery.getOrder(),
                articlePageQuery.getSort(),
                ArticleStatus.VALID);
        PageInfo pageInfo = new PageInfo(articleInfoList);
        List<Long> articleIdList = articleInfoList.stream().map(ArticleInfo::getId).collect(Collectors.toList());
        if(!articleIdList.isEmpty()){
            List<ArticleTagsInfo> articleTagsInfoList = articleTagsInfoMapper.selectByArticleIds(articleIdList);
            Map<Long, List<ArticleTagsInfo>> articleIdKeyTagsInfoMap = articleTagsInfoList.stream().collect(Collectors.groupingBy(ArticleTagsInfo::getArticleId));
            for (int i = 0; i < articleInfoList.size(); i++) {
                ArticleInfo articleInfo = articleInfoList.get(i);
                ArticleListVO vo = new ArticleListVO();
                vo.setId(Long.toString(articleInfo.getId()));
                vo.setTitle(articleInfo.getTitle());
                vo.setCategory(articleInfo.getCategory());
                vo.setSummary(articleInfo.getSummary());
                vo.setCover(articleInfo.getCover());
                // 获取标签信息
                List<ArticleTagsInfo> articleTagsInfoListVO = articleIdKeyTagsInfoMap.get(articleInfo.getId());
                vo.setTags(Objects.isNull(articleTagsInfoListVO)  ? Collections.emptyList() : articleTagsInfoListVO.stream().map(ArticleTagsInfo::getTagsCode).collect(Collectors.toList()));
                vo.setCreateTime(articleInfo.getCreateTime());
                voList.add(vo);
            }
        }
        pageInfo.setList(voList);
        return pageInfo;
    }

    @Override
    public ArticleInfoVO getArticleInfoById(Long id) {
        ArticleInfoVO vo = new ArticleInfoVO();
        ArticleInfo articleInfo = articleInfoMapper.selectByPrimaryKey(id);
        Long articleInfoId = articleInfo.getId();
        vo.setId(articleInfoId);
        vo.setTitle(articleInfo.getTitle());
        vo.setContent(articleInfo.getContent());
        vo.setCategory(articleInfo.getCategory());
        List<Integer> tagsCodeList =
                articleTagsInfoMapper.selectTagsCodeByArticleId(articleInfoId);
        vo.setTags(tagsCodeList);
        vo.setAuthor(articleInfo.getCreateBy());
        vo.setCreateTime(articleInfo.getCreateTime());
        return vo;
    }
}
