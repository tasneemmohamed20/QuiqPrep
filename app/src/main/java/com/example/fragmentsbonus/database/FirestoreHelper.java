package com.example.fragmentsbonus.database;

import com.example.fragmentsbonus.models.meals.MealsItem;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class FirestoreHelper {
    private static final String COLLECTION_MEALS = "meals";
    private final FirebaseFirestore db;
//    private final String userId;

    public FirestoreHelper() {
        this.db = FirebaseFirestore.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

//        this.userId = user != null ? user.getUid() : null;
    }

    public Task<Void> syncMealToFirestore(MealsItem meal) {
//        if (userId == null) return Tasks.forException(new Exception("User not logged in"));
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser == null) {
            return Tasks.forException(new Exception("User not logged in"));
        }

        return db.collection(COLLECTION_MEALS)
                .document(currentUser.getUid())
                .collection("userMeals")
                .document(meal.getIdMeal())
                .set(meal);
    }

    public Task<Void> deleteMealFromFirestore(MealsItem meal) {
//        if (userId == null) return Tasks.forException(new Exception("User not logged in"));
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser == null) {
            return Tasks.forException(new Exception("User not logged in"));
        }
        return db.collection(COLLECTION_MEALS)
                .document(currentUser.getUid())
                .collection("userMeals")
                .document(meal.getIdMeal())
                .delete();
    }

    public Task<QuerySnapshot> retrieveUserMeals() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser == null) {
            return Tasks.forException(new Exception("User not logged in"));
        }
//        if (userId == null) return Tasks.forException(new Exception("User not logged in"));

        return db.collection(COLLECTION_MEALS)
                .document(currentUser.getUid())
                .collection("userMeals")
                .get();
    }
}
