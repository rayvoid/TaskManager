package view;
import controller.MainListener;
import model.TaskList;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MainFrame {

    private Dimension btnSize = new Dimension(45,45);
    private Color bgColor = new Color(90,90,90);
    private Color btnColor = new Color(180,180,180);
    private Color textColor = new Color(220,220,220);
    private JList taskListView;
    private JScrollPane scrollPane;
    private JLabel removeConfirmText;
    private TaskList taskList;
    private JFrame mainForm;
    private JFrame confirmForm;
    private DefaultListModel<String> model = new DefaultListModel();
    private MainListener mainListener;

    public MainFrame(TaskList list) {
        taskList = list;
        mainForm = new JFrame("Планировщик задач");
        confirmForm = new JFrame("Удаление!");

        mainForm.getContentPane().setLayout(new FlowLayout());
        mainForm.getContentPane().setBackground(bgColor);
        mainForm.setBounds(500, 200, 266, 305);
        confirmForm.setLocationRelativeTo(null);
        mainForm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainForm.setResizable(false);

        ImageIcon smallIcon = new ImageIcon("resources/cup.png");
        mainForm.setIconImage(smallIcon.getImage());

        JLabel logo = new JLabel();
        JLabel line = new JLabel();
        JLabel line2 = new JLabel();

        logo.setIcon(new ImageIcon("resources/logo2.png"));
        line.setIcon(new ImageIcon("resources/line.png"));
        line2.setIcon(new ImageIcon("resources/line.png"));

        for (int i = 0; i < taskList.size(); i++) {
            String active;
            if(list.getTask(i).isActive() == true) {active = " (Активная)";
            }else {active = " (Неактивная)";
            }
            model.addElement(list.getTask(i).getTitle() + active);
        }

        taskListView = new JList(model);

        taskListView.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        taskListView.setBackground(new Color(200, 200, 200));
        scrollPane = new JScrollPane(taskListView);
        scrollPane.setPreferredSize(new Dimension(252, 150));

        JButton addButton = new JButton(new ImageIcon("resources/formAddImg.png"));
        JButton removeButton = new JButton(new ImageIcon("resources/formRemoveImg.png"));
        JButton calendarButton = new JButton(new ImageIcon("resources/formCalendarImg.png"));
        JButton editButton = new JButton(new ImageIcon("resources/formEditImg.png"));
        JButton activateButton = new JButton(new ImageIcon("resources/formActiveImg.png"));

        addButton.setActionCommand("add");
        removeButton.setActionCommand("remove");
        calendarButton.setActionCommand("calendar");
        editButton.setActionCommand("edit");
        activateButton.setActionCommand("active");

        AddForm addForm = new AddForm(this);
        EditForm editForm = new EditForm(this, addForm);
        CalendarForm calendarForm = new CalendarForm(this, addForm);

        mainListener = new MainListener(this, calendarForm, addForm, editForm);

            btnConfig(addButton);
            btnConfig(removeButton);
            btnConfig(calendarButton);
            btnConfig(editButton);
            btnConfig(activateButton);

            mainForm.getContentPane().add(logo);
            mainForm.getContentPane().add(scrollPane);
            mainForm.getContentPane().add(line);
            mainForm.getContentPane().add(addButton);
            mainForm.getContentPane().add(removeButton);
            mainForm.getContentPane().add(calendarButton);
            mainForm.getContentPane().add(editButton);
            mainForm.getContentPane().add(activateButton);
            mainForm.getContentPane().add(line2);

            confirmForm.getContentPane().setLayout(new FlowLayout());
            confirmForm.getContentPane().setBackground(bgColor);
            confirmForm.setSize(250,100);
            confirmForm.setLocationRelativeTo(null);
            confirmForm.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            confirmForm.setResizable(false);

            ImageIcon confirmImage = new ImageIcon("resources/confirmDeleteImage.png");
            confirmForm.setIconImage(confirmImage.getImage());

        confirmForm.setVisible(false);

        JButton yesButton = new JButton("Удалить");
        JButton noButton = new JButton("Отмена");

        yesButton.setActionCommand("removeTaskConfirm");
        noButton.setActionCommand("cancelRemoveConfirm");

        yesButton.setBackground(btnColor);
        yesButton.addActionListener(mainListener);
        yesButton.setBorderPainted(false);

        noButton.setBackground(btnColor);
        noButton.addActionListener(mainListener);
        noButton.setBorderPainted(false);

        removeConfirmText = new JLabel("Удалить выбранную задачу ?");
        removeConfirmText.setForeground(textColor);

        confirmForm.add(removeConfirmText);
        confirmForm.add(yesButton);
        confirmForm.add(noButton);
        startNotifier();
        mainForm.setVisible(true);
}

    private void startNotifier(){
        final ScheduledExecutorService ses = Executors.newSingleThreadScheduledExecutor();
        ses.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {


                for (int i = 0; i < taskList.size(); i++){
                    if (taskList.getTask(i).nextTimeAfter(new Date()) != null){
                        Date notifierDate = taskList.getTask(i).nextTimeAfter(new Date());
                        if (notifierDate.getTime() - (new Date().getTime()) <= 1000){
                            showMessage(i);
                        }
                    }

                }

            }
        }, 0, 1, TimeUnit.SECONDS);
    }

    private void showMessage(int i){
        new NotifierForm(taskList.getTask(i).getTitle(), this);
    }

    private void btnConfig(JButton btn){
        btn.setPreferredSize(btnSize);
        btn.setBackground(bgColor);
        btn.addActionListener(mainListener);
        btn.setBorderPainted(false);
    }

    public JList getTaskListView() {
        return taskListView;
    }

    public TaskList getTaskList() {
        return taskList;
    }

    public JFrame getConfirmForm() {
        return confirmForm;
    }

    public DefaultListModel<String> getModel() {
        return model;
    }

    public Color getTextColor() {
        return textColor;
    }

    public Color getBgColor() {
        return bgColor;
    }

    public Color getBtnColor() {
        return btnColor;
    }

    public JFrame getMainForm() {
        return mainForm;
    }
}
