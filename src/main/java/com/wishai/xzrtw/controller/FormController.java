package com.wishai.xzrtw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("manager/form")
public class FormController {

    private final HttpSession httpSession;

    @Autowired
    public FormController(HttpSession httpSession) {
        this.httpSession = httpSession;
    }

    @GetMapping("/")
    public String formAddPage() {
        return "FormAddPage";
    }

    @GetMapping("upload")
    public String uploadFormPage() {
        return "UploadForm";
    }

    @GetMapping("saveArticle")
    public String articleFormPage() {
        return "ArticleForm";
    }

    @GetMapping("removeArticle")
    public String articleRemover() {
        return "ArticleRemover";
    }
}
