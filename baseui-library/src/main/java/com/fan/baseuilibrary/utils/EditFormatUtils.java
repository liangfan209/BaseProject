package com.fan.baseuilibrary.utils;

import android.text.Editable;
import android.text.InputType;
import android.text.Selection;
import android.text.TextWatcher;
import android.widget.EditText;

public class EditFormatUtils {
    /**
     * 手机344格式
     * @param mEditText
     */
    public static void phoneNumAddSpace(final EditText mEditText) {
        mEditText.addTextChangedListener(new TextWatcher() {
            int beforeTextLength = 0;
            int onTextLength = 0;
            boolean isChanged = false;

            int location = 0;// 记录光标的位置
            private char[] tempChar;
            private StringBuffer buffer = new StringBuffer();
            int konggeNumberB = 0;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                beforeTextLength = s.length();
                if (buffer.length() > 0) {
                    buffer.delete(0, buffer.length());
                }
                konggeNumberB = 0;
                for (int i = 0; i < s.length(); i++) {
                    if (s.charAt(i) == ' ') {
                        konggeNumberB++;
                    }
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                onTextLength = s.length();
                buffer.append(s.toString());
                if (onTextLength == beforeTextLength || onTextLength <= 3
                        || isChanged) {
                    isChanged = false;
                    return;
                }
                isChanged = true;
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (isChanged) {
                    location = mEditText.getSelectionEnd();
                    int index = 0;
                    while (index < buffer.length()) {
                        if (buffer.charAt(index) == ' ') {
                            buffer.deleteCharAt(index);
                        } else {
                            index++;
                        }
                    }

                    index = 0;
                    int konggeNumberC = 0;
                    while (index < buffer.length()) {
                        if ((index == 3 || index == 8)) {
                            buffer.insert(index, ' ');
                            konggeNumberC++;
                        }
                        index++;
                    }

                    if (konggeNumberC > konggeNumberB) {
                        location += (konggeNumberC - konggeNumberB);
                    }

                    tempChar = new char[buffer.length()];
                    buffer.getChars(0, buffer.length(), tempChar, 0);
                    String str = buffer.toString();
                    /*if (location > str.length()) {
                        location = str.length();
                    } else if (location < 0) {
                        location = 0;
                    }*/

                    mEditText.setText(str);
                    Editable etable = mEditText.getText();
                    if(location > etable.length()){
                        location = etable.length();
                    } else if (location < 0) {
                        location = 0;
                    }
                    Selection.setSelection(etable, location);
                    isChanged = false;
                }
            }
        });
    }

    /**
     * 银行卡号码的格式
     * @param mEditText
     */
    public static void bankCardNumAddSpace(final EditText mEditText) {
        mEditText.addTextChangedListener(new TextWatcher() {
            int beforeTextLength = 0;
            int onTextLength = 0;
            boolean isChanged = false;

            int location = 0;// 记录光标的位置
            private char[] tempChar;
            private StringBuffer buffer = new StringBuffer();
            int konggeNumberB = 0;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                beforeTextLength = s.length();
                if (buffer.length() > 0) {
                    buffer.delete(0, buffer.length());
                }
                konggeNumberB = 0;
                for (int i = 0; i < s.length(); i++) {
                    if (s.charAt(i) == ' ') {
                        konggeNumberB++;
                    }
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                onTextLength = s.length();
                buffer.append(s.toString());
                if (onTextLength == beforeTextLength || onTextLength <= 3
                        || isChanged) {
                    isChanged = false;
                    return;
                }
                isChanged = true;
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (isChanged) {
                    location = mEditText.getSelectionEnd();
                    int index = 0;
                    while (index < buffer.length()) {
                        if (buffer.charAt(index) == ' ') {
                            buffer.deleteCharAt(index);
                        } else {
                            index++;
                        }
                    }

                    index = 0;
                    int konggeNumberC = 0;
                    while (index < buffer.length()) {
                        if ((index == 4 || index == 9 || index == 14 || index == 19)) {
                            buffer.insert(index, ' ');
                            konggeNumberC++;
                        }
                        index++;
                    }

                    if (konggeNumberC > konggeNumberB) {
                        location += (konggeNumberC - konggeNumberB);
                    }

                    tempChar = new char[buffer.length()];
                    buffer.getChars(0, buffer.length(), tempChar, 0);
                    String str = buffer.toString();
                    /*if (location > str.length()) {
                        location = str.length();
                    } else if (location < 0) {
                        location = 0;
                    }*/
                    mEditText.setText(str);
                    Editable etable = mEditText.getText();
                    if(location > etable.length()){
                        location = etable.length();
                    } else if (location < 0) {
                        location = 0;
                    }
                    Selection.setSelection(etable, location);
                    isChanged = false;
                }
            }
        });
    }

