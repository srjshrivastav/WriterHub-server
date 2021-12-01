package com.writerHub.practice.Exception;

public class AuthenticationExceptions {
    public static class AuthorNotFound extends Exception{
        private String message;
        public AuthorNotFound(String message){
            this.message = message;
        }
    }

    public static class AuthorExists extends Exception{
        private String message;
        public AuthorExists(String message){
            this.message = message;
        }
    }
}
