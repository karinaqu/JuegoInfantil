package com.example.karinaquimbiamba.juegoinfantil;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class GridViewAdapter extends BaseAdapter {

    private int iconos[];
    private String lstNumeros[];
    //List<String> lstNumeros;
    Context mContext;
    private LayoutInflater inflater;

    public GridViewAdapter(Context mContext, int[] iconos, String[] lstNumeros) {
        this.iconos = iconos;
        this.lstNumeros = lstNumeros;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return lstNumeros.length;
    }

    @Override
    public Object getItem(int position) {
        return lstNumeros[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View grvNumeros= convertView;
        if(convertView==null){
            inflater=(LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            grvNumeros=inflater.inflate(R.layout.imagenes,null);
        }
        ImageView icon=grvNumeros.findViewById(R.id.imgNumeros);
        TextView numero= grvNumeros.findViewById(R.id.lstNumeros);
        numero.setText(lstNumeros[position]);
        icon.setImageResource(iconos[position]);

        /*final Button button;
        if(convertView ==null){
            button= new Button(mContext);
            button.setLayoutParams(new GridView.LayoutParams(90, 90));
            //button.setLayoutParams(new GridLayout.LayoutParams(85,85));
            button.setPadding(10,10,10,10);
            button.setText(lstNumeros[position]);
            button.setBackgroundColor(Color.DKGRAY);
            button.setTextColor(Color.YELLOW);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext,button.getText().toString(),Toast.LENGTH_SHORT).show();
                }
            });
        }
        else
            button=(Button)convertView;
        return button;*/

        return grvNumeros;
    }
}
