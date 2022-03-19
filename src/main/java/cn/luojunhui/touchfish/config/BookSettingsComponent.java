package cn.luojunhui.touchfish.config;

import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory;
import com.intellij.openapi.ui.TextFieldWithBrowseButton;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.ui.components.JBLabel;
import com.intellij.util.ui.FormBuilder;

import javax.swing.*;

/**
 * 配置页的表单
 */
public class BookSettingsComponent {

    private final JPanel settingPanel;
    private final TextFieldWithBrowseButton chooseFileBtn = new TextFieldWithBrowseButton();
    private JTextField pageTextField = new JTextField();
    private JTextField pageSizeTextField = new JTextField();


    public BookSettingsComponent() {
        settingPanel = FormBuilder.createFormBuilder()
                .addLabeledComponent(new JBLabel("文件路径: "), chooseFileBtn, 1, false)
                .addLabeledComponent(new JBLabel("每页行数: "), pageSizeTextField, 1, false)
                .addLabeledComponent(new JBLabel("当前页码: "), pageTextField, 1, false)
                .getPanel();
    }

    /**
     * 表单初始化赋值
     */
    public void init() {
        //按钮绑定事件
        this.chooseFileBtn.addBrowseFolderListener("选择文件", null, null,
                FileChooserDescriptorFactory.createSingleFileDescriptor("txt"));

        BookSettingsState settings = BookSettingsState.getInstance().getState();
        String bookPath = settings.getBookPath();
        if (StringUtil.isNotEmpty(bookPath)) {
            this.setBookPath(bookPath.trim());
            this.setPage(settings.getPage());
            this.setPageSize(settings.getPageSize());
        }
    }

    public JPanel getPanel() {
        return settingPanel;
    }

    public String getBookPath() {
        return chooseFileBtn.getTextField().getText();
    }

    public void setBookPath(String s) {
        this.chooseFileBtn.getTextField().setText(s);
    }

    /**
     * 获取设置的行数,不能小于1
     *
     * @return
     */
    public int getPage() {
        int line = Integer.valueOf(this.pageTextField.getText());
        if (line < 1) {
            this.pageTextField.setText("1");
            return 1;
        }
        return line;
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
