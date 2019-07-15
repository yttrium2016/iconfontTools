package cn.com.yangzhenyu.iconfont.frame;


import cn.com.yangzhenyu.iconfont.config.Config;
import cn.com.yangzhenyu.iconfont.view.InputPanel;
import cn.com.yangzhenyu.iconfont.view.YButton;

import javax.swing.*;
import java.awt.*;

/**
 * Created with IntelliJ IDEA
 * Created By 杨振宇
 * Date: 2019/4/26
 * Time: 14:02
 */
public class SettingPanel extends JPanel {

    private InputPanel cookies = null;
    private InputPanel type = null;
    private InputPanel color = null;
    private InputPanel size = null;

    public SettingPanel() {
        initView();
        initData();
    }

    private void initData() {
        this.cookies.setText(Config.getString("cookies", ""));
        this.type.setText(Config.getString("type", "png"));
        this.color.setText(Config.getString("color", "333333"));
        this.size.setText(Config.getString("size", "200"));
    }

    private void initView() {
        this.setBackground(Color.white);
        this.setLayout(new GridLayout(-1, 1));
        this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        cookies = new InputPanel("cookies:");
        type = new InputPanel("下载类型(png,svg,eps):");
        color = new InputPanel("color:");
        size = new InputPanel("size:");

        this.add(cookies);
        this.add(type);
        this.add(color);
        this.add(size);
        this.add(new YButton("保存", e -> {
            Config.putString("cookies", cookies.getValue());
            Config.putString("type", type.getValue());
            Config.putString("color", color.getValue());
            Config.putString("size", size.getValue());
            JOptionPane.showMessageDialog(null, "保存成功", "", JOptionPane.INFORMATION_MESSAGE);
        }));

    }

}
