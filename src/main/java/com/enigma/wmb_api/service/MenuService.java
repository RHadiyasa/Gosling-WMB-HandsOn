package com.enigma.wmb_api.service;

import com.enigma.wmb_api.dto.request.MenuRequest;
import com.enigma.wmb_api.dto.response.MenuResponse;
import com.enigma.wmb_api.dto.request.SearchMenuRequest;
import com.enigma.wmb_api.entity.Menu;
import org.springframework.data.domain.Page;

public interface MenuService {
    MenuResponse createMenu(MenuRequest menuRequest);
    MenuResponse getMenuById(String id);
    Menu getOne(String id);
//    List<MenuResponse> getAll(String name, Long Price, String menuCategory);
    Page<MenuResponse> getAll(SearchMenuRequest searchMenuRequest);
    MenuResponse updateMenu(String id, MenuRequest menuRequest);
    Menu updateOne(Menu menu);
    void deleteMenu(String id);
}
