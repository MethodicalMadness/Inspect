package com.example.inspect.Data;

import android.net.Uri;

import androidx.room.TypeConverter;

public class UriConverter {
    @TypeConverter
    public String uriToString(Uri uri){
        return uri.toString();
    }

    @TypeConverter
    public Uri stringToUri(String uriString){
        return Uri.parse(uriString);
    }
}
