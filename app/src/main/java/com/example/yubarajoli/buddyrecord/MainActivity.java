package com.example.yubarajoli.buddyrecord;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    BuddyDatabase buddyDatabase;
    EditText name, address, phone, email,id;
    Button buttonSave;
    ImageButton imageButtonView,imageButtonUpdate,imageButtonDelete;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         buddyDatabase= new BuddyDatabase(this);
        name=(EditText) findViewById(R.id.editTextName);
        address=(EditText) findViewById(R.id.editTextAddress);
        phone=(EditText)findViewById(R.id.editTextPhone);
        email=(EditText)findViewById(R.id.editTextEmail);
        id=(EditText)findViewById(R.id.editTextId);
        buttonSave=(Button)findViewById(R.id.buttonSave);
        imageButtonView=(ImageButton)findViewById(R.id.imageButtonView);
        imageButtonDelete=(ImageButton)findViewById(R.id.imageButtonDelete);
        imageButtonUpdate=(ImageButton)findViewById(R.id.imageButtonUpdate);

        addData();
        viewAll();
        updateData();
        deleteData();
    }
    public void addData(){
        buttonSave.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if((name.getText().toString().isEmpty())||(address.getText().toString().isEmpty())||
                                (phone.getText().toString().isEmpty())||(email.getText().toString().isEmpty())){
                            Toast.makeText(MainActivity.this,"Name,Address,Phone and email are required",Toast.LENGTH_LONG).show();
                        }
                        else{

                        boolean isInserted = buddyDatabase.insertData(name.getText().toString(), address.getText().toString(),
                                phone.getText().toString(), email.getText().toString());


                            if (isInserted == true) {
                                Toast.makeText(MainActivity.this, "Saved", Toast.LENGTH_LONG).show();
                            } else
                                Toast.makeText(MainActivity.this, "Failed to save", Toast.LENGTH_LONG).show();
                        }
                    }
                }
        );

}
    public void viewAll(){
         imageButtonView.setOnClickListener(
            new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (id.getText().toString().isEmpty()) {
                        Cursor res = buddyDatabase.getAllData();
                        if (res.getCount() == 0) {
                            showMessage("Error!!!", "There is no data in the database");
                            return;
                        }

                        StringBuffer buffer = new StringBuffer();
                        while (res.moveToNext()) {

                            buffer.append("ID: " + res.getString(0) + "\n");
                            buffer.append("Name: " + res.getString(1) + "\n");
                            buffer.append("Address: " + res.getString(2) + "\n");
                            buffer.append("Phone: " + res.getString(3) + "\n");
                            buffer.append("Email: " + res.getString(4) + "\n\n");
                        }
                        showMessage("Here is data", buffer.toString());
                    }
                    if(id.getText().length()!=0)
                        {
                        //int val= Integer.parseInt(id.getText().toString());
                        String val=id.getText().toString();
                        Cursor res=buddyDatabase.getById(val);
                        if (res.getCount() == 0) {
                            showMessage("Error!!!", "There is no data in the database");
                            return;
                        }
                        while(res.moveToNext()) {


                            StringBuffer buffer = new StringBuffer();
                            buffer.append("ID: " + res.getString(0) + "\n");
                            buffer.append("Name: " + res.getString(1) + "\n");
                            buffer.append("Address: " + res.getString(2) + "\n");
                            buffer.append("Phone: " + res.getString(3) + "\n");
                            buffer.append("Email: " + res.getString(4) + "\n\n");

                            showMessage("Here is data", buffer.toString());
                        }

                    }
                }



            }
         );
    }
    public void updateData(){
        imageButtonUpdate.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(id.getText().toString().isEmpty()){
                            id.setError("ID is required");
                        }
                        else {
                       boolean isUpdated= buddyDatabase.updateData(id.getText().toString(),name.getText().toString(),
                               address.getText().toString(),phone.getText().toString(),email.getText().toString());

                            if (isUpdated == true) {
                                Toast.makeText(MainActivity.this, "Successfully updated", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(MainActivity.this, "Upadte failed!!!", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                }
        );
    }
    public void deleteData(){
        imageButtonDelete.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(id.getText().toString().isEmpty()){
                            Toast.makeText(MainActivity.this,"Id required",Toast.LENGTH_LONG).show();
                            return;
                        }
                        boolean isdeleted=buddyDatabase.deleteData(id.getText().toString());
                        if (isdeleted==true)
                            Toast.makeText(MainActivity.this,"Successfully deleted",Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(MainActivity.this,"Failed to delete",Toast.LENGTH_LONG).show();
                    }
                }
        );
    }
    public void showMessage(String title,String message){
        AlertDialog.Builder builder= new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();

    }

}
