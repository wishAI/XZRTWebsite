package com.wishai.xzrtw.controller;

import com.wishai.xzrtw.model.Progress;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("info")
@SessionAttributes("status")
public class InfoController {

    @ModelAttribute("status")
    public Progress status() {
        return new Progress();
    }

    @GetMapping("/upload")
    @ResponseBody
    public String uploadProgressInfo(@ModelAttribute("status") Progress status) {
        if (status.isEmpty()) {
            return "{}";
        }
        return status.toString();
    }
}
