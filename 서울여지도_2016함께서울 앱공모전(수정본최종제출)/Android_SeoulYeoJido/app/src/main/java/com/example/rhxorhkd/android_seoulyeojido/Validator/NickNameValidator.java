package com.example.rhxorhkd.android_seoulyeojido.Validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by kwongyo on 2016-09-30.
 */

public class NickNameValidator{
    private static NickNameValidator instance;

    public static NickNameValidator getInstance(){
        if(instance == null)
            instance = new NickNameValidator();
        return instance;
    }


    private Pattern pattern;
    private Matcher matcher;
    /**
     * 특문이
     */
    private static final String PASSWORD_PATTERN =
            "[0-9|a-z|A-Z|ㄱ-ㅎ|ㅏ-ㅣ|가-힝]*";


    private NickNameValidator(){
        pattern = Pattern.compile(PASSWORD_PATTERN);
    }

    /**
     * Validate password with regular expression
     * @param nickName nickName for validation
     * @return true valid nickName, false invalid nickName
     */
    public boolean validate(final String nickName){

        matcher = pattern.matcher(nickName);
        return matcher.matches();

    }
    public boolean validate(final String nickName,int minLength, int maxLength){
        if(nickName.length()<minLength || nickName.length()>maxLength)
            return false;
        matcher = pattern.matcher(nickName);
        return matcher.matches();

    }
    /*public static void main(String[]args) {
        NickNameValidator val= new NickNameValidator();
        System.out.println(val.validate("asdbl!@#$"));
    }*/
}
