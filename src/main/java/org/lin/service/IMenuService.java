package org.lin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.lin.entity.bo.Menu;
import org.lin.entity.dto.MenuDTO;
import org.lin.entity.req.MenuQuery;
import org.lin.entity.req.MenuSave;
import org.lin.entity.req.MenuStatusUpdate;
import org.lin.entity.vo.PageListVO;
import org.lin.entity.vo.menu.CategoryWithMenus;
import org.lin.entity.vo.menu.MenuVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lw
 * @since 2024-03-13
 */
public interface IMenuService extends IService<Menu> {

    MenuVO getMenuVOById(Integer id);

    List<CategoryWithMenus>  queryList(MenuQuery query);

    Integer save(MenuSave menu);

    Integer update(MenuSave menu);

    Integer updateStatus(MenuStatusUpdate menu);
}
