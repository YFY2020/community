package com.nowcoder.community.entity;

/**
 * 封装分页相关得信息
 */
public class Page {

    private int current = 1;    // 当前的页码

    private int limit = 10;   //显示的上限

    private int rows;   //数据总数(用于计算总页数)

    private String path;     //查询路径(用于复用分页链接)

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        //判断当前的页面页码是否合理，防止出现0和负数的情况
        if (current >= 1) {
            this.current = current;
        }
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        //限制limit的范围，1~100之间是合理的
        if (limit >= 1 && limit <= 100) {
            this.limit = limit;
        }
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        //数据的总行数rows>=0是合理的
        if (rows >= 0) {
            this.rows = rows;
        }
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    /**
     * 获取当前页的起始行
     *
     * @return
     */
    public int getOffset() {
        //current * limit - limit（当前页码*每页显示的最大行数等于最后一行的行数，减去每页显示的最大行数等于当前页的起始行）
        return (current - 1) * limit;
    }

    /**
     * 获取总页数
     *
     * @return
     */
    public int getTotal() {
        // rows / limit [+1]   总行数/每页显示的行数等于总页数
        if (rows % limit == 0) {
            return rows / limit;
        } else {
            return rows / limit + 1;
        }
    }

   //分页的框只显示前两页和后两页如：当前页是第5页，则框中只显示34567共5个框
    /**
     * 获取起始页码
     *
     * @return
     */
    public int getFrom() {
        int from = current - 2;
        return from < 1 ? 1 : from;
    }

    /**
     * 获取结束页码
     * @return
     */
    public int getTo() {
        int to = current + 2;
        int total = getTotal();
        return to > total ? total : to;
    }

}
