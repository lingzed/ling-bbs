package com.ling.service.impl;

import com.ling.commons.CommonMsg;
import com.ling.constant.Constant;
import com.ling.entity.dto.UserInfoSessionDto;
import com.ling.entity.dto.query.LikeRecodeQueryDto;
import com.ling.entity.po.Article;
import com.ling.entity.po.LikeRecode;
import com.ling.entity.po.UserMessage;
import com.ling.entity.vo.ArticleVo;
import com.ling.entity.vo.PageBean;
import com.ling.enums.*;
import com.ling.exception.BusinessException;
import com.ling.mappers.ArticleMapper;
import com.ling.mappers.LikeRecodeMapper;
import com.ling.service.ArticleService;
import com.ling.service.LikeRecodeService;
import com.ling.service.UserMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.function.Consumer;

@Service
public class LikeRecodeServiceImpl implements LikeRecodeService {
    private Logger log = LoggerFactory.getLogger(LikeRecodeServiceImpl.class);

    @Resource
    private LikeRecodeMapper likeRecodeMapper;

    /**
     * 条件查询
     *
     * @param
     */
    @Override
    public PageBean<LikeRecode> findByCondition(LikeRecodeQueryDto likeRecodeQueryDto) {
        List<LikeRecode> list = likeRecodeMapper.selectByCondition(likeRecodeQueryDto);
        Long total = findTotalByCondition(likeRecodeQueryDto);
        return PageBean.of(total, likeRecodeQueryDto.getPage(), likeRecodeQueryDto.getPageSize(), list);
    }

    /**
     * 条件查询总数
     *
     * @param
     */
    @Override
    public Long findTotalByCondition(LikeRecodeQueryDto likeRecodeQueryDto) {
        return likeRecodeMapper.selectCountByCondition(likeRecodeQueryDto);
    }

    /**
     * 查询所有
     */
    @Override
    public List<LikeRecode> findAll() {
        return likeRecodeMapper.selectAll();
    }

    /**
     * 查询总数
     */
    @Override
    public Long findTotal() {
        return likeRecodeMapper.selectCount();
    }

    /**
     * 通过id查询
     *
     * @param
     */
    @Override
    public LikeRecode findById(Integer id) {
        return likeRecodeMapper.selectById(id);
    }

    /**
     * 通过目标id和点赞人和点赞类型查询
     *
     * @param targetId
     * @param likerId
     * @param likeType
     * @return
     */
    @Override
    public LikeRecode findByTargetIdAndLikerIdAndLikeType(String targetId, String likerId, Integer likeType) {
        LikeRecode likeRecode = likeRecodeMapper.selectByTargetIdAndLikerIdAndLikeType(targetId, likerId, likeType);
        return likeRecode;
    }

    /**
     * 添加
     *
     * @param
     */
    @Override
    public void add(LikeRecode likeRecode) {
        Date date = new Date();
        likeRecode.setLikeTime(date);
        likeRecodeMapper.insert(likeRecode);
    }

//    /**
//     * 添加
//     * @param
//     */
//    @Override
//    public void add(LikeRecodeDto likeRecodeDto) {
//        LikeRecode likeRecode = new LikeRecode();
//        Date date = new Date();
//        likeRecode.setLikeTime(date);
//        BeanUtils.copyProperties(likeRecodeDto, likeRecode);
//        likeRecodeMapper.insert(likeRecode);
//    }

    /**
     * 批量添加
     *
     * @param
     */
    @Override
    public void batchAdd(List<LikeRecode> list) {
        Date date = new Date();
        list.forEach(e -> {
            e.setLikeTime(date);
        });
        likeRecodeMapper.batchInsert(list);
    }

//    /**
//     * 批量添加
//     * @param
//     */
//    @Override
//    public void batchAdd(List<LikeRecodeDto> list) {
//        Date date = new Date();
//        List<LikeRecode> likeRecodes = list.stream().map(e -> {
//            LikeRecode likeRecode = new LikeRecode();
//            likeRecode.setCreateTime(date);
//            likeRecode.setUpdateTime(date);
//            BeanUtils.copyProperties(e, likeRecode);
//            return likeRecode;
//        }).collect(Collectors.toList());
//        likeRecodeMapper.batchInsert(likeRecodes);
//    }

    /**
     * 编辑
     *
     * @param
     */
    @Override
    public void edit(LikeRecode likeRecode) {
        likeRecodeMapper.update(likeRecode);
    }

//    /**
//     * 编辑
//     * @param
//     */
//    @Override
//    public void edit(LikeRecodeDto likeRecodeDto) {
//        LikeRecode likeRecode = new LikeRecode();
//        Date date = new Date();
//        likeRecode.setUpdateTime(date);
//        BeanUtils.copyProperties(likeRecodeDto, likeRecode);
//        likeRecodeMapper.update(likeRecode);
//    }

    /**
     * 批量编辑
     *
     * @param
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchEdit(List<LikeRecode> list) {
        likeRecodeMapper.batchUpdate(list);
    }

//    /**
//     * 批量编辑
//     * @param
//     */
//    @Override
//    @Transactional(rollbackFor = Exception.class)
//    public void batchEdit(List<LikeRecodeDto> list) {
//        Date date = new Date();
//        List<LikeRecode> likeRecodes = list.stream().map(e -> {
//            LikeRecode likeRecode = new LikeRecode();
//            likeRecode.setUpdateTime(date);
//            BeanUtils.copyProperties(e, likeRecode);
//            return likeRecode;
//        }).collect(Collectors.toList());
//        likeRecodeMapper.batchUpdate(likeRecodes);
//    }

    /**
     * 删除
     *
     * @param
     */
    @Override
    public void delete(List<Integer> list) {
        likeRecodeMapper.delete(list);
    }

    /**
     * 处理文章点赞业务：点赞记录变更&目标点赞量变更
     *
     * @param targetId
     * @param authorId
     * @param likerId
     * @param likeType
     * @param changeLikeCount
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean processLike(String targetId, String authorId, String likerId, Integer likeType,
                               Consumer<Integer> changeLikeCount) {
        LikeRecode likeRecode = findByTargetIdAndLikerIdAndLikeType(targetId, likerId, likeType);
        boolean notLike = Objects.isNull(likeRecode);
        int likeCount = notLike ? Constant.NUM_1 : Constant.NUM_NEG_1;     // 点赞变更量
        if (notLike) {
            LikeRecode lr = new LikeRecode();
            lr.setTargetId(targetId);
            lr.setTargetAuthorId(authorId);
            lr.setLikerId(likerId);
            lr.setLikeType(likeType);
            add(lr);    // 没有点赞记录就增加
        } else {
            delete(Arrays.asList(likeRecode.getLikeRecodeId()));    // 有点赞记录就删除
        }
        changeLikeCount.accept(likeCount);    // 变更目标点赞量
        // 点赞且不是给自己的文章或评论点赞才记录消息，返回可行标识
        return notLike && !Objects.equals(authorId, likerId);
    }
}
