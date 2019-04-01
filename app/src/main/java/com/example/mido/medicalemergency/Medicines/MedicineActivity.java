package com.example.mido.medicalemergency.Medicines;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.mido.medicalemergency.R;

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
        medicineList = new ArrayList<>();
        medicineList.add(new Medicine());
        medicineList.add(new Medicine());
        medicineList.add(new Medicine());
        medicineList.add(new Medicine());
        medicineList.add(new Medicine());
        setMedicineRecyclerView();

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
