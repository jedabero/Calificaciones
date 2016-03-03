package co.kinbu.calificaciones;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView notasView = (RecyclerView) findViewById(R.id.list_notas);
        notasView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        List<Nota> notas = new ArrayList<>();

        notas.add(new Nota(4, 30));
        notas.add(new Nota(4, 30));
        notas.add(new Nota(4, 40));

        notasView.setAdapter(new NotaRecyclerViewAdapter(notas));


    }
}
