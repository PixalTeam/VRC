package com.google.appinventor.components.runtime;

import com.google.appinventor.components.annotations.PropertyCategory;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.SimpleProperty;
import com.google.appinventor.components.runtime.util.ErrorMessages;

@SimpleObject
public abstract class LegoMindstormsNxtSensor extends LegoMindstormsNxtBase {
    static final int SENSOR_MODE_ANGLESTEPMODE = 224;
    static final int SENSOR_MODE_BOOLEANMODE = 32;
    static final int SENSOR_MODE_CELSIUSMODE = 160;
    static final int SENSOR_MODE_FAHRENHEITMODE = 192;
    static final int SENSOR_MODE_MASK_MODE = 224;
    static final int SENSOR_MODE_MASK_SLOPE = 31;
    static final int SENSOR_MODE_PCTFULLSCALEMODE = 128;
    static final int SENSOR_MODE_PERIODCOUNTERMODE = 96;
    static final int SENSOR_MODE_RAWMODE = 0;
    static final int SENSOR_MODE_TRANSITIONCNTMODE = 64;
    static final int SENSOR_TYPE_ANGLE = 4;
    static final int SENSOR_TYPE_CUSTOM = 9;
    static final int SENSOR_TYPE_LIGHT_ACTIVE = 5;
    static final int SENSOR_TYPE_LIGHT_INACTIVE = 6;
    static final int SENSOR_TYPE_LOWSPEED = 10;
    static final int SENSOR_TYPE_LOWSPEED_9V = 11;
    static final int SENSOR_TYPE_NO_SENSOR = 0;
    static final int SENSOR_TYPE_REFLECTION = 3;
    static final int SENSOR_TYPE_SOUND_DB = 7;
    static final int SENSOR_TYPE_SOUND_DBA = 8;
    static final int SENSOR_TYPE_SWITCH = 1;
    static final int SENSOR_TYPE_TEMPERATURE = 2;
    protected int port;
    private String sensorPortLetter;

    static class SensorValue<T> {
        final boolean valid;
        final T value;

        SensorValue(boolean valid2, T value2) {
            this.valid = valid2;
            this.value = value2;
        }
    }

    public abstract void SensorPort(String str);

    /* access modifiers changed from: protected */
    public abstract void initializeSensor(String str);

    protected LegoMindstormsNxtSensor(ComponentContainer container, String logTag) {
        super(container, logTag);
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "The sensor port that the sensor is connected to.", userVisible = false)
    public String SensorPort() {
        return this.sensorPortLetter;
    }

    /* access modifiers changed from: protected */
    public final void setSensorPort(String sensorPortLetter2) {
        String functionName = "SensorPort";
        try {
            int port2 = convertSensorPortLetterToNumber(sensorPortLetter2);
            this.sensorPortLetter = sensorPortLetter2;
            this.port = port2;
            if (this.bluetooth != null && this.bluetooth.IsConnected()) {
                initializeSensor(functionName);
            }
        } catch (IllegalArgumentException e) {
            this.form.dispatchErrorOccurredEvent(this, functionName, ErrorMessages.ERROR_NXT_INVALID_SENSOR_PORT, sensorPortLetter2);
        }
    }

    public void afterConnect(BluetoothConnectionBase bluetoothConnection) {
        initializeSensor("Connect");
    }
}
