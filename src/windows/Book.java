package windows;

import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.openapi.wm.ToolWindow;
import config.Config;
import config.ConfigService;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Book.class
 *
 * @author junhui
 */
public class Book {
    private final int PREV = 0;
    private final int NEXT = 1;

    private JPanel book;
    private JTextPane text;

    public Book(ToolWindow toolWindow) {
        this.init();
    }


    private void init() {
        Config config = ConfigService.getInstance().getState();
        if (StringUtil.isNotEmpty(config.getBookPath())) {
            if (config.getLines().size() > config.getLine()) {
                this.readText(NEXT);
            } else {
                this.text.setText("没有更多内容...");
            }
        } else {
            this.text.setText("没有文本文件路径...");
        }

        // 设置键盘监听
        this.text.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent keyEvent) {
            }

            @Override
            public void keyPressed(KeyEvent keyEvent) {
                boolean isNext = keyEvent.getKeyCode() == KeyEvent.VK_DOWN;
                boolean isPrev = keyEvent.getKeyCode() == KeyEvent.VK_UP;
                if (isNext) {
                    readText(NEXT);
                } else if (isPrev) {
                    readText(PREV);
                }
            }

            @Override
            public void keyReleased(KeyEvent keyEvent) {
            }
        });
    }


    private void readText(int op) {
        Config config = ConfigService.getInstance().getState();
        StringBuffer sb = new StringBuffer();
        String curLine = config.getLines().get(config.getLine());
        switch (op) {
            case PREV:
                int tmp = config.getLine() - config.getRowCount();
                if (tmp == 0) {
                    for (int i = 0; i < config.getRowCount(); i++) {
                        String line = config.getLines().get(i);
                        sb.append(line).append("\n");
                    }
                    config.setLine(config.getRowCount());
                } else if (tmp > 0) {
                    for (int i = config.getLine() - config.getRowCount(); i < config.getLine(); i++) {
                        String line = config.getLines().get(i);
                        sb.append(line).append("\n");
                    }
                    config.setLine(config.getLine() - config.getRowCount());
                } else {
                    config.setLine(0);
                    Notifications.Bus.notify(new Notification("", "tips", "不能再往前了...", NotificationType.INFORMATION));
                }
                break;
            case NEXT:
                if (config.getLine() + config.getRowCount() < config.getLines().size()) {
                    for (int i = config.getLine(); i < config.getLine() + config.getRowCount(); i++) {
                        String line = config.getLines().get(i);
                        sb.append(line).append("\n");
                    }
                    config.setLine(config.getLine() + config.getRowCount());
                } else {
                    sb.append(curLine).append("\n");
                    config.setLine(config.getLines().size() - 1);
                    Notifications.Bus.notify(new Notification("", "tips", "没有更多内容了", NotificationType.INFORMATION));
                }
                break;
            default:
                break;
        }
        this.text.setText(sb.toString());
        ConfigService.getInstance().setState(config);
    }

    public JComponent getContent() {
        return this.book;
    }
}
