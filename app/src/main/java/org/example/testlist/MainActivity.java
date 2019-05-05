package org.example.testlist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ListView lvPeople;
    AdapterListView adapter;
    Button button;
    List <ObjectPeople> chequeadas;
    ArrayList<ObjectPeople> arrPeople;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvPeople= (ListView) findViewById(R.id.lvPeople);
        button = findViewById(R.id.button);
        ObjectPeople person;
        arrPeople = new ArrayList<>();

        for (int i=1; i<=20; i++){
            person=new ObjectPeople();
            person.setName(String.valueOf(i));
            double ran = Math.random() * i + 1;
            person.setTotal((double) Math.round(ran * 100d) / 100d);
            person.setShowName(false);
            arrPeople.add(person);
        }
        adapter=new AdapterListView(this,R.layout.item_listview,arrPeople);
        lvPeople.setAdapter(adapter);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mostrar();
            }
        });
    }

    void mostrar(){
        System.out.println("mostrando...");
        chequeadas = new ArrayList<>();

        for (int i = 0; i < arrPeople.size(); i++) {
            if(arrPeople.get(i).isShowName()){
                if(arrPeople.get(i).getName().isEmpty() || arrPeople.get(i).getName().equals("")){
                    //nada
                } else {
                    chequeadas.add(arrPeople.get(i));
                }

            }

        }

        if(chequeadas.size() != 0){
            for(int i = 0; i < chequeadas.size(); i++){
                System.out.println(chequeadas.get(i).getName());
            }
        }
    }

}
