package com.ling.service.impl;

import com.ling.entity.dto.query.ArticleQueryDto;
import com.ling.entity.dto.UserInfoSessionDto;
import com.ling.entity.po.Article;
import com.ling.entity.vo.ArticleDetailVo;
import com.ling.entity.vo.ArticleVo;
import com.ling.entity.vo.PageBean;
import com.ling.enums.ArticleStatusEnum;
import com.ling.enums.ResponseCodeEnum;
import com.ling.exception.BusinessException;
import com.ling.mappers.ArticleMapper;
import com.ling.service.ArticleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ArticleServiceImpl implements ArticleService {
    private Logger log = LoggerFactory.getLogger(ArticleServiceImpl.class);

    @Resource
    private ArticleMapper articleMapper;

    /**
     * 条件查询
     *
     * @param
     */
    @Override
    public PageBean<Article> findByCondition(ArticleQueryDto articleQueryDto) {
        List<Article> list = articleMapper.selectByCondition(articleQueryDto);
        Long total = findTotalByCondition(articleQueryDto);
        return PageBean.of(total, articleQueryDto.getPage(), articleQueryDto.getPageSize(), list);
    }

    /**
     * 条件查询Vo
     *
     * @param articleQueryDto
     * @return
     */
    @Override
    public PageBean<ArticleVo> findVoByCondition(ArticleQueryDto articleQueryDto) {
        List<Article> list = articleMapper.selectByCondition(articleQueryDto);
        List<ArticleVo> articleVos = list.stream().map(e -> {
            ArticleVo vo = new ArticleVo();
            BeanUtils.copyProperties(e, vo);
            return vo;
        }).collect(Collectors.toList());
        Long total = findTotalByCondition(articleQueryDto);
        return PageBean.of(total, articleQueryDto.getPage(), articleQueryDto.getPageSize(), articleVos);
    }

    /**
     * 条件查询总数
     *
     * @param
     */
    @Override
    public Long findTotalByCondition(ArticleQueryDto articleQueryDto) {
        return articleMapper.selectCountByCondition(articleQueryDto);
    }

    /**
     * 查询所有
     */
    @Override
    public List<Article> findAll() {
        return articleMapper.selectAll();
    }

    /**
     * 查询总数
     */
    @Override
    public Long findTotal() {
        return articleMapper.selectCount();
    }

    /**
     * 通过id查询
     *
     * @param
     */
    @Override
    public ArticleVo findById(String id) {
        ArticleVo articleVo = new ArticleVo();
        BeanUtils.copyProperties(articleMapper.selectById(id), articleVo);
        return articleVo;
    }

    /**
     * 通过id查询
     *
     * @param
     */
    @Override
    public ArticleVo findById(Integer id) {
        ArticleVo articleVo = new ArticleVo();
        BeanUtils.copyProperties(articleMapper.selectById(id), articleVo);
        return articleVo;
    }

    /**
     * 文章详情
     *
     * @param userinfo
     * @param articleId
     * @return
     */
    @Override
    public ArticleDetailVo articleDetails(UserInfoSessionDto userinfo, String articleId) {
        ArticleVo articleVo = findById(articleId);
        if (Objects.isNull(articleVo))
            throw new BusinessException(ResponseCodeEnum.CODE_404);
        // 不能访问已删除文章
        boolean isDeleted = articleVo.getStatus().equals(ArticleStatusEnum.DELETED.getStatus());
        if (isDeleted)
            throw new BusinessException(ResponseCodeEnum.CODE_404);

        boolean isLogin = Objects.nonNull(userinfo);
        boolean isPending = articleVo.getStatus().equals(ArticleStatusEnum.PENDING.getStatus());
        // 未登录不能访问待审核文章
        if (!isLogin && isPending)
            throw new BusinessException(ResponseCodeEnum.CODE_404);

        boolean isAuthor = isLogin && articleVo.getUserId().equals(userinfo.getUserId());
        // 非管理员登录，不能访问待审核文章，除非是作者本人
        if (isLogin && !userinfo.getIsAdmin() && isPending && !isAuthor)
            throw new BusinessException(ResponseCodeEnum.CODE_404);

        boolean isAudited = articleVo.getStatus().equals(ArticleStatusEnum.AUDITED.getStatus());
        // 登录用户访问已审核文章才增加阅读量
        if (isLogin && isAudited) articleMapper.incrementReadCount(articleId);

        ArticleDetailVo articleDetailVo = new ArticleDetailVo();
        articleDetailVo.setArticle(articleVo);
        articleDetailVo.setAttachment();
        return articleDetailVo;
    }

    /**
     * 添加
     *
     * @param
     */
    @Override
    public void add(Article article) {
        Date date = new Date();
        article.setCreateTime(date);
        article.setUpdateTime(date);
        articleMapper.insert(article);
    }

//    /**
//     * 添加
//     *
//     * @param
//     */
//    @Override
//    public void add(ArticleDto articleDto) {
//        Article article = new Article();
//        Date date = new Date();
//        article.setCreateTime(date);
//        article.setUpdateTime(date);
//        BeanUtils.copyProperties(articleDto, article);
//        articleMapper.insert(article);
//    }

    /**
     * 批量添加
     *
     * @param
     */
    @Override
    public void batchAdd(List<Article> list) {
        Date date = new Date();
        list.forEach(e -> {
            e.setCreateTime(date);
            e.setUpdateTime(date);
        });
        articleMapper.batchInsert(list);
    }

//    /**
//     * 批量添加
//     *
//     * @param
//     */
//    @Override
//    public void batchAdd(List<ArticleDto> list) {
//        Date date = new Date();
//        List<Article> articles = list.stream().map(e -> {
//            Article article = new Article();
//            article.setCreateTime(date);
//            article.setUpdateTime(date);
//            BeanUtils.copyProperties(e, article);
//            return article;
//        }).collect(Collectors.toList());
//        articleMapper.batchInsert(articles);
//    }

    /**
     * 编辑
     *
     * @param
     */
    @Override
    public void edit(Article article) {
        Date date = new Date();
        article.setUpdateTime(date);
        articleMapper.update(article);
    }

//    /**
//     * 编辑
//     *
//     * @param
//     */
//    @Override
//    public void edit(ArticleDto articleDto) {
//        Article article = new Article();
//        Date date = new Date();
//        article.setUpdateTime(date);
//        BeanUtils.copyProperties(articleDto, article);
//        articleMapper.update(article);
//    }

    /**
     * 批量编辑
     *
     * @param
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchEdit(List<Article> list) {
        Date date = new Date();
        list.forEach(e -> {
            e.setUpdateTime(date);
        });
        articleMapper.batchUpdate(list);
    }

//    /**
//     * 批量编辑
//     *
//     * @param
//     */
//    @Override
//    @Transactional(rollbackFor = Exception.class)
//    public void batchEdit(List<ArticleDto> list) {
//        Date date = new Date();
//        List<Article> articles = list.stream().map(e -> {
//            Article article = new Article();
//            article.setUpdateTime(date);
//            BeanUtils.copyProperties(e, article);
//            return article;
//        }).collect(Collectors.toList());
//        articleMapper.batchUpdate(articles);
//    }

    /**
     * 删除
     *
     * @param
     */
    @Override
    public void delete(List<String> list) {
        articleMapper.delete(list);
    }
}
