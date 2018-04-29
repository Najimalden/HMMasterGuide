package mrsmaster.hmmasterguide;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

/*        Button hallo = (Button) findViewById(R.id.btnhallo);

       hallo.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               openMain2Activity();
           }
       });*/




    }

  /*  public void openMain2Activity() {
        Intent intent = new Intent(this, Main2Activity.class);
        startActivity(intent);
    }*/

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Intent intent = new Intent(this, Main2Activity.class);



        switch (item.getItemId()) {
            case R.id.item_back:
                setContentView(R.layout.activity_main);
                return true;
            case R.id.item_forward:
                setContentView(R.layout.activity_main3);
                return true;
            case R.id.item_home:
               //setContentView(R.layout.activity_main);
                startActivity(intent);
            default:
                return super.onOptionsItemSelected(item);
        }
    }



}
