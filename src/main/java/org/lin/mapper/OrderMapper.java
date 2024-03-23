package org.lin.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.lin.entity.bo.Order;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.lin.entity.req.OrderQuery;

import java.util.List;

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

    List<Order> pageList(@Param("query") OrderQuery query);

    Long totalCount(@Param("query") OrderQuery query);
}
