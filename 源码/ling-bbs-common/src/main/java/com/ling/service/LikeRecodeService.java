package com.ling.service;

import com.ling.entity.dto.UserInfoSessionDto;
import com.ling.entity.dto.query.LikeRecodeQueryDto;
import com.ling.entity.po.LikeRecode;
import com.ling.entity.vo.PageBean;

import java.util.List;
import java.util.function.Consumer;

public interface LikeRecodeService {
    /**
     * 条件查询
     *
     * @param
     */
    PageBean<LikeRecode> findByCondition(LikeRecodeQueryDto likeRecodeQueryDto);

    /**
     * 条件查询总数
     *
     * @param
     */
    Long findTotalByCondition(LikeRecodeQueryDto likeRecodeQueryDto);

    /**
     * 查询所有
     */
    List<LikeRecode> findAll();

    /**
     * 查询总数
     */
    Long findTotal();

    /**
     * 通过id查询
     *
     * @param
     */
    LikeRecode findById(Integer id);

    /**
     * 通过目标id和点赞人和点赞类型查询
     *
     * @param targetId
     * @param likerId
     * @param likeType
     * @return
     */
    LikeRecode findByTargetIdAndLikerIdAndLikeType(String targetId, String likerId, Integer likeType);

    /**
     * 添加
     *
     * @param
     */
    void add(LikeRecode likeRecode);

    /**
     * 批量添加
     *
     * @param
     */
    void batchAdd(List<LikeRecode> list);

    /**
     * 编辑
     *
     * @param
     */
    void edit(LikeRecode likeRecode);

    /**
     * 批量编辑
     *
     * @param
     */
    void batchEdit(List<LikeRecode> list);

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
