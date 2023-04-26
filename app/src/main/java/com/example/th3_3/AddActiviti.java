package com.example.th3_3;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.th3_3.databinding.ActivityAddActivitiBinding;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import DAL.SQLiteHelper;
import model.Item;

public class AddActiviti extends AppCompatActivity implements View.OnClickListener{
    public Spinner sp;
    private EditText eTitle, ePrice, eDate;
    private Button btUpdate,btCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_activiti);
        initView();
        btCancel.setOnClickListener(this);
        btUpdate.setOnClickListener(this);
        eDate.setOnClickListener(this);
    }

    private void initView() {
        sp = findViewById(R.id.spinnerCategory);
        eTitle = findViewById(R.id.tvTitle);
        ePrice = findViewById(R.id.tvPrice);
        eDate = findViewById(R.id.tvDate);
        btUpdate = findViewById(R.id.btUpdate);
        btCancel = findViewById(R.id.btCancel);
//        btCancel.setOnClickListener(this);
//        btUpdate.setOnClickListener(this);
        List<String> listCategory = new ArrayList<>();
        listCategory.add("an com");
        listCategory.add("quan ao 1");

        listCategory.add("quan ao 2");

        listCategory.add("quan ao 3");

        listCategory.add("quan ao 4");
        sp.setAdapter(new ArrayAdapter<String>(this, R.layout.item_spinner, listCategory));
    }


    @Override
    public void onClick(View view) {
        if(view == eDate) {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog dialog = new DatePickerDialog(AddActiviti.this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    String date = "";
                    if (month > 8) {
                        date = dayOfMonth + "/" + month + "/" + year;
                    } else date = dayOfMonth + "/" + "0" + month + "/" + year;
                    eDate.setText(date);
                }
            }, year, month, day);
            dialog.show();

        }
        if (view == btCancel) {
            finish();
        }
        if (view == btUpdate) {
            String t = eTitle.getText().toString();
            String p = ePrice.getText().toString();
            String cate = sp.getSelectedItem().toString();

            String d = eDate.getText().toString();
            if (!t.isEmpty() && p.matches("\\d+")) {
                Item i = new Item(t, cate, p, d);
                SQLiteHelper db = new SQLiteHelper(this);
                db.addItem(i);
                finish();
            }
        }
    }

}