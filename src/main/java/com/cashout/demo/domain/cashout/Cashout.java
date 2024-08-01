package com.cashout.demo.domain.cashout;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "cashout")
public class Cashout {
    @Id
    private String id;
    private String userId;
    private long amount;
}
