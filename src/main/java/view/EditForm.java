package view;

import controller.EditFormListener;
import model.TaskList;
import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class EditForm {

    private JFrame editForm;
    private TaskList taskList;
    private DefaultListModel model;
    private JTextField addTitleTextField;
    private JCheckBox flagIsRepetable;
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
    private  JComboBox endDay;
    private JComboBox repeatTime;
    private int index;

    public EditForm(MainFrame mainFrame,AddForm addForm){
        this.taskList = mainFrame.getTaskList();
        this.model = mainFrame.getModel();

        UIManager.put("ComboBox.disabledBackground", mainFrame.getBgColor());
        UIManager.put("ComboBox.disabledForeground", mainFrame.getBgColor());

        editForm = new JFrame("Редактирование");
        editForm.getContentPane().setLayout(new FlowLayout());
        editForm.getContentPane().setBackground(mainFrame.getBgColor());
        editForm.setSize(380,345);
        editForm.setLocationRelativeTo(null);
        editForm.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        editForm.setResizable(false);

        ImageIcon smallIcon = new ImageIcon("resources/editTitleLabel.png");
        editForm.setIconImage(smallIcon.getImage());

        JLabel addJLabelTitle = new JLabel("Название:");
        JLabel addJLabelEndTime = new JLabel("End Time:");
        addJLabelTitle.setForeground(mainFrame.getTextColor());
        addJLabelEndTime.setForeground(mainFrame.getTextColor());

        addTitleTextField = new JTextField(15);

        flagIsRepetable = new JCheckBox();
        flagIsRepetable.setBackground(mainFrame.getBgColor());
        JLabel jCheckBoxText = new JLabel("    Повторять");

        jCheckBoxText.setForeground(mainFrame.getTextColor());
        JLabel twoDots = new JLabel(":");
        twoDots.setForeground(mainFrame.getTextColor());
        JLabel secondTwoDots = new JLabel(":");
        secondTwoDots.setForeground(mainFrame.getTextColor());

        JPanel startTimePanel = new JPanel();
        startTimePanel.setOpaque(true);
        panelConfig (startTimePanel,"Дата и время начала", mainFrame.getTextColor(),mainFrame.getMainForm());
        startTimePanel.setBackground(mainFrame.getBgColor());

        startHours = new JComboBox(addForm.getHour());
        startMinutes = new JComboBox(addForm.getMinute());
        startYear = new JComboBox(addForm.getYear());
        startMonth = new JComboBox(addForm.getMonth());
        startDay = new JComboBox(addForm.getDay());
        endHours = new JComboBox(addForm.getHour());
        endMinutes = new JComboBox(addForm.getMinute());
        endYear = new JComboBox(addForm.getYear());
        endMonth = new JComboBox(addForm.getMonth());
        endDay = new JComboBox(addForm.getDay());

        JLabel freeSpace = new JLabel("          ");

        borderConfig(startHours,"Часы", mainFrame.getTextColor(),mainFrame.getMainForm());
        borderConfig(startMinutes,"Минуты", mainFrame.getTextColor(),mainFrame.getMainForm());
        borderConfig(startDay,"День", mainFrame.getTextColor(),mainFrame.getMainForm());
        borderConfig(startMonth,"Месяц", mainFrame.getTextColor(),mainFrame.getMainForm());
        borderConfig(startYear,"Год", mainFrame.getTextColor(),mainFrame.getMainForm());

        startHours.setBackground(mainFrame.getBgColor());
        startMinutes.setBackground(mainFrame.getBgColor());
        startDay.setBackground(mainFrame.getBgColor());
        startMonth.setBackground(mainFrame.getBgColor());
        startYear.setBackground(mainFrame.getBgColor());

        startHours.setForeground(mainFrame.getTextColor());
        startMinutes.setForeground(mainFrame.getTextColor());
        startDay.setForeground(mainFrame.getTextColor());
        startMonth.setForeground(mainFrame.getTextColor());
        startYear.setForeground(mainFrame.getTextColor());

        startTimePanel.add(startHours);
        startTimePanel.add(twoDots);
        startTimePanel.add(startMinutes);
        startTimePanel.add(freeSpace);
        startTimePanel.add(startDay);
        startTimePanel.add(startMonth);
        startTimePanel.add(startYear);

        endTimePanel = new JPanel();
        endTimePanel.setOpaque(true);

        panelConfig (endTimePanel,"Дата и время окончания", mainFrame.getTextColor(),mainFrame.getMainForm());

        endTimePanel.setBackground(mainFrame.getBgColor());
        JLabel secondFreeSpace = new JLabel("          ");
        borderConfig(endHours,"Часы", mainFrame.getTextColor(),mainFrame.getMainForm());
        borderConfig(endMinutes,"Минуты", mainFrame.getTextColor(),mainFrame.getMainForm());
        borderConfig(endDay,"День", mainFrame.getTextColor(),mainFrame.getMainForm());
        borderConfig(endMonth,"Месяц", mainFrame.getTextColor(),mainFrame.getMainForm());
        borderConfig(endYear,"Год", mainFrame.getTextColor(),mainFrame.getMainForm());

        endHours.setBackground(mainFrame.getBgColor());
        endMinutes.setBackground(mainFrame.getBgColor());
        endDay.setBackground(mainFrame.getBgColor());
        endMonth.setBackground(mainFrame.getBgColor());
        endYear.setBackground(mainFrame.getBgColor());

        endHours.setForeground(mainFrame.getTextColor());
        endMinutes.setForeground(mainFrame.getTextColor());
        endDay.setForeground(mainFrame.getTextColor());
        endMonth.setForeground(mainFrame.getTextColor());
        endYear.setForeground(mainFrame.getTextColor());

        endTimePanel.add(endHours);
        endTimePanel.add(secondTwoDots);
        endTimePanel.add(endMinutes);
        endTimePanel.add(secondFreeSpace);
        endTimePanel.add(endDay);
        endTimePanel.add(endMonth);
        endTimePanel.add(endYear);

        repeatTimePanel = new JPanel();

        panelConfig (repeatTimePanel,"Повторять", mainFrame.getTextColor(),mainFrame.getMainForm());

        repeatTimePanel.setBackground(mainFrame.getBgColor());
        repeatTimePanel.setPreferredSize(new Dimension(369,61));
        repeatTime = new JComboBox(addForm.getRepeatTimeValue());
        repeatTime.setPreferredSize(new Dimension(320, 23));
        repeatTime.setBackground(mainFrame.getBgColor());
        repeatTime.setForeground(mainFrame.getTextColor());
        repeatTimePanel.setEnabled(false);
        repeatTimePanel.add(repeatTime);

        JButton saveChanges = new JButton("Сохранить изменения");
        JButton cancelButton = new JButton("Отмена");

        saveChanges.setActionCommand("saveChanges");
        cancelButton.setActionCommand("cancelButton");

        saveChanges.setBackground(mainFrame.getBtnColor());
        saveChanges.setBorderPainted(false);
        cancelButton.setBackground(mainFrame.getBtnColor());
        cancelButton.setBorderPainted(false);

        JLabel longLine = new JLabel();
        longLine.setIcon(new ImageIcon("resources/longLine.png"));

        editForm.getContentPane().add(addJLabelTitle);
        editForm.getContentPane().add(addTitleTextField);
        editForm.getContentPane().add(jCheckBoxText);
        editForm.getContentPane().add(flagIsRepetable);
        editForm.getContentPane().add(startTimePanel);
        editForm.getContentPane().add(endTimePanel);
        editForm.getContentPane().add(repeatTimePanel);
        editForm.getContentPane().add(longLine);
        editForm.getContentPane().add(saveChanges);
        editForm.getContentPane().add(cancelButton);

        EditFormListener efl = new EditFormListener(this, mainFrame.getMainForm(), mainFrame);
        flagIsRepetable.addItemListener(efl);
        saveChanges.addActionListener(efl);
        cancelButton.addActionListener(efl);

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

    public JTextField getAddTitleTextField() {
        return addTitleTextField;
    }

    public JCheckBox getFlagIsRepetable() {
        return flagIsRepetable;
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

    public JFrame getEditForm() {
        return editForm;
    }

    public TaskList getTaskList() {
        return taskList;
    }

    public DefaultListModel getModel() {
        return model;
    }

    public void setModel(DefaultListModel model) {
        this.model = model;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

}
