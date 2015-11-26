package com.innofarm.external;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.innofarm.R;

/**
 * Created by dell on 2015/10/21.
 */
public class SelectCountDialog extends Dialog {
    //定义回调事件，用于dialog的点击事件
    public interface OnSelectCountDialogListener {
        public void back(String count);
    }

    //private String name;
    private OnSelectCountDialogListener selectCountDialogListener;
    TextView etCount;
    Button sure;
    Button cancel;
    ImageView plus;
    ImageView minus;


    public SelectCountDialog(Context context, OnSelectCountDialogListener selectCountDialogListener) {
        super(context);

        // this.name = name;
        this.selectCountDialogListener = selectCountDialogListener;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_select_count);

        //设置标题
        //setTitle(name);
        etCount = (TextView) findViewById(R.id.tv_count);
        sure = (Button) findViewById(R.id.btn_selectcount_sure);
        cancel = (Button) findViewById(R.id.btn_selectcount_cancel);
        plus = (ImageView) findViewById(R.id.iv_plus);
        minus = (ImageView) findViewById(R.id.iv_minus);

        minus.setOnClickListener(clickListener);
        plus.setOnClickListener(clickListener);
        cancel.setOnClickListener(clickListener);
        sure.setOnClickListener(clickListener);

    }

    private View.OnClickListener clickListener = new View.OnClickListener() {


        @Override
        public void onClick(View v) {
            int count = Integer.parseInt(etCount.getText().toString());

            switch (v.getId()) {

                case R.id.iv_minus:
                    if (count <= 0) {
                        minus.setEnabled(false);
                    } else {
                        count--;
                        etCount.setText(count+"");
                    }

                    break;
                case R.id.btn_selectcount_sure:

                    selectCountDialogListener.back(etCount.getText().toString());
                    SelectCountDialog.this.dismiss();

                    break;
                case R.id.btn_selectcount_cancel:

                    SelectCountDialog.this.dismiss();

                    break;
                case R.id.iv_plus:
                    minus.setEnabled(true);
                    count++;
                    etCount.setText(count+"");
                    break;


            }


        }
    };


}
