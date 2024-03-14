package org.lin.entity.req;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @Author LvWei
 * @Date 2024/3/13 17:27
 */
@Data
public class MenuSave {
    @NotBlank(message = "菜单名称不能为空")
    private String name;

    private String description;

    @NotNull(message = "菜单分类不能为空")
    private Integer menuCategoryId;

    @NotNull
    @Min(1)
    private Integer price;

    private Integer id;

    private List<Integer> pictures;
}
