package com.memory.controller;


import com.alibaba.fastjson.JSONObject;
import com.memory.pojo.Bug;
import com.memory.service.BugService;
import com.memory.utils.JsonResult;
import com.memory.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/bug")
public class BugController {

    @Autowired
    private BugService bugService;

    /**
     * Bug反馈
     * @return
     */
    @RequestMapping(value = "/feedback")
    public @ResponseBody String feedback(int userId,String content){
        Bug bug = new Bug();
        bug.setBugContent(content);                   //build bug object
        try {
            bugService.save(bug);
        }catch (Exception e){
            return JsonUtils.toJSON(JsonResult.build(1000,"请求参数错误",""));
        }
        return JsonUtils.toJSON(JsonResult.ok());
    }

    /**
     * 测试方法
     * @return
     */
    @RequestMapping(value = "/bug")
    public @ResponseBody String bug(){
        System.out.println("test method");
        return JsonUtils.toJSON(JsonResult.ok());
    }
}
