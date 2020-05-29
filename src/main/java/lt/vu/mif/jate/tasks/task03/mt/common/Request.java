package lt.vu.mif.jate.tasks.task03.mt.common;

/**
 * Request codes
 * @author valdo
 */

public enum Request {
    
    Ping(0),
    Addition(1),
    Substraction(2),
    Multiplication(3),
    Division(4),
    Function01(5),
    Function02(6),
    Close(7);
    
    private final int code;

    private Request(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }    
    
    public static Request fromInt(int code) {
        for (Request r: values()) {
            if (r.getCode() == code) {
                return r;
            }
        }
        throw new IllegalArgumentException("Code " + code + " not defined?");
    }
    
}
