package com.investor.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class NotifyMessageDTO implements Serializable {
    private Long userId;
    private String type;
    private String title;
    private String content;
}
