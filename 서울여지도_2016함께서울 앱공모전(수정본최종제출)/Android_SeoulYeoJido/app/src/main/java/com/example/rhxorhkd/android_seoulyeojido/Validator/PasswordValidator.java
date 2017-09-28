package com.example.rhxorhkd.android_seoulyeojido.Validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/*
Description
(			# Start of group
  (?=.*\d)		#   must contains one digit from 0-9
  (?=.*[a-z])		#   must contains one lowercase characters
  (?=.*[A-Z])		#   must contains one uppercase characters
  (?=.*[@#$%])		#   must contains one special symbols in the list "@#$%"
              .		#     match anything with previous condition checking
                {6,20}	#        length at least 6 characters and maximum of 20
)			# End of group
".*[ㄱ-ㅎㅏ-ㅣ가-힣]+.*" - this reg is korean language .
 */
public class PasswordValidator{
    private static PasswordValidator instance;
    public static PasswordValidator getInstance(){
        if(instance == null)
            instance = new PasswordValidator();
        return instance;
    }

    private Pattern pattern;
    private Matcher matcher;
    /**
     * 영문 대문자,소문자 포함
     * 숫자 포함
     * 특문 !@#$%^&*()포함
     * 길이 6~20
     */
    private static final String PASSWORD_PATTERN =
            "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*()]).{6,20})";

    private static final String TVTALK_PASSWORD_PATTERN =
            "^[a-zA-Z0-9]*$";

    private PasswordValidator(){
        pattern = Pattern.compile(TVTALK_PASSWORD_PATTERN);
    }

    /**
     * Validate password with regular expression
     * @param password password for validation
     * @return true valid password, false invalid password
     */
    public boolean validate(final String password){
        matcher = pattern.matcher(password);
        return matcher.matches();

    }
    public boolean tvtalkValidate(final String password) {
        if(password.length()>12 || password.length() < 6)
            return false;

        matcher = pattern.matcher(password);
        if( !matcher.matches() | Pattern.compile("^[0-9]*$").matcher(password).matches() )
            return false;

        return true;
    }
}
