package com.wishai.xzrtw.controller

import com.wishai.xzrtw.model.Progress
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*

@Controller
@RequestMapping("info")
@SessionAttributes("status")
class InfoController {

    @ModelAttribute("status")
    fun status(): Progress {
        return Progress()
    }

    @GetMapping("/upload")
    @ResponseBody
    fun uploadProgressInfo(@ModelAttribute("status") status: Progress): String {
        return if (status.isEmpty) {
            "{}"
        } else {
            status.toString()
        }
    }
}
