package com.lembergstate.spcs;


import com.google.firebase.functions.FirebaseFunctions;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public class FBFunctions {
    private AtomicReference<String> Json = new AtomicReference<>("");
    private FirebaseFunctions mFunctions;
    private String FunctionName;
    private Map<String, Object> data;

    public FBFunctions(String FunctionName, HashMap<String, Object> Data) {
        this.FunctionName = FunctionName;
        this.data = Data;
    }

    public String getJson() {
        addMessage();
        return Json.get();
    }


    private void addMessage() {
        mFunctions = FirebaseFunctions.getInstance();
        // Create the arguments to the callable function.
//        data.put("text", text);
//        data.put("push", true);
        mFunctions
                .getHttpsCallable(FunctionName)
                .call(data)
                .addOnSuccessListener(httpsCallableResult -> {
                    Gson g = new Gson();
                    Json.set(g.toJson(httpsCallableResult.getData()));
                })
                .addOnFailureListener(e -> {
                    Json.set("fail");
                });
    }
}
