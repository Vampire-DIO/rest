package org.lin.entity.req;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author LvWei
 * @Date 2024/3/13 16:59
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class MenuQuery extends PageBaseReq{

    private String name;

    private Integer menuCategoryId;

}
