package com.eden.core.idgen;

import com.eden.core.result.ResultEnum;
import com.eden.core.util.AssertUtil;

public class IDWorker {

    private static volatile SnowFlake snowFlake;

    public static long nextId() {
        init();
        return snowFlake.nextId();
    }

    private static void init() {
        if (snowFlake == null) {
            synchronized (IDWorker.class) {
                if (snowFlake == null) {
                    String machineId = System.getProperty("machineId");
                    AssertUtil.notBlank(machineId, ResultEnum.MACHINEID_NOT_EXIST);
                    String dataCenterId = System.getProperty("dataCenterId");
                    AssertUtil.notBlank(dataCenterId, ResultEnum.DATACENTERID_NOT_EXIST);

                    snowFlake = new SnowFlake(Integer.valueOf(machineId), Integer.valueOf(dataCenterId));
                }
            }
        }
    }

}
