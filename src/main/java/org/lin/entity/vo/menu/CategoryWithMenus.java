package org.lin.entity.vo.menu;

import lombok.Data;
import org.lin.entity.bo.Menu;
import org.lin.entity.bo.MenuCategory;

import java.util.List;

/**
 * @Author LvWei
 * @Date 2024/3/19 14:04
 */
@Data
public class CategoryWithMenus {
    private Integer categoryId;

    private String categoryName;

    private Integer categorySort;

    private List<MenuVO> menus;
}
