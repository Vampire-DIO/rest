package org.lin.entity.dto;

import lombok.Data;
import org.lin.entity.bo.Menu;
import org.lin.entity.vo.menu.MenuVO;

import java.util.List;

/**
 * @Author LvWei
 * @Date 2024/3/19 11:23
 */
@Data
public class MenuDTO {
    private Integer categoryId;

    private Integer shopId;

    private Integer categorySort;

    private String categoryName;

    private List<MenuVO> menus;
}
