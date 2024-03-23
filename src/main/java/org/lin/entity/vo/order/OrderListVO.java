package org.lin.entity.vo.order;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.lin.entity.vo.menu.MenuVO;
import org.lin.enums.OrderStatusEnum;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

/**
 * @Author: lvwei
 * @Description: TODO
 * @DateTime: 2024/3/23 11:17
 **/
@Data
public class OrderListVO {
    private Integer id;

    private Integer creatorId;

    private OrderStatusEnum status;

    private Integer shopId;

    private List<MenuVO> menuList;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    private Set<String> pics;
}
