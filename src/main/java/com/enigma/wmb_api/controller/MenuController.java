package com.enigma.wmb_api.controller;

import com.enigma.wmb_api.constant.Constant;
import com.enigma.wmb_api.dto.request.MenuRequest;
import com.enigma.wmb_api.dto.response.MenuResponse;
import com.enigma.wmb_api.dto.request.SearchMenuRequest;
import com.enigma.wmb_api.service.MenuService;
import com.enigma.wmb_api.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

//    @GetMapping
//    public ResponseEntity<?>  getAllMenu(@RequestParam(name = "name", required = false) String menuName, @RequestParam(required = false) Long price, @RequestParam(required = false, defaultValue = "Makanan") String menuCategory) {
//            System.out.println("name: " + menuName + " price: " + price + " menuCategory: " + menuCategory);
//        List<MenuResponse> allMenu = menuService.getAll(menuName, price, menuCategory);
//        return ResponseUtil.buildResponse(HttpStatus.OK, Constant.SUCCESS_GET_ALL_MENU, allMenu);
//    }

    //   @GetMapping("/menus")
    @GetMapping
//    public ResponseEntity<?>  getAllMenu(@RequestParam(name = "name", required = false) String menuName, @RequestParam(required = false) Long price, @RequestParam(required = false, defaultValue = "Makanan") String menuCategory) {
    public ResponseEntity<?>  getAllMenu(
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "category", required = false) String category,
            @RequestParam(name = "minPrice", required = false) Long minPrice,
            @RequestParam(name = "maxPrice", required = false) Long maxPrice,
            @RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
            @RequestParam(name = "size", required = false, defaultValue = "10") Integer size,
            @RequestParam(name = "sort", required = false, defaultValue = "name") String sort
            ) {
        SearchMenuRequest searchMenuRequest = SearchMenuRequest.builder()
                .name(name)
                .category(category)
                .minPrice(minPrice)
                .maxPrice(maxPrice)
                .page(page)
                .size(size)
                .sort(sort)
                .build();
        Page<MenuResponse> menuPage = menuService.getAll(searchMenuRequest);
        return ResponseUtil.buildPageResponse(HttpStatus.OK, Constant.SUCCESS_GET_ALL_MENU, menuPage);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getMenu(@PathVariable String id) {
        MenuResponse singleMenu = menuService.getMenuById(id);
        return ResponseUtil.buildResponse(HttpStatus.OK, Constant.SUCCESS_UPDATE_MENU, singleMenu);
    }

//    @GetMapping(path = "/{id}/name")
//    public ResponseEntity<?> getMenuName(@PathVariable String id) {
//        MenuResponse singleMenu = menuService.getMenuById(id);
//        return ResponseUtil.buildResponse(HttpStatus.OK, Constant.SUCCESS_UPDATE_MENU, singleMenu.getName());
//    }

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
