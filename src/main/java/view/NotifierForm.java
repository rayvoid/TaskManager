package view;

import javax.swing.*;
import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

public class NotifierForm {

    private JFrame notifierForm;

    public NotifierForm(String message, MainFrame mainFrame){

        String thisMessage;
        if (message.length() > 40) thisMessage = message.substring(0,40) + "...";
        else thisMessage = message;

        notifierForm = new JFrame("Напоминание");
        notifierForm.getContentPane().setLayout(new FlowLayout());
        notifierForm.getContentPane().setBackground(mainFrame.getBgColor());
        notifierForm.setSize(400,105);
        notifierForm.setLocationRelativeTo(null);
        notifierForm.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        notifierForm.setResizable(false);
        ImageIcon smallIcon = new ImageIcon("resources/notifierImage.png");
        notifierForm.setIconImage(smallIcon.getImage());

        JLabel textMessage = new JLabel(thisMessage);
        textMessage.setForeground(mainFrame.getTextColor());
        textMessage.setPreferredSize(new Dimension(300,60));

        JLabel alarmImg = new JLabel();
        alarmImg.setVerticalAlignment(JLabel.CENTER);
        alarmImg.setIcon(new ImageIcon("resources/alarmImg.png"));

        notifierForm.getContentPane().add(alarmImg);
        notifierForm.getContentPane().add(textMessage);
        timeToView();
        notifierForm.setVisible(true);
        notifierForm.toFront();

    }

   private void timeToView(){
       Timer timer = new Timer();
       timer.schedule(new TimerTask() {
           @Override
           public void run() {
               setVisible();
           }
       }, 10*1000);
   }

   private void setVisible(){
        notifierForm.setVisible(false);
   }
}
