package com.translationagency.ordermanager.data;

import com.translationagency.ordermanager.controller.OrderController;
import com.translationagency.ordermanager.entity.Order;
import com.translationagency.ordermanager.entity.OrderStatus;
import com.translationagency.ordermanager.to.order.OrderDetailTo;
import com.translationagency.ordermanager.to.order.OrderTo;
import com.translationagency.ordermanager.util.OrderUtil;

import java.time.LocalDate;
import java.util.List;

import static com.translationagency.ordermanager.data.DocumentTestData.*;

public class OrderTestData {

    public static final String URL = "/" + OrderController.ORDER_REST_URL;
    public static final Order nancyOrder = new Order(
            1, "Nancy", "+380981233344,nancy.thomas@gmail.com", 600,
            -150, 450, LocalDate.parse("2023-02-15"), LocalDate.parse("2023-02-17"),
            OrderStatus.IN_WORK, "check naming", null, null
    );

    public static final Order karenOrder = new Order(
            2, "Karen", "+380978419375,karen.jackson@gmail.com", 2000,
            100, 2100, LocalDate.parse("2023-02-18"), LocalDate.parse("2023-02-25"),
            OrderStatus.IN_WORK, "important note", null, null
    );

    public static final Order bettyOrder = new Order(
            3, "Betty", "+380395520473,betty.white@gmail.com", 300,
            1450, 1750, LocalDate.parse("2023-02-20"), LocalDate.parse("2023-02-21"),
            OrderStatus.IN_WORK, "make coffee", null, null
    );

    public static final Order helenOrder = new Order(
            4, "Helen", "+380654584344,helen.harris@gmail.com", 2000,
            350, 2350, LocalDate.parse("2023-02-21"), LocalDate.parse("2023-02-22"),
            OrderStatus.IN_WORK, "", null, null
    );

    public static final Order sandraOrder = new Order(
            5, "Sandra", "+380981232946,sandra.martin@gmail.com", 1300,
            0, 1300, LocalDate.parse("2023-02-21"), LocalDate.parse("2023-02-24"),
            OrderStatus.IN_WORK, "", null, null
    );

    public static final Order donnaOrder = new Order(
            6, "Donna", "+380231231174,donna.thompson@gmail.com", 1800,
            -50, 1750, LocalDate.parse("2023-02-22"), LocalDate.parse("2023-02-23"),
            OrderStatus.IN_WORK, "", null, null
    );

    public static final Order carolOrder = new Order(
            7, "Carol", "+380991209844,carol.garcia@gmail.com", 850,
            0, 850, LocalDate.parse("2023-02-22"), LocalDate.parse("2023-02-24"),
            OrderStatus.IN_WORK, "print copies", null, null
    );

    public static final Order ruthOrder = new Order(
            8, "Ruth", "+380981235332,ruth.martinez@gmail.com", 600,
            0, 600, LocalDate.parse("2023-02-22"), LocalDate.parse("2023-02-23"),
            OrderStatus.IN_WORK, "", null, null
    );

    public static final Order sharonOrder = new Order(
            9, "Sharon", "+380672638588,sharon.robinson@gmail.com", 850,
            0, 850, LocalDate.parse("2023-02-23"), LocalDate.parse("2023-02-28"),
            OrderStatus.IN_WORK, "", null, null
    );

    public static final Order michelleOrder = new Order(
            10, "Michelle", "+380980300293,michelle.clark@gmail.com", 300,
            30, 330, LocalDate.parse("2023-02-23"), LocalDate.parse("2023-02-24"),
            OrderStatus.IN_WORK, "send to email", null, null
    );

    public static final Order lauraOrder = new Order(
            11, "Laura", "+380661232315,laura.rodriguez@gmail.com", 570,
            -50, 570, LocalDate.parse("2023-02-23"), LocalDate.parse("2023-02-25"),
            OrderStatus.IN_WORK, "", null, null
    );

