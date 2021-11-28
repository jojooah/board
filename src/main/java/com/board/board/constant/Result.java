package com.board.board.constant;

public class Result<T> {

    private final T resultObject;
    private final ResultCode resultCode;

    public Result(T resultObject, ResultCode errorCode) {
        super();
        this.resultObject = resultObject;
        this.resultCode = errorCode;
    }

    public T getResultObject() {
        return resultObject;
    }

    public ResultCode getResultCode() {
        return resultCode;
    }

    public boolean isSuccess() {
        return ResultCode.Success.equals(this.resultCode);
    }

    public boolean isNotSuccess() {
        return !isSuccess();
    }

    /**
     * @return
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "{resultObject=" + resultObject + ", resultCode=" + resultCode + "}";
    }

}
