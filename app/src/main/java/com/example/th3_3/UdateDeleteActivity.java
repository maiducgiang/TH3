package com.example.th3_3;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.th3_3.databinding.ActivityUdateDeleteBinding;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import DAL.SQLiteHelper;
import model.Item;

public class UdateDeleteActivity extends AppCompatActivity implements OnClickListener {
    public Spinner sp;
    private EditText eTitle, ePrice, eDate;
    private Button btUpdate,btCancel, btRemove;
    private  Item item;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_udate_delete);
        initView();
        btCancel.setOnClickListener(this);
        btUpdate.setOnClickListener(this);
        btRemove.setOnClickListener(this);
        eDate.setOnClickListener(this);
        Intent intent = getIntent();
        item =(Item) intent.getSerializableExtra("item");
        eTitle.setText(item.getTitle());
        eDate.setText(item.getDate());
        ePrice.setText(item.getPrice());

        int p = 0;
        for(int i = 0; i <sp.getCount(); i++){
            if(sp.getItemAtPosition(i).toString().equalsIgnoreCase(item.getCategory())){
                p = i;
                break;
            }
        }
        sp.setSelection(p);
    }
    private void initView() {
        sp = findViewById(R.id.spinnerCategory);
        eTitle = findViewById(R.id.tvTitle);
        ePrice = findViewById(R.id.tvPrice);
        eDate = findViewById(R.id.tvDate);
        btUpdate = findViewById(R.id.btUpdate);
        btCancel = findViewById(R.id.btBack);
        btRemove = findViewById(R.id.btRemove);
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
        SQLiteHelper db = new SQLiteHelper(this);
        if(view == eDate) {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog dialog = new DatePickerDialog(UdateDeleteActivity.this, new DatePickerDialog.OnDateSetListener() {
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
        if(view == btRemove){
            int id = item.getId();
            AlertDialog.Builder buider = new AlertDialog.Builder(view.getContext());
            buider.setTitle("thong bao xoa");
            buider.setMessage("Ban co chac chan muon xoa"+item.getTitle()+"khong");
//            buider.setIcon(R.drawable.)
            buider.setPositiveButton("Co", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    db.delete(item.getId());
                    finish();
                }
            });
            buider.setNegativeButton("khong", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
        }
        if (view == btUpdate) {
            String t = eTitle.getText().toString();
            String p = ePrice.getText().toString();
            String cate = sp.getSelectedItem().toString();

            String d = eDate.getText().toString();
            if (!t.isEmpty() && p.matches("\\d+")) {
                Item i = new Item(t, cate, p, d);
//                SQLiteHelper db = new SQLiteHelper(this);
                db.update(i);
                finish();
            }
        }
    }
}