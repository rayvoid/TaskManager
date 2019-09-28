import model.LinkedTaskList;
import model.TaskIO;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import view.MainFrame;
import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;



public class App {

    private final static Logger logger = Logger.getLogger(App.class);

    public static void main(String[] args) {
        PropertyConfigurator.configure("resources/log4j.properties");
        logger.info("program start");

        LinkedTaskList ls2 = new LinkedTaskList();

        try {
            TaskIO.readText(ls2, new File("resources/data.txt"));
        } catch (IOException e) {
            logger.error("Task file not found!");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainFrame(ls2);
            }
        });
    }
}
