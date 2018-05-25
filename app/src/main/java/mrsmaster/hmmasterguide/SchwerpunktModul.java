package mrsmaster.hmmasterguide;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class SchwerpunktModul extends AppCompatActivity {


    Button btnmodule;
    Button btnschwerpunkte;

    Integer intModulButtonAlreadyUsed = 1;
    Integer intSoWiSe = 1;
    Integer dbid;

    String [] listItems;
    String [] listItemsm;

    boolean[] checkedItems;
    boolean[] checkedItemsm;
    //    in das Array wird die Position der checked Items geschriben
//    z.B. bei der Auswahl von item 1 und 3 mUserItems = [0 2]
    ArrayList<Integer> mUserItems = new ArrayList<>();
    ArrayList<Integer> mUserItemsm = new ArrayList<>();
    //    Array für die Schwerpunkte
    ArrayList<String> arListschwerp = new ArrayList<>();
    //    Array für die Module
    ArrayList<String> arListmodule = new ArrayList<>();

    ListView listschwerp;
    ListView listmodule;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schwerpunkte);

        final Button btnSoWiSe = (Button) findViewById(R.id.btnSoWiSe);

//      Übergabe aus der Datenbank der vorhandenen Schwerpunkte
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
        databaseAccess.open();
        List<String> strGetSchwerpunkte = databaseAccess.getSchwerpunkte();
        databaseAccess.close();



//      Umwandlung von Array to String[] für die Schwerpunkte
        String[] arraySchwerpunkt = new String[strGetSchwerpunkte.size()];
        arraySchwerpunkt = strGetSchwerpunkte.toArray(arraySchwerpunkt);


        btnmodule =(Button)findViewById(R.id.btnSoWiSe);
        btnschwerpunkte = (Button) findViewById(R.id.btnschwerpunkt);
        btnmodule = (Button) findViewById(R.id.btnmodule);

/*
        mItemSelected = (TextView) findViewById(R.id.textview_schwerpunkte);
        mItemSelected1 = (TextView) findViewById(R.id.textview_module);
*/


//        Einträge vom Arrays holen
        listItems = arraySchwerpunkt;
        checkedItems = new boolean[listItems.length];




//      ListViews für die Anzeige der Wahl
        listschwerp = (ListView) findViewById(R.id.list_schwerp);
        listmodule = (ListView) findViewById(R.id.list_module);


        listmodule.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 1) {
                    Context context = getApplicationContext();
                    CharSequence text = "Ziel des Moduls Geodateninfrastruktur ist es, Kenntnis und Anwendung der wichtigsten Standards und Normen in der Geoinformatik sowie deren technischen Grundlagen zu vermitteln. In diesem Modul sollen die politischen - und rechtlichen Grundlagen der nationalen und internationalen Geodateninfrastrukturen (z.B. INSPIRE) vermittelt werden.";
                    int duration = Toast.LENGTH_LONG;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();

                }
                return true;
            }
        });





/*        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arListschwerp);
        listschwerp.setAdapter(arrayAdapter);*/

/*        final ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arListmodule);
        listmodule.setAdapter(arrayAdapter1);*/


        final ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(
                this, android.R.layout.simple_list_item_1, arListschwerp){

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view =super.getView(position, convertView, parent);

                TextView textView=(TextView) view.findViewById(android.R.id.text1);

//            YOUR CHOICE OF COLOR
                textView.setTextColor(Color.BLACK);
                textView.setTextSize(10);
                textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

                return view;
            }
        };

//    SET THE ADAPTER TO LISTVIEW
        listschwerp.setAdapter(arrayAdapter);



        final ArrayAdapter<String> arrayAdapter1=new ArrayAdapter<String>(
                this, android.R.layout.simple_list_item_1, arListmodule){

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view =super.getView(position, convertView, parent);

                TextView textView=(TextView) view.findViewById(android.R.id.text1);

            /*YOUR CHOICE OF COLOR*/
                textView.setTextColor(Color.BLACK);
                textView.setTextSize(10);
                textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

                return view;
            }
        };

    /*SET THE ADAPTER TO LISTVIEW*/
        listmodule.setAdapter(arrayAdapter1);


        arrayAdapter.notifyDataSetInvalidated();



//!!!!!!!!!!!!!!!!!!Anfang Dialoge!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!



