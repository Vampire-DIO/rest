package org.lin.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.lin.entity.bo.ShopUserRel;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.lin.entity.dto.ShopWithRel;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lw
 * @since 2024-03-13
 */
@Mapper
public interface ShopUserRelMapper extends BaseMapper<ShopUserRel> {

    ShopWithRel getShopWithRelById(@Param("shopId") Integer shopId);
}
