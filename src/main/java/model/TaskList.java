package model;

import java.io.Serializable;
import java.util.Iterator;

public abstract class TaskList implements Iterable<Task>, Cloneable, Serializable {

    public abstract void add(Task task);

    public abstract boolean remove(Task task);

    public abstract int size();

    public abstract Task getTask(int index);

    public abstract Iterator<Task> iterator();

}





































