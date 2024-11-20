package com.enigma.wmb_api.service.impl;

import com.enigma.wmb_api.constant.MenuCategory;
import com.enigma.wmb_api.entity.Menu;
import com.enigma.wmb_api.service.MenuDemoService;

import java.util.UUID;

public class MenuDemoServiceImpl implements MenuDemoService {
    @Override
    public Menu getSomeFooodMenu() {
        return new Menu(new UUID(5, 10), "Bakso", 15000L, MenuCategory.FOOD);
    }
}
