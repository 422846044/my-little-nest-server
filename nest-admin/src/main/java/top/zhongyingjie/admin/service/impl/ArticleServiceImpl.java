package top.zhongyingjie.admin.service.impl;

import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import top.zhongyingjie.admin.dto.ArticleDTO;
import top.zhongyingjie.admin.dto.ArticlePageQueryDTO;
import top.zhongyingjie.admin.event.AddArticleEvent;
import top.zhongyingjie.admin.event.EditArticleEvent;
import top.zhongyingjie.admin.mapper.ArticleInfoMapper;
import top.zhongyingjie.admin.mapper.ArticleTagsInfoMapper;
import top.zhongyingjie.admin.service.ArticleService;
import top.zhongyingjie.admin.service.DictCacheService;
import top.zhongyingjie.admin.vo.ArticleInfoVO;
import top.zhongyingjie.admin.vo.ArticleListVO;
import top.zhongyingjie.admin.vo.HomeDataCountVO;
import top.zhongyingjie.common.constant.ArticleStatus;
import top.zhongyingjie.common.constant.DictCodes;
import top.zhongyingjie.common.constant.StatusConstant;
import top.zhongyingjie.common.domain.Result;
import top.zhongyingjie.common.entity.ArticleInfo;
import top.zhongyingjie.common.entity.ArticleTagsInfo;
import top.zhongyingjie.common.exchandler.GlobalException;
import top.zhongyingjie.common.utils.CheckUtils;
import top.zhongyingjie.common.utils.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * 文章信息服务实现类
 *
 * @author Kong
 */
