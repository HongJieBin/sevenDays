package com.memory.controller;


import com.memory.pojo.Bug;
import com.memory.service.BugService;
import com.memory.utils.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "/bug")
public class BugController {

    @Autowired
    private static BugService bugService;

    @RequestMapping(value = "/feedback",method = RequestMethod.POST)
    public JsonResult feedback(@RequestParam(value = "userId")Integer uid, @RequestParam(value = "content")String content){
        Bug bug = new Bug();
        bug.setBugContent(content);
        bugService.save(bug);
        return JsonResult.ok();
    }
}
