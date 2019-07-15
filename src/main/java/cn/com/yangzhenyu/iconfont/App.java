package cn.com.yangzhenyu.iconfont;

import cn.com.yangzhenyu.iconfont.frame.MainFrame;

import javax.swing.*;
import java.awt.*;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        }
        Frame frame = new MainFrame();
        frame.setVisible(true);
    }
}
