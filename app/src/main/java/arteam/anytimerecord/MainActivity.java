package arteam.anytimerecord;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    //声明控件
    private static List<DataBean> dataBeanList = new ArrayList<>();
    private EditText moneyET;
    private EditText categoryET;
    private TextView dateTV;
    private TextView timeTV;
    private EditText noteET;
    private DBOperator dbOperator;
    private DataAdapter dataAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //初始化控件
        moneyET = (EditText) findViewById(R.id.et_money);
        categoryET = (EditText) findViewById(R.id.et_category);
        dateTV = (TextView) findViewById(R.id.tv_date);
        Button dateBtn = (Button) findViewById(R.id.btn_date);
        timeTV = (TextView) findViewById(R.id.tv_time);
        Button timeBtn = (Button) findViewById(R.id.btn_time);
        noteET = (EditText) findViewById(R.id.et_notes);
        Button doneBtn = (Button) findViewById(R.id.btn_done);
        TextView tapTV = (TextView) findViewById(R.id.tv_tap_for_detail);
        FloatingActionButton calculatorFAB = (FloatingActionButton) findViewById(R.id.fab_calculator);


        //响应事件
        dateBtn.setOnClickListener(this);
        timeBtn.setOnClickListener(this);
        calculatorFAB.setOnClickListener(this);
        doneBtn.setOnClickListener(this);
        tapTV.setOnClickListener(this);
        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.rv_list);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setNestedScrollingEnabled(true);
        dbOperator = new DBOperator(this, DBOpenHelper.TABLE_NAME);
        setData();
        mRecyclerView.setAdapter(dataAdapter);


    }

    /*--------创建菜单--------*/
    //创建菜单
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    //菜单响应事件
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.about) {
            new AlertDialog.Builder(this)
                    .setTitle(R.string.thanks)
                    .setView(R.layout.about)
                    .setPositiveButton(R.string.close, null)
                    .show();
        }
        if (id == R.id.update_detail) {
            new AlertDialog.Builder(this)
                    .setTitle(R.string.update_detail)
                    .setView(R.layout.update_detail)
                    .setPositiveButton(R.string.close, null)
                    .show();
        }
        if (id == R.id.exit) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    /*-------创建菜单-------*/

    private void setData() {

        dataBeanList = dbOperator.queryAll();
        dataAdapter = new DataAdapter(MainActivity.this);
        dataAdapter.resetData(dataBeanList);
    }

    @Override
    public void onClick(final View v) {
        /*--------时间与日期-------*/
        Calendar c = Calendar.getInstance();//创建实例
        switch (v.getId()) {
            //时间按钮响应事件
            case R.id.btn_date:
                new DatePickerDialog(MainActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                TextView show = (TextView) findViewById(R.id.tv_date);
                                //给小于10的数字添加0
                                if (monthOfYear < 10 && dayOfMonth > 10) {
                                    show.setText(new StringBuffer().append(year).append("/").append("0").append(monthOfYear + 1).append("/").append(dayOfMonth));
                                } else if (monthOfYear > 10 && dayOfMonth < 10) {
                                    show.setText(new StringBuffer().append(year).append("/").append(monthOfYear + 1).append("/").append("0").append(dayOfMonth));
                                } else if (monthOfYear < 10 && dayOfMonth < 10) {
                                    show.setText(new StringBuffer().append(year).append("/").append("0").append(monthOfYear + 1).append("/").append("0").append(dayOfMonth));
                                } else {
                                    show.setText(new StringBuffer().append(year).append("/").append(monthOfYear + 1).append("/").append(dayOfMonth));
                                }
                            }
                        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();
                break;
            //日期按钮响应事件
            case R.id.btn_time:
                new TimePickerDialog(MainActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view,
                                                  int hourOfDay, int minute) {
                                TextView show = (TextView) findViewById(R.id.tv_time);
                                //给小于10的数字添加0
                                if (hourOfDay < 10 && minute > 10) {
                                    show.setText(new StringBuffer().append("0").append(hourOfDay).append(":").append(minute));
                                } else if (hourOfDay > 10 && minute < 10) {
                                    show.setText(new StringBuffer().append(hourOfDay).append(":").append("0").append(minute));
                                } else if (hourOfDay < 10 && minute < 10) {
                                    show.setText(new StringBuffer().append("0").append(hourOfDay).append(":").append("0").append(minute));
                                } else {
                                    show.setText(new StringBuffer().append(hourOfDay).append(":").append(minute));
                                }
                            }
                        }, c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), true).show();
                break;
            /*-------时间与日期-------*/

            //开启计算器
            case R.id.fab_calculator:
                Intent intent = new Intent(this, CalculatorActivity.class);
                startActivity(intent);
                break;

            case R.id.btn_done:
                if (TextUtils.isEmpty(moneyET.getText()) || TextUtils.isEmpty(dateTV.getText()) || TextUtils.isEmpty(timeTV.getText())) {
                    Snackbar.make(v, getResources().getString(R.string.input_error), Snackbar.LENGTH_LONG).show();//返回失败消息
                } else {
                    dbOperator.insert(moneyET.getText().toString().trim(), categoryET.getText().toString().trim(), dateTV.getText().toString().trim(), timeTV.getText().toString().trim(), noteET.getText().toString().trim());
                    Snackbar.make(v, getResources().getString(R.string.input_complete), Snackbar.LENGTH_LONG).show();
                    dataBeanList = dbOperator.queryAll();
                    dataAdapter.resetData(dataBeanList);
                }
                break;
            case R.id.tv_tap_for_detail:
                new AlertDialog.Builder(this)
                        .setTitle(R.string.ins)
                        .setView(R.layout.instructions)
                        .setPositiveButton(R.string.close, null)
                        .show();
        }
    }
}
