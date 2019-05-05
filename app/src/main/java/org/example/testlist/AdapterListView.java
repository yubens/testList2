package org.example.testlist;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Latitude on 02/10/2016.
 */

public class AdapterListView extends ArrayAdapter<ObjectPeople> {

    Context mContext;

    LayoutInflater inflater;

    ArrayList<ObjectPeople> arrayPeople;

    public AdapterListView(Context context, int resource, ArrayList<ObjectPeople> arraypeople) {

        super(context, resource);

        this.mContext = context;
        this.arrayPeople = arraypeople;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override

    public View getView(final int position, View convertView, ViewGroup parent) {


        final ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_listview, null);
            holder = new ViewHolder();
            holder.tvPosition = (TextView) convertView.findViewById(R.id.tvPosition);
            holder.edName = (EditText) convertView.findViewById(R.id.edName);
            holder.cbShowName = (CheckBox) convertView.findViewById(R.id.cbShowName);
            holder.vPrecio = convertView.findViewById(R.id.textPrecio);
            holder.vTotal = convertView.findViewById(R.id.textTotal);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final ObjectPeople person = arrayPeople.get(position);
        holder.tvPosition.setText("Item " + (position + 1));
        holder.cbShowName.setChecked(person.isShowName());
        holder.vPrecio.setText(String.valueOf(person.getTotal()));
        holder.vTotal.setText(String.valueOf(Double.valueOf(person.getName()) * person.getTotal()));

        holder.edName.setEnabled(person.isShowName());

        /*if (person.isShowName()) {
            holder.edName.setVisibility(View.VISIBLE);
        } else {
            holder.edName.setVisibility(View.INVISIBLE);
        }*/

        //Using setOnclickListener not setOnCheckedChangeListener
        holder.cbShowName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.cbShowName.isChecked()) {
                    arrayPeople.get(position).setShowName(true);
                    //holder.edName.setVisibility(View.VISIBLE);
                    holder.edName.setEnabled(true);

                } else {
                    arrayPeople.get(position).setShowName(false);
                    //holder.edName.setVisibility(View.INVISIBLE);
                    holder.edName.setEnabled(false);
                    //arrayPeople.get(position).setName("");

                }
            }
        });

//Fill EditText with the value you have in data source
        holder.edName.setText(person.getName());
        holder.edName.setId(position);

//we need to update adapter once we finish with editing
        /*holder.edName.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            public void onFocusChange(View v, boolean hasFocus) {
                double cantidad, total;

                if (!hasFocus) {
                    final int position = v.getId();
                    final EditText Caption = (EditText) v;
                    cantidad = Double.valueOf(Caption.getText().toString());
                    total = cantidad * arrayPeople.get(position).getTotal();
                    arrayPeople.get(position).setName(String.valueOf(cantidad));
                    holder.vTotal.setText(String.valueOf(total));
                }
            }

        });*/


        holder.edName.addTextChangedListener(new TextWatcher() {
            double total, cantidad;
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(s.length() > 0){

                    if(Double.valueOf(s.toString()).isNaN() ){
                        System.out.println("error de entrada");
                    }
                    else {
                        cantidad = Double.valueOf(s.toString());
                        total = cantidad * arrayPeople.get(position).getTotal();
                        //arrayPeople.get(position).setName(String.valueOf(cantidad));
                    }

                }



            }

            @Override
            public void afterTextChanged(Editable editable) {
                System.out.println("despues");
                System.out.println(editable.toString());
                arrayPeople.get(position).setName(String.valueOf(cantidad));
                holder.vTotal.setText(String.valueOf(total));
            }
        });

        return convertView;

    }

    @Override

    public int getCount() {

        return arrayPeople.size();

    }

    static class ViewHolder {
        TextView tvPosition;
        TextView vPrecio;
        TextView vTotal;
        EditText edName;
        CheckBox cbShowName;
    }
}

