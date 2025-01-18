package com.ling.mappers;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TestMapper {

    void update(String id, Integer val);
}
