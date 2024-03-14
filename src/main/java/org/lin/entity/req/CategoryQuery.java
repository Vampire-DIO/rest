package org.lin.entity.req;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

/**
 * @Author LvWei
 * @Date 2024/3/14 10:06
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CategoryQuery extends PageBaseReq{

    private String name;

    @NotNull
    private Integer shopId;
}
