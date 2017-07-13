package uw.studybuddy.Tutoring;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by leksharamdenee on 2017-07-13.
 */

public class FirebaseTutor {

    private static String table_tutors = "Tutors";

    public static DatabaseReference getTutorsTable() {
        return FirebaseDatabase.getInstance().getReference().child(table_tutors);
    }

    public static void addNewTutor(TutorInfo tutor){
        DatabaseReference DestReference = getTutorsTable().child(tutor.getUuid());
        DestReference.setValue(tutor);
    }

    public static void updateTutor(TutorInfo tutor) {
        addNewTutor(tutor);
    }
}
