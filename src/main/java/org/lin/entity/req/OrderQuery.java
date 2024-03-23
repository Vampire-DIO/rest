package org.lin.entity.req;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.lin.enums.OrderStatusEnum;

/**
 * @Author LvWei
 * @Date 2024/3/15 11:15
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class OrderQuery extends PageBaseReq{

    private Integer id;

    private String status;

    private Integer shopId;

    private Integer creatorId;

    /**
     * 0 = 创建时间倒序 1= 创建时间逆序
     */
    private Integer sort = 0;
}
