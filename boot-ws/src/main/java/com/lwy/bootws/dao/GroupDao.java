package com.lwy.bootws.dao;

import com.lwy.bootws.bean.Group;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface GroupDao {
    List<Group> findGroups();
}
