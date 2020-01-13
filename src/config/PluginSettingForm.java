package config;

import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory;
import com.intellij.openapi.ui.TextFieldWithBrowseButton;
import com.intellij.openapi.util.text.StringUtil;

import javax.swing.*;
import java.io.File;

/**
 * PluginSettingForm.class
 * 配置页的表单
 *
 * @author junhui
 */
public class PluginSettingForm {
    private JPanel settingPanel;
    private TextFieldWithBrowseButton chooseFileBtn;
    private JTextField pageSizeTextField;
    private JTextField pageTextField;
    private JLabel filePath;
    private JLabel pageSize;
    private JLabel curPage;

    public PluginSettingForm() {
        this.init();
    }

    public JPanel getSettingPanel() {
        return this.settingPanel;
    }

    /**
     * 读取xml获取历史配置
     */
    private void init() {
        //按钮绑定事件
        this.chooseFileBtn.addBrowseFolderListener("选择文件", null, null,
                FileChooserDescriptorFactory.createSingleFileDescriptor("txt"));


        Config config = ConfigService.getInstance().getState();
        String bookPath = config.getBookPath().trim();
        if (StringUtil.isNotEmpty(bookPath)) {
            this.chooseFileBtn.getTextField().setText(bookPath);
            this.pageSizeTextField.setText(String.valueOf(config.getPageSize()));
            this.pageTextField.setText(String.valueOf(config.getPage()));
        }
    }

    private void openFileAndSetPath(int selectedMode, Boolean isSupportMultiSelect) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(selectedMode);
        fileChooser.setMultiSelectionEnabled(isSupportMultiSelect);
        fileChooser.showOpenDialog(null);

        File chooseFile = fileChooser.getSelectedFile();
        String bookPath = chooseFile.getAbsolutePath();
        this.chooseFileBtn.getTextField().setText(bookPath);

    }


    public String getBookPath() {
        return this.chooseFileBtn.getTextField().getText();
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
        this.chooseFileBtn.getTextField().setText(s);
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
