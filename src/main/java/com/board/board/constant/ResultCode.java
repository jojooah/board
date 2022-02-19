package com.board.board.constant;

public enum ResultCode {

    Success(0, "성공"),


    //유저 관련 에러 100번대
    NOT_ALLOW_USER(100, "허가되지 않은 유저입니다."),
    UNKNOWN_USER(101, "존재하지 않는 유저입니다."),
    PASSWORD_NOT_CORRECT(102, "비밀번호가 일치하지 않습니다."),



    //글 관련 오류 200번대
    FAIL_TO_REGISTER_BOARD(200, "글 올리는것을 실패했습니다."),

    //글 올리기 관련 오류 300번대
    FAIL_TO_REGISET_COMMENT(300, "댓글다는것을 실패했ㅅ브니다.");

    private int code;
    private String message;

    ResultCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public <T> Result<T> result(T resultObject) {
        return new Result<T>(resultObject, this);
    }

    public <T> Result<T> result() {
        return new Result<T>(null, this);
    }

    @Override
    public String toString() {
        return "{code=" + code + ", message=" + message + "}";
    }
}

