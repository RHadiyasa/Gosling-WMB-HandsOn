package com.enigma.wmb_api.service.impl;

import com.enigma.wmb_api.constant.MenuCategory;
import com.enigma.wmb_api.dto.MenuRequest;
import com.enigma.wmb_api.dto.MenuResponse;
import com.enigma.wmb_api.entity.Menu;
import com.enigma.wmb_api.repository.MenuRepository;
import com.enigma.wmb_api.service.MenuService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//@AllArgsConstructor
@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {
    private final MenuRepository menuRepository;

    @Override
    public MenuResponse createMenu(MenuRequest menuRequest) {
        Menu menu = Menu.builder().name(menuRequest.getName()).price(menuRequest.getPrice()).category(MenuCategory.FOOD).build();
        menuRepository.saveAndFlush(menu);
        return toMenuResponse(menu);
    }

    @Override
    public MenuResponse getMenuById(String id) {
        Menu menu = getOne(id);
        return toMenuResponse(menu);
    }

    @Override
    public Menu getOne(String id) {
        Optional<Menu> menu = menuRepository.findById(id);
        if (menu.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Menu with id" + id + " not found");
        }
        return menu.get();
    }

    @Override
    public List<MenuResponse> getAll() {
        List<MenuResponse> menuResponses = new ArrayList<>();
        menuRepository.findAll().forEach(menu -> menuResponses.add(toMenuResponse(menu)));
        return menuResponses;
    }

    @Override
    public MenuResponse updateMenu(String id, MenuRequest menuRequest) {
        Menu currentMenu = getOne(id);
        currentMenu.setName(menuRequest.getName());
        currentMenu.setPrice(menuRequest.getPrice());
//        currentMenu.setCategory(MenuCategory.valueOf(menuRequest.getCategory()));

        currentMenu.setCategory(MenuCategory.FOOD);
//        currentMenu.setCategory(MenuCategory.fromValue(menuRequest.getCategory()));
        menuRepository.save(currentMenu);
        return toMenuResponse(currentMenu);
    }

    @Override
    public void deleteMenu(String id) {
        Menu menu = getOne(id);
        menuRepository.delete(menu);
    }

    private MenuResponse toMenuResponse(Menu menu) {
        MenuResponse menuResponse = new MenuResponse();
        menuResponse.setName(menu.getName());
        menuResponse.setPrice(menu.getPrice());
        menuResponse.setCategory(menu.getCategory());
        return menuResponse;
    }
}
