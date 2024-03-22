package org.lin.entity.dto;

import lombok.Data;
import org.lin.enums.ShopStatusEnum;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author LvWei
 * @Date 2024/3/22 15:14
 */
@Data
public class ShopWithRel {
    private Integer id;

    private List<Integer> userIds;

    private String name;

    private LocalDateTime createTime;

    private ShopStatusEnum status;
}
