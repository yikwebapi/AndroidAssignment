package com.example.schooldetect.jsonData;

import java.util.Locale;

public class LanguageDefiner {

    String language = Locale.getDefault().toString();

    public String definelanguage() {
        String lan = "eng";

        if (language.matches("zh(.*)")) {
            lan = "cn";
        }
        return lan;
    }







}
