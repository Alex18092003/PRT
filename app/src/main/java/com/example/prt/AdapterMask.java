package com.example.prt;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class AdapterMask extends BaseAdapter {
    private Context mContext;
    private ArrayList<Mask> mListMask;
    String img="";

    public AdapterMask(Context mContext, List<Mask> listProduct) {
        this.mContext = mContext;
        this.maskList = listProduct;
    }

    List<Mask> maskList;

    @Override
    public int getCount() {
        return maskList.size();
    }

    @Override
    public Object getItem(int i) {
        return maskList.get(i);
    }
    @Override
    public long getItemId(int i)
    {
        return maskList.get(i).getKod_teacher();
    }

    private Bitmap getUserImage(String encodedImg)
    {

        if(encodedImg!=null&& !encodedImg.equals("null")) {
            byte[] bytes = Base64.decode(encodedImg, Base64.DEFAULT);
            return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        }
        else
        {
            return BitmapFactory.decodeResource(mContext.getResources(), R.drawable.nophoto);

        }
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup)
    {
        View v = View.inflate(mContext,R.layout.mask,null);
        TextView Name = v.findViewById(R.id.Name);
        TextView Surname = v.findViewById(R.id.Surname);
        TextView Patronymic = v.findViewById(R.id.Patronymic);
        TextView Subject = v.findViewById(R.id.Subject);
        ImageView Images = v.findViewById(R.id.imageView);

        Mask mask = maskList.get(i);
        Name.setText(mask.getName());
        Surname.setText(mask.getSurname());
        Patronymic.setText(mask.getPatronymic());
        Subject.setText(mask.getSubject());
        Images.setImageBitmap(getUserImage(mask.getImages()));

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(mContext,editing.class);
                intent.putExtra("Student",mask);
                mContext.startActivity(intent);
            }
        });

        return v;
    }
}
