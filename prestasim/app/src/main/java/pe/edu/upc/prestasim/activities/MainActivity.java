package pe.edu.upc.prestasim.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import pe.edu.upc.prestasim.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this);

        Button btnRegister = (Button) findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent i;
        switch (v.getId()) {
            case R.id.btnLogin:
                i = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(i);
                finish();
                break;
            case R.id.btnRegister:
                i = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(i);
                finish();
                break;
        }
    }

    @Override
    public void onBackPressed() {}
}
