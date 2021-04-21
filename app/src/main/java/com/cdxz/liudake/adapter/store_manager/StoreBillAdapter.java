package com.cdxz.liudake.adapter.store_manager;

import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.cdxz.liudake.R;
import com.cdxz.liudake.bean.StoreTodayInviteBean;
import com.cdxz.liudake.bean.StoreTodaySettlementBean;
import com.cdxz.liudake.bean.StoreTodaySettlementCashBean;
import com.chad.library.adapter.base.BaseDelegateMultiAdapter;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.delegate.BaseMultiTypeDelegate;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class StoreBillAdapter extends BaseDelegateMultiAdapter<Object,BaseViewHolder> {
    public StoreBillAdapter() {
        super();

        setMultiTypeDelegate(new BaseMultiTypeDelegate<Object>() {
            @Override
            public int getItemType(@NotNull List<?> list, int i) {

                if (list.get(i) instanceof StoreTodaySettlementBean){
                    return 0;
                }else if (list.get(i) instanceof StoreTodayInviteBean){
                    return 1;
                }

                return 0;
            }
        });

        getMultiTypeDelegate()
                .addItemType(0, R.layout.item_store_today_settlement_new)
                .addItemType(1, R.layout.item_store_today_invite);

    }


    private int type =0;

    public void setType(int type){
        this.type = type;
    }


    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, Object o) {

        if (baseViewHolder.getItemViewType() == 0){

            switch (type){
                case 0:
                    StoreTodaySettlementBean bean = (StoreTodaySettlementBean) o;

                    baseViewHolder.setText(R.id.tv_postion,"第"+(baseViewHolder.getPosition()+1)+"单");
                    baseViewHolder.setText(R.id.tv_name,bean.getName() +" "+bean.getPhone());

                    if (!ObjectUtils.isEmpty(bean.getTo_account_time())){
                        if (!ObjectUtils.isEmpty(bean.getCreate_time())){
                            baseViewHolder.setText(R.id.tv_time,TimeUtils.millis2String(Long.valueOf(bean.getCreate_time())*1000));
                        }

                    }

                    baseViewHolder.setText(R.id.tv_price,"￥"+bean.getReceipt_amount());
                    baseViewHolder.setText(R.id.tv_hm,"红米支付"+bean.getReceipt_amount());
                    break;
                case 1:
                    StoreTodaySettlementCashBean bean1 = (StoreTodaySettlementCashBean) o;

                    baseViewHolder.setText(R.id.tv_postion,"第"+(baseViewHolder.getPosition()+1)+"单");
                    baseViewHolder.setText(R.id.tv_name,bean1.getName() +" "+bean1.getPhone());

                        if (!ObjectUtils.isEmpty(bean1.getPaytime())){
                            baseViewHolder.setText(R.id.tv_time,TimeUtils.millis2String(Long.valueOf(bean1.getPaytime())*1000));
                        }


                    baseViewHolder.setText(R.id.tv_price,"￥"+bean1.getTotalprice());
                    baseViewHolder.setText(R.id.tv_hm,bean1.getPayment()+bean1.getTotalprice());
                    break;
            }


        }else if (baseViewHolder.getItemViewType() == 1){
            StoreTodayInviteBean bean = (StoreTodayInviteBean) o;
            if (!ObjectUtils.isEmpty(bean.getCreate_time())){
                baseViewHolder.setText(R.id.tv_time,TimeUtils.millis2String(Long.valueOf(bean.getCreate_time())*1000));

            }
            baseViewHolder.setText(R.id.tv_postion,'第'+(baseViewHolder.getPosition()+1)+"位");
            baseViewHolder.setText(R.id.tv_name,bean.getName() +" "+bean.getPhone());
        }

    }
}
