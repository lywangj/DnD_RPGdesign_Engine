package com.company.Exceptions;

public class CmdException extends Exception{

    private static final long serialVersionUID = -2405736440969523511L;

    public CmdException(String message) {
        super(message);
    }

    public static class notOnlyInventory extends CmdException {
        private static final long serialVersionUID = -2405736440969523512L;

        public notOnlyInventory() {
            super("I only understood you as far as wanting to inventory.");
        }
    }

    public static class notOnlyLook extends CmdException {
        private static final long serialVersionUID = -2405736440969523513L;

        public notOnlyLook() {
            super("I only understood you as far as wanting to look.");
        }
    }

    public static class invalidDestination extends CmdException {
        private static final long serialVersionUID = -2405736440969523514L;

        public invalidDestination() {
            super("You can't go there.");
        }
    }

    public static class absentItemInEnvironment extends CmdException {
        private static final long serialVersionUID = -2405736440969523515L;

        public absentItemInEnvironment() {
            super("You can't get that.");
        }
    }

    public static class tooManyItemsToGet extends CmdException {
        private static final long serialVersionUID = -2405736440969523516L;

        public tooManyItemsToGet() {
            super("You can only get one item at a time.");
        }
    }

    public static class absentItemInInv extends CmdException {
        private static final long serialVersionUID = -2405736440969523517L;

        public absentItemInInv() {
            super("You don't have that.");
        }
    }

    public static class invalidImplement extends CmdException {
        private static final long serialVersionUID = -2405736440969523519L;

        public invalidImplement() {
            super("You can't do that.");
        }
    }

    public static class tooManyActions extends CmdException {
        private static final long serialVersionUID = -2405736440969523520L;

        public tooManyActions() {
            super("You can only do one action at a time.");
        }
    }

    public static class tooManyDestination extends CmdException {
        private static final long serialVersionUID = -2405736440969523514L;

        public tooManyDestination() {
            super("You can only go one place at a time.");
        }
    }

    public String toString(){
        return getMessage();
    }


}