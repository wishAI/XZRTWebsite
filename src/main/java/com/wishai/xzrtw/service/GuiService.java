package com.wishai.xzrtw.service;

import com.wishai.xzrtw.model.gui.HomeFlowEle;
import com.wishai.xzrtw.model.gui.HomeItemEle;
import com.wishai.xzrtw.model.gui.MenuFlowEle;
import com.wishai.xzrtw.model.gui.MenuItemEle;

import java.util.List;

public interface GuiService {

    List<HomeItemEle> makeHomeItemEles(String lang);

    List<HomeFlowEle> makeHomeFlowEles(String lang);

    List<MenuItemEle> makeMenuItemEles(String lang, String category, int pageNum);

    List<MenuFlowEle> makeMenuFlowEles(String lang, String categpry);
}
