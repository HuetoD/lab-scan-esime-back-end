package ipn.esimecu.labscan.dto;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

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

    private List<T> data;

    @JsonProperty ("total_items")
    private int totalItems;

    @JsonProperty ("current_page")
    private int currentPage;

    @JsonProperty ("total_pages")
    private int totalPages;
}
