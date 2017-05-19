package common;

import java.io.Serializable;

/**
 * Created by Анастасия on 08.05.2017.
 */
public class Request implements Serializable {

    public Command getCommand() {
        return command;
    }

    public void setCommand(Command command) {
        this.command = command;
    }

    public String getObjectType() {
        return objectType;
    }

    public void setObjectType(String objectType) {
        this.objectType = objectType;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void post(String objectType, String data) {
        setCommand(Command.POST);
        setObjectType(objectType);
        setData(data);
    }

    public void put(String objectType, String data) {
        setCommand(Command.PUT);
        setObjectType(objectType);
        setData(data);
    }

    public void delete(String objectType, String data) {
        setCommand(Command.DELETE);
        setObjectType(objectType);
        setData(data);
    }

    public void get(String objectType, String data) {
        setCommand(Command.GET);
        setObjectType(objectType);
        setData(data);
    }

    public enum Command {
        POST, PUT, DELETE, GET
    }

    private Command command;
    private String objectType;
    private String data;
}
