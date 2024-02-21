package fun.dfwh.nest.service.impl;

import fun.dfwh.nest.entity.ArticleTagsInfo;
import fun.dfwh.nest.mapper.ArticleTagsInfoMapper;
import fun.dfwh.nest.vo.ArticleListVO;
import fun.dfwh.nest.constant.ArticleStatus;
import fun.dfwh.nest.entity.ArticleInfo;
import fun.dfwh.nest.mapper.ArticleInfoMapper;
import fun.dfwh.nest.service.ArticleService;
import fun.dfwh.nest.vo.ArticleInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ArticleServiceImpl implements ArticleService {
    @Autowired(required = false)
    private ArticleInfoMapper articleInfoMapper;

    @Autowired(required = false)
    private ArticleTagsInfoMapper articleTagsInfoMapper;
    @Override
    public Map getArticleByPage(Long lastId, Integer pageSize, Boolean isNext) {
        Map data = new HashMap();
        List<ArticleListVO> voList = new ArrayList<>();
        Integer total = articleInfoMapper.selectCountByStatus(ArticleStatus.EFFECTIVE);
        List<ArticleInfo> articleInfoList = articleInfoMapper.selectByPage(lastId,pageSize,isNext, ArticleStatus.EFFECTIVE);
        for (int i = 0; i < articleInfoList.size(); i++) {
            ArticleInfo articleInfo = articleInfoList.get(i);
            ArticleListVO vo = new ArticleListVO();
            vo.setId(Long.toString(articleInfo.getId()));
            vo.setTitle(articleInfo.getTitle());
            vo.setCategory(articleInfo.getCategory());
            vo.setSummary(articleInfo.getSummary());
            vo.setCover(articleInfo.getCover());
            vo.setTags(new ArrayList<>());
            vo.setCreateTime(articleInfo.getCreateTime());
            voList.add(vo);
            if((i+1)==articleInfoList.size()){
                lastId = articleInfo.getId();
            }
        }
        data.put("articleList",voList);
        data.put("lastId",lastId);
        data.put("total",total);
        return data;
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
