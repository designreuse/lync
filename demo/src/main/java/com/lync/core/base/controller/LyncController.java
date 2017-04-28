package com.lync.core.base.controller;

import com.lync.core.toolbox.result.Result;

/**
 * Created by breeze on 2017/2/26.
 */
public class LyncController {


    public Result success(String message) {
        return new Result().addSuccess(message);
    }

    public Result success(Object data) {
        return new Result().success(data);
    }

    public Result error(String message) {
        return new Result().addError(message);
    }

    public Result warn(String message) {
        return new Result().addWarn(message);
    }


}
