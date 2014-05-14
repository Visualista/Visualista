package io.github.visualista.visualista.controller;

import java.util.EventObject;

public class ActionEvent extends EventObject{
    
    public final Object dataObject;
    public final ActionEventType aeType;
    
    public ActionEvent(Object source, ActionEventType newAeType, Object newData) {
        super(source);
        aeType = newAeType;
        dataObject = newData;
    }

    private static final long serialVersionUID = -5969105349275332629L;
    
    public Object getDataObject(){
        return dataObject;
    }
    
    public ActionEventType getAeType(){
        return aeType;
    }
}
