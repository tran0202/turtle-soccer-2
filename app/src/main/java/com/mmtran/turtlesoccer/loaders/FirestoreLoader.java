package com.mmtran.turtlesoccer.loaders;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.mmtran.turtlesoccer.models.Nation;

import java.util.ArrayList;
import java.util.List;

public class FirestoreLoader {

    private FirebaseFirestore db;

    private List<Nation> nationList = new ArrayList<Nation>();

    public FirestoreLoader() {
        db = FirebaseFirestore.getInstance();
    }

    public List<Nation> getNations(List<Nation> list) {

        DocumentReference docRef = db.collection("nation").document("AFG");
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Nation nation = documentSnapshot.toObject(Nation.class);
                list.add(nation);
            }
        });

        return nationList;
    }

//    public List<Nation> getActiveNations() {
//        return getNations().stream().filter(nation ->
//                nation.getParentNationId().isEmpty() && nation.getConfederationId() != null && !nation.getConfederationId().isEmpty())
//                .collect(Collectors.toList());
//    }
}
