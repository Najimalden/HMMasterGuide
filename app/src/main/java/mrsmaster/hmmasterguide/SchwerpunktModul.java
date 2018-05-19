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





//      Übergabe aus der Datenbank
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
        databaseAccess.open();
        List<String> strGetSchwerpunkte = databaseAccess.getSchwerpunkte();


// Umwandlung von Array to String[]
        String[] arraySchwerpunkt = new String[strGetSchwerpunkte.size()];
        arraySchwerpunkt = strGetSchwerpunkte.toArray(arraySchwerpunkt);


        btnschwerpunkte = (Button) findViewById(R.id.btnschwerpunkt);
        btnmodule = (Button) findViewById(R.id.btnmodule);

/*
        mItemSelected = (TextView) findViewById(R.id.textview_schwerpunkte);
        mItemSelected1 = (TextView) findViewById(R.id.textview_module);
*/


//        Einträge vom Arrays holen
        listItems = arraySchwerpunkt;
        checkedItems = new boolean[listItems.length];

        listItemsm = getResources().getStringArray(R.array.Module);
        checkedItemsm = new boolean[listItemsm.length];
//                        neu 0 und 1
        checkedItemsm[0] = true;
        checkedItemsm[1] = true;
        mUserItemsm.add(0);
        mUserItemsm.add(1);





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

                    }

                });




//              das Schließen des Dialogs durch den Zürück-Button wird verboten
                mBuilder.setCancelable(false);



                AlertDialog mDialog = mBuilder.create();


                mDialog.show();



            }


        });





//      !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!11

//      Button Module
        btnmodule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder mBuilder1 = new AlertDialog.Builder(SchwerpunktModul.this);
                mBuilder1.setTitle(R.string.dialog_title1);
                mBuilder1.setMultiChoiceItems(listItemsm, checkedItemsm, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int positionm, boolean isCheckedm) {



//
                    if (checkedItemsm[0] == false || checkedItemsm[1] == false){

                    Context context = getApplicationContext();
                        CharSequence text = "Die ersten zwei Module sind Pflichtmodule!";
                        int duration = Toast.LENGTH_LONG;

                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                        checkedItemsm[0] = true;
                        checkedItemsm[1] = true;

                    }


                        if (positionm >= 2) {
                            if (isCheckedm) {
                                mUserItemsm.add(positionm);
                            } else {
                                mUserItemsm.remove(Integer.valueOf(positionm));
                            }
                        }



//
                    }



                });

                mBuilder1.setCancelable(false);
                mBuilder1.setPositiveButton(R.string.ok_label, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        arListmodule.clear();




                        String itemm = "";
                        for (int i1 = 0; i1 < mUserItemsm.size(); i1++) {
//                            itemm = itemm + listItemsm[mUserItemsm.get(i1)];
                            //                          Array befüllen
                            arListmodule.add(listItemsm[mUserItemsm.get(i1)]);
//                           wenn es nicht das letzte Item ist, dann füge ein Komma hinzu
/*                            if (i1 != mUserItems.size() - 1) {
                                itemm = itemm + ", ";
                            }*/
                        }

//                        mItemSelected.setText(item);

//                      Listview listschwerp wird aktualisiert
                        arrayAdapter1.notifyDataSetChanged();


//                        neu 0 und 1
                        checkedItemsm[0] = true;
                        checkedItemsm[1] = true;

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
                        for (int i1 = 2; i1 < checkedItemsm.length; i1++) {
                            checkedItemsm[i1] = false;
                            mUserItemsm.clear();
/*//                            mItemSelected.setText("");*/
                            arListmodule.clear();
                            checkedItemsm[0] = true;
                            checkedItemsm[1] = true;
                            mUserItemsm.add(0);
                            mUserItemsm.add(1);
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












}



