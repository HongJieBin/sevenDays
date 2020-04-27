package com.memory.controller;

import com.memory.pojo.Tag;
import com.memory.service.TagService;
import com.memory.utils.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

@Controller
@RequestMapping(value = "/tag")
public class TagController {

    @Autowired
    private static TagService tagService;

    @RequestMapping(value = "/getTag",method = RequestMethod.GET)
    public @ResponseBody JsonResult getTag(){
        //System.out.println("方法执行。。。");
        Random random = new Random();
        List<Tag> list;
        try {
            list = tagService.getAll();
        }catch (Exception e){
            return JsonResult.errorException("查询异常");
        }
        if(list.size() <= 10)
            return JsonResult.ok(list);
        List<Tag> returnList = new LinkedList<>();
        for(int i = 0 ; i < 10 ; i++){
            returnList.add(list.get(random.nextInt()));
        }
        return JsonResult.ok(returnList);
    }
}
