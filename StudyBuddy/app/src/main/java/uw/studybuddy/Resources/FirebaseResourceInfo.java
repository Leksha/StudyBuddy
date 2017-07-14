package uw.studybuddy.Resources;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import uw.studybuddy.HomePageFragments.HomePage;

/**
 * Created by leksharamdenee on 2017-07-14.
 */

public class FirebaseResourceInfo {

    private static String table_resources = "Resources";
    private static String field_course = "course";

    public static DatabaseReference getResourcesTable() {
        return FirebaseDatabase.getInstance().getReference().child(table_resources);
    }

    public static void addNewResource(ResourceInfo resourceInfo){
        DatabaseReference DestReference = getResourcesTable().child(resourceInfo.getUuid());
        DestReference.setValue(resourceInfo);
    }

    public static void updateResource(ResourceInfo resourceInfo) {
        addNewResource(resourceInfo);
    }

    public static void deleteResource(ResourceInfo resourceInfo) {
        getResourcesTable().child(resourceInfo.getUuid()).removeValue();
    }

    public static Query getFilteredQuery() {
        String filterCourse = HomePage.clickedCourse;
        if (filterCourse.isEmpty()) {
            return getResourcesTable();
        } else {
            return getResourcesTable().orderByChild(field_course).equalTo(filterCourse);
        }
    }
}
