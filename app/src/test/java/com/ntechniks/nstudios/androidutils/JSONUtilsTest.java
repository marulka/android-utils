package com.ntechniks.nstudios.androidutils;

import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@RunWith(PowerMockRunner.class)
@PrepareForTest(Debug.class)
public class JSONUtilsTest {

    @Test
    public void parseJSONData() {

        final String data = "{ 'data': [ '100', '101', '102' ], 'message': 'Some string here' }";
        final JSONObject json = JSONUtils.parseJSONData(data);

        assertNotNull(json);
    }

    @Test
    public void parseNullJSONShouldReturnNullPointer() {

        PowerMockito.mockStatic(Debug.class);
        final JSONObject json = JSONUtils.parseJSONData(null);

        assertNull(json);
    }
}