package ec.com.empresa.utnappagendacontactos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Agenda agenda;
    TableLayout tblAgenda;
    EditText txtBuscar;
    TextView tvNombre, tvTelefono, tvDireccion, tvEmail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        agenda = new Agenda(this,"agenda1.db", 1);
        tblAgenda = findViewById(R.id.tblAgenda);
        LlenarTabla();

        txtBuscar = findViewById(R.id.txtBuscar);
        Buscar();
    }

    public void cmdActivity2_onClick(View v){
        Intent i = new Intent(this, Activity2.class);
        startActivity(i);
    }

    private void LlenarTabla(){
        if(agenda != null){
            Contacto[] listaContactos = agenda.Read_All();

            TableLayout tabla = findViewById(R.id.tblAgenda);
            TableRow headers = (TableRow) tabla.getChildAt(0);
            tabla.removeAllViews();
            tabla.addView(headers);

            if(listaContactos != null){
                for (Contacto contacto : listaContactos) {
                    TableRow fila = new TableRow(this);

                    tvNombre = new TextView(this);
                    tvNombre.setText(contacto.nombre);
                    tvNombre.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f));
                    tvNombre.setPadding(10, 10, 10, 10);
                    tvNombre.setTag("tvNombre");
                    fila.addView(tvNombre);

                    tvTelefono = new TextView(this);
                    tvTelefono.setText(contacto.telefono);
                    tvTelefono.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f));
                    tvTelefono.setPadding(10, 10, 10, 10);
                    fila.addView(tvTelefono);

                    tvDireccion = new TextView(this);
                    tvDireccion.setText(contacto.direccion);
                    tvDireccion.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f));
                    tvDireccion.setPadding(10, 10, 10, 10);
                    fila.addView(tvDireccion);

                    tvEmail = new TextView(this);
                    tvEmail.setText(contacto.email);
                    tvEmail.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f));
                    tvEmail.setPadding(10, 10, 10, 10);
                    fila.addView(tvEmail);

                    tabla.addView(fila);

                    fila.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // Abre la nueva ventana
                            Intent intent = new Intent(getApplicationContext(), Activity2.class);
                            intent.putExtra("id", contacto.id);
                            intent.putExtra("nombre", contacto.nombre);
                            intent.putExtra("telefono", contacto.telefono);
                            intent.putExtra("direccion", contacto.direccion);
                            intent.putExtra("email", contacto.email);

                            startActivity(intent);
                        }
                    });
                }
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        LlenarTabla();
    }

    private void Buscar(){
        txtBuscar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String query = s.toString();
                filterTable(query);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void filterTable(String query) {
        for (int i = 1; i < tblAgenda.getChildCount(); i++) {
            TableRow row = (TableRow) tblAgenda.getChildAt(i);
            TextView textView = (TextView) row.getChildAt(0);
            String text = textView.getText().toString();
            if (text.toLowerCase().contains(query.toLowerCase())) {
                row.setVisibility(View.VISIBLE);
            } else {
                row.setVisibility(View.GONE);
            }
        }
    }
}