package com.fan.baseuilibrary.view;

import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;
import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Describe 过滤表情
 */
public class EmojiFilter implements InputFilter {
 
    private static final String TAG = "EmojiFilter";
 
    private static Pattern mEmojiPattern = Pattern.compile("[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]", Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);
 
    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        Matcher emojiMatcher = mEmojiPattern.matcher (source) ;
 
        if (emojiMatcher.find( )) {
            Log.d(TAG, "source: " + source.toString() + " is match.");
            return "";
        }
        return source;
    }
 
//    保留原有Filter,同时新增默认的过滤规则,直接上代码:
    /**
     * 过滤表情            保留原有Filter,同时新增默认的过滤规则
     *
     * @param editText edittext
     */
    public static void setEditTextFilter(EditText editText) {
        InputFilter filter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                Matcher emojiMatcher = mEmojiPattern.matcher (source) ;
 
                if (emojiMatcher.find( )) {
                    Log.d(TAG, "source: " + source.toString() + " is match.");
                    return "";
                }
                return source;
            }
        };
 
        //保留原有的editText的filter
        InputFilter[] oldFilters = editText.getFilters();
        int oldFiltersLength = oldFilters.length;
        InputFilter[] newFilters = new InputFilter[oldFiltersLength + 1];
        if (oldFiltersLength > 0) {
            System.arraycopy(oldFilters, 0, newFilters, 0, oldFiltersLength);
        }
        //添加新的过滤规则
        newFilters[oldFiltersLength] = filter;
        editText.setFilters(newFilters);
    }
 
}