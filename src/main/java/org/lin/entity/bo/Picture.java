package org.lin.entity.bo;

import com.baomidou.mybatisplus.annotation.*;

import java.time.LocalDateTime;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * <p>
 * 
 * </p>
 *
 * @author lw
 * @since 2024-01-24
 */
@Data
@TableName("picture")
@ApiModel(value="Picture对象", description="")
public class Picture implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String url;

    private Integer menuId;

    private LocalDateTime createTime;

}
