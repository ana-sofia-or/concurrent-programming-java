import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.concurrent.ScheduledExecutorService;

public class GUI
{
    private ScheduledExecutorService scheduler;
    private final JTextArea alertsArea;

    public GUI(ScheduledExecutorService scheduler)
    {
        JFrame windowFrame = new JFrame("Resource Monitor GUI");
        JButton btnExit = new JButton("Quit");
        btnExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                scheduler.shutdown();
                windowFrame.dispose();
                Main.isRunning = false;

            }
        });
        windowFrame.add(btnExit,BorderLayout.SOUTH);
        windowFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        windowFrame.setResizable(false);
        alertsArea = new JTextArea();
        alertsArea.setBounds(0,0, 500,500);
        alertsArea.setMargin(new Insets(10, 10, 10, 10));
        alertsArea.setLineWrap(true);
        alertsArea.setWrapStyleWord(true);
        alertsArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(alertsArea);
        windowFrame.add(scrollPane);
        windowFrame.setSize(500, 500);
        windowFrame.setBackground(Color.pink);
        windowFrame.setVisible(true);
    }

    public void addAlert(String alert)
    {
        if (!alert.endsWith("\n"))
            alert += "\n";
        alertsArea.append(alert);
    }


    public static GUI newInstance(ScheduledExecutorService scheduler)
    {
        return new GUI(scheduler);
    }

}