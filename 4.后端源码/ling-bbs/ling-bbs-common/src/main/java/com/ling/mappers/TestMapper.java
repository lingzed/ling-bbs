package com.ling.mappers;

import com.ling.entity.po.Test;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TestMapper {

    void update(String id, Integer val);

    List<Test> select(String id);
}
