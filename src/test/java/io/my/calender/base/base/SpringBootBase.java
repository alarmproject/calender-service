package io.my.calender.base.base;

import io.my.calender.base.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SpringBootBase {

    @Autowired
    private JwtUtil jwtUtil;


}
