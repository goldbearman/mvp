package com.sabirovfarit.android.rx;

public class WordCheckBox {

    private String value;                      //Само слова
    private boolean inList;                    //Добавлено ли в подборку

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean isInList() {
        return inList;
    }

    public void setInList(boolean inList) {
        this.inList = inList;
    }
}
