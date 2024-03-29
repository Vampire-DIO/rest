package org.lin.controller;


import lombok.Getter;
import org.lin.aspect.Permission;
import org.lin.entity.bo.Order;
import org.lin.entity.dto.OrderWithMenu;
import org.lin.entity.req.OrderQuery;
import org.lin.entity.req.OrderSave;
import org.lin.entity.req.OrderUpdate;
import org.lin.entity.vo.PageListVO;
import org.lin.entity.vo.R;
import org.lin.entity.vo.order.OrderVO;
import org.lin.service.IOrderService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lw
 * @since 2024-01-24
 */
@RestController
@RequestMapping("/order")
@ControllerAdvice
public class OrderController {

    @Resource
    private IOrderService orderService;


    @PostMapping()
    @Permission
    public R<Integer> save(@RequestBody OrderSave save){
        return new R<Integer>().data(orderService.save(save));
    }

    @DeleteMapping()
    @Permission
    public R<Boolean> cancel(Integer id){
        return new R<Boolean>().data(orderService.cancel(id));
    }

    @PutMapping()
    @Permission
    public R<Boolean> update(@RequestBody  OrderUpdate update){
        return new R<Boolean>().data(orderService.update(update));
    }

    @GetMapping("list")
    @Permission
    public R<PageListVO<OrderWithMenu>> list(OrderQuery query){
        return new R<PageListVO<OrderWithMenu>>().data(orderService.list(query));
    }

    @GetMapping("{id}")
    @Permission
    public R<OrderWithMenu> get(@PathVariable Integer id){
        return new R<OrderWithMenu>().data(orderService.get(id));
    }
}
