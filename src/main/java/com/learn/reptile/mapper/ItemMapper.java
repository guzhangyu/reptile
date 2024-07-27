package com.learn.reptile.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.learn.reptile.entity.po.Item;

@Mapper
public interface ItemMapper extends BaseMapper<Item> {

}
