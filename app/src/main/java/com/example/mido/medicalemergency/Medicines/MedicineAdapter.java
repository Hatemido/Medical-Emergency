package com.example.mido.medicalemergency.Medicines;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mido.medicalemergency.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MedicineAdapter extends RecyclerView.Adapter<MedicineAdapter.MedicineViewHolder> {

    Context mContext;
    List<Medicine> medicineList;
    OnMedicineClickListener mOnMedicineClickListener;

    public MedicineAdapter(Context mContext, List<Medicine> medicineList, OnMedicineClickListener mMedicineClickListener) {
        this.mContext = mContext;
        this.medicineList = medicineList;
        this.mOnMedicineClickListener = mMedicineClickListener;
    }

    @NonNull
    @Override
    public MedicineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MedicineViewHolder(LayoutInflater.from(mContext).inflate(R.layout.medicine_row,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MedicineViewHolder holder, int position) {
//        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return medicineList.size();
    }
    interface OnMedicineClickListener{
        void onMedicineClicked(int position);
    }

    class MedicineViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @BindView(R.id.medicineName)
        TextView medicineNameTextView;
        @BindView(R.id.medicineDescription)
        TextView medicineDescriptionTextView;
        public MedicineViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        void bind(int position) {
            Medicine medicine = medicineList.get(position);
            medicineNameTextView.setText(medicine.getName());
            medicineDescriptionTextView.setText(medicine.getDescription());
        }

        @Override
        public void onClick(View view) {
            mOnMedicineClickListener.onMedicineClicked(getAdapterPosition());
        }
    }
}
