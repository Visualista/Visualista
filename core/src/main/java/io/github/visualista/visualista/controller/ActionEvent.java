package io.github.visualista.visualista.controller;

import java.util.EventObject;

public class ActionEvent extends EventObject{
    
    private final ActionEventType aeType;
    private final Object target;
    private final Object newData;
    
    public ActionEvent(Object source, ActionEventType aeType, Object target, Object newData) {
        super(source);
        this.aeType = aeType;
        this.target = target;
        this.newData = newData;
    }

    private static final long serialVersionUID = -5969105349275332629L;

    public ActionEventType getAeType(){
        return aeType;
    }
    
    public Object getTarget(){
        return target;
    }
    
    public Object getNewData(){
        return newData;
    }
}
