package com.remy.loginregisterlaravel.entities;

import java.util.List;
import java.util.Map;

/**
 * Created by remy on 27/11/2017.
 */

public class ApiError {

    String message;

    Map<String, List<String>> errors;

    public String getMessage() {
        return message;
    }

    public Map<String, List<String>> getErrors() {
        return errors;
    }
}
