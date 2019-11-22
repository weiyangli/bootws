package com.lwy.bootws.service.impl;

import com.lwy.bootws.bean.Group;
import com.lwy.bootws.bean.Result;
import com.lwy.bootws.dao.GroupDao;
import com.lwy.bootws.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupServiceImpl implements GroupService {

    @Autowired
    private GroupDao groupDao;

    @Override
    public Result findGroups() {
        List<Group> groups = groupDao.findGroups();
        return Result.sussess(groups);
    }
}
