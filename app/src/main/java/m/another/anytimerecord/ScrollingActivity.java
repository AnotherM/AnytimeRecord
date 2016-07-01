package m.another.anytimerecord;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ScrollingActivity extends AppCompatActivity implements View.OnClickListener {
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
        setContentView(R.layout.activity_scrolling);
        //初始化控件
        moneyET = (EditText) findViewById(R.id.et_money);
        categoryET = (EditText) findViewById(R.id.et_category);
        dateTV = (TextView) findViewById(R.id.tv_date);
        Button dateBtn = (Button) findViewById(R.id.btn_date);
        timeTV = (TextView) findViewById(R.id.tv_time);
        Button timeBtn = (Button) findViewById(R.id.btn_time);
        noteET = (EditText) findViewById(R.id.et_notes);
        Button doneBtn = (Button) findViewById(R.id.btn_done);
        FloatingActionButton calculatorFAB = (FloatingActionButton) findViewById(R.id.fab_calculator);

        //响应事件
        assert dateBtn != null;
        dateBtn.setOnClickListener(this);
        assert timeBtn != null;
        timeBtn.setOnClickListener(this);
        assert calculatorFAB != null;
        calculatorFAB.setOnClickListener(this);
        assert doneBtn != null;
        doneBtn.setOnClickListener(this);
        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.rv_list);
        assert mRecyclerView != null;
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setNestedScrollingEnabled(true);
        dbOperator = new DBOperator(this);
        resetData();
        mRecyclerView.setAdapter(dataAdapter);


    }

    /*--------创建菜单--------*/
    //创建菜单
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_scrolling, menu);
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
        if (id == R.id.exit) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    /*-------创建菜单-------*/

    private void resetData() {
        dataBeanList = dbOperator.queryAll();
        dataAdapter = new DataAdapter(ScrollingActivity.this);
        dataAdapter.resetData(dataBeanList);
    }

    @Override
    public void onClick(final View v) {
        /*--------时间与日期-------*/
        Calendar c = Calendar.getInstance();//创建实例
        switch (v.getId()) {
            //时间按钮响应事件
            case R.id.btn_date:
                new DatePickerDialog(ScrollingActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                TextView show = (TextView) findViewById(R.id.tv_date);
                                //给小于10的数字添加0
                                if (monthOfYear < 10 && dayOfMonth > 10) {
                                    assert show != null;
                                    show.setText(new StringBuffer().append(year).append("/").append("0").append(monthOfYear + 1).append("/").append(dayOfMonth));
                                } else if (monthOfYear > 10 && dayOfMonth < 10) {
                                    assert show != null;
                                    show.setText(new StringBuffer().append(year).append("/").append(monthOfYear + 1).append("/").append("0").append(dayOfMonth));
                                } else if (monthOfYear < 10 && dayOfMonth < 10) {
                                    assert show != null;
                                    show.setText(new StringBuffer().append(year).append("/").append("0").append(monthOfYear + 1).append("/").append("0").append(dayOfMonth));
                                } else {
                                    assert show != null;
                                    show.setText(new StringBuffer().append(year).append("/").append(monthOfYear + 1).append("/").append(dayOfMonth));
                                }
                            }
                        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();
                break;
            //日期按钮响应事件
            case R.id.btn_time:
                new TimePickerDialog(ScrollingActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view,
                                                  int hourOfDay, int minute) {
                                TextView show = (TextView) findViewById(R.id.tv_time);
                                //给小于10的数字添加0
                                if (hourOfDay < 10 && minute > 10) {
                                    assert show != null;
                                    show.setText(new StringBuffer().append("0").append(hourOfDay).append(":").append(minute));
                                } else if (hourOfDay > 10 && minute < 10) {
                                    assert show != null;
                                    show.setText(new StringBuffer().append(hourOfDay).append(":").append("0").append(minute));
                                } else if (hourOfDay < 10 && minute < 10) {
                                    assert show != null;
                                    show.setText(new StringBuffer().append("0").append(hourOfDay).append(":").append("0").append(minute));
                                } else {
                                    assert show != null;
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
                    Toast.makeText(this, getResources().getString(R.string.input_error), Toast.LENGTH_LONG).show();//返回失败消息
                } else {
                    dbOperator.insert(moneyET.getText().toString().trim(), categoryET.getText().toString().trim(), dateTV.getText().toString().trim(), timeTV.getText().toString().trim(), noteET.getText().toString().trim());
                    Toast.makeText(this, getResources().getString(R.string.input_complete), Toast.LENGTH_LONG).show();
                    dataBeanList = dbOperator.queryAll();
                    dataAdapter.resetData(dataBeanList);
                }
                break;
        }
    }
}
