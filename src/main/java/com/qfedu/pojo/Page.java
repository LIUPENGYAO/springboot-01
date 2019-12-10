package com.qfedu.pojo;
import java.util.List;

/**
 * 分页实体类
 *
 * @Author
 */
public class Page {
    private List<Document> result;
    private List<User> userResult;

    private int total; //总条数

    private int pageNum; //需要展示的页码

    private int pageSize; //每页的记录数

    private int startIndex; //由页码和记录数计算出 limit(startIndex,pageSize)中使用

    private int totalPage;//总共多少页 由pageSize以及查询结果的总条数计算出
    /*Map:pageNum pageSize,startIndex*/
    /*1   3   0
      2   3   3
      3   3   6
     */
    public Page(int pageNum, int pageSize) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.startIndex = (this.pageNum - 1) * this.pageSize;
    }

    public List<Document> getResult() {
        return result;
    }

    public void setResult(List<Document> result) {
        this.result = result;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.totalPage = total % pageSize == 0 ? total / pageSize : total / pageSize + 1;
        this.total = total;
    }

    public int getPageNum() {
        return pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public List<User> getUserResult() {
        return userResult;
    }

    public void setUserResult(List<User> userResult) {
        this.userResult = userResult;
    }
}
