package com.example.flexbuy.common.exception;

public class CustomException {
    public static class UserAlreadyExistException extends RuntimeException{
        public UserAlreadyExistException(String email){
            super("user already exist with email: "+email);
        }
    }

    public static class InvalidEmailFormatException extends RuntimeException{
        public InvalidEmailFormatException(String email){
            super("Invalid email format: "+email);
        }
    }

    public static class PasswordDoNotMatchException extends RuntimeException{
        public PasswordDoNotMatchException(){
            super("passwords do not match");
        }
    }

    public static class WrongPasswordException extends RuntimeException{
       public WrongPasswordException(){
           super("Wrong Password");
       }
    }

    public static class UnwantedException extends RuntimeException{
        public UnwantedException(String message){
            super("no Orders found for: "+ message);
        }
    }
}
