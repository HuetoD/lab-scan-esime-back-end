package ipn.esimecu.labscan.dto;

import java.io.Serial;
import java.io.Serializable;
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
public class Page<T> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private ArrayList<T> data;

    @JsonProperty ("total_items")
    private int totalItems;

    @JsonProperty ("current_pages")
    private int currentPages;

    @JsonProperty ("total_pages")
    private int totalPages;
}
