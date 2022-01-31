package com.github.ticherti.simplechat.exception;

public class ParseException extends RuntimeException{
    public static final String CANNOT_PARSE = "Can't parse the line";
    public ParseException() {
        super(CANNOT_PARSE);
    }
}
