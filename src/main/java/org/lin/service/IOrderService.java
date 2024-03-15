package org.lin.service;

import org.lin.entity.bo.Order;
import com.baomidou.mybatisplus.extension.service.IService;
import org.lin.entity.req.OrderQuery;
import org.lin.entity.req.OrderSave;
import org.lin.entity.req.OrderUpdate;
import org.lin.entity.vo.PageListVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lw
 * @since 2024-01-24
 */
public interface IOrderService extends IService<Order> {

    Integer save(OrderSave save);

    Boolean cancel(Integer id);

    Boolean update(OrderUpdate update);

    PageListVO<Order> list(OrderQuery query);
}
