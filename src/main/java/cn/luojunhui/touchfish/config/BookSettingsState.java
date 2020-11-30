package cn.luojunhui.touchfish.config;

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.util.xmlb.XmlSerializerUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * 持久存储自定义设置
 * https://jetbrains.org/intellij/sdk/docs/tutorials/settings_tutorial.html#declaring-appsettingsstate
 *
 * @author : junhui.luo
 * @version V1.0
 * @date Date : 2020年11月27日
 */
@State(
        name = "cn.luojunhui.touchfish.config.BookSettingsState",
        storages = {@Storage("TouchFishSettingsPlugin.xml")}
)
public class BookSettingsState implements PersistentStateComponent<BookSettingsState> {
    /**
     * txt文本的绝对路径
     */
    private String bookPath = "";
    /**
     * 当前页码
     */
    private Integer page = 1;
    /**
     * 每页展示多少行文字
     */
    private Integer pageSize = 3;
    /**
     * txt文本按pageSize分页的总页码
     */
    private Integer totalPage = 0;
    /**
     * txt内容，当文本内容过大时，会占用很多内存，建议分割成多个txt文件
     */
    private List<String> lines = new ArrayList<>();

    /**
     * 获取配置实例
     *
     * @return
     */
    public static BookSettingsState getInstance() {
        return ServiceManager.getService(BookSettingsState.class);
    }

    @Nullable
    @Override
    public BookSettingsState getState() {
        return this;
    }

    @Override
    public void loadState(@NotNull BookSettingsState appSettingsState) {
        XmlSerializerUtil.copyBean(appSettingsState, this);
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
