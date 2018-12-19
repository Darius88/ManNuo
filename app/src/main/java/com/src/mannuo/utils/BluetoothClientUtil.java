package com.src.mannuo.utils;

import com.inuker.bluetooth.library.connect.options.BleConnectOptions;
import com.inuker.bluetooth.library.search.SearchRequest;

/**
 * Created by ZM on 2018/12/18 0018.
 */

public class BluetoothClientUtil {

    public static SearchRequest getBluetoothSearchRequest() {
        SearchRequest request = new SearchRequest.Builder()
                .searchBluetoothLeDevice(3000, 3)   // 先扫BLE设备3次，每次3s
                .searchBluetoothClassicDevice(5000) // 再扫经典蓝牙5s
                .searchBluetoothLeDevice(2000)      // 再扫BLE设备2s
                .build();
        return request;
    }

    public static BleConnectOptions getBluetoothConnection() {
        BleConnectOptions options =  new BleConnectOptions.Builder()
                .setConnectRetry(3)   // 连接如果失败重试3次
                .setConnectTimeout(30000)   // 连接超时30s
                .setServiceDiscoverRetry(3)  // 发现服务如果失败重试3次
                .setServiceDiscoverTimeout(20000)  // 发现服务超时20s
                .build();
        return options;
    }


}
