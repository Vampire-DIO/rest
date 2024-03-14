package org.lin.entity.req;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @Author LvWei
 * @Date 2024/3/14 10:02
 */
@Data
public class CategorySave {
    private Integer id;

    @NotBlank
    private String name;

    @NotNull
    private Integer shopId;

    private Integer sort;
}
