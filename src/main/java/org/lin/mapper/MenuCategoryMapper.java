package org.lin.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.lin.entity.bo.MenuCategory;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.lin.entity.req.CategoryQuery;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lw
 * @since 2024-03-13
 */
@Mapper
public interface MenuCategoryMapper extends BaseMapper<MenuCategory> {

    long getTotal(@Param("shopId") Integer shopId);

    List<MenuCategory> queryList(@Param("query") CategoryQuery query);
}
