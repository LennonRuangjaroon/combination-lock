/**
 * A lock with a two digit combination.
 */
public class CombinationLock {

    private int digit1st;           // 1st digit of the combination
    private int digit2nd;           // 2nd digit of the combination
    private boolean isOpen;         // lock is open
    private boolean needSecond;     // 2nd combination digit will open the lock

    public CombinationLock(int combination) {
        this.digit1st = (combination / 10);
        this.digit2nd = (combination % 10);
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void enter(int digit) {
        if (needSecond) {
            isOpen = (digit == digit2nd);
        } else {
            needSecond = (digit == digit1st);
        }
    }

    public void close() {
        isOpen = false;
        needSecond = false;
    }

    public boolean isAlmostOpen() {
        return needSecond;
    }

}
