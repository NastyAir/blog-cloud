package com.nastryair.project.hotelmanager.common.swagger;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class SwaggerController {
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index() {
        return "redirect:/eswagger-ui.html";
    }
}
