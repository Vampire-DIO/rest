package org.lin.service;

import org.lin.entity.bo.MenuCategory;
import com.baomidou.mybatisplus.extension.service.IService;
import org.lin.entity.req.CategoryQuery;
import org.lin.entity.req.CategorySave;
import org.lin.entity.vo.PageListVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lw
 * @since 2024-03-13
 */
public interface IMenuCategoryService extends IService<MenuCategory> {

    Integer save(CategorySave category);

    Boolean update(List<CategorySave> category);

    PageListVO<MenuCategory> queryList(CategoryQuery query);
}
