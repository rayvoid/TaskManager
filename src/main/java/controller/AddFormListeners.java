package controller;

import model.Task;
import model.TaskIO;
import model.TaskList;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import view.AddForm;
import view.MainFrame;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

public class AddFormListeners implements ItemListener, ActionListener {

    private final static Logger logger = Logger.getLogger(AddFormListeners.class);

    private JFrame addForm;
    private JFrame mainForm;
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
    private JCheckBox flagIsRepeatable;
    private JTextField addTitleTextField;
    private DefaultListModel model;
    private TaskList taskList;
    private Date startTime;
    private Date endTime;
    private int repeatInterval;

    public AddFormListeners(AddForm addForm, MainFrame mainFrame)
    {
        PropertyConfigurator.configure("resources/log4j.properties");

        this.addForm = addForm.getAddForm();
        this.mainForm = mainFrame.getMainForm();
        this.flagIsRepeatable =addForm.getFlagIsRepetable();
        this.repeatTimePanel = addForm.getRepeatTimePanel();
        this.endTimePanel = addForm.getEndTimePanel();
        this.startHours = addForm.getStartHours();
        this.startMinutes = addForm.getStartMinutes();
        this.startYear = addForm.getStartYear();
        this.startMonth = addForm.getStartMonth();
        this.startDay = addForm.getStartDay();
        this.endHours = addForm.getEndHours();
        this.endMinutes = addForm.getEndMinutes();
        this.endYear = addForm.getEndYear();
        this.endMonth = addForm.getEndMonth();
        this.endDay = addForm.getEndDay();
        this.repeatTime = addForm.getRepeatTime();
        this.addTitleTextField = addForm.getAddTitleTextField();
        this.taskList = mainFrame.getTaskList();
        this.model = mainFrame.getModel();
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getStateChange() == ItemEvent.SELECTED){
            componentsIsActive(true);
        }
        if (e.getStateChange() == ItemEvent.DESELECTED){
            componentsIsActive(false);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("confirmAddButton")) {

            String title = "Задача без названия";
            String startTimeString;
            String endTimeString;

            if (!addTitleTextField.getText().equals("")) title = addTitleTextField.getText();

            startTimeString = startYear.getSelectedItem()
                    + "-" +   startMonth.getSelectedItem() + "-" + startDay.getSelectedItem() + "  " +
                              startHours.getSelectedItem() + ":" + startMinutes.getSelectedItem() + ":00:000";
            endTimeString =   endYear.getSelectedItem()
                    + "-" +   endMonth.getSelectedItem() + "-" + endDay.getSelectedItem() + "  " +
                              endHours.getSelectedItem() + ":" + endMinutes.getSelectedItem() + ":00:000";

            try {
               startTime =  TaskIO.reverseConverter(startTimeString);
               endTime = TaskIO.reverseConverter(endTimeString);
            } catch (ParseException e1) {
                logger.error("Time conversion error" + e1.toString());
            }

            switch (repeatTime.getSelectedIndex()){
                case 0:
                    repeatInterval = 60;
                    break;
                case 1:
                    repeatInterval = 900;
                case 2:
                    repeatInterval = 1800;
                    break;
                case 3:
                    repeatInterval = 3600;
                    break;
                case 4:
                    repeatInterval = 86400;
                    break;
                case 5:
                    repeatInterval = 604800;
                    break;
            }

            if (flagIsRepeatable.isSelected()){
                if (startTime.before(new Date())){

                    logger.warn("attempt to enter incorrect start time date");
                    JOptionPane.showMessageDialog(new JFrame(),
                            "Время начала события должно быть больше текущего!", "Подсказка!",
                            JOptionPane.INFORMATION_MESSAGE);
            }else {
                if (startTime.after(endTime)){

                    logger.warn("attempt to enter incorrect end time date");

                    JOptionPane.showMessageDialog(new JFrame(),
                            "Время окончания не может быть раньше времени начала события", "Подсказка!",
                            JOptionPane.INFORMATION_MESSAGE);
                    }else {

                    Task tempTask = new Task(title, startTime, endTime, repeatInterval);
                    tempTask.setActive(true);
                    taskList.add(tempTask);
                    model.addElement(title + " (Активная)");
                    try {
                        TaskIO.writeText(taskList, new File("resources/data.txt"));
                    } catch (IOException e1) {
                        logger.error("error writing to file" + e1.toString());
                    }
                    defaultAddFormClose();
                    }
                }
            }else {
                if (startTime.before(new Date())) {
                    logger.warn("attempt to enter incorrect start time date");
                    JOptionPane.showMessageDialog(new JFrame(),
                            "Время начала события должно быть больше текущего!", "Подсказка!",
                            JOptionPane.INFORMATION_MESSAGE);
                } else {
                    Task tempTask = new Task(title, startTime);
                    tempTask.setActive(true);
                    taskList.add(tempTask);
                    model.addElement(title + " (Активная)");
                    try {
                        TaskIO.writeText(taskList, new File("resources/data.txt"));
                    } catch (IOException e1) {
                        logger.error("error writing to file" + e1.toString());
                    }
                    defaultAddFormClose();
                }
            }
        }

        if (e.getActionCommand().equals("cancelAddButton")) {
            componentsIsActive(false);
            flagIsRepeatable.setSelected(false);
            addForm.setVisible(false);
            mainForm.setEnabled(true);
            mainForm.toFront();
        }
    }

    private void componentsIsActive(boolean visible){
        endTimePanel.setEnabled(visible);
        repeatTimePanel.setEnabled(visible);
        endHours.setEnabled(visible);
        endMinutes.setEnabled(visible);
        endDay.setEnabled(visible);
        endMonth.setEnabled(visible);
        endYear.setEnabled(visible);
        repeatTime.setEnabled(visible);
    }

    private void defaultAddFormClose(){
        componentsIsActive(false);
        flagIsRepeatable.setSelected(false);
        addForm.setVisible(false);
        mainForm.setEnabled(true);
        mainForm.toFront();
    }
}
