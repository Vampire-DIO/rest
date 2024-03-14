package org.lin.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.lin.aspect.Permission;
import org.lin.entity.bo.Shop;
import org.lin.entity.req.ShopSave;
import org.lin.entity.vo.R;
import org.lin.service.IShopService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @Author LvWei
 * @Date 2024/3/13 16:52
 */
@RestController
@RequestMapping("/shop")
@ControllerAdvice
@Api
public class ShopController {

    @Resource
    private IShopService shopService;


    @GetMapping("{id}")
    @ApiOperation("单个店铺信息")
    @Permission
    public R<Shop> get(@PathVariable Integer id){
        return new R<Shop>().data(shopService.getById(id));
    }

    @PostMapping("")
    @ApiOperation("新增店铺")
    @Permission
    public R<Integer> save(@RequestBody @Valid ShopSave save){
        return new R<Integer>().data(shopService.save(save));
    }

}
