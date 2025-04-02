package com.ling.mappers;

import com.ling.entity.po.MailCode;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

@Mapper
public interface MailCodeMapper {

    /**
     * 查询
     *
     * @param mailCode
     * @return
     */
    List<MailCode> select(MailCode mailCode);

    /**
     * 根据mail和code查询
     *
     * @param mail
     * @param code
     * @return
     */
    MailCode selectByMailAndCode(String mail, String code);

    /**
     * 添加
     *
     * @param mailCode
     */
    void insert(MailCode mailCode);

    /**
     * 批量添加
     *
     * @param mailCodes
     */
    void batchInsert(@Param("mailCodes") List<MailCode> mailCodes);

    /**
     * 更新
     *
     * @param mailCode
     */
    void update(MailCode mailCode);

    /**
     * 通过邮箱更新状态
     *
     * @param mail
     * @param updateTime
     */
    void updateStatusByMail(String mail, Date updateTime);

    /**
     * 批量更新
     *
     * @param mailCodes
     */
    void batchUpdate(@Param("mailCodes") List<MailCode> mailCodes);

    /**
     * 根据mail和code删除
     *
     * @param mail
     * @param code
     */
    void delete(String mail, String code);
}
