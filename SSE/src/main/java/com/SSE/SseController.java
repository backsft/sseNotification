package com.SSE;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequestMapping("/sse")
public class SseController {

    private final Map<String, SseEmitter> userEmitters = new ConcurrentHashMap<>();

    @GetMapping("/connect/{userId}")
    public SseEmitter connect(@PathVariable String userId) {
        SseEmitter emitter = new SseEmitter();
        userEmitters.put(userId, emitter);

        // Remove the emitter when the client disconnects
        emitter.onCompletion(() -> userEmitters.remove(userId, emitter));
        emitter.onTimeout(() -> userEmitters.remove(userId, emitter));

        return emitter;
    }

    @GetMapping("/notify/{userId}")
    public void notifyClient(@PathVariable String userId) {
        SseEmitter emitter = userEmitters.get(userId);
        if (emitter != null) {
            try {
                // Create a MyData object
                MyData myData = new MyData();
                myData.setId("123");
                myData.setName("John Doe");
                myData.setUsername("johndoe");
                myData.setDetails("Some details");

                // Send the MyData object to the specific user
                emitter.send(myData);
            } catch (IOException e) {
                // Handle exceptions (e.g., client disconnected)
                userEmitters.remove(userId, emitter);
            }
        }
    }
}
