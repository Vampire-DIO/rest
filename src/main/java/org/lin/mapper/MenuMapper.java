package org.lin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.lin.entity.bo.Menu;
import org.lin.entity.dto.MenuDTO;
import org.lin.entity.req.MenuQuery;
import org.lin.entity.vo.menu.CategoryWithMenus;

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
public interface MenuMapper extends BaseMapper<Menu> {

    List<CategoryWithMenus> queryList(@Param("query") MenuQuery query);

    long queryTotal(@Param("categoryId") Integer categoryId);
}
