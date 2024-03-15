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

    private Integer status = OrderStatusEnum.WAIT_RECEIVE.getCode();

    private Integer shopId;

}
