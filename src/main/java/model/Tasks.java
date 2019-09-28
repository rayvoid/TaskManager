package model;

import java.util.*;

public class Tasks {

    public static Iterable<Task> incoming(Iterable<Task> tasks, Date start, Date end) {
        Set<Task> result = new HashSet();
        Iterator it = tasks.iterator();
        while (it.hasNext()) {
            Task tempTask = (Task)it.next();
            if (tempTask.nextTimeAfter(start) != null && tempTask.nextTimeAfter(start).compareTo(end) <= 0) {
                result.add(tempTask);
            }
        }
        return result;
    }

    public static SortedMap<Date, Set<Task>> calendar(Iterable<Task> tasks, Date start, Date end) {
        SortedMap<Date, Set<Task>> temp = new TreeMap<>();
        for (Task task : tasks) {
            Date result = task.nextTimeAfter(start);
            while (result != null && result.compareTo(end) <= 0) {
                if (temp.containsKey(result)) {
                    temp.get(result).add(task);
                }
                else {
                    Set<Task> tasksSet = new HashSet<>();
                    tasksSet.add(task);
                    temp.put(result, tasksSet);
                }
                result = task.nextTimeAfter(result);
            }
        }
        return temp;
    }
}