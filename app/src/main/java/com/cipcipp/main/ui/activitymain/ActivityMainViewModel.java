package com.cipcipp.main.ui.activitymain;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;
import android.util.Log;

import com.cipcipp.main.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.cipcipp.main.utils.Util;

import java.util.HashMap;

public class ActivityMainViewModel extends ViewModel {
    private MutableLiveData<User> data = new MutableLiveData<>();


    public void setData() {
        String uid;
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        } else {
            uid = Util.UID_Guest; //guest uid
        }
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("users")
                .child(uid);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null) {
                    String email = ((HashMap) dataSnapshot.getValue()).get("email").toString();
                    String usern = ((HashMap) dataSnapshot.getValue()).get("username").toString();
                    String photo_url = ((HashMap) dataSnapshot.getValue()).get("url_photo").toString();
                    User user = new User(usern, email, photo_url);
                    data.postValue(user);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e(ActivityMain.TAG, databaseError.toString());


            }
        });
    }

    public LiveData<User> getData() {
        Log.e(ActivityMain.TAG, "mulai getData");
        return data;
    }
}
