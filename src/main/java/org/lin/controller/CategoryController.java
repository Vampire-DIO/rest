package org.lin.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.lin.aspect.Permission;
import org.lin.entity.bo.MenuCategory;
import org.lin.entity.req.CategoryQuery;
import org.lin.entity.req.CategorySave;
import org.lin.entity.vo.PageListVO;
import org.lin.entity.vo.R;
import org.lin.service.IMenuCategoryService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * @Author LvWei
 * @Date 2024/3/14 10:00
 */
@RequestMapping("category")
@RestController
@Api
public class CategoryController {

    @Resource
    private IMenuCategoryService categoryService;


    @GetMapping("list")
    @ApiOperation("列表查询")
    @Permission
    public R<PageListVO<MenuCategory>> list(@Valid CategoryQuery query){
        return new R<PageListVO<MenuCategory>>().data(categoryService.queryList(query));
    }

    @GetMapping("{id}")
    @ApiOperation("单个查询菜单分类")
    @Permission
    public R<MenuCategory> getById(@PathVariable Integer id){
        return new R<MenuCategory>().data(categoryService.getById(id));
    }

    @PostMapping("")
    @ApiOperation("新增菜单分类")
    @Permission
    public R<Integer> save(@RequestBody CategorySave category){
        return new R<Integer>().data(categoryService.save(category));
    }

    @PutMapping("")
    @ApiOperation("更新菜单分类")
    @Permission
    public R<Boolean> update(@RequestBody List<CategorySave> category){
        return new R<Boolean>().data(categoryService.update(category));
    }

}
