package com.SSE;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequestMapping("/sse")
public class SseController {

    private final CopyOnWriteArrayList<SseEmitter> emitters = new CopyOnWriteArrayList<>();

    @GetMapping("/connect")
    public SseEmitter connect() {
        SseEmitter emitter = new SseEmitter();
        emitters.add(emitter);

        // Remove the emitter when the client disconnects
        emitter.onCompletion(() -> emitters.remove(emitter));
        emitter.onTimeout(() -> emitters.remove(emitter));

        return emitter;
    }

    @GetMapping("/notify")
    public void notifyClients() {
        for (SseEmitter emitter : emitters) {
            try {
                // Create a MyData object
                MyData myData = new MyData();
                myData.setId("123");
                myData.setName("John Doe");
                myData.setUsername("johndoe");
                myData.setDetails("Some details");

                // Send the MyData object to all connected clients
                emitter.send(myData);
            } catch (IOException e) {
                // Handle exceptions (e.g., client disconnected)
                emitters.remove(emitter);
            }
        }
    }
}
