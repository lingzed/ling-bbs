package com.ling.service;

import com.ling.entity.dto.query.LikeRecordQuery;
import com.ling.entity.po.LikeRecord;
import com.ling.entity.vo.LikeRecordVo;
import com.ling.entity.vo.PageBean;

import java.util.List;
import java.util.function.Consumer;

public interface LikeRecordService {
    /**
     * 条件查询
     * 返回 PageBean
     *
     * @param
     */
    PageBean<LikeRecord> findByCondition(LikeRecordQuery likeRecordQueryDto);

    /**
     * 条件查询
     * 返回 List
     *
     * @param likeRecordQueryDto
     * @return
     */
    List<LikeRecord> findListByCondition(LikeRecordQuery likeRecordQueryDto);

    /**
     * 条件查询
     * 返回VoList
     *
     * @param likeRecordQueryDto
     * @return
     */
    List<LikeRecordVo> findVoListByCondition(LikeRecordQuery likeRecordQueryDto);

    /**
     * 条件查询总数
     *
     * @param
     */
    Long findTotalByCondition(LikeRecordQuery likeRecordQueryDto);

    /**
     * 查询所有
     */
    List<LikeRecord> findAll();

    /**
     * 查询总数
     */
    Long findTotal();

    /**
     * 通过id查询
     *
     * @param
     */
    LikeRecord findById(Integer id);

    /**
     * 通过目标id和点赞人和点赞类型查询
     *
     * @param targetId
     * @param likerId
     * @param likeType
     * @return
     */
    LikeRecord findByTargetIdAndLikerIdAndLikeType(String targetId, String likerId, Integer likeType);

    /**
     * 查询点赞历史
     *
     * @param likerId
     * @return
     */
    List<LikeRecordVo> findLikeHistory(String likerId);

    /**
     * 添加
     *
     * @param
     */
    void add(LikeRecord likeRecord);

    /**
     * 批量添加
     *
     * @param
     */
    void batchAdd(List<LikeRecord> list);

    /**
     * 编辑
     *
     * @param
     */
    void edit(LikeRecord likeRecord);

    /**
     * 批量编辑
     *
     * @param
     */
    void batchEdit(List<LikeRecord> list);

    /**
     * 删除
     *
     * @param
     */
    void delete(List<Integer> list);


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
    boolean processLike(String targetId, String authorId, String likerId, Integer likeType,
                        Consumer<Integer> changeLikeCount);
}
