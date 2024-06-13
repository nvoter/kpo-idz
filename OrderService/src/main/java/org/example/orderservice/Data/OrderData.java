package org.example.orderservice.Data;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class OrderData {
    private String fromStation;
    private String toStation;
}
