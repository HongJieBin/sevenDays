package com.memory.controller;


import com.alibaba.fastjson.JSONObject;
import com.memory.dao.TagDAO;
import com.memory.pojo.Tag;
import com.memory.pojo.User;
import com.memory.pojo.UserTag;
import com.memory.service.TagService;
import com.memory.service.UserService;
import com.memory.service.UserTagService;
import com.memory.utils.JsonResult;
import com.memory.utils.SpringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

@Controller
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private static UserService userService;

    @Autowired
    private static UserTagService userTagService;

    @Autowired
    private static TagService tagService;


    @RequestMapping(value = "/modifyInformation", method = RequestMethod.POST)
    public @ResponseBody JsonResult modifyInformation (@RequestBody String body){
        JSONObject json = JSONObject.parseObject(body);
        User u = userService.get(json.getInteger("userId"));
        u.setNickname(json.getString("nickname"));
        if (json.getString("image") != null || json.getString("image").length()!=0 )
            u.setIcon(json.getString("image"));
        u.setGender(json.getString("gender"));
        if (json.getString("profile") != null || json.getString("profile").length() != 0 )
            u.setProfile(json.getString("profile"));
        userService.update(u);
        return JsonResult.ok();
    }

    @RequestMapping(value = "/pastTag" , method = RequestMethod.GET)
    public @ResponseBody JsonResult pastTag(@RequestBody  String body){
        JSONObject json = JSONObject.parseObject(body);
        List<String> tagList = new LinkedList<>();
        List<UserTag> list = null;
        try {
            list = userTagService.getByUserId(json.getInteger("userId"));
        }catch (Exception e){
            return JsonResult.build(1000,"args error","");
        }
        for(UserTag ut:list){
            Tag t = tagService.get(ut.getTagId());
            tagList.add(t.getTagName());
        }
        return JsonResult.ok(tagList);
    }

    @RequestMapping(value = "/setThisWeekTag" , method = RequestMethod.POST)
    public @ResponseBody JsonResult setThisWeekTag(@RequestBody String body){
        JSONObject json = JSONObject.parseObject(body);
        Integer uid = json.getInteger("userId");
        User u = userService.get(uid);
        String lastTag = u.getThisWeekTag();
        String[] lastTagList = lastTag.split(",");
        for(String s: lastTagList){
            Tag t = tagService.getByTagName(s);
            UserTag ut = userTagService.get(uid,t.getTagId());
            if (ut == null){
                ut = new UserTag();
                ut.setUserId(uid);
                ut.setTagId(t.getTagId());
                ut.setTagNumber(1);
                userTagService.save(ut);
            }else {
                ut.setTagNumber(ut.getTagNumber() + 1);
                userTagService.update(ut);
            }
        }
        u.setThisWeekTag(json.getString("tag"));
        userService.update(u);
        return JsonResult.ok();
    }

    @RequestMapping(value = "/getThisTags",method = RequestMethod.POST)
    public @ResponseBody JsonResult getThisTags(@RequestBody String body){
        JSONObject json = JSONObject.parseObject(body);
        User u = userService.get(json.getInteger("userId"));
        String tags = u.getThisWeekTag();
        String[] list = tags.split(",");
        return JsonResult.ok(list);
    }


}
