import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * CombinationLockTest
 *
 * @author 59130701113 Werawad Ruangjaroon
 */
public class CombinationLockTest {

    private CombinationLock lock00; // comb. 00
    private CombinationLock lock01; // comb. 01
    private CombinationLock lock12; // comb. 12
    private CombinationLock lock99; // comb. 99

    @Before
    public void setUp() {
        lock00 = new CombinationLock(0);
        lock01 = new CombinationLock(1);
        lock12 = new CombinationLock(12);
        lock99 = new CombinationLock(99);
    }

    @After
    public void tearDown() {
        lock00 = null;
        lock01 = null;
        lock12 = null;
        lock99 = null;
    }

    /**
     * 2.b) The lock is closed.
     * 2.b.1) The lock is reset.
     * Test entering correct combination opens lock.
     */
    @Test
    public void correct_input_combination_should_be_unlock() {
        closeLocks();
        asssertCorrectCombination(lock00, 0, 0);
        asssertCorrectCombination(lock01, 0, 1);
        asssertCorrectCombination(lock12, 1, 2);
        asssertCorrectCombination(lock99, 9, 9);
    }

    /**
     * 2.b) The lock is closed.
     * 2.b.1) The lock is reset.
     * Test entering incorrect combination doesn’t open lock.
     */
    @Test
    public void incorrect_input_combination_should_be_lock() {
        closeLocks();
        assertIncorrectCombination(lock00, 1, 0);
        assertIncorrectCombination(lock01, 1, 1);
        assertIncorrectCombination(lock12, 2, 1);
        assertIncorrectCombination(lock99, 0, 9);
    }

    /**
     * 2.b) The lock is closed.
     * 2.b.2) The lock is almost open. (first digit has been entered)
     * Test that an almost open lock remains almost open if digit entered is not the correct second digit, but correct first digit.
     */
    @Test
    public void correct_first_input_combination_should_be_almost_open() {
        closeLocks();
        assertAlmostOpen(lock00, 0, 5);
        assertAlmostOpen(lock01, 0, 5);
        assertAlmostOpen(lock12, 1, 5);
        assertAlmostOpen(lock99, 9, 5);
    }

    /**
     * 2.b) The lock is closed.
     * 2.b.2) The lock is almost open. (first digit has been entered)
     * Test an almost open lock is reset if digit entered is neither first nor second digit of combination.
     */
    @Test
    public void incorrect_both_input_combination_should_be_reset_almost_open() {
        closeLocks();
        assertAlmostOpenResetClose(lock00, 5, 5);
        assertAlmostOpenResetClose(lock01, 5, 5);
        assertAlmostOpenResetClose(lock12, 5, 5);
        assertAlmostOpenResetClose(lock99, 5, 5);
    }

    /**
     * 3. Test closing the lock:
     * Test an open lock is closed and reset after executing close.
     */
    @Test
    public void reset_close_after_execution_close_should_be_close() {
        closeLocks();
        assertCloseAfterExcution(lock00, 0,0);
        assertCloseAfterExcution(lock01, 0,1);
        assertCloseAfterExcution(lock12, 1,2);
        assertCloseAfterExcution(lock99, 9,9);

    }

    /**
     * 3. Test closing the lock:
     * Test a reset lock remains reset after executing close.
     */
    @Test
    public void reset_close_and_reset_code_again_after_excution_close_should_be_remains_close() {
        closeLocks();
        assertRemainCloseAfterExcution(lock00, 0,0);
        assertRemainCloseAfterExcution(lock01, 0,1);
        assertRemainCloseAfterExcution(lock12, 1,2);
        assertRemainCloseAfterExcution(lock99, 9,9);
    }

    /**
     * 3. Test closing the lock:
     * Test an almost open lock is reset after executing close.
     */
    @Test
    public void reset_close_almost_open_lock_after_execution_close_should_be_close() {
        closeLocks();
        assertAlmostOpenLockAfterCloseExcution(lock00, 0,0);
        assertAlmostOpenLockAfterCloseExcution(lock01, 0,1);
        assertAlmostOpenLockAfterCloseExcution(lock12, 1,2);
        assertAlmostOpenLockAfterCloseExcution(lock99, 9,9);
    }

    private void asssertCorrectCombination(
            CombinationLock lock, int first, int second) {
        assertFalse(lock.isOpen());
        enterInputs(lock, first, second);
        assertTrue(lock.isOpen());
    }

    private void assertIncorrectCombination(CombinationLock lock, int first, int second) {
        enterInputs(lock, first, second);
        assertFalse(lock.isOpen());
    }

    private void assertAlmostOpen(CombinationLock lock, int first, int second) {
        assertFalse(lock.isAlmostOpen());
        enterInputs(lock, first, second);
        assertTrue(lock.isAlmostOpen());
        assertFalse(lock.isOpen());
    }

    private void assertAlmostOpenResetClose(CombinationLock lock, int first, int second) {
        assertFalse(lock.isAlmostOpen());
        enterInputs(lock, first, second);
        assertFalse(lock.isOpen());
        assertFalse(lock.isAlmostOpen());
    }

    private void assertCloseAfterExcution(CombinationLock lock, int first, int second) {
        assertFalse(lock.isOpen());
        lock.enter(first);
        lock.close();
        lock.enter(second);
        assertFalse(lock.isOpen());
    }

    private void assertRemainCloseAfterExcution(CombinationLock lock, int first, int second) {
        assertFalse(lock.isOpen());
        enterInputs(lock, first, second);
        assertTrue(lock.isOpen());
        lock.close(); // first close
        lock.close(); // second close
        assertFalse(lock.isOpen());
    }

    private void assertAlmostOpenLockAfterCloseExcution(CombinationLock lock, int first, int second) {
        assertFalse(lock.isOpen());
        lock.enter(first);
        assertTrue(lock.isAlmostOpen());
        lock.close();
        assertFalse(lock.isAlmostOpen());
    }

    private void enterInputs(CombinationLock lock, int first, int second) {
        lock.enter(first);
        lock.enter(second);
    }

    private void closeLocks() {
        lock00.close();
        lock01.close();
        lock12.close();
        lock99.close();
    }
}
