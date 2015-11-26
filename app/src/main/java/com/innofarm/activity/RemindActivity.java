package com.innofarm.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.innofarm.R;
import com.innofarm.adapter.Adapter;
import com.innofarm.external.FailureRequestCallBack;
import com.innofarm.external.SuccessRequestCallBack;
import com.innofarm.external.ViewHolder;
import com.innofarm.manager.ServiceManager;
import com.innofarm.model.EventRemindCountInfo;
import com.innofarm.protocol.EventRemindCountResult;
import com.innofarm.protocol.HeatEventRemindResult;
import com.innofarm.utils.UIUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by dell on 2015/10/20.
 * <p/>
 * 提醒
 */
public class RemindActivity extends BaseActivity {


    @ViewInject(R.id.lv_remind_1)
    ListView lv1;
    @ViewInject(R.id.imgbtn_left)
    ImageButton imgbtn_left;
    @ViewInject(R.id.txt_title)
    TextView txt_title;


    LinkedList numList = new LinkedList();//更新数据数量
    MyAdapter adapter;

    ArrayList<Temp<String, Integer>> arr;


    @Override
    protected void init() {
        View view = View.inflate(this, R.layout.activity_remind, null);
        ViewUtils.inject(this, view);
        setContentView(view);

    }

    @Override
    public void initView() {
        txt_title.setText("事件提醒");
        //返回
        imgbtn_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        for (int x = 0; x < 7; x++) {
            numList.add(String.valueOf(x));
        }

        ServiceManager.EventRemindCount(eventRemindCount(), failrequest());
        //ServiceManager.HeatEventRemind(heatEventRemind(), failrequest());



        arr = new ArrayList<Temp<String, Integer>>();


        String[] s1 = {"发情监测", "配种操作", "", "妊检操作", "干奶操作", "围产（前）操作", "", "产犊操作", "围产（后）操作"};
        Integer[] i1 = {R.drawable.faqingjiance, R.drawable.peizhongcaozuo, 0,
                R.drawable.renjiancaozuo, R.drawable.gannaicaozuo,
                R.drawable.weichanqian, 0,
                R.drawable.chanducaozuo, R.drawable.weichanhou};
        arr = getTemplist(s1, i1, arr);

        //lv1.setAdapter(getAdapter(arr, numList));

        lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ViewHolder holder = ViewHolder.get(RemindActivity.this, view, adapterView, R.layout.adapter_eventremind_lv, i);
                TextView tv = holder.getView(R.id.tv_eventremind_name);
                Intent intent = new Intent(RemindActivity.this, RemindContentActivity.class);
                intent.putExtra("name", tv.getText().toString());
                startActivity(intent);

            }
        });


    }


    abstract class MyAdapter<T> extends Adapter<T> {

        List numList;

        public MyAdapter(Context context, List<T> mDatas, List numList, int itemLayoutId) {
            super(context, mDatas, itemLayoutId);
            this.numList = numList;
        }

        @Override
        public void convert(ViewHolder helper, T item) {
            convert(helper, item, numList);
        }

        public abstract void convert(ViewHolder helper, T item, List numList);
    }


    /**
     * 临时内部类，填充listView所需资源
     */
    class Temp<T, E> {
        public T t;
        public E e;
    }


    /**
     * 获取填充listview的Templist
     *
     * @param s 操作名称
     * @param i 图标
     */

    public ArrayList getTemplist(String[] s, Integer[] i, ArrayList list) {
        list.clear();
        for (int x = 0; x < s.length; x++) {
            Temp<String, Integer> temp = new Temp<String, Integer>();
            temp.t = s[x];
            temp.e = i[x];
            list.add(temp);
        }
        return list;
    }


    /**
     * listView适配器
     */

    public Adapter getAdapter(final ArrayList<Temp<String, Integer>> list1, ArrayList list) {


        adapter = new MyAdapter<Temp<String, Integer>>(this, list1, list, R.layout.adapter_eventremind_lv) {

            @Override
            public boolean isEnabled(int position) {
                if (position == 2 || position == 6) {
                    return false;
                }
                return super.isEnabled(position);
            }

            @Override
            public void convert(ViewHolder helper, Temp<String, Integer> item, List numList) {
                int position = list1.indexOf(item);
                String count = "";
                if (position == 2 || position == 6) {
                    helper.getView(R.id.adapter_evenremind_rl_1).setVisibility(View.INVISIBLE);
                    return;
                }
                if (position > 2 && position < 6) {
                    count = (String) numList.get(position - 1);
                } else if (position > 6) {
                    count = (String) numList.get(position - 2);
                } else {
                    count = (String) numList.get(position);
                }
                helper.setText(R.id.tv_eventremind_name, item.t);
                helper.setText(R.id.tv_eventremind_num, "所有" + count + "项");
                helper.setImageResource(R.id.iv_eventremind, item.e);

            }
        };
        return adapter;
    }


    /**请求响应成功 */

    /**
     * 请求提醒数量
     */
    private SuccessRequestCallBack<String> eventRemindCount() {
        return new SuccessRequestCallBack<String>() {
            @Override
            public void onSuccess(String responseInfo) {

                String str = responseInfo;



                EventRemindCountResult result  = UIUtils.json2str(str,  EventRemindCountResult.class);
                String status = result.getReturn_sts();



                ArrayList<String> countlist = new ArrayList<String>();


                for (EventRemindCountInfo count : result.getAlertCnt()) {
                    countlist.add(count.getCnt());

                }
                lv1.setAdapter(getAdapter(arr,countlist));

            }
        };
    }


    /**
     * 请求发情事件提醒
     */
    private SuccessRequestCallBack<String> heatEventRemind() {
        return new SuccessRequestCallBack<String>() {
            @Override
            public void onSuccess(String responseInfo) {

                String str = responseInfo;
                HeatEventRemindResult result  = UIUtils.json2str(str, HeatEventRemindResult.class);
                String status = result.getReturn_sts();


            }
        };
    }








    private FailureRequestCallBack failrequest() {
        return new FailureRequestCallBack() {
            @Override
            public void onFailure(HttpException error, String msg) {
                String str = msg;

            }
        };
    }


}
