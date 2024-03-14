package org.lin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.lin.entity.bo.Menu;
import org.lin.entity.req.MenuQuery;
import org.lin.entity.req.MenuSave;
import org.lin.entity.vo.PageListVO;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lw
 * @since 2024-03-13
 */
public interface IMenuService extends IService<Menu> {

    PageListVO<Menu> queryList(MenuQuery query);

    Integer save(MenuSave menu);
}
