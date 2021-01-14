

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.List;

public class MainFrame extends JFrame implements ActionListener {
    JButton startBtn;
    JButton stopBtn;
    JLabel statusLbl;
    Clicker clicker = new Clicker();
    ClickTask clickTask;


    public MainFrame() {
        Container container = getContentPane();
        setTitle("AutoClicker");

        JPanel northPnl = new JPanel();
        JPanel southPnl = new JPanel(new FlowLayout(FlowLayout.CENTER));

        JLabel lbl = new JLabel("5 Seconds Delay to Start");
        lbl.setForeground(Color.GRAY);
        statusLbl = new JLabel("Press [ESC] to exit", SwingConstants.CENTER);
        startBtn = new JButton("START");
        stopBtn = new JButton("STOP");
        startBtn.addActionListener(this);
        stopBtn.addActionListener(this);

        northPnl.add(lbl);
        southPnl.add(startBtn);
        southPnl.add(stopBtn);
        setKeyStroke();


        container.add(northPnl, BorderLayout.NORTH);
        container.add(statusLbl, BorderLayout.CENTER);
        container.add(southPnl, BorderLayout.SOUTH);


        setBounds(10, 10, 400, 300);
        setResizable(false);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);


    }


    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();


        boolean startMode = src == startBtn;


        //  if not "Start" then "Stop"
        startBtn.setEnabled(!startMode);
        stopBtn.setEnabled(startMode);

        if (startMode) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException exception) {
                exception.printStackTrace();
            }


            clickTask = new ClickTask();
            clickTask.execute();


        } else {
            clickTask.cancel(true);
            clickTask = null;
        }


    }


    private class ClickTask extends SwingWorker<Void, Integer> {


        @Override
        protected Void doInBackground() throws Exception {
            int clickCount = 0;


            while (!isCancelled()) {

                clicker.clickButton(KeyEvent.BUTTON1_DOWN_MASK);
                clickCount++;
                publish(clickCount);

            }
            return null;
        }

        @Override
        protected void process(List<Integer> chunks) {
            int counter = chunks.get(chunks.size() - 1);
            statusLbl.setText("Clicked " + counter + " times");
        }
    }


    private void setKeyStroke() {
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "Exit");
        getRootPane().getActionMap().put("Exit", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

    }

}
