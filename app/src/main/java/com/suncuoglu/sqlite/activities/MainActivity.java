package com.suncuoglu.sqlite.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.suncuoglu.sqlite.db.DatabaseHelper;
import com.suncuoglu.sqlite.adapters.NoteAdapter;
import com.suncuoglu.sqlite.R;
import com.suncuoglu.sqlite.models.User;
import com.suncuoglu.sqlite.fragments.EditBottomSheet;
import com.suncuoglu.sqlite.services.BottomSheetClick;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Calendar;

/*
12.01.2021 by Şansal Uncuoğlu
*/

public class MainActivity extends AppCompatActivity implements BottomSheetClick {

    TextInputEditText name;
    TextInputEditText surname;
    TextInputEditText username;
    TextInputEditText password;

    TextView birthday;

    Button btn_Add;
    Button btn_List;
    RecyclerView user_recylerview;
    String noteID = "";
    ArrayList<User> noteList = new ArrayList<>();

    TextView user_name;

    EditBottomSheet editBottomSheet;

    Dialog delete_dialog;
    Dialog edit_dialog;

    String birthday_text;

    TextView null_text;

    String dialog_name_text;
    String dialog_surname_text;
    String dialog_username_text;
    String dialog_password_text;
    String dialog_birthday_text;

    TextInputEditText dialog_name;
    TextInputEditText dialog_surname;
    TextInputEditText dialog_username;
    TextInputEditText dialog_password;
    TextView dialog_birthday;

