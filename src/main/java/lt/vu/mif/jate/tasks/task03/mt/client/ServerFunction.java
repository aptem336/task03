package lt.vu.mif.jate.tasks.task03.mt.client;

/**
 * ServerFunction class
 * @author valdo
 */
public enum ServerFunction {

    Ping(0),
    Addition(1),
    Substraction(2),
    Multiplication(3),
    Division(4),
    Function01(5),
    Function02(6),
    Close(7);

    private final int code;

    private ServerFunction(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

}
