package org.lin.service.impl;

import org.lin.entity.bo.Order;
import org.lin.mapper.OrderMapper;
import org.lin.service.IOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lw
 * @since 2024-01-24
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements IOrderService {

}
