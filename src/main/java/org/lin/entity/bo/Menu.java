package org.lin.entity.bo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import org.lin.enums.MenuStatusEnum;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Author LvWei
 * @Date 2024/3/13 10:41
 */
@Data
@TableName("menu")
public class Menu  implements Serializable {
    private static final long serialVersionUID = 1L;
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String name;
    private String description;
    private Integer sort;
    private Integer soldNum;
    private Integer price;
    private MenuStatusEnum status;
    private LocalDateTime createTime;

}
