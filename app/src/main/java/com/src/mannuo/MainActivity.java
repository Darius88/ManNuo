package com.src.mannuo;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.inuker.bluetooth.library.BluetoothClient;
import com.inuker.bluetooth.library.Constants;
import com.inuker.bluetooth.library.beacon.Beacon;
import com.inuker.bluetooth.library.beacon.BeaconItem;
import com.inuker.bluetooth.library.connect.listener.BleConnectStatusListener;
import com.inuker.bluetooth.library.connect.listener.BluetoothStateListener;
import com.inuker.bluetooth.library.connect.options.BleConnectOptions;
import com.inuker.bluetooth.library.connect.response.BleConnectResponse;
import com.inuker.bluetooth.library.connect.response.BleNotifyResponse;
import com.inuker.bluetooth.library.connect.response.BleReadResponse;
import com.inuker.bluetooth.library.connect.response.BleReadRssiResponse;
import com.inuker.bluetooth.library.model.BleGattCharacter;
import com.inuker.bluetooth.library.model.BleGattDescriptor;
import com.inuker.bluetooth.library.model.BleGattProfile;
import com.inuker.bluetooth.library.model.BleGattService;
import com.inuker.bluetooth.library.search.SearchRequest;
import com.inuker.bluetooth.library.search.SearchResult;
import com.inuker.bluetooth.library.search.response.SearchResponse;
import com.inuker.bluetooth.library.utils.BluetoothLog;
import com.src.mannuo.BluetoothManage.BluetoothClientTask;
import com.src.mannuo.base.SimpleTitleActivity;
import com.src.mannuo.eventbus.MainBatteryEventBean;
import com.src.mannuo.menu.MainMenuActivity;
import com.src.mannuo.utils.BluetoothClientUtil;
import com.src.mannuo.utils.ToastUtil;
import com.src.mannuo.view.SearchBlueImageView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import permison.PermissonUtil;
import permison.listener.PermissionListener;

import static com.inuker.bluetooth.library.Constants.REQUEST_SUCCESS;
import static com.inuker.bluetooth.library.Constants.STATUS_CONNECTED;
import static com.inuker.bluetooth.library.Constants.STATUS_DEVICE_CONNECTING;
import static com.inuker.bluetooth.library.Constants.STATUS_DISCONNECTED;

public class MainActivity extends SimpleTitleActivity implements SearchResponse, PermissionListener, BleConnectResponse, BleNotifyResponse {

    SearchBlueImageView mIvBlueScanRote;

    BluetoothClient mClient;

    BluetoothClientTask task;
    SearchRequest request;
    BleConnectOptions options;

    TextView mTvSearchConnectStatus;

    String[] permissions = new String[]{Manifest.permission.BLUETOOTH, Manifest.permission.BLUETOOTH_ADMIN,
            Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.WRITE_EXTERNAL_STORAGE
            ,Manifest.permission.READ_EXTERNAL_STORAGE
    };


    private List<SearchResult> mListBlues;
    private String[] mBlueAddress;
    private String[] mBlueNames;

    private String mac = "";

