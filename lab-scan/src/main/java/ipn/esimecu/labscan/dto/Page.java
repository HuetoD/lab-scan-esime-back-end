package ipn.esimecu.labscan.dto;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class Page<T> {
    private ArrayList<T> data;
    @JsonProperty ("total_items")
    private int totalItems;
    @JsonProperty ("current_pages")
    private int currentPages;
    @JsonProperty ("total_pages")
    private int totalPages;
}
