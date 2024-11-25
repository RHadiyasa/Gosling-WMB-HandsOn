package com.enigma.wmb_api.service.impl;

import com.enigma.wmb_api.constant.MenuCategory;
import com.enigma.wmb_api.dto.request.MenuRequest;
import com.enigma.wmb_api.dto.response.MenuResponse;
import com.enigma.wmb_api.dto.request.SearchMenuRequest;
import com.enigma.wmb_api.entity.Menu;
import com.enigma.wmb_api.repository.MenuRepository;
import com.enigma.wmb_api.service.MenuService;
import com.enigma.wmb_api.specification.MenuSpecification;
import com.enigma.wmb_api.util.SortUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.UUID;

//@AllArgsConstructor
@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {
    private final MenuRepository menuRepository;

    @Override
    public MenuResponse createMenu(MenuRequest menuRequest) {
        Menu menu = Menu.builder()
                .name(menuRequest.getName())
                .price(menuRequest.getPrice())
                .category(MenuCategory.fromValue(menuRequest.getCategory()))
                .isAvailable(true)
                .stock(menuRequest.getStock())
                .build();
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
        return menuRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Menu not found"));
    }

//    @Override
//    public List<MenuResponse> getAll(Integer name, Long price, String menuCategory) {
//        List<MenuResponse> menuResponses = new ArrayList<>();
//        if (name != null && price != null) {
//            menuRepository.findByNameIgnoreCaseAndPrice(name, price).forEach(menu -> menuResponses.add(toMenuResponse(menu)));
//        } else if (name != null) {
//            menuRepository.findByNameIgnoreCase(name).forEach(menu -> menuResponses.add(toMenuResponse(menu)));
//        } else if (price != null) {
//            menuRepository.findByPrice(price).forEach(menu -> menuResponses.add(toMenuResponse(menu)));
//        } else {
//            menuRepository.findAll().forEach(menu -> menuResponses.add(toMenuResponse(menu)));
//        }
//        return menuResponses;
//    }

//    @Override
//    public Page<MenuResponse> getAll(Integer page, Integer size, String sort) {
        // page - 1 karena page di jpa itu seperti array index mulai dari 0
        /**
         * Pageable adalah interface yang berfungsi sebagai penampung atau pembungkus informasi pagination yang ingin ambil datanya misal
         *
         * index/nomor halaman (pageNumber) dimulai dari 0 seperti index array/list
         * jumlah data per halaman (pageSize)
         * informasi urutannya/pengurutan (sort) ini optional untuk sorting
         */
        // intinya ada di Pageable ini dan Page<Menu>, kalau mau diurutkan berarti  dan Sort  juga
//        Pageable menuPageable = PageRequest.of((page - 1), size, SortUtil.parseSortFromQueryParam(sort));
//        Page<Menu> menusPage = menuRepository.findAll(menuPageable);
//        return menusPage.map(menu -> toMenuResponse(menu));
//    }


    @Override
    public Page<MenuResponse> getAll(SearchMenuRequest searchMenuRequest) {
        Pageable menuPageable = PageRequest.of(
                (searchMenuRequest.getPage() - 1),
                searchMenuRequest.getSize(),
                SortUtil.parseSortFromQueryParam(searchMenuRequest.getSort())
        );
        Specification<Menu> menuSpecification = MenuSpecification.getSpecification(searchMenuRequest);
        Page<Menu> menusPage = menuRepository.findAll(menuSpecification, menuPageable);
        return menusPage.map(this::toMenuResponse);
    }

    @Override
    public MenuResponse updateMenu(String id, MenuRequest menuRequest) {
        Menu currentMenu = getOne(id);
        currentMenu.setId(id);
        currentMenu.setName(menuRequest.getName());
        currentMenu.setPrice(menuRequest.getPrice());
        currentMenu.setStock(menuRequest.getStock());
        currentMenu.setCategory(MenuCategory.fromValue(menuRequest.getCategory()));
        menuRepository.save(currentMenu);
        return toMenuResponse(currentMenu); // alternative dari toMenuResponse, dan bagusnya itu di setiap DTO atau di Mapper Util (karena ya biar service focus pada core business logicnya)
    }

    @Override
    public void deleteMenu(String id) {
        Menu menu = getOne(id);
        menuRepository.delete(menu);
    }

    // sebagai Mapper dari Menu Entity menjadi MenuResponse DTO
    private MenuResponse toMenuResponse(Menu menu) {
        MenuResponse menuResponse = new MenuResponse();
        menuResponse.setId(String.valueOf(menu.getId()));
        menuResponse.setName(menu.getName());
        menuResponse.setPrice(menu.getPrice());
        menuResponse.setStock(menu.getStock());
        menuResponse.setCategory(menu.getCategory().toString());
        return menuResponse;
    }
}
