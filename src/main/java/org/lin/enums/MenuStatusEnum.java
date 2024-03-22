package org.lin.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;
import lombok.ToString;
import org.lin.exception.BussinessException;

import java.util.Objects;

/**
 * @Author LvWei
 * @Date 2024/3/14 8:53
 */
@Getter
@ToString
public enum MenuStatusEnum {

    EXIST(0, "存在"),

    SOLD_OUT(1, "售罄");


    private final Integer code;

    @EnumValue
    private final String tag;

    MenuStatusEnum(Integer code, String tag) {
        this.code = code;
        this.tag = tag;
    }

    public static MenuStatusEnum getByCode(Integer status) {
        for (MenuStatusEnum value : MenuStatusEnum.values()) {
            if (Objects.equals(value.getCode(), status)){
                return value;
            }
        }
        throw new BussinessException(4312, "未找到对应的状态");
    }
}
