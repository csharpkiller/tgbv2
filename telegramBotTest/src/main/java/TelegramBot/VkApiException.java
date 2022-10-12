package TelegramBot;

import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class VkApiException extends Exception{
    private TypeException excpType;
    private Exception exception;
    public VkApiException(UnsupportedEncodingException exception){
        excpType = TypeException.UnsupportedEncodingException;
        this.exception = exception;
    }
    public VkApiException(ParseException exception){
        excpType = TypeException.ParseException;
        this.exception = exception;
    }

    public VkApiException(IOException exception){
        excpType = TypeException.IOException;
        this.exception = exception;
    }

    public TypeException getExcpType() {
        return excpType;
    }

    public Exception getException() {
        return exception;
    }
}

