package com.creapple.cafe_manager;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {


    private InventoryMainActivity.GetData getdata;

    @Before
    public void init() {
    }
    
    @Test
    // 기댓값과 실제 값이 같으면 테스트 통과. 다를시 테스트에 실패하는 구조
    public void addition_isCorrect() {
        assertEquals(4, getdata.doInBackground());
    }

//    protected String doInBackground(String... params) {
//
//        return null;
//    }
}

//    assertArrayEquals(a,b) : 배열 a와b가 일치함을 확인
//    assertEquals(a,b) : 객체 a와b의 값이 같은지 확인
//    assertSame(a,b) : 객체 a와b가 같은 객체임을 확인
//    assertTrue(a) : a가 참인지 확인
//    assertNotNull(a) : a객체가 null이 아님을 확인
