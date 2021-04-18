package org.example.router.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.util.UUID;

@RedisHash("MESSAGE")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageCached implements Serializable {

    @Id
    private UUID uuid;

    private String content;

}
