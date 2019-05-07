package org.example.testlist;

import android.app.Activity;
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

import java.util.List;

/**
 * Created by Latitude on 02/10/2016.
 */

public class AdapterListView2 extends ArrayAdapter<ObjectPeople> {


    Activity context;

    //LayoutInflater inflater;

    List<ObjectPeople> lista;
    List<Integer> originales;

    public AdapterListView2(Activity context,  List<ObjectPeople> lista, List<Integer> originales) {

        super(context, R.layout.item_listview, lista);

        this.context = context;
        this.lista = lista;
        this.originales = originales;
        //this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    static class ViewHolder {
        protected TextView tvPosition;
        protected TextView vPrecio;
        protected TextView vTotal;
        protected  EditText edName;
        protected CheckBox cbShowName;
        protected  TextView txName;
    }

    @Override

    public View getView(final int position, final View convertView, ViewGroup parent) {

        View view = null;
        double cantidad, total;

        //final ViewHolder holder;


        if (convertView == null) {
            LayoutInflater inflator = context.getLayoutInflater();
            view = inflator.inflate(R.layout.item_listview, null);

            final ViewHolder viewHolder = new ViewHolder();

            viewHolder.tvPosition = (TextView) view.findViewById(R.id.tvPosition);
            viewHolder.edName = (EditText) view.findViewById(R.id.edName);
            viewHolder.cbShowName = (CheckBox) view.findViewById(R.id.cbShowName);
            viewHolder.vPrecio = view.findViewById(R.id.textPrecio);
            viewHolder.vTotal = view.findViewById(R.id.textTotal);
            viewHolder.txName = view.findViewById(R.id.textCant);

            TextWatcher watcher = new TextWatcher() {
                double total, cantidad;

                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    double cantidad, total;
                    int position = viewHolder.edName.getId();

                    if(s.length() > 0){
                        System.out.println("cambiando pos " + position);

                        if(Double.valueOf(s.toString()).isNaN() ){
                            System.out.println("error de entrada");
                        } else {
                            cantidad = Double.valueOf(s.toString());
                            ObjectPeople people = (ObjectPeople) viewHolder.edName.getTag();
                            if(cantidad == 0 || cantidad > originales.get(position)){
                                System.out.println("error de cantidad");
                                cantidad = originales.get(position);
                                //people.setName(String.valueOf(cantidad));
                                viewHolder.edName.setText("");

                            } else {

                                //TextView txtTotal = (TextView) viewHolder.vTotal.getTag();
                                people.setName(String.valueOf(cantidad));

                            }

                            total = cantidad * people.getTotal();
                            viewHolder.txName.setText(String.valueOf(cantidad));
                            viewHolder.vTotal.setText(String.valueOf(total));

                        }
                    }



                }

                @Override
                public void afterTextChanged(Editable editable) {
                    System.out.println("after");

                }
            };

            viewHolder.edName.addTextChangedListener(watcher);


            viewHolder.cbShowName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int pos = viewHolder.cbShowName.getId();
                    if (viewHolder.cbShowName.isChecked()) {
                        lista.get(pos).setShowName(true);
                        viewHolder.edName.setVisibility(View.VISIBLE);
                        viewHolder.edName.setEnabled(true);
                        System.out.println("check " + pos + " " + true);

                    } else {
                        lista.get(position).setShowName(false);
                        viewHolder.edName.setVisibility(View.INVISIBLE);
                        viewHolder.edName.setEnabled(false);
                        System.out.println("check " + pos + false);
                        //arrayPeople.get(position).setName("");

                    }
                }
            });


            /*
            viewHolder.cbShowName.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    System.out.println("check " + b);
                    System.out.println("but " + compoundButton.isChecked());
                    ObjectPeople people = (ObjectPeople) viewHolder.cbShowName.getTag();
                    people.setShowName(compoundButton.isChecked());
                    viewHolder.edName.setEnabled(compoundButton.isChecked() );
                }
            });*/

            viewHolder.cbShowName.setTag(lista.get(position));
            viewHolder.edName.setTag(lista.get(position));
            viewHolder.vTotal.setTag(lista.get(position));
            viewHolder.txName.setTag(lista.get(position));
            view.setTag(viewHolder);

        } else {
            view = convertView;
            ((ViewHolder) view.getTag()).cbShowName.setTag(lista.get(position));
            ((ViewHolder) view.getTag()).edName.setTag(lista.get(position));
            ((ViewHolder) view.getTag()).vTotal.setTag(lista.get(position));
            ((ViewHolder) view.getTag()).txName.setTag(lista.get(position));
        }

        ViewHolder holder = (ViewHolder) view.getTag();
        cantidad = Double.valueOf(lista.get(position).getName());
        total = cantidad * lista.get(position).getTotal();
        holder.txName.setText(String.valueOf(cantidad));
        holder.edName.setId(position);
        holder.vPrecio.setText(String.valueOf(lista.get(position).getTotal()));
        holder.vTotal.setText(String.valueOf(total));
        holder.cbShowName.setChecked(lista.get(position).isShowName());
        holder.cbShowName.setId(position);

        if(holder.cbShowName.isChecked()){
            holder.edName.setVisibility(View.VISIBLE);
        }
        else {
            holder.edName.setVisibility(View.INVISIBLE);
        }
        //holder.edName.setEnabled(lista.get(position).isShowName() );

        return view;

    }


}

