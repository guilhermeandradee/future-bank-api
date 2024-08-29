package br.com.project.futureBank.Controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("ping")
public class PingController {

    @GetMapping(value = "/ping")
    public void getPing(){
        int pingCount = 0;

        pingCount++;

        System.out.println("ping " + pingCount);
    }
}
