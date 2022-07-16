package com.example.pme2_3dennis59;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.pme2_3dennis59.Config.Operaciones;
import com.example.pme2_3dennis59.Config.SQLiteConexion;
import com.example.pme2_3dennis59.Modelos.Foto;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;

public class ListarFotografia extends AppCompatActivity {

    SQLiteConexion conexion = new SQLiteConexion(this, Operaciones.NameDatabase, null, 1);
    Button btnAtras;
    ArrayList<Foto> listaFotos = new ArrayList<Foto>();
    ImageView imageView;
    ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_fotografia);

        SQLiteDatabase db = conexion.getWritableDatabase();
        String sql = "SELECT * FROM fotografia";
        Cursor cursor = db.rawQuery(sql, new String[]{});

        while (cursor.moveToNext()) {
            listaFotos.add(new Foto(cursor.getInt(0), cursor.getString(1), cursor.getBlob(2)));
        }

        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        db.close();
        AdaptadorFotografia adaptador = new AdaptadorFotografia(this);
        list = findViewById(R.id.lista);
        list.setAdapter(adaptador);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                obtenerFoto(i);
            }
        });

        btnAtras = (Button) findViewById(R.id.btnAtras);
        btnAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });
    }

    private void obtenerFoto(int id) {
        SQLiteDatabase db = conexion.getReadableDatabase();
        Foto lista_Fotografia = null;
        listaFotos = new ArrayList<Foto>();

        Cursor cursor = db.rawQuery("SELECT * FROM " + Operaciones.tblFotografias, null);

        while (cursor.moveToNext()) {
            lista_Fotografia = new Foto();
            lista_Fotografia.setId(cursor.getInt(0));
            lista_Fotografia.setDescription(cursor.getString(1));
            listaFotos.add(lista_Fotografia);
        }
        cursor.close();
        Foto fotografia = listaFotos.get(id);

    }

    class AdaptadorFotografia extends ArrayAdapter<Foto> {

        AppCompatActivity appCompatActivity;

        AdaptadorFotografia(AppCompatActivity context) {
            super(context, R.layout.fotografia, listaFotos);
            appCompatActivity = context;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = appCompatActivity.getLayoutInflater();
            View item = inflater.inflate(R.layout.fotografia, null);

            imageView = item.findViewById(R.id.imageView);

            SQLiteDatabase db = conexion.getWritableDatabase();

            String sql = "SELECT * FROM fotografia";

            Cursor cursor = db.rawQuery(sql, new String[]{});
            Bitmap bitmap = null;
            TextView textView1 = item.findViewById(R.id.textView);

            if (cursor.moveToNext()) {
                textView1.setText(listaFotos.get(position).getDescription());
                byte[] blob = listaFotos.get(position).getFoto();
                ByteArrayInputStream bais = new ByteArrayInputStream(blob);
                bitmap = BitmapFactory.decodeStream(bais);
                imageView.setImageBitmap(bitmap);
            }
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
            db.close();

            return (item);
        }
    }
}