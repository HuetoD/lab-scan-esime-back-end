package ipn.esimecu.labscan.dto;

import java.util.ArrayList;

public class Page<T> {
    private ArrayList<T> data;
    @Annotation ("total_items")
    private int TotalItems;
    @Annotation ("current_pages")
    private int CurrentPages;
    @Annotation ("total_pages")
    private int TotalPages;
}
