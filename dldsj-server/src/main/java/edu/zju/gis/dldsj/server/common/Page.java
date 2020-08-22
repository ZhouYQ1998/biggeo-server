package edu.zju.gis.dldsj.server.common;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * 分页查询结果
 * 二次封装PageHelper库中的Page
 *
 * @param <T>
 */
@Getter
@Setter
@ToString
public class Page<T> {

    private static final int DEFAULT_START = 1;

    private static final int DEFAULT_SIZE = 20;

    private static final int MAX_SIZE = 200;

    private Integer pageNo;

    private Integer pageSize;

    private Integer totalPage;

    private Long totalCount;

    private List<T> result;

    public Page() {
        this.pageNo = DEFAULT_START;
        this.pageSize = DEFAULT_SIZE;
    }

    public Page(Integer pageNo, Integer pageSize) {
        this.pageNo = pageNo > 0 ? pageNo : DEFAULT_START;
        this.pageSize = pageSize > 0 && pageSize < MAX_SIZE ? pageSize : DEFAULT_SIZE;
    }

    public Page(Integer totalPage, Long totalCount, List<T> result) {
        this.totalPage = totalPage;
        this.totalCount = totalCount;
        this.result = result;
    }

    public Page(Integer pageNo, Integer pageSize, List<T> result) {
        this.pageNo = pageNo > 0 ? pageNo : DEFAULT_START;
        this.pageSize = pageSize > 0 && pageSize < MAX_SIZE ? pageSize : DEFAULT_SIZE;
        this.result = result;
        this.totalCount = (long) result.size();
        this.totalPage = Math.toIntExact(totalCount % pageSize == 0 ? totalCount / pageSize : totalCount / pageSize + 1);
    }

    public Page(Integer pageNo, Integer pageSize, Integer totalPage, Long totalCount, List<T> result) {
        this.pageNo = pageNo > 0 ? pageNo : DEFAULT_START;
        this.pageSize = pageSize > 0 && pageSize < MAX_SIZE ? pageSize : DEFAULT_SIZE;
        this.totalPage = totalPage;
        this.totalCount = totalCount;
        this.result = result;
    }

    public Page(List<T> list) {
        if (list instanceof com.github.pagehelper.Page) {
            com.github.pagehelper.Page page = (com.github.pagehelper.Page) list;
            this.totalCount = page.getTotal();
            this.totalPage = page.getPages();
            this.pageNo = page.getPageNum();
            this.pageSize = page.getPageSize();
            this.result = list;
        } else {
            throw new UnsupportedOperationException();
        }
    }

    public Integer getOffset() {
        return this.pageSize * (this.pageNo-1);
    }
}

