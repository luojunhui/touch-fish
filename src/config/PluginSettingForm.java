package config;

import com.intellij.openapi.util.text.StringUtil;

import javax.swing.*;

/**
 * PluginSettingForm.class
 * 配置页的表单
 *
 * @author junhui
 */
public class PluginSettingForm {
    private JPanel pluginSettingPanel;
    private JTextField pathField;
    private JTextField pageTextField;
    private JTextField pageSizeTextField;

    public JPanel getPluginSettingPanel() {
        return this.pluginSettingPanel;
    }

    /**
     * 读取xml获取历史配置
     */
    private void createUIComponents() {
        Config config = ConfigService.getInstance().getState();
        String bookPath = config.getBookPath().trim();
        if (StringUtil.isNotEmpty(bookPath)) {
            this.pathField.setText(bookPath);
            this.pageTextField.setText(String.valueOf(config.getPage()));
            this.pageSizeTextField.setText(String.valueOf(config.getPageSize()));
        }
    }

    public String getBookPath() {
        return this.pathField.getText();
    }

    /**
     * 获取设置的行数,不能小于1
     *
     * @return
     */
    public int getPage() {
        int line = Integer.valueOf(this.pageTextField.getText());
        if (line < 1) {
            this.setPage(1);
            return 1;
        }
        return line;
    }

    public void setBookPath(String s) {
        this.pathField.setText(s);
    }

    public void setPage(int line) {
        this.pageTextField.setText(String.valueOf(line));
    }

    /**
     * 获取加载行数,不能小于1
     *
     * @return
     */
    public int getPageSize() {
        int rowCount = Integer.valueOf(this.pageSizeTextField.getText());
        if (rowCount < 1) {
            this.setPageSize(1);
            return 1;
        }
        return rowCount;
    }

    public void setPageSize(int rowCount) {
        this.pageSizeTextField.setText(String.valueOf(rowCount));
    }
}
