package com.enigma.wmb_api.service;

import com.enigma.wmb_api.dto.MenuRequest;
import com.enigma.wmb_api.dto.MenuResponse;
import com.enigma.wmb_api.entity.Menu;

import java.util.List;

public interface MenuService {
    MenuResponse createMenu(MenuRequest menuRequest);
    MenuResponse getMenuById(String id);
    Menu getOne(String id);
    List<MenuResponse> getAll();
    MenuResponse updateMenu(String id, MenuRequest menuRequest);
    void deleteMenu(String id);
}
