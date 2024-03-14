package org.lin.entity.bo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Author LvWei
 * @Date 2024/3/13 10:42
 */
@Data
@TableName("menu_pic_rel")
public class MenuPicRel implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer menuId;

    private Integer picId;
}
