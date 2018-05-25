package com.iolll.liubo.retrofitrxjava;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.iolll.liubo.retrofitrxjava.model.bean.GankData;
import com.iolll.liubo.retrofitrxjava.model.net.DataManager;
import com.iolll.liubo.retrofitrxjava.model.net.RxObserverWithProgress;
import com.iolll.liubo.retrofitrxjava.model.viewbinder.GankDataViewBinder;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;

public class MainActivity extends BaseActivity {


    @BindView(R.id.list)
    RecyclerView list;
    @BindView(R.id.smartRefreshLayout)
    SmartRefreshLayout smartRefreshLayout;
    private TextView showText;
    private int page;
    private Items items = new Items();
    private MultiTypeAdapter adapter = new MultiTypeAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        adapter.setItems(items);
        adapter.register(GankData.class,new GankDataViewBinder());
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter(adapter);
        smartRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                getData(page++);
            }

            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                getData(1,true);
            }
        });
        smartRefreshLayout.autoRefresh();

    }

    public void getData(int page){
        getData(page,false);
    }
    public void getData(int page,boolean isRefresh) {
        DataManager.getType("Android", page)
                .compose(provider.bindToLifecycle())
                .subscribe(new RxObserverWithProgress<ArrayList<GankData>>(this, false) {
                    @Override
                    public void onSuccess(ArrayList<GankData> response) {
                        if (isRefresh) {
                            smartRefreshLayout.finishRefresh();
                            items.clear();
                        }else{
                            smartRefreshLayout.finishLoadMore();
                        }
                        items.addAll(response);
                        adapter.notifyDataSetChanged();

                    }

                    @Override
                    public void onFailed(ArrayList<GankData> response) {
                        super.onFailed(response);
                    }
                });
    }
}