    /**
     * 身份证号码格式化
     * @param mEditText
     */
    public static void idCardAddSpace(final EditText mEditText) {
        mEditText.addTextChangedListener(new TextWatcher() {
            int beforeTextLength = 0;
            int onTextLength = 0;
            boolean isChanged = false;

            int location = 0;// 记录光标的位置
            private char[] tempChar;
            private StringBuffer buffer = new StringBuffer();
            int konggeNumberB = 0;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                beforeTextLength = s.length();
                if (buffer.length() > 0) {
                    buffer.delete(0, buffer.length());
                }
                konggeNumberB = 0;
                for (int i = 0; i < s.length(); i++) {
                    if (s.charAt(i) == ' ') {
                        konggeNumberB++;
                    }
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                onTextLength = s.length();
                buffer.append(s.toString());
                if (onTextLength == beforeTextLength || onTextLength <= 6
                        || isChanged) {
                    isChanged = false;
                    return;
                }
                isChanged = true;
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length() == 1){
                    Editable etable = mEditText.getText();
                    Selection.setSelection(etable, 1);
                }
                if (mEditText.length() == 20) {
                    mEditText.setInputType(InputType.TYPE_CLASS_TEXT);
                } else {
                    mEditText.setInputType(InputType.TYPE_CLASS_NUMBER);
                }
                if (isChanged) {
                    location = mEditText.getSelectionEnd();
                    int index = 0;
                    while (index < buffer.length()) {
                        if (buffer.charAt(index) == ' ') {
                            buffer.deleteCharAt(index);
                        } else {
                            index++;
                        }
                    }

                    index = 0;
                    int konggeNumberC = 0;
                    while (index < buffer.length()) {
                        if ((index == 6 || index == 11 || index == 16)) {
                            buffer.insert(index, ' ');
                            konggeNumberC++;
                        }
                        index++;
                    }

                    if (konggeNumberC > konggeNumberB) {
                        location += (konggeNumberC - konggeNumberB);
                    }

                    tempChar = new char[buffer.length()];
                    buffer.getChars(0, buffer.length(), tempChar, 0);
                    String str = buffer.toString();
                    /*if (location > str.length()) {
                        location = str.length();
                    } else if (location < 0) {
                        location = 0;
                    }*/

                    mEditText.setText(str);
                    Editable etable = mEditText.getText();
                    if(location > etable.length()){
                        location = etable.length();
                    } else if (location < 0) {
                        location = 0;
                    }
                    Selection.setSelection(etable, location);
                    isChanged = false;
                }
            }
        });
    }

    /**
     * 身份证字符串转换
     * @param num 身份证号码
     * @param encrypt 是否加密（*号替代部分）
     * @return
     */
    public static String idNumFormat(String num, boolean encrypt){
        String part1=num.substring(0,6);
        String part2=num.substring(6,10);
        String part3=num.substring(10,14);
        String part4=num.substring(14,18);
        if(encrypt){
            part2="****";
            part3="****";
        }
        return part1 + part2 + part3 + part4;

    }

    /**
     * 手机号字符串转换
     * @param num 手机号码
     * @param encrypt 是否加密（*号替代部分）
     * @return
     */
    public static String phoneNumFormat(String num, boolean encrypt){
        String part1=num.substring(0,3);
        String part2=num.substring(3,7);
        String part3=num.substring(7,11);
        if(encrypt){
            part2="****";
        }
        return part1 + part2 + part3;

    }

    /**
     * 银行卡字符串转换
     * @param num 银行卡号码
     * @param encrypt 是否加密（*号替代部分）
     * @return
     */
    public static String bankCardNumFormat(String num, boolean encrypt){
        String part1=num.substring(0,6);
        String part2=num.substring(6,12);
        String part3=num.substring(num.length()-4,num.length());
        if(encrypt){
            part2="******";
        }
        return part1 + part2 + part3;

    }
}
