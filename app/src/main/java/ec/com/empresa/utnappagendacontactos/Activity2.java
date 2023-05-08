package ec.com.empresa.utnappagendacontactos;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Activity2 extends AppCompatActivity {

    Agenda agenda;
    EditText txtNombre, txtTelefono, txtDireccion, txtEmail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);

        agenda = new Agenda(this,"agenda1.db", 1);
        txtNombre = findViewById(R.id.txtNombre);
        txtTelefono = findViewById(R.id.txtTelefono);
        txtDireccion = findViewById(R.id.txtDireccion);
        txtEmail = findViewById(R.id.txtEmail);

        Intent intent = getIntent();
        String nombre = intent.getStringExtra("nombre");
        String telefono = intent.getStringExtra("telefono");
        String direccion = intent.getStringExtra("direccion");
        String email = intent.getStringExtra("email");

        txtNombre.setText(nombre);
        txtTelefono.setText(telefono);
        txtDireccion.setText(direccion);
        txtEmail.setText(email);
    }

    public void cmdEliminar_onClick(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("¿Seguro desea eliminar este contacto?");
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = getIntent();
                int id = intent.getIntExtra("id", 0);
                Boolean aux = agenda.Delete(id);

                if(aux){
                    Toast.makeText(Activity2.this, "Contacto eliminado con éxito", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(Activity2.this, "Error, contacto no eliminado", Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
                Intent resultIntent = new Intent();
                setResult(Activity.RESULT_OK, resultIntent);
                finish();
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    public void cmdActivityMain_onClick(View v){
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
    public void cmdGuardar_onClick(View v){
        Intent intent = getIntent();
        int id = intent.getIntExtra("id", 0);
        if(agenda.Read_One(id) == null){
            if((txtTelefono.getText().toString()).trim().isEmpty()){
                Toast.makeText(this, "Ingrese un número", Toast.LENGTH_SHORT).show();
                return;
            }

            Contacto cont = agenda.Create(txtNombre.getText().toString(),
                    txtTelefono.getText().toString(),
                    txtDireccion.getText().toString(),
                    txtEmail.getText().toString());

            if(cont != null){
                Toast.makeText(this, "Contacto guardado con éxito", Toast.LENGTH_SHORT).show();
                txtNombre.setText("");
                txtTelefono.setText("");
                txtDireccion.setText("");
                txtEmail.setText("");
            }else{
                Toast.makeText(this, "Error, contacto no guardado", Toast.LENGTH_SHORT).show();
            }
        }else{
            Boolean cont = agenda.Update(id,txtNombre.getText().toString(),txtTelefono.getText().toString(),txtDireccion.getText().toString(),
                    txtEmail.getText().toString());
            if(cont){
                Toast.makeText(this, "Contacto actualizado con éxito", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "Error, contacto no actualizado", Toast.LENGTH_SHORT).show();
            }
        }
        Intent resultIntent = new Intent();
        setResult(Activity.RESULT_OK, resultIntent);
        finish();
    }

}