package org.lin.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;
import org.lin.exception.BussinessException;

/**
 * @Author LvWei
 * @Date 2024/3/14 10:35
 */
@Getter
public enum ShopStatusEnum {

    CLOSE("CLOSE", 0),
    OPEN("OPEN", 1)
    ;

    @EnumValue
    private final String tag;

    private final Integer code;

    ShopStatusEnum(String tag, Integer code){
        this.code = code;
        this.tag = tag;
    }

    public static ShopStatusEnum getByCode(int code){
        for (ShopStatusEnum value : ShopStatusEnum.values()) {
            if (value.getCode() == code) {
                return value;
            }
        }
        throw new BussinessException(4000,"未找到相应状态");
    }
}
