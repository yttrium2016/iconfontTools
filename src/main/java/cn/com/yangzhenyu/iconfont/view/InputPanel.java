package cn.com.yangzhenyu.iconfont.view;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

/**
 * Created with IntelliJ IDEA
 * Created By 杨振宇
 * Date: 2019/4/26
 * Time: 14:36
 */
public class InputPanel extends JPanel {

    private JTextField text = null;
    private boolean isPassword = false;

    public InputPanel(String title) {
        initView(title);
    }

    public InputPanel(String title, boolean isPassword) {
        this.isPassword = isPassword;
        initView(title);
    }

    private void initView(String title) {
        this.setBackground(Color.white);
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        JLabel label = new JLabel(title);
        Border border = BorderFactory.createEmptyBorder(5, 5, 5, 5);
        label.setBorder(border);
        Font f = new Font("微软雅黑", Font.PLAIN, 14);
        label.setFont(f);
        this.add(label);

        text = isPassword ? new JPasswordField() : new JTextField();
        text.setBorder(border);
        this.add(text);


    }

    public String getValue() {
        return text.getText();
    }

    public void setText(String str) {
        text.setText(str);
    }
}
