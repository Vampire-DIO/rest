package org.lin.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;
import lombok.ToString;

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

}