    private String BUNDLE_BLUE_MAC = "bundle_blue_mac";
    private String BUNDLE_BLUE_ADDRESS = "bundle_blue_address";
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    public int setContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        super.initView();
        sharedPreferences = getSharedPreferences(BUNDLE_BLUE_MAC, Context.MODE_PRIVATE); //私有数据
        editor = sharedPreferences.edit();
        mIvBlueScanRote = findViewById(R.id.iv_blue_scan_rote);
        mTvSearchConnectStatus = findViewById(R.id.tv_search_connect_status);
        mIvBlueScanRote.setOnClickListener(this);
        mListBlues = new ArrayList<>();
        PermissonUtil.checkPermission(MainActivity.this, this, permissions);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.iv_blue_scan_rote:
                if (!mIvBlueScanRote.isSearching()) {
                    if (!mClient.isBluetoothOpened()) {
                       ToastUtil.show(this,"您的手机蓝牙未打开，请先打开您的蓝牙");
                    } else {
                        if (!TextUtils.isEmpty(mac) && mClient.getConnectStatus(mac) == Constants.STATUS_DEVICE_CONNECTED) {
                            startActivity(MainMenuActivity.class);
                        } else {
                            mIvBlueScanRote.startSearchBlue();
                            mClient.search(request, this);
                        }
                    }
                }
                break;
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (null == request) {
            request = BluetoothClientUtil.getBluetoothSearchRequest();
        }
        if (null == options) {
            options = BluetoothClientUtil.getBluetoothConnection();
        }
        if (null == task) {
            task = BluetoothClientTask.getInstance();
        }
        if (null == mClient) {
            mClient = task.getClient(this);
        }
        if (!TextUtils.isEmpty(mac) && mClient.getConnectStatus(mac) == Constants.STATUS_DEVICE_CONNECTED) {
            mTvSearchConnectStatus.setText("设备已连接 点击Man Nuo商标直接进入");
        }
    }


    /***
     * 开始搜索蓝牙
     */
    @Override
    public void onSearchStarted() {
        Log.e("===========", "=====onSearchStarted======");
        mListBlues.clear();
        mTvSearchConnectStatus.setText(getResources().getString(R.string.ble_start_scan));
    }

    /***
     * 搜索到蓝牙设备
     */
    @Override
    public void onDeviceFounded(SearchResult device) {
        Log.e("===address===", device.getAddress());
        Log.e("===name===", device.getName());
        Beacon beacon = new Beacon(device.scanRecord);
        //device对象就包括了设备的mac地址和蓝牙名称 ，将device放在list里面 按mac地址做唯一区别
        if (device.getName().equals(product_1) || device.getName().equals(product_2)) {
            if(!mListBlues.contains(device)){
                mListBlues.add(device);
            }

        }
    }


    /***
     * 停止搜索蓝牙
     */
    @SuppressLint("NewApi")
    @Override
    public void onSearchStopped() {
        mIvBlueScanRote.stopSearchBlue();
        if (null != mListBlues && mListBlues.size() != 0) {
            mBlueAddress = new String[mListBlues.size()];
            mBlueNames = new String[mListBlues.size()];

            for (int i = 0; i < mListBlues.size(); i++) {
                mBlueAddress[i] = mListBlues.get(i).getAddress();
                mBlueNames[i] = mListBlues.get(i).getName();
            }

            if (mBlueAddress.length == 1 && mBlueAddress[0].equals(sharedPreferences.getString(BUNDLE_BLUE_ADDRESS, ""))) {
                mac = sharedPreferences.getString(BUNDLE_BLUE_ADDRESS, "");
                mDialog.Show("正在连接最近设备...");
                mClient.connect(mac, options, MainActivity.this);

                mClient.registerConnectStatusListener(mac, mBleConnectStatusListener);
            } else {
                new AlertDialog.Builder(this).setTitle(getResources().getString(R.string.dialog_title)).setItems(mBlueNames, onClickItemListener).show();
            }
        } else {
            mTvSearchConnectStatus.setText(getResources().getString(R.string.ble_scanner_over_nofind));
        }
    }


    /***
     * 点击扫描出的蓝牙设备
     */
    DialogInterface.OnClickListener onClickItemListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            dialogInterface.dismiss();
            mac = mBlueAddress[i];
            mDialog.Show("正在连接蓝牙设备");
            mClient.connect(mac, options, MainActivity.this);
            mClient.registerConnectStatusListener(mac, mBleConnectStatusListener);
        }
    };


    /***
     * 手动关闭蓝牙搜索
     */
    @Override
    public void onSearchCanceled() {
        Log.e("===========", "=====onSearchCanceled======");
        mIvBlueScanRote.stopSearchBlue();
    }

    /***
     * android 6.0以上获取权限成功回调
     */
    @Override
    public void havePermission() {
        Log.e("====", "======权限获取成功==");
    }

    /***
     * android 6.0以上获取权限失败回调
     */
    @Override
    public void requestPermissionFail() {
        Log.e("====", "======权限获取失败==");
    }

    /***
     * 解绑蓝牙状态监听
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mClient.unregisterConnectStatusListener(mac, mBleConnectStatusListener);
    }


    private final BleConnectStatusListener mBleConnectStatusListener = new BleConnectStatusListener() {
        @Override
        public void onConnectStatusChanged(String mac, int status) {
            switch (status) {
                case STATUS_CONNECTED:
                    Log.e("==", "==蓝牙已连接==");
                    break;
                case STATUS_DISCONNECTED:
                    Log.e("==", "==蓝牙已断开==");
                    mClient.disconnect(mac);
                    mDialog.dismiss();
                    mTvSearchConnectStatus.setText(getResources().getString(R.string.ble_disConnected));
                    mApp.clearActivityToList();
                    mClient.unregisterConnectStatusListener(mac, mBleConnectStatusListener);
                    break;
                case STATUS_DEVICE_CONNECTING:
                    Log.e("==", "==蓝牙正在连接==");
                    break;
            }

        }
    };


    /***
     * 蓝牙设备连接回调
     * @param code
     * @param data
     */
    @Override
    public void onResponse(int code, BleGattProfile data) {
        switch (code) {
            case Constants.REQUEST_SUCCESS:
                mDialog.dimissSuc("蓝牙连接成功");
                mClient.notify(mac, UUID.fromString(SERVICE), UUID.fromString(CHARACT_NOTIFY), MainActivity.this);
                mTvSearchConnectStatus.setText(getResources().getString(R.string.ble_connected));
                startActivity(MainMenuActivity.class);
                editor.putString(BUNDLE_BLUE_ADDRESS, mac);
                editor.commit();
                break;
            case Constants.REQUEST_FAILED:
                mDialog.dimissFail("蓝牙连接失败");
                break;
            case Constants.REQUEST_TIMEDOUT:
                mDialog.dimissFail("连接超时");
                break;
        }
    }


    /**
     * 解析数据
     *
     * @return 返回指令
     */
    @SuppressLint("NewApi")
    private String parseData(byte[] value) {
        if (value[2] == 4) {
            //电量
            String v = value[5] + "" + value[4];
            int tempDian = Integer.parseInt(v);
            mApp.setmElectric(String.valueOf(tempDian));
            EventBus.getDefault().post(new MainBatteryEventBean());
        } else if (value[2] == 9) {
            if (value[7] == 2) {
                //发热蛋

            } else {
                //聪明球
            }
        }

        return "";
    }

    ;

    @Override
    public void onNotify(UUID service, UUID character, byte[] value) {
        if (null != value) {
            parseData(value);
        }

    }


    @Override
    public void onResponse(int code) {
        Log.e("====", "===code==" + code);
    }
}