    int date_position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setViews(); //Viewlarımı tanımladığım method
        setClick(); //Click eventleri tek bir method içerisinde yönetebilmem için method
    }

    private void setViews() {

        name = findViewById(R.id.name);
        surname = findViewById(R.id.surname);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        birthday = findViewById(R.id.birthday);

        btn_Add = findViewById(R.id.btn_Add);
        btn_List = findViewById(R.id.btn_List);
        user_recylerview = findViewById(R.id.user_recylerview);

        null_text = findViewById(R.id.null_text);

    }

    void setClick() {
        btn_List.setOnClickListener(MyListener);
        birthday.setOnClickListener(MyListener);
        btn_Add.setOnClickListener(MyListener);
    }

    private View.OnClickListener MyListener = new View.OnClickListener() {
        @SuppressLint("NewApi")
        public void onClick(View v) {

            switch (v.getId()) {

                case R.id.btn_List:

                    getList(); // Database listeleme methodum

                    break;

                case R.id.birthday:
                    date_position = 0;
                    setDate(); //Tarih seçmemize olanak sunan picker

                    break;

                case R.id.btn_Add:

                    addUser(); // Database kullanıcı ekleme

                    break;

                default:
                    break;
            }
        }
    };

    public void setDate() {

        Calendar mcurrentTime = Calendar.getInstance();
        int year = mcurrentTime.get(Calendar.YEAR);//Güncel Yılı alıyoruz
        int month = mcurrentTime.get(Calendar.MONTH);//Güncel Ayı alıyoruz
        int day = mcurrentTime.get(Calendar.DAY_OF_MONTH);//Güncel Günü alıyoruz

        DatePickerDialog datePicker;//Datepicker objemiz
        datePicker = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {

                int month_count = monthOfYear + 1;

                birthday_text = dayOfMonth + "." + month_count + "." + year;

                if (date_position == 0) {
                    birthday.setText(birthday_text);
                } else {
                    dialog_birthday.setText(birthday_text);
                }
            }
        }, year, month, day);
        datePicker.setTitle("Tarih Seçiniz");
        datePicker.setButton(DatePickerDialog.BUTTON_POSITIVE, "Ayarla", datePicker);
        datePicker.setButton(DatePickerDialog.BUTTON_NEGATIVE, "İptal", datePicker);

        datePicker.show();
    }

    void getList() {
        DatabaseHelper db = new DatabaseHelper(getApplicationContext());
        noteList = db.getNoteList();

        if (noteList.size() == 0) {
            null_text.setAlpha(1.0f);
        } else {
            null_text.setAlpha(0f);
        }


        NoteAdapter adp = new NoteAdapter(this, noteList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        user_recylerview.setLayoutManager(layoutManager);
        user_recylerview.setHasFixedSize(true);
        user_recylerview.setAdapter(adp);

        adp.setOnItemClickListener(onItemNoteClickListener);
    }

    View.OnClickListener onItemNoteClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) view.getTag();
            int i = viewHolder.getAdapterPosition();
            User item = noteList.get(i);
            user_name = view.findViewById(R.id.user_name);
            noteID = item.getNote_id();
            dialog_name_text = item.getNote_name();
            dialog_surname_text = item.getNote_surname();
            dialog_username_text = item.getNote_username();
            dialog_password_text = item.getNote_password();
            dialog_birthday_text = item.getNote_birthday();


            editBottomSheet = EditBottomSheet.newInstance(MainActivity.this);
            editBottomSheet.show(getSupportFragmentManager(), "ModalBottomSheet");

        }
    };

    public void addUser() {

        if (TextUtils.isEmpty(name.getText().toString().trim())) {
            name.setError("Lütfen adınızı giriniz");
        } else if (TextUtils.isEmpty(surname.getText().toString().trim())) {
            surname.setError("Lütfen soyadınızı giriniz");
        } else if (TextUtils.isEmpty(username.getText().toString().trim())) {
            username.setError("Lütfen kullanıcı adınızı giriniz");
        } else if (TextUtils.isEmpty(password.getText().toString().trim())) {
            password.setError("Lütfen şifrenizi giriniz");
        } else if (birthday.getText().toString().trim().equals("Seçim Yapınız...")) {
            Toast.makeText(this, "Lütfen doğum tarihinizi seçiniz", Toast.LENGTH_SHORT).show();
        } else {
            DatabaseHelper db = new DatabaseHelper(getApplicationContext());
            db.addUser(name.getText().toString(), surname.getText().toString(), username.getText().toString(),
                    password.getText().toString(), birthday.getText().toString());
            db.close();
            name.setText("");
            surname.setText("");
            username.setText("");
            password.setText("");
            birthday.setText("Seçim Yapınız...");
            getList();
        }

    }

    public void deleteUser() {
        if (!noteID.equals("")) {
            DatabaseHelper db = new DatabaseHelper(getApplicationContext());
            db.deleteUser(noteID);
            db.close();

            Toast.makeText(getApplicationContext(), "Not silindi", Toast.LENGTH_SHORT).show();
            noteID = "";
            //etNote.setText("");
            getList();
            editBottomSheet.dismiss();
        } else
            Toast.makeText(getApplicationContext(), "Lütfen silinecek notu seçiniz", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void delete() {

        delete_dialog = new Dialog(this);
        delete_dialog.setContentView(R.layout.dialog_delete);
        delete_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        TextView btn_yes = delete_dialog.findViewById(R.id.btn_yes);
        TextView btn_no = delete_dialog.findViewById(R.id.btn_no);

        btn_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                deleteUser();
                delete_dialog.dismiss();

            }
        });

        btn_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                delete_dialog.dismiss();

            }
        });

        delete_dialog.show();

    }

    @Override
    public void edit() {

        edit_dialog = new Dialog(this);
        edit_dialog.setContentView(R.layout.dialog_edit);
        edit_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        dialog_name = edit_dialog.findViewById(R.id.dialog_name);
        dialog_surname = edit_dialog.findViewById(R.id.dialog_surname);
        dialog_username = edit_dialog.findViewById(R.id.dialog_username);
        dialog_password = edit_dialog.findViewById(R.id.dialog_password);
        dialog_birthday = edit_dialog.findViewById(R.id.dialog_birthday);
        TextView btn_cancel = edit_dialog.findViewById(R.id.btn_cancel);
        TextView btn_update = edit_dialog.findViewById(R.id.btn_update);

        dialog_birthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                date_position = 1;
                setDate();
            }
        });

        dialog_name.setText(dialog_name_text);
        dialog_surname.setText(dialog_surname_text);
        dialog_username.setText(dialog_username_text);
        dialog_password.setText(dialog_password_text);
        dialog_birthday.setText(dialog_birthday_text);

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit_dialog.dismiss();
            }
        });

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHelper db = new DatabaseHelper(getApplicationContext());
                db.updateUser(dialog_name.getText().toString(), dialog_surname.getText().toString(), dialog_username.getText().toString(),
                        dialog_password.getText().toString(), dialog_birthday.getText().toString(), noteID);
                db.close();
                getList();
                edit_dialog.dismiss();
                editBottomSheet.dismiss();
            }
        });

        edit_dialog.show();

    }
}