//      Button Schwerpunkte
        btnschwerpunkte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder mBuilder = new AlertDialog.Builder(SchwerpunktModul.this);
                mBuilder.setTitle(R.string.dialog_title);
                mBuilder.setSingleChoiceItems(listItems, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i1) {
                        mUserItems.add(i1);

                        arListschwerp.clear();
                        String item = "";

//                        id für db search
                        dbid = i1 + 1;


//                          Array befüllen
                        arListschwerp.add(listItems[(i1)]);




//                          !!!SingleChoice-Lösung damit der alte wert verschwindet
                        mUserItems.clear();



//                      Listview listschwerp wird aktualisiert
                        arrayAdapter.notifyDataSetChanged();

                        dialog.cancel();

                        // Übergabe aus der Datenbank der vorhandenen Module
                        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(SchwerpunktModul.this);
                        databaseAccess.open();
                        List<String> strGetModule = databaseAccess.getModule(dbid,intSoWiSe);
                        databaseAccess.close();
                        // Auswahl der Pflichtmodule
                        List<String> compulsoryModuls = strGetModule.subList(0,4);

                        // Umwandlung von Array to String[] für die Module
                        String[] compulsoryModulsArray = new  String[compulsoryModuls.size()];
                        compulsoryModulsArray = compulsoryModuls.toArray(compulsoryModulsArray);


                    }

                });




//              das Schließen des Dialogs durch den Zürück-Button wird verboten
                mBuilder.setCancelable(false);



                AlertDialog mDialog = mBuilder.create();


                mDialog.show();



            }


        });

//      Botton Sommer-Winter-Semester
        btnSoWiSe.setTag(1);
        btnSoWiSe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int status = (Integer) v.getTag();
                if(status == 0) {
                    btnSoWiSe.setText("Sommer");
                    intSoWiSe = 1;
                    v.setTag(1);
                } else {
                    btnSoWiSe.setText("Winter");
                    intSoWiSe = 2;
                    v.setTag(0); //pause
                }
            }
        });

//      !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!11

//      Button Module
        btnmodule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                writeToSQLDB();
                // Übergabe aus der Datenbank der vorhandenen Module
                DatabaseAccess databaseAccess = DatabaseAccess.getInstance(SchwerpunktModul.this);
                databaseAccess.open();
                List<String> strGetModule = databaseAccess.getModule(dbid,intSoWiSe);
                databaseAccess.close();
                // Auswahl der Wahlpflichtmodule
                List<String> electoralModuls = strGetModule.subList(4,8);

//              Umwandlung von Array to String[] für die Module
                String[] electoralModulsArray = new  String[electoralModuls.size()];
                electoralModulsArray = electoralModuls.toArray(electoralModulsArray);

                listItemsm = electoralModulsArray;
                listItemsm = electoralModulsArray;
                if(intModulButtonAlreadyUsed == 1){
                    checkedItemsm = new boolean[listItemsm.length];
                    intModulButtonAlreadyUsed = 2;
                }




                final AlertDialog.Builder mBuilder1 = new AlertDialog.Builder(SchwerpunktModul.this);
                mBuilder1.setTitle(R.string.dialog_title1);
                mBuilder1.setMultiChoiceItems(listItemsm, checkedItemsm, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int positionm, boolean isCheckedm) {




                            if (isCheckedm) {
                                mUserItemsm.add(positionm);
                            } else {
                                mUserItemsm.remove(Integer.valueOf(positionm));
                            }
                        }



//




                });

                mBuilder1.setCancelable(false);
                mBuilder1.setPositiveButton(R.string.ok_label, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        arListmodule.clear();




                        for (int i1 = 0; i1 < mUserItemsm.size(); i1++) {
//                            itemm = itemm + listItemsm[mUserItemsm.get(i1)];
                            //                          Array befüllen
                            arListmodule.add(listItemsm[mUserItemsm.get(i1)]);
//                           wenn es nicht das letzte Item ist, dann füge ein Komma hinzu
/*                            if (i1 != mUserItems.size() - 1) {
                                itemm = itemm + ", ";
                            }*/
                        }


//                      Listview listschwerp wird aktualisiert
                        arrayAdapter1.notifyDataSetChanged();



                    }


                });


                mBuilder1.setNegativeButton(R.string.dismiss_label, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i1) {
                        dialogInterface.dismiss();
                    }
                });




                mBuilder1.setNeutralButton(R.string.clear_all_label, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
//                        neu 0 und 1
                        for (int i1 = 0; i1 < checkedItemsm.length; i1++) {
                            checkedItemsm[i1] = false;
                            mUserItemsm.clear();
                            arListmodule.clear();
                        }


                        arrayAdapter1.notifyDataSetChanged();

                    }


                });

                AlertDialog mDialog = mBuilder1.create();


                mDialog.show();


            }


        });

    }





    //    Aufruf des Menu-Layouts und Anzege der Action bar

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
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

    public void writeToSQLDB (){
        List<String> listToWriteToSQLDB = new ArrayList<String>();
        listToWriteToSQLDB.add("Raumanalysen und regionale Planungsprozesse");
        listToWriteToSQLDB.add("Geo-Monitoring");
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(SchwerpunktModul.this);
        databaseAccess.open();
        databaseAccess.setChoosedModuls(listToWriteToSQLDB);
        databaseAccess.close();
    }
}



