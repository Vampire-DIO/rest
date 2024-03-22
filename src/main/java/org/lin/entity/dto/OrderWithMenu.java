package org.lin.entity.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.lin.entity.vo.menu.MenuVO;
import org.lin.enums.OrderStatusEnum;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author LvWei
 * @Date 2024/3/22 15:33
 */
@Data
public class OrderWithMenu {

    private Integer id;

    private Integer creatorId;

    private OrderStatusEnum status;

    private Integer shopId;

    private List<MenuVO> menuList;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
}
