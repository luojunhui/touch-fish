package cn.luojunhui.touchfish.config;

import com.intellij.util.xmlb.annotations.OptionTag;

import java.util.List;

/**
 * Config.class
 * 配置类
 *
 * @author junhui
 */
public class Config {
    @OptionTag
    private String bookPath;
    @OptionTag
    private Integer page = 1;
    @OptionTag
    private Integer pageSize = 3;
    @OptionTag
    private Integer totalPage = 0;

    @OptionTag
    private List<String> lines;


    public Config() {
    }

    public String getBookPath() {
        return bookPath;
    }

    public void setBookPath(String bookPath) {
        this.bookPath = bookPath;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public List<String> getLines() {
        return lines;
    }

    public void setLines(List<String> lines) {
        this.lines = lines;
    }
}
