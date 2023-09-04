package com.translationagency.ordermanager.data;

import com.translationagency.ordermanager.entity.Document;
import com.translationagency.ordermanager.entity.Language;

import static com.translationagency.ordermanager.data.OrderTestData.*;
import static com.translationagency.ordermanager.data.TranslatorTestData.*;
public class DocumentTestData {
    public static final Document nancyOrder_doc = new Document(1, nancyOrder, Language.ENGLISH, false,
            300, 1.5, 0, 450, jared, "180/1000", 270);

    public static final Document karenOrder_doc1 = new Document(2, karenOrder, Language.GERMAN, false,
            350, 1.0, 250, 600, mary, "200/1000", 200);

    public static final Document karenOrder_doc2 = new Document(19, karenOrder, Language.ENGLISH, false,
            300, null, 0, 450, null, null, null);

    public static final Document bettyOrder_doc = new Document(3, bettyOrder, Language.KOREAN, false,
            500, 3.0, 250, 1750, mary, "400/1500", 800);

    public static final Document helenOrder_doc = new Document(4, helenOrder, Language.GERMAN, false,
            350, 1.0, 0, 350, patricia, "210/1000", 210);

    public static final Document sandraOrder_doc = new Document(5, sandraOrder, Language.ENGLISH, false,
            300, 1.0, 0, 300, elizabeth, "100/1000", 100);

    public static final Document donnaOrder_doc = new Document(6, donnaOrder, Language.FRENCH, false,
            400, 1.0, 0, 400, barbara, "330/1500", 231);

    public static final Document carolOrder_doc1 = new Document(7, carolOrder, Language.ENGLISH, false,
            300, 1.0, 0, 300, elizabeth, "100/1000", 100);

    public static final Document carolOrder_doc2 = new Document(8, carolOrder, Language.ENGLISH, false,
            300, 1.0, 250, 550, elizabeth, "100/1000", 100);

    public static final Document ruthOrder_doc = new Document(9, ruthOrder, Language.FRENCH, false,
            350, 1.0, 250, 600, patricia, "210/1000", 210);

    public static final Document sharonOrder_doc1 = new Document(10, sharonOrder, Language.ENGLISH, false,
            300, 1.0, 0, 300, elizabeth, "100/1000", 100);

    public static final Document sharonOrder_doc2 = new Document(11, sharonOrder, Language.ENGLISH, false,
            300, 1.0, 250, 550, elizabeth, "100/1000", 100);

    public static final Document michelleOrder_doc = new Document(12, michelleOrder, Language.ITALIAN, false,
            330, 1.0, 0, 330, elizabeth, "220/1000", 220);

    public static final Document lauraOrder_doc = new Document(13, lauraOrder, Language.ESTONIAN, false,
            320, 1.0, 250, 570, barbara, "400/1500", 280);

    public static final Document sarahOrder_doc = new Document(14, sarahOrder, Language.ENGLISH, false,
            300, 1.0, 0, 300, elizabeth, "100/1000", 100);

    public static final Document terriOrder_doc = new Document(15, terriOrder, Language.GERMAN, false,
            350, 1.0, 0, 350, patricia, "210/1000", 210);

    public static final Document colleenOrder_doc = new Document(16, colleenOrder, Language.ENGLISH, false,
            300, 1.0, 0, 300, elizabeth, "100/1000", 100);

    public static final Document joyOrder_doc = new Document(17, joyOrder, Language.POLISH, false,
            250, 1.0, 0, 250, margaret, "150/1000", 150);

    public static final Document jackieOrder_doc = new Document(18, jackieOrder, Language.POLISH, false,
            250, 1.0, 0, 250, margaret, "200/1000", 200);
}
