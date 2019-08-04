package com.gutotech.narutogame.config;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class ConfigFirebase {
    private static DatabaseReference database;
    private static FirebaseAuth auth;
    private static StorageReference storage;

    public static DatabaseReference getDatabase() {
        if (database == null)
            database = FirebaseDatabase.getInstance().getReference();
        return database;
    }

    public static FirebaseAuth getAuth() {
        if (auth == null)
            auth = FirebaseAuth.getInstance();
        return auth;
    }

    public static StorageReference getStorage() {
        if (storage == null)
            storage = FirebaseStorage.getInstance().getReference();
        return storage;
    }

    public static void downloadImage(Context context, StorageReference imageReference, ImageView imageView) {
        Glide.with(context)
                .using(new FirebaseImageLoader())
                .load(imageReference)
                .into(imageView);
    }
}
