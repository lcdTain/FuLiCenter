package day1010.fulicenter.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import day1010.fulicenter.Adapter.CategoryAdapter;
import day1010.fulicenter.R;
import day1010.fulicenter.activity.MainActivity;
import day1010.fulicenter.bean.CategoryChildBean;
import day1010.fulicenter.bean.CategoryGroupBean;
import day1010.fulicenter.fragment.BaseFragment;
import day1010.fulicenter.net.NetDao;
import day1010.fulicenter.utils.ConvertUtils;
import day1010.fulicenter.utils.OkHttpUtils;

/**
 * Created by Administrator on 2016/10/20.
 */
public class CategoryFragment extends BaseFragment {

    @Bind(R.id.elvCategory)
    ExpandableListView elvCategory;

    CategoryAdapter mAdapter;
    MainActivity context;
    ArrayList<CategoryGroupBean> groupList;
    ArrayList<ArrayList<CategoryChildBean>> childList;
    int groupCount;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_category, container, false);
        ButterKnife.bind(this, layout);
        context = (MainActivity) getContext();
        groupList = new ArrayList<>();
        childList = new ArrayList<>();
        mAdapter = new CategoryAdapter(context,groupList,childList);
        super.onCreateView(inflater, container, savedInstanceState);
        return layout;
    }

    @Override
    protected void initView() {
        elvCategory.setGroupIndicator(null);
        elvCategory.setAdapter(mAdapter);

    }

    @Override
    protected void initData() {
        downloadGroup();

    }

    private void downloadGroup() {
        NetDao.downloadCategoryGroup(context, new OkHttpUtils.OnCompleteListener<CategoryGroupBean[]>() {
            @Override
            public void onSuccess(CategoryGroupBean[] result) {
                if (result != null && result.length>0){
                    ArrayList<CategoryGroupBean> groupBeanList = ConvertUtils.array2List(result);
                    groupList.addAll(groupBeanList);
                    for(int i = 0;i<groupBeanList.size();i++){
                        childList.add(new ArrayList<CategoryChildBean>());
                        CategoryGroupBean g = groupBeanList.get(i);
                        downloadChild(g.getId(),i);
                    }
                }
            }

            @Override
            public void onError(String error) {

            }
        });
    }

    private void downloadChild(int id,final int index) {
        NetDao.downloadCategoryChild(context, id, new OkHttpUtils.OnCompleteListener<CategoryChildBean[]>() {
            @Override
            public void onSuccess(CategoryChildBean[] result) {
                groupCount++;
                if (result != null && result.length>0){
                    ArrayList<CategoryChildBean> childBeanList = ConvertUtils.array2List(result);
                    childList.set(index,childBeanList);
                }
                if (groupCount == groupList.size()){
                    mAdapter.initData(groupList,childList);
                }
            }

            @Override
            public void onError(String error) {

            }
        });
    }

    @Override
    protected void setListener() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
