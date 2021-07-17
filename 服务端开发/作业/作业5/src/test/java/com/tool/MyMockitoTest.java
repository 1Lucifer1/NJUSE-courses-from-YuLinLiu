package com.tool;

import org.junit.Test;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class MyMockitoTest {
    //模拟接口
    @Test
    public void testMockitoInterface() {
        // 创建mock对象
        List mockedList = mock(List.class);

        // 使用模拟对象(而不是真实对象)
        mockedList.add("one");
        mockedList.clear();

        // 验证方法是否被调用
        verify(mockedList).add("one");
        verify(mockedList).clear();
    }

    //模拟具体类
    @Test
    public void testMockitoConcreteClass() {
        // you can mock concrete classes, not only interfaces
        LinkedList mockedList = mock(LinkedList.class);

        // stubbing appears before the actual execution
        when(mockedList.get(0)).thenReturn("first");

        // the following prints "first"
        System.out.println(mockedList.get(0));

        // the following prints "null" because get(999) was not stubbed
        System.out.println(mockedList.get(999));

//        verify(mockedList,atLeastOnce()).get(anyInt());
    }

    //可选参数
    @Test
    public void returnsSmartNullsTest() {
        List mock = mock(List.class, RETURNS_SMART_NULLS);

        //使用RETURNS_SMART_NULLS参数创建的mock对象，不会抛出NullPointerException异常。另外控制台窗口会提示信息“SmartNull returned by this unstubbed method call on a mock:list.get(0);”
        System.out.println(mock.get(0));

        System.out.println(mock.toArray().length);
    }

    //多个thenReturn
    @Test
    public void when_thenReturn() {
        //mock一个Iterator类
        Iterator iterator = mock(Iterator.class);

        //预设当iterator调用next()时第一次返回hello，第n次都返回world
        when(iterator.next()).thenReturn("hello").thenReturn("world");

        //使用mock的对象
        String result = iterator.next() + " " + iterator.next() + " " + iterator.next();

        //验证结果
        assertEquals("hello world world", result);
//        verify(iterator,atLeastOnce()).next();
    }

    //模拟方法体抛出异常
    @Test(expected = IOException.class)
    public void when_thenThrow() throws IOException {
        OutputStream outputStream = mock(OutputStream.class);
        OutputStreamWriter writer = new OutputStreamWriter(outputStream);

        //预设当流关闭时抛出异常
        doThrow(new IOException()).when(outputStream).close();

//        outputStream.close();
        writer.close();
    }
}