package com.example.helloandroid;

//这个activity实现计算器后台通过老师给的参照文档的第四种方法实现，即直接用activity实现OnClickListener接口，然后重写onClick(View)方法
//参考文档：https://www.runoob.com/w3cnote/android-tutorial-listen-event-handle.html
//安道龙，2020-0408

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConstraintActivity extends AppCompatActivity implements View.OnClickListener {
    //第一步是定义按钮视图之类在java文件里的对应变量
    //第二步是在onCreate方法里面通过刚才定义好的变量获取到视图里的空间,设置好事件监听器
    //第三步是重写onClick()方法，通过控制btn来实现计算器的后台功能
    //计算器的后台逻辑：
    //      用一个switch多分支语句来区分点击的按钮:
    //      1.首先每一个按钮都绑定好了点击事件，如果点击了数字或者是小数点按钮，就把当前的输入框的文本设置为之前的文本拼接上输入的数字文本
    //      2.然后如果点击的是加减乘除和取余等运算按钮，那么同上，将运算按钮的文本拼接到后面
    //      3.如果是clear清空按钮的话，就将输入框的文本设置为空
    //      4.如果是Back删除按钮的话，就删除最后一个字符
    //      5.如果是=等于按钮的话，就调用计算函数getResult()函数来计算当前文本框里的算式
    //          接上一行：getResult()计算函数的实现逻辑：
    //              1.第一个问题是区分数字和运算符：可以在运算符的前后各加一个空格，然后再计算的时候通过检测空格来分离数字和运算符
    //              2.第二个问题是算式的合理性检测，呃，这个情况很多


    //1.定义相关变量(用来对应和获取xml文件里的控件)
    //1.1定义按钮：数字0-9，小数点.
    private Button btnDot;
    private Button btnNum0;
    private Button btnNum1;
    private Button btnNum2;
    private Button btnNum3;
    private Button btnNum4;
    private Button btnNum5;
    private Button btnNum6;
    private Button btnNum7;
    private Button btnNum8;
    private Button btnNum9;
    //1.2.定义按钮：运算符号+-*/%
    private Button btnAdd;
    private Button btnSub;
    private Button btnMul;
    private Button btnExp;
    private Button btnRemain;
    //1.2.定义按钮：清空C，删除Back,等号=
    private Button btnC;
    private Button btnBack;
    private Button btnCalculate;
    //1.3.定义控件：顶部输入框
    private EditText topEditor;
    private TextView resultShowPanel;

    //定义一个布尔值判断当前的输入框是否为空，因为后面的删除按钮需要判断如果是空的话就不能删除，不判断的话会出错
    boolean topEditorIsEmpty = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_constraint);

        //2.1.在onCreate()里获取到xml视图文件里的所有控件,实例化对象
        btnDot = (Button) findViewById(R.id.btnDot);
        btnNum0 = (Button) findViewById(R.id.btnNum0);
        btnNum1 = (Button) findViewById(R.id.btnNum1);
        btnNum2 = (Button) findViewById(R.id.btnNum2);
        btnNum3 = (Button) findViewById(R.id.btnNum3);
        btnNum4 = (Button) findViewById(R.id.btnNum4);
        btnNum5 = (Button) findViewById(R.id.btnNum5);
        btnNum6 = (Button) findViewById(R.id.btnNum6);
        btnNum7 = (Button) findViewById(R.id.btnNum7);
        btnNum8 = (Button) findViewById(R.id.btnNum8);
        btnNum9 = (Button) findViewById(R.id.btnNum9);
        //分隔一下
        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnSub = (Button) findViewById(R.id.btnSub);
        btnMul = (Button) findViewById(R.id.btnMul);
        btnExp = (Button) findViewById(R.id.btnExp);
        //分隔一下
        btnC = (Button) findViewById(R.id.btnC);
        btnBack = (Button) findViewById(R.id.btnBack);
        btnCalculate = (Button) findViewById(R.id.btnCalculate);
        //分隔一下
        topEditor = (EditText) findViewById(R.id.topEditor);
        resultShowPanel = (TextView) findViewById(R.id.resultShowPanel);

        //2.2.设置事件监听器
        btnDot.setOnClickListener(this);
        btnNum0.setOnClickListener(this);
        btnNum1.setOnClickListener(this);
        btnNum2.setOnClickListener(this);
        btnNum3.setOnClickListener(this);
        btnNum4.setOnClickListener(this);
        btnNum5.setOnClickListener(this);
        btnNum6.setOnClickListener(this);
        btnNum7.setOnClickListener(this);
        btnNum8.setOnClickListener(this);
        btnNum9.setOnClickListener(this);
        //分隔一下
        btnAdd.setOnClickListener(this);
        btnSub.setOnClickListener(this);
        btnMul.setOnClickListener(this);
        btnExp.setOnClickListener(this);
        //分隔一下
        btnC.setOnClickListener(this);
        btnBack.setOnClickListener(this);
        btnCalculate.setOnClickListener(this);

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onClick(View v) {

        //每次判断点击事件之前都把当前的输入框内容存起来
        String topEditorContent = topEditor.getText().toString();
        //判断点击事件
        switch (v.getId()) {
            case R.id.btnDot:
            case R.id.btnNum0:
            case R.id.btnNum1:
            case R.id.btnNum2:
            case R.id.btnNum3:
            case R.id.btnNum4:
            case R.id.btnNum5:
            case R.id.btnNum6:
            case R.id.btnNum7:
            case R.id.btnNum8:
            case R.id.btnNum9:
            case R.id.btnAdd:
            case R.id.btnSub:
            case R.id.btnMul:
            case R.id.btnExp:
                //Toast.makeText(getApplicationContext(), "点击了数字按钮", Toast.LENGTH_SHORT).show();
                //Toast.makeText(getApplicationContext(), topEditorContent, Toast.LENGTH_SHORT).show();
                //每次点击数字按钮就将数字加在之前输入框内容的后面
                //Toast.makeText(getApplicationContext(), "点击了运算符按钮", Toast.LENGTH_SHORT).show();
                //每次点击运算符按钮也将运算符加在之前输入框内容的后面

                //闪烁一次
                startFlick(topEditor);
                //stopFlick(topEditor);

                topEditor.setText(topEditorContent + ((Button) v).getText());
                resultShowPanel.setText("");
                topEditorIsEmpty = false;
                break;


            case R.id.btnC:
                //Toast.makeText(getApplicationContext(), "点击了清空按钮", Toast.LENGTH_SHORT).show();
                //点击清空按钮的话就将当前的输入框内容清空

                topEditor.setText("");
                topEditorContent = "";
                resultShowPanel.setText("");
                topEditorIsEmpty = true;
                break;
            case R.id.btnBack:
                //Toast.makeText(getApplicationContext(), "点击了删除按钮", Toast.LENGTH_SHORT).show();
                //topEditor.setText(topEditorContent+" "+((Button)v).getText()+" ");
                //点击删除按钮就删除最后一个数字

                if (!(topEditorContent == "") && (topEditorIsEmpty == false)) {
                    topEditor.setText(topEditorContent.substring(0, topEditorContent.length() - 1));
                }
                resultShowPanel.setText("");
                break;
            case R.id.btnCalculate:
                //Toast.makeText(getApplicationContext(), "点击了计算按钮", Toast.LENGTH_SHORT).show();
                resultShowPanel.setText("");
                getResult();
                break;
            default:
                Toast.makeText(getApplicationContext(), "这是switch的default，你需要检查一下程序", Toast.LENGTH_SHORT).show();
                resultShowPanel.setText("");
                break;
        }

    }

    private void getResult() {
        String formula = topEditor.getText().toString();
        if (formula == null || formula.equals("")) {
            Toast.makeText(getApplicationContext(), "当前算式为空，请检查输入", Toast.LENGTH_SHORT).show();
            return;
        }

//        //截取运算符前面的字符串，通过空格进行识别
//        String s1 = formula.substring(0, formula.indexOf(" "));
//        //截取运算符
//        String op = formula.substring(formula.indexOf(" ") + 1, formula.indexOf(" ") + 2);
//        //截取运算符后面的字符串
//        String s2 = formula.substring(formula.indexOf(" ") + 3);
//
//        double result = 0;
//        if (!s1.equals("") && !s2.equals("")) {
//            double d1 = Double.parseDouble(s1);
//            double d2 = Double.parseDouble(s2);
//            if (op.equals("+")) {
//                result = d1 + d2;
//            }
//            if (op.equals("-")) {
//                result = d1 - d2;
//            }
//            if (op.equals("x")) {
//                result = d1 * d2;
//            }
//            if (op.equals("÷")) {
//                if (d2 == 0) result = 0;
//                else result = d1 / d2;
//            }
//            if (!s1.contains(".") && !s2.contains(".") && !op.equals("÷")) {
//                //int res = (int) result;
//                //topEditor.setText(res + "");
//                topEditor.setText(formula+"="+result + "");
//            } else {
//                topEditor.setText(formula+"="+result + "");
//            }
//        }


        //formula是当前的算式
        //第一步是将中缀表达式转化为后缀表达式，写一个转化表达式用的函数
        //第二步是计算后缀表达式的结果，然后回显给xml界面层，写一个计算用的函数

        //第一步，转化为后缀表达式
        String postfix = infixToPostfix(formula);
        //topEditor.setText(postfix);
        //第二步，计算结果
        String resultStr = calculateResultFromPostfix(postfix);
        //topEditor.setText(formula);
        resultShowPanel.setText("=" + resultStr);

    }


    //为运算符定义好优先级级别，这里采用java里的Map接口，设定一对对的键和值，通过键来获取值
    private static final Map<Character, Integer> opPriority = new HashMap<Character, Integer>();

    static {
        opPriority.put('+', 1);
        opPriority.put('-', 1);
        opPriority.put('x', 2);
        opPriority.put('÷', 2);
    }

    private String infixToPostfix(String infix) {
        //思路：建立一个堆栈stack1和队列queue1，
        // 堆栈用来存储转化过程中的临时量，
        // 队列用来存储转化出来的后缀表达式
        List<String> queue1 = new ArrayList<String>();
        List<Character> stack1 = new ArrayList<Character>();

        char[] charArr = infix.substring(0, infix.length()).trim().toCharArray();
        //这里是将中缀表达式转化为一个字符型的数组，用来逐个拆分算式，trim函数用来去掉首尾的空白符
        String opStandard = "+-x÷";
        char ch = '~';//用于暂存拆分出来的字符,初始化字符无意义
        int numLength = 0;//用于记录拆分出来的数字长度

        //开始遍历转化
        for (int i = 0; i < charArr.length; i++) {

            ch = charArr[i];//取出一个字符
            if (Character.isDigit(ch)) {
                //是数字的话就将当前数字的长度+1
                numLength++;
            } else if (ch == '.') {
                //是小数点的话也将当前数字的长度+1
                numLength++;
            } else if (opStandard.indexOf(ch) != -1) {
                //ch属于+—*/运算符中的一个
                if (numLength > 0) {
                    //遇到了运算符说明这个运算符所在位置前面的几位是一个数字，比如123.2，这个是5位，拿出来加到队列里去
                    queue1.add(String.valueOf(Arrays.copyOfRange(charArr, i - numLength, i)));
                    numLength = 0;//截取出来后长度置空
                }
                if (!stack1.isEmpty()) {
                    //如果堆栈还不空的话
                    int sizeOfStack01 = stack1.size() - 1;//获取栈顶元素的标号
                    while (sizeOfStack01 >= 0 && (opPriority.get(ch) <= opPriority.get(stack1.get(sizeOfStack01)))) {
                        //hhhh,昨天纠结了两个小时的问题原来在这，opPriority get的应该是一个字符，我写的是sizeOfStack01，应该是stack1.get(sizeOfStack01)
                        //如果当前的元素的优先级小于等于栈顶元素的优先级，那么就弹出栈顶元素然后加到队列里去
                        queue1.add(String.valueOf(stack1.remove(sizeOfStack01)));
                        sizeOfStack01--;
                        //因为当前并没有将这次去除的元素添加到队列里去，所以size不能变，用size--纠正回来
                    }
                }
                stack1.add(ch);
                //经过上一步的判断，可知当前的运算符优先级比栈顶元素优先级高了，
                //所以将得到的运算符加到堆栈里面去
            }
            if (i == charArr.length - 1) {                                                //如果已经走到了中缀表达式的最后一位
                if (numLength > 0) {                                                            //如果numLength>0,说明最后一个是数字，就截取最后一个数字数字到队列
                    queue1.add(String.valueOf(Arrays.copyOfRange(charArr, i - numLength + 1, i + 1)));
                }
                int size = stack1.size() - 1;                                            //size表示栈内最后一个元素下标
                while (size >= 0) {                                                        //一直将栈内  符号全部出栈 并且加入队列中  【最终的后缀表达式是存放在队列中的，而栈内最后会被弹空】
                    queue1.add(String.valueOf(stack1.remove(size)));
                    size--;
                }
            }
        }

        String postfix = queue1.toString();
        return postfix.substring(1, postfix.length() - 1);//这里前后各去一个位置，是因为从队列直接转化过来前后各有一个[]，需要去掉，已验证
    }


    private String calculateResultFromPostfix(String postfix) {

        //Double result=0;
        String resultStr = "";
        String errorFlag = "计算后缀表达式有误";
        String[] arrList = postfix.split(", ");//用“，”来区分postfix里的东西，里面的格式类似于1.2，2,+,2,+
        List<String> list = new ArrayList<String>();
        //用于存储临时结果，因为一个数字可能有多位，所以用集合存储而不是Character或者是String

        DecimalFormat doubleFormat = new DecimalFormat("0.000");//设置一下小数位数，避免做除法的时候出现的小数位数过多的问题
        float formatResult = 0;

        for (int i = 0; i < arrList.length; i++) {                                    //此处就是上面说的运算过程， 因为list.remove的缘故，所以取出最后一个数个最后两个数  都是size-2
            int size = list.size();
            switch (arrList[i]) {
                case "+":
                    double a = Double.parseDouble(list.remove(size - 2)) + Double.parseDouble(list.remove(size - 2));
                    list.add(doubleFormat.format(a));//这里对除法进行一下小数位数限定
                    break;
                case "-":
                    double b = Double.parseDouble(list.remove(size - 2)) - Double.parseDouble(list.remove(size - 2));
                    list.add(doubleFormat.format(b));//这里对除法进行一下小数位数限定
                    break;
                case "x":
                    double c = Double.parseDouble(list.remove(size - 2)) * Double.parseDouble(list.remove(size - 2));
                    list.add(doubleFormat.format(c));//这里对除法进行一下小数位数限定
                    break;
                case "÷":
                    double d = Double.parseDouble(list.remove(size - 2)) / Double.parseDouble(list.remove(size - 2));
                    list.add(doubleFormat.format(d));//这里对除法进行一下小数位数限定
                    break;
                default:
                    list.add(arrList[i]);
                    break;//如果是数字  直接放进list中
            }
        }

        resultStr = String.valueOf(list.get(0));
        if (list.size() == 1) {
            return resultStr;
        } else {
            return errorFlag;
        }
    }


    //通过alphaAnimation实现点击闪烁效果
    private void startFlick(View view) {
        if (null == view) {
            return;
        }

        Animation alphaAnimation = new AlphaAnimation(1, 0);
        alphaAnimation.setDuration(60);
        //alphaAnimation.setInterpolator( new LinearInterpolator( ) );
        alphaAnimation.setRepeatCount(1);
        alphaAnimation.setRepeatMode(Animation.REVERSE);
        view.startAnimation(alphaAnimation);
    }

    private void stopFlick(View view) {
        if (null == view) {
            return;
        }
        view.clearAnimation();
    }


}

