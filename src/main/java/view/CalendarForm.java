package view;

import controller.CalendarListener;
import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class CalendarForm {

    private MainFrame mainFrame;
    private AddForm addForm;
    private JFrame calendarForm;
    private JPanel startTimePanel;
    private JPanel endTimePanel;
    private JComboBox startYear;
    private JComboBox startMonth;
    private JComboBox startDay;
    private  JComboBox endYear;
    private JComboBox endMonth;
    private JComboBox endDay;
    private JTextArea textArea;
    private JScrollPane scrollPane;
    private JButton tasksViewButton;
    private  JButton cancelViewButton;

    public CalendarForm(MainFrame mainFrame, AddForm addForm){
        this.mainFrame = mainFrame;
        this.addForm = addForm;

        UIManager.put("ComboBox.disabledBackground", mainFrame.getBgColor());
        UIManager.put("ComboBox.disabledForeground", mainFrame.getBgColor());

        calendarForm = new JFrame("Календарь событий за период");
        calendarForm.getContentPane().setLayout(new FlowLayout());
        calendarForm.getContentPane().setBackground(mainFrame.getBgColor());
        calendarForm.setSize(500,345);
        calendarForm.setLocationRelativeTo(null);
        calendarForm.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        calendarForm.setResizable(false);

        ImageIcon smallIcon = new ImageIcon("resources/calendarImage.png");
        calendarForm.setIconImage(smallIcon.getImage());

        startTimePanel = new JPanel();
        startTimePanel.setOpaque(true);
        panelConfig (startTimePanel,"Начало", mainFrame.getTextColor(),mainFrame.getMainForm());
        startTimePanel.setBackground(mainFrame.getBgColor());

        startYear = new JComboBox(addForm.getYear());
        startMonth = new JComboBox(addForm.getMonth());
        startDay = new JComboBox(addForm.getDay());
        endYear = new JComboBox(addForm.getYear());
        endMonth = new JComboBox(addForm.getMonth());
        endDay = new JComboBox(addForm.getDay());

        borderConfig(startDay,"День", mainFrame.getTextColor(),mainFrame.getMainForm());
        borderConfig(startMonth,"Месяц", mainFrame.getTextColor(),mainFrame.getMainForm());
        borderConfig(startYear,"Год", mainFrame.getTextColor(),mainFrame.getMainForm());

        startDay.setBackground(mainFrame.getBgColor());
        startMonth.setBackground(mainFrame.getBgColor());
        startYear.setBackground(mainFrame.getBgColor());

        startDay.setForeground(mainFrame.getTextColor());
        startMonth.setForeground(mainFrame.getTextColor());
        startYear.setForeground(mainFrame.getTextColor());

        startTimePanel.add(startDay);
        startTimePanel.add(startMonth);
        startTimePanel.add(startYear);

        endTimePanel = new JPanel();
        endTimePanel.setOpaque(true);

        panelConfig (endTimePanel,"Окончание", mainFrame.getTextColor(),mainFrame.getMainForm());

        endTimePanel.setBackground(mainFrame.getBgColor());

        borderConfig(endDay,"День", mainFrame.getTextColor(),mainFrame.getMainForm());
        borderConfig(endMonth,"Месяц",  mainFrame.getTextColor(),mainFrame.getMainForm());
        borderConfig(endYear,"Год",  mainFrame.getTextColor(),mainFrame.getMainForm());

        endDay.setBackground(mainFrame.getBgColor());
        endMonth.setBackground(mainFrame.getBgColor());
        endYear.setBackground(mainFrame.getBgColor());

        endDay.setForeground(mainFrame.getTextColor());
        endMonth.setForeground(mainFrame.getTextColor());
        endYear.setForeground(mainFrame.getTextColor());

        endTimePanel.add(endDay);
        endTimePanel.add(endMonth);
        endTimePanel.add(endYear);

        textArea = new JTextArea( 10, 43);
        textArea.setTabSize(10);
        textArea.setBackground(mainFrame.getBgColor());
        textArea.setForeground(mainFrame.getTextColor());
        textArea.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(
                EtchedBorder.LOWERED, Color.GRAY
                , Color.DARK_GRAY), "Список задач",TitledBorder.CENTER, TitledBorder.TOP,
                mainFrame.getMainForm().getFont(),mainFrame.getTextColor()));

        scrollPane = new JScrollPane(textArea);
        scrollPane.setBorder(null);

        textArea.setEditable(false);

        tasksViewButton = new JButton("Просмотреть");
        cancelViewButton = new JButton("Отмена");

        tasksViewButton.setActionCommand("viewTasks");
        cancelViewButton.setActionCommand("cancelView");

        tasksViewButton.setBackground(mainFrame.getBtnColor());
        tasksViewButton.setBorderPainted(false);

        cancelViewButton.setBackground(mainFrame.getBtnColor());
        cancelViewButton.setBorderPainted(false);

        CalendarListener calendarListener = new CalendarListener(this, mainFrame);

        tasksViewButton.addActionListener(calendarListener);
        cancelViewButton.addActionListener(calendarListener);

        calendarForm.getContentPane().add(startTimePanel);
        calendarForm.getContentPane().add(endTimePanel);
        calendarForm.getContentPane().add(scrollPane);
        calendarForm.getContentPane().add(tasksViewButton);
        calendarForm.getContentPane().add(cancelViewButton);
        calendarForm.setVisible(false);
    }

    private void borderConfig(JComboBox comboBox, String title, Color color, JFrame mainForm ){
        comboBox.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(
                EtchedBorder.LOWERED, Color.GRAY
                , Color.DARK_GRAY),title, TitledBorder.LEFT, TitledBorder.TOP, mainForm.getFont(), color));
    }
    private void panelConfig(JPanel panel, String title, Color color, JFrame mainForm ){
        panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(
                EtchedBorder.LOWERED, Color.GRAY
                , Color.DARK_GRAY),title, TitledBorder.LEFT, TitledBorder.TOP, mainForm.getFont(), color));
    }

    public JComboBox getStartYear() {
        return startYear;
    }

    public JComboBox getStartMonth() {
        return startMonth;
    }

    public JComboBox getStartDay() {
        return startDay;
    }

    public JComboBox getEndYear() {
        return endYear;
    }

    public JComboBox getEndMonth() {
        return endMonth;
    }

    public JComboBox getEndDay() {
        return endDay;
    }

    public JFrame getCalendarForm() {
        return calendarForm;
    }

    public JTextArea getTextArea() {
        return textArea;
    }
}
