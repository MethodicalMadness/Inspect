package com.example.inspect;

import android.graphics.drawable.Drawable;
import androidx.databinding.Bindable;
import androidx.databinding.ObservableField;

/**
 * Heading field data object. Data binding ensures that changes in the corresponding view are
 * replicated here so that when the elements are deconstructed the users input makes it into the
 * resulting blueprint.
 */
public class ElementImageField extends TemplateElement {

    private final ObservableField<String> imageUriString = new ObservableField<>();
    private final ObservableField<Drawable> image = new ObservableField<>();

    /**
     * Constructor.
     */
    public ElementImageField(String uriString) {
        setType("5");
        setImageUriString(uriString);
    }

    /**
     * Gets image Uri string.
     * @return
     */
    @Bindable
    public String getImageUriString() {
        return this.imageUriString.get();
    }

    /**
     * Sets image Uri string.
     * @param imageUriString
     */
    @Bindable
    public void setImageUriString(String imageUriString) {
        if (!this.imageUriString.equals(imageUriString)){
            this.imageUriString.set(imageUriString);
            //notifyPropertyChanged(BR.imageUriString);
        }
    }

    /**
     * Get Drawable image.
     * @return
     */
    @Bindable
    public Drawable getImage() {
        return this.image.get();
    }

    /**
     * Set Drawable image
     * @param image
     */
    @Bindable
    public void setImage(Drawable image) {
        if(!this.image.equals(image)){
            this.image.set(image);
        }
    }

    @Override
    public String deconstructElement() {
        String blueprintFragment = getType() + "," + getImageUriString();
        return blueprintFragment;
    }
}
