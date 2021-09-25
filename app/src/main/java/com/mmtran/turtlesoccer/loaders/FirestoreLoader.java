package com.mmtran.turtlesoccer.loaders;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.core.OrderBy;
import com.mmtran.turtlesoccer.models.AssociationsViewModel;
import com.mmtran.turtlesoccer.models.Nation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class FirestoreLoader {

    private static final String TAG = "FirestoreLoader";

    private final FirebaseFirestore db;
    private final List<Nation> nationList = new ArrayList<>();

    public FirestoreLoader() {
        db = FirebaseFirestore.getInstance();
    }

    public void getActiveNations(AssociationsViewModel associationsViewModel) {

        Query query = db.collection("nation")
                .whereEqualTo("parent_nation_id", "")
                .whereNotEqualTo("confederation_id", "");
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Nation nation = document.toObject(Nation.class);
                        nation.setId(document.getId());
                        nationList.add(nation);
                        Log.d(TAG, document.getId() + " => " + document.getData());
                    }
                    Collections.sort(nationList, new Comparator<Nation>() {
                        @Override
                        public int compare(Nation lhs, Nation rhs) {
                            return Integer.compare(lhs.getName().compareTo(rhs.getName()), 0);
                        }
                    });
                    associationsViewModel.setNationList(nationList);
                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }
            }
        });
    }

    public void getDocument(AssociationsViewModel associationsViewModel) {

        DocumentReference docRef = db.collection("nation").document("AFG");
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Nation nation = documentSnapshot.toObject(Nation.class);
                nationList.add(nation);
                nationList.stream().filter(n ->
                        n.getParentNationId().isEmpty() && n.getConfederationId() != null && !n.getConfederationId().isEmpty())
                        .collect(Collectors.toList());
                associationsViewModel.setNationList(nationList);
            }
        });
    }
}
