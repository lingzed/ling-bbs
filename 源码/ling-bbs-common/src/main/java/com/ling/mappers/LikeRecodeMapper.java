package com.ling.mappers;

import com.ling.entity.dto.query.LikeRecodeQueryDto;
import com.ling.entity.po.LikeRecode;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface LikeRecodeMapper {
    /**
     * 条件查询
     *
     * @param
     */
    List<LikeRecode> selectByCondition(LikeRecodeQueryDto likeRecodeQueryDto);

    /**
     * 条件查询总数量
     *
     * @param
     */
    Long selectCountByCondition(LikeRecodeQueryDto likeRecodeQueryDto);

    /**
     * 查询所有
     */
    List<LikeRecode> selectAll();

    /**
     * 查询总数量
     */
    Long selectCount();

    /**
     * 通过id查询
     *
     * @param
     */
    LikeRecode selectById(Integer id);

    /**
     * 通过目标id and 点赞人 and 点赞类型查询
     *
     * @param targetId
     * @param likerId
     * @param likeType
     * @return
     */
    LikeRecode selectByTargetIdAndLikerIdAndLikeType(String targetId, String likerId, Integer likeType);

    /**
     * 添加
     *
     * @param
     */
    void insert(LikeRecode likeRecode);

    /**
     * 批量添加
     *
     * @param
     */
    void batchInsert(List<LikeRecode> list);

    /**
     * 编辑
     *
     * @param
     */
    void update(LikeRecode likeRecode);

    /**
     * 批量编辑
     *
     * @param
     */
    void batchUpdate(List<LikeRecode> list);

    /**
     * 删除
     *
     * @param
     */
    void delete(List<Integer> list);
}
