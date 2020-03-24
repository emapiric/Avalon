package com.example.avalon.service.impl;

import com.example.avalon.service.Service;

public class ServiceImpl implements Service {
    @Override
    public boolean verifyId(String id) {
        try {
            int number = Integer.parseInt(id);
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
