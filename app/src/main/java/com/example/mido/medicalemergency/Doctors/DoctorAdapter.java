package com.example.mido.medicalemergency.Doctors;

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

public class DoctorAdapter extends RecyclerView.Adapter<DoctorAdapter.DoctorViewHolder> {

    Context context;
    List<Doctor> doctorsList;
    OnDoctorClickListener mOnDoctorClickListener;

    public DoctorAdapter(Context context, List<Doctor> doctorsList, OnDoctorClickListener mOnDoctorClickListener) {
        this.context = context;
        this.doctorsList = doctorsList;
        this.mOnDoctorClickListener = mOnDoctorClickListener;
    }

    @NonNull
    @Override
    public DoctorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DoctorViewHolder(LayoutInflater.from(context).inflate(R.layout.doctor_row,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull DoctorViewHolder holder, int position) {
//        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return doctorsList.size();
    }

    interface OnDoctorClickListener{
        void onDoctorClicked(int position);
    }

    class DoctorViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.doctorName)
        TextView doctorNameTextView;
        @BindView(R.id.doctorType)
        TextView doctorTypeTextView;
        public DoctorViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        void bind(int position) {
            Doctor doctor = doctorsList.get(position);
            doctorNameTextView.setText(doctor.getDoctorName());
            doctorTypeTextView.setText(doctor.getType());
        }
    }
}
