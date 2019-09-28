package view;

import controller.AddFormListeners;
import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class AddForm{

    private JFrame mainForm;
    private JFrame addForm;
    private JTextField addTitleTextField;
    private Color bgColor;
    private  Color textColor;
    private Color btnColor;
    private JCheckBox flagIsRepeatable;
    private JPanel startTimePanel;
    private JPanel endTimePanel;
    private JPanel repeatTimePanel;
    private JComboBox startHours;
    private JComboBox startMinutes;
    private JComboBox startYear;
    private JComboBox startMonth;
    private JComboBox startDay;
    private JComboBox endHours;
    private JComboBox endMinutes;
    private JComboBox endYear;
    private JComboBox endMonth;
    private JComboBox endDay;
    private JComboBox repeatTime;
    private JLabel jCheckBoxText;

    private String[] hour = {"00","01","02","03","04","05","06","07","08","09","10","11","12","13","14","15","16","17"
            ,"18","19","20","21","22","23"};

    private String[] minute = {"00","01","02","03","04","05","06","07","08","09","10","11","12","13","14","15","16","17"
            ,"18","19","20","21","22","23","24","25","26","27","28","29","30","31","32","33","34","35"
            ,"36","37","38","39","40","41","42","43","44","45","46","47","48","49","50","51","52","53"
            ,"54","55","56","57","58","59","60"};

    private String[] year = {"2019","2020","2021","2022","2023","2024","2025","2026","2027","2028","2029"};

    private String[] month = {"01","02","03","04","05","06","07","08","09","10","11","12"};

    private String[] day = {"01","02","03","04","05","06","07","08","09","10","11","12","13","14","15","16","17","18"
            ,"19","20","21","22","23","24","25","26","27","28","29","30","31"};

    private String[] repeatTimeValue = {"1 минута","15 минут","30 минут","1 час", "Каждый день в это время",
                                        "Каждую неделю в это время"};

    public AddForm(MainFrame mainFrame){

        this.mainForm = mainFrame.getMainForm();
        this.bgColor = mainFrame.getBgColor();
        this.textColor = mainFrame.getTextColor();
        this.btnColor = mainFrame.getBtnColor();

        UIManager.put("ComboBox.disabledBackground", bgColor);
        UIManager.put("ComboBox.disabledForeground", bgColor);

        addForm = new JFrame("Новая задача");
        addForm.getContentPane().setLayout(new FlowLayout());
        addForm.getContentPane().setBackground(bgColor);
        addForm.setSize(380,345);
        addForm.setLocationRelativeTo(null);
        addForm.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addForm.setResizable(false);

        ImageIcon smallIcon = new ImageIcon("resources/addTitleLabel.png");
        addForm.setIconImage(smallIcon.getImage());

        JLabel addJLabelTitle = new JLabel("Название:");
        JLabel addJLabelEndTime = new JLabel("End Time:");
        addJLabelTitle.setForeground(textColor);
        addJLabelEndTime.setForeground(textColor);

        addTitleTextField = new JTextField(15);

        flagIsRepeatable = new JCheckBox();
        flagIsRepeatable.setBackground(bgColor);
        jCheckBoxText = new JLabel("    Повторять");
        jCheckBoxText.setForeground(textColor);

        JLabel twoDots = new JLabel(":");
        twoDots.setForeground(textColor);
        JLabel secondTwoDots = new JLabel(":");
        secondTwoDots.setForeground(textColor);

        startTimePanel = new JPanel();
        startTimePanel.setOpaque(true);
        panelConfig (startTimePanel,"Дата и время начала", textColor,mainForm);
        startTimePanel.setBackground(bgColor);

        startHours = new JComboBox(hour);
        startMinutes = new JComboBox(minute);
        startYear = new JComboBox(year);
        startMonth = new JComboBox(month);
        startDay = new JComboBox(day);
        endHours = new JComboBox(hour);
        endMinutes = new JComboBox(minute);
        endYear = new JComboBox(year);
        endMonth = new JComboBox(month);
        endDay = new JComboBox(day);

        JLabel freeSpace = new JLabel("          ");

        borderConfig(startHours,"Часы", textColor,mainForm);
        borderConfig(startMinutes,"Минуты", textColor,mainForm);
        borderConfig(startDay,"День", textColor,mainForm);
        borderConfig(startMonth,"Месяц", textColor,mainForm);
        borderConfig(startYear,"Год", textColor,mainForm);

        startHours.setBackground(bgColor);
        startMinutes.setBackground(bgColor);
        startDay.setBackground(bgColor);
        startMonth.setBackground(bgColor);
        startYear.setBackground(bgColor);

        startHours.setForeground(textColor);
        startMinutes.setForeground(textColor);
        startDay.setForeground(textColor);
        startMonth.setForeground(textColor);
        startYear.setForeground(textColor);

        startTimePanel.add(startHours);
        startTimePanel.add(twoDots);
        startTimePanel.add(startMinutes);
        startTimePanel.add(freeSpace);
        startTimePanel.add(startDay);
        startTimePanel.add(startMonth);
        startTimePanel.add(startYear);

        endTimePanel = new JPanel();
        endTimePanel.setOpaque(true);

        panelConfig (endTimePanel,"Дата и время окончания", textColor,mainForm);
        endTimePanel.setBackground(bgColor);
        JLabel secondFreeSpace = new JLabel("          ");

        borderConfig(endHours,"Часы", textColor,mainForm);
        borderConfig(endMinutes,"Минуты", textColor,mainForm);
        borderConfig(endDay,"День", textColor,mainForm);
        borderConfig(endMonth,"Месяц", textColor,mainForm);
        borderConfig(endYear,"Год", textColor,mainForm);

        endHours.setBackground(bgColor);
        endMinutes.setBackground(bgColor);
        endDay.setBackground(bgColor);
        endMonth.setBackground(bgColor);
        endYear.setBackground(bgColor);

        endHours.setForeground(textColor);
        endMinutes.setForeground(textColor);
        endDay.setForeground(textColor);
        endMonth.setForeground(textColor);
        endYear.setForeground(textColor);

        endTimePanel.setEnabled(false);
        endHours.setEnabled(false);
        endMinutes.setEnabled(false);
        endDay.setEnabled(false);
        endMonth.setEnabled(false);
        endYear.setEnabled(false);

        endTimePanel.add(endHours);
        endTimePanel.add(secondTwoDots);
        endTimePanel.add(endMinutes);
        endTimePanel.add(secondFreeSpace);
        endTimePanel.add(endDay);
        endTimePanel.add(endMonth);
        endTimePanel.add(endYear);

        repeatTimePanel = new JPanel();

        panelConfig (repeatTimePanel,"Повторять", textColor,mainForm);

        repeatTimePanel.setBackground(bgColor);
        repeatTimePanel.setPreferredSize(new Dimension(369,61));
        repeatTime = new JComboBox(repeatTimeValue);
        repeatTime.setPreferredSize(new Dimension(320, 23));
        repeatTime.setBackground(bgColor);
        repeatTime.setForeground(textColor);
        repeatTime.setEnabled(false);
        repeatTimePanel.setEnabled(false);
        repeatTimePanel.add(repeatTime);

        JButton confirmAddButton = new JButton("Добавить");
        JButton cancelAddButton = new JButton("Отмена");

        confirmAddButton.setActionCommand("confirmAddButton");
        cancelAddButton.setActionCommand("cancelAddButton");

        confirmAddButton.setBackground(btnColor);
        confirmAddButton.setBorderPainted(false);
        cancelAddButton.setBackground(btnColor);
        cancelAddButton.setBorderPainted(false);

        JLabel longLine = new JLabel();
        longLine.setIcon(new ImageIcon("resources/longLine.png"));

        addForm.getContentPane().add(addJLabelTitle);
        addForm.getContentPane().add(addTitleTextField);
        addForm.getContentPane().add(jCheckBoxText);
        addForm.getContentPane().add(flagIsRepeatable);
        addForm.getContentPane().add(startTimePanel);
        addForm.getContentPane().add(endTimePanel);
        addForm.getContentPane().add(repeatTimePanel);
        addForm.getContentPane().add(longLine);
        addForm.getContentPane().add(confirmAddButton);
        addForm.getContentPane().add(cancelAddButton);

        AddFormListeners addFormListeners = new AddFormListeners(this, mainFrame);
        flagIsRepeatable.addItemListener(addFormListeners);
        confirmAddButton.addActionListener(addFormListeners);
        cancelAddButton.addActionListener(addFormListeners);
        startTimer();
        addForm.setVisible(false);
    }

    private void startTimer(){
        final ScheduledExecutorService ses = Executors.newSingleThreadScheduledExecutor();
        ses.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                if (!addForm.isVisible()){
                    setActualTime();
                }
            }
        }, 0, 1, TimeUnit.SECONDS);
    }

    private void setActualTime(){
        String hourNow = new SimpleDateFormat("HH").format(Calendar.getInstance().getTime());
        String minuteNow = new SimpleDateFormat("mm").format(Calendar.getInstance().getTime());
        String dayNow = new SimpleDateFormat("dd").format(Calendar.getInstance().getTime());
        String monthNow = new SimpleDateFormat("MM").format(Calendar.getInstance().getTime());
        String yearNow = new SimpleDateFormat("yyyy").format(Calendar.getInstance().getTime());

        for (int i = 0; i < hour.length; i++){
            if (hour[i].equals(hourNow)){
                if(hour[i] == "23"){
                    hourNow = "00";
                    break;
                }else {
                    hourNow = hour[i + 1];
                    break;
                }
            }
        }

        startHours.setSelectedItem(hourNow);
        startMinutes.setSelectedItem(minuteNow);
        startDay.setSelectedItem(dayNow);
        startMonth.setSelectedItem(monthNow);
        startYear.setSelectedItem(yearNow);

        endHours.setSelectedItem(hourNow);
        endMinutes.setSelectedItem(minuteNow);
        endDay.setSelectedItem(dayNow);
        endMonth.setSelectedItem(monthNow);
        endYear.setSelectedItem(yearNow);

        addTitleTextField.setText("");
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

    public JFrame getAddForm() {
        return addForm;
    }

    public JTextField getAddTitleTextField() {
        return addTitleTextField;
    }

    public JCheckBox getFlagIsRepetable() {
        return flagIsRepeatable;
    }

    public JPanel getEndTimePanel() {
        return endTimePanel;
    }

    public JPanel getRepeatTimePanel() {
        return repeatTimePanel;
    }

    public JComboBox getStartHours() {
        return startHours;
    }

    public JComboBox getStartMinutes() {
        return startMinutes;
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

    public JComboBox getEndHours() {
        return endHours;
    }

    public JComboBox getEndMinutes() {
        return endMinutes;
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

    public JComboBox getRepeatTime() {
        return repeatTime;
    }

    public String[] getHour() {
        return hour;
    }

    public String[] getMinute() {
        return minute;
    }

    public String[] getYear() {
        return year;
    }

    public String[] getMonth() {
        return month;
    }

    public String[] getDay() {
        return day;
    }

    public String[] getRepeatTimeValue() {
        return repeatTimeValue;
    }
}
