package com.example.promasu3_1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Ejercicio1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ejercicio1);
    }



    public void leerInterno(View view) {
        try{
            BufferedReader br =new BufferedReader(new InputStreamReader(openFileInput("fich_int_creado.txt")));
            String linea = br.readLine();
            String escribir = "";
            TextView tv = findViewById(R.id.tvLeido);
            while (linea!=null){
                if (escribir.equals("")) {
                    escribir=linea;
                } else {
                    escribir+="\n"+linea;
                }
                linea=br.readLine();
            }
            tv.setText(escribir);
            br.close();
            Log.i("Fichero interno","Archivo leido correctamente");
        } catch (FileNotFoundException e) {
            String error = "No se ha encontrado el archivo para leer";
            Log.e("Fichero interno", "[ERROR] "+error);
            Toast t = Toast.makeText(getApplicationContext(),error,Toast.LENGTH_SHORT);
            t.show();
        } catch (IOException e) {
            String error = "No se ha podido leer datos del archivo";
            Log.e("Fichero interno", "[ERROR] "+error);
            Toast t = Toast.makeText(getApplicationContext(),error,Toast.LENGTH_SHORT);
            t.show();
        }
    }

    public void escribirInterno(View view) {
        try{
            OutputStreamWriter osw = new OutputStreamWriter(openFileOutput("fich_int_creado.txt", Context.MODE_APPEND));
            EditText ed = findViewById(R.id.etEscribir);
            String escribir = ed.getText().toString();
            if (escribir.equals(""))  throw new IllegalArgumentException("No se puede escribir información vacía en el archivo");
            osw.write(escribir+"\n");
            osw.close();
            Toast t = Toast.makeText(getApplicationContext(),"Se ha escrito '"+escribir+"' correctamente",Toast.LENGTH_SHORT);
            t.show();
            ed.setText("");
            Log.i("Fichero interno","Archivo escrito correctamente");
        } catch (FileNotFoundException e) {
            String error = "No se ha encontrado el archivo para escribir";
            Log.e("Fichero interno", "[ERROR] "+error);
            Toast t = Toast.makeText(getApplicationContext(),error,Toast.LENGTH_SHORT);
            t.show();
        } catch (IOException e) {
            String error = "No se ha podido escribir datos en el archivo";
            Log.e("Fichero interno", "[ERROR] "+error);
            Toast t = Toast.makeText(getApplicationContext(),error,Toast.LENGTH_SHORT);
            t.show();
        } catch (IllegalArgumentException e) {
            Log.e("Fichero interno","[ERROR] "+e.getMessage());
            Toast t = Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT);
            t.show();
        }
    }

    public void borrarInterno(View view) {
        try{
            OutputStreamWriter osw = new OutputStreamWriter(openFileOutput("fich_int_creado.txt", Context.MODE_PRIVATE));
//            EditText ed = findViewById(R.id.etEscribir);
//            String escribir = ed.getText().toString();
//            if (escribir.equals(""))  throw new IllegalArgumentException("No se puede escribir información vacía en el archivo");
//            osw.write(escribir+"\n");
            osw.close();
            Toast t = Toast.makeText(getApplicationContext(),"Se ha borrado el contenido correctamente",Toast.LENGTH_SHORT);
            t.show();
            Log.i("Fichero interno","Archivo borrado correctamente");
        } catch (FileNotFoundException e) {
            String error = "No se ha encontrado el archivo para borrar";
            Log.e("Fichero interno", "[ERROR] "+error);
            Toast t = Toast.makeText(getApplicationContext(),error,Toast.LENGTH_SHORT);
            t.show();
        } catch (IOException e) {
            String error = "No se han podido borrar datos en el archivo";
            Log.e("Fichero interno", "[ERROR] "+error);
            Toast t = Toast.makeText(getApplicationContext(),error,Toast.LENGTH_SHORT);
            t.show();
        } catch (IllegalArgumentException e) {
            Log.e("Fichero interno","[ERROR] "+e.getMessage());
            Toast t = Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT);
            t.show();
        }
    }

    private boolean[] estadoSD() {
        boolean sdDisponible = false;
        boolean sdAccesoEscritura= false;

        String estado= Environment.getExternalStorageState();
        Log.i("Memoria", estado);

        if (estado.equals(Environment.MEDIA_MOUNTED)) {
            sdDisponible = true;
            sdAccesoEscritura = true;
        }else if (estado.equals(Environment.MEDIA_MOUNTED_READ_ONLY)) {
            sdDisponible = true;
        }

        boolean[] array = {sdDisponible,sdAccesoEscritura};
        return array;
    }

    public void leerExterno(View view) {
        try {
            boolean[] estadoSD = estadoSD();

            if (estadoSD[0]) {

                File ruta_sd = Environment.getExternalStorageDirectory();

                File f = new File(ruta_sd.getAbsolutePath(), "fich_ext_creado.txt");

                BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(f)));

                String linea = br.readLine();
                String escribir = "";
                TextView tv = findViewById(R.id.tvLeido);
                while (linea!=null){
                    if (escribir.equals("")) {
                        escribir=linea;
                    } else {
                        escribir+="\n"+linea;
                    }
                    linea=br.readLine();
                }
                tv.setText(escribir);
                br.close();

                Log.i("Ficheros", linea);
            } else Log.e ("Fichero externo", "La SD no está disponible para lectura");

        } catch (Exception ex){
            Log.e("Fichero externo", "Error al leer fichero en tarjeta SD");
        }
    }

    public void escribirExterno(View view) {
        try {
            boolean[] estadoSD = estadoSD();

            if (estadoSD[1]) {

                File ruta_sd = Environment.getExternalStorageDirectory();
                File f = new File (ruta_sd.getAbsolutePath(),"fich_ext_creado.txt");

                OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(f));
                EditText ed = findViewById(R.id.etEscribir);
                String escribir = ed.getText().toString();

                osw.write(escribir+"\n");
                osw.close();
                ed.setText("");
                Log.i ("Fichero externo", "Fichero escrito correctamente");
            } else Log.e ("Fichero externo", "La SD no está disponible para escritura");

        } catch (Exception ex) {
            ex.printStackTrace();
            Log.e ("Fichero externo", "Error al escribir fichero en tarjeta SD");
        }
    }


    //Para crear recurso en assets, en app creamos assets folder
    //dentro metemos un directorio con nuestro txt de recursos
    public void leerRecurso(View view) {
        try {
            InputStream fraw = getResources().openRawResource(R.raw.recurso);
            BufferedReader br = new BufferedReader(new InputStreamReader(fraw));

            String linea= br.readLine();
            TextView tv = findViewById(R.id.tvLeido);
            while (linea!=null){
                tv.setText(tv.getText().toString()+"\n"+linea);
                linea=br.readLine();
            }
            br.close();
            fraw.close();
        } catch (IOException e) {
            String error = "No se ha podido leer datos del recurso";
            Log.e("Recursos", "[ERROR] "+error);
            Toast t = Toast.makeText(getApplicationContext(),error,Toast.LENGTH_SHORT);
            t.show();
        }
    }
}
