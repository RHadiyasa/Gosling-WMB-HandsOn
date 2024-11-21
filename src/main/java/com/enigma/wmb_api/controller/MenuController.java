package com.enigma.wmb_api.controller;

import com.enigma.wmb_api.constant.Constant;
import com.enigma.wmb_api.constant.MenuCategory;
import com.enigma.wmb_api.dto.MenuRequest;
import com.enigma.wmb_api.dto.MenuResponse;
import com.enigma.wmb_api.service.MenuDemoService;
import com.enigma.wmb_api.service.MenuService;
import com.enigma.wmb_api.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@RequestMapping("/menus")
@RequestMapping(Constant.MENU_API)
@RestController
@RequiredArgsConstructor
public class MenuController {
    private final MenuService menuService;

    //   @PostMapping("/menus")
    @PostMapping
    public ResponseEntity<?> createNewMenu(@RequestBody MenuRequest request) {
        MenuResponse createdMenu = menuService.createMenu(request);
        return ResponseUtil.buildResponse(HttpStatus.CREATED, Constant.SUCCESS_CREATE_MENU, createdMenu);
    }

    //   @GetMapping("/menus")
    @GetMapping
    public ResponseEntity<?>  getAllMenu(@RequestParam(name = "name", required = false) String menuName, @RequestParam(required = false) Long price, @RequestParam(required = false) String menuCategory) {
        System.out.println("name: " + menuName + " price: " + price + " menuCategory: " + menuCategory);
        List<MenuResponse> allMenu = menuService.getAll(menuName, price, menuCategory);
        return ResponseUtil.buildResponse(HttpStatus.OK, Constant.SUCCESS_GET_ALL_MENU, allMenu);
    }

    //   @PutMapping("/menus")
    @PutMapping(path = "/{id}")
    public ResponseEntity<?> updateMenu(@PathVariable String id, @RequestBody MenuRequest request) {
        MenuResponse updatedMenu = menuService.updateMenu(id, request);
        return ResponseUtil.buildResponse(HttpStatus.OK, Constant.SUCCESS_UPDATE_MENU, updatedMenu);
    }

    //   @DeleteMapping("/menus")
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> deleteMenu(@PathVariable String id) {
        menuService.deleteMenu(id);
        return ResponseUtil.buildResponse(HttpStatus.OK, Constant.SUCCESS_DELETE_MENU, null);
    }

//    @Autowired
//    MenuDemoService menuDemoService;
//    private final MenuDemoService menuDemoService;
//
//    public MenuController(MenuDemoService menuDemoService) {
//        this.menuDemoService = menuDemoService;
//    }

//    @GetMapping(path = "bean-demo")
//    public String getMakananMenuBean() {
////        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfigBeanDemo.class);
////        Menu menu = context.getBean("menu", Menu.class);
//        return menuDemoService.getSomeFooodMenu().toString();
//    }


}
