package controller;

import model.TaskIO;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import view.EditForm;
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

public class EditFormListener implements ActionListener, ItemListener {

    private final static Logger logger = Logger.getLogger(EditFormListener.class);

    private EditForm editFormObject;
    private JFrame mainForm;
    private Date startTime;
    private Date endTime;
    private int repeatInterval;

    public EditFormListener(EditForm editFormObject, JFrame mainForm, MainFrame mainFrame){
        PropertyConfigurator.configure("resources/log4j.properties");
        this.editFormObject = editFormObject;
        this.mainForm = mainForm;
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getStateChange() == ItemEvent.SELECTED){
            setActive(true);
        }
        if (e.getStateChange() == ItemEvent.DESELECTED){
            setActive(false);
        }
    }

    private void setActive(boolean flag){
        editFormObject.getFlagIsRepetable().setSelected(flag);
        editFormObject.getRepeatTimePanel().setEnabled(flag);
        editFormObject.getEndTimePanel().setEnabled(flag);
        editFormObject.getEndHours().setEnabled(flag);
        editFormObject.getEndMinutes().setEnabled(flag);
        editFormObject.getEndDay().setEnabled(flag);
        editFormObject.getEndMonth().setEnabled(flag);
        editFormObject.getEndYear().setEnabled(flag);
        editFormObject.getRepeatTime().setEnabled(flag);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getActionCommand().equals("cancelButton")) {
            setActive(false);
            editFormObject.getFlagIsRepetable().setSelected(false);
            editFormObject.getEditForm().setVisible(false);
            mainForm.setEnabled(true);
            mainForm.toFront();
        }

        if (e.getActionCommand().equals("saveChanges")) {

            String title = editFormObject.getAddTitleTextField().getText();
            String startTimeString;
            String endTimeString;

            if (editFormObject.getAddTitleTextField().getText().equals("")) title = "Задача без названия";

            startTimeString = editFormObject.getStartYear().getSelectedItem()
                    + "-" +   editFormObject.getStartMonth().getSelectedItem() + "-" +
                    editFormObject.getStartDay().getSelectedItem() + "  " +
                    editFormObject.getStartHours().getSelectedItem() + ":" +
                    editFormObject.getStartMinutes().getSelectedItem() + ":00:000";
            endTimeString = editFormObject.getEndYear().getSelectedItem()
                    + "-" +   editFormObject.getEndMonth().getSelectedItem() +
                    "-" + editFormObject.getEndDay().getSelectedItem() + "  " +
                    editFormObject.getEndHours().getSelectedItem() + ":" +
                    editFormObject.getEndMinutes().getSelectedItem() + ":00:000";

            try {
                startTime =  TaskIO.reverseConverter(startTimeString);
                endTime = TaskIO.reverseConverter(endTimeString);
            } catch (ParseException e1) {
                logger.error("Time conversion error" + e1.toString());
            }

            switch (editFormObject.getRepeatTime().getSelectedIndex()){
                case 0:
                    repeatInterval = 60;
                    break;
                case 1:
                    repeatInterval = 900;
                    break;
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

            if (editFormObject.getFlagIsRepetable().isSelected()){
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
                        editFormObject.getTaskList().getTask(editFormObject.getIndex()).setTitle(title);
                        editFormObject.getTaskList().getTask(editFormObject.getIndex()).setTime(startTime,endTime,
                                repeatInterval);


                        editFormObject.getModel().set(editFormObject.getIndex(),title + " (Активная)");

                        try {
                            TaskIO.writeText(editFormObject.getTaskList(), new File("resources/data.txt"));
                        } catch (IOException e1) {
                            logger.warn("error writing to file" + e1.toString());
                        }
                        defaultEditFormClose();
                    }
                }
            }else {
                if (startTime.before(new Date())) {
                    logger.info("attempt to enter incorrect start time date");
                    JOptionPane.showMessageDialog(new JFrame(),
                            "Время начала события должно быть больше текущего!", "Подсказка!",
                            JOptionPane.INFORMATION_MESSAGE);
                } else {

                    editFormObject.getTaskList().getTask(editFormObject.getIndex()).setTitle(title);
                    editFormObject.getTaskList().getTask(editFormObject.getIndex()).setTime(startTime);

                    editFormObject.getModel().set(editFormObject.getIndex(),title + " (Активная)");
                    try {
                        TaskIO.writeText(editFormObject.getTaskList(), new File("resources/data.txt"));
                    } catch (IOException e1) {
                        logger.error("error writing to file" + e1.toString());
                    }
                    defaultEditFormClose();
                }
            }
        }
    }
    private void defaultEditFormClose(){
        setActive(false);
        editFormObject.getEditForm().setVisible(false);
        mainForm.setEnabled(true);
        mainForm.toFront();
    }
}
