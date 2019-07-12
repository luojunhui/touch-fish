package config;

import com.intellij.util.xmlb.annotations.OptionTag;

import java.util.List;

/**
 * Config.class
 * 配置类
 * @author junhui
 */
public class Config {
    @OptionTag
    private String bookPath;
    @OptionTag
    private Integer line = 0;
    @OptionTag
    private Integer rowCount = 0;

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

    public Integer getLine() {
        return line;
    }

    public void setLine(Integer line) {
        this.line = line;
    }

    public Integer getRowCount() {
        return rowCount;
    }

    public void setRowCount(Integer rowCount) {
        this.rowCount = rowCount;
    }

    public List<String> getLines() {
        return lines;
    }

    public void setLines(List<String> lines) {
        this.lines = lines;
    }
}
