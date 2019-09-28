package model;
import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class ArrayTaskList extends TaskList{

    private final int ARRAY_SIZE = 1;
    private int freeSlot = 0;
    private Task[] array = new Task[ARRAY_SIZE];

    @Override
    public void add(Task task){
        if (array[freeSlot] == null){
            array[freeSlot] = task;
            sizeUp();
        }
    }

    @Override
    public boolean remove(Task task) {
        boolean flag = false;
        boolean isInTaskArray = false;

        if (freeSlot != 0){
            for (int i = 0; i < array.length -1; i++){
                if (array[i].equals(task)){
                    array[i] = null;
                    isInTaskArray = true;
                    break;
                }
            }
            if (isInTaskArray){
                Task tempArray[] = new Task[freeSlot];
                int t = 0;
                for(int j = 0; j < array.length; j++){
                    if (array[j] != null){
                        tempArray[t] = array[j];
                        t++;
                    }
                }
                array = tempArray;
                freeSlot--;
                flag = true;
            }
        }
        return flag;
    }

    @Override
    public int size(){
        if(array[0] == null){
            return 0;
        }
        return array.length - 1;
    }

    @Override
    public Task getTask(int index){
        return array[index];
    }

    private void sizeUp(){
        Task tempArray[] = new Task[array.length + 1];
        System.arraycopy(array,0,tempArray,0,array.length);
        array = tempArray;
        freeSlot++;
    }

    @Override
    public Iterator iterator()    {
        return new Itr();
    }
    private class Itr implements Iterator {
        int cursor;
        int lastRet = -1;

        @Override
        public boolean hasNext() {
            return cursor != size();
        }

        @Override
        public Task next() {
            int i = cursor;
            if (i >= size()) throw new NoSuchElementException();
            Task element = ArrayTaskList.this.array[cursor];
            lastRet = i;
            cursor = i + 1;
            return element;
        }

        @Override
        public void remove() {
            if (lastRet < 0)
                throw new IllegalStateException();

            try {
                ArrayTaskList.this.remove(getTask(lastRet));
                cursor = lastRet;
                lastRet = -1;
            } catch (IndexOutOfBoundsException ex) {
                System.out.println("IndexOutOfBoundsException");
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ArrayTaskList that = (ArrayTaskList) o;
        return freeSlot == that.freeSlot
                && ARRAY_SIZE == that.ARRAY_SIZE
                && arrayCompare(array, that.array);
    }

    boolean arrayCompare(Task[] o1, Task[] o2){
        if (o1==o2)
            return true;
        if (o1 == null || o2 == null) return false;
        if (o1.length != o2.length) return false;
        for (int i = 0; i < o1.length; i++) {
            Task a1 = o1[i];
            Task a2 = o2[i];
            if (!(a1 == null ? a2 == null : a1.equals(a2))) return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = freeSlot + ARRAY_SIZE;
        result = 31 * result + Arrays.hashCode(array);
        return result;
    }

    @Override
    public String toString() {
        return "Task counts: " + size() + " \n " + "List of Tasks {" + Arrays.toString(array) +
                '}';
    }

    public ArrayTaskList clone() {
        try {
            ArrayTaskList cloneArray = (ArrayTaskList) super.clone();
            cloneArray.array = this.array.clone();
            for (int i = 0; i < size(); i++)
                cloneArray.array[i] = array[i].clone();
            return cloneArray;
        } catch (CloneNotSupportedException e) {
            throw new IllegalArgumentException();
        }
    }

}



