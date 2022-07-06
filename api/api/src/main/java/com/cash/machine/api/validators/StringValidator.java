package com.cash.machine.api.validators;

public class StringValidator {

    private StringValidator() { }
    
    //valida se a string esta vazia
    public static String isEmpty(String str, String label) {
        if (str.isEmpty() || str.equals(null)) {
            return label + " é Obrigátorio.";
        }

        return "";
    }

  //valida tamanho maximo
    public static String isMaxLength(String str, String label, int maxLength) {
        if (str.length() > maxLength) {
            return label + " Deve Conter no Máximo " + maxLength + " Caracters.";
        }

        return "";
    }

  //valida tamanho minimo
    public static String isMinLength(String str, String label, int minLength) {
        if (str.length() < minLength) {
            return label + " Deve Conter no Minímo " + minLength + " Caracters.";
        }

        return "";
    }

  //if chamando as validações
    public static String isValidString(String str, String label, int maxLength, int minLength) {

        String message = "";

        message = StringValidator.isEmpty(str, label);

        if (!message.isEmpty()) {
            return message;
        }

        message = StringValidator.isMinLength(str, label, minLength);

        if (!message.isEmpty()) {
            return message;
        }

        message = StringValidator.isMaxLength(str, label, maxLength);

        if (!message.isEmpty()) {
            return message;
        }
        
        return message;
    }
}
