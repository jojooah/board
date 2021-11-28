package com.board.board.controller.api;

import com.board.board.constant.Result;
import net.sf.json.JSONObject;

public class AbstractController {

    protected JSONObject resultJSON(Result result){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", result.getResultCode().getCode());
        jsonObject.put("msg", result.getResultCode().getMessage());
        jsonObject.put("object", result.getResultObject());
        return jsonObject;
    }

    protected String resultString(Result result){
        return resultJSON(result).toString();
    }

}
