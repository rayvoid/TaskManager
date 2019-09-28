package controller;
import model.TaskIO;
import model.TaskList;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import view.AddForm;
import view.CalendarForm;
import view.EditForm;
import view.MainFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.File;
import java.io.IOException;

public class MainListener implements ActionListener {

    private final static Logger logger = Logger.getLogger(MainListener.class);

    private JFrame mainForm;
    private JFrame addForm;
    private JFrame editForm;
    private JFrame confirmForm;
    private EditForm editFormObject;
    private CalendarForm calendarForm;
    private DefaultListModel model;
    private TaskList taskList;
    private JList taskListView;

    public MainListener(MainFrame mainFrame, CalendarForm calendarForm, AddForm addForm, EditForm editForm){
        PropertyConfigurator.configure("resources/log4j.properties");
        this.mainForm = mainFrame.getMainForm();
        this.editForm = editForm.getEditForm();
        this.calendarForm = calendarForm;
        this.confirmForm = mainFrame.getConfirmForm();
        this.model = mainFrame.getModel();
        this.taskList = mainFrame.getTaskList();
        this.taskListView = mainFrame.getTaskListView();
        this.addForm = addForm.getAddForm();
        this.editFormObject = editForm;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("add")) {
            addForm.setVisible(true);
            mainForm.setEnabled(false);
        }

        if (e.getActionCommand().equals("remove")) {
            int idx = taskListView.getSelectedIndex();
            if (idx == -1){
                logger.info("task to remove is not selected");
                JOptionPane.showMessageDialog(new JFrame(), "Выберите задачу для удаления", "Подсказка!",
                        JOptionPane.INFORMATION_MESSAGE);
            }
            else {
                confirmForm.setVisible(true);
                mainForm.setEnabled(false);
            }
        }

        if (e.getActionCommand().equals("removeTaskConfirm")) {
            taskList.remove(taskList.getTask(taskListView.getSelectedIndex()));
            model.remove(taskListView.getSelectedIndex());
            try {
                TaskIO.writeText(taskList, new File("resources/data.txt"));
            } catch (IOException e1) {
                logger.error("Time conversion error" + e1.toString());
            }
            confirmForm.setVisible(false);
            mainForm.setEnabled(true);
            mainForm.toFront();
        }

        if (e.getActionCommand().equals("cancelRemoveConfirm")){
            confirmForm.setVisible(false);
            mainForm.setEnabled(true);
            mainForm.toFront();
        }

        if (e.getActionCommand().equals("edit")) {
            int idx = taskListView.getSelectedIndex();
            if (idx == -1){
                logger.info("task to edit not selected or missing");
                JOptionPane.showMessageDialog(new JFrame(), "Выберите задачу для редактирования",
                        "Подсказка!", JOptionPane.INFORMATION_MESSAGE);
            }else {
                editFormObject.getAddTitleTextField().setText(taskList.getTask(idx).getTitle());
                editFormObject.setIndex(idx);

                if(taskList.getTask(taskListView.getSelectedIndex()).isRepeated()){

                    String startTimeString = TaskIO.timeConverter(taskList.getTask(idx).getStartTime());
                    String endTimeString = TaskIO.timeConverter(taskList.getTask(idx).getEndTime());

                    String[] startTime = substringTime(startTimeString);
                    String[] endTime = substringTime(endTimeString);

                    editFormObject.getFlagIsRepetable().setSelected(true);
                    editFormObject.getRepeatTimePanel().setEnabled(true);

                    editFormObject.getStartHours().setSelectedItem(startTime[3]);
                    editFormObject.getStartMinutes().setSelectedItem(startTime[4]);
                    editFormObject.getStartDay().setSelectedItem(startTime[2]);
                    editFormObject.getStartMonth().setSelectedItem(startTime[1]);
                    editFormObject.getStartYear().setSelectedItem(startTime[0]);

                    editFormObject.getEndHours().setSelectedItem(endTime[3]);
                    editFormObject.getEndMinutes().setSelectedItem(endTime[4]);
                    editFormObject.getEndDay().setSelectedItem(endTime[2]);
                    editFormObject.getEndMonth().setSelectedItem(endTime[1]);
                    editFormObject.getEndYear().setSelectedItem(endTime[0]);

                    switch (taskList.getTask(idx).getRepeatInterval()){
                        case 60:
                            editFormObject.getRepeatTime().setSelectedIndex(0);
                            break;
                        case 900:
                            editFormObject.getRepeatTime().setSelectedIndex(1);
                            break;
                        case 1800:
                            editFormObject.getRepeatTime().setSelectedIndex(2);
                            break;
                        case 3600:
                            editFormObject.getRepeatTime().setSelectedIndex(3);
                            break;
                        case 86400:
                            editFormObject.getRepeatTime().setSelectedIndex(4);
                            break;
                        case 604800:
                            editFormObject.getRepeatTime().setSelectedIndex(5);
                            break;
                    }
                }else{
                    String startTimeString = TaskIO.timeConverter(taskList.getTask(idx).getStartTime());
                    String[] startTime = substringTime(startTimeString);

                    setActive();

                    editFormObject.getStartHours().setSelectedItem(startTime[3]);
                    editFormObject.getStartMinutes().setSelectedItem(startTime[4]);
                    editFormObject.getStartDay().setSelectedItem(startTime[2]);
                    editFormObject.getStartMonth().setSelectedItem(startTime[1]);
                    editFormObject.getStartYear().setSelectedItem(startTime[0]);
                }
                editForm.setVisible(true);
                mainForm.setEnabled(false);
            }
        }

        if (e.getActionCommand().equals("calendar")) {
            mainForm.setEnabled(false);
            calendarForm.getCalendarForm().setVisible(true);
            calendarForm.getTextArea().setText("");
        }

        if (e.getActionCommand().equals("active")) {
            int idx = taskListView.getSelectedIndex();
            if (idx == -1){
                System.out.println("Task is not selected");
            }
            else {
                if (taskList.getTask(taskListView.getSelectedIndex()).isActive()){
                    taskList.getTask(taskListView.getSelectedIndex()).setActive(false);
                    model.set(taskListView.getSelectedIndex(),
                            taskList.getTask(taskListView.getSelectedIndex()).getTitle() + " (Не активная)");
                }else {
                    taskList.getTask(taskListView.getSelectedIndex()).setActive(true);
                    model.set(taskListView.getSelectedIndex(),
                            taskList.getTask(taskListView.getSelectedIndex()).getTitle() + " (Активная)");
                }

                try {
                    TaskIO.writeText(taskList, new File("resources/data"));
                } catch (IOException e1) {
                    logger.warn("error writing to file" + e1.toString());
                }
            }
        }
    }

    private String[] substringTime(String startTime){
        String[] tempArray = new String[5];
            tempArray[0] = startTime.substring(0,4);
            tempArray[1] = startTime.substring(5,7);
            tempArray[2] = startTime.substring(8,10);
            tempArray[3] = startTime.substring(12,14);
            tempArray[4] = startTime.substring(15,17);
        return tempArray;
    }

    private void setActive(){
        editFormObject.getFlagIsRepetable().setSelected(false);
        editFormObject.getRepeatTimePanel().setEnabled(false);
        editFormObject.getEndTimePanel().setEnabled(false);
        editFormObject.getEndHours().setEnabled(false);
        editFormObject.getEndMinutes().setEnabled(false);
        editFormObject.getEndDay().setEnabled(false);
        editFormObject.getEndMonth().setEnabled(false);
        editFormObject.getEndYear().setEnabled(false);
        editFormObject.getRepeatTime().setEnabled(false);
    }
}
