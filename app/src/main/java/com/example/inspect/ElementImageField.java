package com.example.inspect;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import androidx.databinding.Bindable;
import androidx.databinding.ObservableField;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class ElementImageField extends TemplateElement {

    private final ObservableField<String> imageUriString = new ObservableField<>();
    private final ObservableField<Drawable> image = new ObservableField<>();

    public ElementImageField() {
        setType("5");
    }

    @Bindable
    public String getImageUriString() {
        return this.imageUriString.get();
    }

    @Bindable
    public void setImageUriString(String imageUriString) {
        if (!this.imageUriString.equals(imageUriString)){
            this.imageUriString.set(imageUriString);
            //notifyPropertyChanged(BR.imageUriString);
            //todo: convert URI into bitmap image, then drawable. setImage(drawable).
        }
    }

    @Bindable
    public Drawable getImage() {
        return this.image.get();
    }

    @Bindable
    public void setImage(Drawable image) {
        if(!this.image.equals(image)){
            this.image.set(image);
        }
    }

    @Override
    public String deconstructElement() {
        String blueprintFragment = getType() + "," + "imageString";
        return blueprintFragment;
    }
}
