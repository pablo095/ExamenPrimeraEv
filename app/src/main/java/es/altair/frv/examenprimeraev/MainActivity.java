package es.altair.frv.examenprimeraev;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Date;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener {

    private ImageButton btnPerson1, btnPerson2, btnPerson3, btnPerson4;
    private Button btnDelete;
    private TextView txtPerson1, txtPerson2, txtPerson3, txtPerson4, txtUltima, txtUltimaFecha;

    private final int CODE1 = 1, CODE2 = 2, CODE3 = 3, CODE4 = 4;

    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtPerson1 = (TextView) findViewById(R.id.txtPerson1);
        txtPerson2 = (TextView) findViewById(R.id.txtPerson2);
        txtPerson3 = (TextView) findViewById(R.id.txtPerson3);
        txtPerson4 = (TextView) findViewById(R.id.txtPerson4);

        txtUltima = (TextView) findViewById(R.id.txtUltima);
        txtUltimaFecha = (TextView) findViewById(R.id.txtUltimaFecha);

        btnPerson1 = (ImageButton)findViewById(R.id.btnPerson1);
        btnPerson1.setOnClickListener(this);
        btnPerson1.setOnLongClickListener(this);
        btnPerson2 = (ImageButton)findViewById(R.id.btnPerson2);
        btnPerson2.setOnClickListener(this);
        btnPerson2.setOnLongClickListener(this);
        btnPerson3 = (ImageButton)findViewById(R.id.btnPerson3);
        btnPerson3.setOnClickListener(this);
        btnPerson3.setOnLongClickListener(this);
        btnPerson4 = (ImageButton)findViewById(R.id.btnPerson4);
        btnPerson4.setOnClickListener(this);
        btnPerson4.setOnLongClickListener(this);
        btnDelete = (Button)findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(this);




        sp = getSharedPreferences("datos", MODE_PRIVATE);

        showInfo();
    }

    private void showInfo() {
        txtPerson1.setText("");
        txtPerson2.setText("");
        txtPerson3.setText("");
        txtPerson4.setText("");
        txtUltima.setText("");
        txtUltimaFecha.setText("");

        txtPerson1.setText(sp.getString("name1", ""));
        txtPerson2.setText(sp.getString("name2", ""));
        txtPerson3.setText(sp.getString("name3", ""));
        txtPerson4.setText(sp.getString("name4", ""));

        txtUltima.setText(sp.getString("ultima", ""));
        txtUltimaFecha.setText(sp.getString("ultimaFecha", ""));
    }

    @Override
    public void onClick(View v) {
        String number = "", ultima = "";
        SharedPreferences.Editor editor = sp.edit();
        switch (v.getId()) {
            case R.id.btnPerson1:
                number = sp.getString("number1", "");
                ultima = sp.getString("name1", "");
                editor.commit();
                break;
            case R.id.btnPerson2:
                number = sp.getString("number2", "");
                ultima = sp.getString("name2", "");
                editor.commit();
                break;
            case R.id.btnPerson3:
                number = sp.getString("number3", "");
                ultima = sp.getString("name3", "");
                editor.commit();
                break;
            case R.id.btnPerson4:
                number = sp.getString("number4", "");
                ultima = sp.getString("name4", "");
                editor.commit();
                break;
            case R.id.btnDelete:
                editor.clear();
                editor.commit();
                showInfo();
                break;
        }
        if (!number.equals("")) {
            editor.putString("ultima", ultima);
            txtUltima.setText(ultima);
            editor.putString("ultimaFecha", new Date().toString());
            editor.commit();
            txtUltimaFecha.setText(sp.getString("ultimaFecha", ""));

            Intent i = new Intent(Intent.ACTION_DIAL);
            i.setData(Uri.parse("tel:" + number));
            startActivity(i);
        } else {
            editor.remove("ultima");
            editor.remove("ultimaFecha");
            editor.commit();
        }

    }

    @Override
    public boolean onLongClick(View v) {
        Intent i = new Intent(MainActivity.this, SecondActivity.class);
        switch (v.getId()) {
            case R.id.btnPerson1:
                startActivityForResult(i, CODE1);
                break;
            case R.id.btnPerson2:
                startActivityForResult(i, CODE2);
                break;
            case R.id.btnPerson3:
                startActivityForResult(i, CODE3);
                break;
            case R.id.btnPerson4:
                startActivityForResult(i, CODE4);
                break;
        }
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case CODE1:
                if (resultCode==RESULT_OK) {
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("name1", data.getStringExtra("name"));
                    editor.putString("number1", data.getStringExtra("number"));
                    editor.commit();
                }
                break;
            case CODE2:
                if (resultCode==RESULT_OK) {
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("name2", data.getStringExtra("name"));
                    editor.putString("number2", data.getStringExtra("number"));
                    editor.commit();
                }
                break;
            case CODE3:
                if (resultCode==RESULT_OK) {
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("name3", data.getStringExtra("name"));
                    editor.putString("number3", data.getStringExtra("number"));
                    editor.commit();
                }
                break;
            case CODE4:
                if (resultCode==RESULT_OK) {
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("name4", data.getStringExtra("name"));
                    editor.putString("number4", data.getStringExtra("number"));
                    editor.commit();
                }
                break;
        }
        showInfo();
    }
}
