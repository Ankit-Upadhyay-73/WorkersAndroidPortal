package com.example.workers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;

public class MainActivity extends AppCompatActivity
{
    ArrayList<DatabaseReference> ref = new ArrayList<DatabaseReference>();
    TextView editText;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText =   findViewById(R.id.idR);
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("message");
        System.out.println("----------------"+FirebaseDatabase.getInstance().getApp()+"             "+databaseReference.toString());
        databaseReference.setValue("Ankit");
        // Read from the database
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                editText.setText(dataSnapshot.getValue(String.class).toString());
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                editText.setText(error.getMessage());
            }
        });
        System.out.println("Written Successfully");
    }

    public void loadOwnData()
    {
        System.out.println("Calling loadOwnData()");
        refLength = ref.size();
        l = 0;
        for (int i=0;i<refLength;i++)
        {
            removeRef = null;
            jobName = null;
            jobDate = null;
            System.out.println("Job ref"+"  "+ref.get(i));
            final DatabaseReference databaseReference = ref.get(i);
            databaseReference.addValueEventListener(new ValueEventListener()
            {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                {
                    Iterator<DataSnapshot> dataSnapshotIterator = dataSnapshot.getChildren().iterator();
                    while (dataSnapshotIterator.hasNext())
                    {
                        DataSnapshot data = dataSnapshotIterator.next();
                        if (data.getKey().equals("jobRequiredDate"))
                            jobDate = data.getValue().toString();
                        if (data.getKey().equals("jobName"))
                            jobName = data.getValue().toString();
                        removeRef = databaseReference;
                    }
                    setValueInField(jobName,jobDate,removeRef);
                    l++;
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError)
                {

                }
            });
        }
    }

}