@Service
@Slf4j
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleInfoMapper articleInfoMapper;

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private ArticleTagsInfoMapper articleTagsInfoMapper;

    @Autowired
    private DictCacheService dictCacheService;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Transactional(rollbackFor = Exception.class)
    public void addArticle(ArticleDTO articleDTO) {
        ArticleInfo articleInfo = new ArticleInfo();
        Long articleId = idWorker.nextId();
        setArticleInfo(articleDTO, articleInfo, articleId);
        if(articleInfoMapper.insert(articleInfo) == 0){
            log.error("插入文章信息失败：插入条数为0");
            throw new GlobalException("操作失败");
        }
        checkAndInsertTags(articleDTO, articleId);
        // 发送事件
        applicationEventPublisher.publishEvent(new AddArticleEvent(articleInfo.getTitle()));
    }

    private void checkAndInsertTags(ArticleDTO articleDTO, Long articleId) {
        List<String> tags = articleDTO.getTags();
        if(!tags.isEmpty()){
            // 校验标签
            dictCacheService.validCode(DictCodes.ARTICLE_TAGS, tags);
            List<ArticleTagsInfo> articleTagsInfoList = new ArrayList<>(tags.size());
            tags.forEach(tag->{
                ArticleTagsInfo articleTagsInfo = new ArticleTagsInfo();
                articleTagsInfo.setId(idWorker.nextId());
                articleTagsInfo.setArticleId(articleId);
                articleTagsInfo.setTagsCode(tag);
                articleTagsInfoList.add(articleTagsInfo);
            });
            articleTagsInfoMapper.batchInsert(articleTagsInfoList);
        }
    }

    private void setArticleInfo(ArticleDTO articleDTO, ArticleInfo articleInfo, Long articleId) {
        articleInfo.setId(articleId);
        articleInfo.setCategory(articleDTO.getCategory());
        String title = articleDTO.getTitle();
        articleInfo.setTitle(title);
        String content = articleDTO.getContent();
        articleInfo.setContent(content);
        String summary = articleDTO.getSummary();
        summary = extractSummary(content, summary);
        articleInfo.setSummary(summary);
        articleInfo.setCover(articleDTO.getCover());
        articleInfo.setStatus(StatusConstant.VALID);
    }

    @Override
    public HomeDataCountVO getDataCount() {
        HomeDataCountVO homeDataCountVO = new HomeDataCountVO();
        // 查询总数量
        LocalDate now = LocalDate.now();
        int year = now.getYear();
        int month = now.getMonthValue();
        int day = now.getDayOfMonth();
        LocalDate lastMonthDate = now.minusMonths(1);
        int monthThanYear = lastMonthDate.getYear();
        int monthThanMonth = lastMonthDate.getMonthValue();
        LocalDate lastDayDate = now.minusDays(1);
        int dayThanYear = lastDayDate.getYear();
        int dayThanMonth = lastDayDate.getMonthValue();
        int dayThanDay = lastDayDate.getDayOfMonth();
        HashMap<String,Object> dataMap = articleInfoMapper.selectDataCount(year,month,day,monthThanYear,monthThanMonth,dayThanYear,
                dayThanMonth,dayThanDay, ArticleStatus.DRAFT);
        Long allCount = (Long) dataMap.get("allCount");
        homeDataCountVO.setAllCount(allCount);
        Long monthCount = (Long) dataMap.get("monthCount");
        homeDataCountVO.setMonthCount(monthCount);
        Long dayCount = (Long) dataMap.get("dayCount");
        homeDataCountVO.setDayCount(dayCount);
        homeDataCountVO.setDraftCount((Long) dataMap.get("draftCount"));
        Long monthCountThan = (Long) dataMap.get("monthCountThan");
        Long dayCountThan = (Long) dataMap.get("dayCountThan");
        // 计算新增下降占比
        int monthCountThanInt = monthCountThan == 0L ? monthCount.intValue() * 100 : new BigDecimal(Long.toString(monthCount)).subtract(new BigDecimal(Long.toString(monthCountThan))).divide(new BigDecimal(Long.toString(monthCountThan)),RoundingMode.HALF_UP).setScale(2).multiply(new BigDecimal("100")).intValue();
        int dayCountThanInt = dayCountThan == 0L ? dayCount.intValue() * 100 : new BigDecimal(Long.toString(dayCount)).subtract(new BigDecimal(Long.toString(dayCountThan))).divide(new BigDecimal(Long.toString(dayCountThan)),RoundingMode.HALF_UP).setScale(2).multiply(new BigDecimal("100")).intValue();
        homeDataCountVO.setMonthCountThan(monthCountThanInt);
        homeDataCountVO.setDayCountThan(dayCountThanInt);
        return homeDataCountVO;
    }

    @Override
    public Result<Result.PageData<ArticleListVO>> getArticleList(ArticlePageQueryDTO articlePageQuery) {
        StrUtil.trim(articlePageQuery.getKeyword());
        articlePageQuery.setOrder("ai."+articlePageQuery.getOrder());
        List<ArticleListVO> voList = new ArrayList<>();
        PageHelper.startPage(articlePageQuery.getPageNum(),articlePageQuery.getPageSize());
        List<ArticleInfo> articleInfoList = articleInfoMapper.selectByPage(articlePageQuery.getKeyword(),
                articlePageQuery.getCategory(),
                articlePageQuery.getTag(),
                articlePageQuery.getOrder(),
                articlePageQuery.getSort(),
                Arrays.asList(ArticleStatus.VALID,ArticleStatus.DRAFT));
        PageInfo<ArticleInfo> pageInfo = new PageInfo<>(articleInfoList);
        List<Long> articleIdList = articleInfoList.stream().map(ArticleInfo::getId).collect(Collectors.toList());
        if(!articleIdList.isEmpty()){
            Map<String, String> categoryMap = dictCacheService.getDictMapByDictCode(DictCodes.ARTICLE_CATEGORY);
            Map<String, String> tagMap = dictCacheService.getDictMapByDictCode(DictCodes.ARTICLE_TAGS);
            List<ArticleTagsInfo> articleTagsInfoList = articleTagsInfoMapper.selectByArticleIds(articleIdList);
            Map<Long, List<ArticleTagsInfo>> articleIdKeyTagsInfoMap = articleTagsInfoList.stream().collect(Collectors.groupingBy(ArticleTagsInfo::getArticleId));
            for (ArticleInfo articleInfo : articleInfoList) {
                ArticleListVO vo = new ArticleListVO();
                vo.setId(Long.toString(articleInfo.getId()));
                vo.setTitle(articleInfo.getTitle());
                Integer category = articleInfo.getCategory();
                String categoryStr = "";
                if(Objects.nonNull(category)){
                    categoryStr = categoryMap.get(Integer.toString(category));
                }
                vo.setCategory(categoryStr);
                vo.setSummary(articleInfo.getSummary());
                vo.setCover(articleInfo.getCover());
                vo.setStatus(articleInfo.getStatus());
                // 获取标签信息
                List<ArticleTagsInfo> articleTagsInfoListVO = articleIdKeyTagsInfoMap.get(articleInfo.getId());
                vo.setTags(Objects.isNull(articleTagsInfoListVO) ? Collections.emptyList() : articleTagsInfoListVO.stream().map(info -> tagMap.get(info.getTagsCode())).collect(Collectors.toList()));
                vo.setCreateTime(articleInfo.getCreateTime());
                voList.add(vo);
            }
        }
        return Result.page(voList, pageInfo.getTotal());
    }

    @Override
    public ArticleInfoVO getArticleInfo(Long articleId) {
        ArticleInfoVO vo = new ArticleInfoVO();
        ArticleInfo articleInfo = articleInfoMapper.selectByPrimaryKey(articleId);
        Long articleInfoId = articleInfo.getId();
        vo.setId(articleInfoId);
        vo.setTitle(articleInfo.getTitle());
        vo.setContent(articleInfo.getContent());
        vo.setSummary(articleInfo.getSummary());
        vo.setCategory(Integer.toString(articleInfo.getCategory()));
        vo.setStatus(articleInfo.getStatus());
        List<String> tagsCodeList =
                articleTagsInfoMapper.selectTagsCodeByArticleId(articleInfoId);
        vo.setTags(tagsCodeList);
        return vo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateArticle(ArticleDTO articleDTO) {
        ArticleInfo articleInfo = new ArticleInfo();
        Long articleId = articleDTO.getId();
        setArticleInfo(articleDTO, articleInfo, articleId);
        CheckUtils.isTure(articleInfoMapper.updateByPrimaryKeySelective(articleInfo) != 1).throwMessage("更新失败");
        // 去重
        List<String> tags = articleDTO.getTags().stream().distinct().collect(Collectors.toList());
        // 校验标签
        dictCacheService.validCode(DictCodes.ARTICLE_TAGS, tags);
        // 查询文章标签信息
        List<String> tagsCode = articleTagsInfoMapper.selectTagsCodeByArticleId(articleId);
        // 取差集
        List<String> addList = new ArrayList<>(tags);
        List<String> deletedList = new ArrayList<>(tagsCode);
        CheckUtils.isTure((!addList.isEmpty() && !tagsCode.isEmpty() && !addList.removeAll(tagsCode))
                ||(!deletedList.isEmpty() && !tags.isEmpty() && !deletedList.removeAll(tags))).throwMessage("更新失败");
        // 新增
        if(!addList.isEmpty()){
            List<ArticleTagsInfo> articleTagsInfoList = new ArrayList<>(addList.size());
            addList.forEach(tag->{
                ArticleTagsInfo articleTagsInfo = new ArticleTagsInfo();
                articleTagsInfo.setId(idWorker.nextId());
                articleTagsInfo.setArticleId(articleId);
                articleTagsInfo.setTagsCode(tag);
                articleTagsInfoList.add(articleTagsInfo);
            });
            articleTagsInfoMapper.batchInsert(articleTagsInfoList);
        }
                    // 更新为删除
        if(!deletedList.isEmpty()){
            articleTagsInfoMapper.batchDeleted(deletedList, articleId);
        }
        // 发送事件
        applicationEventPublisher.publishEvent(new EditArticleEvent(articleInfo.getTitle()));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addArticleDraft(ArticleDTO articleDTO) {
        ArticleInfo articleInfo = new ArticleInfo();
        Long articleId = articleDTO.getId();
        if(Objects.isNull(articleId)){
            // 若为空则新增
            final Long finalArticleId = idWorker.nextId();
            articleInfo.setId(finalArticleId);
            articleInfo.setCategory(articleDTO.getCategory());
            articleInfo.setTitle(articleDTO.getTitle());
            String content = articleDTO.getContent();
            articleInfo.setContent(content);
            String summary = articleDTO.getSummary();
            summary = extractSummary(content, summary);
            articleInfo.setSummary(summary);
            articleInfo.setCover(articleDTO.getCover());
            articleInfo.setStatus(StatusConstant.DRAFT);
            articleInfoMapper.insert(articleInfo);
            checkAndInsertTags(articleDTO, finalArticleId);
        }else{
            // 更新草稿
            articleInfo.setId(articleId);
            articleInfo.setCategory(articleDTO.getCategory());
            articleInfo.setTitle(articleDTO.getTitle());
            String content = articleDTO.getContent();
            articleInfo.setContent(content);
            String summary = articleDTO.getSummary();
            summary = extractSummary(content, summary);
            articleInfo.setSummary(summary);
            articleInfo.setCover(articleDTO.getCover());
            articleInfo.setStatus(StatusConstant.DRAFT);
            CheckUtils.isTure(articleInfoMapper.updateByPrimaryKeySelective(articleInfo) != 1).throwMessage("更新失败");
            // 去重
            List<String> tags = articleDTO.getTags().stream().distinct().collect(Collectors.toList());
            // 校验标签
            dictCacheService.validCode(DictCodes.ARTICLE_TAGS, tags);

            // 查询文章标签信息
            List<String> tagsCode = articleTagsInfoMapper.selectTagsCodeByArticleId(articleId);
            // 取差集
            List<String> addList = new ArrayList<>(tags);
            List<String> deletedList = new ArrayList<>(tagsCode);
            CheckUtils.isTure((!addList.isEmpty() && !tagsCode.isEmpty() && !addList.removeAll(tagsCode))
                    ||(!deletedList.isEmpty() && !tags.isEmpty() && !deletedList.removeAll(tags))).throwMessage("更新失败");
            // 新增
            if(!addList.isEmpty()){
                List<ArticleTagsInfo> articleTagsInfoList = new ArrayList<>(addList.size());
                addList.forEach(tag->{
                    ArticleTagsInfo articleTagsInfo = new ArticleTagsInfo();
                    articleTagsInfo.setId(idWorker.nextId());
                    articleTagsInfo.setArticleId(articleId);
                    articleTagsInfo.setTagsCode(tag);
                    articleTagsInfoList.add(articleTagsInfo);
                });
                articleTagsInfoMapper.batchInsert(articleTagsInfoList);
            }
            // 更新为删除
            if(!deletedList.isEmpty()){
                articleTagsInfoMapper.batchDeleted(deletedList, articleId);
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delArticleInfo(Long articleId) {
        ArticleInfo articleInfo = articleInfoMapper.selectByPrimaryKey(articleId);
        if(Objects.isNull(articleInfo)){
            throw new GlobalException("未找到该文章");
        }
        if(!articleInfo.getDeletedFlag()){
            ArticleInfo updateInfo = new ArticleInfo();
            updateInfo.setId(articleInfo.getId());
            updateInfo.setDeletedFlag(true);
            if(articleInfoMapper.updateByPrimaryKeySelective(updateInfo) == 0){
                throw new GlobalException("操作失败");
            }
        }
    }

    private String extractSummary(String content, String summary) {
                    // 若概要为空，截取部分正文内容 包含标题（h1,h2,h3,h4,h5,h6,h7）
        if(StrUtil.isBlank(summary)){
            String regex1 = "<h[1-6]>(.*?)</h[1-6]>";
            Pattern pattern1 = Pattern.compile(regex1);
            Matcher matcher1 = pattern1.matcher(content);
            if (matcher1.find()){
                summary = matcher1.group(1);
            }else {
                String regex2 = "<p>(.*?)</p>";
                Pattern pattern2 = Pattern.compile(regex2);
                Matcher matcher2 = pattern2.matcher(content);
                if(matcher2.find()){
                    summary = matcher2.group(1);
                }else{
                    summary = content;
                }
            }
        }
        // 超出长度省略
        StrUtil.maxLength(summary,100);
        return summary;
    }


}