    public static final Order sarahOrder = new Order(
            12, "Sarah", "+380669638432,sarah.lewis@gmail.com", 300,
            0, 300, LocalDate.parse("2023-02-24"), LocalDate.parse("2023-02-26"),
            OrderStatus.IN_WORK, "", null, null
    );

    public static final Order terriOrder = new Order(
            13, "Terri", "+380983333455,terri.vasquez@gmail.com", 350,
            0, 350, LocalDate.parse("2023-02-24"), LocalDate.parse("2023-02-25"),
            OrderStatus.IN_WORK, "", null, null
    );

    public static final Order colleenOrder = new Order(
            14, "Colleen", "+380451233344,colleen.burton@gmail.com", 300,
            0, 300, LocalDate.parse("2023-02-25"), LocalDate.parse("2023-02-27"),
            OrderStatus.IN_WORK, "",  null, null
    );

    public static final Order joyOrder = new Order(
            15, "Joy", "+380978676754,joy.george@gmail.com", 250,
            0, 250, LocalDate.parse("2023-02-25"), LocalDate.parse("2023-02-28"),
            OrderStatus.IN_WORK, "", null, null
    );

    public static final Order jackieOrder = new Order(
            16, "Jackie", "+380662300344,jackie.lynch@gmail.com", 250,
            0, 250, LocalDate.parse("2023-02-26"), LocalDate.parse("2023-03-01"),
            OrderStatus.IN_WORK, "deposite", null, null
    );

    public static final Order markOrder = new Order(
            17, "Mark", "+380630049867,mark.rinehart@gmail.com", 1200,
            0, 1200, LocalDate.parse("2023-02-26"), LocalDate.parse("2023-03-01"),
            OrderStatus.IN_WORK, "", null, null
    );

    public static final Order stevenOrder = new Order(
            18, "Steven", "+380991233474,steven.curley@gmail.com", 1100,
            0, 1100, LocalDate.parse("2023-02-26"), LocalDate.parse("2023-03-02"),
            OrderStatus.IN_WORK, "", null, null
    );

    public static final List<Order> allOrders = List.of(stevenOrder, markOrder, jackieOrder, joyOrder, colleenOrder,
            terriOrder, sarahOrder, lauraOrder, michelleOrder, sharonOrder, ruthOrder, carolOrder, donnaOrder,
            sandraOrder, helenOrder, bettyOrder, karenOrder, nancyOrder);

    public static final int ALL_ORDERS_COUNT = allOrders.size();

    public static List<OrderTo> getFirstFiveTos() {
        Order order1 = new Order(stevenOrder);
        Order order2 = new Order(markOrder);
        Order order3 = new Order(jackieOrder);
        order3.setDocuments(List.of(jackieOrder_doc));
        Order order4 = new Order(joyOrder);
        order4.setDocuments(List.of(joyOrder_doc));
        Order order5 = new Order(colleenOrder);
        order5.setDocuments(List.of(colleenOrder_doc));
        return OrderUtil.getTos(List.of(order1, order2, order3, order4, order5));
    }

    public static OrderDetailTo getDetailTo() {
        Order detailOrder = new Order(karenOrder);
        detailOrder.setDocuments(List.of(DocumentTestData.karenOrder_doc1, DocumentTestData.karenOrder_doc2));
        detailOrder.setApostilles(List.of(ApostilleTestData.karenOrder_apos));

        return OrderUtil.getDetailTo(detailOrder);
    }

    public static Order getNew() {
        return new Order("Alison", "+380991299474,alison.bunda@gmail.com",
                100, 0, 100,
                LocalDate.parse("2023-09-26"), LocalDate.parse("2023-09-27"), OrderStatus.IN_WORK, "",
                null, null);
    }

    public static int getNewId() {
        return allOrders.get(0).id() + 1;
    }

    public static Order getUpdated() {
        Order updated = new Order(karenOrder);
        updated.setOrderStatus(OrderStatus.COMPLETED);

        return updated;
    }
}
