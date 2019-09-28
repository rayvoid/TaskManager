package model;
import java.io.Serializable;
import java.util.Date;

public class Task implements Cloneable, Serializable {
    private static final int TIMETOSECONDS = 1000;
    private static final int NUM = 31;

    private String title;
    private Date currentTime;
    private Date currentStartTime;
    private Date currentEndTime;
    private int currentInterval;
    private boolean isActive;
    private boolean isRepeated;

    public Task(String title, Date time) {
        this.title = title;
        currentTime = time;
    }

    public Task(String title, Date start, Date end, int interval) {
        this.title = title;
        currentStartTime = start;
        currentEndTime = end;
        currentInterval = interval;
        isRepeated = true;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public boolean isActive() {
        return isActive;
    }
    public void setActive(boolean active) {
        this.isActive = active;
    }

    public Date getTime() {
        if (isRepeated) {
            Date newStart = new Date(currentStartTime.getTime());
            return newStart;
        }
        Date newTime = new Date(currentTime.getTime());
        return newTime;
    }

    public void setTime(Date time) {
        this.currentTime = time;
        isRepeated = false;
    }

    public Date getStartTime() {
        if (!isRepeated) {
            Date time = new Date(currentTime.getTime());
            return time;
        }
        Date time = new Date(currentStartTime.getTime());
        return time;
    }

    public Date getEndTime() {
        if (!isRepeated) {
            Date time = new Date(currentTime.getTime());
            return time;
        }
        Date time = new Date(currentEndTime.getTime());
        return time;
    }

    public int getRepeatInterval() {
        if (!isRepeated) {
            return 0;
        }
        return currentInterval;
    }

    public void setTime(Date start, Date end, int interval) {
        this.currentStartTime = start;
        this.currentEndTime = end;
        this.currentInterval = interval;
        isRepeated = true;
    }

    public boolean isRepeated() {
        return isRepeated;
    }

    public Date nextTimeAfter(Date current) {
        if (!isActive()) {return null;}
        if (isRepeated()) {
            for (Date time = getStartTime(); getEndTime().compareTo(time) >= 0;) {
                if (time.compareTo(current) > 0) {
                    return time;
                }
                time = new Date(time.getTime() + currentInterval * TIMETOSECONDS);
            }
        }
        if (getTime().after(current)) {
            return getTime();
        }
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if (o == this) return true;
        if (o.getClass().equals(this.getClass())) {
            Task tempOb = (Task) o;
            if (title.equals(tempOb.title) && getStartTime().equals(tempOb.getStartTime()) &&
                    (getEndTime().equals(tempOb.getEndTime())) &&
                    tempOb.getRepeatInterval() == this.getRepeatInterval()) {
                return true;
            }
        }
            return false;
    }

    @Override
    public int hashCode() {
        long result;
        if (isRepeated()){
            result = NUM + getTitle().hashCode() + getStartTime().getTime() + getEndTime().getTime() * getRepeatInterval();
        }
        else result = NUM + getTitle().hashCode() + getTime().getTime();
        return (int)result;
    }

    @Override
    protected Task clone() throws CloneNotSupportedException {
        return (Task)super.clone();
    }

    @Override
    public String toString() {
        return title;
    }
}