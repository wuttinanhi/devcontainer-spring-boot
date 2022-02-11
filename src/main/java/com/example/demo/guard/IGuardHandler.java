package com.example.demo.guard;

import javax.servlet.http.HttpServletRequest;

public interface IGuardHandler {
    public boolean process(HttpServletRequest request);
}
