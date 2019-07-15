package cn.com.yangzhenyu.iconfont.view;

import javax.swing.*;
import java.awt.*;

/**
 * 自定义的按钮
 * Created with IntelliJ IDEA
 * Created By 杨振宇
 * Date: 2019/4/25
 * Time: 14:20
 */
public class YButton extends JButton {

    public YButton(String text) {
        this.setText(text);
        Font f = new Font("微软雅黑", Font.PLAIN, 14);
        this.setFont(f);
        this.setBackground(Color.white);
        
    }

    public YButton(String text, ButtonClick click) {
        this(text);
        this.addActionListener(click::onClick);
    }

    public void setClick(ButtonClick click) {
        this.addActionListener(click::onClick);
    }
}
