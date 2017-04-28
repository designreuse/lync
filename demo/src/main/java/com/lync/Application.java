package com.lync;


import com.lync.core.toolbox.kit.SpringKit;
import com.lync.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@ServletComponentScan
@Import(value={SpringKit.class})
public class Application {
    @Autowired
	UserService userService;
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
