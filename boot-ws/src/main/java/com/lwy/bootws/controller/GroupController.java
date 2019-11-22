package com.lwy.bootws.controller;

import com.lwy.bootws.bean.Result;
import com.lwy.bootws.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class GroupController {

    @Autowired
    private GroupService groupService;

    /**
     * 查询所有的聊天组
     *
     * @param
     * @return
     */
    @GetMapping(Urls.API_GROUP)
    @ResponseBody
    public Result findGroups() {
        return groupService.findGroups();
    }
}
