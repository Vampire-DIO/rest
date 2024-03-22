package org.lin.service;

import org.apache.ibatis.annotations.Param;
import org.lin.entity.bo.OrderMenuRel;
import com.baomidou.mybatisplus.extension.service.IService;
import org.lin.entity.dto.OrderWithMenu;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lw
 * @since 2024-03-14
 */
public interface IOrderMenuRelService extends IService<OrderMenuRel> {

    OrderWithMenu getOrderWithMenu(Integer orderId);
}
