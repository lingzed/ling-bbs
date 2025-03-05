package com.ling.service.impl;

import com.ling.constant.Constant;
import com.ling.entity.dto.query.LikeRecordQueryDto;
import com.ling.entity.po.LikeRecord;
import com.ling.entity.vo.PageBean;
import com.ling.mappers.LikeRecordMapper;
import com.ling.service.LikeRecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.function.Consumer;

@Service
public class LikeRecordServiceImpl implements LikeRecordService {
    private Logger log = LoggerFactory.getLogger(LikeRecordServiceImpl.class);

    @Resource
    private LikeRecordMapper likeRecordMapper;

    /**
     * 条件查询
     *
     * @param
     */
    @Override
    public PageBean<LikeRecord> findByCondition(LikeRecordQueryDto likeRecordQueryDto) {
        List<LikeRecord> list = likeRecordMapper.selectByCondition(likeRecordQueryDto);
        Long total = findTotalByCondition(likeRecordQueryDto);
        return PageBean.of(total, likeRecordQueryDto.getPage(), likeRecordQueryDto.getPageSize(), list);
    }

    /**
     * 条件查询总数
     *
     * @param
     */
    @Override
    public Long findTotalByCondition(LikeRecordQueryDto likeRecordQueryDto) {
        return likeRecordMapper.selectCountByCondition(likeRecordQueryDto);
    }

    /**
     * 查询所有
     */
    @Override
    public List<LikeRecord> findAll() {
        return likeRecordMapper.selectAll();
    }

    /**
     * 查询总数
     */
    @Override
    public Long findTotal() {
        return likeRecordMapper.selectCount();
    }

    /**
     * 通过id查询
     *
     * @param
     */
    @Override
    public LikeRecord findById(Integer id) {
        return likeRecordMapper.selectById(id);
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
    public LikeRecord findByTargetIdAndLikerIdAndLikeType(String targetId, String likerId, Integer likeType) {
        LikeRecord likeRecord = likeRecordMapper.selectByTargetIdAndLikerIdAndLikeType(targetId, likerId, likeType);
        return likeRecord;
    }

    /**
     * 添加
     *
     * @param
     */
    @Override
    public void add(LikeRecord likeRecord) {
        Date date = new Date();
        likeRecord.setLikeTime(date);
        likeRecordMapper.insert(likeRecord);
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
    public void batchAdd(List<LikeRecord> list) {
        Date date = new Date();
        list.forEach(e -> {
            e.setLikeTime(date);
        });
        likeRecordMapper.batchInsert(list);
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
    public void edit(LikeRecord likeRecord) {
        likeRecordMapper.update(likeRecord);
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
    public void batchEdit(List<LikeRecord> list) {
        likeRecordMapper.batchUpdate(list);
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
        likeRecordMapper.delete(list);
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
        LikeRecord likeRecord = findByTargetIdAndLikerIdAndLikeType(targetId, likerId, likeType);
        boolean notLike = Objects.isNull(likeRecord);
        int likeCount = notLike ? Constant.NUM_1 : Constant.NUM_NEG_1;     // 点赞变更量
        if (notLike) {
            LikeRecord lr = new LikeRecord();
            lr.setTargetId(targetId);
            lr.setTargetAuthorId(authorId);
            lr.setLikerId(likerId);
            lr.setLikeType(likeType);
            add(lr);    // 没有点赞记录就增加
        } else {
            delete(Arrays.asList(likeRecord.getLikeRecordId()));    // 有点赞记录就删除
        }
        changeLikeCount.accept(likeCount);    // 变更目标点赞量
        // 点赞且不是给自己的文章或评论点赞才记录消息，返回可行标识
        return notLike && !Objects.equals(authorId, likerId);
    }
}
