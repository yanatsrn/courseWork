package com.course.coursework.validator;

import javafx.scene.control.TextField;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserValidator {

    private static final Pattern PHONE_PATTERN = Pattern
            .compile("^(\\+375|80)(29|25|44|33)(\\d{3})(\\d{2})(\\d{2})$");
    private static final Pattern NUMBER_PATTERN = Pattern.compile("\\d+");
    //проверка на число

    private static final Pattern LOGIN_PATTERN = Pattern.compile("^[a-zA-Z][a-zA-Z0-9-_\\.]{1,20}$");
    private static final Pattern STRING_PATTERN = Pattern.compile("^[\\p{L}]+$");
    //проверка на строку
    private static final Pattern FUEL_CONSUMPTION_PATTERN = Pattern.compile("\\-?\\d+(\\.\\d{0,})?");
    //проверка на целое число или с точкой
    private static final Pattern PASSWORD_PATTERN = Pattern
            .compile("^.*(?=.{8,})(?=..*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).*$");
    /* должен включать хотя бы одну букву в верхнем и нижнем регистре, хотя бы одину цифру,
         хотя бы один специальный символ ("@", "#". "$", "%", "^", "&", "( "или") ",
         без пробелов, табуляции и т. Д и не менее 8 символов*/
    private static final Pattern MAIL_PATTERN = Pattern
            .compile("^[-0-9a-zA-Z.+_]+@[-0-9a-zA-Z.+_]+\\.[a-zA-Z]{2,4}");

    private UserValidator() {
    }

    public static boolean isValidSignIn(TextField login, TextField password) {
        boolean isValid = true;
        if (login == null || login.getText().isEmpty() || password == null || password.getText().isEmpty()) {
            isValid = false;
        }
        return isValid;
    }

    public static boolean isValidLogin(TextField login) {
        boolean isValid = true;
        if (login == null || login.getText().isEmpty() || !LOGIN_PATTERN.matcher(login.getText()).matches()) {
            isValid = false;
        }
        return isValid;
    }

    public static boolean isValidPassword(TextField password) {
        boolean isValid = true;
        if (password == null || password.getText().isEmpty() || !PASSWORD_PATTERN.matcher(password.getText()).matches()) {
            isValid = false;
        }
        return isValid;
    }

    public static boolean isValidSurnameAndName(TextField surname, TextField name) {
        boolean isValid = true;
        if (surname == null || surname.getText().isEmpty() || !STRING_PATTERN.matcher(surname.getText()).matches() ||
                name == null || name.getText().isEmpty() || !STRING_PATTERN.matcher(name.getText()).matches()) {
            isValid = false;
        }
        return isValid;
    }

    public static boolean isValidAge(TextField age) {
        boolean isValid = true;
        if (age == null || age.getText().isEmpty() || !NUMBER_PATTERN.matcher(age.getText()).matches()) {
            isValid = false;
        }
        return isValid;
    }

    public static boolean isValidPhone(TextField phone) {
        boolean isValid = true;
        if (phone == null || phone.getText().isEmpty() || !PHONE_PATTERN.matcher(phone.getText()).matches()) {
            isValid = false;
        }
        return isValid;
    }

    public static boolean isValidMail(TextField mail) {
        boolean isValid = true;
        if (mail == null || mail.getText().isEmpty() || !MAIL_PATTERN.matcher(mail.getText()).matches()) {
            isValid = false;
        }
        return isValid;
    }

    public static boolean isValidString(TextField string) {
        boolean isValid = true;
        if (string == null || string.getText().isEmpty() || !STRING_PATTERN.matcher(string.getText()).matches()) {
            isValid = false;
        }
        return isValid;
    }

    public static boolean isValidNumber(TextField number) {
        boolean isValid = true;
        if (number == null || number.getText().isEmpty() || !NUMBER_PATTERN.matcher(number.getText()).matches());
        return isValid;
    }

    public static boolean isValidFuelConsumption(TextField fuelConsumption) {
        boolean isValid = true;
        if (fuelConsumption == null || fuelConsumption.getText().isEmpty() || !FUEL_CONSUMPTION_PATTERN.matcher(fuelConsumption.getText()).matches()) {
            isValid = false;
        }
        return isValid;
    }

    public static boolean createUser(TextField surname, TextField name, TextField age, TextField phone, TextField mail,
                                     TextField login, TextField password){
        boolean createUser = true;
        if (surname == null && surname.getText().trim().isEmpty()) {
            createUser = false;
        }
        if (name == null && name.getText().trim().isEmpty()) {
            createUser = false;
        }
        Matcher matcher = NUMBER_PATTERN.matcher(age.getText());
        if (!matcher.matches()) {
            createUser = false;
        }
        if (phone == null && phone.getText().trim().isEmpty()) {
            createUser = false;
        }
        if (mail == null && mail.getText().trim().isEmpty()) {
            createUser = false;
        }
        if (login == null && login.getText().trim().isEmpty()) {
            createUser = false;
        }
        if (password == null && password.getText().trim().isEmpty()) {
            createUser = false;
        }
        return createUser;
    }

    public static boolean createModel(TextField name, TextField year, TextField fuel,
                                      TextField fuelConsumption, TextField price) {
        boolean createModel = true;
        if (name == null && name.getText().trim().isEmpty()) {
            createModel = false;
        }
        if (year == null && year.getText().trim().isEmpty()) {
            createModel = false;
        }
        if (fuel == null && fuel.getText().trim().isEmpty()) {
            createModel = false;
        }
        if (fuelConsumption == null && fuelConsumption.getText().trim().isEmpty()) {
            createModel = false;
        }
        if (price == null && price.getText().trim().isEmpty()) {
            createModel = false;
        }

        return createModel;
    }
}
