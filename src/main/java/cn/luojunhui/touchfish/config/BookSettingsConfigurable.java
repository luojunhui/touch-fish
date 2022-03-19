package cn.luojunhui.touchfish.config;

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.util.NlsContexts;
import com.intellij.util.ExceptionUtil;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

/**
 * 修改配置
 * @author : junhui.luo
 * @version V1.0
 * @date Date : 2020年11月27日
 */
public class BookSettingsConfigurable implements Configurable {
    private static final Logger LOGGER = Logger.getInstance(BookSettingsConfigurable.class);
    private BookSettingsComponent bookSettingsComponent;

    public BookSettingsConfigurable() {
    }

    @Override
    public @NlsContexts.ConfigurableName String getDisplayName() {
        return "Touch Fish";
    }

    /**
     * 创建一个Component用于展示
     * @return bookSettingsComponent
     */
    @Nullable
    @Override
    public JComponent createComponent() {
        if (this.bookSettingsComponent == null) {
            this.bookSettingsComponent = new BookSettingsComponent();
            try {
                this.bookSettingsComponent.init();
            }catch (Exception e){
                String errInfo = "设置面板出现错误:\n" + ExceptionUtil.currentStackTrace();
                LOGGER.error(errInfo,e);
            }
        }
        return this.bookSettingsComponent.getPanel();
    }

    /**
     * IDEA 初始化设置页面时，判断 "Apply" 按钮是否为可用<br/>
     * 存在条件修改返回true
     * @return true 是；false 否
     */
    @Override
    public boolean isModified() {
        if (this.bookSettingsComponent == null) {
            return false;
        }
        BookSettingsState settings = BookSettingsState.getInstance();
        String inputFilePath = this.bookSettingsComponent.getBookPath();
        int inputPage = this.bookSettingsComponent.getPage();
        int inputPageSize = this.bookSettingsComponent.getPageSize();
        return !StringUtils.equals(settings.getBookPath().trim(), inputFilePath)
                || settings.getPage() != inputPage
                || settings.getPageSize() != inputPageSize;
    }

    /**
     * 用户点击 "Apply" 按钮或 "OK" 按钮之后，会调用此方法
     */
    @Override
    public void apply() throws ConfigurationException {
        if (null == this.bookSettingsComponent) {
            return;
        }
        BookSettingsState settings = BookSettingsState.getInstance();
        if (settings == null) {
            String errInfo = "Touch Fish 工具设置对象为空,请查看idea.log查询更多信息";
            LOGGER.error(errInfo);
            throw new ConfigurationException(errInfo);
        }
        settings.setBookPath(this.bookSettingsComponent.getBookPath());
        settings.setPage(this.bookSettingsComponent.getPage());
        settings.setPageSize(this.bookSettingsComponent.getPageSize());
        // 更新文本内容
        List<String> lines;
        try {
            lines = Files.readAllLines(Paths.get(settings.getBookPath()));
            int totalPage = (lines.size() + settings.getPageSize() - 1) / settings.getPageSize();
            settings.setTotalPage(totalPage);
            settings.setLines(lines);
        } catch (IOException e) {
            throw new ConfigurationException("读取文件失败!");
        }
    }

    /**
     * 重置值
     */
    @Override
    public void reset() {
        BookSettingsState settings = BookSettingsState.getInstance().getState();
        String bookPath = Optional.of(settings).map(s->s.getBookPath()).orElse("");
        int page = Optional.of(settings).map(s->s.getPage()).orElse(1);
        int pageSize = Optional.of(settings).map(s->s.getPageSize()).orElse(5);
        this.bookSettingsComponent.setBookPath(bookPath);
        this.bookSettingsComponent.setPage(page);
        this.bookSettingsComponent.setPageSize(pageSize);
    }

    /**
     * IDEA 销毁设置页面后，会调用此方法
     */
    @Override
    public void disposeUIResources() {
        this.bookSettingsComponent = null;
    }
}
