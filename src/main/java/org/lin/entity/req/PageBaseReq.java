package org.lin.entity.req;

import lombok.Data;

/**
 * @Author LvWei
 * @Date 2024/3/13 17:05
 */
@Data
public class PageBaseReq {
    private int page = 0;

    private int pageSize = 10;
}
