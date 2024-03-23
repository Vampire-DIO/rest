package org.lin.entity.vo.menu;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.List;

/**
 * @Author LvWei
 * @Date 2024/3/13 16:56
 */
@Data
@ApiModel
public class MenuVO {
    private Integer id;
    private Integer categoryId;
    private String name;
    private String description;
    private List<String> pics;
    private Integer price;
    private Integer soldNum;
    private String status;
}
