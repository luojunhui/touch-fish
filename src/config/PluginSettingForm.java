package config;

import com.intellij.openapi.util.text.StringUtil;

import javax.swing.*;

/**
 * PluginSettingForm.class
 * 配置页的表单
 * @author junhui
 */
public class PluginSettingForm {
    private JPanel pluginSettingPanel;
    private JTextField pathField;
    private JTextField lineField;
    private JTextField rowCountField;

    public JPanel getPluginSettingPanel() {
        return this.pluginSettingPanel;
    }

    /**
     * 读取xml获取历史配置
     */
    private void createUIComponents() {
        Config config = ConfigService.getInstance().getState();
        String bookPath = config.getBookPath();
        if (StringUtil.isNotEmpty(bookPath)) {
            this.pathField.setText(bookPath);
            this.lineField.setText(String.valueOf(config.getLine()));
            this.rowCountField.setText(String.valueOf(config.getRowCount()));
        }
    }

    public String getBookPath() {
        return this.pathField.getText();
    }

    public int getLine() {
        return Integer.valueOf(this.lineField.getText());
    }

    public void setBookPath(String s) {
        this.pathField.setText(s);
    }

    public void setLine(int line) {
        this.lineField.setText(String.valueOf(line));
    }

    public int getRowCount(){
        return Integer.valueOf(this.rowCountField.getText());
    }

    public void setRowCount(int line) {
        this.rowCountField.setText(String.valueOf(line));
    }
}
