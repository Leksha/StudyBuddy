package uw.studybuddy.Tutoring;

import com.google.android.gms.common.stats.StatsEvent;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import uw.studybuddy.HomePageFragments.HomePage;

/**
 * Created by leksharamdenee on 2017-07-13.
 */

public class FirebaseTutor {

    private static String table_tutors = "Tutors";
    private static String field_course = "course";

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

    public static void deleteTutor(TutorInfo tutor) {
        getTutorsTable().child(tutor.getUuid()).removeValue();
    }

    public static Query getFilteredQuery() {
        String filterCourse = HomePage.clickedCourse;
        if (filterCourse.isEmpty()) {
            return getTutorsTable();
        } else {
            return getTutorsTable().orderByChild(field_course).equalTo(filterCourse);
        }
    }
}
