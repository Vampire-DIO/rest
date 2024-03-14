package org.lin.entity.bo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Author LvWei
 * @Date 2024/3/13 10:43
 */
@Data
@TableName("order_menu_rel")
public class OrderMenuRel implements Serializable {
    private static final long serialVersionUID = 1L;
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer orderId;

    private Integer menuId;

}
