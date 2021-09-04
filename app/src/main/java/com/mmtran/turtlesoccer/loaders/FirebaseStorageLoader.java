package com.mmtran.turtlesoccer.loaders;

import android.content.Context;
import android.widget.ImageView;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.mmtran.turtlesoccer.utils.FirebaseSafetyNetUtil;

public class FirebaseStorageLoader {

    private StorageReference storageReference;

    public void init(Context context) {

        FirebaseSafetyNetUtil.init(context);

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        this.storageReference = storageRef;
    }

    public void loadImage(Context context, ImageView imageView, String filename) {
        StorageReference imagesRef = storageReference.child(filename);
        GlideApp.with(context)
                .load(imagesRef)
                .into(imageView);
    }
}
