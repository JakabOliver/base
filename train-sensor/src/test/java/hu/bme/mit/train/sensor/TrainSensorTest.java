package hu.bme.mit.train.sensor;

import hu.bme.mit.train.interfaces.TrainController;
import hu.bme.mit.train.interfaces.TrainUser;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;

public class TrainSensorTest {
    TrainController mockTc;
    TrainUser mockTu;
    TrainSensorImpl trainSensor;

    @Before
    public void before() {
        mockTc = mock(TrainController.class);
        mockTu = mock(TrainUser.class);
        trainSensor = new TrainSensorImpl(mockTc, mockTu);
    }

    @Test
    public void absolutMarginDownTest() {
        trainSensor.overrideSpeedLimit(-10);
        verify(mockTu, times(1)).setAlarmState(true);
    }

    @Test
    public void absolutMarginUpTest() {
        trainSensor.overrideSpeedLimit(510);
        verify(mockTu, times(1)).setAlarmState(true);
    }

    @Test
    public void relativMarginDownTest() {
        when(mockTc.getReferenceSpeed()).thenReturn(5);
        trainSensor.overrideSpeedLimit(2);
        verify(mockTu, times(1)).setAlarmState(true);
    }

    @Test
    public void higherLimitTest() {
        when(mockTc.getReferenceSpeed()).thenReturn(5);
        trainSensor.overrideSpeedLimit(7);
        verify(mockTu, times(0)).setAlarmState(false);
    }
}
