package org.lin.entity.bo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.lin.enums.ShopStatusEnum;

/**
 * <p>
 * 
 * </p>
 *
 * @author lw
 * @since 2024-03-13
 */
@TableName("shop")
@ApiModel(value="Shop对象", description="")
@Data
public class Shop implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String name;

    private LocalDateTime createTime;

    private ShopStatusEnum status;

}
