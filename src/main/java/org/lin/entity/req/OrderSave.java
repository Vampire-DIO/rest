package org.lin.entity.req;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.List;

/**
 * @Author LvWei
 * @Date 2024/3/15 10:47
 */
@Data
@ApiModel
public class OrderSave {

    private List<MenuOrder> menuIds;

    private Integer shopId;

}
