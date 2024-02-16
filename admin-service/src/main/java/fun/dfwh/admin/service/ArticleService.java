package fun.dfwh.admin.service;


import fun.dfwh.admin.dto.ArticleDTO;
import fun.dfwh.admin.entity.ArticleInfo;
import fun.dfwh.admin.mapper.ArticleInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArticleService {

    @Autowired(required = false)
    private ArticleInfoMapper articleInfoMapper;

    public void addArticle(ArticleDTO articleDTO) {
        ArticleInfo articleInfo = new ArticleInfo();
        articleInfo.setGategory(articleDTO.getGategory());
        articleInfo.setTitle(articleDTO.getTitle());
        articleInfo.setContent(articleDTO.getContent());
        articleInfo.setSummary(articleDTO.getSummary());
        articleInfo.setCover(articleDTO.getCover());
        articleInfo.setDirectory("");
        articleInfo.setStatus(1);
        articleInfoMapper.insert(articleInfo);
    }
}
