package m.another.anytimerecord;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

public class ScrollingActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    //声明控件
    private static List<DataBean> dataBeanList = new ArrayList<>();
    private EditText moneyET;
    private EditText categoryET;
    private TextView dateTV;
    private TextView timeTV;
    private EditText noteET;
    private DBOperator dbOperator;
    private DataAdapter dataAdapter;
    private Calendar mCalendar;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        moneyET = findViewById(R.id.et_money);
        categoryET = findViewById(R.id.et_category);
        dateTV = findViewById(R.id.tv_date);
        timeTV = findViewById(R.id.tv_time);
        noteET = findViewById(R.id.et_notes);
        mCalendar = Calendar.getInstance();
        RecyclerView mRecyclerView = findViewById(R.id.rv_list);


        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setNestedScrollingEnabled(true);

        dbOperator = new DBOperator(this);
        resetData();
        mRecyclerView.setAdapter(dataAdapter);

        //下拉刷新 thanks to:http://my.oschina.net/smuswc/blog/612697
        mSwipeRefreshLayout = findViewById(R.id.swipe_layout);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        Toolbar toolbar = findViewById(R.id.toolbar_scrolling);
        setSupportActionBar(toolbar);

        AppBarLayout appBar = findViewById(R.id.app_bar);
        appBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {

                if (verticalOffset >= 0) {
                    mSwipeRefreshLayout.setEnabled(true);
                } else {
                    mSwipeRefreshLayout.setEnabled(false);
                }
            }
        });

        String permission[] = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE, Manifest.permission.RECORD_AUDIO};
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permission, 0);
        }
    }

    /*--------创建菜单--------*/
    //创建菜单
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_scrolling, menu);
        return true;
    }

    //菜单响应事件
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.about: {
                new AlertDialog.Builder(this)
                        .setTitle(R.string.thanks)
                        .setView(R.layout.about)
                        .setPositiveButton(R.string.close, null)
                        .show();
                break;
            }
            case R.id.exit: {
                finish();
                break;
            }
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    /*-------创建菜单-------*/

    private void resetData() {
        dataBeanList = dbOperator.queryAll();
        dataAdapter = new DataAdapter(ScrollingActivity.this);
        dataAdapter.resetData(dataBeanList);
    }


    public void onRefresh() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                dataBeanList = dbOperator.queryAll();
                dataAdapter.resetData(dataBeanList);
                mSwipeRefreshLayout.setRefreshing(false);
            }
        }, 500);

    }

    public void SADateBtn(View view) {
        new DatePickerDialog(ScrollingActivity.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        TextView show = findViewById(R.id.tv_date);
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
                }, mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH), mCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    public void SATimeBtn(View view) {
        new TimePickerDialog(ScrollingActivity.this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view,
                                          int hourOfDay, int minute) {
                        TextView show = findViewById(R.id.tv_time);
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
                }, mCalendar.get(Calendar.HOUR_OF_DAY), mCalendar.get(Calendar.MINUTE), true).show();
    }

    public void SADoneBtn(View view) {
        if (TextUtils.isEmpty(moneyET.getText()) || TextUtils.isEmpty(dateTV.getText()) || TextUtils.isEmpty(timeTV.getText())) {
            Toast.makeText(this, R.string.input_error, Toast.LENGTH_LONG).show();//返回失败消息
        } else {
            dbOperator.insert(moneyET.getText().toString().trim(), categoryET.getText().toString().trim(), dateTV.getText().toString().trim(), timeTV.getText().toString().trim(), noteET.getText().toString().trim());
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            assert imm != null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                imm.hideSoftInputFromWindow(Objects.requireNonNull(getCurrentFocus()).getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
            Toast.makeText(this, R.string.input_complete, Toast.LENGTH_LONG).show();
            dataBeanList = dbOperator.queryAll();
            dataAdapter.resetData(dataBeanList);
        }
    }

    public void SAFloatActionButton(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("https://github.com/AnotherM/AnytimeRecord"));
        startActivity(intent);
    }
}
