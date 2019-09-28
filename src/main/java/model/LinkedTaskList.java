package model;
import java.util.Iterator;



public class LinkedTaskList extends TaskList {

    private Node first;
    private Node last;
    private int size = 0;

    public LinkedTaskList() {
        first = new Node(null, null, last);
        last = new Node(null, first, null);
    }

    @Override
    public Task getTask(int index) {
        Node target = first.next;
        for (int i = 0; i < index; i++) {
            target = getNextElement(target);
        }
        return target.task;
    }

    private Node getNextElement(Node current) {
        return current.next;
    }

    @Override
    public void add(Task task) {
        if (task == null) {
            throw new NullPointerException("Task is NULL");
        } else {
            Node prev = last;
            prev.task = task;
            last = new Node(null, prev, null);
            prev.next = last;
            prev.prev.next = prev;
            size++;
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean remove(Task task) {
        boolean flag = false;
        if (task == null) {
            throw new NullPointerException("Element must be not empty");
        } else {
            Node target = first.next;
            for (int i = 0; i < size; i++) {
                if (target.task.equals(task)) {

                    if (target.prev != null) target.prev.next = target.next;
                    if (target.next != null) target.next.prev = target.prev;

                    target.task = null;
                    size--;
                    flag = true;
                    break;
                }
                target = getNextElement(target);
            }
            return flag;
        }
    }


    private static class Node implements Cloneable {
        Task task;
        Node next;
        Node prev;

        Node(Task receivedTask, Node prev, Node next) {
            this.task = receivedTask;
            this.next = next;
            this.prev = prev;
        }

        @Override
        public Node clone()  {
            Node copy = null;
            try {
                copy = (Node)super.clone();
                copy.task = null;
                copy.next = copy;
                copy.prev = copy;

            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
            return copy;
        }
    }
    @Override
    public Iterator iterator() {
        return new Itr();
    }

public class Itr implements Iterator{
    int cursor = 0;
    int lastRet = -1;

    @Override
    public boolean hasNext() { return cursor != size(); }

    @Override
    public Task next() {
        Task next = null;
        try {
            int i = cursor;
            next = getTask(i);
            lastRet = i;
            cursor = i + 1;
            return next;
        } catch (IndexOutOfBoundsException e) {
            System.out.println(" NoSuchElementException.");
        }
        return next;
    }

    @Override
    public void remove() {
        if (lastRet < 0)
            throw new IllegalStateException();
        try {
            LinkedTaskList.this.remove(LinkedTaskList.this.getTask(lastRet));
            if (lastRet < cursor)
                cursor--;
            lastRet = -1;
        } catch (IndexOutOfBoundsException e) {
            System.out.println("IndexOutOfBoundsException");
        }
    }
}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LinkedTaskList that = (LinkedTaskList) o;
        return size == that.size && compareTasks(that);

    }
    boolean compareTasks(LinkedTaskList a2){
        for (int i = 0; i < size; i ++){
            if (!(getTask(i) == null ? a2.getTask(i) == null : getTask(i).equals(a2.getTask(i)))) return false;
        }
        return  true;
    }

    @Override
    public int hashCode() {
        int result = 17;
        for (int i = 0; i < size; i++){result = 31 * result + getTask(i).hashCode();}
        return result;
    }

    @Override
    public String toString() {
        String result = "";

        for (int i = 0; i < size; i++){
            System.out.println("[" + getTask(i).getTitle()+"]");
        }
        return result;
    }

    @Override
    public LinkedTaskList clone(){
        try {
            LinkedTaskList clone = (LinkedTaskList) super.clone();

            clone.first = this.first.clone();
            clone.last = this.last.clone();
            clone.size = 0;

            clone.first.task = null;
            clone.first.next = clone.last;
            clone.first.prev = null;
            clone.last.task = null;
            clone.last.prev = clone.first;
            clone.last.next = null;

            for (int i = 0; i < size; i++){
                clone.add(getTask(i).clone());
            }
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new IllegalArgumentException();
        }
    }
}















