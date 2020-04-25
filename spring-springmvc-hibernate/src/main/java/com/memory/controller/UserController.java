package com.memory.controller;


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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

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
    public JsonResult modifyInformation (@RequestParam(value = "userId")Integer uid,
                                         @RequestParam(value = "nickname")String nickname,
                                         @RequestParam(value = "image",required = false)String image,
                                         @RequestParam(value = "gender")String gender,
                                         @RequestParam(value = "profile",required = false)String profile){
        User u = userService.get(uid);
        u.setNickname(nickname);
        if (image != null)
            u.setIcon(image);
        u.setGender(gender);
        if (profile != null)
            u.setProfile(profile);
        userService.update(u);
        return JsonResult.ok();
    }

    @RequestMapping(value = "/pastTag" , method = RequestMethod.GET)
    public JsonResult pastTag(@RequestParam(value = "userId")Integer uid){
        List<String> tagList = new LinkedList<>();
        List<UserTag> list = userTagService.getByUserId(uid);
        for(UserTag ut:list){
            Tag t = tagService.get(ut.getTagId());
            tagList.add(t.getTagName());
        }
        return JsonResult.ok(tagList);
    }

    @RequestMapping(value = "/setThisWeekTag" , method = RequestMethod.POST)
    public JsonResult setThisWeekTag(@RequestParam(value = "userId")Integer uid,
                                     @RequestParam(value = "tag",required = false)String tag){
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
        return JsonResult.ok();
    }

    @RequestMapping(value = "/getThisTags",method = RequestMethod.POST)
    public JsonResult getThisTags(@RequestParam(value = "userId") Integer uid){
        User u = userService.get(uid);
        String tags = u.getThisWeekTag();
        String[] list = tags.split(",");
        return JsonResult.ok(list);
    }


}
