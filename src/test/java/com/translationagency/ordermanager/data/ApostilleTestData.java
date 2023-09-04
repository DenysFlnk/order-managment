package com.translationagency.ordermanager.data;

import com.translationagency.ordermanager.entity.Apostille;

import static com.translationagency.ordermanager.data.OrderTestData.*;

public class ApostilleTestData {

    public static final Apostille karenOrder_apos = new Apostille(1, karenOrder, "Apostille to Spain",
            "Spain", "ABC", 1500);

    public static final Apostille helenOrder_apos = new Apostille(2, helenOrder, "Apostille to Germany",
            "Germany", "ABC", 2000);

    public static final Apostille sandraOrder_apos = new Apostille(3, sandraOrder, "Apostille to Ukraine",
            "Ukraine", "ABC", 1000);

    public static final Apostille donnaOrder_apos = new Apostille(4, donnaOrder, "Apostille to Spain",
            "Spain", "ABC", 1350);

    public static final Apostille markOrder_apos = new Apostille(5, markOrder, "Apostille to Italy",
            "Italy", "ABC", 1200);

    public static final Apostille stevenOrder_apos = new Apostille(6, stevenOrder, "Apostille to Latvia",
            "Latvia", "ABC", 1100);
}
