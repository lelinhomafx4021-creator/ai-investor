package com.investor.component;

import cn.hutool.core.bean.BeanUtil;
import com.investor.config.RabbitConfig;
import com.investor.entity.dto.NotifyMessageDTO;
import com.investor.entity.po.Notification;
import com.investor.service.INotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class ConsumerQueue {
    private INotificationService notificationService;

    @RabbitListener(queues =RabbitConfig.QUEUE_NAME)
    public void handle(NotifyMessageDTO notifyMessageDTO){
        Notification notification = BeanUtil.copyProperties(notifyMessageDTO, Notification.class);
        notificationService.save(notification);
        log.info("消息通知成功");

    }

}
