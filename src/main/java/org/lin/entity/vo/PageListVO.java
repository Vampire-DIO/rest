package org.lin.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author LvWei
 * @Date 2024/3/13 16:57
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageListVO<T> {
    private List<T> data;

    private long currentPage;

    private long total;

    private long pageSize;

    private long totalPage;
}
