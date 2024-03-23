package org.lin.service.impl;

import org.lin.entity.bo.OrderMenuRel;
import org.lin.entity.dto.OrderWithMenu;
import org.lin.mapper.OrderMenuRelMapper;
import org.lin.service.IOrderMenuRelService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.lin.utils.AssertUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lw
 * @since 2024-03-14
 */
@Service
public class OrderMenuRelServiceImpl extends ServiceImpl<OrderMenuRelMapper, OrderMenuRel> implements IOrderMenuRelService {

    @Resource
    private OrderMenuRelMapper orderMenuRelMapper;


    @Override
    public OrderWithMenu getOrderWithMenu(Integer orderId) {
        AssertUtils.notNull(orderId, 4221, "订单id不能为空");
        return orderMenuRelMapper.getOrderWithMenu(orderId);
    }



}
