package com.example.mido.medicalemergency.Medicines;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.mido.medicalemergency.Consts;
import com.example.mido.medicalemergency.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MedicineActivity extends AppCompatActivity implements MedicineAdapter.OnMedicineClickListener {


    List<Medicine> medicineList;

    @BindView(R.id.medicineRecyclerView)
    RecyclerView medicineRecyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine);
        ButterKnife.bind(this);
      getMedicines();
    }

    void getMedicines() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        reference.keepSynced(true);
        reference.child(Consts.MEIDINCES).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                medicineList = new ArrayList<>();
                for (DataSnapshot childSnap : dataSnapshot.getChildren()) {
                    Medicine medicine = childSnap.getValue(Medicine.class);
                    medicine.setId(childSnap.getKey());
                    medicineList.add(medicine);
                }
                setMedicineRecyclerView();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    void setMedicineRecyclerView(){
        MedicineAdapter adapter = new MedicineAdapter(this, medicineList, this);
        medicineRecyclerView.setAdapter(adapter);
        medicineRecyclerView.setLayoutManager(new GridLayoutManager(this,2));
    }

    @Override
    public void onMedicineClicked(int position) {

    }
}
