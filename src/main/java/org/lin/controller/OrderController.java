package org.lin.controller;


import lombok.Getter;
import org.lin.aspect.Permission;
import org.lin.entity.bo.Order;
import org.lin.entity.req.OrderQuery;
import org.lin.entity.req.OrderSave;
import org.lin.entity.req.OrderUpdate;
import org.lin.entity.vo.PageListVO;
import org.lin.entity.vo.R;
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
    public R<Integer> save(OrderSave save){
        return new R<Integer>().data(orderService.save(save));
    }

    @DeleteMapping()
    @Permission
    public R<Boolean> cancel(Integer id){
        return new R<Boolean>().data(orderService.cancel(id));
    }

    @PutMapping()
    @Permission
    public R<Boolean> update(OrderUpdate update){
        return new R<Boolean>().data(orderService.update(update));
    }

    @GetMapping()
    @Permission
    public R<PageListVO<Order>> list(OrderQuery query){
        return new R<PageListVO<Order>>().data(orderService.list(query));
    }

}
