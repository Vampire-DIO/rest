package org.lin.entity.req;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * @Author LvWei
 * @Date 2024/3/14 10:34
 */
@Data
public class ShopSave {

    private Integer id;

    private String name;

    @Min(1)
    @Max(2)
    private Integer status;
}
