package com.memory.controller;


import com.alibaba.fastjson.JSONObject;
import com.memory.pojo.Blacklist;
import com.memory.service.BlacklistService;
import com.memory.utils.JsonResult;
import com.memory.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/blacklist")
public class BlacklistController {

    @Autowired
    private BlacklistService blacklistService;


    /**
     * 将用户添加到黑名单中
     * @param body
     * @return
     */
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public @ResponseBody String add(@RequestBody String body){
        JSONObject json = JSONObject.parseObject(body);
        Integer uid = json.getInteger("userId");
        Integer addId = json.getInteger("addedId");
        Blacklist blacklist;
        try {
            blacklist = blacklistService.get(uid, addId);
        }catch (Exception e){
            return JsonUtils.toJSON(JsonResult.errorException("查询错误"));
        }
        if(blacklist != null)
            return JsonUtils.toJSON(JsonResult.build(1000,"请求参数错误","该用户已在你的黑名单中"));
        else{
            blacklist = new Blacklist();
            blacklist.setUserId(uid);
            blacklist.setAddedId(addId);
            try {
                blacklistService.add(blacklist);
            }catch (Exception e){
                return JsonUtils.toJSON(JsonResult.errorException("服务器错误"));
            }
        }
        return JsonUtils.toJSON(JsonResult.ok());
    }

    /**
     * 从黑名单中移除
     * @param body
     * @return
     */
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    public @ResponseBody String delete(@RequestBody String body){
        JSONObject json = JSONObject.parseObject(body);
        Integer uid = json.getInteger("userId");
        Integer deleteId = json.getInteger("deleteId");
        Blacklist blacklist;
        try {
            blacklist = blacklistService.get(uid, deleteId);
        }catch (Exception e){
            return JsonUtils.toJSON(JsonResult.errorException("服务器错误"));
        }
        if(blacklist == null){
            return JsonUtils.toJSON(JsonResult.build(1000,"请求参数错误","该用户不在你的黑名单中"));
        }else{
            blacklistService.delete(blacklist);
        }
        return JsonUtils.toJSON(JsonResult.ok());
    }
}
