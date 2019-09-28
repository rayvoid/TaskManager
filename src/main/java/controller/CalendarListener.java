package controller;

import model.Task;
import model.TaskIO;
import model.Tasks;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import view.CalendarForm;
import view.MainFrame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.Date;
import java.util.Map;
import java.util.Set;


public class CalendarListener implements ActionListener {

    private final static Logger logger = Logger.getLogger(CalendarListener.class);

    private MainFrame mainFrame;
    private CalendarForm calendarForm;
    private Date startTime;
    private Date endTime;

    public CalendarListener(CalendarForm calendarForm, MainFrame mainFrame){

        PropertyConfigurator.configure("resources/log4j.properties");
        this.mainFrame = mainFrame;
        this.calendarForm = calendarForm;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("viewTasks")) {

            logger.info("view events for the period");

            String startTimeString;
            String endTimeString;

            startTimeString = calendarForm.getStartYear().getSelectedItem()
                    + "-" +   calendarForm.getStartMonth().getSelectedItem() +
                    "-" + calendarForm.getStartDay().getSelectedItem()
                    + "  00:00:00:000";

            endTimeString = calendarForm.getEndYear().getSelectedItem()
                    + "-" +   calendarForm.getEndMonth().getSelectedItem() +
                    "-" + calendarForm.getEndDay().getSelectedItem()
                    + "  00:00:00:000";

            try {
                startTime =  TaskIO.reverseConverter(startTimeString);
                endTime = TaskIO.reverseConverter(endTimeString);
            } catch (ParseException e1) {
                logger.error("Time conversion error" + e1.toString());
            }


            String text ="";
            for (Map.Entry<Date, Set<Task>> calendar: (Tasks.calendar(mainFrame.getTaskList(), startTime,
                    endTime)).entrySet()) {
                String date = TaskIO.timeConverter(calendar.getKey());
                date = date.substring(0,17);
                text = text + (" " + date + "\t" + calendar.getValue() + "\n");
            }
            calendarForm.getTextArea().setText(text);
        }

        if (e.getActionCommand().equals("cancelView")) {
            calendarForm.getCalendarForm().setVisible(false);
            mainFrame.getMainForm().setVisible(true);
            mainFrame.getMainForm().setEnabled(true);
            mainFrame.getMainForm().toFront();
        }
    }

}
