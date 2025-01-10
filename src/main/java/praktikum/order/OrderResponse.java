package praktikum.order;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderResponse {
    private boolean success;
    private String name;
    private Order order;
    private List<Orders> orders;
    private int total;
    private int totalToday;

}