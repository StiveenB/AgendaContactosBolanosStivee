package ec.com.empresa.utnappagendacontactos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class Agenda {

    private DBHelper dbHelper;
    private SQLiteDatabase db;

    public Agenda(Context contexto, String dbName, int version){
        dbHelper = new DBHelper(contexto, dbName, null, version);
    }
    static int id = 0;
    public Contacto Create(String nombre, String telefono, String direccion, String email){

        long qty = 0;
        try{
            db = dbHelper.getWritableDatabase();
            ContentValues row = new ContentValues();
            row.put("nombre", nombre);
            row.put("telefono", telefono);
            row.put("direccion", direccion);
            row.put("email", email);

            qty = db.insert("agenda",null,row);

        }catch (Exception ex){
            ex.toString();
        }

        if(qty>0){
            Contacto data = new Contacto();
            data.id = id++;
            data.nombre = nombre;
            data.telefono = telefono;
            data.direccion = direccion;
            data.email = email;
            return data;
        } else {
            return null;
        }
    }

    public Contacto Read_One(int id){
        db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT id, nombre, telefono, direccion, email FROM agenda where id = '" + id + "'", null);

        if(cursor.getCount() > 0){
            Contacto contacto;
            cursor.moveToNext();

            contacto = new Contacto();
            contacto.id = cursor.getInt(0);
            contacto.nombre = cursor.getString(1);
            contacto.telefono = cursor.getString(2);
            contacto.direccion = cursor.getString(3);
            contacto.email = cursor.getString(4);

            return contacto;
        }else{
            return null;
        }
    }

    public Contacto[] Read_All()
    {
        db = dbHelper.getReadableDatabase();
        Cursor cr = db.rawQuery("SELECT id, nombre, telefono, direccion, email FROM agenda ORDER BY id", null);
        if(cr.getCount()>0)
        {
            Contacto[] datos = new Contacto[cr.getCount()];
            Contacto cont;
            int i = 0;
            while (cr.moveToNext())
            {
                cont = new Contacto();
                cont.id =cr.getInt(0);
                cont.nombre = cr.getString(1);
                cont.telefono = cr.getString(2);
                cont.direccion = cr.getString(3);
                cont.email = cr.getString(4);
                datos[i++]=cont;
            }
            return datos;
        }
        else {
            return null;
        }
    }

    public boolean Update(int id, String nombre, String telefono, String direccion, String email)
    {
        db = dbHelper.getWritableDatabase();
        ContentValues row = new ContentValues();
        row.put("nombre", nombre);
        row.put("telefono", telefono);
        row.put("direccion", direccion);
        row.put("email", email);

        int qty = db.update("agenda",row,"id='"+id+"'",null);
        return qty>0;
    }

    public boolean Delete(int id)
    {
        db = dbHelper.getWritableDatabase();
        long qty = db.delete("agenda", "id='"+id+"'",null);
        return qty>0;
    }
}
