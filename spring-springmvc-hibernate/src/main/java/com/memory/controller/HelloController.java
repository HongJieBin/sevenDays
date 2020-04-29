package com.memory.controller;

import com.memory.pojo.User;
import com.memory.utils.JsonResult;
import com.memory.utils.JsonUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {

    @RequestMapping("/hell")
    public String hello(Model model) {
        model.addAttribute("msg","hello");
        return "hello";
    }

    @RequestMapping(value = "/hello",method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String hello(@RequestBody User user) {

        return JsonUtils.toJSON(JsonResult.ok(user));
    }
}
