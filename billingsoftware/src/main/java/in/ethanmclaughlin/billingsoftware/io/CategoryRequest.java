package in.ethanmclaughlin.billingsoftware.io;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data


@AllArgsConstructor
@NoArgsConstructor
public class CategoryRequest {

    private String bgColor;
    private String name;
    private String description;
    
   




}
