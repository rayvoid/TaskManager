package model;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

public class TaskIO {

    public static void write(TaskList tasks, OutputStream out) throws IOException {
        Iterator<Task> i = tasks.iterator();
        try(DataOutputStream dos = new DataOutputStream(out)){
            dos.writeInt(tasks.size());
        while (i.hasNext()){
            Task task = i.next();
            if (task.isRepeated()) {
                dos.writeUTF(task.getTitle());
                dos.writeBoolean(task.isActive());
                dos.writeLong(task.getStartTime().getTime());
                dos.writeUTF(" ");
                dos.writeLong(task.getEndTime().getTime());
                dos.writeInt(task.getRepeatInterval());
                dos.writeUTF("\n");
            }
            else {
                dos.writeUTF(task.getTitle());
                dos.writeBoolean( task.isActive());
                dos.writeLong(task.getTime().getTime());
                dos.writeUTF("\n");
                }
            }
        }
    }
    public static void read(TaskList tasks, InputStream in) throws IOException {

        int taskCount;
        String title;
        boolean isActive;
        int isRepeat;

        try (DataInputStream dis = new DataInputStream(new BufferedInputStream(in))){
            taskCount = dis.readInt();
            for (int i = 0; i < taskCount; i++) {

                title = dis.readUTF();
                isActive = dis.readBoolean();

                Date startTime = new Date();
                startTime.setTime(dis.readLong());

                if (dis.readUTF().equals("\n")){
                    Task task = new Task(title, startTime);
                    task.setActive(isActive);
                    tasks.add(task);
                }
                else {
                    Date endTime = new Date();
                    endTime.setTime(dis.readLong());
                    isRepeat = dis.readInt();
                    Task task = new Task(title, startTime, endTime, isRepeat);
                    task.setActive(isActive);
                    tasks.add(task);
                    dis.readUTF();
                }
            }
        }
    }
    public static void writeBinary(TaskList tasks, File filename) throws IOException{
        try(FileOutputStream fos = new FileOutputStream(filename)){
            write(tasks,fos);
        }
    }
    public static void readBinary(TaskList tasks, File filename) throws IOException {
        try( FileInputStream fis = new FileInputStream(filename)){
            read(tasks, fis);
        }
    }


