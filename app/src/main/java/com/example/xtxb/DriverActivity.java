package com.example.xtxb;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiDetailSearchResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.example.xtxb.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.util.List;

public class DriverActivity extends AppCompatActivity {
    private MapView mMapView = null;
    private  LocationClient  mLocationClient=null;
    private PoiSearch mPoiSearch=null;
    private EditText mInputText=null;
    private BDLocation mCurLocation=null;
    private RoutePlanSearch mRoutePlanSrch=null;
    private File mSDCardPath=null;
    private static final String APP_FOLDER_NAME = "lmap";
    private PoiInfo mDestation=null;
    class MyLocationListener extends BDAbstractLocationListener {
        private boolean isFirstLoc=true;
        private boolean autoLocation=false;
        public void setAutoLocation(boolean b){
            autoLocation=b;
        }
        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            //mapView 销毁后不在处理新接收的位置
            if (bdLocation == null || mMapView == null){
                return;
            }
            BDLocation mCurLocation = bdLocation;//保存当前定位，后面检索算路要用
            int type=bdLocation.getLocType();
            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(bdLocation.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(bdLocation.getDirection()).latitude(bdLocation.getLatitude())
                    .longitude(bdLocation.getLongitude()).build();
            BaiduMap bmap=mMapView.getMap();
            bmap.setMyLocationData(locData);
            /**
             *当首次定位或手动发起定位时，记得要放大地图，便于观察具体的位置
             * LatLng是缩放的中心点，这里注意一定要和上面设置给地图的经纬度一致；
             * MapStatus.Builder 地图状态构造器
             */
            if (isFirstLoc||autoLocation) {
                isFirstLoc = false;
                autoLocation=false;
                LatLng ll = new LatLng(bdLocation.getLatitude(), bdLocation.getLongitude());
                MapStatus.Builder builder = new MapStatus.Builder();
                //设置缩放中心点；缩放比例；
                builder.target(ll).zoom(18.0f);
                //给地图设置状态
                bmap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
            }
        }
    }

    //创建poi检索监听器
    OnGetPoiSearchResultListener poiSearchListener = new OnGetPoiSearchResultListener() {
        @Override
        public void onGetPoiResult(PoiResult poiResult) {
            //显示搜索结果
            List<PoiInfo> poiList = poiResult.getAllPoi();

            if (poiList != null) { // Check if poiList is not null
                PoiAdapter adapter = new PoiAdapter(DriverActivity.this, R.layout.poi_item, poiList);
                ListView listView = findViewById(R.id.searchResult);
                listView.setAdapter(adapter);
                listView.setVisibility(View.VISIBLE);
                setupListViewItemClick(listView, poiList);
                //当滑动到底部时加载更多搜索结果
                listView.setOnScrollListener(new AbsListView.OnScrollListener() {
                    @Override
                    public void onScrollStateChanged(AbsListView view, int scrollState) {
                        if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                            // 判断是否滚动到底部
                            if (view.getLastVisiblePosition() == view.getCount() - 1) {
                                //加载更多
                                int curPage = poiResult.getCurrentPageNum();
                                int totalPage = poiResult.getTotalPageNum();
                                if (curPage < totalPage) {
                                    poiResult.setCurrentPageNum(curPage + 1);
                                    String city = "明溪县";
                                    String keyWord = "停车场";
                                    mPoiSearch.searchInCity(new PoiCitySearchOption()
                                            .city(city)
                                            .keyword(keyWord)
                                            .pageNum(curPage + 1));
                                }
                            }
                        }
                    }

                    @Override
                    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                        // Do nothing
                    }
                });
            } else {
                // Handle the case when poiList is null (e.g., show an error message)
                // For example, you can display a Toast message:
                Toast.makeText(DriverActivity.this, "No results found.", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onGetPoiDetailResult(PoiDetailSearchResult poiDetailSearchResult) {

        }
        @Override
        public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {

        }
        //废弃
        @Override
        public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {

        }
    };

    private void setupListViewItemClick(ListView listView, List<PoiInfo> poiList) {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PoiInfo selectedPoi = poiList.get(position);
                // 在点击时跳转到PoiDetailActivity，并传递选中的停车场信息
                Intent intent = new Intent(DriverActivity.this,PoinDetailActivity.class);
                intent.putExtra("poiName", selectedPoi.getName()); // 传递停车场名称
                intent.putExtra("poiAddress", selectedPoi.getAddress()); // 传递停车场地址
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver);

        //获取地图控件引用
        mMapView = (MapView) findViewById(R.id.bmapView);
        mMapView.getMap().setMyLocationEnabled(true);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //定位初始化
        try {
            LocationClient.setAgreePrivacy(true);
            mLocationClient = new LocationClient(this);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        //通过LocationClientOption设置LocationClient相关参数

        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // 打开gps
        option.setIsNeedAddress(true);
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(1000);
        //设置locationClientOption
        mLocationClient.setLocOption(option);
        //注册LocationListener监听器
        MyLocationListener myLocationListener = new MyLocationListener();
        mLocationClient.registerLocationListener(myLocationListener);
        //开启地图定位图层
        mLocationClient.start();

        //获得检索输入框控件
        ImageButton button_findpark = findViewById(R.id.button_findpark);
        button_findpark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String city = "明溪县";
                String keyWord = "停车场";

                // 执行搜索附近停车场操作
                boolean ret = mPoiSearch.searchInCity(new PoiCitySearchOption()
                        .city(city)
                        .keyword(keyWord)
                        .pageNum(0));

            }
        });
        //创建poi检索实例
        mPoiSearch = PoiSearch.newInstance();
        mPoiSearch.setOnGetPoiSearchResultListener(poiSearchListener);

        ImageButton locationBtn=this.findViewById(R.id.btn_getPlace);
        locationBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                mLocationClient.start();
            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mLocationClient.stop();
        mMapView.getMap().setMyLocationEnabled(false);
        mMapView.onDestroy();
    }
}