package com.example.mido.medicalemergency.Doctors;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.request.RequestOptions;
import com.example.mido.medicalemergency.GlideApp;
import com.example.mido.medicalemergency.Models.User;
import com.example.mido.medicalemergency.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DoctorAdapter extends RecyclerView.Adapter<DoctorAdapter.DoctorViewHolder> {

    Context context;
    List<User> doctorsList;
    OnDoctorClickListener mOnDoctorClickListener;

    public DoctorAdapter(Context context, List<User> doctorsList, OnDoctorClickListener mOnDoctorClickListener) {
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
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return doctorsList.size();
    }

    public interface OnDoctorClickListener{
        void onDoctorClicked(int position);
        void onCallClicked(int position,User user);
    }

    class DoctorViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.doctorName)
        TextView doctorNameTextView;
        @BindView(R.id.doctorType)
        TextView doctorTypeTextView;
        @BindView(R.id.doctorImage)
        ImageView doctorImageView;
        @BindView(R.id.callImageView)
        ImageView callImageView;

        public DoctorViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            itemView.setOnClickListener(this);
            callImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnDoctorClickListener.onCallClicked(getAdapterPosition(),doctorsList.get(getAdapterPosition()));
                }
            });
        }

        void bind(int position) {
            User doctor = doctorsList.get(position);
            doctorNameTextView.setText(doctor.getName());
            doctorTypeTextView.setText(doctor.getType());
            if(doctor.getImage()!=null){
                GlideApp.with(context)
                        .load(doctor.getImage())
                        .apply(RequestOptions.circleCropTransform())
                        .into(doctorImageView);
            }
        }

        @Override
        public void onClick(View view) {
        mOnDoctorClickListener.onDoctorClicked(getAdapterPosition());
        }
    }
}
