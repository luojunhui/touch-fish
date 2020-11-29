package cn.luojunhui.touchfish.config;

import com.intellij.ide.ui.UINumericRange;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.options.SearchableConfigurable;
import com.intellij.openapi.ui.ComboBox;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * BookConfigurable.class
 *
 * @author junhui
 */
public class BookConfigurable implements SearchableConfigurable {

    private PluginSettingForm form;

    @NotNull
    @Override
    public String getId() {
        return "Touch Fish";
    }

    @Nullable
    @Override
    public Runnable enableSearch(String option) {
        return null;
    }

    @NotNull
    @Override
    public Class<?> getOriginalClass() {
        return null;
    }

    @Nls(capitalization = Nls.Capitalization.Title)
    @Override
    public String getDisplayName() {
        return "Touch Fish";
    }

    @Nullable
    @Override
    public String getHelpTopic() {
        return null;
    }

    @Override
    public boolean isModified(@NotNull JTextField textField, @NotNull String value) {
        return false;
    }

    @Override
    public boolean isModified(@NotNull JTextField textField, int value, @NotNull UINumericRange range) {
        return false;
    }

    @Override
    public boolean isModified(@NotNull JToggleButton toggleButton, boolean value) {
        return false;
    }

    @Override
    public <T> boolean isModified(@NotNull ComboBox<T> comboBox, T value) {
        return false;
    }

    @Override
    public JComponent getPreferredFocusedComponent() {
        return null;
    }

    @Nullable
    @Override
    public JComponent createComponent() {
        if (this.form == null) {
            this.form = new PluginSettingForm();
        }
        return this.form.getSettingPanel();
    }

    /**
     * IDEA 初始化设置页面时，判断 "Apply" 按钮是否为可用
     *
     * @return true 是；false 否
     */
    @Override
    public boolean isModified() {
        return this.form != null;
    }

    /**
     * 用户点击 "Apply" 按钮或 "OK" 按钮之后，会调用此方法
     */
    @Override
    public void apply() throws ConfigurationException {
        if (this.form != null) {
            Config config = ConfigService.getInstance().getState();
            config.setBookPath(this.form.getBookPath());
            config.setPage(this.form.getPage());
            config.setPageSize(this.form.getPageSize());
            // 更新文本内容
            List<String> lines = null;
            try {
                lines = Files.readAllLines(Paths.get(config.getBookPath()));
                int totalPage = (lines.size() + config.getPageSize() - 1) / config.getPageSize();
                config.setTotalPage(totalPage);
            } catch (IOException e) {
            }
            config.setLines(lines);
            ConfigService.getInstance().setState(config);
        }
    }

    @Override
    public void reset() {
        Config config = ConfigService.getInstance().getState();
        this.form.setBookPath(config.getBookPath());
        this.form.setPage(config.getPage());
        this.form.setPageSize(config.getPageSize());
    }

    /**
     * IDEA 销毁设置页面后，会调用此方法
     */
    @Override
    public void disposeUIResources() {
        this.form = null;
    }
}
