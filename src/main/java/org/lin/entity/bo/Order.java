package org.lin.entity.bo;

import com.baomidou.mybatisplus.annotation.*;

import java.time.LocalDateTime;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.lin.entity.enums.OrderStatusEnum;

/**
 * <p>
 * 
 * </p>
 *
 * @author lw
 * @since 2024-01-24
 */
@Data
@TableName("order")
@ApiModel(value="Order对象", description="")
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String name;

    private String description;

    private OrderStatusEnum status;

    private Integer creatorId;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

}
