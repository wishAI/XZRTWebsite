package com.wishai.xzrtw.controller;

import com.wishai.xzrtw.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("block")
public class BlockController {

    @Autowired
    ResourceService resourceService;

/*    @GetMapping("/image")
    public String imageBlock(@RequestParam("key") Integer key, ModelMap map) {
        //get the blockSrc
        ImageBlockSrc imageBlockSrc = null;
        try {
            imageBlockSrc = (ImageBlockSrc) resourceService.getBlockSrc(key, ResourceService.BLOCK_TYPE_IMAGE);
        } catch (NoSuchBlockType noSuchBlockType) {
            noSuchBlockType.printStackTrace();
        }
        //set the map attribute here
        map.addAttribute("imageUrl", imageBlockSrc.getFile().toRelUrl());
        map.addAttribute("info", imageBlockSrc.getInfo());

        return "model/ComponentModel :: imageBlock";
    }

    @GetMapping("/paragraph")
    public String paragraphBlock(@RequestParam("key") Integer key, ModelMap map) {
        //get the blockSrc
        ParagraphBlockSrc paragraphBlockSrc = null;
        try {
            paragraphBlockSrc = (ParagraphBlockSrc) resourceService.getBlockSrc(key, ResourceService.BLOCK_TYPE_PARAGRAPH);
        } catch (NoSuchBlockType noSuchBlockType) {
            noSuchBlockType.printStackTrace();
        }
        //set the map attribute here
        map.addAttribute("text", paragraphBlockSrc.getText());

        return "model/ComponentModel :: paragraphBlock";
    }

    @PostMapping("/doAddParagraph")
    public
    @ResponseBody
    String doAddParagraphBlock(@ModelAttribute ParagraphBlockSrc paragraphBlockSrc) {
        Integer key = resourceService.addBlockSrc((BlockSrc) paragraphBlockSrc, ResourceService.BLOCK_TYPE_PARAGRAPH);
        Map<String, String> map = new HashMap<String, String>();
        map.put("key", key.toString());
        JSONObject json = new JSONObject(map);
        return json.toString();
    }*/
}
