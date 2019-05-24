package com.sabirovfarit.android.rx;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "words")
public class Word {

    @PrimaryKey(autoGenerate = true)
    long Id;

    private String value;                      //Само слова
    private int diffLetter;                    //Сложные буквы Difficult

    private int wordsClass;                    // К какому классу относится слово

//    private int emphLetter;                    //Номер буквы ударения Emphasis

    private boolean isStage1;                  //Слово прочитано
    private boolean isStage2;                  //Правильно вставлена одна буква
    private boolean isStage3;                  //Правильно собраны все буквы
    private boolean isStage4;                  //Правильно написано слово

    private boolean mLearned;                  //Выучено ли слово

    private boolean inList;                    //Добавлено ли в подборку

//    private int sound1;                        //Звук произошение с выделением сложной буквы
//    private int sound;                         //Звук обычного произношения слова

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getDiffLetter() {
        return diffLetter;
    }

    public int getWordsClass() {
        return wordsClass;
    }

    public void setWordsClass(int wordsClass) {
        this.wordsClass = wordsClass;
    }

    public void setDiffLetter(int diffLetter) {
        this.diffLetter = diffLetter;
    }

//    public int getEmphLetter() {
//        return emphLetter;
//    }
//
//    public void setEmphLetter(int emphLetter) {
//        this.emphLetter = emphLetter;
//    }

    public boolean isStage1() {
        return isStage1;
    }

    public void setStage1(boolean stage1) {
        isStage1 = stage1;
    }

    public boolean isStage2() {
        return isStage2;
    }

    public void setStage2(boolean stage2) {
        isStage2 = stage2;
    }

    public boolean isStage3() {
        return isStage3;
    }

    public void setStage3(boolean stage3) {
        isStage3 = stage3;
    }

    public boolean isStage4() {
        return isStage4;
    }

    public void setStage4(boolean stage4) {
        isStage4 = stage4;
    }

    public boolean isLearned() {
        return mLearned;
    }

    public void setLearned(boolean learned) {
        mLearned = learned;
    }

    public boolean isInList() {
        return inList;
    }

    public void setInList(boolean inList) {
        this.inList = inList;
    }

//    public int getSound1() {
//        return sound1;
//    }
//
//    public void setSound1(int sound1) {
//        this.sound1 = sound1;
//    }
//
//    public int getSound() {
//        return sound;
//    }
//
//    public void setSound(int sound) {
//        this.sound = sound;
//    }
}
