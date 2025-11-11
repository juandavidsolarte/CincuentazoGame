package com.example.cincuentazo.models;

public class CincuentazoException extends RuntimeException
{
    // Create an exception regarding null with the specified message
    public CincuentazoException()
    {
        super();
    }

    // Create an exception with a detailed message (String message)
    public CincuentazoException(String message)
    {
        super(message);
    }

    // Enclosing constructors (throwable cause)

    // Create an exception by wrapping another exception but without a detailed message
    public CincuentazoException(Throwable cause)
    {
        super(cause);
    }

    // Create an exception by wrapping another one, with a detailed message.
    public CincuentazoException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
