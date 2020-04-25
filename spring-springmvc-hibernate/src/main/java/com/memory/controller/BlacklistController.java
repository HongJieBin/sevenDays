package com.memory.controller;


import com.memory.pojo.Blacklist;
import com.memory.service.BlacklistService;
import com.memory.utils.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "/blacklist")
public class BlacklistController {

    @Autowired
    private BlacklistService blacklistService;

    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public JsonResult add(@RequestParam(value = "userId")Integer uid,@RequestParam(value = "addedId")Integer addId){
        Blacklist blacklist = blacklistService.get(uid,addId);
        if(blacklist != null)
            return JsonResult.build(1000,"请求参数错误","该用户已在你的黑名单中");
        else{
            blacklist = new Blacklist();
            blacklist.setUserId(uid);
            blacklist.setAddedId(addId);
            blacklistService.add(blacklist);
        }
        return JsonResult.ok();
    }

    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    public JsonResult delete(@RequestParam(value = "userId")Integer uid,@RequestParam(value = "deleteId")Integer deleteId){
        Blacklist blacklist = blacklistService.get(uid,deleteId);
        if(blacklist == null){
            return JsonResult.build(1000,"请求参数错误","该用户不在你的黑名单中");
        }else{
            blacklistService.delete(blacklist);
        }
        return JsonResult.ok();
    }
}