    public static String timeConverter(Date time){
        String strDateFormat = "yyyy-MM-dd  HH:mm:ss:SSS";
        SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);
        return sdf.format(time).toString();
    }
    public static Date reverseConverter (String strTime) throws ParseException {
        String strDateFormat = "yyyy-MM-dd  HH:mm:ss:SSS";
        SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);
        return sdf.parse(strTime);
    }

    public static void write(TaskList tasks, Writer out) throws IOException{
        Iterator<Task> i = tasks.iterator();

        try(BufferedWriter bw = new BufferedWriter(out)){
        while(i.hasNext()) {
            Task task = i.next();
            if (task.isRepeated()) {
                bw.write("\"" + task.getTitle() + "\"");
                bw.write(" from ");
                System.out.println();
                bw.write("[" + TaskIO.timeConverter(task.getStartTime()) + "]");
                bw.write(" to ");
                bw.write("[" + TaskIO.timeConverter(task.getEndTime()) + "]");
                bw.write(" every ");
                bw.write("[" + secToDays(task.getRepeatInterval()) + "] ");

                if (task.isActive()) {
                    bw.write("active");
                }else {
                    bw.write("inactive");
                }

                if (i.hasNext()) {
                    bw.write(";");
                } else {
                    bw.write(".");
                }

                bw.write("\n");
            } else {
                bw.write("\"" + task.getTitle() + "\"");
                bw.write(" at ");
                bw.write("[" + TaskIO.timeConverter(task.getTime()) + "] ");
                if (task.isActive()) {
                    bw.write("active");
                }else {
                    bw.write("inactive");
                }

                if (i.hasNext()) {
                    bw.write(";");
                } else {
                    bw.write(".");
                }
                bw.write("\n");
                }
            }
        }
    }
    public static void writeText(TaskList tasks, File filename)throws IOException {
        try (FileWriter fw = new FileWriter(filename)) {
            write(tasks, fw);
        }
    }

    public static void read(TaskList tasks, Reader in) throws IOException, ParseException{
        String readedStringTask;
        String title;
        Date time;
        Date startTime;
        Date endTime;
        boolean isActive;
        int interval;

        try(BufferedReader br = new BufferedReader(in)) {

            while ((readedStringTask = br.readLine()) != null) {
                if (readedStringTask.contains("from")) {
                    title = readedStringTask.substring(1, readedStringTask.lastIndexOf("\""));
                    String startDate = readedStringTask.substring((readedStringTask.indexOf("[") + 1),
                            readedStringTask.indexOf("[") + 25);
                    String endDate = readedStringTask.substring((readedStringTask.indexOf("[") + 31),
                            readedStringTask.indexOf("[") + 55);
                    String inter = readedStringTask.substring((readedStringTask.lastIndexOf("every") + 7),
                            readedStringTask.lastIndexOf("]"));

                    if (readedStringTask.contains("inactive")) {
                        isActive = false;
                    } else isActive = true;

                    startTime = reverseConverter(startDate);
                    endTime = reverseConverter(endDate);
                    interval = stringToSec(inter);

                    Task tempTask = new Task(title, startTime, endTime, interval);
                    tempTask.setActive(isActive);
                    tasks.add(tempTask);
                } else {
                    title = readedStringTask.substring(1, readedStringTask.lastIndexOf("\""));
                    String noRepTime = readedStringTask.substring((readedStringTask.indexOf("[") + 1),
                            readedStringTask.indexOf("[") + 25);
                    if (readedStringTask.contains("inactive")) {
                        isActive = false;
                    } else isActive = true;
                    time = reverseConverter(noRepTime);

                    Task tempTask = new Task(title, time);
                    tempTask.setActive(isActive);
                    tasks.add(tempTask);
                }
            }
        }
    }

    public static void readText(TaskList tasks, File filename)throws IOException, ParseException {
        try(FileReader fr = new FileReader(filename)) {
            read(tasks, fr);
        }
    }


    public static String secToDays(int seconds) {
        int secs = seconds % 60;
        int minutes = seconds % 3600 / 60;
        int hours = seconds % 86400 / 3600;
        int days = seconds / 86400;

        String result;
        String secsStr = (secs == 1 ? "second" : "seconds");
        String minutesStr = (minutes == 1 ? "minute" : "minutes");
        String hoursStr = (hours == 1 ? "hour" : "hours");
        String daysStr = (days == 1 ? "day" : "days");

        if (days == 0 && hours == 0 && minutes == 0) {
            result = String.format("%02d %s" ,secs ,secsStr);
        }
        else if (days == 0 && hours == 0) {
            result = String.format("%02d %s %02d %s", minutes, minutesStr, secs, secsStr);
        }
        else if (days == 0 && hours != 0) {
            result = (String.format("%02d %s %02d %s %02d %s", hours, hoursStr, minutes, minutesStr, secs, secsStr));
        }
        else result = String.format("%02d %s %02d %s %02d %s %02d %s", days, daysStr, hours, hoursStr, minutes,
                    minutesStr, secs, secsStr);

        return result;
    }

    public static int stringToSec(String string) {
        String tmp = string.substring(0, string.length());
        int secs = 0;
        int minutes = 0;
        int hours = 0;
        int days = 0;

        String[] arrS = tmp.split(" ");

        for (int j = 0; j < arrS.length; j++) {

            if (arrS[j].equals("days") || arrS[j].equals("day")) {
                days = Integer.parseInt(arrS[j - 1]) * 60 * 60 * 24;

            }
                if (arrS[j].equals("second") || arrS[j].equals("seconds")) {
                    secs = Integer.parseInt(arrS[j - 1]);

                }
                if (arrS[j].equals("minutes") || arrS[j].equals("minute")) {
                    minutes = Integer.parseInt(arrS[j - 1]) * 60;

                }
                if (arrS[j].equals("hours") || arrS[j].equals("hour")) {
                    hours = Integer.parseInt(arrS[j - 1]) * 60 * 60;

            }
        }
        return days + hours + minutes + secs;
    }
}