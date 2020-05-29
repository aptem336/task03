package lt.vu.mif.jate.tasks.task03.mt.tool;

public abstract class TestCaseRunnable implements Runnable {

    private final MultiThreadedTestCase testCase;
    
    public TestCaseRunnable(MultiThreadedTestCase testCase) {
        this.testCase = testCase;
    }

    public abstract void runTestCase() throws Throwable;
    
    @Override
    public void run() {
        try {
            runTestCase();
        } catch (Throwable t) {
            testCase.handleException(t);
            testCase.interruptThreads();
        }
    }

}
