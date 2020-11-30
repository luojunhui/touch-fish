package cn.luojunhui.touchfish.config;

import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.util.NlsContexts;
import com.intellij.openapi.wm.ToolWindowManager;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * 修改配置
 * @author : junhui.luo
 * @version V1.0
 * @date Date : 2020年11月27日
 */
public class BookSettingsConfigurable implements Configurable {
    private BookSettingsComponent bookSettingsComponent;

    public BookSettingsConfigurable() {
    }

    @Override
    public @NlsContexts.ConfigurableName String getDisplayName() {
        return "Touch Fish";
    }

    /**
     * 创建一个Component用于展示
     * @return
     */
    @Nullable
    @Override
    public JComponent createComponent() {
        if (this.bookSettingsComponent == null) {
            this.bookSettingsComponent = new BookSettingsComponent();
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
                || settings.getPage().intValue() != inputPage
                || settings.getPageSize().intValue() != inputPageSize;
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
        settings.setBookPath(this.bookSettingsComponent.getBookPath());
        settings.setPage(this.bookSettingsComponent.getPage());
        settings.setPageSize(this.bookSettingsComponent.getPageSize());
        // 更新文本内容
        List<String> lines = null;
        try {
            lines = Files.readAllLines(Paths.get(settings.getBookPath()));
            int totalPage = (lines.size() + settings.getPageSize() - 1) / settings.getPageSize();
            settings.setTotalPage(totalPage);
            settings.setLines(lines);
        } catch (IOException e) {
        }
    }

    /**
     * 重置值
     */
    @Override
    public void reset() {
        BookSettingsState settings = BookSettingsState.getInstance().getState();
        this.bookSettingsComponent.setBookPath(settings.getBookPath());
        this.bookSettingsComponent.setPage(settings.getPage());
        this.bookSettingsComponent.setPageSize(settings.getPageSize());
    }

    /**
     * IDEA 销毁设置页面后，会调用此方法
     */
    @Override
    public void disposeUIResources() {
        this.bookSettingsComponent = null;
    }
}
