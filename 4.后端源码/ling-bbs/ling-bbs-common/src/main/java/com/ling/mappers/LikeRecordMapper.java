package com.ling.mappers;

import com.ling.entity.dto.query.LikeRecordQuery;
import com.ling.entity.po.LikeRecord;
import com.ling.entity.vo.LikeRecordVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface LikeRecordMapper {
    /**
     * 条件查询
     *
     * @param
     */
    List<LikeRecord> selectByCondition(LikeRecordQuery likeRecordQueryDto);

    /**
     * 条件查询总数量
     *
     * @param
     */
    Long selectCountByCondition(LikeRecordQuery likeRecordQueryDto);

    /**
     * 查询所有
     */
    List<LikeRecord> selectAll();

    /**
     * 查询总数量
     */
    Long selectCount();

    /**
     * 通过id查询
     *
     * @param
     */
    LikeRecord selectById(Integer id);

    /**
     * 通过目标id and 点赞人 and 点赞类型查询
     *
     * @param targetId
     * @param likerId
     * @param likeType
     * @return
     */
    LikeRecord selectByTargetIdAndLikerIdAndLikeType(String targetId, String likerId, Integer likeType);

    /**
     * 查询点赞历史
     *
     * @param likerId
     * @return
     */
    List<LikeRecordVo> selectLikeHistory(String likerId);

    /**
     * 添加
     *
     * @param
     */
    void insert(LikeRecord likeRecord);

    /**
     * 批量添加
     *
     * @param
     */
    void batchInsert(List<LikeRecord> list);

    /**
     * 编辑
     *
     * @param
     */
    void update(LikeRecord likeRecord);

    /**
     * 批量编辑
     *
     * @param
     */
    void batchUpdate(List<LikeRecord> list);

    /**
     * 删除
     *
     * @param
     */
    void delete(List<Integer> list);
}
