package matchingEngine;

import java.util.ArrayList;
import java.util.Comparator;
// total branches : 30
// never covered with valid orders : 1

public class OrderBook {
    ArrayList<Order> buy_order_ids = new ArrayList<>();
    ArrayList<Order> sell_order_ids = new ArrayList<>();

    public String orders_toString(ArrayList<Order> orders) {
        TCRunner.method_called();
        StringBuilder output = new StringBuilder();
        for(Order order : orders)
            output.append(order.toString());
        return output.toString();
    }

    @Override
    public String toString() {
        TCRunner.method_called();
        return "\tOrders\t" + (buy_order_ids.size() + sell_order_ids.size()) +
                orders_toString(buy_order_ids) + orders_toString(sell_order_ids);
    }

    public void add_order(Order order) {
        TCRunner.method_called();
        if(order.is_buy) {
            TCRunner.condition_covered();
            buy_order_ids.add(order);
            sort_buy_orders();
        }
        else {
            sell_order_ids.add(order);
            sort_sell_orders();
        }
        order.order_added_to_queue();
    }

    public void remove_order(Order order) {
        TCRunner.method_called();
        if(order.is_buy) {
            TCRunner.condition_covered();
            buy_order_ids.remove(order);
            sort_buy_orders();
        }
        else {
            sell_order_ids.remove(order);
            sort_sell_orders();
        }
        order.order_removed_from_queue();
    }

    public void sort_buy_orders() {
        TCRunner.method_called();
        buy_order_ids.sort(Comparator.comparingInt(o -> -o.price));
    }

    public void sort_sell_orders() {
        TCRunner.method_called();
        sell_order_ids.sort(Comparator.comparingInt(o -> o.price));
    }

    public Order get_first_with_positive_quantity(ArrayList<Order> orders) {
        TCRunner.method_called();
        for(Order order : orders)
            if(order.quantity > 0) {
                TCRunner.condition_covered();
                return order;
            }
        return null;
    }

    public Order get_first_buy_order() {
        TCRunner.method_called();
        return get_first_with_positive_quantity(buy_order_ids);
    }

    public Order get_first_sell_order() {
        TCRunner.method_called();
        return get_first_with_positive_quantity(sell_order_ids);
    }

    public void remove_empty_orders() {
        TCRunner.method_called();
        remove_empty_orders(buy_order_ids);
        remove_empty_orders(sell_order_ids);
    }

    public void remove_empty_orders(ArrayList<Order> orders) {
        TCRunner.method_called();
        int i = 0;
        while(i < orders.size()) {
            if(orders.get(i).quantity == 0) {
                TCRunner.condition_covered();
                remove_order(orders.get(i));
            }
            else
                i++;
        }
    }

    public void rollback_order_book(Trade trade) {
        TCRunner.method_called();
        if(trade.buy_order_id.is_in_queue){
            TCRunner.condition_covered();
            buy_order_ids.remove(trade.buy_order_id);
            buy_order_ids.add(0, trade.buy_order_id);
        }
        else{
            sell_order_ids.remove(trade.sell_order_id);
            sell_order_ids.add(0, trade.sell_order_id);
        }
    }

    public Order get_order(ArrayList<Order> orders, int order_id) {
        TCRunner.method_called();
        for(Order order : orders)
            if(order.id == order_id) {
                TCRunner.condition_covered();
                return order;
            }
        return null;
    }

    public Order get_order(int order_id) {
        TCRunner.method_called();
        Order buy_order = get_order(buy_order_ids, order_id);
        return (buy_order != null)? buy_order : get_order(sell_order_ids, order_id);
    }

    public int get_order_index(Order order) {
        TCRunner.method_called();
        ArrayList<Order> queue = (order.is_buy)? buy_order_ids:sell_order_ids;
        for(int i=0; i<queue.size(); i++)
            if(queue.get(i).id == order.id) {
                TCRunner.condition_covered();
                return i;
            }
        return 0;
    }

    public void insert_order(Order order, int index) {
        TCRunner.method_called();
        if(order.is_buy) {
            TCRunner.condition_covered();
            buy_order_ids.add(index, order);
        }
        else
            sell_order_ids.add(index, order);
        order.order_added_to_queue();
    }
}









































