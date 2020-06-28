package com.fan.baseuilibrary.utils.provinces;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.blankj.utilcode.util.ColorUtils;
import com.bq.utilslib.AppUtils;
import com.contrarywind.interfaces.IPickerViewData;
import com.fan.baseuilibrary.R;
import com.google.gson.Gson;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

/**
 * 文件名：
 * 描述：
 * 作者：梁帆
 * 时间：2020/4/27
 * 版权：
 */
public class CityUtils {
    private List<CityJsonBean> options1Items1 = new ArrayList<>();
    private ArrayList<ArrayList<CityJsonBean.City>> options2Items2 = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<CityJsonBean.Area>>> options2Items3 = new ArrayList<>();


    public static CityUtils cityUtils;
    public static CityUtils getInstance(Context context){
        if(cityUtils == null){
            cityUtils = new CityUtils(context);
        }
        return cityUtils;
    }



    private CityUtils(Context context) {
        initJsonData(context);
    }

    public void showPickerView(Context context, CityCallBack listener, String cityStrs) {// 弹出选择器
        if(options1Items1.size() == 0){
            initJsonData(context);
        }
        int[] optionInt = null;
        if(cityStrs.contains("-")){
            String[] split = cityStrs.split("-");
            if(split.length == 2){
                optionInt = new int[2];
                optionInt[0]=getIndexOption1(split[0],options1Items1);
                optionInt[1]=getIndexOption2(split[1],options2Items2);
            }else if(split.length == 3){
                optionInt = new int[3];
                optionInt[0]=getIndexOption1(split[0],options1Items1);
                optionInt[1]=getIndexOption2(split[1],options2Items2);
                optionInt[2]=getIndexOption3(split[2],options2Items3);
            }else{

            }
        }
        OptionsPickerBuilder builder = new OptionsPickerBuilder(context, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String opt1tx = options1Items1.size() > 0 ?
                        options1Items1.get(options1).getPickerViewText() : "";

                String opt2tx = options2Items2.size() > 0
                        && options2Items2.get(options1).size() > 0 ?
                        options2Items2.get(options1).get(options2).getPickerViewText() : "";

                String opt3tx = options2Items3.size() > 0
                        && options2Items3.get(options1).size() > 0
                        && options2Items3.get(options1).get(options2).size() > 0 ?
                        options2Items3.get(options1).get(options2).get(options3).getPickerViewText() : "";
                String tx= "";
                if(TextUtils.isEmpty(opt3tx)){
                    tx = opt1tx +"-"+ opt2tx;
                }else{
                    tx = opt1tx +"-"+ opt2tx +"-"+ opt3tx;
                }
                listener.getCitys(tx);
            }
        })

                .setTitleText("城市选择")
                .setDividerColor(Color.BLACK)
                .setSubmitColor(ColorUtils.getColor(R.color.ui_primary_color))
                .setCancelColor(ColorUtils.getColor(R.color.ui_primary_color))
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20);

        if(optionInt != null){
            if(optionInt.length == 3){
                builder.setSelectOptions(optionInt[0],optionInt[1],optionInt[2]);
            }else if(optionInt.length == 2){
                builder.setSelectOptions(optionInt[0],optionInt[1]);
            }
        }
        OptionsPickerView pvOptions = builder.build();

        /*pvOptions.setPicker(options1Items);//一级选择器
        pvOptions.setPicker(options1Items, options2Items);//二级选择器*/
        pvOptions.setPicker(options1Items1, options2Items2, options2Items3);//三级选择器
        pvOptions.show();
    }

    //解析json
    private void initJsonData(Context context) {//解析数据
        String JsonData = AppUtils.getAssetJson(context, "city.json");//获取assets目录下的json文件数据
        ArrayList<CityJsonBean> jsonBean = new ArrayList<>();
        try {
            JSONArray data = new JSONArray(JsonData);
            Gson gson = new Gson();
            for (int i = 0; i < data.length(); i++) {
                CityJsonBean entity = gson.fromJson(data.optJSONObject(i).toString(), CityJsonBean.class);
                jsonBean.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        options1Items1 = jsonBean;
        for (int i = 0; i < jsonBean.size(); i++) {//遍历省份
            ArrayList<CityJsonBean.City> cityList = new ArrayList<>();//该省的城市列表（第二级）
            ArrayList<ArrayList<CityJsonBean.Area>> areaList = new ArrayList<>();//该省的所有地区列表（第三极）
            for (int c = 0; c< jsonBean.get(i).getChildren().size(); c++){//对城市遍历
                ArrayList<CityJsonBean.Area> list1 = new ArrayList<>();
                CityJsonBean.City city = jsonBean.get(i).getChildren().get(c);
                cityList.add(city);
                areaList.add(list1);
                if(jsonBean.get(i).getChildren().get(c) != null && jsonBean.get(i).getChildren().get(c).getChildren() != null){
                    for (int a = 0; a< jsonBean.get(i).getChildren().get(c).getChildren().size(); a++){//对区进行便利
                        CityJsonBean.Area area = jsonBean.get(i).getChildren().get(c).getChildren().get(a);
                        list1.add(area);
                    }
                }
            }
            options2Items2.add(cityList);
            options2Items3.add(areaList);
        }

    }

    private<T extends IPickerViewData> int getIndexOption1(String str, List<T> list){
        int index = 0;
        for (int i = 0 ;i<list.size(); i++){
            if(str.equals(list.get(i).getPickerViewText())){
                return i;
            }
        }
        return index;
    }

    private int getIndexOption2(String str, ArrayList<ArrayList<CityJsonBean.City>> list){
        int index = 0;
        for (int i = 0 ;i<list.size(); i++){
            for (int j= 0; j< list.get(i).size(); j++){
                if(str.equals(list.get(i).get(j).getPickerViewText())){
                    return j;
                }
            }
        }
        return index;
    }
    private int getIndexOption3(String str, ArrayList<ArrayList<ArrayList<CityJsonBean.Area>>> list){
        int index = 0;
        for (int i = 0 ;i<list.size(); i++){
            for (int j= 0; j< list.get(i).size(); j++){
                for(int k = 0; k < list.get(i).get(j).size();k++)
                if(str.equals(list.get(i).get(j).get(k).getPickerViewText())){
                    return k;
                }
            }
        }
        return index;
    }


    public interface CityCallBack{
        void getCitys(String citys);
    }
}
