package com.example.android.japaneselanguageapp;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class WordAdapter extends ArrayAdapter<Word> {

    private static final String LOG_TAG=WordAdapter.class.getSimpleName();


    public WordAdapter(Activity context, ArrayList<Word> words){
        super(context, 0, words);
    }

    /*purpose is to get a list item view and return it to the list view*/
    public View getView(int position,  View convertView,  ViewGroup parent) {

        View listItemView = convertView;
        if (listItemView == null){

            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }



        /*gets word adapter at given position in list*/
        Word currentWord = getItem(position);


        /*find the text view in the list_item.xml with id japanese text*/
        TextView japaneseTextView = listItemView.findViewById(R.id.japanese_text_view);
        japaneseTextView.setText(currentWord.getJapaneseTranslation());

        /*find the text view in the list_item.xml with id default text*/
        TextView defaultTextView = listItemView.findViewById(R.id.default_text_view);
        defaultTextView.setText(currentWord.getDefaultTranslation());

        /*find the image view in the list_item.xml with id default text*/
        ImageView imageView = listItemView.findViewById(R.id.japaneseImageView);

        if (currentWord.hasImage()){
            /*set the image view resource in Word*/
            imageView.setImageResource(currentWord.getJimageResourceId());

            /*Makes sure that the view is visible*/
            imageView.setVisibility(View.VISIBLE);
        }

        /*sets visibility to gone which hides it completely*/
        else {
            imageView.setVisibility(View.GONE);
        }

        /*return whole list item layout (2 text views + 1 image view) */
        return listItemView;
    }
}