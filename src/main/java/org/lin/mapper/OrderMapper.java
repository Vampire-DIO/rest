package org.lin.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.lin.entity.bo.Order;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lw
 * @since 2024-01-24
 */
@Mapper
public interface OrderMapper extends BaseMapper<Order> {

}
