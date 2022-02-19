package com.board.board.controller.api;

import com.board.board.constant.Result;
import net.sf.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AbstractController {

    protected JSONObject resultJSON(Result result) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", result.getResultCode().getCode());
        jsonObject.put("msg", result.getResultCode().getMessage());
        jsonObject.put("object", result.getResultObject());
        return jsonObject;
    }

    protected Map<String, Object> resultMap(Result result) {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("code", result.getResultCode().getCode());
        resultMap.put("msg", result.getResultCode().getMessage());
        if (result.getResultObject() != null) {
            resultMap.put("object", result.getResultObject());
        }
        return resultMap;
    }

    protected Map<String, Object> resultMapCodeAndMsg(Result result) {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("code", result.getResultCode().getCode());
        resultMap.put("msg", result.getResultCode().getMessage());
        return resultMap;
    }

    protected String resultString(Result result) {
        return resultJSON(result).toString();
    }

}
