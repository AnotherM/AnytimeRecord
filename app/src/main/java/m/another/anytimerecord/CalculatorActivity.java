package m.another.anytimerecord;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class CalculatorActivity extends AppCompatActivity implements View.OnClickListener {
    private double num1;
    private TextView tv_result;
    private String strOperator = "+";//显示运算符号
    private StringBuffer sbDisplay = new StringBuffer();//显示运算
    private String sbResult;//显示结果
    private boolean singleDot = true;//控制小数点位数
    private boolean bPlus, bMinus, bMultiplied, bDivide;//控制运算符

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);
        // 0-9数字控件
        tv_result = (TextView) findViewById(R.id.tv_result);
        TextView tv_one = (TextView) findViewById(R.id.tv_one);
        TextView tv_two = (TextView) findViewById(R.id.tv_two);
        TextView tv_three = (TextView) findViewById(R.id.tv_three);
        TextView tv_four = (TextView) findViewById(R.id.tv_four);
        TextView tv_five = (TextView) findViewById(R.id.tv_five);
        TextView tv_six = (TextView) findViewById(R.id.tv_six);
        TextView tv_seven = (TextView) findViewById(R.id.tv_seven);
        TextView tv_eight = (TextView) findViewById(R.id.tv_eight);
        TextView tv_nine = (TextView) findViewById(R.id.tv_nine);
        TextView tv_zero = (TextView) findViewById(R.id.tv_zero);
        //运算符控件
        TextView tv_plus = (TextView) findViewById(R.id.tv_plus);
        TextView tv_minus = (TextView) findViewById(R.id.tv_minus);
        TextView tv_multiplied = (TextView) findViewById(R.id.tv_multiplied);
        TextView tv_divide = (TextView) findViewById(R.id.tv_divide);
        TextView tv_equal = (TextView) findViewById(R.id.tv_equal);
        TextView tv_dot = (TextView) findViewById(R.id.tv_dot);
        //删除、清除
        TextView tv_del = (TextView) findViewById(R.id.tv_del);
        TextView tv_clear = (TextView) findViewById(R.id.tv_clear);
        //设置监听事件
        tv_result.setOnClickListener(this);
        tv_one.setOnClickListener(this);
        tv_two.setOnClickListener(this);
        tv_three.setOnClickListener(this);
        tv_four.setOnClickListener(this);
        tv_five.setOnClickListener(this);
        tv_six.setOnClickListener(this);
        tv_seven.setOnClickListener(this);
        tv_eight.setOnClickListener(this);
        tv_nine.setOnClickListener(this);
        tv_zero.setOnClickListener(this);
        tv_plus.setOnClickListener(this);
        tv_minus.setOnClickListener(this);
        tv_multiplied.setOnClickListener(this);
        tv_divide.setOnClickListener(this);
        tv_equal.setOnClickListener(this);
        tv_dot.setOnClickListener(this);
        tv_del.setOnClickListener(this);
        tv_clear.setOnClickListener(this);
        //初始化运算结果
        tv_result.setText("0");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //数字0-9的点击事件
            case R.id.tv_one:
                sbDisplay.append("1");
                tv_result.setText(sbDisplay.toString());
                break;
            case R.id.tv_two:
                sbDisplay.append("2");
                tv_result.setText(sbDisplay.toString());
                break;
            case R.id.tv_three:
                sbDisplay.append("3");
                tv_result.setText(sbDisplay.toString());
                break;
            case R.id.tv_four:
                sbDisplay.append("4");
                tv_result.setText(sbDisplay.toString());
                break;
            case R.id.tv_five:
                sbDisplay.append("5");
                tv_result.setText(sbDisplay.toString());
                break;
            case R.id.tv_six:
                sbDisplay.append("6");
                tv_result.setText(sbDisplay.toString());
                break;
            case R.id.tv_seven:
                sbDisplay.append("7");
                tv_result.setText(sbDisplay.toString());
                break;
            case R.id.tv_eight:
                sbDisplay.append("8");
                tv_result.setText(sbDisplay.toString());
                break;
            case R.id.tv_nine:
                sbDisplay.append("9");
                tv_result.setText(sbDisplay.toString());
                break;
            case R.id.tv_zero:
                sbDisplay.append("0");
                tv_result.setText(sbDisplay.toString());
                break;
            //运算符号的点击事件
            case R.id.tv_dot:
                if (singleDot) {
                    sbDisplay.append(".");
                    singleDot = false;
                }
                break;
            case R.id.tv_plus:
                strOperator = "＋";
                if (!bPlus && !(sbDisplay.toString().equals(""))) {
                    num1 = Double.parseDouble(sbDisplay.toString());
                    tv_result.setText(String.valueOf(num1));
                    sbDisplay = new StringBuffer("");
                    bPlus = true;
                } else {
                    if (!(sbDisplay.toString().equals(""))) {
                        num1 += Double.parseDouble(sbDisplay.toString());
                        sbDisplay = new StringBuffer("");
                    }
                    if (!(sbResult == null)) {
                        num1 = Double.parseDouble(sbResult);
                        sbResult = null;
                    }
                    tv_result.setText(String.valueOf(num1));
                }
                singleDot = true;
                break;
            case R.id.tv_minus:
                strOperator = "－";
                if (!bMinus && !(sbDisplay.toString().equals(""))) {
                    num1 = Double.parseDouble(sbDisplay.toString());
                    tv_result.setText(String.valueOf(num1));
                    sbDisplay = new StringBuffer("");
                    bMinus = true;
                } else {
                    if (!(sbDisplay.toString().equals(""))) {
                        num1 -= Double.parseDouble(sbDisplay.toString());
                        sbDisplay = new StringBuffer("");
                    }
                    if (!(sbResult == null)) {
                        num1 = Double.parseDouble(sbResult);
                        sbResult = null;
                    }
                    tv_result.setText(String.valueOf(num1));
                }
                singleDot = true;
                break;
            case R.id.tv_multiplied:
                strOperator = "×";
                if (!bMultiplied && !(sbDisplay.toString().equals(""))) {
                    num1 = Double.parseDouble(sbDisplay.toString());
                    tv_result.setText(String.valueOf(num1));
                    sbDisplay = new StringBuffer("");
                    bMultiplied = true;
                } else {
                    if (!(sbDisplay.toString().equals(""))) {
                        num1 *= Double.parseDouble(sbDisplay.toString());
                        sbDisplay = new StringBuffer("");
                    }
                    if (!(sbResult == null)) {
                        num1 = Double.parseDouble(sbResult);
                        sbResult = null;
                    }
                    tv_result.setText(String.valueOf(num1));
                }
                singleDot = true;
                break;
            case R.id.tv_divide:
                strOperator = "÷";
                if (!bDivide && !(sbDisplay.toString().equals(""))) {
                    num1 = Double.parseDouble(sbDisplay.toString());
                    tv_result.setText(String.valueOf(num1));
                    sbDisplay = new StringBuffer("");
                    bDivide = true;
                } else {
                    if (!(sbDisplay.toString().equals(""))) {
                        if (Double.parseDouble(sbDisplay.toString()) == 0) {
                            tv_result.setText(getResources().getString(R.string.not_zero));
                        } else {
                            num1 /= Double.parseDouble(sbDisplay.toString());
                            sbDisplay = new StringBuffer("");
                        }
                    }
                    if (!(sbResult == null)) {
                        num1 = Double.parseDouble(sbResult);
                        sbResult = null;
                    }
                    tv_result.setText(String.valueOf(num1));
                }
                singleDot = true;
                break;
            case R.id.tv_equal:
                double num2;
                if (strOperator.equals("＋")) {
                    num2 = Double.parseDouble(sbDisplay.toString());
                    sbResult = String.valueOf(num1 + num2);
                    tv_result.setText(sbResult);
                    sbDisplay = new StringBuffer("");
                }
                if (strOperator.equals("－")) {
                    num2 = Double.parseDouble(sbDisplay.toString());
                    sbResult = String.valueOf(num1 - num2);
                    tv_result.setText(sbResult);
                    sbDisplay = new StringBuffer("");
                }
                if (strOperator.equals("×")) {
                    num2 = Double.parseDouble(sbDisplay.toString());
                    sbResult = String.valueOf(num1 * num2);
                    tv_result.setText(sbResult);
                    sbDisplay = new StringBuffer("");
                }
                if (strOperator.equals("÷")) {
                    num2 = Double.parseDouble(sbDisplay.toString());
                    if (!(num2 == 0)) {
                        sbResult = String.valueOf((num1 / num2));
                        tv_result.setText(sbResult);
                    } else {
                        tv_result.setText(getResources().getString(R.string.not_zero));
                    }
                    sbDisplay = new StringBuffer("");
                }
                break;
            //删除、清空点击事件
            case R.id.tv_del:
                if (sbDisplay.length() != 0) {
                    sbDisplay.deleteCharAt(sbDisplay.length() - 1);
                    tv_result.setText(sbDisplay.toString());
                }
                break;
            case R.id.tv_clear:
                strOperator = "+";
                sbDisplay = new StringBuffer("");
                sbResult = null;
                num1 = 0;
                singleDot = true;
                bPlus = false;
                bMinus = false;
                bMultiplied = false;
                bDivide = false;
                tv_result.setText("0");
        }
    }
}

