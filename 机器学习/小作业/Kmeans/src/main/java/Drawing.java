import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class Drawing extends JFrame {
    /*声明一系列组件*/
    JButton jButton1 = new JButton("Start");
    JButton jButton2 = new JButton("Step");
    JLabel c_label = new JLabel("Clusters");
    JTextField c_textField = new JTextField("This is buffer for text", 15);
    JPanel jPanel = new JPanel();
    KMeansPanel painting = new KMeansPanel();

    //当前迭代第几次
    int num = 0;

    Drawing() {
        setTitle("K-Means");
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(660, 710);
        c_textField.setText(String.valueOf(painting.K));
        /*Start按钮的监听器*/
        jButton1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                int ks = Integer.parseInt(c_textField.getText());


                painting.K = ks;
                painting.initial();
                painting.repaint();
                jButton2.setText("Step");
                jButton2.setEnabled(true);
            }
        });
        /*Step按钮的监听器*/
        jButton2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {


                painting.assign();

                painting.updateCentroids();


                /*算法终止的话让按钮变灰并提示算法结束*/
                if (painting.stop(num++)) {
                    jButton2.setText("End");
                    jButton2.setEnabled(false);
                }


                painting.repaint();
            }
        });

        jPanel.add(c_label);
        jPanel.add(c_textField);
        jPanel.add(jButton1);
        jPanel.add(jButton2);
        jPanel.setBackground(new Color(125, 125, 125));
        add(BorderLayout.NORTH, jPanel);
        add(BorderLayout.CENTER, painting);
    }
}
