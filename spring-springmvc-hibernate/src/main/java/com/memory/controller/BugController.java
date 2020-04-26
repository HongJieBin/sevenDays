package com.memory.controller;


import com.alibaba.fastjson.JSONObject;
import com.memory.pojo.Bug;
import com.memory.service.BugService;
import com.memory.utils.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/bug")
public class BugController {

    @Autowired
    private static BugService bugService;

    /**
     * Bug反馈
     * @param uid
     * @param content
     * @return
     */
    @RequestMapping(value = "/feedback")
    public @ResponseBody JsonResult feedback(@RequestBody String body){
        JSONObject json = JSONObject.parseObject(body);
        Bug bug = new Bug();
        bug.setBugContent(json.getString("content"));
        try {
            bugService.save(bug);
        }catch (Exception e){
            return JsonResult.build(1000,"请求参数错误","");
        }
        return JsonResult.ok();
    }
}
