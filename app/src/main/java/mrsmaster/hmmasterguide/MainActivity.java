package mrsmaster.hmmasterguide;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);






        final Intent intent = new Intent(this, SchwerpunktModul.class);

        Button meinplan = (Button) findViewById(R.id.btnplan);

        meinplan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
            }
        });

    }

//    Aufruf des Menu-Layouts und Anzege der Action bar

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

//Dadurch kann man mit dem Android-Zurück-Button zum Splash-Screen wechseln
    @Override
    public void onBackPressed()
    {

    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId()) {
            case R.id.home:
                final Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                return true;
            case R.id.first:
                final Intent intent1 = new Intent(this, SchwerpunktModul.class);
                startActivity(intent1);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }



}
