package m.another.anytimerecord;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.util.Calendar;


public class EditActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView idTV;
    private EditText moneyET;
    private EditText categoryET;
    private TextView dateTV;
    private TextView timeTV;
    private EditText noteET;
    private String getId;
    private String getMoney;
    private String getCategory;
    private String getDate;
    private String getTime;
    private String getNote;
    private DBOperator dbOperator;
    private AdView mAdView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        idTV = (TextView) findViewById(R.id.tv_id_EA);
        moneyET = (EditText) findViewById(R.id.et_money_EA);
        categoryET = (EditText) findViewById(R.id.et_category_EA);
        dateTV = (TextView) findViewById(R.id.tv_date_EA);
        Button dateBtn = (Button) findViewById(R.id.btn_date_EA);
        timeTV = (TextView) findViewById(R.id.tv_time_EA);
        Button timeBtn = (Button) findViewById(R.id.btn_time_EA);
        noteET = (EditText) findViewById(R.id.et_notes_EA);
        Button doneBtn = (Button) findViewById(R.id.btn_done_EA);
        dbOperator = new DBOperator(this);

        dateBtn.setOnClickListener(this);
        timeBtn.setOnClickListener(this);
        dateBtn.setOnClickListener(this);
        doneBtn.setOnClickListener(this);
        Intent getIntent = getIntent();
        int getid = getIntent.getIntExtra(DBOpenHelper.ID, 0);
        getId = String.valueOf(getid);
        getMoney = getIntent.getStringExtra(DBOpenHelper.DATA_MONEY);
        getCategory = getIntent.getStringExtra(DBOpenHelper.DATA_CATEGORY);
        getDate = getIntent.getStringExtra(DBOpenHelper.DATA_DATE);
        getTime = getIntent.getStringExtra(DBOpenHelper.DATA_TIME);
        getNote = getIntent.getStringExtra(DBOpenHelper.DATA_NOTE);

        idTV.setText(getId);
        moneyET.setText(getMoney);
        dateTV.setText(getDate);
        timeTV.setText(getTime);
        categoryET.setText(getCategory);
        noteET.setText(getNote);

        MobileAds.initialize(this, "ca-app-pub-4522566152785892/3188170363");
        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build();
        mAdView.loadAd(adRequest);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_edit);
        setSupportActionBar(toolbar);
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
            case R.id.btn_time_EA:
                new TimePickerDialog(EditActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view,
                                                  int hourOfDay, int minute) {
                                TextView show = (TextView) findViewById(R.id.tv_time_EA);
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


            case R.id.btn_done_EA:
                if (TextUtils.isEmpty(moneyET.getText()) || TextUtils.isEmpty(dateTV.getText()) || TextUtils.isEmpty(timeTV.getText())) {
                    Toast.makeText(this, getResources().getString(R.string.input_error), Toast.LENGTH_LONG).show();//返回失败消息
                } else {
                    dbOperator.update(getId, getMoney, getCategory, getDate, getTime, getNote,
                            idTV.getText().toString().trim(),
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

    @Override
    public void onPause() {
        if (mAdView != null) {
            mAdView.pause();
        }
        super.onPause();
    }

    /**
     * Called when returning to the activity
     */
    @Override
    public void onResume() {
        super.onResume();
        if (mAdView != null) {
            mAdView.resume();
        }
    }

    /**
     * Called before the activity is destroyed
     */
    @Override
    public void onDestroy() {
        if (mAdView != null) {
            mAdView.destroy();
        }
        super.onDestroy();
    }
}
