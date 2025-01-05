package com.ling.service.impl;

import com.ling.commons.CommonMsg;
import com.ling.entity.dto.ArticleQueryDto;
import com.ling.entity.po.Article;
import com.ling.entity.vo.ArticleVo;
import com.ling.entity.vo.PageBean;
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
    public Article findById(String id) {
        return articleMapper.selectById(id);
    }

    /**
     * 通过id查询
     *
     * @param
     */
    @Override
    public Article findById(Integer id) {
        return articleMapper.selectById(id);
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
