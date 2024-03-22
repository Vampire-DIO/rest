package org.lin.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.lin.entity.bo.OrderMenuRel;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.lin.entity.dto.OrderWithMenu;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lw
 * @since 2024-03-14
 */
@Mapper
public interface OrderMenuRelMapper extends BaseMapper<OrderMenuRel> {

    OrderWithMenu getOrderWithMenu(@Param("orderId")  Integer orderId);
}
