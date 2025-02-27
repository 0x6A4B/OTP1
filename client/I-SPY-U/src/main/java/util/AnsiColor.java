package util;


public enum AnsiColor{

    RED ("\033[0;31m"),
    GREEN ("\033[0;32m"),
    BLUE ("\033[0;34m"),
    YELLOW ("\033[0;33m"),
//    ORANGE (""),
    CYAN ("\033[0;36m"),
    RESET ("\033[0m");

    private String color;

    AnsiColor(final String color){ this.color = color; }

    public String getColor(){ return this.color; }

    @Override
    public String toString(){ return this.getColor(); }

}
