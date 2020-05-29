package lt.vu.mif.jate.tasks.task03.mt.common;

/**
 * Response codes
 * @author valdo
 */

public enum Response {
    
    Pong(0),
    Success(1),
    Error(2),
    Closed(3);
    
    private final int code;

    private Response(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
    
    public static Response fromInt(int code) {
        for (Response r: values()) {
            if (r.getCode() == code) {
                return r;
            }
        }
        throw new IllegalArgumentException("Code " + code + " not defined?");
    }
    
}