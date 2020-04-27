package com.memory.controller;

import com.memory.pojo.Tag;
import com.memory.service.TagService;
import com.memory.utils.JsonResult;
import com.memory.utils.JsonUtils;
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
    private TagService tagService;

    @RequestMapping(value = "/getTag",method = RequestMethod.GET)
    public @ResponseBody String getTag(){
        System.out.println("方法执行。。。");
        Random random = new Random();
        List<Tag> list;
        try {
            list = tagService.getAll();
        }catch (Exception e){
            return JsonUtils.toJSON(JsonResult.errorException("查询异常"));
        }
        if(list.size() <= 10)
            return JsonUtils.toJSON(JsonResult.ok(list));
        List<Tag> returnList = new LinkedList<>();
        for(int i = 0 ; i < 10 ; i++){
            returnList.add(list.get(random.nextInt()%list.size()));
        }
        return JsonUtils.toJSON(JsonResult.ok(returnList));
    }
}
