package com.company.Exceptions;

public class ActionException extends Exception{

    private static final long serialVersionUID = -2405736440969623511L;

    public ActionException(String message) {
        super(message);
    }

    public static class notFoundToConsume extends ActionException {
        private static final long serialVersionUID = -2405736440969623512L;

        public notFoundToConsume() {
            super("Targeted item cannot be used.");
        }
    }

    public String toString(){
        return getMessage();
    }


}