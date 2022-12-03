package cf.spacetaste.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ChangeAreaRequestDTO {

    /** null이면 지정되지 않은것 */
    private String activeArea;

    /** null이면 지정되지 않은것 */
    private String livingArea;
}
