package m.another.anytimerecord;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;


@SuppressWarnings("ALL")
public class EditActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText moneyET;
    private EditText categoryET;
    private TextView dateTV;
    private Button dateBtn;
    private TextView timeTV;
    private Button timeBtn;
    private EditText noteET;
    private Button calculatorBtn;
    private FloatingActionButton doneFAB;
    private String getMoney;
    private String getCategory;
    private String getDate;
    private String getTime;
    private String getNote;
    private DBOperator dbOperator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        moneyET = (EditText) findViewById(R.id.et_money_EA);
        categoryET = (EditText) findViewById(R.id.et_category_EA);
        dateTV = (TextView) findViewById(R.id.tv_date_EA);
        dateBtn = (Button) findViewById(R.id.btn_date_EA);
        timeTV = (TextView) findViewById(R.id.tv_time_EA);
        timeBtn = (Button) findViewById(R.id.btn_time_EA);
        noteET = (EditText) findViewById(R.id.et_notes_EA);
        calculatorBtn = (Button) findViewById(R.id.btn_calculator);
        doneFAB = (FloatingActionButton) findViewById(R.id.fab_done);
        dbOperator = new DBOperator(this, DBOpenHelper.TABLE_NAME);

        dateBtn.setOnClickListener(this);
        timeBtn.setOnClickListener(this);
        calculatorBtn.setOnClickListener(this);
        doneFAB.setOnClickListener(this);
        Intent getIntent = getIntent();
        getMoney = getIntent.getStringExtra(DBOpenHelper.DATA_MONEY);
        getCategory = getIntent.getStringExtra(DBOpenHelper.DATA_CATEGORY);
        getDate = getIntent.getStringExtra(DBOpenHelper.DATA_DATE);
        getTime = getIntent.getStringExtra(DBOpenHelper.DATA_TIME);
        getNote = getIntent.getStringExtra(DBOpenHelper.DATA_NOTE);

        moneyET.setText(getMoney);
        categoryET.setText(getCategory);
        dateTV.setText(getDate);
        timeTV.setText(getTime);
        noteET.setText(getNote);
    }

    @Override
    public void onClick(View v) {
        Calendar c = Calendar.getInstance();
        switch (v.getId()) {
            case R.id.btn_date_EA:
                new DatePickerDialog(EditActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                TextView show = (TextView) findViewById(R.id.tv_date_EA);
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
            case R.id.btn_time_EA:
                new TimePickerDialog(EditActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view,
                                                  int hourOfDay, int minute) {
                                TextView show = (TextView) findViewById(R.id.tv_time_EA);
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
            case R.id.btn_calculator:
                Intent intent = new Intent(this, CalculatorActivity.class);
                startActivity(intent);
                break;

            case R.id.fab_done:
                if (TextUtils.isEmpty(moneyET.getText()) || TextUtils.isEmpty(dateTV.getText()) || TextUtils.isEmpty(timeTV.getText())) {
                    Toast.makeText(this, getResources().getString(R.string.input_error), Toast.LENGTH_LONG).show();//返回失败消息
                } else {
                    dbOperator.update(getMoney, getCategory, getDate, getTime, getNote,
                            moneyET.getText().toString().trim(),
                            categoryET.getText().toString().trim(),
                            dateTV.getText().toString().trim(),
                            timeTV.getText().toString().trim(),
                            noteET.getText().toString().trim());
                    Toast.makeText(this, getResources().getString(R.string.finished), Toast.LENGTH_LONG).show();
                }
                break;
        }
    }
}
