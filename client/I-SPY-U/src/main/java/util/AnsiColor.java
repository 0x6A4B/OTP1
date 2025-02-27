package util;


public enum AnsiColor{

    RED ("\033[0;31m"),
    GREEN ("\033[0;32m"),
    BLUE ("\033[0;34m"),
    YELLOW ("\033[0;33m"),
//    ORANGE (""),
    CYAN ("\033[0;36m"),
    PURPLE ("\033[0;35m"),
    WHITE ("\033[0;37m"),
    BLACK ("\033[30m"),

    // BRIGHTORANGE (""),
    BRIGHT_RED ("\033[1;31m"),
    BRIGHT_GREEN ("\033[1;32m"),
    BRIGHT_BLUE ("\033[1;34m"),
    BRIGHT_YELLOW ("\033[1;33m"),
    BRIGHT_PURPLE ("\033[1;35m"),
    BRIGHT_CYAN ("\033[1;36m"),
    BRIGHT_WHITE ("\033[1;37m"),
    BRIGHT_BLACK ("\033[10m"),

    RESET ("\033[0m");

    private String color;

    AnsiColor(final String color){ this.color = color; }

    public String getColor(){ return this.color; }

    @Override
    public String toString(){ return this.getColor(); }

}
