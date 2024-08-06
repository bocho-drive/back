package com.sparta.bochodrive.domain.OAuth;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class OAuth2Controller {

    @GetMapping("/api/oauth/login/kakao")
    @ResponseBody
    public String mainAPI() {

        return "main route";
    }
}