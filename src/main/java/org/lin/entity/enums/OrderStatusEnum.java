package org.lin.entity.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;
import lombok.ToString;

/**
 * @Author LvWei
 * @Date 2024/3/13 11:10
 */
@Getter
@ToString
public enum OrderStatusEnum {

    IN_PROCESS(0, "处理中", "IN_PROCESS"),
    SUCCESS(1, "已完成,待评价","SUCCESS_WAIT_EVALUATE"),
    CANCEL(2, "已取消","CANCEL"),
    FINISH(3, "已完成,已评价","FINISH"),
    ;

    private final Integer code;

    @EnumValue
    private final String tag;

    private final String message;

    OrderStatusEnum(Integer code, String message, String tag) {
        this.code = code;
        this.message = message;
        this.tag = tag;
    }
}
