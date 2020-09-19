package com.example.safofoh.myapplicationandroidproject;

        import android.app.Activity;
        import android.app.AlertDialog.Builder;
        import android.content.Context;
        import android.database.Cursor;
        import android.database.sqlite.SQLiteDatabase;
        import android.os.Bundle;
        import android.view.View;
        import android.view.View.OnClickListener;
        import android.widget.Button;
        import android.widget.EditText;

public class MainActivity extends Activity implements OnClickListener
{
    EditText editID,editTitle,editPages,editAuthor;
    Button btnAdd,btnDelete,btnUpdate,btnView,btnViewAll;
    SQLiteDatabase db;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editID=(EditText)findViewById(R.id.editID);
        editTitle=(EditText)findViewById(R.id.editTitle);
        editPages=(EditText)findViewById(R.id.editPages);
        editAuthor=(EditText)findViewById(R.id.editAuthor);

        btnAdd=(Button)findViewById(R.id.btnAdd);
        btnDelete=(Button)findViewById(R.id.btnDelete);
        btnUpdate=(Button)findViewById(R.id.btnUpdate);
        btnView=(Button)findViewById(R.id.btnView);
        btnViewAll=(Button)findViewById(R.id.btnViewAll);



        btnAdd.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        btnUpdate.setOnClickListener(this);
        btnView.setOnClickListener(this);
        btnViewAll.setOnClickListener(this);

        db=openOrCreateDatabase("BookDB", Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS book(ID VARCHAR,title VARCHAR,pages VARCHAR, author VARCHAR);");
    }
    public void onClick(View view)
    {
        if(view==btnAdd)
        {
            if(editID.getText().toString().trim().length()==0||
                    editTitle.getText().toString().trim().length()==0||
                    editAuthor.getText().toString().trim().length()==0||
                    editPages.getText().toString().trim().length()==0)
            {
                showMessage("Error", "Please enter all values");
                return;
            }
            db.execSQL("INSERT INTO book VALUES('"+editID.getText()+"','"+editTitle.getText()+
                            "','"+editPages.getText() +
                    "','"+editAuthor.getText()+"');");
            showMessage("Success", "Record added");
            clearText();
        }
        if(view==btnDelete)
        {
            if(editID.getText().toString().trim().length()==0)
            {
                showMessage("Error", "Please enter ID");
                return;
            }
            Cursor c=db.rawQuery("SELECT * FROM book WHERE ID='"+editID.getText()+"'", null);
            if(c.moveToFirst())
            {
                db.execSQL("DELETE FROM book WHERE ID='"+editID.getText()+"'");
                showMessage("Success", "Record Deleted");
            }
            else
            {
                showMessage("Error", "Invalid ID");
            }
            clearText();
        }
        if(view==btnUpdate)
        {
            if(editID.getText().toString().trim().length()==0)
            {
                showMessage("Error", "Please enter ID");
                return;
            }
            Cursor c=db.rawQuery("SELECT * FROM book WHERE ID='"+editID.getText()+"'", null);
            if(c.moveToFirst())
            {
                db.execSQL("UPDATE book SET title='"+editTitle.getText()+"',pages='"+editPages.getText()+
                        "',author='"+editAuthor.getText()+
                        "' WHERE ID='"+editID.getText()+"'");
                showMessage("Success", "Record Modified");
            }
            else
            {
                showMessage("Error", "Invalid ID");
            }
            clearText();
        }
        if(view==btnView)
        {
            if(editID.getText().toString().trim().length()==0)
            {
                showMessage("Error", "Please enter ID");
                return;
            }
            Cursor c=db.rawQuery("SELECT * FROM book WHERE ID='"+editID.getText()+"'", null);
            if(c.moveToFirst())
            {
                editTitle.setText(c.getString(1));
                editAuthor.setText(c.getString(3));
                editPages.setText(c.getString(2));
            }
            else
            {
                showMessage("Error", "Invalid ID");
                clearText();
            }
        }
        if(view==btnViewAll)
        {
            Cursor c=db.rawQuery("SELECT * FROM book", null);
            if(c.getCount()==0)
            {
                showMessage("Error", "No records found");
                return;
            }
            StringBuffer buffer=new StringBuffer();
            while(c.moveToNext())
            {
                buffer.append("ID: "+c.getString(0)+"\n");
                buffer.append("Title: "+c.getString(1)+"\n");
                buffer.append("Author: "+c.getString(3)+"\n");
                buffer.append("Pages: "+c.getString(2)+"\n\n");
            }
            showMessage("Student Details", buffer.toString());
        }

    }
    public void showMessage(String title,String message)
    {
        Builder builder=new Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
    public void clearText()
    {
        editID.setText("");
        editTitle.setText("");
        editAuthor.setText("");
        editPages.setText("");
        editID.requestFocus();
    }
}