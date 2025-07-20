package model;

import java.sql.Date;

public class RequestForLeave {
    private int rid;
    private String title;
    private Date from;
    private Date to;
    private String reason;
    private int status;
    private int createdBy;
    private Integer processedBy; // có thể null

    // Getters và Setters
    // ... (bạn có thể generate trong NetBeans)
}
