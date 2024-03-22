package org.lin.entity.vo.order;

import lombok.Builder;
import lombok.Data;
import org.lin.entity.vo.menu.MenuVO;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author LvWei
 * @Date 2024/3/22 15:10
 */
@Data
@Builder
public class OrderVO {
    private Integer id;

    private LocalDateTime createTime;

    private Integer status;

    private List<MenuVO> menuList;
}
