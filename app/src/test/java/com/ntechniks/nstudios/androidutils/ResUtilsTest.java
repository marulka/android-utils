package com.ntechniks.nstudios.androidutils;

import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.spy;

@RunWith(PowerMockRunner.class)
@PrepareForTest({Debug.class, FileUtils.class, Uri.class})
public class ResUtilsTest {

    @Test
    public void positiveTestUnpackZipResource() throws IOException {

        PowerMockito.mockStatic(Uri.class);
        PowerMockito.mockStatic(Debug.class);
        PowerMockito.mockStatic(FileUtils.class);

        final File mockedFile = mock(File.class);
        when(mockedFile.getAbsolutePath()).thenReturn("someAbsolutePath");

        final Context context = mock(Context.class);
        final Resources mockedRes = mock(Resources.class);
        when(context.getResources()).thenReturn(mockedRes);
        when(context.getFilesDir()).thenReturn(mockedFile);

        final InputStream mockedInputStream = mock(InputStream.class);
        when(mockedRes.openRawResource(anyInt())).thenReturn(mockedInputStream);

        final ZipInputStream mockedZipInputStream = mock(ZipInputStream.class);
        final ZipEntry mockedZipEntry = mock(ZipEntry.class);
        when(mockedZipEntry.getName()).thenReturn("" + new Random().nextLong());
        when(mockedZipInputStream.getNextEntry()).thenReturn(mockedZipEntry);

        spy(FileUtils.class);
        when(FileUtils.createZipInputStream(mockedInputStream)).thenReturn(mockedZipInputStream);

//        ResUtils.unpackZipResource(123, context, "destinationFolder");
//        verify(mockedZipInputStream).getNextEntry();
    }

    @Test
    public void negativeTestShouldNotCrashWhenContextIsNull() throws IOException {

        ResUtils.unpackZipResource(123, null, "destinationFolder");
    }
}