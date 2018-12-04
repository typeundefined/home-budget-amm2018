package dsr.amm.homebudget.error;

public class UniqueConditionException extends RuntimeException{

    private int responseCode;

    public UniqueConditionException(String msg, int responseCode) {
        super(msg);
        this.responseCode = responseCode;
    }

    public int getResponseCode() {
        return responseCode;
    }
}
