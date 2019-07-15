package cn.com.yangzhenyu.iconfont.frame;


import cn.com.yangzhenyu.iconfont.config.Config;
import cn.com.yangzhenyu.iconfont.core.DownloadTools;
import cn.com.yangzhenyu.iconfont.core.DownloadToolsImpl;
import cn.com.yangzhenyu.iconfont.core.MyHandler;
import cn.com.yangzhenyu.iconfont.core.MyMessage;
import cn.com.yangzhenyu.iconfont.utils.StringUtils;
import cn.com.yangzhenyu.iconfont.view.InputPanel;
import cn.com.yangzhenyu.iconfont.view.YButton;

import javax.swing.*;
import java.awt.*;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;

/**
 * Created with IntelliJ IDEA
 * Created By 杨振宇
 * Date: 2019/4/26
 * Time: 14:02
 */
class ToolsPanel extends JPanel {

    private InputPanel fileName = null;
    private JTextArea textBox = null;
    private boolean flag = false;
    private DownloadTools downloadTools = new DownloadToolsImpl();
    private Handler handler = new MyHandler() {
        @Override
        public void publish(LogRecord record) {
            if (textBox != null && record.getLevel() == Level.ALL) {
                textBox.append("[" + StringUtils.now() + "] " + record.getMessage());
                textBox.append("\n");
            }
        }
    };

    ToolsPanel() {
        initView();
        initData();
    }

    private void initData() {
        try {
            handler.publish(new MyMessage("开始初始化"));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Git地址不正确 请重新填写", "", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void initView() {
        // 样式
        this.setLayout(new BorderLayout());
        this.setBackground(Color.white);

        // 标题按钮
        this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        fileName = new InputPanel("输入网页ID");

        fileName.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // 文本域
        textBox = new JTextArea();
        textBox.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // 文本域
        JScrollPane sp = new JScrollPane(textBox);
        sp.setBackground(Color.white);
        sp.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        this.add(sp, BorderLayout.CENTER);


        JPanel btnBox = new JPanel();

        btnBox.setBackground(Color.white);

        btnBox.setLayout(new FlowLayout(FlowLayout.CENTER));
//        btnBox.add(fileName);

        btnBox.add(new YButton("下载", e -> {
            if (flag) return;
            if ("".equals(Config.getString("cookies",""))) {
                JOptionPane.showMessageDialog(null, "请填写相关设置(cookies)", "", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            flag = true;
            handler.publish(new MyMessage("开始下载"));
            new Thread(() -> {
                try {
                    // 初始化
                    downloadTools.downloadIcons(fileName.getValue(),handler);
                    handler.publish(new MyMessage("下载完成"));
                } catch (Exception ex) {
                    handler.publish(new MyMessage(ex.getMessage()));
                }
                flag = false;
            }).start();
        }));

        this.add(fileName,BorderLayout.PAGE_START);
        this.add(btnBox, BorderLayout.LINE_START);
        this.add(sp,BorderLayout.CENTER);

    }

}
