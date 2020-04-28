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
import com.memory.utils.JsonUtils;
import com.memory.utils.SpringUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
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
    private UserService userService;

    @Autowired
    private UserTagService userTagService;

    @Autowired
    private TagService tagService;

    /**
     * 修改个人信息
     * @param body
     * @return
     */
    @RequestMapping(value = "/modifyInformation", method = RequestMethod.POST)
    public @ResponseBody String modifyInformation (@RequestBody String body){
        JSONObject json = JSONObject.parseObject(body);
        User u;
        try {
            u = userService.get(json.getInteger("userId"));
        }catch (Exception e){
            return JsonUtils.toJSON(JsonResult.errorException("查询错误"));
        }
        if( u ==null ){                         //can not find the user
            return JsonUtils.toJSON(JsonResult.errorMsg("找不到该用户：uid= "+json.getInteger("userId").toString()));
        }
        u.setNickname(json.getString("nickname"));
        if (json.getString("image") != null || json.getString("image").length()!=0 )
            u.setIcon(json.getString("image"));
        u.setGender(json.getString("gender"));
        if (json.getString("profile") != null || json.getString("profile").length() != 0 )
            u.setProfile(json.getString("profile"));
        try {
            userService.update(u);
        }catch (Exception e){
            return JsonUtils.toJSON(JsonResult.errorException("修改错误"));
        }
        return JsonUtils.toJSON(JsonResult.ok());
    }

    /**
     * 获取过去标签
     * @param body
     * @return
     */
    @RequestMapping(value = "/pastTag" , method = RequestMethod.GET)
    public @ResponseBody String pastTag(@RequestBody  String body){
        JSONObject json = JSONObject.parseObject(body);
        List<String> tagList = new LinkedList<>();
        List<UserTag> list = null;
        try {
            list = userTagService.getByUserId(json.getInteger("userId"));
        }catch (Exception e){
            return JsonUtils.toJSON(JsonResult.build(1000,"args error",""));
        }
        for(UserTag ut:list){
            Tag t = tagService.get(ut.getTagId());
            tagList.add(t.getTagName());
        }
        return JsonUtils.toJSON(JsonResult.ok(tagList));
    }

    /**
     * 设置本周标签
     * @param body
     * @return
     */
    @RequestMapping(value = "/setThisWeekTag" , method = RequestMethod.POST)
    public @ResponseBody String setThisWeekTag(@RequestBody String body){
        JSONObject json = JSONObject.parseObject(body);
        Integer uid = json.getInteger("userId");
        User u;
        try {
            u = userService.get(uid);               //查找User
        }catch (Exception e){
            return JsonUtils.toJSON(JsonResult.errorException("查询错误"));
        }
        String lastTag = u.getThisWeekTag();
        String[] lastTagList = lastTag.split(",");
        for(String s: lastTagList){                     //update UserTag chart
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
        try {
            u.setThisWeekTag(json.getString("tag"));            //update user.thisWeekTag
            userService.update(u);
        }catch (Exception e){
            return JsonUtils.toJSON(JsonResult.errorException("服务器错误"));
        }
        return JsonUtils.toJSON(JsonResult.ok());
    }

    /**
     * 获取本周标签
     * @param body
     * @return
     */
    @RequestMapping(value = "/getThisTags",method = RequestMethod.POST)
    public @ResponseBody String getThisTags(@RequestBody String body){
        JSONObject json = JSONObject.parseObject(body);
        User u;
        try {
            u = userService.get(json.getInteger("userId"));
        }catch (Exception e){
            return JsonUtils.toJSON(JsonResult.errorException("查询错误"));
        }
        String tags = u.getThisWeekTag();
        String[] list = tags.split(",");
        return JsonUtils.toJSON(JsonResult.ok(list));
    }
}
