package org.lin.entity.bo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;

/**
 * <p>
 * 
 * </p>
 *
 * @author lw
 * @since 2024-03-14
 */
@TableName("menu_category_rel")
@ApiModel(value="MenuCategoryRel对象", description="")
@Builder
public class MenuCategoryRel implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer menuId;

    private Integer categoryId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getMenuId() {
        return menuId;
    }

    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }
    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public String toString() {
        return "MenuCategoryRel{" +
            "id=" + id +
            ", menuId=" + menuId +
            ", categoryId=" + categoryId +
        "}";
    }
}
