package m.another.anytimerecord;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

public class CalculatorActivity extends AppCompatActivity {
    double num1;
    double num2;
    private EditText tv_result;
    private String strOperator = "+";//显示运算符号
    private StringBuffer sbDisplay = new StringBuffer();//显示运算
    private String sbResult;//显示结果
    private boolean singleDot = true;//控制小数点位数
    private boolean bPlus, bMinus, bMultiplied, bDivide;//控制运算符

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);
        tv_result = (EditText) findViewById(R.id.tv_result);
        tv_result.setText("0");
        textWatcher(tv_result);
    }

    public void tvOne(View view) {
        sbDisplay.append("1");
        tv_result.setText(sbDisplay.toString());
    }

    public void tvTwo(View view) {
        sbDisplay.append("2");
        tv_result.setText(sbDisplay.toString());
    }

    public void tvThree(View view) {
        sbDisplay.append("3");
        tv_result.setText(sbDisplay.toString());
    }

    public void tvFour(View view) {
        sbDisplay.append("4");
        tv_result.setText(sbDisplay.toString());
    }

    public void tvFive(View view) {
        sbDisplay.append("5");
        tv_result.setText(sbDisplay.toString());
    }

    public void tvSix(View view) {
        sbDisplay.append("6");
        tv_result.setText(sbDisplay.toString());
    }

    public void tvSeven(View view) {
        sbDisplay.append("7");
        tv_result.setText(sbDisplay.toString());
    }

    public void tvEight(View view) {
        sbDisplay.append("8");
        tv_result.setText(sbDisplay.toString());
    }

    public void tvNine(View view) {
        sbDisplay.append("9");
        tv_result.setText(sbDisplay.toString());
    }

    public void tvZero(View view) {
        sbDisplay.append("0");
        tv_result.setText(sbDisplay.toString());
    }

    public void tvDot(View view) {
        if (singleDot) {
            sbDisplay.append(".");
            singleDot = false;
        }
    }

    public void tvPlus(View view) {
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
    }

    public void tvMinus(View view) {
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
    }

    public void tvMultiplied(View view) {
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
    }

    public void tvDivide(View view) {
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
    }

    public void tvEqual(View view) {
        try {
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void tvDelete(View view) {
        if (sbDisplay.length() != 0) {
            sbDisplay.deleteCharAt(sbDisplay.length() - 1);
            tv_result.setText(sbDisplay.toString());
        } else {
            tv_result.setText("0");
        }
    }

    public void tvClean(View view) {
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

    public void textWatcher(final EditText editText) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().contains(".")) {
                    if (s.length() - 1 - s.toString().indexOf(".") > 2) {
                        s = s.toString().subSequence(0,
                                s.toString().indexOf(".") + 3);
                        editText.setText(s);
                        editText.setSelection(s.length());
                    }
                }
                if (s.toString().trim().equals(".")) {
                    s = "0" + s;
                    editText.setText(s);
                    editText.setSelection(2);
                }

                if (s.toString().startsWith("0")
                        && s.toString().trim().length() > 1) {
                    if (!s.toString().substring(1, 2).equals(".")) {
                        editText.setText(s.subSequence(0, 1));
                        editText.setSelection(1);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}

