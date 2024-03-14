package org.lin.controller;

import io.swagger.models.auth.In;
import org.lin.entity.bo.Menu;
import org.lin.entity.req.MenuQuery;
import org.lin.entity.req.MenuSave;
import org.lin.entity.vo.PageListVO;
import org.lin.entity.vo.R;
import org.lin.entity.vo.menu.MenuVO;
import org.lin.exception.BussinessException;
import org.lin.service.IMenuService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * @Author LvWei
 * @Date 2024/3/13 16:52
 */
@RestController
@RequestMapping("/menu")
@ControllerAdvice
public class MenuController {

    @Resource
    private IMenuService menuService;


    @GetMapping("{id}")
    public R<Menu> getById(@PathVariable Integer id){
        return new R<Menu>().data(menuService.getById(id));
    }

    @GetMapping("list")
    public R<PageListVO<Menu>> list(MenuQuery query){
        return new R<PageListVO<Menu>>().data(menuService.queryList(query));
    }

    @PostMapping("")
    public R<Integer> save(@RequestBody @Valid MenuSave menu){
        return new R<Integer>().data(menuService.save(menu));
    }

}
