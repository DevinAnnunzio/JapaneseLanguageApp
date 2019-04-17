package com.example.android.japaneselanguageapp;

public class Word {

    /*Default translation for the word*/
    private String jDefaultTranslation;
    /*Japanese translation for the word*/
    private String jJapaneseTranslation;

    /*Image resource ID for the word*/
    private int jimageResourceId = NO_IMAGE;

    /*Constant to set no image state */
    private static final int NO_IMAGE = -1;

    /*resource id for word*/
    private int mAudioResourceId;



    /*Constructor for phrases activity */
    public Word(String defaultTranslation, String japaneseTranslation, int audioResourceId){

        jDefaultTranslation = defaultTranslation;
        jJapaneseTranslation = japaneseTranslation;
        mAudioResourceId = audioResourceId;

    }

    /*Overloaded Constructor for everything needing images*/
    public Word(String defaultTranslation, String japaneseTranslation, int imageResourceId, int audioResourceId){

        jDefaultTranslation = defaultTranslation;
        jJapaneseTranslation = japaneseTranslation;
        jimageResourceId = imageResourceId;
        mAudioResourceId= audioResourceId;
    }


    public String getDefaultTranslation(){

        return jDefaultTranslation;
    }


    public String getJapaneseTranslation(){

        return jJapaneseTranslation;
    }


    public int getJimageResourceId(){
        return jimageResourceId;
    }

    /*determines whether or not there is an image for this word*/
    public boolean hasImage() {

        return jimageResourceId != NO_IMAGE;
    }

    public int getAudioResourceId(){
        return mAudioResourceId;

    }



}